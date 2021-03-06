package org.telluriumsource.test.java;

import org.telluriumsource.component.connector.SeleniumConnector;
import org.telluriumsource.framework.bootstrap.TelluriumSupport;

/**
 * Created by IntelliJ IDEA.
 * User: haroon
 * Date: 09-Jul-2010
 * Time: 22:36:13
  */

public class TelluriumEasybTestCase extends BaseTelluriumJavaTestCase {

    public void start() {
        if(tellurium == null){
            tellurium = TelluriumSupport.addSupport();
            tellurium.startServer(customConfig);
            tellurium.connectServer();
            connector = (SeleniumConnector) getCurrentConnector();
        }
    }

    public void stop() {
        if(tellurium != null){
            tellurium.disconnectServer();
            tellurium.stopServer();
        }

    }
   
}
