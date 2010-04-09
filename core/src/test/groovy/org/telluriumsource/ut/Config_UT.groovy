package org.telluriumsource.ut

import org.telluriumsource.entity.config.Tellurium

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 8, 2010
 * 
 */
class Config_UT extends GroovyTestCase {

  public void testToJSON(){
    Tellurium tellurium = new Tellurium();
    tellurium.getDefault();
    println tellurium.toJSON().toString();
  }

}
