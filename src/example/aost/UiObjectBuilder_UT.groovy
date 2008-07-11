package example.aost
/**
 *
 *  Test all default UI object builder
 *
 *   @author Jian Fang (John.Jian.Fang@gmail.com)
 */
class UiObjectBuilder_UT extends GroovyTestCase{

    public void testBaseLocator(){
        SampleUI sample = new SampleUI()
        sample.defineUi()
        Map registry = sample.ui.registry
        assertNotNull(registry)
    }

    public void testCompositeLocator(){
        SampleUI sample = new SampleUI()
        sample.defineCompositeUi()
        Map registry = sample.ui.registry
        assertNotNull(registry)
    }
}