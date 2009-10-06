grammar T6;

// START:scope
// rules prog, func, and block share the same global scope
// and, therefore, push their scopes onto the same stack
// of scopes as you would expect for C (a code block's
// scope hides the function scope, which in turn, hides
// the global scope).
scope CScope {
    String name;
    List symbols;
}
// END:scope

// START:members
@members {
/** Is id defined in a CScope?  Walk from top of stack
 *  downwards looking for a symbols list containing id.
 */
boolean isDefined(String id) {
    for (int s=$CScope.size()-1; s>=0; s--) {
        if ( $CScope[s]::symbols.contains(id) ) {
            System.out.println(id+" found in "+$CScope[s]::name);
            return true;
        }
    }
    return false;
}
}
// END:members

// START:augment
prog
scope CScope;
@init {
    // initialize a scope for overall C program
    $CScope::symbols = new ArrayList();
    $CScope::name = "global";
}
@after {
    // dump global symbols after matching entire program
    System.out.println("global symbols = "+$CScope::symbols);
}
    :   decl* func*
    ;

func
scope CScope;
@init {
    // initialize a scope for this function
    $CScope::symbols = new ArrayList();
}

@after {
    // dump variables defined within the function itself
    System.out.println("function "+$CScope::name+" symbols = "+
                       $CScope::symbols);
}
    :   'void' ID {$CScope::name=$ID.text;} '(' ')' '{' decl* stat+ '}'
    ;

block
scope CScope;
@init {
    // initialize a scope for this code block
    $CScope::symbols = new ArrayList();
    $CScope::name = "level "+$CScope.size();
}
@after {
    // dump variables defined within this code block
    System.out.println("code block level "+$CScope.size()+" = "+
                       $CScope::symbols);
}
    :   '{' decl* stat+ '}'
    ;
// END:augment

/** Match a declaration and had the variable name to the current scope */
decl:   'int' ID {$CScope::symbols.add($ID.text);} ';'
    ;

/** Match an assignment and call isDefined to ensure that the variable
 *  is defined by looking it up in the stack of scopes.
 */
stat:   ID '=' INT ';'
        {
        if ( !isDefined($ID.text) ) {
            System.err.println("undefined variable level "+
                $CScope.size()+": "+$ID.text);
        }
        }
    |   block
    ;
ID  :   'a'..'z'+ ;
INT :   '0'..'9'+ ;
WS  :   (' '|'\n'|'\r')+ {$channel = HIDDEN;} ;


