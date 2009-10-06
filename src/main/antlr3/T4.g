grammar T4;

prog:   block
    ;

block
scope {
    /** List of symbols defined within this block */
    List symbols;
}
@init {
    // initialize symbol list
    $block::symbols = new ArrayList();
}
    :   '{' decl* stat+ '}'
        // print out all symbols found in block
        // $block::symbols evaluates to a List as defined in scope
        {System.out.println("symbols="+$block::symbols);}
    ;

/** Match a declaration and add identifier name to list of symbols */
decl:   'int' ID {$block::symbols.add($ID.text);} ';'
    ;

/** Match an assignment then test list of symbols to verify
 *  that it contains the variable on the left side of the assignment.
 *  Method contains() is List.contains() because $block::symbols
 *  is a List.
 */
stat:   ID '=' INT ';'
        {
        if ( !$block::symbols.contains($ID.text) ) {
            System.err.println("undefined variable: "+$ID.text);
        }
        }
    ;
ID  :   'a'..'z'+ ;
INT :   '0'..'9'+ ;
WS  :   (' '|'\n'|'\r')+ {$channel = HIDDEN;} ;


