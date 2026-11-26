package com.miui.libhandwritingpage.utils;

import android.graphics.Color;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import com.miui.libhandwritingpage.listener.PageSaveListener;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.beanCurve.CurveBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.newbean.beanTouch.TData;
import com.miui.libhandwritingpage.view.PageItemView;
import com.miui.maml.data.VariableNames;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import xc.engine.api.CreationPageSDK;
import xc.engine.core.algorithm.bean.StrokeClassificationCurve;
import xc.engine.core.algorithm.bean.StrokeClassificationPoint;
import xc.engine.data.curve.ICurvePointList;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.DataShape;
import xc.engine.data.pagedata.ICurve;
import xc.engine.storage.ent.PageEntInfo;
import xc.engine.storage.ent.constants.PageEntConstants;
import xc.engine.storage.ent.ink_ent.InkEnt;
import xc.engine.storage.ent.tools.PageEntTools;
import xc.engine.utils.FileUtil;
import xc.engine.utils.PenEngineUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PageEntHelper {
    private static final String TAG = "PageEntHelper";
    private final List<PageItemData> mItemDataList = new ArrayList();
    private PageBean mPageBean;
    private PageEntInfo mPageEntInfo;
    private PageItemView mView;
    private PageSaveListener saveListener;
    private String savePath;
    private String tempDir;
    private String tempSavePath;

    private void saveIinkPackageIfAvailable() {
    }

    public void saveEnt(List<PageItemData> list, PageItemView pageItemView, PageBean pageBean, PageSaveListener pageSaveListener) {
        this.mItemDataList.clear();
        this.mItemDataList.addAll(list);
        this.mView = pageItemView;
        this.mPageBean = pageBean;
        this.savePath = FileUtil.getEntPathByDir(pageBean.path);
        this.tempSavePath = FileUtil.getTempEntPathByDir(this.mPageBean.path);
        this.saveListener = pageSaveListener;
        this.tempDir = FileUtil.getDir(CreationPageSDK.sApplication, FileUtil.PAGE_EXPORT);
        saveEntFile();
    }

    private void saveEntFile() {
        PageEntInfo pageEntInfo = new PageEntInfo();
        this.mPageEntInfo = pageEntInfo;
        pageEntInfo.setLastModifyTime(System.currentTimeMillis());
        this.mPageEntInfo.setTitle(this.mPageBean.title);
        this.mPageEntInfo.setCreateTime(this.mPageBean.createTime);
        this.mPageEntInfo.setPageOrientation(this.mPageBean.orientation);
        this.mPageEntInfo.setWidth(this.mPageBean.width);
        this.mPageEntInfo.setHeight(this.mPageBean.height);
        this.mPageEntInfo.setCoverIndex(this.mPageBean.cover);
        this.mPageEntInfo.setPageFilletRadius(this.mPageBean.radius);
        for (int i = 0; i < this.mItemDataList.size(); i++) {
            PageItemData pageItemData = this.mItemDataList.get(i);
            if (pageItemData.filePath == null) {
                pageItemData.filePath = new File(this.tempDir, UUID.randomUUID().toString()).getAbsolutePath() + ".ent";
            }
            try {
                if (this.mView != null && pageItemData.isModified && pageItemData.hadLoad) {
                    this.mView.model.waitForIdle();
                    this.mView.save(pageItemData);
                }
            } catch (Exception e) {
                Log.d(TAG, "saveEntFile: Error" + e.getMessage());
                pageItemData.isModified = true;
                saveCompleted(false, this.savePath, false);
            }
        }
        saveInkEntList();
        if (!this.mPageBean.isSuniaSinglePage || this.mPageBean.iinkPackage == null) {
            return;
        }
        saveIinkPackageIfAvailable();
    }

    public void saveInkEntList() {
        InkEnt inkEnt;
        Log.d(TAG, "saveInkEntList: ");
        JSONArray jSONArray = new JSONArray();
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        for (int i = 0; i < this.mItemDataList.size(); i++) {
            PageItemData pageItemData = this.mItemDataList.get(i);
            if (!TextUtils.isEmpty(pageItemData.filePath)) {
                inkEnt = new InkEnt(pageItemData.filePath, PageEntConstants.ENT_INK);
            } else {
                inkEnt = new InkEnt(new byte[0], PageEntConstants.ENT_INK);
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
                    for (int i2 = 0; i2 < bgGridConfiguration.dottedLine.length; i2++) {
                        if (i2 == 0) {
                            sb.append(bgGridConfiguration.dottedLine[0]);
                        } else {
                            sb.append(",").append(bgGridConfiguration.dottedLine[i2]);
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
                    jSONObject.put("bgGridConfig", false);
                }
                jSONObject.put("bgImage", pageItemData.bgImage);
                jSONObject.put("bgImageMode", pageItemData.bgImageMode);
                jSONObject.put("bookmark", pageItemData.bookmark);
                String str = pageItemData.covePath;
                if (str == null || "".equals(str)) {
                    pageItemData.covePath = UUID.randomUUID().toString() + ".png";
                }
                jSONObject.put("covePath", pageItemData.covePath);
                jSONObject.put("totalPoints", pageItemData.curvTotalSavePoint);
                jSONObject.put(VariableNames.ORIENTATION, pageItemData.orientation);
                jSONObject.put("isContentEmpty", pageItemData.isContentEmpty);
                jSONArray.put(jSONObject);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        this.mPageEntInfo.setExtInfo(jSONArray.toString());
        this.mPageEntInfo.setList(arrayList);
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

    private void saveCompleted(final boolean z, final String str, final boolean z2) {
        if (CreationPageSDK.sHandler != null) {
            CreationPageSDK.with(this.mView.getContext()).post(new Runnable() { // from class: com.miui.libhandwritingpage.utils.PageEntHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m857xb5b0fde2(z, str, z2);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$saveCompleted$0$com-miui-libhandwritingpage-utils-PageEntHelper, reason: not valid java name */
    /* synthetic */ void m857xb5b0fde2(boolean z, String str, boolean z2) {
        Log.d(TAG, "saveCompleted: Listener Is Null:" + (this.saveListener == null));
        PageSaveListener pageSaveListener = this.saveListener;
        if (pageSaveListener == null) {
            return;
        }
        pageSaveListener.onDataSaved(z, str, z2);
    }

    public PageBean loadEnt(String str, PageEntInfo pageEntInfo) {
        PageBean pageBean = new PageBean();
        pageBean.path = str.substring(0, str.lastIndexOf(File.separator));
        pageBean.cover = pageEntInfo.getCoverIndex();
        pageBean.createTime = pageEntInfo.getCreateTime();
        pageBean.moreInfo = pageEntInfo.getExtInfo();
        pageBean.radius = pageEntInfo.getPageFilletRadius();
        pageBean.width = pageEntInfo.getWidth();
        pageBean.height = pageEntInfo.getHeight();
        if (pageBean.moreInfo == null) {
            pageBean.moreInfo = "";
        }
        pageBean.title = pageEntInfo.getTitle();
        if (pageBean.title == null) {
            pageBean.title = "";
        }
        pageBean.orientation = pageEntInfo.getPageOrientation();
        return pageBean;
    }

    public List<PageItemData> getItemDataList(PageEntInfo pageEntInfo) {
        ArrayList arrayList = new ArrayList();
        try {
            if (pageEntInfo.getExtInfo() != null) {
                String str = TAG;
                Log.d(str, "getItemDataList:" + pageEntInfo.getExtInfo());
                int i = 0;
                if (pageEntInfo.getExtInfo().contains("multi_page_info")) {
                    Log.i(str, "getItemDataList: sunia");
                    JSONArray jSONArray = new JSONArray(new JSONObject(new JSONObject(pageEntInfo.getExtInfo()).getString("multi_page_info")).getString("list").replace("\\\"", "\"").replace("\\\\", "\\"));
                    while (i < pageEntInfo.getInkEntList().size()) {
                        PageItemData itemData = PageDataUtils.getItemData(pageEntInfo.getInkEntList().get(i), jSONArray.getJSONObject(i));
                        if (itemData != null) {
                            itemData.setPageIndex(i);
                        }
                        arrayList.add(itemData);
                        i++;
                    }
                } else if (pageEntInfo.getExtInfo() != null) {
                    JSONArray jSONArray2 = new JSONArray(pageEntInfo.getExtInfo());
                    while (i < pageEntInfo.getInkEntList().size()) {
                        PageItemData itemData2 = PageDataUtils.getItemData(pageEntInfo.getInkEntList().get(i), jSONArray2.getJSONObject(i));
                        if (itemData2 != null) {
                            itemData2.setPageIndex(i);
                            arrayList.add(itemData2);
                        }
                        i++;
                    }
                }
            }
            return arrayList;
        } catch (JSONException e) {
            Log.e(TAG, "getItemDataList:" + e.getMessage());
            return null;
        }
    }

    public PageItemData getSinglePageItemData(PageEntInfo pageEntInfo, String str) {
        InkEnt inkEnt = pageEntInfo.getInkEntList().get(0);
        BgGridConfiguration bgGridConfiguration = new BgGridConfiguration();
        if (str.equals("dot")) {
            bgGridConfiguration.dottedLine = new float[]{4.0f, 4.0f};
            bgGridConfiguration.isDottedLine = true;
            bgGridConfiguration.isShieldLine = false;
            bgGridConfiguration.lineColor = 1291835632;
            bgGridConfiguration.lineInterval = 75.0f;
            bgGridConfiguration.lineMargin = new RectF(-44.0f, -0.5f, 0.0f, 0.0f);
            bgGridConfiguration.lineWidth = 1.0f;
            bgGridConfiguration.pointColor = 1291835632;
            bgGridConfiguration.pointInterval = 37.0f;
            bgGridConfiguration.pointMargin = new RectF(-14.5f, -7.0f, 0.0f, 0.0f);
            bgGridConfiguration.pointRadius = 1.0f;
        }
        PageItemData pageItemData = new PageItemData(bgGridConfiguration, "", "", 0, "", 0, 0, inkEnt.getBackgroundColor(), inkEnt.getWidth(), inkEnt.getHeight(), (int) System.currentTimeMillis(), inkEnt.getPdfFileId() == null ? "" : inkEnt.getPdfFileId());
        pageItemData.filePath = inkEnt.getDataCachePath();
        if (Objects.equals(str, "dot")) {
            pageItemData.bgGrid = 4;
        } else {
            pageItemData.bgGrid = inkEnt.getBgTextureIndex();
        }
        pageItemData.isContentEmpty = false;
        pageItemData.isCustom = inkEnt.isCustom();
        pageItemData.useNewSize = inkEnt.isUseNewSize();
        pageItemData.maxHeight = inkEnt.getMaxHeight();
        pageItemData.maxWidth = inkEnt.getMaxWidth();
        pageItemData.sizeUnit = inkEnt.getSizeUnit();
        pageItemData.widthMM = inkEnt.getWidthMM();
        pageItemData.heightMM = inkEnt.getHeightMM();
        pageItemData.isUnLimit = inkEnt.isUnLimit();
        pageItemData.unLimitStart = inkEnt.getUnLimitStart();
        pageItemData.unLimitEnd = inkEnt.getUnLimitEnd();
        pageItemData.unLimitTop = inkEnt.getUnLimitTop();
        pageItemData.unLimitBottom = inkEnt.getUnLimitBottom();
        pageItemData.standardWidth = inkEnt.getStandardWidth();
        pageItemData.exportLeft = inkEnt.getExportLeft();
        pageItemData.exportTop = inkEnt.getExportTop();
        pageItemData.exportRight = inkEnt.getExportRight();
        pageItemData.exportBottom = inkEnt.getExportBottom();
        pageItemData.isFixSize = inkEnt.isFixSize();
        pageItemData.fixWidth = inkEnt.getFixWidth();
        pageItemData.unLimitToNormal = inkEnt.getUnLimitToNormal();
        pageItemData.pageSizeType = inkEnt.getPageSizeType();
        pageItemData.isModified = true;
        if (Objects.equals(str, "black")) {
            pageItemData.bgColor = -16777216;
            return pageItemData;
        }
        pageItemData.bgColor = -1;
        return pageItemData;
    }

    public static List<CurveBean> getCurveDataList(List<ICurve> list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            ICurvePointList curvePointList = ((DataCurve) list.get(i)).getCurvePointList(((DataCurve) list.get(i)).mSavePoints);
            boolean z = false;
            for (int i2 = 0; i2 < curvePointList.size(); i2++) {
                if (curvePointList.get(i2).getX() == Float.NEGATIVE_INFINITY || curvePointList.get(i2).getX() == Float.POSITIVE_INFINITY || Float.isNaN(curvePointList.get(i2).getX())) {
                    z = true;
                }
                if (curvePointList.get(i2).getY() == Float.NEGATIVE_INFINITY || curvePointList.get(i2).getY() == Float.POSITIVE_INFINITY || Float.isNaN(curvePointList.get(i2).getY())) {
                    z = true;
                }
            }
            if (!z) {
                ArrayList arrayList2 = new ArrayList();
                for (int i3 = 0; i3 < curvePointList.size(); i3++) {
                    if (i3 == 0) {
                        arrayList2.add(new TData(curvePointList.get(i3).getX(), curvePointList.get(i3).getY(), curvePointList.get(i3).getTime(), 0));
                    } else if (i3 == curvePointList.size() - 1) {
                        arrayList2.add(new TData(curvePointList.get(i3).getX(), curvePointList.get(i3).getY(), curvePointList.get(i3).getTime(), 1));
                    } else {
                        arrayList2.add(new TData(curvePointList.get(i3).getX(), curvePointList.get(i3).getY(), curvePointList.get(i3).getTime(), 2));
                    }
                }
                int alpha = list.get(i).getAlpha();
                int curveColor = list.get(i).getCurveColor();
                arrayList.add(new CurveBean(arrayList2, list.get(i).getCurvePenWidth(), list.get(i).getPenType(), Color.argb(alpha, Color.red(curveColor), Color.green(curveColor), Color.blue(curveColor)), list.get(i) instanceof DataShape));
            }
        }
        return arrayList;
    }

    public static List<StrokeClassificationCurve> getClassificationCurves(List<ICurve> list, boolean z) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            ICurvePointList curvePointList = ((DataCurve) list.get(i)).getCurvePointList(((DataCurve) list.get(i)).mSavePoints);
            boolean z2 = false;
            for (int i2 = 0; i2 < curvePointList.size(); i2++) {
                if (curvePointList.get(i2).getX() == Float.NEGATIVE_INFINITY || curvePointList.get(i2).getX() == Float.POSITIVE_INFINITY || Float.isNaN(curvePointList.get(i2).getX())) {
                    z2 = true;
                }
                if (curvePointList.get(i2).getY() == Float.NEGATIVE_INFINITY || curvePointList.get(i2).getY() == Float.POSITIVE_INFINITY || Float.isNaN(curvePointList.get(i2).getY())) {
                    z2 = true;
                }
            }
            if (!z2) {
                ArrayList arrayList2 = new ArrayList();
                for (int i3 = 0; i3 < curvePointList.size(); i3++) {
                    if (i3 == 0) {
                        arrayList2.add(new StrokeClassificationPoint(curvePointList.get(i3).getX(), curvePointList.get(i3).getY(), curvePointList.get(i3).getTime(), curvePointList.get(i3).getPressure(), 0));
                    } else if (i3 == curvePointList.size() - 1) {
                        arrayList2.add(new StrokeClassificationPoint(curvePointList.get(i3).getX(), curvePointList.get(i3).getY(), curvePointList.get(i3).getTime(), curvePointList.get(i3).getPressure(), 1));
                    } else {
                        arrayList2.add(new StrokeClassificationPoint(curvePointList.get(i3).getX(), curvePointList.get(i3).getY(), curvePointList.get(i3).getTime(), curvePointList.get(i3).getPressure(), 2));
                    }
                }
                arrayList.add(new StrokeClassificationCurve(arrayList2, ((DataCurve) list.get(i)).mId, z));
            }
        }
        return arrayList;
    }
}
