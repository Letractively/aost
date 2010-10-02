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

        def cached = shell.evaluate("""
          package org.telluriumsource.framework
          import org.telluriumsource.annotation.Provider
          
          @Provider
          class Cached {
            private Map<String, Class> map = new HashMap<String, Class>()

            public Set<String> getNames(){
              return this.map.keySet();
            }

            public Set<Class> getCachedClasses(){
              return this.map.values();
            }
            
            public void addCache(String name, Class clazz){
              this.map.put(name, clazz);
            }
          }

          @Provider(type=W.class)
           class W {
                private ArrayList list = [1,2,3]

                void op () {
                  list
                }
           }

           Cached.instance
          
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
    
        Set<Class> classes = cached.getCachedClasses();
        assertNotNull classes
        assertFalse classes.isEmpty()
        assertEquals 3, classes.size()
  }

  public void testExplicitProvider(){
    Session session = SessionManager.getSession()

    assertNotNull session

    Object obj = session.getBean(Y.class);
    assertNotNull obj

    new Y()

    obj = session.getBean(Y.class);
    assertNotNull obj

  }
}
