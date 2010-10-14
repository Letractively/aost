package org.telluriumsource.framework;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Sep 10, 2010
 */
public class SessionManager {

    private static class SessionCache extends ThreadLocal {
        private Session session = null;
        public Object get(){
            return session;
        }

        public void set(Object newValue){
            this.session = (Session) newValue;
        }

        public Object initialValue(){
            return this.session;
        }
    }

    private static SessionCache cache = new SessionCache();

    public static void setSession(Session session){
        cache.set(session);
    }

    public static Session getSession() {
      return (Session) cache.get();
    }

}
