teJQuery("#selenium_myiframe").contents().find("#category-list > li.division:eq(0) ul > li:eq(0) a").css("background-color");

var elem = teJQuery("#selenium_myiframe").contents().find("#category-list > li.division:eq(0) ul > li:eq(0) a").get(0);

fbLog(elem.style.backgroundColor);

teJQuery.curCSS(elem, "background-color");