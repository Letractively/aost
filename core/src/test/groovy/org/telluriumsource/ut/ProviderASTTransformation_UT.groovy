package org.telluriumsource.ut

import org.telluriumsource.framework.SessionManager
import org.telluriumsource.mock.MockSessionFactory
import org.telluriumsource.framework.Session

import org.telluriumsource.framework.dj.BeanInfo

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

        Object obj = session.getBean(res.getClass());
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

        def injector = shell.evaluate("""
          package org.telluriumsource.framework.dj

          import org.telluriumsource.annotation.Provider
          
          @Provider
          class Injector {
            private Map<String, BeanInfo> map = new HashMap<String, BeanInfo>()

            public Set<String> getNames(){
              return this.map.keySet();
            }

            public Set<BeanInfo> getBeanInfos(){
              return this.map.values();
            }
            
            public void addBeanInfo(String name, Class clazz, String scope, boolean singleton){
              BeanInfo cl = new BeanInfo();
              cl.setName(name);
              cl.setClazz(clazz);
              cl.setScope(Scope.valueOf(scope));
              cl.setSingleton(singleton);

              this.map.put(name, cl);
            }
          }

          @Provider(type=W.class)
           class W {
                private ArrayList list = [1,2,3]

                void op () {
                  list
                }
           }

           Injector.instance
          
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
    
        Set<BeanInfo> infos = injector.getBeanInfos();
        assertNotNull infos
        assertFalse infos.isEmpty()
        assertEquals 3, infos.size()
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
