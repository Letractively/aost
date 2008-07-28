package org.aost.framework

/**
 * Make AostFramework a singleton
 *
 * User: Jian Fang (John.Jian.Fang@gmail.com)
 * Date: Jun 2, 2008
 */
class AostFrameworkMetaClass extends MetaClassImpl{

    private final static INSTANCE = new AostFramework()

    AostFrameworkMetaClass() { super(AostFramework) }

    def invokeConstructor(Object[] arguments) { return INSTANCE }
}