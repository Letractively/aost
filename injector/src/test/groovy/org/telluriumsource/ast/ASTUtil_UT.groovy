package org.telluriumsource.ast

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.telluriumsource.mock.MockProvider

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 30, 2010
 * 
 */
class ASTUtil_UT extends GroovyTestCase {

  public void testProvider(){
    List<ASTNode> list = ASTUtil.getProviderNodes("org.telluriumsource.mock.MockProvider", MockProvider.class, "Session", true)
    assertNotNull(list);
    assertEquals(1, list.size());
    BlockStatement stm = (BlockStatement) list.get(0);
    assertNotNull(stm);
    println stm.toString()
  }

  public void testGetByName(){
    List<ASTNode> list = ASTUtil.getInjectNodeByName("org.telluriumsource.mock.MockProvider");
    assertNotNull(list);
    assertEquals(1, list.size());
    BlockStatement stm = (BlockStatement) list.get(0);
    assertNotNull(stm);
    println stm.toString()
  }

  public void testGetByNameLazy(){
    ASTUtil util = new ASTUtil();
    List<ASTNode> list = util.getInjectNodeByNameLazy("org.telluriumsource.mock.MockProvider")
    assertNotNull(list);
    assertEquals(1, list.size());
    BlockStatement stm = (BlockStatement) list.get(0);
    assertNotNull(stm);
    println stm.toString()
  }

  public void testInjectMethodLazy(){
    ASTUtil util = new ASTUtil();
    List<ASTNode> list = util.getInjectMethodLazy()
    assertNotNull(list);
    assertEquals(1, list.size());
    BlockStatement stm = (BlockStatement) list.get(0);
    assertNotNull(stm);
    println stm.toString()

  }

  public void testMethodClosure(){
    ASTUtil util = new ASTUtil();
    util.setAVar();
    println util.aVar;
  }

}
