package org.telluriumsource.ast;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.telluriumsource.annotation.Injector;
import org.telluriumsource.inject.AbstractInjector;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 14, 2010
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class InjectorASTTransformation implements ASTTransformation, Opcodes {
    private static final ClassNode INJECTOR = new ClassNode(Injector.class);
    private static ClassNode injector = null;

    public static ClassNode getInjector(){
        return injector;
    }
    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (nodes.length != 2 || !(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
            throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(nodes));
        }

        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode node = (AnnotationNode) nodes[0];
        if (!INJECTOR.equals(node.getClassNode()))
            return;
        
        ClassNode clazzNode = (ClassNode)parent;
            
        if(AbstractInjector.class.getCanonicalName().equals(clazzNode.getSuperClass().getName())){
            injector = clazzNode;
            createNonLazy(clazzNode);
        }
    }

    private void createNonLazy(ClassNode classNode) {
        final FieldNode fieldNode = classNode.addField("instance", ACC_PUBLIC|ACC_FINAL|ACC_STATIC, classNode, new ConstructorCallExpression(classNode, new ArgumentListExpression()));
        createConstructor(classNode, fieldNode);

        final BlockStatement body = new BlockStatement();
        body.addStatement(new ReturnStatement(new VariableExpression(fieldNode)));
        classNode.addMethod("getInstance", ACC_STATIC|ACC_PUBLIC, classNode, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private void createConstructor(ClassNode classNode, FieldNode field) {

        final List list = classNode.getDeclaredConstructors();
        MethodNode found = null;
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            MethodNode mn = (MethodNode) it.next();
            final Parameter[] parameters = mn.getParameters();
            if (parameters == null || parameters.length == 0) {
                found = mn;
                break;
            }
        }

        if (found == null) {
            final BlockStatement body = new BlockStatement();
            body.addStatement(new IfStatement(
                    new BooleanExpression(new BinaryExpression(new VariableExpression(field), Token.newSymbol("!=",-1,-1), ConstantExpression.NULL)),
                new ThrowStatement(
                        new ConstructorCallExpression(ClassHelper.make(RuntimeException.class),
                                new ArgumentListExpression(
                                        new ConstantExpression("Can't instantiate singleton " + classNode.getName() + ". Use " + classNode.getName() + ".instance" )))),
                new EmptyStatement()));
            classNode.addConstructor(new ConstructorNode(ACC_PRIVATE, body));
        }
    }

}
