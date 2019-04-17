package com.nettm.exercise.ast.parse;

import com.github.javaparser.Range;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.type.Type;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 描述
 *
 * @author mu.tian
 * @date 2019-04-17 12:09
 */
@UtilityClass
public class ParseUtils {

    public String getReturnType(JavaClass javaClass, Type type) {
        if (Objects.isNull(type)) {
            return null;
        }

        String strType = type.asString();
        if (strType.contains("<")) {
            strType = StringUtils.substringBefore(strType, "<");
        }

        return getFullType(javaClass, strType);
    }

    public String getFullType(JavaClass javaClass, String type) {
        List<String> importList = javaClass.getImportList();
        for (String im : importList) {
            if (im.endsWith(type)) {
                return im;
            }
        }

        switch (type) {
            case "String":
                return String.class.getName();
            case "Integer":
                return Integer.class.getName();
            case "Long":
                return Long.class.getName();
            case "Short":
                return Short.class.getName();
            case "Double":
                return Double.class.getName();
            case "Float":
                return Float.class.getName();
            case "Char":
                return Character.class.getName();
            case "Boolean":
                return Boolean.class.getName();
        }

        return type;
    }

    public String getAccess(NodeList<Modifier> nodeList) {
        if (Objects.isNull(nodeList) || nodeList.isEmpty()) {
            return null;
        }

        String access = nodeList.get(0).toString();
        if (access.trim().equalsIgnoreCase(Keyword.PUBLIC.asString())) {
            return Keyword.PUBLIC.asString();
        }
        if (access.trim().equalsIgnoreCase(Keyword.PROTECTED.asString())) {
            return Keyword.PROTECTED.asString();
        }
        if (access.trim().equalsIgnoreCase(Keyword.PRIVATE.asString())) {
            return Keyword.PRIVATE.asString();
        }

        return null;
    }

    public Optional<Method> getMethodByRange(JavaClass javaClass, Range range) {
        if (Objects.isNull(range)) {
            return Optional.empty();
        }

        List<Method> methodList = javaClass.getMethodList();
        for (Method method : methodList) {
            if (method.getRange().contains(range)) {
                return Optional.of(method);
            }
        }

        return Optional.empty();
    }

    public Optional<String> getFullFieldCall(JavaClass javaClass, String callName) {
        if (Objects.isNull(callName)) {
            return Optional.empty();
        }

        List<Field> fiedlList = javaClass.getFieldList();
        for (Field field : fiedlList) {
            if (field.getName().equals(callName)) {
                return Optional.of(field.getFullType());
            }
        }

        return Optional.empty();
    }

}
