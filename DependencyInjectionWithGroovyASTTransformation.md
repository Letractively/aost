

# Introduction #

[AST Transformation](http://groovy.codehaus.org/Compile-time+Metaprogramming+-+AST+Transformations) is a cool feature in Groovy to do compile-time meta-programming. This feature somehow looks like the compile-time weaving in AOP programming. This article will
introduce how to use Groovy AST Transformation as a simple dependency injection framework for Groovy classes. The basic idea is to change the Groovy class AST syntax tree at compile time to wire in dependences.

# Implementation #

## Dependence Provider ##

First, we need to define the dependency provider so that the framework knows which instance is used to inject into a class. Thus, we define the following @Provider annotation.

```
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
@GroovyASTTransformationClass("org.telluriumsource.ast.ProviderASTTransformation")
public @interface Provider {
    String name() default "";
    Class type();
    String scope() default "Session";
    boolean singleton() default true;
}
```

The above annotation can be used on a concrete class as follows,

```
@Provider(name="ConsoleAppender", type=Appender.class)
public class ConsoleAppender implements Appender {

  public static final String TE = "TE"

  public void listen(String message) {
    println "${TE}: ${message}"
  }

}
```

where the name is a unique id and the type is the class the annotated class provides instance for. For example, the @Provider annotation on the ConsoleAppender class indicates that it provides instance for the interface Appender.

The AST transformation for @Provider is defined as follows.

```
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

...
}
```

The idea is to add the class information to the constructor of the class annotated by @Injector, which will be discussed later.

## Bean Factory ##

A bean factory is defined to generate instances for different classes.

```
public interface SessionAwareBeanFactory {

    void addBean(String sessionId, String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance);

    Object getByName(String sessionId, String name);

    <T> T getByClass(String sessionId, Class<T> clazz);

    List<Bean> getAllBeans();

    void destroy();
}
```

## Input or Environment Variables ##

Sometimes, we need to inject input data or environment variable to classes. We provided a Lookup interface for you to implement your own class to provide this type of data.

```
public interface Lookup {

    boolean has(String name);
    
    Object getByName(String name);

    <T> T getByClass(Class<T> clazz);
}

```

For example, we can define the run time environment as follows in the Tellurium framework.

```
public class RuntimeEnvironment implements Lookup, Cloneable {

    private Map<String, Object> map = new HashMap<String, Object>();

    public Object getByName(String name) {
        return map.get(name);
    }

    public <T> T getByClass(Class<T> clazz) {
        return (T)map.get(clazz.getCanonicalName());
    }

    ...
}
```

Then we can use the key of the map as an ID for dependency injection.

## Inject Dependency ##

The @Inject annotation is used on field variables to introduce dependency injection.

```
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD})
@GroovyASTTransformationClass("org.telluriumsource.ast.InjectASTTransformation")
public @interface Inject {
    String name() default "";
    boolean lazy() default false;
}
```

where the name is the unique id and the lazy option means the actual value will not be set until the field variable is first called.

Similarly, the AST transformation for @Inject is defined as follows.

```
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class InjectASTTransformation implements ASTTransformation, Opcodes {

    private static final Expression NULL_EXPR = ConstantExpression.NULL;

    private static final Token ASSIGN = Token.newSymbol("=", -1, -1);
    private static final Token COMPARE_NOT_EQUAL = Token.newSymbol("!=", -1, -1);

    private static final List<InjectInfo> list = new ArrayList<InjectInfo>();

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

            ClassNode injector = InjectorASTTransformation.getInjector();

            if(injector == null){
                InjectInfo info = new InjectInfo(fieldNode, name, lazy);
                list.add(info);
            }else{
                if(!list.isEmpty()){
                    for(InjectInfo inf: list){
                        inject(inf.getFieldNode(), inf.getName(), inf.isLazy());
                    }
                    list.clear();
                }
                inject(fieldNode, name, lazy);
            }
        }
    }
    
...
}
```

To inject a variable, simply add the value assignment to its constructor. For lazy wiring, we use the same mechanism that the Groovy @Lazy AST transformation does, i.e., rename the field variable and add a getter to the class.

## Injector ##

The injector is the actual class to handle all the injection work. The injector must be annotated with the @Injector annotation and you should only have one injector for your project.

```
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
@GroovyASTTransformationClass("org.telluriumsource.ast.InjectorASTTransformation")
public @interface Injector {
}

```

The @Injector AST Transformation will change the injector to a Groovy singleton class and record the compile time class node of the injector for @Provider and @Inject AST transformation.

```
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
...
}
```

Be aware that Groovy AST transformation is a compile-time weaving, thus, you need to defined your own injector in your Groovy project. We defined a base injector for users to extend.

```
abstract class AbstractInjector implements SessionAwareBeanFactory {

  private Map<String, Lookup> sLookup = new HashMap<String, Lookup>();

  private SessionAwareBeanFactory beanFactory = new DefaultSessionAwareBeanFactory();

  public abstract String getCurrentSessionId();

  public void addLookupForSession(String sessionId, Lookup lookup){
    this.sLookup.put(sessionId, lookup);
  }

  public void addBeanInfo(String name, Class clazz, Class concrete, String scope, boolean singleton){
    
    addBean(name, clazz, concrete, Scope.valueOf(scope), singleton, null);
  }

  public Object getByName(String name) {
    Lookup lookup = this.sLookup.get(this.getCurrentSessionId());
    if(lookup != null && lookup.has(name)){
      return lookup.getByName(name);
    }

    return this.beanFactory.getByName(this.getCurrentSessionId(), name);
  }

  public <T> T getByClass(Class<T> clazz) {
    return this.beanFactory.getByClass(this.getCurrentSessionId(), clazz);
  }

  public void addBean(String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance) {
    this.beanFactory.addBean(this.getCurrentSessionId(), name, clazz, concrete, scope, singleton, instance);
  }

  public List<Bean> getAllBeans() {
    return this.beanFactory.getAllBeans();
  }

  public void destroy() {
    this.beanFactory.destroy();
  }

  public void addBean(String sessionId, String name, Class clazz, Class concrete, Scope scope, boolean singleton, Object instance) {
    this.beanFactory.addBean(sessionId, name, clazz, concrete, scope, singleton, instance);
  }

  public <T> T getByClass(String sessionId, Class<T> clazz) {
    return this.beanFactory.getByClass(sessionId, clazz)
  }

  public Object getByName(String sessionId, String name) {
    Lookup lookup = this.sLookup.get(sessionId);
    if(lookup != null && lookup.has(name)){
      return lookup.getByName(name);
    }

    return this.beanFactory.getByName(sessionId, name);
  }
...
}
```

To define your own injector, simply extend the AbstractInjector class and add the @Injector annotation to it. For example,

```
@Injector
class MockInjector extends AbstractInjector {
  private static final String DEFAULT = "default";

  String getCurrentSessionId() {
    return DEFAULT;
  }
}
```

## AST Expressions ##

AST transformation introduces new AST expressions to the compile time AST syntax tree. It is non-trival to write AST expressions by hand. The good news is that we can use [AstBuilder](http://groovy.codehaus.org/Building+AST+Guide). For example, we can use the following code to see what the AST expression looks like and then write the same expression to the AST transformation class.

```
  public static List<ASTNode> getInjectNodeByName(String name){
    List<ASTNode> nodes = new AstBuilder().buildFromCode {
      Object var;

      var = MockInjector.getInstance().getByName(name);
    }

    return nodes;
  }

```

You can also rely on [Groovy AST Browser](http://groovy.codehaus.org/Groovy+Console). Here is [an online Groovy AST browser](http://groovyastbrowser.appspot.com/).

# Test #

To test the AST transformation, we need to use GroovySellTestCase to compile the Groovy
script during the testing. For example, we test the InjectASTTransformation in the following way.

```
class InjectASTTransformation_UT extends GroovyShellTestCase {

  public void setUp(){
    super.setUp()

    InjectorASTTransformation.injector = null;
  }

  public void testInject(){
    def y = shell.evaluate("""
      package org.telluriumsource


      import org.telluriumsource.annotation.Provider
      import org.telluriumsource.annotation.Inject
      import org.telluriumsource.annotation.Injector
      import org.telluriumsource.inject.AbstractInjector
      import org.telluriumsource.inject.Bean

      @Injector
      class NewInjector extends AbstractInjector {

          public String getCurrentSessionId(){
            return "default"
          }

      }

      @Provider(name="x")
      public class X {
        private int x = 10;

        public int getValue(){
          return x
        }
      }


      public class Y {
        @Inject(name="x", lazy=true)
        private X x
        private List<Bean> beans;

        public int getValue(){
          return x.getValue()
        }

      }

      Y y = new Y()
      y.beans = NewInjector.instance.getAllBeans()

      y
      """)

      assertNotNull y
      assertNotNull y.getValue()
      assertEquals 10, y.getValue()
      List<Bean> beans = y.beans
      assertNotNull beans
      assertTrue(beans.size() > 0)
      println "Bean size: " + beans.size()
      StringBuffer sb = new StringBuffer(64 * beans.size())
      sb.append("{")
      sb.append(beans.join(",\n"))
      sb.append("}")
      println sb.toString()
  }

}

```

# Usage #

## Download ##

Please download Tellurium Groovy dependency injection module, tellurium-injector, from the following url.

http://aost.googlecode.com/files/tellurium-injector-0.8.0-SNAPSHOT.tar.gz

Or add the following Maven dependency to your project:

```
         <dependency>
            <groupId>org.telluriumsource</groupId>
            <artifactId>tellurium-injector</artifactId>
            <version>${pom.version}</version>
            <scope>compile</scope>
        </dependency>
```

You may also need to add the Tellurium Maven repository to your Maven settings.xml or your pom file.

```
        <repository>
            <id>kungfuters-public-snapshots-repo</id>
            <name>Kungfuters Public Snapshots</name>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <url>http://maven.kungfuters.org/content/repositories/snapshots</url>
        </repository>

```

## Example ##

Tellurium core uses the Groovy dependency injection module to wire the core framework. We like to show you the code snippets on how we use the dependency injection framework in tellurium core. Be aware that _the dependency injection framework only works for Groovy classes, not Java classes_.

The Tellurium core injector simply extends the AbstractInjector class.

```
@Injector
public class TelluriumInjector extends AbstractInjector {

    public String getCurrentSessionId(){
        return SessionManager.getSession().getSessionId();
    }
}

```

The framework wiring is pretty simple.

```
  public synchronized void assembleFramework(Session session){
    TelluriumInjector injector = TelluriumInjector.instance;
    Session original = SessionManager.getSession();
    String sessionId = session.getSessionId();
    SessionManager.setSession(session);
    try{
      session.beanFactory = TelluriumInjector.instance;
      RuntimeEnvironment env = session.getEnv();
      injector.addLookupForSession(sessionId, env);
      injector.addBean(sessionId, RuntimeEnvironment.class.getCanonicalName(),  RuntimeEnvironment.class, RuntimeEnvironment.class, Scope.Session, true, env);
      IResourceBundle i18nBundle =  new org.telluriumsource.crosscut.i18n.ResourceBundle();
      String[] split = env.getLocale().split("_");
      Locale loc = new Locale(split[0], split[1]);
      i18nBundle.updateDefaultLocale(loc);
      env.setResourceBundle(i18nBundle);
      injector.addBean(sessionId, "i18nBundle",  IResourceBundle.class, org.telluriumsource.crosscut.i18n.ResourceBundle.class, Scope.Session, true, i18nBundle);

      Map<String, UiObjectBuilder> customBuilders = env.getEnvironmentVariable("tellurium.uiobject.builder");

      if(customBuilders != null && (!customBuilders.isEmpty())){
        UiObjectBuilderRegistry uobRegistry = injector.getByClass(sessionId, UiObjectBuilderRegistry.class);
          customBuilders.each {key, value ->
            UiObjectBuilder builder = (UiObjectBuilder) Class.forName(value).newInstance()
            uobRegistry.registerBuilder(key, builder)
        }
      }

      String widgetModules = env.getEnvironmentVariable("tellurium.widget.module.included");

      if(widgetModules != null && (!widgetModules.isEmpty())){
        WidgetConfigurator widgetConfigurator = injector.getByClass(sessionId, WidgetConfigurator.class);
        widgetConfigurator.configWidgetModule(widgetModules);
      }

      injector.getByClass(sessionId, SeleniumConnector.class);

      SeleniumWrapper wrapper = injector.getByName(sessionId, "SeleniumWrapper");
      session.wrapper = wrapper;
      TelluriumApi api = injector.getByName(sessionId, "TelluriumApi");
      session.api = api;
    }catch(Exception e){
      e.printStackTrace();
      throw new FrameworkWiringException("Error Wiring Tellurium Framework", e);
    }finally{
      SessionManager.setSession(original);
    }
```

Let us see some example classes for the dependency injection.

```
abstract class BaseDslContext implements IDslContext {

  @Inject(name="i18nBundle", lazy=true)
  protected IResourceBundle i18nBundle;

  @Inject
  protected UiDslParser ui;

  @Inject
  protected RuntimeEnvironment env;

  @Inject
  protected EventHandler eventHandler;

  @Inject
  protected Accessor accessor;

  @Inject
  protected Extension extension;

  @Inject
  protected JQueryOptimizer optimizer;

  @Inject
  protected LocatorProcessor locatorProcessor;
...
}

```

The dependency provider itself may also require dependency injection, for example.

```
@Provider
class UiDslParser extends BuilderSupport{
       @Inject(name="i18nBundle", lazy=true)
       protected IResourceBundle i18nBundle

       @Inject
       protected UiObjectBuilderRegistry builderRegistry
...
}
```

Be aware, the runtime environment is used to pass input variables to classes in Tellurium core. For example, values of the variable port and the useMultiWindows option are obtained from Tellurium configuration file instead of the bean factory and are injected into the following class by the injector.

```
class EmbeddedSeleniumServer implements Configurable{

    @Inject(name="tellurium.embeddedserver.port")
    protected int port;

    @Inject(name="tellurium.embeddedserver.useMultiWindows")
    protected boolean useMultiWindows;

...
}
```

To see the whole example, please check out tellurium core project from

http://aost.googlecode.com/svn/branches/trunk-0.8.0/core

# Resources #

  * [Compile-time Metaprogramming - AST Transformations](http://groovy.codehaus.org/Compile-time+Metaprogramming+-+AST+Transformations)
  * [Building AST Guide](http://groovy.codehaus.org/Building+AST+Guide)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Tellurium on Twitter](http://twitter.com/TelluriumSource)
  * [Tellurium IDE](http://code.google.com/p/aost/wiki/TelluriumIde080RC1)
