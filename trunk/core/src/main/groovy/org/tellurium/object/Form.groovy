package org.tellurium.object

 /**
 *  FORM
 *
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class Form  extends Container{

    public static final String TAG = "form"

    def submit(Closure c){
        c(locator)
    }
}