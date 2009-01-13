package org.tellurium.tool;

import java.util.Comparator;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Jan 12, 2009
 */

public class XPath implements Comparator<XPath> {
    private String xpath;

    private Node node;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public int compare(XPath first, XPath second) {
        return first.getXpath().length() - second.getXpath().length();
    }
}
