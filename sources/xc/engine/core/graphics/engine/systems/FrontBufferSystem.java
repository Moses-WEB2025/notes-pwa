package xc.engine.core.graphics.engine.systems;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import androidx.graphics.lowlatency.BufferInfo;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.cbridge.MiCurveNative;
import xc.engine.core.fast.data.CurveSegment;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.TileRenderer;
import xc.engine.core.graphics.engine.core.BaseEngineStruct;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.core.ScreenTexture;
import xc.engine.core.graphics.engine.core.TexturePool;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.CurveRenderer;
import xc.engine.core.graphics.engine.renderers.ShapeRenderer;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CsPoint;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: compiled from: FrontBufferSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 *2\u00020\u0001:\u0002*+B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0012H\u0014J\b\u0010\u0016\u001a\u00020\u0012H\u0016J\u0006\u0010\u0017\u001a\u00020\u0012J\u0006\u0010\u0018\u001a\u00020\u0012J\u0006\u0010\u0019\u001a\u00020\u0012J\u0006\u0010\u001a\u001a\u00020\u0012J&\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#J\u001e\u0010$\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u001fJ\u000e\u0010(\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001dJ0\u0010)\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lxc/engine/core/graphics/engine/systems/FrontBufferSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineStruct;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "curveRenderer", "Lxc/engine/core/graphics/engine/renderers/CurveRenderer;", "tileRenderer", "Lxc/engine/core/graphics/engine/TileRenderer;", "shapeRenderer", "Lxc/engine/core/graphics/engine/renderers/ShapeRenderer;", "renderRes", "Lxc/engine/core/graphics/engine/systems/FrontBufferSystem$FrontBufferRenderResource;", "bDrawing", "", "bFinishingDrawing", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "onScreenResize", "release", "onDown", "onCancel", "onUp", "drawEraser", "bufferInfo", "Landroidx/graphics/lowlatency/BufferInfo;", "param", "Lxc/engine/core/fast/data/CurveSegment;", "eraserSize", "", "bgColor", "Lxc/engine/core/graphics/engine/core/Color;", "drawCurve", "dataCurve", "Lxc/engine/data/pagedata/DataCurve;", "inParam", "clean", "drawHoverBrush", "Companion", "FrontBufferRenderResource", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FrontBufferSystem extends BaseEngineStruct {
    private static final String TAG = "FrontBufferSystem";
    private boolean bDrawing;
    private boolean bFinishingDrawing;
    private final CurveRenderer curveRenderer;
    private final FrontBufferRenderResource renderRes;
    private final ShapeRenderer shapeRenderer;
    private final TileRenderer tileRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FrontBufferSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.curveRenderer = engine.getCurveRenderer();
        this.tileRenderer = engine.getTileRenderer();
        this.shapeRenderer = engine.getShapeRenderer();
        this.renderRes = new FrontBufferRenderResource(getCurveEngine());
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.renderRes.init();
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        this.renderRes.destroy();
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineStruct
    public void onScreenResize() {
        this.renderRes.destroy();
    }

    public final void release() {
        if (this.bDrawing) {
            return;
        }
        this.renderRes.release();
        this.bFinishingDrawing = false;
    }

    public final void onDown() {
        Log.d(TAG, "xl-- [Front] onDown");
        this.bDrawing = true;
    }

    public final void onCancel() {
        Log.d(TAG, "xl-- [Front] onCancel");
        this.bDrawing = false;
        this.renderRes.release();
    }

    public final void onUp() {
        Log.d(TAG, "xl-- [Front] onUp");
        this.bDrawing = false;
        this.bFinishingDrawing = true;
    }

    public final void drawEraser(BufferInfo bufferInfo, CurveSegment param, float eraserSize, Color bgColor) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        Intrinsics.checkNotNullParameter(param, "param");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        if (!this.renderRes.getBIsPrepared()) {
            this.renderRes.prepare();
        }
        GLES30.glViewport(0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
        RectF screenAreaWS = getCurveEngine().getScreenAreaWS();
        GLES30.glBindFramebuffer(36160, this.renderRes.getTmpFramebufferId());
        GLES30.glClear(16384);
        CsPoint hoverPoint = param.getHoverPoint();
        Intrinsics.checkNotNull(hoverPoint);
        float f = hoverPoint.x;
        float f2 = param.getHoverPoint().y;
        float pressure = eraserSize + (1.15f * eraserSize * param.getHoverPoint().getPressure());
        RectF rectF = new RectF(0.0f, 0.0f, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
        float screenWidthInPixel = getCurveEngine().getScreenWidthInPixel() / screenAreaWS.width();
        int i = ((int) (0.7f * pressure * screenWidthInPixel)) + 4;
        int iMax = Math.max(0, ((int) f) - i);
        int iMax2 = Math.max(0, (getCurveEngine().getScreenHeightInPixel() - ((int) f2)) - i);
        GLES30.glEnable(R2.color.common_folder_dialog_bg);
        int i2 = i * 2;
        GLES30.glScissor(iMax, iMax2, i2, i2);
        this.shapeRenderer.render(rectF, f, f2, pressure, screenWidthInPixel, getCurveEngine().getDrawRatio(), bgColor);
        GLES30.glDisable(R2.color.common_folder_dialog_bg);
        GLES30.glViewport(0, 0, bufferInfo.getWidth(), bufferInfo.getHeight());
        GLES30.glBindFramebuffer(36160, bufferInfo.getFrameBufferId());
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        this.tileRenderer.drawFullScreen(this.renderRes.getTmpTexture().getTexId(), false, false, getCurveEngine().getFrontBufferFixAngle());
    }

    public final void drawCurve(BufferInfo bufferInfo, DataCurve dataCurve, CurveSegment inParam) {
        RectF rectF;
        float[] fArr;
        float[] fArr2;
        String str;
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        Intrinsics.checkNotNullParameter(dataCurve, "dataCurve");
        Intrinsics.checkNotNullParameter(inParam, "inParam");
        if (this.bDrawing) {
            if (!this.renderRes.getBIsPrepared()) {
                this.renderRes.prepare();
            }
            GLES30.glViewport(0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
            RectF screenAreaWS = getCurveEngine().getScreenAreaWS();
            RectF screenAreaSS = getCurveEngine().getScreenAreaSS();
            float[] fArrCalculateMatrix$default = MiMathUtils.Companion.calculateMatrix$default(MiMathUtils.INSTANCE, screenAreaSS.left, screenAreaSS.right, screenAreaSS.top, screenAreaSS.bottom, 0.0f, 0.0f, 48, null);
            float[] colorFromInt = MiMathUtils.INSTANCE.getColorFromInt(getCurveEngine().getPenEngine().mCanvasOperator.getCurCurveColor(dataCurve));
            colorFromInt[3] = dataCurve.getAlpha() / 255.0f;
            float[] colorFromInt2 = MiMathUtils.INSTANCE.getColorFromInt(PenEngineUtils.g(dataCurve.getCurveColor()));
            if (inParam.getDrawingCurve() == 0 || inParam.getDrawingCount() <= 0) {
                rectF = screenAreaSS;
                fArr = fArrCalculateMatrix$default;
                fArr2 = colorFromInt;
                str = "getPenType(...)";
            } else {
                long jNativeGenerateVertexHandleRanged = this.curveRenderer.nativeGenerateVertexHandleRanged(dataCurve, inParam.getDrawingCurve(), screenAreaSS, inParam.getDrawingOffset(), inParam.getDrawingCount());
                CurveRenderer curveRenderer = this.curveRenderer;
                int tmpFramebufferId = this.renderRes.getTmpFramebufferId();
                PenType penType = dataCurve.mCurveProp.getPenType();
                Intrinsics.checkNotNullExpressionValue(penType, "getPenType(...)");
                fArr = fArrCalculateMatrix$default;
                fArr2 = colorFromInt;
                rectF = screenAreaSS;
                str = "getPenType(...)";
                CurveRenderer.drawCurve$default(curveRenderer, dataCurve, tmpFramebufferId, fArr, fArr2, penType, getCurveEngine().getScaleRatio(), jNativeGenerateVertexHandleRanged, false, 0.0f, R2.attr.alignItems, null);
            }
            GLES30.glBindFramebuffer(36160, this.renderRes.getTmpFramebufferId());
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, this.renderRes.getTmpWithPredictTexture().getTexId());
            GLES30.glCopyTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, 0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel(), 0);
            if (inParam.getEstimatedCurve() != 0) {
                if (MiCurveNative.size(inParam.getEstimatedCurve()) > 0) {
                    long jNativeGenerateVertexHandle = this.curveRenderer.nativeGenerateVertexHandle(dataCurve, inParam.getEstimatedCurve(), rectF);
                    CurveRenderer curveRenderer2 = this.curveRenderer;
                    int tmpWithPredictFramebufferId = this.renderRes.getTmpWithPredictFramebufferId();
                    PenType penType2 = dataCurve.mCurveProp.getPenType();
                    Intrinsics.checkNotNullExpressionValue(penType2, str);
                    CurveRenderer.drawCurve$default(curveRenderer2, dataCurve, tmpWithPredictFramebufferId, fArr, fArr2, penType2, getCurveEngine().getScaleRatio(), jNativeGenerateVertexHandle, false, 0.0f, R2.attr.alignItems, null);
                }
                MiCurveNative.releaseLine(inParam.getEstimatedCurve());
            }
            if (inParam.getShapeCurve() != 0 && MiCurveNative.size(inParam.getShapeCurve()) > 0) {
                long jNativeGenerateVertexHandle2 = this.curveRenderer.nativeGenerateVertexHandle(dataCurve, inParam.getShapeCurve(), rectF);
                CurveRenderer curveRenderer3 = this.curveRenderer;
                int tmpWithPredictFramebufferId2 = this.renderRes.getTmpWithPredictFramebufferId();
                PenType penType3 = dataCurve.mCurveProp.getPenType();
                Intrinsics.checkNotNullExpressionValue(penType3, str);
                CurveRenderer.drawCurve$default(curveRenderer3, dataCurve, tmpWithPredictFramebufferId2, fArr, colorFromInt2, penType3, getCurveEngine().getScaleRatio(), jNativeGenerateVertexHandle2, false, 0.0f, R2.attr.alignItems, null);
            }
            GLES30.glViewport(0, 0, bufferInfo.getWidth(), bufferInfo.getHeight());
            GLES30.glBindFramebuffer(36160, bufferInfo.getFrameBufferId());
            GLES30.glClear(16384);
            float frontBufferFixAngle = getCurveEngine().getFrontBufferFixAngle();
            float alpha = dataCurve.mCurveProp.getPenType() == PenType.PENCIL ? 1.0f : dataCurve.getAlpha() / 255.0f;
            CurveRenderer curveRenderer4 = this.curveRenderer;
            int frameBufferId = bufferInfo.getFrameBufferId();
            int texId = this.renderRes.getTmpWithPredictTexture().getTexId();
            PenType penType4 = dataCurve.mCurveProp.getPenType();
            Intrinsics.checkNotNullExpressionValue(penType4, str);
            curveRenderer4.blendCurve(frameBufferId, texId, penType4, alpha, screenAreaWS, frontBufferFixAngle, 1);
        }
    }

    public final void clean(BufferInfo bufferInfo) {
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        GLES30.glBindFramebuffer(36160, this.renderRes.getTmpFramebufferId());
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        GLES30.glBindFramebuffer(36160, bufferInfo.getFrameBufferId());
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void drawHoverBrush(BufferInfo bufferInfo, DataCurve dataCurve, CurveSegment inParam, float eraserSize, Color bgColor) {
        float f;
        ?? r11;
        int i;
        Intrinsics.checkNotNullParameter(bufferInfo, "bufferInfo");
        Intrinsics.checkNotNullParameter(inParam, "inParam");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        RectF screenAreaWS = getCurveEngine().getScreenAreaWS();
        if (this.bFinishingDrawing && this.renderRes.getBIsPrepared()) {
            GLES30.glViewport(0, 0, bufferInfo.getWidth(), bufferInfo.getHeight());
            GLES30.glBindFramebuffer(36160, bufferInfo.getFrameBufferId());
            GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES30.glClear(16384);
            float frontBufferFixAngle = getCurveEngine().getFrontBufferFixAngle();
            CurveRenderer curveRenderer = this.curveRenderer;
            int frameBufferId = bufferInfo.getFrameBufferId();
            int texId = this.renderRes.getTmpWithPredictTexture().getTexId();
            Intrinsics.checkNotNull(dataCurve);
            PenType penType = dataCurve.mCurveProp.getPenType();
            Intrinsics.checkNotNullExpressionValue(penType, "getPenType(...)");
            curveRenderer.blendCurve(frameBufferId, texId, penType, dataCurve.getAlpha() / 255.0f, screenAreaWS, frontBufferFixAngle, 1);
            return;
        }
        if (!this.renderRes.getBIsPrepared()) {
            this.renderRes.prepare();
        }
        GLES30.glViewport(0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
        GLES30.glBindFramebuffer(36160, this.renderRes.getTmpFramebufferId());
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        if (inParam.getScenario() == CurveSegment.EScenario.EraserHover) {
            CsPoint hoverPoint = inParam.getHoverPoint();
            Intrinsics.checkNotNull(hoverPoint);
            float f2 = hoverPoint.x;
            float f3 = inParam.getHoverPoint().y;
            RectF rectF = new RectF(0.0f, 0.0f, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
            float screenWidthInPixel = getCurveEngine().getScreenWidthInPixel() / screenAreaWS.width();
            int i2 = ((int) (0.7f * eraserSize * screenWidthInPixel)) + 4;
            int iMax = Math.max(0, ((int) f2) - i2);
            int iMax2 = Math.max(0, (getCurveEngine().getScreenHeightInPixel() - ((int) f3)) - i2);
            GLES30.glEnable(R2.color.common_folder_dialog_bg);
            int i3 = i2 * 2;
            GLES30.glScissor(iMax, iMax2, i3, i3);
            this.shapeRenderer.render(rectF, f2, f3, eraserSize, screenWidthInPixel, getCurveEngine().getDrawRatio(), bgColor);
            GLES30.glDisable(R2.color.common_folder_dialog_bg);
            f = 0.0f;
            r11 = 0;
            i = 36160;
        } else {
            CurveRenderer curveRenderer2 = this.curveRenderer;
            Intrinsics.checkNotNull(dataCurve);
            CsPoint hoverPoint2 = inParam.getHoverPoint();
            Intrinsics.checkNotNull(hoverPoint2);
            f = 0.0f;
            r11 = 0;
            i = 36160;
            curveRenderer2.drawBrush(dataCurve, hoverPoint2, getCurveEngine().getScreenAreaSS(), getCurveEngine().getScaleRatio(), inParam.getPostureDegree());
        }
        GLES30.glViewport(r11, r11, bufferInfo.getWidth(), bufferInfo.getHeight());
        GLES30.glBindFramebuffer(i, bufferInfo.getFrameBufferId());
        GLES30.glClearColor(f, f, f, f);
        GLES30.glClear(16384);
        float frontBufferFixAngle2 = getCurveEngine().getFrontBufferFixAngle();
        if (inParam.getScenario() == CurveSegment.EScenario.EraserHover) {
            this.tileRenderer.drawFullScreen(this.renderRes.getTmpTexture().getTexId(), r11, r11, frontBufferFixAngle2);
            return;
        }
        CurveRenderer curveRenderer3 = this.curveRenderer;
        int frameBufferId2 = bufferInfo.getFrameBufferId();
        int texId2 = this.renderRes.getTmpTexture().getTexId();
        Intrinsics.checkNotNull(dataCurve);
        PenType penType2 = dataCurve.mCurveProp.getPenType();
        Intrinsics.checkNotNullExpressionValue(penType2, "getPenType(...)");
        curveRenderer3.blendCurve(frameBufferId2, texId2, penType2, dataCurve.getAlpha() / 255.0f, screenAreaWS, frontBufferFixAngle2, 1);
    }

    /* JADX INFO: compiled from: FrontBufferSystem.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020!J\u0006\u0010#\u001a\u00020!J\u0006\u0010$\u001a\u00020!R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001a\u0010\u0018\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0012\"\u0004\b\u001a\u0010\u0014R\u001e\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001b\u001a\u00020\u001c@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006%"}, d2 = {"Lxc/engine/core/graphics/engine/systems/FrontBufferSystem$FrontBufferRenderResource;", "", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "curveEngine", "texturePool", "Lxc/engine/core/graphics/engine/core/TexturePool;", "tmpTexture", "Lxc/engine/core/graphics/engine/core/ScreenTexture;", "getTmpTexture", "()Lxc/engine/core/graphics/engine/core/ScreenTexture;", "setTmpTexture", "(Lxc/engine/core/graphics/engine/core/ScreenTexture;)V", "tmpFramebufferId", "", "getTmpFramebufferId", "()I", "setTmpFramebufferId", "(I)V", "tmpWithPredictTexture", "getTmpWithPredictTexture", "setTmpWithPredictTexture", "tmpWithPredictFramebufferId", "getTmpWithPredictFramebufferId", "setTmpWithPredictFramebufferId", "value", "", "bIsPrepared", "getBIsPrepared", "()Z", "init", "", "destroy", "prepare", "release", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class FrontBufferRenderResource {
        private boolean bIsPrepared;
        private final MiCurveEngine curveEngine;
        private final TexturePool texturePool;
        private int tmpFramebufferId;
        private ScreenTexture tmpTexture;
        private int tmpWithPredictFramebufferId;
        private ScreenTexture tmpWithPredictTexture;

        public FrontBufferRenderResource(MiCurveEngine engine) {
            Intrinsics.checkNotNullParameter(engine, "engine");
            this.curveEngine = engine;
            this.texturePool = engine.getTexturePool();
            this.tmpTexture = new ScreenTexture(0, 0, 0, 7, null);
            this.tmpWithPredictTexture = new ScreenTexture(0, 0, 0, 7, null);
        }

        public final ScreenTexture getTmpTexture() {
            return this.tmpTexture;
        }

        public final void setTmpTexture(ScreenTexture screenTexture) {
            Intrinsics.checkNotNullParameter(screenTexture, "<set-?>");
            this.tmpTexture = screenTexture;
        }

        public final int getTmpFramebufferId() {
            return this.tmpFramebufferId;
        }

        public final void setTmpFramebufferId(int i) {
            this.tmpFramebufferId = i;
        }

        public final ScreenTexture getTmpWithPredictTexture() {
            return this.tmpWithPredictTexture;
        }

        public final void setTmpWithPredictTexture(ScreenTexture screenTexture) {
            Intrinsics.checkNotNullParameter(screenTexture, "<set-?>");
            this.tmpWithPredictTexture = screenTexture;
        }

        public final int getTmpWithPredictFramebufferId() {
            return this.tmpWithPredictFramebufferId;
        }

        public final void setTmpWithPredictFramebufferId(int i) {
            this.tmpWithPredictFramebufferId = i;
        }

        public final boolean getBIsPrepared() {
            return this.bIsPrepared;
        }

        public final void init() {
            int[] iArr = new int[2];
            GLES30.glGenFramebuffers(2, iArr, 0);
            this.tmpFramebufferId = iArr[0];
            this.tmpWithPredictFramebufferId = iArr[1];
        }

        public final void destroy() {
            GLUtils.INSTANCE.deleteFramebuffer(this.tmpFramebufferId);
            GLUtils.INSTANCE.deleteFramebuffer(this.tmpWithPredictFramebufferId);
            this.texturePool.recycleScreenTexture(this.tmpTexture);
            this.texturePool.recycleScreenTexture(this.tmpWithPredictTexture);
            this.tmpFramebufferId = 0;
            this.tmpWithPredictFramebufferId = 0;
            this.tmpTexture = new ScreenTexture(0, 0, 0, 7, null);
            this.tmpWithPredictTexture = new ScreenTexture(0, 0, 0, 7, null);
            this.bIsPrepared = false;
        }

        public final void prepare() {
            if (this.bIsPrepared) {
                return;
            }
            if (this.tmpFramebufferId <= 0) {
                init();
            }
            if (this.tmpTexture.isNotValid()) {
                this.tmpTexture = this.curveEngine.getFrontBufferSystem().acquireScreenTexture();
                this.tmpWithPredictTexture = this.curveEngine.getFrontBufferSystem().acquireScreenTexture();
                GLUtils.INSTANCE.bindTextureToFramebuffer(this.tmpFramebufferId, this.tmpTexture.getTexId());
                GLES30.glClear(16384);
                GLUtils.INSTANCE.bindTextureToFramebuffer(this.tmpWithPredictFramebufferId, this.tmpWithPredictTexture.getTexId());
            } else {
                GLES30.glBindFramebuffer(36160, this.tmpFramebufferId);
                GLES30.glClear(16384);
            }
            this.bIsPrepared = true;
        }

        public final void release() {
            if (this.bIsPrepared) {
                Log.d(FrontBufferSystem.TAG, "xl-- [Front] release");
            }
            this.bIsPrepared = false;
        }
    }
}
