package org.tellurium.builder

import org.tellurium.object.Image
import org.tellurium.locator.BaseLocator

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