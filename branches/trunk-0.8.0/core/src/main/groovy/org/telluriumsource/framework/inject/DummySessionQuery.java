package org.telluriumsource.framework.inject;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Oct 14, 2010
 */
public class DummySessionQuery implements SessionQuery {

    private static final String DEFAULT = "default";

    public String getCurrentSessionId() {
        return DEFAULT;
    }
}
