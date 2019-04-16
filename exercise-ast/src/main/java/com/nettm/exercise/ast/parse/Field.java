package com.nettm.exercise.ast.parse;

import lombok.Data;

/**
 * 类的成员变量
 */
@Data
public class Field {

    private String type;

    private String name;

    private Boolean isStatic;

    private String access;

}
