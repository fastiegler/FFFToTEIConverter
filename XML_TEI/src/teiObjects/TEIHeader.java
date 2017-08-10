package teiObjects;

import org.w3c.dom.Node;

/**
 * Created by fstiegle on 20.05.2017.
 */
public class TEIHeader {

    private Node teiHeaderNode;

    public TEIHeader(Node headerNode) {
        this.teiHeaderNode = headerNode;
    }

    public Node getTeiHeaderNode() {
        return teiHeaderNode;
    }

    public void setTeiHeaderNode(Node teiHeaderNode) {
        this.teiHeaderNode = teiHeaderNode;
    }
}
