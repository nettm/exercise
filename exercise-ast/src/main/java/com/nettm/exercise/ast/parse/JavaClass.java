package com.nettm.exercise.ast.parse;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Java类
 */
@Data
public class JavaClass {

    private String name;

    private String fullName;

    private String source;

    private Boolean isInterface;

    private Boolean isAbstractClass;

    private Boolean isClass;

    private Boolean isStatic;

    private Boolean isAnnotation;

    private Boolean isEnum;

    private Boolean isBaseType;

    private String javaPackage;

    private String access;

    private List<Field> fieldList = new ArrayList<>();

    private List<Method> methodList = new ArrayList<>();

    private List<JavaClass> interfaceList = new ArrayList<>();

    private List<JavaClass> parentList = new ArrayList<>();

    private List<String> importList = new ArrayList<>();

}
