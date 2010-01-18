package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 *
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 9, 2009
 *
 */

public class StyleModule extends DslContext {

    public static String HTML_BODY = """
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN">
<html>
<head>
  <title>My first styled page</title>
  <style type="text/css">
     .error {
        color:red;
      }
    * {
        margin:0;
      }
     .c1 {
        background: black none repeat
      }
  </style>
</head>
<body>
<div id="errorDiv" class="error" style="">
A message cannot contain a blank message body.
<br/>
</div>
    
<div class="c1">
</body>
</html>
    """

    public void defineUi() {
      ui.Div(uid: "errorDiv", clocator: [class: "error"])
      ui.Div(uid: "c1", clocator: [class: "c1"])
    }

}