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

uid	: 	baseUid
	|	listUid
	|	tableUid	
	;
	
baseUid 
	:	ID
	;
listUid	:	'{' INDEX '}' AS ID 
	;
		
tableUid:	tableHeaderUid
	|	tableFooterUid
	|	tableBodyUid
	;
tableHeaderUid
	:	'{' HEADER ':' INDEX '}' AS ID 
	;
tableFooterUid
	:       '{' FOOTER ':' INDEX '}' AS ID
	;
tableBodyUid
        :	'{' ROW ':' INDEX ',' COLUMN ':' INDEX '}' AS ID
        |	'{' ROW '=' ID ',' COLUMN ':' INDEX '}' AS ID
        |	'{' ROW ':' INDEX ',' COLUMN '=' ID '}' AS ID 
        |	'{' ROW '=' ID ',' COLUMN '=' ID '}' AS ID
        |       '{' TBODY ':' INDEX ',' ROW ':' INDEX ',' COLUMN ':' INDEX '}' AS ID
        |	'{' TBODY ':' INDEX ',' ROW '=' ID ',' COLUMN ':' INDEX '}' AS ID
        |    	'{' TBODY ':' INDEX ',' ROW ':' INDEX ',' COLUMN '=' ID '}' AS ID
        |	'{' TBODY ':' INDEX ',' ROW '=' ID ',' COLUMN '=' ID '}' AS ID
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
ODD	:	'odd'
	;
EVEN 	:	'even'
	;
FIRST   :	'first'
	;
LAST	:	'last'
	;			
ANY     :	'any'
	;
							
fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
INDEX	:	(DIGIT+ | ALL | ODD | EVEN | ANY | FIRST | LAST );
ID 	: 	LETTER (LETTER | DIGIT)*;
WS 	: 	(' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};