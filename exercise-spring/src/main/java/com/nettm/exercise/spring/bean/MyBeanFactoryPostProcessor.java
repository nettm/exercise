package com.nettm.exercise.spring.bean;

import com.nettm.exercise.spring.cat.ICat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        super();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory");

        // 手动注入bean
        ApplicationContext applicationContext = MyApplicationContextAware.getApplicationContext();

        // 将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

        // 获取bean工厂并转换为DefaultListableBeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        String[] beanNamesForType = defaultListableBeanFactory.getBeanNamesForType(ICat.class);
        System.out.println("beanNamesForType:" + Arrays.toString(beanNamesForType));

        defaultListableBeanFactory.removeBeanDefinition(beanNamesForType[0]);

        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ICat.class);

        // 注册bean
        defaultListableBeanFactory.registerBeanDefinition("com.nettm.exercise.spring.cat.Persia", beanDefinitionBuilder.getRawBeanDefinition());
        ICat bean = MyApplicationContextAware.getBean(ICat.class);
        bean.run();
    }
}
