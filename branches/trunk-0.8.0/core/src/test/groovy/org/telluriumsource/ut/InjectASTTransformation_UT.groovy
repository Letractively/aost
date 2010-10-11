package org.telluriumsource.ut

import org.telluriumsource.framework.SessionManager
import org.telluriumsource.mock.MockSessionFactory
import org.telluriumsource.framework.dj.Injector

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
        MockSessionFactory.getNewSession()
  }

  public void testInject(){
    def y = shell.evaluate("""
      package org.telluriumsource
      import org.telluriumsource.annotation.Provider
      import org.telluriumsource.annotation.Inject
      import org.telluriumsource.framework.dj.Injector
      import org.telluriumsource.framework.dj.Scope

//      @Provider
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
      Injector.instance.addBean("x",  X.class, X.class, Scope.Session, true, new X());
      new Y()
      
      Injector.instance.showAllBeans()
    """)

      println Injector.instance.showAllBeans();
      assertNotNull y
      assertNotNull y.getValue()
    
  }

}
