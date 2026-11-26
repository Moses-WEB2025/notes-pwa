package com.miui.handwriting.common.model;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.miui.creation.common.R;
import com.miui.creation.taskservice.ITaskServiceCallback;
import com.miui.doodle.document.Layer;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.clipboard.ClipManager;
import com.miui.handwriting.common.data.bean.BoundingBox;
import com.miui.handwriting.common.data.bean.CommonPageBean;
import com.miui.handwriting.common.data.bean.CommonPageData;
import com.miui.handwriting.common.data.bean.CommonSearchWrapper;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.data.dataset.EstimateParamsSet;
import com.miui.handwriting.common.data.dataset.ShapeRecognizeParamsSet;
import com.miui.handwriting.common.engine.secret.ImportStrategy;
import com.miui.handwriting.common.listener.CommonExportProcessListener;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPageStateListener;
import com.miui.handwriting.common.listener.CommonPreviewClickListener;
import com.miui.handwriting.common.listener.RecognizeStateListener;
import com.miui.handwriting.common.taskservice.CreationServiceManager;
import com.miui.handwriting.common.tools.BgGridConfigurationHelper;
import com.miui.handwriting.common.tools.FilePathConstant;
import com.miui.handwriting.common.tools.NumUtil;
import com.miui.handwriting.common.tools.RecognizeDataHelper;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.view.toolbar.MiuiToolBoxView;
import com.miui.libhandwritingpage.listener.PageChangeListener;
import com.miui.libhandwritingpage.listener.PageFooterView;
import com.miui.libhandwritingpage.listener.PageLoadListener;
import com.miui.libhandwritingpage.listener.PageSaveListener;
import com.miui.libhandwritingpage.listener.PageSizeListener;
import com.miui.libhandwritingpage.listener.PageStateListener;
import com.miui.libhandwritingpage.listener.PageStateView;
import com.miui.libhandwritingpage.listener.PageTextSelectListener;
import com.miui.libhandwritingpage.listener.PreviewListener;
import com.miui.libhandwritingpage.listener.TitleColorChangeListener;
import com.miui.libhandwritingpage.model.IPageInkModel;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import com.miui.libhandwritingpage.newbean.SearchWrapper;
import com.miui.libhandwritingpage.newbean.beanCurve.CurveBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import com.miui.libhandwritingpage.newbean.beanSelect.SelectCopyBean;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpage.view.GlobalRulerView;
import com.miui.libhandwritingpage.view.PageItemView;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import miuix.appcompat.app.AlertDialog;
import org.apache.commons.lang3.StringUtils;
import xc.engine.api.ICreationEngine;
import xc.engine.api.listener.DrawErrorCallback;
import xc.engine.core.algorithm.bean.StrokeClassificationCurve;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.edit.select.ShapeEditAttr;
import xc.engine.utils.EngineConfig;
import xc.engine.utils.PageSizeConfig;
import xc.richtext.data.SpannedData;
import xc.richtext.data.TextInfo;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiHandViewModelImpl implements IMiuiHandWritingModel {
    private static final String TAG = "MiuiHandViewModelImpl";
    private CommonPageLoadListener createLoadlistener;
    private CommonPageLoadListener loadListener;
    private final Context mContext;
    private CommonExportProcessListener mExportProcessListener;
    private PageInkModel mPageInkViewModel;
    private CommonPageStateListener pageStateChangeListener;
    private RecognizeStateListener recognizeStateListener;
    private String dirPath = FilePathConstant.getEngineAndPageEntPath();
    private final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl.1
        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            super.dispatchMessage(message);
            MiuiHandViewModelImpl.this.recognizeAsText((PartiBean) message.obj, message.arg1);
        }
    };
    private final ITaskServiceCallback mTaskCallback = new ITaskServiceCallback.Stub() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl.2
        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void classificationForAi(boolean z, String str) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToAI(boolean z, String str, int i) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportToPcCompleted(String str) throws RemoteException {
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void recognizeAsText(String str, int i) throws RemoteException {
            PartiBean selectRecognizeDataFromFile = RecognizeDataHelper.readSelectRecognizeDataFromFile(str);
            Message messageObtainMessage = MiuiHandViewModelImpl.this.mHandler.obtainMessage();
            messageObtainMessage.obj = selectRecognizeDataFromFile;
            messageObtainMessage.arg1 = i;
            MiuiHandViewModelImpl.this.mHandler.sendMessage(messageObtainMessage);
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void copyAsText(String str) throws RemoteException {
            MiuiHandViewModelImpl.this.copyAsText(RecognizeDataHelper.readSelectRecognizeDataFromFile(str));
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void showExportPreview(String str, int i, String str2, List<String> list, String str3) throws RemoteException {
            CreationServiceManager.INSTANCE.clearLastMessage();
            if (MiuiHandViewModelImpl.this.mExportProcessListener != null) {
                MiuiHandViewModelImpl.this.mExportProcessListener.exportFinish(true, list);
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportPreviewFail() throws RemoteException {
            if (MiuiHandViewModelImpl.this.mExportProcessListener != null) {
                MiuiHandViewModelImpl.this.mExportProcessListener.exportFinish(false, null);
            }
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportOnProgress(int i) throws RemoteException {
            Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
            intent.putExtra("operationType", "onProgress");
            intent.putExtra("percentDone", i);
            Log.d(MiuiHandViewModelImpl.TAG, "Fragment receive: MSG_FRAGMENT_EXPORT_ON_PROGRESS: " + i);
            LocalBroadcastManager.getInstance(HandWritingViewApplication.INSTANCE.getApplication()).sendBroadcast(intent);
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void exportCompleted(boolean z, String str, int i) throws RemoteException {
            Log.d(MiuiHandViewModelImpl.TAG, "Fragment receive: MSG_FRAGMENT_EXPORT_ON_COMPLETED");
            Intent intent = new Intent(Constants.EXPORT_OPERATION_INTENT);
            intent.putExtra("operationType", "onCompleted");
            intent.putExtra("isCompletedSuccess", z);
            intent.putExtra("completedPath", str);
            Log.d(MiuiHandViewModelImpl.TAG, "EXPORT_completedPath:" + str);
            intent.putExtra("exportType", i);
            CreationServiceManager.INSTANCE.clearLastMessage();
            LocalBroadcastManager.getInstance(HandWritingViewApplication.INSTANCE.getApplication()).sendBroadcast(intent);
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void backToHome() throws RemoteException {
            CreationServiceManager.INSTANCE.clearLastMessage();
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public boolean exportThumbnailFail() throws RemoteException {
            if (CreationServiceManager.INSTANCE.getRestartNum() < 3) {
                CreationServiceManager.INSTANCE.restartServiceTask();
                return false;
            }
            CreationServiceManager.INSTANCE.setRestartNum(0);
            backToHome();
            return true;
        }

        @Override // com.miui.creation.taskservice.ITaskServiceCallback
        public void onGenComplete(long j, ContentValues contentValues) {
            if (MiuiHandViewModelImpl.this.recognizeStateListener != null) {
                MiuiHandViewModelImpl.this.recognizeStateListener.onGenComplete(j, contentValues);
            }
        }
    };

    static /* synthetic */ void lambda$createLimitDialog$0(DialogInterface dialogInterface, int i) {
    }

    static /* synthetic */ void lambda$createLimitDialog$1(DialogInterface dialogInterface, int i) {
    }

    public MiuiHandViewModelImpl(Context context) {
        this.mContext = context;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void init(PageInkModel pageInkModel) {
        this.mPageInkViewModel = pageInkModel;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public PageInkModel getPageInkModel() {
        return this.mPageInkViewModel;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onTitleColorChanged(int i, int i2) {
        checkViewModeBind();
        this.mPageInkViewModel.onTitleColorChanged(i, i2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setTitleChangeListener(TitleColorChangeListener titleColorChangeListener) {
        checkViewModeBind();
        this.mPageInkViewModel.setTitleColorChangeListener(titleColorChangeListener);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void insertTextPosition(String str, float f, float f2) {
        checkViewModeBind();
        this.mPageInkViewModel.addCopyText(str, f, f2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setModifyFlagToItemData(int i, boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setModifyFlagToItemData(i, z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setOnlyPenWrite(boolean z) {
        SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences(Constants.CREATION_SHPREF, 0).edit();
        editorEdit.putBoolean("isOnlyPenWrite", z);
        editorEdit.apply();
        enablePenOnlyMode(z);
        Log.d(TAG, "setOnlyPenWrite");
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean isOnlyPenWrite() {
        boolean z = this.mContext.getSharedPreferences(Constants.CREATION_SHPREF, 0).getBoolean("isOnlyPenWrite", false);
        Log.d(TAG, "mOnlyPenWrite is:" + z);
        return z;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void recognizeAsText(PartiBean partiBean, int i) {
        checkViewModeBind();
        Log.d(TAG, "getRecognizeResult: " + partiBean);
        if (partiBean == null || partiBean.getLabel() == null) {
            return;
        }
        RectF recognizeRectF = getRecognizeRectF(partiBean);
        float lineTextSize = getLineTextSize(partiBean, recognizeRectF);
        this.mPageInkViewModel.addText(new TextInfo(new RectF(recognizeRectF.left, recognizeRectF.top, recognizeRectF.right + (lineTextSize / 8.0f), recognizeRectF.bottom), i, lineTextSize, partiBean.getLabel()), true);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void copyAsText(PartiBean partiBean) {
        Log.d(TAG, "getRecognizeResult: " + partiBean);
        checkViewModeBind();
        if (partiBean == null) {
            Log.i(TAG, "select recognize text is null");
            return;
        }
        String label = partiBean.getLabel();
        if (label == null) {
            Log.i(TAG, "select recognize text is null");
            return;
        }
        String strReplaceAll = label.replaceAll("\\n", "");
        Log.i(TAG, "asText : " + strReplaceAll);
        ClipManager.copyContentToClipboard(strReplaceAll, this.mContext);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public int getCurrentPosition() {
        checkViewModeBind();
        return this.mPageInkViewModel.getCurrentPosition();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPageSizeDialogShow(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setPageSizeDialogShow(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void onResume() {
        checkViewModeBind();
        this.mPageInkViewModel.resume();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPageBackground(int i, int i2, int i3) {
        PagePreviewData pagePreviewData;
        checkViewModeBind();
        List<PagePreviewData> previewBeans = this.mPageInkViewModel.getPreviewBeans();
        if (previewBeans != null && !previewBeans.isEmpty() && previewBeans.get(i) != null) {
            pagePreviewData = previewBeans.get(i);
        } else {
            pagePreviewData = new PagePreviewData();
        }
        this.mPageInkViewModel.setPageBackground(BgGridConfigurationHelper.getPreviewData(i, pagePreviewData, i2, i3));
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setPageBackground(Map<Integer, PagePreviewData> map) {
        checkViewModeBind();
        this.mPageInkViewModel.setPageBackground(map);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void jumpPage(int i, boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.jumpPage(i, z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void resetScale(Boolean bool, Float f) {
        this.mPageInkViewModel.resetScale(bool, f);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void resetCurveCenter() {
        this.mPageInkViewModel.resetCurveCenter();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void expanseUnLimitedPage(float f, float f2, boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.expanseUnLimitedPage(f, f2, z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void addPage(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.addPage(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setPageChangeListener(PageChangeListener pageChangeListener) {
        this.mPageInkViewModel.setPageChangeListener(pageChangeListener);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setPageSizeListener(PageSizeListener pageSizeListener) {
        this.mPageInkViewModel.setPageSizeListener(pageSizeListener);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean isCanWrite() {
        return this.mPageInkViewModel.isCanWrite();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setBgImage(int i, String str) {
        checkViewModeBind();
        HashMap map = new HashMap();
        PagePreviewData pagePreviewData = new PagePreviewData();
        pagePreviewData.bgImage = str;
        map.put(Integer.valueOf(i), pagePreviewData);
        this.mPageInkViewModel.setPageBackground(map);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<StrokeClassificationCurve> getSelectedStrokeClassificationCurves() {
        checkViewModeBind();
        return this.mPageInkViewModel.getSelectedStrokeClassificationCurves();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setCanWrite(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setCanWrite(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void onWindowFocused() {
        this.mPageInkViewModel.windowFocusChange();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void calculateUnLimitExportArea() {
        checkViewModeBind();
        this.mPageInkViewModel.calculateUnLimitExportArea();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPreviewListener(PreviewListener previewListener) {
        checkViewModeBind();
        this.mPageInkViewModel.setPreviewListener(previewListener);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPageStateListener(CommonPageStateListener commonPageStateListener) {
        checkViewModeBind();
        this.pageStateChangeListener = commonPageStateListener;
        this.mPageInkViewModel.setPageStateListener(new PageStateListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl.3
            @Override // com.miui.libhandwritingpage.listener.PageStateListener
            public void onStepChanged(int i, int i2, boolean z, boolean z2, boolean z3) {
                if (MiuiHandViewModelImpl.this.pageStateChangeListener != null) {
                    MiuiHandViewModelImpl.this.pageStateChangeListener.onStepChanged(i, i2, z, z2, z3);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageStateListener
            public void onContentChanged(int i) {
                if (MiuiHandViewModelImpl.this.pageStateChangeListener != null) {
                    MiuiHandViewModelImpl.this.pageStateChangeListener.onContentChanged(i);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageStateListener
            public void onDoubleUndo() {
                if (MiuiHandViewModelImpl.this.pageStateChangeListener != null) {
                    MiuiHandViewModelImpl.this.pageStateChangeListener.onDoubleUndo();
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageStateListener
            public void onPageScale(float f) {
                if (MiuiHandViewModelImpl.this.pageStateChangeListener != null) {
                    MiuiHandViewModelImpl.this.pageStateChangeListener.onPageScale(f);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageStateListener
            public void onPageLoadingFinish() {
                if (MiuiHandViewModelImpl.this.pageStateChangeListener != null) {
                    MiuiHandViewModelImpl.this.pageStateChangeListener.onPageLoadingFinish();
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageStateListener
            public void dataLoadFinish() {
                if (MiuiHandViewModelImpl.this.pageStateChangeListener != null) {
                    MiuiHandViewModelImpl.this.pageStateChangeListener.dataLoadFinish();
                }
            }
        });
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public boolean isUnlimited() {
        checkViewModeBind();
        return this.mPageInkViewModel.isUnlimited();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void expanseUnLimitPage() {
        checkViewModeBind();
        this.mPageInkViewModel.expanseUnLimitPage();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public boolean isContentEmpty() {
        checkViewModeBind();
        return this.mPageInkViewModel.isContentEmpty();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean isContentEmpty(int i) {
        checkViewModeBind();
        return this.mPageInkViewModel.isContentEmpty(i);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public AlertDialog createLimitDialog(Activity activity) {
        checkViewModeBind();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.creation_edit_limit_dialog_title);
        builder.setMessage(getPageCount() >= 500 ? R.string.creation_edit_limit_dialog_message_page_max : R.string.creation_edit_limit_dialog_message_short);
        if (this.mPageInkViewModel.getPageCount() >= 500) {
            builder.setPositiveButton(R.string.creation_edit_limit_dialog_sure, new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    MiuiHandViewModelImpl.lambda$createLimitDialog$0(dialogInterface, i);
                }
            });
        } else {
            builder.setNegativeButton(R.string.creation_edit_limit_dialog_cancel, new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    MiuiHandViewModelImpl.lambda$createLimitDialog$1(dialogInterface, i);
                }
            });
            builder.setPositiveButton(R.string.creation_edit_limit_dialog_new, new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m730x5c59fe4(dialogInterface, i);
                }
            });
        }
        return builder.create();
    }

    /* JADX INFO: renamed from: lambda$createLimitDialog$2$com-miui-handwriting-common-model-MiuiHandViewModelImpl, reason: not valid java name */
    /* synthetic */ void m730x5c59fe4(DialogInterface dialogInterface, int i) {
        if (this.mPageInkViewModel == null || Utils.isFastClick(i, 300L)) {
            return;
        }
        this.mPageInkViewModel.addPage(true);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void saveEntFile(final CommonPageSaveListener commonPageSaveListener) {
        checkViewModeBind();
        this.mPageInkViewModel.saveEntFile(new PageSaveListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl.4
            @Override // com.miui.libhandwritingpage.listener.PageSaveListener
            public void onDataSaved(boolean z, String str, boolean z2) {
                commonPageSaveListener.onDataSaved(z, str, z2);
            }
        });
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void finishSelect() {
        checkViewModeBind();
        this.mPageInkViewModel.finishSelect();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public int getPageCount() {
        checkViewModeBind();
        return this.mPageInkViewModel.getPageCount();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void onPause() {
        checkViewModeBind();
        this.mPageInkViewModel.pause();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSelectCopyDataToClip() {
        checkViewModeBind();
        ClipManager.INSTANCE.setPageCopyData(this.mPageInkViewModel.getSelectCopyData());
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public boolean isContentChanged() {
        checkViewModeBind();
        return this.mPageInkViewModel.isContentChanged();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<CommonPageData> getPageDatas() {
        checkViewModeBind();
        ArrayList arrayList = new ArrayList();
        for (Iterator<PageData> it = this.mPageInkViewModel.getPageDatas().iterator(); it.hasNext(); it = it) {
            PageData next = it.next();
            arrayList.add(new CommonPageData(next.bgImagePathList, next.bitmapPathList, next.bgImage, next.width, next.covePath, next.pdfIndex, next.pdfPath, next.bgColor, next.inkPath, next.height, next.pdfId, next.bgConfig, next.bgGrid, next.radius, next.isUnLimit, next.sizeUnit, next.maxWidth, next.standardWidth, next.exportLeft, next.exportTop, next.exportRight, next.exportBottom, next.isFixSize, next.fixWidth));
        }
        return arrayList;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSearchResult(int i, int i2, int i3, CommonSearchWrapper commonSearchWrapper) {
        checkViewModeBind();
        this.mPageInkViewModel.setSearchResult(i, i2, i3, new SearchWrapper(i, commonSearchWrapper.searchRectMap, commonSearchWrapper.searchText, commonSearchWrapper.searchSpannedRect, commonSearchWrapper.searchSpannedRectContent, commonSearchWrapper.searchSpannedTextSize, commonSearchWrapper.searchColor, commonSearchWrapper.searchAlpha));
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setDoubleFingerClickUndo(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setDoubleFingerClickUndo(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void enableEstimate(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.enableEstimate(z, EstimateParamsSet.getCurEstimateParams());
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setOneShape(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setOneShape(z, ShapeRecognizeParamsSet.getCurShapeRecognizeParams());
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void load(String str, CommonPageLoadListener commonPageLoadListener) {
        checkViewModeBind();
        this.loadListener = commonPageLoadListener;
        this.mPageInkViewModel.load(str, new PageLoadListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl.5
            @Override // com.miui.libhandwritingpage.listener.PageLoadListener
            public void onLoadCompleted(boolean z, boolean z2, String str2) {
                if (MiuiHandViewModelImpl.this.loadListener != null) {
                    MiuiHandViewModelImpl.this.loadListener.onLoadCompleted(z, z2, str2);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageLoadListener
            public void onPageCount(int i) {
                if (MiuiHandViewModelImpl.this.loadListener != null) {
                    MiuiHandViewModelImpl.this.loadListener.onPageCount(i);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageLoadListener
            public void onPdfError(int i) {
                if (MiuiHandViewModelImpl.this.loadListener != null) {
                    MiuiHandViewModelImpl.this.loadListener.onPdfError(i);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageLoadListener
            public String getSuniaSinglePageBgType() {
                if (MiuiHandViewModelImpl.this.loadListener != null) {
                    return MiuiHandViewModelImpl.this.loadListener.getSuniaSinglePageBgType();
                }
                return "";
            }
        });
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void create(CommonPageBean commonPageBean, CommonPageLoadListener commonPageLoadListener, boolean z) {
        checkViewModeBind();
        this.createLoadlistener = commonPageLoadListener;
        this.mPageInkViewModel.create(new PageBean(commonPageBean.radius, commonPageBean.path, commonPageBean.pdfPath, commonPageBean.pdfPassword, commonPageBean.title, commonPageBean.moreInfo, commonPageBean.orientation, commonPageBean.cover, commonPageBean.width, commonPageBean.height, commonPageBean.createTime, commonPageBean.sizeType, commonPageBean.bgImage), new PageLoadListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl.6
            @Override // com.miui.libhandwritingpage.listener.PageLoadListener
            public void onLoadCompleted(boolean z2, boolean z3, String str) {
                if (MiuiHandViewModelImpl.this.createLoadlistener != null) {
                    MiuiHandViewModelImpl.this.createLoadlistener.onLoadCompleted(z2, z3, str);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageLoadListener
            public void onPageCount(int i) {
                if (MiuiHandViewModelImpl.this.createLoadlistener != null) {
                    MiuiHandViewModelImpl.this.createLoadlistener.onPageCount(i);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PageLoadListener
            public void onPdfError(int i) {
                if (MiuiHandViewModelImpl.this.createLoadlistener != null) {
                    MiuiHandViewModelImpl.this.createLoadlistener.onPdfError(i);
                }
            }
        }, z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPcMode(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setPcMode(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void deletePage(ArrayList<Integer> arrayList) {
        checkViewModeBind();
        this.mPageInkViewModel.deletePage(arrayList);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void insertPage(int i, int i2, int i3) {
        checkViewModeBind();
        this.mPageInkViewModel.insertPage(i, i2, i3);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void insertPage(int i, int i2, int i3, boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.insertPage(i, i2, i3, z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void removePageChangeStep(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.removePageChangeStep(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void addBitmap(String str, RectF rectF) throws Throwable {
        checkViewModeBind();
        this.mPageInkViewModel.addBitmap(str, rectF);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void addBitmap(String str, RectF rectF, PointF pointF) throws Throwable {
        checkViewModeBind();
        this.mPageInkViewModel.addBitmap(str, rectF, pointF);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public RectF getItemRectF() {
        checkViewModeBind();
        return this.mPageInkViewModel.getItemRectF();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public RectF getVisibleRectF() {
        checkViewModeBind();
        return this.mPageInkViewModel.getVisibleRectF();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void insertImageCenter(String str) throws Throwable {
        checkViewModeBind();
        ImportStrategy.insertImageCenter(this.mPageInkViewModel, str);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void insertImagePosition(String str, float f, float f2) throws Throwable {
        checkViewModeBind();
        ImportStrategy.insertImagePosition(this.mPageInkViewModel, str, f, f2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void importImageBgToPage(String str) {
        checkViewModeBind();
        ImportStrategy.importImageBgToPage(HandWritingViewApplication.INSTANCE.getApplication(), this.mPageInkViewModel, str);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void addImage(Uri uri, int i) throws Throwable {
        checkViewModeBind();
        insertImageCenter(Utils.getCopyPath(this.mContext, uri));
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public <T> void exportImage(CommonExportProcessListener<T> commonExportProcessListener) {
        checkViewModeBind();
        this.mExportProcessListener = commonExportProcessListener;
        commonExportProcessListener.exportStart();
        saveEntFile(new CommonPageSaveListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl.7
            @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
            public void onDataSaved(boolean z, String str, boolean z2) {
                if (!z) {
                    if (MiuiHandViewModelImpl.this.mExportProcessListener != null) {
                        MiuiHandViewModelImpl.this.mExportProcessListener.exportFinish(false, null);
                    }
                    Log.i(MiuiHandViewModelImpl.TAG, "showShare save fail");
                    return;
                }
                MiuiHandViewModelImpl.this.dirPath = str.substring(0, str.lastIndexOf(File.separator));
                Bundle bundle = new Bundle();
                bundle.putString("pre_view", "preview");
                bundle.putString("outFileName", "export");
                bundle.putInt("exportType", 2);
                bundle.putString("creationTitle", "导出图片1");
                bundle.putString("entFilePath", str);
                bundle.putString("dirPath", MiuiHandViewModelImpl.this.dirPath);
                bundle.putInt(Layer.KEY_WIDTH, 400);
                if (CreationServiceManager.INSTANCE.dispatchTask(2, bundle, MiuiHandViewModelImpl.this.mTaskCallback) || MiuiHandViewModelImpl.this.mExportProcessListener == null) {
                    return;
                }
                MiuiHandViewModelImpl.this.mExportProcessListener.exportFinish(false, null);
            }
        });
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setHeaderViewShow(boolean z) {
        checkViewModeBind();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPreviewOnClick(final CommonPreviewClickListener commonPreviewClickListener) {
        checkViewModeBind();
        PageInkModel pageInkModel = this.mPageInkViewModel;
        Objects.requireNonNull(commonPreviewClickListener);
        pageInkModel.setClickListener(new IPageInkModel.IPreviewClickListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl$$ExternalSyntheticLambda4
            @Override // com.miui.libhandwritingpage.model.IPageInkModel.IPreviewClickListener
            public final void onClick() {
                commonPreviewClickListener.onClick();
            }
        });
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<String> getImagesPaths() {
        checkViewModeBind();
        return this.mPageInkViewModel.getImagePaths();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<String> getAllBitmapPaths() {
        checkViewModeBind();
        return this.mPageInkViewModel.getAllSpanBitmapPaths();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<String> getDeleteBitmapPaths() {
        checkViewModeBind();
        return this.mPageInkViewModel.getDeleteSpanBitmapPaths();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public String saveThumbnail(String str) {
        checkViewModeBind();
        return exportFirstPageThumbnail(str);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public String saveThumbnail() {
        checkViewModeBind();
        return exportFirstPageThumbnail(FilePathConstant.getEngineAndPageEntPath());
    }

    private String exportFirstPageThumbnail(String str) {
        ICreationEngine creationEngine;
        MiCurveEngine curveEngine;
        Bitmap bitmap = null;
        try {
            try {
                PageInkModel pageInkModel = this.mPageInkViewModel;
                if (pageInkModel != null && pageInkModel.getCurrentItemView() != null && (creationEngine = this.mPageInkViewModel.getCurrentItemView().getCreationEngine()) != null && (curveEngine = creationEngine.getCurveEngine()) != null && curveEngine.getIsInitialized()) {
                    Bitmap pageThumbnail = curveEngine.getExportSystem().getPageThumbnail(0);
                    if (pageThumbnail == null) {
                        if (pageThumbnail != null && !pageThumbnail.isRecycled()) {
                            pageThumbnail.recycle();
                        }
                        return "";
                    }
                    File file = new File(FilePathConstant.getEntFilePathThumb(str));
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(file, FilePathConstant.THUMBNAIL_FILE_NAME);
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    try {
                        pageThumbnail.compress(Bitmap.CompressFormat.JPEG, 85, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        Log.d(TAG, "exportFirstPageThumbnail success: " + file2.getAbsolutePath());
                        String absolutePath = file2.getAbsolutePath();
                        if (pageThumbnail != null && !pageThumbnail.isRecycled()) {
                            pageThumbnail.recycle();
                        }
                        return absolutePath;
                    } catch (Throwable th) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                return "";
            } catch (Exception e) {
                Log.e(TAG, "exportFirstPageThumbnail failed", e);
                if (0 != 0 && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                return "";
            }
        } catch (Throwable th3) {
            if (0 != 0 && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            throw th3;
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSearchPathArray(int[] iArr, List<List<Path>> list) {
        checkViewModeBind();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSearchPath(List<Path> list) {
        checkViewModeBind();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onCopyPaste(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.onCopyPaste(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onDelete(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.onDelete(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onCopy(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.onCopy(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onCrop() {
        checkViewModeBind();
        this.mPageInkViewModel.onCrop();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onCropFinish() {
        checkViewModeBind();
        this.mPageInkViewModel.onCropFinish();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onCropCancel() {
        checkViewModeBind();
        this.mPageInkViewModel.onCropCancel();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onCut(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.onCut(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onPaste(float f, float f2) {
        checkViewModeBind();
        this.mPageInkViewModel.onPaste(f, f2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public List<CurveBean> getSelectedDataList() {
        checkViewModeBind();
        return this.mPageInkViewModel.getSelectedDataList();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setEdit(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setEdit(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void cancelSelect(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.cancelSelect(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public long getPageTotalSavePoint() {
        checkViewModeBind();
        return this.mPageInkViewModel.getPageTotalSavePoint();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public long getCurvePasteCount() {
        checkViewModeBind();
        return this.mPageInkViewModel.getCurvePasteCount();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void checkSelectViewPosition() {
        checkViewModeBind();
        this.mPageInkViewModel.checkSelectViewPosition();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void checkSelectViewPosition(float[] fArr) {
        checkViewModeBind();
        this.mPageInkViewModel.checkSelectViewPosition(fArr);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setSelectMenuSize(List<RectF> list, List<RectF> list2) {
        checkViewModeBind();
        this.mPageInkViewModel.setSelectMenuSize(list, list2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setShapeMenuSize(List<RectF> list, RectF rectF) {
        checkViewModeBind();
        this.mPageInkViewModel.setShapeMenuSize(list, rectF);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public Bitmap getBitmapBySize(boolean z, int i) {
        checkViewModeBind();
        return this.mPageInkViewModel.getBitmapBySize(z, i);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void copyTempSelectData() {
        checkViewModeBind();
        this.mPageInkViewModel.copyTempSelectData();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setPageFootView(PageFooterView pageFooterView) {
        checkViewModeBind();
        this.mPageInkViewModel.setPageFootView(pageFooterView);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setMultiStateViewVisibility(int i) {
        checkViewModeBind();
        this.mPageInkViewModel.setMultiStateViewVisibility(i);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setPadding(int i, int i2, int i3, int i4) {
        checkViewModeBind();
        this.mPageInkViewModel.setPadding(i, i2, i3, i4);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setSelectListener(PageTextSelectListener pageTextSelectListener) {
        checkViewModeBind();
        this.mPageInkViewModel.setSelectListener(pageTextSelectListener);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void enableContourAntLine(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.enableContourAntLine(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public MiCurveEngine getMiCurveEngine() {
        return this.mPageInkViewModel.getMiCurveEngine();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void scrollOnKeyBoardHeightChanged(Activity activity, int i, boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.scrollOnKeyBoardHeightChanged(activity, i, z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public List<SpannedData> getSelectedSpanList() {
        checkViewModeBind();
        return this.mPageInkViewModel.getSelectedSpanList();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void destroy() {
        checkViewModeBind();
        this.loadListener = null;
        this.createLoadlistener = null;
        this.pageStateChangeListener = null;
        this.mExportProcessListener = null;
        this.mPageInkViewModel.destroy();
        this.mPageInkViewModel.setPageStateListener(null);
        this.mPageInkViewModel.setShieldEventListener(null);
        this.mPageInkViewModel.setPreviewListener(null);
        this.mPageInkViewModel.setPageChangeListener(null);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public boolean save(final CommonPageSaveListener commonPageSaveListener) {
        checkViewModeBind();
        this.mPageInkViewModel.saveEntFile(new PageSaveListener() { // from class: com.miui.handwriting.common.model.MiuiHandViewModelImpl$$ExternalSyntheticLambda0
            @Override // com.miui.libhandwritingpage.listener.PageSaveListener
            public final void onDataSaved(boolean z, String str, boolean z2) {
                MiuiHandViewModelImpl.lambda$save$3(commonPageSaveListener, z, str, z2);
            }
        });
        return true;
    }

    static /* synthetic */ void lambda$save$3(CommonPageSaveListener commonPageSaveListener, boolean z, String str, boolean z2) {
        Log.d(TAG, "save: CallBack Listener Is Null:" + (commonPageSaveListener == null));
        if (commonPageSaveListener != null) {
            commonPageSaveListener.onDataSaved(z, str, z2);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean isTextEditing() {
        checkViewModeBind();
        return this.mPageInkViewModel.isTextEditing();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void enablePenOnlyMode(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.enablePenOnlyMode(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setPageCopyData(SelectCopyBean selectCopyBean) {
        checkViewModeBind();
        this.mPageInkViewModel.setPageCopyData(selectCopyBean);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void selectMoveEnable(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.selectMoveEnable(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public List<CurveBean> getAllSelectedDataList() {
        checkViewModeBind();
        return this.mPageInkViewModel.getAllSelectedDataList();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean isFromAi() {
        checkViewModeBind();
        return this.mPageInkViewModel.isFromAi().booleanValue();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public List<SpannedData> getAllSelectImageSpanned() {
        checkViewModeBind();
        return this.mPageInkViewModel.getAllSelectImageSpanned();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public List<SpannedData> getAllFromAISpanned() {
        checkViewModeBind();
        return this.mPageInkViewModel.getAllFromAISpanned();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void simulateClick(TouchPosition touchPosition) {
        checkViewModeBind();
        this.mPageInkViewModel.simulateClick(touchPosition);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public List<SpannedData> getAllFromInsertSpanned() {
        checkViewModeBind();
        return this.mPageInkViewModel.getAllFromInsertSpanned();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public RectF getNewSelectSaveRectF(RectF rectF, int i) {
        checkViewModeBind();
        return this.mPageInkViewModel.getNewSelectSaveRectF(rectF, i);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public Bitmap getSelectScreenshot(boolean z) {
        checkViewModeBind();
        return this.mPageInkViewModel.getSelectScreenshot(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public Bitmap getSelectScreenshotWithoutSpanRotate(boolean z) {
        checkViewModeBind();
        return this.mPageInkViewModel.getSelectScreenshotWithoutSpanRotate(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public float getScale() {
        checkViewModeBind();
        return this.mPageInkViewModel.getScale();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public float getFactoryScale() {
        checkViewModeBind();
        return this.mPageInkViewModel.getFactoryScale();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void toOriginPointFIgnoreScale(PointF pointF) {
        checkViewModeBind();
        this.mPageInkViewModel.toOriginPointFIgnoreScale(pointF);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean isUnLimitToNormal() {
        checkViewModeBind();
        return this.mPageInkViewModel.isUnLimitToNormal();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void toDrawReactF(RectF rectF) {
        checkViewModeBind();
        this.mPageInkViewModel.toDrawReactF(rectF);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public List<TouchPosition> getTouchPositionList() {
        checkViewModeBind();
        return this.mPageInkViewModel.getTouchPositionList();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void addText(RectF rectF) {
        checkViewModeBind();
        this.mPageInkViewModel.addText(rectF);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void deleteNewSelectSaveData(List<TouchPosition> list) {
        checkViewModeBind();
        this.mPageInkViewModel.deleteNewSelectSaveData(list);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public EngineConfig getEngineConfig() {
        checkViewModeBind();
        return this.mPageInkViewModel.getEngineConfig();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public GlobalRulerView getGlobalRulerView() {
        checkViewModeBind();
        return this.mPageInkViewModel.getGlobalRulerView();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void clearPage(int i) {
        checkViewModeBind();
        this.mPageInkViewModel.clearPage(i);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean isPcMode() {
        checkViewModeBind();
        return this.mPageInkViewModel.isPcMode();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean canChangeSizeConfig(int i) {
        checkViewModeBind();
        return this.mPageInkViewModel.canChangeSizeConfig(i);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean invalidPosition() {
        checkViewModeBind();
        return this.mPageInkViewModel.invalidPosition();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public RectF getItemVisibleRectF(int i) {
        checkViewModeBind();
        return this.mPageInkViewModel.getItemVisibleRectF(i);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void scrollBy(int i, int i2) {
        checkViewModeBind();
        this.mPageInkViewModel.scrollBy(i, i2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void scrollBy(float f, float f2) {
        checkViewModeBind();
        this.mPageInkViewModel.scrollBy(f, f2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void updateCurrentViewItem(float f, float f2) {
        checkViewModeBind();
        this.mPageInkViewModel.updateCurrentViewItem(f, f2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void addChangePageSizeStep(PageSizeConfig pageSizeConfig, PageSizeConfig pageSizeConfig2) {
        checkViewModeBind();
        this.mPageInkViewModel.addChangePageSizeStep(pageSizeConfig, pageSizeConfig2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setPageSizeConfig(PageSizeConfig pageSizeConfig, boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setPageSizeConfig(pageSizeConfig, z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public List<PageItemData> getItemDataList() {
        checkViewModeBind();
        return this.mPageInkViewModel.getItemDataList();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void undo() {
        checkViewModeBind();
        this.mPageInkViewModel.undo();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void redo() {
        checkViewModeBind();
        this.mPageInkViewModel.redo();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void onCancelEvent() {
        checkViewModeBind();
        this.mPageInkViewModel.onCancelEvent();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public ShapeProp getSelectShapeProp() {
        checkViewModeBind();
        return this.mPageInkViewModel.getSelectShapeProp();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public String getAiBitmapPath() {
        checkViewModeBind();
        return this.mPageInkViewModel.getAiBitmapPath();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public RectF getSelectRectFWithoutOffset(RectF rectF, int i) {
        checkViewModeBind();
        return this.mPageInkViewModel.getSelectRectFWithoutOffset(rectF, i);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public SpannedData getClickImage() {
        checkViewModeBind();
        return this.mPageInkViewModel.getClickImage();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public PageItemView getCurrentItemView() {
        checkViewModeBind();
        return this.mPageInkViewModel.getCurrentItemView();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void addSelectRectBitmap(String str, RectF rectF, int i, boolean z, DrawErrorCallback drawErrorCallback) throws Throwable {
        checkViewModeBind();
        this.mPageInkViewModel.addSelectRectBitmap(str, rectF, i, z, drawErrorCallback);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public CanvasMaskEntity getSelectScreenshotAndMask(boolean z, boolean z2, float f, float f2, int i) {
        checkViewModeBind();
        return this.mPageInkViewModel.getSelectScreenshotAndMask(z, z2, f, f2, i);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setShieldEventListener(MiuiToolBoxView miuiToolBoxView) {
        checkViewModeBind();
        this.mPageInkViewModel.setShieldEventListener(miuiToolBoxView);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setSelect(boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setSelect(z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean isDrawing() {
        checkViewModeBind();
        return this.mPageInkViewModel.isDrawing();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setSelect(boolean z, boolean z2) {
        checkViewModeBind();
        this.mPageInkViewModel.setSelect(z, z2);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setStateView(PageStateView pageStateView) {
        checkViewModeBind();
        this.mPageInkViewModel.setStateView(pageStateView);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean isAiModel() {
        checkViewModeBind();
        return this.mPageInkViewModel.isAiModel();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public void setSelectShapeAttr(ShapeEditAttr shapeEditAttr, boolean z) {
        checkViewModeBind();
        this.mPageInkViewModel.setSelectShapeAttr(shapeEditAttr, z);
    }

    private RectF getRecognizeRectF(PartiBean partiBean) {
        RectF rectF = new RectF();
        for (BoundingBox boundingBox : partiBean.getLine_box()) {
            if (rectF.isEmpty()) {
                rectF.set(boundingBox.getX(), boundingBox.getY(), boundingBox.getX() + boundingBox.getWidth(), boundingBox.getY() + boundingBox.getHeight());
            } else {
                rectF.union(boundingBox.getX(), boundingBox.getY(), boundingBox.getX() + boundingBox.getWidth(), boundingBox.getY() + boundingBox.getHeight());
            }
        }
        return rectF;
    }

    private float getLineTextSize(PartiBean partiBean, RectF rectF) {
        float fMin = 0.0f;
        for (String str : partiBean.getLabel().split(StringUtils.LF)) {
            float textSize = NumUtil.parseTextSize(str, rectF.width());
            if (fMin == 0.0f) {
                fMin = textSize;
            }
            fMin = Math.min(fMin, textSize);
        }
        return Math.max(fMin, 10.0f);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean selectByRect(RectF rectF, boolean z) {
        checkViewModeBind();
        return this.mPageInkViewModel.selectByRect(rectF, z);
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingModel
    public boolean selectByRect(RectF rectF) {
        checkViewModeBind();
        return this.mPageInkViewModel.selectByRect(rectF);
    }

    private void checkViewModeBind() {
        if (this.mPageInkViewModel == null) {
            throw new IllegalStateException("you should init this viewmodel");
        }
    }

    private void printStack() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        Log.d(TAG, "print stack---- start ");
        for (StackTraceElement stackTraceElement : stackTrace) {
            Log.d(TAG, "print stack---- " + stackTraceElement.toString());
        }
        Log.d(TAG, "print stack---- end");
    }
}
