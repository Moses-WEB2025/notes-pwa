package com.miui.maml.animation;

import com.miui.maml.elements.ListScreenElement;
import com.miui.maml.elements.ScreenElement;
import com.miui.maml.folme.AnimatedProperty;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public class ScaleAnimation extends BaseAnimation {
    public static final String TAG_NAME = "ScaleAnimation";
    private double mDelayValueX;
    private double mDelayValueY;

    public ScaleAnimation(Element element, ScreenElement screenElement) {
        super(element, ListScreenElement.ListItemElement.TAG_NAME, new String[]{"value", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y}, screenElement);
        BaseAnimation.AnimationItem item = getItem(0);
        this.mDelayValueX = getItemX(item);
        this.mDelayValueY = getItemY(item);
    }

    public final double getScaleX() {
        return getCurValue(1);
    }

    public final double getScaleY() {
        return getCurValue(2);
    }

    @Override // com.miui.maml.animation.BaseAnimation
    protected void onTick(BaseAnimation.AnimationItem animationItem, BaseAnimation.AnimationItem animationItem2, float f) {
        if (animationItem == null && animationItem2 == null) {
            return;
        }
        double itemX = getItemX(animationItem);
        double d = f;
        setCurValue(1, itemX + ((getItemX(animationItem2) - itemX) * d));
        double itemY = getItemY(animationItem);
        setCurValue(2, itemY + ((getItemY(animationItem2) - itemY) * d));
    }

    private double getItemX(BaseAnimation.AnimationItem animationItem) {
        if (animationItem == null) {
            return 1.0d;
        }
        return animationItem.attrExists(0) ? animationItem.get(0) : animationItem.get(1);
    }

    private double getItemY(BaseAnimation.AnimationItem animationItem) {
        if (animationItem == null) {
            return 1.0d;
        }
        return animationItem.attrExists(0) ? animationItem.get(0) : animationItem.get(2);
    }

    @Override // com.miui.maml.animation.BaseAnimation
    protected double getDelayValue(int i) {
        return (i == 0 || i == 1) ? this.mDelayValueX : this.mDelayValueY;
    }
}
