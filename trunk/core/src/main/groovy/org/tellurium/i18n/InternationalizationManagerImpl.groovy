package org.telluriumsource.i18n

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;

/**
 * InternationalizationManager - provides internationalization support
 *
 * @author Ajay (ajay.ravichandran@gmail.com)
 *
 * Date: Sep 23, 2009
 *
 */

class InternationalizationManagerImpl implements InternationalizationManager
{
	//define the default locale
	private Locale defaultLocale

	//map every locale to the ResourceBundle name
	private Set<String> bundleBaseNames

	//map every locale to a ResourceBundle defined for that locale
	private Map<Locale , Set<ResourceBundle>> bundles

	  public InternationalizationManagerImpl()
	  {
			bundles = new HashMap<Locale , Set<ResourceBundle>>()
			bundleBaseNames = new HashSet<String>()
			defaultLocale = Locale.getDefault()
			
			//the first time we enter the constructor, always create a DefaultResourceBundle
			addResourceBundle("DefaultMessagesBundle" , defaultLocale)
	  }
	public addResourceBundle(String resourceBundleName , Locale specificLocale = null)
	{
		if(specificLocale == null)
			specificLocale = defaultLocale;
		Set<ResourceBundle> bundlesFromMap = bundles.get(specificLocale)

		if(bundlesFromMap == null)
			bundlesFromMap = new HashSet<ResourceBundle>()
			
		bundlesFromMap.add(ResourceBundle.getBundle(resourceBundleName , specificLocale))
		bundles.put(specificLocale, bundlesFromMap)
		
		bundleBaseNames.add(resourceBundleName)
	}

  //i18n for numbers and currencies
  public String translate(Double doubleValue, Boolean isCurrency)
  {
	  return translate(doubleValue, isCurrency , defaultLocale)
  }
  public String translate(Double doubleValue, Boolean isCurrency , Locale specificLocale)
  {
	  if(isCurrency)
	  {
		  NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(specificLocale)
		  return currencyFormatter.format(doubleValue)
	  }
	  else
	  {
		  NumberFormat numberFormatter = NumberFormat.getNumberInstance(specificLocale)
		  return numberFormatter.format(doubleValue)
	  }
  }

  //i18n for dates and time
  public String translate(Date dateValue , Boolean isTime)
  {
	  return translate(dateValue , isTime , defaultLocale)
  }
  public String translate(Date dateValue , Boolean isTime , Locale specificLocale)
  {
	  if(isTime)
	  {
		  DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, specificLocale)
		  return timeFormatter.format(dateValue)
	  }
	  else
	  {
		  DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, specificLocale)
		  return dateFormatter.format(dateValue)
	  }
	  
  }

  public String translate(String messageKey , Object... arguments)
  {
	  return translate(messageKey , defaultLocale , arguments)
  }
  public String translate(String messageKey , Locale specificLocale , Object... arguments)
  {
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

  private String getMessageFromBundle(Locale specificLocale , String messageKey )
  {
	  String translatedMessage
		//check if the given locale has bundles defined
	  Set<ResourceBundle> bundleSet = bundles.get(specificLocale);
	  if (bundleSet == null)
	  {
	     bundleSet = new HashSet<ResourceBundle>();
	     for (String bundleNames: bundleBaseNames) {
	    	 bundleSet.add(ResourceBundle.getBundle(bundleNames , specificLocale))
	     }
	     bundles.put(specificLocale , bundleSet);
	  }

	  for (ResourceBundle bundle : bundleSet) 
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

  public String translate(String messageKey)
  {
	translate(messageKey , defaultLocale)  
  }
  public String translate(String messageKey , Locale specificLocale)
  {
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