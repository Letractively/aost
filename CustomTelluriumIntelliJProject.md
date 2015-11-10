

# How to create your own Tellurium testing project with IntelliJ 9.0 Community Edition #

## Prerequisites ##

  * Download IntelliJ IDEA Community edition. Its free and its got the best Groovy support.
```
   http://www.jetbrains.com/idea/download/
```
  * Download Groovy 1.6.x from the following location and unpack to your system.
```
     http://groovy.codehaus.org/Download
```
  * Create a **lib** folder to include the dependent jar files
  * Download the **tellurium-core.jar** file into the created **lib** folder from the following location.
```
    http://code.google.com/p/aost/downloads/list
```
  * Download **tellurium-0.6.0-dependencies.zip** into the created **lib** folder, which contains Tellurium dependent jar files from here. Unzip it.
  * Download the **test\_source.zip** file from the following location. Zip file contains the UI Groovy file NewGoogleStartPage.groovy, Java Test case NewGoogleStartPageTestCase.java and Config Groovy file TelluriumConfig.groovy, which will be used to setup the project.
```
    http://code.google.com/p/aost/downloads/list
```

## Project Setup ##

  * Launch IntelliJ. Create a new Project.  **File > New Project > Create Project From Scratch**.

http://tellurium-users.googlegroups.com/web/new_project.png?gsc=Lt2VpAsAAACYrUAQupYOFFjJqrec6c4b

  * Set the project details as shown below and Click Next.

http://tellurium-users.googlegroups.com/web/new_project_name.png?gsc=SQjmlAsAAABU12cSZ1PQkbA1X28ECoRz

  * Set the source directory and Click Next.
http://tellurium-users.googlegroups.com/web/src.png?gsc=wBXCJAsAAAAfbqx3lKR2rZ5CIwHQeaZZ

  * Select the Groovy checkbox and the Groovy version. Click Next.
http://tellurium-users.googlegroups.com/web/groovy.png?gsc=mROe7gsAAACXrIR7ScxzvxzrUyN0hFSB


  * **Project Settings > Module > GoogleSearch > Dependencies**. Create new module library named **lib** by clicking on the **Add**. Click **Attach Classes** and select the downloaded jar files from the **lib folder** as shown.

http://tellurium-users.googlegroups.com/web/libs.png?gsc=SuXkMAsAAACL4iKNGSJySdQkrzCLnBNh
  * Create a **com.test.ui** package by right clicking src -> New -> Package

http://tellurium-users.googlegroups.com/web/ui_package.png?gda=a8rE5EAAAACsdq5EJRKLPm_KNrr_VHB_pdlLdymAXg8SYKls_MNszYIi0Ih1v3jXyEZ-mDEG2pNtxVPdW1gYotyj7-X7wDON

  * Create a new groovy file. **NewGoogleStartPage** in **com.test.ui** package by right clicking the package New -> Groovy Class. Copy the content for this file from the download test\_source.zip.

http://tellurium-users.googlegroups.com/web/groovy_class.png?gda=AO8noEIAAACsdq5EJRKLPm_KNrr_VHB_rs_1bllq13ceSQv1YyBWZQeUrVoEA5FcVoITwkAodIFV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ

  * Create a **com.test.testcase** package by right clicking src -> New -> Package

http://tellurium-users.googlegroups.com/web/test_package.png?gda=6DlfyUIAAACsdq5EJRKLPm_KNrr_VHB_T5Rrh2I2VBiHoIgwQaOa3gQ4f2xCM-mnIF4GEPZVUTdV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ


  * Create a new Java file. **NewGoogleStartPageTestCase** in **com.test.ui** package by right clicking the package New -> Java Class. Copy the content for this file from the download test\_source.zip.

http://tellurium-users.googlegroups.com/web/java_class.png?gda=cU3PYEAAAACsdq5EJRKLPm_KNrr_VHB_LnyL7BN_dh2inUW9ibcDQQW3FfTtZ9NcXsaHwGXqopxtxVPdW1gYotyj7-X7wDON


  * Create a new **TelluriumConfig.groovy** file at the Project root level and not in the src directory as shown. Copy the content for this file from the download test\_source.zip.

http://tellurium-users.googlegroups.com/web/config.png?gsc=uYRTbwsAAADFfFhQNHNCvCJqBbGSCRgg

  * Build the project by clicking **Build -> Make Project**. If you get an error as shown, then first **compile** the **NewGoogleStartPage.groovy** file and then do **Make Project**.

http://tellurium-users.googlegroups.com/web/compile_error.png?gsc=IXBBMgsAAAA7CVLrPDGRSyHvhacFT88S

  * Right click on the **NewGoogleStartPageTestCase** -> Run **NewGoogleStartPageTestCase**.

http://tellurium-users.googlegroups.com/web/test_runner.png?gsc=IXBBMgsAAAA7CVLrPDGRSyHvhacFT88S

http://tellurium-users.googlegroups.com/web/test_passed.png?gsc=IXBBMgsAAAA7CVLrPDGRSyHvhacFT88S

The test will result as passed.

## Summary ##

This wiki guide is for Ant users to manually create a Tellurium project. For Maven users, you can simply use [Tellurium archetypes](http://code.google.com/p/aost/wiki/UserGuide070TelluriumSubprojects#Tellurium_Maven_Archetypes) to create a Tellurium project and then load them up to IntelliJ IDEA as a Maven project. Apart from that, you can also use Tellurium reference project as a template project instead of creating everything from scratch.

## Resources ##

  * [Tellurium on Twitter](http://twitter.com/TelluriumSource)
  * [Tellurium at Rich Web Experience 2009 by Jian Fang and Vivek Mongolu](http://www.slideshare.net/John.Jian.Fang/tellurium-at-rich-web-experience2009-2806967)
  * [Tellurium 0.6.0 User Guide](http://www.slideshare.net/John.Jian.Fang/tellurium-060-user-guide)
  * [Tellurium video tutorial by Vivek Mongolu](http://vimeo.com/8601173)
  * [Tellurium - A New Approach For Web Testing](http://www.slideshare.net/John.Jian.Fang/telluriumanewapproachforwebtesting)
  * [10 Minutes to Tellurium](http://vimeo.com/8600410)
  * [Tellurium 0.7.0 Demo Project](http://aost.googlecode.com/files/santa-algorithm-demo.tar.gz)