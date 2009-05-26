package org.tellurium.bundle

import org.tellurium.dsl.UiID

/**
 * Selenium Command sending to the Engine
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 19, 2009
 * 
 */

public class SelenCmd {
  
  //sequence number for this command
  public static final String SEQUENCE = "sequ";
  private int sequ;

  //The element UID associated with this command
  public static final String UID = "uid";
  private String uid;

  //the command name
  public static final String NAME = "name";
  private String name;

  //arguments for this command
  public static final String ARGS = "args";
  private def args;

  public String getParentUid(){
    if(uid != null && uid.trim().length() > 0){
      UiID uid = UiID.convertToUiID(uid);
      String parentUid = uid.pop();

      return parentUid;
    }

    return null;
  }

  public SelenCmd(int sequ, String uid, String name, args) {
    this.name = name;
    this.sequ = sequ;
    this.uid = uid;
    this.args = args;
  }
}