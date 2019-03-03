package com.nettm.exercise.lombok;

import lombok.Cleanup;
import lombok.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述
 *
 * @author mu.tian
 * @date 2019-03-03 13:48
 */
public class Person {

    String name;

    public void check(@NonNull String name) throws IOException {
        @Cleanup
        InputStream in = new FileInputStream(new File("/etc/my.cnf"));
        this.name = name;
        int b = in.read();
        System.out.println(b);
    }

    public static void main(String[] args) throws IOException {
        Person p = new Person();
        p.check("abc");
    }

}
