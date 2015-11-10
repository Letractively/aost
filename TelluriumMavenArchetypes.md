

# Tellurium Maven Archetypes #

## How Tellurium Maven Archetypes Are Created ##

Tellurium includes two maven archetypes, i.e., tellurium-junit-archetype and tellurium-testng-archetype for Tellurium JUnit test project and Tellurium TestNG test project, respectively.

The procedures to create tellurium Maven archetypes are surprisingly simple. Take the tellurium-junit-archetype as an example.

First, create a maven archetype project using the following maven command

```
mvn archetype:create -DgroupId=tellurium -DartifactId=tellurium-junit-archetype -DarchetypeArtifactId=maven-archetype-archetype
```

Then, you need to copy the prototype files to the archetype directory: `src/main/resources/archetype-resources/`, including pom file, example UI module file, and example Tellurium JUnit test file.

After that, you need to customize the archetype descriptor, archetype.xml in directory: `src/main/resources/META-INF/`. Notice that GMaven uses `src/main/groovy` and `src/test/groovy` directories and you must put them under the resources tag.

The last step is to build and deploy the archetype using the following command

```
mvn package deploy
```

Sometimes, you may like to first try to test it on your local repository, then you can
run

```
mvn install
```

To create a test project from the local repository, you should use the offline option "-o".

## Use Tellurium JUnit Archetype to Create A New Project ##

First, you should modify your maven setting.xml file to allow you to automatically include Tellurium artifacts in your Maven project. This should go in your\_home/.m2/settings.xml file:

```
<settings>
    <profiles>
        <profile>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <repositories>
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
            </repositories>
        </profile>
    </profiles>
</settings>
```

Then, run the following maven command to create your new project

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0
```

Without adding the Tellurium Maven repository, you can specify it in the command line as
```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0 -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/releases
```

But you still need to put the Tellurium repository into your settings.xml later since the Tellurium test project needs to download other Tellurium artifacts from the repository, for example, the tellurium core jar file.

After the project is created, you will find the following files
```
pom.xml
src
src/main
src/main/groovy
src/main/resources
src/test
src/test/groovy
src/test/groovy/module
src/test/groovy/module/GoogleSearchModule.groovy
src/test/groovy/test
src/test/groovy/test/GoogleSearchTestCase.java
src/test/resources
TelluriumConfig.groovy
```

The GoogleSearchModule UI module is created using the Trump plugin and the GoogleSearchTestCase is an example Tellurium JUnit Test case.

You should use your IDE to open the project, for example, in IntelliJ IDEA, do the following steps

```
New Project > Import project from external model > Maven > Project directory > Finish
```

You will open up a Maven project and make sure you are using Groovy 1.6.0 in your project. After that, compile the project and run the example test GoogleSearchTestCase.

If you want to run the tests in command line, you can use the following command

```
mvn test
```

## Use Tellurium TestNG Archetype to Create A New Project ##

This is the same as the JUnit archetype, except that you should use a different archetype

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=tellurium -DarchetypeVersion=0.6.0
```

## Tellurium 0.7.0 SNAPSHOT ##

To create a Tellurium project based on Tellurium 0.7.0 SNAPSHOT, you should use the Maven archetype 0.7.0-SNAPSHOT. To create a JUnit project, use the following Maven command:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-junit-archetype -DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.7.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```

Similarly, to create a TestNG project, use the following command:

```
mvn archetype:create -DgroupId=your_group_id -DartifactId=your_artifact_id -DarchetypeArtifactId=tellurium-testng-archetype -DarchetypeGroupId=org.telluriumsource -DarchetypeVersion=0.7.0-SNAPSHOT -DarchetypeRepository=http://maven.kungfuters.org/content/repositories/snapshots
```