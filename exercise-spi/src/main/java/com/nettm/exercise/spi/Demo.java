package com.nettm.exercise.spi;

import com.nettm.exercise.spi.inner.IDog;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Demo {

    public static void main(String[] args) {
        ServiceLoader<IDog> serviceLoader = ServiceLoader.load(IDog.class, Demo.class.getClassLoader());
        Iterator<IDog> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            IDog dog = iterator.next();
            System.out.println(dog.getClass().getName());
            dog.run();
        }
    }

}
