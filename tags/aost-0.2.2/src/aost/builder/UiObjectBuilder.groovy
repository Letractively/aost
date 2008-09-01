package aost.builder

import aost.object.UiObject

abstract class UiObjectBuilder{
    def static final String ID = "id"
    def static final String NAMESPACE = "namespace"
    def static final String LOCATOR = "locator"

    def static Closure baseClosure = { UiObject obj, Map map ->
        obj.id = map.get(ID)
        obj.namespace = map.get(NAMESPACE)
    }

    def abstract build(Map map, Closure c);
}