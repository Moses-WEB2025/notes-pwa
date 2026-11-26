package com.miui.libhandwritingpage.utils;

import android.graphics.RectF;
import android.text.TextUtils;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.beanCurve.CurveBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.maml.data.VariableNames;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import xc.engine.data.DataInterfaceSet;
import xc.engine.data.PathInfo;
import xc.engine.data.pagedata.BaseData;
import xc.engine.data.pagedata.ICurve;
import xc.engine.storage.ent.PageEntInfo;
import xc.engine.storage.ent.ink_ent.InkEnt;
import xc.engine.storage.ent.tools.PageEntTools;
import xc.engine.utils.FileUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PageDataUtils {
    public static List<PageData> parseItemList(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        if (PageEntTools.verifyFormat(str)) {
            try {
                PageEntInfo pageEnt = PageEntTools.readPageEnt(str, str2);
                if (pageEnt.getExtInfo() != null) {
                    JSONArray jSONArray = new JSONArray(pageEnt.getExtInfo());
                    for (int i = 0; i < pageEnt.getInkEntList().size(); i++) {
                        PageData pageData = getPageData(pageEnt.getInkEntList().get(i), jSONArray.getJSONObject(i));
                        if (pageData != null) {
                            pageData.radius = pageEnt.getPageFilletRadius();
                            arrayList.add(pageData);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    public static List<CurveBean> getPageDataList(String str, DataInterfaceSet dataInterfaceSet, int i) {
        PathInfo pathInfoLoadEntFile;
        List<BaseData> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(str) && (pathInfoLoadEntFile = PathInfo.loadEntFile(str, dataInterfaceSet, i)) != null) {
            arrayList = pathInfoLoadEntFile.getAllDataList().computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.miui.libhandwritingpage.utils.PageDataUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PageDataUtils.lambda$getPageDataList$0((Integer) obj);
                }
            });
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            BaseData baseData = arrayList.get(i2);
            if (baseData.getType() == 1) {
                arrayList2.add((ICurve) baseData);
            }
        }
        if (arrayList2.size() == 0) {
            return null;
        }
        List<CurveBean> curveDataList = PageEntHelper.getCurveDataList(arrayList2);
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList.get(i3).releasePointList();
        }
        return curveDataList;
    }

    static /* synthetic */ List lambda$getPageDataList$0(Integer num) {
        return new ArrayList();
    }

    public static PageItemData getItemData(InkEnt inkEnt, JSONObject jSONObject) {
        try {
            String string = jSONObject.getString("bgImage");
            File file = new File(string);
            if (file.exists()) {
                string = FileUtil.getBgFileName(file.getAbsolutePath());
            }
            String str = string;
            String string2 = jSONObject.getString("covePath");
            File file2 = new File(string2);
            if (file2.exists()) {
                string2 = FileUtil.getBgFileName(file2.getAbsolutePath());
            }
            PageItemData pageItemData = new PageItemData(initBgGrid(jSONObject), string2, jSONObject.getString("bookmark"), inkEnt.getPdfPageIndex(), str, jSONObject.getInt("bgImageMode"), jSONObject.optInt(VariableNames.ORIENTATION, 0), inkEnt.getBackgroundColor(), inkEnt.getWidth(), inkEnt.getHeight(), (int) System.currentTimeMillis(), inkEnt.getPdfFileId() == null ? "" : inkEnt.getPdfFileId());
            pageItemData.filePath = inkEnt.getDataCachePath();
            pageItemData.bgGrid = inkEnt.getBgTextureIndex();
            pageItemData.curvTotalSavePoint = jSONObject.optLong("totalPoints");
            pageItemData.isContentEmpty = jSONObject.optBoolean("isContentEmpty", false);
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
            pageItemData.isModified = false;
            return pageItemData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PageData getPageData(InkEnt inkEnt, JSONObject jSONObject) {
        try {
            PageData pageData = new PageData();
            pageData.inkPath = inkEnt.getDataCachePath();
            pageData.pdfId = inkEnt.getPdfFileId();
            if (pageData.pdfId == null) {
                pageData.pdfId = "";
            }
            pageData.pdfIndex = inkEnt.getPdfPageIndex();
            pageData.width = inkEnt.getWidth();
            pageData.height = inkEnt.getHeight();
            pageData.sizeUnit = inkEnt.getSizeUnit();
            pageData.maxWidth = inkEnt.getMaxWidth();
            pageData.isUnLimit = inkEnt.isUnLimit();
            pageData.standardWidth = inkEnt.getStandardWidth();
            pageData.exportLeft = inkEnt.getExportLeft();
            pageData.exportTop = inkEnt.getExportTop();
            pageData.exportRight = inkEnt.getExportRight();
            pageData.exportBottom = inkEnt.getExportBottom();
            pageData.bgColor = inkEnt.getBackgroundColor();
            pageData.bgGrid = inkEnt.getBgTextureIndex();
            pageData.isFixSize = inkEnt.isFixSize();
            pageData.fixWidth = inkEnt.getFixWidth();
            pageData.bgConfig = initBgGrid(jSONObject);
            pageData.bgImage = jSONObject.getString("bgImage");
            File file = new File(pageData.bgImage);
            if (file.exists()) {
                pageData.bgImage = FileUtil.getBgFileName(file.getAbsolutePath());
            }
            pageData.covePath = jSONObject.getString("covePath");
            File file2 = new File(pageData.covePath);
            if (file2.exists()) {
                pageData.covePath = FileUtil.getBgFileName(file2.getAbsolutePath());
            }
            return pageData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BgGridConfiguration initBgGrid(JSONObject jSONObject) throws JSONException {
        boolean z = jSONObject.getBoolean("bgGridConfig");
        BgGridConfiguration bgGridConfiguration = new BgGridConfiguration();
        if (z) {
            bgGridConfiguration.lineColor = jSONObject.getInt("bgLineColor");
            bgGridConfiguration.pointColor = jSONObject.getInt("bgPointColor");
            bgGridConfiguration.lineInterval = jSONObject.getInt("bgLineInterval");
            bgGridConfiguration.pointInterval = jSONObject.getInt("bgPointInterval");
            bgGridConfiguration.lineWidth = jSONObject.getInt("bgLineWidth");
            bgGridConfiguration.pointRadius = jSONObject.getInt("bgPointRadius");
            String[] strArrSplit = jSONObject.getString("bgDottedLine").split(",");
            float[] fArr = new float[strArrSplit.length];
            for (int i = 0; i < strArrSplit.length; i++) {
                fArr[i] = Float.parseFloat(strArrSplit[i]);
            }
            bgGridConfiguration.dottedLine = fArr;
            bgGridConfiguration.isDottedLine = jSONObject.getBoolean("isDottedLine");
            String[] strArrSplit2 = jSONObject.getString("bgLineMargin").split(",");
            bgGridConfiguration.lineMargin = new RectF(Float.parseFloat(strArrSplit2[0]), Float.parseFloat(strArrSplit2[1]), Float.parseFloat(strArrSplit2[2]), Float.parseFloat(strArrSplit2[3]));
            String[] strArrSplit3 = jSONObject.getString("bgPointMargin").split(",");
            bgGridConfiguration.pointMargin = new RectF(Float.parseFloat(strArrSplit3[0]), Float.parseFloat(strArrSplit3[1]), Float.parseFloat(strArrSplit3[2]), Float.parseFloat(strArrSplit3[3]));
        }
        return bgGridConfiguration;
    }
}
