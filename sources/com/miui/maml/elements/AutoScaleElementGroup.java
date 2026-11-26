package com.miui.maml.elements;

import com.miui.maml.ScreenElementRoot;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class AutoScaleElementGroup extends ElementGroup {
    public static final String TAG_NAME = "AutoScaleGroup";
    private float mInitRawHeight;
    private float mInitRawWidth;

    public AutoScaleElementGroup(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
    }

    @Override // com.miui.maml.elements.ElementGroup, com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        this.mInitRawWidth = getWidthRaw();
        this.mInitRawHeight = getHeightRaw();
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public float getScaleX() {
        float widthRaw = getWidthRaw();
        float f = this.mInitRawWidth;
        if (f > 0.0f && widthRaw > 0.0f) {
            return (widthRaw / f) * super.getScaleX();
        }
        return super.getScaleX();
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public float getScaleY() {
        float heightRaw = getHeightRaw();
        float f = this.mInitRawHeight;
        if (f > 0.0f && heightRaw > 0.0f) {
            return (heightRaw / f) * super.getScaleY();
        }
        return super.getScaleY();
    }
}
