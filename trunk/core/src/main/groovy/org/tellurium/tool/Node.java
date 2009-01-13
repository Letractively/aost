package org.tellurium.tool;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A node for the tree
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 *         Date: Dec 10, 2008
 */
public class Node {
    public static final String TAG = "tag";
    
    private String id;

    //relative xpath
    private String xpath;

    private Map<String, String> attributes = new HashMap<String, String>();

    private Node parent;

    private LinkedList<Node> children = new LinkedList<Node>();

    public void printUI(){
        boolean hasChildren = false;
        if(children.size() > 0)
            hasChildren = true;
        StringBuffer sb = new StringBuffer();
        sb.append(Ui.getType(attributes.get(TAG), hasChildren)).append("(UID: ").append(id).append(", clocator: [:])");
        if(hasChildren)
            sb.append("{");
        System.out.println(sb.toString());
        if(hasChildren){
            for(Node node: children){
                node.printUI();
            }
        }
        if(hasChildren)
            System.out.println("}");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public LinkedList<Node> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<Node> children) {
        this.children = children;
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    public boolean isEmpty(){
        if(children != null && children.size() > 0)
            return true;
        
        return false;
    }

    public void addChild(Node child){
        children.add(child);
    }
    
    public void removeChild(String uid){
        Node child = findChild(uid);
        if(child != null){
            children.remove(child);
        }
    }

    public Node findChild(String uid){
        for(Node current: children){
            if(current.getId().equals(uid)){
                return current;
            }
        }

        return null;
    }
}
