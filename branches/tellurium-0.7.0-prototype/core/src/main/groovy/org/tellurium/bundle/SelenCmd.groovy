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
  private int sequ;

  //The element UID associated with this command
  public static final String UID = "uid";
  private String uid;

  //the command name
  private String name;

  //arguments for this command
  private def args;

  public String getParentUid(){
    UiID uid = UiID.convertToUiID(uid);
    String parentUid = uid.pop();

    return parentUid;
  }

  public SelenCmd(int sequ, String uid, String name, args) {
    this.name = name;
    this.sequ = sequ;
    this.uid = uid;
    this.args = args;
  }
}