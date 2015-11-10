# How to create your own Tellurium testing project with NetBeans 6.5(Beta) #


### Prerequisites ###

  * Download NetBeans 6.5 Beta from the following location. Groovy support is bundled in.
```
      http://www.netbeans.org/community/releases/65/
```

  * Launch Netbeans.
  * File > New Project.

http://tellurium-users.googlegroups.com/web/NetBeansCustom001.png?gsc=dG0gBwsAAADMr3HQF7PJ8hAA6M_sWiLZ

  * Java > Java Application > Next.

http://tellurium-users.googlegroups.com/web/NetBeansCustom002.png?gsc=dG0gBwsAAADMr3HQF7PJ8hAA6M_sWiLZ

  * Set the project details as shown below.
  * Click Finish

http://tellurium-users.googlegroups.com/web/NetBeansCustom003.png?gsc=dG0gBwsAAADMr3HQF7PJ8hAA6M_sWiLZ

  * Project Properties > Library > Add Jar/Folder. (Add the jar files for seleinium java client, selenium server, junit and tellurium.)
  * Click OK

http://tellurium-users.googlegroups.com/web/NetBeansCustom004.png?gsc=dG0gBwsAAADMr3HQF7PJ8hAA6M_sWiLZ

  * Create a Groovy class file (GoogleSearch) in the Source Package as shown below.

http://tellurium-users.googlegroups.com/web/NetBeansCustom005.png?gsc=dG0gBwsAAADMr3HQF7PJ8hAA6M_sWiLZ

http://tellurium-users.googlegroups.com/web/NetBeansCustom006.png?gsc=dG0gBwsAAADMr3HQF7PJ8hAA6M_sWiLZ

http://tellurium-users.googlegroups.com/web/NetBeansCustom007.png?gsc=dG0gBwsAAADMr3HQF7PJ8hAA6M_sWiLZ

  * Create a Java file (GoogleStartPageJavaTestCase) in the Test Package as shown below.

http://tellurium-users.googlegroups.com/web/NetBeansCustom008.png?gsc=dG0gBwsAAADMr3HQF7PJ8hAA6M_sWiLZ

http://tellurium-users.googlegroups.com/web/NetBeansCustom009%20%282%29.png?gsc=6OW5IwsAAAB4k6RHZOaL-6yTLPT2ISsG

  * Create TelluriumConfig.groovy file and place it under C:\workspace\GoogleSearch.

  * Right Click on GoogleStartPageJavaTestCase > Run File.

http://tellurium-users.googlegroups.com/web/NetBeansCustom010.png?gsc=6OW5IwsAAAB4k6RHZOaL-6yTLPT2ISsG

  * The Tellurium test will run and results in pass.

http://tellurium-users.googlegroups.com/web/NetBeansCustom011.png?gsc=6OW5IwsAAAB4k6RHZOaL-6yTLPT2ISsG




