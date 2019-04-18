package com.nettm.exercise.ast.parse;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Java类
 */
@Data
public class JavaClass {

    /**
     * 类名
     */
    private String name;

    /**
     * 类的全名（包含包路径）
     */
    private String fullName;

    /**
     * 源码的路径
     */
    private String source;

    /**
     * 是否是接口
     */
    private Boolean isInterface;

    /**
     * 是否是抽象类
     */
    private Boolean isAbstractClass;

    /**
     * 是否是类
     */
    private Boolean isClass;

    /**
     * 是否是静态类
     */
    private Boolean isStatic;

    /**
     * 是否是注解
     */
    private Boolean isAnnotation;

    /**
     * 是否是枚举
     */
    private Boolean isEnum;

    /**
     * 是否是基本类型
     */
    private Boolean isBaseType;

    /**
     * 包
     */
    private String javaPackage;

    /**
     * 访问范围
     */
    private String access;

    /**
     * 父类
     */
    private String parent;

    /**
     * 类属性
     */
    private List<Field> fieldList = new ArrayList<>();

    /**
     * 方法列表
     */
    private List<Method> methodList = new ArrayList<>();

    /**
     * 接口列表
     */
    private List<String> interfaceList = new ArrayList<>();

    /**
     * import列表
     */
    private List<String> importList = new ArrayList<>();

}
