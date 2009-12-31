package org.telluriumsource.dsl

import org.telluriumsource.locator.CompositeLocator

/**
 * Relax detail for closest matching in Engine when do UI module locating and caching
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 31, 2009
 * 
 */

public class RelaxDetail {
    //which UID got relaxed, i.e., closest Match
    private String uid = null;
    //the clocator defintion for the UI object corresponding to the UID
    private CompositeLocator locator = null;

    //The actual html source of the closest match element
    private String html = null;
}