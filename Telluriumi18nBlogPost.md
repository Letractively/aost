# Introduction #

There are more than 6,000 languages in use around the world today. So imagine the vast array of characters your application is introduced to. Any software product that involves users across the globe, has a very important requirement - the ability of users to interact with it in their own specific language, locale or cultural conventions. There are two main concepts that facilitate a software for international use

  * Internationalization - The software is designed such that it can be used in multiple geographic locations. This involves requirements such as requiring all messages and constants to be stored externally and not hard coded. Date, time or currency should not be assumed to have a certain convention but instead should be generic and manageable.

  * Localization - This is the process of adapting internationalized software to the user needs in a particular region or locale. This includes ability to translate.

The following cultural conventions impact internationalization (i18n for short).
  * language and punctuations
  * date and time
  * numbers
  * currency

# ASCII and beyond #

Character handling is also a very import aspect of i18n. The first step here is to be able to encode or represent alphabets. Initially when ASCII was introduced it could be stored in 7 bits and was used to represent a character using a number between 32 and 127. This was great until the world became a smaller place to live in, and new language started to be used in the world of computer science. This is where unicode came into picture and was an effort to create a character set to be able to handle every character on the planet. It is a 16 bit character set. In unicode every letter has what is called a codepoint as this : U+<hex number>. This Unicode had to be stored, thus introducing the concept of encoding. UTF-8 provides a way to store the codepoints in 8 bit bytes. Anyways the story of Unicode is our cup of tea for another day.

# Designing Tellurium with Internationalization in mind #

Now let me get back to the point, One of the key features of 0.7.0 version of Tellurium is it's ability to be used effectively in multiple locales. Tellurium provides a very effective internationalization functionality.

First let us look at the key Java i18n modules that we use to implement Internationalization.

http://tellurium-users.googlegroups.com/web/i18nArchitecture-1.jpg?gsc=HIQSyAsAAAASDyr15e1v8GfwarRf2LYO

Every Locale is represented by a String language code and String country code. ResourceBundles have key value combination with the key representing a bundle name and the value is the particular ResourceBundle.  NumberFormat, MessageFormat and DateFormat provides internationalization support for numbers,  messages and dates respectively.

Tellurium uses the following design strategy to provide internationalization

http://tellurium-users.googlegroups.com/web/i18nArchitecture-2.jpg?gda=J8dFI0gAAAA7fMi2EBxrNTLhqoq3FzProNCd84e40kso0FvwiWFz6y3bdMhJq4RdCxxYnBsE9DQl7L0n4r1OlWc0FWonxWPiGjVgdwNi-BwrUzBGT2hOzg&gsc=HIQSyAsAAAASDyr15e1v8GfwarRf2LYO

  * ResourceBundleSource handles resource bundles that are defined as properties files. Every Locale has an associated properties file, like for ex: DefaultMessagesBundle\_en\_US. All messages that need to be internationalized are defined separately in the properties file.  The ResourceBundleSource maintains a map of bundles to locales.

  * ResourceBundle handles internationalization for all other data types in addition to the message  management provided by ResourceBundleSource.

  * IResourceBundle provides the polymorphism required to provide access to resource bundles.

These provide a very effective way to run tests all within the confines of one's own locale, it cannot get much simpler than that. Another great aspect of internationalization in tellurium is that users can extend ResourceBundle and provide their own default implementation very easily.

If you want to know more about usages of i18n Functionality in Tellurium check out the latest user guide at

[Tellurium 0.7.0 Reference](http://code.google.com/p/aost/downloads/detail?name=tellurium-reference-0.7.0.pdf&can=2&q=)

# Resources #

  * [Tellurium Project Home](http://code.google.com/p/aost/)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Tellurium Developer Group](http://groups.google.com/group/tellurium-developers)
  * [Tellurium on Twitter](http://twitter.com/TelluriumSource)
  * [TelluriumSource](http://telluriumsource.org)
  * [Tellurium 0.7.0](http://code.google.com/p/aost/wiki/Tellurium070Released)
  * [Tellurium Reference Documentation](http://aost.googlecode.com/files/tellurium-reference-0.7.0.pdf)