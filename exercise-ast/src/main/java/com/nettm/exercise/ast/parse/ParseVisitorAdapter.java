package com.nettm.exercise.ast.parse;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 描述
 */
public class ParseVisitorAdapter extends VoidVisitorAdapter {

    private JavaClass javaClass;

    public ParseVisitorAdapter(JavaClass javaClass) {
        super();
        this.javaClass = javaClass;
    }

    @Override
    public void visit(PackageDeclaration n, Object arg) {
        javaClass.setJavaPackage(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(ImportDeclaration n, Object arg) {
        javaClass.getImportList().add(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        javaClass.setName(n.getNameAsString());
        javaClass.setFullName(javaClass.getJavaPackage() + "." + n.getNameAsString());

        if (n.isClassOrInterfaceDeclaration()) {
            if (n.isInterface()) {
                javaClass.setIsInterface(true);
                javaClass.setIsClass(false);
            } else {
                javaClass.setIsInterface(false);
                javaClass.setIsClass(true);
            }
        }
        javaClass.setIsAbstractClass(n.isAbstract());
        javaClass.setIsEnum(n.isEnumDeclaration());

        n.getImplementedTypes().forEach(s -> javaClass.getInterfaceList().add(s.getNameAsString()));
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Object arg) {
        List<String> paramList = new ArrayList<>();
        n.getArguments().forEach(s -> {
            paramList.add(ParseUtils.getFullType(javaClass, s.toString()));
        });

        MethodCall call = new MethodCall();
        Optional<Expression> scope = n.getScope();
        if (scope.isPresent()) {
            call.setType(ParseUtils.getFullFieldCall(javaClass, scope.get().toString()).orElse(null));
        }
        call.setName(n.getNameAsString());
        call.setParams(paramList);

        Optional<Method> methodOptional = ParseUtils.getMethodByRange(javaClass, n.getRange().orElse(null));
        if (methodOptional.isPresent()) {
            methodOptional.get().getMethodCallList().add(call);
        }

        super.visit(n, arg);
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        List<String> paramList = new ArrayList<>();
        n.getParameters().forEach(s -> {
            paramList.add(ParseUtils.getFullType(javaClass, s.getTypeAsString()));
        });

        Method method = new Method();
        method.setName(n.getNameAsString());
        method.setParams(paramList);
        method.setReturnType(ParseUtils.getReturnType(javaClass, n.getType()));
        method.setAccess(ParseUtils.getAccess(n.getModifiers()));
        method.setRange(n.getRange().orElse(null));
        javaClass.getMethodList().add(method);

        super.visit(n, arg);
    }

    @Override
    public void visit(FieldDeclaration n, Object arg) {
        Field field = new Field();
        VariableDeclarator declarator = n.getVariable(0);
        field.setName(declarator.getNameAsString());
        field.setType(declarator.getTypeAsString());
        field.setFullType(ParseUtils.getFullType(javaClass, declarator.getTypeAsString()));
        field.setAccess(ParseUtils.getAccess(n.getModifiers()));

        javaClass.getFieldList().add(field);

        super.visit(n, arg);
    }

}
