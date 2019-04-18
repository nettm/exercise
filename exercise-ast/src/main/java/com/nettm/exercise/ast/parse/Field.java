package com.nettm.exercise.ast.parse;

import lombok.Data;

/**
 * 类的成员变量
 */
@Data
public class Field {

    /**
     * 变量名
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 类型（包含包路径）
     */
    private String fullType;

    /**
     * 是否是静态变量
     */
    private Boolean isStatic;

    /**
     * 访问范围
     */
    private String access;

}
