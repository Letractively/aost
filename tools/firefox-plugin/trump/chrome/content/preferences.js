function TrUMP(){

    //default options for TrUMP
    this.DEFAULT_OPTIONS = {

	    optionDirectory: "/",

        optionJslog: "true"
    };

    this.directry = this.DEFAULT_OPTIONS.optionDirectory;
    this.jslog = this.DEFAULT_OPTIONS.optionJslog;
};

var TrUMPOption = new TrUMP();