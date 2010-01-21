println "Running DSL test script" + args[0]

@Grab(group='org.codehaus.groovy', module='groovy-all', version='1.7.0')
@Grab(group='org.seleniumhq.selenium.server', module='selenium-server', version='1.0.1-te2')
@Grab(group='org.seleniumhq.selenium.client-drivers', module='selenium-java-client-driver', version='1.0.1')
@Grab(group='junit', module='junit', version='4.7')
@Grab(group='caja', module='json_simple', version='r1')
@Grab(group='org.apache.poi', module='poi', version='3.0.1-FINAL')
@Grab(group='org.stringtree', module='stringtree-json', version='2.0.10')
@Grab(group='org.telluriumsource', module='tellurium-core', version='0.7.0-SNAPSHOT')
def runDsl(String[] args) {
  org.telluriumsource.dsl.DslScriptExecutor.main(args);
}

println "Running DSL test script, press Ctrl+C to stop."

runDsl(args);
