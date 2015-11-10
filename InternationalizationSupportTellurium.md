
## Tellurium Tutorial Series: Internationalization support in Tellurium ##

Tellurium now has support for internationalization and localization.

## Prerequisites ##

  * [Tellurium Core 0.7.0-SNAPSHOT](http://maven.kungfuters.org/content/repositories/snapshots/tellurium/tellurium-core/0.7.0-SNAPSHOT/)
  * Java 6

## Introduction ##

Tellurium now provides support for internationalization of strings and exception messages. Any software system should have support for regional language settings and options to be effective. Internationalization and localization provides this support. Locales define the language and region. Locales can define how region specific data is presented to users. Every locale will have a language code followed by a region code. Ex: fr\_FR represents french language in the region of France. Internationalized strings for each locale is provided through a MessageBundle engineered for a specific locale which is of the format `<MessageBundleName>_<language-code>_<country code>.properties`

### Internationalization support in Tellurium ###

The Internationalization support in Tellurium is provided through the InternationalizationManager class. The default bundle used in Tellurium is the DefaultMessagesBundle.properties. All strings and exception messages used in the tellurium core classes are read in from the DefaultMessageBundle properties file.

In order to configure regional messages, This class has a getMessage function that provides Internationalization support. This function can also take an optional Locale argument to accept function level locale translations.


For plain strings
```
getMessage ( "<key>") 
```
For Strings with parameters
```
getMessage ("<key>" , { [ item1 , item2 , … , item n]}
```
For double numeric value
```
getNumber(<doubleValue>)
```
For currency data
```
getCurrency(<doubleValue>)
```
For Dates
```
getDate(<dateValue>)
```
For time
```
getTime(<timeValue>)
```

The `getMessage (<key>)` method signature internationalizes a simple string. The `getMessage (<key> , { [ item1 , item2 , … , item n]}` method definition allows parameterization of an internationalized string to allow external strings/arguments as parameter to the string. This also takes locale as an argument, so we have `getMessage (<key> , locale)` to allow translation of the string to the locale passed in as an argument, provided the key value pair exists in the respective locale property file

The localization can be defined by setting the locale on your system preferences / settings. (ex: regional settings in Windows machine).

**Note:** By default using getMessage() without any locale argument causes the system to use the default locale as defined in ur regional settings. In order to use a locale different from the regional settings, you will have to pass in the locale as an argument to getMessage.

### Internationalization extension to user defined tests ###

Internationalization support has been extended to test cases, so any user defined test case can use
```
 geti18nBundle() 
```

to utilize the getMessage function support in their own test code. Internationalized strings can be added to user defined MessageBundles defined in the src/main/resources folder of user defined projects. The general steps to provide internationalization in your project are as follows:

1. Create a user defined MessageBundle.properties, a default locale message bundle, as well as one for each region you want to provide support for in your project, ex: MessageBundle\_fr\_FR.properties will have strings translated into french

2. Add the user defined resource bundle using the geti18nManager function, like so: `getI18nBundle().addResourceBundle("MessageBundle")`. This can take an optional locale to add the resource bundle in that locale

3. Now use the `getMessage` function to internationalize strings

## Simple Example ##

Here is a simple example of code from a GoogleBooksListGroovyTestCase. I assume that user has already defined a MessagesBundle.properties,located at src/main/resources, as follows

MessagesBundle.properties
```
GoogleBooksListGroovyTestCase.SetUpModule=Setting up google book list
GoogleBooksListGroovyTestCase.Category=Category is {0}
GoogleBooksListGroovyTestCase.ConnectSeleniumServer=Connection to selenium server
```

Now defining the same properties file in French

MessageBundle\_fr\_FR.properties
```
GoogleBooksListGroovyTestCase.SetUpModule=Liste de livre de google d'établissement
GoogleBooksListGroovyTestCase.Category=La catégorie est {0}
GoogleBooksListGroovyTestCase.ConnectSeleniumServer=Se relier au serveur de sélénium
```

Here is the definition of a testCase that uses the Internationalization support
```
class SampleGroovyTestCase extends TelluriumGroovyTestCase {

    public void initUi() {
    }

    public void setUp(){
        setUpForClass()
        //adding the local resource bundle, make sure it's not titled "DefaultMessagesBundle" since
        //this will overwrite the default one we use in Tellurium core and cause exceptions
        geti18nBundle().addResourceBundle("MessagesBundle")
        
        //geti18nBundle() can also be replaced by 
        //IResourceBundle bundle = new ResourceBundle(), 

    }

    public void tearDown(){
        tearDownForClass()
    }

    public void testTranslateWithEnglishLocale()
    {
       //translating of strings
       String message = geti18nBundle().getMessage("i18nManager.testString")
       assertEquals("This is a testString in English", message)

       //translation of number data types
       Double amount = new Double(345987.246);
       String translatedValue = geti18nBundle().getNumber(amount)
       assertEquals("345,987.246" , translatedValue)

       //translation of currency data types
       amount = new Double(9876543.21);
       translatedValue = geti18nBundle().getCurrency(amount)
       assertEquals("\$9,876,543.21" , translatedValue)

       //translation of dates - date is 2009, Jan 1
       Date date = new Date(109 , 0 , 1)
       translatedValue = geti18nBundle().getDate(date)
       assertEquals("Jan 1, 2009" , translatedValue)
     }
}
```

## Future Work ##

We expect to provide internationalization support for Selenium messages on the console in the future.