package org.telluriumsource.crosscut.i18n

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat
import org.telluriumsource.framework.config.Configurable
import org.telluriumsource.annotation.Provider;

/**
 * ResourceBundle - provides internationalization support
 *
 * @author Ajay (ajay.ravichandran@gmail.com)
 *
 * Date: Sep 23, 2009
 *
 */
//@Provider(type=IResourceBundle.class)
class ResourceBundle implements IResourceBundle, Configurable
{
 	//define the default locale
	private Locale defaultLocale
	//map every locale to the ResourceBundle name
	private Set<String> bundleBaseNames = new HashSet<String>()

	//map every locale to a ResourceBundle defined for that locale
	private Map<Locale , Set<java.util.ResourceBundle>> bundles = new HashMap<Locale , Set<java.util.ResourceBundle>>()

	private boolean isInitialized = false;

	public ResourceBundle(){
		if(!isInitialized){
			defaultLocale = Locale.getDefault()
			addResourceBundle("DefaultMessagesBundle" , defaultLocale)
			isInitialized = true;
		}
	}

	public void addResourceBundle(String resourceBundleName, Locale specificLocale = null)
	{
		if(specificLocale == null)
			specificLocale = defaultLocale;
		Set<java.util.ResourceBundle> bundlesFromMap = bundles.get(specificLocale)

		if(bundlesFromMap == null)
			bundlesFromMap = new HashSet<java.util.ResourceBundle>()

		bundlesFromMap.add(java.util.ResourceBundle.getBundle(resourceBundleName , specificLocale))
		bundles.put(specificLocale, bundlesFromMap)

		bundleBaseNames.add(resourceBundleName)
	}

	public String getMessageFromBundle(Locale specificLocale , String messageKey )
	  {
		  String translatedMessage
			//check if the given locale has bundles defined
		  Set<java.util.ResourceBundle> bundleSet = bundles.get(specificLocale);
		  if (bundleSet == null)
		  {
		     bundleSet = new HashSet<java.util.ResourceBundle>();
		     for (String bundleNames: bundleBaseNames) {
		    	 bundleSet.add(java.util.ResourceBundle.getBundle(bundleNames , specificLocale))
		     }
		     bundles.put(specificLocale , bundleSet);
		  }

		  for (java.util.ResourceBundle bundle : bundleSet)
		  {
			  try
			  {
				  translatedMessage = bundle.getString(messageKey)
			  }
			  catch(MissingResourceException ex)
			  {
				  translatedMessage = null;
			  }
			  if(translatedMessage !=null ) break

		  }
		  return translatedMessage
	  }

	public Locale getLocale(){
		return defaultLocale
	}

    public void updateDefaultLocale(Locale locale){
        this.defaultLocale = locale;
    }

	//i18n for currency and numbers
	public String getCurrency(Double doubleValue){
		return getCurrency(doubleValue, getLocale())
	}

   	public String getNumber(Double doubleValue){
		return getNumber(doubleValue, getLocale())
	}

	public String getCurrency(Double doubleValue , Locale specificLocale){
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(specificLocale)
		return currencyFormatter.format(doubleValue)
	}

	public String getNumber(Double doubleValue , Locale specificLocale){
		NumberFormat numberFormatter = NumberFormat.getNumberInstance(specificLocale)
		return numberFormatter.format(doubleValue)
	}

	  //i18n for dates and time
	public String getDate(Date dateValue){
		return getDate(dateValue, getLocale())
	}

	public String getTime(Date timeValue){
		return getTime(timeValue, getLocale())
	}

	public String getDate(Date dateValue , Locale specificLocale){
		DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, specificLocale)
		return dateFormatter.format(dateValue)
	}

	public String getTime(Date timeValue , Locale specificLocale){
		DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, specificLocale)
		return timeFormatter.format(timeValue)
	}

	public String getMessage(String messageKey , Object... arguments){
		return getMessage(messageKey , getLocale() , arguments)
	}
	public String getMessage(String messageKey , Locale specificLocale , Object... arguments){
		MessageFormat formatter = new MessageFormat(messageKey, specificLocale)
		String translatedMessage = getMessageFromBundle(specificLocale , messageKey)
		if(translatedMessage == null)
		{
			throw new MissingResourceException("Can't find resource in any of the bundles "
	                + this.getClass().getName()
	                +", key "+messageKey,
	                this.getClass().getName(),
	                messageKey);
		}
		return formatter.format(translatedMessage , arguments);
	}


	public String getMessage(String messageKey){
		getMessage(messageKey , getLocale())
	}

	public String getMessage(String messageKey , Locale specificLocale){
		String translatedMessage = getMessageFromBundle(specificLocale , messageKey)
		if(translatedMessage == null)
		{
			throw new MissingResourceException("Can't find resource in any of the bundles "
                  + this.getClass().getName()
                  +", key "+messageKey,
                  this.getClass().getName(),
                  messageKey);
		}
		return translatedMessage
	}

}