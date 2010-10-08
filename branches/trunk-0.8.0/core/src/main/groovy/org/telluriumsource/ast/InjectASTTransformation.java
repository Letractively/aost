package org.telluriumsource.ast;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.MetaClassHelper;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.objectweb.asm.Opcodes;
import org.telluriumsource.framework.dj.Injector;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Sep 30, 2010
 */

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class InjectASTTransformation implements ASTTransformation, Opcodes {

    private static final Expression NULL_EXPR = ConstantExpression.NULL;

    private static final Token ASSIGN = Token.newSymbol("=", -1, -1);
    private static final Token COMPARE_NOT_EQUAL = Token.newSymbol("!=", -1, -1);

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (nodes.length != 2 || !(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
            throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(nodes));
        }

        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode node = (AnnotationNode) nodes[0];
        if (parent instanceof FieldNode) {
            final FieldNode fieldNode = (FieldNode) parent;

            String name;
            final Expression nameExpr = node.getMember("name");
            if(nameExpr != null && nameExpr instanceof ConstantExpression){
                name = (String) ((ConstantExpression)nameExpr).getValue();
            }else{
                name = fieldNode.getType().getName();
            }

            boolean lazy = false;
            final Expression lazyExpr = node.getMember("lazy");
            if (lazyExpr != null && lazyExpr instanceof ConstantExpression) {
                lazy = (Boolean) ((ConstantExpression) lazyExpr).getValue();
            }

            if(lazy){
                addMethodPointer(name, fieldNode);
            }else{
                addMethodToConstructor(name, fieldNode);
            }
        }
    }

    private void addMethodPointer(String name, FieldNode fieldNode){
        ClassNode classNode = fieldNode.getType();
        final String methodName = "inject" + MetaClassHelper.capitalize(fieldNode.getName());

        BlockStatement body = new BlockStatement();
        body.addStatement(
                new ReturnStatement(
                    new MethodCallExpression(
                        new MethodCallExpression(
                                new ClassExpression(new ClassNode(Injector.class)),
                                new ConstantExpression("getInstance"),
                                new ArgumentListExpression()
                        ),
                        new ConstantExpression("getByName"),
                        new ArgumentListExpression(
                            new ConstantExpression(name)
                        )
                    )
                )

        );

        addMethod(methodName, fieldNode, body, classNode);

        fieldNode.setInitialValueExpression(
                new MethodPointerExpression(
                    new VariableExpression("this"),
                    new ConstantExpression(methodName)
                )
        );
    }

    private void addMethod(String name, FieldNode fieldNode, BlockStatement body, ClassNode type) {
        int visibility = ACC_PUBLIC;
        if (fieldNode.isStatic()) visibility |= ACC_STATIC;
        fieldNode.getDeclaringClass().addMethod(name, visibility, type, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private Statement getInjectStatement(String name, FieldNode fieldNode) {
        final Expression fieldExpr = new VariableExpression(fieldNode);
        return new ExpressionStatement(
                new BinaryExpression(
                        fieldExpr,
                        ASSIGN,
                        new MethodCallExpression(
                                new MethodCallExpression(
                                        new ClassExpression(new ClassNode(Injector.class)),
                                        new ConstantExpression("getInstance"),
                                        new ArgumentListExpression()
                                ),
                                new ConstantExpression("getByName"),
                                new ArgumentListExpression(
                                        new Expression[]{
                                                new ConstantExpression(name)
                                        }
                                )
                        )


                )

        );
    }

    private void addMethodToConstructor(String name, FieldNode fieldNode){

//        ClassNode classNode = fieldNode.getType();
        ClassNode classNode = fieldNode.getDeclaringClass();

        List list = classNode.getDeclaredConstructors();
        if(list == null || list.isEmpty()){
            final BlockStatement emptyBody = new BlockStatement();
            classNode.addConstructor(new ConstructorNode(ACC_PUBLIC, emptyBody));
        }

        list = classNode.getDeclaredConstructors();
        for(int i=0; i<list.size(); i++){
            MethodNode mn = (MethodNode) list.get(i);
            BlockStatement body = ((BlockStatement)mn.getCode());
            if(body == null){
                body = new BlockStatement();
            }
            List existingStatements = body.getStatements();
            Statement stm = getInjectStatement(name, fieldNode);
            existingStatements.add(stm);
        }
    }
}
