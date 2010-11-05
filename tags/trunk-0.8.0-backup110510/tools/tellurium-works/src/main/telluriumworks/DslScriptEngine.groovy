package telluriumworks

import org.telluriumsource.dsl.DdDslContext
import org.telluriumsource.test.groovy.DslTelluriumGroovyTestCase

/**
 * 
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * 
 * Date: Aug 25, 2010
 * 
 */
class DslScriptEngine extends DdDslContext {
  @Delegate
  protected DslTelluriumGroovyTestCase aost = new DslTelluriumGroovyTestCase()
}
