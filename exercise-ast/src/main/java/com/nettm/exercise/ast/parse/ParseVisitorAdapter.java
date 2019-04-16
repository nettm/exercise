package com.nettm.exercise.ast.parse;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * 描述
 */
public class ParseVisitorAdapter extends VoidVisitorAdapter {

    private JavaClass javaClass;

    public ParseVisitorAdapter(JavaClass javaClass) {
        this.javaClass = javaClass;
    }

    @Override
    public void visit(MethodCallExpr n, Object arg) {
        System.out.println(" [L " + n.getBegin().get().line + "] " + n.getNameAsString());
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        System.out.println(" [* " + n.getBegin().get().line + "] " + n.getDeclarationAsString());
    }

    @Override
    public void visit(FieldDeclaration n, Object arg) {
        System.out.println(" [# " + n.getBegin().get().line + "] " + n);
    }

}
