package com.nettm.exercise.ast.parse;

/**
 * 描述
 */
public enum Keyword {

    DEFAULT("default", false),
    PUBLIC("public", false),
    PROTECTED("protected", false),
    PRIVATE("private", false),
    ABSTRACT("abstract", false),
    STATIC("static", false),
    FINAL("final", false),
    TRANSIENT("transient", false),
    VOLATILE("volatile", false),
    SYNCHRONIZED("synchronized", false),
    NATIVE("native", false),
    STRICTFP("strictfp", false),
    TRANSITIVE("transitive", false),
    PACKAGE_PRIVATE("", true);

    private final String codeRepresentation;

    private final boolean pseudoKeyword;

    Keyword(String codeRepresentation, boolean pseudoKeyword) {
        this.codeRepresentation = codeRepresentation;
        this.pseudoKeyword = pseudoKeyword;
    }

    /**
     * @return the Java keyword represented by this enum constant.
     */
    public String asString() {
        return codeRepresentation;
    }

    /**
     * @return true when this keyword is not an actual keyword in source code. The only case is "PACKAGE_PRIVATE."
     */
    public boolean isPseudoKeyword() {
        return pseudoKeyword;
    }

}
