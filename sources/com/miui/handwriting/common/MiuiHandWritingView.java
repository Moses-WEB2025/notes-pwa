package com.miui.handwriting.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.miui.handwriting.common.data.bean.CommonPageBean;
import com.miui.handwriting.common.data.bean.CommonPageData;
import com.miui.handwriting.common.data.bean.CommonSearchWrapper;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.data.bean.PenBean;
import com.miui.handwriting.common.data.dataset.PenSet;
import com.miui.handwriting.common.listener.CanvasElementTransactCallback;
import com.miui.handwriting.common.listener.CommonExportProcessListener;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPageStateListener;
import com.miui.handwriting.common.listener.CommonPreviewClickListener;
import com.miui.handwriting.common.listener.ToolBoxActionCallBack;
import com.miui.handwriting.common.mask.CanvasElementTransactionMask;
import com.miui.handwriting.common.model.IMiuiHandWritingCommonModel;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.model.MiuiHandViewModelImpl;
import com.miui.handwriting.common.view.check.ICheck;
import com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView;
import com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView;
import com.miui.handwriting.common.view.handview.MiuiHandWritingImpl;
import com.miui.handwriting.common.view.page.HeaderView;
import com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu;
import com.miui.handwriting.common.view.quickMenu.base.StylusPenQuickMenuType;
import com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar;
import com.miui.handwriting.common.view.toolbar.MiuiToolBoxView;
import com.miui.libhandwritingpage.listener.PreviewListener;
import com.miui.libhandwritingpage.view.PageItemView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import miuix.appcompat.app.AlertDialog;
import xc.engine.core.algorithm.bean.StrokeClassificationCurve;
import xc.engine.operate.canvas.track.delete.DeleteProp;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiHandWritingView extends FrameLayout implements ICommonMiuiHandWritingView, IMiuiHandWritingCommonModel, ICommonMiuiHandToolbar {
    private Rect layoutRect;
    boolean mFocus;
    private final IMiuiHandWritingModel mHandWritingModel;
    private final MiuiHandWritingImpl mHandWritingView;
    private Boolean mIsStylusQuickMenuLocked;
    private final MiuiToolBoxView mMiuiHandToolbar;
    private View mPaintView;
    private PenSet.PenSetChangeListener mPenSetChangeListener;
    private StylusPenQuickMenu mStylusPenQuickMenu;

    public MiuiHandWritingView(Context context) {
        this(context, null);
    }

    public MiuiHandWritingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MiuiHandWritingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.layoutRect = new Rect();
        this.mIsStylusQuickMenuLocked = false;
        this.mPaintView = null;
        this.mFocus = true;
        MiuiHandViewModelImpl miuiHandViewModelImpl = new MiuiHandViewModelImpl(context);
        this.mHandWritingModel = miuiHandViewModelImpl;
        MiuiToolBoxView miuiToolBoxView = new MiuiToolBoxView(context, attributeSet, i);
        MiuiHandWritingImpl miuiHandWritingImpl = new MiuiHandWritingImpl(context, miuiHandViewModelImpl, miuiToolBoxView);
        this.mHandWritingView = miuiHandWritingImpl;
        this.mMiuiHandToolbar = miuiToolBoxView;
        this.mPaintView = miuiHandWritingImpl.initView(attributeSet, i, miuiHandViewModelImpl);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        ViewGroup.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 1;
        addView(miuiToolBoxView, layoutParams);
        addView(this.mPaintView, layoutParams2);
        miuiHandWritingImpl.init();
        initStylusPenQuickMenu();
    }

    private /* synthetic */ void lambda$new$0(MiuiToolBoxView miuiToolBoxView, boolean z) {
        miuiToolBoxView.pageLimitChange(z);
        this.mHandWritingView.getThumbnailManager().pageLimitChange(z);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel == null || !iMiuiHandWritingModel.isCanWrite()) {
            return;
        }
        this.mPaintView.measure(i, View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2) - this.mMiuiHandToolbar.getMeasuredHeight(), 1073741824));
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null && iMiuiHandWritingModel.isCanWrite()) {
            int measuredWidth = this.mMiuiHandToolbar.getMeasuredWidth();
            int measuredHeight = this.mMiuiHandToolbar.getMeasuredHeight();
            this.mMiuiHandToolbar.layout(0, 0, measuredWidth, measuredHeight);
            this.mPaintView.layout(0, measuredHeight, measuredWidth, i4);
            StylusPenQuickMenu stylusPenQuickMenu = this.mStylusPenQuickMenu;
            if (stylusPenQuickMenu != null) {
                stylusPenQuickMenu.layout(i, i2, i3, i4);
            }
            this.layoutRect.set(i, i2, i3, i4);
            return;
        }
        this.mPaintView.layout(0, 0, i3, i4);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.onConfigurationChanged(configuration);
        }
        StylusPenQuickMenu stylusPenQuickMenu = this.mStylusPenQuickMenu;
        if (stylusPenQuickMenu != null) {
            stylusPenQuickMenu.destory();
            this.mStylusPenQuickMenu = null;
            initStylusPenQuickMenu();
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setSaveClickListener(View.OnClickListener onClickListener) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setSaveClickListener(onClickListener);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setBackClickEnable(boolean z) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setBackClickEnable(z);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setFromPc(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel;
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setFromPc(z);
        }
        if (!z || (iMiuiHandWritingModel = this.mHandWritingModel) == null) {
            return;
        }
        iMiuiHandWritingModel.setPageFootView(null);
        this.mHandWritingModel.setMultiStateViewVisibility(8);
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void initUIProperties() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.initUIProperties();
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setShareClickListener(View.OnClickListener onClickListener) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setShareClickListener(onClickListener);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public boolean save(CommonPageSaveListener commonPageSaveListener) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.save(commonPageSaveListener);
        }
        return false;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void onWindowFocused() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.onWindowFocused();
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void enableViewSelect() {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.enableViewSelect();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void onStepChanged(int i, int i2, boolean z, boolean z2, boolean z3) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.onStepChanged(i, i2, z, z2, z3);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void onDoubleUndo() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.onDoubleUndo();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setTitleAndTime(String str, long j) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.setTitleAndTime(str, j);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setTitleChangedListener(HeaderView.OnTitleChangedListener onTitleChangedListener) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.setTitleChangedListener(onTitleChangedListener);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setActionCallBack(ToolBoxActionCallBack toolBoxActionCallBack) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setActionCallBack(toolBoxActionCallBack);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView, com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void copyAsText(PartiBean partiBean) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.copyAsText(partiBean);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void recognizeAsText(final PartiBean partiBean, final int i) {
        post(new Runnable() { // from class: com.miui.handwriting.common.MiuiHandWritingView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m694xc008664a(partiBean, i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$recognizeAsText$1$com-miui-handwriting-common-MiuiHandWritingView, reason: not valid java name */
    /* synthetic */ void m694xc008664a(PartiBean partiBean, int i) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.recognizeAsText(partiBean, i);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public int getToolBoxHeight() {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            return miuiToolBoxView.getToolBoxHeight();
        }
        return -1;
    }

    public void dismissPasteDialog() {
        this.mHandWritingView.dismissLoadingDialog();
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void dismissLoadingDialog() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.dismissLoadingDialog();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void updateBackgroundMargin(float f) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.updateBackgroundMargin(f);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void release() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.release();
        }
        if (this.mPenSetChangeListener != null) {
            PenSet penSet = this.mMiuiHandToolbar.getPenSet();
            if (penSet != null) {
                penSet.removeListener(this.mPenSetChangeListener);
            }
            this.mPenSetChangeListener = null;
        }
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView == null || !(miuiToolBoxView instanceof MiuiToolBoxView)) {
            return;
        }
        miuiToolBoxView.destroy();
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void replaceSelected(String str) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.replaceSelected(str);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void replaceSelected(String str, CreationImage creationImage, CanvasElementTransactionMask canvasElementTransactionMask) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.replaceSelected(str, creationImage, canvasElementTransactionMask);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void dismissAILoadingDialog() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.dismissAILoadingDialog();
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void dismissPopupWindow() {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.dismissPopupWindow();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setCanvasElementTransactListener(CanvasElementTransactCallback canvasElementTransactCallback) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.setCanvasElementTransactListener(canvasElementTransactCallback);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void enableAi(boolean z) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.enableAi(z);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setShareIconState(boolean z) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setShareIconState(z);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void adjustPageSize(int i, int i2, int i3) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.adjustPageSize(i, i2, i3);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public int getCurrentPosition() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getCurrentPosition();
        }
        return -1;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPageSizeDialogShow(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.getCurrentPosition();
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void onResume() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.onResume();
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPageBackground(int i, int i2, int i3) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setPageBackground(i, i2, i3);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void calculateUnLimitExportArea() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.calculateUnLimitExportArea();
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPreviewListener(PreviewListener previewListener) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setPreviewListener(previewListener);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPageStateListener(CommonPageStateListener commonPageStateListener) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setPageStateListener(commonPageStateListener);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public boolean isUnlimited() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.isUnlimited();
        }
        return false;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void expanseUnLimitPage() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.expanseUnLimitPage();
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public boolean isContentEmpty() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.isContentEmpty();
        }
        return true;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void saveEntFile(CommonPageSaveListener commonPageSaveListener) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.saveEntFile(commonPageSaveListener);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void finishSelect() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.finishSelect();
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public int getPageCount() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getPageCount();
        }
        return 0;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public AlertDialog createLimitDialog(Activity activity) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.createLimitDialog(activity);
        }
        return null;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void onPause() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.onPause();
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSelectCopyDataToClip() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setSelectCopyDataToClip();
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public boolean isContentChanged() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.isContentChanged();
        }
        return false;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<CommonPageData> getPageDatas() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getPageDatas();
        }
        return new ArrayList();
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public RectF getSelectAiRect() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            return miuiHandWritingImpl.getSelectAiRect();
        }
        return null;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSearchResult(int i, int i2, int i3, CommonSearchWrapper commonSearchWrapper) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setSearchResult(i, i2, i3, commonSearchWrapper);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setDoubleFingerClickUndo(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setDoubleFingerClickUndo(z);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void enableEstimate(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.enableEstimate(z);
        }
    }

    public void registerToolBarListener() {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.registerListener();
        }
    }

    public void refreshPenType() {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.refreshPenType();
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setOneShape(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setOneShape(z);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void load(String str, CommonPageLoadListener commonPageLoadListener) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.load(str, commonPageLoadListener);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void create(CommonPageBean commonPageBean, CommonPageLoadListener commonPageLoadListener, boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.create(commonPageBean, commonPageLoadListener, z);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPcMode(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setPcMode(z);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void deletePage(ArrayList<Integer> arrayList) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.deletePage(arrayList);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void insertPage(int i, int i2, int i3) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.insertPage(i, i2, i3);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void insertPage(int i, int i2, int i3, boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.insertPage(i, i2, i3, z);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void removePageChangeStep(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.removePageChangeStep(z);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void addBitmap(String str, RectF rectF) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.addBitmap(str, rectF);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void addBitmap(String str, RectF rectF, PointF pointF) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.addBitmap(str, rectF, pointF);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public RectF getItemRectF() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getItemRectF();
        }
        return null;
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public RectF getVisibleRectF() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getVisibleRectF();
        }
        return null;
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setSelectCheck(ICheck iCheck) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.setSelectCheck(iCheck);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setToolbarCheck(ICheck iCheck, boolean z) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setToolbarCheck(iCheck, z);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void insertImageCenter(String str) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.insertImageCenter(str);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void insertImagePosition(String str, float f, float f2) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.insertImagePosition(str, f, f2);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void importImageBgToPage(String str) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.importImageBgToPage(str);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setBgImage(int i, String str) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setBgImage(i, str);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<StrokeClassificationCurve> getSelectedStrokeClassificationCurves() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getSelectedStrokeClassificationCurves();
        }
        return Collections.emptyList();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setCanWrite(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setCanWrite(z);
        }
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setCanWrite(z);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void addImage(Uri uri, int i) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.enableViewSelect();
        }
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.addImage(uri, i);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public <T> void exportImage(CommonExportProcessListener<T> commonExportProcessListener) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.exportImage(commonExportProcessListener);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void setToolBoxViewTopPadding(int i, int i2) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setToolBoxViewTopPadding(i, i2);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void onStepChanged(int i, boolean z, boolean z2) {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.onStepChanged(i, z, z2);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void clearTitleCursor() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.clearTitleCursor();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setTitleTime(long j) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.setTitleTime(j);
        }
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public void changeColorMode(int i, int i2) {
        this.mMiuiHandToolbar.changeColorMode(i, i2);
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setTitle(String str, int i) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.setTitle(str, i);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public String getTitle(int i) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            return miuiHandWritingImpl.getTitle(i);
        }
        return "";
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setHeaderViewShow(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setHeaderViewShow(z);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setPreviewOnClick(CommonPreviewClickListener commonPreviewClickListener) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setPreviewOnClick(commonPreviewClickListener);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<String> getImagesPaths() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getImagesPaths();
        }
        return Collections.emptyList();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<String> getAllBitmapPaths() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getAllBitmapPaths();
        }
        return Collections.emptyList();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public List<String> getDeleteBitmapPaths() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getDeleteBitmapPaths();
        }
        return Collections.emptyList();
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public String saveThumbnail(String str) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.saveThumbnail(str);
        }
        return "";
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public String saveThumbnail() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.saveThumbnail();
        }
        return "";
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSearchPathArray(int[] iArr, List<List<Path>> list) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setSearchPathArray(iArr, list);
        }
    }

    @Override // com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSearchPath(List<Path> list) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.setSearchPath(list);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void showLoadingDialog() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.showLoadingDialog();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void insertAiImage(String str, CreationImage creationImage, CanvasElementTransactionMask canvasElementTransactionMask) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.insertAiImage(str, creationImage, canvasElementTransactionMask);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void startAiFormClassification(boolean z, String str) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.startAiFormClassification(z, str);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void addPage(boolean z) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.addPage(z);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setToolBarHeight(int i) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.setToolBarHeight(i);
        }
    }

    public void preventBackByAI() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.preventBackByAI();
        }
    }

    public void allowBackByAI() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl != null) {
            miuiHandWritingImpl.allowBackByAI();
        }
    }

    public boolean isPreventBackByAI() {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        return miuiHandWritingImpl != null && miuiHandWritingImpl.isPreventBackByAI();
    }

    public BaseMiuiHandWritingView getHandWritingView() {
        return this.mHandWritingView;
    }

    public void bindPageId(long j) {
        if (this.mHandWritingModel != null) {
            this.mHandWritingView.bindPageId(j);
        }
    }

    public void selectMoveEnable(boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.selectMoveEnable(z);
        }
    }

    public void jumpPage(int i, boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.jumpPage(i, z);
        }
    }

    public void resetScale(Boolean bool, Float f) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.resetScale(bool, f);
        }
    }

    public void resetCurveCenter() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.resetCurveCenter();
        }
    }

    public float getScale() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getScale();
        }
        return 1.0f;
    }

    public float getFactoryScale() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getFactoryScale();
        }
        return 1.0f;
    }

    public PageItemView getCurrentItemView() {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            return iMiuiHandWritingModel.getCurrentItemView();
        }
        return null;
    }

    public void expanseUnLimitedPage(float f, float f2, boolean z) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.expanseUnLimitedPage(f, f2, z);
        }
    }

    public void aiScrollBy(float f, float f2) {
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.scrollBy(f, f2);
        }
    }

    public Rect getLayoutRect() {
        return this.layoutRect;
    }

    public DeleteProp getCurDeleteProp() {
        return this.mMiuiHandToolbar.getCurrentEraserPen().covert();
    }

    private void initStylusPenQuickMenu() {
        StylusPenQuickMenu stylusPenQuickMenu = new StylusPenQuickMenu(getContext());
        this.mStylusPenQuickMenu = stylusPenQuickMenu;
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            miuiToolBoxView.setStylusPenQuickMenu(stylusPenQuickMenu);
            PenSet penSet = this.mMiuiHandToolbar.getPenSet();
            if (penSet != null) {
                PenBean curPenBean = penSet.getCurPenBean();
                if (curPenBean != null) {
                    this.mStylusPenQuickMenu.setPenBean(curPenBean);
                    this.mStylusPenQuickMenu.setCurPenInfo(curPenBean.convert());
                }
                PenSet.PenSetChangeListener penSetChangeListener = new PenSet.PenSetChangeListener() { // from class: com.miui.handwriting.common.MiuiHandWritingView.1
                    @Override // com.miui.handwriting.common.data.dataset.PenSet.PenSetChangeListener
                    public void onPenBeanChanged(PenBean penBean) {
                        if (MiuiHandWritingView.this.mStylusPenQuickMenu == null || penBean == null) {
                            return;
                        }
                        try {
                            MiuiHandWritingView.this.mStylusPenQuickMenu.setPenBean(penBean);
                            MiuiHandWritingView.this.mStylusPenQuickMenu.setCurPenInfo(penBean.convert());
                        } catch (Exception unused) {
                        }
                    }
                };
                this.mPenSetChangeListener = penSetChangeListener;
                penSet.addListener(penSetChangeListener);
            }
        }
        DeleteProp curDeleteProp = getCurDeleteProp();
        this.mStylusPenQuickMenu.setEraser(curDeleteProp.getDeleteType(), curDeleteProp.getDeleteRadius());
    }

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    public PenSet getPenSet() {
        MiuiToolBoxView miuiToolBoxView = this.mMiuiHandToolbar;
        if (miuiToolBoxView != null) {
            return miuiToolBoxView.getPenSet();
        }
        return null;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 9) {
            this.mStylusPenQuickMenu.setStylusHovered(true);
        } else if (motionEvent.getAction() == 10) {
            this.mStylusPenQuickMenu.setStylusHovered(false);
        }
        this.mStylusPenQuickMenu.setStylusPenHoverPosition(motionEvent.getX(), motionEvent.getY());
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!this.mIsStylusQuickMenuLocked.booleanValue()) {
            int i2 = i == 194 ? Settings.System.getInt(getContext().getContentResolver(), "stylus_pinch_status", 5) : -1;
            if (i == 195) {
                i2 = Settings.System.getInt(getContext().getContentResolver(), "stylus_double_click_status", 1);
            }
            if (i2 == 4 || i2 == 3) {
                this.mStylusPenQuickMenu.autoShowAndClose(this, getLayoutRect(), i2 == 4 ? StylusPenQuickMenuType.PARAMETER_DISK : StylusPenQuickMenuType.COLOR_DISK);
                this.mIsStylusQuickMenuLocked = true;
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        this.mIsStylusQuickMenuLocked = false;
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            onKeyDown(keyEvent.getKeyCode(), keyEvent);
        }
        if (keyEvent.getAction() == 1) {
            onKeyUp(keyEvent.getKeyCode(), keyEvent);
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (new Rect(this.mMiuiHandToolbar.getLeft(), this.mMiuiHandToolbar.getTop(), this.mMiuiHandToolbar.getRight(), this.mMiuiHandToolbar.getBottom()).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
            this.mHandWritingView.getCreationPaintLayout().dismissLatexPopupWindow();
        }
        if (this.mFocus) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    public void setPageIndexVisible(boolean z) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl == null || miuiHandWritingImpl.getCreationPaintLayout() == null) {
            return;
        }
        this.mHandWritingView.getCreationPaintLayout().setPageIndexVisible(z);
    }

    public void setTitleViewVisible(boolean z) {
        MiuiHandWritingImpl miuiHandWritingImpl = this.mHandWritingView;
        if (miuiHandWritingImpl == null || miuiHandWritingImpl.getCreationPaintLayout() == null) {
            return;
        }
        this.mHandWritingView.getCreationPaintLayout().setTitleViewVisible(z);
    }
}
