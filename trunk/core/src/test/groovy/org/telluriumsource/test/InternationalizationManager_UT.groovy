package org.telluriumsource.test;

import java.sql.Date;


import org.telluriumsource.i18n.InternationalizationManager;
import org.telluriumsource.i18n.InternationalizationManagerImpl;


public class InternationalizationManager_UT extends GroovyTestCase {

	public void testTranslateWithEnglishLocale()
	{
		InternationalizationManager i18nManager = new InternationalizationManagerImpl()
		i18nManager.defaultLocale = new Locale("en" , "US")
		i18nManager.addResourceBundle("TestMessagesBundle")

		//translating of strings
		String messageFromResourceBundle = i18nManager.translate("i18nManager.testString")
		assertEquals("This is a testString in English", messageFromResourceBundle)
		
		//translation of number data types
		Double amount = new Double(345987.246);
		String translatedValue = i18nManager.translate(amount, false)
		assertEquals("345,987.246" , translatedValue)

		//translation of currency data types
		amount = new Double(9876543.21);
		translatedValue = i18nManager.translate(amount, true)
		assertEquals("\$9,876,543.21" , translatedValue)

		//translation of dates - date is 2009, Jan 1
		Date date = new Date(109 , 0 , 1)
		translatedValue = i18nManager.translate(date, false)
		assertEquals("Jan 1, 2009" , translatedValue)

		//now test functionality of adding another resource bundle
		i18nManager.addResourceBundle("AnotherTestMessagesBundle")
		messageFromResourceBundle = i18nManager.translate("i18nManager.anotherTestString")
		assertEquals("This is another testString in English", messageFromResourceBundle)

	}

	public void testTranslateWithFrenchLocale()
	{
		InternationalizationManager i18nManager = new InternationalizationManagerImpl()
		i18nManager.defaultLocale = new Locale("fr" , "FR")
		i18nManager.addResourceBundle("TestMessagesBundle")		
		
		//String messageFromResourceBundle = i18nManager.translate("i18nManager.testString")
		//assertEquals("c'est une corde d'essai en fran�ais", messageFromResourceBundle)

		//translation of number data types
		Double amount = new Double(21.26);
		String translatedValue = i18nManager.translate(amount, false)
		assertEquals("21,26" , translatedValue)

		//translation of dates - date is 2009, Jan 1
		Date date = new Date(109 , 0, 1)
		translatedValue = i18nManager.translate(date, false)
		assertEquals("1 janv. 2009" , translatedValue)
	}

}