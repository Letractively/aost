# Appendices #

## Sample POM for Tellurium Maven project ##

Tellurium Maven JUnit testing project could use the following POM file,

```
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>Your_Group_ID</groupId>
    <artifactId>Your_Artifact_Id</artifactId>
    <version>Your_Version</version>
    <name>Tellurium JUnit Test Project</name>

    <repositories>
        <repository>
            <id>caja</id>
            <url>http://google-caja.googlecode.com/svn/maven</url>
        </repository>
        <repository>
            <id>kungfuters-public-snapshots-repo</id>
            <name>Kungfuters.org Public Snapshot Repository</name>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <url>http://maven.kungfuters.org/content/repositories/snapshots</url>
        </repository>
        <repository>
            <id>kungfuters-public-releases-repo</id>
            <name>Kungfuters.org Public Releases Repository</name>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <url>http://maven.kungfuters.org/content/repositories/releases</url>
        </repository>
        <repository>
            <id>kungfuters-thirdparty-releases-repo</id>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <url>http://maven.kungfuters.org/content/repositories/thirdparty</url>
        </repository>
        <repository>
            <id>openqa-release-repo</id>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <url>http://archiva.openqa.org/repository/releases</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>org.codehaus.groovy.maven</groupId>
            <artifactId>gmaven-mojo</artifactId>
            <version>1.0-rc-5</version>
            <exclusions>
                <exclusion>
                    <groupId>org.codehaus.groovy.maven.runtime</groupId>
                    <artifactId>gmaven-runtime-1.5</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>org.codehaus.groovy.maven.runtime</groupId>
            <artifactId>gmaven-runtime-1.6</artifactId>
            <version>1.0-rc-5</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.4</version>
            <scope>test</scope>
        </dependency>

        <dependency>
          <groupId>org.openqa.selenium.grid</groupId>
          <artifactId>selenium-grid-tools</artifactId>
          <version>1.0.2</version>
        </dependency>

        <dependency>
            <groupId>tellurium</groupId>
            <artifactId>tellurium-core</artifactId>
            <version>0.6.0</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium.server</groupId>
            <artifactId>selenium-server</artifactId>
            <version>1.0.1-te</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium.client-drivers</groupId>
            <artifactId>selenium-java-client-driver</artifactId>
            <version>1.0.1</version>
            <exclusions>
                <exclusion>
                    <groupId>org.codehaus.groovy.maven.runtime</groupId>
                    <artifactId>gmaven-runtime-default</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.seleniumhq.selenium.core</groupId>
                    <artifactId>selenium-core</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.seleniumhq.selenium.server</groupId>
                    <artifactId>selenium-server</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.codehaus.groovy</groupId>
            <artifactId>groovy-all</artifactId>
            <version>1.6.0</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>caja</groupId>
            <artifactId>json_simple</artifactId>
            <version>r1</version>
        </dependency>
        <dependency>
            <groupId>org.stringtree</groupId>
            <artifactId>stringtree-json</artifactId>
            <version>2.0.10</version>
        </dependency>
    </dependencies>

    <build>
        <resources>
            <resource>
                <directory>src/main/groovy</directory>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
        </resources>
        <testResources>
            <testResource>
                <directory>src/test/groovy</directory>
            </testResource>
            <testResource>
                <directory>src/test/resources</directory>
            </testResource>
        </testResources>

        <pluginManagement>
            <plugins>
                <plugin>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>2.0.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>2.4.3</version>
                </plugin>
                <plugin>
                    <artifactId>maven-surefire-report-plugin</artifactId>
                    <version>2.4.3</version>
                </plugin>
                <plugin>
                    <artifactId>maven-jar-plugin</artifactId>
                    <version>2.2</version>
                </plugin>
                <plugin>
                    <artifactId>maven-source-plugin</artifactId>
                    <version>2.0.4</version>
                </plugin>
                <plugin>
                    <artifactId>maven-javadoc-plugin</artifactId>
                    <version>2.4</version>
                </plugin>
                <plugin>
                    <artifactId>maven-site-plugin</artifactId>
                    <version>2.0-beta-7</version>
                </plugin>
                <plugin>
                    <groupId>org.codehaus.groovy.maven</groupId>
                    <artifactId>gmaven-plugin</artifactId>
                    <version>1.0-rc-5</version>
                </plugin>
            </plugins>
        </pluginManagement>

        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.5</source>
                    <target>1.5</target>
                </configuration>
            </plugin>
            <plugin>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <includes>
                        <include>**/*_UT.java</include>
                        <include>**/*TestCase.java</include>
                    </includes>
                </configuration>
            </plugin>
            <plugin>
                <artifactId>maven-jar-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>test-jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <artifactId>maven-source-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>jar</goal>
                            <goal>test-jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <artifactId>maven-javadoc-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.codehaus.groovy.maven
                </groupId>
                <artifactId>gmaven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>testCompile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-antrun-plugin</artifactId>
                <version>1.3</version>
                <dependencies>
                    <dependency>
                        <groupId>org.apache.ant</groupId>
                        <artifactId>ant</artifactId>
                        <version>1.7.1</version>
                    </dependency>
                    <dependency>
                        <groupId>org.apache.ant</groupId>
                        <artifactId>ant-launcher</artifactId>
                        <version>1.7.1</version>
                    </dependency>
                    <dependency>
                        <groupId>org.apache.ant</groupId>
                        <artifactId>ant-nodeps</artifactId>
                        <version>1.7.1</version>
                    </dependency>
                    <dependency>
                        <groupId>sun.jdk</groupId>
                        <artifactId>tools</artifactId>
                        <version>1.5.0</version>
                        <scope>system</scope>
                        <systemPath>${java.home}/../lib/tools.jar</systemPath>
                    </dependency>
                </dependencies>
                <executions>
                    <execution>
                        <id>compile-java-test</id>
                        <phase>test-compile</phase>
                        <configuration>
                            <tasks>
                                <path id="testcompile.classpath">
                                    <path refid="maven.compile.classpath"/>
                                    <path refid="maven.test.classpath"/>
                                    <pathelement location="target/classes"/>
                                    <pathelement location="target/test-classes"/>
                                </path>
                                <javac srcdir="src/test/groovy"
                                       destdir="target/test-classes"
                                            classpathref="testcompile.classpath">
                                    <include name="**/*.java"/>
                                </javac>
                            </tasks>
                        </configuration>
                        <goals>
                            <goal>run</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

Tellurium Maven TestNG project uses the same POM file as the JUnit one shown above and you only need to replace the JUnit dependency with the TestNG dependency, i.e., you should use

```
    <dependency>
       <groupId>org.testng</groupId>
       <artifactId>testng</artifactId>
       <version>5.8</version>
       <classifier>jdk15</classifier>
       <optional>true</optional>
    </dependency>
```

for your TestNG project.

## Tellurium Sample Ant Build Script ##

build.properties:

```
### Change javahome to your own Java Home ###
javahome = /usr/java/current

################# javac settings ##########################################
# For javac -- can leave alone in most cases
javac.compiler=javac1.5
javac.deprecation = on
javac.optimize = on
javac.debug = true
javac.fork=no

dist.dir = ${basedir}/dist
```

build.xml:

```
<?xml version="1.0"?>

<project name="tellurium-testng" default="compile-test" basedir=".">

	<property name="dir.project" value="${basedir}" />

	<property file="build.properties" />

	<property name="dir.source" value="${dir.project}/src" />
	<property name="dir.source.tellurium" value="${dir.source}/main/groovy" />
	<property name="dir.source.test" value="${dir.source}/test/groovy" />

	<property name="dir.build" value="${dir.project}/out" />
	<property name="dir.build.tellurium" value="${dir.build}/production" />
	<property name="dir.build.test" value="${dir.build}/test" />

	<property name="dir.lib" value="${dir.project}/lib" />

	<path id="lib.path">
		<fileset dir="${dir.lib}">
			<include name="*.jar" />
			<exclude name="*-src.jar" />
			<include name="*.class" />
		</fileset>
	</path>


	<!-- Match runtime libraries -->
	<patternset id="pattern.libs">
		<include name="**/*.jar" />
		<exclude name="**/*-src.jar"/>
		<!--exclude name="**/*junit.jar"/-->
	</patternset>

	<path id="junit.classpath">
		<fileset dir="${dir.lib}">
			<include name="junit*.jar"/>
		</fileset>
	</path>

	<path id="tellurium.classpath">
		<path refid="lib.path" />
		<pathelement location="${dir.build.tellurium}" />
	</path>

	<path id="test.classpath">
		<path refid="tellurium.classpath" />
		<path refid="junit.classpath" />
		<pathelement location="${dir.build.test}" />
	</path>

	<path id="project.classpath">
		<path refid="test.classpath" />
	</path>

	<taskdef name="junit"
         classname="org.apache.tools.ant.taskdefs.optional.junit.JUnitTask"
         classpathref="junit.classpath" />

	<target name="clean">
		<echo message="Cleaning ..." />
		<delete dir="${dir.build.tellurium}" />
		<delete dir="${dir.build.test}" />
	</target>

	<target name="init">
		<echo message="Initializing project..." />
		<tstamp>
			<format property="time.formatted"
                                pattern="MM/dd/yyyy hh:mm:ss a" unit="hour" />
		</tstamp>
		<mkdir dir="${dir.build}" />
		<mkdir dir="${dir.build.tellurium}" />
		<mkdir dir="${dir.build.test}" />
    </target>

    <macrodef name="compile-java">
            <!-- required attributes -->
            <attribute name="srcdir" />
            <attribute name="destdir" />
            <attribute name="excludes" default="" />

            <!-- these defaults can be changed using properties -->
            <attribute name="compiler" default="${javac.compiler}" />
            <attribute name="debug" default="${javac.debug}" />
            <attribute name="optimize" default="${javac.optimize}" />
            <attribute name="deprecation" default="${javac.deprecation}" />
            <attribute name="fork" default="${javac.fork}" />

            <!-- these defaults can only be overridden explicitly by a task -->
            <attribute name="encoding" default="UTF-8" />
            <attribute name="includeAndRunTime" default="no" />
            <attribute name="failonerror" default="false" />

            <!-- this element sucks up all elements when the macro is used -->
            <element name="javac-elements" implicit="yes" />

            <!-- the macro body -->
            <sequential>
                    <javac srcdir="@{srcdir}"
                            excludes="@{excludes}"
                            destdir="@{destdir}" compiler="@{compiler}"
                            debug="@{debug}"
                            optimize="@{optimize}"
                            deprecation="@{deprecation}"
                            fork="@{fork}"
                            encoding="@{encoding}" failonerror="@{failonerror}">
                    </javac>
            </sequential>
    </macrodef>

   <taskdef name="groovyc" classname="org.codehaus.groovy.ant.Groovyc"
            classpathref="lib.path"/>

	<target name="compile-tellurium" depends="init">
		<echo message="Compiling java..." />
		<groovyc srcdir="${dir.source.tellurium}"
			destdir="${dir.build.tellurium}">
			<classpath refid="lib.path" />
           <!--javac source="1.5" target="1.5" debug="on" /-->
        </groovyc>
        <javac srcdir="${dir.source.tellurium}"
            destdir="${dir.build.tellurium}">
            <classpath refid="tellurium.classpath" />
        </javac>
	</target>

	<target name="compile-test" depends="clean, compile-tellurium">
		<echo message="Compiling test.." />
		<groovyc srcdir="${dir.source.test}" destdir="${dir.build.test}">
			<classpath refid="tellurium.classpath" />
			<include name="**" />
		</groovyc>
        <javac srcdir="${dir.source.test}"
            destdir="${dir.build.test}">
            <classpath refid="test.classpath" />
        </javac>
	</target>

	<target name="run-unit-tests" depends="compile-test">
		<junit fork="yes" forkmode="once" maxmemory="1024m"
			printsummary="yes" errorProperty="test.failed"
                        failureProperty="test.failed">
			<classpath refid="project.classpath" />
			<formatter type="brief" usefile="false" />
			<formatter type="xml" />
			<batchtest todir="${dir.build.test}">
				<fileset dir="${dir.source.test}">
					<include name="**/org/tellurium/test/**"/>
                    <include name="**/org/tellurium/ddt/**"/>
                </fileset>
			</batchtest>
		</junit>
		<fail if="test.failed" />
	</target>

</project>
```

## Tellurium Sample Configuration File ##

```
/**
 * The global place to Tellurium configuration
 *
 *
 */

tellurium{
    //embedded selenium server configuration
    embeddedserver {
        //port number
        port = "4444"
        //whether to use multiple windows
        useMultiWindows = false
        //whether to run the embedded selenium server.
        //If false, you need to manually set up a selenium server
        runInternally = true
        //profile location
        profile = ""
        //user-extension.js file, for example, "target/user-extensions.js"
        userExtension = ""
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
    //the configuration for the connector that connects the selenium client
    //to the selenium server
    connector{
        //selenium server host
        //please change the host if you run the Selenium server remotely
        serverHost = "localhost"
        //server port number the client needs to connect
        port = "4444"
        //base URL
        baseUrl = "http://localhost:8080"
        //Browser setting, valid options are
        //  *firefox [absolute path]
        //  *iexplore [absolute path]
        //  *chrome
        //  *iehta
        browser = "*chrome"
        //user's class to hold custom selenium methods associated with user-extensions.js
        //should in full class name, for instance, "com.mycom.CustomSelenium"
        customClass = ""
    }
    datadriven{
        dataprovider{
            //specify which data reader you like the data provider to use
            //the valid options include "PipeFileReader", "CVSFileReader" at this point
            reader = "PipeFileReader"
        }
    }
    test{
        //at current stage, the result report is only for tellurium data driven testing
        //we may add the result report for regular tellurium test case
        result{
            //specify what result reporter used for the test result
            //valid options include "SimpleResultReporter", "XMLResultReporter",
            //and "StreamXMLResultReporter"
            reporter = "XMLResultReporter"
            //the output of the result
            //valid options include "Console", "File" at this point
            //if the option is "File", you need to specify the file name,
            //otherwise it will use the default file name "TestResults.output"
            output = "Console"
            //test result output file name
            filename = "TestResult.output"
        }
        exception{
            //whether Tellurium captures the screenshot when exception occurs.
            //Note that the exception is the one thrown by Selenium Server
            //we do not care the test logic errors here
            captureScreenshot = true
            //we may have a series of screenshots, specify the file name pattern here
            //Here the ? will be replaced by the timestamp and you might also want to put
            //file path in the file name pattern
            filenamePattern = "Screenshot?.png"
        }
    }
    uiobject{
        builder{
            //user can specify custom UI objects here by define the builder for
            //each UI object
            //the custom UI object builder must extend UiObjectBuilder class
            //and implement the following method:
            //
            // public build(Map map, Closure c)
            //
            //For container type UI object, the builder is a bit more complicated,
            //please take the TableBuilder or ListBuilder as an example

            //example:
//           Icon="org.tellurium.builder.IconBuilder"

        }
    }
    widget{
        module{
            //define your widget modules here, for example Dojo or ExtJs
//            included="dojo, extjs"
            included=""
        }
    }
}
```

## Maven IDE Configuration ##

### Eclipse ###

#### Install Maven Plug-in ####

  1. Click the Add Site button.
  1. Enter the following URL: http://m2eclipse.sonatype.org/update/
  1. Click OK.
  1. Expand the Maven Integration for Eclipse Update Site node.
  1. Select the Maven Integration check box.
  1. Click the Install button and follow the wizard.
  1. Restart Eclipse.

#### Configure Maven Plug-in ####

  1. Click Window | Preferences.
  1. Expand the Maven node.
  1. Select the Installations node.
  1. Click the Add button.
  1. Navigate the the Maven 2.0.9 installation folder.
  1. Click OK.
  1. Click the check box next to the new Maven installation.

#### Import Projects ####

  1. Click File | Import.
  1. Type Maven in the filter text field.
  1. Under the General node, select the Maven Projects node.
  1. Click Next.
  1. Click the Browse button and navigate to the ide-settings folder.
  1. Click OK.
  1. Click Finish.

### Netbeans ###

#### Install Maven Plugin ####

Netbeans does not come with automatic support for Maven, you will need to install the plugin.

  1. From the menu select Tools/Plugins
  1. In the plugins screen, click on the Installed tab and Click on the Reload Catalog tab to get the list of available plugins.
  1. Select the Available Plugins tab and scroll down until you see the Maven plugin in.
  1. Click on the check box next to this plug in and then select Install.
  1. Follow the instructions to install the plug in.

#### Configure Maven Plugin ####

  1. Bring up the preferences dialog.
  1. Select Miscellaneous and then click on the Maven tab.
  1. Tools=>Options for Windows users.
  1. Click on the Browse button next to the entry for External Maven Home. Navigate to the directory where you installed Maven and click Open.
  1. Update the frequency with which your local repository is indexed

#### Import Projects ####

  1. Click File | Open Project
  1. Navigate to the ide-settings folder.
  1. Choose the pom.xml file and click open.

### IntelliJ ###

IntelliJ IDEA has Maven Plugin installed by default.

#### Configure Maven Plug-in ####

  1. Click File | Settings to bring up the IDE settings window.
  1. Click the Maven node.
  1. Click the Override check box next to the Maven Home Directory text field.
  1. Click the ... button.
  1. Navigate to the Maven 2.0.9 installation directory.
  1. Click OK.

#### Import Projects ####

  1. Click File | Open Project
  1. Choose the pom.xml file and click OK.