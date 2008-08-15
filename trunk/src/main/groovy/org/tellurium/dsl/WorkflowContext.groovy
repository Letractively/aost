package org.tellurium.dsl

/**
 * Hold metadata for execution workflow
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 2, 2008
 *
 */
class WorkflowContext {

    public static final String REFERENCE_LOCATOR = "Reference_Locator"
    public static final String OPTION_LOCATOR = "Option_Locator"

    private boolean useOption = false

    def context = [:]

    public boolean isUseOption(){
        return useOption
    }

    public void setUseOption(boolean useOption){
        this.useOption = useOption
    }

    public def getOptionLocator(){
        return context.get(OPTION_LOCATOR)
    }

    public Object getContext(String name){

        return context.get(name)
    }

    public void putContext(String name, Object obj){
        context.put(name, obj)
    }

    public static WorkflowContext getDefaultContext(){

        WorkflowContext context = new WorkflowContext()
        context.putContext(REFERENCE_LOCATOR, "")

        return context
    }

    public String getReferenceLocator(){
        
        return context.get(REFERENCE_LOCATOR)
    }

    public void setReferenceLocator(String loc){

        context.put(REFERENCE_LOCATOR, loc)
    }

    //append the relative locator to the end of the reference locator
    public void appendReferenceLocator(String loc){

        String rl = context.get(REFERENCE_LOCATOR)
        if(rl == null){
            rl = ""
        }
        if(loc != null){
            rl = rl + loc
        }

        context.put(REFERENCE_LOCATOR, rl)
    }
}