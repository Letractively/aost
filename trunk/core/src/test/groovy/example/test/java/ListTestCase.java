package example.test.java;

import example.other.ListModule;
import org.junit.Ignore;
import org.junit.Test;
import org.tellurium.test.java.TelluriumJavaTestCase;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jul 20, 2009
 */
public class ListTestCase  extends TelluriumJavaTestCase {

    @Ignore
    @Test
    public void testGetSeparatorAttribute(){
        ListModule lm = new ListModule();
        lm.defineUi();
        connectUrl("http://localhost:8080/list.html");
        lm.disableJQuerySelector();
        String attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
        System.out.println("XPath: Class attribute for rotator.tnails[6] " + attr);
        lm.useJQuerySelector();
        attr = (String)lm.getParentAttribute("rotator.tnails[6]", "class");
        System.out.println("jQuery: Class attribute for rotator.tnails[6] " + attr);
    }
}
