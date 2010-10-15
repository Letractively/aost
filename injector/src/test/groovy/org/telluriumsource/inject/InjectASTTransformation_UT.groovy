package org.telluriumsource.inject

import org.telluriumsource.mock.MockInjector
import org.telluriumsource.ast.ProviderASTTransformation
import org.telluriumsource.ast.InjectASTTransformation

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 6, 2010
 * 
 */
class InjectASTTransformation_UT extends GroovyShellTestCase {

  public void setUp(){
    super.setUp()
    ProviderASTTransformation.injector = null;
    InjectASTTransformation.injector = null;
  }

  public void testInject(){
    def y = shell.evaluate("""
      package org.telluriumsource


      import org.telluriumsource.annotation.Provider
      import org.telluriumsource.annotation.Inject
      import org.telluriumsource.inject.Injector
      import org.telluriumsource.inject.Bean

      @Inject
      @Provider
      class NewInjector extends Injector {

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
      StringBuffer sb = new StringBuffer(64 * beans.size());
      sb.append("{");
      sb.append(beans.join(",\n"));
      sb.append("}");
      println sb.toString()
  }

}
