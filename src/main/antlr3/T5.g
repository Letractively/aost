grammar T5;

@members {
/** Track the nesting level for better messages */
int level=0;
}

prog:   block
    ;
block
scope {
    List symbols;
}
@init {
    $block::symbols = new ArrayList();
    level++;
}
@after {
    System.out.println("symbols level "+level+" = "+$block::symbols);
    level--;
}
    :   '{' decl* stat+ '}'
    ;
decl:   'int' ID {$block::symbols.add($ID.text);} ';'
    ;
stat:   ID '=' INT ';'
        {
        if ( !$block::symbols.contains($ID.text) ) {
            System.err.println("undefined variable level "+level+
                               ": "+$ID.text);
        }
        }
    |   block
    ;
ID  :   'a'..'z'+ ;
INT :   '0'..'9'+ ;
WS  :   (' '|'\n'|'\r')+ {$channel = HIDDEN;} ;


