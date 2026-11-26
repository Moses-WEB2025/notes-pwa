package androidx.constraintlayout.core.dsl;

import com.miui.doodle.document.Layer;

/* JADX INFO: loaded from: classes.dex */
public class VChain extends Chain {
    private VAnchor mBaseline;
    private VAnchor mBottom;
    private VAnchor mTop;

    public class VAnchor extends Chain.Anchor {
        VAnchor(Constraint.VSide vSide) {
            super(Constraint.Side.valueOf(vSide.name()));
        }
    }

    public VChain(String str) {
        super(str);
        this.mTop = new VAnchor(Constraint.VSide.TOP);
        this.mBottom = new VAnchor(Constraint.VSide.BOTTOM);
        this.mBaseline = new VAnchor(Constraint.VSide.BASELINE);
        this.type = new Helper.HelperType(typeMap.get(Helper.Type.VERTICAL_CHAIN));
    }

    public VChain(String str, String str2) {
        super(str);
        this.mTop = new VAnchor(Constraint.VSide.TOP);
        this.mBottom = new VAnchor(Constraint.VSide.BOTTOM);
        this.mBaseline = new VAnchor(Constraint.VSide.BASELINE);
        this.config = str2;
        this.type = new Helper.HelperType(typeMap.get(Helper.Type.VERTICAL_CHAIN));
        this.configMap = convertConfigToMap();
        if (this.configMap.containsKey("contains")) {
            Ref.addStringToReferences(this.configMap.get("contains"), this.references);
        }
    }

    public VAnchor getTop() {
        return this.mTop;
    }

    public void linkToTop(Constraint.VAnchor vAnchor) {
        linkToTop(vAnchor, 0);
    }

    public void linkToTop(Constraint.VAnchor vAnchor, int i) {
        linkToTop(vAnchor, i, Integer.MIN_VALUE);
    }

    public void linkToTop(Constraint.VAnchor vAnchor, int i, int i2) {
        this.mTop.mConnection = vAnchor;
        this.mTop.mMargin = i;
        this.mTop.mGoneMargin = i2;
        this.configMap.put(Layer.KEY_TOP, this.mTop.toString());
    }

    public VAnchor getBottom() {
        return this.mBottom;
    }

    public void linkToBottom(Constraint.VAnchor vAnchor) {
        linkToBottom(vAnchor, 0);
    }

    public void linkToBottom(Constraint.VAnchor vAnchor, int i) {
        linkToBottom(vAnchor, i, Integer.MIN_VALUE);
    }

    public void linkToBottom(Constraint.VAnchor vAnchor, int i, int i2) {
        this.mBottom.mConnection = vAnchor;
        this.mBottom.mMargin = i;
        this.mBottom.mGoneMargin = i2;
        this.configMap.put("bottom", this.mBottom.toString());
    }

    public VAnchor getBaseline() {
        return this.mBaseline;
    }

    public void linkToBaseline(Constraint.VAnchor vAnchor) {
        linkToBaseline(vAnchor, 0);
    }

    public void linkToBaseline(Constraint.VAnchor vAnchor, int i) {
        linkToBaseline(vAnchor, i, Integer.MIN_VALUE);
    }

    public void linkToBaseline(Constraint.VAnchor vAnchor, int i, int i2) {
        this.mBaseline.mConnection = vAnchor;
        this.mBaseline.mMargin = i;
        this.mBaseline.mGoneMargin = i2;
        this.configMap.put("baseline", this.mBaseline.toString());
    }
}
