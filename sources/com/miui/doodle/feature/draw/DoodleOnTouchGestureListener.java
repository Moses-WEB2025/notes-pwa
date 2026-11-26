package com.miui.doodle.feature.draw;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.DoodleRotatableItemBase;
import com.miui.doodle.base.DoodleSelectableItemBase;
import com.miui.doodle.base.ScaleGestureDetectorApi27;
import com.miui.doodle.base.TouchGestureDetector;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.base.core.IDoodleSelectableItem;
import com.miui.doodle.document.Dot;
import com.miui.doodle.document.Layer;
import com.miui.doodle.document.LayerBitmap;
import com.miui.doodle.utils.DrawUtil;
import com.miui.doodle.utils.HapticFeedbackTool;
import com.miui.doodle.utils.UIUtils;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.note.algorithm.GraphRecognition;
import com.miui.note.algorithm.common.Graph;
import com.miui.note.algorithm.common.GraphType;
import com.miui.note.algorithm.common.Point;
import com.miui.notes.base.utils.Logger;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: DoodleOnTouchGestureListener.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0016\u0018\u0000 z2\u00020\u0001:\u0002yzB\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010G\u001a\u00020H2\b\u0010I\u001a\u0004\u0018\u00010JJ\u0010\u0010K\u001a\u00020%2\u0006\u0010L\u001a\u00020MH\u0016J\u0010\u0010N\u001a\u00020H2\u0006\u0010L\u001a\u00020MH\u0016J\u0018\u0010O\u001a\u00020H2\u0006\u0010L\u001a\u00020M2\u0006\u0010P\u001a\u00020%H\u0016J*\u0010Q\u001a\u00020%2\b\u0010R\u001a\u0004\u0018\u00010M2\u0006\u0010S\u001a\u00020M2\u0006\u0010T\u001a\u00020\t2\u0006\u0010U\u001a\u00020\tH\u0016J \u0010X\u001a\u00020H2\u0006\u0010Y\u001a\u00020\u001c2\u0006\u0010Z\u001a\u00020\t2\u0006\u0010[\u001a\u00020\tH\u0002J \u0010\\\u001a\u00020H2\u0006\u0010]\u001a\u00020^2\u0006\u0010Z\u001a\u00020\t2\u0006\u0010[\u001a\u00020\tH\u0002J\u0010\u0010_\u001a\u00020%2\u0006\u0010L\u001a\u00020MH\u0016J \u0010`\u001a\u00020H2\u0006\u0010a\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002J\u0010\u0010b\u001a\u00020H2\u0006\u0010a\u001a\u00020\u0003H\u0002J\u0018\u0010c\u001a\u00020H2\u0006\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020gH\u0002J\u0010\u0010h\u001a\u00020%2\u0006\u0010i\u001a\u00020jH\u0016J\u0010\u0010n\u001a\u00020%2\u0006\u0010i\u001a\u00020jH\u0016J\u0010\u0010o\u001a\u00020H2\u0006\u0010i\u001a\u00020jH\u0016J\u0006\u0010p\u001a\u00020HJ\u0010\u0010q\u001a\u00020H2\u0006\u0010r\u001a\u00020%H\u0002J\u0010\u0010s\u001a\u00020%2\u0006\u0010t\u001a\u00020-H\u0002J\u0018\u0010u\u001a\u00020H2\u0006\u0010v\u001a\u00020g2\u0006\u0010w\u001a\u00020-H\u0002J\u0006\u0010x\u001a\u00020HR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0012R\u0012\u0010\u0013\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0012R\u000e\u0010\u0014\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010!\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u00020%X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010&\"\u0004\b'\u0010(R\u000e\u0010)\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020%X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010.\u001a\n\u0012\u0004\u0012\u000200\u0018\u00010/X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u000102X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00103\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u001a\u00108\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u00105\"\u0004\b:\u00107R\u001a\u0010;\u001a\u00020<X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R(\u0010A\u001a\u0004\u0018\u00010B2\b\u0010A\u001a\u0004\u0018\u00010B@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u0010\u0010V\u001a\u0004\u0018\u00010WX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010k\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010l\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010m\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006{"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleOnTouchGestureListener;", "Lcom/miui/doodle/base/TouchGestureDetector$OnTouchGestureListener;", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", "selectionListener", "Lcom/miui/doodle/feature/draw/DoodleOnTouchGestureListener$ISelectionListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/doodle/feature/draw/DoodleView;Lcom/miui/doodle/feature/draw/DoodleOnTouchGestureListener$ISelectionListener;)V", "touchX", "", "touchY", "touchZ", "lastTouchX", "lastTouchY", "lastTouchZ", "touchDownX", "touchDownY", "lastFocusX", "Ljava/lang/Float;", "lastFocusY", "touchCentreX", "touchCentreY", "startX", "startY", "rotateDiff", "currPath", "Landroid/graphics/Path;", "currDoodlePath", "Lcom/miui/doodle/feature/draw/DoodleBrushPath;", "scaleAnimator", "Landroid/animation/ValueAnimator;", "scaleAnimTransX", "scaleAnimTranY", "translateAnimator", "transAnimOldY", "transAnimY", "isSupportScaleItem", "", "()Z", "setSupportScaleItem", "(Z)V", "minDistance", "minTouchGap", "needPrintDebugPoint", "debugDataIndex", "", "debugPoints", "", "Lcom/miui/doodle/feature/draw/DebugPoint;", "replacedItem", "Lcom/miui/doodle/base/core/IDoodleItem;", "hasMovedXOnScrollBegin", "getHasMovedXOnScrollBegin", "()F", "setHasMovedXOnScrollBegin", "(F)V", "hasMovedYOnScrollBegin", "getHasMovedYOnScrollBegin", "setHasMovedYOnScrollBegin", "moveItemSafeZone", "Landroid/graphics/PointF;", "getMoveItemSafeZone", "()Landroid/graphics/PointF;", "setMoveItemSafeZone", "(Landroid/graphics/PointF;)V", "selectedItem", "Lcom/miui/doodle/base/core/IDoodleSelectableItem;", "getSelectedItem", "()Lcom/miui/doodle/base/core/IDoodleSelectableItem;", "setSelectedItem", "(Lcom/miui/doodle/base/core/IDoodleSelectableItem;)V", "cacheOldDrawingLayerBitmap", "", "layerView", "Lcom/miui/doodle/feature/draw/LayerView;", "onDown", "e", "Landroid/view/MotionEvent;", "onScrollBegin", "onScrollEnd", "needTrim", "onScroll", "e1", "e2", "distanceX", "distanceY", "timer", "Ljava/util/Timer;", "onShapeRecognized", "path", AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y, "onShapeChangedOnScroll", "shape", "Lcom/miui/doodle/feature/draw/DoodleShape;", "onSingleTapUp", "onElementEraserScroll", EditStat.ELEMENT_DOODLE, "onElementEraserScrollEnd", "onUpdateTextOrBitmap", CloudPushConstants.XML_ITEM, "Lcom/miui/doodle/base/DoodleSelectableItemBase;", "dirtyRect", "Landroid/graphics/Rect;", "onScaleBegin", "detector", "Lcom/miui/doodle/base/ScaleGestureDetectorApi27;", "pendingX", "pendingY", "pendingScale", "onScale", "onScaleEnd", EditStat.ELEMENT_CENTER, "limitBound", "anim", "useTouchZ", "type", "expandRect", "rect", "expand", "clearViewAnimator", "ISelectionListener", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class DoodleOnTouchGestureListener extends TouchGestureDetector.OnTouchGestureListener {
    public static final String TAG = "DoodleOnTouchGestureListener";
    private static final float VALUE = 1.0f;
    private DoodleBrushPath currDoodlePath;
    private Path currPath;
    private int debugDataIndex;
    private List<DebugPoint> debugPoints;
    private final DoodleView doodleView;
    private float hasMovedXOnScrollBegin;
    private float hasMovedYOnScrollBegin;
    private boolean isSupportScaleItem;
    private Float lastFocusX;
    private Float lastFocusY;
    private float lastTouchX;
    private float lastTouchY;
    private float lastTouchZ;
    private final float minDistance;
    private float minTouchGap;
    private PointF moveItemSafeZone;
    private final boolean needPrintDebugPoint;
    private float pendingScale;
    private float pendingX;
    private float pendingY;
    private IDoodleItem replacedItem;
    private float rotateDiff;
    private float scaleAnimTranY;
    private float scaleAnimTransX;
    private ValueAnimator scaleAnimator;
    private IDoodleSelectableItem selectedItem;
    private ISelectionListener selectionListener;
    private float startX;
    private float startY;
    private Timer timer;
    private float touchCentreX;
    private float touchCentreY;
    private float touchDownX;
    private float touchDownY;
    private float touchX;
    private float touchY;
    private float touchZ;
    private float transAnimOldY;
    private float transAnimY;
    private ValueAnimator translateAnimator;
    private static ExecutorService sExecutorService = Executors.newSingleThreadExecutor();

    /* JADX INFO: compiled from: DoodleOnTouchGestureListener.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J \u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH&J\u0018\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0012H&¨\u0006\u0013À\u0006\u0003"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodleOnTouchGestureListener$ISelectionListener;", "", "onSelectedItem", "", EditStat.ELEMENT_DOODLE, "Lcom/miui/doodle/base/core/IDoodle;", "selectableItem", "Lcom/miui/doodle/base/core/IDoodleSelectableItem;", "selected", "", "onCreateSelectableItem", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, "onDeleteItem", "onDeleteItemByButton", "onTextEdit", "doodleText", "Lcom/miui/doodle/feature/draw/DoodleText;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface ISelectionListener {
        void onCreateSelectableItem(IDoodle doodle, float x, float y);

        void onDeleteItem(IDoodle doodle, IDoodleSelectableItem selectableItem);

        void onDeleteItemByButton(IDoodle doodle, IDoodleSelectableItem selectableItem);

        void onSelectedItem(IDoodle doodle, IDoodleSelectableItem selectableItem, boolean selected);

        void onTextEdit(IDoodle doodle, DoodleText doodleText);
    }

    /* JADX INFO: compiled from: DoodleOnTouchGestureListener.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GraphType.values().length];
            try {
                iArr[GraphType.CIRCLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[GraphType.ELLIPSE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[GraphType.STRAIGHT_LINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final void onUpdateTextOrBitmap(DoodleSelectableItemBase item, Rect dirtyRect) {
    }

    private final boolean useTouchZ(int type) {
        return type == 2;
    }

    public DoodleOnTouchGestureListener(DoodleView doodleView, ISelectionListener iSelectionListener) {
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        this.doodleView = doodleView;
        this.selectionListener = iSelectionListener;
        this.isSupportScaleItem = true;
        UIUtils uIUtils = UIUtils.INSTANCE;
        Context context = doodleView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this.minDistance = uIUtils.dip2px(context, 18.0f);
        this.minTouchGap = 1.0f;
        this.debugPoints = new ArrayList();
        this.moveItemSafeZone = new PointF();
        this.pendingScale = 1.0f;
    }

    /* JADX INFO: renamed from: isSupportScaleItem, reason: from getter */
    public boolean getIsSupportScaleItem() {
        return this.isSupportScaleItem;
    }

    public void setSupportScaleItem(boolean z) {
        this.isSupportScaleItem = z;
    }

    public final float getHasMovedXOnScrollBegin() {
        return this.hasMovedXOnScrollBegin;
    }

    public final void setHasMovedXOnScrollBegin(float f) {
        this.hasMovedXOnScrollBegin = f;
    }

    public final float getHasMovedYOnScrollBegin() {
        return this.hasMovedYOnScrollBegin;
    }

    public final void setHasMovedYOnScrollBegin(float f) {
        this.hasMovedYOnScrollBegin = f;
    }

    public final PointF getMoveItemSafeZone() {
        return this.moveItemSafeZone;
    }

    public final void setMoveItemSafeZone(PointF pointF) {
        Intrinsics.checkNotNullParameter(pointF, "<set-?>");
        this.moveItemSafeZone = pointF;
    }

    public final IDoodleSelectableItem getSelectedItem() {
        return this.selectedItem;
    }

    public final void setSelectedItem(IDoodleSelectableItem iDoodleSelectableItem) {
        List<IDoodleItem> itemStackOnViewCanvas;
        IDoodleSelectableItem iDoodleSelectableItem2 = this.selectedItem;
        this.selectedItem = iDoodleSelectableItem;
        if (iDoodleSelectableItem2 != null) {
            iDoodleSelectableItem2.setSelected(false);
            ISelectionListener iSelectionListener = this.selectionListener;
            if (iSelectionListener != null) {
                iSelectionListener.onSelectedItem(this.doodleView, iDoodleSelectableItem2, false);
            }
            LayerView currentLayerView = this.doodleView.getCurrentLayerView();
            if (currentLayerView != null && (itemStackOnViewCanvas = currentLayerView.getItemStackOnViewCanvas()) != null) {
                itemStackOnViewCanvas.remove(iDoodleSelectableItem2);
            }
            this.doodleView.invalidate();
        }
        IDoodleSelectableItem iDoodleSelectableItem3 = this.selectedItem;
        if (iDoodleSelectableItem3 != null) {
            iDoodleSelectableItem3.setSelected(true);
            ISelectionListener iSelectionListener2 = this.selectionListener;
            if (iSelectionListener2 != null) {
                iSelectionListener2.onSelectedItem(this.doodleView, iDoodleSelectableItem3, true);
            }
        }
    }

    public final void cacheOldDrawingLayerBitmap(final LayerView layerView) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        LayerView currentLayerView = this.doodleView.getCurrentLayerView();
        Integer numValueOf = (currentLayerView == null || (layer = currentLayerView.getLayer()) == null || (layerBitmapList = layer.getLayerBitmapList()) == null) ? null : Integer.valueOf(layerBitmapList.size());
        Intrinsics.checkNotNull(numValueOf);
        final int iIntValue = numValueOf.intValue() - 1;
        sExecutorService.execute(new Runnable() { // from class: com.miui.doodle.feature.draw.DoodleOnTouchGestureListener$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                DoodleOnTouchGestureListener.cacheOldDrawingLayerBitmap$lambda$1(layerView, iIntValue);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cacheOldDrawingLayerBitmap$lambda$1(LayerView layerView, int i) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        LayerBitmap layerBitmap;
        Logger.INSTANCE.d(TAG, "cacheOldDrawingLayerBitmap");
        if (layerView == null || (layer = layerView.getLayer()) == null || (layerBitmapList = layer.getLayerBitmapList()) == null || (layerBitmap = layerBitmapList.get(i)) == null) {
            return;
        }
        layerBitmap.cacheImageBeforeChanged();
    }

    @Override // com.miui.doodle.base.TouchGestureDetector.OnTouchGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        if (!this.doodleView.isEnabled()) {
            return false;
        }
        float x = e.getX();
        this.touchDownX = x;
        this.touchX = x;
        float y = e.getY();
        this.touchDownY = y;
        this.touchY = y;
        return true;
    }

    @Override // com.miui.doodle.base.TouchGestureDetector.OnTouchGestureListener, com.miui.doodle.base.TouchGestureDetector.IOnTouchGestureListener
    public void onScrollBegin(MotionEvent e) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        Object next;
        Layer layer2;
        Layer layer3;
        Layer layer4;
        Layer layer5;
        Intrinsics.checkNotNullParameter(e, "e");
        if (this.doodleView.isEnabled()) {
            this.doodleView.setDrawing(true);
            this.doodleView.cancelAutoSave();
            this.touchX = e.getX();
            this.touchY = e.getY();
            this.touchZ = e.getPressure();
            if (e.getToolType(0) == 2 || e.getToolType(0) == 3) {
                this.minTouchGap = 0.5f;
            }
            this.lastTouchX = this.touchX;
            this.lastTouchY = this.touchY;
            this.lastTouchZ = this.touchZ;
            if (this.needPrintDebugPoint) {
                this.debugDataIndex++;
                List<DebugPoint> list = this.debugPoints;
                if (list != null) {
                    list.clear();
                }
                DebugPoint debugPoint = new DebugPoint();
                debugPoint.setX(this.lastTouchX);
                debugPoint.setY(this.lastTouchY);
                List<DebugPoint> list2 = this.debugPoints;
                if (list2 != null) {
                    list2.add(debugPoint);
                }
            }
            this.doodleView.setScrollingDoodle(true);
            if (this.doodleView.getIsEditMode() || this.doodleView.getPen().isPenEditable()) {
                IDoodleSelectableItem iDoodleSelectableItem = this.selectedItem;
                if (iDoodleSelectableItem != null) {
                    if (iDoodleSelectableItem instanceof DoodleSelectableItemBase) {
                        Rect boundsInCanvas = ((DoodleSelectableItemBase) iDoodleSelectableItem).getBoundsInCanvas();
                        float fCenterX = boundsInCanvas.centerX();
                        LayerView currentLayerView = this.doodleView.getCurrentLayerView();
                        Integer numValueOf = (currentLayerView == null || (layer5 = currentLayerView.getLayer()) == null) ? null : Integer.valueOf(layer5.getWidth());
                        Intrinsics.checkNotNull(numValueOf);
                        this.hasMovedXOnScrollBegin = fCenterX - (numValueOf.intValue() / 2.0f);
                        float fCenterY = boundsInCanvas.centerY();
                        LayerView currentLayerView2 = this.doodleView.getCurrentLayerView();
                        Integer numValueOf2 = (currentLayerView2 == null || (layer4 = currentLayerView2.getLayer()) == null) ? null : Integer.valueOf(layer4.getHeight());
                        Intrinsics.checkNotNull(numValueOf2);
                        this.hasMovedYOnScrollBegin = fCenterY - (numValueOf2.intValue() / 2.0f);
                        PointF pointF = this.moveItemSafeZone;
                        LayerView currentLayerView3 = this.doodleView.getCurrentLayerView();
                        Integer numValueOf3 = (currentLayerView3 == null || (layer3 = currentLayerView3.getLayer()) == null) ? null : Integer.valueOf(layer3.getWidth());
                        Intrinsics.checkNotNull(numValueOf3);
                        pointF.x = numValueOf3.intValue() * 0.2f;
                        PointF pointF2 = this.moveItemSafeZone;
                        LayerView currentLayerView4 = this.doodleView.getCurrentLayerView();
                        Integer numValueOf4 = (currentLayerView4 == null || (layer2 = currentLayerView4.getLayer()) == null) ? null : Integer.valueOf(layer2.getHeight());
                        Intrinsics.checkNotNull(numValueOf4);
                        pointF2.y = numValueOf4.intValue() * 0.1f;
                    }
                    LayerView currentLayerView5 = this.doodleView.getCurrentLayerView();
                    if (currentLayerView5 != null && (layer = currentLayerView5.getLayer()) != null && (layerBitmapList = layer.getLayerBitmapList()) != null) {
                        Iterator<T> it = layerBitmapList.iterator();
                        do {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                        } while (!((LayerBitmap) next).getItems().contains(iDoodleSelectableItem));
                        LayerBitmap layerBitmap = (LayerBitmap) next;
                        if (layerBitmap != null) {
                            layerBitmap.cacheImageBeforeChanged();
                        }
                    }
                    iDoodleSelectableItem.cacheTransitInfoBeforeChanged();
                    PointF location = iDoodleSelectableItem.getLocation();
                    this.startX = location.x;
                    this.startY = location.y;
                    if (iDoodleSelectableItem instanceof DoodleRotatableItemBase) {
                        DoodleRotatableItemBase doodleRotatableItemBase = (DoodleRotatableItemBase) iDoodleSelectableItem;
                        if (doodleRotatableItemBase.canRotate(this.doodleView.toX(this.touchX), this.doodleView.toY(this.touchY))) {
                            doodleRotatableItemBase.setRotating(true);
                            this.rotateDiff = doodleRotatableItemBase.getItemRotate() - DrawUtil.INSTANCE.computeAngle(doodleRotatableItemBase.getPivotPoint().x * doodleRotatableItemBase.getItemScale(), doodleRotatableItemBase.getPivotPoint().y * doodleRotatableItemBase.getItemScale(), this.doodleView.toX(this.touchX), this.doodleView.toY(this.touchY));
                            Logger.INSTANCE.d(TAG, "rotateDiff: " + this.rotateDiff);
                        }
                    }
                }
                if (this.selectedItem == null && this.doodleView.getIsEditMode()) {
                    this.startX = this.doodleView.getDoodleTranslationX();
                    this.startY = this.doodleView.getDoodleTranslationY();
                }
            } else if (this.doodleView.getPen().isElementEraser()) {
                IDoodle.refresh$default(this.doodleView, null, 1, null);
            } else if (!this.doodleView.getIsShowConfigPopupWindow()) {
                if (!this.doodleView.getPen().isEraser()) {
                    cacheOldDrawingLayerBitmap(this.doodleView.getCurrentLayerView());
                }
                Path path = new Path();
                this.currPath = path;
                Intrinsics.checkNotNull(path);
                path.moveTo(this.doodleView.toX(this.touchX), this.doodleView.toY(this.touchY));
                DoodleBrushPath path2 = DoodleBrushPath.INSTANCE.toPath(this.doodleView, this.doodleView.toX(e.getX()), this.doodleView.toY(e.getY()), e.getEventTime() - e.getDownTime(), this.minTouchGap);
                this.currDoodlePath = path2;
                if (path2 != null && path2.isEraserPath()) {
                    Iterator<T> it2 = path2.getDots().iterator();
                    while (it2.hasNext()) {
                        this.doodleView.eraserPathItem((Dot) it2.next());
                    }
                    this.doodleView.eraserLayerBitmap(path2.getDots());
                }
                DoodleView doodleView = this.doodleView;
                DoodleBrushPath doodleBrushPath = this.currDoodlePath;
                Intrinsics.checkNotNull(doodleBrushPath);
                doodleView.markItemToOptimizeDrawing(doodleBrushPath);
            }
            IDoodle.refresh$default(this.doodleView, null, 1, null);
        }
    }

    @Override // com.miui.doodle.base.TouchGestureDetector.OnTouchGestureListener
    public void onScrollEnd(MotionEvent e, boolean needTrim) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        Intrinsics.checkNotNullParameter(e, "e");
        if (this.doodleView.isEnabled()) {
            this.lastTouchX = this.touchX;
            this.lastTouchY = this.touchY;
            this.lastTouchZ = this.touchZ;
            this.touchX = e.getX();
            this.touchY = e.getY();
            this.touchZ = e.getPressure();
            if (this.needPrintDebugPoint) {
                DebugPoint debugPoint = new DebugPoint();
                debugPoint.setX(this.lastTouchX);
                debugPoint.setY(this.lastTouchY);
                List<DebugPoint> list = this.debugPoints;
                if (list != null) {
                    list.add(debugPoint);
                }
            }
            this.doodleView.setScrollingDoodle(false);
            if (this.doodleView.getPen().isElementEraser()) {
                onElementEraserScrollEnd(this.doodleView);
                this.doodleView.setDrawing(false);
                return;
            }
            if (this.doodleView.getIsEditMode() || this.doodleView.getPen().isPenEditable()) {
                IDoodleSelectableItem iDoodleSelectableItem = this.selectedItem;
                if (iDoodleSelectableItem instanceof DoodleRotatableItemBase) {
                    Intrinsics.checkNotNull(iDoodleSelectableItem, "null cannot be cast to non-null type com.miui.doodle.base.DoodleRotatableItemBase");
                    ((DoodleRotatableItemBase) iDoodleSelectableItem).setRotating(false);
                    DoodleView doodleView = this.doodleView;
                    if (doodleView != null) {
                        IDoodleSelectableItem iDoodleSelectableItem2 = this.selectedItem;
                        Intrinsics.checkNotNull(iDoodleSelectableItem2);
                        doodleView.notifySelectedItemLayoutFinished(iDoodleSelectableItem2);
                    }
                }
                if (this.doodleView.getIsEditMode()) {
                    limitBound(true);
                }
            }
            Timer timer = this.timer;
            if (timer != null) {
                Intrinsics.checkNotNull(timer);
                timer.cancel();
                this.timer = null;
            }
            DoodleBrushPath doodleBrushPath = this.currDoodlePath;
            if (doodleBrushPath != null) {
                doodleBrushPath.endPath(this.doodleView.toX(this.touchX), this.doodleView.toY(this.touchY), this.touchZ, e.getEventTime() - e.getDownTime());
                if (doodleBrushPath.isEraserPath()) {
                    this.doodleView.notifyItemFinishedErasing(doodleBrushPath);
                } else {
                    LayerView currentLayerView = this.doodleView.getCurrentLayerView();
                    LayerBitmap layerBitmap = (currentLayerView == null || (layer = currentLayerView.getLayer()) == null || (layerBitmapList = layer.getLayerBitmapList()) == null) ? null : (LayerBitmap) CollectionsKt.last((List) layerBitmapList);
                    if ((doodleBrushPath instanceof DoodleShape) && this.replacedItem != null) {
                        ((DoodleShape) doodleBrushPath).endShape();
                        DoodleView doodleView2 = this.doodleView;
                        IDoodleItem iDoodleItem = this.replacedItem;
                        Intrinsics.checkNotNull(iDoodleItem);
                        Intrinsics.checkNotNull(layerBitmap);
                        doodleView2.notifyItemFinishedReplacing(iDoodleItem, doodleBrushPath, layerBitmap);
                        this.replacedItem = null;
                    } else {
                        this.doodleView.notifyItemFinishedDrawing(doodleBrushPath, layerBitmap != null ? layerBitmap.getCacheBitmapBeforeChanged() : null);
                    }
                }
                this.currDoodlePath = null;
            }
            IDoodle.refresh$default(this.doodleView, null, 1, null);
            this.doodleView.setDrawing(false);
        }
    }

    /* JADX WARN: Code duplicated, block: B:51:0x0195  */
    /* JADX WARN: Code duplicated, block: B:53:0x0199  */
    /* JADX WARN: Code duplicated, block: B:74:0x02a6  */
    /* JADX WARN: Code duplicated, block: B:76:0x02aa  */
    /* JADX WARN: Code duplicated, block: B:81:0x02e9  */
    /* JADX WARN: Code duplicated, block: B:87:0x031a  */
    /* JADX WARN: Code duplicated, block: B:90:0x033a  */
    /* JADX WARN: Code duplicated, block: B:91:0x033d  */
    /* JADX WARN: Code duplicated, block: B:93:0x0343  */
    /* JADX WARN: Code duplicated, block: B:96:0x034d  */
    /* JADX WARN: Code duplicated, block: B:97:0x0350  */
    /* JADX WARN: Code duplicated, block: B:99:0x0356  */
    @Override // com.miui.doodle.base.TouchGestureDetector.OnTouchGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float x;
        float y;
        LayerView currentLayerView;
        Integer numValueOf;
        float fWidth;
        float f;
        LayerView currentLayerView2;
        Integer numValueOf2;
        float fHeight;
        float f2;
        float f3;
        float f4;
        Layer layer;
        Layer layer2;
        Timer timer;
        Intrinsics.checkNotNullParameter(e2, "e2");
        if (!this.doodleView.isEnabled()) {
            return false;
        }
        this.lastTouchX = this.touchX;
        this.lastTouchY = this.touchY;
        this.lastTouchZ = this.touchZ;
        this.touchX = e2.getX();
        this.touchY = e2.getY();
        this.touchZ = e2.getPressure();
        if (this.needPrintDebugPoint) {
            DebugPoint debugPoint = new DebugPoint();
            debugPoint.setX(this.lastTouchX);
            debugPoint.setY(this.lastTouchY);
            List<DebugPoint> list = this.debugPoints;
            if (list != null) {
                list.add(debugPoint);
            }
        }
        if (this.doodleView.getPen().isElementEraser()) {
            onElementEraserScroll(this.doodleView, this.doodleView.toX(this.touchX), this.doodleView.toY(this.touchY));
            return true;
        }
        DoodleBrushPath doodleBrushPath = this.currDoodlePath;
        if (doodleBrushPath instanceof DoodleShape) {
            Intrinsics.checkNotNull(doodleBrushPath, "null cannot be cast to non-null type com.miui.doodle.feature.draw.DoodleShape");
            onShapeChangedOnScroll((DoodleShape) doodleBrushPath, this.touchX, this.touchY);
            return true;
        }
        if (this.doodleView.getIsEditMode() || this.doodleView.getPen().isPenEditable()) {
            IDoodleSelectableItem iDoodleSelectableItem = this.selectedItem;
            if (iDoodleSelectableItem != null) {
                if (iDoodleSelectableItem instanceof DoodleRotatableItemBase) {
                    DoodleRotatableItemBase doodleRotatableItemBase = (DoodleRotatableItemBase) iDoodleSelectableItem;
                    if (doodleRotatableItemBase.getIsRotating()) {
                        Rect rect = new Rect();
                        rect.union(doodleRotatableItemBase.getBoundsInCanvas());
                        float itemScale = doodleRotatableItemBase.getPivotPoint().x * doodleRotatableItemBase.getItemScale();
                        float itemScale2 = doodleRotatableItemBase.getPivotPoint().y * doodleRotatableItemBase.getItemScale();
                        float itemRotate = doodleRotatableItemBase.getItemRotate();
                        doodleRotatableItemBase.setItemRotate(this.rotateDiff + DrawUtil.INSTANCE.computeAngle(itemScale, itemScale2, this.doodleView.toX(this.touchX), this.doodleView.toY(this.touchY)));
                        if (Math.abs(doodleRotatableItemBase.getItemRotate() % 360.0f) <= 5.0f) {
                            if (itemRotate != 0.0f) {
                                HapticFeedbackTool.INSTANCE.performHapticFeedback(this.doodleView);
                            }
                            doodleRotatableItemBase.setItemRotate(0.0f);
                        }
                        double d = itemScale;
                        double dSqrt = Math.sqrt(Math.pow(((double) this.doodleView.toX(this.lastTouchX)) - d, 2.0d) + Math.pow(((double) this.doodleView.toY(this.lastTouchY)) - d, 2.0d));
                        float fSqrt = (float) (Math.sqrt(Math.pow(((double) this.doodleView.toX(this.touchX)) - d, 2.0d) + Math.pow(((double) this.doodleView.toY(this.touchY)) - d, 2.0d)) - dSqrt);
                        float fAbs = (float) (((double) 1) + (((double) fSqrt) / dSqrt));
                        if (Math.min(this.doodleView.getAllTranX(), this.doodleView.getAllTranY()) < -6000.0f) {
                            fAbs += (Math.abs(fSqrt) * 0.005f) / fSqrt;
                        }
                        doodleRotatableItemBase.setItemScale(doodleRotatableItemBase.getItemScale() * fAbs);
                        rect.union(doodleRotatableItemBase.getBoundsInCanvas());
                        onUpdateTextOrBitmap((DoodleSelectableItemBase) iDoodleSelectableItem, rect);
                    } else if (iDoodleSelectableItem instanceof DoodleSelectableItemBase) {
                        DoodleSelectableItemBase doodleSelectableItemBase = (DoodleSelectableItemBase) iDoodleSelectableItem;
                        Rect boundsInCanvas = doodleSelectableItemBase.getBoundsInCanvas();
                        x = this.doodleView.toX(this.touchX) - this.doodleView.toX(this.touchDownX);
                        y = this.doodleView.toY(this.touchY) - this.doodleView.toY(this.touchDownY);
                        currentLayerView = this.doodleView.getCurrentLayerView();
                        if (currentLayerView != null || (layer2 = currentLayerView.getLayer()) == null) {
                            numValueOf = null;
                        } else {
                            numValueOf = Integer.valueOf(layer2.getWidth());
                        }
                        Intrinsics.checkNotNull(numValueOf);
                        fWidth = (((-numValueOf.intValue()) / 2.0f) - (boundsInCanvas.width() / 2.0f)) + this.moveItemSafeZone.x;
                        f = -fWidth;
                        currentLayerView2 = this.doodleView.getCurrentLayerView();
                        if (currentLayerView2 != null || (layer = currentLayerView2.getLayer()) == null) {
                            numValueOf2 = null;
                        } else {
                            numValueOf2 = Integer.valueOf(layer.getHeight());
                        }
                        Intrinsics.checkNotNull(numValueOf2);
                        fHeight = (((-numValueOf2.intValue()) / 2.0f) - (boundsInCanvas.height() / 2.0f)) + this.moveItemSafeZone.y;
                        f2 = -fHeight;
                        f3 = this.hasMovedXOnScrollBegin;
                        if (f3 + x < fWidth) {
                            x = fWidth - f3;
                        } else if (f3 + x > f) {
                            x = f - f3;
                        }
                        f4 = this.hasMovedYOnScrollBegin;
                        if (f4 + y < fHeight) {
                            y = fHeight - f4;
                        } else if (f4 + y > f2) {
                            y = f2 - f4;
                        }
                        Rect rect2 = new Rect();
                        rect2.union(doodleSelectableItemBase.getBoundsInCanvas());
                        doodleSelectableItemBase.getLocation().x = this.startX + x;
                        doodleSelectableItemBase.getLocation().y = this.startY + y;
                        rect2.union(doodleSelectableItemBase.getBoundsInCanvas());
                        onUpdateTextOrBitmap(doodleSelectableItemBase, rect2);
                    }
                } else if (iDoodleSelectableItem instanceof DoodleSelectableItemBase) {
                    DoodleSelectableItemBase doodleSelectableItemBase2 = (DoodleSelectableItemBase) iDoodleSelectableItem;
                    Rect boundsInCanvas2 = doodleSelectableItemBase2.getBoundsInCanvas();
                    x = this.doodleView.toX(this.touchX) - this.doodleView.toX(this.touchDownX);
                    y = this.doodleView.toY(this.touchY) - this.doodleView.toY(this.touchDownY);
                    currentLayerView = this.doodleView.getCurrentLayerView();
                    if (currentLayerView != null) {
                        numValueOf = null;
                    } else {
                        numValueOf = null;
                    }
                    Intrinsics.checkNotNull(numValueOf);
                    fWidth = (((-numValueOf.intValue()) / 2.0f) - (boundsInCanvas2.width() / 2.0f)) + this.moveItemSafeZone.x;
                    f = -fWidth;
                    currentLayerView2 = this.doodleView.getCurrentLayerView();
                    if (currentLayerView2 != null) {
                        numValueOf2 = null;
                    } else {
                        numValueOf2 = null;
                    }
                    Intrinsics.checkNotNull(numValueOf2);
                    fHeight = (((-numValueOf2.intValue()) / 2.0f) - (boundsInCanvas2.height() / 2.0f)) + this.moveItemSafeZone.y;
                    f2 = -fHeight;
                    f3 = this.hasMovedXOnScrollBegin;
                    if (f3 + x < fWidth) {
                        x = fWidth - f3;
                    } else if (f3 + x > f) {
                        x = f - f3;
                    }
                    f4 = this.hasMovedYOnScrollBegin;
                    if (f4 + y < fHeight) {
                        y = fHeight - f4;
                    } else if (f4 + y > f2) {
                        y = f2 - f4;
                    }
                    Rect rect3 = new Rect();
                    rect3.union(doodleSelectableItemBase2.getBoundsInCanvas());
                    doodleSelectableItemBase2.getLocation().x = this.startX + x;
                    doodleSelectableItemBase2.getLocation().y = this.startY + y;
                    rect3.union(doodleSelectableItemBase2.getBoundsInCanvas());
                    onUpdateTextOrBitmap(doodleSelectableItemBase2, rect3);
                }
            }
            if (this.selectedItem == null && this.doodleView.getIsEditMode()) {
                this.doodleView.setDoodleTranslation((this.startX + this.touchX) - this.touchDownX, (this.startY + this.touchY) - this.touchDownY);
            }
        } else {
            final DoodleBrushPath doodleBrushPath2 = this.currDoodlePath;
            if (doodleBrushPath2 != null) {
                ArrayList arrayList = new ArrayList();
                int historySize = e2.getHistorySize();
                int i = 0;
                while (true) {
                    float historicalPressure = -1.0f;
                    if (i >= historySize) {
                        break;
                    }
                    this.lastTouchX = this.touchX;
                    this.lastTouchY = this.touchY;
                    this.lastTouchZ = this.touchZ;
                    this.touchX = e2.getHistoricalX(i);
                    this.touchY = e2.getHistoricalY(i);
                    if (useTouchZ(e2.getToolType(0))) {
                        historicalPressure = e2.getHistoricalPressure(i);
                    }
                    this.touchZ = historicalPressure;
                    arrayList.addAll(doodleBrushPath2.updatePath(this.doodleView.toX(this.touchX), this.doodleView.toY(this.touchY), this.touchZ, e2.getHistoricalEventTime(i) - e2.getDownTime(), false));
                    i++;
                }
                this.lastTouchX = this.touchX;
                this.lastTouchY = this.touchY;
                this.lastTouchZ = this.touchZ;
                this.touchX = e2.getX();
                this.touchY = e2.getY();
                if (!useTouchZ(e2.getToolType(0))) {
                    this.touchZ = -1.0f;
                } else {
                    this.touchZ = e2.getPressure();
                }
                final float x2 = this.doodleView.toX(this.touchX);
                final float y2 = this.doodleView.toY(this.touchY);
                arrayList.addAll(doodleBrushPath2.updatePath(x2, y2, this.touchZ, e2.getEventTime() - e2.getDownTime(), false));
                float fAbs2 = Math.abs(this.touchX - this.lastTouchX);
                float fAbs3 = Math.abs(this.touchY - this.lastTouchY);
                if (!(doodleBrushPath2 instanceof DoodleShape) && !doodleBrushPath2.isEraserPath()) {
                    float f5 = this.minTouchGap;
                    if (fAbs2 < f5 && fAbs3 < f5) {
                        Timer timer2 = this.timer;
                        if (timer2 != null) {
                            timer2.cancel();
                        }
                        this.timer = new Timer();
                        TimerTask timerTask = new TimerTask() { // from class: com.miui.doodle.feature.draw.DoodleOnTouchGestureListener$onScroll$2$task$1
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public void run() {
                                try {
                                    Logger.INSTANCE.d(DoodleOnTouchGestureListener.TAG, "TimerTask,onShapeRecognized");
                                    this.this$0.onShapeRecognized(doodleBrushPath2, x2, y2);
                                } catch (Exception unused) {
                                }
                            }
                        };
                        Timer timer3 = this.timer;
                        if (timer3 != null) {
                            timer3.schedule(timerTask, 200L);
                        }
                    } else {
                        timer = this.timer;
                        if (timer != null) {
                            timer.cancel();
                        }
                    }
                } else {
                    timer = this.timer;
                    if (timer != null) {
                        timer.cancel();
                    }
                }
                if (doodleBrushPath2.isEraserPath()) {
                    this.doodleView.eraserLayerBitmap(arrayList);
                }
            }
        }
        IDoodle.refresh$default(this.doodleView, null, 1, null);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onShapeRecognized(DoodleBrushPath path, float x, float y) throws FileNotFoundException {
        LayerView currentLayerView;
        List<IDoodleItem> itemStackOnViewCanvas;
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        boolean z;
        if (!this.doodleView.isEnabled() || (path instanceof DoodleShape) || (currentLayerView = this.doodleView.getCurrentLayerView()) == null || (itemStackOnViewCanvas = currentLayerView.getItemStackOnViewCanvas()) == null || !itemStackOnViewCanvas.contains(path)) {
            return;
        }
        if (this.needPrintDebugPoint) {
            DrawUtil drawUtil = DrawUtil.INSTANCE;
            Context context = this.doodleView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            drawUtil.printDebugPointsToFile(context, this.debugPoints, this.debugDataIndex);
            List<DebugPoint> list = this.debugPoints;
            if (list != null) {
                list.clear();
            }
        }
        List<Dot> originDots = path.getOriginDots();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(originDots, 10));
        for (Dot dot : originDots) {
            arrayList.add(new Point(dot.getX(), dot.getY()));
        }
        Graph graphCurveTypeRecognition = GraphRecognition.curveTypeRecognition(arrayList);
        if (graphCurveTypeRecognition != null) {
            ArrayList<Point> fittedPoints = graphCurveTypeRecognition.getFittedPoints();
            Intrinsics.checkNotNullExpressionValue(fittedPoints, "getFittedPoints(...)");
            List<Point> list2 = CollectionsKt.toList(fittedPoints);
            List list3 = list2;
            if (list3 == null || list3.isEmpty()) {
                return;
            }
            for (Point point : list2) {
                if (Float.isNaN(point.getX()) || Float.isNaN(point.getY())) {
                    return;
                }
            }
            DoodleShape doodleShape = new DoodleShape(path.getDoodle(), path.getPen().copy(), null, path.getItemRotate(), path.getItemScale(), new PointF(path.getLocation().x, path.getLocation().y), new PointF(path.getLocation().x, path.getLocation().y), path.getAlpha(), 4, null);
            List<Point> list4 = list2;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
            for (Point point2 : list4) {
                arrayList2.add(new Dot(point2.getX(), point2.getY(), 0.0f, 0L, 0.0f, null, 0.0f, 0, 0, 0.0f, 1020, null));
            }
            List mutableList = CollectionsKt.toMutableList((Collection) arrayList2);
            graphCurveTypeRecognition.getParams().getOrDefault("rotationAngle", Float.valueOf(0.0f));
            GraphType type = graphCurveTypeRecognition.getType();
            int i = type == null ? -1 : WhenMappings.$EnumSwitchMapping$0[type.ordinal()];
            int i2 = 0;
            if (i == 1) {
                doodleShape.getDoodle().getMarkDoodleOperation().invoke(0);
                Float orDefault = graphCurveTypeRecognition.getParams().getOrDefault("centerX", Float.valueOf(0.0f));
                Float orDefault2 = graphCurveTypeRecognition.getParams().getOrDefault("centerY", Float.valueOf(0.0f));
                Float orDefault3 = graphCurveTypeRecognition.getParams().getOrDefault("radius", Float.valueOf(0.0f));
                Intrinsics.checkNotNull(orDefault);
                float fFloatValue = orDefault.floatValue();
                Intrinsics.checkNotNull(orDefault2);
                float fFloatValue2 = orDefault2.floatValue();
                Intrinsics.checkNotNull(orDefault3);
                doodleShape.setCircleShape(fFloatValue, fFloatValue2, orDefault3.floatValue());
            } else if (i == 2) {
                doodleShape.getDoodle().getMarkDoodleOperation().invoke(1);
                Float orDefault4 = graphCurveTypeRecognition.getParams().getOrDefault("centerX", Float.valueOf(0.0f));
                Float orDefault5 = graphCurveTypeRecognition.getParams().getOrDefault("centerY", Float.valueOf(0.0f));
                Float orDefault6 = graphCurveTypeRecognition.getParams().getOrDefault("majorAxis", Float.valueOf(0.0f));
                Float orDefault7 = graphCurveTypeRecognition.getParams().getOrDefault("minorAxis", Float.valueOf(0.0f));
                float fFloatValue3 = ((graphCurveTypeRecognition.getParams().getOrDefault("rotationAngle", Float.valueOf(0.0f)).floatValue() / 3.1415925f) * 180.0f) % 360.0f;
                Intrinsics.checkNotNull(orDefault4);
                float fFloatValue4 = orDefault4.floatValue();
                Intrinsics.checkNotNull(orDefault5);
                float fFloatValue5 = orDefault5.floatValue();
                Intrinsics.checkNotNull(orDefault6);
                float fFloatValue6 = orDefault6.floatValue();
                Intrinsics.checkNotNull(orDefault7);
                doodleShape.setEllipseShape(fFloatValue4, fFloatValue5, fFloatValue6, orDefault7.floatValue(), fFloatValue3);
            } else if (i == 3) {
                doodleShape.getDoodle().getMarkDoodleOperation().invoke(2);
                Dot dot2 = (Dot) mutableList.get(0);
                List<IDoodleItem> straightLineItems = this.doodleView.getStraightLineItems();
                Iterator<IDoodleItem> it = straightLineItems.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    IDoodleItem next = it.next();
                    Intrinsics.checkNotNull(next, "null cannot be cast to non-null type com.miui.doodle.feature.draw.DoodleShape");
                    DoodleShape doodleShape2 = (DoodleShape) next;
                    Dot dot3 = doodleShape2.getFittedDots().get(i2);
                    Dot dot4 = doodleShape2.getFittedDots().get(1);
                    if (dot3.distanceTo(dot2) <= this.minDistance) {
                        HapticFeedbackTool.INSTANCE.performHapticFeedback(this.doodleView);
                        dot2 = dot3;
                    } else if (dot4.distanceTo(dot2) <= this.minDistance) {
                        HapticFeedbackTool.INSTANCE.performHapticFeedback(this.doodleView);
                        dot2 = dot4;
                    } else {
                        i2 = 0;
                    }
                    z = true;
                    break;
                }
                if (!z) {
                    for (IDoodleItem iDoodleItem : straightLineItems) {
                        Intrinsics.checkNotNull(iDoodleItem, "null cannot be cast to non-null type com.miui.doodle.feature.draw.DoodleShape");
                        DoodleShape doodleShape3 = (DoodleShape) iDoodleItem;
                        Dot dotCalcCrossedDot = Dot.INSTANCE.calcCrossedDot(doodleShape3.getFittedDots().get(0), doodleShape3.getFittedDots().get(1), dot2, (Dot) mutableList.get(1));
                        if (dotCalcCrossedDot != null && dotCalcCrossedDot.distanceTo(dot2) <= this.minDistance) {
                            if (dot2.getX() != dotCalcCrossedDot.getX() || dot2.getY() != dotCalcCrossedDot.getY()) {
                                HapticFeedbackTool.INSTANCE.performHapticFeedback(this.doodleView);
                            }
                            dot2 = dotCalcCrossedDot;
                            break;
                        }
                    }
                }
                mutableList.set(0, dot2);
                GraphType type2 = graphCurveTypeRecognition.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                DoodleShape.setShape$default(doodleShape, mutableList, type2, 0.0f, 0.0f, 12, null);
            } else {
                Float orDefault8 = graphCurveTypeRecognition.getParams().getOrDefault("rotationAngle", Float.valueOf(0.0f));
                if (graphCurveTypeRecognition.getType() == GraphType.PENTAGRAM || graphCurveTypeRecognition.getType() == GraphType.PENTAGON) {
                    orDefault8 = Float.valueOf(orDefault8.floatValue() + 36.0f);
                }
                GraphType type3 = graphCurveTypeRecognition.getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                DoodleShape.setShape$default(doodleShape, mutableList, type3, 0.0f, (orDefault8.floatValue() + 360.0f) % 360.0f, 4, null);
            }
            if (doodleShape.getCenterX() != null && doodleShape.getCenterY() != null) {
                UIUtils uIUtils = UIUtils.INSTANCE;
                Float centerX = doodleShape.getCenterX();
                Intrinsics.checkNotNull(centerX);
                float fFloatValue7 = centerX.floatValue();
                Float centerY = doodleShape.getCenterY();
                Intrinsics.checkNotNull(centerY);
                doodleShape.setLastTouchDistance(uIUtils.dotsDistance(x, y, fFloatValue7, centerY.floatValue()));
                float rotationAngle = doodleShape.getRotationAngle();
                DrawUtil drawUtil2 = DrawUtil.INSTANCE;
                Float centerX2 = doodleShape.getCenterX();
                Intrinsics.checkNotNull(centerX2);
                float fFloatValue8 = centerX2.floatValue();
                Float centerY2 = doodleShape.getCenterY();
                Intrinsics.checkNotNull(centerY2);
                this.rotateDiff = rotationAngle - drawUtil2.computeAngle(fFloatValue8, centerY2.floatValue(), x, y);
            }
            IDoodle.refresh$default(this.doodleView, null, 1, null);
            path.resetBounds();
            LayerView currentLayerView2 = this.doodleView.getCurrentLayerView();
            LayerBitmap layerBitmap = (currentLayerView2 == null || (layer = currentLayerView2.getLayer()) == null || (layerBitmapList = layer.getLayerBitmapList()) == null) ? null : (LayerBitmap) CollectionsKt.last((List) layerBitmapList);
            Bitmap cacheBitmapBeforeChanged = layerBitmap != null ? layerBitmap.getCacheBitmapBeforeChanged() : null;
            if (layerBitmap != null) {
                layerBitmap.cacheImageBeforeChanged();
            }
            Intrinsics.checkNotNull(cacheBitmapBeforeChanged);
            if (layerBitmap != null) {
                Bitmap.Config config = cacheBitmapBeforeChanged.getConfig();
                Intrinsics.checkNotNull(config);
                Bitmap bitmapCopy = cacheBitmapBeforeChanged.copy(config, true);
                Intrinsics.checkNotNullExpressionValue(bitmapCopy, "copy(...)");
                layerBitmap.updateBitmap(bitmapCopy);
            }
            DoodleBrushPath doodleBrushPath = path;
            this.doodleView.notifyItemTempRemove(doodleBrushPath);
            this.replacedItem = doodleBrushPath;
            this.currDoodlePath = doodleShape;
            this.doodleView.markItemToOptimizeDrawing(doodleShape);
        }
    }

    private final void onShapeChangedOnScroll(DoodleShape shape, float x, float y) {
        if (this.doodleView.isEnabled()) {
            shape.changeByTouch(this.doodleView, this.rotateDiff, this.minDistance, x, y, new Runnable() { // from class: com.miui.doodle.feature.draw.DoodleOnTouchGestureListener$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    DoodleOnTouchGestureListener.onShapeChangedOnScroll$lambda$12(this.f$0);
                }
            });
            IDoodle.refresh$default(this.doodleView, null, 1, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onShapeChangedOnScroll$lambda$12(DoodleOnTouchGestureListener doodleOnTouchGestureListener) {
        HapticFeedbackTool.INSTANCE.performHapticFeedback(doodleOnTouchGestureListener.doodleView);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0064 A[Catch: Exception -> 0x0151, TryCatch #0 {Exception -> 0x0151, blocks: (B:3:0x0006, B:6:0x0010, B:8:0x002d, B:10:0x0031, B:12:0x0035, B:14:0x0052, B:16:0x0056, B:54:0x014a, B:17:0x0064, B:19:0x007d, B:21:0x0081, B:23:0x0085, B:24:0x0094, B:26:0x00ae, B:28:0x00b4, B:30:0x00ba, B:31:0x00c0, B:33:0x00c6, B:37:0x00d9, B:39:0x00dd, B:40:0x00e1, B:42:0x00e5, B:44:0x00ec, B:45:0x00f7, B:47:0x0103, B:49:0x0107, B:50:0x0121, B:52:0x012d, B:53:0x013c), top: B:57:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:19:0x007d A[Catch: Exception -> 0x0151, TryCatch #0 {Exception -> 0x0151, blocks: (B:3:0x0006, B:6:0x0010, B:8:0x002d, B:10:0x0031, B:12:0x0035, B:14:0x0052, B:16:0x0056, B:54:0x014a, B:17:0x0064, B:19:0x007d, B:21:0x0081, B:23:0x0085, B:24:0x0094, B:26:0x00ae, B:28:0x00b4, B:30:0x00ba, B:31:0x00c0, B:33:0x00c6, B:37:0x00d9, B:39:0x00dd, B:40:0x00e1, B:42:0x00e5, B:44:0x00ec, B:45:0x00f7, B:47:0x0103, B:49:0x0107, B:50:0x0121, B:52:0x012d, B:53:0x013c), top: B:57:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x00c6 A[Catch: Exception -> 0x0151, TryCatch #0 {Exception -> 0x0151, blocks: (B:3:0x0006, B:6:0x0010, B:8:0x002d, B:10:0x0031, B:12:0x0035, B:14:0x0052, B:16:0x0056, B:54:0x014a, B:17:0x0064, B:19:0x007d, B:21:0x0081, B:23:0x0085, B:24:0x0094, B:26:0x00ae, B:28:0x00b4, B:30:0x00ba, B:31:0x00c0, B:33:0x00c6, B:37:0x00d9, B:39:0x00dd, B:40:0x00e1, B:42:0x00e5, B:44:0x00ec, B:45:0x00f7, B:47:0x0103, B:49:0x0107, B:50:0x0121, B:52:0x012d, B:53:0x013c), top: B:57:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x00dd A[Catch: Exception -> 0x0151, TryCatch #0 {Exception -> 0x0151, blocks: (B:3:0x0006, B:6:0x0010, B:8:0x002d, B:10:0x0031, B:12:0x0035, B:14:0x0052, B:16:0x0056, B:54:0x014a, B:17:0x0064, B:19:0x007d, B:21:0x0081, B:23:0x0085, B:24:0x0094, B:26:0x00ae, B:28:0x00b4, B:30:0x00ba, B:31:0x00c0, B:33:0x00c6, B:37:0x00d9, B:39:0x00dd, B:40:0x00e1, B:42:0x00e5, B:44:0x00ec, B:45:0x00f7, B:47:0x0103, B:49:0x0107, B:50:0x0121, B:52:0x012d, B:53:0x013c), top: B:57:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:40:0x00e1 A[Catch: Exception -> 0x0151, TryCatch #0 {Exception -> 0x0151, blocks: (B:3:0x0006, B:6:0x0010, B:8:0x002d, B:10:0x0031, B:12:0x0035, B:14:0x0052, B:16:0x0056, B:54:0x014a, B:17:0x0064, B:19:0x007d, B:21:0x0081, B:23:0x0085, B:24:0x0094, B:26:0x00ae, B:28:0x00b4, B:30:0x00ba, B:31:0x00c0, B:33:0x00c6, B:37:0x00d9, B:39:0x00dd, B:40:0x00e1, B:42:0x00e5, B:44:0x00ec, B:45:0x00f7, B:47:0x0103, B:49:0x0107, B:50:0x0121, B:52:0x012d, B:53:0x013c), top: B:57:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x00e5 A[Catch: Exception -> 0x0151, TryCatch #0 {Exception -> 0x0151, blocks: (B:3:0x0006, B:6:0x0010, B:8:0x002d, B:10:0x0031, B:12:0x0035, B:14:0x0052, B:16:0x0056, B:54:0x014a, B:17:0x0064, B:19:0x007d, B:21:0x0081, B:23:0x0085, B:24:0x0094, B:26:0x00ae, B:28:0x00b4, B:30:0x00ba, B:31:0x00c0, B:33:0x00c6, B:37:0x00d9, B:39:0x00dd, B:40:0x00e1, B:42:0x00e5, B:44:0x00ec, B:45:0x00f7, B:47:0x0103, B:49:0x0107, B:50:0x0121, B:52:0x012d, B:53:0x013c), top: B:57:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x00ec A[Catch: Exception -> 0x0151, TryCatch #0 {Exception -> 0x0151, blocks: (B:3:0x0006, B:6:0x0010, B:8:0x002d, B:10:0x0031, B:12:0x0035, B:14:0x0052, B:16:0x0056, B:54:0x014a, B:17:0x0064, B:19:0x007d, B:21:0x0081, B:23:0x0085, B:24:0x0094, B:26:0x00ae, B:28:0x00b4, B:30:0x00ba, B:31:0x00c0, B:33:0x00c6, B:37:0x00d9, B:39:0x00dd, B:40:0x00e1, B:42:0x00e5, B:44:0x00ec, B:45:0x00f7, B:47:0x0103, B:49:0x0107, B:50:0x0121, B:52:0x012d, B:53:0x013c), top: B:57:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:59:0x00d8 A[SYNTHETIC] */
    @Override // com.miui.doodle.base.TouchGestureDetector.OnTouchGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent e) {
        IDoodleSelectableItem selectableItemInThisPoint$default;
        IDoodleSelectableItem iDoodleSelectableItem;
        ISelectionListener iSelectionListener;
        LayerView currentLayerView;
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        Iterator<T> it;
        Object next;
        LayerBitmap layerBitmap;
        Intrinsics.checkNotNullParameter(e, "e");
        try {
            if (!this.doodleView.isEnabled()) {
                return false;
            }
            this.lastTouchX = this.touchX;
            this.lastTouchY = this.touchY;
            this.touchX = e.getX();
            this.touchY = e.getY();
            if (this.doodleView.getIsEditMode()) {
                IDoodleSelectableItem iDoodleSelectableItem2 = this.selectedItem;
                if (iDoodleSelectableItem2 != null && (iDoodleSelectableItem2 instanceof DoodleRotatableItemBase)) {
                    Intrinsics.checkNotNull(iDoodleSelectableItem2, "null cannot be cast to non-null type com.miui.doodle.base.DoodleRotatableItemBase");
                    if (((DoodleRotatableItemBase) iDoodleSelectableItem2).shouldClose(this.doodleView.toX(this.touchX), this.doodleView.toY(this.touchY))) {
                        ISelectionListener iSelectionListener2 = this.selectionListener;
                        if (iSelectionListener2 != null) {
                            DoodleView doodleView = this.doodleView;
                            IDoodleSelectableItem iDoodleSelectableItem3 = this.selectedItem;
                            Intrinsics.checkNotNull(iDoodleSelectableItem3);
                            iSelectionListener2.onDeleteItemByButton(doodleView, iDoodleSelectableItem3);
                        }
                    } else {
                        DoodleView doodleView2 = this.doodleView;
                        selectableItemInThisPoint$default = DoodleView.getSelectableItemInThisPoint$default(doodleView2, doodleView2.toX(this.touchX), this.doodleView.toY(this.touchY), false, 4, null);
                        if (selectableItemInThisPoint$default != null) {
                            if (selectableItemInThisPoint$default instanceof DoodleText) {
                                ISelectionListener iSelectionListener3 = this.selectionListener;
                                Intrinsics.checkNotNull(iSelectionListener3);
                                iSelectionListener3.onTextEdit(this.doodleView, (DoodleText) selectableItemInThisPoint$default);
                            }
                            setSelectedItem(selectableItemInThisPoint$default);
                            PointF location = selectableItemInThisPoint$default.getLocation();
                            this.startX = location.x;
                            this.startY = location.y;
                            selectableItemInThisPoint$default.cacheTransitInfoBeforeChanged();
                            currentLayerView = this.doodleView.getCurrentLayerView();
                            if (currentLayerView != null) {
                                it = layerBitmapList.iterator();
                                do {
                                    if (it.hasNext()) {
                                        next = null;
                                        break;
                                    }
                                    next = it.next();
                                } while (!((LayerBitmap) next).getItems().contains(selectableItemInThisPoint$default));
                                layerBitmap = (LayerBitmap) next;
                                if (layerBitmap != null) {
                                    layerBitmap.cacheImageBeforeChanged();
                                }
                            }
                        } else {
                            iDoodleSelectableItem = this.selectedItem;
                            if (iDoodleSelectableItem != null) {
                                setSelectedItem(null);
                                iSelectionListener = this.selectionListener;
                                if (iSelectionListener != null) {
                                    Intrinsics.checkNotNull(iSelectionListener);
                                    iSelectionListener.onSelectedItem(this.doodleView, iDoodleSelectableItem, false);
                                }
                            }
                        }
                    }
                } else {
                    DoodleView doodleView3 = this.doodleView;
                    selectableItemInThisPoint$default = DoodleView.getSelectableItemInThisPoint$default(doodleView3, doodleView3.toX(this.touchX), this.doodleView.toY(this.touchY), false, 4, null);
                    if (selectableItemInThisPoint$default != null) {
                        if ((selectableItemInThisPoint$default instanceof DoodleText) && selectableItemInThisPoint$default == this.selectedItem) {
                            ISelectionListener iSelectionListener4 = this.selectionListener;
                            Intrinsics.checkNotNull(iSelectionListener4);
                            iSelectionListener4.onTextEdit(this.doodleView, (DoodleText) selectableItemInThisPoint$default);
                        }
                        setSelectedItem(selectableItemInThisPoint$default);
                        PointF location2 = selectableItemInThisPoint$default.getLocation();
                        this.startX = location2.x;
                        this.startY = location2.y;
                        selectableItemInThisPoint$default.cacheTransitInfoBeforeChanged();
                        currentLayerView = this.doodleView.getCurrentLayerView();
                        if (currentLayerView != null && (layer = currentLayerView.getLayer()) != null && (layerBitmapList = layer.getLayerBitmapList()) != null) {
                            it = layerBitmapList.iterator();
                            do {
                                if (it.hasNext()) {
                                    next = null;
                                    break;
                                }
                                next = it.next();
                            } while (!((LayerBitmap) next).getItems().contains(selectableItemInThisPoint$default));
                            layerBitmap = (LayerBitmap) next;
                            if (layerBitmap != null) {
                                layerBitmap.cacheImageBeforeChanged();
                            }
                        }
                    } else {
                        iDoodleSelectableItem = this.selectedItem;
                        if (iDoodleSelectableItem != null) {
                            setSelectedItem(null);
                            iSelectionListener = this.selectionListener;
                            if (iSelectionListener != null) {
                                Intrinsics.checkNotNull(iSelectionListener);
                                iSelectionListener.onSelectedItem(this.doodleView, iDoodleSelectableItem, false);
                            }
                        }
                    }
                }
            } else if (this.doodleView.getPen().isPenEditable()) {
                ISelectionListener iSelectionListener5 = this.selectionListener;
                if (iSelectionListener5 != null) {
                    Intrinsics.checkNotNull(iSelectionListener5);
                    DoodleView doodleView4 = this.doodleView;
                    iSelectionListener5.onCreateSelectableItem(doodleView4, doodleView4.toX(this.touchX), this.doodleView.toY(this.touchY));
                }
            } else if (this.doodleView.getPen().isElementEraser()) {
                onElementEraserScroll(this.doodleView, this.touchX, this.touchY);
                onElementEraserScrollEnd(this.doodleView);
            } else {
                onScrollBegin(e);
                e.offsetLocation(1.0f, 1.0f);
                onScroll(e, e, 1.0f, 1.0f);
                onScrollEnd(e);
            }
            IDoodle.refresh$default(this.doodleView, null, 1, null);
        } catch (Exception unused) {
        }
        return true;
    }

    private final void onElementEraserScroll(DoodleView doodle, float touchX, float touchY) {
        float finalSize = doodle.getPen().getFinalSize() / 2.0f;
        doodle.removeItemByElementEraser(new RectF(touchX - finalSize, touchY - finalSize, touchX + finalSize, finalSize + touchY), touchX, touchY);
    }

    private final void onElementEraserScrollEnd(DoodleView doodle) {
        doodle.notifyElementFinishRemove();
    }

    @Override // com.miui.doodle.base.TouchGestureDetector.OnTouchGestureListener, com.miui.doodle.base.ScaleGestureDetectorApi27.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetectorApi27 detector) {
        Intrinsics.checkNotNullParameter(detector, "detector");
        if (!this.doodleView.isEnabled()) {
            return false;
        }
        this.lastFocusX = null;
        this.lastFocusY = null;
        return true;
    }

    @Override // com.miui.doodle.base.TouchGestureDetector.OnTouchGestureListener, com.miui.doodle.base.ScaleGestureDetectorApi27.OnScaleGestureListener
    public boolean onScale(ScaleGestureDetectorApi27 detector) {
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        Object next;
        Intrinsics.checkNotNullParameter(detector, "detector");
        if (!this.doodleView.isEnabled()) {
            return false;
        }
        this.touchCentreX = detector.getFocusX();
        this.touchCentreY = detector.getFocusY();
        Float f = this.lastFocusX;
        if (f != null && this.lastFocusY != null) {
            float f2 = this.touchCentreX;
            Intrinsics.checkNotNull(f);
            float fFloatValue = f2 - f.floatValue();
            float f3 = this.touchCentreY;
            Float f4 = this.lastFocusY;
            Intrinsics.checkNotNull(f4);
            float fFloatValue2 = f3 - f4.floatValue();
            if (Math.abs(fFloatValue) > 1.0f || Math.abs(fFloatValue2) > 1.0f) {
                if (this.selectedItem == null || !getIsSupportScaleItem()) {
                    this.doodleView.setDoodleTranslation(this.doodleView.getDoodleTranslationX() + fFloatValue + this.pendingX, this.doodleView.getDoodleTranslationY() + fFloatValue2 + this.pendingY);
                }
                this.pendingY = 0.0f;
                this.pendingX = 0.0f;
            } else {
                this.pendingX += fFloatValue;
                this.pendingY += fFloatValue2;
            }
        }
        if (Math.abs(1 - detector.getScaleFactor()) > 0.005f) {
            if (this.selectedItem == null || !getIsSupportScaleItem()) {
                float doodleScale = this.doodleView.getDoodleScale() * detector.getScaleFactor() * this.pendingScale;
                DoodleView doodleView = this.doodleView;
                doodleView.setDoodleScale(doodleScale, doodleView.toX(this.touchCentreX), this.doodleView.toY(this.touchCentreY));
            } else {
                IDoodleSelectableItem iDoodleSelectableItem = this.selectedItem;
                if (iDoodleSelectableItem != null) {
                    this.doodleView.cancelAutoSave();
                    if (iDoodleSelectableItem instanceof DoodleSelectableItemBase) {
                        if (iDoodleSelectableItem instanceof DoodleRotatableItemBase) {
                            DoodleRotatableItemBase doodleRotatableItemBase = (DoodleRotatableItemBase) iDoodleSelectableItem;
                            if (!doodleRotatableItemBase.getIsScaling()) {
                                doodleRotatableItemBase.setScaling(true);
                                LayerView currentLayerView = this.doodleView.getCurrentLayerView();
                                if (currentLayerView != null && (layer = currentLayerView.getLayer()) != null && (layerBitmapList = layer.getLayerBitmapList()) != null) {
                                    Iterator<T> it = layerBitmapList.iterator();
                                    do {
                                        if (!it.hasNext()) {
                                            next = null;
                                            break;
                                        }
                                        next = it.next();
                                    } while (!((LayerBitmap) next).getItems().contains(iDoodleSelectableItem));
                                    LayerBitmap layerBitmap = (LayerBitmap) next;
                                    if (layerBitmap != null) {
                                        layerBitmap.cacheImageBeforeChanged();
                                    }
                                }
                                doodleRotatableItemBase.cacheTransitInfoBeforeChanged();
                            }
                        }
                        Rect rect = new Rect();
                        DoodleSelectableItemBase doodleSelectableItemBase = (DoodleSelectableItemBase) iDoodleSelectableItem;
                        rect.union(doodleSelectableItemBase.getBoundsInCanvas());
                        doodleSelectableItemBase.setItemScale(doodleSelectableItemBase.getItemScale() * detector.getScaleFactor() * this.pendingScale);
                        rect.union(doodleSelectableItemBase.getBoundsInCanvas());
                        onUpdateTextOrBitmap(doodleSelectableItemBase, rect);
                    } else {
                        iDoodleSelectableItem.setItemScale(iDoodleSelectableItem.getItemScale() * detector.getScaleFactor() * this.pendingScale);
                    }
                }
            }
            this.pendingScale = 1.0f;
        } else {
            this.pendingScale *= detector.getScaleFactor();
        }
        this.lastFocusX = Float.valueOf(this.touchCentreX);
        this.lastFocusY = Float.valueOf(this.touchCentreY);
        return true;
    }

    @Override // com.miui.doodle.base.TouchGestureDetector.OnTouchGestureListener, com.miui.doodle.base.ScaleGestureDetectorApi27.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetectorApi27 detector) {
        Intrinsics.checkNotNullParameter(detector, "detector");
        if (this.doodleView.isEnabled()) {
            center();
            IDoodleSelectableItem iDoodleSelectableItem = this.selectedItem;
            if (iDoodleSelectableItem == null || !(iDoodleSelectableItem instanceof DoodleRotatableItemBase)) {
                return;
            }
            DoodleRotatableItemBase doodleRotatableItemBase = (DoodleRotatableItemBase) iDoodleSelectableItem;
            if (doodleRotatableItemBase.getIsScaling()) {
                this.doodleView.notifySelectedItemLayoutFinished(iDoodleSelectableItem);
                doodleRotatableItemBase.setScaling(false);
            }
        }
    }

    public final void center() {
        if (this.doodleView.getDoodleScale() > 3.0f) {
            ValueAnimator valueAnimator = this.scaleAnimator;
            if (valueAnimator != null && valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator valueAnimator2 = new ValueAnimator();
            this.scaleAnimator = valueAnimator2;
            Intrinsics.checkNotNull(valueAnimator2);
            valueAnimator2.setDuration(100L);
            ValueAnimator valueAnimator3 = this.scaleAnimator;
            Intrinsics.checkNotNull(valueAnimator3);
            valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.doodle.feature.draw.DoodleOnTouchGestureListener$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator4) {
                    DoodleOnTouchGestureListener.center$lambda$18(this.f$0, valueAnimator4);
                }
            });
            this.scaleAnimTransX = this.doodleView.getDoodleTranslationX();
            this.scaleAnimTranY = this.doodleView.getDoodleTranslationY();
            ValueAnimator valueAnimator4 = this.scaleAnimator;
            Intrinsics.checkNotNull(valueAnimator4);
            valueAnimator4.setFloatValues(this.doodleView.getDoodleScale(), 3.0f);
            ValueAnimator valueAnimator5 = this.scaleAnimator;
            Intrinsics.checkNotNull(valueAnimator5);
            valueAnimator5.start();
            return;
        }
        final float f = 1.0f;
        if (this.doodleView.getDoodleScale() < 1.0f) {
            ValueAnimator valueAnimator6 = this.scaleAnimator;
            if (valueAnimator6 != null && valueAnimator6 != null) {
                valueAnimator6.cancel();
            }
            ValueAnimator valueAnimator7 = new ValueAnimator();
            this.scaleAnimator = valueAnimator7;
            Intrinsics.checkNotNull(valueAnimator7);
            valueAnimator7.setDuration(100L);
            ValueAnimator valueAnimator8 = this.scaleAnimator;
            Intrinsics.checkNotNull(valueAnimator8);
            valueAnimator8.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.doodle.feature.draw.DoodleOnTouchGestureListener$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator9) {
                    DoodleOnTouchGestureListener.center$lambda$19(this.f$0, valueAnimator9);
                }
            });
            if (this.doodleView.getDoodleScale() < 0.9f) {
                ValueAnimator valueAnimator9 = this.scaleAnimator;
                Intrinsics.checkNotNull(valueAnimator9);
                valueAnimator9.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.doodle.feature.draw.DoodleOnTouchGestureListener$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator10) {
                        DoodleOnTouchGestureListener.center$lambda$20(this.f$0, f, valueAnimator10);
                    }
                });
            }
            ValueAnimator valueAnimator10 = this.scaleAnimator;
            Intrinsics.checkNotNull(valueAnimator10);
            valueAnimator10.addListener(new Animator.AnimatorListener() { // from class: com.miui.doodle.feature.draw.DoodleOnTouchGestureListener$center$$inlined$addListener$default$1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    this.this$0.limitBound(true);
                }
            });
            this.scaleAnimTransX = this.doodleView.getDoodleTranslationX();
            this.scaleAnimTranY = this.doodleView.getDoodleTranslationY();
            ValueAnimator valueAnimator11 = this.scaleAnimator;
            Intrinsics.checkNotNull(valueAnimator11);
            valueAnimator11.setFloatValues(this.doodleView.getDoodleScale(), 1.0f);
            ValueAnimator valueAnimator12 = this.scaleAnimator;
            Intrinsics.checkNotNull(valueAnimator12);
            valueAnimator12.start();
            return;
        }
        limitBound(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void center$lambda$18(DoodleOnTouchGestureListener doodleOnTouchGestureListener, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        DoodleView doodleView = doodleOnTouchGestureListener.doodleView;
        doodleView.setDoodleScale(fFloatValue, doodleView.toX(doodleOnTouchGestureListener.touchCentreX), doodleOnTouchGestureListener.doodleView.toY(doodleOnTouchGestureListener.touchCentreY));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void center$lambda$19(DoodleOnTouchGestureListener doodleOnTouchGestureListener, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        DoodleView doodleView = doodleOnTouchGestureListener.doodleView;
        doodleView.setDoodleScale(fFloatValue, doodleView.toX(doodleOnTouchGestureListener.touchCentreX), doodleOnTouchGestureListener.doodleView.toY(doodleOnTouchGestureListener.touchCentreY));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void center$lambda$20(DoodleOnTouchGestureListener doodleOnTouchGestureListener, float f, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        float animatedFraction = f - animation.getAnimatedFraction();
        doodleOnTouchGestureListener.doodleView.setDoodleTranslation(doodleOnTouchGestureListener.scaleAnimTransX * animatedFraction, doodleOnTouchGestureListener.scaleAnimTranY * animatedFraction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:25:0x00a2 A[PHI: r8
  0x00a2: PHI (r8v14 float) = (r8v10 float), (r8v2 float) binds: [B:41:0x00e6, B:24:0x00a0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:26:0x00a4 A[PHI: r8
  0x00a4: PHI (r8v13 float) = (r8v10 float), (r8v2 float) binds: [B:41:0x00e6, B:24:0x00a0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:29:0x00ae A[PHI: r8
  0x00ae: PHI (r8v12 float) = (r8v10 float), (r8v2 float) binds: [B:44:0x00ef, B:28:0x00ac] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:30:0x00b0 A[PHI: r8
  0x00b0: PHI (r8v11 float) = (r8v10 float), (r8v2 float) binds: [B:44:0x00ef, B:28:0x00ac] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:66:0x015b A[PHI: r6
  0x015b: PHI (r6v14 float) = (r6v10 float), (r6v2 float) binds: [B:82:0x019f, B:65:0x0159] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:67:0x015d A[PHI: r6
  0x015d: PHI (r6v13 float) = (r6v10 float), (r6v2 float) binds: [B:82:0x019f, B:65:0x0159] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:70:0x0167 A[PHI: r6
  0x0167: PHI (r6v12 float) = (r6v10 float), (r6v2 float) binds: [B:85:0x01a8, B:69:0x0165] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:71:0x0169 A[PHI: r6
  0x0169: PHI (r6v11 float) = (r6v10 float), (r6v2 float) binds: [B:85:0x01a8, B:69:0x0165] A[DONT_GENERATE, DONT_INLINE]] */
    public final void limitBound(boolean anim) {
        if (this.doodleView.getDoodleRotation() % 90 != 0) {
            return;
        }
        float doodleTranslationX = this.doodleView.getDoodleTranslationX();
        float doodleTranslationY = this.doodleView.getDoodleTranslationY();
        RectF doodleBound = this.doodleView.getDoodleBound();
        float doodleTranslationX2 = this.doodleView.getDoodleTranslationX();
        float doodleTranslationY2 = this.doodleView.getDoodleTranslationY();
        int centerWidth = this.doodleView.getCenterWidth();
        float centerHeight = this.doodleView.getCenterHeight();
        if (doodleBound.height() <= this.doodleView.getHeight()) {
            if (this.doodleView.getDoodleRotation() == 0 || this.doodleView.getDoodleRotation() == 180) {
                doodleTranslationY2 = (centerHeight - (this.doodleView.getDoodleScale() * centerHeight)) / 2;
            } else {
                float f = centerWidth;
                doodleTranslationX2 = (f - (this.doodleView.getDoodleScale() * f)) / 2;
            }
        } else {
            float height = doodleBound.top;
            if (doodleBound.top <= 0.0f || doodleBound.bottom < this.doodleView.getHeight()) {
                if (doodleBound.bottom < this.doodleView.getHeight() && doodleBound.top <= 0.0f) {
                    height = this.doodleView.getHeight() - doodleBound.bottom;
                    if (this.doodleView.getDoodleRotation() == 0 || this.doodleView.getDoodleRotation() == 180) {
                        if (this.doodleView.getDoodleRotation() == 0) {
                            doodleTranslationY2 += height;
                        } else {
                            doodleTranslationY2 -= height;
                        }
                    } else if (this.doodleView.getDoodleRotation() == 90) {
                        doodleTranslationX2 += height;
                    } else {
                        doodleTranslationX2 -= height;
                    }
                }
            } else if (this.doodleView.getDoodleRotation() == 0 || this.doodleView.getDoodleRotation() == 180) {
                if (this.doodleView.getDoodleRotation() == 0) {
                    doodleTranslationY2 -= height;
                } else {
                    doodleTranslationY2 += height;
                }
            } else if (this.doodleView.getDoodleRotation() == 90) {
                doodleTranslationX2 -= height;
            } else {
                doodleTranslationX2 += height;
            }
        }
        if (doodleBound.width() <= this.doodleView.getWidth()) {
            if (this.doodleView.getDoodleRotation() == 0 || this.doodleView.getDoodleRotation() == 180) {
                float f2 = centerWidth;
                doodleTranslationX2 = (f2 - (this.doodleView.getDoodleScale() * f2)) / 2;
            } else {
                doodleTranslationY2 = (centerHeight - (this.doodleView.getDoodleScale() * centerHeight)) / 2;
            }
        } else {
            float width = doodleBound.left;
            if (doodleBound.left <= 0.0f || doodleBound.right < this.doodleView.getWidth()) {
                if (doodleBound.right < this.doodleView.getWidth() && doodleBound.left <= 0.0f) {
                    width = this.doodleView.getWidth() - doodleBound.right;
                    if (this.doodleView.getDoodleRotation() == 0 || this.doodleView.getDoodleRotation() == 180) {
                        if (this.doodleView.getDoodleRotation() == 0) {
                            doodleTranslationX2 += width;
                        } else {
                            doodleTranslationX2 -= width;
                        }
                    } else if (this.doodleView.getDoodleRotation() == 90) {
                        doodleTranslationY2 -= width;
                    } else {
                        doodleTranslationY2 += width;
                    }
                }
            } else if (this.doodleView.getDoodleRotation() == 0 || this.doodleView.getDoodleRotation() == 180) {
                if (this.doodleView.getDoodleRotation() == 0) {
                    doodleTranslationX2 -= width;
                } else {
                    doodleTranslationX2 += width;
                }
            } else if (this.doodleView.getDoodleRotation() == 90) {
                doodleTranslationY2 += width;
            } else {
                doodleTranslationY2 -= width;
            }
        }
        if (anim) {
            ValueAnimator valueAnimator = this.translateAnimator;
            if (valueAnimator != null && valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator valueAnimator2 = new ValueAnimator();
            this.translateAnimator = valueAnimator2;
            Intrinsics.checkNotNull(valueAnimator2);
            valueAnimator2.setDuration(100L);
            ValueAnimator valueAnimator3 = this.translateAnimator;
            Intrinsics.checkNotNull(valueAnimator3);
            valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.doodle.feature.draw.DoodleOnTouchGestureListener$$ExternalSyntheticLambda3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator4) {
                    DoodleOnTouchGestureListener.limitBound$lambda$22(this.f$0, valueAnimator4);
                }
            });
            ValueAnimator valueAnimator4 = this.translateAnimator;
            Intrinsics.checkNotNull(valueAnimator4);
            valueAnimator4.setFloatValues(doodleTranslationX, doodleTranslationX2);
            this.transAnimOldY = doodleTranslationY;
            this.transAnimY = doodleTranslationY2;
            ValueAnimator valueAnimator5 = this.translateAnimator;
            Intrinsics.checkNotNull(valueAnimator5);
            valueAnimator5.start();
            return;
        }
        this.doodleView.setDoodleTranslation(doodleTranslationX2, doodleTranslationY2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void limitBound$lambda$22(DoodleOnTouchGestureListener doodleOnTouchGestureListener, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue();
        float animatedFraction = animation.getAnimatedFraction();
        DoodleView doodleView = doodleOnTouchGestureListener.doodleView;
        float f = doodleOnTouchGestureListener.transAnimOldY;
        doodleView.setDoodleTranslation(fFloatValue, f + ((doodleOnTouchGestureListener.transAnimY - f) * animatedFraction));
    }

    private final void expandRect(Rect rect, int expand) {
        rect.left = rect.left - expand < 0 ? 0 : rect.left - expand;
        rect.right += expand;
        rect.top = rect.top - expand >= 0 ? rect.top - expand : 0;
        rect.bottom += expand;
    }

    public final void clearViewAnimator() {
        ValueAnimator valueAnimator = this.translateAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.scaleAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
    }
}
