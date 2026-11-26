package org.ccil.cowan.tagsoup;

import com.market.sdk.utils.Region;

/* JADX INFO: loaded from: classes6.dex */
public class Element {
    private boolean preclosed;
    private AttributesImpl theAtts;
    private Element theNext;
    private ElementType theType;

    public Element(ElementType elementType, boolean z) {
        this.theType = elementType;
        if (z) {
            this.theAtts = new AttributesImpl(elementType.atts());
        } else {
            this.theAtts = new AttributesImpl();
        }
        this.theNext = null;
        this.preclosed = false;
    }

    public ElementType type() {
        return this.theType;
    }

    public AttributesImpl atts() {
        return this.theAtts;
    }

    public Element next() {
        return this.theNext;
    }

    public void setNext(Element element) {
        this.theNext = element;
    }

    public String name() {
        return this.theType.name();
    }

    public String namespace() {
        return this.theType.namespace();
    }

    public String localName() {
        return this.theType.localName();
    }

    public int model() {
        return this.theType.model();
    }

    public int memberOf() {
        return this.theType.memberOf();
    }

    public int flags() {
        return this.theType.flags();
    }

    public ElementType parent() {
        return this.theType.parent();
    }

    public boolean canContain(Element element) {
        return this.theType.canContain(element.theType);
    }

    public void setAttribute(String str, String str2, String str3) {
        this.theType.setAttribute(this.theAtts, str, str2, str3);
    }

    public void anonymize() {
        for (int length = this.theAtts.getLength() - 1; length >= 0; length--) {
            if (this.theAtts.getType(length).equals(Region.ID) || this.theAtts.getQName(length).equals("name")) {
                this.theAtts.removeAttribute(length);
            }
        }
    }

    public void clean() {
        for (int length = this.theAtts.getLength() - 1; length >= 0; length--) {
            String localName = this.theAtts.getLocalName(length);
            if (this.theAtts.getValue(length) == null || localName == null || localName.length() == 0) {
                this.theAtts.removeAttribute(length);
            }
        }
    }

    public void preclose() {
        this.preclosed = true;
    }

    public boolean isPreclosed() {
        return this.preclosed;
    }
}
