package org.telluriumsource.server;

import groovy.lang.Closure;

/*

    http://grepcode.com/file/repo1.maven.org/maven2/org.codehaus.groovy.maven/gmaven-mojo-support/1.0/org/codehaus/groovy/maven/mojo/support/ProcessLauncher.java

    https://svn.apache.org/repos/asf/geronimo/server/tags/2.1.2/framework/modules/geronimo-commands/src/main/groovy/org/apache/geronimo/commands/ProcessLauncher.groovy

class ProcessLauncher
{
    private Logger log = LoggerFactory.getLogger(this.class)

    IO io

    String name

    Closure process

    Closure verifier

    int verifyWaitDelay = 1000

    int timeout = -1

    boolean background = false

    def launch() {
        assert io
        assert process
        assert name

        Throwable error

        def runner = {
            try {
                process()
            }
            catch (Exception e) {
                error = e
            }
        }

        def t = new Thread(runner, "$name Runner")

        io.out.println("Launching ${name}...")
        io.flush()

        def watch = new StopWatch()
        watch.start()

        t.start()

        if (verifier) {
            def timer = new Timer("$name Timer", true)

            def timedOut = false

            def timeoutTask
            if (timeout > 0) {
                timeoutTask = timer.runAfter(timeout * 1000, {
                    timedOut = true
                })
            }

            def started = false

            log.debug("Waiting for ${name}...")

            while (!started) {
                if (timedOut) {
                    throw new Exception("Unable to verify if $name was started in the given time ($timeout seconds)")
                }

                if (error) {
                    throw new Exception("Failed to start: $name", error)
                }

                if (verifier()) {
                    started = true
                }
                else {
                    Thread.sleep(verifyWaitDelay)
                }
            }

            timeoutTask?.cancel()
        }

        io.out.println("$name started in $watch")
        io.flush()

        if (!background) {
            log.debug("Waiting for $name to shutdown...")

            t.join()

            log.debug("$name has shutdown")
        }
    }
}

 */
public class ProcessLauncher implements groovy.lang.GroovyObject {
    private java.lang.String name = null;
    public java.lang.String getName() {
        throw new InternalError("Stubbed method");
    }
    public void setName(java.lang.String value) {
        throw new InternalError("Stubbed method");
    }

    private Closure process = null;
    public Closure getProcess() {
        throw new InternalError("Stubbed method");
    }
    public void setProcess(Closure value) {
        throw new InternalError("Stubbed method");
    }

    private Closure verifier = null;
    public Closure getVerifier() {
        throw new InternalError("Stubbed method");
    }
    public void setVerifier(Closure value) {
        throw new InternalError("Stubbed method");
    }

    private int verifyWaitDelay = 0;
    public int getVerifyWaitDelay() {
        throw new InternalError("Stubbed method");
    }
    public void setVerifyWaitDelay(int value) {
        throw new InternalError("Stubbed method");
    }

    private int timeout = 0;
    public int getTimeout() {
        throw new InternalError("Stubbed method");
    }
    public void setTimeout(int value) {
        throw new InternalError("Stubbed method");
    }

    private boolean background = false;
    public boolean getBackground() {
        throw new InternalError("Stubbed method");
    }
    public boolean isBackground() {
        throw new InternalError("Stubbed method");
    }
    public void setBackground(boolean value) {
        throw new InternalError("Stubbed method");
    }

    public java.lang.Object launch() {
        throw new InternalError("Stubbed method");
    }

    public groovy.lang.MetaClass getMetaClass() {
        throw new InternalError("Stubbed method");
    }

    public void setMetaClass(groovy.lang.MetaClass metaClass) {
        throw new InternalError("Stubbed method");
    }

    public java.lang.Object invokeMethod(java.lang.String name, java.lang.Object args) {
        throw new InternalError("Stubbed method");
    }

    public java.lang.Object getProperty(java.lang.String name) {
        throw new InternalError("Stubbed method");
    }

    public void setProperty(java.lang.String name, java.lang.Object value) {
        throw new InternalError("Stubbed method");
    }
}
