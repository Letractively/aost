grammar T2;
options {
    language=Java;
}
@members {
String s;
}
r : ID '#' {s = $ID.text; System.out.println("found "+s);} ;
ID: 'a'..'z'+ ;
WS: (' '|'\n'|'\r')+ {skip();} ; // ignore whitespace
