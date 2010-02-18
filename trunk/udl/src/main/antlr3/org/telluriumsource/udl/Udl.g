grammar Udl;

options {
  language = Java;
}

@header {
  package org.telluriumsource.udl;
  
  import org.telluriumsource.udl.code.IndexType;
  import org.telluriumsource.udl.Index;
  import org.telluriumsource.udl.MetaData;
  import org.telluriumsource.udl.ListMetaData;
  import org.telluriumsource.udl.TableHeaderMetaData;
  import org.telluriumsource.udl.TableFooterMetaData;
  import org.telluriumsource.udl.TableBodyMetaData;
}

@lexer::header {
  package org.telluriumsource.udl;
  
  import org.telluriumsource.udl.code.IndexType;
  import org.telluriumsource.udl.Index;
  import org.telluriumsource.udl.MetaData;
  import org.telluriumsource.udl.ListMetaData;
  import org.telluriumsource.udl.TableHeaderMetaData;
  import org.telluriumsource.udl.TableFooterMetaData;
  import org.telluriumsource.udl.TableBodyMetaData;  
}

@members{
//  MetaData metaData;  	  	
}

uid	returns [MetaData metadata]
	: 	bu=baseUid {metadata=bu;}
	|	lu=listUid {metadata=lu;}
	|	tu=tableUid {metadata=tu;}	
	;
	
baseUid returns [MetaData metadata]
	:	ID {metadata = new MetaData($ID.text);}
	;
listUid returns [ListMetaData metadata]
	:	'{' INDEX '}' AS ID {metadata = new ListMetaData($ID.text, $INDEX.text);}
	;
		
tableUid returns [MetaData metadata]
	:	thu=tableHeaderUid {metadata = thu;}
	|	tfu=tableFooterUid {metadata = tfu;}
	|	tbu=tableBodyUid {metadata = tbu;}
	;
	
tableHeaderUid returns [TableHeaderMetaData metadata]
	:	'{' HEADER ':' INDEX '}' AS ID {metadata = new TableHeaderMetaData($ID.text, $INDEX.text);}
	;
	
tableFooterUid returns [TableFooterMetaData metadata]
	:       '{' FOOTER ':' INDEX '}' AS ID {metadata = new TableFooterMetaData($ID.text, $INDEX.text);}
	;
	
tableBodyUid returns [TableBodyMetaData metadata]
        :	'{' ROW ':' inx1=INDEX ',' COLUMN ':' inx2=INDEX '}' AS ID {metadata = new TableBodyMetaData($ID.text); metadata.setRow(new Index(inx1.getText())); metadata.setColumn(new Index(inx2.getText())); }
        |	'{' ROW '=' id1=ID ',' COLUMN ':' INDEX '}' AS id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setRow(new Index(IndexType.REF, id1.getText()));  metadata.setColumn(new Index($INDEX.text));}
        |	'{' ROW ':' INDEX ',' COLUMN '=' id1=ID '}' AS id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setRow(new Index($INDEX.text)); metadata.setColumn(new Index(IndexType.REF, id1.getText()));}
        |	'{' ROW '=' id1=ID ',' COLUMN '=' id2=ID '}' AS id3=ID {metadata = new TableBodyMetaData(id3.getText()); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(IndexType.REF, id2.getText()));}
        |       '{' TBODY ':' inx1=INDEX ',' ROW ':' inx2=INDEX ',' COLUMN ':' inx3=INDEX '}' AS ID {metadata = new TableBodyMetaData($ID.text); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(inx2.getText())); metadata.setColumn(new Index(inx3.getText()));}
        |	'{' TBODY ':' inx1=INDEX ',' ROW '=' id1=ID ',' COLUMN ':' inx2=INDEX '}' AS id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(inx2.getText()));}
        |    	'{' TBODY ':' inx1=INDEX ',' ROW ':' inx2=INDEX ',' COLUMN '=' id1=ID '}' AS id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(inx2.getText())); metadata.setColumn(new Index(IndexType.REF, id1.getText()));}
        |	'{' TBODY ':' INDEX ',' ROW '=' id1=ID ',' COLUMN '=' id2=ID '}' AS id3=ID {metadata = new TableBodyMetaData(id3.getText()); metadata.setTbody(new Index($INDEX.text)); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(IndexType.REF, id2.getText()));}
        |       ALL {metadata = new TableBodyMetaData($ALL.text); metadata.setTbody(new Index($ALL.text)); metadata.setRow(new Index($ALL.text)); metadata.setColumn(new Index($ALL.text)); }
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