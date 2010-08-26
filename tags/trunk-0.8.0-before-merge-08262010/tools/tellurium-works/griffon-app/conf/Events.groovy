import org.slf4j.LoggerFactory
onNewInstance = {klass, type, instance ->
    instance.metaClass.logger = LoggerFactory.getLogger(klass);
}
