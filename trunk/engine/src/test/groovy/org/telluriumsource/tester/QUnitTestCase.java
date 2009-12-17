package org.telluriumsource.tester;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.telluriumsource.test.java.TelluriumJUnitTestCase;

/**
 * @author: Jian Fang (John.Jian.Fang@gmail.com)
 * <
 * Date: Dec 16, 2009
 */
public class QUnitTestCase extends TelluriumJUnitTestCase {
    private static QUnitHttpServer server;
    static{
        setCustomConfig(true, 4444, "*chrome", true, null);
    }

    @BeforeClass
    public static void setUp(){
        server = new QUnitHttpServer(8080);
    }
    
    @AfterClass
    public static void tearDown(){
        server.stop();
    }

    public static void start(){
        server.start();        
    }

    public static void registerHtmlBody(String url, String javascript, String body){
        server.registerHtmlBody(url, javascript, body);
    }

    public static void registerHtml(String url, String html){
        server.registerHtml(url, html);
    }
}
