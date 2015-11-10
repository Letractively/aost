# Introduction #

ReportNG is a simple HTML reporting plug-in for the TestNG framework. It is intended as a replacement for the default TestNG HTML report. The default report is comprehensive but is not so easy to understand at-a-glance. ReportNG provides a simple, colour-coded view of the test results. You can find more information about ReportNG on the following URL. [ReportNG](https://reportng.dev.java.net/)


# How to generate test results with ReportNG? #

TestNG reference project supports generating reports using ReportNG out of the box. To use the reporting plug-in, set the listeners attribute of the testng element in your suite file. ReportNG provides following TestNG listeners.

```
org.uncommons.reportng.HTMLReporter
org.uncommons.reportng.JUnitXMLReporter
```

We are going to use HTMLReporter in the following example. We have a test suite file as shown below.


```
<!DOCTYPE suite SYSTEM "http://beust.com/testng/testng-1.0.dtd" >
<suite name="Google Code Tests">
   <listeners>
        <listener class-name="org.uncommons.reportng.HTMLReporter"/>
    </listeners>

	<test name="Downloads Page">
        <classes>
            <class name="org.tellurium.test.TelluriumDownloadsPageTestNGTestCase"></class>
        </classes>
	</test>

 	<test name="Project Page">
         <classes>
             <class name="org.tellurium.test.TelluriumProjectPageTestNGTestCase"></class>
         </classes>
	</test>

 	<test name="Wiki Page">
         <classes>
             <class name="org.tellurium.test.TelluriumWikiPageTestNGTestCase"></class>
         </classes>
	</test>
</suite>
```

Now you can run the tests either by Maven, ANT or any IDE using this TestNG suite file.

When the test run is complete, you will get a report as shown below.

http://tellurium-users.googlegroups.com/web/tellurium_reportng.png?gsc=4yz_OwsAAABERwLPC1sClZrHewT3tcR5




You can see the test report is more readable and comprehensive.

# Note #
You can find this example in the TestNG reference project.
The location of the test suite file is as follows.

```
tellurium/reference-projects/tellurium-testng-java/src/test/resources/org/tellurium/test-suites/reportng.xml
```

If you run the test suite then you can find the results generated in the following location.

```
/tellurium/test-output/html/index.html
```


Please feel free to contact us in case of any query.


Tellurium Team