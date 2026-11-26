package com.xiaomi.passport.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.account.passportsdk.account_sso.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public class FlexVerticalLinearLayout extends LinearLayout {
    public static final String TAG = "FVLinearLayout";
    private FlexAdjustStrategy mFinalStrategy;
    private FlexAdjustStrategy mHideSpaceStrategy;
    private List<Pair<Integer, Integer>> mInitialChildVerticalMargin;
    private Set<ViewTreeObserver.OnPreDrawListener> mPreDrawListenerSet;
    private FlexAdjustStrategy mResetStrategy;
    private FlexAdjustStrategy mScaleGapStrategy;

    interface FlexAdjustStrategy {
        boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list);
    }

    private static class FlexAdjustParam {
        public final int requestGapHeight;
        public final int requestTotalHeight;
        public final int visibleHeight;

        private FlexAdjustParam(int i, int i2, int i3) {
            this.requestTotalHeight = i;
            this.requestGapHeight = i2;
            this.visibleHeight = i3;
        }

        public boolean needFlexAdjust() {
            return this.requestTotalHeight > this.visibleHeight;
        }

        public String toString() {
            return "FlexAdjustParam{requestTotalHeight=" + this.requestTotalHeight + ", requestGapHeight=" + this.requestGapHeight + ", visibleHeight=" + this.visibleHeight + CoreConstants.CURLY_RIGHT;
        }
    }

    public FlexVerticalLinearLayout(Context context) {
        super(context);
        this.mPreDrawListenerSet = new HashSet();
        this.mResetStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.1
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                Log.e(FlexVerticalLinearLayout.TAG, "apply resetStrategy");
                for (FlexVerticalLinearLayout flexVerticalLinearLayout : list) {
                    flexVerticalLinearLayout.setupInitialChildVerticalMarginWhenPreDraw();
                    int childCount = flexVerticalLinearLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View childAt = flexVerticalLinearLayout.getChildAt(i);
                        if (childAt instanceof FlexVerticalSpace) {
                            childAt.setVisibility(0);
                        }
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                        layoutParams.topMargin = ((Integer) ((Pair) flexVerticalLinearLayout.mInitialChildVerticalMargin.get(i)).first).intValue();
                        layoutParams.bottomMargin = ((Integer) ((Pair) flexVerticalLinearLayout.mInitialChildVerticalMargin.get(i)).second).intValue();
                        childAt.setLayoutParams(layoutParams);
                    }
                }
                return true;
            }
        };
        this.mHideSpaceStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.2
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                Log.e(FlexVerticalLinearLayout.TAG, "apply hideSpaceStrategy");
                boolean zNeedFlexAdjust = false;
                for (FlexVerticalLinearLayout flexVerticalLinearLayout : list) {
                    flexVerticalLinearLayout.setupInitialChildVerticalMarginWhenPreDraw();
                    FlexAdjustParam flexAdjustParamWhenPreDraw = flexVerticalLinearLayout.getFlexAdjustParamWhenPreDraw();
                    Log.e(FlexVerticalLinearLayout.TAG, "flexAdjustParam " + flexAdjustParamWhenPreDraw);
                    if (flexAdjustParamWhenPreDraw != null) {
                        zNeedFlexAdjust |= flexAdjustParamWhenPreDraw.needFlexAdjust();
                    }
                }
                Log.e(FlexVerticalLinearLayout.TAG, "need flex adjust " + zNeedFlexAdjust);
                if (zNeedFlexAdjust) {
                    for (FlexVerticalLinearLayout flexVerticalLinearLayout2 : list) {
                        int childCount = flexVerticalLinearLayout2.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View childAt = flexVerticalLinearLayout2.getChildAt(i);
                            if (childAt instanceof FlexVerticalSpace) {
                                childAt.setVisibility(8);
                            }
                        }
                    }
                }
                return zNeedFlexAdjust;
            }
        };
        this.mScaleGapStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.3
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                SparseArray<Integer> scaledGap;
                int iIntValue;
                int iIntValue2;
                Log.e(FlexVerticalLinearLayout.TAG, "apply scaleGapStrategy");
                float dimension = FlexVerticalLinearLayout.this.getResources().getDimension(R.dimen.passport_minimum_vertical_spacing);
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                for (FlexVerticalLinearLayout flexVerticalLinearLayout : list) {
                    flexVerticalLinearLayout.setupInitialChildVerticalMarginWhenPreDraw();
                    FlexAdjustParam flexAdjustParamWhenPreDraw = flexVerticalLinearLayout.getFlexAdjustParamWhenPreDraw();
                    Log.e(FlexVerticalLinearLayout.TAG, "flexAdjustParam " + flexAdjustParamWhenPreDraw);
                    if (flexAdjustParamWhenPreDraw != null) {
                        i += flexAdjustParamWhenPreDraw.requestTotalHeight;
                        i2 += flexAdjustParamWhenPreDraw.requestGapHeight;
                        i3 += flexAdjustParamWhenPreDraw.visibleHeight;
                    }
                }
                Log.e(FlexVerticalLinearLayout.TAG, String.format("requestTotalHeight=%d, requestGapHeight=%d, visibleHeight=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
                if (i3 >= i) {
                    return false;
                }
                int i4 = i - i3;
                if (i2 <= i4) {
                    scaledGap = new SparseArray<>();
                } else {
                    scaledGap = getScaledGap(list, i2 - i4, dimension);
                }
                Log.e(FlexVerticalLinearLayout.TAG, "scaledGaps=" + scaledGap);
                for (FlexVerticalLinearLayout flexVerticalLinearLayout2 : list) {
                    int childCount = flexVerticalLinearLayout2.getChildCount();
                    for (int i5 = 0; i5 < childCount; i5++) {
                        View childAt = flexVerticalLinearLayout2.getChildAt(i5);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                        if (scaledGap.get(layoutParams.topMargin) == null) {
                            iIntValue = (int) Math.min(layoutParams.topMargin, dimension);
                        } else {
                            iIntValue = scaledGap.get(layoutParams.topMargin).intValue();
                        }
                        if (scaledGap.get(layoutParams.bottomMargin) == null) {
                            iIntValue2 = (int) Math.min(layoutParams.bottomMargin, dimension);
                        } else {
                            iIntValue2 = scaledGap.get(layoutParams.bottomMargin).intValue();
                        }
                        layoutParams.topMargin = iIntValue;
                        layoutParams.bottomMargin = iIntValue2;
                        childAt.setLayoutParams(layoutParams);
                    }
                }
                return true;
            }

            private SparseArray<Integer> getScaledGap(List<FlexVerticalLinearLayout> list, int i, float f) {
                boolean z;
                int iIntValue;
                SparseArray<Integer> sparseArray = new SparseArray<>();
                ArrayList arrayList = new ArrayList();
                Iterator<FlexVerticalLinearLayout> it = list.iterator();
                int iIntValue2 = 0;
                int i2 = 0;
                while (it.hasNext()) {
                    for (Pair pair : it.next().mInitialChildVerticalMargin) {
                        if (((Integer) pair.first).intValue() <= f) {
                            iIntValue = ((Integer) pair.first).intValue();
                        } else {
                            iIntValue2 += ((Integer) pair.first).intValue();
                            arrayList.add((Integer) pair.first);
                            if (((Integer) pair.second).intValue() <= f) {
                                iIntValue = ((Integer) pair.second).intValue();
                            } else {
                                iIntValue2 += ((Integer) pair.second).intValue();
                                arrayList.add((Integer) pair.second);
                            }
                        }
                        i2 += iIntValue;
                    }
                }
                Collections.sort(arrayList);
                Log.e(FlexVerticalLinearLayout.TAG, "target=" + i + ", unfixed=" + iIntValue2 + ", fixed=" + i2 + ", ints=" + arrayList);
                do {
                    int i3 = (i2 + iIntValue2) - i;
                    if (i3 >= iIntValue2) {
                        break;
                    }
                    float f2 = ((iIntValue2 - i3) * 1.0f) / iIntValue2;
                    Log.e(FlexVerticalLinearLayout.TAG, "scale=" + f2);
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it2 = arrayList.iterator();
                    z = false;
                    while (it2.hasNext()) {
                        int iIntValue3 = ((Integer) it2.next()).intValue();
                        int i4 = (int) (iIntValue3 * f2);
                        if (i4 <= f) {
                            i2 = (int) (i2 + f);
                            iIntValue2 -= iIntValue3;
                            sparseArray.put(iIntValue3, Integer.valueOf((int) f));
                            z = true;
                        } else {
                            arrayList2.add(Integer.valueOf(i4));
                            sparseArray.put(iIntValue3, Integer.valueOf(i4));
                        }
                    }
                    arrayList.clear();
                    arrayList.addAll(arrayList2);
                } while (z);
                return sparseArray;
            }
        };
        this.mFinalStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.4
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                Log.e(FlexVerticalLinearLayout.TAG, "apply finalStrategy");
                Iterator<FlexVerticalLinearLayout> it = list.iterator();
                while (it.hasNext()) {
                    Log.e(FlexVerticalLinearLayout.TAG, "flexAdjustParam " + it.next().getFlexAdjustParamWhenPreDraw());
                }
                return true;
            }
        };
        init(context);
    }

    public FlexVerticalLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPreDrawListenerSet = new HashSet();
        this.mResetStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.1
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                Log.e(FlexVerticalLinearLayout.TAG, "apply resetStrategy");
                for (FlexVerticalLinearLayout flexVerticalLinearLayout : list) {
                    flexVerticalLinearLayout.setupInitialChildVerticalMarginWhenPreDraw();
                    int childCount = flexVerticalLinearLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View childAt = flexVerticalLinearLayout.getChildAt(i);
                        if (childAt instanceof FlexVerticalSpace) {
                            childAt.setVisibility(0);
                        }
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                        layoutParams.topMargin = ((Integer) ((Pair) flexVerticalLinearLayout.mInitialChildVerticalMargin.get(i)).first).intValue();
                        layoutParams.bottomMargin = ((Integer) ((Pair) flexVerticalLinearLayout.mInitialChildVerticalMargin.get(i)).second).intValue();
                        childAt.setLayoutParams(layoutParams);
                    }
                }
                return true;
            }
        };
        this.mHideSpaceStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.2
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                Log.e(FlexVerticalLinearLayout.TAG, "apply hideSpaceStrategy");
                boolean zNeedFlexAdjust = false;
                for (FlexVerticalLinearLayout flexVerticalLinearLayout : list) {
                    flexVerticalLinearLayout.setupInitialChildVerticalMarginWhenPreDraw();
                    FlexAdjustParam flexAdjustParamWhenPreDraw = flexVerticalLinearLayout.getFlexAdjustParamWhenPreDraw();
                    Log.e(FlexVerticalLinearLayout.TAG, "flexAdjustParam " + flexAdjustParamWhenPreDraw);
                    if (flexAdjustParamWhenPreDraw != null) {
                        zNeedFlexAdjust |= flexAdjustParamWhenPreDraw.needFlexAdjust();
                    }
                }
                Log.e(FlexVerticalLinearLayout.TAG, "need flex adjust " + zNeedFlexAdjust);
                if (zNeedFlexAdjust) {
                    for (FlexVerticalLinearLayout flexVerticalLinearLayout2 : list) {
                        int childCount = flexVerticalLinearLayout2.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View childAt = flexVerticalLinearLayout2.getChildAt(i);
                            if (childAt instanceof FlexVerticalSpace) {
                                childAt.setVisibility(8);
                            }
                        }
                    }
                }
                return zNeedFlexAdjust;
            }
        };
        this.mScaleGapStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.3
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                SparseArray<Integer> scaledGap;
                int iIntValue;
                int iIntValue2;
                Log.e(FlexVerticalLinearLayout.TAG, "apply scaleGapStrategy");
                float dimension = FlexVerticalLinearLayout.this.getResources().getDimension(R.dimen.passport_minimum_vertical_spacing);
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                for (FlexVerticalLinearLayout flexVerticalLinearLayout : list) {
                    flexVerticalLinearLayout.setupInitialChildVerticalMarginWhenPreDraw();
                    FlexAdjustParam flexAdjustParamWhenPreDraw = flexVerticalLinearLayout.getFlexAdjustParamWhenPreDraw();
                    Log.e(FlexVerticalLinearLayout.TAG, "flexAdjustParam " + flexAdjustParamWhenPreDraw);
                    if (flexAdjustParamWhenPreDraw != null) {
                        i += flexAdjustParamWhenPreDraw.requestTotalHeight;
                        i2 += flexAdjustParamWhenPreDraw.requestGapHeight;
                        i3 += flexAdjustParamWhenPreDraw.visibleHeight;
                    }
                }
                Log.e(FlexVerticalLinearLayout.TAG, String.format("requestTotalHeight=%d, requestGapHeight=%d, visibleHeight=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
                if (i3 >= i) {
                    return false;
                }
                int i4 = i - i3;
                if (i2 <= i4) {
                    scaledGap = new SparseArray<>();
                } else {
                    scaledGap = getScaledGap(list, i2 - i4, dimension);
                }
                Log.e(FlexVerticalLinearLayout.TAG, "scaledGaps=" + scaledGap);
                for (FlexVerticalLinearLayout flexVerticalLinearLayout2 : list) {
                    int childCount = flexVerticalLinearLayout2.getChildCount();
                    for (int i5 = 0; i5 < childCount; i5++) {
                        View childAt = flexVerticalLinearLayout2.getChildAt(i5);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                        if (scaledGap.get(layoutParams.topMargin) == null) {
                            iIntValue = (int) Math.min(layoutParams.topMargin, dimension);
                        } else {
                            iIntValue = scaledGap.get(layoutParams.topMargin).intValue();
                        }
                        if (scaledGap.get(layoutParams.bottomMargin) == null) {
                            iIntValue2 = (int) Math.min(layoutParams.bottomMargin, dimension);
                        } else {
                            iIntValue2 = scaledGap.get(layoutParams.bottomMargin).intValue();
                        }
                        layoutParams.topMargin = iIntValue;
                        layoutParams.bottomMargin = iIntValue2;
                        childAt.setLayoutParams(layoutParams);
                    }
                }
                return true;
            }

            private SparseArray<Integer> getScaledGap(List<FlexVerticalLinearLayout> list, int i, float f) {
                boolean z;
                int iIntValue;
                SparseArray<Integer> sparseArray = new SparseArray<>();
                ArrayList arrayList = new ArrayList();
                Iterator<FlexVerticalLinearLayout> it = list.iterator();
                int iIntValue2 = 0;
                int i2 = 0;
                while (it.hasNext()) {
                    for (Pair pair : it.next().mInitialChildVerticalMargin) {
                        if (((Integer) pair.first).intValue() <= f) {
                            iIntValue = ((Integer) pair.first).intValue();
                        } else {
                            iIntValue2 += ((Integer) pair.first).intValue();
                            arrayList.add((Integer) pair.first);
                            if (((Integer) pair.second).intValue() <= f) {
                                iIntValue = ((Integer) pair.second).intValue();
                            } else {
                                iIntValue2 += ((Integer) pair.second).intValue();
                                arrayList.add((Integer) pair.second);
                            }
                        }
                        i2 += iIntValue;
                    }
                }
                Collections.sort(arrayList);
                Log.e(FlexVerticalLinearLayout.TAG, "target=" + i + ", unfixed=" + iIntValue2 + ", fixed=" + i2 + ", ints=" + arrayList);
                do {
                    int i3 = (i2 + iIntValue2) - i;
                    if (i3 >= iIntValue2) {
                        break;
                    }
                    float f2 = ((iIntValue2 - i3) * 1.0f) / iIntValue2;
                    Log.e(FlexVerticalLinearLayout.TAG, "scale=" + f2);
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it2 = arrayList.iterator();
                    z = false;
                    while (it2.hasNext()) {
                        int iIntValue3 = ((Integer) it2.next()).intValue();
                        int i4 = (int) (iIntValue3 * f2);
                        if (i4 <= f) {
                            i2 = (int) (i2 + f);
                            iIntValue2 -= iIntValue3;
                            sparseArray.put(iIntValue3, Integer.valueOf((int) f));
                            z = true;
                        } else {
                            arrayList2.add(Integer.valueOf(i4));
                            sparseArray.put(iIntValue3, Integer.valueOf(i4));
                        }
                    }
                    arrayList.clear();
                    arrayList.addAll(arrayList2);
                } while (z);
                return sparseArray;
            }
        };
        this.mFinalStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.4
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                Log.e(FlexVerticalLinearLayout.TAG, "apply finalStrategy");
                Iterator<FlexVerticalLinearLayout> it = list.iterator();
                while (it.hasNext()) {
                    Log.e(FlexVerticalLinearLayout.TAG, "flexAdjustParam " + it.next().getFlexAdjustParamWhenPreDraw());
                }
                return true;
            }
        };
        init(context);
    }

    public FlexVerticalLinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPreDrawListenerSet = new HashSet();
        this.mResetStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.1
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                Log.e(FlexVerticalLinearLayout.TAG, "apply resetStrategy");
                for (FlexVerticalLinearLayout flexVerticalLinearLayout : list) {
                    flexVerticalLinearLayout.setupInitialChildVerticalMarginWhenPreDraw();
                    int childCount = flexVerticalLinearLayout.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = flexVerticalLinearLayout.getChildAt(i2);
                        if (childAt instanceof FlexVerticalSpace) {
                            childAt.setVisibility(0);
                        }
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                        layoutParams.topMargin = ((Integer) ((Pair) flexVerticalLinearLayout.mInitialChildVerticalMargin.get(i2)).first).intValue();
                        layoutParams.bottomMargin = ((Integer) ((Pair) flexVerticalLinearLayout.mInitialChildVerticalMargin.get(i2)).second).intValue();
                        childAt.setLayoutParams(layoutParams);
                    }
                }
                return true;
            }
        };
        this.mHideSpaceStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.2
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                Log.e(FlexVerticalLinearLayout.TAG, "apply hideSpaceStrategy");
                boolean zNeedFlexAdjust = false;
                for (FlexVerticalLinearLayout flexVerticalLinearLayout : list) {
                    flexVerticalLinearLayout.setupInitialChildVerticalMarginWhenPreDraw();
                    FlexAdjustParam flexAdjustParamWhenPreDraw = flexVerticalLinearLayout.getFlexAdjustParamWhenPreDraw();
                    Log.e(FlexVerticalLinearLayout.TAG, "flexAdjustParam " + flexAdjustParamWhenPreDraw);
                    if (flexAdjustParamWhenPreDraw != null) {
                        zNeedFlexAdjust |= flexAdjustParamWhenPreDraw.needFlexAdjust();
                    }
                }
                Log.e(FlexVerticalLinearLayout.TAG, "need flex adjust " + zNeedFlexAdjust);
                if (zNeedFlexAdjust) {
                    for (FlexVerticalLinearLayout flexVerticalLinearLayout2 : list) {
                        int childCount = flexVerticalLinearLayout2.getChildCount();
                        for (int i2 = 0; i2 < childCount; i2++) {
                            View childAt = flexVerticalLinearLayout2.getChildAt(i2);
                            if (childAt instanceof FlexVerticalSpace) {
                                childAt.setVisibility(8);
                            }
                        }
                    }
                }
                return zNeedFlexAdjust;
            }
        };
        this.mScaleGapStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.3
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                SparseArray<Integer> scaledGap;
                int iIntValue;
                int iIntValue2;
                Log.e(FlexVerticalLinearLayout.TAG, "apply scaleGapStrategy");
                float dimension = FlexVerticalLinearLayout.this.getResources().getDimension(R.dimen.passport_minimum_vertical_spacing);
                int i2 = 0;
                int i3 = 0;
                int i4 = 0;
                for (FlexVerticalLinearLayout flexVerticalLinearLayout : list) {
                    flexVerticalLinearLayout.setupInitialChildVerticalMarginWhenPreDraw();
                    FlexAdjustParam flexAdjustParamWhenPreDraw = flexVerticalLinearLayout.getFlexAdjustParamWhenPreDraw();
                    Log.e(FlexVerticalLinearLayout.TAG, "flexAdjustParam " + flexAdjustParamWhenPreDraw);
                    if (flexAdjustParamWhenPreDraw != null) {
                        i2 += flexAdjustParamWhenPreDraw.requestTotalHeight;
                        i3 += flexAdjustParamWhenPreDraw.requestGapHeight;
                        i4 += flexAdjustParamWhenPreDraw.visibleHeight;
                    }
                }
                Log.e(FlexVerticalLinearLayout.TAG, String.format("requestTotalHeight=%d, requestGapHeight=%d, visibleHeight=%d", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)));
                if (i4 >= i2) {
                    return false;
                }
                int i5 = i2 - i4;
                if (i3 <= i5) {
                    scaledGap = new SparseArray<>();
                } else {
                    scaledGap = getScaledGap(list, i3 - i5, dimension);
                }
                Log.e(FlexVerticalLinearLayout.TAG, "scaledGaps=" + scaledGap);
                for (FlexVerticalLinearLayout flexVerticalLinearLayout2 : list) {
                    int childCount = flexVerticalLinearLayout2.getChildCount();
                    for (int i6 = 0; i6 < childCount; i6++) {
                        View childAt = flexVerticalLinearLayout2.getChildAt(i6);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                        if (scaledGap.get(layoutParams.topMargin) == null) {
                            iIntValue = (int) Math.min(layoutParams.topMargin, dimension);
                        } else {
                            iIntValue = scaledGap.get(layoutParams.topMargin).intValue();
                        }
                        if (scaledGap.get(layoutParams.bottomMargin) == null) {
                            iIntValue2 = (int) Math.min(layoutParams.bottomMargin, dimension);
                        } else {
                            iIntValue2 = scaledGap.get(layoutParams.bottomMargin).intValue();
                        }
                        layoutParams.topMargin = iIntValue;
                        layoutParams.bottomMargin = iIntValue2;
                        childAt.setLayoutParams(layoutParams);
                    }
                }
                return true;
            }

            private SparseArray<Integer> getScaledGap(List<FlexVerticalLinearLayout> list, int i2, float f) {
                boolean z;
                int iIntValue;
                SparseArray<Integer> sparseArray = new SparseArray<>();
                ArrayList arrayList = new ArrayList();
                Iterator<FlexVerticalLinearLayout> it = list.iterator();
                int iIntValue2 = 0;
                int i3 = 0;
                while (it.hasNext()) {
                    for (Pair pair : it.next().mInitialChildVerticalMargin) {
                        if (((Integer) pair.first).intValue() <= f) {
                            iIntValue = ((Integer) pair.first).intValue();
                        } else {
                            iIntValue2 += ((Integer) pair.first).intValue();
                            arrayList.add((Integer) pair.first);
                            if (((Integer) pair.second).intValue() <= f) {
                                iIntValue = ((Integer) pair.second).intValue();
                            } else {
                                iIntValue2 += ((Integer) pair.second).intValue();
                                arrayList.add((Integer) pair.second);
                            }
                        }
                        i3 += iIntValue;
                    }
                }
                Collections.sort(arrayList);
                Log.e(FlexVerticalLinearLayout.TAG, "target=" + i2 + ", unfixed=" + iIntValue2 + ", fixed=" + i3 + ", ints=" + arrayList);
                do {
                    int i4 = (i3 + iIntValue2) - i2;
                    if (i4 >= iIntValue2) {
                        break;
                    }
                    float f2 = ((iIntValue2 - i4) * 1.0f) / iIntValue2;
                    Log.e(FlexVerticalLinearLayout.TAG, "scale=" + f2);
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it2 = arrayList.iterator();
                    z = false;
                    while (it2.hasNext()) {
                        int iIntValue3 = ((Integer) it2.next()).intValue();
                        int i5 = (int) (iIntValue3 * f2);
                        if (i5 <= f) {
                            i3 = (int) (i3 + f);
                            iIntValue2 -= iIntValue3;
                            sparseArray.put(iIntValue3, Integer.valueOf((int) f));
                            z = true;
                        } else {
                            arrayList2.add(Integer.valueOf(i5));
                            sparseArray.put(iIntValue3, Integer.valueOf(i5));
                        }
                    }
                    arrayList.clear();
                    arrayList.addAll(arrayList2);
                } while (z);
                return sparseArray;
            }
        };
        this.mFinalStrategy = new FlexAdjustStrategy() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.4
            @Override // com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.FlexAdjustStrategy
            public boolean applyAdjustStrategy(List<FlexVerticalLinearLayout> list) {
                Log.e(FlexVerticalLinearLayout.TAG, "apply finalStrategy");
                Iterator<FlexVerticalLinearLayout> it = list.iterator();
                while (it.hasNext()) {
                    Log.e(FlexVerticalLinearLayout.TAG, "flexAdjustParam " + it.next().getFlexAdjustParamWhenPreDraw());
                }
                return true;
            }
        };
        init(context);
    }

    private void init(Context context) {
        setOrientation(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        flexAdjustAllInstancesInViewTree(getAllInstancesInViewTree(), this.mResetStrategy, this.mHideSpaceStrategy, this.mScaleGapStrategy);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        Iterator<ViewTreeObserver.OnPreDrawListener> it = this.mPreDrawListenerSet.iterator();
        while (it.hasNext()) {
            getViewTreeObserver().removeOnPreDrawListener(it.next());
        }
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupInitialChildVerticalMarginWhenPreDraw() {
        if (this.mInitialChildVerticalMargin != null) {
            return;
        }
        this.mInitialChildVerticalMargin = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getChildAt(i).getLayoutParams();
            this.mInitialChildVerticalMargin.add(new Pair<>(Integer.valueOf(layoutParams.topMargin), Integer.valueOf(layoutParams.bottomMargin)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FlexAdjustParam getFlexAdjustParamWhenPreDraw() {
        if (this.mInitialChildVerticalMargin == null) {
            setupInitialChildVerticalMarginWhenPreDraw();
        }
        int measuredHeight = getMeasuredHeight();
        int iIntValue = 0;
        if (measuredHeight == 0) {
            return new FlexAdjustParam(iIntValue, iIntValue, iIntValue);
        }
        for (Pair<Integer, Integer> pair : this.mInitialChildVerticalMargin) {
            iIntValue += ((Integer) pair.first).intValue() + ((Integer) pair.second).intValue();
        }
        ViewGroup viewGroup = (ViewGroup) getParent();
        int measuredHeight2 = measuredHeight;
        while (viewGroup != null) {
            if (measuredHeight2 > viewGroup.getMeasuredHeight()) {
                measuredHeight2 = viewGroup.getMeasuredHeight();
            }
            viewGroup = viewGroup.getParent() instanceof ViewGroup ? (ViewGroup) viewGroup.getParent() : null;
        }
        return new FlexAdjustParam(measuredHeight, iIntValue, measuredHeight2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v1, types: [android.view.ViewGroup, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7 */
    private LinkedList<FlexVerticalLinearLayout> getAllInstancesInViewTree() {
        ?? r6 = this;
        while (r6.getParent() instanceof ViewGroup) {
            r6 = (ViewGroup) r6.getParent();
        }
        LinkedList<FlexVerticalLinearLayout> linkedList = new LinkedList<>();
        LinkedList<View> linkedList2 = new LinkedList();
        LinkedList linkedList3 = new LinkedList();
        linkedList2.add(r6);
        while (!linkedList2.isEmpty()) {
            for (View view : linkedList2) {
                if (view instanceof FlexVerticalLinearLayout) {
                    linkedList.add((FlexVerticalLinearLayout) view);
                }
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        linkedList3.add(viewGroup.getChildAt(i));
                    }
                }
            }
            linkedList2.clear();
            linkedList2.addAll(linkedList3);
            linkedList3.clear();
        }
        return linkedList;
    }

    private void flexAdjustAllInstancesInViewTree(List<FlexVerticalLinearLayout> list, FlexAdjustStrategy... flexAdjustStrategyArr) {
        registerAndApplyStrategy(list, 0, flexAdjustStrategyArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerAndApplyStrategy(final List<FlexVerticalLinearLayout> list, final int i, final FlexAdjustStrategy... flexAdjustStrategyArr) {
        if (i == flexAdjustStrategyArr.length) {
            return;
        }
        ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.xiaomi.passport.ui.view.FlexVerticalLinearLayout.5
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                FlexVerticalLinearLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                FlexVerticalLinearLayout.this.mPreDrawListenerSet.remove(this);
                if (!flexAdjustStrategyArr[i].applyAdjustStrategy(list)) {
                    return true;
                }
                FlexVerticalLinearLayout.this.registerAndApplyStrategy(list, i + 1, flexAdjustStrategyArr);
                return false;
            }
        };
        getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
        this.mPreDrawListenerSet.add(onPreDrawListener);
    }
}
