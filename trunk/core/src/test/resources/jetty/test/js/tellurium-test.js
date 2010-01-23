function TelluriumTestCase(){

};

TelluriumTestCase.prototype.testUiid = function(){
    var uiid = new Uiid();
    uiid.convertToUiid("ProblematicForm.Username.Input");
    fbLog("", uiid);
    fbLog("", uiid.peek());
    var uiid1 = getUiid("Form.Username.Input");
    var uiid2 = getUiid("Form.Username");
    var match = uiid1.matchWith(uiid2);
    var sub = uiid1.subUiid(uiid2.size());
};

TelluriumTestCase.prototype.testPrie = function(){
    var trie = new Trie();
    trie.insert("Form", "form1");
    trie.insert("Form.Password", "password2");
    trie.insert("Form.Username.Input", "input4");
    trie.insert("Form.Username.Label", "username3");
    trie.insert("Form.Password.Input", "input6");   
    trie.insert("Form.Submit", "submit5");
    trie.checkLevel();
    trie.printMe();
    trie.dumpMe();
    var form = trie.getChildrenData("Form");
    var username = trie.getChildrenData("Form.Username");
    var label = trie.getChildrenData("Form.Username.Submit");
};

TelluriumTestCase.prototype.testGeneralTableModule = function(){
    var json = [{"obj":{"uid":"GT","hrt":"tr","locator":{"tag":"table","attributes":{"id":"xyz"}},"ht":"tbody","bct":"td","frt":"tr","hct":"th","ft":"tfoot","brt":"tr","uiType":"StandardTable","fct":"td","bt":"tbody"},"key":"GT"},{"obj":{"uid":"header: all","locator":{},"uiType":"TextBox"},"key":"GT._ALL"},{"obj":{"uid":"row: 1, column: 1","locator":{"tag":"div","attributes":{"class":"abc"}},"uiType":"TextBox"},"key":"GT._1_1_1"},{"obj":{"uid":"row: 1, column: 2","locator":null,"uiType":"Container"},"key":"GT._1_1_2"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"class":"123"}},"uiType":"InputBox"},"key":"GT._1_1_2.Input"},{"obj":{"uid":"Some","locator":{"tag":"div","attributes":{"class":"someclass"}},"uiType":"Container"},"key":"GT._1_1_2.Some"},{"obj":{"uid":"Span","locator":{"tag":"span","attributes":{"class":"x"}},"uiType":"Span"},"key":"GT._1_1_2.Some.Span"},{"obj":{"uid":"Link","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"GT._1_1_2.Some.Link"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    alg.allowRelax = true;
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var uiid = new Uiid();
    var tb = uim.walkTo(context, uiid.convertToUiid("GT"));
    var worker = new TextUiWorker();
    var out = tb.getAllBodyCell(context, worker);
    var uinput = uim.walkTo(new WorkflowContext(), uiid.convertToUiid("GT[1][1]"));
    var pinput = uim.walkTo(new WorkflowContext(), uiid.convertToUiid("GT[1][2].Input"));
    var smt = uim.walkTo(new WorkflowContext(), uiid.convertToUiid("GT[1][2].Some.Link"));
};

TelluriumTestCase.prototype.testTelluriumIssueModule = function(){
    var json = [{"obj":{"uid":"issueSearch","locator":{"tag":"form","attributes":{"action":"list","method":"get"}},"uiType":"Form"},"key":"issueSearch"},{"obj":{"uid":"issueType","locator":{"tag":"select","attributes":{"id":"can","name":"can"}},"uiType":"Selector"},"key":"issueSearch.issueType"},{"obj":{"uid":"searchLabel","locator":{"text":"*for","tag":"span"},"uiType":"TextBox"},"key":"issueSearch.searchLabel"},{"obj":{"uid":"searchBox","locator":{"tag":"input","attributes":{"name":"q","type":"text"}},"uiType":"InputBox"},"key":"issueSearch.searchBox"},{"obj":{"uid":"searchButton","locator":{"tag":"input","attributes":{"value":"Search","type":"submit"}},"uiType":"SubmitButton"},"key":"issueSearch.searchButton"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    alg.allowRelax = true;
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var uiid = new Uiid();
    var uinput = uim.walkTo(context, uiid.convertToUiid("issueSearch.issueType"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("issueSearch.searchBox"));
    var smt = uim.walkTo(context, uiid.convertToUiid("issueSearch.searchButton"));
};

TelluriumTestCase.prototype.testEditPageModule = function(){
    var json = [{"obj":{"uid":"Account","locator":{"tag":"form","attributes":{"method":"post"}},"uiType":"Form"},"key":"Account"},{"obj":{"uid":"Name","locator":{"tag":"input","attributes":{"name":"A","type":"text"}},"uiType":"InputBox"},"key":"Account.Name"},{"obj":{"uid":"Site","locator":{"tag":"input","attributes":{"name":"B","type":"text"}},"uiType":"InputBox"},"key":"Account.Site"},{"obj":{"uid":"Revenue","locator":{"tag":"input","attributes":{"name":"C","type":"text"}},"uiType":"InputBox"},"key":"Account.Revenue"},{"obj":{"uid":"Info","locator":{"tag":"div","attributes":{"class":"info"}},"uiType":"Container"},"key":"Account.Info"},{"obj":{"uid":"Label","locator":{"text":"Test:","tag":"div","attributes":{"id":"label1"}},"uiType":"TextBox"},"key":"Account.Info.Label"},{"obj":{"uid":"Test","locator":{"tag":"input","attributes":{"id":"input5","name":"testname","type":"text"}},"uiType":"InputBox"},"key":"Account.Info.Test"},{"obj":{"uid":"save","locator":{"tag":"input","attributes":{"title":"Save","name":"save","class":"btn","type":"submit"}},"uiType":"SubmitButton"},"key":"Account.Save"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var uiid = new Uiid();
    var uinput = uim.walkTo(context, uiid.convertToUiid("Account.Site"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("Account.Info.Test"));
    var smt = uim.walkTo(context, uiid.convertToUiid("Account.Save"));
};

TelluriumTestCase.prototype.testLogicalContainerModule = function(){
//    var json = [{"obj":{"uid":"AbstractForm","locator":{"tag":"form"},"uiType":"Form"},"key":"AbstractForm"},{"obj":{"uid":"Form1","locator":{"loc":null},"uiType":"Container"},"key":"AbstractForm.Form1"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"AbstractForm.Form1.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"AbstractForm.Form1.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"AbstractForm.Form1.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"AbstractForm.Form1.Password"},{"obj":{"uid":"Password1","locator":{"loc":null},"uiType":"Container"},"key":"AbstractForm.Form1.Password.Password1"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"AbstractForm.Form1.Password.Password1.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"AbstractForm.Form1.Password.Password1.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"AbstractForm.Form1.Submit"}];
    var json = [{"obj":{"uid":"AbstractForm","locator":{"tag":"form"},"uiType":"Form"},"key":"AbstractForm"},{"obj":{"uid":"Form1","locator":null,"uiType":"Container"},"key":"AbstractForm.Form1"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"AbstractForm.Form1.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"AbstractForm.Form1.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"AbstractForm.Form1.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"AbstractForm.Form1.Password"},{"obj":{"uid":"Password1","locator":null,"uiType":"Container"},"key":"AbstractForm.Form1.Password.Password1"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"AbstractForm.Form1.Password.Password1.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"AbstractForm.Form1.Password.Password1.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"AbstractForm.Form1.Submit"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    context.alg = alg;
    var uiid = new Uiid();
    var uinput = uim.walkTo(context, uiid.convertToUiid("AbstractForm.Form1.Username.Input"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("AbstractForm.Form1.Password.Password1.Input"));
    var smt = uim.walkTo(context, uiid.convertToUiid("AbstractForm.Form1.Submit")); 
};

TelluriumTestCase.prototype.testLogonUiModule = function(){
 //  var json =[{"obj":{"uid":"Form","locator":{"tag":"table"},"generated":"\/\/descendant-or-self::table","uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Username:\")]","uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"direct":true,"tag":"input","name":"j_username","type":"text"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::input[@type=\"text\" and @name=\"j_username\"]","uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Password:\")]","uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"direct":true,"tag":"input","name":"j_password","type":"password"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::input[@type=\"password\" and @name=\"j_password\"]","uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","name":"submit","value":"Login","type":"submit"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::input[@type=\"submit\" and @value=\"Login\" and @name=\"submit\"]","uiType":"SubmitButton"},"key":"Form.Submit"}];
//    var json = [{"obj":{"uid":"Form","locator":{"tag":"table"},"generated":"\/\/descendant-or-self::table","uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Username:\")]","uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","name":"j_username","type":"text"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/descendant-or-self::input[@type=\"text\" and @name=\"j_username\"]","uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Password:\")]","uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","name":"j_password","type":"password"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/descendant-or-self::input[@type=\"password\" and @name=\"j_password\"]","uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","name":"submit","value":"Login","type":"submit"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::input[@type=\"submit\" and @value=\"Login\" and @name=\"submit\"]","uiType":"SubmitButton"},"key":"Form.Submit"}]
//    var json = [{"obj":{"uid":"Form","locator":{"tag":"table"},"uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"Form.Submit"}];
//    var json = [{"obj":{"uid":"Google","locator":{"tag":"table"},"uiType":"Container"},"key":"Google"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"title":"Google Search","name":"q"}},"uiType":"InputBox"},"key":"Google.Input"},{"obj":{"uid":"Search","locator":{"tag":"input","attributes":{"name":"btnG","value":"Google Search","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.Search"},{"obj":{"uid":"ImFeelingLucky","locator":{"tag":"input","attributes":{"name":"btnI","value":"I'm Feeling Lucky","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.ImFeelingLucky"}];
//    var json = [{"obj":{"uid":"Google","locator":{"tag":"table"},"uiType":"Container"},"key":"Google"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"title":"Google Search","name":"q"}},"uiType":"InputBox"},"key":"Google.Input"},{"obj":{"uid":"Search","locator":{"tag":"input","attributes":{"name":"btnG","value":"Google Search","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.Search"},{"obj":{"uid":"ImFeelingLucky","locator":{"tag":"input","attributes":{"name":"btnI","value":"I'm Feeling Lucky","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.ImFeelingLucky"}];

//    var json = [{"obj":{"uid":"ProblematicForm","locator":{"tag":"table"},"uiType":"Container"},"key":"ProblematicForm"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"text"}},"uiType":"InputBox"},"key":"ProblematicForm.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"password"}},"uiType":"InputBox"},"key":"ProblematicForm.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"logon","type":"submit"}},"uiType":"SubmitButton"},"key":"ProblematicForm.Submit"}];

    var json = [{"obj":{"uid":"Form","locator":{"tag":"form"},"uiType":"Form"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"Form.Submit"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var uiid = new Uiid();
//    var uinput = uim.walkTo(context, "ProblematicForm.Username.Input");
//    var pinput = uim.walkTo(context, "ProblematicForm.Password.Input");
//    var smt = uim.walkTo(context, "ProblematicForm.Submit");
    var uinput = uim.walkTo(context, uiid.convertToUiid("Form.Username.Input"));
    var pinput = uim.walkTo(context, uiid.convertToUiid("Form.Password.Input"));
    var smt = uim.walkTo(context, uiid.convertToUiid("Form.Submit"));
};

TelluriumTestCase.prototype.testLogoUiModule = function(){
    var json = [{"obj":{"uid":"Logo","locator":{"tag":"img","attributes":{"alt":"Logo","src":"*.gif"}},"uiType":"Image"},"key":"Logo"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var uiid = new Uiid();
    var logo = uim.walkTo(context, uiid.convertToUiid("Logo"));
};

TelluriumTestCase.prototype.testThumbnailUiModule = function(){
    var json = [{"obj":{"uid":"Thumbnail","locator":{"tag":"div","attributes":{"class":"thumbnail potd"}},"uiType":"Container"},"key":"Thumbnail"},{"obj":{"uid":"ICon","locator":{"tag":"div","attributes":{"class":"potd:icon png.fix"}},"uiType":"Container"},"key":"Thumbnail.ICon"},{"obj":{"uid":"Image","locator":{"tag":"img","attributes":{"src":"*.jpg"}},"uiType":"Image"},"key":"Thumbnail.ICon.Image"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"id":"Image:name","type":"text"}},"uiType":"InputBox"},"key":"Thumbnail.ICon.Input"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var uiid = new Uiid();
    var image = uim.walkTo(context, uiid.convertToUiid("Thumbnail.ICon.Image"));
    var input = uim.walkTo(context, uiid.convertToUiid("Thumbnail.ICon.Input"));
};

TelluriumTestCase.prototype.testBookUiModule = function(){
//    var json = [{"obj":{"uid":"GoogleBooksList","locator":{"tag":"table","attributes":{"id":"hp_table"}},"uiType":"Container"},"key":"GoogleBooksList"},{"obj":{"uid":"category","locator":{"tag":"div","attributes":{"class":"sub_cat_title"}},"uiType":"TextBox"},"key":"GoogleBooksList.category"},{"obj":{"uid":"subcategory","locator":{"tag":"div","attributes":{"class":"sub_cat_section"}},"uiType":"List","separator":"p"},"key":"GoogleBooksList.subcategory"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"GoogleBooksList.subcategory._ALL"}];
    var json = [{"obj":{"uid":"GoogleBooksList","locator":{"tag":"table","attributes":{"id":"hp_table"}},"uiType":"Container"},"key":"GoogleBooksList"},{"obj":{"uid":"subcategory","locator":{"tag":"td","attributes":{"class":"sidebar"}},"uiType":"List","separator":"div"},"key":"GoogleBooksList.subcategory"},{"obj":{"uid":"all","locator":{"loc":null},"uiType":"Container"},"key":"GoogleBooksList.subcategory._ALL"},{"obj":{"uid":"title","locator":{"tag":"div","attributes":{"class":"sub_cat_title"}},"uiType":"TextBox"},"key":"GoogleBooksList.subcategory._ALL.title"},{"obj":{"uid":"links","locator":{"loc":null},"uiType":"List","separator":"p"},"key":"GoogleBooksList.subcategory._ALL.links"},{"obj":{"uid":"all","locator":{"tag":"a"},"uiType":"UrlLink"},"key":"GoogleBooksList.subcategory._ALL.links._ALL"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
//    var dom = teJQuery("html > body");
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var uiid = new Uiid();
    var list = uim.walkTo(context, uiid.convertToUiid("GoogleBooksList"));
    var category = uim.walkTo(context, uiid.convertToUiid("GoogleBooksList.category"));
    var subcategory = uim.walkTo(context, uiid.convertToUiid("GoogleBooksList.subcategory"));
    var subcategorylink1 = uim.walkTo(context, uiid.convertToUiid("GoogleBooksList.subcategory[1]"));
    var subcategorylink2 = uim.walkTo(context,  uiid.convertToUiid("GoogleBooksList.subcategory[2]"));
};

TelluriumTestCase.prototype.testExpandUiModule = function(){
    var json = [{"obj":{"uid":"expand","locator":{"tag":"a","attributes":{"id":"qrForm:innie"}},"uiType":"UrlLink"},"key":"expand"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var list = uim.walkTo(context, "expand");
};

TelluriumTestCase.prototype.testErisUiModule = function(){
//    var json = [{"obj":{"uid":"EcisPlusUiCAV","locator":{"tag":"table"},"uiType":"Container"},"key":"EcisPlusUiCAV"},{"obj":{"uid":"Save","locator":{"tag":"input","attributes":{"id":"cif:customerInfoSaveButton","name":"cif:customerInfoSaveButton","value":"Save","class":"btn saveButton","type":"button"}},"events":["click"],"uiType":"InputBox"},"key":"EcisPlusUiCAV.Save"}];
    var json = [{"obj":{"uid":"EcisPlusUiCAV","locator":{"tag":"div"},"uiType":"Container"},"key":"EcisPlusUiCAV"},{"obj":{"uid":"Save","locator":{"tag":"input","attributes":{"id":"cif:customerInfoSaveButton","name":"cif:customerInfoSaveButton","value":"Save","class":"btn saveButton","type":"button"}},"events":["click"],"uiType":"InputBox"},"key":"EcisPlusUiCAV.Save"}];
    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    var dom = teJQuery("html");
    alg.validate(uim, dom);
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var uiid = new Uiid();
    uiid.convertToUiid("EcisPlusUiCAV.Save");
//    uiid.reverse();
    var save = uim.walkTo(context, uiid);
};

TelluriumTestCase.prototype.testUiCache = function(){

    var json = [{"obj":{"uid":"ProblematicForm","locator":{"tag":"table"},"uiType":"Container"},"key":"ProblematicForm"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"text"}},"uiType":"InputBox"},"key":"ProblematicForm.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"password"}},"uiType":"InputBox"},"key":"ProblematicForm.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"logon","type":"submit"}},"uiType":"SubmitButton"},"key":"ProblematicForm.Submit"}];
    tellurium.cache.uiAlg.allowRelax = true;
    tellurium.cache.useUiModule(JSON.stringify(json));
    tellurium.getUiElementFromCache("ProblematicForm.Username.Input");
    tellurium.getUiElementFromCache("ProblematicForm.Password.Input");
};

TelluriumTestCase.prototype.testSuite = function(){
//    this.testUiid();
//    this.testPrie();
//    this.testLogonUiModule(); 
//    this.testEditPageModule();
//    this.testLogicalContainerModule();
//    this.testTelluriumIssueModule();
    this.testGeneralTableModule();
//    this.testLogoUiModule();
//    this.testThumbnailUiModule();
//    this.testBookUiModule();
//    this.testExpandUiModule();
//    this.testErisUiModule();
//    this.testUiCache();
};
                                   
var teTestCase = new TelluriumTestCase();

