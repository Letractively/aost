package org.tellurium.i18n
import org.tellurium.config.Configurable;

/**
 * InternationalizationManager - provides internationalization support
 *
 * @author Ajay (ajay.ravichandran@gmail.com)
 *
 * Date: Sep 23, 2009
 *
 */

interface InternationalizationManager extends Configurable
{
	public String translate(Double doubleValue, Boolean isCurrency)
    public String translate(Double doubleValue, Boolean isCurrency , Locale specificLocale)
	public String translate(Date dateValue , Boolean isTime)
	public String translate(Date dateValue , Boolean isTime , Locale specificLocale)
	public String translate(String messageKey , Object... arguments)
	public String translate(String messageKey , Locale specificLocale , Object... arguments)
	public String translate(String messageKey)
	public String translate(String messageKey , Locale specificLocale)
}