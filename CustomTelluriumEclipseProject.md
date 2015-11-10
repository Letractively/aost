# How to create your own Tellurium testing project with Eclipse #

## Prerequisites ##
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

  * Download the Tellurium tellurium-0.4.0-u01.jar file from the following location, which is for Java 6. If you use Java 5, please download the tellurium-0.4.0-java5.jar file.
```
    http://code.google.com/p/aost/downloads/list
```

## Create Eclipse Project ##
  * Launch Eclipse. The workspace in the example is set to C:\Workspace. (You can define any directory based on your preference.)
  * File > New > Java Project
  * Set the project name as GoogleSearch.

> http://tellurium-users.googlegroups.com/web/telluriumeclipse1.png?gda=OC8GtkcAAABBo5YKz4My4Er929brhwkDIyoOIQtWQ7BImQDx27OOk-nGk5etqh-w_MeV1tM8ksoVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=_R8KTgsAAABRJG7IT_Cc2wnbkeju_NaW


  * Click Next.
  * Click Libraries.
  * Click the Add External Jars

http://tellurium-users.googlegroups.com/web/telluriumeclipse2.png?gda=HeVZBUcAAABBo5YKz4My4Er929brhwkDIyoOIQtWQ7BImQDx27OOkx6Rgebp9ug94aepc_IiRDkVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=_R8KTgsAAABRJG7IT_Cc2wnbkeju_NaW


  * Click Finish.
  * Right Click on the project GoogleSearch > Groovy > Add Groovy Nature

http://tellurium-users.googlegroups.com/web/telluriumeclipse3.png?gda=OhhakkcAAABBo5YKz4My4Er929brhwkDIyoOIQtWQ7BImQDx27OOkz8IenHyOGEnFssS9OXVZIMVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=_R8KTgsAAABRJG7IT_Cc2wnbkeju_NaW


  * Bring up the Project Properties.
  * Click on Java Build Path > Source.
  * Change the GoogleSearch/binto  GoogleSearch/bin-groovy.

http://tellurium-users.googlegroups.com/web/telluriumeclipse4.png?gda=h_xHUkcAAABBo5YKz4My4Er929brhwkDIyoOIQtWQ7BImQDx27OOk_Qbz-_w4aZ6WfFleMC_bZkVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=_R8KTgsAAABRJG7IT_Cc2wnbkeju_NaW


  * Click OK.
  * Create a new Groovy class (NewGoogleStartPage) file.

http://tellurium-users.googlegroups.com/web/telluriumeclipse5.png?gda=WM6XbEcAAABBo5YKz4My4Er929brhwkDIyoOIQtWQ7BImQDx27OOk_IrSwXIi02DGQiThA-u8Q4VeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=_R8KTgsAAABRJG7IT_Cc2wnbkeju_NaW


  * Create a new Java class file GoogleStartPageJavaTestCase.

http://tellurium-users.googlegroups.com/web/telluriumeclipse6.png?gda=VDs5yUcAAABBo5YKz4My4Er929brhwkDIyoOIQtWQ7BImQDx27OOk5Yz0AYjP2l37QjvA11JXRwVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=_R8KTgsAAABRJG7IT_Cc2wnbkeju_NaW


  * Create a TelluriumConfig.groovy. Place this file C:\Workspace\GoogleSearch

http://tellurium-users.googlegroups.com/web/telluriumeclipse7.png?gda=0x8jzEcAAABBo5YKz4My4Er929brhwkDIyoOIQtWQ7BImQDx27OOk79EAx3ompzid0GV2gdiN7YVeY4b49xGcMK802iZZ8SFeV4duv6pDMGhhhZdjQlNAw&gsc=_R8KTgsAAABRJG7IT_Cc2wnbkeju_NaW


  * Run the file GoogleStartPageJavaTestCase. The test will result as passed.