package ch.qos.logback.core.pattern.parser;

/* JADX INFO: loaded from: classes.dex */
public class Node {
    static final int COMPOSITE_KEYWORD = 2;
    static final int LITERAL = 0;
    static final int SIMPLE_KEYWORD = 1;
    Node next;
    final int type;
    final Object value;

    Node(int i) {
        this(i, null);
    }

    Node(int i, Object obj) {
        this.type = i;
        this.value = obj;
    }

    public boolean equals(Object obj) {
        Object obj2;
        Node node;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Node)) {
            return false;
        }
        Node node2 = (Node) obj;
        return this.type == node2.type && ((obj2 = this.value) == null ? node2.value == null : obj2.equals(node2.value)) && ((node = this.next) == null ? node2.next == null : node.equals(node2.next));
    }

    public Node getNext() {
        return this.next;
    }

    public int getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }

    public int hashCode() {
        int i = this.type * 31;
        Object obj = this.value;
        return i + (obj != null ? obj.hashCode() : 0);
    }

    String printNext() {
        return this.next != null ? " -> " + this.next : "";
    }

    public void setNext(Node node) {
        this.next = node;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.type != 0 ? super.toString() : "LITERAL(" + this.value + ")");
        stringBuffer.append(printNext());
        return stringBuffer.toString();
    }
}
