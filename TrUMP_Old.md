# Tellurium UI Model Plugin (TrUMP) #

Tellurium UI Model Plugin (TrUMP) is the Firefox plugin to automatically create UI modules for users.

## Install TrUMP IDE ##

Go to the Tellurium project download page and download the TrUMP xpi file as shown in the Figure,

http://tellurium-users.googlegroups.com/web/TrUMPDownload.png?gsc=N4jWPhYAAACC-9YBMpIljZK8WE-abHrag-kXU5InE09W2o0GCSVgCQ

Then, open the xpi file with Firefox,

http://tellurium-users.googlegroups.com/web/trump010b.png?gda=sK2vfz8AAACXZPxEX7Ki-M5C2JpeBoXXpbNhTBGwKHnla96SFPZ5YrYvoqxkO5DC4aYRH_5SRSSccyFKn-rNKC-d1pM_IdV0&gsc=z7xdXxYAAACUfwRI-f7WsvI5RgWLBanlg-kXU5InE09W2o0GCSVgCQ

You will see the following Firefox plugin installation page, click "Install Now",

http://tellurium-users.googlegroups.com/web/SoftwareInstallation.png?gsc=zCT_fBYAAACQz_B0Pa_Ea1pLiVja5Z6ag-kXU5InE09W2o0GCSVgCQ


After that, restart Firefox to make the TrUMP plugin take effect.

http://tellurium-users.googlegroups.com/web/RestartFirefox.png?gsc=N4jWPhYAAACC-9YBMpIljZK8WE-abHrag-kXU5InE09W2o0GCSVgCQ

## Setup TrUMP From Project Source ##

Sometime, you may want to work on TrUMP source. To do this, check out the firefox-plugin folder in tools/firefox-plugin.

Lets say you have checked it into C:\Project\firefox-plugin

First you’ll need to copy the file trump@aost.net inside the extensions-file folder to your firefox profile.

Profiles for firefox are located in
C:\Documents and Settings\$user\Application Data\Mozilla\Firefox\Profiles\
For Windows OS

For linux,  the firefox user profile lives in

~/.mozilla/firefox/

Lets say you want to copy it to user1 profile

Open the user1 profile. Copy the trump@aost.net file to the extensions folder

C:\Documents and Settings\$user\Application Data\Mozilla\Firefox\Profiles\user1\extensions

Edit the trump@aost.net file by opening it in a text editor. This file will contain the path to our trump plugin folder, which is C:\Project\firefox-plugin\trump\. Don’t forget the \ after the trump. Save and close the file.

We are done setting up the plugin.

After that, open the firefox with the profile “user1”. You are ready to use TrUMP IDE

## Build TrUMP From Source ##

Check out TrUMP source code from Tellurium trunk/tools/firefox-plugin.

Then run the following ant tasks,
```
ant clean
ant dist
```

The generated .xpi file is under the /dist directory

## Use TrUMP ##

In Firefox, select "Tools" > "TrUMP IDE"

http://tellurium-users.googlegroups.com/web/trump010e.png?gsc=jTelIRYAAAC0nhYDa9gni8Uq6-kI4oX7g-kXU5InE09W2o0GCSVgCQ

You will see the TrUMP IDE as follows,

http://tellurium-users.googlegroups.com/web/trump010f.png?gda=IF9OXz8AAACXZPxEX7Ki-M5C2JpeBoXXGvl4Gd4uFZh4sOybBRtMejKXHS9twXs2d3PI5PCtpFCccyFKn-rNKC-d1pM_IdV0&gsc=jTelIRYAAAC0nhYDa9gni8Uq6-kI4oX7g-kXU5InE09W2o0GCSVgCQ

The "Record" button is on by default, you can click on "Stop" to stop recording. Now, you can start to use the TrUMP IDE to record whatever UI elements you click on the WEB. For example, you can open Google search page and click on the search elements and the three links,

http://tellurium-users.googlegroups.com/web/trump010g.png?gda=0hMSFz8AAACXZPxEX7Ki-M5C2JpeBoXXGvl4Gd4uFZh4sOybBRtMevEOLvEQPr9lnZXEWcnY2r-ccyFKn-rNKC-d1pM_IdV0&gsc=jTelIRYAAAC0nhYDa9gni8Uq6-kI4oX7g-kXU5InE09W2o0GCSVgCQ

The blue color indicates selected element, you can click on the selected element again to un-select it.

Then, you can click on the "Generate" button to create the Tellurium UI Module,

http://tellurium-users.googlegroups.com/web/trump010h.png?gda=WwV8UT8AAACXZPxEX7Ki-M5C2JpeBoXXGvl4Gd4uFZh4sOybBRtMekGLp6dQXNkqlGzJQvNUMXKccyFKn-rNKC-d1pM_IdV0&gsc=jTelIRYAAAC0nhYDa9gni8Uq6-kI4oX7g-kXU5InE09W2o0GCSVgCQ

You will see the generated UI module in the "Source" window,

http://tellurium-users.googlegroups.com/web/TrUMPSource.png?gsc=N4jWPhYAAACC-9YBMpIljZK8WE-abHrag-kXU5InE09W2o0GCSVgCQ


We will see the actual UI module as follows,

```
ui.Container(uid: 'root', clocator: [tag: 'table']){
	Container(uid: 'T4t', clocator: [tag: 'td']){
		InputBox(uid: 'input0', clocator: [tag: 'input', title: 'Google Search', name: 'q'], respond: ['keyDown'])
		SubmitButton(uid: 'input1', clocator: [tag: 'input', type: 'submit', value: 'Google Search', name: 'btnG'])
		SubmitButton(uid: 'input2', clocator: [tag: 'input', type: 'submit', value: "I'm Feeling Lucky", name: 'btnI'])
	}
	Container(uid: 'T4tf', clocator: [tag: 'td']){
		UrlLink(uid: 'a3', clocator: [tag: 'a', text: 'Advanced Search', href: '/advanced_search?hl=en'])
		UrlLink(uid: 'a4', clocator: [tag: 'a', text: 'Preferences', href: '/preferences?hl=en'])
		UrlLink(uid: 'a5', clocator: [tag: 'a', text: 'Language Tools', href: '/language_tools?hl=en'])
	}
}
```

## Additional Features ##
TrUMP provides the ability for users to check which UI element the recorded UI actually maps to. As shown in the following figure, in the "Record" window, click on any recorded UI, the corresponding UI element will be marked with a black box.

http://tellurium-users.googlegroups.com/web/CheckUiElement.png?gsc=tMf2vBYAAAAKrp4o2HOPFnb0UWCE3cHWg-kXU5InE09W2o0GCSVgCQ

TrUMP also adds logs to Javascript Error Console. To turn on the log, please first type "about:config" on Firefox address box, then select "javascript.options.showInConsole" and double click it to flip its value to be true.

![http://tellurium-users.googlegroups.com/web/AboutConfig.png](http://tellurium-users.googlegroups.com/web/AboutConfig.png)

After you click the "Generate" button, you can find the log message in Javascript console as follow (Tools > Error Console),

http://tellurium-users.googlegroups.com/web/ErrorConsole.png?gsc=ECfkzxYAAACkNoHhoJXKpjewPqNZCHesg-kXU5InE09W2o0GCSVgCQ

## Known Issues ##

Please see Issues 80-96 on the issue page.