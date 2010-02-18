grammar Udl;

options {
  language = Java;
}

@header {
  package org.telluriumsource.udl;
 
  import org.telluriumsource.udl.MetaData;
}

@lexer::header {
  package org.telluriumsource.udl;
  
  import org.telluriumsource.udl.MetaData; 
}

@members{
  MetaData metaData = new MetaData();  	  	
}

uid	: 	baseuid
	|	listuid
	|	tableuid	
	;
	
baseuid 
	:	ID
	;
listuid	:	ID AS INTEGER
	|	ALL
	;
		
tableuid:	tableheaderuid
	|	tablefooteruid
	|	tablebodyuid
	;
tableheaderuid
	:	ID AS HEADER ':' INTEGER 
	|       HEADER ':' ALL
	;
tablefooteruid
	:       ID AS FOOTER ':' INTEGER
	|       FOOTER ':' ALL
	;
tablebodyuid
        :	ID AS ROW ':' INTEGER ',' COLUMN ':' INTEGER
        |       ID AS TBODY ':'INTEGER ',' ROW ':' INTEGER ',' COLUMN ':' INTEGER
        |       ALL
        ;
        			
HEADER 	:	'header'
	;	
FOOTER	:	'footer'
	;
ROW	:	'row'
	;
COLUMN  : 	'column'
	;	
TBODY	:	'tbody'
	;
AS	:	'as'
	;
ALL     :	'all'
	;	
						
fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
INTEGER : DIGIT+ ;
ID : LETTER (LETTER | DIGIT)*;
WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};