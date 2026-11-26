package com.miui.handwriting.common.model;

import android.app.Activity;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import com.miui.handwriting.common.data.bean.CommonPageBean;
import com.miui.handwriting.common.data.bean.CommonPageData;
import com.miui.handwriting.common.data.bean.CommonSearchWrapper;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.listener.CommonExportProcessListener;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPageStateListener;
import com.miui.handwriting.common.listener.CommonPreviewClickListener;
import com.miui.libhandwritingpage.listener.PreviewListener;
import java.util.ArrayList;
import java.util.List;
import miuix.appcompat.app.AlertDialog;
import xc.engine.core.algorithm.bean.StrokeClassificationCurve;

/* JADX INFO: loaded from: classes3.dex */
public interface IMiuiHandWritingCommonModel {
    void addBitmap(String str, RectF rectF);

    void addBitmap(String str, RectF rectF, PointF pointF);

    void addImage(Uri uri, int i);

    void calculateUnLimitExportArea();

    void copyAsText(PartiBean partiBean);

    void create(CommonPageBean commonPageBean, CommonPageLoadListener commonPageLoadListener, boolean z);

    AlertDialog createLimitDialog(Activity activity);

    void deletePage(ArrayList<Integer> arrayList);

    void enableEstimate(boolean z);

    void expanseUnLimitPage();

    <T> void exportImage(CommonExportProcessListener<T> commonExportProcessListener);

    void finishSelect();

    List<String> getAllBitmapPaths();

    int getCurrentPosition();

    List<String> getDeleteBitmapPaths();

    List<String> getImagesPaths();

    RectF getItemRectF();

    int getPageCount();

    List<CommonPageData> getPageDatas();

    List<StrokeClassificationCurve> getSelectedStrokeClassificationCurves();

    RectF getVisibleRectF();

    void importImageBgToPage(String str);

    void insertImageCenter(String str);

    void insertImagePosition(String str, float f, float f2);

    void insertPage(int i, int i2, int i3);

    void insertPage(int i, int i2, int i3, boolean z);

    boolean isContentChanged();

    boolean isContentEmpty();

    boolean isUnlimited();

    void load(String str, CommonPageLoadListener commonPageLoadListener);

    void onPause();

    void onResume();

    void onWindowFocused();

    void recognizeAsText(PartiBean partiBean, int i);

    void removePageChangeStep(boolean z);

    boolean save(CommonPageSaveListener commonPageSaveListener);

    void saveEntFile(CommonPageSaveListener commonPageSaveListener);

    String saveThumbnail();

    String saveThumbnail(String str);

    void setBgImage(int i, String str);

    void setCanWrite(boolean z);

    void setDoubleFingerClickUndo(boolean z);

    void setHeaderViewShow(boolean z);

    void setOneShape(boolean z);

    void setPageBackground(int i, int i2, int i3);

    void setPageSizeDialogShow(boolean z);

    void setPageStateListener(CommonPageStateListener commonPageStateListener);

    void setPcMode(boolean z);

    void setPreviewListener(PreviewListener previewListener);

    void setPreviewOnClick(CommonPreviewClickListener commonPreviewClickListener);

    void setSearchPath(List<Path> list);

    void setSearchPathArray(int[] iArr, List<List<Path>> list);

    void setSearchResult(int i, int i2, int i3, CommonSearchWrapper commonSearchWrapper);

    void setSelectCopyDataToClip();
}
