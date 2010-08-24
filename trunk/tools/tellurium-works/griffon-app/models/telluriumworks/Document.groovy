package telluriumworks

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 23, 2010
 * 
 */
import groovy.beans.Bindable

@groovy.beans.Bindable
class Document {
  @Bindable String fileText
  @Bindable boolean dirty  
}
