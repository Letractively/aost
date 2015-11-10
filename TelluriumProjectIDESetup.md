

## Set up the Tellurium Reference project tellurium-website with Eclipse ##

### Prerequisites ###
  * Download Eclipse from the following location.
```
      http://www.eclipse.org/
```

  * Download Eclipse Groovy Plug-in from the following location.
```
     http://dist.codehaus.org/groovy/distributions/update/
```

  * Download Eclipse Subversion client plugin, subclipse, from the following location.
```
    http://subclipse.tigris.org/update_1.4.x
```

### Checkout Project tellurium-website as an Eclipse Project ###
  * Launch Eclipse and point it to your workspace.
  * File > New > Other > SVN > Checkout projects from SVN

http://tellurium-users.googlegroups.com/web/eclipsetjj1.png?gda=GBMET0EAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFpgPruVTcYdmQoitNQUIKj9TCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Next > create a new repository location, type in

```
http://aost.googlecode.com/svn/trunk/
```

http://tellurium-users.googlegroups.com/web/eclipsetjj2.png?gda=8ixIE0EAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFuCVqkthRocdd89BERD_d71TCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Next > select Trunk/Reference-Projects/tellurium-website

http://tellurium-users.googlegroups.com/web/eclipsetjj3.png?gda=3jmR-EEAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFnZX4Gs5tf7s00egSi0tJ_9TCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Next > Check out as a project in the workspace

http://tellurium-users.googlegroups.com/web/eclipsetjj4.png?gda=4nBTt0EAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFrU0FOUc9vZoJjns4dLQ-3NTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Next, Project starts to check out

> http://tellurium-users.googlegroups.com/web/eclipsetjj5.png?gda=Z4dfHUEAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFvaAr9cVNNFjxABxlviK2d5TCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Check project settings for builders

http://tellurium-users.googlegroups.com/web/eclipsetjj6.png?gda=-tk2nUEAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFlvJRVWcg7O_gqh-xpXBQWNTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Check project build path > source

![http://tellurium-users.googlegroups.com/web/eclipsetjj7.png](http://tellurium-users.googlegroups.com/web/eclipsetjj7.png)

  * Check project build path > Libraries

![http://tellurium-users.googlegroups.com/web/eclipsetjj8.png](http://tellurium-users.googlegroups.com/web/eclipsetjj8.png)

  * Build project > Click on any Junit tests to run


Note, sometimes, eclipse complaints that it cannot find the Groovy class imported into the Java class, you need to build the project first. If the problem is still there, you need to go to project settings > Java build path > source. Change the default output folder to another directory and then change it back to "tellurium-website/bin-groovy" again. This problem should go away.

## Set up the Tellurium Reference project tellurium-website with NetBeans ##

### Prerequisites ###

  * Download NetBeans 6.5 Beta from the following location. Groovy support and Subversion client are bundled in.
```
      http://www.netbeans.org/community/releases/65/
```

### Checkout Project tellurium-website as an IntelliJ Project ###

  * Versioning > Subversion > Checkout

http://tellurium-users.googlegroups.com/web/netbeanstjj1.png?gda=y4FjUEIAAABBo5YKz4My4Er929brhwkDOfPH_-0lvp4HIrp0Q_XUijLSgcBP2H4YJK4DTUC04UZV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=NZ6-QgsAAACJSCtoe_Z9JxffJHfhUOy0

  * Add repository URL
```
  http://aost.googlecode.com/svn/trunk/
```

http://tellurium-users.googlegroups.com/web/netbeanstjj2.png?gda=_vK7TEIAAABBo5YKz4My4Er929brhwkDOfPH_-0lvp4HIrp0Q_XUitEZVpkz_84uYBDXtynCLvZV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=NZ6-QgsAAACJSCtoe_Z9JxffJHfhUOy0

  * Next > Browse trunk

http://tellurium-users.googlegroups.com/web/netbeanstjj3.png?gda=nTEV-kIAAABBo5YKz4My4Er929brhwkDOfPH_-0lvp4HIrp0Q_XUiqFVHQjDswjLI7eZs6zw0RpV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=NZ6-QgsAAACJSCtoe_Z9JxffJHfhUOy0

  * Select the trunk/reference-projects/tellurium-website sub-project

http://tellurium-users.googlegroups.com/web/netbeanstjj4.png?gda=y4BrZ0IAAABBo5YKz4My4Er929brhwkDOfPH_-0lvp4HIrp0Q_XUilBOURjdvkzg1aJsrTazqCBV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=NZ6-QgsAAACJSCtoe_Z9JxffJHfhUOy0

  * Ok > Finish

http://tellurium-users.googlegroups.com/web/netbeanstjj5.png?gda=_62SGkIAAABBo5YKz4My4Er929brhwkDOfPH_-0lvp4HIrp0Q_XUiqfx94cQP-6pU61jlld0TsBV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=NZ6-QgsAAACJSCtoe_Z9JxffJHfhUOy0

  * After project is checked out, you will be asked to open the project. Select "Open Project"

http://tellurium-users.googlegroups.com/web/netbeanstjj6.png?gda=RimZWEIAAABBo5YKz4My4Er929brhwkDOfPH_-0lvp4HIrp0Q_XUigfccOaR-pDC_9tRWiyaN7RV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=NZ6-QgsAAACJSCtoe_Z9JxffJHfhUOy0

  * Check project settings for sources

http://tellurium-users.googlegroups.com/web/netbeanstjj7.png?gda=PJXoEkIAAABBo5YKz4My4Er929brhwkDOfPH_-0lvp4HIrp0Q_XUilvcmX6r4K3faJxWRTtONCdV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=NZ6-QgsAAACJSCtoe_Z9JxffJHfhUOy0

  * Check project settings for libraries

http://tellurium-users.googlegroups.com/web/netbeanstjj8.png?gda=-bMHzUIAAABBo5YKz4My4Er929brhwkDOfPH_-0lvp4HIrp0Q_XUilcFHX0URf1Ol11ODx04gy1V4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=NZ6-QgsAAACJSCtoe_Z9JxffJHfhUOy0

  * Build project and then select any JUnit file to run

http://tellurium-users.googlegroups.com/web/netbeanstjj9.png?gda=tYAk-UIAAABBo5YKz4My4Er929brhwkDOfPH_-0lvp4HIrp0Q_XUitdE2Ef6u0dawf8H3GUFxU5V4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=NZ6-QgsAAACJSCtoe_Z9JxffJHfhUOy0

Note, if you have some problems to import Groovy classes into Java classes. Please remove the libraries and then re-import them.

## Set up the Tellurium Reference project tellurium-website with IntelliJ ##

### Prerequisites ###

  * Download IntelliJ from the following location.
```
      http://www.jetbrains.com/idea/download/index.html
```

  * Download Groovy from the following location and unpack to your system.
```
     http://groovy.codehaus.org/Download
```

  * Download and install Groovy plugin JetGroovy for IntelliJ using IntelliJ Plugin management GUI.

http://tellurium-users.googlegroups.com/web/JetGroovyPluginInstall.png?gda=dg_HwkwAAABBo5YKz4My4Er929brhwkD2ucJQACD2wYtaZWgGCc7zIEl67BXdTEo51dgWIN_GgVLzz4t2ricMlsfMJNt13gv_Vpvmo5s1aABVJRO3P3wLQ&gsc=CrhfYQsAAADkz0nf0JiQ0myB9uM55KVK

### Checkout Project tellurium-website as an IntelliJ Project ###

  * Version Control > Checkout from Version Control > Subversion

![http://tellurium-users.googlegroups.com/web/intellijtjj1.png](http://tellurium-users.googlegroups.com/web/intellijtjj1.png)

  * Add subversion location for
```
  http://aost.googlecode.com/svn/trunk/
```

![http://tellurium-users.googlegroups.com/web/intellijtjj2.png](http://tellurium-users.googlegroups.com/web/intellijtjj2.png)

  * Select trunk/reference-projects/tellurium-website sub-project

![http://tellurium-users.googlegroups.com/web/intellijtjj3.png](http://tellurium-users.googlegroups.com/web/intellijtjj3.png)

  * Create a tellurium-website directory and select it for this project

![http://tellurium-users.googlegroups.com/web/intellijtjj4.png](http://tellurium-users.googlegroups.com/web/intellijtjj4.png)

  * Click "Checkout" and see the checkout options

![http://tellurium-users.googlegroups.com/web/intellijtjj5.png](http://tellurium-users.googlegroups.com/web/intellijtjj5.png)

  * Click "Ok" and the project starts to check out

  * After you are done with the checkout, you will be asked if you want to open the project. Click "Yes" to open it.

![http://tellurium-users.googlegroups.com/web/intellijtjj7.png](http://tellurium-users.googlegroups.com/web/intellijtjj7.png)

  * Open module settings > Project settings > Modules > Groovy, set your Groovy path.

![http://tellurium-users.googlegroups.com/web/intellijtjj8.png](http://tellurium-users.googlegroups.com/web/intellijtjj8.png)

  * On the same UI, open the dependencies for module tellurium-website, uncheck "tellurium-core" until you also check out tellurium-core. Add your project library, i.e., the lib directory in, which includes the tellurium jar file. Click "Apply" and then "Ok".

![http://tellurium-users.googlegroups.com/web/intellijtjj9.png](http://tellurium-users.googlegroups.com/web/intellijtjj9.png)

  * Click on any Junit test file and run it.

![http://tellurium-users.googlegroups.com/web/intellijtjj10.png](http://tellurium-users.googlegroups.com/web/intellijtjj10.png)