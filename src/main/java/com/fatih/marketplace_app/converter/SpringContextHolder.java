package com.fatih.marketplace_app.converter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Utility class to hold the Spring {@link ApplicationContext} and provide access to beans.
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Sets the {@link ApplicationContext} instance to be used statically.
     *
     * @param applicationContext the application context provided by Spring
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.context = applicationContext;
    }

    /**
     * Retrieves a Spring-managed bean of the specified type.
     *
     * @param beanClass the class type of the bean
     * @param <T>       the generic type of the bean
     * @return an instance of the requested bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}