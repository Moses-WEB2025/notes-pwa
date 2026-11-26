package com.miui.libhandwritingpreview;

import android.graphics.PointF;
import android.graphics.RectF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.operator.PreCanvasOperator;
import com.miui.libhandwritingpreview.system.PreCamera;
import com.miui.maml.folme.AnimatedProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.systems.MathSystem;
import xc.richtext.api.ScreenRenderProvider;
import xc.richtext.data.SpannedData;

/* JADX INFO: compiled from: PreCurveEngineRenderService.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eJ\u0016\u0010\u0010\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012J\u000e\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012J\u0006\u0010\u0015\u001a\u00020\u0016J\u0016\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000eJ\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u000eJ\u001e\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eJ\u0006\u0010 \u001a\u00020\u0016J\u0006\u0010!\u001a\u00020\u0016J\u000e\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020$J\u0006\u0010%\u001a\u00020\u0016J\u0006\u0010&\u001a\u00020\fJ\u0006\u0010'\u001a\u00020\u0016R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006("}, d2 = {"Lcom/miui/libhandwritingpreview/PreCurveEngineRenderService;", "", "engine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", "curveEngine", "provider", "Lxc/richtext/api/ScreenRenderProvider;", "getProvider", "()Lxc/richtext/api/ScreenRenderProvider;", "getPageIndexBySSPoint", "", AnimatedProperty.PROPERTY_NAME_X, "", AnimatedProperty.PROPERTY_NAME_Y, "getPageIndexByWSPoint", "covertWSRect2SSRect", "Landroid/graphics/RectF;", "rectWS", "covertSSRect2WSRect", "onSelected", "", "onMove", "offsetX", "offsetY", "onRotate", "angle", "onScale", "scale", "centerX", "centerY", "forceRefresh", "refreshTextAndImg", "refreshText", "span", "Lxc/richtext/data/SpannedData;", "enableEdit", "getCurrentPageIndex", "refreshAll", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreCurveEngineRenderService {
    private final PreMiCurveEngine curveEngine;
    private final ScreenRenderProvider provider;

    public final void onMove(float offsetX, float offsetY) {
    }

    public final void onRotate(float angle) {
    }

    public final void onScale(float scale, float centerX, float centerY) {
    }

    public final void onSelected() {
    }

    public PreCurveEngineRenderService(PreMiCurveEngine engine) {
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.curveEngine = engine;
        this.provider = new ScreenRenderProvider() { // from class: com.miui.libhandwritingpreview.PreCurveEngineRenderService$provider$1
            @Override // xc.richtext.api.ScreenRenderProvider
            public MathSystem getMathSystem() {
                return null;
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public int getPageIndexByScreenSpacePoint(float x, float y) {
                return this.this$0.getPageIndexBySSPoint(x, y);
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public int getPageIndexByWorldSpacePoint(float x, float y) {
                return this.this$0.getPageIndexByWSPoint(x, y);
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public RectF covertWSRectToSSRect(RectF rect) {
                Intrinsics.checkNotNullParameter(rect, "rect");
                return this.this$0.covertWSRect2SSRect(rect);
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public RectF covertSSRectToWSRect(RectF rect) {
                Intrinsics.checkNotNullParameter(rect, "rect");
                return this.this$0.covertSSRect2WSRect(rect);
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public float getSaveRatio() {
                PreCanvasOperator preCanvasOperator = this.this$0.curveEngine.getPenEngine().mCanvasOperator;
                Intrinsics.checkNotNull(preCanvasOperator);
                return preCanvasOperator.getSaveRatio();
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public float getDrawRatio() {
                PreCanvasOperator preCanvasOperator = this.this$0.curveEngine.getPenEngine().mCanvasOperator;
                Intrinsics.checkNotNull(preCanvasOperator);
                return preCanvasOperator.getDrawRatio();
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public void selected() {
                this.this$0.onSelected();
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public void doMove(float offsetX, float offsetY) {
                this.this$0.onMove(offsetX, offsetY);
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public void doRotate(float angle) {
                this.this$0.onRotate(angle);
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public void doScale(float scale, float centerX, float centerY) {
                this.this$0.onScale(scale, centerX, centerY);
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public void refreshEditText(SpannedData span) {
                Intrinsics.checkNotNullParameter(span, "span");
                this.this$0.refreshText(span);
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public void refresh() {
                this.this$0.forceRefresh();
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public void refreshSpan() {
                this.this$0.refreshTextAndImg();
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public void enableEditMode() {
                this.this$0.enableEdit();
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public boolean isPenEngineReleased() {
                return this.this$0.curveEngine.getPenEngine().isRelease();
            }

            @Override // xc.richtext.api.ScreenRenderProvider
            public boolean isCurrentPageBgDark() {
                return this.this$0.curveEngine.getPageSystem().isBgDark(this.this$0.getCurrentPageIndex());
            }
        };
    }

    public final ScreenRenderProvider getProvider() {
        return this.provider;
    }

    public final int getPageIndexBySSPoint(float x, float y) {
        return this.curveEngine.getPageSystem().getPageIndexByScreenSpacePoint(new PointF(x, y));
    }

    public final int getPageIndexByWSPoint(float x, float y) {
        return this.curveEngine.getPageSystem().getPageIndexByWorldSpacePoint(new PointF(x, y));
    }

    public final RectF covertWSRect2SSRect(RectF rectWS) {
        Intrinsics.checkNotNullParameter(rectWS, "rectWS");
        return this.curveEngine.getScreenSpaceRectFromWorldSpaceRect(rectWS);
    }

    public final RectF covertSSRect2WSRect(RectF rectWS) {
        Intrinsics.checkNotNullParameter(rectWS, "rectWS");
        return this.curveEngine.getWorldSpaceRectFromScreenSpaceRect(rectWS);
    }

    public final void forceRefresh() {
        this.curveEngine.requestMultiBufferRender(PreMiCurveEngine.ERefreshReason.Force);
    }

    public final void refreshTextAndImg() {
        this.curveEngine.getImgSystem().markDirty();
        this.curveEngine.getTextSystem().markDirty();
        PreMiCurveEngine.requestMultiBufferRender$default(this.curveEngine, null, 1, null);
    }

    public final void refreshText(SpannedData span) {
        Intrinsics.checkNotNullParameter(span, "span");
        this.curveEngine.getTextSystem().setEditingTextSpan(span);
        PreMiCurveEngine.requestMultiBufferRender$default(this.curveEngine, null, 1, null);
    }

    public final void enableEdit() {
        this.curveEngine.requestMultiBufferRender(PreMiCurveEngine.ERefreshReason.ExitTextEdit);
    }

    public final int getCurrentPageIndex() {
        return this.curveEngine.getPageSystem().getCurrentPageIndex();
    }

    public final void refreshAll() {
        this.curveEngine.getImgSystem().markDirty();
        this.curveEngine.getTextSystem().markDirty();
        PreCamera.refreshView$default(this.curveEngine.getCamera(), false, 1, null);
    }
}
