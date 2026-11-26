package com.miui.handwriting.common.view.handview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.gson.Gson;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.ai.AiCreationHelper;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.miui.handwriting.common.clipboard.ClipManager;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.data.dataset.PreviewDataSet;
import com.miui.handwriting.common.data.dataset.ShapeEditSet;
import com.miui.handwriting.common.listener.CanvasElementTransactCallback;
import com.miui.handwriting.common.mask.CanvasElementTransactionMask;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.softinputhelper.KeyboardHelper;
import com.miui.handwriting.common.softinputhelper.KeyboardMarginProcessor;
import com.miui.handwriting.common.tools.AiBitmapHelper;
import com.miui.handwriting.common.tools.ColorUtils;
import com.miui.handwriting.common.tools.MainThreadUtils;
import com.miui.handwriting.common.tools.PreferenceUtils;
import com.miui.handwriting.common.tools.ScreenModeUtils;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.handwriting.common.view.check.ICheck;
import com.miui.handwriting.common.view.page.HeaderView;
import com.miui.handwriting.common.view.popup.ColorPickerPopupWindow;
import com.miui.handwriting.common.view.popup.CropPopupWindow;
import com.miui.handwriting.common.view.popup.PastePopupWindow;
import com.miui.handwriting.common.view.popup.SelectPopupWindow;
import com.miui.handwriting.common.view.popup.SelectPopupWindowInner;
import com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow;
import com.miui.handwriting.common.view.popup.ShapeEditColorPopupWindow;
import com.miui.handwriting.common.view.popup.ShapeEditPopupWindow;
import com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager;
import com.miui.handwriting.common.view.toolbar.IMiuiHandToolbar;
import com.miui.libhandwritingpage.listener.PageTextSelectListener;
import com.miui.libhandwritingpage.listener.PreviewListener;
import com.miui.libhandwritingpage.newbean.beanCurve.CurveBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import com.miui.libhandwritingpage.utils.MathUtils;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpage.view.GlobalRulerView;
import com.miui.libhandwritingpage.view.PageItemView;
import defpackage.R2;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.IntSupplier;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.ProgressDialog;
import miuix.autodensity.AutoDensityConfig;
import net.lingala.zip4j.util.InternalZipConstants;
import xc.engine.api.CreationPageSDK;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.edit.select.ShapeEditAttr;
import xc.engine.utils.DisplayUtils;
import xc.engine.utils.FileUtil;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiHandWritingImpl extends BaseMiuiHandWritingView implements PageTextSelectListener {
    private static final int BACK_BY_AI_DELAY_TIME = 1500;
    private static final String TAG = "MiuiHandWritingImpl";
    private String local;
    private AiCreationHelper mAiCreationHelper;
    ICheck mCheck;
    private final WeakReference<Context> mContextRef;
    private CreationPaintLayout mCreationPaintLayout;
    private CropPopupWindow mCropPopupWindows;
    private final IMiuiHandWritingModel mHandWritingModel;
    private boolean mIsSingleBitmap;
    private boolean mIsSingleText;
    private AlertDialog mNetworkCtaDialog;
    private HeaderView.OnTitleChangedListener mOnTitleChangedListener;
    private PastePopupWindow mPastePopWindow;
    private int mPopWindowOuterLeft;
    private int mPopWindowOuterTop;
    private List<PagePreviewData> mPreviewDataList;
    private RectF mRectF;
    private ShapeColorPickerPopupWindow mShapeColorPickerPopupWindow;
    private ShapeEditPopupWindow mShapeEditPopupWindow;
    private CreationShieldPageInkView mShieldPageInkView;
    private TitleFocusChangeListener mTitleFocusChangeListener;
    private TitleTextWatcher mTitleTextWatcher;
    private EditText mTitleView;
    private final IMiuiHandToolbar mToolBoxView;
    private long pageId;
    private ProgressDialog pasteDialog;
    private Bitmap pointBitmap;
    private SelectPopupWindow selectActionModePopWindow;
    private SelectPopupWindowInner selectActionModePopWindowInner;
    private Paint selectPaint;
    private Paint touchPointPaint;
    private KeyboardHelper mEditKeyboardDetector = null;
    private boolean mIsKeyboardVisible = false;
    private int screenOrientation = 1;
    private boolean isNightMode = false;
    private boolean mIsPreventBackByAI = false;
    int lastPageSize = 0;
    private Runnable dismissSelectionActionModePopWindowRunnable = new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.3
        @Override // java.lang.Runnable
        public void run() {
            if (MiuiHandWritingImpl.this.selectActionModePopWindow != null && MiuiHandWritingImpl.this.selectActionModePopWindow.isShowing()) {
                MiuiHandWritingImpl.this.selectActionModePopWindow.dismiss();
            }
            if (MiuiHandWritingImpl.this.selectActionModePopWindowInner == null || !MiuiHandWritingImpl.this.selectActionModePopWindowInner.isShowing()) {
                return;
            }
            MiuiHandWritingImpl.this.selectActionModePopWindowInner.dismiss();
        }
    };
    private float[] lastFirstPoint = null;
    private float[] lastCenterPoint = null;
    private final float zone = 2.5f;
    private final float innerZone = 0.5f;
    List<float[]> pointsArr = new ArrayList();
    private final SelectPopupWindow.OnActionClickListener mActionClickListener = new AnonymousClass6();
    private final int ALPHA_TIME = 500;
    private final int DISMISS_TIME = 5000;
    public Runnable pasteDialogAlphaRunnable = new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.7
        @Override // java.lang.Runnable
        public void run() {
            WindowManager.LayoutParams attributes;
            if (MiuiHandWritingImpl.this.pasteDialog != null) {
                MiuiHandWritingImpl.this.pasteDialog.show();
                Window window = MiuiHandWritingImpl.this.pasteDialog.getWindow();
                if (window == null || (attributes = window.getAttributes()) == null) {
                    return;
                }
                attributes.alpha = 1.0f;
                window.setAttributes(attributes);
            }
        }
    };
    public Runnable pasteDialogDismissRunnable = new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.8
        @Override // java.lang.Runnable
        public void run() {
            if (MiuiHandWritingImpl.this.pasteDialog != null) {
                MiuiHandWritingImpl.this.pasteDialog.dismiss();
            }
        }
    };
    private AllowBackByAIRunnable mAllowBackByAIRunnable = new AllowBackByAIRunnable(this);

    private void initSDKCallback() {
    }

    static /* synthetic */ void lambda$onPastePopWindow$6(DialogInterface dialogInterface, int i) {
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void drawRotateAndScaleButton(Canvas canvas, int i, Path path, View view, boolean z) {
    }

    private static class AllowBackByAIRunnable implements Runnable {
        private final WeakReference<MiuiHandWritingImpl> implRef;

        AllowBackByAIRunnable(MiuiHandWritingImpl miuiHandWritingImpl) {
            this.implRef = new WeakReference<>(miuiHandWritingImpl);
        }

        @Override // java.lang.Runnable
        public void run() {
            MiuiHandWritingImpl miuiHandWritingImpl = this.implRef.get();
            if (miuiHandWritingImpl != null) {
                miuiHandWritingImpl.mIsPreventBackByAI = false;
            }
        }
    }

    private static class TitleTextWatcher implements TextWatcher {
        private final WeakReference<MiuiHandWritingImpl> implRef;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        TitleTextWatcher(MiuiHandWritingImpl miuiHandWritingImpl) {
            this.implRef = new WeakReference<>(miuiHandWritingImpl);
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            MiuiHandWritingImpl miuiHandWritingImpl = this.implRef.get();
            if (miuiHandWritingImpl == null || miuiHandWritingImpl.mOnTitleChangedListener == null) {
                return;
            }
            miuiHandWritingImpl.mOnTitleChangedListener.onTitleChanged(editable.toString());
        }
    }

    private static class TitleFocusChangeListener implements View.OnFocusChangeListener {
        private final WeakReference<MiuiHandWritingImpl> implRef;
        private final WeakReference<IMiuiHandToolbar> toolBoxRef;

        TitleFocusChangeListener(MiuiHandWritingImpl miuiHandWritingImpl, IMiuiHandToolbar iMiuiHandToolbar) {
            this.implRef = new WeakReference<>(miuiHandWritingImpl);
            this.toolBoxRef = new WeakReference<>(iMiuiHandToolbar);
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            MiuiHandWritingImpl miuiHandWritingImpl = this.implRef.get();
            IMiuiHandToolbar iMiuiHandToolbar = this.toolBoxRef.get();
            if (miuiHandWritingImpl != null) {
                Log.i(MiuiHandWritingImpl.TAG, "EtTitle  onFocusChange hasFocus=" + z);
                if (z || iMiuiHandToolbar == null) {
                    return;
                }
                iMiuiHandToolbar.setFocus(true);
            }
        }
    }

    public MiuiHandWritingImpl(Context context, IMiuiHandWritingModel iMiuiHandWritingModel, IMiuiHandToolbar iMiuiHandToolbar) {
        this.mContextRef = new WeakReference<>(context);
        this.mHandWritingModel = iMiuiHandWritingModel;
        this.mToolBoxView = iMiuiHandToolbar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Context getContext() {
        Context context = this.mContextRef.get();
        return context == null ? HandWritingViewApplication.INSTANCE.getApplication() : context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isActivityValid() {
        Context context = this.mContextRef.get();
        if (!(context instanceof Activity)) {
            return context != null;
        }
        Activity activity = (Activity) context;
        return (activity == null || activity.isFinishing() || activity.isDestroyed()) ? false : true;
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void init() {
        if (isActivityValid()) {
            this.mToolBoxView.initToolBox(this.mHandWritingModel, this);
            setKeyboardDetector();
            this.mAiCreationHelper = new AiCreationHelper(getContext(), this.mHandWritingModel, this.mShieldPageInkView, this.mToolBoxView);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void adjustPageSize(int i, int i2, int i3) {
        if (isActivityValid()) {
            PageSizeConfig pageSizeConfig = this.mToolBoxView.getPageSizeConfig(this.mHandWritingModel.getCurrentPosition(), this.mHandWritingModel.isUnlimited());
            PageSizeConfig pageSizeConfig2 = new PageSizeConfig(pageSizeConfig);
            pageSizeConfig2.width = i;
            pageSizeConfig2.height = i2;
            pageSizeConfig2.type = i3;
            pageSizeConfig2.isUnLimit = false;
            if (getContext().getResources().getConfiguration().orientation == 1) {
                pageSizeConfig2.orientation = 0;
            } else {
                pageSizeConfig2.orientation = 1;
            }
            this.mToolBoxView.adjustPageSize(pageSizeConfig2, pageSizeConfig, false);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void onPreviewUpdate(List<PagePreviewData> list) {
        if (isActivityValid()) {
            AiCreationHelper aiCreationHelper = this.mAiCreationHelper;
            if (aiCreationHelper != null) {
                aiCreationHelper.setPreviewDataList(list);
            }
            Log.d(TAG, "onPreviewUpdate,size:" + list.size());
            updateBgFollowLast(list);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void onPreviewItemUpdated(int i, PagePreviewData pagePreviewData) {
        if (isActivityValid()) {
            Log.d(TAG, "PreviewData:" + i + ":" + pagePreviewData.bgColor);
            IMiuiHandToolbar iMiuiHandToolbar = this.mToolBoxView;
            if (iMiuiHandToolbar != null) {
                iMiuiHandToolbar.onPreviewItemUpdated(i, pagePreviewData);
            }
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void onBgColorItemUpdated(int i, int i2) {
        if (isActivityValid()) {
            Log.d(TAG, "onBgColorItemUpdated:" + i + ":" + i2);
            List<PagePreviewData> list = this.mPreviewDataList;
            if (list == null || list.get(i) == null) {
                return;
            }
            this.mPreviewDataList.get(i).bgColor = i2;
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void onStepChanged(int i, int i2, boolean z, boolean z2, boolean z3) {
        if (isActivityValid()) {
            IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
            if (iMiuiHandWritingModel != null) {
                iMiuiHandWritingModel.setModifyFlagToItemData(i2, true);
            }
            IMiuiHandToolbar iMiuiHandToolbar = this.mToolBoxView;
            if (iMiuiHandToolbar != null) {
                iMiuiHandToolbar.onStepChanged(i, z, z2);
            }
            if (i != 4 && i != 3 && z3) {
                dismissLoadingDialog();
            }
            if ((i == 1 || i == 0) && Utils.getActivity(getContext()) != null && KeyboardHelper.isKeyboardVisible(Utils.getActivity(getContext()))) {
                Utils.hideSoftInput(this.mCreationPaintLayout);
            }
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void onDoubleUndo() {
        if (isActivityValid()) {
            showLoadingDialog();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public View initView(AttributeSet attributeSet, int i, IMiuiHandWritingModel iMiuiHandWritingModel) {
        if (!isActivityValid()) {
            return null;
        }
        Log.d(TAG, "MiuiHandWritingImpl init begin with [sets and style]");
        this.mCreationPaintLayout = new CreationPaintLayout(getContext(), attributeSet, i, iMiuiHandWritingModel);
        innerInitView();
        return this.mCreationPaintLayout;
    }

    public CreationPaintLayout getCreationPaintLayout() {
        return this.mCreationPaintLayout;
    }

    private void innerInitView() {
        if (isActivityValid()) {
            CreationShieldPageInkView pageInkView = this.mCreationPaintLayout.getPageInkView();
            this.mShieldPageInkView = pageInkView;
            final IMiuiHandToolbar iMiuiHandToolbar = this.mToolBoxView;
            Objects.requireNonNull(iMiuiHandToolbar);
            pageInkView.setToolViewHeightCallback(new IntSupplier() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.IntSupplier
                public final int getAsInt() {
                    return iMiuiHandToolbar.getToolBoxHeight();
                }
            });
            this.mHandWritingModel.setSelectListener(this);
            if (ScreenModeUtils.isFull(getContext())) {
                this.mHandWritingModel.setPadding((int) getContext().getResources().getDimension(R.dimen.creation_paint_view_margin), 0, (int) getContext().getResources().getDimension(R.dimen.creation_paint_view_margin), 0);
            }
            this.mTitleView = this.mCreationPaintLayout.getTitleView();
            TitleTextWatcher titleTextWatcher = new TitleTextWatcher(this);
            this.mTitleTextWatcher = titleTextWatcher;
            this.mTitleView.addTextChangedListener(titleTextWatcher);
            TitleFocusChangeListener titleFocusChangeListener = new TitleFocusChangeListener(this, this.mToolBoxView);
            this.mTitleFocusChangeListener = titleFocusChangeListener;
            this.mTitleView.setOnFocusChangeListener(titleFocusChangeListener);
            this.mHandWritingModel.enableContourAntLine(true);
            setCopySelectData();
            initSelectPaint();
            initSDKCallback();
            this.mShieldPageInkView.setListener(new CreationShieldPageInkView.PageAIListener() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda5
                @Override // com.miui.handwriting.common.view.handview.CreationShieldPageInkView.PageAIListener
                public final void doPageAI() {
                    this.f$0.m734xb9854851();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$innerInitView$0$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m734xb9854851() {
        if (Utils.isFastClick()) {
            return;
        }
        if (RomUtils.isUseNewCreationAI()) {
            Log.i(TAG, "innerInitView: new creation ai function has no full page ai !");
            return;
        }
        AiCreationHelper aiCreationHelper = this.mAiCreationHelper;
        if (aiCreationHelper != null) {
            aiCreationHelper.m705xf59ec7ec(false, hashCode());
        }
    }

    private void setCopySelectData() {
        if (isActivityValid()) {
            this.mHandWritingModel.setPageCopyData(ClipManager.INSTANCE.getPageCopyData());
        }
    }

    private void initSelectPaint() {
        if (isActivityValid()) {
            Paint paint = new Paint();
            this.selectPaint = paint;
            paint.setColor(getContext().getResources().getColor(R.color.select_rect));
            this.selectPaint.setStyle(Paint.Style.STROKE);
            this.selectPaint.setStrokeWidth(getContext().getResources().getDimension(R.dimen.creation_paint_select_rect_resize_stroke_size));
            this.selectPaint.setPathEffect(new DashPathEffect(new float[]{20.0f, 15.0f}, 0.0f));
            this.selectPaint.setStrokeCap(Paint.Cap.ROUND);
            Paint paint2 = new Paint();
            this.touchPointPaint = paint2;
            paint2.setStyle(Paint.Style.FILL);
            this.touchPointPaint.setStrokeWidth(getContext().getResources().getDimension(R.dimen.creation_paint_select_rect_point_size));
            this.touchPointPaint.setAntiAlias(true);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.outHeight = (int) getContext().getResources().getDimension(R.dimen.creation_paint_select_rect_point_size);
            options.outWidth = (int) getContext().getResources().getDimension(R.dimen.creation_paint_select_rect_point_size);
            this.pointBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.creation_select_point, options);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setTitleAndTime(String str, long j) {
        CreationPaintLayout creationPaintLayout;
        if (isActivityValid() && (creationPaintLayout = this.mCreationPaintLayout) != null) {
            creationPaintLayout.setTitleAndTime(str, j);
        }
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void onSelectSaveRectF(RectF rectF, List<TouchPosition> list, int i, List<DataCurve> list2, List<Point> list3) {
        if (isActivityValid()) {
            this.mHandWritingModel.selectMoveEnable(true);
            if (!this.mToolBoxView.isAISelect() || isFullPageAI()) {
                return;
            }
            if (RomUtils.isUseNewCreationAI()) {
                AiCreationHelper aiCreationHelper = this.mAiCreationHelper;
                if (aiCreationHelper != null) {
                    aiCreationHelper.startAIFunctionForOS2(rectF, list, i, list2, list3);
                    return;
                }
                return;
            }
            AiCreationHelper aiCreationHelper2 = this.mAiCreationHelper;
            if (aiCreationHelper2 != null) {
                aiCreationHelper2.startAIFunctionForOS1(rectF, list, i, hashCode());
            }
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void dismissAIPop() {
        AiCreationHelper aiCreationHelper;
        if (isActivityValid() && (aiCreationHelper = this.mAiCreationHelper) != null) {
            aiCreationHelper.dismissAIPop();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void setToolBoxPreviewListener(PreviewListener previewListener) {
        if (isActivityValid()) {
            this.mHandWritingModel.setPreviewListener(previewListener);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void onConfigurationChanged(Configuration configuration) {
        if (isActivityValid()) {
            AutoDensityConfig.forceUpdateDensity(getContext());
            setKeyboardDetector();
            SelectPopupWindow selectPopupWindow = this.selectActionModePopWindow;
            if (selectPopupWindow != null) {
                selectPopupWindow.dismiss();
                this.selectActionModePopWindow = null;
            }
            SelectPopupWindowInner selectPopupWindowInner = this.selectActionModePopWindowInner;
            if (selectPopupWindowInner != null) {
                selectPopupWindowInner.dismiss();
                this.selectActionModePopWindowInner = null;
            }
            ShapeEditPopupWindow shapeEditPopupWindow = this.mShapeEditPopupWindow;
            if (shapeEditPopupWindow != null) {
                shapeEditPopupWindow.dismissImmediately();
                this.mShapeEditPopupWindow = null;
            }
            ShapeColorPickerPopupWindow shapeColorPickerPopupWindow = this.mShapeColorPickerPopupWindow;
            if (shapeColorPickerPopupWindow != null) {
                if (shapeColorPickerPopupWindow.getFloatingPickerIsShowing()) {
                    this.mShapeColorPickerPopupWindow.dismissFloatingPickerView();
                    this.mShapeColorPickerPopupWindow.resetView();
                }
                if (this.mShapeColorPickerPopupWindow.isShowing()) {
                    this.mShapeColorPickerPopupWindow.setFromClick(false);
                    this.mShapeColorPickerPopupWindow.dismiss();
                }
            }
            AlertDialog alertDialog = this.mNetworkCtaDialog;
            if (alertDialog != null && alertDialog.isShowing()) {
                this.mNetworkCtaDialog.dismiss();
            }
            PastePopupWindow pastePopupWindow = this.mPastePopWindow;
            if (pastePopupWindow != null) {
                pastePopupWindow.dismiss();
                this.mPastePopWindow = null;
            }
            ProgressDialog progressDialog = this.pasteDialog;
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (ScreenModeUtils.isFull(getContext())) {
                this.mHandWritingModel.setPadding((int) getContext().getResources().getDimension(R.dimen.creation_paint_view_margin), 0, (int) getContext().getResources().getDimension(R.dimen.creation_paint_view_margin), 0);
            } else {
                this.mHandWritingModel.setPadding(0, 0, 0, 0);
            }
            dismissAIPop();
            if (!this.mHandWritingModel.isTextEditing() && !this.mTitleView.hasFocus()) {
                this.mToolBoxView.setFocus(true);
            }
            if (!this.mTitleView.hasFocus() && (UIUtils.isNightMode() != this.isNightMode || UIUtils.getScreenOrientation(Utils.getActivity(getContext())) != this.screenOrientation)) {
                Log.i(TAG, "MiuiHandWritingImpl onConfigurationChanged: current mode: " + UIUtils.isNightMode() + " last mode:" + this.isNightMode + " current orientation:" + UIUtils.getScreenOrientation(Utils.getActivity(getContext())) + " last orientation:" + this.screenOrientation);
                this.isNightMode = UIUtils.isNightMode();
                this.screenOrientation = UIUtils.getScreenOrientation(Utils.getActivity(getContext()));
                this.mHandWritingModel.finishSelect();
                Utils.hideSoftInput(this.mCreationPaintLayout);
            }
            if (TextUtils.equals(configuration.getLocales().toString(), this.local)) {
                return;
            }
            this.local = configuration.getLocales().toString();
            this.mHandWritingModel.finishSelect();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void initUIProperties() {
        if (isActivityValid()) {
            this.isNightMode = UIUtils.isNightMode();
            this.screenOrientation = UIUtils.getScreenOrientation(Utils.getActivity(getContext()));
            this.local = UIUtils.getLocale(getContext());
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView, com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void copyAsText(PartiBean partiBean) {
        if (isActivityValid()) {
            Log.d(TAG, "getRecognizeResult: " + partiBean);
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
            ClipManager.copyContentToClipboard(strReplaceAll, getContext());
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void startAiFormClassification(boolean z, String str) {
        AiCreationHelper aiCreationHelper;
        if (isActivityValid() && (aiCreationHelper = this.mAiCreationHelper) != null) {
            aiCreationHelper.startAiFormClassification(z, str);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void addPage(boolean z) {
        if (isActivityValid()) {
            this.mHandWritingModel.addPage(z);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setToolBarHeight(int i) {
        MiCurveEngine miCurveEngine = this.mHandWritingModel.getMiCurveEngine();
        if (miCurveEngine != null) {
            miCurveEngine.setToolBarHeight(i);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void updateBackgroundMargin(float f) {
        if (isActivityValid()) {
            this.mCreationPaintLayout.updateBackgroundMargin(f);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setTitleChangedListener(HeaderView.OnTitleChangedListener onTitleChangedListener) {
        this.mOnTitleChangedListener = onTitleChangedListener;
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void release() {
        CreationPaintLayout creationPaintLayout;
        TitleTextWatcher titleTextWatcher;
        EditText editText = this.mTitleView;
        if (editText != null && (titleTextWatcher = this.mTitleTextWatcher) != null) {
            editText.removeTextChangedListener(titleTextWatcher);
        }
        EditText editText2 = this.mTitleView;
        if (editText2 != null && this.mTitleFocusChangeListener != null) {
            editText2.setOnFocusChangeListener(null);
        }
        Runnable runnable = this.pasteDialogAlphaRunnable;
        if (runnable != null) {
            CreationPaintLayout creationPaintLayout2 = this.mCreationPaintLayout;
            if (creationPaintLayout2 != null) {
                creationPaintLayout2.removeCallbacks(runnable);
            }
            this.pasteDialogAlphaRunnable = null;
        }
        Runnable runnable2 = this.pasteDialogDismissRunnable;
        if (runnable2 != null) {
            CreationPaintLayout creationPaintLayout3 = this.mCreationPaintLayout;
            if (creationPaintLayout3 != null) {
                creationPaintLayout3.removeCallbacks(runnable2);
            }
            this.pasteDialogDismissRunnable = null;
        }
        AllowBackByAIRunnable allowBackByAIRunnable = this.mAllowBackByAIRunnable;
        if (allowBackByAIRunnable != null && (creationPaintLayout = this.mCreationPaintLayout) != null) {
            creationPaintLayout.removeCallbacks(allowBackByAIRunnable);
        }
        this.mAllowBackByAIRunnable = null;
        ProgressDialog progressDialog = this.pasteDialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.pasteDialog.dismiss();
            this.pasteDialog = null;
        }
        AiCreationHelper aiCreationHelper = this.mAiCreationHelper;
        if (aiCreationHelper != null) {
            aiCreationHelper.release();
            this.mAiCreationHelper = null;
        }
        IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
        if (iMiuiHandWritingModel != null) {
            iMiuiHandWritingModel.destroy();
            this.mHandWritingModel.setSelectListener(null);
        }
        IMiuiHandToolbar iMiuiHandToolbar = this.mToolBoxView;
        if (iMiuiHandToolbar != null) {
            iMiuiHandToolbar.release();
        }
        CreationPageSDK.sErrorCallback = null;
        if (this.mCreationPaintLayout != null) {
            CreationShieldPageInkView creationShieldPageInkView = this.mShieldPageInkView;
            if (creationShieldPageInkView != null) {
                creationShieldPageInkView.removeAllViews();
                this.mShieldPageInkView.setToolViewHeightCallback(null);
                this.mShieldPageInkView.setListener(null);
            }
            if (this.mCreationPaintLayout.getParent() != null) {
                ((ViewGroup) this.mCreationPaintLayout.getParent()).removeView(this.mCreationPaintLayout);
            }
            this.mCreationPaintLayout.removeAllViews();
        }
        CropPopupWindow cropPopupWindow = this.mCropPopupWindows;
        if (cropPopupWindow != null) {
            if (cropPopupWindow.isShowing()) {
                this.mCropPopupWindows.dismiss();
            }
            this.mCropPopupWindows = null;
        }
        SelectPopupWindow selectPopupWindow = this.selectActionModePopWindow;
        if (selectPopupWindow != null) {
            selectPopupWindow.dismiss();
            this.selectActionModePopWindow = null;
        }
        SelectPopupWindowInner selectPopupWindowInner = this.selectActionModePopWindowInner;
        if (selectPopupWindowInner != null) {
            selectPopupWindowInner.dismiss();
            this.selectActionModePopWindowInner = null;
        }
        ShapeEditPopupWindow shapeEditPopupWindow = this.mShapeEditPopupWindow;
        if (shapeEditPopupWindow != null) {
            shapeEditPopupWindow.dismissImmediately();
            this.mShapeEditPopupWindow = null;
        }
        ShapeColorPickerPopupWindow shapeColorPickerPopupWindow = this.mShapeColorPickerPopupWindow;
        if (shapeColorPickerPopupWindow != null) {
            shapeColorPickerPopupWindow.dismiss();
            this.mShapeColorPickerPopupWindow = null;
        }
        PastePopupWindow pastePopupWindow = this.mPastePopWindow;
        if (pastePopupWindow != null) {
            pastePopupWindow.dismiss();
            this.mPastePopWindow = null;
        }
        AlertDialog alertDialog = this.mNetworkCtaDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mNetworkCtaDialog = null;
        }
        KeyboardHelper keyboardHelper = this.mEditKeyboardDetector;
        if (keyboardHelper != null) {
            keyboardHelper.release();
            this.mEditKeyboardDetector = null;
        }
        this.mTitleTextWatcher = null;
        this.mTitleFocusChangeListener = null;
        this.mShieldPageInkView = null;
        this.mCreationPaintLayout = null;
        this.mTitleView = null;
        WeakReference<Context> weakReference = this.mContextRef;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.mOnTitleChangedListener = null;
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void enableAllPageAI(boolean z) {
        if (isActivityValid()) {
            this.mShieldPageInkView.setShieldEvent(z);
        }
    }

    public boolean isFullPageAI() {
        return this.mShieldPageInkView.isShieldEvent();
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void dismissAILoadingDialog() {
        CreationShieldPageInkView creationShieldPageInkView;
        if (isActivityValid() && (creationShieldPageInkView = this.mShieldPageInkView) != null) {
            creationShieldPageInkView.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    if (MiuiHandWritingImpl.this.mAiCreationHelper != null) {
                        MiuiHandWritingImpl.this.mAiCreationHelper.dismissAIDialog();
                    }
                }
            }, 50L);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void replaceSelected(String str) {
        AiCreationHelper aiCreationHelper;
        if (isActivityValid() && (aiCreationHelper = this.mAiCreationHelper) != null) {
            aiCreationHelper.setAiInsertImagePath(str);
            this.mHandWritingModel.deleteNewSelectSaveData(this.mAiCreationHelper.getAiSelectData());
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void replaceSelected(String str, CreationImage creationImage, CanvasElementTransactionMask canvasElementTransactionMask) {
        AiCreationHelper aiCreationHelper;
        if (isActivityValid() && (aiCreationHelper = this.mAiCreationHelper) != null) {
            aiCreationHelper.setCreationImage(creationImage);
            this.mAiCreationHelper.setAiInsertImagePath(str);
            this.mAiCreationHelper.setTransactionMask(canvasElementTransactionMask);
            this.mHandWritingModel.deleteNewSelectSaveData(this.mAiCreationHelper.getAiSelectData());
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void insertAiImage(String str, CreationImage creationImage, CanvasElementTransactionMask canvasElementTransactionMask) {
        AiCreationHelper aiCreationHelper;
        if (isActivityValid() && (aiCreationHelper = this.mAiCreationHelper) != null) {
            aiCreationHelper.setCreationImage(creationImage);
            this.mAiCreationHelper.setAiInsertImagePath(str);
            this.mAiCreationHelper.setTransactionMask(canvasElementTransactionMask);
            this.mAiCreationHelper.insertImageFromText();
            this.mShieldPageInkView.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m735xa764d561();
                }
            }, 300L);
        }
    }

    /* JADX INFO: renamed from: lambda$insertAiImage$1$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m735xa764d561() {
        AiCreationHelper aiCreationHelper = this.mAiCreationHelper;
        if (aiCreationHelper != null) {
            aiCreationHelper.dismissTransactionMask();
        }
        this.mIsPreventBackByAI = false;
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void onDeleteData() {
        if (isActivityValid()) {
            if (RomUtils.isUseNewCreationAI()) {
                this.mAiCreationHelper.insertImageFormDraw(true);
                this.mShieldPageInkView.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m736x8b605796();
                    }
                }, 300L);
            } else {
                this.mShieldPageInkView.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m737x7eefdbd7();
                    }
                }, 300L);
            }
        }
    }

    /* JADX INFO: renamed from: lambda$onDeleteData$2$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m736x8b605796() {
        AiCreationHelper aiCreationHelper = this.mAiCreationHelper;
        if (aiCreationHelper != null) {
            aiCreationHelper.dismissTransactionMask();
        }
        this.mIsPreventBackByAI = false;
    }

    /* JADX INFO: renamed from: lambda$onDeleteData$3$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m737x7eefdbd7() {
        AiCreationHelper aiCreationHelper = this.mAiCreationHelper;
        if (aiCreationHelper != null) {
            aiCreationHelper.insertImageFormDraw(false);
        }
        this.mIsPreventBackByAI = false;
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void onSelectPopWindow(RectF rectF, boolean z, final boolean z2, final boolean z3) {
        if (!isActivityValid() || Utils.isFastClick() || rectF.isEmpty() || this.mHandWritingModel.isAiModel()) {
            return;
        }
        final RectF rectF2 = new RectF(rectF);
        if (z) {
            if (this.mHandWritingModel.getSelectShapeProp() == null) {
                return;
            }
            ShapeEditSet.switchCurShape(this.mHandWritingModel.getSelectShapeProp().getShapeType());
            ShapeEditSet.getCurShape().update(this.mHandWritingModel.getSelectShapeProp());
            ShapeEditPopupWindow shapeEditPopupWindow = new ShapeEditPopupWindow(getContext(), new AnonymousClass2(rectF2, rectF), this.mHandWritingModel);
            this.mShapeEditPopupWindow = shapeEditPopupWindow;
            shapeEditPopupWindow.setWeakReference(this.mCreationPaintLayout);
            showShapeEditPopWindow(rectF, false);
            return;
        }
        if (this.selectActionModePopWindow == null) {
            this.selectActionModePopWindow = new SelectPopupWindow(getContext(), this.mActionClickListener, R.layout.creation_select_pop_menu);
        }
        if (this.selectActionModePopWindowInner == null) {
            this.selectActionModePopWindowInner = new SelectPopupWindowInner(getContext(), this.mActionClickListener, R.layout.creation_select_pop_menu_inner);
        }
        this.mCreationPaintLayout.removeCallbacks(this.dismissSelectionActionModePopWindowRunnable);
        this.mCreationPaintLayout.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m742x446f1fb0(rectF2, z2, z3);
            }
        }, 50L);
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$2, reason: invalid class name */
    class AnonymousClass2 implements ShapeEditPopupWindow.OnActionClickListener {
        final /* synthetic */ RectF val$mRectF;
        final /* synthetic */ RectF val$rectF;

        AnonymousClass2(RectF rectF, RectF rectF2) {
            this.val$mRectF = rectF;
            this.val$rectF = rectF2;
        }

        @Override // com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.OnActionClickListener
        public void onActionClick(int i) {
            if (MiuiHandWritingImpl.this.mHandWritingModel.getSelectShapeProp() == null) {
                return;
            }
            if (i == 256) {
                if (MiuiHandWritingImpl.this.mShapeEditPopupWindow != null && MiuiHandWritingImpl.this.mShapeEditPopupWindow.isShowing()) {
                    MiuiHandWritingImpl.this.mShapeEditPopupWindow.dismiss();
                }
                MiuiHandWritingImpl.this.mHandWritingModel.onDelete(false);
                return;
            }
            if (i == 8192) {
                MiuiHandWritingImpl.this.showLoadingDialog();
                if (MiuiHandWritingImpl.this.mShapeEditPopupWindow != null && MiuiHandWritingImpl.this.mShapeEditPopupWindow.isShowing()) {
                    MiuiHandWritingImpl.this.mShapeEditPopupWindow.dismiss();
                }
                MiuiHandWritingImpl.this.mHandWritingModel.onCopyPaste(MiuiHandWritingImpl.this.mHandWritingModel.isUnlimited());
                ClipManager.INSTANCE.setMTimeStamp(System.currentTimeMillis());
                return;
            }
            if (i != 458752) {
                if (i == 327680) {
                    MiuiHandWritingImpl.this.mHandWritingModel.setSelectShapeAttr(ShapeEditSet.getCurShape().convertAttr(), true);
                    return;
                } else {
                    if (i != 327681) {
                        return;
                    }
                    MiuiHandWritingImpl.this.mHandWritingModel.setSelectShapeAttr(ShapeEditSet.getCurShape().convertAttr(), false);
                    return;
                }
            }
            if (MiuiHandWritingImpl.this.mShapeEditPopupWindow != null && MiuiHandWritingImpl.this.mShapeEditPopupWindow.isShowing()) {
                MiuiHandWritingImpl.this.mShapeEditPopupWindow.dismissImmediately();
            }
            MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow = new ShapeColorPickerPopupWindow(MiuiHandWritingImpl.this.getContext(), MiuiHandWritingImpl.this.mHandWritingModel);
            if (ShapeEditSet.getCurShape().getEditMode() == 0) {
                MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow.setColor(ShapeEditSet.getCurShape().getFillColor());
            } else {
                MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow.setColor(ShapeEditSet.getCurShape().getStrokeColor());
            }
            MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow.setElevation(20.0f);
            final int[] iArr = new int[2];
            MiuiHandWritingImpl.this.mCreationPaintLayout.getLocationInWindow(iArr);
            View contentView = MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow.getContentView();
            contentView.measure(0, 0);
            final Point point = new Point(0, 0);
            int dimensionPixelSize = MiuiHandWritingImpl.this.getContext().getResources().getDimensionPixelSize(R.dimen.creation_paint_select_rect_point_size);
            if ((MiuiHandWritingImpl.this.mCreationPaintLayout.getWidth() - this.val$mRectF.right) - iArr[0] <= contentView.getMeasuredWidth() && this.val$mRectF.left + iArr[0] > contentView.getMeasuredWidth()) {
                point.set((int) (((iArr[0] + this.val$mRectF.left) - contentView.getMeasuredWidth()) - dimensionPixelSize), (int) (((iArr[1] + this.val$mRectF.top) + ((this.val$mRectF.bottom - this.val$mRectF.top) / 2.0f)) - (contentView.getMeasuredHeight() - DisplayUtils.dip2px(MiuiHandWritingImpl.this.getContext(), 50.0f))));
            } else {
                point.set((int) (iArr[0] + this.val$mRectF.right + dimensionPixelSize), (int) (((iArr[1] + this.val$mRectF.top) + ((this.val$mRectF.bottom - this.val$mRectF.top) / 2.0f)) - (contentView.getMeasuredHeight() - DisplayUtils.dip2px(MiuiHandWritingImpl.this.getContext(), 50.0f))));
            }
            contentView.getMeasuredWidth();
            MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow.addOnColorChangeListener(new ColorPickerPopupWindow.OnColorPickerColorChangeListener() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.2.1
                @Override // com.miui.handwriting.common.view.popup.ColorPickerPopupWindow.OnColorPickerColorChangeListener
                public void onColorChange(ColorPickerPopupWindow.ColorPickerChangeEvent colorPickerChangeEvent) {
                    int color = colorPickerChangeEvent.getColor();
                    ShapeEditAttr shapeEditAttrConvertAttr = ShapeEditSet.getCurShape().convertAttr();
                    if (ShapeEditSet.getCurShape().getEditMode() == 0) {
                        shapeEditAttrConvertAttr.setFillColor(color);
                        shapeEditAttrConvertAttr.setFillAlpha(Color.alpha(color));
                    } else {
                        shapeEditAttrConvertAttr.setStrokeColor(color);
                        shapeEditAttrConvertAttr.setStrokeAlpha(Color.alpha(color));
                    }
                    MiuiHandWritingImpl.this.mHandWritingModel.setSelectShapeAttr(shapeEditAttrConvertAttr, colorPickerChangeEvent.getNeedUpdate());
                }
            });
            MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow.addActionCallBack(new ShapeColorPickerPopupWindow.ActionCallBack() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.2.2
                @Override // com.miui.handwriting.common.view.popup.ShapeColorPickerPopupWindow.ActionCallBack
                public void onAction(int i2) {
                    ShapeProp selectShapeProp;
                    if (i2 != 393216 || (selectShapeProp = MiuiHandWritingImpl.this.mHandWritingModel.getSelectShapeProp()) == null) {
                        return;
                    }
                    if (ShapeEditSet.getCurShape().getEditMode() == 0) {
                        ShapeEditSet.getCurShape().setFillColor(selectShapeProp.getFillColor());
                        ShapeEditSet.getCurShape().setFillAlpha(selectShapeProp.getFillAlpha());
                        if (ShapeEditSet.getCurShape().getFillAlpha() == 0.0f) {
                            ShapeEditSet.getCurShape().setCheckedFillColorIndex(ShapeEditSet.getCurShape().getDefaultFillColorArray().length - 1);
                        } else {
                            ShapeEditSet.getCurShape().setDefaultFillColorArray(ShapeEditSet.getCurShape().getCheckedFillColorIndex(), ColorUtils.calculateARGBColor(selectShapeProp.getFillAlpha(), selectShapeProp.getFillColor()));
                        }
                    } else {
                        ShapeEditSet.getCurShape().setStrokeColor(selectShapeProp.getPenColor());
                        ShapeEditSet.getCurShape().setStrokeAlpha(selectShapeProp.getPenAlpha());
                        if (ShapeEditSet.getCurShape().getStrokeAlpha() == 0) {
                            ShapeEditSet.getCurShape().setCheckedStrokeColorIndex(ShapeEditSet.getCurShape().getDefaultFillColorArray().length - 1);
                        } else {
                            ShapeEditSet.getCurShape().setDefaultStrokeColorArray(ShapeEditSet.getCurShape().getCheckedStrokeColorIndex(), ColorUtils.calculateARGBColor(selectShapeProp.getPenAlpha(), selectShapeProp.getPenColor()));
                        }
                    }
                    MiuiHandWritingImpl.this.showShapeEditPopWindow(AnonymousClass2.this.val$rectF, true);
                }
            });
            MiuiHandWritingImpl.this.mCreationPaintLayout.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m745xd9c953e7(point, iArr);
                }
            }, 50L);
            MiuiHandWritingImpl.this.mCreationPaintLayout.postDelayed(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m746x2788cbe8();
                }
            }, 300L);
        }

        /* JADX INFO: renamed from: lambda$onActionClick$0$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl$2, reason: not valid java name */
        /* synthetic */ void m745xd9c953e7(Point point, int[] iArr) {
            MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow.showAtLocation(MiuiHandWritingImpl.this.mCreationPaintLayout, 0, point.x, Math.max(iArr[1], point.y));
        }

        /* JADX INFO: renamed from: lambda$onActionClick$1$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl$2, reason: not valid java name */
        /* synthetic */ void m746x2788cbe8() {
            if (MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow.isShowing() && MiuiHandWritingImpl.this.mHandWritingModel.getSelectShapeProp() == null) {
                MiuiHandWritingImpl.this.mShapeColorPickerPopupWindow.dismissImmediately();
            }
        }
    }

    /* JADX INFO: renamed from: lambda$onSelectPopWindow$4$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m742x446f1fb0(RectF rectF, boolean z, boolean z2) {
        SelectPopupWindow selectPopupWindow = this.selectActionModePopWindow;
        if (selectPopupWindow != null && selectPopupWindow.isShowing()) {
            this.selectActionModePopWindow.dismiss();
        }
        SelectPopupWindowInner selectPopupWindowInner = this.selectActionModePopWindowInner;
        if (selectPopupWindowInner != null && selectPopupWindowInner.isShowing()) {
            this.selectActionModePopWindowInner.dismiss();
        }
        showPopWindow(rectF, z, z2);
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void dismissSelectPopWindow() {
        if (isActivityValid()) {
            this.mCreationPaintLayout.removeCallbacks(this.dismissSelectionActionModePopWindowRunnable);
            this.mCreationPaintLayout.post(this.dismissSelectionActionModePopWindowRunnable);
        }
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void onPastePopWindow(final float f, final float f2, final boolean z) {
        if (isActivityValid()) {
            if (ClipManager.isContainContent(getContext()) || z) {
                if (this.mCreationPaintLayout.getLatexByPoint(f, f2) != null || this.mHandWritingModel.isAiModel()) {
                    Log.d(TAG, "onPastePopWindow prohibit");
                    return;
                }
                if (this.mPastePopWindow == null) {
                    PastePopupWindow pastePopupWindow = new PastePopupWindow(getContext());
                    this.mPastePopWindow = pastePopupWindow;
                    pastePopupWindow.setOnPasteCallback(new PastePopupWindow.OnPasteClickListener() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda2
                        @Override // com.miui.handwriting.common.view.popup.PastePopupWindow.OnPasteClickListener
                        public final void onPasteClick(float f3, float f4, boolean z2) throws InterruptedException {
                            this.f$0.m740xc3549f75(f3, f4, z2);
                        }
                    });
                }
                this.mCreationPaintLayout.post(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m741xb6e423b6(f, f2, z);
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: lambda$onPastePopWindow$8$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m740xc3549f75(final float f, final float f2, boolean z) throws InterruptedException {
        int currentPosition = this.mHandWritingModel.getCurrentPosition();
        PageItemView currentItemView = this.mHandWritingModel.getCurrentItemView();
        if (ClipManager.isPasteFromLocal(getContext(), z)) {
            showLoadingDialog();
            if (currentPosition == -1 || currentItemView == null || currentItemView.isLoading() || !currentItemView.isSelectReady()) {
                return;
            }
            if (this.mHandWritingModel.getCurvePasteCount() + this.mHandWritingModel.getPageTotalSavePoint() > 20000000) {
                this.mHandWritingModel.cancelSelect(false);
                if (CreationPageSDK.sErrorCallback != null) {
                    CreationPageSDK.sErrorCallback.onError(1);
                    return;
                }
                return;
            }
            this.mHandWritingModel.onPaste(f, f2);
            return;
        }
        if (ClipManager.isNeedNetwork() && !PreferenceUtils.isNetworkPermissionAccepted()) {
            MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m739xcfc51b34(f, f2);
                }
            });
            return;
        }
        String copyText = ClipManager.getCopyText(getContext());
        if (TextUtils.isEmpty(copyText)) {
            doPasteExternalImage(f, f2);
        } else {
            doPasteText(copyText, f, f2);
        }
    }

    /* JADX INFO: renamed from: lambda$onPastePopWindow$7$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m739xcfc51b34(final float f, final float f2) {
        this.mNetworkCtaDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.creation_request_network_title);
        builder.setMessage(R.string.creation_request_network_desc_for_copy_image);
        builder.setPositiveButton(R.string.creation_cta_accept, new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws InterruptedException {
                this.f$0.m738xe8a612b2(f, f2, dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.creation_cta_not_accept, new DialogInterface.OnClickListener() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MiuiHandWritingImpl.lambda$onPastePopWindow$6(dialogInterface, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        this.mNetworkCtaDialog = alertDialogCreate;
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$onPastePopWindow$5$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m738xe8a612b2(float f, float f2, DialogInterface dialogInterface, int i) throws InterruptedException {
        PreferenceUtils.setNetworkPermissionAccepted(true);
        doPasteExternalImage(f, f2);
    }

    /* JADX INFO: renamed from: lambda$onPastePopWindow$9$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m741xb6e423b6(float f, float f2, boolean z) {
        int[] iArr = new int[2];
        this.mCreationPaintLayout.getLocationInWindow(iArr);
        PastePopupWindow pastePopupWindow = this.mPastePopWindow;
        if (pastePopupWindow == null || pastePopupWindow.isShowing()) {
            return;
        }
        this.mPastePopWindow.show(this.mCreationPaintLayout, f, Math.max(f2, iArr[1]), z);
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void drawFollowAntLine(Canvas canvas, Path path) {
        if (isActivityValid()) {
            this.lastFirstPoint = null;
            this.lastCenterPoint = null;
            this.selectPaint.setColor(getContext().getResources().getColor(R.color.select_rect));
            this.selectPaint.setStrokeWidth(getContext().getResources().getDimension(R.dimen.creation_paint_select_rect_ant_stroke_size));
            this.selectPaint.setPathEffect(new DashPathEffect(new float[]{20.0f, 15.0f}, 0.0f));
            this.selectPaint.setStrokeCap(Paint.Cap.ROUND);
            IMiuiHandWritingModel iMiuiHandWritingModel = this.mHandWritingModel;
            if (iMiuiHandWritingModel != null) {
                iMiuiHandWritingModel.checkSelectViewPosition();
            }
            canvas.drawPath(path, this.selectPaint);
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x014e  */
    /* JADX WARN: Code duplicated, block: B:38:0x01ca A[LOOP:1: B:37:0x01c8->B:38:0x01ca, LOOP_END] */
    /* JADX WARN: Failed to calculate best type for var: r42v0 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r42v0 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:59)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:147)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:125)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$1(TypeInferenceVisitor.java:103)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:103)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 7 more
     */
    /* JADX WARN: Failed to set immutable type for var: r42v0 ??
    jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r42v0 ??, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:73)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setImmutableType(TypeInferenceVisitor.java:111)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$0(TypeInferenceVisitor.java:102)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:102)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 6 more
     */
    /* JADX WARN: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: Type update failed for variable: r42v0 int, new type: int
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:109)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.applyWithWiderIgnSame(TypeUpdate.java:73)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.applyResolvedVars(TypeSearch.java:100)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:76)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.runMultiVariableSearch(FixTypesVisitor.java:119)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
    Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "arg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.verifyType(TypeUpdate.java:210)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.queueTypeUpdate(TypeUpdate.java:171)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.sameFirstArgListener(TypeUpdate.java:454)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:310)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runUpdate(TypeUpdate.java:124)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:91)
    	... 5 more
     */
    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void drawRectangularAntLine(Canvas canvas, Path path, float[] fArr, boolean z, int i) {
        double dAtan2;
        RectF[] rectFArr;
        ArrayList arrayList;
        int i2;
        float[] fArr2;
        if (isActivityValid()) {
            if (!z) {
                this.lastFirstPoint = null;
                this.lastCenterPoint = null;
                if (i == 2) {
                    this.mHandWritingModel.setEdit(true);
                    return;
                }
            }
            this.mHandWritingModel.checkSelectViewPosition(fArr);
            this.selectPaint.setColor(getContext().getResources().getColor(R.color.select_rect));
            if (z) {
                this.selectPaint.setStrokeWidth(getContext().getResources().getDimension(R.dimen.creation_paint_select_rect_resize_stroke_size));
                this.selectPaint.setPathEffect(null);
                canvas.drawPath(path, this.selectPaint);
                Matrix matrix = new Matrix();
                Bitmap bitmap = this.pointBitmap;
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.pointBitmap.getHeight(), matrix, true);
                char c = 3;
                float[] fArr3 = {fArr[0], fArr[1], fArr[2], fArr[3], fArr[4], fArr[5], fArr[6], fArr[7]};
                bitmapCreateBitmap.getWidth();
                bitmapCreateBitmap.getHeight();
                bitmapCreateBitmap.getHeight();
                bitmapCreateBitmap.getWidth();
                this.pointsArr.clear();
                for (int i3 = 0; i3 < 4; i3++) {
                    int i4 = i3 * 2;
                    this.pointsArr.add(new float[]{fArr3[i4], fArr3[i4 + 1]});
                }
                float[] centerOfGravityPoint = MathUtils.getCenterOfGravityPoint(this.pointsArr);
                float[] fArr4 = this.lastFirstPoint;
                if (fArr4 != null && (fArr2 = this.lastCenterPoint) != null) {
                    if (i == 0 || i == 3 || i == 4) {
                        c = 3;
                        matrix = matrix;
                        dAtan2 = 360.0d - MathUtils.getAngle(fArr2[0], fArr2[1], fArr4[0], fArr4[1], centerOfGravityPoint[0], centerOfGravityPoint[1], fArr3[0], fArr3[1]);
                    }
                    if (this.lastCenterPoint == null) {
                        this.lastCenterPoint = centerOfGravityPoint;
                        this.lastFirstPoint = new float[]{fArr[0], fArr[1]};
                    }
                    float width = this.pointBitmap.getWidth() * 2.5f;
                    float width2 = this.pointBitmap.getWidth() * 0.5f;
                    rectFArr = new RectF[4];
                    float f = fArr[0];
                    float f2 = fArr[1];
                    rectFArr[0] = new RectF(f - width, f2 - width, f + width2, f2 + width2);
                    float f3 = fArr[2];
                    float f4 = fArr[c];
                    rectFArr[1] = new RectF(f3 - width2, f4 - width, f3 + width, f4 + width2);
                    float f5 = fArr[4];
                    float f6 = fArr[5];
                    rectFArr[2] = new RectF(f5 - width2, f6 - width2, f5 + width, f6 + width);
                    float f7 = fArr[6];
                    float f8 = fArr[7];
                    rectFArr[c] = new RectF(f7 - width, f8 - width2, f7 + width2, f8 + width);
                    arrayList = new ArrayList();
                    for (i2 = 0; i2 < 4; i2++) {
                        int i5 = i2 * 2;
                        Matrix matrix2 = matrix;
                        matrix2.setRotate((float) dAtan2, fArr[i5], fArr[i5 + 1]);
                        matrix2.mapRect(rectFArr[i2]);
                        arrayList.add(rectFArr[i2]);
                        matrix2.reset();
                    }
                    return;
                }
                dAtan2 = (i == r7 || i == 2) ? (Math.atan2(fArr[c] - fArr[1], fArr[2] - fArr[0]) * 180.0d) / 3.141592653589793d : 0.0d;
                if (this.lastCenterPoint == null) {
                    this.lastCenterPoint = centerOfGravityPoint;
                    this.lastFirstPoint = new float[]{fArr[0], fArr[1]};
                }
                float width3 = this.pointBitmap.getWidth() * 2.5f;
                float width4 = this.pointBitmap.getWidth() * 0.5f;
                rectFArr = new RectF[4];
                float f9 = fArr[0];
                float f10 = fArr[1];
                rectFArr[0] = new RectF(f9 - width3, f10 - width3, f9 + width4, f10 + width4);
                float f11 = fArr[2];
                float f12 = fArr[c];
                rectFArr[1] = new RectF(f11 - width4, f12 - width3, f11 + width3, f12 + width4);
                float f13 = fArr[4];
                float f14 = fArr[5];
                rectFArr[2] = new RectF(f13 - width4, f14 - width4, f13 + width3, f14 + width3);
                float f15 = fArr[6];
                float f16 = fArr[7];
                rectFArr[c] = new RectF(f15 - width3, f16 - width4, f15 + width4, f16 + width3);
                arrayList = new ArrayList();
                while (i2 < 4) {
                    int i6 = i2 * 2;
                    Matrix matrix3 = matrix;
                    matrix3.setRotate((float) dAtan2, fArr[i6], fArr[i6 + 1]);
                    matrix3.mapRect(rectFArr[i2]);
                    arrayList.add(rectFArr[i2]);
                    matrix3.reset();
                }
                return;
            }
            this.selectPaint.setStrokeWidth(getContext().getResources().getDimension(R.dimen.creation_paint_select_rect_ant_stroke_size));
            this.selectPaint.setPathEffect(new DashPathEffect(new float[]{20.0f, 15.0f}, 0.0f));
            this.selectPaint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawPath(path, this.selectPaint);
        }
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void drawShapeAntLine(Canvas canvas, Path path, float[] fArr, boolean z, boolean z2) {
        float[] fArrCalculateLinearInterpolation;
        float strokeWidth;
        if (isActivityValid()) {
            this.touchPointPaint.setColor(getContext().getResources().getColor(R.color.select_rect));
            if (!z2) {
                ArrayList arrayList = new ArrayList();
                float f = 10;
                arrayList.add(new RectF((fArr[0] - this.touchPointPaint.getStrokeWidth()) - f, (fArr[1] - this.touchPointPaint.getStrokeWidth()) - f, fArr[0] + this.touchPointPaint.getStrokeWidth() + f, fArr[1] + this.touchPointPaint.getStrokeWidth() + f));
                arrayList.add(new RectF((fArr[4] - this.touchPointPaint.getStrokeWidth()) - f, (fArr[5] - this.touchPointPaint.getStrokeWidth()) - f, fArr[4] + this.touchPointPaint.getStrokeWidth() + f, fArr[5] + this.touchPointPaint.getStrokeWidth() + f));
                this.mHandWritingModel.setShapeMenuSize(arrayList, new RectF());
                return;
            }
            RectF rectF = new RectF();
            if (z) {
                if (this.mHandWritingModel.getSelectShapeProp() != null && (this.mHandWritingModel.getSelectShapeProp().getShapeType() == 3 || this.mHandWritingModel.getSelectShapeProp().getShapeType() == 4)) {
                    if (this.mHandWritingModel.getSelectShapeProp().getShapeType() == 3) {
                        strokeWidth = (float) (Math.sqrt(2.0d) * ((double) this.touchPointPaint.getStrokeWidth()));
                    } else {
                        strokeWidth = this.mHandWritingModel.getSelectShapeProp().getShapeType() == 4 ? this.touchPointPaint.getStrokeWidth() * 3.0f : 0.0f;
                    }
                    fArrCalculateLinearInterpolation = MathUtils.calculateLinearInterpolation(fArr[10], fArr[11], fArr[12], fArr[13], fArr[8], fArr[9], strokeWidth);
                } else {
                    fArrCalculateLinearInterpolation = new float[]{fArr[8], fArr[9]};
                }
                float f2 = 10;
                rectF = new RectF((fArrCalculateLinearInterpolation[0] - this.touchPointPaint.getStrokeWidth()) - f2, (fArrCalculateLinearInterpolation[1] - this.touchPointPaint.getStrokeWidth()) - f2, fArrCalculateLinearInterpolation[0] + this.touchPointPaint.getStrokeWidth() + f2, fArrCalculateLinearInterpolation[1] + this.touchPointPaint.getStrokeWidth() + f2);
            }
            ArrayList arrayList2 = new ArrayList();
            float f3 = 10;
            arrayList2.add(new RectF((((fArr[0] + fArr[2]) / 2.0f) - this.touchPointPaint.getStrokeWidth()) - f3, (((fArr[1] + fArr[3]) / 2.0f) - this.touchPointPaint.getStrokeWidth()) - f3, ((fArr[0] + fArr[2]) / 2.0f) + this.touchPointPaint.getStrokeWidth() + f3, ((fArr[1] + fArr[3]) / 2.0f) + this.touchPointPaint.getStrokeWidth() + f3));
            arrayList2.add(new RectF((((fArr[2] + fArr[4]) / 2.0f) - this.touchPointPaint.getStrokeWidth()) - f3, (((fArr[3] + fArr[5]) / 2.0f) - this.touchPointPaint.getStrokeWidth()) - f3, ((fArr[2] + fArr[4]) / 2.0f) + this.touchPointPaint.getStrokeWidth() + f3, ((fArr[3] + fArr[5]) / 2.0f) + this.touchPointPaint.getStrokeWidth() + f3));
            arrayList2.add(new RectF((((fArr[4] + fArr[6]) / 2.0f) - this.touchPointPaint.getStrokeWidth()) - f3, (((fArr[5] + fArr[7]) / 2.0f) - this.touchPointPaint.getStrokeWidth()) - f3, ((fArr[4] + fArr[6]) / 2.0f) + this.touchPointPaint.getStrokeWidth() + f3, ((fArr[5] + fArr[7]) / 2.0f) + this.touchPointPaint.getStrokeWidth() + f3));
            arrayList2.add(new RectF((((fArr[6] + fArr[0]) / 2.0f) - this.touchPointPaint.getStrokeWidth()) - f3, (((fArr[7] + fArr[1]) / 2.0f) - this.touchPointPaint.getStrokeWidth()) - f3, ((fArr[6] + fArr[0]) / 2.0f) + this.touchPointPaint.getStrokeWidth() + f3, ((fArr[7] + fArr[1]) / 2.0f) + this.touchPointPaint.getStrokeWidth() + f3));
            float f4 = 20;
            arrayList2.add(new RectF((fArr[0] - this.touchPointPaint.getStrokeWidth()) - f4, (fArr[1] - this.touchPointPaint.getStrokeWidth()) - f4, fArr[0] + this.touchPointPaint.getStrokeWidth() + f4, fArr[1] + this.touchPointPaint.getStrokeWidth() + f4));
            arrayList2.add(new RectF((fArr[2] - this.touchPointPaint.getStrokeWidth()) - f4, (fArr[3] - this.touchPointPaint.getStrokeWidth()) - f4, fArr[2] + this.touchPointPaint.getStrokeWidth() + f4, fArr[3] + this.touchPointPaint.getStrokeWidth() + f4));
            arrayList2.add(new RectF((fArr[4] - this.touchPointPaint.getStrokeWidth()) - f4, (fArr[5] - this.touchPointPaint.getStrokeWidth()) - f4, fArr[4] + this.touchPointPaint.getStrokeWidth() + f4, fArr[5] + this.touchPointPaint.getStrokeWidth() + f4));
            arrayList2.add(new RectF((fArr[6] - this.touchPointPaint.getStrokeWidth()) - f4, (fArr[7] - this.touchPointPaint.getStrokeWidth()) - f4, fArr[6] + this.touchPointPaint.getStrokeWidth() + f4, fArr[7] + this.touchPointPaint.getStrokeWidth() + f4));
            this.mHandWritingModel.setShapeMenuSize(arrayList2, rectF);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showShapeEditPopWindow(RectF rectF, boolean z) {
        int measuredHeight;
        if (isActivityValid()) {
            int[] iArr = new int[2];
            this.mCreationPaintLayout.getLocationInWindow(iArr);
            ShapeEditColorPopupWindow shapeEditColorPopupWindow = new ShapeEditColorPopupWindow(getContext(), new ShapeEditPopupWindow.OnActionClickListener() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.4
                @Override // com.miui.handwriting.common.view.popup.ShapeEditPopupWindow.OnActionClickListener
                public void onActionClick(int i) {
                }
            }, true);
            ShapeEditPopupWindow shapeEditPopupWindow = this.mShapeEditPopupWindow;
            if (shapeEditPopupWindow == null) {
                Log.e(TAG, "ShapeEditPopupWindow is null");
                return;
            }
            View contentView = shapeEditPopupWindow.getContentView();
            contentView.measure(0, 0);
            View contentView2 = shapeEditColorPopupWindow.getContentView();
            contentView2.measure(0, 0);
            int measuredHeight2 = (int) (rectF.bottom + contentView.getMeasuredHeight() + contentView2.getMeasuredHeight());
            int measuredHeight3 = contentView.getMeasuredHeight() + contentView2.getMeasuredHeight();
            int iWidth = (int) (iArr[0] + rectF.left + ((rectF.width() - contentView.getMeasuredWidth()) / 2.0f));
            if (measuredHeight2 < this.mCreationPaintLayout.getHeight()) {
                measuredHeight = (int) (rectF.bottom + iArr[1]);
                this.mShapeEditPopupWindow.setChildShowUpside(false);
            } else if (measuredHeight3 <= rectF.top + iArr[1]) {
                measuredHeight = (int) ((rectF.top + iArr[1]) - contentView.getMeasuredHeight());
                this.mShapeEditPopupWindow.setChildShowUpside(true);
            } else {
                measuredHeight = (int) ((rectF.top + iArr[1]) - contentView.getMeasuredHeight());
                this.mShapeEditPopupWindow.setChildShowUpside(false);
            }
            this.mShapeEditPopupWindow.setAnchorPoint(iWidth, measuredHeight);
            this.mShapeEditPopupWindow.setNeedShowChild(z);
            this.mShapeEditPopupWindow.initView();
            this.mShapeEditPopupWindow.update();
            int toolbarHeight = this.mToolBoxView.getToolbarHeight();
            MiCurveEngine miCurveEngine = this.mHandWritingModel.getMiCurveEngine();
            if (miCurveEngine != null) {
                miCurveEngine.setToolBarHeight(toolbarHeight);
            }
            this.mShapeEditPopupWindow.showAtLocation(this.mCreationPaintLayout, 0, iWidth, Math.max(measuredHeight, toolbarHeight));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPopWindow(RectF rectF, boolean z, boolean z2) {
        float fDip2px;
        if (isActivityValid() && this.selectActionModePopWindow != null) {
            this.mRectF = rectF;
            this.mIsSingleBitmap = z;
            this.mIsSingleText = z2;
            SelectPopupWindowInner selectPopupWindowInner = this.selectActionModePopWindowInner;
            if (selectPopupWindowInner != null && selectPopupWindowInner.isShowing()) {
                this.selectActionModePopWindowInner.dismiss();
            }
            List<CurveBean> selectedDataList = this.mHandWritingModel.getSelectedDataList();
            if (selectedDataList != null && !selectedDataList.isEmpty()) {
                this.selectActionModePopWindow.showSelectCopyOrRecognizeAsText(true);
                this.selectActionModePopWindowInner.showSelectCopyOrRecognizeAsText(true);
            } else {
                this.selectActionModePopWindow.showSelectCopyOrRecognizeAsText(false);
                this.selectActionModePopWindowInner.showSelectCopyOrRecognizeAsText(false);
            }
            if (z) {
                this.selectActionModePopWindow.showSelectResize(false);
                this.selectActionModePopWindowInner.showSelectResize(false);
                this.selectActionModePopWindow.showSelectCrop(false);
            } else {
                this.selectActionModePopWindow.showSelectResize(true);
                this.selectActionModePopWindowInner.showSelectResize(true);
                this.selectActionModePopWindow.showSelectCrop(false);
            }
            this.selectActionModePopWindow.hideViewInNarrowScreen();
            this.selectActionModePopWindow.showSave(this.mHandWritingModel.isFromAi());
            int[] iArr = new int[2];
            this.mCreationPaintLayout.getLocationInWindow(iArr);
            View contentView = this.selectActionModePopWindow.getContentView();
            contentView.measure(0, 0);
            int iWidth = (int) (iArr[0] + rectF.left + ((rectF.width() - contentView.getMeasuredWidth()) / 2.0f));
            if (z) {
                fDip2px = rectF.top + iArr[1] + ((rectF.height() - contentView.getMeasuredHeight()) / 2.0f);
            } else {
                fDip2px = (rectF.top + iArr[1]) - DisplayUtils.dip2px(getContext(), 83.6f);
            }
            int iMax = (int) fDip2px;
            int i = iArr[1];
            if (iMax < i) {
                if (z2) {
                    iMax = (UIUtils.isSmallWindowMode(getContext()) ? UIUtils.getStatusBarHeight(getContext()) : 0) + ((int) rectF.bottom) + contentView.getMeasuredHeight();
                } else {
                    iMax = i;
                }
            }
            RectF pageLocation = this.mHandWritingModel.getCurrentItemView().getPageLocation();
            if (pageLocation != null) {
                iMax = (int) Math.max(iMax, pageLocation.top + contentView.getMeasuredHeight());
            }
            this.mPopWindowOuterLeft = iWidth;
            this.mPopWindowOuterTop = iMax;
            this.selectActionModePopWindow.showAtLocation(this.mCreationPaintLayout, 0, iWidth, iMax);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCropActionPopWindow() {
        if (isActivityValid()) {
            if (this.mCropPopupWindows == null) {
                this.mCropPopupWindows = new CropPopupWindow(getContext());
            }
            this.mCropPopupWindows.setOnCropCallback(new CropPopupWindow.OnCropClickListener() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.5
                @Override // com.miui.handwriting.common.view.popup.CropPopupWindow.OnCropClickListener
                public void onFinishClick() {
                    MiuiHandWritingImpl.this.mHandWritingModel.onCropFinish();
                    MiuiHandWritingImpl.this.mCropPopupWindows.dismiss();
                }

                @Override // com.miui.handwriting.common.view.popup.CropPopupWindow.OnCropClickListener
                public void onCancelClick() {
                    MiuiHandWritingImpl.this.mHandWritingModel.onCropCancel();
                    MiuiHandWritingImpl.this.mCropPopupWindows.dismiss();
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$6, reason: invalid class name */
    class AnonymousClass6 implements SelectPopupWindow.OnActionClickListener {
        private long lastClickTime = System.currentTimeMillis();

        AnonymousClass6() {
        }

        @Override // com.miui.handwriting.common.view.popup.SelectPopupWindow.OnActionClickListener
        public void onActionClick(int i) throws Throwable {
            if (MiuiHandWritingImpl.this.isActivityValid() && System.currentTimeMillis() - this.lastClickTime >= 300) {
                this.lastClickTime = System.currentTimeMillis();
                switch (i) {
                    case 256:
                        MiuiHandWritingImpl.this.mHandWritingModel.onDelete(false);
                        break;
                    case 512:
                        MiuiHandWritingImpl.this.showCropActionPopWindow();
                        MiuiHandWritingImpl.this.mHandWritingModel.onCrop();
                        break;
                    case 4096:
                        MiuiHandWritingImpl.this.mHandWritingModel.onCopy(MiuiHandWritingImpl.this.mHandWritingModel.isUnlimited());
                        if (ClipManager.isSupportSuperClipboard()) {
                            ClipManager.copyContentToSuperClipboard(MiuiHandWritingImpl.this.mHandWritingModel.getSelectScreenshot(false), MiuiHandWritingImpl.this.getContext());
                        }
                        break;
                    case 8192:
                        MiuiHandWritingImpl.this.showLoadingDialog();
                        MiuiHandWritingImpl.this.mHandWritingModel.onCopyPaste(MiuiHandWritingImpl.this.mHandWritingModel.isUnlimited());
                        ClipManager.INSTANCE.setMTimeStamp(System.currentTimeMillis());
                        break;
                    case 16384:
                        if (ClipManager.isSupportSuperClipboard()) {
                            ClipManager.copyContentToSuperClipboard(MiuiHandWritingImpl.this.mHandWritingModel.getSelectScreenshot(false), MiuiHandWritingImpl.this.getContext());
                        }
                        MiuiHandWritingImpl.this.mHandWritingModel.onCut(MiuiHandWritingImpl.this.mHandWritingModel.isUnlimited());
                        break;
                    case 32768:
                        MiuiHandWritingImpl.this.mHandWritingModel.setEdit(true);
                        break;
                    case 65536:
                        MiuiHandWritingImpl.this.distinguish(false);
                        break;
                    case 131072:
                        MiuiHandWritingImpl.this.distinguish(true);
                        break;
                    case 196608:
                        View contentView = MiuiHandWritingImpl.this.selectActionModePopWindow.getContentView();
                        View contentView2 = MiuiHandWritingImpl.this.selectActionModePopWindowInner.getContentView();
                        contentView2.measure(0, 0);
                        int[] iArr = new int[2];
                        MiuiHandWritingImpl.this.selectActionModePopWindow.getSelectMore().getLocationOnScreen(iArr);
                        final int iMax = DisplayUtils.isRTL() ? iArr[0] : (Math.max(MiuiHandWritingImpl.this.mPopWindowOuterLeft, 0) + contentView.getMeasuredWidth()) - contentView2.getMeasuredWidth();
                        MiuiHandWritingImpl.this.selectActionModePopWindow.dismiss();
                        MiuiHandWritingImpl.this.mCreationPaintLayout.post(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$6$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m747xd9c953eb(iMax);
                            }
                        });
                        return;
                    case 262144:
                        MiuiHandWritingImpl.this.selectActionModePopWindowInner.dismiss();
                        MiuiHandWritingImpl.this.mCreationPaintLayout.post(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$6$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.m748x2788cbec();
                            }
                        });
                        return;
                    case 524288:
                        MiuiHandWritingImpl.this.selectActionModePopWindow.dismiss();
                        MiuiHandWritingImpl.this.mCreationPaintLayout.post(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.6.1
                            @Override // java.lang.Runnable
                            public void run() {
                                String aiBitmapPath = MiuiHandWritingImpl.this.mHandWritingModel.getAiBitmapPath();
                                if (!TextUtils.isEmpty(aiBitmapPath)) {
                                    AiBitmapHelper.INSTANCE.saveImageToGallery(MiuiHandWritingImpl.this.getContext(), aiBitmapPath, "image_" + System.currentTimeMillis() + com.miui.common.tool.Utils.JPG_SUFFIX);
                                    Toast.makeText(MiuiHandWritingImpl.this.getContext(), MiuiHandWritingImpl.this.getContext().getResources().getString(R.string.save_bitmap_done_toast), 0).show();
                                    return;
                                }
                                Toast.makeText(MiuiHandWritingImpl.this.getContext(), MiuiHandWritingImpl.this.getContext().getResources().getString(R.string.save_bitmap_fail_toast), 0).show();
                            }
                        });
                        return;
                }
                MiuiHandWritingImpl.this.selectActionModePopWindow.dismiss();
                MiuiHandWritingImpl.this.selectActionModePopWindowInner.dismiss();
            }
        }

        /* JADX INFO: renamed from: lambda$onActionClick$0$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl$6, reason: not valid java name */
        /* synthetic */ void m747xd9c953eb(int i) {
            MiuiHandWritingImpl.this.selectActionModePopWindowInner.showAtLocation(MiuiHandWritingImpl.this.mCreationPaintLayout, 0, i, MiuiHandWritingImpl.this.mPopWindowOuterTop);
        }

        /* JADX INFO: renamed from: lambda$onActionClick$1$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl$6, reason: not valid java name */
        /* synthetic */ void m748x2788cbec() {
            MiuiHandWritingImpl miuiHandWritingImpl = MiuiHandWritingImpl.this;
            miuiHandWritingImpl.showPopWindow(miuiHandWritingImpl.mRectF, MiuiHandWritingImpl.this.mIsSingleBitmap, MiuiHandWritingImpl.this.mIsSingleText);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void distinguish(boolean z) {
        if (isActivityValid()) {
            List<CurveBean> selectedDataList = this.mHandWritingModel.getSelectedDataList();
            this.mHandWritingModel.copyTempSelectData();
            if (selectedDataList == null || selectedDataList.isEmpty()) {
                return;
            }
            String json = new Gson().toJson(selectedDataList);
            String str = getContext().getExternalFilesDir("") + File.separator + "select.json";
            FileUtil.saveJsonToFile(str, json);
            Intent intent = new Intent();
            intent.setAction(Constants.OPERATION_INTENT);
            intent.putExtra("operationType", "distinguish");
            intent.putExtra("filePath", str);
            intent.putExtra("textColor", selectedDataList.get(0).curveColor);
            intent.putExtra("isRecognizeAsText", z);
            LocalBroadcastManager.getInstance(HandWritingViewApplication.INSTANCE.getApplication()).sendBroadcast(intent);
        }
    }

    private void doPasteExternalImage(final float f, final float f2) throws InterruptedException {
        if (isActivityValid()) {
            showLoadingDialog();
            final String copyPath = ClipManager.getCopyPath(getContext());
            MainThreadUtils.runOnUiThread(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m733x5457415(copyPath, f, f2);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$doPasteExternalImage$10$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m733x5457415(String str, float f, float f2) {
        this.mHandWritingModel.insertImagePosition(str, f, f2);
        dismissLoadingDialog();
    }

    private void doPasteText(String str, float f, float f2) {
        if (isActivityValid()) {
            showLoadingDialog();
            this.mHandWritingModel.insertTextPosition(str, f, f2);
            dismissLoadingDialog();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void showLoadingDialog() {
        if (isActivityValid()) {
            ProgressDialog progressDialog = this.pasteDialog;
            if (progressDialog == null || !progressDialog.isShowing()) {
                this.mCreationPaintLayout.post(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m744xe557b011();
                    }
                });
                this.mCreationPaintLayout.postDelayed(this.pasteDialogAlphaRunnable, 500L);
                this.mCreationPaintLayout.postDelayed(this.pasteDialogDismissRunnable, 5000L);
            }
        }
    }

    /* JADX INFO: renamed from: lambda$showLoadingDialog$11$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m744xe557b011() {
        WindowManager.LayoutParams attributes;
        Activity activity = Utils.getActivity(getContext());
        if (activity == null || activity.isFinishing()) {
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(activity);
        this.pasteDialog = progressDialog;
        progressDialog.setIndeterminate(true);
        this.pasteDialog.setMessage(activity.getString(miuix.springback.R.string.miuix_sbl_tracking_progress_labe_up_refresh));
        this.pasteDialog.setCanceledOnTouchOutside(false);
        this.pasteDialog.setCancelable(false);
        Window window = this.pasteDialog.getWindow();
        if (window == null || (attributes = window.getAttributes()) == null) {
            return;
        }
        attributes.alpha = 0.0f;
        window.setAttributes(attributes);
        window.getDecorView().setSystemUiVisibility(window.getDecorView().getSystemUiVisibility() | R2.color.theme_default_high_light_color);
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void dismissLoadingDialog() {
        if (isActivityValid()) {
            Runnable runnable = this.pasteDialogAlphaRunnable;
            if (runnable != null) {
                this.mCreationPaintLayout.removeCallbacks(runnable);
            }
            Runnable runnable2 = this.pasteDialogDismissRunnable;
            if (runnable2 != null) {
                this.mCreationPaintLayout.removeCallbacks(runnable2);
            }
            this.mCreationPaintLayout.post(this.pasteDialogDismissRunnable);
        }
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void onExitTextEdit() {
        if (isActivityValid() && !this.mToolBoxView.isFocus()) {
            this.mToolBoxView.setFocus(true);
        }
    }

    private void setKeyboardDetector() {
        if (isActivityValid()) {
            KeyboardHelper keyboardHelper = this.mEditKeyboardDetector;
            if (keyboardHelper != null) {
                keyboardHelper.release();
            }
            CreationPaintLayout creationPaintLayout = this.mCreationPaintLayout;
            KeyboardHelper keyboardHelper2 = new KeyboardHelper(creationPaintLayout, creationPaintLayout.getRootView(), new KeyboardMarginProcessor() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl.9
                @Override // com.miui.handwriting.common.softinputhelper.KeyboardMarginProcessor, com.miui.handwriting.common.softinputhelper.KeyboardHeightListener
                public void onKeyboardHeightChanged(View view, int i, int i2) {
                    Activity activity = Utils.getActivity(MiuiHandWritingImpl.this.getContext());
                    if (activity != null && activity.isInMultiWindowMode()) {
                        if (UIUtils.isSmallWindowMode(activity)) {
                            boolean zIsKeyboardVisibleInMultiWindowMode = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(activity);
                            if (MiuiHandWritingImpl.this.mIsKeyboardVisible != zIsKeyboardVisibleInMultiWindowMode) {
                                MiuiHandWritingImpl.this.mIsKeyboardVisible = zIsKeyboardVisibleInMultiWindowMode;
                            }
                            Log.d(MiuiHandWritingImpl.TAG, "小窗: height is " + i + "is keyboard is show :" + MiuiHandWritingImpl.this.mIsKeyboardVisible + " toolBox height is: " + MiuiHandWritingImpl.this.mToolBoxView.getToolBoxHeight());
                            int toolbarHeight = MiuiHandWritingImpl.this.mToolBoxView.getToolbarHeight();
                            MiCurveEngine miCurveEngine = MiuiHandWritingImpl.this.mHandWritingModel.getMiCurveEngine();
                            if (miCurveEngine != null) {
                                miCurveEngine.setToolBarHeight(toolbarHeight);
                            }
                            MiuiHandWritingImpl.this.mHandWritingModel.scrollOnKeyBoardHeightChanged(activity, toolbarHeight, MiuiHandWritingImpl.this.mIsKeyboardVisible);
                            return;
                        }
                        boolean zIsKeyboardVisibleInMultiWindowMode2 = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(activity);
                        if (MiuiHandWritingImpl.this.mIsKeyboardVisible != zIsKeyboardVisibleInMultiWindowMode2) {
                            MiuiHandWritingImpl.this.mIsKeyboardVisible = zIsKeyboardVisibleInMultiWindowMode2;
                        }
                        Log.d(MiuiHandWritingImpl.TAG, "分屏 " + i + " mIsKeyboardVisible " + MiuiHandWritingImpl.this.mIsKeyboardVisible);
                        if (!MiuiHandWritingImpl.this.mIsKeyboardVisible) {
                            MiuiHandWritingImpl.this.mHandWritingModel.scrollOnKeyBoardHeightChanged(activity, i, MiuiHandWritingImpl.this.mIsKeyboardVisible);
                        } else if (i != 0) {
                            MiuiHandWritingImpl.this.mHandWritingModel.scrollOnKeyBoardHeightChanged(activity, i, true);
                        }
                    }
                }

                @Override // com.miui.handwriting.common.softinputhelper.KeyboardMarginProcessor, com.miui.handwriting.common.softinputhelper.KeyboardHeightListener
                public void onKeyboardHeightChangedProgress(View view, int i, int i2) {
                    Activity activity = Utils.getActivity(MiuiHandWritingImpl.this.getContext());
                    if (activity == null || activity.isInMultiWindowMode()) {
                        return;
                    }
                    boolean zIsKeyboardVisibleInMultiWindowMode = KeyboardHelper.isKeyboardVisibleInMultiWindowMode(activity);
                    if (MiuiHandWritingImpl.this.mIsKeyboardVisible != zIsKeyboardVisibleInMultiWindowMode) {
                        MiuiHandWritingImpl.this.mIsKeyboardVisible = zIsKeyboardVisibleInMultiWindowMode;
                    }
                    MiuiHandWritingImpl.this.mHandWritingModel.scrollOnKeyBoardHeightChanged(activity, i, MiuiHandWritingImpl.this.mIsKeyboardVisible);
                }
            });
            this.mEditKeyboardDetector = keyboardHelper2;
            keyboardHelper2.setEnable(true);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void showRuler(boolean z) {
        if (isActivityValid()) {
            this.mHandWritingModel.getEngineConfig().enableRuler = z;
            GlobalRulerView globalRulerView = this.mHandWritingModel.getGlobalRulerView();
            CreationShieldPageInkView pageInkView = this.mCreationPaintLayout.getPageInkView();
            for (int i = 0; i < pageInkView.getChildCount(); i++) {
                if (pageInkView.getChildAt(i) instanceof GlobalRulerView) {
                    if (!z) {
                        pageInkView.removeView(globalRulerView);
                    } else {
                        pageInkView.bringChildToFront(globalRulerView);
                        globalRulerView.enableRuler(true);
                        return;
                    }
                }
            }
            if (z) {
                pageInkView.addView(globalRulerView);
            }
            globalRulerView.enableRuler(z);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public RectF getSelectAiRect() {
        if (!isActivityValid()) {
            return new RectF();
        }
        AiCreationHelper aiCreationHelper = this.mAiCreationHelper;
        if (aiCreationHelper != null) {
            return aiCreationHelper.getSelectAiRect();
        }
        return new RectF();
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setSelectCheck(ICheck iCheck) {
        this.mCheck = iCheck;
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void clearTitleCursor() {
        if (isActivityValid()) {
            this.mCreationPaintLayout.getTitleView().clearFocus();
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setTitleTime(long j) {
        if (isActivityValid()) {
            this.mCreationPaintLayout.setTitleTime(j);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setTitle(String str, int i) {
        if (isActivityValid()) {
            this.mCreationPaintLayout.getTitleView().setText(str);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public String getTitle(int i) {
        return !isActivityValid() ? "" : this.mCreationPaintLayout.getTitleView().getText().toString();
    }

    @Override // com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setCanvasElementTransactListener(CanvasElementTransactCallback canvasElementTransactCallback) {
        AiCreationHelper aiCreationHelper;
        if (isActivityValid() && (aiCreationHelper = this.mAiCreationHelper) != null) {
            aiCreationHelper.setCanvasElementTransactListener(canvasElementTransactCallback);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void preventBackByAI() {
        AllowBackByAIRunnable allowBackByAIRunnable;
        if (isActivityValid()) {
            this.mIsPreventBackByAI = true;
            CreationPaintLayout creationPaintLayout = this.mCreationPaintLayout;
            if (creationPaintLayout == null || (allowBackByAIRunnable = this.mAllowBackByAIRunnable) == null) {
                return;
            }
            creationPaintLayout.postDelayed(allowBackByAIRunnable, 1500L);
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void allowBackByAI() {
        if (isActivityValid()) {
            this.mIsPreventBackByAI = false;
        }
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public boolean isPreventBackByAI() {
        return this.mIsPreventBackByAI;
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public AiCreationHelper getAiCreationHelper() {
        return this.mAiCreationHelper;
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public void bindPageId(long j) {
        this.pageId = j;
    }

    @Override // com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView
    public ThumbnailRecyclerManager getThumbnailManager() {
        if (isActivityValid()) {
            return this.mCreationPaintLayout.getThumbnailManager();
        }
        return null;
    }

    private void updateBgFollowLast(List<PagePreviewData> list) {
        int i;
        if (isActivityValid()) {
            if (this.mPreviewDataList == null) {
                this.mPreviewDataList = new ArrayList();
            }
            this.mPreviewDataList.clear();
            this.mPreviewDataList.addAll(list);
            int size = list.size();
            if (size > 1 && size != (i = this.lastPageSize) && i != 0 && size > i && !isFullPageAI()) {
                Log.d(TAG, "PreviewData update bg:" + size);
                int i2 = size - 1;
                PagePreviewData pagePreviewData = list.get(i2);
                PagePreviewData pagePreviewData2 = list.get(size - 2);
                HashMap map = new HashMap();
                if (!TextUtils.isEmpty(pagePreviewData2.bgImage) && !pagePreviewData2.bgImage.endsWith(InternalZipConstants.ZIP_FILE_SEPARATOR)) {
                    pagePreviewData.bgColor = pagePreviewData2.bgColor == 0 ? ContextCompat.getColor(getContext(), R.color.paper_white) : pagePreviewData2.bgColor;
                    pagePreviewData.bgGrid = pagePreviewData2.bgGrid;
                    pagePreviewData.bgConfig = pagePreviewData2.bgConfig;
                } else {
                    PreviewDataSet.copyBg(pagePreviewData2, pagePreviewData);
                    map.put(Integer.valueOf(i2), pagePreviewData);
                }
                map.put(Integer.valueOf(i2), pagePreviewData);
                this.mHandWritingModel.setPageBackground(map);
            }
            this.lastPageSize = list.size();
        }
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void showCropPopWindow(final RectF rectF) {
        CropPopupWindow cropPopupWindow;
        if (!isActivityValid() || (cropPopupWindow = this.mCropPopupWindows) == null || cropPopupWindow.isShowing()) {
            return;
        }
        this.mCropPopupWindows.getContentView().measure(View.MeasureSpec.makeMeasureSpec(getContext().getResources().getDisplayMetrics().widthPixels, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(getContext().getResources().getDisplayMetrics().heightPixels, Integer.MIN_VALUE));
        final int measuredWidth = this.mCropPopupWindows.getContentView().getMeasuredWidth();
        final int measuredHeight = this.mCropPopupWindows.getContentView().getMeasuredHeight();
        this.mCreationPaintLayout.post(new Runnable() { // from class: com.miui.handwriting.common.view.handview.MiuiHandWritingImpl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m743xa74472a5(rectF, measuredWidth, measuredHeight);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showCropPopWindow$12$com-miui-handwriting-common-view-handview-MiuiHandWritingImpl, reason: not valid java name */
    /* synthetic */ void m743xa74472a5(RectF rectF, int i, int i2) {
        this.mCropPopupWindows.show(this.mCreationPaintLayout, ((int) rectF.centerX()) - (i / 2), ((int) rectF.centerY()) + (i2 / 3));
    }

    @Override // com.miui.libhandwritingpage.listener.PageTextSelectListener
    public void dismissCropPopWindow() {
        CropPopupWindow cropPopupWindow;
        if (isActivityValid() && (cropPopupWindow = this.mCropPopupWindows) != null && cropPopupWindow.isShowing()) {
            this.mCropPopupWindows.dismiss();
        }
    }
}
