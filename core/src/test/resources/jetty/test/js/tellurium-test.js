function TelluriumTestCase(){

};

TelluriumTestCase.prototype.testUiModule = function(){
    var json =[{"obj":{"uid":"Form","locator":{"tag":"table"},"generated":"\/\/descendant-or-self::table","uiType":"Container"},"key":"Form"},{"obj":{"uid":"Username","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Username"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Username:\")]","uiType":"TextBox"},"key":"Form.Username.Label"},{"obj":{"uid":"Input","locator":{"direct":true,"tag":"input","name":"j_username","type":"text"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::input[@type=\"text\" and @name=\"j_username\"]","uiType":"InputBox"},"key":"Form.Username.Input"},{"obj":{"uid":"Password","locator":{"tag":"tr"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr","uiType":"Container"},"key":"Form.Password"},{"obj":{"uid":"Label","locator":{"direct":true,"tag":"td"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::td[normalize-space(text())=normalize-space(\"Password:\")]","uiType":"TextBox"},"key":"Form.Password.Label"},{"obj":{"uid":"Input","locator":{"direct":true,"tag":"input","name":"j_password","type":"password"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::tr\/child::input[@type=\"password\" and @name=\"j_password\"]","uiType":"InputBox"},"key":"Form.Password.Input"},{"obj":{"uid":"Submit","locator":{"tag":"input","name":"submit","value":"Login","type":"submit"},"generated":"\/\/descendant-or-self::table\/descendant-or-self::input[@type=\"submit\" and @value=\"Login\" and @name=\"submit\"]","uiType":"SubmitButton"},"key":"Form.Submit"}];

    var uim = new UiModule();
    uim.parseUiModule(JSON.stringify(json));
    var alg = new UiAlg();
    var dom = teJQuery("html > body");
    alg.takeSnapshot(uim, dom);
};

var teTestCase = new TelluriumTestCase();
