package com.miui.doodle.feature.draw;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import com.miui.doodle.document.Layer;
import com.miui.doodle.utils.UIUtils;
import com.xiaomi.mirror.synergy.CallMethod;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DoodlePenManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0016\u0010\u001a\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010 \u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010!\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010\"\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010#\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010$\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010%\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010&\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010'\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010(\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010)\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010*\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u000e\u0010+\u001a\u00020\u00052\u0006\u0010,\u001a\u00020-J\u0010\u0010.\u001a\u00020\u00052\u0006\u0010,\u001a\u00020-H\u0002J(\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u0002042\u0006\u00106\u001a\u000204H\u0002J\u0010\u00107\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u00108\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0011\u0010\u0010\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0011\u0010\u0012\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007R\u0011\u0010\u0014\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0007R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u00069"}, d2 = {"Lcom/miui/doodle/feature/draw/DoodlePenManager;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "pencil", "Lcom/miui/doodle/feature/draw/DoodlePen;", "getPencil", "()Lcom/miui/doodle/feature/draw/DoodlePen;", "text", "getText", DoodleBitmap.KEY_BITMAP, "getBitmap", "normal", "getNormal", "markPen", "getMarkPen", "watercolor", "getWatercolor", "eraser", "getEraser", "elementEraser", "getElementEraser", "scale", "", "getScale", "()F", "setScale", "(F)V", "initAllPens", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "initPencil", "initPencilMaskBitmap", "initWatercolor", "initWatercolorMaskBitmap", "initMarkPen", "initMarkPenMaskBitmap", "initNormal", "initNormalMaskBitmap", "initEraser", "initElementEraser", "initText", CallMethod.METHOD_GET, "type", "", "createPen", "decodeScaledExpandResource", "Landroid/graphics/Bitmap;", "res", "Landroid/content/res/Resources;", "id", "", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "initAllPensSize", "initBrushMaskBitmap", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DoodlePenManager {
    public static final DoodlePenManager INSTANCE;
    private static final DoodlePen bitmap;
    private static final DoodlePen elementEraser;
    private static final DoodlePen eraser;
    private static final DoodlePen markPen;
    private static final DoodlePen normal;
    private static final DoodlePen pencil;
    private static float scale;
    private static final DoodlePen text;
    private static final DoodlePen watercolor;

    private DoodlePenManager() {
    }

    static {
        DoodlePenManager doodlePenManager = new DoodlePenManager();
        INSTANCE = doodlePenManager;
        pencil = doodlePenManager.createPen(DoodlePen.TYPE_PENCIL);
        text = doodlePenManager.createPen(DoodlePen.TYPE_TEXT);
        bitmap = doodlePenManager.createPen(DoodlePen.TYPE_BITMAP);
        normal = doodlePenManager.createPen(DoodlePen.TYPE_NORMAL);
        markPen = doodlePenManager.createPen(DoodlePen.TYPE_MARK_PEN);
        watercolor = doodlePenManager.createPen(DoodlePen.TYPE_WATERCOLOR);
        eraser = doodlePenManager.createPen(DoodlePen.TYPE_ERASER);
        elementEraser = doodlePenManager.createPen(DoodlePen.TYPE_ELEMENT_ERASER);
        scale = 1.0f;
    }

    public final DoodlePen getPencil() {
        return pencil;
    }

    public final DoodlePen getText() {
        return text;
    }

    public final DoodlePen getBitmap() {
        return bitmap;
    }

    public final DoodlePen getNormal() {
        return normal;
    }

    public final DoodlePen getMarkPen() {
        return markPen;
    }

    public final DoodlePen getWatercolor() {
        return watercolor;
    }

    public final DoodlePen getEraser() {
        return eraser;
    }

    public final DoodlePen getElementEraser() {
        return elementEraser;
    }

    public final float getScale() {
        return scale;
    }

    public final void setScale(float f) {
        scale = f;
    }

    public final void initAllPens(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        initPencil(context);
        initWatercolor(context);
        initMarkPen(context);
        initNormal(context);
        initEraser(context);
        initElementEraser(context);
        initText(context);
    }

    public final void setScale(Context context, float scale2) {
        Intrinsics.checkNotNullParameter(context, "context");
        scale = scale2;
        initAllPensSize(context);
    }

    private final void initPencil(Context context) {
        DoodlePen doodlePen = pencil;
        doodlePen.setAlpha(1.0f);
        doodlePen.setSize(UIUtils.INSTANCE.dip2px(context, 2.0f) * scale);
        doodlePen.setColorInt(-16777216);
        doodlePen.setStrokeLevel(2);
        doodlePen.setAngle(0.32045645f);
        doodlePen.setAngleJitter(0.6f);
        doodlePen.setUseFlowingAngle(false);
        doodlePen.setSpacing(0.15f);
        doodlePen.setColorPatchAlpha(0.85f);
        doodlePen.setSpread(0.11f);
        doodlePen.setAirBrushMode(false);
        doodlePen.setLineEndSpeedLength(31.0f);
        doodlePen.setUseSimpleTexture(false);
        initPencilMaskBitmap(context);
    }

    private final void initPencilMaskBitmap(Context context) {
        DoodlePen doodlePen = pencil;
        int size = (int) (doodlePen.getSize() * 5);
        Resources resources = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        PenTextureManager.INSTANCE.addTexture(PenTextureManager.HardPencil00, decodeScaledExpandResource(resources, R.drawable.doodle_brush_pencil_03, size, size));
        doodlePen.setMaskBitmapIds(new String[]{PenTextureManager.HardPencil00});
    }

    private final void initWatercolor(Context context) {
        DoodlePen doodlePen = watercolor;
        doodlePen.setAlpha(1.0f);
        doodlePen.setSize(UIUtils.INSTANCE.dip2px(context, 11.5f) * scale);
        doodlePen.setColorInt(-12539393);
        doodlePen.setStrokeLevel(3);
        doodlePen.setAngle(0.0f);
        doodlePen.setAngleJitter(0.0f);
        doodlePen.setUseFlowingAngle(false);
        doodlePen.setSpacing(0.05f);
        doodlePen.setColorPatchAlpha(0.4f);
        doodlePen.setSpread(0.02235f);
        doodlePen.setAirBrushMode(false);
        doodlePen.setLineEndSpeedLength(0.0f);
        doodlePen.setUseSimpleTexture(false);
        initWatercolorMaskBitmap(context);
    }

    private final void initWatercolorMaskBitmap(Context context) {
        DoodlePen doodlePen = watercolor;
        int size = (int) (doodlePen.getSize() * 5);
        Resources resources = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        Bitmap bitmapDecodeScaledExpandResource = decodeScaledExpandResource(resources, R.drawable.doodle_brush_watercolor_01, size, size);
        Resources resources2 = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources2, "getResources(...)");
        Bitmap bitmapDecodeScaledExpandResource2 = decodeScaledExpandResource(resources2, R.drawable.doodle_brush_watercolor_02, size, size);
        Resources resources3 = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources3, "getResources(...)");
        Bitmap bitmapDecodeScaledExpandResource3 = decodeScaledExpandResource(resources3, R.drawable.doodle_brush_watercolor_03, size, size);
        Resources resources4 = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources4, "getResources(...)");
        Bitmap bitmapDecodeScaledExpandResource4 = decodeScaledExpandResource(resources4, R.drawable.doodle_brush_watercolor_04, size, size);
        Resources resources5 = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources5, "getResources(...)");
        Bitmap bitmapDecodeScaledExpandResource5 = decodeScaledExpandResource(resources5, R.drawable.doodle_brush_watercolor_05, size, size);
        PenTextureManager.INSTANCE.addTexture(PenTextureManager.WaterColor00, bitmapDecodeScaledExpandResource);
        PenTextureManager.INSTANCE.addTexture(PenTextureManager.WaterColor01, bitmapDecodeScaledExpandResource2);
        PenTextureManager.INSTANCE.addTexture(PenTextureManager.WaterColor02, bitmapDecodeScaledExpandResource3);
        PenTextureManager.INSTANCE.addTexture(PenTextureManager.WaterColor03, bitmapDecodeScaledExpandResource4);
        PenTextureManager.INSTANCE.addTexture(PenTextureManager.WaterColor04, bitmapDecodeScaledExpandResource5);
        doodlePen.setMaskBitmapIds(new String[]{PenTextureManager.WaterColor00, PenTextureManager.WaterColor01, PenTextureManager.WaterColor02, PenTextureManager.WaterColor03, PenTextureManager.WaterColor04});
    }

    private final void initMarkPen(Context context) {
        DoodlePen doodlePen = markPen;
        doodlePen.setAlpha(1.0f);
        doodlePen.setSize(UIUtils.INSTANCE.dip2px(context, 7.0f) * scale);
        doodlePen.setColorInt(-22208);
        doodlePen.setStrokeLevel(3);
        doodlePen.setAngle(0.39082968f);
        doodlePen.setAngleJitter(0.0f);
        doodlePen.setUseFlowingAngle(false);
        doodlePen.setSpacing(0.07f);
        doodlePen.setColorPatchAlpha(0.5f);
        doodlePen.setSpread(0.0f);
        doodlePen.setAirBrushMode(false);
        doodlePen.setLineEndSpeedLength(0.0f);
        doodlePen.setUseSimpleTexture(false);
        initMarkPenMaskBitmap(context);
    }

    private final void initMarkPenMaskBitmap(Context context) {
        DoodlePen doodlePen = markPen;
        int size = (int) (doodlePen.getSize() * 5);
        Resources resources = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        PenTextureManager.INSTANCE.addTexture(PenTextureManager.MarkPen00, decodeScaledExpandResource(resources, R.drawable.doodle_brush_markpen_01, size, size));
        doodlePen.setMaskBitmapIds(new String[]{PenTextureManager.MarkPen00});
    }

    private final void initNormal(Context context) {
        DoodlePen doodlePen = normal;
        doodlePen.setAlpha(1.0f);
        doodlePen.setSize(UIUtils.INSTANCE.dip2px(context, 4.0f) * scale);
        doodlePen.setColorInt(-9187011);
        doodlePen.setStrokeLevel(1);
        doodlePen.setAngle(0.0f);
        doodlePen.setAngleJitter(0.0f);
        doodlePen.setUseFlowingAngle(false);
        doodlePen.setSpacing(0.12f);
        doodlePen.setColorPatchAlpha(1.0f);
        doodlePen.setSpread(0.0f);
        doodlePen.setAirBrushMode(false);
        doodlePen.setLineEndSpeedLength(31.0f);
        doodlePen.setUseSimpleTexture(true);
        initNormalMaskBitmap(context);
    }

    private final void initNormalMaskBitmap(Context context) {
        DoodlePen doodlePen = normal;
        int size = (int) (doodlePen.getSize() * 5);
        Resources resources = context.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
        PenTextureManager.INSTANCE.addTexture(PenTextureManager.Pen00, decodeScaledExpandResource(resources, R.drawable.doodle_brush_pen_01, size, size));
        doodlePen.setMaskBitmapIds(new String[]{PenTextureManager.Pen00});
    }

    private final void initEraser(Context context) {
        DoodlePen doodlePen = eraser;
        doodlePen.setAlpha(1.0f);
        doodlePen.setColorInt(-16777216);
        doodlePen.setStrokeLevel(5);
        doodlePen.setAngle(0.0f);
        doodlePen.setAngleJitter(0.0f);
        doodlePen.setUseFlowingAngle(false);
        doodlePen.setSize(UIUtils.INSTANCE.dip2px(context, 8.0f) * scale);
        doodlePen.setSpacing(0.12f);
        doodlePen.setColorPatchAlpha(1.0f);
        doodlePen.setSpread(0.0f);
        doodlePen.setAirBrushMode(false);
        doodlePen.setLineEndSpeedLength(0.0f);
        doodlePen.setUseSimpleTexture(true);
    }

    private final void initElementEraser(Context context) {
        DoodlePen doodlePen = elementEraser;
        doodlePen.setAlpha(1.0f);
        doodlePen.setColorInt(-16777216);
        doodlePen.setStrokeLevel(5);
        doodlePen.setAngle(0.0f);
        doodlePen.setAngleJitter(0.0f);
        doodlePen.setUseFlowingAngle(false);
        doodlePen.setSize(UIUtils.INSTANCE.dip2px(context, 8.0f) * scale);
        doodlePen.setSpacing(0.12f);
        doodlePen.setColorPatchAlpha(1.0f);
        doodlePen.setSpread(0.0f);
        doodlePen.setAirBrushMode(false);
        doodlePen.setLineEndSpeedLength(0.0f);
        doodlePen.setUseSimpleTexture(true);
    }

    private final void initText(Context context) {
        DoodlePen doodlePen = text;
        doodlePen.setAlpha(1.0f);
        doodlePen.setSize(UIUtils.INSTANCE.dip2px(context, 18.0f) * scale);
        doodlePen.setColorInt(-16777216);
        doodlePen.setColorPatchAlpha(1.0f);
        doodlePen.setStrokeLevel(1);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final DoodlePen get(String type) {
        Intrinsics.checkNotNullParameter(type, "type");
        switch (type.hashCode()) {
            case -2054401991:
                if (type.equals(DoodlePen.TYPE_NORMAL)) {
                    return normal;
                }
                break;
            case -1798555596:
                if (type.equals(DoodlePen.TYPE_MARK_PEN)) {
                    return markPen;
                }
                break;
            case -1236652428:
                if (type.equals(DoodlePen.TYPE_WATERCOLOR)) {
                    return watercolor;
                }
                break;
            case -1156528557:
                if (type.equals(DoodlePen.TYPE_PENCIL)) {
                    return pencil;
                }
                break;
            case 234237044:
                if (type.equals(DoodlePen.TYPE_ERASER)) {
                    return eraser;
                }
                break;
            case 234237045:
                if (type.equals(DoodlePen.TYPE_ELEMENT_ERASER)) {
                    return elementEraser;
                }
                break;
            case 246813235:
                if (type.equals(DoodlePen.TYPE_TEXT)) {
                    return text;
                }
                break;
        }
        return pencil;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final DoodlePen createPen(String type) {
        switch (type.hashCode()) {
            case -2054401991:
                if (type.equals(DoodlePen.TYPE_NORMAL)) {
                    return new DoodlePen(type, 1.0f, 9187011, 1, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32752, null);
                }
                break;
            case -1931585807:
                if (type.equals(DoodlePen.TYPE_BITMAP)) {
                    return new DoodlePen(type, 0.0f, 0, 0, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32766, null);
                }
                break;
            case -1798555596:
                if (type.equals(DoodlePen.TYPE_MARK_PEN)) {
                    return new DoodlePen(type, 1.0f, R2.styleable.TextEffects_textFillColor, 5, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32752, null);
                }
                break;
            case -1236652428:
                if (type.equals(DoodlePen.TYPE_WATERCOLOR)) {
                    return new DoodlePen(type, 1.0f, 12539393, 3, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32752, null);
                }
                break;
            case -1156528557:
                if (type.equals(DoodlePen.TYPE_PENCIL)) {
                    return new DoodlePen(type, 1.0f, -16777216, 2, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32752, null);
                }
                break;
            case 234237044:
                if (type.equals(DoodlePen.TYPE_ERASER)) {
                    return new DoodlePen(type, 1.0f, 0, 1, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32752, null);
                }
                break;
            case 234237045:
                if (type.equals(DoodlePen.TYPE_ELEMENT_ERASER)) {
                    return new DoodlePen(type, 0.0f, 0, 0, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32766, null);
                }
                break;
            case 246813235:
                if (type.equals(DoodlePen.TYPE_TEXT)) {
                    return new DoodlePen(type, 0.0f, 0, 0, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32766, null);
                }
                break;
            case 616576326:
                if (type.equals(DoodlePen.TYPE_BRUSH)) {
                    return new DoodlePen(type, 1.0f, -16777216, 1, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32752, null);
                }
                break;
        }
        return new DoodlePen(type, 1.0f, -16777216, 1, 0.0f, 0.0f, false, 0.0f, 0.0f, 0.0f, 0.0f, false, 0.0f, false, null, 32752, null);
    }

    private final Bitmap decodeScaledExpandResource(Resources res, int id, int width, int height) {
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(res, id);
        Intrinsics.checkNotNull(bitmapDecodeResource);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(Math.max(1, width), Math.max(1, height), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmapDecodeResource, new Rect(0, 0, bitmapDecodeResource.getWidth(), bitmapDecodeResource.getHeight()), new Rect(0, 0, width, height), (Paint) null);
        canvas.setBitmap(null);
        if (Intrinsics.areEqual(bitmapDecodeResource, bitmapCreateBitmap)) {
            return bitmapCreateBitmap;
        }
        bitmapDecodeResource.recycle();
        return bitmapCreateBitmap;
    }

    private final void initAllPensSize(Context context) {
        pencil.setSize(UIUtils.INSTANCE.dip2px(context, 2.0f) * scale);
        watercolor.setSize(UIUtils.INSTANCE.dip2px(context, 11.5f) * scale);
        markPen.setSize(UIUtils.INSTANCE.dip2px(context, 7.0f) * scale);
        normal.setSize(UIUtils.INSTANCE.dip2px(context, 4.0f) * scale);
        eraser.setSize(UIUtils.INSTANCE.dip2px(context, 8.0f) * scale);
        elementEraser.setSize(UIUtils.INSTANCE.dip2px(context, 8.0f) * scale);
        text.setSize(UIUtils.INSTANCE.dip2px(context, 18.0f) * scale);
        initBrushMaskBitmap(context);
    }

    private final void initBrushMaskBitmap(Context context) {
        initPencilMaskBitmap(context);
        initWatercolorMaskBitmap(context);
        initMarkPenMaskBitmap(context);
        initNormalMaskBitmap(context);
    }
}
