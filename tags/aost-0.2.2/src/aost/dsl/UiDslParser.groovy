package aost.dsl

import aost.object.*
import aost.builder.UiObjectBuilderRegistry

class UiDslParser extends BuilderSupport{

//       public static final String OBJECT_PREFIX = "uo_"

       UiObject root
    
       def registry = [:]

       //this should return a singleton class with default builders populated
       def UiObjectBuilderRegistry builderRegistry = new UiObjectBuilderRegistry()

/*
       protected String getObjectName(String id){
          OBJECT_PREFIX + id
       }
*/

       protected String nestObjectName(UiObject obj){
          String id
          if(obj.parent != null && obj.parent instanceof Table){
              id =  Table.internalId(obj.id)
          }else{
              id = obj.id
          }

          def op = obj

          while(op.parent != null){
            op = op.parent
            if(op.parent != null && op.parent instanceof Table){
                id = Table.internalId(op.id) + "." + id
            }else{
                id = op.id + "." + id
            }            
           }

          return id
       }

       def findUiObjectFromRegistry(String id){

           if(id.startsWith("${root.id}")){

             return registry.get(id)
           }else{
             String t = "${root.id}.${id}"

             return registry.get(t)
           }
       }

       public UiObject walkTo(WorkflowContext context, String id)
       {
          if(!id.startsWith("${root.id}")){
              id = "${root.id}.${id}"
          }

          UiID uiid = UiID.convertToUiID(id)

          if(uiid.size() > 1){
              String first = uiid.pop()
              if(root.id.equals(first)){
                  return root.walkTo(context, uiid)
              }else{
                  println("Error: expected start id is ${root.id}, but is ${first}")
                  return null
              }
          }

          println("Error: id cannot be empty")
           
          return null
       }

       def addUiObjectToRegistry(UiObject obj){

           registry.put(nestObjectName(obj), obj)
       }

       protected void setParent(Object parent, Object child) {
           if(parent instanceof Container){
               parent.add(child)
               child.parent = parent
           }

           //only put the object to the registry when its parent is the root
           //since the root is special case
           if(root.id.equals(parent.id))
               addUiObjectToRegistry(child)
       }

       protected Object createNode(Object name) {
           def builder = builderRegistry.getBuilder(name)

           if(builder != null){
                def obj =  builder.build(null, null)
//                addUiObjectToRegistry(obj)
/*
                if(registry.isEmpty()){
                    root = obj
                    addUiObjectToRegistry(obj)
                }
*/
                //set the root
                if(root == null)
                    root = obj

                return obj
           }

           return null  
       }

    //should not come here for Our DSL
       protected Object createNode(Object name, Object value) {

           return null
       }

       protected Object createNode(Object name, Map map) {
           def builder = builderRegistry.getBuilder(name)

           if(builder != null){
                def obj =  builder.build(map, null)
//                addUiObjectToRegistry(obj)
                //check if it is the root
/*
                if(registry.isEmpty()){
                    root = obj
                    addUiObjectToRegistry(obj)
                }
*/
                //set the root
                if(root == null)
                    root = obj

                return obj
           }   

           return null
       }

       protected Object createNode(Object name, Map map, Object value) {
           def builder = builderRegistry.getBuilder(name)

           if(builder != null){
                def obj =  builder.build(map, (Closure)value)
//                addUiObjectToRegistry(obj)
/*                if(registry.isEmpty()){
                    root = obj
                    addUiObjectToRegistry(obj)
                }
 */
               //set the root
               if(root == null)
                   root = obj

               return obj
           }   

          return null
       }

   }