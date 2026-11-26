package org.ccil.cowan.tagsoup;

import java.util.HashMap;

/* JADX INFO: loaded from: classes6.dex */
public abstract class Schema {
    public static final int F_CDATA = 2;
    public static final int F_NOFORCE = 4;
    public static final int F_RESTART = 1;
    public static final int M_ANY = -1;
    public static final int M_EMPTY = 0;
    public static final int M_PCDATA = 1073741824;
    public static final int M_ROOT = Integer.MIN_VALUE;
    private HashMap theEntities = new HashMap();
    private HashMap theElementTypes = new HashMap();
    private String theURI = "";
    private String thePrefix = "";
    private ElementType theRoot = null;

    public void elementType(String str, int i, int i2, int i3) {
        ElementType elementType = new ElementType(str, i, i2, i3, this);
        this.theElementTypes.put(str.toLowerCase(), elementType);
        if (i2 == Integer.MIN_VALUE) {
            this.theRoot = elementType;
        }
    }

    public ElementType rootElementType() {
        return this.theRoot;
    }

    public void attribute(String str, String str2, String str3, String str4) {
        ElementType elementType = getElementType(str);
        if (elementType == null) {
            throw new Error("Attribute " + str2 + " specified for unknown element type " + str);
        }
        elementType.setAttribute(str2, str3, str4);
    }

    public void parent(String str, String str2) {
        ElementType elementType = getElementType(str);
        ElementType elementType2 = getElementType(str2);
        if (elementType == null) {
            throw new Error("No child " + str + " for parent " + str2);
        }
        if (elementType2 == null) {
            throw new Error("No parent " + str2 + " for child " + str);
        }
        elementType.setParent(elementType2);
    }

    public void entity(String str, int i) {
        this.theEntities.put(str, new Integer(i));
    }

    public ElementType getElementType(String str) {
        return (ElementType) this.theElementTypes.get(str.toLowerCase());
    }

    public int getEntity(String str) {
        Integer num = (Integer) this.theEntities.get(str);
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public String getURI() {
        return this.theURI;
    }

    public String getPrefix() {
        return this.thePrefix;
    }

    public void setURI(String str) {
        this.theURI = str;
    }

    public void setPrefix(String str) {
        this.thePrefix = str;
    }
}
