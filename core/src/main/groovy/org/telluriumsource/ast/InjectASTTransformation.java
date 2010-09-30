package org.telluriumsource.ast;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *         
 *         Date: Sep 30, 2010
 */

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class InjectASTTransformation implements ASTTransformation{

    public void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {

    }
}
