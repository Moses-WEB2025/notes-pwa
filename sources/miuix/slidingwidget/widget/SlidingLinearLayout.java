package miuix.slidingwidget.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import java.util.HashMap;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes6.dex */
public class SlidingLinearLayout extends LinearLayout {
    private final HashMap<View, Pair<Float, Float>> preAddViewPairs;
    private final int[] preLayout;
    private final HashMap<View, Pair<Float, Float>> preRemoveViewPairs;

    public SlidingLinearLayout(Context context) {
        this(context, null);
    }

    public SlidingLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.preAddViewPairs = new HashMap<>();
        this.preRemoveViewPairs = new HashMap<>();
        this.preLayout = new int[4];
        setLayoutTransition(null);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float fFloatValue;
        float fFloatValue2;
        float fFloatValue3;
        float fFloatValue4;
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            int childCount = getChildCount();
            char c = 3;
            char c2 = 2;
            int i5 = 0;
            boolean z2 = getOrientation() != 1 ? Math.abs(this.preLayout[0] - i) > Math.abs(this.preLayout[2] - i3) : Math.abs(this.preLayout[1] - i2) > Math.abs(this.preLayout[3] - i4);
            int i6 = 0;
            while (i6 < childCount) {
                View childAt = getChildAt(i6);
                HashMap<View, Pair<Float, Float>> map = this.preAddViewPairs;
                if (map == null || map.size() <= 0) {
                    c2 = c2;
                    i6 = i6;
                } else {
                    Pair<Float, Float> pair = this.preAddViewPairs.get(childAt);
                    if (pair != null && childAt.getVisibility() != 8) {
                        if ((childAt.getX() != ((Float) pair.first).floatValue() || childAt.getY() != ((Float) pair.second).floatValue()) && !z2) {
                            fFloatValue3 = ((Float) pair.first).floatValue() - childAt.getX();
                            fFloatValue4 = ((Float) pair.second).floatValue() - childAt.getY();
                        } else if (childAt.getX() == ((Float) pair.first).floatValue() && childAt.getY() == ((Float) pair.second).floatValue() && z2) {
                            int[] iArr = this.preLayout;
                            float f = iArr[i5] - i;
                            fFloatValue4 = iArr[1] - i2;
                            fFloatValue3 = f;
                        } else {
                            fFloatValue3 = 0.0f;
                            fFloatValue4 = 0.0f;
                        }
                        int i7 = i5;
                        AnimState animStateAdd = new AnimState("start").add(ViewProperty.TRANSLATION_X, fFloatValue3).add(ViewProperty.TRANSLATION_Y, fFloatValue4);
                        AnimState animStateAdd2 = new AnimState("end").add(ViewProperty.TRANSLATION_X, 0.0d).add(ViewProperty.TRANSLATION_Y, 0.0d);
                        View[] viewArr = new View[1];
                        viewArr[i7] = childAt;
                        Folme.useAt(viewArr).state().setTo(animStateAdd).fromTo(animStateAdd, animStateAdd2, new AnimConfig[i7]);
                    }
                    this.preAddViewPairs.remove(childAt);
                }
                HashMap<View, Pair<Float, Float>> map2 = this.preRemoveViewPairs;
                if (map2 != null && map2.size() > 0) {
                    Pair<Float, Float> pair2 = this.preRemoveViewPairs.get(childAt);
                    if (pair2 != null && childAt.getVisibility() != 8) {
                        if ((childAt.getX() != ((Float) pair2.first).floatValue() || childAt.getY() != ((Float) pair2.second).floatValue()) && !z2) {
                            fFloatValue = ((Float) pair2.first).floatValue() - childAt.getX();
                            fFloatValue2 = ((Float) pair2.second).floatValue() - childAt.getY();
                        } else if (childAt.getX() == ((Float) pair2.first).floatValue() && childAt.getY() == ((Float) pair2.second).floatValue() && z2) {
                            int[] iArr2 = this.preLayout;
                            fFloatValue = iArr2[0] - i;
                            fFloatValue2 = iArr2[1] - i2;
                        } else {
                            fFloatValue2 = 0.0f;
                            fFloatValue = 0.0f;
                        }
                        AnimState animStateAdd3 = new AnimState("start").add(ViewProperty.TRANSLATION_X, fFloatValue).add(ViewProperty.TRANSLATION_Y, fFloatValue2);
                        Folme.useAt(childAt).state().setTo(animStateAdd3).fromTo(animStateAdd3, new AnimState("end").add(ViewProperty.TRANSLATION_X, 0.0d).add(ViewProperty.TRANSLATION_Y, 0.0d), new AnimConfig[0]);
                    }
                    this.preRemoveViewPairs.remove(childAt);
                }
                i6++;
                c = c;
                c2 = c2;
                i5 = 0;
            }
            char c3 = c;
            char c4 = c2;
            HashMap<View, Pair<Float, Float>> map3 = this.preAddViewPairs;
            if (map3 != null) {
                map3.clear();
            }
            HashMap<View, Pair<Float, Float>> map4 = this.preRemoveViewPairs;
            if (map4 != null) {
                map4.clear();
            }
            int[] iArr3 = this.preLayout;
            iArr3[0] = i;
            iArr3[1] = i2;
            iArr3[c4] = i3;
            iArr3[c3] = i4;
        }
    }

    public void addViewSliding(View view) {
        preAddView();
        addView(view);
        show(view);
    }

    public void addViewSliding(View view, int i) {
        preAddView();
        addView(view, i);
        show(view);
    }

    public void addViewSliding(View view, int i, int i2) {
        preAddView();
        addView(view, i, i2);
        show(view);
    }

    public void addViewSliding(View view, LinearLayout.LayoutParams layoutParams) {
        preAddView();
        addView(view, layoutParams);
        show(view);
    }

    public void addViewSliding(View view, int i, LinearLayout.LayoutParams layoutParams) {
        preAddView();
        addView(view, i, layoutParams);
        show(view);
    }

    public void removeViewSliding(final View view) {
        preRemoveView(view);
        Folme.useAt(view).visible().setFlags(1L).setShow().hide(new AnimConfig().addListeners(new TransitionListener() { // from class: miuix.slidingwidget.widget.SlidingLinearLayout.1
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                SlidingLinearLayout.this.removeView(view);
            }
        }));
    }

    public void removeViewSlidingAt(final int i) {
        View childAt = getChildAt(i);
        preRemoveView(childAt);
        Folme.useAt(childAt).visible().setFlags(1L).setShow().hide(new AnimConfig().addListeners(new TransitionListener() { // from class: miuix.slidingwidget.widget.SlidingLinearLayout.2
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                if (i < SlidingLinearLayout.this.getChildCount()) {
                    try {
                        SlidingLinearLayout.this.removeViewAt(i);
                    } catch (Exception e) {
                        Log.e("SlidingLinear", "error in removeViewSlidingAt, index " + i + StringUtils.SPACE + e);
                    }
                }
            }
        }));
    }

    public void removeViewsSliding(int i, int i2) {
        SlidingLinearLayout slidingLinearLayout;
        final int i3;
        final int i4;
        preRemoveView(i, i2);
        final int i5 = i + i2;
        final int i6 = i;
        while (i6 < i5) {
            View childAt = this.getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                slidingLinearLayout = this;
                i3 = i;
                i4 = i2;
                Folme.useAt(childAt).visible().setFlags(1L).setShow().hide(new AnimConfig().addListeners(new TransitionListener() { // from class: miuix.slidingwidget.widget.SlidingLinearLayout.3
                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        if (i6 != i5 - 1 || i3 + i4 > SlidingLinearLayout.this.getChildCount()) {
                            return;
                        }
                        try {
                            SlidingLinearLayout.this.removeViews(i3, i4);
                        } catch (Exception e) {
                            Log.e("SlidingLinear", "error in removeViewsSliding,start " + i3 + " count " + i4 + StringUtils.SPACE + e);
                        }
                    }
                }));
            } else {
                slidingLinearLayout = this;
                i3 = i;
                i4 = i2;
            }
            i6++;
            this = slidingLinearLayout;
            i = i3;
            i2 = i4;
        }
    }

    private void show(View view) {
        Folme.useAt(view).visible().setFlags(1L).setFlags(1L).setShowDelay(100L).setHide().show(new AnimConfig[0]);
    }

    private void preAddView() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            this.preAddViewPairs.put(childAt, new Pair<>(Float.valueOf(childAt.getX()), Float.valueOf(childAt.getY())));
        }
    }

    private void preRemoveView(View view) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (view != childAt && childAt.getVisibility() != 8) {
                this.preRemoveViewPairs.put(childAt, new Pair<>(Float.valueOf(childAt.getX()), Float.valueOf(childAt.getY())));
            }
        }
    }

    private void preRemoveView(int i, int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if ((i3 < i || i3 >= i + i2) && childAt.getVisibility() != 8) {
                this.preRemoveViewPairs.put(childAt, new Pair<>(Float.valueOf(childAt.getX()), Float.valueOf(childAt.getY())));
            }
        }
    }
}
