package com.nettm.exercise.ast.parse;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法
 */
@Data
public class Method {

    private JavaClass returnType;

    private String name;

    private Boolean isStatic;

    private String access;

    private List<JavaClass> params = new ArrayList<>();

    private List<MethodCall> methodCallList = new ArrayList<>();

}
