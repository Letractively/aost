package org.telluriumsource.example.other

import org.telluriumsource.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jan 7, 2010
 * 
 */

public class MenuModule extends DslContext {

  public void defineUi() {
    ui.Container(uid: "categories", clocator: [tag: "div", class: "categories"]) {
      Container(uid: "body", clocator: [tag: "div", class: "body"]) {
        List(uid: "categoryList", clocator: [tag: "ul", id: "category-list"]) {
          Container(uid: "all", clocator: [tag: "li", class: "division"]) {
            List(uid: "cat", clocator: [tag: "ul"], separator: "li") {
              UrlLink(uid: "all", clocator: [:])
            }
          }
        }
      }
    }

    ui.Container(uid: "colors", clocator: [tag: "div", class: "colors"]){
      Div(uid: "color1", clocator: [tag: "div", position: "1"])
      Div(uid: "color2", clocator: [tag: "div", position: "2"])
      Div(uid: "color3", clocator: [tag: "div", position: "3"])
      Div(uid: "color4", clocator: [tag: "div", position: "4"])
    }
  }
/*
.red{
background-color: red;
}

 */
  public static String HTML= """
<html>
<head>
<script src="http://localhost:4444/selenium-server/core/scripts/jquery-1.3.2.js"> </script>
<style type="text/css">
.content .division .category.selected {
background-color: red;
}

</style>
</head>
<body>

<div class="categories">
<div class="content">
<div class="body">
       <ul id="category-list">
               <li class="division">
                       <ul>
                       <li class="category selected">
                               <div class="title">
                                       <a href="/suv" class="category-suv">SUVs (6)</a>
                               </div>

                       </li>

                           <li class="category category_69564">
                               <div class="title">
                                       <a href="/4by4" class="category-general">4x4 (6)</a>
                               </div>

                       </li>
                       <li class="category category_73293">
                               <div class="title">
                                       <a href="/hybrid" class="category-hybrid">Hybrid</a>
                               </div>
                       </li>
              </ul>
               </li>
    </ul>
</div>
<div class="colors">
  <div style="background-color:blue;"></div>
  <div style="background-color:rgb(15,99,30);"></div>
  <div style="background-color:#123456;"></div>
  <div class="red"></div>
</div>
</div>
</div>

</body>
</html>
  """
}