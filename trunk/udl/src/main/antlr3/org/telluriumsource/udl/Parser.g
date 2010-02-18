grammar Parser;

options {
  language = Java;
}

@header {
  package org.telluriumsource.example;
}

@lexer::header {
  package org.telluriumsource.example;
}

@members{
  MetaData metaData = new MetaData();  	  	
}

uid	: ID	
	;

fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
INTEGER : DIGIT+ ;
ID : LETTER (LETTER | DIGIT)*;
WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};