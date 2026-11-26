package com.miui.doodle.feature.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.base.DoodleSelectableItemBase;
import com.miui.doodle.base.ItemTransitInfo;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.base.core.IDoodleTouchDetector;
import com.miui.doodle.document.Dot;
import com.miui.doodle.document.Layer;
import com.miui.doodle.document.LayerBitmap;
import com.miui.doodle.feature.action.AddAction;
import com.miui.doodle.feature.action.AddLayerBitmapAction;
import com.miui.doodle.feature.action.BaseAction;
import com.miui.doodle.feature.action.EraseAction;
import com.miui.doodle.feature.action.RemovedItemInfo;
import com.miui.doodle.feature.action.RemovedLayerBitmapInfo;
import com.miui.doodle.feature.action.ReplaceAction;
import com.miui.doodle.feature.action.TransitAction;
import com.miui.doodle.utils.DrawUtil;
import com.miui.doodle.utils.UIUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: LayerView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\b\u0007\u0018\u0000 ]2\u00020\u0001:\u0001]B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0017J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001eH\u0014J\u0010\u0010.\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001eH\u0002J$\u0010/\u001a\u00020,2\u0006\u0010-\u001a\u00020\u001e2\u0006\u00100\u001a\u00020\u00162\n\b\u0002\u00101\u001a\u0004\u0018\u00010'H\u0002J\u0010\u00102\u001a\u00020#2\u0006\u00100\u001a\u00020\u0016H\u0002J\u0006\u00103\u001a\u00020,J\b\u00104\u001a\u00020,H\u0002J\u0010\u00105\u001a\u00020,2\u0006\u00100\u001a\u00020\u0016H\u0002J\u001c\u00106\u001a\u00020,2\n\b\u0002\u00101\u001a\u0004\u0018\u00010'2\b\b\u0002\u00107\u001a\u00020#J\u001a\u00108\u001a\u00020,2\u0006\u00100\u001a\u0002092\n\b\u0002\u00101\u001a\u0004\u0018\u00010'J\u001a\u0010:\u001a\u00020,2\u0006\u00100\u001a\u0002092\n\b\u0002\u00101\u001a\u0004\u0018\u00010'J\u001c\u0010;\u001a\u00020,2\n\b\u0002\u00101\u001a\u0004\u0018\u00010'2\b\b\u0002\u00107\u001a\u00020#J\u0014\u0010<\u001a\u00020,2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020?0>J\u0014\u0010@\u001a\u00020,2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020?0>J\u001a\u0010A\u001a\u00020,2\u0006\u0010B\u001a\u00020C2\n\b\u0002\u00101\u001a\u0004\u0018\u00010'J\u001a\u0010D\u001a\u00020,2\u0006\u00100\u001a\u00020C2\n\b\u0002\u00101\u001a\u0004\u0018\u00010'J\u000e\u0010E\u001a\u00020,2\u0006\u0010F\u001a\u00020GJ\u000e\u0010H\u001a\u00020,2\u0006\u00100\u001a\u00020\u0016J\u000e\u0010I\u001a\u00020#2\u0006\u00100\u001a\u00020\u0016J\u0018\u0010J\u001a\u00020,2\u0006\u00100\u001a\u00020\u00162\b\u0010K\u001a\u0004\u0018\u00010\u001cJ\u001e\u0010L\u001a\u00020,2\u0006\u0010M\u001a\u00020\u00162\u0006\u0010N\u001a\u00020\u00162\u0006\u0010F\u001a\u00020GJ\u000e\u0010O\u001a\u00020,2\u0006\u00100\u001a\u00020\u0016J\u000e\u0010J\u001a\u00020,2\u0006\u00100\u001a\u00020\u0016J\u000e\u0010P\u001a\u00020,2\u0006\u00100\u001a\u00020\u0016J\u0018\u0010Q\u001a\u00020,2\u0006\u00100\u001a\u00020\u00162\b\u0010F\u001a\u0004\u0018\u00010GJ\f\u0010R\u001a\b\u0012\u0004\u0012\u00020S0\u0015J\f\u0010T\u001a\b\u0012\u0004\u0012\u00020U0\u0015J\u0006\u0010V\u001a\u00020,J\u001f\u0010W\u001a\u00020,2\u0006\u00100\u001a\u00020\u00162\n\b\u0002\u0010X\u001a\u0004\u0018\u00010Y¢\u0006\u0002\u0010ZJ\u000e\u0010[\u001a\u00020,2\u0006\u0010F\u001a\u00020GJ\u0006\u0010\\\u001a\u00020,R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010&\u001a\u0004\u0018\u00010'X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010(\u001a\u0004\u0018\u00010)X\u0082\u000e¢\u0006\u0004\n\u0002\u0010*¨\u0006^"}, d2 = {"Lcom/miui/doodle/feature/draw/LayerView;", "Landroid/view/View;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", "defaultTouchDetector", "Lcom/miui/doodle/base/core/IDoodleTouchDetector;", "layer", "Lcom/miui/doodle/document/Layer;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/doodle/feature/draw/DoodleView;Lcom/miui/doodle/base/core/IDoodleTouchDetector;Lcom/miui/doodle/document/Layer;)V", "getDefaultTouchDetector", "()Lcom/miui/doodle/base/core/IDoodleTouchDetector;", "setDefaultTouchDetector", "(Lcom/miui/doodle/base/core/IDoodleTouchDetector;)V", "getLayer", "()Lcom/miui/doodle/document/Layer;", "setLayer", "(Lcom/miui/doodle/document/Layer;)V", "itemStackOnViewCanvas", "", "Lcom/miui/doodle/base/core/IDoodleItem;", "getItemStackOnViewCanvas", "()Ljava/util/List;", "pendingItemsDrawToBitmap", "getPendingItemsDrawToBitmap", "tmpBitmap", "Landroid/graphics/Bitmap;", "tmpBitmapCanvas", "Landroid/graphics/Canvas;", "clearPaint", "Landroid/graphics/Paint;", "paint", "onTouchEvent", "", "event", "Landroid/view/MotionEvent;", "dirtyRect", "Landroid/graphics/Rect;", "lastDrawTime", "", "Ljava/lang/Long;", "onDraw", "", "canvas", "doDraw", "drawItem", CloudPushConstants.XML_ITEM, Notes.Data.DIRTY, "finishDrawItem", "addNewLayerBitmap", "drawToDoodleBitmap", "drawToBitmap", "reset", "fast", "resetTextOrBitmap", "Lcom/miui/doodle/base/DoodleSelectableItemBase;", "postResetTextOrBitmap", "postReset", "eraserLayerBitmap", "eraserDots", "", "Lcom/miui/doodle/document/Dot;", "postEraserLayerBitmap", "resetShape", "shapeItem", "Lcom/miui/doodle/feature/draw/DoodleBrushPath;", "postResetShape", "updateTextBitmapLayer", "layerBitmap", "Lcom/miui/doodle/document/LayerBitmap;", "markItemToOptimizeDrawing", "notifyItemFinishedErasing", "notifyItemFinishedDrawing", "oldLayerBitmap", "notifyItemFinishedReplacing", "oldItem", "newItem", "notifyItemTempRemove", "notifySelectedItemLayoutFinished", "removeItem", "createRemovedItemInfoList", "Lcom/miui/doodle/feature/action/RemovedItemInfo;", "getRemovedLayerBitmapList", "Lcom/miui/doodle/feature/action/RemovedLayerBitmapInfo;", "finishRemoveAction", "addItem", "index", "", "(Lcom/miui/doodle/base/core/IDoodleItem;Ljava/lang/Integer;)V", "removeLayerBitmap", "clear", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class LayerView extends View {
    public static final String TAG = "LayerView";
    private final Paint clearPaint;
    private IDoodleTouchDetector defaultTouchDetector;
    private final Rect dirtyRect;
    private final DoodleView doodleView;
    private final List<IDoodleItem> itemStackOnViewCanvas;
    private Long lastDrawTime;
    private Layer layer;
    private final Paint paint;
    private final List<IDoodleItem> pendingItemsDrawToBitmap;
    private final Bitmap tmpBitmap;
    private final Canvas tmpBitmapCanvas;

    public /* synthetic */ LayerView(Context context, DoodleView doodleView, IDoodleTouchDetector iDoodleTouchDetector, Layer layer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, doodleView, (i & 4) != 0 ? null : iDoodleTouchDetector, layer);
    }

    public final IDoodleTouchDetector getDefaultTouchDetector() {
        return this.defaultTouchDetector;
    }

    public final void setDefaultTouchDetector(IDoodleTouchDetector iDoodleTouchDetector) {
        this.defaultTouchDetector = iDoodleTouchDetector;
    }

    public final Layer getLayer() {
        return this.layer;
    }

    public final void setLayer(Layer layer) {
        Intrinsics.checkNotNullParameter(layer, "<set-?>");
        this.layer = layer;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LayerView(Context context, DoodleView doodleView, IDoodleTouchDetector iDoodleTouchDetector, Layer layer) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        Intrinsics.checkNotNullParameter(layer, "layer");
        this.doodleView = doodleView;
        this.defaultTouchDetector = iDoodleTouchDetector;
        this.layer = layer;
        this.itemStackOnViewCanvas = new ArrayList();
        this.pendingItemsDrawToBitmap = new ArrayList();
        Paint paint = new Paint(3);
        this.clearPaint = paint;
        this.paint = new Paint();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.layer.getWidth(), this.layer.getHeight(), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        this.tmpBitmap = bitmapCreateBitmap;
        Canvas canvas = new Canvas();
        this.tmpBitmapCanvas = canvas;
        canvas.setBitmap(bitmapCreateBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public final List<IDoodleItem> getItemStackOnViewCanvas() {
        return this.itemStackOnViewCanvas;
    }

    public final List<IDoodleItem> getPendingItemsDrawToBitmap() {
        return this.pendingItemsDrawToBitmap;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        IDoodleTouchDetector iDoodleTouchDetector = this.defaultTouchDetector;
        if (iDoodleTouchDetector != null) {
            return iDoodleTouchDetector.onTouchEvent(event);
        }
        return false;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        DrawUtil.INSTANCE.setLastDrawTime(System.currentTimeMillis());
        this.lastDrawTime = Long.valueOf(System.currentTimeMillis());
        int iSave = canvas.save();
        Rect rect = this.dirtyRect;
        if (rect != null) {
            canvas.clipRect(rect);
        }
        canvas.rotate(this.doodleView.getDoodleRotation(), getWidth() / 2.0f, getHeight() / 2.0f);
        doDraw(canvas);
        canvas.restoreToCount(iSave);
    }

    private final void doDraw(Canvas canvas) {
        System.currentTimeMillis();
        float allTranX = this.doodleView.getAllTranX();
        float allTranY = this.doodleView.getAllTranY();
        float allScale = this.doodleView.getAllScale();
        if (!canvas.isHardwareAccelerated()) {
            allScale = 1.0f;
            allTranX = 0.0f;
            allTranY = 0.0f;
        }
        int iSave = canvas.save();
        canvas.translate(allTranX, allTranY);
        try {
            iSave = canvas.save();
            canvas.scale(allScale, allScale, 0.0f, 0.0f);
            try {
                Bitmap backgroundBitmap = this.layer.getBackgroundBitmap();
                if (backgroundBitmap != null && !backgroundBitmap.isRecycled()) {
                    canvas.drawBitmap(backgroundBitmap, 0.0f, 0.0f, this.paint);
                }
                for (LayerBitmap layerBitmap : this.layer.getLayerBitmapList()) {
                    if (this.doodleView.getPen().isElementEraser()) {
                        layerBitmap.elementRedrawToView(canvas, this.doodleView, this.layer.getWidth(), this.layer.getHeight());
                    } else {
                        layerBitmap.drawToView(canvas, this.doodleView);
                    }
                }
                canvas.restoreToCount(iSave);
            } finally {
                canvas.restoreToCount(iSave);
            }
        } catch (Throwable th) {
            canvas.restoreToCount(iSave);
            throw th;
        }
    }

    static /* synthetic */ void drawItem$default(LayerView layerView, Canvas canvas, IDoodleItem iDoodleItem, Rect rect, int i, Object obj) {
        if ((i & 4) != 0) {
            rect = null;
        }
        layerView.drawItem(canvas, iDoodleItem, rect);
    }

    private final void drawItem(Canvas canvas, IDoodleItem item, Rect dirty) {
        if (item.getPen().isEraser()) {
            return;
        }
        if ((item instanceof DoodleBrushPath) && !(item instanceof DoodleShape)) {
            Bitmap brushGlobalCacheBitmap = DoodleBrushPath.INSTANCE.getBrushGlobalCacheBitmap();
            if (brushGlobalCacheBitmap == null || brushGlobalCacheBitmap.isRecycled()) {
                return;
            }
            DoodleBrushPath doodleBrushPath = (DoodleBrushPath) item;
            if (this.paint.getAlpha() != doodleBrushPath.getAlpha()) {
                this.paint.setAlpha(doodleBrushPath.getAlpha());
            }
            canvas.drawBitmap(brushGlobalCacheBitmap, 0.0f, 0.0f, this.paint);
            return;
        }
        this.tmpBitmapCanvas.save();
        this.tmpBitmapCanvas.drawPaint(this.clearPaint);
        if (dirty != null) {
            this.tmpBitmapCanvas.clipRect(dirty);
        }
        IDoodleItem.draw$default(item, this.tmpBitmapCanvas, dirty, null, null, 12, null);
        this.tmpBitmapCanvas.restore();
        this.paint.setAlpha(item.getAlpha());
        canvas.drawBitmap(this.tmpBitmap, 0.0f, 0.0f, this.paint);
    }

    private final boolean finishDrawItem(IDoodleItem item) {
        boolean zAddItem = this.layer.addItem(item);
        if ((item instanceof DoodleBrushPath) && !(item instanceof DoodleShape)) {
            Bitmap brushGlobalCacheBitmap = DoodleBrushPath.INSTANCE.getBrushGlobalCacheBitmap();
            if (brushGlobalCacheBitmap != null) {
                DoodleBrushPath doodleBrushPath = (DoodleBrushPath) item;
                if (DoodleBrushPath.INSTANCE.getNormalPaint().getAlpha() != doodleBrushPath.getAlpha()) {
                    DoodleBrushPath.INSTANCE.getNormalPaint().setAlpha(doodleBrushPath.getAlpha());
                }
                Canvas currentCanvas = this.layer.getCurrentCanvas();
                if (currentCanvas != null) {
                    currentCanvas.drawBitmap(brushGlobalCacheBitmap, 0.0f, 0.0f, DoodleBrushPath.INSTANCE.getNormalPaint());
                }
                invalidate();
                return zAddItem;
            }
        } else if (item instanceof DoodleShape) {
            DoodleShape doodleShape = (DoodleShape) item;
            Bitmap bitmap = doodleShape.getBitmap();
            if (bitmap != null) {
                if (this.paint.getAlpha() != doodleShape.getAlpha()) {
                    this.paint.setAlpha(doodleShape.getAlpha());
                }
                Canvas currentCanvas2 = this.layer.getCurrentCanvas();
                if (currentCanvas2 != null) {
                    currentCanvas2.drawBitmap(bitmap, 0.0f, 0.0f, this.paint);
                    return zAddItem;
                }
            }
        } else {
            this.tmpBitmapCanvas.save();
            this.tmpBitmapCanvas.drawPaint(this.clearPaint);
            IDoodleItem.draw$default(item, this.tmpBitmapCanvas, null, null, null, 14, null);
            this.tmpBitmapCanvas.restore();
            if (this.paint.getAlpha() != item.getAlpha()) {
                this.paint.setAlpha(item.getAlpha());
            }
            Canvas currentCanvas3 = this.layer.getCurrentCanvas();
            if (currentCanvas3 != null) {
                currentCanvas3.drawBitmap(this.tmpBitmap, 0.0f, 0.0f, this.paint);
            }
        }
        return zAddItem;
    }

    public final void addNewLayerBitmap() {
        this.layer.addNewLayerBitmap();
    }

    private final void drawToDoodleBitmap() {
        List<IDoodleItem> list = this.pendingItemsDrawToBitmap;
        if (list == null || list.isEmpty()) {
            return;
        }
        for (IDoodleItem iDoodleItem : this.pendingItemsDrawToBitmap) {
            this.layer.addItem(iDoodleItem);
            drawToBitmap(iDoodleItem);
        }
        this.pendingItemsDrawToBitmap.clear();
    }

    private final void drawToBitmap(IDoodleItem item) {
        Canvas currentCanvas = this.layer.getCurrentCanvas();
        if (currentCanvas != null) {
            drawItem$default(this, currentCanvas, item, null, 4, null);
        }
    }

    public static /* synthetic */ void reset$default(LayerView layerView, Rect rect, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            rect = null;
        }
        if ((i & 2) != 0) {
            z = false;
        }
        layerView.reset(rect, z);
    }

    public final void reset(Rect dirty, boolean fast) {
        Logger.INSTANCE.d(TAG, "dirty: " + dirty);
        if (!fast) {
            for (LayerBitmap layerBitmap : this.layer.getLayerBitmapList()) {
                Canvas canvas = layerBitmap.getCanvas();
                if (canvas != null) {
                    int iSave = canvas.save();
                    if (dirty != null) {
                        canvas.clipRect(dirty);
                    }
                    canvas.drawPaint(this.clearPaint);
                    for (IDoodleItem iDoodleItem : layerBitmap.getItems()) {
                        if (dirty == null || !(iDoodleItem instanceof DoodleBrushPath) || UIUtils.INSTANCE.detectIntersect(dirty, ((DoodleBrushPath) iDoodleItem).getBounds())) {
                            drawItem(canvas, iDoodleItem, dirty);
                        }
                    }
                    canvas.restoreToCount(iSave);
                }
            }
        }
        invalidate();
    }

    public static /* synthetic */ void resetTextOrBitmap$default(LayerView layerView, DoodleSelectableItemBase doodleSelectableItemBase, Rect rect, int i, Object obj) {
        if ((i & 2) != 0) {
            rect = null;
        }
        layerView.resetTextOrBitmap(doodleSelectableItemBase, rect);
    }

    public final void resetTextOrBitmap(DoodleSelectableItemBase item, Rect dirty) {
        Intrinsics.checkNotNullParameter(item, "item");
        Canvas canvasFindCanvas = this.layer.findCanvas(item);
        if (canvasFindCanvas != null) {
            int iSave = canvasFindCanvas.save();
            if (dirty != null) {
                canvasFindCanvas.clipRect(dirty);
            }
            canvasFindCanvas.drawPaint(this.clearPaint);
            List<IDoodleItem> listFindItemsInSameLayer = this.layer.findItemsInSameLayer(item);
            if (listFindItemsInSameLayer != null) {
                for (IDoodleItem iDoodleItem : listFindItemsInSameLayer) {
                    if (dirty == null || ((iDoodleItem instanceof DoodleSelectableItemBase) && UIUtils.INSTANCE.detectIntersect(((DoodleSelectableItemBase) iDoodleItem).getBoundsInCanvas(), dirty))) {
                        drawItem(canvasFindCanvas, iDoodleItem, dirty);
                    }
                }
            }
            canvasFindCanvas.restoreToCount(iSave);
        }
        invalidate();
    }

    public static /* synthetic */ void postResetTextOrBitmap$default(LayerView layerView, DoodleSelectableItemBase doodleSelectableItemBase, Rect rect, int i, Object obj) {
        if ((i & 2) != 0) {
            rect = null;
        }
        layerView.postResetTextOrBitmap(doodleSelectableItemBase, rect);
    }

    public final void postResetTextOrBitmap(final DoodleSelectableItemBase item, final Rect dirty) {
        Intrinsics.checkNotNullParameter(item, "item");
        post(new Runnable() { // from class: com.miui.doodle.feature.draw.LayerView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.resetTextOrBitmap(item, dirty);
            }
        });
    }

    public static /* synthetic */ void postReset$default(LayerView layerView, Rect rect, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            rect = null;
        }
        if ((i & 2) != 0) {
            z = false;
        }
        layerView.postReset(rect, z);
    }

    public final void postReset(final Rect dirty, final boolean fast) {
        post(new Runnable() { // from class: com.miui.doodle.feature.draw.LayerView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.reset(dirty, fast);
            }
        });
    }

    public final void eraserLayerBitmap(List<Dot> eraserDots) {
        Intrinsics.checkNotNullParameter(eraserDots, "eraserDots");
        this.layer.doEraser(eraserDots, this.clearPaint);
    }

    public final void postEraserLayerBitmap(final List<Dot> eraserDots) {
        Intrinsics.checkNotNullParameter(eraserDots, "eraserDots");
        post(new Runnable() { // from class: com.miui.doodle.feature.draw.LayerView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.eraserLayerBitmap(eraserDots);
            }
        });
    }

    public static /* synthetic */ void resetShape$default(LayerView layerView, DoodleBrushPath doodleBrushPath, Rect rect, int i, Object obj) {
        if ((i & 2) != 0) {
            rect = null;
        }
        layerView.resetShape(doodleBrushPath, rect);
    }

    public final void resetShape(DoodleBrushPath shapeItem, Rect dirty) {
        Canvas canvas;
        Intrinsics.checkNotNullParameter(shapeItem, "shapeItem");
        LayerBitmap currentLayerBitmap = this.layer.getCurrentLayerBitmap();
        if (currentLayerBitmap == null || (canvas = currentLayerBitmap.getCanvas()) == null) {
            return;
        }
        int iSave = canvas.save();
        if (dirty != null) {
            canvas.clipRect(dirty);
        }
        canvas.drawPaint(this.clearPaint);
        for (IDoodleItem iDoodleItem : currentLayerBitmap.getItems()) {
            if (dirty == null || !(iDoodleItem instanceof DoodleBrushPath) || UIUtils.INSTANCE.detectIntersect(dirty, ((DoodleBrushPath) iDoodleItem).getBounds())) {
                drawItem(canvas, iDoodleItem, dirty);
            }
        }
        canvas.restoreToCount(iSave);
    }

    public static /* synthetic */ void postResetShape$default(LayerView layerView, DoodleBrushPath doodleBrushPath, Rect rect, int i, Object obj) {
        if ((i & 2) != 0) {
            rect = null;
        }
        layerView.postResetShape(doodleBrushPath, rect);
    }

    public final void postResetShape(final DoodleBrushPath item, final Rect dirty) {
        Intrinsics.checkNotNullParameter(item, "item");
        post(new Runnable() { // from class: com.miui.doodle.feature.draw.LayerView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.resetShape(item, dirty);
            }
        });
    }

    public final void updateTextBitmapLayer(LayerBitmap layerBitmap) {
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        layerBitmap.updateBitmap(this.doodleView);
    }

    public final void markItemToOptimizeDrawing(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (this.itemStackOnViewCanvas.contains(item)) {
            throw new RuntimeException("The item has been added");
        }
        this.itemStackOnViewCanvas.add(item);
    }

    public final boolean notifyItemFinishedErasing(IDoodleItem item) throws InterruptedException {
        Intrinsics.checkNotNullParameter(item, "item");
        if (!this.itemStackOnViewCanvas.remove(item) || !(item instanceof DoodleBrushPath) || !((DoodleBrushPath) item).isEraserPath() || !this.layer.getStartChanged()) {
            return false;
        }
        this.doodleView.onProcessing(false);
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.doodleView.setEnabled(false);
        BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(jCurrentTimeMillis, null), 1, null);
        return true;
    }

    /* JADX INFO: renamed from: com.miui.doodle.feature.draw.LayerView$notifyItemFinishedErasing$1, reason: invalid class name */
    /* JADX INFO: compiled from: LayerView.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lkotlinx/coroutines/Job;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.doodle.feature.draw.LayerView$notifyItemFinishedErasing$1", f = "LayerView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Job>, Object> {
        final /* synthetic */ long $time;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(long j, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$time = j;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return LayerView.this.new AnonymousClass1(this.$time, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Job> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.doodle.feature.draw.LayerView$notifyItemFinishedErasing$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: LayerView.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.doodle.feature.draw.LayerView$notifyItemFinishedErasing$1$1", f = "LayerView.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00411 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ long $time;
            int label;
            final /* synthetic */ LayerView this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00411(LayerView layerView, long j, Continuation<? super C00411> continuation) {
                super(2, continuation);
                this.this$0 = layerView;
                this.$time = j;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00411(this.this$0, this.$time, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00411) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0.doodleView.pushToActionHistory(new EraseAction(this.this$0.getLayer().createEraserLayerBitmapInfoList()));
                this.this$0.getLayer().finishEraserAction();
                BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getMain()), null, null, new C00421(this.$time, this.this$0, null), 3, null);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: com.miui.doodle.feature.draw.LayerView$notifyItemFinishedErasing$1$1$1, reason: invalid class name and collision with other inner class name */
            /* JADX INFO: compiled from: LayerView.kt */
            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
            @DebugMetadata(c = "com.miui.doodle.feature.draw.LayerView$notifyItemFinishedErasing$1$1$1", f = "LayerView.kt", i = {0, 0}, l = {R2.attr.actionModeTheme}, m = "invokeSuspend", n = {"timeDifference", "needDelay"}, s = {"J$0", "I$0"})
            static final class C00421 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                final /* synthetic */ long $time;
                int I$0;
                long J$0;
                int label;
                final /* synthetic */ LayerView this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                C00421(long j, LayerView layerView, Continuation<? super C00421> continuation) {
                    super(2, continuation);
                    this.$time = j;
                    this.this$0 = layerView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                    return new C00421(this.$time, this.this$0, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                    return ((C00421) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        long jCurrentTimeMillis = System.currentTimeMillis() - this.$time;
                        int i2 = 0;
                        if (501 <= jCurrentTimeMillis && jCurrentTimeMillis < 600) {
                            i2 = 1;
                        }
                        if (i2 != 0) {
                            this.J$0 = jCurrentTimeMillis;
                            this.I$0 = i2;
                            this.label = 1;
                            if (DelayKt.delay(100L, this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    this.this$0.doodleView.setEnabled(true);
                    this.this$0.doodleView.onProcessing(true);
                    return Unit.INSTANCE;
                }
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new C00411(LayerView.this, this.$time, null), 3, null);
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final void notifyItemFinishedDrawing(IDoodleItem item, Bitmap oldLayerBitmap) {
        Bitmap bitmapCopy;
        Intrinsics.checkNotNullParameter(item, "item");
        if (this.itemStackOnViewCanvas.remove(item)) {
            List<IDoodleItem> currentItems = this.layer.getCurrentItems();
            if (currentItems != null && currentItems.contains(item)) {
                if (item instanceof DoodleSelectableItemBase) {
                    reset$default(this, ((DoodleSelectableItemBase) item).getBoundsInCanvas(), false, 2, null);
                    return;
                } else {
                    reset$default(this, null, false, 3, null);
                    return;
                }
            }
            boolean zFinishDrawItem = finishDrawItem(item);
            int size = this.layer.getLayerBitmapList().size() - 1;
            Bitmap bitmap = this.layer.getLayerBitmapList().get(size).getBitmap();
            if (bitmap != null) {
                Bitmap.Config config = bitmap.getConfig();
                Intrinsics.checkNotNull(config);
                bitmapCopy = bitmap.copy(config, true);
            } else {
                bitmapCopy = null;
            }
            if (!(item instanceof DoodleShape)) {
                if (zFinishDrawItem) {
                    DoodleView doodleView = this.doodleView;
                    String id = this.layer.getLayerBitmapList().get(size).getId();
                    Bitmap bitmap2 = this.layer.getLayerBitmapList().get(size).getBitmap();
                    doodleView.pushToActionHistory(new AddLayerBitmapAction(new LayerBitmap(id, bitmap2 != null ? Bitmap.createBitmap(bitmap2) : null, this.layer.getLayerBitmapList().get(size).getCanvas(), this.layer.getLayerBitmapList().get(size).getType(), this.layer.getLayerBitmapList().get(size).getItems()), this, size));
                } else {
                    this.doodleView.pushToActionHistory(new AddAction(item, this, size, oldLayerBitmap, bitmapCopy));
                }
            }
            invalidate();
        }
    }

    public final void notifyItemFinishedReplacing(IDoodleItem oldItem, IDoodleItem newItem, LayerBitmap layerBitmap) {
        Intrinsics.checkNotNullParameter(oldItem, "oldItem");
        Intrinsics.checkNotNullParameter(newItem, "newItem");
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        if (this.itemStackOnViewCanvas.remove(newItem)) {
            finishDrawItem(newItem);
            layerBitmap.cacheImageAfterChanged();
            this.doodleView.pushToActionHistory(new ReplaceAction(oldItem, newItem, layerBitmap.getItems().indexOf(newItem), layerBitmap, layerBitmap.getCacheBitmapBeforeChanged(), layerBitmap.getCacheBitmapAfterChanged()));
            layerBitmap.finishChangedAction();
        }
    }

    public final void notifyItemTempRemove(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (this.itemStackOnViewCanvas.remove(item)) {
            List<IDoodleItem> currentItems = this.layer.getCurrentItems();
            if (currentItems != null) {
                currentItems.remove(item);
            }
            invalidate();
        }
    }

    public final void notifyItemFinishedDrawing(IDoodleItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (this.itemStackOnViewCanvas.remove(item)) {
            if ((item instanceof DoodleBrushPath) && ((DoodleBrushPath) item).isEraserPath()) {
                return;
            }
            List<IDoodleItem> currentItems = this.layer.getCurrentItems();
            if (currentItems != null && currentItems.contains(item)) {
                if (item instanceof DoodleSelectableItemBase) {
                    reset$default(this, ((DoodleSelectableItemBase) item).getBoundsInCanvas(), false, 2, null);
                    return;
                } else {
                    reset$default(this, null, false, 3, null);
                    return;
                }
            }
            this.pendingItemsDrawToBitmap.add(item);
            drawToDoodleBitmap();
            invalidate();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void notifySelectedItemLayoutFinished(IDoodleItem item) {
        LayerBitmap layerBitmap;
        Layer layer;
        List<LayerBitmap> layerBitmapList;
        Layer layer2;
        List<LayerBitmap> layerBitmapList2;
        Object next;
        Intrinsics.checkNotNullParameter(item, "item");
        Object transitAction = null;
        if (this.itemStackOnViewCanvas.remove(item)) {
            Bitmap cacheBitmapBeforeChanged = ((LayerBitmap) CollectionsKt.last((List) this.layer.getLayerBitmapList())).getCacheBitmapBeforeChanged();
            boolean zFinishDrawItem = finishDrawItem(item);
            int size = this.layer.getLayerBitmapList().size() - 1;
            Bitmap bitmapCacheImageAfterChanged = this.layer.getLayerBitmapList().get(size).cacheImageAfterChanged();
            if (!(item instanceof DoodleShape)) {
                if (zFinishDrawItem) {
                    DoodleView doodleView = this.doodleView;
                    String id = this.layer.getLayerBitmapList().get(size).getId();
                    Bitmap bitmap = this.layer.getLayerBitmapList().get(size).getBitmap();
                    if (bitmap != null) {
                        try {
                            transitAction = Bitmap.createBitmap(bitmap);
                        } catch (Exception e) {
                            Logger.INSTANCE.d(TAG, "exception :+" + e);
                            transitAction = bitmap;
                        }
                    }
                    doodleView.pushToActionHistory(new AddLayerBitmapAction(new LayerBitmap(id, transitAction, this.layer.getLayerBitmapList().get(size).getCanvas(), this.layer.getLayerBitmapList().get(size).getType(), this.layer.getLayerBitmapList().get(size).getItems()), this, size));
                } else {
                    this.doodleView.pushToActionHistory(new AddAction(item, this, size, cacheBitmapBeforeChanged, bitmapCacheImageAfterChanged));
                }
            }
            invalidate();
            ((LayerBitmap) CollectionsKt.last((List) this.layer.getLayerBitmapList())).releaseCacheImageBeforeAndAfterChanged();
            return;
        }
        LayerView currentLayerView = this.doodleView.getCurrentLayerView();
        if (currentLayerView == null || (layer2 = currentLayerView.layer) == null || (layerBitmapList2 = layer2.getLayerBitmapList()) == null) {
            layerBitmap = null;
        } else {
            Iterator<T> it = layerBitmapList2.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!((LayerBitmap) next).getItems().contains(item));
            layerBitmap = (LayerBitmap) next;
        }
        if (layerBitmap != null) {
            Bitmap cacheBitmapBeforeChanged2 = layerBitmap.getCacheBitmapBeforeChanged();
            LayerView currentLayerView2 = this.doodleView.getCurrentLayerView();
            Integer numValueOf = (currentLayerView2 == null || (layer = currentLayerView2.layer) == null || (layerBitmapList = layer.getLayerBitmapList()) == null) ? null : Integer.valueOf(layerBitmapList.indexOf(layerBitmap));
            item.cacheTransitInfoAfterChanged();
            updateTextBitmapLayer(layerBitmap);
            Bitmap bitmapCacheImageAfterChanged2 = layerBitmap.cacheImageAfterChanged();
            ItemTransitInfo transitInfoBeforeChanged = item.getTransitInfoBeforeChanged();
            if (transitInfoBeforeChanged != null) {
                ItemTransitInfo transitInfoAfterChanged = item.getTransitInfoAfterChanged();
                if (transitInfoAfterChanged != null) {
                    Integer num = numValueOf;
                    Intrinsics.checkNotNull(num);
                    transitAction = new TransitAction(item, this, num.intValue(), transitInfoBeforeChanged, transitInfoAfterChanged, cacheBitmapBeforeChanged2, bitmapCacheImageAfterChanged2);
                }
                if (transitAction != null) {
                    this.doodleView.pushToActionHistory((BaseAction) transitAction);
                }
            }
            item.releaseCacheTransitInfo();
            invalidate();
            layerBitmap.releaseCacheImageBeforeAndAfterChanged();
        }
    }

    public final void removeItem(IDoodleItem item, LayerBitmap layerBitmap) {
        Intrinsics.checkNotNullParameter(item, "item");
        this.pendingItemsDrawToBitmap.remove(item);
        this.itemStackOnViewCanvas.remove(item);
        this.layer.removeItem(item, layerBitmap);
    }

    public final List<RemovedItemInfo> createRemovedItemInfoList() {
        return this.layer.createRemovedItemInfoList();
    }

    public final List<RemovedLayerBitmapInfo> getRemovedLayerBitmapList() {
        return this.layer.getRemovedLayerBitmapList();
    }

    public final void finishRemoveAction() {
        this.layer.finishRemoveAction(this.doodleView);
    }

    public static /* synthetic */ void addItem$default(LayerView layerView, IDoodleItem iDoodleItem, Integer num, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        layerView.addItem(iDoodleItem, num);
    }

    public final void addItem(IDoodleItem item, Integer index) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (index != null) {
            this.layer.addItem(index.intValue(), item);
        } else {
            this.layer.addItem(item);
        }
    }

    public final void removeLayerBitmap(LayerBitmap layerBitmap) {
        Intrinsics.checkNotNullParameter(layerBitmap, "layerBitmap");
        this.pendingItemsDrawToBitmap.removeAll(layerBitmap.getItems());
        this.itemStackOnViewCanvas.removeAll(layerBitmap.getItems());
        this.layer.removeLayerBitmap(layerBitmap);
    }

    public final void clear() {
        this.tmpBitmap.recycle();
        this.layer.clear();
    }
}
