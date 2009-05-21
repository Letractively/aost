package org.tellurium.bundle

import org.tellurium.dispatch.Dispatcher
import org.tellurium.config.Configurable

/**
 * Command Bundle Processor
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: May 19, 2009
 * 
 */

@Singleton
public class CommandBundleProcessor implements GroovyInterceptable, Configurable {
  public static final String OK = "ok";

  //sequence number for each command
  private int sequence = 0;

  //maximum number of commands in a bundle
  private int bundleMaxCmds = 5;

  private Dispatcher dispatcher = new Dispatcher();

  private CommandBundle bundle = new CommandBundle();

  //whether to use the bundle feature
  private boolean exploitBundle = true;

  public void useBundleFeature(){
    this.exploitBundle = true;
  }

  public void disableBundleFeature(){
    this.exploitBundle = false;
  }

  public int nextSeq(){
    return ++sequence;
  }

  //TODO: how to parse the returning result?
  protected def parseReturnValue(String value){

  }

  def issueCommand(String uid, String name, args){
    SelenCmd cmd = new SelenCmd(nextSeq(), uid, name, args);
    if(bundle.shouldAppend(cmd)){
      bundle.addToBundle(cmd);
      if(bundle.size() >= this.bundleMaxCmds){
        //need to issue the command bundle since it reaches the maximum limit
        String json = bundle.extractAllAndConvertToJson();

        String val = dispatcher.issueBundle(json);
        return parseReturnValue(val);
      }

      return OK;
    }else{
      String json = bundle.extractAllAndConvertToJson();
      bundle.addToBundle(cmd);
      
      String val = dispatcher.issueBundle(json);
      return parseReturnValue(val);
    }
  }

  def passThrough(String uid, String name, args){
    //if no command on the bundle, call directly
    if(bundle.size() == 0){
      return dispatcher.metaClass.invokeMethod(name, args);
    }else{
      //there are commands in the bundle, pigback this command with the commands in a bundle and issue it
      SelenCmd cmd = new SelenCmd(nextSeq(), uid, name, args);
      bundle.addToBundle(cmd);
      String json = bundle.extractAllAndConvertToJson();
      
      String val = dispatcher.issueBundle(json);
      return parseReturnValue(val);
    }
  }
  
  def invokeMethod(String name, args) {

  }
}