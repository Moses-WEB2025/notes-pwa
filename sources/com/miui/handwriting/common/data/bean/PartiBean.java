package com.miui.handwriting.common.data.bean;

import ch.qos.logback.core.CoreConstants;
import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class PartiBean implements Serializable {
    private static final long serialVersionUID = 9189425552866968481L;
    private List<PartieWords> chars;
    private long id;
    private String improperFraction;
    private String label;
    private List<BoundingBox> line_box;
    private List<MathLabels> mathLabels;
    private String properFraction;
    private int resultCode;
    private String type;
    private int version;
    private List<PartieWords> words;

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public List<PartieWords> getWords() {
        return this.words;
    }

    public void setWords(List<PartieWords> list) {
        this.words = list;
    }

    public List<PartieWords> getChars() {
        return this.chars;
    }

    public void setChars(List<PartieWords> list) {
        this.chars = list;
    }

    public List<BoundingBox> getLine_box() {
        return this.line_box;
    }

    public void setLine_box(List<BoundingBox> list) {
        this.line_box = list;
    }

    public String getImproperFraction() {
        return this.improperFraction;
    }

    public void setImproperFraction(String str) {
        this.improperFraction = str;
    }

    public String getProperFraction() {
        return this.properFraction;
    }

    public void setProperFraction(String str) {
        this.properFraction = str;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int i) {
        this.resultCode = i;
    }

    public List<MathLabels> getMathLabels() {
        return this.mathLabels;
    }

    public void setMathLabels(List<MathLabels> list) {
        this.mathLabels = list;
    }

    public String toString() {
        return "PartiBean{type='" + this.type + "', version=" + this.version + ", id=" + this.id + ", label='" + this.label + "', improperFraction='" + this.improperFraction + "', properFraction='" + this.properFraction + "', resultCode=" + this.resultCode + ", words=" + this.words + ", chars=" + this.chars + ", line_box=" + this.line_box + CoreConstants.CURLY_RIGHT;
    }
}
