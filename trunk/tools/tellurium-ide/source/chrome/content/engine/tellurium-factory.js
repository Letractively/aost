
function TelluriumFactory() {
  var instance = (function() {
    var _tellurium = null;
    var initialized = false;

    function initialize () {
        _tellurium = new Tellurium();
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

var tellurium = (new TelluriumFactory()).getInstance();

var browserBot = tellurium.browserBot;

