package com.nettm.exercise.mybatis;

import java.io.*;

public class CustomClassLoader extends ClassLoader {

    public CustomClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        byte[] b = new byte[0];
        try {
            b = loadClassFromFile(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassFromFile(String fileName) throws IOException {
        try (InputStream inputStream = new FileInputStream(new File(fileName))) {
            byte[] buffer;
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = 0;
            try {
                while ((nextValue = inputStream.read()) != -1) {
                    byteStream.write(nextValue);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer = byteStream.toByteArray();
            return buffer;
        }
    }

}
