package com.miui.libhandwritingpage.adapter;

import android.content.Context;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;
import com.miui.libhandwritingpage.R;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePosition;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpage.view.PageInkView;
import com.miui.libhandwritingpage.view.PageItemView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import xc.engine.api.CreationPageSDK;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PageLayoutManager {
    private static final String TAG = "PageLayoutManager";
    private int bottomMargin;
    private float contentHeight;
    private float contentWidth;
    private final Context context;
    private float currentOffsetX;
    private float currentOffsetY;
    private int divider;
    private boolean dragging;
    private boolean[] edgeAdd;
    private boolean flinging;
    private float headCurHeight;
    private int headViewHeight;
    private boolean headViewVis;
    private boolean isHasTouchDown;
    private boolean isInit;
    private boolean isKeyboardLive;
    private float lastCurrentX;
    private float lastCurrentY;
    private int leftMargin;
    private int mBottomEdge;
    private int mEndEdge;
    private final int mExpanseDistance;
    private int mStartEdge;
    private int mTopEdge;
    private float maxVisibleArea;
    private final PageInkModel model;
    private final PageInkView pageInkView;
    private int pageViewPadding;
    private int rightMargin;
    private float scrollDx;
    private float scrollDy;
    private final OverScroller scroller;
    private boolean showHeadWhenExpanse;
    private boolean springBack;
    private int standardWidth;
    private int topMargin;
    private int visibleHeight;
    private int visibleWidth;
    private int standardWidthInUnLimit = 2560;
    private float SCALE_MIN = 0.55f;
    private float SCALE_MAX = 3.0f;
    private float scale = 1.0f;
    private float scaleRadio = 1.0f;
    private float lastScale = 1.0f;
    private int dragState = 0;
    private int initIndex = -1;
    private final List<Integer> tempComputeList = new ArrayList();
    float tmpScaleFactor = 1.0f;
    private int currentPosition = -1;
    private int maxTop = 0;
    private int maxStart = 0;
    private int maxEnd = 0;

    public PageLayoutManager(PageInkView pageInkView, PageInkModel pageInkModel) {
        Context context = pageInkView.getContext();
        this.context = context;
        this.pageInkView = pageInkView;
        this.model = pageInkModel;
        this.scroller = new OverScroller(context, new DecelerateInterpolator(1.0f));
        this.divider = context.getResources().getDimensionPixelSize(R.dimen.creation_page_spacing);
        prepareHeight(context);
        this.edgeAdd = new boolean[4];
        this.mExpanseDistance = Math.min(UIUtils.getRealHeight(context), UIUtils.getRealWidth(context));
    }

    MiCurveEngine getCurveEngine() {
        PageItemView currentItemView;
        PageInkModel pageInkModel = this.model;
        if (pageInkModel == null || (currentItemView = pageInkModel.getCurrentItemView()) == null) {
            return null;
        }
        return currentItemView.getCurveEngine();
    }

    public void onSizeChanged(int i, int i2) {
        int i3 = this.visibleWidth;
        int i4 = 0;
        this.isHasTouchDown = false;
        PagePosition firstItemPosition = getFirstItemPosition(i, i2);
        this.visibleWidth = i;
        this.visibleHeight = i2;
        int i5 = this.mStartEdge;
        if (i5 == 0) {
            i5 = 1;
        }
        this.mStartEdge = i5;
        int i6 = this.mEndEdge;
        if (i6 == 0) {
            i6 = -1;
        }
        this.mEndEdge = i6;
        int i7 = this.mTopEdge;
        if (i7 == 0) {
            i7 = 1;
        }
        this.mTopEdge = i7;
        int i8 = this.mBottomEdge;
        if (i8 == 0) {
            i8 = -2;
        }
        this.mBottomEdge = i8;
        if (i > 0 && i2 > 0) {
            int i9 = (i - this.leftMargin) - this.rightMargin;
            this.standardWidth = i9;
            this.scaleRadio = (i9 * 1.0f) / i;
        }
        if (i3 == 0 || i3 == i) {
            return;
        }
        float f = this.currentOffsetX;
        float itemHeight = 0.0f;
        if (f == 0.0f && this.currentOffsetY == 0.0f) {
            return;
        }
        this.currentOffsetX = (i * f) / i3;
        if (this.currentOffsetY < 0.0f && firstItemPosition != null) {
            List<PageItemData> itemDataList = this.model.getItemDataList();
            float f2 = this.topMargin;
            int i10 = firstItemPosition.position;
            while (i4 <= i10 && i4 < itemDataList.size()) {
                itemHeight = itemDataList.get(i4).getItemHeight(this.standardWidth, this.scale);
                f2 += i4 != itemDataList.size() - 1 ? (this.divider * this.scale) + itemHeight : itemHeight;
                i4++;
            }
            this.currentOffsetY = (itemHeight * (1.0f - firstItemPosition.percentY)) - f2;
        }
        this.contentWidth = this.standardWidth;
    }

    private PagePosition getFirstItemPosition(int i, int i2) {
        int i3;
        PageInkModel pageInkModel = this.model;
        List<PageItemData> itemDataList = pageInkModel == null ? null : pageInkModel.getItemDataList();
        if (i != 0 && i2 != 0 && (i3 = this.visibleWidth) != 0 && this.visibleHeight != 0 && i3 != i && this.currentOffsetY <= (-this.topMargin) && itemDataList != null && itemDataList.size() != 0) {
            float f = this.topMargin;
            int i4 = 0;
            while (i4 < itemDataList.size()) {
                float itemHeight = itemDataList.get(i4).getItemHeight(this.standardWidth, this.scale);
                f += i4 != itemDataList.size() + (-1) ? (this.divider * this.scale) + itemHeight : itemHeight;
                if (this.currentOffsetY + f > 0.0f) {
                    return new PagePosition(0.0f, 1.0f - ((f + this.currentOffsetY) / itemHeight), i4);
                }
                i4++;
            }
        }
        return null;
    }

    public void initByWindowMode(boolean z) {
        PageInkModel pageInkModel;
        if (!z || this.visibleWidth == 0 || this.visibleHeight == 0 || (pageInkModel = this.model) == null || pageInkModel.getItemDataList() == null || this.model.getItemDataList().size() == 0) {
            return;
        }
        computeContentHeight();
        int i = this.visibleHeight;
        float f = i;
        float f2 = this.contentHeight;
        int i2 = this.headViewHeight;
        if (f > i2 + f2) {
            float f3 = i / (f2 + i2);
            if (UIUtils.isInSplitScreenMode(this.context) || UIUtils.isInMultiWindowMode(Utils.getActivity(this.context))) {
                f3 = 1.0f;
            }
            float f4 = this.tmpScaleFactor * f3;
            this.tmpScaleFactor = f4;
            this.scale = f4;
            limitScale();
            int i3 = this.visibleWidth;
            this.currentOffsetX = -(((((i3 / 2.0f) - this.currentOffsetX) * this.scale) / this.lastScale) - (i3 / 2.0f));
            this.currentOffsetY = 0.0f;
            computeContentHeight();
            notifyDataChanged();
            this.lastScale = this.scale;
        }
    }

    public void computeScroll() {
        if (this.scroller.computeScrollOffset()) {
            scrollTo(this.scroller.getCurrX(), this.scroller.getCurrY());
        } else if (this.flinging) {
            this.flinging = false;
            notifyDataChanged();
            onScrollEnd();
        }
    }

    public void stopFling() {
        this.flinging = false;
        this.scroller.forceFinished(true);
        scrollTo(this.scroller.getCurrX(), this.scroller.getCurrY());
    }

    public void scrollBy(float f, float f2) {
        this.currentOffsetX += f;
        this.currentOffsetY += f2;
        notifyDataChanged();
    }

    public void scrollTo(float f, float f2) {
        this.currentOffsetX = f;
        this.currentOffsetY = f2;
        notifyDataChanged();
    }

    public void jumpTo(int i, boolean z) {
        if (getCurveEngine() != null) {
            getCurveEngine().getCamera().jumpOrScrollToPage(i, z);
        }
    }

    public boolean isFlinging() {
        return this.flinging;
    }

    private int calculateTimeForDeceleration(int i) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return (int) Math.ceil(((double) ((int) Math.ceil(Math.abs(i) * (30.0f / displayMetrics.densityDpi)))) / 0.4d);
    }

    public void onTouchEnd() {
        if (this.flinging) {
            this.isHasTouchDown = false;
            return;
        }
        reviseOffset();
        onScrollEnd();
        this.isHasTouchDown = false;
    }

    public float getCurrentOffsetY() {
        return this.currentOffsetY;
    }

    public float getScale() {
        if (getCurveEngine() != null) {
            return getCurveEngine().getCamera().getScale();
        }
        return this.scale;
    }

    public float getFactorScale() {
        if (getCurveEngine() != null) {
            return getCurveEngine().getCamera().getFactorScale();
        }
        return this.scale;
    }

    public float getChildScale() {
        return this.scale * this.scaleRadio;
    }

    public int getStandardWidth() {
        return this.standardWidth;
    }

    public void onDataChanged() {
        List<PageItemData> itemDataList = this.model.getItemDataList();
        if (itemDataList == null || itemDataList.size() == 0) {
            return;
        }
        float itemHeight = this.topMargin;
        for (int i = 0; i < itemDataList.size(); i++) {
            itemHeight += itemDataList.get(i).getItemHeight(this.standardWidth, this.scale) + (this.divider * this.scale);
        }
        this.contentHeight = (itemHeight - (this.divider * this.scale)) + this.bottomMargin;
        if (!this.isInit) {
            int i2 = this.initIndex;
            if (i2 >= 0) {
                float fComputeItemTopByPosition = computeItemTopByPosition(i2);
                if (fComputeItemTopByPosition < 0.0f) {
                    return;
                }
                computeContentHeight();
                int i3 = this.visibleHeight;
                float f = i3;
                float f2 = this.contentHeight;
                this.currentOffsetY = Math.max(-fComputeItemTopByPosition, f <= f2 ? i3 - f2 : 0.0f);
                this.currentPosition = this.initIndex;
            }
            this.isInit = true;
        }
        notifyDataChanged();
        CreationPageSDK.with(this.context).post(new Runnable() { // from class: com.miui.libhandwritingpage.adapter.PageLayoutManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m844x450d7027();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onDataChanged$0$com-miui-libhandwritingpage-adapter-PageLayoutManager, reason: not valid java name */
    /* synthetic */ void m844x450d7027() {
        onScrollEnd();
        this.pageInkView.requestLayout();
    }

    public float getMinTranX() {
        float f = this.contentWidth;
        if (f > this.standardWidth) {
            if (f * this.scale > this.visibleWidth) {
                return -getMaxTranX();
            }
            return 0.0f;
        }
        float f2 = this.scale;
        float f3 = f * f2;
        int i = this.visibleWidth;
        return f3 > ((float) i) ? (i * (1.0f - f2)) + (this.leftMargin * f2) : i * (1.0f - f2);
    }

    public float getMaxTranX() {
        float f = this.contentWidth;
        int i = this.standardWidth;
        if (f > i) {
            float f2 = this.scale;
            if (f * f2 > this.visibleWidth) {
                return ((((-this.leftMargin) * f2) * f) / i) + (((f * f2) - i) / 2.0f);
            }
            return 0.0f;
        }
        float f3 = i;
        float f4 = this.scale;
        if (f3 * f4 > this.visibleWidth) {
            return (-this.leftMargin) * f4;
        }
        return 0.0f;
    }

    public void checkEdgePoint(float f, float f2) {
        checkEdgePoint(f, f2, false);
    }

    public void checkEdgePoint(float f, float f2, boolean z) {
        boolean z2;
        boolean z3;
        float f3 = this.currentOffsetX - f;
        float f4 = this.currentOffsetY - f2;
        double d = f3;
        float f5 = this.mStartEdge;
        float f6 = this.scale;
        int i = this.mExpanseDistance;
        double d2 = f5 * f6 * i;
        int i2 = this.visibleWidth;
        boolean z4 = false;
        if (d > d2 - (((double) i2) * 0.25d)) {
            this.edgeAdd[0] = true;
            z4 = true;
        }
        boolean z5 = z4;
        if (d < ((double) ((this.mEndEdge - 1) * f6 * i)) + (((double) i2) * 0.25d)) {
            this.edgeAdd[1] = true;
            z2 = true;
        } else {
            z2 = z5;
        }
        double d3 = f4;
        double d4 = this.mTopEdge * f6 * i;
        int i3 = this.visibleHeight;
        if (d3 > d4 - (((double) i3) * 0.25d)) {
            this.edgeAdd[2] = true;
            z2 = true;
        }
        if (d3 < ((double) ((this.mBottomEdge - 1) * f6 * i)) + (((double) i3) * 0.25d)) {
            this.edgeAdd[3] = true;
            z3 = true;
        } else {
            z3 = z2;
        }
        if (z && z3) {
            expansePage();
        }
    }

    public void checkSelectViewPosition(float[] fArr) {
        while (this.mStartEdge * this.mExpanseDistance * this.scale < this.currentOffsetX - fArr[0]) {
            this.edgeAdd[0] = true;
            expansePage();
        }
        while (this.mTopEdge * this.mExpanseDistance * this.scale < this.currentOffsetY - fArr[1]) {
            this.edgeAdd[2] = true;
            expansePage();
        }
        while ((this.mEndEdge - 1) * this.mExpanseDistance * this.scale > this.currentOffsetX - fArr[2]) {
            this.edgeAdd[1] = true;
            expansePage();
        }
        while ((this.mBottomEdge - 1) * this.mExpanseDistance * this.scale > this.currentOffsetY - fArr[5]) {
            this.edgeAdd[3] = true;
            expansePage();
        }
    }

    public void expansePage() {
        int i = this.mStartEdge;
        boolean[] zArr = this.edgeAdd;
        this.mStartEdge = i + (zArr[0] ? 1 : 0);
        this.mEndEdge -= zArr[1] ? 1 : 0;
        int i2 = this.mTopEdge;
        boolean z = zArr[2];
        this.mTopEdge = i2 + (z ? 1 : 0);
        this.mBottomEdge -= zArr[3] ? 1 : 0;
        if (z && this.headViewVis) {
            this.showHeadWhenExpanse = true;
            this.headCurHeight = this.headViewHeight;
        }
        Arrays.fill(zArr, false);
    }

    public void resetExpanse() {
        Arrays.fill(this.edgeAdd, false);
    }

    private void reviseOffset() {
        float f;
        int dimensionPixelOffset = this.context.getResources().getDimensionPixelOffset(R.dimen.creation_footer_view_height_max);
        if (this.model.isCanWrite()) {
            int i = this.visibleHeight;
            float f2 = i;
            float f3 = this.contentHeight;
            f = f2 > f3 ? -dimensionPixelOffset : (i - f3) - dimensionPixelOffset;
        } else {
            int i2 = this.visibleHeight;
            float f4 = i2;
            float f5 = this.contentHeight;
            f = f4 > f5 ? 0.0f : i2 - f5;
        }
        if (this.model.isUnlimited()) {
            this.currentOffsetX = Math.min(Math.max(this.currentOffsetX, ((this.mEndEdge - 1) * this.scale * this.mExpanseDistance) + this.visibleWidth), this.mStartEdge * this.scale * this.mExpanseDistance);
            this.currentOffsetY = Math.min(Math.max(this.currentOffsetY, ((this.mBottomEdge - 1) * this.scale * this.mExpanseDistance) + this.visibleHeight), (this.mTopEdge * this.scale * this.mExpanseDistance) + this.headViewHeight);
            return;
        }
        this.currentOffsetX = Math.min(Math.max(this.currentOffsetX, getMinTranX()), getMaxTranX());
        if (!this.isKeyboardLive) {
            this.currentOffsetY = Math.max(this.currentOffsetY, f);
        }
        float fMin = Math.min(this.currentOffsetY, this.visibleHeight / 2.5f);
        this.currentOffsetY = fMin;
        if (this.isKeyboardLive || !this.isHasTouchDown || this.dragging) {
            return;
        }
        float f6 = this.contentHeight;
        int i3 = this.visibleHeight;
        if ((f6 >= i3 || fMin >= 0.0f) && (fMin >= 0.0f || fMin >= i3 - f6)) {
            return;
        }
        this.dragging = true;
    }

    private void reviseOffsetByContent() {
        int i = this.visibleHeight;
        float f = i;
        float f2 = this.contentHeight;
        float f3 = f > f2 ? 0.0f : i - f2;
        if (!this.model.isUnLimitToNormal()) {
            this.currentOffsetX = Math.min(Math.max(this.currentOffsetX, getMinTranX()), getMaxTranX());
        }
        float fMax = Math.max(this.currentOffsetY, f3);
        this.currentOffsetY = fMax;
        int i2 = this.headViewHeight;
        if (fMax > i2) {
            this.currentOffsetY = i2;
        } else {
            if (fMax <= 0.0f || fMax >= i2) {
                return;
            }
            this.currentOffsetY = 0.0f;
        }
    }

    private void computeLayoutData() {
        float f;
        float f2;
        float f3;
        List<PageItemData> itemDataList = this.model.getItemDataList();
        if (itemDataList == null || itemDataList.isEmpty()) {
            return;
        }
        RectF rectF = new RectF(0.0f, 0.0f, this.visibleWidth, this.visibleHeight);
        this.maxVisibleArea = 0.0f;
        float f4 = this.topMargin;
        int i = 0;
        boolean z = false;
        int i2 = -1;
        int iMax = -1;
        while (i < itemDataList.size()) {
            PageItemData pageItemData = itemDataList.get(i);
            pageItemData.standardWidth = this.standardWidth;
            pageItemData.isUnLimit = this.model.isUnlimited();
            if (this.model.isUnlimited()) {
                pageItemData.unLimitStart = Math.max(pageItemData.unLimitStart, this.mStartEdge);
                pageItemData.unLimitEnd = Math.min(pageItemData.unLimitEnd, this.mEndEdge);
                pageItemData.unLimitTop = Math.max(pageItemData.unLimitTop, this.mTopEdge);
                pageItemData.unLimitBottom = Math.min(pageItemData.unLimitBottom, this.mBottomEdge);
                this.mStartEdge = pageItemData.unLimitStart;
                this.mEndEdge = pageItemData.unLimitEnd;
                this.mTopEdge = pageItemData.unLimitTop;
                this.mBottomEdge = pageItemData.unLimitBottom;
                pageItemData.maxWidth = Math.max((pageItemData.unLimitStart - pageItemData.unLimitEnd) * this.visibleWidth, pageItemData.maxWidth);
                pageItemData.maxHeight = Math.max((pageItemData.unLimitTop - pageItemData.unLimitBottom) * this.visibleHeight, pageItemData.maxHeight);
                this.maxTop = (int) Math.max(this.maxTop, this.currentOffsetY / this.scale);
                this.maxStart = (int) Math.max(this.maxStart, this.currentOffsetX / this.scale);
                this.maxEnd = (int) Math.min(this.maxEnd, this.currentOffsetX / this.scale);
            }
            float itemHeight = pageItemData.getItemHeight(this.standardWidth, this.scale);
            if (!pageItemData.useNewSize && !this.model.isUnlimited()) {
                pageItemData.maxHeight = (this.model.getWindowWidth() * pageItemData.height) / pageItemData.width;
                pageItemData.maxWidth = Math.max(this.model.getWindowWidth(), pageItemData.maxWidth);
            }
            if (!pageItemData.isFixSize && !pageItemData.unLimitToNormal) {
                float f5 = pageItemData.maxHeight / pageItemData.maxWidth;
                pageItemData.maxWidth = this.model.getWindowWidth();
                pageItemData.maxHeight = (int) (this.model.getWindowWidth() * f5);
            }
            float fMax = Math.max(this.contentWidth, Math.max(this.standardWidth, pageItemData.maxWidth));
            this.contentWidth = fMax;
            if (fMax > this.standardWidth) {
                f = f4;
                float dimension = ((this.standardWidth - (pageItemData.maxWidth * this.scale)) / 2.0f) + this.context.getResources().getDimension(R.dimen.creation_paint_view_margin);
                RectF rectF2 = pageItemData.layoutRectF;
                float f6 = this.currentOffsetX;
                rectF2.set(dimension + f6, f + this.currentOffsetY, dimension + f6 + (pageItemData.maxWidth * this.scale), f + itemHeight + this.currentOffsetY);
            } else if (pageItemData.maxWidth * this.scale < this.standardWidth || pageItemData.maxWidth != this.contentWidth) {
                f = f4;
                if (this.model.isPcMode()) {
                    float f7 = ((this.standardWidth * (1.0f - this.scale)) / 2.0f) + this.leftMargin;
                    RectF rectF3 = pageItemData.layoutRectF;
                    float f8 = this.currentOffsetX;
                    float f9 = f + this.currentOffsetY;
                    int i3 = this.standardWidth;
                    rectF3.set(f7 + f8, f9, f7 + (i3 * this.scale) + f8, f + (((i3 * pageItemData.height) * this.scale) / pageItemData.width) + this.currentOffsetY);
                } else {
                    float f10 = ((this.standardWidth - (pageItemData.maxWidth * this.scale)) / 2.0f) + this.leftMargin;
                    if (pageItemData.sizeUnit == 0 && !pageItemData.isFixSize) {
                        f2 = this.standardWidth;
                        f3 = this.scale;
                    } else {
                        f2 = pageItemData.maxWidth;
                        f3 = this.scale;
                    }
                    float f11 = (f2 * f3) + f10;
                    float fMax2 = this.scale > 1.0f ? (((Math.max(this.contentWidth, this.standardWidth) * this.scale) - (f11 - f10)) / 2.0f) - f10 : 0.0f;
                    RectF rectF4 = pageItemData.layoutRectF;
                    float f12 = this.currentOffsetX;
                    float f13 = this.currentOffsetY;
                    rectF4.set(f10 + f12 + fMax2, f + f13, f11 + f12 + fMax2, f + itemHeight + f13);
                }
            } else {
                RectF rectF5 = pageItemData.layoutRectF;
                float f14 = this.currentOffsetX;
                int i4 = this.leftMargin;
                float f15 = this.scale;
                float f16 = this.currentOffsetY;
                f = f4;
                rectF5.set((i4 * f15) + f14, f + f16, (this.standardWidth * f15) + f14 + (i4 * f15), f + itemHeight + f16);
            }
            if (!z && RectF.intersects(rectF, pageItemData.layoutRectF)) {
                computeCurrentPosition(i, pageItemData.layoutRectF);
                z = true;
            }
            if (RectF.intersects(rectF, pageItemData.layoutRectF)) {
                if (i2 == -1) {
                    i2 = i;
                }
                iMax = Math.max(iMax, i);
            }
            i++;
            f4 = f + itemHeight + (this.divider * this.scale);
        }
        handleItemView(i2, iMax, itemDataList);
        this.contentHeight = (f4 - (this.divider * this.scale)) + this.bottomMargin;
        this.model.onLayout();
    }

    public void calculateUnLimitExportArea() {
        List<PageItemData> itemDataList;
        PageInkModel pageInkModel = this.model;
        if (pageInkModel == null || (itemDataList = pageInkModel.getItemDataList()) == null || itemDataList.size() == 0) {
            return;
        }
        for (int i = 0; i < itemDataList.size(); i++) {
            PageItemData pageItemData = itemDataList.get(i);
            if (this.model.isUnlimited()) {
                float[] fArrCalcAllCurveBound = this.model.calcAllCurveBound();
                float[] fArrCalcAllSpanBound = this.model.calcAllSpanBound();
                float[] fArr = {Math.max(fArrCalcAllCurveBound[0], fArrCalcAllSpanBound[0]), Math.max(fArrCalcAllCurveBound[1], fArrCalcAllSpanBound[1]), Math.min(fArrCalcAllCurveBound[2], fArrCalcAllSpanBound[2]), Math.min(fArrCalcAllCurveBound[3], fArrCalcAllSpanBound[3])};
                pageItemData.exportLeft = ((int) fArr[0]) + 100;
                pageItemData.exportTop = ((int) fArr[1]) + 100;
                pageItemData.exportRight = ((int) fArr[2]) - 100;
                pageItemData.exportBottom = ((int) fArr[3]) - 100;
                int iAbs = Math.abs(pageItemData.exportLeft - pageItemData.exportRight);
                int iAbs2 = Math.abs(pageItemData.exportTop - pageItemData.exportBottom);
                if (iAbs2 != 0 && iAbs / iAbs2 > 8) {
                    float f = ((iAbs / 8.0f) - iAbs2) / 2.0f;
                    pageItemData.exportTop = (int) (pageItemData.exportTop + f);
                    pageItemData.exportBottom = (int) (pageItemData.exportBottom - f);
                } else if (iAbs != 0 && iAbs2 / iAbs > 8) {
                    float f2 = ((iAbs2 / 8.0f) - iAbs) / 2.0f;
                    pageItemData.exportLeft = (int) (pageItemData.exportLeft + f2);
                    pageItemData.exportRight = (int) (pageItemData.exportRight - f2);
                }
            }
        }
    }

    private void handleItemView(int i, int i2, List<PageItemData> list) {
        if (this.model.isUnlimited()) {
            i = 0;
            i2 = 0;
        }
        if (this.model.getCurrentItemView() == null) {
            this.model.createItemView();
            this.model.bindView();
        }
        if (i < 0 || i2 < 0) {
            return;
        }
        if (i2 - i < 0) {
            i--;
            if (i2 - i < 0 && i2 < list.size() - 1) {
                i2++;
            }
        }
        int i3 = i;
        PageItemView currentItemView = this.model.getCurrentItemView();
        if (!this.model.isUnlimited() || this.model.isUnLimitToNormal()) {
            Log.i(TAG, "handleItemView addPages");
            RectF rectF = new RectF();
            rectF.set(list.get(0).layoutRectF.left, list.get(0).layoutRectF.top, list.get(list.size() - 1).layoutRectF.right, list.get(list.size() - 1).layoutRectF.bottom);
            currentItemView.update(i3, rectF, this.scale * this.scaleRadio, this.standardWidth, this.currentOffsetX, this.currentOffsetY);
            currentItemView = currentItemView;
        }
        for (int i4 = i3; i4 <= i2; i4++) {
            RectF rectF2 = list.get(i4).layoutRectF;
            RectF rectF3 = new RectF();
            rectF3.set(rectF2);
            if (this.model.isUnlimited()) {
                if (this.showHeadWhenExpanse && this.headViewVis) {
                    float f = this.currentOffsetY;
                    float f2 = this.headCurHeight + (f - this.lastCurrentY);
                    this.headCurHeight = f2;
                    this.lastCurrentY = f;
                    float fMin = Math.min(f2, this.headViewHeight);
                    this.headCurHeight = fMin;
                    if (fMin <= 0.0f) {
                        this.headCurHeight = 0.0f;
                        this.showHeadWhenExpanse = false;
                        this.headViewVis = false;
                    }
                    rectF3.top = this.headCurHeight;
                    currentItemView.update(i4, rectF3, this.scale * this.scaleRadio, this.standardWidth, this.currentOffsetX, this.currentOffsetY - rectF3.top);
                } else if (rectF3.top > this.mTopEdge * this.scale * this.mExpanseDistance) {
                    rectF3.top -= (this.mTopEdge * this.scale) * this.mExpanseDistance;
                    this.headViewVis = rectF3.top >= ((float) this.headViewHeight);
                    currentItemView.update(i4, rectF3, this.scale * this.scaleRadio, this.standardWidth, this.currentOffsetX, this.currentOffsetY - rectF3.top);
                } else {
                    rectF3.set(0.0f, 0.0f, this.visibleWidth, this.visibleHeight);
                    currentItemView.update(i4, rectF3, this.scale * this.scaleRadio, this.standardWidth, this.currentOffsetX, this.currentOffsetY);
                }
            }
        }
    }

    private void computeCurrentPosition(int i, RectF rectF) {
        float fMin = (Math.min(this.visibleWidth, rectF.right) - Math.max(0.0f, rectF.left)) * (Math.min(this.visibleHeight, rectF.bottom) - Math.max(0.0f, rectF.top));
        if (fMin > this.maxVisibleArea) {
            this.currentPosition = this.model.getCurrentPosition();
            this.maxVisibleArea = fMin;
        }
    }

    private void computeContentHeight() {
        List<PageItemData> itemDataList = this.model.getItemDataList();
        if (itemDataList == null || itemDataList.size() == 0) {
            return;
        }
        float itemHeight = this.topMargin;
        Iterator<PageItemData> it = itemDataList.iterator();
        while (it.hasNext()) {
            itemHeight += it.next().getItemHeight(this.standardWidth, this.scale) + (this.divider * this.scale);
        }
        this.contentHeight = (itemHeight + this.bottomMargin) - (this.divider * this.scale);
    }

    public float computeItemTopByPosition(int i) {
        float itemHeight = 0.0f;
        if (i == 0) {
            return 0.0f;
        }
        List<PageItemData> itemDataList = this.model.getItemDataList();
        if (itemDataList == null || itemDataList.isEmpty() || i >= itemDataList.size()) {
            return -1.0f;
        }
        for (int i2 = 0; i2 < i; i2++) {
            itemHeight += itemDataList.get(i2).getItemHeight(this.standardWidth, this.scale) + (this.divider * this.scale);
        }
        return itemHeight;
    }

    public void notifyDataChanged() {
        this.currentPosition = this.model.getCurrentPosition();
        reviseOffset();
        computeLayoutData();
        int i = this.currentPosition;
        if (i != -1) {
            this.model.onCurrentPageChanged(i);
        }
    }

    public void notifyDataChangedNew() {
        int currentPosition = this.model.getCurrentPosition();
        this.currentPosition = currentPosition;
        if (currentPosition != -1) {
            this.model.onCurrentPageChanged(currentPosition);
        }
    }

    public float getContentHeight() {
        return this.contentHeight;
    }

    private void limitScale() {
        if (this.scale > 3.0f) {
            this.scale = 3.0f;
            this.tmpScaleFactor = 3.0f;
        }
        if (this.scale < 0.55f) {
            this.scale = 0.55f;
            this.tmpScaleFactor = 0.55f;
        }
    }

    public void onScrollEnd() {
        if (this.model.getCurrentItemView() == null) {
            return;
        }
        Log.e(TAG, "xl-- onScrollEnd: " + this.model.isMaxPageCount() + ", isHasTouchDown: " + this.isHasTouchDown + ", " + this.dragging + StringUtils.SPACE + this.dragState);
        if (!this.model.isMaxPageCount() && this.isHasTouchDown && this.dragging && 1 == this.dragState) {
            this.dragState = 0;
            this.model.onFooterViewMaxHeight();
        } else {
            handleSpringBack();
        }
        this.dragging = false;
        this.dragState = 0;
        this.model.getCurrentItemView().onScrollEnd(this.scale * this.scaleRadio, this.standardWidth);
    }

    public void onScrollEndNew() {
        if (this.model.getCurrentItemView() == null) {
            return;
        }
        Log.e(TAG, "xl-- onScrollEnd new: " + this.model.isMaxPageCount() + ", isHasTouchDown: " + this.isHasTouchDown + ", " + this.dragState);
        if (!this.model.isMaxPageCount() && this.isHasTouchDown && 1 == this.dragState) {
            this.dragState = 0;
            this.model.onFooterViewMaxHeight();
        }
    }

    private void handleSpringBack() {
        float f;
        if (this.model.isUnlimited()) {
            if (this.showHeadWhenExpanse && this.headViewVis) {
                float f2 = this.headCurHeight;
                if (f2 < this.headViewHeight - 10) {
                    float f3 = this.currentOffsetX;
                    this.lastCurrentX = f3;
                    float f4 = this.currentOffsetY;
                    this.lastCurrentY = f4;
                    this.scroller.startScroll((int) f3, (int) f4, 0, (int) (-f2), 200);
                    this.pageInkView.postInvalidate();
                    return;
                }
            }
            float f5 = this.currentOffsetY;
            float f6 = this.scale;
            int i = this.mExpanseDistance;
            int i2 = this.mTopEdge;
            if (f5 <= i * f6 * i2 || f5 >= (((i * f6) * i2) + this.headViewHeight) - 10.0f) {
                return;
            }
            this.scroller.startScroll((int) this.currentOffsetX, (int) f5, 0, (int) (-(f5 - ((f6 * i) * i2))), 200);
            this.pageInkView.postInvalidate();
            return;
        }
        if (this.springBack) {
            reviseOffsetByContent();
            notifyDataChanged();
            this.springBack = false;
            return;
        }
        float f7 = this.currentOffsetY;
        int i3 = this.headViewHeight;
        if (f7 > i3) {
            f = i3 - f7;
        } else {
            f = (f7 <= 0.0f || f7 >= ((float) i3)) ? 0.0f : -f7;
        }
        int i4 = this.visibleHeight;
        float f8 = i4;
        float f9 = this.contentHeight;
        float f10 = f8 > f9 ? 0.0f : i4 - f9;
        if (f7 < f10) {
            f = f10 - f7;
        }
        if (f != 0.0f && !this.isKeyboardLive && this.scroller.isFinished()) {
            this.springBack = true;
            this.flinging = true;
            this.scroller.startScroll((int) this.currentOffsetX, (int) this.currentOffsetY, 0, (int) f, 200);
            this.pageInkView.postInvalidate();
            return;
        }
        notifyDataChanged();
    }

    public boolean isDragging() {
        float f;
        if (!this.model.isUnlimited() && !this.model.isUnLimitToNormal()) {
            this.currentOffsetX = Math.min(Math.max(this.currentOffsetX, getMinTranX()), getMaxTranX());
        }
        float f2 = this.currentOffsetY;
        int i = this.headViewHeight;
        if (f2 > i) {
            f = i - f2;
        } else {
            f = (f2 <= 0.0f || f2 >= ((float) i)) ? 0.0f : f2 - i;
        }
        int i2 = this.visibleHeight;
        float f3 = i2;
        float f4 = this.contentHeight;
        float f5 = f3 > f4 ? 0.0f : i2 - f4;
        if (f2 < f5) {
            f = f5 - f2;
        }
        return f != 0.0f;
    }

    public void setDraggingState(int i) {
        this.dragState = i;
    }

    public void resetDrag() {
        this.isHasTouchDown = false;
        this.dragging = false;
        this.dragState = 0;
    }

    public void onTouchDown() {
        this.isHasTouchDown = true;
        this.dragging = false;
        this.dragState = 0;
    }

    public void setKeyboardLive(boolean z) {
        this.isKeyboardLive = z;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.leftMargin = i;
        this.topMargin = i2;
        this.rightMargin = i3;
        this.bottomMargin = i4;
        int i5 = this.visibleWidth;
        if (i5 > 0) {
            this.scaleRadio = (((i5 - i) - i3) * 1.0f) / i5;
        }
    }

    private void prepareHeight(Context context) {
        String language = Locale.getDefault().getLanguage();
        if (context.getResources().getConfiguration().fontScale > DisplayUtils.DEFAULT_FONT_SIZE) {
            this.headViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.creation_header_view_height_max);
            if (language.equals("bo") || language.equals("ug")) {
                this.headViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.creation_header_view_height_language_big_font);
                return;
            }
            return;
        }
        this.headViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.creation_header_view_height_min);
    }

    public float getHeadHeight() {
        return this.headViewHeight;
    }

    public float[] getLocation() {
        return new float[]{this.currentOffsetX, this.currentOffsetY};
    }

    public void setContentWidth(float f) {
        this.contentWidth = f;
    }

    public float getPageScaleRatio() {
        return this.scaleRadio;
    }

    public int[] getUnLimitBound() {
        int i = this.mStartEdge;
        int i2 = this.mExpanseDistance;
        return new int[]{(i * i2) + (i2 - this.visibleWidth), this.mTopEdge * i2, (this.mEndEdge - 1) * i2, (this.mBottomEdge - 1) * i2};
    }

    public int[] getCenterPoint() {
        return new int[]{(int) ((getMaxTranX() + getMinTranX()) / 2.0f), this.maxTop};
    }

    public void setPageDivider(int i) {
        this.divider = i;
    }

    public void setPageViewPadding(int i) {
        this.pageViewPadding = i;
    }
}
