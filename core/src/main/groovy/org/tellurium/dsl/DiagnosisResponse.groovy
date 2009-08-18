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

  def DiagnosisResponse() {
  }

  def DiagnosisResponse(Map map) {
    this.uid = map.get(UID);
    this.uid = map.get(COUNT);
    this.matches = map.get(MATCHES);
    this.parents = map.get(PARENTS);
  }

  public void show() {
    println("\nDiagnosis Result for " + uid);
    println("\n-------------------------------------------------------\n")

    println("\tMatching count: " + count + "\n");
    if(matches != null && matches.size() > 0){
      println("\tMatch elements: \n");
      matches.each {String elem ->
        println("\t\t" + elem + "\n");
      }
    }

    if(parents != null && parents.size() > 0){
      println("\n\tClosest elements: \n")
      parents.each {String elem ->
        println("\t\t" + elem + "\n");
      }
    }

    println("\n-------------------------------------------------------\n")
  }
}