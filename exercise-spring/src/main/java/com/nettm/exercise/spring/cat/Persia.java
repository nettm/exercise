package com.nettm.exercise.spring.cat;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Persia implements ICat, InitializingBean, DisposableBean {

    @Override
    public void run() {
        System.out.println("run");
    }

    @Override
    public boolean bathe(String water) {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.print("afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
    }
}
