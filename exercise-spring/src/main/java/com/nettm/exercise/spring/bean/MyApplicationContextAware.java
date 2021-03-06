package com.nettm.exercise.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MyApplicationContextAware implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Objects.isNull(context)) {
            context = applicationContext;
        }
    }


    public static ApplicationContext getApplicationContext() {
        return context;
    }


    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}
