package org.telluriumsource.ut

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.telluriumsource.framework.TelluriumFramework
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.FieldNode
import java.lang.reflect.Modifier
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.stmt.Statement

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
      TelluriumFramework.instance.registerBean(name, clazz, scope, singleton);
    }

    return nodes;
  }

/*void makeMethodCached(ClassNode classNode, MethodNode methodNode) {
   // add field of hashmap for cached objects
   def cachedFieldName = methodNode.getName();
   FieldNode cachedField =
    new FieldNode("cache$cachedFieldName", Modifier.PRIVATE, new ClassNode(Map.class), new ClassNode(classNode.getClass()),
      new ConstructorCallExpression(new ClassNode(HashMap.class), new ArgumentListExpression()));
    classNode.addField(cachedField)

    //augment method with cache calls
    Parameter[] params = methodNode.getParameters()
    //methodNode
    String parameterName = params[0].getName()
    List<Statement> statements = methodNode.getCode().getStatements();
    Statement oldReturnStatement = statements.last();
    def ex = oldReturnStatement.getExpression();
    def ast = new AstBuilder().buildFromSpec  {
      expression{
          declaration {
                variable "cachedValue"
                token "="
                methodCall {
                    variable "cache$cachedFieldName"
                    constant 'get'
                    argumentList {
                      variable parameterName
                    }
                }
          }
      }
      ifStatement {
          booleanExpression {
              variable "cachedValue"
          }
          //if block
          returnStatement {
              variable "cachedValue"
          }
          //else block
          empty()
      }
      expression{
          declaration {
            variable "localCalculated$cachedFieldName"
            token "="
            {-> delegate.expression < < ex}()
          }
        }
        expression {
          methodCall {
            variable "cache$cachedFieldName"
            constant 'put'
            argumentList {
              variable parameterName
              variable "localCalculated$cachedFieldName"
            }
          }
        }
        returnStatement {
              variable "localCalculated$cachedFieldName"
        }
    }

    statements.remove(oldReturnStatement)
    statements.add(0,ast[0]);
    statements.add(1,ast[1]);
    statements.add(ast[2])
    statements.add(ast[3])
    statements.add(ast[4])
  }*/
}
