package com.miui.maml.elements;

import android.graphics.Canvas;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.miui.maml.ScreenElementRoot;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class MirrorScreenElement extends AnimatedScreenElement {
    private static final String LOG_TAG = "MirrorScreenElement";
    public static final String TAG_NAME = "Mirror";
    private boolean mMirrorTranslation;
    private ScreenElement mTarget;
    private String mTargetName;

    public MirrorScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mTargetName = element.getAttribute(TypedValues.AttributesType.S_TARGET);
        this.mMirrorTranslation = Boolean.parseBoolean(element.getAttribute("mirrorTranslation"));
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        ScreenElement screenElementFindElement = this.mRoot.findElement(this.mTargetName);
        this.mTarget = screenElementFindElement;
        if (screenElementFindElement == null) {
            Log.e(LOG_TAG, "the target does not exist: " + this.mTargetName);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    protected void doRender(Canvas canvas) {
        ScreenElement screenElement = this.mTarget;
        if (screenElement != null) {
            if (this.mMirrorTranslation && (screenElement instanceof AnimatedScreenElement)) {
                ((AnimatedScreenElement) screenElement).doRenderWithTranslation(canvas);
            } else {
                screenElement.doRender(canvas);
            }
        }
    }
}
