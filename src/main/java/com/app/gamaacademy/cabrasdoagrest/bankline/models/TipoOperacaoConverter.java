package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoOperacaoConverter implements AttributeConverter<TipoOperacao, String> {
    @Override
    public String convertToDatabaseColumn(TipoOperacao planoConta) {
        return planoConta == null ? null : planoConta.getCodigo();
    }

    @Override
    public TipoOperacao convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(TipoOperacao.values())
          .filter(plano -> plano.getCodigo().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
