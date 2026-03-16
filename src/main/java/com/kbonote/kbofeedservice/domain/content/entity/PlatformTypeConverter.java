package com.kbonote.kbofeedservice.domain.content.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PlatformTypeConverter implements AttributeConverter<PlatformType, String> {

    @Override
    public String convertToDatabaseColumn(PlatformType attribute) {
        return attribute == null ? null : attribute.getDbValue();
    }

    @Override
    public PlatformType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PlatformType.fromDbValue(dbData);
    }
}
