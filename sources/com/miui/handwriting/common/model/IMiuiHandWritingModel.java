package com.miui.handwriting.common.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import com.miui.handwriting.common.view.toolbar.MiuiToolBoxView;
import com.miui.libhandwritingpage.listener.PageChangeListener;
import com.miui.libhandwritingpage.listener.PageFooterView;
import com.miui.libhandwritingpage.listener.PageSizeListener;
import com.miui.libhandwritingpage.listener.PageStateView;
import com.miui.libhandwritingpage.listener.PageTextSelectListener;
import com.miui.libhandwritingpage.listener.TitleColorChangeListener;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import com.miui.libhandwritingpage.newbean.beanCurve.CurveBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import com.miui.libhandwritingpage.newbean.beanSelect.SelectCopyBean;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import com.miui.libhandwritingpage.view.GlobalRulerView;
import com.miui.libhandwritingpage.view.PageItemView;
import java.util.List;
import java.util.Map;
import xc.engine.api.listener.DrawErrorCallback;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.edit.select.ShapeEditAttr;
import xc.engine.utils.EngineConfig;
import xc.engine.utils.PageSizeConfig;
import xc.richtext.data.SpannedData;

/* JADX INFO: loaded from: classes3.dex */
public interface IMiuiHandWritingModel extends IMiuiHandWritingCommonModel {
    void addChangePageSizeStep(PageSizeConfig pageSizeConfig, PageSizeConfig pageSizeConfig2);

    void addPage(boolean z);

    void addSelectRectBitmap(String str, RectF rectF, int i, boolean z, DrawErrorCallback drawErrorCallback);

    void addText(RectF rectF);

    boolean canChangeSizeConfig(int i);

    void cancelSelect(boolean z);

    void checkSelectViewPosition();

    void checkSelectViewPosition(float[] fArr);

    void clearPage(int i);

    void copyTempSelectData();

    void deleteNewSelectSaveData(List<TouchPosition> list);

    void destroy();

    void enableContourAntLine(boolean z);

    void enablePenOnlyMode(boolean z);

    void expanseUnLimitedPage(float f, float f2, boolean z);

    String getAiBitmapPath();

    List<SpannedData> getAllFromAISpanned();

    List<SpannedData> getAllFromInsertSpanned();

    List<SpannedData> getAllSelectImageSpanned();

    List<CurveBean> getAllSelectedDataList();

    Bitmap getBitmapBySize(boolean z, int i);

    SpannedData getClickImage();

    PageItemView getCurrentItemView();

    long getCurvePasteCount();

    EngineConfig getEngineConfig();

    float getFactoryScale();

    GlobalRulerView getGlobalRulerView();

    List<PageItemData> getItemDataList();

    RectF getItemVisibleRectF(int i);

    MiCurveEngine getMiCurveEngine();

    RectF getNewSelectSaveRectF(RectF rectF, int i);

    PageInkModel getPageInkModel();

    long getPageTotalSavePoint();

    float getScale();

    RectF getSelectRectFWithoutOffset(RectF rectF, int i);

    Bitmap getSelectScreenshot(boolean z);

    CanvasMaskEntity getSelectScreenshotAndMask(boolean z, boolean z2, float f, float f2, int i);

    Bitmap getSelectScreenshotWithoutSpanRotate(boolean z);

    ShapeProp getSelectShapeProp();

    List<CurveBean> getSelectedDataList();

    List<SpannedData> getSelectedSpanList();

    List<TouchPosition> getTouchPositionList();

    void init(PageInkModel pageInkModel);

    void insertTextPosition(String str, float f, float f2);

    boolean invalidPosition();

    boolean isAiModel();

    boolean isCanWrite();

    boolean isContentEmpty(int i);

    boolean isDrawing();

    boolean isFromAi();

    boolean isOnlyPenWrite();

    boolean isPcMode();

    boolean isTextEditing();

    boolean isUnLimitToNormal();

    void jumpPage(int i, boolean z);

    void onCancelEvent();

    void onCopy(boolean z);

    void onCopyPaste(boolean z);

    void onCrop();

    void onCropCancel();

    void onCropFinish();

    void onCut(boolean z);

    void onDelete(boolean z);

    void onPaste(float f, float f2);

    void onTitleColorChanged(int i, int i2);

    void redo();

    void resetCurveCenter();

    void resetScale(Boolean bool, Float f);

    void scrollBy(float f, float f2);

    void scrollBy(int i, int i2);

    void scrollOnKeyBoardHeightChanged(Activity activity, int i, boolean z);

    boolean selectByRect(RectF rectF);

    boolean selectByRect(RectF rectF, boolean z);

    void selectMoveEnable(boolean z);

    void setEdit(boolean z);

    void setModifyFlagToItemData(int i, boolean z);

    void setMultiStateViewVisibility(int i);

    void setOnlyPenWrite(boolean z);

    void setPadding(int i, int i2, int i3, int i4);

    void setPageBackground(Map<Integer, PagePreviewData> map);

    void setPageChangeListener(PageChangeListener pageChangeListener);

    void setPageCopyData(SelectCopyBean selectCopyBean);

    void setPageFootView(PageFooterView pageFooterView);

    void setPageSizeConfig(PageSizeConfig pageSizeConfig, boolean z);

    void setPageSizeListener(PageSizeListener pageSizeListener);

    void setSelect(boolean z);

    void setSelect(boolean z, boolean z2);

    void setSelectListener(PageTextSelectListener pageTextSelectListener);

    void setSelectMenuSize(List<RectF> list, List<RectF> list2);

    void setSelectShapeAttr(ShapeEditAttr shapeEditAttr, boolean z);

    void setShapeMenuSize(List<RectF> list, RectF rectF);

    void setShieldEventListener(MiuiToolBoxView miuiToolBoxView);

    void setStateView(PageStateView pageStateView);

    void setTitleChangeListener(TitleColorChangeListener titleColorChangeListener);

    void simulateClick(TouchPosition touchPosition);

    void toDrawReactF(RectF rectF);

    void toOriginPointFIgnoreScale(PointF pointF);

    void undo();

    void updateCurrentViewItem(float f, float f2);
}
