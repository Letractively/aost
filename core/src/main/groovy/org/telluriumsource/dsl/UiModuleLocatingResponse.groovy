package org.telluriumsource.dsl
/**
 * The response object Passing back from Engine when do UI module locating and caching
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 31, 2009
 * 
 */

public class UiModuleLocatingResponse {
    //ID for the UI module
    private String id = null;

    //Successfully found or not
    private boolean found = false;

    //whether this the UI module used closest Match or not
    private boolean relaxed = false;

    //details for the relax, i.e., closest match
    private List<RelaxDetail> relaxDetails = null;
}