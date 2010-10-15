package org.telluriumsource.inject

import org.telluriumsource.mock.MockInjector

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
  }

  public void testInject(){
    def y = shell.evaluate("""
      package org.telluriumsource


      import org.telluriumsource.annotation.Provider
      import org.telluriumsource.annotation.Inject
      import org.telluriumsource.inject.Scope
      import org.telluriumsource.mock.MockInjector


      public class X {
        private int x = 10;

        public int getValue(){
          return x
        }
      }


      public class Y {
        @Inject(name="x", lazy=true)
        private X x

        public int getValue(){
          return x.getValue()
        }

      }
      MockInjector.instance.addBean("x",  X.class, X.class, Scope.Session, true, new X());
      new Y()

      """)

      println MockInjector.instance.showAllBeans();
      assertNotNull y
      assertNotNull y.getValue()
      assertEquals 10, y.getValue()
  }

}
