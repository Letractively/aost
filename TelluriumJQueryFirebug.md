

# Introduction #

From time to time, a lot of Tellurium users run into problems for their Tellurium tests and asked our Tellurium developers for help. The difficult thing is that our Tellurium developers do not have access to their their whole test code, let alone their applications. For most cases, we can only give some suggestions/hints on what the problems might be. As a result, a practical way to trace/debug Tellurium test code will benefit all our users. That is why I create this wiki page to share our experiences on how to debug Tellurium code with Firebug and jQuery.

Tellurium test mainly consists of two parts, i.e., the Java/Groovy test code and JavaScript code in our customized Selenium server. Tracing and debugging Java/Groovy code in IDE is very simple and thus we only focus on the custom Selenium server part here.

# Prerequisites #

## Firefox Profile ##

Usually, you will see a lot of posts about using the following command to manage Firefox profiles

```
Firefox -ProfileManager
```

Unfortunately, this seems not working for Firefox 3.5. (At least on my Linux box). I have to use the following command instead

```
firefox -ProfileManager -no-remote
```

Once you get there, you can create a new Firefox profile.

http://tellurium-users.googlegroups.com/web/FireFoxUserProfile.png?gda=I4FoeUgAAACvY3VTaWrtpkaxlyj9o09Emp7WzKfacwuA5qU_hvWhiTsYZlJux9GJX8kg1N3C8bklzhb83kORdLwM2moY-MeuGjVgdwNi-BwrUzBGT2hOzg&gsc=U1o_cQsAAAA1lkHi4n397EEqRcu0Nd-6

## Firebug Support ##

To add Firebug support, one way is to install the Firebug plugin to your web browser. You can get Firebug from

https://addons.mozilla.org/en-US/firefox/addon/1843

Then, use the Firefox profile in your Tellurium Tests. For example, you can add the Firefox profile in TelluriumConfig.groovy as follows,

```
tellurium{
    //embedded selenium server configuration
    embeddedserver {
        //port number
        port = "4444"
        //whether to use multiple windows
        useMultiWindows = false
        //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
        runInternally = true
        //profile location
        profile = "/home/jfang/.mozilla/firefox/zlduhghq.test"
        //user-extension.js file
        userExtension = "target/test-classes/extension/user-extensions.js"
    }
```

Or you can use the following command to specify the profile if you run the Selenium server externally,

```
[jfang@Mars ]$ java -jar selenium-server.jar -profilesLocation /home/jfang/.mozilla/firefox/zlduhghq.test
```

But sometimes, Selenium server has trouble to create a new profile from your profile and it might be better to add the Firebug plugin directly to the Selenium server. To do this, you need to following the following steps.

First, unpack the custom Selenium server

```
[jfang@Mars ]$ jar xvf selenium-server.jar
```

You will see all the files and directories listed as follows

```
[jfang@Mars Mars]$ ls -l
-rw-rw-r--. 1 jfang jfang    1677 2009-06-09 12:59 coding-conventions.txt
drwxrwxr-x. 6 jfang jfang    4096 2009-06-17 18:41 core
drwxrwxr-x. 3 jfang jfang    4096 2009-06-17 18:41 customProfileDirCUSTFF
drwxrwxr-x. 3 jfang jfang    4096 2009-08-14 16:58 customProfileDirCUSTFFCHROME
drwxrwxr-x. 3 jfang jfang    4096 2009-06-17 18:41 cybervillains
drwxrwxr-x. 2 jfang jfang    4096 2009-06-17 18:41 doctool
drwxrwxr-x. 2 jfang jfang    4096 2009-06-17 18:41 hudsuckr
drwxrwxr-x. 2 jfang jfang    4096 2009-06-17 18:41 images
-rw-rw-r--. 1 jfang jfang    1933 2009-06-09 12:59 index.html
-rw-rw-r--. 1 jfang jfang     620 2009-06-09 12:59 install-readme.txt
drwxrwxr-x. 3 jfang jfang    4096 2009-06-17 18:41 javax
drwxrwxr-x. 6 jfang jfang    4096 2009-06-17 18:41 jsunit
drwxrwxr-x. 2 jfang jfang    4096 2009-06-17 18:41 killableprocess
drwxrwxr-x. 2 jfang jfang    4096 2009-06-17 18:41 konqueror
drwxrwxr-x. 3 jfang jfang    4096 2009-06-17 18:41 META-INF
drwxrwxr-x. 2 jfang jfang    4096 2009-06-17 18:41 opera
drwxrwxr-x. 6 jfang jfang    4096 2009-06-17 18:41 org
-rw-rw-r--. 1 jfang jfang    2020 2009-06-09 12:59 readyState.xpi
-rw-rw-r--. 1 jfang jfang  129458 2009-06-09 12:59 reference.html
-rw-rw-r--. 1 jfang jfang      55 2009-06-12 15:12 selenium-ant.properties
drwxrwxr-x. 2 jfang jfang    4096 2009-06-17 18:41 sslSupport
drwxrwxr-x. 2 jfang jfang    4096 2009-06-17 18:41 strands
drwxrwxr-x. 5 jfang jfang    4096 2009-06-17 18:41 tests
drwxrwxr-x. 3 jfang jfang    4096 2009-06-17 18:41 unittest
-rw-rw-r--. 1 jfang jfang     153 2009-06-12 15:14 VERSION.txt
```

Then, copy your Firebug installed in your Firefox profile to the profiles in Selenium Server.

```
[jfang@Mars Mars]$ cp -rf /home/jfang/.mozilla/firefox/zlduhghq.test/extensions/firebug\@software.joehewitt.com customProfileDirCUSTFF/extensions/

[jfang@Mars Mars]$ cp -rf /home/jfang/.mozilla/firefox/zlduhghq.test/extensions/firebug\@software.joehewitt.com customProfileDirCUSTFFCHROME/extensions/
```

After that, re-pack the custom Selenium server

```
jar cmf META-INF/MANIFEST.MF selenium-server.jar *
```

Fortunately, you don't need to repeat the above step any more, we provide a custom Selenium server with Firebug support in our Maven repository. You should access it by using the following Maven dependency,

```
        <dependency>
            <groupId>org.openqa.selenium.server</groupId>
            <artifactId>selenium-server</artifactId>
            <version>1.0.1-tf</version>
          </dependency>
        </dependencies>
```

Of course, you need to specify our Maven repository in your settings.xml or your pom file.

```
        <repository>
            <id>kungfuters-thirdparty-releases-repo</id>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <url>http://maven.kungfuters.org/content/repositories/thirdparty</url>
        </repository>
```

# Debug and Trace #

## Debug JavaScript Using Firebug ##

First, you need to put a breakpoint in your Java/Groovy test code, for example, we put a breakpoint on the UserTestCase class,


http://tellurium-users.googlegroups.com/web/TelluriumUserTestCase.png?gda=aK5n30sAAACvY3VTaWrtpkaxlyj9o09Eze2zIhq6411I_8TGzz5DI-qW4SmLmVbvXBbEmeIsaNidYZYxwa0BE4XjboRU-oYaBkXa90K8pT5MNmkW1w_4BQ&gsc=EAymlwsAAABsf4AHuALmOmkLNHG2feRw

Then, use the "Debug" menu to start your test case. Once the test reaches breakpoint, you can go to the Firefox browser to open Firebug.

http://tellurium-users.googlegroups.com/web/TelluriumOpenFirebug.png?gda=ixZrO0oAAACvY3VTaWrtpkaxlyj9o09EDO6MGplfJGHVhUec0kpXPoiAcPDq6RaxFOV-k14EkLyB_EtY4PNGNRy1KtA3K9bL_e3Wg0GnqfdKOwDqUih1tA&gsc=hGoVDAsAAAB8vRO2zTlOouy3DcrgOZrb

Sometime, the Firebug console is disabled by default, you need to enable it. After that, you can select the JavaScript files including those from Selenium core from the Javascripts menu in Firebug.

http://tellurium-users.googlegroups.com/web/TelluriumFirebugScript.png?gda=4-oX70wAAACvY3VTaWrtpkaxlyj9o09E7NkZ8HCattGn-1iiIrkpLwyVYbbBb4wvSnrgfyj7FfY29mST-NKidxSrAhXCh25v_Vpvmo5s1aABVJRO3P3wLQ&gsc=hGoVDAsAAAB8vRO2zTlOouy3DcrgOZrb

You can set a breakpoint in the JavaScript file and resume the test until it hits the breakpoint in the JavaScript file. You can find more details on how to debug Javascript from [Firebug JavaScript debugging](http://getfirebug.com/js.html).

## Trace Problems Using jQuery ##

The custom Selenium server is bundled with jQuery 1.3.2 when we added support for jQuery selector in Tellurium. We yielded the "$" sign and also renamed _jQuery_ to _teJQuery_ to avoid conflicts with user's jQuery library.

To use jQuery, you need to use the single window module for the custom Selenium server, i.e., change settings in TelluriumConfig.groovy to

```
        //whether to use multiple windows
        useMultiWindows = false
```

If you run the Selenium server externally, you should use the following command to start it in a single window mode,

```
java -jar selenium-server.jar -singlewindow
```

Similarly, you need to set a breakpoint in your Java/Groovy test code so that you can work on the Firefox browser using Firebug when the test suspends.

If you open Firebug and look at the html content, you will see that your web application is actually running inside an IFrame in Selenium server shown as follows,

http://tellurium-users.googlegroups.com/web/TelluriumSeleniumServerIFrame.png?gda=PVoZHlMAAACvY3VTaWrtpkaxlyj9o09EgO6a7zKvb8te6WdvjGDohVyd-tnS0xlr1YxP__WLUaGnQLDSh5D6u11BZBWKcByUMrYifh3RmGHD4v9PaZfDexVi73jmlo822J6Z5KZsXFo&gsc=n_OfJwsAAACQg_IcbRBLWbV5y8FhKpga

To access elements in the IFrame using jQuery, you need to use the following trick

```
teJQuery("#selenium_myiframe").contents().find(YOUR_JQUERY)
```

For example, we use the following jQuery to check if a button is there

```
teJQuery("#selenium_myiframe").contents().find("input.btn")
```

We can also dump out the html source starting from the button's parent,

```

teJQuery("#selenium_myiframe").contents().find("input.btn").parent().html()
```

The output is shown as follows,

http://tellurium-users.googlegroups.com/web/TelluriumJQueryIframe.png?gda=W0CtWEsAAACvY3VTaWrtpkaxlyj9o09Ei2DGeSsGpuBT4mNQYpRc0uTSq-Pi7IIn7mvi-lgcznBE4WwyXqDrOCG6YsJPv3PQBkXa90K8pT5MNmkW1w_4BQ&gsc=n_OfJwsAAACQg_IcbRBLWbV5y8FhKpga

Thanks to Dominic. For multiple window mode, you can use the following way to find an element.

```
teJQuery(selenium.browserbot.getCurrentWindow().document).find("#username")
```

# Feedback #

Please post your questions, comments, and suggestions to [Tellurium User Group](http://groups.google.com/group/tellurium-users). Also, please see [How to contribute to Tellurium](http://code.google.com/p/aost/wiki/HowToContribute) if you want to contribute to Tellurium.

Thanks.

# Resources #

  * [Tellurium Online User Guide](http://code.google.com/p/aost/wiki/UserGuide)
  * [Tellurium User Guide PDF Version](http://telluriumdoc.googlecode.com/files/TelluriumUserGuide.Draft.pdf)
  * [Tellurium User Group](http://groups.google.com/group/tellurium-users)
  * [Firebug](http://getfirebug.com/)
  * [Firebug Javascript Debugging](http://getfirebug.com/js.html)
  * [jQuery](http://jquery.com/)
