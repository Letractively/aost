package org.telluriumsource.ast;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.MetaClassHelper;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.telluriumsource.annotation.Provider;
import org.telluriumsource.framework.ASTUtil;
import org.telluriumsource.framework.TelluriumFramework;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Sep 30, 2010
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class ProviderASTTransformation implements ASTTransformation, Opcodes {
    private static final ClassNode MY_TYPE = new ClassNode(Provider.class);

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (nodes.length != 2 || !(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
            throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(nodes));
        }

        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode node = (AnnotationNode) nodes[0];
        
        if (!MY_TYPE.equals(node.getClassNode()))
            return;

        String name = "";
        final Expression nameExpr = node.getMember("name");
        if(nameExpr != null && nameExpr instanceof ConstantExpression){
            name = (String) ((ConstantExpression)nameExpr).getValue();
        }

        ClassNode clazz;
        final Expression clazzExpr = node.getMember("type");
        if(clazzExpr != null && clazzExpr instanceof ClassExpression){
            clazz = ((ClassExpression)clazzExpr).getType();
        }else{
            clazz = (ClassNode)parent;
        }

        String scope = "Session";
        final Expression scopeExpr = node.getMember("scope");
        if(scopeExpr != null && scopeExpr instanceof ConstantExpression){
            scope = (String) ((ConstantExpression)scopeExpr).getValue();
        }

        boolean singleton = true;   
        final Expression singletonExpr = node.getMember("singleton");
        if(singletonExpr != null && singletonExpr instanceof ConstantExpression){
            singleton = (Boolean)((ConstantExpression)singletonExpr).getValue();
        }

        addConstructor(name, clazz, scope, singleton);

    }

    private void addConstructor(String name, ClassNode classNode, String scope, boolean singleton){

        final List list = classNode.getDeclaredConstructors();
        MethodNode found = null;
        for (Iterator it = list.iterator(); it.hasNext();) {
            MethodNode mn = (MethodNode) it.next();
            final Parameter[] parameters = mn.getParameters();
            if (parameters == null || parameters.length == 0) {
                found = mn;
                break;
            }
        }

//        ClassExpression clazz = new ClassExpression(classNode);
        List<ASTNode> nodes = null;
        try {
//            nodes = ASTUtil.getProviderNodes(name, Class.forName(classNode.getName()), scope, singleton);
            nodes = ASTUtil.getProviderNodes(name, this.getClass(), scope, singleton);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        BlockStatement stm = (BlockStatement) nodes.get(0);
        ReturnStatement rst = (ReturnStatement) stm.getStatements().get(0);
        BlockStatement b = new BlockStatement();

        b.addStatement(
            new ExpressionStatement(rst.getExpression())
        );

        if (found == null) {
            final BlockStatement body = new BlockStatement();
            body.addStatement(
                    new ExpressionStatement(
                            new MethodCallExpression(
                                    new PropertyExpression(
//                                            new VariableExpression("TelluriumFramework"),
                                            new ClassExpression(new ClassNode(TelluriumFramework.class)),
                                            new ConstantExpression("instance")
                                    ),
                                    new ConstantExpression("registerBean"),
                                    new ArgumentListExpression(
                                            new Expression[]{
                                                    new VariableExpression(name),
//                                          new VariableExpression(classNode),
//                                          new VariableExpression(clazz),
 //                                                   new VariableExpression("this"),
                                                    new ClassExpression(classNode),
                                                    new ConstantExpression(scope),
                                                    new ConstantExpression(singleton)

                                            }
                                    )
                            )
                    )
            );

//            final BlockStatement body = stm;

   //         classNode.addConstructor(new ConstructorNode(ACC_PUBLIC, body));
              classNode.addConstructor(new ConstructorNode(ACC_PUBLIC, b));

/*            body.addStatement(new IfStatement(
                    new BooleanExpression(new BinaryExpression(new VariableExpression(field), Token.newSymbol("!=",-1,-1), ConstantExpression.NULL)),
                new ThrowStatement(
                        new ConstructorCallExpression(ClassHelper.make(RuntimeException.class),
                                new ArgumentListExpression(
                                        new ConstantExpression("Can't instantiate singleton " + classNode.getName() + ". Use " + classNode.getName() + ".instance" )))),
                new EmptyStatement()));
            classNode.addConstructor(new ConstructorNode(ACC_PRIVATE, body));
            */
        }
    }

}
