package org.tellurium.locator

class PlaceHolderLocateStrategy {

    public static final String PLACE_HOLDER = "\\?";

    def static boolean canHandle(locator){
       if(locator instanceof PlaceHolderLocator)
        return true
       else
        return false
    }

    def static String locate(PlaceHolderLocator locator){
        if(locator == null)
            throw new RuntimeException("Invalid null locator")
        def template = locator.template
        def attributes = locator.attributes

        if(template == null || attributes == null)
			return template;

		String loc = new String(template);
		for(String attribute : attributes){

			if(attribute != null){
				loc = loc.replaceFirst(PLACE_HOLDER, attribute);
			}else{
				throw new RuntimeException("Invalid null attribute\n");
			}
		}

		return loc;
    }
}