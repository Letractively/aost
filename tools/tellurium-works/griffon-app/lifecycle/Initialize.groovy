/*
 * This script is executed inside the UI thread, so be sure to  call 
 * long running code in another thread.
 *
 * You have the following options
 * - execOutside { // your code }
 * - execFuture { // your code }
 * - Thread.start { // your code }
 *
 * You have the following options to run code again inside the UI thread
 * - execAsync { // your code }
 * - execSync { // your code }
 */

import groovy.swing.SwingBuilder
import griffon.util.GriffonPlatformHelper
import static griffon.util.GriffonApplicationUtils.*
import telluriumworks.ResourceUtils

GriffonPlatformHelper.tweakForNativePlatform(app)
SwingBuilder.lookAndFeel((isMacOSX ? 'system' : 'nimbus'), 'gtk', ['metal', [boldFonts: false]])
ResourceUtils.setBundlePath("Resource.properties")
//System.properties['http.proxySet'] = true
//System.properties['http.proxyHost']='localhost'
//System.properties['http.proxyPort']='5865'
