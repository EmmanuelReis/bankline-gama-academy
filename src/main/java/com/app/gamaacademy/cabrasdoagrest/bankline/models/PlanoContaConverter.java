package com.app.gamaacademy.cabrasdoagrest.bankline.models;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PlanoContaConverter implements AttributeConverter<PlanoConta, String> {
    @Override
    public String convertToDatabaseColumn(PlanoConta planoConta) {
        return planoConta != null ? planoConta.getCode() : null;
    }

    @Override
    public PlanoConta convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(PlanoConta.values())
          .filter(plano -> plano.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
