package org.telluriumsource.server

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Apr 20, 2010
 * 
 *  https://svn.apache.org/repos/asf/geronimo/server/tags/2.1.2/framework/modules/geronimo-commands/src/main/groovy/org/apache/geronimo/commands/ProcessLauncher.groovy
 * 
 */
class ProcessLauncher {
//    private Logger log = LoggerFactory.getLogger(this.class)

//    IO io

    String name

    Closure process

    Closure verifier

    int verifyWaitDelay = 1000

    int timeout = -1

    boolean background = false

    def launch() {
//        assert io
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

//        io.out.println("Launching ${name}...")
//        io.flush()

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

//            log.debug("Waiting for ${name}...")

            while (!started) {
                if (timedOut) {
                    throw new RuntimeException("Unable to verify if $name was started in the given time ($timeout seconds)")
                }

                if (error) {
                    throw new RuntimeException("Failed to start: $name", error)
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

//        io.out.println("$name started in $watch")
//        io.flush()

        if (!background) {
//            log.debug("Waiting for $name to shutdown...")

            t.join()

 //           log.debug("$name has shutdown")
        }
    }
}
