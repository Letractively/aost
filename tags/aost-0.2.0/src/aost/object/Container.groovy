package aost.object

class Container extends UiObject {

    //since we use map, the component name must be unique
    def components = [:]

    def add(UiObject component){
        components.put(component.id, component)
    }

    def getComponent(String id){
        components.get(id)
    }
}