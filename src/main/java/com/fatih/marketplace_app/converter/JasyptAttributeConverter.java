package com.fatih.marketplace_app.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jasypt.encryption.StringEncryptor;

@Converter
public class JasyptAttributeConverter implements AttributeConverter<String, String> {

    private StringEncryptor encryptor;

    private StringEncryptor getEncryptor() {
        if (encryptor == null) {
            encryptor = SpringContextHolder.getBean(StringEncryptor.class);
        }
        return encryptor;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        return getEncryptor().encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return getEncryptor().decrypt(dbData);
    }
}
