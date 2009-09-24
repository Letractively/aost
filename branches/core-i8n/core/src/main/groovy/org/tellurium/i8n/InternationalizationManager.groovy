package org.tellurium.i8n

import java.text.MessageFormat
import org.tellurium.config.Configurable

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
  private ResourceBundle resourceBundle;
  MessageFormat formatter = new MessageFormat("");

  public Locale getLocale()
  {
    return locale
  }

  public String translate(String messageKey , Object[] arguments)
  {
    formatter.applyPattern(getResourceBundle().getString(messageKey));
    return formatter.format(arguments);
  }

  public String translate(String key)
  {
    getResourceBundle().getString(key);
  }


  public void setLocale(Locale locale)
  {
    this.locale = locale
  }

  public void createResourceBundle(Locale locale)
  {
    formatter.setLocale(locale);
    setLocale(locale)
    resourceBundle =  ResourceBundle.getBundle("MessagesBundle" , locale);
  }

  public ResourceBundle getResourceBundle()
  {
    return resourceBundle;
  }

}