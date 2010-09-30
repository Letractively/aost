package foo

import org.codehaus.groovy.ast.builder.AstBuilder

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Sep 30, 2010
 * 
 */
@WithLogging
def greet() {
  println "Hello World"
  def code = new AstBuilder().buildFromCode {
    def parameter = 1
    println 'Hello from a synthesized method!'
    println "Parameter value: $parameter"
    Map map = [:]
    return test(map)
  }
  println "Done"
}

greet()

def test(Map map){
  return map.size()
}
