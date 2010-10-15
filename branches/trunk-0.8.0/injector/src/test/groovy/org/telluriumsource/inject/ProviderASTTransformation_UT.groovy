package org.telluriumsource.inject

import java.lang.reflect.Method
import java.lang.reflect.Field
import org.telluriumsource.mock.MockInjector
import org.telluriumsource.mock.MockProvider
import org.telluriumsource.ast.InjectorASTTransformation

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 1, 2010
 * 
 */
class ProviderASTTransformation_UT extends GroovyShellTestCase {

  public void setUp(){
    super.setUp()
    InjectorASTTransformation.injector = null;
  }

  public void testProvider(){

        def injector = shell.evaluate("""
          package org.telluriumsource

          import org.telluriumsource.annotation.Provider
          import org.telluriumsource.annotation.Inject
          import org.telluriumsource.annotation.Injector
          import org.telluriumsource.inject.AbstractInjector

          @Provider(name="x")
          class X {
            private ArrayList list = [1,2,3]

            void op () {
              list
            }
          }

          @Provider
          class Y {
              private ArrayList list = [1,2,3]

              void op () {
                list
              }
           }

          @Injector
          class TestInjector extends AbstractInjector {

            public String getCurrentSessionId(){
              return "default"
            }

          }

          @Provider(type=W.class)
           class W {
                private ArrayList list = [1,2,3]

                void op () {
                  list
                }
           }

           TestInjector.instance
          
        """)

        Set<Bean> infos = injector.getAllBeans()
        assertNotNull infos
        assertFalse infos.isEmpty()
        assertEquals 3, infos.size()
  }

  public void testExplicitProvider(){
    MockInjector.getMethods().each{Method m ->
      println m.toGenericString();  
    }

    MockInjector.getFields().each{Field f ->
      println f.toGenericString();
    }
    
    Object obj = MockInjector.instance.getByClass(MockProvider.class);
    assertNotNull obj

    obj = MockInjector.instance.getByName(MockProvider.class.getCanonicalName());
    assertNotNull obj

  }
}
