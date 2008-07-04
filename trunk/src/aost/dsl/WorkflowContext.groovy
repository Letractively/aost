package aost.dsl

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

    def context = [:]

    Object getContext(String name){

        return context.get(name)
    }

    void putContext(String name, Object obj){
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