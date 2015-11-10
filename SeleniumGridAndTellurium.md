

Following are the instructions to use Selenium Grid with Tellurium.
# Introduction #
We recently added support for Selenium Grid to the Tellurium. Now you can run the Tellurium tests against different browsers using Selenium Grid.

This document assumes that users are already familiar with setting up Selenium Grid environment otherwise please refer to the Selenium Grid Demo first.

http://selenium-grid.seleniumhq.org/run_the_demo.html

# Steps to Follow #

I have 3 machines set up to run the Tellurium tests on Selenium Grid. You can do all these steps on your local box. To do this locally remove the machine names with localhost. Each machine in this set up has a defined role as described below.

**dev1.tellurium.com**
Tellurium test development machine.

**hub.tellurium.com**
Selenium Grid hub machine that will drive the tests.

**rc.tellurium.com**
Multiple Selenium RC server running and registered to the Selenium Grid HUB. The actual test execution will be done on this machine. You can register as many Selenium RC servers as you wish. You need to be realistic about the hardware specification though.

Download Selenium Grid from the following URL and extract the contents of the folder on each of these machines.

I am using Selenium Grid 1.0.3 which is the current released version.
http://selenium-grid.seleniumhq.org/download.html

Following is the illustration of the environment.

http://tellurium-users.googlegroups.com/web/grid_setup%20(3).png?hl=en&gsc=k9PUBQsAAAD0iHvYwplYsVJSt_ydkZvu


The first step would be to launch the selenium grid hub on the hub machine. Open up a terminal on the HUB machine **hub.tellurium.com** and go to the download directory of Selenium Grid.

```
> cd /Tools/selenium-grid-1.0.3
> ant launch-hub
```

You will notice that it has launched the Selenium HUB on the machine with different browsers. To ensure that the HUB is working properly go to the following location.

http://hub.tellurium.com:4444/console

You can see a web page with 3 distinct columns as **Configured Environments, Available Remote Controls, Active Remote Controls**

You will have a list of browsers that are configured by default to run the tests while the list for Available Remote Controls and Avtive Remote Controls will be empty.

The next step would be to launch the Selenium RC servers and register them with the selenium HUB. Open up a terminal on **rc.tellurium.com** and go to the selenium grid download directory.

```
> cd /Tools/selenium-grid-1.0.3
> ant -Dport=5555 -Dhost=rc.tellurium.com -DhubURL=http://hub.tellurium.com:4444 -Denvironment="Firefox on Windows" launch-remote-control
```
This command will start a Selenium RC server on this machine and register it with the Selenium Grid hub machine as specified by the hubURL.

To register another Selenium RC server on this machine for internet explorer do the same on a different port.

```
> cd /Tools/selenium-grid-1.0.3
> ant -Dport=5556 -Dhost=rc.tellurium.com -DhubURL=http://hub.tellurium.com:4444 -Denvironment="IE on Windows" launch-remote-control
```

-port will that the remote control will be listening at. Must be unique on the machine the remote control runs on.

-hostname Hostname or IP address of the machine the remote control runs on. Must be visible from the Hub machine.

-hub url  Which hub the remote control should register/unregister to. as the hub is running on hostname hub.tellurium.com, this URL will be http://hub.tellurium.com:4444

Once you are successful in replicating a setup similar to the one described above, point your browser to the Hub console (http://hub.tellurium.com:4444/console). Check that all the remote controls did register properly. Available remote controls list should be updated and have these 2 selenium servers available to run the tests.

Now you have started the selenium hub and the selenium rc servers on the Grid environment, the next step would be to run the Tellurium tests against different browsers.

Go to the Tellurium test development machine which in our case is **dev1.tellurium.com**.

Open up the TelluriumConfig.groovy and change the values of the selenium server and port to make sure that tellurium requests for the new sessions from Selenium HUB and then Selenium HUB can point tellurium tests to be run on **rc.tellurium.com** based on the browser of choice.

You need to change the values for the following properties.

runInternally - ensures that you do not launch the Selenium Server on the local machine.
serverHost - the selenium grid hub machine that has the information about the available selenium rc servers.
port - port that Selenium HUB is running on, by default this port is 4444 but you can change that in the grid\_configuraton.yml file if this port is not available on your HUB machine.
browser - the browser that comes under the configured environments list on the selenium HUB machine, you can change these values to your own choice in the grid\_configuration.yml file.

```

tellurium{
    //embedded selenium server configuration
    embeddedserver {
        //port number
        port = "4444"
        //whether to use multiple windows
        useMultiWindows = false
        //whether to run the embedded selenium server. If false, you need to manually set up a selenium server
        runInternally = false
        //profile location
        profile = ""
        //user-extension.js file
        userExtension = "target/classes/extension/user-extensions.js"
    }
    //event handler
    eventhandler{
        //whether we should check if the UI element is presented
        checkElement = false
        //wether we add additional events like "mouse over"
        extraEvent = true
    }
    //data accessor
    accessor{
        //whether we should check if the UI element is presented
        checkElement = true
    }
    //the configuration for the connector that connects the selenium client to the selenium server
    connector{
        //selenium server host
        //please change the host if you run the Selenium server remotely
        serverHost = "hub.tellurium.com"
        //server port number the client needs to connect
        port = "4444"
        //base URL
        baseUrl = "http://localhost:8080"
        //Browser setting, valid options are
        //  *firefox [absolute path]
        //  *iexplore [absolute path]
        //  *chrome
        //  *iehta
        browser = "Firefox on Windows"
        //user's class to hold custom selenium methods associated with user-extensions.js
        //should in full class name, for instance, "com.mycom.CustomSelenium"
        customClass = "org.tellurium.test.MyCommand"
    }
```


The set up is now complete.

Now run the tests as you normally do by either the Maven command or the IDE and you will notice that the tests are running on **rc.tellurium.com** and the list for **Active Remote Controls** is also updated on the hub URL (http://hub.tellurium.com:4444/console) during the test execution.

In case of any question/issue please feel free to contact.

Thanks

Tellurium Team