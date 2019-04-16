package com.nettm.exercise.ast.parse;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * 描述
 */
public class ParseVisitorAdapter extends VoidVisitorAdapter {

    private JavaClass javaClass;

    private Method method;

    public ParseVisitorAdapter(JavaClass javaClass) {
        this.javaClass = javaClass;
    }

    @Override
    public void visit(PackageDeclaration n, Object arg) {
        super.visit(n, arg);

        javaClass.setJavaPackage(n.getNameAsString());
    }

    @Override
    public void visit(ImportDeclaration n, Object arg) {
        super.visit(n, arg);

        javaClass.getImportList().add(n.getNameAsString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        super.visit(n, arg);

        javaClass.setIsInterface(n.isInterface());
        javaClass.setName(n.getNameAsString());
        javaClass.setFullName(javaClass.getJavaPackage() + "." + n.getNameAsString());

        n.getImplementedTypes().forEach(s -> javaClass.getInterfaceList().add(s.getNameAsString()));
    }

    @Override
    public void visit(MethodCallExpr n, Object arg) {
        super.visit(n, arg);

        MethodCall call = new MethodCall();
        this.method.getMethodCallList().add(call);
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        super.visit(n, arg);

        Method method = new Method();
        this.method = method;

        javaClass.getMethodList().add(method);
    }

    @Override
    public void visit(FieldDeclaration n, Object arg) {
        super.visit(n, arg);

        Field field = new Field();

        javaClass.getFieldList().add(field);
    }

}
