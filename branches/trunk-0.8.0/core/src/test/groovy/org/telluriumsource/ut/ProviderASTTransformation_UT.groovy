package org.telluriumsource.ut

import org.telluriumsource.framework.SessionManager
import org.telluriumsource.mock.MockSessionFactory
import org.telluriumsource.framework.Session
import org.telluriumsource.framework.Cached

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

  public void testProviderNoParameter(){
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

        Object obj = session.getBean(res.getClass());
        assertNotNull obj
  }

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

        Session session = SessionManager.getSession()

        assertNotNull session

        Object obj = session.getBean(res.getClass());
        assertNotNull obj

        shell.evaluate("""
          package org.telluriumsource.framework
          import org.telluriumsource.annotation.Provider
          
          @Provider
          @Singleton
          class Cached {
            private Map<String, Class> map

            public Map<String, Class> getCached(){
              return this.map
            }

            private def Cached() {
              map = new HashMap<String, Class>()
              initiate()
            }

            public void initiate(){
            }
          }
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
        """)
    
        Cached cached = Cached.instance;
        Map<String, Class> map = cached.getCached();
        assertNotNull map
        assertFalse map.isEmpty()
  }

  public void testExplicitProvider(){
    Session session = SessionManager.getSession()

    assertNotNull session

    Object obj = session.getBean(Y.class);
    assertNotNull obj

    new Y()

    obj = session.getBean(Y.class);
    assertNotNull obj

    Cached cached = Cached.instance;
    Map<String, Class> map = cached.getCached();
    assertNotNull map
    assertFalse map.isEmpty()
  }
}
