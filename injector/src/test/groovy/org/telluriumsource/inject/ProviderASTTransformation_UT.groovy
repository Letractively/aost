package org.telluriumsource.inject

import java.lang.reflect.Method
import java.lang.reflect.Field
import org.telluriumsource.mock.MockInjector
import org.telluriumsource.mock.MockProvider
import org.telluriumsource.ast.ProviderASTTransformation
import org.telluriumsource.ast.InjectASTTransformation

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
    ProviderASTTransformation.injector = null;
    InjectASTTransformation.injector = null;
  }

  public void testProvider(){

        def injector = shell.evaluate("""
          package org.telluriumsource

          import org.telluriumsource.annotation.Provider
          import org.telluriumsource.annotation.Inject
          import org.telluriumsource.inject.Injector

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

          @Inject
          @Provider
          class TestInjector extends Injector {

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
