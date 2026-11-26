package xc.engine.core.graphics.engine.renderers;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.renderscript.Matrix3f;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.onetrack.util.a;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.core.BaseStruct;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.core.MiMathUtils;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.data.pagedata.DataShape;
import xc.engine.utils.XLog;

/* JADX INFO: compiled from: ShapeRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u0011\u0018\u0000 62\u00020\u0001:\u00016B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u0011H\u0014J:\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\f2\b\b\u0002\u0010\u001c\u001a\u00020\f2\b\b\u0002\u0010\u001d\u001a\u00020\fJ>\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\"\u001a\u00020#JH\u0010$\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010\u001e\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\f2\u0006\u0010!\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\fH\u0002J8\u0010$\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010\u001b\u001a\u00020\fH\u0002J8\u0010)\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010\u001b\u001a\u00020\fH\u0002J8\u0010*\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010\u001b\u001a\u00020\fH\u0002J\u0018\u0010+\u001a\u00020\f2\u0006\u0010,\u001a\u00020\f2\u0006\u0010-\u001a\u00020\fH\u0002J8\u0010.\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010\u001b\u001a\u00020\fH\u0002J8\u0010/\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010\u001b\u001a\u00020\fH\u0002J8\u00100\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010\u001b\u001a\u00020\fH\u0002J\u0010\u00101\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J(\u00102\u001a\u00020\u00112\u0006\u00103\u001a\u00020\f2\u0006\u00104\u001a\u00020\f2\u0006\u00105\u001a\u00020\u001a2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lxc/engine/core/graphics/engine/renderers/ShapeRenderer;", "Lxc/engine/core/graphics/engine/core/BaseStruct;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "circleProgramId", "", "rectProgramId", "triangleProgramId", "starProgramId", "bigArrowProgramId", "arrowProgramId", "offsetX", "", "offsetY", "matrix", "Landroid/renderscript/Matrix3f;", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "render", "shape", "Lxc/engine/data/pagedata/DataShape;", "framebufferId", a.e, "Landroid/graphics/RectF;", "drawRatio", "posOffsetX", "posOffsetY", "centerX", "centerY", "radius", "scaleSize", "bgColor", "Lxc/engine/core/graphics/engine/core/Color;", "drawCircle", "fillColor", "", "strokeColor", "shapeInfo", "drawRect", "drawTriangle", "getTriangleInscribedCircleRadius", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "drawStar", "drawBigArrow", "drawArrow", "printMatrix3f", "updateMatrix", "rotateAngle", "strokeWidth", "shapeRect", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ShapeRenderer extends BaseStruct {
    private static final String TAG = "ShapeRenderer";
    private int arrowProgramId;
    private int bigArrowProgramId;
    private int circleProgramId;
    private Matrix3f matrix = new Matrix3f();
    private float offsetX;
    private float offsetY;
    private int rectProgramId;
    private int starProgramId;
    private int triangleProgramId;

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.circleProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(2100, 301);
        this.rectProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(2100, 302);
        this.triangleProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(2100, 303);
        this.starProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(3041, 3041);
        this.bigArrowProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(2100, 305);
        this.arrowProgramId = GLUtils.INSTANCE.createShaderProgramFromSStore(2100, 306);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        GLES30.glDeleteProgram(this.circleProgramId);
        GLES30.glDeleteProgram(this.rectProgramId);
        GLES30.glDeleteProgram(this.triangleProgramId);
        GLES30.glDeleteProgram(this.starProgramId);
        GLES30.glDeleteProgram(this.bigArrowProgramId);
        GLES30.glDeleteProgram(this.arrowProgramId);
    }

    public static /* synthetic */ void render$default(ShapeRenderer shapeRenderer, DataShape dataShape, int i, RectF rectF, float f, float f2, float f3, int i2, Object obj) throws Throwable {
        if ((i2 & 16) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 32) != 0) {
            f3 = 0.0f;
        }
        shapeRenderer.render(dataShape, i, rectF, f, f2, f3);
    }

    public final synchronized void render(DataShape shape, int framebufferId, RectF area, float drawRatio, float posOffsetX, float posOffsetY) throws Throwable {
        ShapeRenderer shapeRenderer;
        try {
            try {
                Intrinsics.checkNotNullParameter(shape, "shape");
                Intrinsics.checkNotNullParameter(area, "area");
                this.offsetX = posOffsetX;
                this.offsetY = posOffsetY;
                GLES30.glEnable(R2.color.audio_share_time_color_green);
                GLES30.glBlendEquation(32774);
                GLES30.glBlendFuncSeparate(R2.attr.colorPrimaryFixedDim, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
                float[] colorFromInt = MiMathUtils.INSTANCE.getColorFromInt(shape.getFillColor());
                float[] colorFromInt2 = MiMathUtils.INSTANCE.getColorFromInt(shape.getCurveColor());
                colorFromInt[3] = shape.getFillAlpha() / 255.0f;
                colorFromInt2[3] = shape.getAlpha() / 255.0f;
                switch (shape.mShapeProp.getShapeType()) {
                    case 2:
                        shapeRenderer = this;
                        shapeRenderer.drawArrow(shape, framebufferId, area, colorFromInt, colorFromInt2, drawRatio);
                        break;
                    case 3:
                        shapeRenderer = this;
                        shapeRenderer.drawRect(shape, framebufferId, area, colorFromInt, colorFromInt2, drawRatio);
                        break;
                    case 4:
                        shapeRenderer = this;
                        shapeRenderer.drawTriangle(shape, framebufferId, area, colorFromInt, colorFromInt2, drawRatio);
                        break;
                    case 5:
                        shapeRenderer = this;
                        shapeRenderer.drawCircle(shape, framebufferId, area, colorFromInt, colorFromInt2, drawRatio);
                        break;
                    case 6:
                        shapeRenderer = this;
                        shapeRenderer.drawStar(shape, framebufferId, area, colorFromInt, colorFromInt2, drawRatio);
                        break;
                    case 7:
                        shapeRenderer = this;
                        shapeRenderer.drawBigArrow(shape, framebufferId, area, colorFromInt, colorFromInt2, drawRatio);
                        break;
                    default:
                        shapeRenderer = this;
                        break;
                }
                return;
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
        throw th;
    }

    public final void render(RectF area, float centerX, float centerY, float radius, float scaleSize, float drawRatio, Color bgColor) {
        Intrinsics.checkNotNullParameter(area, "area");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(R2.attr.colorPrimaryFixedDim, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        drawCircle(area, new float[]{1.0f, 1.0f, 1.0f, 0.0f}, Intrinsics.areEqual(bgColor, new Color(0.0f, 0.0f, 0.0f, 1.0f)) ? new float[]{1.0f, 1.0f, 1.0f, 1.0f} : new float[]{0.0f, 0.0f, 0.0f, 1.0f}, centerX, centerY, radius, scaleSize, drawRatio);
    }

    private final void drawCircle(RectF area, float[] fillColor, float[] strokeColor, float centerX, float centerY, float radius, float scaleSize, float drawRatio) {
        GLES30.glUseProgram(this.circleProgramId);
        GLES30.glUniform4f(0, fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
        GLES30.glUniform4f(1, strokeColor[0], strokeColor[1], strokeColor[2], strokeColor[3]);
        GLES30.glUniform4f(2, area.left, area.top, area.right, area.bottom);
        GLES30.glUniform2f(3, centerX, centerY);
        float f = radius * scaleSize;
        GLES30.glUniform2f(4, f, f);
        GLES30.glUniform1f(6, drawRatio * 1.8f);
        GLES30.glUniform1f(7, 1.0f);
        GLES30.glDrawArrays(5, 0, 4);
    }

    private final void drawCircle(DataShape shapeInfo, int framebufferId, RectF area, float[] fillColor, float[] strokeColor, float drawRatio) {
        float[] fArr = shapeInfo.mAnchorPoint4;
        RectF rectF = new RectF(fArr[0] * drawRatio, fArr[1] * drawRatio, fArr[2] * drawRatio, fArr[3] * drawRatio);
        GLES30.glBindFramebuffer(36160, framebufferId);
        GLES30.glUseProgram(this.circleProgramId);
        GLES30.glUniform4f(0, fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
        GLES30.glUniform4f(1, strokeColor[0], strokeColor[1], strokeColor[2], strokeColor[3]);
        GLES30.glUniform4f(2, area.left, area.top, area.right, area.bottom);
        GLES30.glUniform2f(3, rectF.centerX() + this.offsetX, rectF.centerY() + this.offsetY);
        GLES30.glUniform2f(4, rectF.width(), rectF.height());
        GLES30.glUniform1f(6, shapeInfo.getCurvePenWidth() * drawRatio);
        GLES30.glUniform1f(7, shapeInfo.getRotateAngle());
        GLES30.glDrawArrays(5, 0, 4);
    }

    private final void drawRect(DataShape shapeInfo, int framebufferId, RectF area, float[] fillColor, float[] strokeColor, float drawRatio) {
        float[] fArr = shapeInfo.mAnchorPoint4;
        RectF rectF = new RectF(fArr[0] * drawRatio, fArr[1] * drawRatio, fArr[2] * drawRatio, fArr[3] * drawRatio);
        GLES30.glBindFramebuffer(36160, framebufferId);
        GLES30.glUseProgram(this.rectProgramId);
        GLES30.glUniform4f(0, fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
        GLES30.glUniform4f(1, strokeColor[0], strokeColor[1], strokeColor[2], strokeColor[3]);
        GLES30.glUniform4f(2, area.left, area.top, area.right, area.bottom);
        GLES30.glUniform2f(3, rectF.centerX() + this.offsetX, rectF.centerY() + this.offsetY);
        GLES30.glUniform2f(4, rectF.width(), rectF.height());
        GLES30.glUniform1f(6, shapeInfo.getCurvePenWidth() * drawRatio);
        GLES30.glUniform1f(7, shapeInfo.getRotateAngle());
        GLES30.glUniform1f(8, shapeInfo.getFilletPointF().x * drawRatio);
        GLES30.glDrawArrays(5, 0, 4);
    }

    private final void drawTriangle(DataShape shapeInfo, int framebufferId, RectF area, float[] fillColor, float[] strokeColor, float drawRatio) {
        float[] fArr = shapeInfo.mAnchorPoint4;
        RectF rectF = new RectF(fArr[0] * drawRatio, fArr[1] * drawRatio, fArr[2] * drawRatio, fArr[3] * drawRatio);
        float triangleInscribedCircleRadius = getTriangleInscribedCircleRadius(rectF.width(), rectF.height());
        float fRemapRangeClamped = MiMathUtils.INSTANCE.remapRangeClamped(shapeInfo.getFilletPointF().y, 0.0f, rectF.height() - triangleInscribedCircleRadius, 0.0f, triangleInscribedCircleRadius * 0.999f) * drawRatio;
        GLES30.glBindFramebuffer(36160, framebufferId);
        GLES30.glUseProgram(this.triangleProgramId);
        GLES30.glUniform4f(0, fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
        GLES30.glUniform4f(1, strokeColor[0], strokeColor[1], strokeColor[2], strokeColor[3]);
        GLES30.glUniform4f(2, area.left, area.top, area.right, area.bottom);
        GLES30.glUniform2f(3, rectF.centerX() + this.offsetX, rectF.centerY() + this.offsetY);
        GLES30.glUniform2f(4, rectF.width(), rectF.height());
        GLES30.glUniform1f(6, shapeInfo.getCurvePenWidth() * drawRatio);
        GLES30.glUniform1f(7, shapeInfo.getRotateAngle());
        GLES30.glUniform1f(8, fRemapRangeClamped);
        GLES30.glDrawArrays(5, 0, 4);
    }

    private final float getTriangleInscribedCircleRadius(float w, float h) {
        return (h * w) / (w + (((float) Math.sqrt((((w * 0.5f) * w) * 0.5f) + (h * h))) * 2.0f));
    }

    private final void drawStar(DataShape shapeInfo, int framebufferId, RectF area, float[] fillColor, float[] strokeColor, float drawRatio) {
        float[] fArr = shapeInfo.mAnchorPoint4;
        RectF rectF = new RectF(fArr[0] * drawRatio, fArr[1] * drawRatio, fArr[2] * drawRatio, fArr[3] * drawRatio);
        float fWidth = rectF.width();
        float fHeight = rectF.height();
        float fMin = Math.min(MiMathUtils.INSTANCE.remapRange(shapeInfo.getFilletPointF().y * drawRatio, (fHeight / 2.618034f) * 0.5f, fHeight * 0.552786f, 0.809f, 0.0f), 0.809f);
        float curvePenWidth = shapeInfo.getCurvePenWidth() * drawRatio;
        float f = fWidth > fHeight ? curvePenWidth / fWidth : curvePenWidth / fHeight;
        updateMatrix(shapeInfo.getRotateAngle(), curvePenWidth, rectF, area);
        GLES30.glBindFramebuffer(36160, framebufferId);
        GLES30.glUseProgram(this.starProgramId);
        GLES30.glUniformMatrix3fv(0, 1, false, this.matrix.getArray(), 0);
        GLES30.glUniform4f(1, fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
        GLES30.glUniform4f(2, strokeColor[0], strokeColor[1], strokeColor[2], strokeColor[3]);
        GLES30.glUniform3fv(3, 1, new float[]{f * 0.95f, (curvePenWidth / fWidth) * 0.95f, (curvePenWidth / fHeight) * 1.809f}, 0);
        GLES30.glUniform1f(4, fMin);
        GLES30.glDrawArrays(5, 0, 4);
    }

    private final void drawBigArrow(DataShape shapeInfo, int framebufferId, RectF area, float[] fillColor, float[] strokeColor, float drawRatio) {
        float[] fArr = shapeInfo.mAnchorPoint4;
        RectF rectF = new RectF(fArr[0] * drawRatio, fArr[1] * drawRatio, fArr[2] * drawRatio, fArr[3] * drawRatio);
        float f = shapeInfo.getFilletPointF().x * drawRatio;
        float f2 = shapeInfo.getFilletPointF().y * drawRatio;
        GLES30.glBindFramebuffer(36160, framebufferId);
        GLES30.glUseProgram(this.bigArrowProgramId);
        GLES30.glUniform4f(0, fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
        GLES30.glUniform4f(1, strokeColor[0], strokeColor[1], strokeColor[2], strokeColor[3]);
        GLES30.glUniform4f(2, area.left, area.top, area.right, area.bottom);
        GLES30.glUniform2f(3, rectF.centerX() + this.offsetX, rectF.centerY() + this.offsetY);
        GLES30.glUniform2f(4, rectF.width(), rectF.height());
        GLES30.glUniform1f(6, shapeInfo.getCurvePenWidth() * drawRatio);
        GLES30.glUniform1f(7, shapeInfo.getRotateAngle());
        GLES30.glUniform2f(8, f, f2);
        GLES30.glDrawArrays(5, 0, 4);
    }

    private final void drawArrow(DataShape shapeInfo, int framebufferId, RectF area, float[] fillColor, float[] strokeColor, float drawRatio) {
        float[] fArr = shapeInfo.mAnchorPoint4;
        RectF rectF = new RectF(fArr[0] * drawRatio, fArr[1] * drawRatio, fArr[2] * drawRatio, fArr[3] * drawRatio);
        float penSize = shapeInfo.mShapeProp.getPenSize() * drawRatio * 1.6f;
        float penSize2 = (shapeInfo.mShapeProp.getPenSize() - 1.0f) * drawRatio;
        GLES30.glBindFramebuffer(36160, framebufferId);
        GLES30.glUseProgram(this.arrowProgramId);
        GLES30.glUniform4f(0, fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
        GLES30.glUniform4f(1, strokeColor[0], strokeColor[1], strokeColor[2], strokeColor[3]);
        GLES30.glUniform4f(2, area.left, area.top, area.right, area.bottom);
        GLES30.glUniform2f(3, rectF.centerX() + this.offsetX, rectF.centerY() + this.offsetY);
        GLES30.glUniform2f(4, rectF.width(), rectF.height());
        GLES30.glUniform1f(6, shapeInfo.getCurvePenWidth() * drawRatio);
        GLES30.glUniform1f(7, shapeInfo.getRotateAngle());
        GLES30.glUniform2f(8, penSize, penSize2);
        GLES30.glDrawArrays(5, 0, 4);
    }

    private final void printMatrix3f(Matrix3f matrix) {
        float[] array = matrix.getArray();
        XLog.d(TAG, "Matrix3f: \n[" + array[0] + ", " + array[3] + ", " + array[6] + "]\n[" + array[1] + ", " + array[4] + ", " + array[7] + "]\n[" + array[2] + ", " + array[5] + ", " + array[8] + "]", new Object[0]);
    }

    private final void updateMatrix(float rotateAngle, float strokeWidth, RectF shapeRect, RectF area) {
        this.matrix.loadIdentity();
        float fWidth = area.width();
        float fHeight = area.height();
        if (fWidth != 0.0f && fHeight != 0.0f) {
            this.matrix.scale(1.0f / fWidth, 1.0f / fHeight);
        } else {
            XLog.w(TAG, "Area width and height must be not zero", new Object[0]);
        }
        float f = 2;
        this.matrix.translate(((shapeRect.centerX() - area.centerX()) + this.offsetX) * f, ((area.centerY() - shapeRect.centerY()) - this.offsetY) * f);
        this.matrix.rotate(rotateAngle);
        this.matrix.scale(shapeRect.width() + strokeWidth, shapeRect.height() + strokeWidth);
    }
}
