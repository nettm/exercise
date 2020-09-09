package com.nettm.exercise.spring.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ClassUtils;

import java.util.List;

@UtilityClass
public class ReflectionUtil {

    public List<Class<?>> getInterface(Class<?> clz) {
        return ClassUtils.getAllInterfaces(clz);
    }

}
