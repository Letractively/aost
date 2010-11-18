
function TelluriumFactory() {
  var instance = (function() {
    var _tellurium = null;
    var initialized = false;

    function initialize () {
//        alert("create Tellurium instance");
        _tellurium = new Tellurium();
//        alert("start initializing Tellurium");
        _tellurium.initialize();
        initialized = true;
    }

    return {
      // public interface
      getInstance: function () {
          if(!initialized){
              initialize();
          }

          return _tellurium;
      }
    };
  })();

  TelluriumFactory = function () {
    // re-define the function for subsequent calls
    return instance;
  };

  return TelluriumFactory(); // call the new function
}

alert("tellurium before initalization " + tellurium);
var tellurium = (new TelluriumFactory()).getInstance();
alert("tellurium after initalization, ui map size " + tellurium.uiBuilderMap.size());

var browserBot = tellurium.browserBot;