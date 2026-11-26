package com.miui.libhandwritingpreview.system;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.RectF;
import android.opengl.GLES30;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpreview.engine.PreMiCurveEngine;
import defpackage.R2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import xc.engine.core.graphics.engine.gl.GLUtils;
import xc.engine.core.graphics.engine.renderers.ImageRenderer;
import xc.richtext.EngineBindContext;
import xc.richtext.data.SpannedData;
import xc.richtext.data.bean.BitmapBean;
import xc.richtext.operate.edit.SpannedEditOperator;
import xc.richtext.operate.edit.SpannedSelectObject;
import xc.richtext.view.SpannedLayoutModel;
import xc.richtext.view.img.CsImageDrawModel;

/* JADX INFO: compiled from: PreImageSystem.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001e\u001fB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\r\u001a\u00020\u000eH\u0014J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0014J(\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u0017\u001a\u00020\fJ\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0011H\u0002J\u000e\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nJ\u000e\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\nJ\u0006\u0010\u001d\u001a\u00020\fR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreImageSystem;", "Lcom/miui/libhandwritingpreview/system/PreBaseEngineLayer;", "engine", "Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/libhandwritingpreview/engine/PreMiCurveEngine;)V", "imgRenderer", "Lxc/engine/core/graphics/engine/renderers/ImageRenderer;", "imgMap", "Ljava/util/concurrent/ConcurrentHashMap;", "", "bIsEmpty", "", "onDestroy", "", "onRenderLayer", "screenRect", "Landroid/graphics/RectF;", "renderArea", "fbId", "fbSize", "Landroid/graphics/Point;", "areaWS", "bNeedClear", "getSpansToRender", "Lcom/miui/libhandwritingpreview/system/PreImageSystem$QuerySpansOutData;", "getCachedSpanTextureId", "spanId", "refreshCacheSpanTextureId", "isEmpty", "Companion", "QuerySpansOutData", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PreImageSystem extends PreBaseEngineLayer {
    private static final String TAG = "PreImageSystem";
    private boolean bIsEmpty;
    private final ConcurrentHashMap<Integer, Integer> imgMap;
    private final ImageRenderer imgRenderer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreImageSystem(PreMiCurveEngine engine) {
        super(engine);
        Intrinsics.checkNotNullParameter(engine, "engine");
        this.imgRenderer = engine.getImgRenderer();
        this.imgMap = new ConcurrentHashMap<>();
        this.bIsEmpty = true;
    }

    @Override // com.miui.libhandwritingpreview.system.PreBaseEngineLayer, com.miui.libhandwritingpreview.system.BaseStruct
    protected void onDestroy() {
        for (Map.Entry<Integer, Integer> entry : this.imgMap.entrySet()) {
            entry.getKey().intValue();
            GLUtils.INSTANCE.deleteTexture(entry.getValue().intValue());
        }
        this.imgMap.clear();
        super.onDestroy();
    }

    @Override // com.miui.libhandwritingpreview.system.PreBaseEngineLayer
    protected boolean onRenderLayer(RectF screenRect) {
        boolean z;
        Integer num;
        RectF screenRect2 = screenRect;
        Intrinsics.checkNotNullParameter(screenRect2, "screenRect");
        GLES30.glEnable(R2.color.audio_share_time_color_green);
        GLES30.glBlendEquation(32774);
        GLES30.glBlendFuncSeparate(1, R2.attr.colorPrimaryInverse, 1, R2.attr.colorPrimaryInverse);
        GLES30.glViewport(0, 0, getCurveEngine().getScreenWidthInPixel(), getCurveEngine().getScreenHeightInPixel());
        GLES30.glBindFramebuffer(36160, getLayerFramebufferId());
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(16384);
        this.bIsEmpty = true;
        float saveRatio = getCurveEngine().getSaveRatio();
        QuerySpansOutData spansToRender = getSpansToRender(screenRect);
        for (SpannedData spannedData : spansToRender.getAllSpans()) {
            RectF rectF = spannedData.getRectF();
            RectF rectF2 = new RectF(rectF.left / saveRatio, rectF.top / saveRatio, rectF.right / saveRatio, rectF.bottom / saveRatio);
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
            if (!z && this.imgMap.containsKey(Integer.valueOf(spannedData.getId())) && (num = this.imgMap.get(Integer.valueOf(spannedData.getId()))) != null) {
                int iIntValue = num.intValue();
                ImageRenderer imageRenderer = this.imgRenderer;
                float degrees = spannedData.getDegrees();
                RectF mCropRectF = spannedData.mCropRectF;
                Intrinsics.checkNotNullExpressionValue(mCropRectF, "mCropRectF");
                imageRenderer.renderCrop(screenRect2, iIntValue, rectF2, degrees, false, 0.0f, 0.0f, mCropRectF, 1);
                this.bIsEmpty = false;
            }
            screenRect2 = screenRect;
        }
        return spansToRender.getBAllSpansBitmapReady();
    }

    public static /* synthetic */ void renderArea$default(PreImageSystem preImageSystem, int i, Point point, RectF rectF, boolean z, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = false;
        }
        preImageSystem.renderArea(i, point, rectF, z);
    }

    public final void renderArea(int fbId, Point fbSize, RectF areaWS, boolean bNeedClear) {
        Integer num;
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
        QuerySpansOutData spansToRender = getSpansToRender(areaWS);
        float saveRatio = getCurveEngine().getSaveRatio();
        for (SpannedData spannedData : spansToRender.getAllSpans()) {
            RectF rectF = spannedData.getRectF();
            RectF rectF2 = new RectF(rectF.left / saveRatio, rectF.top / saveRatio, rectF.right / saveRatio, rectF.bottom / saveRatio);
            if (this.imgMap.containsKey(Integer.valueOf(spannedData.getId())) && (num = this.imgMap.get(Integer.valueOf(spannedData.getId()))) != null) {
                int iIntValue = num.intValue();
                ImageRenderer imageRenderer = this.imgRenderer;
                float degrees = spannedData.getDegrees();
                RectF mCropRectF = spannedData.mCropRectF;
                Intrinsics.checkNotNullExpressionValue(mCropRectF, "mCropRectF");
                imageRenderer.renderCrop(areaWS, iIntValue, rectF2, degrees, false, 0.0f, 0.0f, mCropRectF, 1);
                this.bIsEmpty = false;
            }
        }
    }

    /* JADX INFO: compiled from: PreImageSystem.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR!\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lcom/miui/libhandwritingpreview/system/PreImageSystem$QuerySpansOutData;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "allSpans", "Ljava/util/ArrayList;", "Lxc/richtext/data/SpannedData;", "Lkotlin/collections/ArrayList;", "getAllSpans", "()Ljava/util/ArrayList;", "selectedSpans", "getSelectedSpans", "bAllSpansBitmapReady", "", "getBAllSpansBitmapReady", "()Z", "setBAllSpansBitmapReady", "(Z)V", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class QuerySpansOutData {
        private final ArrayList<SpannedData> allSpans = new ArrayList<>();
        private final ArrayList<SpannedData> selectedSpans = new ArrayList<>();
        private boolean bAllSpansBitmapReady = true;

        public final ArrayList<SpannedData> getAllSpans() {
            return this.allSpans;
        }

        public final ArrayList<SpannedData> getSelectedSpans() {
            return this.selectedSpans;
        }

        public final boolean getBAllSpansBitmapReady() {
            return this.bAllSpansBitmapReady;
        }

        public final void setBAllSpansBitmapReady(boolean z) {
            this.bAllSpansBitmapReady = z;
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
                if (RectF.intersects(new RectF(fCenterX - fMax, fCenterY - fMax, fCenterX + fMax, fCenterY + fMax), areaWS) && spannedData.getType() == 7 && (spannedData.mSpannedStatus == 0 || spannedData.mSpannedStatus == 1)) {
                    arrayList.add(obj);
                }
            }
            for (SpannedData spannedData2 : arrayList) {
                if (getCachedSpanTextureId(spannedData2.getId()) < 0) {
                    querySpansOutData.setBAllSpansBitmapReady(false);
                }
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
        return querySpansOutData;
    }

    public final int getCachedSpanTextureId(int spanId) {
        Object next;
        CsImageDrawModel csImageDrawModel;
        if (this.imgMap.containsKey(Integer.valueOf(spanId))) {
            Integer num = this.imgMap.get(Integer.valueOf(spanId));
            Intrinsics.checkNotNull(num);
            return num.intValue();
        }
        SpannedLayoutModel spannedLayoutModel = getCurveEngine().getSpannedLayoutModel();
        Bitmap bitmap = null;
        CopyOnWriteArrayList<BitmapBean> copyOnWriteArrayList = (spannedLayoutModel == null || (csImageDrawModel = spannedLayoutModel.mCsImageDrawModel) == null) ? null : csImageDrawModel.mBitmapBeanList;
        if (copyOnWriteArrayList != null) {
            Iterator<T> it = copyOnWriteArrayList.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (((BitmapBean) next).mId != spanId);
            BitmapBean bitmapBean = (BitmapBean) next;
            if (bitmapBean != null) {
                bitmap = bitmapBean.mBitmap;
            }
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            try {
                int iCreateTexture = GLUtils.INSTANCE.createTexture(bitmap);
                this.imgMap.put(Integer.valueOf(spanId), Integer.valueOf(iCreateTexture));
                return iCreateTexture;
            } catch (Exception e) {
                Log.e("PreImageSystem", "Unexpected error creating texture", e);
                return -1;
            }
        }
        if (bitmap != null && bitmap.isRecycled()) {
            Log.e("PreImageSystem", "Bitmap is recycled for spanId: " + spanId);
        }
        return -1;
    }

    public final void refreshCacheSpanTextureId(int spanId) {
        if (this.imgMap.containsKey(Integer.valueOf(spanId))) {
            Integer numRemove = this.imgMap.remove(Integer.valueOf(spanId));
            GLUtils.Companion companion = GLUtils.INSTANCE;
            Intrinsics.checkNotNull(numRemove);
            companion.deleteTexture(numRemove.intValue());
            getCachedSpanTextureId(spanId);
        }
    }

    /* JADX INFO: renamed from: isEmpty, reason: from getter */
    public final boolean getBIsEmpty() {
        return this.bIsEmpty;
    }
}
