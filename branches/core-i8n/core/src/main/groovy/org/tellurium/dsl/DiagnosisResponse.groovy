package org.tellurium.dsl

import org.tellurium.i8n.InternationalizationManager

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 17, 2009
 *
 */

public class DiagnosisResponse {
  protected InternationalizationManager i8nManager = new InternationalizationManager()

  public static final String UID = "uid"
  private String uid;

  public static final String COUNT = "count"
  private int count;

  public static final String MATCHES = "matches"
  private ArrayList<String> matches;

  public static final String PARENTS = "parents"
  private ArrayList<String> parents;

  public static final String CLOSEST = "closest"
  private ArrayList<String> closest;

  private static final String HTML = "html"
  private String html;

  def DiagnosisResponse() {
  }

  def DiagnosisResponse(Map map) {
    this.uid = map.get(UID);
    this.count = map.get(COUNT);
    this.matches = map.get(MATCHES);
    this.parents = map.get(PARENTS);
    this.closest = map.get(CLOSEST);
    this.html = map.get(HTML);
  }

  public void show() {
    println i8nManager.translate("DiagnosisResponse.DiagnosisResult" , {uid})
    
    println("\n-------------------------------------------------------\n")

    println i8nManager.translate("DiagnosisResponse.MatchingCount" , {count})
    if(matches != null && matches.size() > 0){
      println i8nManager.translate("DiagnosisResponse.MatchingElement")
      int i = 0;
      matches.each {String elem ->
        i++;
        println i8nManager.translate("DiagnosisResponse.Element" , {i})
        println(elem + "\n");
      }
    }

    if(parents != null && parents.size() > 0){
      println("\n\tParents: \n");
      int j = 0;
      parents.each {String elem ->
        j++;
        println i8nManager.translate("DiagnosisResponse.Parent" , {j})
        println(elem + "\n");
      }
    }

    if(closest != null && closest.size() > 0){
      println i8nManager.translate("DiagnosisResponse.Closest")
      println("\n\tClosest: \n");
      int k = 0;

      closest.each {String elem ->
        k++;
        println i8nManager.translate("DiagnosisResponse.ClosestElement" , {k});
        println(elem + "\n");
      }
    }

    if(html != null){
      println i8nManager.translate("DiagnosisResponse.HtmlSource")
      println(html);
      println("\n");
    }
    println("\n-------------------------------------------------------\n")
  }
}