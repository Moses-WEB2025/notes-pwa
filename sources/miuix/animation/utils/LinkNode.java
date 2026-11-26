package miuix.animation.utils;

import miuix.animation.utils.LinkNode;

/* JADX INFO: loaded from: classes5.dex */
public class LinkNode<T extends LinkNode> {
    public T next;

    public void addToTail(T t) {
        while (this != t) {
            LinkNode<T> linkNode = this.next;
            if (linkNode == null) {
                this.next = t;
                return;
            }
            this = linkNode;
        }
    }

    public T remove() {
        T t = this.next;
        this.next = null;
        return t;
    }

    public T destroy() {
        while (remove() != null) {
        }
        return null;
    }

    public int size() {
        int i = 0;
        while (true) {
            this = this.next;
            if (this == null) {
                return i;
            }
            i++;
        }
    }
}
