package com.miui.handwriting.common.data.bean;

import ch.qos.logback.core.CoreConstants;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class PartieWords implements Serializable {
    private static final long serialVersionUID = -9169297841178215713L;
    private BoundingBox bounding_box;
    private int id;
    private String label;

    public PartieWords(String str, int i) {
        this.label = str;
        this.id = i;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public BoundingBox getBounding_box() {
        return this.bounding_box;
    }

    public void setBounding_box(BoundingBox boundingBox) {
        this.bounding_box = boundingBox;
    }

    public String toString() {
        return "PartieWords{label='" + this.label + "', id=" + this.id + ", boundingBox=" + this.bounding_box + CoreConstants.CURLY_RIGHT;
    }
}
