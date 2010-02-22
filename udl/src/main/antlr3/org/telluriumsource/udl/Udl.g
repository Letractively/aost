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
	:	'{' INDEX '}' 'as' ID {metadata = new ListMetaData($ID.text, $INDEX.text);}
	;
		
tableUid returns [MetaData metadata]
	:	thu=tableHeaderUid {metadata = thu;}
	|	tfu=tableFooterUid {metadata = tfu;}
	|	tbu=tableBodyUid {metadata = tbu;}
	;
	
tableHeaderUid returns [TableHeaderMetaData metadata]
	:	'{' 'header' ':' INDEX '}' 'as' ID {metadata = new TableHeaderMetaData($ID.text, $INDEX.text);}
	;
	
tableFooterUid returns [TableFooterMetaData metadata]
	:       '{' 'footer' ':' INDEX '}' 'as' ID {metadata = new TableFooterMetaData($ID.text, $INDEX.text);}
	;
	
tableBodyUid returns [TableBodyMetaData metadata]
        :	'{' 'row' ':' inx1=INDEX ',' 'column' ':' inx2=INDEX '}' 'as' ID {metadata = new TableBodyMetaData($ID.text); metadata.setRow(new Index(inx1.getText())); metadata.setColumn(new Index(inx2.getText())); }
        |	'{' 'row' '=' id1=ID ',' 'column' ':' INDEX '}' 'as' id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setRow(new Index(IndexType.REF, id1.getText()));  metadata.setColumn(new Index($INDEX.text));}
        |	'{' 'row' ':' INDEX ',' 'column' '=' id1=ID '}' 'as' id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setRow(new Index($INDEX.text)); metadata.setColumn(new Index(IndexType.REF, id1.getText()));}
        |	'{' 'row' '=' id1=ID ',' 'column' '=' id2=ID '}' 'as' id3=ID {metadata = new TableBodyMetaData(id3.getText()); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(IndexType.REF, id2.getText()));}
        |       '{' 'tbody' ':' inx1=INDEX ',' 'row' ':' inx2=INDEX ',' 'column' ':' inx3=INDEX '}' 'as' ID {metadata = new TableBodyMetaData($ID.text); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(inx2.getText())); metadata.setColumn(new Index(inx3.getText()));}
        |	'{' 'tbody' ':' inx1=INDEX ',' 'row' '=' id1=ID ',' 'column' ':' inx2=INDEX '}' 'as' id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(inx2.getText()));}
        |    	'{' 'tbody' ':' inx1=INDEX ',' 'row' ':' inx2=INDEX ',' 'column' '=' id1=ID '}' 'as' id2=ID {metadata = new TableBodyMetaData(id2.getText()); metadata.setTbody(new Index(inx1.getText())); metadata.setRow(new Index(inx2.getText())); metadata.setColumn(new Index(IndexType.REF, id1.getText()));}
        |	'{' 'tbody' ':' INDEX ',' 'row' '=' id1=ID ',' 'column' '=' id2=ID '}' 'as' id3=ID {metadata = new TableBodyMetaData(id3.getText()); metadata.setTbody(new Index($INDEX.text)); metadata.setRow(new Index(IndexType.REF, id1.getText())); metadata.setColumn(new Index(IndexType.REF, id2.getText()));}
//        |       ALL {metadata = new TableBodyMetaData($ALL.text); metadata.setTbody(new Index($ALL.text)); metadata.setRow(new Index($ALL.text)); metadata.setColumn(new Index($ALL.text)); }
        ;              			
							
fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
INDEX	:	(DIGIT+ | 'all' | 'odd' | 'even' | 'any' | 'first' | 'last' );   
ID 	: 	LETTER (LETTER | DIGIT)*;
WS 	: 	(' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};