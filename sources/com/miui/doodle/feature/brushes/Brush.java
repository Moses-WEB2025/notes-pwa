package com.miui.doodle.feature.brushes;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import com.miui.doodle.document.Layer;
import com.miui.richeditor.schema.HtmlParser;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Brush.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b;\n\u0002\u0010\u0015\n\u0002\b.\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000  \u00012\u00020\u0001:\u0002 \u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0014\u0010\u0093\u0001\u001a\u00030\u0094\u00012\b\u0010\u0095\u0001\u001a\u00030\u0096\u0001H\u0002J\u0019\u0010\u0097\u0001\u001a\n\u0012\u0005\u0012\u00030\u0099\u00010\u0098\u00012\b\u0010\u009a\u0001\u001a\u00030\u009b\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0007\"\u0004\b\u0010\u0010\u0005R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000b\"\u0004\b\u0016\u0010\rR\u001a\u0010\u0017\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0007\"\u0004\b\u0019\u0010\u0005R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010!\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010'\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010$\"\u0004\b)\u0010&R\u001a\u0010*\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0007\"\u0004\b,\u0010\u0005R\u001a\u0010-\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010$\"\u0004\b/\u0010&R\u001a\u00100\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010$\"\u0004\b2\u0010&R\u001a\u00103\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u0007\"\u0004\b5\u0010\u0005R\u001a\u00106\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010$\"\u0004\b8\u0010&R\u001a\u00109\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010$\"\u0004\b;\u0010&R\u001a\u0010<\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\u0007\"\u0004\b>\u0010\u0005R\u001a\u0010?\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010\u0007\"\u0004\bA\u0010\u0005R\u001a\u0010B\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010$\"\u0004\bD\u0010&R\u001a\u0010E\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bF\u0010$\"\u0004\bG\u0010&R\u001a\u0010H\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bI\u0010$\"\u0004\bJ\u0010&R\u001a\u0010K\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bL\u0010$\"\u0004\bM\u0010&R\u001a\u0010N\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bO\u0010\u0007\"\u0004\bP\u0010\u0005R\u001a\u0010Q\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010$\"\u0004\bS\u0010&R\u001a\u0010T\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bU\u0010$\"\u0004\bV\u0010&R\u001a\u0010W\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bX\u0010\u0007\"\u0004\bY\u0010\u0005R\u001a\u0010Z\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b[\u0010\u0007\"\u0004\b\\\u0010\u0005R\u000e\u0010]\u001a\u00020^X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010_\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b`\u0010$\"\u0004\ba\u0010&R\u001a\u0010b\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bc\u0010$\"\u0004\bd\u0010&R\u001a\u0010e\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u0010$\"\u0004\bg\u0010&R\u001a\u0010h\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010$\"\u0004\bj\u0010&R\u001a\u0010k\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bl\u0010$\"\u0004\bm\u0010&R\u001a\u0010n\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010$\"\u0004\bp\u0010&R\u001a\u0010q\u001a\u00020\"X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\br\u0010$\"\u0004\bs\u0010&R\u001a\u0010t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bu\u0010\u0007\"\u0004\bv\u0010\u0005R\u001a\u0010w\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bx\u0010\u001c\"\u0004\by\u0010\u001eR\u001a\u0010z\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b{\u0010\u001c\"\u0004\b|\u0010\u001eR\u001a\u0010}\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b~\u0010\u001c\"\u0004\b\u007f\u0010\u001eR\u001d\u0010\u0080\u0001\u001a\u00020\u001bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0081\u0001\u0010\u001c\"\u0005\b\u0082\u0001\u0010\u001eR\u001d\u0010\u0083\u0001\u001a\u00020\u001bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0084\u0001\u0010\u001c\"\u0005\b\u0085\u0001\u0010\u001eR\u001d\u0010\u0086\u0001\u001a\u00020\u001bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0087\u0001\u0010\u001c\"\u0005\b\u0088\u0001\u0010\u001eR\u001d\u0010\u0089\u0001\u001a\u00020\u001bX\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008a\u0001\u0010\u001c\"\u0005\b\u008b\u0001\u0010\u001eR)\u0010\u008c\u0001\u001a\t\u0012\u0004\u0012\u00020\t0\u008d\u0001X\u0086.¢\u0006\u0015\n\u0003\u0010\u0092\u0001\u001a\u0006\b\u008e\u0001\u0010\u008f\u0001\"\u0006\b\u0090\u0001\u0010\u0091\u0001R\u0013\u0010\u009c\u0001\u001a\u00020\u00038F¢\u0006\u0007\u001a\u0005\b\u009d\u0001\u0010\u0007R\u0013\u0010\u009e\u0001\u001a\u00020\"8F¢\u0006\u0007\u001a\u0005\b\u009f\u0001\u0010$¨\u0006¡\u0001"}, d2 = {"Lcom/miui/doodle/feature/brushes/Brush;", "", "id", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(I)V", "getId", "()I", CallMethod.RESULT_ICON, "", "getIcon", "()Ljava/lang/String;", "setIcon", "(Ljava/lang/String;)V", "iconId", "getIconId", "setIconId", "name", "getName", "setName", "preview", "getPreview", "setPreview", "previewId", "getPreviewId", "setPreviewId", "isEraser", "", "()Z", "setEraser", "(Z)V", "mRandom", "Ljava/util/Random;", "angle", "", "getAngle", "()F", "setAngle", "(F)V", "angleJitter", "getAngleJitter", "setAngleJitter", "autoStrokeCount", "getAutoStrokeCount", "setAutoStrokeCount", "autoStrokeDistribution", "getAutoStrokeDistribution", "setAutoStrokeDistribution", "autoStrokeJointPitch", "getAutoStrokeJointPitch", "setAutoStrokeJointPitch", "autoStrokeLength", "getAutoStrokeLength", "setAutoStrokeLength", "autoStrokeStraight", "getAutoStrokeStraight", "setAutoStrokeStraight", "colorPatchAlpha", "getColorPatchAlpha", "setColorPatchAlpha", "coloringType", "getColoringType", "setColoringType", "defaultColor", "getDefaultColor", "setDefaultColor", "jitterBrightness", "getJitterBrightness", "setJitterBrightness", "jitterHue", "getJitterHue", "setJitterHue", "jitterSaturation", "getJitterSaturation", "setJitterSaturation", "lineEndAlphaScale", "getLineEndAlphaScale", "setLineEndAlphaScale", "lineEndFadeLength", "getLineEndFadeLength", "setLineEndFadeLength", "lineEndSizeScale", "getLineEndSizeScale", "setLineEndSizeScale", "lineEndSpeedLength", "getLineEndSpeedLength", "setLineEndSpeedLength", "lineTaperFadeLength", "getLineTaperFadeLength", "setLineTaperFadeLength", "lineTaperStartLength", "getLineTaperStartLength", "setLineTaperStartLength", "maskImageIdArray", "", "textureDepth", "getTextureDepth", "setTextureDepth", "maxSize", "getMaxSize", "setMaxSize", "minSize", "getMinSize", "setMinSize", "size", "getSize", "setSize", "smudgingPatchAlpha", "getSmudgingPatchAlpha", "setSmudgingPatchAlpha", "spacing", "getSpacing", "setSpacing", "spread", "getSpread", "setSpread", "maskPadding", "getMaskPadding", "setMaskPadding", "airBrushMode", "getAirBrushMode", "setAirBrushMode", "traceMode", "getTraceMode", "setTraceMode", "useDeviceAngle", "getUseDeviceAngle", "setUseDeviceAngle", "useFirstJitter", "getUseFirstJitter", "setUseFirstJitter", "useFlowingAngle", "getUseFlowingAngle", "setUseFlowingAngle", "useSmudging", "getUseSmudging", "setUseSmudging", "useSingleLayerStroke", "getUseSingleLayerStroke", "setUseSingleLayerStroke", "maskBitmapIds", "", "getMaskBitmapIds", "()[Ljava/lang/String;", "setMaskBitmapIds", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "loadFromTypedArray", "", HtmlParser.LinkCardElement.TAG, "Landroid/content/res/TypedArray;", "loadBitmap", "", "Landroid/graphics/Bitmap;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "brushType", "getBrushType", "scaledSize", "getScaledSize", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class Brush {
    public static final int BRUSH_TYPE_PHOTO = 1;
    public static final int BRUSH_TYPE_STYLISH = 0;
    public static final int COLORING_TYPE_NORMAL = 0;
    public static final int COLORING_TYPE_REF_IMAGE_ALL_TIME = 1;
    public static final int COLORING_TYPE_REF_IMAGE_INIT_TIME = 2;
    private static final String TAG = "Brush";
    private boolean airBrushMode;
    private float angle;
    private float angleJitter;
    private int autoStrokeCount;
    private float autoStrokeDistribution;
    private float autoStrokeJointPitch;
    private int autoStrokeLength;
    private float autoStrokeStraight;
    private float colorPatchAlpha;
    private int coloringType;
    private int defaultColor;
    private String icon;
    private int iconId;
    private final int id;
    private boolean isEraser;
    private float jitterBrightness;
    private float jitterHue;
    private float jitterSaturation;
    private float lineEndAlphaScale;
    private int lineEndFadeLength;
    private float lineEndSizeScale;
    private float lineEndSpeedLength;
    private int lineTaperFadeLength;
    private int lineTaperStartLength;
    private Random mRandom;
    public String[] maskBitmapIds;
    private int[] maskImageIdArray;
    private int maskPadding;
    private float maxSize;
    private float minSize;
    private String name;
    private String preview;
    private int previewId;
    private float size;
    private float smudgingPatchAlpha;
    private float spacing;
    private float spread;
    private float textureDepth;
    private boolean traceMode;
    private boolean useDeviceAngle;
    private boolean useFirstJitter;
    private boolean useFlowingAngle;
    private boolean useSingleLayerStroke;
    private boolean useSmudging;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int[] BRUSH_MASK_IMAGE_ARRAY_STYLEABLE = {R.styleable.Brush_maskImageArray0, R.styleable.Brush_maskImageArray1, R.styleable.Brush_maskImageArray2, R.styleable.Brush_maskImageArray3, R.styleable.Brush_maskImageArray4, R.styleable.Brush_maskImageArray5, R.styleable.Brush_maskImageArray6, R.styleable.Brush_maskImageArray7, R.styleable.Brush_maskImageArray8, R.styleable.Brush_maskImageArray9};

    public Brush(int i) {
        this.id = i;
    }

    public final int getId() {
        return this.id;
    }

    public final String getIcon() {
        return this.icon;
    }

    public final void setIcon(String str) {
        this.icon = str;
    }

    public final int getIconId() {
        return this.iconId;
    }

    public final void setIconId(int i) {
        this.iconId = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    public final String getPreview() {
        return this.preview;
    }

    public final void setPreview(String str) {
        this.preview = str;
    }

    public final int getPreviewId() {
        return this.previewId;
    }

    public final void setPreviewId(int i) {
        this.previewId = i;
    }

    /* JADX INFO: renamed from: isEraser, reason: from getter */
    public final boolean getIsEraser() {
        return this.isEraser;
    }

    public final void setEraser(boolean z) {
        this.isEraser = z;
    }

    public final float getAngle() {
        return this.angle;
    }

    public final void setAngle(float f) {
        this.angle = f;
    }

    public final float getAngleJitter() {
        return this.angleJitter;
    }

    public final void setAngleJitter(float f) {
        this.angleJitter = f;
    }

    public final int getAutoStrokeCount() {
        return this.autoStrokeCount;
    }

    public final void setAutoStrokeCount(int i) {
        this.autoStrokeCount = i;
    }

    public final float getAutoStrokeDistribution() {
        return this.autoStrokeDistribution;
    }

    public final void setAutoStrokeDistribution(float f) {
        this.autoStrokeDistribution = f;
    }

    public final float getAutoStrokeJointPitch() {
        return this.autoStrokeJointPitch;
    }

    public final void setAutoStrokeJointPitch(float f) {
        this.autoStrokeJointPitch = f;
    }

    public final int getAutoStrokeLength() {
        return this.autoStrokeLength;
    }

    public final void setAutoStrokeLength(int i) {
        this.autoStrokeLength = i;
    }

    public final float getAutoStrokeStraight() {
        return this.autoStrokeStraight;
    }

    public final void setAutoStrokeStraight(float f) {
        this.autoStrokeStraight = f;
    }

    public final float getColorPatchAlpha() {
        return this.colorPatchAlpha;
    }

    public final void setColorPatchAlpha(float f) {
        this.colorPatchAlpha = f;
    }

    public final int getColoringType() {
        return this.coloringType;
    }

    public final void setColoringType(int i) {
        this.coloringType = i;
    }

    public final int getDefaultColor() {
        return this.defaultColor;
    }

    public final void setDefaultColor(int i) {
        this.defaultColor = i;
    }

    public final float getJitterBrightness() {
        return this.jitterBrightness;
    }

    public final void setJitterBrightness(float f) {
        this.jitterBrightness = f;
    }

    public final float getJitterHue() {
        return this.jitterHue;
    }

    public final void setJitterHue(float f) {
        this.jitterHue = f;
    }

    public final float getJitterSaturation() {
        return this.jitterSaturation;
    }

    public final void setJitterSaturation(float f) {
        this.jitterSaturation = f;
    }

    public final float getLineEndAlphaScale() {
        return this.lineEndAlphaScale;
    }

    public final void setLineEndAlphaScale(float f) {
        this.lineEndAlphaScale = f;
    }

    public final int getLineEndFadeLength() {
        return this.lineEndFadeLength;
    }

    public final void setLineEndFadeLength(int i) {
        this.lineEndFadeLength = i;
    }

    public final float getLineEndSizeScale() {
        return this.lineEndSizeScale;
    }

    public final void setLineEndSizeScale(float f) {
        this.lineEndSizeScale = f;
    }

    public final float getLineEndSpeedLength() {
        return this.lineEndSpeedLength;
    }

    public final void setLineEndSpeedLength(float f) {
        this.lineEndSpeedLength = f;
    }

    public final int getLineTaperFadeLength() {
        return this.lineTaperFadeLength;
    }

    public final void setLineTaperFadeLength(int i) {
        this.lineTaperFadeLength = i;
    }

    public final int getLineTaperStartLength() {
        return this.lineTaperStartLength;
    }

    public final void setLineTaperStartLength(int i) {
        this.lineTaperStartLength = i;
    }

    public final float getTextureDepth() {
        return this.textureDepth;
    }

    public final void setTextureDepth(float f) {
        this.textureDepth = f;
    }

    public final float getMaxSize() {
        return this.maxSize;
    }

    public final void setMaxSize(float f) {
        this.maxSize = f;
    }

    public final float getMinSize() {
        return this.minSize;
    }

    public final void setMinSize(float f) {
        this.minSize = f;
    }

    public final float getSize() {
        return this.size;
    }

    public final void setSize(float f) {
        this.size = f;
    }

    public final float getSmudgingPatchAlpha() {
        return this.smudgingPatchAlpha;
    }

    public final void setSmudgingPatchAlpha(float f) {
        this.smudgingPatchAlpha = f;
    }

    public final float getSpacing() {
        return this.spacing;
    }

    public final void setSpacing(float f) {
        this.spacing = f;
    }

    public final float getSpread() {
        return this.spread;
    }

    public final void setSpread(float f) {
        this.spread = f;
    }

    public final int getMaskPadding() {
        return this.maskPadding;
    }

    public final void setMaskPadding(int i) {
        this.maskPadding = i;
    }

    public final boolean getAirBrushMode() {
        return this.airBrushMode;
    }

    public final void setAirBrushMode(boolean z) {
        this.airBrushMode = z;
    }

    public final boolean getTraceMode() {
        return this.traceMode;
    }

    public final void setTraceMode(boolean z) {
        this.traceMode = z;
    }

    public final boolean getUseDeviceAngle() {
        return this.useDeviceAngle;
    }

    public final void setUseDeviceAngle(boolean z) {
        this.useDeviceAngle = z;
    }

    public final boolean getUseFirstJitter() {
        return this.useFirstJitter;
    }

    public final void setUseFirstJitter(boolean z) {
        this.useFirstJitter = z;
    }

    public final boolean getUseFlowingAngle() {
        return this.useFlowingAngle;
    }

    public final void setUseFlowingAngle(boolean z) {
        this.useFlowingAngle = z;
    }

    public final boolean getUseSmudging() {
        return this.useSmudging;
    }

    public final void setUseSmudging(boolean z) {
        this.useSmudging = z;
    }

    public final boolean getUseSingleLayerStroke() {
        return this.useSingleLayerStroke;
    }

    public final void setUseSingleLayerStroke(boolean z) {
        this.useSingleLayerStroke = z;
    }

    public final String[] getMaskBitmapIds() {
        String[] strArr = this.maskBitmapIds;
        if (strArr != null) {
            return strArr;
        }
        Intrinsics.throwUninitializedPropertyAccessException("maskBitmapIds");
        return null;
    }

    public final void setMaskBitmapIds(String[] strArr) {
        Intrinsics.checkNotNullParameter(strArr, "<set-?>");
        this.maskBitmapIds = strArr;
    }

    /* JADX INFO: compiled from: Brush.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J2\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\tH\u0002J\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/miui/doodle/feature/brushes/Brush$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "BRUSH_MASK_IMAGE_ARRAY_STYLEABLE", "", "BRUSH_TYPE_PHOTO", "", "BRUSH_TYPE_STYLISH", "COLORING_TYPE_NORMAL", "COLORING_TYPE_REF_IMAGE_ALL_TIME", "COLORING_TYPE_REF_IMAGE_INIT_TIME", "decodeScaledExpandResource", "Landroid/graphics/Bitmap;", "res", "Landroid/content/res/Resources;", "id", Layer.KEY_WIDTH, Layer.KEY_HEIGHT, "padding", "parseStyleData", "", "Lcom/miui/doodle/feature/brushes/Brush;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "styleArray", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Bitmap decodeScaledExpandResource(Resources res, int id, int width, int height, int padding) {
            Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(res, id);
            if (bitmapDecodeResource == null) {
                return null;
            }
            int i = padding * 2;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i + width, i + height, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.drawBitmap(bitmapDecodeResource, new Rect(0, 0, bitmapDecodeResource.getWidth(), bitmapDecodeResource.getHeight()), new Rect(padding, padding, width + padding, height + padding), (Paint) null);
            canvas.setBitmap(null);
            if (Intrinsics.areEqual(bitmapDecodeResource, bitmapCreateBitmap)) {
                return bitmapCreateBitmap;
            }
            bitmapDecodeResource.recycle();
            return bitmapCreateBitmap;
        }

        public final List<Brush> parseStyleData(Context context, int[] styleArray) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(styleArray, "styleArray");
            ArrayList arrayList = new ArrayList(styleArray.length);
            int length = styleArray.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int i3 = styleArray[i];
                int i4 = i2 + 1;
                Brush brush = new Brush(i2);
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i3, R.styleable.Brush);
                Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "obtainStyledAttributes(...)");
                brush.loadFromTypedArray(typedArrayObtainStyledAttributes);
                typedArrayObtainStyledAttributes.recycle();
                arrayList.add(brush);
                i++;
                i2 = i4;
            }
            return arrayList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadFromTypedArray(TypedArray a) {
        this.angle = a.getFloat(R.styleable.Brush_angle, 0.0f);
        this.angleJitter = a.getFloat(R.styleable.Brush_angleJitter, 0.0f);
        this.autoStrokeCount = a.getInt(R.styleable.Brush_autoStrokeCount, 0);
        this.autoStrokeDistribution = a.getFloat(R.styleable.Brush_autoStrokeDistribution, 0.0f);
        this.autoStrokeJointPitch = a.getFloat(R.styleable.Brush_autoStrokeJointPitch, 0.0f);
        this.autoStrokeLength = a.getInt(R.styleable.Brush_autoStrokeLength, 1);
        this.autoStrokeStraight = a.getFloat(R.styleable.Brush_autoStrokeStraight, 0.0f);
        this.colorPatchAlpha = a.getFloat(R.styleable.Brush_colorPatchAlpha, 0.0f);
        this.coloringType = a.getInt(R.styleable.Brush_coloringType, 0);
        this.iconId = a.getResourceId(R.styleable.Brush_icon, 0);
        this.isEraser = a.getBoolean(R.styleable.Brush_isEraser, false);
        this.jitterBrightness = a.getFloat(R.styleable.Brush_jitterBrightness, 0.0f);
        this.jitterHue = a.getFloat(R.styleable.Brush_jitterHue, 0.0f);
        this.jitterSaturation = a.getFloat(R.styleable.Brush_jitterSaturation, 0.0f);
        this.lineEndAlphaScale = a.getFloat(R.styleable.Brush_lineEndAlphaScale, 0.0f);
        this.lineEndFadeLength = a.getInt(R.styleable.Brush_lineEndFadeLength, 0);
        this.lineEndSizeScale = a.getFloat(R.styleable.Brush_lineEndSizeScale, 0.0f);
        this.lineEndSpeedLength = a.getFloat(R.styleable.Brush_lineEndSpeedLength, 0.0f);
        this.lineTaperFadeLength = a.getInt(R.styleable.Brush_lineTaperFadeLength, 0);
        this.lineTaperStartLength = a.getInt(R.styleable.Brush_lineTaperStartLength, 0);
        int i = 0;
        while (true) {
            int[] iArr = BRUSH_MASK_IMAGE_ARRAY_STYLEABLE;
            Intrinsics.checkNotNull(iArr);
            if (i >= iArr.length) {
                break;
            }
            Intrinsics.checkNotNull(iArr);
            if (a.getResourceId(iArr[i], 0) == 0) {
                break;
            } else {
                i++;
            }
        }
        this.maskImageIdArray = new int[i];
        int i2 = 0;
        while (true) {
            int[] iArr2 = this.maskImageIdArray;
            int[] iArr3 = null;
            if (iArr2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("maskImageIdArray");
                iArr2 = null;
            }
            if (i2 < iArr2.length) {
                int[] iArr4 = this.maskImageIdArray;
                if (iArr4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("maskImageIdArray");
                } else {
                    iArr3 = iArr4;
                }
                int[] iArr5 = BRUSH_MASK_IMAGE_ARRAY_STYLEABLE;
                Intrinsics.checkNotNull(iArr5);
                iArr3[i2] = a.getResourceId(iArr5[i2], 0);
                i2++;
            } else {
                this.maxSize = a.getDimension(R.styleable.Brush_maxSize, 0.0f);
                this.minSize = a.getDimension(R.styleable.Brush_minSize, 0.0f);
                this.name = a.getString(R.styleable.Brush_name);
                this.previewId = a.getResourceId(R.styleable.Brush_preview, 0);
                this.size = a.getDimension(R.styleable.Brush_size, 0.0f);
                this.smudgingPatchAlpha = a.getFloat(R.styleable.Brush_smudgingPatchAlpha, 0.0f);
                this.spacing = a.getFloat(R.styleable.Brush_spacing, 0.0f);
                this.spread = a.getFloat(R.styleable.Brush_spread, 0.0f);
                this.textureDepth = a.getFloat(R.styleable.Brush_textureDepth, 0.0f);
                this.airBrushMode = a.getBoolean(R.styleable.Brush_airBrushMode, false);
                this.traceMode = a.getBoolean(R.styleable.Brush_traceMode, false);
                this.useDeviceAngle = a.getBoolean(R.styleable.Brush_useDeviceAngle, false);
                this.useFirstJitter = a.getBoolean(R.styleable.Brush_useFirstJitter, false);
                this.useFlowingAngle = a.getBoolean(R.styleable.Brush_useFlowingAngle, false);
                this.useSmudging = a.getBoolean(R.styleable.Brush_useSmudging, false);
                this.useSingleLayerStroke = a.getBoolean(R.styleable.Brush_useSingleLayerStroke, false);
                this.defaultColor = a.getColor(R.styleable.Brush_dColor, 0);
                this.maskPadding = 0;
                this.mRandom = new Random(System.currentTimeMillis());
                return;
            }
        }
    }

    public final List<Bitmap> loadBitmap(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int[] iArr = this.maskImageIdArray;
        if (iArr == null) {
            Intrinsics.throwUninitializedPropertyAccessException("maskImageIdArray");
            iArr = null;
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            Companion companion = INSTANCE;
            Resources resources = context.getResources();
            Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
            float f = this.size;
            arrayList.add(companion.decodeScaledExpandResource(resources, i, ((int) f) * 5, ((int) f) * 5, this.maskPadding));
        }
        return CollectionsKt.filterNotNull(arrayList);
    }

    public final int getBrushType() {
        return this.coloringType == 0 ? 0 : 1;
    }

    public final float getScaledSize() {
        float f = this.size;
        float f2 = this.minSize;
        return (f - f2) / (this.maxSize - f2);
    }
}
