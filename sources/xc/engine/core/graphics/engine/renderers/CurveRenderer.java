package xc.engine.core.graphics.engine.renderers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.onetrack.util.a;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.R;
import xc.engine.cbridge.MiCurveNative;
import xc.engine.core.graphics.engine.core.BaseStruct;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.opengl.GLRenderUtil;
import xc.engine.core.graphics.opengl.TextureResHelper;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.CsPoint;
import xc.engine.data.pagedata.DataCurve;

/* JADX INFO: compiled from: CurveRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0019\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 T2\u00020\u0001:\u0001TB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0014J\b\u0010\u0016\u001a\u00020\u0007H\u0014J\b\u0010\u0017\u001a\u00020\nH\u0002J\b\u0010\u0018\u001a\u00020\nH\u0002J\b\u0010\u0019\u001a\u00020\nH\u0002J\b\u0010\u001a\u001a\u00020\nH\u0002J\b\u0010\u001b\u001a\u00020\nH\u0002J\b\u0010\u001c\u001a\u00020\nH\u0002J\b\u0010\u001d\u001a\u00020\nH\u0002J\b\u0010\u001e\u001a\u00020\nH\u0002J\b\u0010\u001f\u001a\u00020\nH\u0002J\b\u0010 \u001a\u00020\nH\u0002J\b\u0010!\u001a\u00020\nH\u0002J\b\u0010\"\u001a\u00020\nH\u0002J\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0002J\u001e\u0010)\u001a\u00020*2\u0006\u0010%\u001a\u00020&2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020(J2\u0010-\u001a\u00020*2\u0006\u0010%\u001a\u00020&2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020(2\b\b\u0002\u0010.\u001a\u00020\n2\b\b\u0002\u0010/\u001a\u00020\nJ8\u00100\u001a\u00020\u00072\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u00101\u001a\u00020\n2\u0006\u00102\u001a\u00020\n2\u0006\u00103\u001a\u00020\n2\b\b\u0002\u00104\u001a\u000205JL\u00106\u001a\u00020\u00072\u0006\u0010%\u001a\u00020&2\u0006\u00107\u001a\u00020(2\u0006\u00101\u001a\u00020\n2\u0006\u00102\u001a\u00020\n2\u0006\u00103\u001a\u00020\n2\b\b\u0002\u00104\u001a\u0002052\b\b\u0002\u00108\u001a\u00020*2\b\b\u0002\u00109\u001a\u00020:J<\u0010;\u001a\u00020\u00072\u0006\u0010<\u001a\u00020\n2\u0006\u0010%\u001a\u00020&2\u0006\u0010=\u001a\u00020(2\b\b\u0002\u00104\u001a\u0002052\b\b\u0002\u00108\u001a\u00020*2\b\b\u0002\u0010>\u001a\u000205JV\u0010?\u001a\u00020\u00072\u0006\u0010%\u001a\u00020&2\u0006\u0010<\u001a\u00020\n2\u0006\u0010@\u001a\u00020$2\u0006\u0010A\u001a\u00020$2\u0006\u0010B\u001a\u00020C2\b\b\u0002\u00104\u001a\u0002052\b\b\u0002\u00108\u001a\u00020*2\b\b\u0002\u00109\u001a\u00020:2\b\b\u0002\u0010>\u001a\u000205JB\u0010D\u001a\u00020\u00072\u0006\u0010<\u001a\u00020\n2\u0006\u0010E\u001a\u00020\n2\u0006\u0010B\u001a\u00020C2\u0006\u0010F\u001a\u0002052\u0006\u00107\u001a\u00020(2\b\b\u0002\u0010G\u001a\u0002052\b\b\u0002\u0010H\u001a\u00020\nJ(\u0010I\u001a\u0002052\u0006\u0010%\u001a\u00020&2\u0006\u0010J\u001a\u0002052\u0006\u0010K\u001a\u0002052\b\b\u0002\u00104\u001a\u000205J\u0016\u0010L\u001a\u0002052\u0006\u0010%\u001a\u00020&2\u0006\u0010J\u001a\u000205J0\u0010M\u001a\u00020\u00072\u0006\u0010%\u001a\u00020&2\u0006\u0010N\u001a\u00020O2\u0006\u0010,\u001a\u00020(2\b\b\u0002\u00104\u001a\u0002052\u0006\u0010P\u001a\u000205J\"\u0010Q\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010R\u001a\u00020\n2\b\b\u0002\u0010S\u001a\u00020:H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006U"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/CurveRenderer;", "Lxc/engine/core/graphics/engine/core/BaseStruct;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "appContext", "Landroid/content/Context;", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "pencilProgramId", "", "ballProgramId", "inkProgramId", "markProgramId", "waterColorProgramId", "waterColorBlendProgramId", "alphaBlendProgramId", "hoverBrushProgramId", "pencilBrushTextureId", "markBrushTextureId", "waterColorBrushTextureId", "waterColorBlendTextureId", "onDestroy", "getPencilProgramId", "getBallProgramId", "getInkProgramId", "getMarkProgramId", "getWaterColorProgramId", "getWaterColorBlendProgramId", "getAlphaBlendProgramId", "getHoverBrushProgramId", "getMarkBrushTextureId", "getPencilBrushTextureId", "getWaterColorBrushTextureId", "getWaterColorBlendTextureId", "getNativeCurveParamArray", "", "curve", "Lxc/engine/data/pagedata/DataCurve;", "worldArea", "Landroid/graphics/RectF;", "nativeGenerateVertexHandle", "", "curveId", a.e, "nativeGenerateVertexHandleRanged", "startVertex", "vertexCount", "nativeRenderSingleCurve", "finalFramebufferId", "tmpFramebufferId", "tmpTextureId", "sizeScale", "", "renderCurve", "viewportRect", "nativeVertHandle", "reuseNativeHandle", "", "renderMathCurve", "framebufferId", "range", "extraPenSize", "drawCurve", "matrix", "color", "penType", "Lxc/engine/core/graphics/pen/PenType;", "blendCurve", "blendTextureId", "alpha", "screenFixAngle", "invertY", "getBrushSize", "pressure", "tilt", "getBrushAlpha", "drawBrush", "point", "Lxc/engine/data/CsPoint;", "postureDegree", "createTextureFromResource", "resId", "repeat", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CurveRenderer extends BaseStruct {
    public static final int SINGLE_CURVE_MAX_POINTS = 524288;
    private int alphaBlendProgramId;
    private Context appContext;
    private int ballProgramId;
    private int hoverBrushProgramId;
    private int inkProgramId;
    private int markBrushTextureId;
    private int markProgramId;
    private int pencilBrushTextureId;
    private int pencilProgramId;
    private int waterColorBlendProgramId;
    private int waterColorBlendTextureId;
    private int waterColorBrushTextureId;
    private int waterColorProgramId;
    private static final String TAG = "CurveRenderer";

    /* JADX INFO: compiled from: CurveRenderer.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    public static final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PenType.values().length];
            try {
                iArr[PenType.INK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PenType.BALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PenType.PENCIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PenType.MARK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[PenType.WATER_COLOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Context applicationContext = context.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.appContext = applicationContext;
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLUtils.INSTANCE.deleteGLProgram(this.pencilProgramId);
        GLUtils.INSTANCE.deleteGLProgram(this.ballProgramId);
        GLUtils.INSTANCE.deleteGLProgram(this.inkProgramId);
        GLUtils.INSTANCE.deleteGLProgram(this.markProgramId);
        GLUtils.INSTANCE.deleteGLProgram(this.waterColorProgramId);
        GLUtils.INSTANCE.deleteGLProgram(this.waterColorBlendProgramId);
        GLUtils.INSTANCE.deleteGLProgram(this.alphaBlendProgramId);
        GLUtils.INSTANCE.deleteGLProgram(this.hoverBrushProgramId);
        GLUtils.INSTANCE.deleteTextures(ArraysKt.toList(new Integer[]{Integer.valueOf(this.pencilBrushTextureId), Integer.valueOf(this.markBrushTextureId), Integer.valueOf(this.waterColorBrushTextureId), Integer.valueOf(this.waterColorBlendTextureId)}));
    }

    private final int getPencilProgramId() {
        if (this.pencilProgramId <= 0) {
            this.pencilProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(203, 203);
        }
        return this.pencilProgramId;
    }

    private final int getBallProgramId() {
        if (this.ballProgramId <= 0) {
            this.ballProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(204, 204);
        }
        return this.ballProgramId;
    }

    private final int getInkProgramId() {
        if (this.inkProgramId <= 0) {
            this.inkProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(205, 205);
        }
        return this.inkProgramId;
    }

    private final int getMarkProgramId() {
        if (this.markProgramId <= 0) {
            this.markProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(206, 206);
        }
        return this.markProgramId;
    }

    private final int getWaterColorProgramId() {
        if (this.waterColorProgramId <= 0) {
            this.waterColorProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(207, 207);
        }
        return this.waterColorProgramId;
    }

    private final int getWaterColorBlendProgramId() {
        if (this.waterColorBlendProgramId <= 0) {
            this.waterColorBlendProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(210, 208);
        }
        return this.waterColorBlendProgramId;
    }

    private final int getAlphaBlendProgramId() {
        if (this.alphaBlendProgramId <= 0) {
            this.alphaBlendProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(210, 210);
        }
        return this.alphaBlendProgramId;
    }

    private final int getHoverBrushProgramId() {
        if (this.hoverBrushProgramId <= 0) {
            this.hoverBrushProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(2029, 2029);
        }
        return this.hoverBrushProgramId;
    }

    private final int getMarkBrushTextureId() {
        CurveRenderer curveRenderer;
        if (this.markBrushTextureId <= 0) {
            Context context = this.appContext;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appContext");
                context = null;
            }
            curveRenderer = this;
            curveRenderer.markBrushTextureId = createTextureFromResource$default(curveRenderer, context, R.drawable.brush_mark, false, 4, null);
        } else {
            curveRenderer = this;
        }
        return curveRenderer.markBrushTextureId;
    }

    private final int getPencilBrushTextureId() {
        CurveRenderer curveRenderer;
        if (this.pencilBrushTextureId <= 0) {
            Context context = this.appContext;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appContext");
                context = null;
            }
            curveRenderer = this;
            curveRenderer.pencilBrushTextureId = createTextureFromResource$default(curveRenderer, context, R.drawable.brush_pencil, false, 4, null);
            GLUtils.INSTANCE.generateMipmap(curveRenderer.pencilBrushTextureId);
        } else {
            curveRenderer = this;
        }
        return curveRenderer.pencilBrushTextureId;
    }

    private final int getWaterColorBrushTextureId() {
        CurveRenderer curveRenderer;
        if (this.waterColorBrushTextureId <= 0) {
            Context context = this.appContext;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appContext");
                context = null;
            }
            curveRenderer = this;
            curveRenderer.waterColorBrushTextureId = createTextureFromResource$default(curveRenderer, context, R.drawable.brush_watercolor, false, 4, null);
        } else {
            curveRenderer = this;
        }
        return curveRenderer.waterColorBrushTextureId;
    }

    private final int getWaterColorBlendTextureId() {
        if (this.waterColorBlendTextureId <= 0) {
            Context context = this.appContext;
            if (context == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appContext");
                context = null;
            }
            this.waterColorBlendTextureId = createTextureFromResource(context, R.drawable.brush_watercolor_bg, true);
        }
        return this.waterColorBlendTextureId;
    }

    private final float[] getNativeCurveParamArray(DataCurve curve, RectF worldArea) {
        return new float[]{curve.getCurveProp().getPenType().getValue(), curve.getCurveProp().getPenSize(), curve.getDrawRatio(), 0.0f, worldArea.left, worldArea.top, worldArea.right, worldArea.bottom, curve.getSensitivity(), curve.getTipSize()};
    }

    public final long nativeGenerateVertexHandle(DataCurve curve, long curveId, RectF area) {
        long jNativeGenCurveVertexBuffer;
        Intrinsics.checkNotNullParameter(curve, "curve");
        Intrinsics.checkNotNullParameter(area, "area");
        Object mLockObj = curve.mLockObj;
        Intrinsics.checkNotNullExpressionValue(mLockObj, "mLockObj");
        synchronized (mLockObj) {
            jNativeGenCurveVertexBuffer = MiCurveNative.nativeGenCurveVertexBuffer(curveId, getNativeCurveParamArray(curve, area), 0L, false);
            Unit unit = Unit.INSTANCE;
        }
        return jNativeGenCurveVertexBuffer;
    }

    public static /* synthetic */ long nativeGenerateVertexHandleRanged$default(CurveRenderer curveRenderer, DataCurve dataCurve, long j, RectF rectF, int i, int i2, int i3, Object obj) {
        if ((i3 & 8) != 0) {
            i = 0;
        }
        int i4 = i;
        if ((i3 & 16) != 0) {
            i2 = -1;
        }
        return curveRenderer.nativeGenerateVertexHandleRanged(dataCurve, j, rectF, i4, i2);
    }

    public final long nativeGenerateVertexHandleRanged(DataCurve curve, long curveId, RectF area, int startVertex, int vertexCount) {
        long jNativeGenCurveVertexBufferRanged;
        Intrinsics.checkNotNullParameter(curve, "curve");
        Intrinsics.checkNotNullParameter(area, "area");
        Object mLockObj = curve.mLockObj;
        Intrinsics.checkNotNullExpressionValue(mLockObj, "mLockObj");
        synchronized (mLockObj) {
            jNativeGenCurveVertexBufferRanged = MiCurveNative.nativeGenCurveVertexBufferRanged(curveId, getNativeCurveParamArray(curve, area), startVertex, vertexCount);
            Unit unit = Unit.INSTANCE;
        }
        return jNativeGenCurveVertexBufferRanged;
    }

    public static /* synthetic */ void nativeRenderSingleCurve$default(CurveRenderer curveRenderer, DataCurve dataCurve, RectF rectF, int i, int i2, int i3, float f, int i4, Object obj) {
        if ((i4 & 32) != 0) {
            f = 1.0f;
        }
        curveRenderer.nativeRenderSingleCurve(dataCurve, rectF, i, i2, i3, f);
    }

    public final void nativeRenderSingleCurve(DataCurve curve, RectF worldArea, int finalFramebufferId, int tmpFramebufferId, int tmpTextureId, float sizeScale) {
        Intrinsics.checkNotNullParameter(curve, "curve");
        Intrinsics.checkNotNullParameter(worldArea, "worldArea");
        renderCurve$default(this, curve, worldArea, finalFramebufferId, tmpFramebufferId, tmpTextureId, sizeScale, nativeGenerateVertexHandle(curve, curve.mOriginPoints, worldArea), false, 128, null);
    }

    public static /* synthetic */ void renderCurve$default(CurveRenderer curveRenderer, DataCurve dataCurve, RectF rectF, int i, int i2, int i3, float f, long j, boolean z, int i4, Object obj) {
        curveRenderer.renderCurve(dataCurve, rectF, i, i2, i3, (i4 & 32) != 0 ? 1.0f : f, (i4 & 64) != 0 ? 0L : j, (i4 & 128) != 0 ? false : z);
    }

    public final void renderCurve(DataCurve curve, RectF viewportRect, int finalFramebufferId, int tmpFramebufferId, int tmpTextureId, float sizeScale, long nativeVertHandle, boolean reuseNativeHandle) {
        Intrinsics.checkNotNullParameter(curve, "curve");
        Intrinsics.checkNotNullParameter(viewportRect, "viewportRect");
        if (curve.mDataState == 3) {
            return;
        }
        PenType penType = curve.mCurveProp.getPenType();
        float alpha = curve.getAlpha() / 255.0f;
        float[] colorFromInt = MiMathUtils.INSTANCE.getColorFromInt(curve.getCurveColor());
        colorFromInt[3] = alpha;
        float[] fArrCalculateMatrix$default = MiMathUtils.Companion.calculateMatrix$default(MiMathUtils.INSTANCE, viewportRect.left, viewportRect.right, viewportRect.top, viewportRect.bottom, 0.0f, 0.0f, 48, null);
        if (penType == PenType.WATER_COLOR || (alpha < 1.0f && penType != PenType.PENCIL)) {
            GLES30.glBindFramebuffer(36160, tmpFramebufferId);
            GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES30.glClear(16384);
            Intrinsics.checkNotNull(penType);
            drawCurve$default(this, curve, tmpFramebufferId, fArrCalculateMatrix$default, colorFromInt, penType, sizeScale, nativeVertHandle, reuseNativeHandle, 0.0f, 256, null);
            blendCurve$default(this, finalFramebufferId, tmpTextureId, penType, alpha, viewportRect, 0.0f, 0, 96, null);
            return;
        }
        Intrinsics.checkNotNull(penType);
        drawCurve$default(this, curve, finalFramebufferId, fArrCalculateMatrix$default, colorFromInt, penType, sizeScale, nativeVertHandle, reuseNativeHandle, 0.0f, 256, null);
    }

    public static /* synthetic */ void renderMathCurve$default(CurveRenderer curveRenderer, int i, DataCurve dataCurve, RectF rectF, float f, long j, float f2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            f = 1.0f;
        }
        float f3 = f;
        if ((i2 & 16) != 0) {
            j = 0;
        }
        curveRenderer.renderMathCurve(i, dataCurve, rectF, f3, j, (i2 & 32) != 0 ? 0.0f : f2);
    }

    public final void renderMathCurve(int framebufferId, DataCurve curve, RectF range, float sizeScale, long nativeVertHandle, float extraPenSize) {
        Intrinsics.checkNotNullParameter(curve, "curve");
        Intrinsics.checkNotNullParameter(range, "range");
        PenType penType = curve.mCurveProp.getPenType();
        float alpha = curve.getAlpha() / 255.0f;
        float[] colorFromInt = MiMathUtils.INSTANCE.getColorFromInt(curve.getCurveColor());
        colorFromInt[3] = alpha;
        float[] fArrCalculateMatrix$default = MiMathUtils.Companion.calculateMatrix$default(MiMathUtils.INSTANCE, range.left, range.right, range.top, range.bottom, 0.0f, 0.0f, 48, null);
        Intrinsics.checkNotNull(penType);
        drawCurve(curve, framebufferId, fArrCalculateMatrix$default, colorFromInt, penType, sizeScale, nativeVertHandle, false, extraPenSize);
    }

    public static /* synthetic */ void drawCurve$default(CurveRenderer curveRenderer, DataCurve dataCurve, int i, float[] fArr, float[] fArr2, PenType penType, float f, long j, boolean z, float f2, int i2, Object obj) {
        curveRenderer.drawCurve(dataCurve, i, fArr, fArr2, penType, (i2 & 32) != 0 ? 1.0f : f, (i2 & 64) != 0 ? 0L : j, (i2 & 128) != 0 ? false : z, (i2 & 256) != 0 ? 0.0f : f2);
    }

    public final void drawCurve(DataCurve curve, int framebufferId, float[] matrix, float[] color, PenType penType, float sizeScale, long nativeVertHandle, boolean reuseNativeHandle, float extraPenSize) {
        Intrinsics.checkNotNullParameter(curve, "curve");
        Intrinsics.checkNotNullParameter(matrix, "matrix");
        Intrinsics.checkNotNullParameter(color, "color");
        Intrinsics.checkNotNullParameter(penType, "penType");
        if (nativeVertHandle == 0) {
            return;
        }
        GLES30.glBindFramebuffer(36160, framebufferId);
        int i = WhenMappings.$EnumSwitchMapping$0[penType.ordinal()];
        if (i == 1) {
            GLES30.glUseProgram(getInkProgramId());
            GLES30.glUniformMatrix4fv(0, 1, false, matrix, 0);
            GLES30.glUniform4f(1, color[0], color[1], color[2], color[3]);
            GLES30.glUniform1f(2, sizeScale);
            GLES30.glUniform1f(3, extraPenSize);
            GLES30.glUniform1f(4, 0.0f);
        } else if (i == 2) {
            GLES30.glUseProgram(getBallProgramId());
            GLES30.glUniformMatrix4fv(0, 1, false, matrix, 0);
            GLES30.glUniform4f(1, color[0], color[1], color[2], color[3]);
            GLES30.glUniform1f(2, sizeScale);
            GLES30.glUniform1f(3, extraPenSize);
        } else if (i == 3) {
            GLES30.glUseProgram(getPencilProgramId());
            GLES30.glUniformMatrix4fv(0, 1, false, matrix, 0);
            GLES30.glUniform4f(1, color[0], color[1], color[2], color[3]);
            GLES30.glUniform1f(2, sizeScale);
            GLES30.glUniform1f(3, extraPenSize);
            GLES30.glActiveTexture(33985);
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, getPencilBrushTextureId());
            GLES30.glUniform1i(4, 1);
        } else if (i == 4) {
            GLES30.glUseProgram(getMarkProgramId());
            GLES30.glUniformMatrix4fv(0, 1, false, matrix, 0);
            GLES30.glUniform4f(1, color[0], color[1], color[2], color[3]);
            GLES30.glUniform1f(2, sizeScale);
            GLES30.glActiveTexture(33985);
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, getMarkBrushTextureId());
            GLES30.glUniform1i(3, 1);
            GLES30.glUniform1f(4, curve.mRotateDegrees);
        } else {
            if (i != 5) {
                throw new NoWhenBranchMatchedException();
            }
            GLES30.glUseProgram(getWaterColorProgramId());
            GLES30.glUniformMatrix4fv(0, 1, false, matrix, 0);
            GLES30.glUniform4f(1, color[0], color[1], color[2], color[3]);
            GLES30.glUniform1f(2, sizeScale);
            GLES30.glActiveTexture(33985);
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, getWaterColorBrushTextureId());
            GLES30.glUniform1i(3, 1);
        }
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        if (penType == PenType.WATER_COLOR) {
            GLES30.glBlendEquationSeparate(32774, 32776);
            GLES30.glBlendFuncSeparate(1, 0, 1, 1);
        } else {
            GLES30.glBlendEquationSeparate(32774, 32774);
            GLES30.glBlendFuncSeparate(R2.attr.colorPrimaryFixedDim, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        }
        if (reuseNativeHandle) {
            MiCurveNative.nativeDrawVertexBufferReuse(nativeVertHandle);
        } else {
            MiCurveNative.nativeDrawVertexBuffer(nativeVertHandle);
        }
    }

    public static /* synthetic */ void blendCurve$default(CurveRenderer curveRenderer, int i, int i2, PenType penType, float f, RectF rectF, float f2, int i3, int i4, Object obj) {
        if ((i4 & 32) != 0) {
            f2 = 0.0f;
        }
        curveRenderer.blendCurve(i, i2, penType, f, rectF, f2, (i4 & 64) != 0 ? 0 : i3);
    }

    public final void blendCurve(int framebufferId, int blendTextureId, PenType penType, float alpha, RectF viewportRect, float screenFixAngle, int invertY) {
        Intrinsics.checkNotNullParameter(penType, "penType");
        Intrinsics.checkNotNullParameter(viewportRect, "viewportRect");
        GLES30.glBindFramebuffer(36160, framebufferId);
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(R2.attr.colorPrimaryFixedDim, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        if (WhenMappings.$EnumSwitchMapping$0[penType.ordinal()] == 5) {
            GLES30.glUseProgram(getWaterColorBlendProgramId());
        } else {
            GLES30.glUseProgram(getAlphaBlendProgramId());
        }
        GLES30.glActiveTexture(33985);
        GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, blendTextureId);
        GLES30.glUniform1i(0, 1);
        GLES30.glUniform1f(1, alpha);
        GLES30.glUniform1i(4, invertY);
        GLES30.glUniform1f(5, screenFixAngle);
        if (penType == PenType.WATER_COLOR) {
            GLES30.glActiveTexture(33986);
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, getWaterColorBlendTextureId());
            GLES30.glUniform1i(2, 2);
            if (invertY == 0) {
                GLES30.glUniform4f(3, viewportRect.left, viewportRect.top, viewportRect.right, viewportRect.bottom);
            } else {
                GLES30.glUniform4f(3, viewportRect.left, viewportRect.bottom, viewportRect.right, viewportRect.top);
            }
        }
        GLES30.glDrawArrays(5, 0, 4);
    }

    public static /* synthetic */ float getBrushSize$default(CurveRenderer curveRenderer, DataCurve dataCurve, float f, float f2, float f3, int i, Object obj) {
        if ((i & 8) != 0) {
            f3 = 1.0f;
        }
        return curveRenderer.getBrushSize(dataCurve, f, f2, f3);
    }

    public final float getBrushSize(DataCurve curve, float pressure, float tilt, float sizeScale) {
        Intrinsics.checkNotNullParameter(curve, "curve");
        return MiCurveNative.nativeCalculateBrushSize(getNativeCurveParamArray(curve, new RectF()), pressure, tilt) * sizeScale;
    }

    public final float getBrushAlpha(DataCurve curve, float pressure) {
        Intrinsics.checkNotNullParameter(curve, "curve");
        return MiCurveNative.nativeCalculateBrushAlpha(getNativeCurveParamArray(curve, new RectF()), pressure);
    }

    public static /* synthetic */ void drawBrush$default(CurveRenderer curveRenderer, DataCurve dataCurve, CsPoint csPoint, RectF rectF, float f, float f2, int i, Object obj) {
        if ((i & 8) != 0) {
            f = 1.0f;
        }
        curveRenderer.drawBrush(dataCurve, csPoint, rectF, f, f2);
    }

    public final void drawBrush(DataCurve curve, CsPoint point, RectF area, float sizeScale, float postureDegree) {
        Intrinsics.checkNotNullParameter(curve, "curve");
        Intrinsics.checkNotNullParameter(point, "point");
        Intrinsics.checkNotNullParameter(area, "area");
        float brushSize = getBrushSize(curve, point.getPressure(), point.getTilt(), sizeScale);
        float brushAlpha = getBrushAlpha(curve, point.getPressure());
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        if (curve.mCurveProp.getPenType() == PenType.WATER_COLOR) {
            GLES30.glBlendEquationSeparate(32774, 32776);
            GLES30.glBlendFuncSeparate(1, 0, 1, 1);
        } else {
            GLES30.glBlendEquationSeparate(32774, 32774);
            GLES30.glBlendFuncSeparate(R2.attr.colorPrimaryFixedDim, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        }
        float[] colorFromInt = MiMathUtils.INSTANCE.getColorFromInt(curve.getCurveColor());
        float fRemapRange$default = MiMathUtils.Companion.remapRange$default(MiMathUtils.INSTANCE, point.x, area.left, area.right, 0.0f, 0.0f, 24, null);
        float fRemapRange$default2 = MiMathUtils.Companion.remapRange$default(MiMathUtils.INSTANCE, point.y, area.bottom, area.top, 0.0f, 0.0f, 24, null);
        GLES30.glUseProgram(getHoverBrushProgramId());
        GLES30.glUniform4f(0, fRemapRange$default, fRemapRange$default2, brushSize, brushAlpha);
        GLES30.glUniform4f(1, colorFromInt[0], colorFromInt[1], colorFromInt[2], colorFromInt[3]);
        GLES30.glActiveTexture(33988);
        PenType penType = curve.mCurveProp.getPenType();
        int i = penType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[penType.ordinal()];
        if (i == 3) {
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, getPencilBrushTextureId());
        } else if (i == 4) {
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, getMarkBrushTextureId());
        } else if (i == 5) {
            GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, getWaterColorBrushTextureId());
        }
        GLES30.glUniform1i(4, 4);
        GLES30.glUniform1i(5, curve.mCurveProp.getPenType().getValue());
        GLES30.glUniform1f(6, curve.mCurveProp.getPenType() == PenType.MARK ? MiMathUtils.INSTANCE.lerp(0.5f, 1.25f, point.getTilt()) : 1.0f);
        GLES30.glUniform1f(7, postureDegree);
        GLES30.glDrawArrays(0, 0, 1);
    }

    static /* synthetic */ int createTextureFromResource$default(CurveRenderer curveRenderer, Context context, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return curveRenderer.createTextureFromResource(context, i, z);
    }

    private final int createTextureFromResource(Context context, int resId, boolean repeat) {
        int iCreateTexture2;
        Bitmap bitmapFromDrawable = TextureResHelper.getBitmapFromDrawable(context, resId);
        if (repeat) {
            iCreateTexture2 = GLRenderUtil.createTexture(bitmapFromDrawable);
        } else {
            iCreateTexture2 = GLRenderUtil.createTexture2(bitmapFromDrawable);
        }
        if (bitmapFromDrawable != null && !bitmapFromDrawable.isRecycled()) {
            bitmapFromDrawable.recycle();
        }
        if (iCreateTexture2 <= 0) {
            Log.e(TAG, "xl-- createTextureFromResource return invalid texture id.");
        }
        return iCreateTexture2;
    }
}
