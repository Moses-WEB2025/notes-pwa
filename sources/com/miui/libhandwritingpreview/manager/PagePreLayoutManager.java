package com.miui.libhandwritingpreview.manager;

import android.content.Context;
import android.graphics.RectF;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.document.Layer;
import com.miui.libhandwritingpage.R;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePosition;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.interfaces.PreICreationEngine;
import com.miui.libhandwritingpreview.model.PageInkPreModel;
import com.miui.libhandwritingpreview.system.PreCamera;
import com.miui.libhandwritingpreview.view.CreationShieldPrePageInkView;
import com.miui.libhandwritingpreview.view.PagePreDrawView;
import com.miui.libhandwritingpreview.view.PagePreItemView;
import com.miui.maml.folme.AnimatedProperty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: compiled from: PagePreLayoutManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0010\u0018\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0014\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\b\u0006\u0018\u0000 \u0093\u00012\u00020\u0001:\u0002\u0093\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020\u000b2\u0006\u0010M\u001a\u00020\u000bJ\u001a\u0010N\u001a\u0004\u0018\u00010O2\u0006\u0010L\u001a\u00020\u000b2\u0006\u0010M\u001a\u00020\u000bH\u0002J\u000e\u0010P\u001a\u00020K2\u0006\u0010Q\u001a\u00020&J\u0006\u0010R\u001a\u00020KJ\u0016\u0010S\u001a\u00020K2\u0006\u0010T\u001a\u00020\u00182\u0006\u0010U\u001a\u00020\u0018J\u0016\u0010V\u001a\u00020K2\u0006\u0010W\u001a\u00020\u00182\u0006\u0010X\u001a\u00020\u0018J\u0016\u0010Y\u001a\u00020K2\u0006\u0010Z\u001a\u00020\u000b2\u0006\u0010[\u001a\u00020&J\u0006\u0010\\\u001a\u00020KJ\u0006\u0010]\u001a\u00020\u0018J\u0006\u0010`\u001a\u00020KJ\u0006\u0010e\u001a\u00020KJ\u0006\u0010f\u001a\u00020KJ\b\u0010g\u001a\u00020KH\u0002J\b\u0010h\u001a\u00020KH\u0002J\b\u0010i\u001a\u00020KH\u0002J&\u0010j\u001a\u00020K2\u0006\u0010k\u001a\u00020\u000b2\u0006\u0010l\u001a\u00020\u000b2\f\u0010m\u001a\b\u0012\u0004\u0012\u00020o0nH\u0002J\u0018\u0010p\u001a\u00020K2\u0006\u0010q\u001a\u00020\u000b2\u0006\u0010r\u001a\u00020sH\u0002J\b\u0010t\u001a\u00020KH\u0002J\u000e\u0010u\u001a\u00020\u00182\u0006\u0010q\u001a\u00020\u000bJ\u0006\u0010v\u001a\u00020KJ\u0006\u0010w\u001a\u00020KJ\b\u0010x\u001a\u00020KH\u0002J\u0006\u0010y\u001a\u00020KJ\u0006\u0010z\u001a\u00020KJ\b\u0010{\u001a\u00020KH\u0002J\u0006\u0010|\u001a\u00020&J\u000e\u0010}\u001a\u00020K2\u0006\u0010~\u001a\u00020\u000bJ\u0006\u0010\u007f\u001a\u00020KJ\u0010\u0010\u0080\u0001\u001a\u00020K2\u0007\u0010\u0081\u0001\u001a\u00020&J+\u0010\u0082\u0001\u001a\u00020K2\u0007\u0010\u0083\u0001\u001a\u00020\u000b2\u0007\u0010\u0084\u0001\u001a\u00020\u000b2\u0007\u0010\u0085\u0001\u001a\u00020\u000b2\u0007\u0010\u0086\u0001\u001a\u00020\u000bJ\u0011\u0010\u0087\u0001\u001a\u00020K2\u0006\u0010\b\u001a\u00020\tH\u0002J\u000f\u0010\u008c\u0001\u001a\u00020K2\u0006\u0010\"\u001a\u00020\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001f\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001b\"\u0004\b!\u0010\u001dR\u000e\u0010\"\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u00020&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010'\"\u0004\b(\u0010)R\u000e\u0010*\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00100\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u001b\"\u0004\b2\u0010\u001dR\u000e\u00103\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u000bX\u0082D¢\u0006\u0002\n\u0000R\u001a\u00106\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010\u001b\"\u0004\b8\u0010\u001dR\u000e\u00109\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020>X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010^\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b_\u0010\u001bR\u0011\u0010a\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\bb\u0010\u001bR\u0011\u0010c\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\bd\u0010\u001bR\u0015\u0010\u0088\u0001\u001a\u00030\u0089\u00018F¢\u0006\b\u001a\u0006\b\u008a\u0001\u0010\u008b\u0001R\u0015\u0010\u008d\u0001\u001a\u00030\u008e\u00018F¢\u0006\b\u001a\u0006\b\u008f\u0001\u0010\u0090\u0001R\u0015\u0010\u0091\u0001\u001a\u00030\u008e\u00018F¢\u0006\b\u001a\u0006\b\u0092\u0001\u0010\u0090\u0001¨\u0006\u0094\u0001"}, d2 = {"Lcom/miui/libhandwritingpreview/manager/PagePreLayoutManager;", "", "pageInkView", "Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;", "model", "Lcom/miui/libhandwritingpreview/model/PageInkPreModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/view/CreationShieldPrePageInkView;Lcom/miui/libhandwritingpreview/model/PageInkPreModel;)V", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "mExpanseDistance", "", "visibleWidth", "visibleHeight", "standardWidth", "getStandardWidth", "()I", "setStandardWidth", "(I)V", "leftMargin", "rightMargin", "topMargin", "bottomMargin", "currentOffsetX", "", "currentOffsetY", "getCurrentOffsetY", "()F", "setCurrentOffsetY", "(F)V", "maxVisibleArea", "contentHeight", "getContentHeight", "setContentHeight", "contentWidth", "scroller", "Landroid/widget/OverScroller;", "isFlinging", "", "()Z", "setFlinging", "(Z)V", "dragging", "isKeyboardLive", "isInit", "springBack", "isHasTouchDown", "scale", "pageScaleRatio", "getPageScaleRatio", "setPageScaleRatio", "lastScale", "dragState", "initIndex", "tmpScaleFactor", "getTmpScaleFactor", "setTmpScaleFactor", "divider", "currentPosition", "headViewHeight", "headCurHeight", "edgeAdd", "", "mStartEdge", "mEndEdge", "mTopEdge", "mBottomEdge", "headViewVis", "showHeadWhenExpanse", "lastCurrentX", "lastCurrentY", "maxTop", "maxStart", "maxEnd", "onSizeChanged", "", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "getFirstItemPosition", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PagePosition;", "initByWindowMode", "isInWindowMode", "computeScroll", "scrollBy", "disX", "disY", "scrollTo", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "jumpTo", "index", "anim", "onTouchEnd", "getScale", "factorScale", "getFactorScale", "onDataChanged", "minTranX", "getMinTranX", "maxTranX", "getMaxTranX", "expansePage", "resetExpanse", "reviseOffset", "reviseOffsetByContent", "computeLayoutData", "handleItemView", "start", "end", "list", "", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;", "computeCurrentPosition", "position", "layoutRectF", "Landroid/graphics/RectF;", "computeContentHeight", "computeItemTopByPosition", "notifyDataChanged", "notifyDataChangedNew", "limitScale", "onScrollEnd", "onScrollEndNew", "handleSpringBack", "isDragging", "setDraggingState", "state", "onTouchDown", "setKeyboardLive", "isLive", "setPadding", "left", Layer.KEY_TOP, EditStat.ELEMENT_RIGHT, "bottom", "prepareHeight", "location", "", "getLocation", "()[F", "setContentWidth", "unLimitBound", "", "getUnLimitBound", "()[I", "centerPoint", "getCenterPoint", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PagePreLayoutManager {
    private static final String TAG;
    private int bottomMargin;
    private float contentHeight;
    private float contentWidth;
    private final Context context;
    private float currentOffsetX;
    private float currentOffsetY;
    private int currentPosition;
    private int divider;
    private int dragState;
    private boolean dragging;
    private final boolean[] edgeAdd;
    private float headCurHeight;
    private int headViewHeight;
    private boolean headViewVis;
    private final int initIndex;
    private boolean isFlinging;
    private boolean isHasTouchDown;
    private boolean isInit;
    private boolean isKeyboardLive;
    private float lastCurrentX;
    private float lastCurrentY;
    private float lastScale;
    private int leftMargin;
    private int mBottomEdge;
    private int mEndEdge;
    private final int mExpanseDistance;
    private int mStartEdge;
    private int mTopEdge;
    private int maxEnd;
    private int maxStart;
    private int maxTop;
    private float maxVisibleArea;
    private final PageInkPreModel model;
    private final CreationShieldPrePageInkView pageInkView;
    private float pageScaleRatio;
    private int rightMargin;
    private float scale;
    private final OverScroller scroller;
    private boolean showHeadWhenExpanse;
    private boolean springBack;
    private int standardWidth;
    private float tmpScaleFactor;
    private int topMargin;
    private int visibleHeight;
    private int visibleWidth;

    public PagePreLayoutManager(CreationShieldPrePageInkView pageInkView, PageInkPreModel pageInkPreModel) {
        Intrinsics.checkNotNullParameter(pageInkView, "pageInkView");
        this.pageInkView = pageInkView;
        this.model = pageInkPreModel;
        Context context = pageInkView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.context = context;
        this.scroller = new OverScroller(context, new DecelerateInterpolator(1.0f));
        this.scale = 1.0f;
        this.pageScaleRatio = 1.0f;
        this.lastScale = 1.0f;
        this.initIndex = -1;
        this.tmpScaleFactor = 1.0f;
        this.currentPosition = -1;
        this.divider = context.getResources().getDimensionPixelSize(R.dimen.creation_page_spacing);
        prepareHeight(context);
        this.edgeAdd = new boolean[4];
        this.mExpanseDistance = (int) Math.min(UIUtils.getRealHeight(context), UIUtils.getRealWidth(context));
    }

    public final int getStandardWidth() {
        return this.standardWidth;
    }

    public final void setStandardWidth(int i) {
        this.standardWidth = i;
    }

    public final float getCurrentOffsetY() {
        return this.currentOffsetY;
    }

    public final void setCurrentOffsetY(float f) {
        this.currentOffsetY = f;
    }

    public final float getContentHeight() {
        return this.contentHeight;
    }

    public final void setContentHeight(float f) {
        this.contentHeight = f;
    }

    /* JADX INFO: renamed from: isFlinging, reason: from getter */
    public final boolean getIsFlinging() {
        return this.isFlinging;
    }

    public final void setFlinging(boolean z) {
        this.isFlinging = z;
    }

    public final float getPageScaleRatio() {
        return this.pageScaleRatio;
    }

    public final void setPageScaleRatio(float f) {
        this.pageScaleRatio = f;
    }

    public final float getTmpScaleFactor() {
        return this.tmpScaleFactor;
    }

    public final void setTmpScaleFactor(float f) {
        this.tmpScaleFactor = f;
    }

    public final void onSizeChanged(int width, int height) {
        int i = this.visibleWidth;
        int i2 = 0;
        this.isHasTouchDown = false;
        PagePosition firstItemPosition = getFirstItemPosition(width, height);
        this.visibleWidth = width;
        this.visibleHeight = height;
        int i3 = this.mStartEdge;
        if (i3 == 0) {
            i3 = 1;
        }
        this.mStartEdge = i3;
        int i4 = this.mEndEdge;
        if (i4 == 0) {
            i4 = -1;
        }
        this.mEndEdge = i4;
        int i5 = this.mTopEdge;
        if (i5 == 0) {
            i5 = 1;
        }
        this.mTopEdge = i5;
        int i6 = this.mBottomEdge;
        if (i6 == 0) {
            i6 = -2;
        }
        this.mBottomEdge = i6;
        if (width > 0 && height > 0) {
            int i7 = (width - this.leftMargin) - this.rightMargin;
            this.standardWidth = i7;
            this.pageScaleRatio = (i7 * 1.0f) / width;
        }
        if (i == 0 || i == width) {
            return;
        }
        float f = this.currentOffsetX;
        float itemHeight = 0.0f;
        if (f == 0.0f && this.currentOffsetY == 0.0f) {
            return;
        }
        this.currentOffsetX = (width * f) / i;
        if (this.currentOffsetY < 0.0f && firstItemPosition != null) {
            PageInkPreModel pageInkPreModel = this.model;
            Intrinsics.checkNotNull(pageInkPreModel);
            List<PageItemData> itemDataList = pageInkPreModel.getItemDataList();
            float f2 = this.topMargin;
            int i8 = firstItemPosition.position;
            while (i2 <= i8 && i2 < itemDataList.size()) {
                itemHeight = itemDataList.get(i2).getItemHeight(this.standardWidth, this.scale);
                f2 += i2 != itemDataList.size() - 1 ? (this.divider * this.scale) + itemHeight : itemHeight;
                i2++;
            }
            this.currentOffsetY = (itemHeight * (1.0f - firstItemPosition.percentY)) - f2;
        }
        this.contentWidth = this.standardWidth;
    }

    private final PagePosition getFirstItemPosition(int width, int height) {
        int i;
        PageInkPreModel pageInkPreModel = this.model;
        List<PageItemData> itemDataList = pageInkPreModel != null ? pageInkPreModel.getItemDataList() : null;
        if (width != 0 && height != 0 && (i = this.visibleWidth) != 0 && this.visibleHeight != 0 && i != width && this.currentOffsetY <= (-this.topMargin) && itemDataList != null && itemDataList.size() != 0) {
            float f = this.topMargin;
            int i2 = 0;
            while (i2 < itemDataList.size()) {
                float itemHeight = itemDataList.get(i2).getItemHeight(this.standardWidth, this.scale);
                f += i2 != itemDataList.size() + (-1) ? (this.divider * this.scale) + itemHeight : itemHeight;
                if (this.currentOffsetY + f > 0.0f) {
                    return new PagePosition(0.0f, 1.0f - ((f + this.currentOffsetY) / itemHeight), i2);
                }
                i2++;
            }
        }
        return null;
    }

    public final void initByWindowMode(boolean isInWindowMode) {
        PageInkPreModel pageInkPreModel;
        if (!isInWindowMode || this.visibleWidth == 0 || this.visibleHeight == 0 || (pageInkPreModel = this.model) == null || pageInkPreModel.getItemDataList().isEmpty()) {
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

    public final void computeScroll() {
        if (this.scroller.computeScrollOffset()) {
            scrollTo(this.scroller.getCurrX(), this.scroller.getCurrY());
        } else if (this.isFlinging) {
            this.isFlinging = false;
            notifyDataChanged();
            onScrollEnd();
        }
    }

    public final void scrollBy(float disX, float disY) {
        this.currentOffsetX += disX;
        this.currentOffsetY += disY;
        notifyDataChanged();
    }

    public final void scrollTo(float x, float y) {
        this.currentOffsetX = x;
        this.currentOffsetY = y;
        notifyDataChanged();
    }

    public final void jumpTo(int index, boolean anim) {
        PagePreDrawView mDrawView;
        PreICreationEngine creationEngine;
        PreMiCurveEngine curveEngine;
        PreCamera camera;
        PageInkPreModel pageInkPreModel = this.model;
        if (pageInkPreModel == null || (mDrawView = pageInkPreModel.getMDrawView()) == null || (creationEngine = mDrawView.getCreationEngine()) == null || (curveEngine = creationEngine.getCurveEngine()) == null || (camera = curveEngine.getCamera()) == null) {
            return;
        }
        camera.jumpOrScrollToPage(index, anim);
    }

    public final void onTouchEnd() {
        if (this.isFlinging) {
            this.isHasTouchDown = false;
            return;
        }
        reviseOffset();
        onScrollEnd();
        this.isHasTouchDown = false;
    }

    public final float getScale() {
        PagePreDrawView mDrawView;
        PreICreationEngine creationEngine;
        PageInkPreModel pageInkPreModel = this.model;
        PreMiCurveEngine curveEngine = (pageInkPreModel == null || (mDrawView = pageInkPreModel.getMDrawView()) == null || (creationEngine = mDrawView.getCreationEngine()) == null) ? null : creationEngine.getCurveEngine();
        if (curveEngine != null) {
            return curveEngine.getCamera().getScale();
        }
        return this.scale;
    }

    public final float getFactorScale() {
        PagePreDrawView mDrawView;
        PreICreationEngine creationEngine;
        PageInkPreModel pageInkPreModel = this.model;
        PreMiCurveEngine curveEngine = (pageInkPreModel == null || (mDrawView = pageInkPreModel.getMDrawView()) == null || (creationEngine = mDrawView.getCreationEngine()) == null) ? null : creationEngine.getCurveEngine();
        if (curveEngine != null) {
            return curveEngine.getCamera().getFactorScale();
        }
        return this.scale;
    }

    public final void onDataChanged() {
        PreICreationEngine creationEngine;
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        List<PageItemData> itemDataList = pageInkPreModel.getItemDataList();
        if (itemDataList.isEmpty()) {
            return;
        }
        float itemHeight = this.topMargin;
        int size = itemDataList.size();
        for (int i = 0; i < size; i++) {
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
                this.currentOffsetY = (float) Math.max(-fComputeItemTopByPosition, f <= f2 ? i3 - f2 : 0.0f);
                this.currentPosition = this.initIndex;
            }
            this.isInit = true;
        }
        notifyDataChanged();
        PagePreDrawView mDrawView = this.model.getMDrawView();
        if (mDrawView != null && (creationEngine = mDrawView.getCreationEngine()) != null) {
            creationEngine.getEngineHandler();
        }
        onScrollEnd();
        this.pageInkView.requestLayout();
    }

    public final float getMinTranX() {
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

    public final float getMaxTranX() {
        float f = this.contentWidth;
        int i = this.standardWidth;
        if (f > i) {
            float f2 = this.scale;
            if (f * f2 > this.visibleWidth) {
                return ((((-this.leftMargin) * f2) * f) / i) + (((f * f2) - i) / 2);
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

    public final void expansePage() {
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

    public final void resetExpanse() {
        Arrays.fill(this.edgeAdd, false);
    }

    private final void reviseOffset() {
        this.context.getResources().getDimensionPixelOffset(R.dimen.creation_footer_view_height_max);
        int i = this.visibleHeight;
        float f = i;
        float f2 = this.contentHeight;
        float f3 = f > f2 ? 0.0f : i - f2;
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        if (pageInkPreModel.getIsUnlimited()) {
            this.currentOffsetX = (float) Math.min(Math.max(this.currentOffsetX, ((this.mEndEdge - 1) * this.scale * this.mExpanseDistance) + this.visibleWidth), this.mStartEdge * this.scale * this.mExpanseDistance);
            this.currentOffsetY = (float) Math.min(Math.max(this.currentOffsetY, ((this.mBottomEdge - 1) * this.scale * this.mExpanseDistance) + this.visibleHeight), (this.mTopEdge * this.scale * this.mExpanseDistance) + this.headViewHeight);
            return;
        }
        this.currentOffsetX = (float) Math.min(Math.max(this.currentOffsetX, getMinTranX()), getMaxTranX());
        if (!this.isKeyboardLive) {
            this.currentOffsetY = (float) Math.max(this.currentOffsetY, f3);
        }
        float fMin = (float) Math.min(this.currentOffsetY, this.visibleHeight / 2.5f);
        this.currentOffsetY = fMin;
        if (this.isKeyboardLive || !this.isHasTouchDown || this.dragging) {
            return;
        }
        float f4 = this.contentHeight;
        int i2 = this.visibleHeight;
        if ((f4 >= i2 || fMin >= 0.0f) && (fMin >= 0.0f || fMin >= i2 - f4)) {
            return;
        }
        this.dragging = true;
    }

    private final void reviseOffsetByContent() {
        int i = this.visibleHeight;
        float f = i;
        float f2 = this.contentHeight;
        float f3 = f > f2 ? 0.0f : i - f2;
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        if (!pageInkPreModel.getIsUnLimitToNormal()) {
            this.currentOffsetX = (float) Math.min(Math.max(this.currentOffsetX, getMinTranX()), getMaxTranX());
        }
        float fMax = (float) Math.max(this.currentOffsetY, f3);
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

    private final void computeLayoutData() {
        float f;
        int i;
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        List<PageItemData> itemDataList = pageInkPreModel.getItemDataList();
        if (itemDataList.isEmpty()) {
            return;
        }
        RectF rectF = new RectF(0.0f, 0.0f, this.visibleWidth, this.visibleHeight);
        this.maxVisibleArea = 0.0f;
        float f2 = this.topMargin;
        int size = itemDataList.size();
        int i2 = 0;
        boolean z = false;
        int i3 = -1;
        int iMax = -1;
        while (i2 < size) {
            PageItemData pageItemData = itemDataList.get(i2);
            pageItemData.standardWidth = this.standardWidth;
            pageItemData.isUnLimit = this.model.getIsUnlimited();
            if (this.model.getIsUnlimited()) {
                pageItemData.unLimitStart = (int) Math.max(pageItemData.unLimitStart, this.mStartEdge);
                pageItemData.unLimitEnd = (int) Math.min(pageItemData.unLimitEnd, this.mEndEdge);
                pageItemData.unLimitTop = (int) Math.max(pageItemData.unLimitTop, this.mTopEdge);
                pageItemData.unLimitBottom = (int) Math.min(pageItemData.unLimitBottom, this.mBottomEdge);
                this.mStartEdge = pageItemData.unLimitStart;
                this.mEndEdge = pageItemData.unLimitEnd;
                this.mTopEdge = pageItemData.unLimitTop;
                this.mBottomEdge = pageItemData.unLimitBottom;
                pageItemData.maxWidth = (int) Math.max((pageItemData.unLimitStart - pageItemData.unLimitEnd) * this.visibleWidth, pageItemData.maxWidth);
                pageItemData.maxHeight = (int) Math.max((pageItemData.unLimitTop - pageItemData.unLimitBottom) * this.visibleHeight, pageItemData.maxHeight);
                this.maxTop = (int) Math.max(this.maxTop, this.currentOffsetY / this.scale);
                this.maxStart = (int) Math.max(this.maxStart, this.currentOffsetX / this.scale);
                this.maxEnd = (int) Math.min(this.maxEnd, this.currentOffsetX / this.scale);
            }
            float itemHeight = pageItemData.getItemHeight(this.standardWidth, this.scale);
            if (!pageItemData.useNewSize && !this.model.getIsUnlimited()) {
                pageItemData.maxHeight = (this.model.getWindowWidth() * pageItemData.height) / pageItemData.width;
                pageItemData.maxWidth = (int) Math.max(this.model.getWindowWidth(), pageItemData.maxWidth);
            }
            if (!pageItemData.isFixSize && !pageItemData.unLimitToNormal) {
                float f3 = pageItemData.maxHeight / pageItemData.maxWidth;
                pageItemData.maxWidth = this.model.getWindowWidth();
                pageItemData.maxHeight = (int) (this.model.getWindowWidth() * f3);
            }
            int i4 = i2;
            float fMax = (float) Math.max(this.contentWidth, Math.max(this.standardWidth, pageItemData.maxWidth));
            this.contentWidth = fMax;
            if (fMax > this.standardWidth) {
                f = f2;
                float dimension = ((this.standardWidth - (pageItemData.maxWidth * this.scale)) / 2.0f) + this.context.getResources().getDimension(R.dimen.creation_paint_view_margin);
                RectF rectF2 = pageItemData.layoutRectF;
                float f4 = this.currentOffsetX;
                rectF2.set(dimension + f4, f + this.currentOffsetY, dimension + f4 + (pageItemData.maxWidth * this.scale), f + itemHeight + this.currentOffsetY);
            } else if (pageItemData.maxWidth * this.scale < this.standardWidth || pageItemData.maxWidth != this.contentWidth) {
                f = f2;
                float f5 = ((this.standardWidth - (pageItemData.maxWidth * this.scale)) / 2.0f) + this.leftMargin;
                float f6 = (((pageItemData.sizeUnit != 0 || pageItemData.isFixSize) ? pageItemData.maxWidth : this.standardWidth) * this.scale) + f5;
                float fMax2 = this.scale > 1.0f ? (float) ((((Math.max(this.contentWidth, this.standardWidth) * ((double) this.scale)) - ((double) (f6 - f5))) / ((double) 2)) - ((double) f5)) : 0.0f;
                RectF rectF3 = pageItemData.layoutRectF;
                float f7 = this.currentOffsetX;
                float f8 = this.currentOffsetY;
                rectF3.set(f5 + f7 + fMax2, f + f8, f6 + f7 + fMax2, f + itemHeight + f8);
            } else {
                RectF rectF4 = pageItemData.layoutRectF;
                float f9 = this.currentOffsetX;
                int i5 = this.leftMargin;
                float f10 = this.scale;
                float f11 = this.currentOffsetY;
                f = f2;
                rectF4.set((i5 * f10) + f9, f2 + f11, (this.standardWidth * f10) + f9 + (i5 * f10), f + itemHeight + f11);
            }
            if (z == 0 && RectF.intersects(rectF, pageItemData.layoutRectF)) {
                RectF layoutRectF = pageItemData.layoutRectF;
                Intrinsics.checkNotNullExpressionValue(layoutRectF, "layoutRectF");
                i = i4;
                computeCurrentPosition(i, layoutRectF);
                z = true;
            } else {
                i = i4;
                z = z;
            }
            if (RectF.intersects(rectF, pageItemData.layoutRectF)) {
                if (i3 == -1) {
                    i3 = i;
                }
                iMax = (int) Math.max(iMax, i);
            }
            i2 = i + 1;
            f2 = f + itemHeight + (this.divider * this.scale);
        }
        handleItemView(i3, iMax, itemDataList);
        this.contentHeight = (f2 - (this.divider * this.scale)) + this.bottomMargin;
        this.model.onLayout();
    }

    private final void handleItemView(int start, int end, List<? extends PageItemData> list) {
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        if (pageInkPreModel.getIsUnlimited()) {
            start = 0;
            end = 0;
        }
        if (this.model.getCurrentItemView() == null) {
            this.model.createItemView();
            this.model.bindView();
        }
        if (start < 0 || end < 0) {
            return;
        }
        if (end - start < 0) {
            start--;
            if (end - start < 0 && end < list.size() - 1) {
                end++;
            }
        }
        int i = start;
        PagePreItemView currentItemView = this.model.getCurrentItemView();
        if (!this.model.getIsUnlimited() || this.model.getIsUnLimitToNormal()) {
            Log.i(TAG, "handleItemView addPages");
            RectF rectF = new RectF();
            rectF.set(list.get(0).layoutRectF.left, list.get(0).layoutRectF.top, list.get(list.size() - 1).layoutRectF.right, list.get(list.size() - 1).layoutRectF.bottom);
            if (currentItemView != null) {
                currentItemView.update(i, rectF, this.scale * this.pageScaleRatio, this.standardWidth, this.currentOffsetX, this.currentOffsetY);
            }
            currentItemView = currentItemView;
        }
        if (i > end) {
            return;
        }
        int i2 = i;
        while (true) {
            RectF rectF2 = list.get(i2).layoutRectF;
            RectF rectF3 = new RectF();
            rectF3.set(rectF2);
            if (this.model.getIsUnlimited()) {
                if (this.showHeadWhenExpanse && this.headViewVis) {
                    float f = this.currentOffsetY;
                    float f2 = this.headCurHeight + (f - this.lastCurrentY);
                    this.headCurHeight = f2;
                    this.lastCurrentY = f;
                    float fMin = (float) Math.min(f2, this.headViewHeight);
                    this.headCurHeight = fMin;
                    if (fMin <= 0.0f) {
                        this.headCurHeight = 0.0f;
                        this.showHeadWhenExpanse = false;
                        this.headViewVis = false;
                    }
                    rectF3.top = this.headCurHeight;
                    if (currentItemView != null) {
                        currentItemView.update(i2, rectF3, this.scale * this.pageScaleRatio, this.standardWidth, this.currentOffsetX, this.currentOffsetY - rectF3.top);
                    }
                } else if (rectF3.top > this.mTopEdge * this.scale * this.mExpanseDistance) {
                    rectF3.top -= (this.mTopEdge * this.scale) * this.mExpanseDistance;
                    this.headViewVis = rectF3.top >= ((float) this.headViewHeight);
                    if (currentItemView != null) {
                        currentItemView.update(i2, rectF3, this.scale * this.pageScaleRatio, this.standardWidth, this.currentOffsetX, this.currentOffsetY - rectF3.top);
                    }
                } else {
                    rectF3.set(0.0f, 0.0f, this.visibleWidth, this.visibleHeight);
                    if (currentItemView != null) {
                        currentItemView.update(i2, rectF3, this.scale * this.pageScaleRatio, this.standardWidth, this.currentOffsetX, this.currentOffsetY);
                    }
                }
            }
            if (i2 == end) {
                return;
            } else {
                i2++;
            }
        }
    }

    private final void computeCurrentPosition(int position, RectF layoutRectF) {
        float fMin = (((float) Math.min(this.visibleWidth, layoutRectF.right)) - ((float) Math.max(0.0d, layoutRectF.left))) * (((float) Math.min(this.visibleHeight, layoutRectF.bottom)) - ((float) Math.max(0.0d, layoutRectF.top)));
        if (fMin > this.maxVisibleArea) {
            PageInkPreModel pageInkPreModel = this.model;
            Intrinsics.checkNotNull(pageInkPreModel);
            this.currentPosition = pageInkPreModel.getCurrentPosition();
            this.maxVisibleArea = fMin;
        }
    }

    private final void computeContentHeight() {
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        List<PageItemData> itemDataList = pageInkPreModel.getItemDataList();
        if (itemDataList.isEmpty()) {
            return;
        }
        float itemHeight = this.topMargin;
        Iterator<PageItemData> it = itemDataList.iterator();
        while (it.hasNext()) {
            itemHeight += it.next().getItemHeight(this.standardWidth, this.scale) + (this.divider * this.scale);
        }
        this.contentHeight = (itemHeight + this.bottomMargin) - (this.divider * this.scale);
    }

    public final float computeItemTopByPosition(int position) {
        float itemHeight = 0.0f;
        if (position == 0) {
            return 0.0f;
        }
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        List<PageItemData> itemDataList = pageInkPreModel.getItemDataList();
        if (itemDataList.isEmpty() || position >= itemDataList.size()) {
            return -1.0f;
        }
        for (int i = 0; i < position; i++) {
            itemHeight += itemDataList.get(i).getItemHeight(this.standardWidth, this.scale) + (this.divider * this.scale);
        }
        return itemHeight;
    }

    public final void notifyDataChanged() {
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        this.currentPosition = pageInkPreModel.getCurrentPosition();
        reviseOffset();
        computeLayoutData();
        int i = this.currentPosition;
        if (i != -1) {
            this.model.onCurrentPageChanged(i);
        }
    }

    public final void notifyDataChangedNew() {
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        int currentPosition = pageInkPreModel.getCurrentPosition();
        this.currentPosition = currentPosition;
        if (currentPosition != -1) {
            this.model.onCurrentPageChanged(currentPosition);
        }
    }

    private final void limitScale() {
        if (this.scale > 3.0f) {
            this.scale = 3.0f;
            this.tmpScaleFactor = 3.0f;
        }
        if (this.scale < 0.55f) {
            this.scale = 0.55f;
            this.tmpScaleFactor = 0.55f;
        }
    }

    public final void onScrollEnd() {
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        if (pageInkPreModel.getCurrentItemView() == null) {
            return;
        }
        Log.e(TAG, "xl-- onScrollEnd: " + this.model.isMaxPageCount() + ", isHasTouchDown: " + this.isHasTouchDown + ", " + this.dragging + ' ' + this.dragState);
        if (!this.model.isMaxPageCount() && this.isHasTouchDown && this.dragging && 1 == this.dragState) {
            this.dragState = 0;
            this.model.onFooterViewMaxHeight();
        } else {
            handleSpringBack();
        }
        this.dragging = false;
        this.dragState = 0;
        PagePreItemView currentItemView = this.model.getCurrentItemView();
        if (currentItemView != null) {
            currentItemView.onScrollEnd(this.scale * this.pageScaleRatio, this.standardWidth);
        }
    }

    public final void onScrollEndNew() {
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        if (pageInkPreModel.getCurrentItemView() == null) {
            return;
        }
        Log.e(TAG, "xl-- onScrollEnd new: " + this.model.isMaxPageCount() + ", isHasTouchDown: " + this.isHasTouchDown + ", " + this.dragState);
        if (!this.model.isMaxPageCount() && this.isHasTouchDown && 1 == this.dragState) {
            this.dragState = 0;
            this.model.onFooterViewMaxHeight();
        }
    }

    private final void handleSpringBack() {
        float f;
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        if (pageInkPreModel.getIsUnlimited()) {
            if (this.showHeadWhenExpanse && this.headViewVis) {
                float f2 = this.headCurHeight;
                if (f2 < this.headViewHeight - 10) {
                    float f3 = this.currentOffsetX;
                    this.lastCurrentX = f3;
                    float f4 = this.currentOffsetY;
                    this.lastCurrentY = f4;
                    this.scroller.startScroll((int) f3, (int) f4, 0, -((int) f2), 200);
                    this.pageInkView.postInvalidate();
                    return;
                }
            }
            float f5 = this.currentOffsetY;
            float f6 = this.scale;
            int i = this.mExpanseDistance;
            int i2 = this.mTopEdge;
            if (f5 <= i * f6 * i2 || f5 >= (((i * f6) * i2) + this.headViewHeight) - 10) {
                return;
            }
            this.scroller.startScroll((int) this.currentOffsetX, (int) f5, 0, -((int) (f5 - ((f6 * i) * i2))), 200);
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
            this.isFlinging = true;
            this.scroller.startScroll((int) this.currentOffsetX, (int) this.currentOffsetY, 0, (int) f, 200);
            this.pageInkView.postInvalidate();
            return;
        }
        notifyDataChanged();
    }

    public final boolean isDragging() {
        float f;
        PageInkPreModel pageInkPreModel = this.model;
        Intrinsics.checkNotNull(pageInkPreModel);
        if (!pageInkPreModel.getIsUnlimited() && !this.model.getIsUnLimitToNormal()) {
            this.currentOffsetX = (float) Math.min(Math.max(this.currentOffsetX, getMinTranX()), getMaxTranX());
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
        return !(f == 0.0f);
    }

    public final void setDraggingState(int state) {
        this.dragState = state;
    }

    public final void onTouchDown() {
        this.isHasTouchDown = true;
        this.dragging = false;
        this.dragState = 0;
    }

    public final void setKeyboardLive(boolean isLive) {
        this.isKeyboardLive = isLive;
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        this.leftMargin = left;
        this.topMargin = top;
        this.rightMargin = right;
        this.bottomMargin = bottom;
        int i = this.visibleWidth;
        if (i > 0) {
            this.pageScaleRatio = (((i - left) - right) * 1.0f) / i;
        }
    }

    private final void prepareHeight(Context context) {
        String language = Locale.getDefault().getLanguage();
        if (context.getResources().getConfiguration().fontScale > DisplayUtils.DEFAULT_FONT_SIZE) {
            this.headViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.creation_header_view_height_max);
            if (Intrinsics.areEqual(language, "bo") || Intrinsics.areEqual(language, "ug")) {
                this.headViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.creation_header_view_height_language_big_font);
                return;
            }
            return;
        }
        this.headViewHeight = context.getResources().getDimensionPixelOffset(R.dimen.creation_header_view_height_min);
    }

    public final float[] getLocation() {
        return new float[]{this.currentOffsetX, this.currentOffsetY};
    }

    public final void setContentWidth(float contentWidth) {
        this.contentWidth = contentWidth;
    }

    public final int[] getUnLimitBound() {
        int i = this.mStartEdge;
        int i2 = this.mExpanseDistance;
        return new int[]{(i * i2) + (i2 - this.visibleWidth), this.mTopEdge * i2, (this.mEndEdge - 1) * i2, (this.mBottomEdge - 1) * i2};
    }

    public final int[] getCenterPoint() {
        return new int[]{(int) ((getMaxTranX() + getMinTranX()) / 2), this.maxTop};
    }

    static {
        Intrinsics.checkNotNullExpressionValue("PagePreLayoutManager", "getSimpleName(...)");
        TAG = "PagePreLayoutManager";
    }
}
