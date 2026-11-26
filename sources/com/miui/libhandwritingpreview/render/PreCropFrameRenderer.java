package com.miui.libhandwritingpreview.render;

import android.graphics.PointF;
import android.graphics.RectF;
import android.opengl.GLES30;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.EditStat;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import com.miui.libhandwritingpreview.system.PreBaseEngineStruct;
import defpackage.R2;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.opengl.GLRenderUtil;

/* JADX INFO: compiled from: PreCropFrameRenderer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 /2\u00020\u0001:\u0001/B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u000e\u001a\u00020\u000fH\u0014J\b\u0010\u0010\u001a\u00020\u000fH\u0014J0\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J \u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00152\b\b\u0002\u0010\u001e\u001a\u00020\u001aJ$\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u00072\b\b\u0002\u0010!\u001a\u00020\u00172\b\b\u0002\u0010\u001e\u001a\u00020\u001aH\u0002J(\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u00152\u0016\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u001a0%j\b\u0012\u0004\u0012\u00020\u001a`&H\u0002J(\u0010'\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u00152\u0016\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u001a0%j\b\u0012\u0004\u0012\u00020\u001a`&H\u0002J(\u0010(\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u00152\u0016\u0010$\u001a\u0012\u0012\u0004\u0012\u00020\u001a0%j\b\u0012\u0004\u0012\u00020\u001a`&H\u0002J2\u0010)\u001a\u00020\u000f2\u0016\u0010*\u001a\u0012\u0012\u0004\u0012\u00020\u001a0%j\b\u0012\u0004\u0012\u00020\u001a`&2\u0006\u0010+\u001a\u00020\u00152\b\b\u0002\u0010,\u001a\u00020\u0007H\u0002J\u0010\u0010-\u001a\u00020\u001a2\u0006\u0010.\u001a\u00020\u001aH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lcom/miui/libhandwritingpreview/render/PreCropFrameRenderer;", "Lcom/miui/libhandwritingpreview/system/PreBaseEngineStruct;", "engine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", "edgeTexId", "", "edgeHandlerTexId", "cornerHandlerTexId", "batchImageProgramId", "roundedLineProgramId", "vertexBuffer", "Ljava/nio/FloatBuffer;", "onInit", "", "onDestroy", "drawRoundedLine", "fillColor", "", "fbArea", "Landroid/graphics/RectF;", "pt1", "Landroid/graphics/PointF;", "pt2", "radius", "", "render", "imageAreaSS", "cropRatioRect", "angle", "drawBatchImage", "useTextureId", EditStat.ELEMENT_CENTER, "constructEdgeVertexBuffer", "cropAreaSS", "pointData", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "constructEdgeHandlerVertexBuffer", "constructCornerHandlerVertexBuffer", "addRectToVertexBuffer", "buffer", "rect", "id", "dpToPx", "dp", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreCropFrameRenderer extends PreBaseEngineStruct {
    public static final float REF_DENSITY = 2.6f;
    private int batchImageProgramId;
    private int cornerHandlerTexId;
    private int edgeHandlerTexId;
    private int edgeTexId;
    private int roundedLineProgramId;
    private final FloatBuffer vertexBuffer;
    private static final String TAG = "PreCropFrameRenderer";

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreCropFrameRenderer(PreMiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        FloatBuffer floatBufferCreateFloatBuffer = GLRenderUtil.createFloatBuffer(96);
        Intrinsics.checkNotNullExpressionValue(floatBufferCreateFloatBuffer, "createFloatBuffer(...)");
        this.vertexBuffer = floatBufferCreateFloatBuffer;
    }

    @Override // com.miui.libhandwritingpreview.system.BaseStruct
    protected void onInit() {
        this.batchImageProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(3100, 3100);
        this.roundedLineProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(210, 307);
        this.edgeTexId = GLUtils.INSTANCE.createTexture(16, 16);
        this.edgeHandlerTexId = GLUtils.INSTANCE.createTexture(128, 16);
        this.cornerHandlerTexId = GLUtils.INSTANCE.createTexture(128, 128);
        float[] fArr = {0.1f, 0.4f, 0.95f, 1.0f};
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(this.edgeTexId);
        GLES30.glBindFramebuffer(36160, iCreateFramebufferFromTexture);
        GLES30.glViewport(0, 0, 16, 16);
        GLES30.glClear(16384);
        drawRoundedLine(fArr, new RectF(0.0f, 0.0f, 16.0f, 16.0f), new PointF(-5.0f, 8.0f), new PointF(20.0f, 8.0f), 1.5f);
        GLUtils.INSTANCE.bindTextureToFramebuffer(iCreateFramebufferFromTexture, this.edgeHandlerTexId);
        GLES30.glViewport(0, 0, 128, 16);
        GLES30.glClear(16384);
        drawRoundedLine(fArr, new RectF(0.0f, 0.0f, 128.0f, 16.0f), new PointF(30.0f, 8.0f), new PointF(98.0f, 8.0f), 6.0f);
        GLUtils.INSTANCE.bindTextureToFramebuffer(iCreateFramebufferFromTexture, this.cornerHandlerTexId);
        GLES30.glViewport(0, 0, 128, 128);
        GLES30.glClear(16384);
        drawRoundedLine(fArr, new RectF(0.0f, 0.0f, 128.0f, 128.0f), new PointF(64.0f, 64.0f), new PointF(108.0f, 64.0f), 6.0f);
        drawRoundedLine(fArr, new RectF(0.0f, 0.0f, 128.0f, 128.0f), new PointF(64.0f, 64.0f), new PointF(64.0f, 108.0f), 6.0f);
        GLUtils.INSTANCE.deleteFramebuffer(iCreateFramebufferFromTexture);
    }

    @Override // com.miui.libhandwritingpreview.system.BaseStruct
    protected void onDestroy() {
        GLUtils.INSTANCE.deleteTexture(this.edgeTexId);
        GLUtils.INSTANCE.deleteTexture(this.edgeHandlerTexId);
        GLUtils.INSTANCE.deleteTexture(this.cornerHandlerTexId);
        GLES30.glDeleteProgram(this.batchImageProgramId);
        GLES30.glDeleteProgram(this.roundedLineProgramId);
    }

    private final void drawRoundedLine(float[] fillColor, RectF fbArea, PointF pt1, PointF pt2, float radius) {
        GLES30.glUseProgram(this.roundedLineProgramId);
        GLES30.glUniform4f(0, fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
        GLES30.glUniform4f(1, fbArea.left, fbArea.top, fbArea.right, fbArea.bottom);
        GLES30.glUniform2f(2, fbArea.width(), fbArea.height());
        GLES30.glUniform4f(3, pt1.x, pt1.y, pt2.x, pt2.y);
        GLES30.glUniform1f(4, radius);
        GLES30.glUniform1f(5, 0.0f);
        GLES30.glDrawArrays(5, 0, 4);
    }

    public static /* synthetic */ void render$default(PreCropFrameRenderer preCropFrameRenderer, RectF rectF, RectF rectF2, float f, int i, Object obj) {
        if ((i & 4) != 0) {
            f = 0.0f;
        }
        preCropFrameRenderer.render(rectF, rectF2, f);
    }

    public final void render(RectF imageAreaSS, RectF cropRatioRect, float angle) {
        Intrinsics.checkNotNullParameter(imageAreaSS, "imageAreaSS");
        Intrinsics.checkNotNullParameter(cropRatioRect, "cropRatioRect");
        RectF rectF = new RectF(MiMathUtils.INSTANCE.remapRange(cropRatioRect.left, 0.0f, 1.0f, imageAreaSS.left, imageAreaSS.right), MiMathUtils.INSTANCE.remapRange(cropRatioRect.top, 0.0f, 1.0f, imageAreaSS.top, imageAreaSS.bottom), MiMathUtils.INSTANCE.remapRange(cropRatioRect.right, 0.0f, 1.0f, imageAreaSS.left, imageAreaSS.right), MiMathUtils.INSTANCE.remapRange(cropRatioRect.bottom, 0.0f, 1.0f, imageAreaSS.top, imageAreaSS.bottom));
        PointF pointF = new PointF(imageAreaSS.centerX(), imageAreaSS.centerY());
        ArrayList<Float> arrayList = new ArrayList<>();
        this.vertexBuffer.clear();
        constructEdgeVertexBuffer(rectF, arrayList);
        drawBatchImage(this.edgeTexId, pointF, angle);
        arrayList.clear();
        this.vertexBuffer.clear();
        constructEdgeHandlerVertexBuffer(rectF, arrayList);
        drawBatchImage(this.edgeHandlerTexId, pointF, angle);
        arrayList.clear();
        this.vertexBuffer.clear();
        constructCornerHandlerVertexBuffer(rectF, arrayList);
        drawBatchImage(this.cornerHandlerTexId, pointF, angle);
    }

    static /* synthetic */ void drawBatchImage$default(PreCropFrameRenderer preCropFrameRenderer, int i, PointF pointF, float f, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            pointF = new PointF(0.0f, 0.0f);
        }
        if ((i2 & 4) != 0) {
            f = 0.0f;
        }
        preCropFrameRenderer.drawBatchImage(i, pointF, f);
    }

    private final void drawBatchImage(int useTextureId, PointF center, float angle) {
        this.vertexBuffer.position(0);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 4, R2.color.miuix_default_color_on_secondary_light, false, 16, (Buffer) this.vertexBuffer);
        GLES30.glUseProgram(this.batchImageProgramId);
        GLES30.glUniformMatrix4fv(0, 1, false, MiMathUtils.Companion.calculateMatrix$default(MiMathUtils.INSTANCE, 0.0f, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel(), 0.0f, 0.0f, 0.0f, 48, null), 0);
        GLES30.glUniform1f(1, angle);
        GLES30.glUniform2f(2, center.x, center.y);
        GLES30.glActiveTexture(33986);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, useTextureId);
        GLES30.glUniform1i(6, 2);
        GLES30.glUniform1i(7, 0);
        GLES30.glDrawArrays(4, 0, 24);
    }

    private final void constructEdgeVertexBuffer(RectF cropAreaSS, ArrayList<Float> pointData) {
        float fDpToPx = dpToPx(3.0769231f);
        addRectToVertexBuffer(pointData, new RectF(cropAreaSS.left, cropAreaSS.top - fDpToPx, cropAreaSS.right, cropAreaSS.top + fDpToPx), 0);
        addRectToVertexBuffer(pointData, new RectF(cropAreaSS.left, cropAreaSS.bottom - fDpToPx, cropAreaSS.right, cropAreaSS.bottom + fDpToPx), 2);
        addRectToVertexBuffer(pointData, new RectF(cropAreaSS.left - fDpToPx, cropAreaSS.top, cropAreaSS.left + fDpToPx, cropAreaSS.bottom), 3);
        addRectToVertexBuffer(pointData, new RectF(cropAreaSS.right - fDpToPx, cropAreaSS.top, cropAreaSS.right + fDpToPx, cropAreaSS.bottom), 1);
        this.vertexBuffer.put(CollectionsKt.toFloatArray(pointData), 0, 96);
    }

    private final void constructEdgeHandlerVertexBuffer(RectF cropAreaSS, ArrayList<Float> pointData) {
        float fDpToPx = dpToPx(24.615385f);
        float fDpToPx2 = dpToPx(3.0769231f);
        PointF pointF = new PointF(cropAreaSS.centerX(), cropAreaSS.top);
        addRectToVertexBuffer(pointData, new RectF(pointF.x - fDpToPx, pointF.y - fDpToPx2, pointF.x + fDpToPx, pointF.y + fDpToPx2), 0);
        PointF pointF2 = new PointF(cropAreaSS.centerX(), cropAreaSS.bottom);
        addRectToVertexBuffer(pointData, new RectF(pointF2.x - fDpToPx, pointF2.y - fDpToPx2, pointF2.x + fDpToPx, pointF2.y + fDpToPx2), 2);
        PointF pointF3 = new PointF(cropAreaSS.left, cropAreaSS.centerY());
        addRectToVertexBuffer(pointData, new RectF(pointF3.x - fDpToPx2, pointF3.y - fDpToPx, pointF3.x + fDpToPx2, pointF3.y + fDpToPx), 3);
        PointF pointF4 = new PointF(cropAreaSS.right, cropAreaSS.centerY());
        addRectToVertexBuffer(pointData, new RectF(pointF4.x - fDpToPx2, pointF4.y - fDpToPx, pointF4.x + fDpToPx2, pointF4.y + fDpToPx), 1);
        this.vertexBuffer.put(CollectionsKt.toFloatArray(pointData), 0, 96);
    }

    private final void constructCornerHandlerVertexBuffer(RectF cropAreaSS, ArrayList<Float> pointData) {
        float fDpToPx = dpToPx(24.615385f);
        addRectToVertexBuffer(pointData, new RectF(cropAreaSS.left - fDpToPx, cropAreaSS.top - fDpToPx, cropAreaSS.left + fDpToPx, cropAreaSS.top + fDpToPx), 0);
        addRectToVertexBuffer(pointData, new RectF(cropAreaSS.left - fDpToPx, cropAreaSS.bottom - fDpToPx, cropAreaSS.left + fDpToPx, cropAreaSS.bottom + fDpToPx), 3);
        addRectToVertexBuffer(pointData, new RectF(cropAreaSS.right - fDpToPx, cropAreaSS.top - fDpToPx, cropAreaSS.right + fDpToPx, cropAreaSS.top + fDpToPx), 1);
        addRectToVertexBuffer(pointData, new RectF(cropAreaSS.right - fDpToPx, cropAreaSS.bottom - fDpToPx, cropAreaSS.right + fDpToPx, cropAreaSS.bottom + fDpToPx), 2);
        this.vertexBuffer.put(CollectionsKt.toFloatArray(pointData), 0, 96);
    }

    static /* synthetic */ void addRectToVertexBuffer$default(PreCropFrameRenderer preCropFrameRenderer, ArrayList arrayList, RectF rectF, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        preCropFrameRenderer.addRectToVertexBuffer(arrayList, rectF, i);
    }

    private final void addRectToVertexBuffer(ArrayList<Float> buffer, RectF rect, int id) {
        PointF[] pointFArr = new PointF[][]{new PointF[]{new PointF(0.0f, 1.0f), new PointF(1.0f, 0.0f), new PointF(0.0f, 0.0f), new PointF(1.0f, 1.0f)}, new PointF[]{new PointF(0.0f, 0.0f), new PointF(1.0f, 1.0f), new PointF(1.0f, 0.0f), new PointF(0.0f, 1.0f)}, new PointF[]{new PointF(1.0f, 0.0f), new PointF(0.0f, 1.0f), new PointF(1.0f, 1.0f), new PointF(0.0f, 0.0f)}, new PointF[]{new PointF(1.0f, 1.0f), new PointF(0.0f, 0.0f), new PointF(0.0f, 1.0f), new PointF(1.0f, 0.0f)}}[id];
        ArrayList<Float> arrayList = buffer;
        CollectionsKt.addAll(arrayList, new Float[]{Float.valueOf(rect.left), Float.valueOf(rect.top), Float.valueOf(pointFArr[0].x), Float.valueOf(pointFArr[0].y)});
        CollectionsKt.addAll(arrayList, new Float[]{Float.valueOf(rect.right), Float.valueOf(rect.bottom), Float.valueOf(pointFArr[1].x), Float.valueOf(pointFArr[1].y)});
        CollectionsKt.addAll(arrayList, new Float[]{Float.valueOf(rect.left), Float.valueOf(rect.bottom), Float.valueOf(pointFArr[2].x), Float.valueOf(pointFArr[2].y)});
        CollectionsKt.addAll(arrayList, new Float[]{Float.valueOf(rect.left), Float.valueOf(rect.top), Float.valueOf(pointFArr[0].x), Float.valueOf(pointFArr[0].y)});
        CollectionsKt.addAll(arrayList, new Float[]{Float.valueOf(rect.right), Float.valueOf(rect.top), Float.valueOf(pointFArr[3].x), Float.valueOf(pointFArr[3].y)});
        CollectionsKt.addAll(arrayList, new Float[]{Float.valueOf(rect.right), Float.valueOf(rect.bottom), Float.valueOf(pointFArr[1].x), Float.valueOf(pointFArr[1].y)});
    }

    private final float dpToPx(float dp) {
        return dp * getCurveEngine().getPenEngine().mContext.getResources().getDisplayMetrics().density;
    }
}
