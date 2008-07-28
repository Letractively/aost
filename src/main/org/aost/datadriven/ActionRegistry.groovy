package org.aost.datadriven
/**
 *
 * Registry to hold user defined actions
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class ActionRegistry {

    private Map<String, Closure> actions = new HashMap<String, Closure>()

    public void addAction(String name, Closure c){
        actions.put(name, c)
    }

    public Closure getAction(String name){
        actions.get(name)
    }

    public int size(){
        return actions.size()
    }
}