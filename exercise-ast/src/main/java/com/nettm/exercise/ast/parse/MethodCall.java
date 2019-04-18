package com.nettm.exercise.ast.parse;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 调用方法
 */
@Data
public class MethodCall {

    /**
     * 方法名
     */
    private String name;

    /**
     * 类型（包含包路径）
     */
    private String type;

    /**
     * 参数（类型）
     */
    private List<String> params = new ArrayList<>();

}
