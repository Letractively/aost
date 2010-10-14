package org.telluriumsource.ast;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.*;

import org.objectweb.asm.Opcodes;
import org.telluriumsource.annotation.Provider;
import org.telluriumsource.framework.inject.Injector;
import org.telluriumsource.framework.TelluriumFramework;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Sep 30, 2010
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class ProviderASTTransformation implements ASTTransformation, Opcodes {
    private static final ClassNode PROVIDER = new ClassNode(Provider.class);

    private static final Map<String, ClassInfo> map = new HashMap<String, ClassInfo>();
    private static ClassNode injector = null;

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (nodes.length != 2 || !(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
            throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(nodes));
        }

        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode node = (AnnotationNode) nodes[0];
        ClassNode clazzNode = (ClassNode)parent;

        if (!PROVIDER.equals(node.getClassNode()))
            return;

        ClassNode concrete = clazzNode;

        ClassNode clazz;
        final Expression clazzExpr = node.getMember("type");
        if(clazzExpr != null && clazzExpr instanceof ClassExpression){
            clazz = clazzExpr.getType();
        }else{
            clazz = clazzNode;
        }

        String name;
        final Expression nameExpr = node.getMember("name");
        if(nameExpr != null && nameExpr instanceof ConstantExpression){
            name = (String) ((ConstantExpression)nameExpr).getValue();
        }else{
            name = clazz.getName();
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

        if(Injector.class.getName().equals(clazzNode.getName())){
            injector = clazzNode;
            createNonLazy(clazzNode);
            Set<String> names = map.keySet();
            if(names != null && (!names.isEmpty())){
                for(String key: names){
                    ClassInfo info = map.get(key);
                    addInitiateMethod(key, info.getClazz(), info.getConcrete(), info.getScope(), info.isSingleton());
                }
            }
        }else{
            if(injector != null){
                addInitiateMethod(name, clazz, concrete, scope, singleton);
            }else{
                ClassInfo classInfo = new ClassInfo(name, clazz, concrete, singleton, scope);
                map.put(name, classInfo);
            }
        }
    }

    private void addInitiateMethod(String name, ClassNode clazz, ClassNode concrete, String scope, boolean isSingleton){
//        ClassNode cachedNode = new ClassNode(Injector.class);
       final List list = injector.getDeclaredConstructors();
//        final List list = injector.getAllDeclaredMethods();
        MethodNode found = null;
        for (Iterator it = list.iterator(); it.hasNext();) {
            MethodNode mn = (MethodNode) it.next();
            final Parameter[] parameters = mn.getParameters();
            if (parameters == null || parameters.length == 0) {
                found = mn;
                break;
            }
        }

        if(found != null){

            BlockStatement body = ((BlockStatement)found.getCode());
            if(body == null){
                body = new BlockStatement();
            }
            List existingStatements = body.getStatements();
            Statement stm = createCacheStatement(name, clazz, concrete, scope, isSingleton);
            existingStatements.add(stm);
//            found.setCode(body);
        }
    }

    private Statement createCacheStatement(String name, ClassNode clazz, ClassNode concrete, String scope, boolean isSingleton) {
        return new ExpressionStatement(
                new MethodCallExpression(
                        new VariableExpression("this"),
                        new ConstantExpression("addBeanInfo"),
                        new ArgumentListExpression(
                                new Expression[]{
                                        new ConstantExpression(name),
                                        new ClassExpression(clazz),
                                        new ClassExpression(concrete),
                                        new ConstantExpression(scope),
                                        new ConstantExpression(isSingleton)
                                }

                        )
                )
        );
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
/*

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

        if (found == null) {
            final BlockStatement body = new BlockStatement();
            body.addStatement(
                    new ExpressionStatement(
                            new MethodCallExpression(
                                    new MethodCallExpression(
                                            new ClassExpression(new ClassNode(TelluriumFramework.class)),
                                            new ConstantExpression("getInstance"),
                                            new ArgumentListExpression()
                                    ),
                                    new ConstantExpression("registerBean"),
                                    new ArgumentListExpression(
                                            new Expression[]{
                                                    new ConstantExpression(name),
                                                    new ClassExpression(classNode),
                                                    new ConstantExpression(scope),
                                                    new ConstantExpression(singleton)

                                            }
                                    )
                            )
                    )
            );

            classNode.addConstructor(new ConstructorNode(ACC_PUBLIC, body));
        }
    }
*/

    class ClassInfo {
        private String name;

        private ClassNode clazz;

        private ClassNode concrete;

        private boolean singleton = true;

        private String scope;

        ClassInfo(String name, ClassNode clazz, ClassNode concrete, boolean singleton, String scope) {
            this.name = name;
            this.clazz = clazz;
            this.concrete = concrete;
            this.singleton = singleton;
            this.scope = scope;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ClassNode getClazz() {
            return clazz;
        }

        public void setClazz(ClassNode clazz) {
            this.clazz = clazz;
        }

        public ClassNode getConcrete() {
            return concrete;
        }

        public void setConcrete(ClassNode concrete) {
            this.concrete = concrete;
        }

        public boolean isSingleton() {
            return singleton;
        }

        public void setSingleton(boolean singleton) {
            this.singleton = singleton;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }
}
