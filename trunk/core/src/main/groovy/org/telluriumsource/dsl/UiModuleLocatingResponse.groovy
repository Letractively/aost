package org.telluriumsource.dsl
/**
 * The response object Passing back from Engine when do UI module locating and caching
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 31, 2009
 * 
 */

public class UiModuleLocatingResponse {
    //ID for the UI module
    public static String ID = "id";
    private String id = null;

    //Successfully found or not
    public static String FOUND = "found";
    private boolean found = false;

    //whether this the UI module used closest Match or not
    public static String RELAXED = "relaxed";
    private boolean relaxed = false;

    //details for the relax, i.e., closest match
    public static String RELAXDETAILS = "relaxDetails";
    private List<RelaxDetail> relaxDetails = null;

    def UiModuleLocatingResponse(){
      
    }

    def UiModuleLocatingResponse(Map map){
      this.id = map.get(ID);
      this.found = map.get(FOUND);
      this.relaxed = map.get(RELAXED);
      this.relaxDetails = new ArrayList();
      List lst = map.get(RELAXDETAILS);
      if(lst != null && lst.size() > 0){
        for(int i=0; i<lst.size(); i++){
          Map rdm = lst.get(i);
          RelaxDetail rd = new RelaxDetail(rdm);
          this.relaxDetails.add(rd);
        }
      }
    }
}