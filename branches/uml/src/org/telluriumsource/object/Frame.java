package org.telluriumsource.object;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * <p/>
 * Date: Oct 1, 2009
 */
public class Frame extends Container {

    private String id;

    private String name;

    private String title;

    public void selectFrame(){

    }

    public void selectParentFrame(){

    }

    public void selectTopFrame(){

    }

    public boolean getWhetherThisFrameMatchFrameExpression(String target){
        return false;
    }

    public void waitForFrameToLoad(int timeout){
        
    }
}
