package org.telluriumsource.ut

import org.telluriumsource.framework.ASTUtil
import org.telluriumsource.dsl.UiDslParser
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.stmt.BlockStatement

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 30, 2010
 * 
 */
class ASTUtil_UT extends GroovyTestCase {

  public void testProvider(){
    List<ASTNode> list = ASTUtil.getProviderNodes("org.telluriumsource.dsl.UiDslParser", UiDslParser.class, true)
    assertNotNull(list);
    assertEquals(1, list.size());
    BlockStatement stm = (BlockStatement) list.get(0);
    assertNotNull(stm);
  }

}
