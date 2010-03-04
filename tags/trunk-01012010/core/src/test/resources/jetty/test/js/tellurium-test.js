function TelluriumTestCase(){

};

TelluriumTestCase.prototype.testUiid = function(){
    var uiid = new Uiid();
    uiid.convertToUiid("ProblematicForm.Username.Input");
    fbLog("", uiid);
    fbLog("", uiid.peek());
};

TelluriumTestCase.prototype.testUiModule = function(){
 //  var json =[{"obj":{"uid":"Form","locator":{"tag":"table"},"generated":"\/\/descendant-or-self::table","uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Username:\")]","uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"direct":true,"tag":"input","name":"j_username","type":"text"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::input[@type=\"text\" and @name=\"j_username\"]","uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Password:\")]","uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"direct":true,"tag":"input","name":"j_password","type":"password"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::input[@type=\"password\" and @name=\"j_password\"]","uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","name":"submit","value":"Login","type":"submit"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::input[@type=\"submit\" and @value=\"Login\" and @name=\"submit\"]","uiType":"SubmitButton"},"key":"Form.Submit"}];
//    var json = [{"obj":{"uid":"Form","locator":{"tag":"table"},"generated":"\/\/descendant-or-self::table","uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Username:\")]","uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","name":"j_username","type":"text"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/descendant-or-self::input[@type=\"text\" and @name=\"j_username\"]","uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Password:\")]","uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","name":"j_password","type":"password"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/descendant-or-self::input[@type=\"password\" and @name=\"j_password\"]","uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","name":"submit","value":"Login","type":"submit"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::input[@type=\"submit\" and @value=\"Login\" and @name=\"submit\"]","uiType":"SubmitButton"},"key":"Form.Submit"}]
//    var json = [{"obj":{"uid":"Form","locator":{"tag":"table"},"uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_username","type":"text"}},"uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j_password","type":"password"}},"uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"Login","type":"submit"}},"uiType":"SubmitButton"},"key":"Form.Submit"}];
//    var json = [{"obj":{"uid":"Google","locator":{"tag":"table"},"uiType":"Container"},"key":"Google"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"title":"Google Search","name":"q"}},"uiType":"InputBox"},"key":"Google.Input"},{"obj":{"uid":"Search","locator":{"tag":"input","attributes":{"name":"btnG","value":"Google Search","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.Search"},{"obj":{"uid":"ImFeelingLucky","locator":{"tag":"input","attributes":{"name":"btnI","value":"I'm Feeling Lucky","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.ImFeelingLucky"}];
//    var json = [{"obj":{"uid":"Google","locator":{"tag":"table"},"uiType":"Container"},"key":"Google"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"title":"Google Search","name":"q"}},"uiType":"InputBox"},"key":"Google.Input"},{"obj":{"uid":"Search","locator":{"tag":"input","attributes":{"name":"btnG","value":"Google Search","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.Search"},{"obj":{"uid":"ImFeelingLucky","locator":{"tag":"input","attributes":{"name":"btnI","value":"I'm Feeling Lucky","type":"submit"}},"uiType":"SubmitButton"},"key":"Google.ImFeelingLucky"}];
     var json = [{"obj":{"uid":"ProblematicForm","locator":{"tag":"table"},"uiType":"Container"},"key":"ProblematicForm"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Username:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Username.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"text"}},"uiType":"InputBox"},"key":"ProblematicForm.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"uiType":"Container"},"key":"ProblematicForm.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"text":"Password:","tag":"td"},"uiType":"TextBox"},"key":"ProblematicForm.Password.Label"},{"obj":{"uid":"Input","locator":{"tag":"input","attributes":{"name":"j","type":"password"}},"uiType":"InputBox"},"key":"ProblematicForm.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","attributes":{"name":"submit","value":"logon","type":"submit"}},"uiType":"SubmitButton"},"key":"ProblematicForm.Submit"}];

   var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
//    var dom = teJQuery("html > body");
    var dom = teJQuery("html");
    alg.santa(uim, dom);
    var context = new WorkflowContext();
    var uinput = uim.walkTo(context, "ProblematicForm.Username.Input");
    var pinput = uim.walkTo(context, "ProblematicForm.Password.Input");
    var smt = uim.walkTo(context, "ProblematicForm.Submit");
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
//    this.testUiModule();
    this.testUiCache();
};
                                   
var teTestCase = new TelluriumTestCase();
