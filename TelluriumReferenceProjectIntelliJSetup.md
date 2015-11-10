## Set up the Tellurium Reference project tellurium-junit-java with IntelliJ ##

### Prerequisites ###

  * Download IntelliJ 7.0 from the following location.
```
      http://www.jetbrains.com/idea/download/index.html
```

  * Download Groovy 1.5.6 from the following location and unpack to your system.
```
     http://groovy.codehaus.org/Download
```

  * Download and install Groovy plugin JetGroovy for IntelliJ using IntelliJ Plugin management GUI.

http://tellurium-users.googlegroups.com/web/JetGroovyPluginInstall.png?gda=dg_HwkwAAABBo5YKz4My4Er929brhwkD2ucJQACD2wYtaZWgGCc7zIEl67BXdTEo51dgWIN_GgVLzz4t2ricMlsfMJNt13gv_Vpvmo5s1aABVJRO3P3wLQ&gsc=CrhfYQsAAADkz0nf0JiQ0myB9uM55KVK

### Checkout Project tellurium-junit-java as an IntelliJ Project ###
  * Version Control > Checkout from Version Control > Subversion

![http://tellurium-users.googlegroups.com/web/intellijtjj1.png](http://tellurium-users.googlegroups.com/web/intellijtjj1.png)

  * Add subversion location for
```
  http://aost.googlecode.com/svn/trunk/
```

![http://tellurium-users.googlegroups.com/web/intellijtjj2.png](http://tellurium-users.googlegroups.com/web/intellijtjj2.png)

  * Select trunk/reference-prjects/tellurium-junit-java sub-project

![http://tellurium-users.googlegroups.com/web/intellijtjj3.png](http://tellurium-users.googlegroups.com/web/intellijtjj3.png)

  * Create a tellurium-junit-java directory and select it for this project

![http://tellurium-users.googlegroups.com/web/intellijtjj4.png](http://tellurium-users.googlegroups.com/web/intellijtjj4.png)

  * Click "Checkout" and see the checkout options

![http://tellurium-users.googlegroups.com/web/intellijtjj5.png](http://tellurium-users.googlegroups.com/web/intellijtjj5.png)

  * Click "Ok" and the project starts to check out

![http://tellurium-users.googlegroups.com/web/intellijtjj6.png](http://tellurium-users.googlegroups.com/web/intellijtjj6.png)

  * After you are done with the checkout, you will be asked if you want to open the project. Click "Yes" to open it.

![http://tellurium-users.googlegroups.com/web/intellijtjj7.png](http://tellurium-users.googlegroups.com/web/intellijtjj7.png)

  * Open module settings > Project settings > Modules > Groovy, set your Groovy path.

![http://tellurium-users.googlegroups.com/web/intellijtjj8.png](http://tellurium-users.googlegroups.com/web/intellijtjj8.png)

  * On the same UI, open the dependencies for module tellurium-junit-java, uncheck "tellurium-core" until you also check out tellurium-core. Add your project library, i.e., the lib directory in, which includes the tellurium jar file. Click "Apply" and then "Ok".

![http://tellurium-users.googlegroups.com/web/intellijtjj9.png](http://tellurium-users.googlegroups.com/web/intellijtjj9.png)

  * Click on any Junit test file and run it.

![http://tellurium-users.googlegroups.com/web/intellijtjj10.png](http://tellurium-users.googlegroups.com/web/intellijtjj10.png)