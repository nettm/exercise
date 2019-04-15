package com.nettm.exercise.mybatis;

import java.util.HashMap;
import java.util.Map;

public class DynamicClassloader extends ClassLoader {

    private Map<String, Class<?>> classesMap = new HashMap<>();

    public DynamicClassloader(ClassLoader parent) {
        // Also tried super(parent);
        super(sun.misc.Launcher.getLauncher().getClassLoader());
    }

    // Adding dynamically created classes
    public void defineClass(String name, Class<?> clazz) {
        classesMap.put(name, clazz);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // load from parent
        Class<?> result = findLoadedClass(name);
        if (result != null) {
            return result;
        }
        try {
            result = findSystemClass(name);
        } catch (Exception e) {
            // Ignore these
        }
        if (result != null) {
            return result;
        }
        result = classesMap.get(name);
        if (result == null) {
            throw new ClassNotFoundException(name);
        }
        return result;
    }
}
