package org.telluriumsource.test;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.telluriumsource.misc.Trie;
import org.telluriumsource.misc.Node;

/**
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Dec 21, 2009
 */
public class Trie_UT {

    @Test
    public void testInsert(){
        String[] dictionary = {"a", "an", "and", "andy", "bo", "body", "bodyguard", "some", "someday", "goodluck", "joke"};
        Trie trie = new Trie();
        trie.buildTree(dictionary);
        trie.checkAndIndexLevel();
        trie.printMe();
        Node deepest = trie.getDeepestNode();
        assertNotNull(deepest);
        System.out.println("deepest word: " + deepest.getFullWord() + ", level: " + deepest.getLevel());
        
    }
    
}
