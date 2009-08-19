package org.tellurium.dsl
/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 17, 2009
 *
 */

public class DiagnosisResponse {
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

  def DiagnosisResponse() {
  }

  def DiagnosisResponse(Map map) {
    this.uid = map.get(UID);
    this.count = map.get(COUNT);
    this.matches = map.get(MATCHES);
    this.parents = map.get(PARENTS);
    this.closest = map.get(CLOSEST);
  }

  public void show() {
    println("\nDiagnosis Result for " + uid);
    println("\n-------------------------------------------------------\n")

    println("\tMatching count: " + count + "\n");
    if(matches != null && matches.size() > 0){
      println("\tMatch elements: \n");
      int i = 0;
      matches.each {String elem ->
        i++;
        println("\t--- Element " + i + " ---\n");
        println("\t\t" + elem + "\n");
      }
    }

    if(parents != null && parents.size() > 0){
      println("\n\tParents: \n");
      int j = 0;
      parents.each {String elem ->
        j++;
        println("\t--- Parent " + j + "---\n");
        println("\t\t" + elem + "\n");
      }
    }

    if(closest != null && closest.size() > 0){
      println("\n\tClosest: \n");
      int k = 0;

      closest.each {String elem ->
        k++;
        println("\t--- closest element " + k + "---\n");
        println("\t\t" + elem + "\n");
      }
    }

    println("\n-------------------------------------------------------\n")
  }
}