package com.csdm.newsfeed.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Offers a method to return a bean by class without having to autowire it.
 *
 */
@Configuration
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        context = applicationContext;
    }

    /**
     * Returns a bean for the given class
     *
     * @param beanClass {@link Class}
     * @param <T>
     * @return instance of beanClass
     */
    public static <T> T getBean(final Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    /**
     * @param beanName {@link String} representing bean name
     * @param <T>
     * @return instance of beanClass
     */
    public static <T> T getBeanByName(final String beanName) {
        return (T) context.getBean(beanName);
    }
}
