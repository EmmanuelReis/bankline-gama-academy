package com.app.gamaacademy.cabrasdoagrest.bankline.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class BanklineBusinessException extends ResponseStatusException {

	private String mensagem;
	private String object;
	private String property;
	private String value;
	private String[] args;
	private ErrorCode errorCode;

	public BanklineBusinessException(ErrorCode errorCode) {
		super(HttpStatus.BAD_REQUEST);
		this.errorCode = errorCode;
	}

	public BanklineBusinessException(ErrorCode errorCode, String mensagem) {
		this(errorCode);
		this.mensagem = mensagem;
	}

	public BanklineBusinessException(ErrorCode errorCode, String object, String property, String value) {
		this(errorCode, "");
		this.object = object;
		this.property = property;
		this.value = value;
	}

	public BanklineBusinessException(ErrorCode errorCode, String object, String property, String value, String... args)
			throws Exception {
		this(errorCode, object, property, value);
		if (errorCode.equals(ErrorCode.E0007) && args.length < 4)
			throw new Exception("Para o ErrorCode.E0007 precisa ser informado 4 operadores");
		this.args = args;
	}

	public String getMessage() {
		return this.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("%s - %s.", this.errorCode.name(), this.errorCode.getDescricao()));

		switch (this.errorCode) {
		case E0001:
			sb.append(String.format(" Objeto: %s.", this.object));
			break;
		case E0002:
			sb.append(String.format(" Objeto: %s, campo: %s.", this.object, this.property));
			break;
		case E0003:
		case E0004:
		case E0009:
			sb.append(String.format(" Objeto: %s, campo: %s, valor: %s.", this.object, this.property, this.value));
			break;
		case E0007:
		case E0008:
			// Objeto: x, campo: y, (valor|tamanho): z. Precisar ser %Operador%
			// %limiteInferior% entre
			// %operador% %limiteSuperior%
			sb.append(String.format(" Objeto: %s, campo: %s, %s: %s. Precisa ser %s %s e %s %s", this.object,
					this.property, this.errorCode.equals(ErrorCode.E0007) ? "valor" : "tamanho", this.value,
					this.args[0], this.args[1], this.args[2], this.args[3]));
			break;
		default:
			if (StringUtils.isNotBlank(this.mensagem))
				sb.append(this.getMessage());
			break;
		}

		return sb.toString();
	}

}
