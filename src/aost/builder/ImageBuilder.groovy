package aost.builder

import aost.object.Image
import aost.locator.BaseLocator

/**
 *  Image builder
 * 
 *  @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 */
class ImageBuilder extends UiObjectBuilder{

    public build(Map map, Closure c) {
        def df = [:]
        df.put(TAG, Image.TAG)
        Image image = this.internBuild( new Image(), map, df)

        return image
    }
}