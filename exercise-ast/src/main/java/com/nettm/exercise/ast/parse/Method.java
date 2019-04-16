package com.nettm.exercise.ast.parse;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法
 */
@Data
public class Method {

    private String returnType;

    private String name;

    private Boolean isStatic;

    private String access;

    private List<String> params = new ArrayList<>();

    private List<MethodCall> methodCallList = new ArrayList<>();

}
