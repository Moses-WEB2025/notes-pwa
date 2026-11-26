package com.miui.doodle.feature.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.base.DoodleSelectableItemBase;
import com.miui.doodle.base.core.IDoodleItem;
import com.miui.doodle.document.LayerBitmap;
import com.miui.notes.base.utils.Logger;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BrushCacheView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000f"}, d2 = {"Lcom/miui/doodle/feature/draw/BrushCacheView;", "Landroid/view/View;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/doodle/feature/draw/DoodleView;)V", "getDoodleView", "()Lcom/miui/doodle/feature/draw/DoodleView;", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "doDraw", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class BrushCacheView extends View {
    private final DoodleView doodleView;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BrushCacheView(Context context, DoodleView doodleView) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(doodleView, "doodleView");
        this.doodleView = doodleView;
    }

    public final DoodleView getDoodleView() {
        return this.doodleView;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) throws Throwable {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        int iSave = canvas.save();
        canvas.rotate(this.doodleView.getDoodleRotation(), getWidth() / 2.0f, getHeight() / 2.0f);
        doDraw(canvas);
        canvas.restoreToCount(iSave);
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0079 A[Catch: all -> 0x0139, TRY_ENTER, TryCatch #5 {all -> 0x0139, blocks: (B:4:0x0023, B:6:0x002b, B:7:0x0057, B:9:0x005e, B:16:0x0079, B:18:0x007f), top: B:74:0x0023 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x007f A[Catch: all -> 0x0139, TRY_LEAVE, TryCatch #5 {all -> 0x0139, blocks: (B:4:0x0023, B:6:0x002b, B:7:0x0057, B:9:0x005e, B:16:0x0079, B:18:0x007f), top: B:74:0x0023 }] */
    /* JADX WARN: Code duplicated, block: B:25:0x00aa A[Catch: all -> 0x012f, TryCatch #6 {all -> 0x012f, blocks: (B:21:0x00a0, B:23:0x00a5, B:24:0x00a9, B:25:0x00aa, B:27:0x00b6, B:29:0x00bd, B:30:0x00cb, B:32:0x00d1, B:33:0x00e1, B:35:0x00e7, B:37:0x00f1, B:39:0x00fa, B:41:0x0103, B:43:0x011f, B:45:0x0124, B:46:0x0128, B:47:0x0129, B:42:0x011a, B:20:0x009d), top: B:76:0x00a0, inners: #2, #3 }] */
    private final void doDraw(Canvas canvas) throws Throwable {
        Canvas canvas2;
        Throwable th;
        int iSave;
        float allTranX = this.doodleView.getAllTranX();
        float allTranY = this.doodleView.getAllTranY();
        int iSave2 = canvas.save();
        canvas.translate(allTranX, allTranY);
        try {
            try {
                float allScale = this.doodleView.getAllScale();
                int iSave3 = canvas.save();
                canvas.scale(allScale, allScale, 0.0f, 0.0f);
                try {
                    LayerView currentLayerView = this.doodleView.getCurrentLayerView();
                    if (currentLayerView != null) {
                        Logger.INSTANCE.d("BrushCacheView", "layerView.itemStackOnViewCanvas " + currentLayerView.getItemStackOnViewCanvas().size());
                        for (IDoodleItem iDoodleItem : CollectionsKt.toList(currentLayerView.getItemStackOnViewCanvas())) {
                            if (!(iDoodleItem instanceof DoodleBrushPath)) {
                                if (iDoodleItem.getIsNeedClipOutside()) {
                                    int width = currentLayerView.getLayer().getWidth();
                                    int height = currentLayerView.getLayer().getHeight();
                                    iSave = canvas.save();
                                    canvas.clipRect(0, 0, width, height);
                                    canvas2 = canvas;
                                    IDoodleItem.draw$default(iDoodleItem, canvas2, null, null, null, 12, null);
                                    canvas2.restoreToCount(iSave);
                                } else {
                                    canvas2 = canvas;
                                    IDoodleItem.draw$default(iDoodleItem, canvas2, null, null, null, 12, null);
                                }
                            } else {
                                try {
                                    if (((DoodleBrushPath) iDoodleItem).isEraserPath()) {
                                        canvas2 = canvas;
                                    } else if (iDoodleItem.getIsNeedClipOutside()) {
                                        int width2 = currentLayerView.getLayer().getWidth();
                                        int height2 = currentLayerView.getLayer().getHeight();
                                        iSave = canvas.save();
                                        canvas.clipRect(0, 0, width2, height2);
                                        canvas2 = canvas;
                                        try {
                                            IDoodleItem.draw$default(iDoodleItem, canvas2, null, null, null, 12, null);
                                            try {
                                                canvas2.restoreToCount(iSave);
                                            } catch (Throwable th2) {
                                                th = th2;
                                                th = th;
                                                canvas2.restoreToCount(iSave3);
                                                throw th;
                                            }
                                        } catch (Throwable th3) {
                                            canvas2.restoreToCount(iSave);
                                            throw th3;
                                        }
                                    } else {
                                        canvas2 = canvas;
                                        IDoodleItem.draw$default(iDoodleItem, canvas2, null, null, null, 12, null);
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    canvas2 = canvas;
                                    canvas2.restoreToCount(iSave3);
                                    throw th;
                                }
                            }
                            canvas = canvas2;
                        }
                        canvas2 = canvas;
                        if (canvas2.isHardwareAccelerated()) {
                            Iterator<T> it = currentLayerView.getLayer().getLayerBitmapList().iterator();
                            while (it.hasNext()) {
                                for (IDoodleItem iDoodleItem2 : ((LayerBitmap) it.next()).getItems()) {
                                    if ((iDoodleItem2 instanceof DoodleSelectableItemBase) && ((DoodleSelectableItemBase) iDoodleItem2).getIsSelected()) {
                                        if (((DoodleSelectableItemBase) iDoodleItem2).getIsNeedClipOutside()) {
                                            int width3 = currentLayerView.getLayer().getWidth();
                                            int height3 = currentLayerView.getLayer().getHeight();
                                            int iSave4 = canvas2.save();
                                            canvas2.clipRect(0, 0, width3, height3);
                                            try {
                                                ((DoodleSelectableItemBase) iDoodleItem2).drawAtTheTop(canvas2);
                                                canvas2.restoreToCount(iSave4);
                                            } catch (Throwable th5) {
                                                canvas2.restoreToCount(iSave4);
                                                throw th5;
                                            }
                                        } else {
                                            ((DoodleSelectableItemBase) iDoodleItem2).drawAtTheTop(canvas2);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        canvas2 = canvas;
                    }
                    canvas2.restoreToCount(iSave3);
                    canvas2.restoreToCount(iSave2);
                } catch (Throwable th6) {
                    th = th6;
                    canvas2 = canvas;
                }
            } catch (Throwable th7) {
                th = th7;
                Throwable th8 = th;
                canvas.restoreToCount(iSave2);
                throw th8;
            }
        } catch (Throwable th9) {
            th = th9;
            Throwable th10 = th;
            canvas.restoreToCount(iSave2);
            throw th10;
        }
    }
}
