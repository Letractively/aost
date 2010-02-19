package aost.dsl

import aost.object.*
import aost.builder.UiObjectBuilderRegistry

class UiDslParser extends BuilderSupport{

//       UiObject root
    
       def registry = [:]

       //this should return a singleton class with default builders populated
       def UiObjectBuilderRegistry builderRegistry = new UiObjectBuilderRegistry()

       protected String nestObjectName(UiObject obj){
          String id
          if(obj.parent != null && obj.parent instanceof Table){
              id =  Table.internalId(obj.uid)
          }else{
              id = obj.uid
          }

          def op = obj

          while(op.parent != null){
            op = op.parent
            if(op.parent != null && op.parent instanceof Table){
                id = Table.internalId(op.uid) + "." + id
            }else{
                id = op.uid + "." + id
            }            
           }

          return id
       }

/*       def findUiObjectFromRegistry(String id){

           if(id.startsWith("${root.uid}")){

             return registry.get(id)
           }else{
             String t = "${root.uid}.${id}"

             return registry.get(t)
           }
       }*/

       public UiObject walkTo(WorkflowContext context, String id)
       {
          //if only one ui object in the registry, i.e., user only defined one UI module
          //in this case, the top id can be omitted, here we need to put it back
          if(registry.size() == 1){
            UiObject topobj = registry.values().asList().get(0)
            if(!id.startsWith("${topobj.uid}")){
              id = "${topobj.uid}.${id}"
            }
          }

          UiID uiid = UiID.convertToUiID(id)

          if(uiid.size() > 1){
              String first = uiid.pop()
              //first object (i.e., the top object in a UI module) can be found from the registry
              UiObject fo = registry.get(first)
              if(fo != null){
                  return fo.walkTo(context, uiid)
              }else{
                  println("Error: cannot find the top object ${first}")
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
//           if(root.uid.equals(parent.uid))
//               addUiObjectToRegistry(child)
       }

       protected Object createNode(Object name) {
           def builder = builderRegistry.getBuilder(name)

           if(builder != null){
                def obj =  builder.build(null, null)

                //set the root
//                if(root == null)
//                    root = obj

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

                //set the root
//                if(root == null)
//                    root = obj

                return obj
           }   

           return null
       }

       protected Object createNode(Object name, Map map, Object value) {
           def builder = builderRegistry.getBuilder(name)

           if(builder != null){
                def obj =  builder.build(map, (Closure)value)

               //set the root
//               if(root == null)
//                   root = obj

               return obj
           }   

          return null
       }

       protected void nodeCompleted(Object parent, Object node) {
          //when the node is completed and its parent is null, it means this node is at the top level
          if(parent == null){
               UiObject uo = (UiObject)node
               //only put the top level nodes into the registry 
               registry.put(uo.uid, node)
          }

       }

   }