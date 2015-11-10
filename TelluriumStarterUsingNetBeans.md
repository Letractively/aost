Tellurium Starter using NetBeans IDE

## Prerequisites ##
  * Using Windows XP or Windows 2000 or later
  * Install NetBeans IDE 6.1 or later
  * Setup Subversion Client. It can be download at http://www.collab.net/downloads/netbeans/

## Get Tellurium source code using NetBeans ##
  * Select Versioning > SubVersion > Checkout
  * Enter "http://aost.googlecode.com/svn/trunk/" for Repository URL (as shown in Figure 1)
  * Click Next
  * Check the "Skip "trunk" and checkout only its content" checkbox
  * Specify the local folder where do you want to store Tellurium project (e.g C:\Projects\tellurium)
  * Click Finish. It will take few minutes to get the source code from the server.
  * Open the project
  * Select "Clean and Build". The output should look similar as shown in Figure 2.

http://tellurium-users.googlegroups.com/web/netbeans_subversion_checkout.png?hl=en&gda=eYZqIlIAAACjbvY1r0rMz6MRbvC-tSqo9PHQf4U4qgtgEkThurTmvyaVYqLWYw1zdYcUJyU0ka4_74NjZTrT_K2SBXbV3b6SVeLt2muIgCMmECKmxvZ2j4IeqPHHCwbz-gobneSjMyE
  * Figure 1: Checkout Tellurium's source code from SubVersion


http://tellurium-users.googlegroups.com/web/netbeans_build_tellurium.png?hl=en&gda=xioSVE4AAACjbvY1r0rMz6MRbvC-tSqo9PHQf4U4qgtgEkThurTmv6Sa8TbdulJ0dxmJR7q-CbwGpDlVKnH4ETBB8Nngcy_y47Cl1bPl-23V2XOW7kn5sQ
  * Figure 2: Build Tellurium from source code

## Execute a java testcase ##
  * We will run a provided testcases to test Tellurium project website.
  * Select the Projects view and expand "Test Packages" folder and pick "TelluriumWikiPageJavaTestCase".
  * We can execute each testcase with "Run File" option as shown in Figure 3.

http://tellurium-users.googlegroups.com/web/netbeans_run_java_testcase.png?hl=en&gda=9Z1qlVAAAACjbvY1r0rMz6MRbvC-tSqo9PHQf4U4qgtgEkThurTmvyPSfFVdHRZFu9eyksEjk2JbxwI6VoZerisbvQL0MPTGbcVT3VtYGKLco-_l-8AzjQ
  * Figure 3: Execute a java testcase


## Issues ##
  * Unable to start selenium server due to conflicted port
> > >> Change port number from 4444 to 4445 for both port setings from embeddedserver and connector setting in the TelluriumConfig.groovy file
  * Firefox version 3.0.1 does not work properly and hang during running a testcase
> > >>  This is a known Selenium issue. A work-around is to install the 2.0.16 firefox version or change browser type to use iehta. You can make this change in TelluriumConfig.groovy file located at the Tellirium root project folder.