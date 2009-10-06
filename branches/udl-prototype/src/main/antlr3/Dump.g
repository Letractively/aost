tree grammar Dump;
options {
    tokenVocab=P;
    ASTLabelType=CommonTree;
}
expr:   ^( '+' expr {System.out.print('+');} expr )
    |   INT {System.out.print($INT.text);}
    ;
