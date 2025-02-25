package com.fatih.marketplace_app.converter;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Jasypt encryption.
 */
@Configuration
public class JasyptConfig {

    /**
     * Defines a Jasypt {@link StringEncryptor} bean for encrypting and decrypting string values.
     *
     * @return a configured {@link StringEncryptor} instance
     */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("kafeintechnologysolutions");
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        return encryptor;
    }
}
