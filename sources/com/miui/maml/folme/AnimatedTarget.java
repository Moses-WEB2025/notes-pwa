package com.miui.maml.folme;

import com.miui.maml.elements.AnimatedScreenElement;
import com.miui.maml.elements.GeometryScreenElement;
import com.miui.maml.elements.TextScreenElement;
import com.miui.maml.elements.VirtualAnimatedScreenElement;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import miuix.animation.IAnimTarget;
import miuix.animation.ITargetCreator;
import miuix.animation.property.FloatProperty;

/* JADX INFO: loaded from: classes3.dex */
public class AnimatedTarget extends IAnimTarget<AnimatedScreenElement> {
    public static final String STATE_TAG_FROM = "from";
    public static final String STATE_TAG_SET_TO = "setTo";
    public static final String STATE_TAG_TO = "to";
    public static ITargetCreator<AnimatedScreenElement> sCreator;
    private WeakReference<AnimatedScreenElement> mElementRef;
    public static ConcurrentHashMap<FloatProperty, Integer> sPropertyTypeMap = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, FloatProperty> sPropertyMap = new ConcurrentHashMap<>();

    @Override // miuix.animation.IAnimTarget
    public void clean() {
    }

    @Override // miuix.animation.IAnimTarget
    public float getDefaultMinVisible() {
        return 1.0f;
    }

    static {
        sPropertyTypeMap.put(AnimatedProperty.X, 0);
        sPropertyTypeMap.put(AnimatedProperty.Y, 1);
        sPropertyTypeMap.put(AnimatedProperty.SCALE_X, 2);
        sPropertyTypeMap.put(AnimatedProperty.SCALE_Y, 3);
        sPropertyTypeMap.put(AnimatedProperty.ALPHA, 4);
        sPropertyTypeMap.put(AnimatedProperty.HEIGHT, 5);
        sPropertyTypeMap.put(AnimatedProperty.WIDTH, 6);
        sPropertyTypeMap.put(AnimatedProperty.ROTATION, 9);
        sPropertyTypeMap.put(AnimatedProperty.ROTATION_X, 10);
        sPropertyTypeMap.put(AnimatedProperty.ROTATION_Y, 11);
        sPropertyTypeMap.put(AnimatedProperty.TINT_COLOR, 1008);
        sPropertyTypeMap.put(AnimatedProperty.PIVOT_X, 1009);
        sPropertyTypeMap.put(AnimatedProperty.PIVOT_Y, 1010);
        sPropertyTypeMap.put(AnimatedProperty.PIVOT_Z, 1011);
        sPropertyMap.put(0, AnimatedProperty.X);
        sPropertyMap.put(1, AnimatedProperty.Y);
        sPropertyMap.put(2, AnimatedProperty.SCALE_X);
        sPropertyMap.put(3, AnimatedProperty.SCALE_Y);
        sPropertyMap.put(4, AnimatedProperty.ALPHA);
        sPropertyMap.put(5, AnimatedProperty.HEIGHT);
        sPropertyMap.put(6, AnimatedProperty.WIDTH);
        sPropertyMap.put(9, AnimatedProperty.ROTATION);
        sPropertyMap.put(10, AnimatedProperty.ROTATION_X);
        sPropertyMap.put(11, AnimatedProperty.ROTATION_Y);
        sPropertyMap.put(1008, AnimatedProperty.TINT_COLOR);
        sPropertyMap.put(1009, AnimatedProperty.PIVOT_X);
        sPropertyMap.put(1010, AnimatedProperty.PIVOT_Y);
        sPropertyMap.put(1011, AnimatedProperty.PIVOT_Z);
        sCreator = new ITargetCreator<AnimatedScreenElement>() { // from class: com.miui.maml.folme.AnimatedTarget.1
            @Override // miuix.animation.ITargetCreator
            public IAnimTarget createTarget(AnimatedScreenElement animatedScreenElement) {
                return new AnimatedTarget(animatedScreenElement);
            }
        };
    }

    public AnimatedTarget(AnimatedScreenElement animatedScreenElement) {
        setMinVisibleChange(0.00390625f, TextScreenElement.TEXT_COLOR, GeometryScreenElement.FILL_COLOR, GeometryScreenElement.STROKE_COLOR, AnimatedProperty.TINT_COLOR, TextScreenElement.TEXT_SHADOW_COLOR, VirtualAnimatedScreenElement.COLOR_1, VirtualAnimatedScreenElement.COLOR_2);
        this.mElementRef = new WeakReference<>(animatedScreenElement);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // miuix.animation.IAnimTarget
    public AnimatedScreenElement getTargetObject() {
        return this.mElementRef.get();
    }

    @Override // miuix.animation.IAnimTarget
    public void getLocationOnScreen(int[] iArr) {
        AnimatedScreenElement animatedScreenElement = this.mElementRef.get();
        if (animatedScreenElement == null || iArr == null || iArr.length <= 1) {
            return;
        }
        iArr[0] = (int) animatedScreenElement.getAbsoluteLeft();
        iArr[1] = (int) animatedScreenElement.getAbsoluteTop();
    }

    @Override // miuix.animation.IAnimTarget
    public boolean isValid() {
        return this.mElementRef.get() != null;
    }

    @Override // miuix.animation.IAnimTarget
    public void executeOnInitialized(Runnable runnable) {
        if (this.mElementRef.get() != null) {
            runnable.run();
        }
    }
}
