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
import org.telluriumsource.inject.AbstractInjector;


/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Sep 30, 2010
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class ProviderASTTransformation implements ASTTransformation, Opcodes {
    private static final ClassNode PROVIDER = new ClassNode(Provider.class);

    private static final Map<String, ClassInfo> map = new HashMap<String, ClassInfo>();

    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (nodes.length != 2 || !(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
            throw new RuntimeException("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + Arrays.asList(nodes));
        }

        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode node = (AnnotationNode) nodes[0];

        if (!PROVIDER.equals(node.getClassNode()))
            return;

        ClassNode clazzNode = (ClassNode)parent;
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

        ClassNode injector = InjectorASTTransformation.getInjector();
        if(injector != null){
            Set<String> names = map.keySet();
            if(names != null && (!names.isEmpty())){
                for(String key: names){
                    ClassInfo info = map.get(key);
                    addInitiateMethod(key, info.getClazz(), info.getConcrete(), info.getScope(), info.isSingleton());
                }
                map.clear();
            }

            addInitiateMethod(name, clazz, concrete, scope, singleton);
        }else{
            ClassInfo classInfo = new ClassInfo(name, clazz, concrete, singleton, scope);
            map.put(name, classInfo);
        }
    }

    private void addInitiateMethod(String name, ClassNode clazz, ClassNode concrete, String scope, boolean isSingleton){
       ClassNode injector = InjectorASTTransformation.getInjector();
       final List list = injector.getDeclaredConstructors();

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
