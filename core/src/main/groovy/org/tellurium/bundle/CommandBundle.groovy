package org.tellurium.bundle

import org.json.simple.JSONArray
import org.json.simple.JSONObject

/**
 *
 * Command bundle
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 19, 2009
 * 
 */

public class CommandBundle implements BundleStrategy{
  public static final String BUNDLE = "bundle";


  private List<SelenCmd> bundle = new ArrayList<SelenCmd>();

  private String parentUid = null;
  
  public void addToBundle(SelenCmd newcmd){
    bundle.add(newcmd);
    parentUid = newcmd.getParentUid();
  }

  public List<SelenCmd> getAllCmds(){
    return this.bundle;
  }

  public List<SelenCmd> extractAllCmds(){
    List<SelenCmd> result = new ArrayList<SelenCmd>();
    result.addAll(bundle);

    bundle.clear();

    return result;
  }

  public String extractAllAndConvertToJson(){
    JSONArray arr = new JSONArray();
    bundle.each {SelenCmd cmd ->
      JSONObject obj = new JSONObject()
      obj.put(SelenCmd.SEQUENCE, cmd.sequ);
      obj.put(SelenCmd.UID, cmd.uid);
      obj.put(SelenCmd.NAME, cmd.name);
      JSONArray arglist = new JSONArray();
      if(cmd.args != null){
        cmd.args.each {param ->
          arglist.add(param);
        }
      }
      obj.put(SelenCmd.ARGS, arglist);
      arr.add(obj);
    }

    String json = arr.toString();
    bundle.clear();
    
    return json;
  }

  //only append to the bundle if they are in the same UI module
  public boolean shouldAppend(SelenCmd newcmd) {
    if(bundle.size() == 0){
      return true;
    }

    if(parentUid == null)
      return false;

    String puid = newcmd.getParentUid();

    return parentUid.equalsIgnoreCase(puid);  
  }

  public int size(){
    return bundle.size();
  }
}