package com.nettm.exercise.ast.parse;

import com.github.javaparser.Range;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法
 */
@Data
public class Method {

    /**
     * 方法名称
     */
    private String name;

    /**
     * 返回类型（包含包路径）
     */
    private String returnType;

    /**
     * 是否是静态方法
     */
    private Boolean isStatic;

    /**
     * 访问范围
     */
    private String access;

    /**
     * 代码范围（行数）
     */
    private Range range;

    /**
     * 参数（类型）
     */
    private List<String> params = new ArrayList<>();

    /**
     * 方法内部的调用
     */
    private List<MethodCall> methodCallList = new ArrayList<>();

}
