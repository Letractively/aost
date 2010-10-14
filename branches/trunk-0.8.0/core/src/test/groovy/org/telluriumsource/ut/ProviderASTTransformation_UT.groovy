package org.telluriumsource.ut

import org.telluriumsource.framework.SessionManager
import org.telluriumsource.mock.MockSessionFactory
import org.telluriumsource.framework.Session

import org.telluriumsource.framework.inject.BeanInfo

import org.telluriumsource.framework.inject.Injector
import org.telluriumsource.component.dispatch.Dispatcher
import org.telluriumsource.framework.TelluriumInjector

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
        SessionManager.setSession(MockSessionFactory.getNewSession())
  }

/*  public void testProviderNoParameter(){
        def res = shell.evaluate("""
              import org.telluriumsource.annotation.Provider
              @Provider
              class X {
                private ArrayList list = [1,2,3]

                void op () {
                  list
                }
              }

              new X ()
        """)

        Session session = SessionManager.getSession()

        assertNotNull session

        Object obj = session.getInstance(res.getClass());
        assertNotNull obj
  }*/

  public void testProviderWithType(){

        def res = shell.evaluate("""
              package org.telluriumsource

              import org.telluriumsource.annotation.Provider
              @Provider(type=X.class)
              class X {
                private ArrayList list = [1,2,3]

                void op () {
                  list
                }
              }

              new X ()
        """)

        shell.evaluate("""

              package org.telluriumsource

              import org.telluriumsource.annotation.Provider
              @Provider
              class Y {
                private ArrayList list = [1,2,3]

                void op () {
                  list
                }
              }

              new Y()
        """)

        def injector = shell.evaluate("""
          package org.telluriumsource.framework.inject

          import org.telluriumsource.annotation.Provider
          import org.telluriumsource.framework.DefaultSessionQuery

          @Provider
          class TestInjector extends Injector {
                private SessionQuery sQuery = new DefaultSessionQuery();

                public SessionQuery getSessionQuery(){

                  return this.sQuery;
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

        shell.evaluate("""
              package org.telluriumsource

              import org.telluriumsource.annotation.Provider
              @Provider
              class Z {
                private ArrayList list = [1,2,3]

                void op () {
                  list
                }
              }

              new Z()
        """)


        Set<BeanInfo> infos = injector.getBeanInfos()
        assertNotNull infos
        assertFalse infos.isEmpty()
        assertEquals 3, infos.size()
  }

  public void testExplicitProvider(){
    Session session = SessionManager.getSession()

    assertNotNull session

    Object obj = TelluriumInjector.instance.getByClass(Dispatcher.class);
    assertNotNull obj

    obj = TelluriumInjector.instance.getByName("TelluriumApi");
    assertNotNull obj

  }
}
