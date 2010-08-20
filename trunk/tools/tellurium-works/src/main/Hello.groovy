import org.slf4j.LoggerFactory

class Hello {
    def sayHello() {
     logger.info("==> sayHello")
     println "Hello"
     logger.info("<== sayHello")
    }
    def static logger =  LoggerFactory.getLogger(Hello);
}


