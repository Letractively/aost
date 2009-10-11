package org.tellurium.i18n

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.text.NumberFormatter;

import java.util.Set;

import java.util.ResourceBundle;


import org.tellurium.config.Configurable;

/**
 * InternationalizationManager - provides internationalization support
 *
 * @author Ajay (ajay.ravichandran@gmail.com)
 *
 * Date: Sep 23, 2009
 *
 */

class InternationalizationManager implements Configurable
{
  private Locale locale = new Locale("en", "US")
  private Set<ResourceBundle> bundleSet = new HashSet<ResourceBundle>()
  MessageFormat formatter = new MessageFormat("")

  public Locale getLocale()
  {
    return locale
  }

  public Set<ResourceBundle> getResourceBundleSet()
  {
	  if(this.bundleSet == null || this.bundleSet.size() == 0)
		  createDefaultResourceBundle(Locale.getDefault())
	  return this.bundleSet;
  }

  public void addResourceBundle(String bundleName)
  {
	  if(locale == null)
		  locale = Locale.getDefault()
	  this.bundleSet.add(ResourceBundle.getBundle(bundleName, locale))
  }

  //i18n for numbers and currencies
  public String translate(Double doubleValue, Boolean isCurrency)
  {
	  if(locale == null)
		  locale = Locale.getDefault()
	  if(isCurrency)
	  {
		  NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale)
		  return currencyFormatter.format(doubleValue)
	  }
	  else
	  {
		  NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale)
		  return numberFormatter.format(doubleValue)
	  }
  }

  //i18n for dates and time
  public String translate(Date dateValue , Boolean isTime)
  {
	  if(locale == null)
		  locale = Locale.getDefault()
		  if(isTime)
		  {
			  DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale)
			  return timeFormatter.format(dateValue)
		  }
		  else
		  {
			  DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, locale)
			  return dateFormatter.format(dateValue)
		  }
	  
  }

  public String translate(String messageKey , Object[] arguments)
  {
	  String translatedMessage = null
	  Set<ResourceBundle> bundleSet = getResourceBundleSet();
	  for (ResourceBundle bundle : bundleSet) {
		  translatedMessage = bundle.getString(messageKey)
		  if(translatedMessage !=null ) break
			  
	  }
	  formatter.applyPattern(translatedMessage);
	  return formatter.format(arguments);
  }

  public String translate(String messageKey)
  {
	  String translatedMessage = null
	  Set<ResourceBundle> bundleSet = getResourceBundleSet();
	  for (ResourceBundle bundle : bundleSet) {
		  translatedMessage = bundle.getString(messageKey)
		  if(translatedMessage !=null ) break
			  
	  }
	  return translatedMessage
  }


  public void setLocale(Locale locale)
  {
    this.locale = locale
  }

  public void createDefaultResourceBundle(Locale locale)
  {
    formatter.setLocale(locale);
    setLocale(locale)
    if(bundleSet == null)
    	bundleSet = new HashSet<ResourceBundle>()
    bundleSet.add(ResourceBundle.getBundle("DefaultMessagesBundle" , locale))
  }

}