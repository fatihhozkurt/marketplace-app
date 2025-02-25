package com.fatih.marketplace_app.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jasypt.encryption.StringEncryptor;

/**
 * JPA attribute converter that encrypts and decrypts string values using Jasypt.
 */
@Converter
public class JasyptAttributeConverter implements AttributeConverter<String, String> {

    private StringEncryptor encryptor;

    /**
     * Retrieves the {@link StringEncryptor} instance from the Spring application context.
     *
     * @return the {@link StringEncryptor} instance
     */
    private StringEncryptor getEncryptor() {
        if (encryptor == null) {
            encryptor = SpringContextHolder.getBean(StringEncryptor.class);
        }
        return encryptor;
    }

    /**
     * Encrypts the given attribute before persisting it to the database.
     *
     * @param attribute the plain text attribute to be encrypted
     * @return the encrypted value to be stored in the database, or {@code null} if the input is {@code null}
     */
    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        return getEncryptor().encrypt(attribute);
    }

    /**
     * Decrypts the given database column value when retrieving it as an entity attribute.
     *
     * @param dbData the encrypted value from the database
     * @return the decrypted plain text value, or {@code null} if the input is {@code null}
     */
    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return getEncryptor().decrypt(dbData);
    }
}
