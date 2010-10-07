package org.telluriumsource.ast;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.Statement;
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

            if(!lazy){
                addMethodToConstructor(name, fieldNode);
            }

/*            if (!lazy){
                create(fieldNode);
            }else {
                final Expression init = getInitExpr(fieldNode);                
                createLazy(fieldNode, init);
                // @Inject not meaningful with primitive so convert to wrapper if needed
                if (ClassHelper.isPrimitiveType(fieldNode.getType())) {
                    fieldNode.setType(ClassHelper.getWrapper(fieldNode.getType()));
                }
            }*/
        }

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

        ClassNode classNode = fieldNode.getType();

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

    private void create(FieldNode fieldNode){

    }

    private void createLazy(FieldNode fieldNode, final Expression initExpr) {
        final BlockStatement body = new BlockStatement();

        addNonThreadSafeBody(body, fieldNode, initExpr);

        addMethod(fieldNode, body, fieldNode.getType());
    }

    private void addMethod(FieldNode fieldNode, BlockStatement body, ClassNode type) {
        int visibility = ACC_PUBLIC;
        if (fieldNode.isStatic()) visibility |= ACC_STATIC;
        final String name = "get" + MetaClassHelper.capitalize(fieldNode.getName().substring(1));
        fieldNode.getDeclaringClass().addMethod(name, visibility, type, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private Expression getInitExpr(FieldNode fieldNode) {
        Expression initExpr = fieldNode.getInitialValueExpression();
        fieldNode.setInitialValueExpression(null);

        if (initExpr == null)
            initExpr = new ConstructorCallExpression(fieldNode.getType(), new ArgumentListExpression());

        return initExpr;
    }

    private void addNonThreadSafeBody(BlockStatement body, FieldNode fieldNode, Expression initExpr) {
        final Expression fieldExpr = new VariableExpression(fieldNode);
        body.addStatement(new IfStatement(
                new BooleanExpression(new BinaryExpression(fieldExpr, COMPARE_NOT_EQUAL, NULL_EXPR)),
                new ExpressionStatement(fieldExpr),
                new ExpressionStatement(new BinaryExpression(fieldExpr, ASSIGN, initExpr))
        ));
    }
}
