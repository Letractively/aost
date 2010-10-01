package org.telluriumsource.framework

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.builder.AstBuilder

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 30, 2010
 * 
 */
class ASTUtil {
  public static List<ASTNode> getProviderNodes(String name, Class clazz, boolean isSingleton){
    List<ASTNode> nodes = new AstBuilder().buildFromCode {
      SessionManager.getSession().getBeanFactory().provide(name, clazz, isSingleton);
    }

    return nodes;
  }
}
