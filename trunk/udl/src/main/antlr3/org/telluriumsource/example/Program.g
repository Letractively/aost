grammar Program;

options {
  language = Java;
}

@header {
  package org.telluriumsource.example;
}

@lexer::header {
  package org.telluriumsource.example;
}

program
	:	'program' IDENT '='
		(constant | variable | function | procedure | typeDecl)*
		'begin'
		statement*
		'end' IDENT '.'
	;

constant
	:	'constant' IDENT ':' type ':=' expression ';'
	;

variable
	:	'var' IDENT (',' IDENT)* ':' type (':=' expression)? ';'
	;
	
type
	:	'Integer'
	|	'Boolean'
	|	'String'
	|	'Char'
	|	IDENT
	|	typeSpec
	;
	
typeDecl
	:	'type' IDENT '=' typeSpec ';'
	;
	
typeSpec
	:	arrayType
	|	recordType
	|	enumType
	;
	
arrayType
	:	'array' '[' INTEGER '..' INTEGER ']' 'of' type
	;
	
recordType
	:	'record' field* 'end' 'record'
	;
	
field
	:	IDENT ':' type ';'
	;
	
enumType
	:	'<' IDENT (',' IDENT)* '>'
	;
	
statement
	:	assignmentStatement
	|	ifStatement
	|	loopStatement
	|	whileStatement
	|	procedureCallStatement
	;
	
procedureCallStatement
	:	IDENT '(' actualParameters? ')' ';'
	;
	
actualParameters
	:	expression (',' expression)*
	;
	
ifStatement
	:	'if' expression 'then' statement+
		('elsif' expression 'then' statement+)*
		('else' statement+)?
		'end' 'if' ';'
	;
	
assignmentStatement
	:	IDENT ':=' expression ';'
	;
	
exitStatement
	:	'exit' 'when' expression ';'
	;
	
whileStatement
	:	'while' expression 'loop'
		(statement|exitStatement)*
		'end' 'loop' ';'
	;
	
loopStatement
	:	'loop' (statement|exitStatement)* 'end' 'loop' ';'
	;
	
returnStatement
	:	'return' expression ';'
	;
	
procedure
	:	'procedure' IDENT '(' parameters? ')' '='
		(constant | variable)*
		'begin'
		statement*
		'end' IDENT '.'
	;	
function
	:	'function' IDENT '(' parameters? ')' ':' type '='
		(constant | variable)*
		'begin'
		(statement|returnStatement)*
		'end' IDENT '.'
	;	
	
parameters
	:	parameter (',' parameter)*
	;
	
parameter
	:	'var'? IDENT ':' type
	;
	
	
// expressions -- fun time!

term
	:	IDENT
	|	'(' expression ')'
	|	INTEGER
	|	STRING_LITERAL
	|	CHAR_LITERAL
	|	IDENT '(' actualParameters ')'
	;
	
negation
	:	'not'* term
	;
	
unary
	:	('+' | '-')* negation
	;

mult
	:	unary (('*' | '/' | 'mod') unary)*
	;
	
add
	:	mult (('+' | '-') mult)*
	;

relation
	:	add (('=' | '/=' | '<' | '<=' | '>=' | '>') add)*
	;
	
expression
	:	relation (('and' | 'or') relation)*
	;
	

MULTILINE_COMMENT : '/*' .* '*/' {$channel = HIDDEN;} ;

STRING_LITERAL
	:	'"'
		{ StringBuilder b = new StringBuilder(); }
		(	'"' '"'				{ b.appendCodePoint('"');}
		|	c=~('"'|'\r'|'\n')	{ b.appendCodePoint(c);}
		)*
		'"'
		{ setText(b.toString()); }
	;
	
CHAR_LITERAL
	:	'\'' . '\'' {setText(getText().substring(1,2));}
	;

fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
INTEGER : DIGIT+ ;
IDENT : LETTER (LETTER | DIGIT)*;
WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
COMMENT : '//' .* ('\n'|'\r') {$channel = HIDDEN;};
