package com.nettm.exercise.spring.bean;

import com.google.common.reflect.Reflection;
import com.nettm.exercise.spring.cat.ICat;
import com.nettm.exercise.spring.utils.ReflectionUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        super();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization:" + beanName);

        List<Class<?>> interList = ReflectionUtil.getInterface(bean.getClass());
        if (interList.contains(ICat.class)) {
            return Reflection.newProxy(ICat.class, new MyInvocationHandler(bean));
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization:" + beanName);
        return bean;
    }
}
