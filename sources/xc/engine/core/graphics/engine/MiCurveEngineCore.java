package xc.engine.core.graphics.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.cbridge.MiCurveNative;
import xc.engine.core.graphics.curve.CurveFittingProcessor;
import xc.engine.core.graphics.engine.core.BaseStruct;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.BgRenderer;
import xc.engine.core.graphics.engine.renderers.BlendRenderer;
import xc.engine.core.graphics.engine.renderers.CurveRenderer;
import xc.engine.core.graphics.engine.renderers.ImageRenderer;
import xc.engine.core.graphics.engine.renderers.ShapeRenderer;
import xc.engine.core.graphics.engine.systems.TextSystem;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.richtext.data.SpannedData;
import xc.richtext.utils.SpannedUtils;

/* JADX INFO: compiled from: MiCurveEngineCore.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u0011H\u0014J\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lxc/engine/core/graphics/engine/MiCurveEngineCore;", "Lxc/engine/core/graphics/engine/core/BaseStruct;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "bgRenderer", "Lxc/engine/core/graphics/engine/renderers/BgRenderer;", "tileRenderer", "Lxc/engine/core/graphics/engine/TileRenderer;", "curveRenderer", "Lxc/engine/core/graphics/engine/renderers/CurveRenderer;", "blendRenderer", "Lxc/engine/core/graphics/engine/renderers/BlendRenderer;", "imgRenderer", "Lxc/engine/core/graphics/engine/renderers/ImageRenderer;", "shapeRenderer", "Lxc/engine/core/graphics/engine/renderers/ShapeRenderer;", "onInit", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDestroy", "renderToBitmap", "data", "Lxc/engine/core/graphics/engine/RenderData;", "Companion", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class MiCurveEngineCore extends BaseStruct {
    private static final String TAG = "MiCurveEngineCore";
    private final BgRenderer bgRenderer = new BgRenderer();
    private final TileRenderer tileRenderer = new TileRenderer();
    private final CurveRenderer curveRenderer = new CurveRenderer();
    private final BlendRenderer blendRenderer = new BlendRenderer();
    private final ImageRenderer imgRenderer = new ImageRenderer();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onInit(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.bgRenderer.init(context);
        this.tileRenderer.init(context);
        this.curveRenderer.init(context);
        this.blendRenderer.init(context);
        this.imgRenderer.init(context);
        this.shapeRenderer.init(context);
    }

    @Override // xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        this.shapeRenderer.destroy();
        this.imgRenderer.destroy();
        this.blendRenderer.destroy();
        this.curveRenderer.destroy();
        this.tileRenderer.destroy();
        this.bgRenderer.destroy();
    }

    /* JADX WARN: Code duplicated, block: B:105:0x0382  */
    /* JADX WARN: Code duplicated, block: B:107:0x03c5  */
    /* JADX WARN: Code duplicated, block: B:110:0x03e0  */
    /* JADX WARN: Code duplicated, block: B:113:0x03ee  */
    /* JADX WARN: Code duplicated, block: B:115:0x03fe  */
    /* JADX WARN: Code duplicated, block: B:125:0x041d  */
    /* JADX WARN: Code duplicated, block: B:133:0x04a8  */
    /* JADX WARN: Code duplicated, block: B:141:0x0509  */
    /* JADX WARN: Code duplicated, block: B:142:0x053f  */
    /* JADX WARN: Code duplicated, block: B:144:0x0559  */
    /* JADX WARN: Code duplicated, block: B:146:0x0562  */
    /* JADX WARN: Code duplicated, block: B:147:0x0594  */
    /* JADX WARN: Code duplicated, block: B:149:0x05ea  */
    /* JADX WARN: Code duplicated, block: B:152:0x05fc  */
    /* JADX WARN: Code duplicated, block: B:155:0x0610  */
    /* JADX WARN: Code duplicated, block: B:157:0x061d  */
    /* JADX WARN: Code duplicated, block: B:159:0x0625  */
    /* JADX WARN: Code duplicated, block: B:169:0x0644  */
    /* JADX WARN: Code duplicated, block: B:177:0x06db  */
    /* JADX WARN: Code duplicated, block: B:178:0x06e7  */
    /* JADX WARN: Code duplicated, block: B:180:0x06fc  */
    /* JADX WARN: Code duplicated, block: B:181:0x071f  */
    /* JADX WARN: Code duplicated, block: B:184:0x0750  */
    /* JADX WARN: Code duplicated, block: B:187:0x0406 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:189:0x0425 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:191:0x064c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:193:0x062d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:209:0x0243 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:212:0x0227 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:214:0x02d3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:217:0x02a6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:63:0x022d  */
    /* JADX WARN: Code duplicated, block: B:67:0x024f  */
    /* JADX WARN: Code duplicated, block: B:68:0x0277  */
    /* JADX WARN: Code duplicated, block: B:73:0x02ac  */
    /* JADX WARN: Code duplicated, block: B:78:0x02ca  */
    /* JADX WARN: Code duplicated, block: B:84:0x030e  */
    /* JADX WARN: Code duplicated, block: B:86:0x0330  */
    /* JADX WARN: Code duplicated, block: B:87:0x0336  */
    /* JADX WARN: Code duplicated, block: B:90:0x0341  */
    public final void renderToBitmap(RenderData data) throws Throwable {
        List<SpannedData> spans;
        int i;
        float f;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float f2;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        Object mLockObj;
        Object mLockObj2;
        int i28;
        int i29;
        float f3;
        int i30;
        Object mLockObj3;
        Object mLockObj4;
        Paint paint;
        List<DataCurve> curves;
        List<DataCurve> curves2;
        DataCurve dataCurve;
        List<DataCurve> curves3;
        DataCurve dataCurve2;
        boolean z;
        Bitmap bitmapFromTextSpan;
        RectF rectF;
        Bitmap drawBitmap;
        int iCreateTexture;
        Intrinsics.checkNotNullParameter(data, "data");
        Log.w(TAG, "xl-- renderToBitmap: " + data);
        if (data.getOutBitmapSize().x <= 16 || data.getOutBitmapSize().y <= 16) {
            return;
        }
        List<DataCurve> curves4 = data.getCurves();
        if ((curves4 == null || curves4.isEmpty()) && ((spans = data.getSpans()) == null || spans.isEmpty())) {
            return;
        }
        ArrayList<SpannedData> arrayList = new ArrayList();
        ArrayList<SpannedData> arrayList2 = new ArrayList();
        List<SpannedData> spans2 = data.getSpans();
        if (spans2 != null) {
            for (SpannedData spannedData : spans2) {
                if (spannedData.getType() == 7) {
                    arrayList.add(spannedData);
                } else if (spannedData.getType() == 11) {
                    arrayList2.add(spannedData);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
        ArrayList<DataCurve> arrayList3 = new ArrayList();
        ArrayList<DataCurve> arrayList4 = new ArrayList();
        List<DataCurve> curves5 = data.getCurves();
        if (curves5 != null) {
            for (DataCurve dataCurve3 : curves5) {
                if (dataCurve3.mCurveProp.getPenType() == PenType.MARK) {
                    arrayList3.add(dataCurve3);
                } else {
                    arrayList4.add(dataCurve3);
                }
            }
            Unit unit2 = Unit.INSTANCE;
        }
        float f4 = data.getPageSize().x / 1000.0f;
        RectF rectF2 = new RectF(data.getPageOffset().x, data.getPageOffset().y, (data.getPageSize().x / f4) + data.getPageOffset().x, (data.getPageSize().y / f4) + data.getPageOffset().y);
        boolean z2 = (!data.getBPdfMode() || arrayList3.isEmpty() || data.getBgBitmap() == null) ? false : true;
        boolean z3 = arrayList3.isEmpty() || z2;
        int i31 = data.getOutBitmapSize().x;
        int i32 = data.getOutBitmapSize().y;
        int iCreateTexture2 = GLUtils.INSTANCE.createTexture(i31, i32);
        int iCreateTexture3 = GLUtils.INSTANCE.createTexture(i31, i32);
        int iCreateTexture4 = GLUtils.INSTANCE.createTexture(i31, i32);
        int iCreateFramebufferFromTexture = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture2);
        int iCreateFramebufferFromTexture2 = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture3);
        int iCreateFramebufferFromTexture3 = GLUtils.INSTANCE.createFramebufferFromTexture(iCreateTexture4);
        GLES30.glViewport(0, 0, i31, i32);
        int i33 = i31;
        GLES30.glBindFramebuffer(36160, iCreateFramebufferFromTexture);
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        if (z3) {
            if (data.getBgBitmap() != null) {
                GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                GLES30.glClear(16384);
                GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLUtils.Companion companion = GLUtils.INSTANCE;
                Bitmap bgBitmap = data.getBgBitmap();
                Intrinsics.checkNotNull(bgBitmap);
                int iCreateTexture5 = companion.createTexture(bgBitmap);
                i = iCreateFramebufferFromTexture;
                i3 = iCreateTexture3;
                f = 1.0f;
                i2 = i32;
                TileRenderer.drawRect$default(this.tileRenderer, rectF2, data.getArea(), iCreateTexture5, true, false, 0.0f, 48, null);
                GLUtils.INSTANCE.deleteTexture(iCreateTexture5);
            } else {
                arrayList = arrayList;
                arrayList2 = arrayList2;
                arrayList3 = arrayList3;
                iCreateTexture4 = iCreateTexture4;
                arrayList4 = arrayList4;
                i = iCreateFramebufferFromTexture;
                f = 1.0f;
                i2 = i32;
                iCreateTexture2 = iCreateTexture2;
                i3 = iCreateTexture3;
                i33 = i33;
                this.bgRenderer.renderStyle(new RectF(-1.0f, 1.0f, 1.0f, -1.0f), data.getArea(), data.getBgColor(), data.getBgStyle(), 1.0f, new Color(0.65f, 0.65f, 0.65f, 1.0f));
            }
            for (SpannedData spannedData2 : arrayList) {
                rectF = spannedData2.getRectF();
                drawBitmap = SpannedUtils.getDrawBitmap(spannedData2.mBitmapPath, rectF.width());
                if (drawBitmap != null) {
                    iCreateTexture = GLUtils.INSTANCE.createTexture(drawBitmap);
                    if (spannedData2.isHadCrop()) {
                        ImageRenderer imageRenderer = this.imgRenderer;
                        RectF area = data.getArea();
                        Intrinsics.checkNotNull(rectF);
                        float degrees = spannedData2.getDegrees();
                        RectF mCropRectF = spannedData2.mCropRectF;
                        Intrinsics.checkNotNullExpressionValue(mCropRectF, "mCropRectF");
                        ImageRenderer.renderCrop$default(imageRenderer, area, iCreateTexture, rectF, degrees, false, 0.0f, 0.0f, mCropRectF, 0, 256, null);
                    } else {
                        ImageRenderer imageRenderer2 = this.imgRenderer;
                        RectF area2 = data.getArea();
                        Intrinsics.checkNotNull(rectF);
                        ImageRenderer.render$default(imageRenderer2, area2, iCreateTexture, rectF, spannedData2.getDegrees(), false, 0.0f, 0.0f, 112, null);
                    }
                    drawBitmap.recycle();
                    GLUtils.INSTANCE.deleteTexture(iCreateTexture);
                }
            }
            for (SpannedData spannedData3 : arrayList2) {
                if (data.getBUseImageAsBG() && Intrinsics.areEqual(data.getBgColor(), Color.INSTANCE.getBLACK())) {
                    z = true;
                } else {
                    z = false;
                }
                bitmapFromTextSpan = TextSystem.INSTANCE.getBitmapFromTextSpan(spannedData3, z, f);
                if (bitmapFromTextSpan != null) {
                    int iCreateTexture6 = GLUtils.INSTANCE.createTexture(bitmapFromTextSpan);
                    ImageRenderer imageRenderer3 = this.imgRenderer;
                    RectF area3 = data.getArea();
                    RectF rectF3 = spannedData3.getRectF();
                    Intrinsics.checkNotNullExpressionValue(rectF3, "getRectF(...)");
                    ImageRenderer.render$default(imageRenderer3, area3, iCreateTexture6, rectF3, spannedData3.getDegrees(), false, 0.0f, 0.0f, 112, null);
                    bitmapFromTextSpan.recycle();
                    GLUtils.INSTANCE.deleteTexture(iCreateTexture6);
                }
            }
            if (data.getHighlightPath() != null) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i33, i2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                paint = new Paint();
                paint.setColor(-13335809);
                if (data.getBgColor().getIntValue() == -16777216) {
                    paint.setAlpha(90);
                } else {
                    paint.setAlpha(26);
                }
                curves = data.getCurves();
                if (curves != null) {
                    i5 = 1;
                    if ((!curves.isEmpty()) && (curves2 = data.getCurves()) != null && (dataCurve = curves2.get(0)) != null && dataCurve.getCurveColor() == paint.getColor() && (curves3 = data.getCurves()) != null && (dataCurve2 = curves3.get(0)) != null && dataCurve2.getAlpha() == paint.getAlpha()) {
                        paint.setAlpha(13);
                    }
                } else {
                    i5 = 1;
                }
                canvas.translate(-data.getArea().left, -data.getArea().top);
                Path highlightPath = data.getHighlightPath();
                Intrinsics.checkNotNull(highlightPath);
                canvas.drawPath(highlightPath, paint);
                int iCreateTexture7 = GLUtils.INSTANCE.createTexture(bitmapCreateBitmap);
                i4 = R2.attr.colorPrimaryInverse;
                GLES30.glBlendFuncSeparate(i5, R2.attr.colorPrimaryInverse, i5, R2.attr.colorPrimaryInverse);
                TileRenderer.drawFullScreen$default(this.tileRenderer, iCreateTexture7, true, false, 0.0f, 12, null);
                GLUtils.INSTANCE.deleteTexture(iCreateTexture7);
                bitmapCreateBitmap.recycle();
            } else {
                i4 = R2.attr.colorPrimaryInverse;
                i5 = 1;
            }
            if (arrayList3.isEmpty()) {
                i6 = i33;
                f2 = f;
                i7 = iCreateFramebufferFromTexture3;
                i8 = iCreateFramebufferFromTexture2;
                i9 = i3;
                i10 = iCreateTexture4;
                i11 = iCreateTexture2;
                i12 = 36160;
                i13 = R2.color.m3_ref_palette_primary80;
                i14 = i2;
            } else {
                for (DataCurve dataCurve4 : arrayList3) {
                    f3 = f;
                    if (dataCurve4.size(dataCurve4.mOriginPoints) == 0) {
                        mLockObj4 = dataCurve4.mLockObj;
                        Intrinsics.checkNotNullExpressionValue(mLockObj4, "mLockObj");
                        synchronized (mLockObj4) {
                            new CurveFittingProcessor(dataCurve4).fillCurvePoints();
                            Unit unit3 = Unit.INSTANCE;
                        }
                    }
                    if (dataCurve4.size(dataCurve4.mOriginPoints) > 0) {
                        mLockObj3 = dataCurve4.mLockObj;
                        Intrinsics.checkNotNullExpressionValue(mLockObj3, "mLockObj");
                        synchronized (mLockObj3) {
                            float value = dataCurve4.getCurveProp().getPenType().getValue();
                            float penSize = dataCurve4.getCurveProp().getPenSize();
                            float drawRatio = dataCurve4.getDrawRatio();
                            float f5 = data.getArea().left;
                            float f6 = data.getArea().top;
                            i30 = i5;
                            float f7 = data.getArea().right;
                            float f8 = data.getArea().bottom;
                            float sensitivity = dataCurve4.getSensitivity();
                            float tipSize = dataCurve4.getTipSize();
                            float[] fArr = new float[10];
                            fArr[0] = value;
                            fArr[i30] = penSize;
                            fArr[2] = drawRatio;
                            fArr[3] = f3;
                            fArr[4] = f5;
                            fArr[5] = f6;
                            fArr[6] = f7;
                            fArr[7] = f8;
                            fArr[8] = sensitivity;
                            fArr[9] = tipSize;
                            long jNativeGenCurveVertexBuffer = MiCurveNative.nativeGenCurveVertexBuffer(dataCurve4.mOriginPoints, fArr, 0L, false);
                            Unit unit4 = Unit.INSTANCE;
                        }
                        CurveRenderer.renderCurve$default(this.curveRenderer, dataCurve4, data.getArea(), iCreateFramebufferFromTexture3, iCreateFramebufferFromTexture2, i3, 1.0f, jNativeGenCurveVertexBuffer, false, 128, null);
                    } else {
                        i30 = i5;
                    }
                    iCreateFramebufferFromTexture2 = iCreateFramebufferFromTexture2;
                    iCreateFramebufferFromTexture3 = iCreateFramebufferFromTexture3;
                    i3 = i3;
                    f = f3;
                    i5 = i30;
                    i33 = i33;
                    i4 = R2.attr.colorPrimaryInverse;
                }
                i6 = i33;
                f2 = f;
                int i34 = i5;
                i7 = iCreateFramebufferFromTexture3;
                i29 = iCreateFramebufferFromTexture2;
                i9 = i3;
                GLES30.glBlendFuncSeparate(i34, i4, i34, i4);
                if (!arrayList.isEmpty() && arrayList2.isEmpty() && !z2) {
                    i12 = 36160;
                    GLES30.glBindFramebuffer(36160, i);
                    int i35 = iCreateTexture4;
                    TileRenderer.drawFullScreen$default(this.tileRenderer, i35, false, false, 0.0f, 14, null);
                    i8 = i29;
                    i14 = i2;
                    i10 = i35;
                    i11 = iCreateTexture2;
                    i13 = R2.color.m3_ref_palette_primary80;
                } else {
                    int i36 = iCreateTexture4;
                    i12 = 36160;
                    int i37 = iCreateTexture2;
                    this.blendRenderer.doMultiplyBlend(i29, i37, i36, false, true);
                    i8 = i29;
                    i11 = i37;
                    i10 = i36;
                    i13 = R2.color.m3_ref_palette_primary80;
                    GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, i11);
                    int i38 = i2;
                    GLES30.glCopyTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, 0, 0, i6, i38, 0);
                    i14 = i38;
                }
            }
            if (z2) {
                i15 = i12;
                i16 = i8;
                i17 = i9;
                i18 = i14;
                i19 = i6;
            } else {
                GLES30.glBindFramebuffer(i12, i8);
                if (data.getBgBitmap() != null) {
                    GLUtils.Companion companion2 = GLUtils.INSTANCE;
                    Bitmap bgBitmap2 = data.getBgBitmap();
                    Intrinsics.checkNotNull(bgBitmap2);
                    int iCreateTexture8 = companion2.createTexture(bgBitmap2);
                    i16 = i8;
                    i15 = i12;
                    i28 = i13;
                    TileRenderer.drawRect$default(this.tileRenderer, rectF2, data.getArea(), iCreateTexture8, true, false, 0.0f, 48, null);
                    GLUtils.INSTANCE.deleteTexture(iCreateTexture8);
                } else {
                    i15 = i12;
                    i16 = i8;
                    i28 = i13;
                    float f9 = f2;
                    this.bgRenderer.renderStyle(new RectF(-1.0f, f9, f9, -1.0f), data.getArea(), data.getBgColor(), data.getBgStyle(), 1.0f, new Color(0.65f, 0.65f, 0.65f, f9));
                }
                int i39 = i9;
                BlendRenderer.doNormalBlend$default(this.blendRenderer, i7, i39, i11, false, false, 24, null);
                i17 = i39;
                GLES30.glBindTexture(i28, i11);
                int i40 = i14;
                int i41 = i6;
                GLES30.glCopyTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, 0, 0, i41, i40, 0);
                i19 = i41;
                i18 = i40;
            }
            if (arrayList4.isEmpty()) {
                i20 = i11;
                i21 = i16;
                i22 = i17;
                i23 = i10;
            } else {
                GLES30.glBindFramebuffer(i15, i7);
                GLES30.glClear(16384);
                for (DataCurve dataCurve5 : arrayList4) {
                    if (dataCurve5.getType() == 1) {
                        if (dataCurve5.size(dataCurve5.mOriginPoints) == 0) {
                            mLockObj2 = dataCurve5.mLockObj;
                            Intrinsics.checkNotNullExpressionValue(mLockObj2, "mLockObj");
                            synchronized (mLockObj2) {
                                new CurveFittingProcessor(dataCurve5).fillCurvePoints();
                                Unit unit5 = Unit.INSTANCE;
                            }
                        }
                        if (dataCurve5.size(dataCurve5.mOriginPoints) > 0) {
                            mLockObj = dataCurve5.mLockObj;
                            Intrinsics.checkNotNullExpressionValue(mLockObj, "mLockObj");
                            synchronized (mLockObj) {
                                i24 = i11;
                                int i42 = i16;
                                long jNativeGenCurveVertexBuffer2 = MiCurveNative.nativeGenCurveVertexBuffer(dataCurve5.mOriginPoints, new float[]{dataCurve5.getCurveProp().getPenType().getValue(), dataCurve5.getCurveProp().getPenSize(), dataCurve5.getDrawRatio(), 1.0f, data.getArea().left, data.getArea().top, data.getArea().right, data.getArea().bottom, dataCurve5.getSensitivity(), dataCurve5.getTipSize()}, 0L, false);
                                Unit unit6 = Unit.INSTANCE;
                            }
                            CurveRenderer.renderCurve$default(this.curveRenderer, dataCurve5, data.getArea(), i7, i42, i17, 1.0f, jNativeGenCurveVertexBuffer2, false, 128, null);
                            i25 = i42;
                            i27 = i17;
                        } else {
                            i24 = i11;
                            i25 = i16;
                            i27 = i17;
                        }
                    } else {
                        i24 = i11;
                        i25 = i16;
                        i26 = i7;
                        i27 = i17;
                        if (dataCurve5.getType() == 28) {
                            ShapeRenderer shapeRenderer = this.shapeRenderer;
                            Intrinsics.checkNotNull(dataCurve5, "null cannot be cast to non-null type xc.engine.data.pagedata.DataShape");
                            ShapeRenderer.render$default(shapeRenderer, (DataShape) dataCurve5, i26, data.getArea(), 1.0f, 0.0f, 0.0f, 48, null);
                            i7 = i26;
                        } else {
                            i7 = i26;
                        }
                    }
                    i17 = i27;
                    i16 = i25;
                    i11 = i24;
                    i15 = 36160;
                }
                i20 = i11;
                int i43 = i15;
                i21 = i16;
                i22 = i17;
                GLES30.glBindFramebuffer(i43, i);
                GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
                int i44 = i10;
                TileRenderer.drawFullScreen$default(this.tileRenderer, i44, false, false, 0.0f, 14, null);
                i23 = i44;
            }
            int i45 = i20;
            TileRenderer.drawRGBAtoBGRA$default(this.tileRenderer, i21, i45, false, 4, null);
            data.setOutBitmap(GLUtils.INSTANCE.getBitmapFromFramebuffer(i21, i19, i18));
            GLUtils.INSTANCE.deleteFramebuffer(i);
            GLUtils.INSTANCE.deleteFramebuffer(i21);
            GLUtils.INSTANCE.deleteFramebuffer(i7);
            GLUtils.INSTANCE.deleteTexture(i45);
            GLUtils.INSTANCE.deleteTexture(i22);
            GLUtils.INSTANCE.deleteTexture(i23);
        }
        i = iCreateFramebufferFromTexture;
        f = 1.0f;
        i2 = i32;
        i3 = iCreateTexture3;
        while (r8.hasNext()) {
            rectF = spannedData2.getRectF();
            drawBitmap = SpannedUtils.getDrawBitmap(spannedData2.mBitmapPath, rectF.width());
            if (drawBitmap != null) {
                iCreateTexture = GLUtils.INSTANCE.createTexture(drawBitmap);
                if (spannedData2.isHadCrop()) {
                    ImageRenderer imageRenderer4 = this.imgRenderer;
                    RectF area4 = data.getArea();
                    Intrinsics.checkNotNull(rectF);
                    float degrees2 = spannedData2.getDegrees();
                    RectF mCropRectF2 = spannedData2.mCropRectF;
                    Intrinsics.checkNotNullExpressionValue(mCropRectF2, "mCropRectF");
                    ImageRenderer.renderCrop$default(imageRenderer4, area4, iCreateTexture, rectF, degrees2, false, 0.0f, 0.0f, mCropRectF2, 0, 256, null);
                } else {
                    ImageRenderer imageRenderer5 = this.imgRenderer;
                    RectF area5 = data.getArea();
                    Intrinsics.checkNotNull(rectF);
                    ImageRenderer.render$default(imageRenderer5, area5, iCreateTexture, rectF, spannedData2.getDegrees(), false, 0.0f, 0.0f, 112, null);
                }
                drawBitmap.recycle();
                GLUtils.INSTANCE.deleteTexture(iCreateTexture);
            }
        }
        while (r8.hasNext()) {
            if (data.getBUseImageAsBG()) {
                z = false;
            } else {
                z = false;
            }
            bitmapFromTextSpan = TextSystem.INSTANCE.getBitmapFromTextSpan(spannedData3, z, f);
            if (bitmapFromTextSpan != null) {
                int iCreateTexture9 = GLUtils.INSTANCE.createTexture(bitmapFromTextSpan);
                ImageRenderer imageRenderer6 = this.imgRenderer;
                RectF area6 = data.getArea();
                RectF rectF4 = spannedData3.getRectF();
                Intrinsics.checkNotNullExpressionValue(rectF4, "getRectF(...)");
                ImageRenderer.render$default(imageRenderer6, area6, iCreateTexture9, rectF4, spannedData3.getDegrees(), false, 0.0f, 0.0f, 112, null);
                bitmapFromTextSpan.recycle();
                GLUtils.INSTANCE.deleteTexture(iCreateTexture9);
            }
        }
        if (data.getHighlightPath() != null) {
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(i33, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            paint = new Paint();
            paint.setColor(-13335809);
            if (data.getBgColor().getIntValue() == -16777216) {
                paint.setAlpha(90);
            } else {
                paint.setAlpha(26);
            }
            curves = data.getCurves();
            if (curves != null) {
                i5 = 1;
                if (!curves.isEmpty()) {
                    paint.setAlpha(13);
                }
            } else {
                i5 = 1;
            }
            canvas2.translate(-data.getArea().left, -data.getArea().top);
            Path highlightPath2 = data.getHighlightPath();
            Intrinsics.checkNotNull(highlightPath2);
            canvas2.drawPath(highlightPath2, paint);
            int iCreateTexture10 = GLUtils.INSTANCE.createTexture(bitmapCreateBitmap2);
            i4 = R2.attr.colorPrimaryInverse;
            GLES30.glBlendFuncSeparate(i5, R2.attr.colorPrimaryInverse, i5, R2.attr.colorPrimaryInverse);
            TileRenderer.drawFullScreen$default(this.tileRenderer, iCreateTexture10, true, false, 0.0f, 12, null);
            GLUtils.INSTANCE.deleteTexture(iCreateTexture10);
            bitmapCreateBitmap2.recycle();
        } else {
            i4 = R2.attr.colorPrimaryInverse;
            i5 = 1;
        }
        if (arrayList3.isEmpty()) {
            while (r8.hasNext()) {
                f3 = f;
                if (dataCurve4.size(dataCurve4.mOriginPoints) == 0) {
                    mLockObj4 = dataCurve4.mLockObj;
                    Intrinsics.checkNotNullExpressionValue(mLockObj4, "mLockObj");
                    synchronized (mLockObj4) {
                        new CurveFittingProcessor(dataCurve4).fillCurvePoints();
                        Unit unit7 = Unit.INSTANCE;
                    }
                }
                if (dataCurve4.size(dataCurve4.mOriginPoints) > 0) {
                    mLockObj3 = dataCurve4.mLockObj;
                    Intrinsics.checkNotNullExpressionValue(mLockObj3, "mLockObj");
                    synchronized (mLockObj3) {
                        float value2 = dataCurve4.getCurveProp().getPenType().getValue();
                        float penSize2 = dataCurve4.getCurveProp().getPenSize();
                        float drawRatio2 = dataCurve4.getDrawRatio();
                        float f10 = data.getArea().left;
                        float f11 = data.getArea().top;
                        i30 = i5;
                        float f12 = data.getArea().right;
                        float f13 = data.getArea().bottom;
                        float sensitivity2 = dataCurve4.getSensitivity();
                        float tipSize2 = dataCurve4.getTipSize();
                        float[] fArr2 = new float[10];
                        fArr2[0] = value2;
                        fArr2[i30] = penSize2;
                        fArr2[2] = drawRatio2;
                        fArr2[3] = f3;
                        fArr2[4] = f10;
                        fArr2[5] = f11;
                        fArr2[6] = f12;
                        fArr2[7] = f13;
                        fArr2[8] = sensitivity2;
                        fArr2[9] = tipSize2;
                        long jNativeGenCurveVertexBuffer3 = MiCurveNative.nativeGenCurveVertexBuffer(dataCurve4.mOriginPoints, fArr2, 0L, false);
                        Unit unit8 = Unit.INSTANCE;
                        CurveRenderer.renderCurve$default(this.curveRenderer, dataCurve4, data.getArea(), iCreateFramebufferFromTexture3, iCreateFramebufferFromTexture2, i3, 1.0f, jNativeGenCurveVertexBuffer3, false, 128, null);
                    }
                } else {
                    i30 = i5;
                }
                iCreateFramebufferFromTexture2 = iCreateFramebufferFromTexture2;
                iCreateFramebufferFromTexture3 = iCreateFramebufferFromTexture3;
                i3 = i3;
                f = f3;
                i5 = i30;
                i33 = i33;
                i4 = R2.attr.colorPrimaryInverse;
            }
            i6 = i33;
            f2 = f;
            int i310 = i5;
            i7 = iCreateFramebufferFromTexture3;
            i29 = iCreateFramebufferFromTexture2;
            i9 = i3;
            GLES30.glBlendFuncSeparate(i310, i4, i310, i4);
            if (!arrayList.isEmpty()) {
                int i311 = iCreateTexture4;
                i12 = 36160;
                int i312 = iCreateTexture2;
                this.blendRenderer.doMultiplyBlend(i29, i312, i311, false, true);
                i8 = i29;
                i11 = i312;
                i10 = i311;
                i13 = R2.color.m3_ref_palette_primary80;
                GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, i11);
                int i313 = i2;
                GLES30.glCopyTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, 0, 0, i6, i313, 0);
                i14 = i313;
            } else {
                int i314 = iCreateTexture4;
                i12 = 36160;
                int i315 = iCreateTexture2;
                this.blendRenderer.doMultiplyBlend(i29, i315, i314, false, true);
                i8 = i29;
                i11 = i315;
                i10 = i314;
                i13 = R2.color.m3_ref_palette_primary80;
                GLES30.glBindTexture(R2.color.m3_ref_palette_primary80, i11);
                int i316 = i2;
                GLES30.glCopyTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, 0, 0, i6, i316, 0);
                i14 = i316;
            }
        } else {
            i6 = i33;
            f2 = f;
            i7 = iCreateFramebufferFromTexture3;
            i8 = iCreateFramebufferFromTexture2;
            i9 = i3;
            i10 = iCreateTexture4;
            i11 = iCreateTexture2;
            i12 = 36160;
            i13 = R2.color.m3_ref_palette_primary80;
            i14 = i2;
        }
        if (z2) {
            GLES30.glBindFramebuffer(i12, i8);
            if (data.getBgBitmap() != null) {
                GLUtils.Companion companion3 = GLUtils.INSTANCE;
                Bitmap bgBitmap3 = data.getBgBitmap();
                Intrinsics.checkNotNull(bgBitmap3);
                int iCreateTexture11 = companion3.createTexture(bgBitmap3);
                i16 = i8;
                i15 = i12;
                i28 = i13;
                TileRenderer.drawRect$default(this.tileRenderer, rectF2, data.getArea(), iCreateTexture11, true, false, 0.0f, 48, null);
                GLUtils.INSTANCE.deleteTexture(iCreateTexture11);
            } else {
                i15 = i12;
                i16 = i8;
                i28 = i13;
                float f14 = f2;
                this.bgRenderer.renderStyle(new RectF(-1.0f, f14, f14, -1.0f), data.getArea(), data.getBgColor(), data.getBgStyle(), 1.0f, new Color(0.65f, 0.65f, 0.65f, f14));
            }
            int i317 = i9;
            BlendRenderer.doNormalBlend$default(this.blendRenderer, i7, i317, i11, false, false, 24, null);
            i17 = i317;
            GLES30.glBindTexture(i28, i11);
            int i46 = i14;
            int i47 = i6;
            GLES30.glCopyTexImage2D(R2.color.m3_ref_palette_primary80, 0, R2.dimen.ai_invitation_button_margin_bottom_pad_portrait, 0, 0, i47, i46, 0);
            i19 = i47;
            i18 = i46;
        } else {
            i15 = i12;
            i16 = i8;
            i17 = i9;
            i18 = i14;
            i19 = i6;
        }
        if (arrayList4.isEmpty()) {
            GLES30.glBindFramebuffer(i15, i7);
            GLES30.glClear(16384);
            while (r9.hasNext()) {
                if (dataCurve5.getType() == 1) {
                    if (dataCurve5.size(dataCurve5.mOriginPoints) == 0) {
                        mLockObj2 = dataCurve5.mLockObj;
                        Intrinsics.checkNotNullExpressionValue(mLockObj2, "mLockObj");
                        synchronized (mLockObj2) {
                            new CurveFittingProcessor(dataCurve5).fillCurvePoints();
                            Unit unit9 = Unit.INSTANCE;
                        }
                    }
                    if (dataCurve5.size(dataCurve5.mOriginPoints) > 0) {
                        mLockObj = dataCurve5.mLockObj;
                        Intrinsics.checkNotNullExpressionValue(mLockObj, "mLockObj");
                        synchronized (mLockObj) {
                            i24 = i11;
                            int i48 = i16;
                            long jNativeGenCurveVertexBuffer4 = MiCurveNative.nativeGenCurveVertexBuffer(dataCurve5.mOriginPoints, new float[]{dataCurve5.getCurveProp().getPenType().getValue(), dataCurve5.getCurveProp().getPenSize(), dataCurve5.getDrawRatio(), 1.0f, data.getArea().left, data.getArea().top, data.getArea().right, data.getArea().bottom, dataCurve5.getSensitivity(), dataCurve5.getTipSize()}, 0L, false);
                            Unit unit10 = Unit.INSTANCE;
                            CurveRenderer.renderCurve$default(this.curveRenderer, dataCurve5, data.getArea(), i7, i48, i17, 1.0f, jNativeGenCurveVertexBuffer4, false, 128, null);
                            i25 = i48;
                            i27 = i17;
                        }
                    } else {
                        i24 = i11;
                        i25 = i16;
                        i27 = i17;
                    }
                } else {
                    i24 = i11;
                    i25 = i16;
                    i26 = i7;
                    i27 = i17;
                    if (dataCurve5.getType() == 28) {
                        ShapeRenderer shapeRenderer2 = this.shapeRenderer;
                        Intrinsics.checkNotNull(dataCurve5, "null cannot be cast to non-null type xc.engine.data.pagedata.DataShape");
                        ShapeRenderer.render$default(shapeRenderer2, (DataShape) dataCurve5, i26, data.getArea(), 1.0f, 0.0f, 0.0f, 48, null);
                        i7 = i26;
                    } else {
                        i7 = i26;
                    }
                }
                i17 = i27;
                i16 = i25;
                i11 = i24;
                i15 = 36160;
            }
            i20 = i11;
            int i49 = i15;
            i21 = i16;
            i22 = i17;
            GLES30.glBindFramebuffer(i49, i);
            GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
            int i410 = i10;
            TileRenderer.drawFullScreen$default(this.tileRenderer, i410, false, false, 0.0f, 14, null);
            i23 = i410;
        } else {
            i20 = i11;
            i21 = i16;
            i22 = i17;
            i23 = i10;
        }
        int i411 = i20;
        TileRenderer.drawRGBAtoBGRA$default(this.tileRenderer, i21, i411, false, 4, null);
        data.setOutBitmap(GLUtils.INSTANCE.getBitmapFromFramebuffer(i21, i19, i18));
        GLUtils.INSTANCE.deleteFramebuffer(i);
        GLUtils.INSTANCE.deleteFramebuffer(i21);
        GLUtils.INSTANCE.deleteFramebuffer(i7);
        GLUtils.INSTANCE.deleteTexture(i411);
        GLUtils.INSTANCE.deleteTexture(i22);
        GLUtils.INSTANCE.deleteTexture(i23);
    }
}
