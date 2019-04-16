package com.nettm.exercise.ast.parse;

import lombok.Data;

/**
 * 类的成员变量
 */
@Data
public class Field {

    private JavaClass javaClass;

    private String name;

    private Boolean isCollection;

    private Boolean isStatic;

    private String access;

}
