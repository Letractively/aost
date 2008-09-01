package aost.dsl

import aost.object.*
import aost.builder.ButtonBuilder
import aost.builder.ContainerBuilder
import aost.builder.InputBoxBuilder
import aost.builder.CheckBoxBuilder
import aost.builder.IconBuilder
import aost.builder.UrlLinkBuilder
import aost.builder.TextBoxBuilder
import aost.builder.SelectorBuilder
import aost.builder.UiObjectBuilderRegistry

class UiDslParser extends BuilderSupport{

       public static final String OBJECT_PREFIX = "uo_"

       def registry = [:]

       //this should return a singleton class with default builders populated
       def UiObjectBuilderRegistry builderRegistry = new UiObjectBuilderRegistry()

       def String getObjectName(String id){
          OBJECT_PREFIX + id
       }

       def findUiObjectFromRegistry(String id){
           registry.get(getObjectName(id))
       }

       def addUiObjectToRegistry(UiObject obj){
           registry.put(getObjectName(obj.id), obj)
       }

       protected void setParent(Object parent, Object child) {
           if(parent instanceof Container){
               parent.add(child)
           }
       }

       protected Object createNode(Object name) {
           def builder = builderRegistry.getBuilder(name)

           if(builder != null){
                def obj =  builder.build(null, null)
                addUiObjectToRegistry(obj)
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
                addUiObjectToRegistry(obj)
                return obj
           }   

           return null
       }

       protected Object createNode(Object name, Map map, Object value) {
           def builder = builderRegistry.getBuilder(name)

           if(builder != null){
                def obj =  builder.build(map, (Closure)value)
                addUiObjectToRegistry(obj)
                return obj
           }   

          return null
       }

   }