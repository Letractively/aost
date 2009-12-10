package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 13, 2009
 * 
 */

public class M1Module extends DslContext {
  
    public static String HTML_BODY = """
<span id="article_body_parent" class="mceEditor defaultSkin">
<table id="article_body_tbl" class="mceLayout" cellspacing="0" cellpadding="0" style="width: 600px; height: 409px;">
    <tbody>
    <tr class="mceFirst">
        <td class="mceToolbar mceLeft mceFirst mceLast">
            <a title="Jump to tool buttons - Alt+Q, Jump to editor - Alt-Z, Jump to element path - Alt-X" accesskey="q"
               href="http://localhost:8080/"></a>
            <table id="article_body_toolbar1" class="mceToolbar mceToolbarRow1 Enabled" cellspacing="0" cellpadding="0"
                   align="left">
                <tbody>
                <tr>
                    <td class="mceToolbarStart mceToolbarStartButton mceFirst">
                    </td>
                    <td class="mceToolbarEnd mceToolbarEndButton mceLast">
                    </td>
                </tr>
                </tbody>
            </table>
            <a onfocus="tinyMCE.getInstanceById('article_body').focus();"
               title="Jump to tool buttons - Alt+Q, Jump to editor - Alt-Z, Jump to element path - Alt-X" accesskey="z"
               href="http://localhost:8080"></a>
        </td>
    </tr>
    <tr class="mceLast">
        <td class="mceIframeContainer mceFirst mceLast">
            <iframe id="article_body_ifr" frameborder="0" style="width: 100%; height: 386px;">
                <html>
                <head xmlns="http://www.w3.org/1999/xhtml">
                </head>
                <body id="tinymce" class="mceContentBody" spellcheck="false" dir="ltr">
                <p>
                </p>
                </body>
                </html>
            </iframe>
        </td>
    </tr>
    </tbody>
</table>
</span>
"""

  public void defineUi() {
    ui.Container(uid: "writearticle", clocator: [tag: "div", id: "write_step-1"]) {
      Table(uid: "article_body_tbl", clocator: [tag: "table", class: "mceLayout"]) {
        Container(uid: "row: 1, column: 1") {
          UrlLink(uid: "toolbuttonq", clocator: [title: "^Jump to tool buttons", accesskey: "q"])
          Table(uid: "article_body_toolbar1", clocator: [:]) {
            //UI elements inside
          }
          UrlLink(uid: "toolbuttonz", clocator: [title: "^Jump to tool buttons", accesskey: "z"])
        }
        Container(uid: "row: 2, column: 1") {
        }
      }
    }

     ui.Frame(uid: "article_body_ifr", id: "article_body_ifr") {
      Container(uid: "tinymce", clocator: [tag: "body", id: "tinymce", class: "mceContentBody"]) {
        InputBox(uid: "body", clocator: [tag: "p"])
      }
    }
  }

  public void typeArticleBodyText(String input) {
//    selectFrame("article_body_ifr")
    selectFrameByIndex(0);
    type "article_body_ifr.tinymce.body", input
    pause 500
  }
}