function Preferences(){

    //default options for TrUMP
    this.DEFAULT_OPTIONS = {

	    optionDirectory: "/",

        optionJslog: "true",

        optionLogWrap: "false"
    };

    this.directry = this.DEFAULT_OPTIONS.optionDirectory;
    this.jslog = this.DEFAULT_OPTIONS.optionJslog;
    this.logWrap = this.DEFAULT_OPTIONS.optionLogWrap;
};