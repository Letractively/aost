package org.telluriumsource.ut

import org.telluriumsource.framework.AnnotationProcessor
import org.telluriumsource.framework.Clazz

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Oct 2, 2010
 * 
 */
class AnnotationProcessor_UT extends GroovyTestCase {
  private AnnotationProcessor processor = new AnnotationProcessor();

  public void testProviderY(){
    Clazz bean = processor.processProvider(new Y());
    assertNotNull(bean);
  }

}
