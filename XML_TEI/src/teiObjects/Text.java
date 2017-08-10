package teiObjects;

import org.w3c.dom.Node;

/**
 * Created by fstiegle on 20.05.2017.
 */
public class Text {
    private Node textNode;

    public Text(Node textNode) {
        this.textNode = textNode;
    }

    public Node getTeiHeaderNode() {
        return textNode;
    }

    public void setTeiHeaderNode(Node teiHeaderNode) {
        this.textNode = teiHeaderNode;
    }
}
