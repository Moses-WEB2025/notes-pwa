package com.miui.maml.animation;

import android.text.TextUtils;
import com.miui.maml.elements.ScreenElement;
import com.miui.richeditor.schema.HtmlParser;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class AlphaAnimation extends BaseAnimation {
    public static final String INNER_TAG_NAME = "Alpha";
    public static final String TAG_NAME = "AlphaAnimation";
    private int mDelayValue;

    @Override // com.miui.maml.animation.BaseAnimation
    protected double getDefaultValue() {
        return 255.0d;
    }

    public AlphaAnimation(Element element, ScreenElement screenElement) {
        super(element, INNER_TAG_NAME, HtmlParser.LinkCardElement.TAG, screenElement);
        String attribute = element.getAttribute("delayValue");
        if (!TextUtils.isEmpty(attribute)) {
            try {
                this.mDelayValue = Integer.parseInt(attribute);
            } catch (NumberFormatException unused) {
            }
        } else {
            BaseAnimation.AnimationItem item = getItem(0);
            if (item != null) {
                this.mDelayValue = (int) item.get(0);
            }
        }
    }

    public final int getAlpha() {
        return (int) getCurValue(0);
    }

    @Override // com.miui.maml.animation.BaseAnimation
    protected double getDelayValue(int i) {
        return this.mDelayValue;
    }
}
