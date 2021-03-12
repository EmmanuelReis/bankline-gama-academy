package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoPlanoContaConverter implements AttributeConverter<TipoPlanoConta, String> {
    @Override
    public String convertToDatabaseColumn(TipoPlanoConta planoConta) {
        return planoConta == null ? null : planoConta.getCodigo();
    }

    @Override
    public TipoPlanoConta convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(TipoPlanoConta.values())
          .filter(plano -> plano.getCodigo().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
