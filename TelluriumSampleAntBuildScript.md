# build.properties #

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

# build.xml #

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

	<taskdef name="junit" classname="org.apache.tools.ant.taskdefs.optional.junit.JUnitTask" classpathref="junit.classpath" />

	<target name="clean">
		<echo message="Cleaning ..." />
		<delete dir="${dir.build.tellurium}" />
		<delete dir="${dir.build.test}" />
	</target>

	<target name="init">
		<echo message="Initializing project..." />
		<tstamp>
			<format property="time.formatted" pattern="MM/dd/yyyy hh:mm:ss a" unit="hour" />
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

   <taskdef name="groovyc" classname="org.codehaus.groovy.ant.Groovyc" classpathref="lib.path"/>

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
			printsummary="yes" errorProperty="test.failed" failureProperty="test.failed">
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