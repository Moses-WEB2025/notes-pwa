package com.miui.libhandwritingpreview.util;

import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.libhandwritingpage.listener.PageSaveListener;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpreview.model.PageInkPreModel;
import com.miui.libhandwritingpreview.view.PagePreItemView;
import com.miui.maml.data.VariableNames;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import xc.engine.api.CreationPageSDK;
import xc.engine.storage.ent.PageEntInfo;
import xc.engine.storage.ent.constants.PageEntConstants;
import xc.engine.storage.ent.ink_ent.InkEnt;
import xc.engine.storage.ent.tools.PageEntTools;
import xc.engine.utils.FileUtil;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: compiled from: PagePreEntHelper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000  2\u00020\u0001:\u0001 B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J2\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0011J\b\u0010\u001a\u001a\u00020\u0014H\u0002J\u0006\u0010\u001b\u001a\u00020\u0014J\"\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u001f\u001a\u00020\u001eH\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/miui/libhandwritingpreview/util/PagePreEntHelper;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mItemDataList", "", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageItemData;", "mView", "Lcom/miui/libhandwritingpreview/view/PagePreItemView;", "mPageBean", "Lcom/miui/libhandwritingpage/newbean/beanPageInfo/PageBean;", "savePath", "", "tempSavePath", "mPageEntInfo", "Lxc/engine/storage/ent/PageEntInfo;", "saveListener", "Lcom/miui/libhandwritingpage/listener/PageSaveListener;", "tempDir", "saveEnt", "", "list", "", "view", "bean", "listener", "saveEntFile", "saveInkEntList", "saveCompleted", "success", "", "useNewFeature", "Companion", "libHandWritingPreview_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class PagePreEntHelper {
    private static final String TAG;
    private final List<PageItemData> mItemDataList = new ArrayList();
    private PageBean mPageBean;
    private PageEntInfo mPageEntInfo;
    private PagePreItemView mView;
    private PageSaveListener saveListener;
    private String savePath;
    private String tempDir;
    private String tempSavePath;

    public final void saveEnt(List<? extends PageItemData> list, PagePreItemView view, PageBean bean, PageSaveListener listener) {
        Intrinsics.checkNotNullParameter(list, "list");
        this.mItemDataList.clear();
        this.mItemDataList.addAll(list);
        this.mView = view;
        this.mPageBean = bean;
        Intrinsics.checkNotNull(bean);
        this.savePath = FileUtil.getEntPathByDir(bean.path);
        PageBean pageBean = this.mPageBean;
        Intrinsics.checkNotNull(pageBean);
        this.tempSavePath = FileUtil.getTempEntPathByDir(pageBean.path);
        this.saveListener = listener;
        this.tempDir = FileUtil.getDir(CreationPageSDK.sApplication, FileUtil.PAGE_EXPORT);
        saveEntFile();
    }

    private final void saveEntFile() {
        PageEntInfo pageEntInfo = new PageEntInfo();
        this.mPageEntInfo = pageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo);
        pageEntInfo.setLastModifyTime(System.currentTimeMillis());
        PageEntInfo pageEntInfo2 = this.mPageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo2);
        PageBean pageBean = this.mPageBean;
        Intrinsics.checkNotNull(pageBean);
        pageEntInfo2.setTitle(pageBean.title);
        PageEntInfo pageEntInfo3 = this.mPageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo3);
        PageBean pageBean2 = this.mPageBean;
        Intrinsics.checkNotNull(pageBean2);
        pageEntInfo3.setCreateTime(pageBean2.createTime);
        PageEntInfo pageEntInfo4 = this.mPageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo4);
        PageBean pageBean3 = this.mPageBean;
        Intrinsics.checkNotNull(pageBean3);
        pageEntInfo4.setPageOrientation(pageBean3.orientation);
        PageEntInfo pageEntInfo5 = this.mPageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo5);
        PageBean pageBean4 = this.mPageBean;
        Intrinsics.checkNotNull(pageBean4);
        pageEntInfo5.setWidth(pageBean4.width);
        PageEntInfo pageEntInfo6 = this.mPageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo6);
        PageBean pageBean5 = this.mPageBean;
        Intrinsics.checkNotNull(pageBean5);
        pageEntInfo6.setHeight(pageBean5.height);
        PageEntInfo pageEntInfo7 = this.mPageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo7);
        PageBean pageBean6 = this.mPageBean;
        Intrinsics.checkNotNull(pageBean6);
        pageEntInfo7.setCoverIndex(pageBean6.cover);
        PageEntInfo pageEntInfo8 = this.mPageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo8);
        PageBean pageBean7 = this.mPageBean;
        Intrinsics.checkNotNull(pageBean7);
        pageEntInfo8.setPageFilletRadius(pageBean7.radius);
        int size = this.mItemDataList.size();
        for (int i = 0; i < size; i++) {
            PageItemData pageItemData = this.mItemDataList.get(i);
            if (pageItemData.filePath == null) {
                pageItemData.filePath = new File(this.tempDir, UUID.randomUUID().toString()).getAbsolutePath() + ".ent";
            }
            try {
                if (this.mView != null && pageItemData.isModified) {
                    PagePreItemView pagePreItemView = this.mView;
                    Intrinsics.checkNotNull(pagePreItemView);
                    PageInkPreModel pageInkPreModel = pagePreItemView.model;
                    Intrinsics.checkNotNull(pageInkPreModel);
                    pageInkPreModel.waitForIdle();
                    PagePreItemView pagePreItemView2 = this.mView;
                    Intrinsics.checkNotNull(pagePreItemView2);
                    pagePreItemView2.save(pageItemData);
                }
            } catch (Exception unused) {
                pageItemData.isModified = true;
            }
        }
        saveInkEntList();
    }

    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v17 */
    public final void saveInkEntList() {
        InkEnt inkEnt;
        JSONArray jSONArray = new JSONArray();
        ArrayList arrayList = new ArrayList();
        int size = this.mItemDataList.size();
        int i = 0;
        int i2 = 0;
        boolean z = false;
        while (i2 < size) {
            PageItemData pageItemData = this.mItemDataList.get(i2);
            if (!TextUtils.isEmpty(pageItemData.filePath)) {
                inkEnt = new InkEnt(pageItemData.filePath, PageEntConstants.ENT_INK);
            } else {
                inkEnt = new InkEnt(new byte[i], PageEntConstants.ENT_INK);
            }
            inkEnt.setWidth((short) pageItemData.width);
            inkEnt.setHeight((short) pageItemData.height);
            inkEnt.setPdfPageIndex((short) pageItemData.pdfIndex);
            if (!TextUtils.isEmpty(pageItemData.pdfId)) {
                inkEnt.setPdfFileId(pageItemData.pdfId);
            }
            inkEnt.setBackgroundColor(pageItemData.bgColor);
            inkEnt.setBgTextureIndex((short) pageItemData.bgGrid);
            inkEnt.setCustom(pageItemData.isCustom);
            inkEnt.setUseNewSize(pageItemData.useNewSize);
            inkEnt.setMaxHeight(pageItemData.maxHeight);
            inkEnt.setMaxWidth(pageItemData.maxWidth);
            inkEnt.setSizeUnit(pageItemData.sizeUnit);
            inkEnt.setWidthMM(pageItemData.widthMM);
            inkEnt.setHeightMM(pageItemData.heightMM);
            inkEnt.setUnLimit(pageItemData.isUnLimit);
            inkEnt.setUnLimitStart(pageItemData.unLimitStart);
            inkEnt.setUnLimitEnd(pageItemData.unLimitEnd);
            inkEnt.setUnLimitTop(pageItemData.unLimitTop);
            inkEnt.setUnLimitBottom(pageItemData.unLimitBottom);
            inkEnt.setStandardWidth(pageItemData.standardWidth);
            inkEnt.setExportLeft(pageItemData.exportLeft);
            inkEnt.setExportTop(pageItemData.exportTop);
            inkEnt.setExportRight(pageItemData.exportRight);
            inkEnt.setExportBottom(pageItemData.exportBottom);
            inkEnt.setFixSize(pageItemData.isFixSize);
            if (pageItemData.isFixSize) {
                z = true;
            }
            inkEnt.setFixWidth(pageItemData.fixWidth);
            inkEnt.setUnLimitToNormal(pageItemData.unLimitToNormal);
            inkEnt.setPageSizeType(pageItemData.pageSizeType);
            arrayList.add(inkEnt);
            JSONObject jSONObject = new JSONObject();
            try {
                BgGridConfiguration bgGridConfiguration = pageItemData.bgConfig;
                if (bgGridConfiguration != null) {
                    jSONObject.put("bgGridConfig", true);
                    jSONObject.put("bgLineColor", bgGridConfiguration.lineColor);
                    jSONObject.put("bgPointColor", bgGridConfiguration.pointColor);
                    jSONObject.put("bgLineInterval", bgGridConfiguration.lineInterval);
                    jSONObject.put("bgPointInterval", bgGridConfiguration.pointInterval);
                    jSONObject.put("bgLineWidth", bgGridConfiguration.lineWidth);
                    jSONObject.put("bgPointRadius", bgGridConfiguration.pointRadius);
                    StringBuilder sb = new StringBuilder();
                    int length = bgGridConfiguration.dottedLine.length;
                    for (int i3 = i; i3 < length; i3++) {
                        if (i3 == 0) {
                            sb.append(bgGridConfiguration.dottedLine[i]);
                        } else {
                            sb.append(",").append(bgGridConfiguration.dottedLine[i3]);
                        }
                    }
                    jSONObject.put("bgDottedLine", sb.toString());
                    jSONObject.put("isDottedLine", bgGridConfiguration.isDottedLine);
                    StringBuilder sb2 = new StringBuilder();
                    StringBuilder sb3 = new StringBuilder();
                    RectF rectF = bgGridConfiguration.lineMargin;
                    RectF rectF2 = bgGridConfiguration.pointMargin;
                    sb2.append(rectF.left).append(",").append(rectF.top).append(",").append(rectF.right).append(",").append(rectF.bottom);
                    jSONObject.put("bgLineMargin", sb2);
                    sb3.append(rectF2.left).append(",").append(rectF2.top).append(",").append(rectF2.right).append(",").append(rectF2.bottom);
                    jSONObject.put("bgPointMargin", sb3);
                } else {
                    jSONObject.put("bgGridConfig", (boolean) i);
                }
                jSONObject.put("bgImage", pageItemData.bgImage);
                jSONObject.put("bgImageMode", pageItemData.bgImageMode);
                jSONObject.put("bookmark", pageItemData.bookmark);
                String str = pageItemData.covePath;
                if (str == null || Intrinsics.areEqual("", str)) {
                    pageItemData.covePath = UUID.randomUUID() + ".png";
                }
                jSONObject.put("covePath", pageItemData.covePath);
                jSONObject.put("totalPoints", pageItemData.curvTotalSavePoint);
                jSONObject.put(VariableNames.ORIENTATION, pageItemData.orientation);
                jSONObject.put("isContentEmpty", pageItemData.isContentEmpty);
                jSONArray.put(jSONObject);
                i2++;
                i = 0;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        PageEntInfo pageEntInfo = this.mPageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo);
        pageEntInfo.setExtInfo(jSONArray.toString());
        PageEntInfo pageEntInfo2 = this.mPageEntInfo;
        Intrinsics.checkNotNull(pageEntInfo2);
        pageEntInfo2.setList(arrayList);
        if (TextUtils.isEmpty(this.savePath)) {
            saveCompleted(false, this.savePath, z);
            return;
        }
        File file = new File(this.savePath);
        try {
            boolean zSavePageEnt = PageEntTools.savePageEnt(this.tempSavePath, this.mPageEntInfo);
            if (file.exists()) {
                file.delete();
            }
            PenEngineUtils.renameFile(this.tempSavePath, "multi_page_ent.ent");
            saveCompleted(zSavePageEnt, this.savePath, z);
        } catch (Exception e2) {
            if (!TextUtils.isEmpty(this.tempSavePath)) {
                File file2 = new File(this.tempSavePath);
                if (!file.exists() && file2.exists()) {
                    PenEngineUtils.renameFile(this.tempSavePath, "multi_page_ent.ent");
                } else if (file2.exists()) {
                    file2.delete();
                }
            }
            Log.e(TAG, "savePageEntFile: " + e2.getMessage());
            saveCompleted(false, this.savePath, z);
        }
    }

    private final void saveCompleted(final boolean success, final String savePath, final boolean useNewFeature) {
        if (CreationPageSDK.sHandler != null) {
            PagePreItemView pagePreItemView = this.mView;
            CreationPageSDK.with(pagePreItemView != null ? pagePreItemView.getContext() : null).post(new Runnable() { // from class: com.miui.libhandwritingpreview.util.PagePreEntHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PagePreEntHelper.saveCompleted$lambda$0(this.f$0, success, savePath, useNewFeature);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveCompleted$lambda$0(PagePreEntHelper pagePreEntHelper, boolean z, String str, boolean z2) {
        PageSaveListener pageSaveListener = pagePreEntHelper.saveListener;
        if (pageSaveListener == null) {
            return;
        }
        Intrinsics.checkNotNull(pageSaveListener);
        pageSaveListener.onDataSaved(z, str, z2);
    }

    static {
        Intrinsics.checkNotNullExpressionValue("PagePreEntHelper", "getSimpleName(...)");
        TAG = "PagePreEntHelper";
    }
}
