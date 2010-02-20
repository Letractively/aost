grammar Tudl;

uid	
	: 	baseUid
	|	listUid
	|	tableUid	
	;
	
baseUid 
	:	ID
	;
	
listUid 
	:	'{' INDEX '}' 'as' ID 
	;
		
tableUid 
	:	tableHeaderUid
	|	tableFooterUid 
	|	tableBodyUid
	;
	
tableHeaderUid 
	:	'{' 'header' ':' INDEX '}' 'as' ID
	;
	
tableFooterUid 
	:       '{' 'footer' ':' INDEX '}' 'as' ID
	;
	
tableBodyUid 
        :	'{' 'row' ':' INDEX ',' 'column' ':' INDEX '}' 'as' ID 
        |	'{' 'row' '=' ID ',' 'column' ':' INDEX '}' 'as' ID 
        |	'{' 'row' ':' INDEX ',' 'column' '=' ID '}' 'as' ID 
        |	'{' 'row' '=' ID ',' 'column' '=' ID '}' 'as' ID
        |       '{' 'tbody' ':' INDEX ',' 'row' ':' INDEX ',' 'column' ':' INDEX '}' 'as' ID 
        |	'{' 'tbody' ':' INDEX ',' 'row' '=' ID ',' 'column' ':' INDEX '}' 'as' ID 
        |    	'{' 'tbody' ':' INDEX ',' 'row' ':' INDEX ',' 'column' '=' ID '}' 'as' ID
        |	'{' 'tbody' ':' INDEX ',' 'row' '=' id1=ID ',' 'column' '=' ID '}' 'as' ID 
        ;              			
							
fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
INDEX	:	(DIGIT+ |'all' | 'odd' | 'even' | 'any' | 'first' | 'last' );   
ID 	: 	LETTER (LETTER | DIGIT)*;
WS 	: 	(' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
