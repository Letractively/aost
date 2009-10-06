grammar P;
options {
    output=AST;
}
expr:   INT ('+'^ INT)* ;
INT :   '0'..'9'+;
WS  :   ' ' | '\r' | '\n' ;
