## Set up the Tellurium Reference project tellurium-junit-java with Eclipse ##

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

### Checkout Project tellurium-junit-java as an Eclipse Project ###
  * Launch Eclipse and point it to your workspace.
  * File > New > Other > SVN > Checkout projects from SVN

http://tellurium-users.googlegroups.com/web/eclipsetjj1.png?gda=GBMET0EAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFpgPruVTcYdmQoitNQUIKj9TCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Next > create a new repository location, type in

```
http://aost.googlecode.com/svn/trunk/
```

http://tellurium-users.googlegroups.com/web/eclipsetjj2.png?gda=8ixIE0EAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFuCVqkthRocdd89BERD_d71TCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Next > select Trunk/Reference-Projects/tellurium-junit-java sub-project

http://tellurium-users.googlegroups.com/web/eclipsetjj3.png?gda=3jmR-EEAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFnZX4Gs5tf7s00egSi0tJ_9TCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Next > Check out as a project in the workspace

http://tellurium-users.googlegroups.com/web/eclipsetjj4.png?gda=4nBTt0EAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFrU0FOUc9vZoJjns4dLQ-3NTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Next, Project starts to check out

> http://tellurium-users.googlegroups.com/web/eclipsetjj5.png?gda=Z4dfHUEAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFvaAr9cVNNFjxABxlviK2d5TCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Check project settings for builders

http://tellurium-users.googlegroups.com/web/eclipsetjj6.png?gda=-tk2nUEAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFlvJRVWcg7O_gqh-xpXBQWNTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Check project settings for Groovy project property

http://tellurium-users.googlegroups.com/web/eclipsetjj7.png?gda=pmf78kEAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFgSXo8oJkGt2LvTN_twqd7lTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Check project build path > source

http://tellurium-users.googlegroups.com/web/eclipsetjj8.png?gda=q-cYy0EAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFjsFH-31z9WbGF4pZkkCHclTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Check project build path > Libraries

http://tellurium-users.googlegroups.com/web/eclipsetjj9.png?gda=8ACBF0EAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFndZLClnWhBUlHuxjvYc3CNTCT_pCLcFTwcI3Sro5jAzlXFeCn-cdYleF-vtiGpWAA&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

  * Build project > Click on any Junit tests to run

http://tellurium-users.googlegroups.com/web/eclipsetjj10.png?gda=L1vhWEIAAABBo5YKz4My4Er929brhwkDpkboKkfbjSgEMy4g5KXzFnO5bVyzZ0d2PTZCvoYV6LFV4u3aa4iAIyYQIqbG9naPgh6o8ccLBvP6Chud5KMzIQ&gsc=r5aXtAsAAAAMRzmf_Yl0S7-loZxvszUd

Note, sometimes, eclipse complaints that it cannot find the Groovy class imported into the Java class, you need to build the project first. If the problem is still there, you need to go to project settings > Java build path > source. Change the default output folder to another directory and then change it back to "tellurium-junit-java/bin-groovy" again. This problem should go away.

