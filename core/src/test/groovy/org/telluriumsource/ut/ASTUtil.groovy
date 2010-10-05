package org.telluriumsource.ut

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
  public static List<ASTNode> getProviderNodes(String name, Class clazz, String scope, boolean singleton){
    List<ASTNode> nodes = new AstBuilder().buildFromCode {
//      TelluriumFramework.instance.registerBean(name, clazz, scope, singleton);
    }

    return nodes;
  }
}
