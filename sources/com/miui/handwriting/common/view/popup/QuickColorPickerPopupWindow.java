package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.creation.common.R;
import com.miui.handwriting.common.view.colorpicker.QuickColorArrowBlurBackGroundView;
import com.miui.handwriting.common.view.quickMenu.utils.QuickMenuScale;
import com.miui.handwriting.common.view.quickMenu.utils.ScreenModeHelper;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import miuix.core.util.MiShadowUtils;
import org.apache.commons.lang3.StringUtils;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class QuickColorPickerPopupWindow extends ColorPickerPopupWindow {
    private static final String TAG = "QuickColorPickerPopupWindow";
    private int mAnchorGap;
    private Point mAnchorLocation;
    private View mAnchorView;
    private QuickColorArrowBlurBackGroundView mArrowView;
    private int mPopupWindowHeight;
    private int mPopupWindowWidth;
    private ScreenModeHelper mScreenModeHelper;
    private PopupWindowScrollView mScrollView;
    private int mScrollViewHeight;
    private int mUpdatedWindowHeight;
    private int mViewPagesHeight;
    private ConstraintLayout mWrapper;

    @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow, com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    public void initView() {
        super.initView();
        this.mGridColorPicker.measure(0, 0);
        this.mViewPagesHeight = this.mGridColorPicker.getMeasuredHeight();
        this.mContentView.measure(0, 0);
        this.mPopupWindowHeight = this.mContentView.getMeasuredHeight() + this.mViewPagesHeight;
        this.mPopupWindowWidth = this.mContentView.getMeasuredWidth();
        if (this.mScrollView == null) {
            this.mScrollView = (PopupWindowScrollView) this.mContentView.findViewById(R.id.quick_color_popup_scroll_view);
        }
        this.mScrollView.measure(0, 0);
        this.mScrollViewHeight = this.mScrollView.getMeasuredHeight() + this.mViewPagesHeight;
        this.mArrowView = (QuickColorArrowBlurBackGroundView) getContentView().findViewById(R.id.quick_color_popup_window_arrow_view);
        this.mArrowPopupView.setShadowInset(DisplayUtils.dip2px(this.mContext, 13.0f));
        this.mWrapper = (ConstraintLayout) getContentView().findViewById(R.id.quick_color_popup_wrapper);
        if (MiShadowUtils.SUPPORT_MI_SHADOW) {
            this.mArrowPopupView.enableShadow(false);
            MiShadowUtils.setMiShadow(getContentView().findViewById(R.id.ll_root2), 1157627904, 0.0f, 5.0f, 46.0f);
        }
    }

    public QuickColorPickerPopupWindow(Context context) {
        int i;
        super((Boolean) false, context);
        ScreenModeHelper screenModeHelper = new ScreenModeHelper(context);
        this.mScreenModeHelper = screenModeHelper;
        QuickMenuScale popupColorPickerScale = screenModeHelper.getPopupColorPickerScale();
        this.mLayoutScaleType = popupColorPickerScale;
        if (popupColorPickerScale == QuickMenuScale.ZERO_POINT_EIGHT) {
            i = R.layout.popup_quick_color_picker_zero_point_eight;
        } else if (popupColorPickerScale == QuickMenuScale.ZERO_POINT_NINE) {
            i = R.layout.popup_quick_color_picker_zero_point_nine;
        } else {
            i = R.layout.popup_quick_color_picker;
        }
        initLayout(context, i);
        this.mColors = context.getResources().getIntArray(R.array.creation_colors);
    }

    @Override // com.miui.handwriting.common.view.popup.ArrowPopupWindow
    protected void onPrepareWindow() {
        this.mArrowPopupView.enableArrow(false);
    }

    private void updateScrollViewHeight(int i) {
        PopupWindowScrollView popupWindowScrollView = (PopupWindowScrollView) getContentView().findViewById(R.id.quick_color_popup_scroll_view);
        ViewGroup.LayoutParams layoutParams = popupWindowScrollView.getLayoutParams();
        layoutParams.height = i;
        popupWindowScrollView.setLayoutParams(layoutParams);
    }

    private void updateWrapperHeight(int i) {
        ViewGroup.LayoutParams layoutParams = this.mWrapper.getLayoutParams();
        layoutParams.height = i;
        this.mWrapper.setLayoutParams(layoutParams);
    }

    private int[] computeOffset(int i, int i2) {
        int[] iArr = {0, 0};
        if (i2 == 32) {
            Log.d(TAG, "mode: ARROW_LEFT_MODE");
            iArr[0] = i;
            return iArr;
        }
        if (i2 == 64) {
            Log.d(TAG, "mode: ARROW_RIGHT_MODE");
            iArr[0] = -i;
            return iArr;
        }
        if (i2 == 8) {
            Log.d(TAG, "mode: ARROW_TOP_MODE");
            iArr[1] = i;
            return iArr;
        }
        if (i2 == 16) {
            Log.d(TAG, "mode: ARROW_BOTTOM_MODE");
            iArr[1] = -i;
        }
        return iArr;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0252  */
    /* JADX WARN: Code duplicated, block: B:104:0x025c  */
    /* JADX WARN: Code duplicated, block: B:105:0x0297  */
    /* JADX WARN: Code duplicated, block: B:108:0x02a2  */
    /* JADX WARN: Code duplicated, block: B:111:0x02c2  */
    /* JADX WARN: Code duplicated, block: B:114:0x02e2  */
    /* JADX WARN: Code duplicated, block: B:116:0x02e5  */
    /* JADX WARN: Code duplicated, block: B:82:0x020e  */
    /* JADX WARN: Code duplicated, block: B:87:0x021f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:88:0x0221  */
    /* JADX WARN: Code duplicated, block: B:91:0x022b  */
    /* JADX WARN: Code duplicated, block: B:94:0x023e  */
    /* JADX WARN: Code duplicated, block: B:95:0x0241  */
    public void showAsDropDown(View view, Point point, int i) {
        int i2;
        boolean z;
        int i3;
        int i4;
        float[] fArr;
        int i5;
        int i6;
        int i7;
        QuickColorArrowBlurBackGroundView quickColorArrowBlurBackGroundView;
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        this.mAnchorView = view;
        this.mAnchorGap = i;
        this.mAnchorLocation = point;
        int multiHeight = DisplayUtils.getMultiHeight(Utils.getActivity(this.mContext));
        int windowWidth = DisplayUtils.getWindowWidth(Utils.getActivity(this.mContext));
        int iDip2px = DisplayUtils.dip2px(this.mContext, 30.0f);
        int iDip2px2 = DisplayUtils.dip2px(this.mContext, 10.0f);
        Rect rect = new Rect(iDip2px2, iDip2px, windowWidth - iDip2px2, multiHeight - iDip2px);
        Log.d(TAG, String.format("ActivityWidth: %d, ActivityHeight: %d, getWindowHeight: %d", Integer.valueOf(windowWidth), Integer.valueOf(multiHeight), Integer.valueOf(UIUtils.getWindowHeight(this.mContext))));
        Log.d(TAG, "globalLimitRect: " + rect);
        int width = view.getWidth();
        int i8 = point.x;
        if (DisplayUtils.isRTL()) {
            i8 += 2;
        }
        int i9 = point.y;
        int i10 = i8 + width;
        int i11 = i9 + width;
        Log.d(TAG, "anchorLeft: " + i8 + ", anchorRight: " + i10 + " anchorLeft:" + i8);
        int i12 = ((i10 - i8) / 2) + i8;
        int i13 = ((i11 - i9) / 2) + i9;
        Log.d(TAG, String.format("mPopupWindowHeight: %d, mViewPagesHeight: %d, mScrollViewHeight: %d, anchorSize: %d", Integer.valueOf(this.mPopupWindowHeight), Integer.valueOf(this.mViewPagesHeight), Integer.valueOf(this.mScrollViewHeight), Integer.valueOf(width)));
        Log.d(TAG, String.format("anchorLeft: %d, anchorTop: %d", Integer.valueOf(i8), Integer.valueOf(i9)));
        int i14 = (i8 - this.mPopupWindowWidth) - i;
        int i15 = this.mPopupWindowHeight;
        int i16 = i13 - (i15 / 2);
        int i17 = (i15 / 2) + i13;
        boolean z2 = i14 >= rect.left && i16 >= rect.top && i17 <= rect.bottom;
        boolean z3 = (this.mPopupWindowWidth + i10) + i <= rect.right && i16 >= rect.top && i17 <= rect.bottom;
        int i18 = this.mPopupWindowHeight + i11 + i;
        int i19 = this.mPopupWindowWidth;
        int i20 = i12 - (i19 / 2);
        int i21 = (i19 / 2) + i12;
        boolean z4 = i18 <= rect.bottom && i20 >= rect.left && i21 <= rect.right;
        boolean z5 = (i9 - this.mPopupWindowHeight) - i >= rect.top && i20 >= rect.left && i21 <= rect.right;
        if (z2) {
            i2 = 64;
        } else if (z5) {
            i2 = 16;
        } else if (z3) {
            i2 = 32;
        } else {
            i2 = z4 ? 8 : -1;
        }
        if (!z2 && !z3 && !z5 && !z4) {
            Log.d(TAG, "need updateHeight");
            int iMin = Math.min(i13 - rect.top, rect.bottom - i13) * 2;
            int i22 = (i9 - i) - rect.top;
            int i23 = rect.bottom - (i11 + i);
            boolean z6 = (i8 - i) - this.mPopupWindowWidth >= rect.left;
            boolean z7 = (i10 + i) + this.mPopupWindowWidth <= rect.right;
            if (iMin > Math.max(i22, i23) && (z6 || z7)) {
                if (z6) {
                    i2 = 64;
                }
                if (z7) {
                    i2 = 32;
                }
                int i24 = this.mPopupWindowHeight - iMin;
                this.mUpdatedWindowHeight = iMin;
                updateScrollViewHeight(this.mScrollViewHeight - i24);
                z = true;
            } else if (i23 >= i22) {
                int i25 = this.mPopupWindowHeight;
                int i26 = this.mScrollViewHeight - (i25 - i23);
                if (i23 < i25) {
                    this.mUpdatedWindowHeight = i23;
                    updateScrollViewHeight(i26);
                } else {
                    this.mUpdatedWindowHeight = i25;
                }
                i3 = 8;
                z = true;
            } else {
                int i27 = this.mPopupWindowHeight;
                int i28 = this.mScrollViewHeight - (i27 - i22);
                if (i22 < i27) {
                    this.mUpdatedWindowHeight = i22;
                    updateScrollViewHeight(i28);
                } else {
                    this.mUpdatedWindowHeight = i27;
                }
                z = true;
                i3 = 16;
            }
            if (z) {
                updateWrapperHeight(this.mUpdatedWindowHeight);
            }
            if (i3 != 32 && DisplayUtils.isRTL()) {
                setArrowMode(64);
            } else if (i3 != 64 && DisplayUtils.isRTL()) {
                setArrowMode(32);
            } else {
                setArrowMode(i3);
            }
            int[] iArrComputeOffset = computeOffset(i, i3);
            i4 = iArrComputeOffset[0];
            int i29 = iArrComputeOffset[1];
            fArr = new float[2];
            int i30 = this.mPopupWindowWidth;
            if (z) {
                i5 = this.mUpdatedWindowHeight;
            } else {
                i5 = this.mPopupWindowHeight;
            }
            getAnimationPivot(fArr, i30, i5, i3);
            if (i3 != 8 || i3 == 16) {
                if (i12 - rect.left < this.mPopupWindowWidth / 2) {
                    i4 += rect.left;
                    fArr[0] = fArr[0] - rect.left;
                    Log.d("wagnweitest", i12 + StringUtils.SPACE + rect.left + StringUtils.SPACE + this.mPopupWindowWidth);
                    i6 = 2;
                    i7 = i12 - (rect.left + (this.mPopupWindowWidth / 2));
                } else {
                    i6 = 2;
                    i7 = 0;
                }
                if (rect.right - i12 < this.mPopupWindowWidth / i6) {
                    i4 -= rect.right;
                    fArr[0] = fArr[0] + rect.right;
                    i7 = i12 - (rect.right - (this.mPopupWindowWidth / i6));
                }
            } else {
                i7 = 0;
            }
            this.mArrowPopupView.setFolmeAnimationPivot(fArr[0], fArr[1]);
            quickColorArrowBlurBackGroundView = this.mArrowView;
            if (quickColorArrowBlurBackGroundView != null) {
                quickColorArrowBlurBackGroundView.setMode(i3);
                Log.d("wagnweitest", "arrowOffset: " + i7);
                this.mArrowView.setOffset(i7);
            }
            if (DisplayUtils.isRTL()) {
                i4 = -i4;
            }
            if (!z) {
                updateScrollViewHeight(this.mScrollViewHeight);
                updateWrapperHeight(this.mPopupWindowHeight);
            }
            super.showAsDropDown(view, i4, i29);
        }
        z = false;
        i3 = i2;
        if (z) {
            updateWrapperHeight(this.mUpdatedWindowHeight);
        }
        if (i3 != 32) {
            if (i3 != 64) {
                setArrowMode(i3);
            } else {
                setArrowMode(i3);
            }
        } else if (i3 != 64) {
            setArrowMode(i3);
        } else {
            setArrowMode(i3);
        }
        int[] iArrComputeOffset2 = computeOffset(i, i3);
        i4 = iArrComputeOffset2[0];
        int i210 = iArrComputeOffset2[1];
        fArr = new float[2];
        int i31 = this.mPopupWindowWidth;
        if (z) {
            i5 = this.mUpdatedWindowHeight;
        } else {
            i5 = this.mPopupWindowHeight;
        }
        getAnimationPivot(fArr, i31, i5, i3);
        if (i3 != 8) {
            if (i12 - rect.left < this.mPopupWindowWidth / 2) {
                i4 += rect.left;
                fArr[0] = fArr[0] - rect.left;
                Log.d("wagnweitest", i12 + StringUtils.SPACE + rect.left + StringUtils.SPACE + this.mPopupWindowWidth);
                i6 = 2;
                i7 = i12 - (rect.left + (this.mPopupWindowWidth / 2));
            } else {
                i6 = 2;
                i7 = 0;
            }
            if (rect.right - i12 < this.mPopupWindowWidth / i6) {
                i4 -= rect.right;
                fArr[0] = fArr[0] + rect.right;
                i7 = i12 - (rect.right - (this.mPopupWindowWidth / i6));
            }
        } else {
            if (i12 - rect.left < this.mPopupWindowWidth / 2) {
                i4 += rect.left;
                fArr[0] = fArr[0] - rect.left;
                Log.d("wagnweitest", i12 + StringUtils.SPACE + rect.left + StringUtils.SPACE + this.mPopupWindowWidth);
                i6 = 2;
                i7 = i12 - (rect.left + (this.mPopupWindowWidth / 2));
            } else {
                i6 = 2;
                i7 = 0;
            }
            if (rect.right - i12 < this.mPopupWindowWidth / i6) {
                i4 -= rect.right;
                fArr[0] = fArr[0] + rect.right;
                i7 = i12 - (rect.right - (this.mPopupWindowWidth / i6));
            }
        }
        this.mArrowPopupView.setFolmeAnimationPivot(fArr[0], fArr[1]);
        quickColorArrowBlurBackGroundView = this.mArrowView;
        if (quickColorArrowBlurBackGroundView != null) {
            quickColorArrowBlurBackGroundView.setMode(i3);
            Log.d("wagnweitest", "arrowOffset: " + i7);
            this.mArrowView.setOffset(i7);
        }
        if (DisplayUtils.isRTL()) {
            i4 = -i4;
        }
        if (!z) {
            updateScrollViewHeight(this.mScrollViewHeight);
            updateWrapperHeight(this.mPopupWindowHeight);
        }
        super.showAsDropDown(view, i4, i210);
    }

    private void getAnimationPivot(float[] fArr, int i, int i2, int i3) {
        float f;
        int i4 = i2 / 2;
        int i5 = i / 2;
        float f2 = 0.0f;
        if (i3 == 8) {
            f2 = i5;
            f = 0.0f;
        } else if (i3 == 16) {
            f2 = i5;
            f = i2;
        } else if (i3 == 32) {
            f = i4;
        } else if (i3 != 64) {
            f2 = i4;
            f = i5;
        } else {
            f2 = i;
            f = i4;
        }
        fArr[0] = f2;
        fArr[1] = f;
    }

    @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow
    protected void showInner() {
        View view = this.mAnchorView;
        if (view == null || !view.isAttachedToWindow()) {
            return;
        }
        showAsDropDown(this.mAnchorView, this.mAnchorLocation, this.mAnchorGap);
        setOnDismissListener(null);
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        super.dismiss();
        updateScrollViewHeight(this.mScrollViewHeight);
        updateWrapperHeight(this.mPopupWindowHeight);
        this.mScrollView.scrollTo(0, 0);
    }
}
