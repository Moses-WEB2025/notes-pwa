package xc.engine.core.graphics.engine.systems;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.os.LocaleList;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.SizeF;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.core.BaseEngineLayer;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.ImageRenderer;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.operate.edit.SpannedEditOperator;
import xc.richtext.operate.edit.SpannedSelectObject;
import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: compiled from: TextSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 '2\u00020\u0001:\u0003'()B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0010\u001a\u00020\u0011H\u0014J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J(\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00142\b\b\u0002\u0010\u001a\u001a\u00020\u000fJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u0014H\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001e\u001a\u00020\rH\u0002J\u0006\u0010\u001f\u001a\u00020\u000fJ\u000e\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\rJ\u000e\u0010\"\u001a\u00020\n2\u0006\u0010!\u001a\u00020\rJ\u0018\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020%H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TextSystem;", "Lxc/engine/core/graphics/engine/core/BaseEngineLayer;", "engine", "Lxc/engine/core/graphics/engine/MiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lxc/engine/core/graphics/engine/MiCurveEngine;)V", "imgRenderer", "Lxc/engine/core/graphics/engine/renderers/ImageRenderer;", "textCacheMap", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lxc/engine/core/graphics/engine/systems/TextSystem$TextCache;", "editingSpan", "Lxc/richtext/data/SpannedData;", "bIsEmpty", "", "onDestroy", "", "onRenderLayer", "screenRect", "Landroid/graphics/RectF;", "renderArea", "fbId", "fbSize", "Landroid/graphics/Point;", "areaWS", "bNeedClear", "getSpansToRender", "Lxc/engine/core/graphics/engine/systems/TextSystem$QuerySpansOutData;", "generateSpanTextCache", "spanData", "isEmpty", "setEditingTextSpan", "span", "getCachedTextTextureId", "sizeNearlyEqual", "size1", "Landroid/util/SizeF;", "size2", "Companion", "TextCache", "QuerySpansOutData", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TextSystem extends BaseEngineLayer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "TextSystem";
    private boolean bIsEmpty;
    private SpannedData editingSpan;
    private final ImageRenderer imgRenderer;
    private final ConcurrentHashMap<Integer, TextCache> textCacheMap;

    /* JADX INFO: compiled from: TextSystem.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TextSystem$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "kotlin.jvm.PlatformType", "getBitmapFromTextSpan", "Landroid/graphics/Bitmap;", "spanData", "Lxc/richtext/data/SpannedData;", "bIsBgDark", "", "drawRatio", "", "obtainBuilder", "Landroid/text/StaticLayout$Builder;", "spannedDataVar", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Bitmap getBitmapFromTextSpan(SpannedData spanData, boolean bIsBgDark, float drawRatio) throws InterruptedException {
            StaticLayout staticLayoutBuild;
            Intrinsics.checkNotNullParameter(spanData, "spanData");
            if (spanData.getType() != 11) {
                return null;
            }
            Log.d(TextSystem.TAG, "xl-- getBitmapFromTextSpan text: " + spanData.getText());
            String text = spanData.getText();
            if (text == null) {
                text = "";
            }
            if (text.length() == 0) {
                return null;
            }
            CountDownLatch countDownLatch = spanData.saveRectChangeLatch;
            if (countDownLatch != null) {
                countDownLatch.await(5L, TimeUnit.SECONDS);
            }
            RectF rectF = spanData.getRectF();
            Point limitedTextureSize$default = GLUtils.Companion.getLimitedTextureSize$default(GLUtils.INSTANCE, rectF.width() * drawRatio, rectF.height() * drawRatio, 0.0f, 4, null);
            if (limitedTextureSize$default.x > 0 && limitedTextureSize$default.y > 0) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(limitedTextureSize$default.x, limitedTextureSize$default.y, Bitmap.Config.ARGB_8888);
                Log.d(TextSystem.TAG, "xl-- getBitmapFromTextSpan bitmap: " + bitmapCreateBitmap.getWidth() + " x " + bitmapCreateBitmap.getHeight());
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                StaticLayout.Builder builderObtainBuilder = obtainBuilder(spanData, bIsBgDark);
                if (builderObtainBuilder != null && (staticLayoutBuild = builderObtainBuilder.build()) != null) {
                    Log.d(TextSystem.TAG, "xl-- getBitmapFromTextSpan textLayout: " + staticLayoutBuild.getWidth() + " x " + staticLayoutBuild.getHeight());
                    int iSave = canvas.save();
                    canvas.translate(0.0f, 0.0f);
                    try {
                        float width = limitedTextureSize$default.x / staticLayoutBuild.getWidth();
                        canvas.scale(width, width);
                        staticLayoutBuild.draw(canvas);
                        return bitmapCreateBitmap;
                    } finally {
                        canvas.restoreToCount(iSave);
                    }
                }
            }
            return null;
        }

        private final StaticLayout.Builder obtainBuilder(SpannedData spannedDataVar, boolean bIsBgDark) {
            RectF rectF = spannedDataVar.getRectF();
            String str = spannedDataVar.mText;
            String str2 = str;
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            float fWidth = rectF.width() / 1000.0f;
            TextPaint textPaint = new TextPaint();
            textPaint.setStrokeJoin(Paint.Join.ROUND);
            textPaint.setStrokeCap(Paint.Cap.ROUND);
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setAntiAlias(true);
            textPaint.setTextLocales(LocaleList.getDefault());
            textPaint.setTextSize(spannedDataVar.mTextSize / fWidth);
            int i = spannedDataVar.mTextColor;
            if (bIsBgDark) {
                if (i == -16777216) {
                    i = -1;
                } else if (i == -1) {
                    i = -16777216;
                }
            }
            textPaint.setColor(i);
            StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(str2, 0, str.length(), textPaint, 1000);
            Intrinsics.checkNotNullExpressionValue(builderObtain, "obtain(...)");
            StaticLayout.Builder useLineSpacingFromFallbacks = builderObtain.setLineSpacing(0.0f, 1.0f).setUseLineSpacingFromFallbacks(true);
            Locale locale = Locale.getDefault();
            Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
            useLineSpacingFromFallbacks.setTextDirection(TextUtils.getLayoutDirectionFromLocale(locale) == 1 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
            return builderObtain;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextSystem(MiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.imgRenderer = engine.getImgRenderer();
        this.textCacheMap = new ConcurrentHashMap<>();
        this.bIsEmpty = true;
    }

    /* JADX INFO: compiled from: TextSystem.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u001c\u001a\u00020\u0017J\b\u0010\u001d\u001a\u00020\u0005H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006\u001e"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TextSystem$TextCache;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "text", "", "getText", "()Ljava/lang/String;", "setText", "(Ljava/lang/String;)V", "sizeF", "Landroid/util/SizeF;", "getSizeF", "()Landroid/util/SizeF;", "setSizeF", "(Landroid/util/SizeF;)V", "textureId", "", "getTextureId", "()I", "setTextureId", "(I)V", "bIsBgDark", "", "getBIsBgDark", "()Z", "setBIsBgDark", "(Z)V", "isNotValid", "toString", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class TextCache {
        private boolean bIsBgDark;
        private int textureId;
        private String text = "";
        private SizeF sizeF = new SizeF(0.0f, 0.0f);

        public final String getText() {
            return this.text;
        }

        public final void setText(String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.text = str;
        }

        public final SizeF getSizeF() {
            return this.sizeF;
        }

        public final void setSizeF(SizeF sizeF) {
            Intrinsics.checkNotNullParameter(sizeF, "<set-?>");
            this.sizeF = sizeF;
        }

        public final int getTextureId() {
            return this.textureId;
        }

        public final void setTextureId(int i) {
            this.textureId = i;
        }

        public final boolean getBIsBgDark() {
            return this.bIsBgDark;
        }

        public final void setBIsBgDark(boolean z) {
            this.bIsBgDark = z;
        }

        public final boolean isNotValid() {
            return this.text.length() == 0 || this.sizeF.getWidth() <= 0.0f || this.sizeF.getHeight() < 0.0f || this.textureId <= 0;
        }

        public String toString() {
            return "[TextCache] text: " + this.text + ", textureId: " + this.textureId + " , sizeF: " + this.sizeF;
        }
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer, xc.engine.core.graphics.engine.core.BaseStruct
    protected void onDestroy() {
        for (Map.Entry<Integer, TextCache> entry : this.textCacheMap.entrySet()) {
            entry.getKey().intValue();
            GLUtils.INSTANCE.deleteTexture(entry.getValue().getTextureId());
        }
        this.textCacheMap.clear();
        super.onDestroy();
    }

    @Override // xc.engine.core.graphics.engine.core.BaseEngineLayer
    protected boolean onRenderLayer(RectF screenRect) {
        boolean z;
        RectF rectF;
        TextCache textCache;
        Intrinsics.checkNotNullParameter(screenRect, "screenRect");
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        GLES30.glViewport(0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
        GLES30.glBindFramebuffer(36160, getLayerFramebufferId());
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        this.bIsEmpty = true;
        float saveRatio = getCurveEngine().getSaveRatio();
        QuerySpansOutData spansToRender = getSpansToRender(screenRect);
        for (SpannedData spannedData : spansToRender.getAllSpans()) {
            RectF rectF2 = spannedData.getRectF();
            RectF rectF3 = new RectF(rectF2.left / saveRatio, rectF2.top / saveRatio, rectF2.right / saveRatio, rectF2.bottom / saveRatio);
            ArrayList<SpannedData> selectedSpans = spansToRender.getSelectedSpans();
            if (!(selectedSpans instanceof Collection) || !selectedSpans.isEmpty()) {
                Iterator<T> it = selectedSpans.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    if (((SpannedData) it.next()).getId() == spannedData.getId()) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
                break;
            }
            boolean z2 = !z;
            if (z) {
                z2 = !getCurveEngine().getSelectSystem().getBIsActivated();
            }
            SpannedLayoutModel spannedLayoutModel = getCurveEngine().getSpannedLayoutModel();
            if (spannedLayoutModel != null && spannedLayoutModel.g && Intrinsics.areEqual(this.editingSpan, spannedData)) {
                z2 = false;
            }
            String mText = spannedData.mText;
            Intrinsics.checkNotNullExpressionValue(mText, "mText");
            if (mText.length() <= 0 || !z2 || !this.textCacheMap.containsKey(Integer.valueOf(spannedData.getId())) || (textCache = this.textCacheMap.get(Integer.valueOf(spannedData.getId()))) == null) {
                rectF = screenRect;
            } else {
                rectF = screenRect;
                this.imgRenderer.render(getLayerFramebufferId(), rectF, textCache.getTextureId(), rectF3, spannedData.getDegrees());
                this.bIsEmpty = false;
            }
            screenRect = rectF;
        }
        return true;
    }

    public static /* synthetic */ void renderArea$default(TextSystem textSystem, int i, Point point, RectF rectF, boolean z, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = false;
        }
        textSystem.renderArea(i, point, rectF, z);
    }

    public final void renderArea(int fbId, Point fbSize, RectF areaWS, boolean bNeedClear) {
        RectF rectF;
        TextCache textCache;
        Intrinsics.checkNotNullParameter(fbSize, "fbSize");
        Intrinsics.checkNotNullParameter(areaWS, "areaWS");
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        GLES30.glViewport(0, 0, fbSize.x, fbSize.y);
        GLES30.glBindFramebuffer(36160, fbId);
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        if (bNeedClear) {
            GLES30.glClear(16384);
        }
        float saveRatio = getCurveEngine().getSaveRatio();
        for (SpannedData spannedData : getSpansToRender(areaWS).getAllSpans()) {
            RectF rectF2 = spannedData.getRectF();
            RectF rectF3 = new RectF(rectF2.left / saveRatio, rectF2.top / saveRatio, rectF2.right / saveRatio, rectF2.bottom / saveRatio);
            if (!this.textCacheMap.containsKey(Integer.valueOf(spannedData.getId())) || (textCache = this.textCacheMap.get(Integer.valueOf(spannedData.getId()))) == null) {
                rectF = areaWS;
            } else {
                rectF = areaWS;
                ImageRenderer.render$default(this.imgRenderer, rectF, textCache.getTextureId(), rectF3, spannedData.getDegrees(), false, 0.0f, 0.0f, 112, null);
            }
            areaWS = rectF;
        }
    }

    /* JADX INFO: compiled from: TextSystem.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR!\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\f"}, d2 = {"Lxc/engine/core/graphics/engine/systems/TextSystem$QuerySpansOutData;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "allSpans", "Ljava/util/ArrayList;", "Lxc/richtext/data/SpannedData;", "Lkotlin/collections/ArrayList;", "getAllSpans", "()Ljava/util/ArrayList;", "selectedSpans", "getSelectedSpans", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class QuerySpansOutData {
        private final ArrayList<SpannedData> allSpans = new ArrayList<>();
        private final ArrayList<SpannedData> selectedSpans = new ArrayList<>();

        public final ArrayList<SpannedData> getAllSpans() {
            return this.allSpans;
        }

        public final ArrayList<SpannedData> getSelectedSpans() {
            return this.selectedSpans;
        }
    }

    private final QuerySpansOutData getSpansToRender(RectF areaWS) {
        EngineBindContext engineBindContext;
        SpannedEditOperator spannedEditOperator;
        SpannedSelectObject spannedSelectObject;
        SpannedLayoutModel spannedLayoutModel;
        EngineBindContext engineBindContext2;
        QuerySpansOutData querySpansOutData = new QuerySpansOutData();
        List<SpannedData> spanData = getCurveEngine().getSpanData();
        if (spanData != null || ((spannedLayoutModel = getCurveEngine().getSpannedLayoutModel()) != null && (engineBindContext2 = spannedLayoutModel.getEngineBindContext()) != null && (spanData = engineBindContext2.getAllDataList()) != null)) {
            SpannedLayoutModel spannedLayoutModel2 = getCurveEngine().getSpannedLayoutModel();
            List<SpannedData> list = (spannedLayoutModel2 == null || (engineBindContext = spannedLayoutModel2.getEngineBindContext()) == null || (spannedEditOperator = engineBindContext.mSpannedEditOperator) == null || (spannedSelectObject = spannedEditOperator.mSpannedSelectObject) == null) ? null : spannedSelectObject.mSpannedDataList;
            float saveRatio = getCurveEngine().getSaveRatio();
            ArrayList<SpannedData> arrayList = new ArrayList();
            for (Object obj : spanData) {
                SpannedData spannedData = (SpannedData) obj;
                RectF rectF = spannedData.getRectF();
                float fMax = ((Math.max(rectF.width(), rectF.height()) * 1.414f) * 0.5f) / saveRatio;
                float fCenterX = rectF.centerX() / saveRatio;
                float fCenterY = rectF.centerY() / saveRatio;
                if (RectF.intersects(new RectF(fCenterX - fMax, fCenterY - fMax, fCenterX + fMax, fCenterY + fMax), areaWS) && spannedData.getType() == 11 && (spannedData.mSpannedStatus == 0 || spannedData.mSpannedStatus == 1)) {
                    arrayList.add(obj);
                }
            }
            for (SpannedData spannedData2 : arrayList) {
                if (getCachedTextTextureId(spannedData2) > 0) {
                    if (list != null) {
                        List<SpannedData> list2 = list;
                        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                            Iterator<T> it = list2.iterator();
                            while (it.hasNext()) {
                                if (((SpannedData) it.next()).getId() == spannedData2.getId()) {
                                    querySpansOutData.getSelectedSpans().add(spannedData2);
                                    break;
                                }
                            }
                        }
                    }
                    querySpansOutData.getAllSpans().add(spannedData2);
                }
            }
        }
        return querySpansOutData;
    }

    private final TextCache generateSpanTextCache(SpannedData spanData) throws InterruptedException {
        boolean zIsBgDark = getCurveEngine().getPageSystem().isBgDark(spanData.getPageIndex());
        Bitmap bitmapFromTextSpan = INSTANCE.getBitmapFromTextSpan(spanData, zIsBgDark, getCurveEngine().getDrawRatio());
        if (bitmapFromTextSpan == null) {
            return null;
        }
        TextCache textCache = new TextCache();
        String mText = spanData.mText;
        Intrinsics.checkNotNullExpressionValue(mText, "mText");
        textCache.setText(mText);
        textCache.setSizeF(new SizeF(spanData.getRectF().width(), spanData.getRectF().height()));
        textCache.setTextureId(GLUtils.INSTANCE.createTexture(bitmapFromTextSpan));
        textCache.setBIsBgDark(zIsBgDark);
        if (!bitmapFromTextSpan.isRecycled()) {
            bitmapFromTextSpan.recycle();
        }
        Log.d(TAG, "xl-- generateSpanTextCache texId: " + textCache.getTextureId());
        return textCache;
    }

    /* JADX INFO: renamed from: isEmpty, reason: from getter */
    public final boolean getBIsEmpty() {
        return this.bIsEmpty;
    }

    public final void setEditingTextSpan(SpannedData span) {
        Intrinsics.checkNotNullParameter(span, "span");
        this.editingSpan = span;
    }

    public final int getCachedTextTextureId(SpannedData span) throws InterruptedException {
        Intrinsics.checkNotNullParameter(span, "span");
        if (!this.textCacheMap.containsKey(Integer.valueOf(span.getId()))) {
            TextCache textCacheGenerateSpanTextCache = generateSpanTextCache(span);
            if (textCacheGenerateSpanTextCache != null) {
                this.textCacheMap.put(Integer.valueOf(span.getId()), textCacheGenerateSpanTextCache);
            }
        } else {
            TextCache textCache = this.textCacheMap.get(Integer.valueOf(span.getId()));
            boolean zIsBgDark = getCurveEngine().getPageSystem().isBgDark(span.getPageIndex());
            SizeF sizeF = new SizeF(span.getRectF().width(), span.getRectF().height());
            Intrinsics.checkNotNull(textCache);
            if (!Intrinsics.areEqual(textCache.getText(), span.getText()) || !sizeNearlyEqual(textCache.getSizeF(), sizeF) || textCache.getBIsBgDark() != zIsBgDark) {
                GLUtils.INSTANCE.deleteTexture(textCache.getTextureId());
                TextCache textCacheGenerateSpanTextCache2 = generateSpanTextCache(span);
                Log.d(TAG, "xl-- new cache id " + (textCacheGenerateSpanTextCache2 != null ? Integer.valueOf(textCacheGenerateSpanTextCache2.getTextureId()) : null) + ", old: " + textCache.getTextureId() + StringUtils.SPACE);
                if (textCacheGenerateSpanTextCache2 != null) {
                    this.textCacheMap.put(Integer.valueOf(span.getId()), textCacheGenerateSpanTextCache2);
                } else {
                    this.textCacheMap.remove(Integer.valueOf(span.getId()));
                }
            }
        }
        if (!this.textCacheMap.containsKey(Integer.valueOf(span.getId()))) {
            return -1;
        }
        TextCache textCache2 = this.textCacheMap.get(Integer.valueOf(span.getId()));
        Intrinsics.checkNotNull(textCache2);
        return textCache2.getTextureId();
    }

    private final boolean sizeNearlyEqual(SizeF size1, SizeF size2) {
        return Math.abs(size1.getWidth() - size2.getWidth()) < 0.1f && Math.abs(size1.getHeight() - size2.getHeight()) < 0.1f;
    }
}
