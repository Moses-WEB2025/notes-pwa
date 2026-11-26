package com.miui.libhandwritingpage.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.SystemClock;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;
import com.miui.libhandwritingpage.R;
import com.miui.libhandwritingpage.adapter.PageAdapter;
import com.miui.libhandwritingpage.adapter.PageLayoutManager;
import com.miui.libhandwritingpage.listener.PDFExportListener;
import com.miui.libhandwritingpage.listener.PDFImportListener;
import com.miui.libhandwritingpage.listener.PDFLoadingListener;
import com.miui.libhandwritingpage.listener.PageChangeListener;
import com.miui.libhandwritingpage.listener.PageLoadListener;
import com.miui.libhandwritingpage.listener.PageSaveListener;
import com.miui.libhandwritingpage.listener.PageSizeListener;
import com.miui.libhandwritingpage.listener.PageStateView;
import com.miui.libhandwritingpage.listener.PageTextSelectListener;
import com.miui.libhandwritingpage.listener.PdfThumbnailLoadListener;
import com.miui.libhandwritingpage.listener.PreviewListener;
import com.miui.libhandwritingpage.listener.TitleColorChangeListener;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.CanvasMaskEntity;
import com.miui.libhandwritingpage.newbean.SearchWrapper;
import com.miui.libhandwritingpage.newbean.beanCurve.CurveBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageBean;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import com.miui.libhandwritingpage.newbean.beanSelect.SelectCopyBean;
import com.miui.libhandwritingpage.newbean.beanTouch.TouchPosition;
import com.miui.libhandwritingpage.task.ImportPDFTask;
import com.miui.libhandwritingpage.utils.BitmapUtil;
import com.miui.libhandwritingpage.utils.LruCacheUtil;
import com.miui.libhandwritingpage.utils.MathUtils;
import com.miui.libhandwritingpage.utils.PageEntHelper;
import com.miui.libhandwritingpage.utils.PageEntUtil;
import com.miui.libhandwritingpage.utils.PageStepHelper;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpage.view.GlobalRulerView;
import com.miui.libhandwritingpage.view.ImportPdfBitmap;
import com.miui.libhandwritingpage.view.PageDrawView;
import com.miui.libhandwritingpage.view.PageInkView;
import com.miui.libhandwritingpage.view.PageItemView;
import com.miui.libhandwritingpage.view.PageSurfaceView;
import com.miui.libhandwritingpage.view.listenerProxy.MathSelectCallbackProxy;
import com.miui.libhandwritingpage.view.select.SelectViewGroup;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import miuix.os.Build;
import org.apache.commons.lang3.StringUtils;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.ICreationEngine;
import xc.engine.api.PenEngine;
import xc.engine.api.listener.DrawErrorCallback;
import xc.engine.core.algorithm.AlgorithmNative;
import xc.engine.core.algorithm.bean.StrokeClassificationCurve;
import xc.engine.core.algorithm.estimate.ParamsEstimate;
import xc.engine.core.algorithm.recognize.ParamsRecognizeShape;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.PageInkModelInterface;
import xc.engine.core.graphics.engine.core.Color;
import xc.engine.core.graphics.engine.systems.BgSystem;
import xc.engine.core.graphics.engine.systems.PageSystem;
import xc.engine.data.CurveProp;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.ICurve;
import xc.engine.data.shape.ShapeProp;
import xc.engine.math.iink.callback.MathSelectCallback;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.edit.select.ISelectOperator;
import xc.engine.operate.edit.select.SRectF;
import xc.engine.operate.edit.select.ShapeEditAttr;
import xc.engine.operate.rule.IGlobalRulerOperator;
import xc.engine.operate.step.type.StepChangePageSize;
import xc.engine.storage.ent.PageEntInfo;
import xc.engine.storage.ent.file_io.InkEntFileIOHelper;
import xc.engine.storage.ent.tools.PageEntTools;
import xc.engine.utils.EngineConfig;
import xc.engine.utils.FeatureManager;
import xc.engine.utils.FileUtil;
import xc.engine.utils.PageSizeConfig;
import xc.richtext.api.ItemSpannedData;
import xc.richtext.api.SpannedSelectModel;
import xc.richtext.data.SpannedData;
import xc.richtext.data.TextInfo;
import xc.richtext.data.bean.SpannedScaleInfo;
import xc.richtext.utils.ScreenRenderUtil;
import xc.richtext.utils.TextLayoutUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PageInkModel extends IPageInkModel implements MathSelectCallback {
    private static final boolean IS_GLOBAL = Build.IS_INTERNATIONAL_BUILD;
    private static final String TAG = "PageInkModel";
    private final int THUMBNAIL_WIDTH;
    private int UNINTENDED_TOUCH_HEIGHT;
    public int bgColor;
    public int bgColorChange;
    private final Paint bitmapPaint;
    private boolean downOnRuler;
    public Runnable fenestraRunnable;
    private boolean fenestrule;
    public Runnable fullScreenRunnable;
    private boolean hasPointerDown;
    private boolean isAiModel;
    private boolean isCrop;
    private Boolean isHaveScroll;
    private boolean isNightMode;
    private boolean isPageSizeDialogShow;
    private boolean isSearchScrollBy;
    private boolean isShowSelect;
    private boolean isUnLimitToNormal;
    private float mCursorHeight;
    private final PageDrawView mDrawView;
    private boolean mExportLoaded;
    private ImportPdfBitmap mExportPdfBitmap;
    private ImportPdfBitmap mImportPdfBitmap;
    private ImportPdfBitmap mImportPdfThumbnail;
    private int mKeyBoardState;
    private final List<PageItemData> mList;
    private List<DataCurve> mMathCurves;
    private PageChangeListener mPageChangeListener;
    private PageSizeListener mPageSizeListener;
    private boolean mPdfThumbnailIsLoaded;
    private PdfThumbnailLoadListener mPdfThumbnailLoadListener;
    private SelectViewGroup mSelectViewGroup;
    private PageStateView mStateView;
    private TitleColorChangeListener mTitleColorChangeListener;
    private boolean pageChange;
    private final String pageDataTempDir;
    private PageInkModelInterface pageInkModelInterface;
    private boolean pcMode;
    private final List<PagePreviewData> previewBeans;
    private PreviewListener previewListener;
    private int searchTop;
    private SearchWrapper searchWrapper;
    private PageInkView.ShieldEventListener shieldEventListener;
    private int totalScrollDy;
    private final WindowInfo windowInfo;

    public interface IUnBindListener {
        void unBindSuc(PageItemView pageItemView, int i);
    }

    static /* synthetic */ Void lambda$waitForIdle$0() throws Exception {
        return null;
    }

    public Bitmap getBitmapBySize(boolean z, int i) {
        return null;
    }

    public boolean isUnLimitToNormal() {
        return this.isUnLimitToNormal;
    }

    public boolean isUnlimited() {
        return this.isUnlimited;
    }

    public void setPageSizeDialogShow(boolean z) {
        this.isPageSizeDialogShow = z;
    }

    public void setUnlimited(boolean z) {
        this.isUnlimited = z;
    }

    public void checkSelectViewPosition(float[] fArr) {
        if (this.layoutManager == null || fArr == null || !this.isUnlimited) {
            return;
        }
        this.layoutManager.checkSelectViewPosition(fArr);
    }

    public void checkSelectViewPosition() {
        if (this.layoutManager == null || !this.isUnlimited) {
            return;
        }
        this.layoutManager.checkSelectViewPosition(this.mSelectViewGroup.getSpanData());
    }

    public List<SpannedData> getAllSelectImageSpanned() {
        return this.mSelectViewGroup.getAllSelectImageSpanned();
    }

    public void onCopyPaste(boolean z) {
        this.mSelectViewGroup.onCopyPaste(z);
    }

    public void onDelete(boolean z) {
        this.mSelectViewGroup.onDelete(z);
    }

    public void onCopy(boolean z) {
        this.mSelectViewGroup.onCopy(z);
    }

    public void onCrop() {
        this.mSelectViewGroup.onCrop();
        this.isCrop = true;
    }

    public void onCropFinish() {
        this.mSelectViewGroup.onCropFinish();
        this.isCrop = false;
    }

    public void onCropCancel() {
        this.mSelectViewGroup.onCropCancel();
        this.isCrop = false;
    }

    public void onCut(boolean z) {
        this.mSelectViewGroup.onCut(z);
    }

    public void setEdit(boolean z) {
        this.mSelectViewGroup.setEdit(z);
    }

    public int getCount() {
        return this.mSelectViewGroup.getCount();
    }

    public void cancelSelect(boolean z) {
        this.mSelectViewGroup.cancelSelect(z);
    }

    public void onPaste(float f, float f2) {
        this.mSelectViewGroup.onPaste(f, f2);
    }

    public void setSelectMenuSize(List<RectF> list, List<RectF> list2) {
        this.mSelectViewGroup.setSelectMenuSize(list, list2);
    }

    public void setShapeMenuSize(List<RectF> list, RectF rectF) {
        this.mSelectViewGroup.setShapeMenuSize(list, rectF);
    }

    public void setModifyFlagToItemData(int i, boolean z) {
        if (this.mList.isEmpty() || i >= this.mList.size()) {
            return;
        }
        Log.d(TAG, String.format("setModifyFlagToItemData %d %b", Integer.valueOf(i), Boolean.valueOf(z)));
        this.mList.get(i).isModified = z;
    }

    public void onTitleColorChanged(int i, int i2) {
        if (this.mTitleColorChangeListener == null || !this.isUnlimited) {
            return;
        }
        this.bgColorChange = i;
        this.mTitleColorChangeListener.onTitleColorChange(i, i2);
    }

    public void setTitleColorChangeListener(TitleColorChangeListener titleColorChangeListener) {
        this.mTitleColorChangeListener = titleColorChangeListener;
    }

    public void waitForIdle() throws Exception {
        if (this.adapter != null) {
            FutureTask futureTask = new FutureTask(new Callable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda3
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return PageInkModel.lambda$waitForIdle$0();
                }
            });
            this.adapter.waitForIdle(futureTask);
            try {
                futureTask.get();
            } catch (Exception e) {
                throw new Exception("waitForIdle execution error", e);
            }
        }
    }

    public long getCurvePasteCount() {
        return this.mSelectViewGroup.getCount();
    }

    public void setCanWrite(boolean z) {
        this.mCanWrite = z;
    }

    public boolean isCanWrite() {
        return this.mCanWrite;
    }

    public void initInterface() {
        this.pageInkModelInterface = new PageInkModelInterface() { // from class: com.miui.libhandwritingpage.model.PageInkModel.1
            @Override // xc.engine.core.graphics.engine.PageInkModelInterface
            public void mathDataHaveChange(int i) {
                Log.d(PageInkModel.TAG, "mathDataHaveChange: pageIndex:" + i);
                if (PageInkModel.this.mList == null || PageInkModel.this.mList.isEmpty()) {
                    return;
                }
                ((PageItemData) PageInkModel.this.mList.get(i)).isModified = true;
            }
        };
    }

    public PageDrawView getDrawView() {
        return this.mDrawView;
    }

    public PageInkModel(PageInkView pageInkView, int i) {
        super(pageInkView);
        this.searchWrapper = new SearchWrapper();
        this.UNINTENDED_TOUCH_HEIGHT = 70;
        this.isUnLimitToNormal = true;
        this.mPdfThumbnailIsLoaded = false;
        this.mExportLoaded = false;
        this.THUMBNAIL_WIDTH = 200;
        this.isCrop = false;
        this.isNightMode = false;
        this.bgColor = -1;
        this.bgColorChange = -1;
        this.fullScreenRunnable = new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel.3
            @Override // java.lang.Runnable
            public void run() {
                PageInkModel.this.mIsScrollByTouch = false;
                if (PageInkModel.this.mKeyBoardState == 1) {
                    PageInkModel.this.mKeyBoardState = 2;
                    PageInkModel.this.layoutManager.setKeyboardLive(true);
                    PageInkModel pageInkModel = PageInkModel.this;
                    pageInkModel.scrollEditTextBy(-pageInkModel.totalScrollDy);
                    return;
                }
                if (PageInkModel.this.mKeyBoardState == 3) {
                    PageInkModel.this.mKeyBoardState = 1;
                    PageInkModel pageInkModel2 = PageInkModel.this;
                    pageInkModel2.resetScrolledEditTextBy(pageInkModel2.totalScrollDy);
                    PageInkModel.this.layoutManager.setKeyboardLive(false);
                    PageInkModel.this.totalScrollDy = 0;
                }
            }
        };
        this.fenestraRunnable = new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel.4
            @Override // java.lang.Runnable
            public void run() {
                PageInkModel.this.mIsScrollByTouch = false;
                if (PageInkModel.this.mKeyBoardState == 1) {
                    if (PageInkModel.this.totalScrollDy < 0) {
                        PageInkModel.this.totalScrollDy = 0;
                        return;
                    }
                    PageInkModel.this.mKeyBoardState = 2;
                    PageInkModel.this.layoutManager.setKeyboardLive(true);
                    PageInkModel pageInkModel = PageInkModel.this;
                    pageInkModel.scrollBy(0, -pageInkModel.totalScrollDy);
                    return;
                }
                if (PageInkModel.this.mKeyBoardState == 3) {
                    PageInkModel.this.mKeyBoardState = 1;
                    PageInkModel pageInkModel2 = PageInkModel.this;
                    pageInkModel2.scrollBy(0, pageInkModel2.totalScrollDy);
                    PageInkModel.this.layoutManager.setKeyboardLive(false);
                    PageInkModel.this.totalScrollDy = 0;
                }
            }
        };
        this.mList = new ArrayList();
        this.isShowSelect = false;
        this.previewBeans = new ArrayList();
        this.pageChange = false;
        this.searchTop = 0;
        this.mKeyBoardState = 1;
        this.fenestrule = false;
        this.mCursorHeight = 0.0f;
        this.isSearchScrollBy = true;
        this.totalScrollDy = 0;
        this.pageDataTempDir = FileUtil.getDir(this.mContextRef.get(), FileUtil.TMP_PAGE);
        this.windowInfo = new WindowInfo();
        this.mDrawView = new PageDrawView(this.mContextRef.get(), i);
        this.adapter = new PageAdapter(this.mContextRef.get(), this, this.mStateListener);
        this.layoutManager = new PageLayoutManager(pageInkView, this);
        Paint paint = new Paint();
        this.bitmapPaint = paint;
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        SelectViewGroup selectViewGroup = new SelectViewGroup(this.mContextRef.get(), RomUtils.isUseNewCreationAI());
        this.mSelectViewGroup = selectViewGroup;
        initView(selectViewGroup);
        this.mSelectViewGroup.setViewModel(this);
        this.mSelectViewGroup.setKeyboardChangedListener(new SelectViewGroup.OnChangedListener() { // from class: com.miui.libhandwritingpage.model.PageInkModel.2
            @Override // com.miui.libhandwritingpage.view.select.SelectViewGroup.OnChangedListener
            public void onCancelSelect() {
                PageInkModel.this.showSelectViewGroup();
            }

            @Override // com.miui.libhandwritingpage.view.select.SelectViewGroup.OnChangedListener
            public void cancelSoftKeyboard() {
                if (PageInkModel.this.totalScrollDy == 0 || PageInkModel.this.mKeyBoardState != 2) {
                    return;
                }
                PageInkModel.this.mKeyBoardState = 3;
                PageInkModel pageInkModel = PageInkModel.this;
                boolean z = pageInkModel.fenestrule;
                PageInkModel pageInkModel2 = PageInkModel.this;
                pageInkModel.scrollByKeyboard(z ? pageInkModel2.fenestraRunnable : pageInkModel2.fullScreenRunnable, 500L);
            }
        });
        this.mGlobalRulerView = new GlobalRulerView(this.mContextRef.get());
        initView(this.mGlobalRulerView);
        this.mSelectViewGroup.setStepManager(this.adapter.getStepHelper());
        this.adapter.getStepHelper().setSelectView(this.mSelectViewGroup);
        LruCacheUtil.initCache();
        this.isNightMode = UIUtils.isNightMode();
        Log.d(TAG, "This device does not support handwriting calculation.");
        FeatureManager.getInstance().setEnableMathRecognition(false);
        initInterface();
        CreationPageSDK.setToken(this.mContextRef.get());
    }

    public boolean isSupportCalcHandwrite() {
        try {
            return isCalcHandwriteSupportedDevice(this.mContextRef.get());
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean isCalcHandwriteSupportedDevice(Context context) {
        String[] stringArray;
        String str = android.os.Build.DEVICE;
        Resources resources = context.getResources();
        if (IS_GLOBAL) {
            stringArray = resources.getStringArray(R.array.calc_handwrite_global_device_whitelist);
        } else {
            stringArray = resources.getStringArray(R.array.calc_handwrite_device_whitelist);
        }
        return new ArrayList(Arrays.asList(stringArray)).contains(str);
    }

    @Override // com.miui.libhandwritingpage.model.IPageInkModel, android.view.ScaleGestureDetector.OnScaleGestureListener
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        Log.d(TAG, "onScaleBegin: ");
        MiCurveEngine miCurveEngine = getMiCurveEngine();
        if (miCurveEngine != null) {
            miCurveEngine.getMathSystem().setPageInteracting(true);
        }
        return super.onScaleBegin(scaleGestureDetector);
    }

    @Override // com.miui.libhandwritingpage.model.IPageInkModel, android.view.ScaleGestureDetector.OnScaleGestureListener
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.d(TAG, "onScaleEnd: ");
        MiCurveEngine miCurveEngine = getMiCurveEngine();
        if (miCurveEngine != null) {
            miCurveEngine.getMathSystem().setPageInteracting(false);
        }
        super.onScaleEnd(scaleGestureDetector);
    }

    @Override // com.miui.libhandwritingpage.model.IPageInkModel, android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        Log.d(TAG, "onScroll: ");
        Boolean bool = this.isHaveScroll;
        if (bool != null && !bool.booleanValue()) {
            this.isHaveScroll = true;
            MiCurveEngine miCurveEngine = getMiCurveEngine();
            if (miCurveEngine != null) {
                miCurveEngine.getMathSystem().setPageInteracting(true);
            }
        }
        return super.onScroll(motionEvent, motionEvent2, f, f2);
    }

    public void initView(View view) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(9);
        layoutParams.addRule(11);
        layoutParams.addRule(10);
        layoutParams.addRule(12);
        view.setLayoutParams(layoutParams);
    }

    private List<Integer> getItemLoadList() {
        ArrayList arrayList = new ArrayList();
        if (this.currentPosition >= 0 && this.currentPosition < this.mList.size()) {
            arrayList.add(Integer.valueOf(this.currentPosition));
        }
        int i = this.currentPosition - 1;
        int i2 = this.currentPosition + 1;
        for (int i3 = 1; i3 < 3; i3++) {
            if (i >= 0) {
                arrayList.add(Integer.valueOf(i));
            }
            if (i2 < this.mList.size()) {
                arrayList.add(Integer.valueOf(i2));
            }
            i--;
            i2++;
        }
        List<Integer> list = (List) Stream.concat(getDrawView().getCreationEngine().getCurveEngine().getPageSystem().getVisiblePageIndexListByScreenSpacePoint().stream(), arrayList.stream()).distinct().sorted().collect(Collectors.toList());
        Log.d(TAG, "getItemLoadList: " + list);
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindItemData() {
        PageAdapter pageAdapter = this.adapter;
        if (pageAdapter == null || this.currentItemView == null) {
            return;
        }
        pageAdapter.onBindItemView(this.currentItemView, getItemLoadList());
    }

    public void bindView() {
        int iMm2px;
        int iMm2px2;
        if (this.mList.isEmpty()) {
            Log.e(TAG, "[bindView] init failed, mList is empty");
            return;
        }
        if (this.currentItemView == null) {
            Log.e(TAG, "bindView: surfaceView is not ready");
            return;
        }
        this.bgColor = this.mList.get(0).bgColor;
        PageItemData pageItemData = this.mList.get(0);
        this.adapter.updateItemView(this.mVisibleWidth, this.mVisibleHeight, pageItemData.maxWidth);
        if (pageItemData.unLimitToNormal || pageItemData.isUnLimit) {
            this.currentItemView.setUnLimit(pageItemData.isUnLimit);
            pageItemData.isFixSize = true;
            if (pageItemData.unLimitToNormal) {
                this.isUnLimitToNormal = true;
            }
        } else {
            this.currentItemView.setUnLimit(false);
        }
        if (pageItemData.isFixSize) {
            this.currentItemView.setSpannedLayoutFixWidth((int) pageItemData.fixWidth);
        }
        this.currentItemView.getCreationEngine().getCanvasOperator().setCanvasDrawWidth(this.windowInfo.mWindowWidth);
        this.currentItemView.setSpannedLayoutFixMode(pageItemData.isFixSize);
        this.currentItemView.getCreationEngine().getCanvasOperator().setVisibleFixedWidth(pageItemData.maxWidth / this.layoutManager.getPageScaleRatio());
        this.currentItemView.setId(pageItemData.viewId);
        this.currentItemView.updateBackground(this.mPageBean.path, pageItemData);
        this.currentItemView.setRadius(this.mPageBean.radius);
        this.currentItemView.isUnLimitToNormal = pageItemData.unLimitToNormal;
        this.currentItemView.setCanvasSize(this.mVisibleWidth, (int) Math.max(this.mVisibleHeight, pageItemData.getItemHeight(this.mVisibleWidth, 1.0f)));
        bindItemData();
        getMiCurveEngine().getMathDataManager().registerMathSelectCallBack(new MathSelectCallbackProxy(this));
        int i = pageItemData.sizeUnit;
        if (i != 0) {
            if (i == 1) {
                iMm2px2 = pageItemData.width;
                iMm2px = pageItemData.height;
            } else if (i != 2) {
                iMm2px2 = 0;
                iMm2px = 0;
            } else {
                iMm2px2 = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData.width);
                iMm2px = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData.height);
            }
        } else if (pageItemData.isFixSize) {
            iMm2px = pageItemData.height;
            iMm2px2 = pageItemData.width;
        } else {
            iMm2px = (pageItemData.height * this.windowInfo.mWindowWidth) / pageItemData.width;
            iMm2px2 = this.windowInfo.mWindowWidth;
        }
        boolean z = TextUtils.isEmpty(pageItemData.bgImage) && TextUtils.isEmpty(pageItemData.pdfId);
        pageItemData.maxHeight = Math.max(iMm2px, pageItemData.maxHeight);
        pageItemData.maxWidth = Math.max(iMm2px2, pageItemData.maxWidth);
        this.currentItemView.setShieldWidth(z ? (pageItemData.maxWidth - iMm2px2) / 2 : 0);
        this.currentItemView.setBottomShieldHeight(z ? pageItemData.maxHeight - iMm2px : 0.0f);
        showSelectViewGroup();
        bringRulerViewToFront();
        this.currentItemView.setUnlimitedSize(this.mVisibleWidth, this.mVisibleHeight);
        this.currentItemView.update(0, pageItemData.layoutRectF, 0.0f, 0.0f);
        if (this.currentItemView.getCreationEngine() != null) {
            this.currentItemView.getCreationEngine().setGlobalRulerOperator(this.mGlobalRulerView.getRulerManipulation());
        }
        this.mGlobalRulerView.setOffset(this.currentItemView.getLayout());
        getMiCurveEngine().setPageInkModelInterface(this.pageInkModelInterface);
    }

    public SearchWrapper getSearchWrapper() {
        return this.searchWrapper;
    }

    public List<PageItemData> getItemDataList() {
        return this.mList;
    }

    public EngineConfig getEngineConfig() {
        return this.engineConfig;
    }

    public String getDirPath() {
        if (this.mPageBean == null) {
            return null;
        }
        return this.mPageBean.path;
    }

    public String getPdfId() {
        return this.mList.get(0).pdfId;
    }

    public void onCurrentPageChanged(int i) {
        if (this.currentPosition != i) {
            Log.i(TAG, String.format("--debug-- onCurrentPageChanged change currentPosition [%d] to [%d]", Integer.valueOf(this.currentPosition), Integer.valueOf(i)));
            this.currentPosition = i;
            PageChangeListener pageChangeListener = this.mPageChangeListener;
            if (pageChangeListener != null) {
                pageChangeListener.onPageSelectChange(this.currentPosition);
            }
            bindItemData();
            loadPdfBitmap(this.mList);
        }
        if (this.mStateView != null) {
            updateStateView(this.currentPosition);
        }
        AlgorithmNative.getInstance().setScaleRate(this.layoutManager.getScale());
        if (this.mStateListener == null || this.currentItemView == null) {
            return;
        }
        this.mStateListener.onPageScale(this.currentItemView.getScale());
    }

    /* JADX INFO: renamed from: onPageSizeChanged, reason: merged with bridge method [inline-methods] */
    public void m854x4cf22c30(int i) {
        if (this.currentPosition != i) {
            Log.i(TAG, String.format("--debug-- onPageSizeChanged change currentPosition [%d] to [%d]", Integer.valueOf(this.currentPosition), Integer.valueOf(i)));
            this.currentPosition = i;
            PageChangeListener pageChangeListener = this.mPageChangeListener;
            if (pageChangeListener != null) {
                pageChangeListener.onPageSelectChange(this.currentPosition);
            }
        }
        if (this.mStateView != null) {
            updateStateView(this.currentPosition);
        }
        AlgorithmNative.getInstance().setScaleRate(this.layoutManager.getScale());
        this.mPageInkView.requestLayout();
        if (this.isSearchScrollBy) {
            setSearchResult(i, this.searchWrapper.searchColor, this.searchWrapper.searchAlpha, this.searchWrapper);
        }
        if (this.mStateListener == null || this.currentItemView == null) {
            return;
        }
        this.mStateListener.onPageScale(this.currentItemView.getScale());
    }

    public void onFooterViewMaxHeight() {
        CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m850x53ae6283();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onFooterViewMaxHeight$1$com-miui-libhandwritingpage-model-PageInkModel, reason: not valid java name */
    /* synthetic */ void m850x53ae6283() {
        if (this.mList.size() >= 500) {
            return;
        }
        addPage(this.mList.get(getPageCount() - 1));
    }

    public boolean isMaxPageCount() {
        return this.mList.size() >= 500;
    }

    public int getPageCount() {
        return this.mList.size();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mCanWrite) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.downOnRuler = false;
            boolean zDispatchRuler = dispatchRuler(motionEvent);
            this.downOnRuler = zDispatchRuler;
            if (!zDispatchRuler) {
                dispatchSelect(motionEvent);
                dispatchMathAnimation(motionEvent);
            }
        } else if (actionMasked == 1 || actionMasked == 3) {
            if (this.downOnRuler) {
                dispatchRuler(motionEvent);
            } else {
                this.layoutManager.resetDrag();
                dispatchSelect(motionEvent);
            }
        } else if (this.downOnRuler) {
            dispatchRuler(motionEvent);
        } else {
            dispatchSelect(motionEvent);
        }
        return true;
    }

    private boolean dispatchRuler(MotionEvent motionEvent) {
        IGlobalRulerOperator rulerManipulation = this.mGlobalRulerView.getRulerManipulation();
        RectF offset = this.mGlobalRulerView.getOffset();
        if (rulerManipulation == null || !rulerManipulation.isEnable() || offset == null) {
            return false;
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.offsetLocation(offset.left > 0.0f ? -offset.left : 0.0f, offset.top > 0.0f ? -offset.top : 0.0f);
        return rulerManipulation.onTouchEvent(motionEventObtain);
    }

    private void dispatchSelect(MotionEvent motionEvent) {
        if (this.isShowSelect) {
            if (this.hasPointerDown || this.downPos == -1) {
                return;
            }
            this.mSelectViewGroup.onTouchEvent(motionEvent);
            return;
        }
        if (this.currentItemView != null) {
            if (!this.enablePenOnlyMode || motionEvent.getToolType(0) == 2) {
                if (motionEvent.getAction() == 1 && motionEvent.getPointerCount() == 1) {
                    this.currentItemView.setSearchRectList(true, 0);
                }
                if (isUnlimited()) {
                    this.layoutManager.checkEdgePoint(motionEvent.getX(), motionEvent.getY());
                }
                this.currentItemView.onPageTouchEvent(motionEvent);
            }
        }
    }

    private void dispatchMathAnimation(MotionEvent motionEvent) {
        if (this.currentItemView != null) {
            this.currentItemView.cancelMathAnimation(motionEvent);
        }
    }

    public void expanseUnLimitPage() {
        this.layoutManager.expansePage();
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.isCrop && motionEvent.getPointerCount() > 1) {
            Log.i(TAG, "dispatchTouchEvent1 return");
            return false;
        }
        SelectViewGroup selectViewGroup = this.mSelectViewGroup;
        if (selectViewGroup != null && selectViewGroup.isInSelecting && !this.undoWhenUp) {
            Log.i(TAG, "dispatchTouchEvent2 return");
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        this.isDrawing = (actionMasked == 1 || actionMasked == 3) ? false : true;
        if (actionMasked != 0 && this.isDownWithNavigationBar) {
            Log.i(TAG, "dispatchTouchEvent3 return");
            return true;
        }
        if (actionMasked == 0) {
            this.isScaling = false;
            this.isDownWithNavigationBar = false;
            this.hasPointerDown = false;
            if ((motionEvent.getToolType(0) & 2) != 0) {
                if (Math.ceil(motionEvent.getY()) + ((double) UIUtils.getNavigationBarHeight(this.mContextRef.get())) >= this.mVisibleHeight) {
                    this.isDownWithNavigationBar = true;
                }
            } else if (Math.ceil(motionEvent.getY()) + ((double) this.UNINTENDED_TOUCH_HEIGHT) >= this.mVisibleHeight) {
                this.isDownWithNavigationBar = true;
            }
            this.layoutManager.onTouchDown();
            if (isTouchEventOutsidePageArea(motionEvent)) {
                if (isItemSelected() && isDownButton(motionEvent)) {
                    this.downPos = 0;
                } else {
                    onCancelEvent();
                    this.downPos = -1;
                }
            } else {
                this.downPos = 0;
            }
            if (this.isDownWithNavigationBar) {
                return true;
            }
            processGesture(motionEvent);
            if (this.downPos == -1) {
                return true;
            }
            if (this.currentItemView != null && this.currentItemView.getCreationEngine() != null) {
                this.isDownFlinging = getDrawView().getCreationEngine().getCurveEngine().getCamera().getBIsFlinging();
                if (this.isDownFlinging) {
                    this.isDownFlinging = getDrawView().getCreationEngine().getCurveEngine().getCamera().stopFling();
                }
            }
            if (this.layoutManager.getCurrentOffsetY() > 0.0f && motionEvent.getY() < this.layoutManager.getHeadHeight()) {
                IGlobalRulerOperator rulerManipulation = this.mGlobalRulerView.getRulerManipulation();
                if (this.currentItemView != null && this.currentItemView.getCreationEngine() != null && rulerManipulation != null && rulerManipulation.isEnable()) {
                    this.currentItemView.getCreationEngine().setGlobalRulerOperator(this.mGlobalRulerView.getRulerManipulation());
                    this.mGlobalRulerView.setOffset(this.currentItemView.getLayout());
                    RectF offset = this.mGlobalRulerView.getOffset();
                    if (offset != null) {
                        MotionEvent.obtain(motionEvent).offsetLocation(offset.left > 0.0f ? -offset.left : 0.0f, offset.top > 0.0f ? -offset.top : 0.0f);
                    }
                }
            }
            this.isCancelTouch = false;
            updateCurrentViewItem(motionEvent.getX(), motionEvent.getY());
            if (this.downPos == -1) {
                onCancelEvent();
            }
            doubleClickUndo(motionEvent);
            if (!isItemSelected() && isTouchEventOutsidePageArea(motionEvent)) {
                return true;
            }
            if (this.isCancelTouch) {
                this.isCancelTouch = false;
            } else if (!this.isDownFlinging || this.isUnLimitToNormal) {
                onTouchEvent(motionEvent);
            }
        } else if (actionMasked == 1) {
            processGesture(motionEvent);
            if (this.mGlobalRulerView.isUsingRuler()) {
                updateCurrentViewItem(motionEvent.getX(), motionEvent.getY());
            }
            this.hasPointerDown = false;
            processEndAction(motionEvent);
        } else if (actionMasked == 2) {
            processGesture(motionEvent);
            doubleClickUndo(motionEvent);
            if (!this.isCancelTouch && (!this.isDownFlinging || this.isUnLimitToNormal)) {
                onTouchEvent(motionEvent);
            }
        } else if (actionMasked == 3) {
            onCancelEvent();
            processGesture(motionEvent);
            processEndAction(motionEvent);
        } else if (actionMasked == 5) {
            this.layoutManager.resetExpanse();
            processGesture(motionEvent);
            if (motionEvent.getPointerCount() > 1 && !this.mGlobalRulerView.isUsingRuler() && !this.isCancelTouch) {
                this.isCancelTouch = true;
                onCancelEvent();
            }
            doubleClickUndo(motionEvent);
            this.hasPointerDown = true;
            if (!this.isCancelTouch && (!this.isDownFlinging || this.isUnLimitToNormal)) {
                onTouchEvent(motionEvent);
            }
        } else if (actionMasked == 6) {
            if (!this.isCancelTouch && (!this.isDownFlinging || this.isUnLimitToNormal)) {
                onTouchEvent(motionEvent);
            }
            doubleClickUndo(motionEvent);
        }
        return true;
    }

    private boolean isTouchEventOutsidePageArea(MotionEvent motionEvent) {
        PageDrawView pageDrawView;
        if (!isUnlimited() && (pageDrawView = this.mDrawView) != null && pageDrawView.getCreationEngine() != null) {
            PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
            RectF area = getDrawView().getCreationEngine().getCurveEngine().getPageSystem().getPageData(getDrawView().getCreationEngine().getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(pointF)).getArea();
            PointF worldSpacePointFromScreenSpacePoint = getDrawView().getCreationEngine().getCurveEngine().getWorldSpacePointFromScreenSpacePoint(pointF);
            if (!area.contains(worldSpacePointFromScreenSpacePoint.x, worldSpacePointFromScreenSpacePoint.y) && motionEvent.getPointerCount() == 1) {
                Log.d(TAG, "ignore !! outside draw area, action = " + motionEvent.getAction());
                return true;
            }
        }
        return false;
    }

    private void processGesture(MotionEvent motionEvent) {
        this.pointCount = motionEvent.getPointerCount();
        this.hasDownTime = motionEvent.getEventTime() - motionEvent.getDownTime();
        this.mGestureDetector.onTouchEvent(motionEvent);
        this.mScaleGestureDetector.onTouchEvent(motionEvent);
    }

    private void processEndAction(MotionEvent motionEvent) {
        Log.d(TAG, "processEndAction: Action:" + motionEvent.getAction());
        this.layoutManager.onScrollEndNew();
        MiCurveEngine miCurveEngine = getMiCurveEngine();
        if (miCurveEngine != null) {
            miCurveEngine.getCamera().onScrollEnd();
            if (motionEvent.getAction() == 1) {
                this.isHaveScroll = false;
                miCurveEngine.getMathSystem().setPageInteracting(false);
            }
        }
        if (this.downPos == -1 || (this.enablePenOnlyMode && (motionEvent.getToolType(0) & 1) != 0 && !isInSelectedAction())) {
            this.layoutManager.onTouchEnd();
            this.isCancelTouch = false;
        }
        doubleClickUndo(motionEvent);
        if (this.isCancelTouch) {
            if (!isDoubleClickNoNeedRefresh()) {
                this.isScrollGesture = false;
                this.layoutManager.onTouchEnd();
            }
            this.isCancelTouch = false;
            onTouchEvent(motionEvent);
            return;
        }
        if (this.isDownFlinging && !this.isUnLimitToNormal) {
            onCancelEvent();
        } else {
            onTouchEvent(motionEvent);
        }
    }

    @Override // com.miui.libhandwritingpage.model.IPageInkModel
    public boolean isInSelectedAction() {
        return this.mSelectViewGroup.isInSelectedAction();
    }

    public boolean isItemSelected() {
        return this.mSelectViewGroup.isItemSelected();
    }

    public boolean isDownButton(MotionEvent motionEvent) {
        return this.mSelectViewGroup.isDownButton(motionEvent);
    }

    @Override // com.miui.libhandwritingpage.model.IPageInkModel
    public boolean isInEditAction() {
        return this.mSelectViewGroup.isInEditAction();
    }

    @Override // com.miui.libhandwritingpage.model.IPageInkModel
    public boolean isSelect() {
        return this.mSelectViewGroup.isItemSelected();
    }

    public void onCancelEvent() {
        this.isCancelTouch = true;
        this.mKeyBoardState = 1;
        this.layoutManager.setKeyboardLive(false);
        this.totalScrollDy = 0;
        if (this.currentItemView != null) {
            this.currentItemView.onCancelEvent();
        }
        this.mSelectViewGroup.cancelSelect(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initEntPages() {
        int i;
        InkEntFileIOHelper.clear();
        int i2 = this.windowInfo.mWindowWidth;
        Log.i(TAG, String.format("initEntPages: getWindowWidth[%d]", Integer.valueOf(i2)));
        InkEntFileIOHelper.setScreenWidth(i2);
        int i3 = 0;
        for (PageItemData pageItemData : this.mList) {
            if (!pageItemData.unLimitToNormal || pageItemData.maxWidth <= pageItemData.width) {
                i = i2;
            } else {
                int dimension = ((int) this.mContextRef.get().getResources().getDimension(R.dimen.creation_paint_view_margin)) * 2;
                if (pageItemData.width + dimension >= i2) {
                    i = pageItemData.maxWidth;
                } else {
                    i = (pageItemData.maxWidth * i2) / (pageItemData.width + dimension);
                }
            }
            int iRound = Math.round((pageItemData.height * i2) / pageItemData.width);
            pageItemData.width = i2;
            pageItemData.height = iRound;
            pageItemData.maxWidth = Math.max(pageItemData.maxWidth, i2);
            pageItemData.maxHeight = iRound;
            Log.i(TAG, String.format("initEntPages: setPageSize w-h[%d, %d]", Integer.valueOf(i), Integer.valueOf(iRound)));
            InkEntFileIOHelper.addPage(i3, i, iRound, pageItemData.isUnLimit);
            i3++;
        }
    }

    public void load(String str, final PageLoadListener pageLoadListener) {
        PageEntInfo pageEnt;
        boolean z;
        this.mEntFilePath = str;
        this.mLoadListener = pageLoadListener;
        if (TextUtils.isEmpty(str)) {
            if (pageLoadListener != null) {
                pageLoadListener.onLoadCompleted(false, false, "");
                return;
            }
            return;
        }
        File file = new File(str);
        if (!file.exists()) {
            PageBean pageBean = new PageBean();
            pageBean.path = file.getParentFile().getAbsolutePath();
            pageBean.width = 755;
            pageBean.height = 1156;
            pageBean.orientation = (short) 0;
            create(pageBean, pageLoadListener, false);
            return;
        }
        this.adapter.setEntFilePath(str);
        if (PageEntTools.verifyFormat(str)) {
            try {
                pageEnt = PageEntTools.readPageEnt(str, this.pageDataTempDir);
            } catch (Exception e) {
                e.printStackTrace();
                pageEnt = null;
            }
        } else {
            pageEnt = null;
        }
        if (PageEntTools.verifySinglePageFormat(str)) {
            try {
                pageEnt = PageEntTools.readSinglePageEnt(str, this.pageDataTempDir);
                z = true;
            } catch (Exception e2) {
                e2.printStackTrace();
                z = false;
            }
            Log.d(TAG, "ww-- load singlepage");
        } else {
            z = false;
        }
        if (pageEnt != null) {
            PageEntHelper pageEntHelper = new PageEntHelper();
            this.mPageBean = pageEntHelper.loadEnt(str, pageEnt);
            if (z) {
                this.mPageBean.isSuniaSinglePage = true;
                this.mPageBean.path += File.separator + "ent_multi_engine_and_page_" + System.currentTimeMillis();
                if (new File(this.mPageBean.path).mkdir()) {
                    Log.d(TAG, "ww created");
                }
            }
            this.mList.clear();
            if (z) {
                this.mList.add(pageEntHelper.getSinglePageItemData(pageEnt, pageLoadListener.getSuniaSinglePageBgType()));
            } else {
                List<PageItemData> itemDataList = pageEntHelper.getItemDataList(pageEnt);
                if (itemDataList != null && itemDataList.size() > 0) {
                    this.mList.addAll(itemDataList);
                }
            }
            if (this.mLoadListener != null) {
                this.mLoadListener.onPageCount(this.mList.size());
            }
            if (!this.mList.isEmpty()) {
                List<PageItemData> list = this.mList;
                this.isUnlimited = list.get(list.size() - 1).isUnLimit;
            }
            PageSizeListener pageSizeListener = this.mPageSizeListener;
            if (pageSizeListener != null) {
                pageSizeListener.onPageSizeChanged(this.isUnlimited);
            }
        }
        final boolean z2 = pageEnt != null;
        createItemView();
        this.currentItemView.post(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m847lambda$load$2$commiuilibhandwritingpagemodelPageInkModel(z2, pageLoadListener);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$load$2$com-miui-libhandwritingpage-model-PageInkModel, reason: not valid java name */
    /* synthetic */ void m847lambda$load$2$commiuilibhandwritingpagemodelPageInkModel(boolean z, PageLoadListener pageLoadListener) {
        Log.d(TAG, "load: Complete:" + z);
        if (!this.mList.isEmpty() && !this.mList.get(0).pdfId.isEmpty()) {
            getDrawView().getCreationEngine().getCurveEngine().getPageSystem().setPdfMode(true);
        }
        if (z) {
            initLoadData();
            this.mPageInkView.requestLayout();
        }
        if (this.mLoadListener != null) {
            if (this.mPageBean == null) {
                pageLoadListener.onLoadCompleted(false, false, "");
            } else {
                this.mLoadListener.onLoadCompleted(z, !TextUtils.isEmpty(this.mPageBean.pdfPath), this.mEntFilePath);
            }
        }
    }

    private void initLoadData() {
        bindView();
        onPageChanged();
        addSystemPages();
        getDrawView().getCreationEngine().getCurveEngine().getPageSystem().adjustAllPageSize((List) this.mList.stream().map(new Function() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                PageItemData pageItemData = (PageItemData) obj;
                return Arrays.asList(Integer.valueOf(pageItemData.width), Integer.valueOf(pageItemData.height), Integer.valueOf(pageItemData.maxWidth), Integer.valueOf(pageItemData.maxHeight));
            }
        }).collect(Collectors.toList()));
        Activity activity = Utils.getActivity(this.mContextRef.get());
        if (activity != null) {
            this.layoutManager.initByWindowMode(activity.isInMultiWindowMode());
        }
        loadPdfBitmap(this.mList);
    }

    public void createItemView() {
        MiCurveEngine curveEngine;
        String str = TAG;
        Log.i(str, "createItemView");
        if (this.currentItemView == null) {
            this.currentItemView = this.adapter.onCreateItemView(this.mDrawView);
            this.mPageInkView.addView(this.currentItemView);
            if (FeatureManager.getInstance().isEnableMathRecognition()) {
                try {
                    if (getDrawView() == null || getDrawView().getCreationEngine() == null || (curveEngine = getDrawView().getCreationEngine().getCurveEngine()) == null || curveEngine.getRecognitionModel() == null) {
                        return;
                    }
                    PenEngine penEngine = curveEngine.getPenEngine();
                    if (penEngine != null && penEngine.mCurveRecognizeManager != null) {
                        curveEngine.getRecognitionModel().setupRealtimeRecognition(this.mContextRef.get(), penEngine.mCurveRecognizeManager);
                        Log.d(str, "Initialized math recognition engine");
                    } else {
                        Log.w(str, "CurveRecognizeManager not found, skip setup");
                    }
                    if (!TextUtils.isEmpty(this.mEntFilePath)) {
                        Log.d(str, "createItemView: Attempting to open iink file, entFilePath=" + this.mEntFilePath);
                        boolean zOpenIinkFile = curveEngine.getRecognitionModel().openIinkFile(this.mEntFilePath);
                        if (curveEngine.getRecognitionModel().getEditor() != null) {
                            this.mPageBean.iinkPackage = curveEngine.getRecognitionModel().getEditor().getContentPackage();
                        }
                        Log.d(str, "createItemView: Open iink file result: " + zOpenIinkFile);
                        return;
                    }
                    Log.d(str, "createItemView: mEntFilePath is empty, will open later in load() or initData()");
                } catch (Exception e) {
                    Log.e(TAG, "Failed to setup realtime recognition in createItemView", e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadPdfBitmap(List<PageItemData> list) {
        if (list == null || list.isEmpty() || list.get(0).pdfId.isEmpty()) {
            return;
        }
        if (this.mImportPdfBitmap == null) {
            ImportPdfBitmap importPdfBitmap = new ImportPdfBitmap();
            this.mImportPdfBitmap = importPdfBitmap;
            importPdfBitmap.setLoadListener(new PDFLoadingListener() { // from class: com.miui.libhandwritingpage.model.PageInkModel.5
                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded() {
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded(int i) {
                    PageSystem pageSystem = PageInkModel.this.getDrawView().getCreationEngine().getCurveEngine().getPageSystem();
                    if (pageSystem.pageHasBgImage(i)) {
                        Log.d(PageInkModel.TAG, "onLoaded: pageHasBgImage:" + i);
                        return;
                    }
                    Bitmap bitmap = PageInkModel.this.mImportPdfBitmap.getBitmap(i);
                    if (bitmap != null) {
                        Log.d(PageInkModel.TAG, String.format("onLoaded :%d %d %d", Integer.valueOf(i), Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight())));
                        pageSystem.setPageBgImage(i, bitmap, false);
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onFinish() {
                    Log.d(PageInkModel.TAG, "onFinish");
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onRemove(int i) {
                    Log.d(PageInkModel.TAG, "onRemove:" + i);
                    if (i != -1) {
                        PageInkModel.this.getDrawView().getCreationEngine().getCurveEngine().getPageSystem().releasePageBgImage(i);
                    }
                }
            });
        }
        LinkedHashMap<Integer, Integer> pageIndexTable = getPageIndexTable(list);
        String str = getDirPath() + File.separator + list.get(0).pdfId;
        this.mImportPdfBitmap.setVisibleRect(this.mVisibleWidth);
        this.mImportPdfBitmap.setData(str, pageIndexTable);
    }

    private LinkedHashMap<Integer, Integer> getPageIndexTable(List<PageItemData> list) {
        int i = this.currentPosition;
        if (this.mIsFirstjump && this.mFirstJumpPosition != -1) {
            i = this.mFirstJumpPosition;
            this.mIsFirstjump = false;
        }
        Log.d(TAG, String.format("getPageIndexTable: %d %d %d %b", Integer.valueOf(this.currentPosition), Integer.valueOf(this.mFirstJumpPosition), Integer.valueOf(i), Boolean.valueOf(this.mIsFirstjump)));
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        if (i >= 0 && i < list.size()) {
            linkedHashMap.put(Integer.valueOf(list.get(i).getPageIndex()), Integer.valueOf(list.get(i).pdfIndex));
        }
        if (i != 0 || !this.mIsFirstjump) {
            int i2 = i - 1;
            int i3 = i + 1;
            for (int i4 = 1; i4 < 5; i4++) {
                if (i2 >= 0) {
                    linkedHashMap.put(Integer.valueOf(list.get(i2).getPageIndex()), Integer.valueOf(list.get(i2).pdfIndex));
                }
                if (i3 < list.size()) {
                    linkedHashMap.put(Integer.valueOf(list.get(i3).getPageIndex()), Integer.valueOf(list.get(i3).pdfIndex));
                }
                i2--;
                i3++;
            }
            if (!list.isEmpty()) {
                int pageIndex = list.get(0).getPageIndex();
                int i5 = list.get(0).pdfIndex;
                if (!linkedHashMap.containsKey(Integer.valueOf(pageIndex))) {
                    linkedHashMap.put(Integer.valueOf(pageIndex), Integer.valueOf(i5));
                }
            }
        }
        return linkedHashMap;
    }

    public void create(PageBean pageBean, PageLoadListener pageLoadListener, boolean z) {
        this.mPageBean = pageBean;
        this.isUnlimited = z;
        PageSizeListener pageSizeListener = this.mPageSizeListener;
        if (pageSizeListener != null) {
            pageSizeListener.onPageSizeChanged(this.isUnlimited);
        }
        if (this.mPageBean == null || TextUtils.isEmpty(this.mPageBean.path) || this.mPageBean.width == 0 || this.mPageBean.height == 0) {
            if (pageLoadListener != null) {
                pageLoadListener.onLoadCompleted(false, false, "");
                return;
            }
            return;
        }
        this.mLoadListener = pageLoadListener;
        this.mEntFilePath = this.mPageBean.path + File.separator + "multi_page_ent.ent";
        this.adapter.setEntFilePath(this.mPageBean.path);
        if (!TextUtils.isEmpty(pageBean.pdfPath)) {
            String strCopyPdfFileToMultiDir = FileUtil.copyPdfFileToMultiDir(pageBean.pdfPath, this.mPageBean.path);
            if (!TextUtils.isEmpty(strCopyPdfFileToMultiDir)) {
                this.pageChange = true;
                parsePdfFile(strCopyPdfFileToMultiDir, pageBean.pdfPassword, this.mLoadListener);
                return;
            }
        }
        CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m846lambda$create$4$commiuilibhandwritingpagemodelPageInkModel();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$create$4$com-miui-libhandwritingpage-model-PageInkModel, reason: not valid java name */
    /* synthetic */ void m846lambda$create$4$commiuilibhandwritingpagemodelPageInkModel() {
        initData(this.mEntFilePath);
    }

    private BgSystem.EBgStyle getBgStyle(int i) {
        if (i == 1) {
            return BgSystem.EBgStyle.HorizontalLine;
        }
        if (i == 3) {
            return BgSystem.EBgStyle.Grid;
        }
        if (i == 4) {
            return BgSystem.EBgStyle.Dot;
        }
        return BgSystem.EBgStyle.None;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSystemPages() {
        PageSystem.PageInfo pageInfo;
        if (isUnlimited()) {
            return;
        }
        if (this.mList.isEmpty()) {
            Log.i(TAG, "addSystemPages list isEmpty");
            return;
        }
        Log.i(TAG, "addSystemPages");
        ArrayList<PageSystem.PageInfo> arrayList = new ArrayList<>();
        for (PageItemData pageItemData : this.mList) {
            Color color = new Color(pageItemData.bgColor);
            if (isUnLimitToNormal()) {
                pageInfo = new PageSystem.PageInfo(pageItemData.width, pageItemData.height, pageItemData.maxWidth, pageItemData.maxHeight, color);
            } else {
                pageInfo = new PageSystem.PageInfo(pageItemData.maxWidth, pageItemData.maxHeight, pageItemData.maxWidth, pageItemData.maxHeight, color);
            }
            pageInfo.setBgStyle(getBgStyle(pageItemData.bgGrid));
            pageInfo.setBgImagePath(getBgImagePath(pageItemData));
            arrayList.add(pageInfo);
        }
        getDrawView().getCreationEngine().getCurveEngine().getPageSystem().setAllPageInfo(arrayList);
    }

    private void addSystemPage(PageItemData pageItemData, boolean z) {
        PageSystem.PageInfo pageInfo;
        if (isUnlimited()) {
            return;
        }
        Log.i(TAG, "addSystemPage");
        Color color = new Color(pageItemData.bgColor);
        if (isUnLimitToNormal()) {
            pageInfo = new PageSystem.PageInfo(pageItemData.width, pageItemData.height, pageItemData.maxWidth, pageItemData.maxHeight, color);
        } else {
            pageInfo = new PageSystem.PageInfo(pageItemData.maxWidth, pageItemData.maxHeight, pageItemData.maxWidth, pageItemData.maxHeight, color);
        }
        String bgImagePath = getBgImagePath(pageItemData);
        if (bgImagePath != null) {
            pageInfo.setBgImagePath(bgImagePath);
        } else if (z) {
            pageInfo.setBgImagePath(getBgImagePath(this.mPageBean.bgImage));
        }
        pageInfo.setBgStyle(getBgStyle(pageItemData.bgGrid));
        getDrawView().getCreationEngine().getCurveEngine().getPageSystem().addPage(pageInfo);
    }

    private void setPageBGColor(int i, int i2) {
        if (isUnlimited()) {
            return;
        }
        getDrawView().getCreationEngine().getCurveEngine().getPageSystem().setPageBGColor(i, new Color(i2));
    }

    private void setPageBGStyle(int i, int i2) {
        if (isUnlimited()) {
            return;
        }
        getDrawView().getCreationEngine().getCurveEngine().getPageSystem().setPageBGStyle(i, getBgStyle(i2));
    }

    public void initData(String str) {
        this.mPageBean.createTime = System.currentTimeMillis();
        if (!TextUtils.isEmpty(str)) {
            try {
                if (FeatureManager.getInstance().isEnableMathRecognition() && getDrawView() != null && getDrawView().getCreationEngine() != null) {
                    Log.d(TAG, "Open iink file in create(): " + str + ", result: " + getDrawView().getCreationEngine().getCurveEngine().getRecognitionModel().createIinkFile(str));
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to open iink file in create()", e);
            }
        }
        int i = this.mPageBean.width;
        int i2 = this.mPageBean.height;
        PageItemData pageItemData = new PageItemData(new BgGridConfiguration(), "", "" + (this.mList.size() - 1), 0, "", 0, this.mPageBean.orientation, -1, this.mPageBean.orientation == 0 ? i : i2, this.mPageBean.orientation == 0 ? i2 : i, (int) System.currentTimeMillis(), "");
        if (this.isUnlimited) {
            pageItemData.isFixSize = true;
            pageItemData.fixWidth = UIUtils.getRealWidth(this.mContextRef.get());
        }
        if (!TextUtils.isEmpty(this.mPageBean.pdfPath) || !TextUtils.isEmpty(this.mPageBean.bgImage)) {
            pageItemData.isFixSize = true;
            pageItemData.fixWidth = UIUtils.getRealWidth(this.mContextRef.get());
            pageItemData.useNewSize = true;
            float f = i2 / i;
            pageItemData.maxWidth = this.windowInfo.mWindowWidth;
            pageItemData.maxHeight = (int) (this.windowInfo.mWindowWidth * f);
            Log.d(TAG, String.format("initData %d %d %d %d %f", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(pageItemData.maxWidth), Integer.valueOf(pageItemData.maxHeight), Float.valueOf(f)));
        }
        this.bgColor = pageItemData.bgColor;
        this.mList.add(pageItemData);
        pageItemData.setPageIndex(this.mList.size() - 1);
        onPageChanged();
        initEntPages();
        addSystemPage(pageItemData, true);
        Activity activity = Utils.getActivity(this.mContextRef.get());
        if (activity != null) {
            this.layoutManager.initByWindowMode(activity.isInMultiWindowMode());
        }
        if (this.mLoadListener != null) {
            this.mLoadListener.onLoadCompleted(true, !TextUtils.isEmpty(this.mPageBean.pdfPath), str);
        }
        this.mPageInkView.requestLayout();
        if (this.mStateListener != null) {
            this.mStateListener.onContentChanged(-1);
        }
    }

    public void updateCurrentViewItem(float f, float f2) {
        this.downPos = -1;
        if (this.currentItemView == null) {
            Log.e(TAG, "updateCurrentViewItem currentItemView is null");
            return;
        }
        RectF rectF = new RectF(this.currentItemView.getLayout());
        if (f2 < rectF.top || f2 > rectF.bottom) {
            Log.e(TAG, "updateCurrentViewItem invalid touch point");
            return;
        }
        this.currentItemView.getCreationEngine().setGlobalRulerOperator(null);
        this.currentPosition = this.currentItemView.getCreationEngine().getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(new PointF(f, f2));
        this.currentItemView.getCreationEngine().getCurveEngine().getPageSystem().setCurrentPageIndex(this.currentPosition);
        PageChangeListener pageChangeListener = this.mPageChangeListener;
        if (pageChangeListener != null) {
            pageChangeListener.onPageSelectChange(this.currentPosition);
        }
        if (rectF.contains(f, f2)) {
            this.downPos = this.currentPosition;
        }
        bindItemData();
        loadPdfBitmap(this.mList);
        this.mPageInkView.requestLayout();
        this.currentItemView.getCreationEngine().setGlobalRulerOperator(this.mGlobalRulerView.getRulerManipulation());
        this.mGlobalRulerView.setOffset(this.currentItemView.getLayout());
        this.mSelectViewGroup.setViewModel(this.currentItemView, this.engineConfig);
        this.mSelectViewGroup.setSpanned(this.currentItemView.getMode());
        this.currentItemView.setSelectGroup(this.mSelectViewGroup);
        long pageTotalSavePoint = getPageTotalSavePoint();
        this.mSelectViewGroup.setTotalPoint(pageTotalSavePoint);
        this.currentItemView.setSelectView(this.mSelectViewGroup);
        this.currentItemView.getCreationEngine().getEditOperator().enableSelectedContour(this.engineConfig.enableSelectedContour);
        this.currentItemView.getCreationEngine().getDataAccess().setExtraTotalPoint(pageTotalSavePoint);
        if (this.mStateView == null) {
            return;
        }
        updateStateView(this.currentPosition);
    }

    public float getScale() {
        return this.layoutManager.getScale();
    }

    public float getFactoryScale() {
        return this.layoutManager.getFactorScale();
    }

    public long getPageTotalSavePoint() {
        Iterator<PageItemData> it = this.mList.iterator();
        long j = 0;
        while (it.hasNext()) {
            j += it.next().curvTotalSavePoint;
        }
        return j;
    }

    public void addPage(boolean z) {
        if (this.mList.size() >= 500) {
            return;
        }
        addPage(this.mList.get(getCurrentPosition()));
        jumpPage(this.mList.size() - 1, z);
    }

    public void addPage(PageItemData pageItemData) {
        if (this.currentItemView == null) {
            Log.e(TAG, "addPage currentItemView is null");
            return;
        }
        this.pageChange = true;
        PageItemData pageItemData2 = new PageItemData(new BgGridConfiguration(), "", "" + (this.mList.size() - 1), -1, "", 0, this.orientation, -1, this.orientation == 0 ? pageItemData.width : pageItemData.height, this.orientation == 0 ? pageItemData.height : pageItemData.width, (int) System.currentTimeMillis(), "");
        pageItemData2.sizeUnit = pageItemData.sizeUnit;
        pageItemData2.width = pageItemData.width;
        pageItemData2.height = pageItemData.height;
        pageItemData2.pageSizeType = pageItemData.pageSizeType;
        if (pageItemData.sizeUnit == 2) {
            pageItemData2.maxWidth = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData.width);
            pageItemData2.maxHeight = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData.height);
        } else if (pageItemData.sizeUnit == 1) {
            pageItemData2.maxWidth = pageItemData.width;
            pageItemData2.maxHeight = pageItemData.height;
        } else if (pageItemData.isFixSize) {
            pageItemData2.maxWidth = pageItemData.width;
            pageItemData2.maxHeight = pageItemData.height;
            if (!TextUtils.isEmpty(pageItemData.bgImage) || !TextUtils.isEmpty(pageItemData.pdfId)) {
                pageItemData2.width = pageItemData.maxWidth;
                pageItemData2.height = pageItemData.maxHeight;
                pageItemData2.maxWidth = pageItemData.maxWidth;
                pageItemData2.maxHeight = pageItemData.maxHeight;
            }
        } else {
            float f = pageItemData.maxHeight / pageItemData.maxWidth;
            pageItemData2.maxWidth = this.windowInfo.mWindowWidth;
            pageItemData2.maxHeight = (int) (this.windowInfo.mWindowWidth * f);
        }
        pageItemData2.isAdd = true;
        if (pageItemData.sizeUnit != 0 || pageItemData.isFixSize) {
            pageItemData2.isFixSize = true;
            pageItemData2.fixWidth = pageItemData.fixWidth;
            pageItemData2.useNewSize = true;
        }
        this.mList.add(pageItemData2);
        pageItemData2.setPageIndex(this.mList.size() - 1);
        onPageChanged();
        addSystemPage(pageItemData2, false);
        removePageChangeStep(true);
        PageChangeListener pageChangeListener = this.mPageChangeListener;
        if (pageChangeListener != null) {
            pageChangeListener.onPageAdd(getPageCount());
        }
    }

    public void removePageChangeStep(boolean z) {
        String str = TAG;
        Log.d(str, "removePageChangeStep PageCount " + getPageCount());
        if ((!z || getPageCount() == 2) && this.currentItemView != null) {
            this.currentItemView.getCreationEngine().getStepManager().removeChangePageSizeStep(z);
            Log.d(str, "removePageChangeStep clearStep");
        }
    }

    public void saveEntFile(final PageSaveListener pageSaveListener) {
        if (this.mPageBean != null && !TextUtils.isEmpty(this.mPageBean.path)) {
            final PageEntHelper pageEntHelper = new PageEntHelper();
            CreationPageSDK.sExecutorService.execute(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m853xc000ed81(pageEntHelper, pageSaveListener);
                }
            });
        } else if (pageSaveListener != null) {
            pageSaveListener.onDataSaved(false, null, false);
        }
    }

    /* JADX INFO: renamed from: lambda$saveEntFile$5$com-miui-libhandwritingpage-model-PageInkModel, reason: not valid java name */
    /* synthetic */ void m853xc000ed81(PageEntHelper pageEntHelper, PageSaveListener pageSaveListener) {
        updateContentEmpty();
        initEntPages();
        pageEntHelper.saveEnt(this.mList, this.currentItemView, this.mPageBean, pageSaveListener);
        onPreviewChanged();
    }

    public ShapeProp getSelectShapeProp() {
        return this.mSelectViewGroup.getShapeData();
    }

    public void setSelectShapeAttr(ShapeEditAttr shapeEditAttr, boolean z) {
        this.mSelectViewGroup.setSelectShapeAttr(shapeEditAttr, z);
        this.currentItemView.getCreationEngine().getCurveEngine().requestMultiBufferRender(MiCurveEngine.ERefreshReason.Force);
    }

    public void showSelectViewGroup() {
        if (this.isShowSelect) {
            boolean z = false;
            for (int i = 0; i < this.mPageInkView.getChildCount(); i++) {
                if (this.mPageInkView.getChildAt(i) instanceof SelectViewGroup) {
                    this.mPageInkView.bringChildToFront(this.mSelectViewGroup);
                    if (this.engineConfig.enableRuler) {
                        this.mPageInkView.bringChildToFront(this.mGlobalRulerView);
                    }
                    z = true;
                }
            }
            if (z) {
                return;
            }
            Log.d("MathSelect", "showSelectViewGroup: SelectViewGroup not exist，Add to SelectViewGroup");
            this.mPageInkView.addView(this.mSelectViewGroup);
            if (this.engineConfig.enableRuler) {
                this.mPageInkView.bringChildToFront(this.mGlobalRulerView);
            }
        }
    }

    private void bringRulerViewToFront() {
        if (this.engineConfig.enableRuler) {
            this.mPageInkView.bringChildToFront(this.mGlobalRulerView);
        }
    }

    public void setSelect(boolean z) {
        this.engineConfig.isAiModel = false;
        this.isAiModel = false;
        this.mSelectViewGroup.setAiState(false);
        this.engineConfig.enableSelect = z;
        if (this.isShowSelect != z) {
            this.isShowSelect = z;
            if (z) {
                showSelectViewGroup();
            }
            this.mSelectViewGroup.setInSelect(z);
        }
    }

    public void setSelect(boolean z, boolean z2) {
        this.engineConfig.isAiModel = z2;
        this.isAiModel = z2;
        this.mSelectViewGroup.setAiState(false);
        this.engineConfig.enableSelect = z;
        if (this.isShowSelect != z) {
            this.isShowSelect = z;
            if (z) {
                showSelectViewGroup();
            }
            this.mSelectViewGroup.setInSelect(z);
        }
    }

    public void setSelectListener(PageTextSelectListener pageTextSelectListener) {
        this.mSelectViewGroup.setSelectListener(pageTextSelectListener);
    }

    public void enableEstimate(boolean z, ParamsEstimate paramsEstimate) {
        this.engineConfig.enableEstimate = z;
        this.engineConfig.estimateParams.set(paramsEstimate);
        if (this.currentItemView != null) {
            this.currentItemView.enableEstimate(z, paramsEstimate);
        }
    }

    public RectF getItemRectF() {
        if (invalidPosition()) {
            return new RectF();
        }
        if (this.isUnlimited) {
            return this.currentItemView.getLayout();
        }
        return this.currentItemView.getCreationEngine().getCurveEngine().getCurPageWorldRect(this.currentPosition);
    }

    public RectF getVisibleRectF() {
        if (invalidPosition()) {
            return new RectF();
        }
        RectF rectF = new RectF(0.0f, 0.0f, this.currentItemView.getVisWidth(), this.currentItemView.getVisHeight());
        rectF.intersect(getItemRectF());
        return rectF;
    }

    private RectF getCurrentVisibleRectF() {
        return getItemVisibleRectF(0);
    }

    public RectF getItemVisibleRectF(int i) {
        if (i < 0 || this.currentItemView == null) {
            return new RectF();
        }
        RectF layout = this.currentItemView.getLayout();
        int visWidth = this.currentItemView.getVisWidth();
        int visHeight = this.currentItemView.getVisHeight();
        float fMax = Math.max(layout.left, 0.0f);
        float fMax2 = Math.max(layout.top, 0.0f);
        float f = visWidth;
        if (layout.right < f) {
            f = layout.right;
        }
        float f2 = visHeight;
        if (layout.bottom < f2) {
            f2 = layout.bottom;
        }
        return new RectF(fMax, fMax2, f, f2);
    }

    public void addBitmap(String str, RectF rectF) throws Throwable {
        if (invalidPosition()) {
            return;
        }
        addBitmap(str, rectF, this.currentPosition);
    }

    public void addBitmap(String str, RectF rectF, PointF pointF) throws Throwable {
        if (invalidPosition()) {
            return;
        }
        boolean zContains = getCurrentVisibleRectF().contains(pointF.x, pointF.y);
        if (pointF == null || !zContains) {
            addBitmap(str, rectF, this.currentPosition);
            return;
        }
        int[] iArr = new int[2];
        updateCurrentViewItem(pointF.x, pointF.y);
        String strCheckAndSaveScaleBitmap = checkAndSaveScaleBitmap(str, iArr);
        if (isOverBitmapLimit(iArr, this.currentItemView)) {
            return;
        }
        RectF layout = this.currentItemView.getLayout();
        rectF.offset((pointF.x - rectF.centerX()) + (layout.left > 0.0f ? -layout.left : 0.0f), (pointF.y - rectF.centerY()) + (layout.top > 0.0f ? -layout.top : 0.0f));
        this.currentItemView.addBitmap(strCheckAndSaveScaleBitmap, rectF, 0.0f);
        setSelectEdit(true);
    }

    private void addBitmap(String str, RectF rectF, int i) throws Throwable {
        float f;
        if (i == -1) {
            return;
        }
        int[] iArr = new int[2];
        String strCheckAndSaveScaleBitmap = checkAndSaveScaleBitmap(str, iArr);
        if (isOverBitmapLimit(iArr, this.currentItemView)) {
            return;
        }
        RectF itemRectF = getItemRectF();
        RectF itemVisibleRectF = getItemVisibleRectF(this.currentPosition);
        RectF rectF2 = new RectF(rectF);
        rectF2.offset(itemRectF.left, itemRectF.top);
        if (!itemVisibleRectF.contains(rectF2)) {
            if (rectF.top < 0.0f) {
                f = -rectF.top;
            } else {
                f = itemRectF.top > rectF.top ? rectF.top - itemRectF.top : 0.0f;
            }
            scrollBy(0, (int) f);
            updateCurrentViewItem(itemRectF.left + 1.0f, itemRectF.top + 1.0f);
            rectF.offset(0.0f, Math.max(0.0f, f));
            this.currentItemView.addBitmap(strCheckAndSaveScaleBitmap, rectF, 0.0f);
            setSelectEdit(true);
            return;
        }
        updateCurrentViewItem(itemRectF.left + 1.0f, itemRectF.top + 1.0f);
        this.currentItemView.addBitmap(strCheckAndSaveScaleBitmap, rectF, 0.0f);
        setSelectEdit(true);
    }

    public List<RectF> getVisibleItemRectFList() {
        ArrayList arrayList = new ArrayList();
        if (this.currentItemView != null) {
            arrayList.add(getCurrentVisibleRectF());
        }
        return arrayList;
    }

    public void addSelectRectBitmap(String str, RectF rectF, int i, boolean z, DrawErrorCallback drawErrorCallback) throws Throwable {
        if (i != -1) {
            int[] iArr = new int[2];
            String strCheckAndSaveScaleBitmap = checkAndSaveScaleBitmap(str, iArr);
            int i2 = iArr[0] * iArr[1] * 4;
            if (this.currentItemView == null || this.currentItemView.getMode() == null) {
                return;
            }
            if (this.currentItemView.isBitmapSizeMax(i2)) {
                if (drawErrorCallback != null) {
                    drawErrorCallback.onError(2);
                    return;
                }
                return;
            }
            RectF layout = this.currentItemView.getLayout();
            RectF rectF2 = new RectF(rectF);
            rectF2.offset(-layout.left, -layout.top);
            if (z) {
                this.currentItemView.addBitmap(strCheckAndSaveScaleBitmap, rectF2, 0.0f, true);
            } else {
                this.currentItemView.addBitmap(strCheckAndSaveScaleBitmap, rectF2, 0.0f);
            }
            setSelectEdit(true);
        }
    }

    private boolean isOverBitmapLimit(int[] iArr, PageItemView pageItemView) {
        int i = iArr[0] * iArr[1] * 4;
        if (pageItemView.getMode() == null || !pageItemView.isBitmapSizeMax(i)) {
            return false;
        }
        if (CreationPageSDK.sErrorCallback != null) {
            CreationPageSDK.sErrorCallback.onError(2);
        }
        return true;
    }

    public boolean hoverEvent(MotionEvent motionEvent) {
        float f = getItemVisibleRectF(0).top;
        if (!this.mCanWrite || this.currentItemView == null) {
            return false;
        }
        if (this.engineConfig.operatorMode == 2) {
            return this.currentItemView.hoverEvent(motionEvent, false, f);
        }
        return this.currentItemView.hoverEvent(motionEvent, true, f);
    }

    private String checkAndSaveScaleBitmap(String str, int[] iArr) throws Throwable {
        String strSubstring;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i = options.outWidth;
        int i2 = options.outHeight;
        int i3 = FeatureManager.getInstance().isSupportHighQuality() ? 4954829 : 2753026;
        if (i != 0 && i2 != 0) {
            iArr[0] = i;
            iArr[1] = i2;
            if (i * i2 > i3) {
                float f = i3 / (options.outWidth * options.outHeight);
                int i4 = (int) (i * f);
                int i5 = (int) (i2 * f);
                iArr[0] = i4;
                iArr[1] = i5;
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_4444);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                options.inJustDecodeBounds = false;
                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
                canvas.drawBitmap(bitmapDecodeFile, (Rect) null, new Rect(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight()), this.bitmapPaint);
                bitmapDecodeFile.recycle();
                canvas.setBitmap(null);
                String str2 = this.mPageBean.path;
                int iLastIndexOf = str.lastIndexOf(".");
                if (iLastIndexOf == -1) {
                    strSubstring = "";
                } else {
                    strSubstring = str.substring(iLastIndexOf);
                }
                String str3 = UUID.randomUUID().toString() + strSubstring;
                File file = new File(str2);
                String absolutePath = new File(file, str3).getAbsolutePath();
                BitmapUtil.saveBitmapToFile(bitmapCreateBitmap, new File(file, str3), true);
                return absolutePath;
            }
        }
        return str;
    }

    public RectF getTextLocation(int i, int i2) {
        RectF itemRectF = getItemRectF();
        RectF visibleRectF = getVisibleRectF();
        float fWidth = visibleRectF.left;
        float fHeight = visibleRectF.top;
        float f = i;
        if (visibleRectF.width() >= f) {
            fWidth = ((visibleRectF.width() - f) / 2.0f) + visibleRectF.left;
        } else if (itemRectF.width() > f && itemRectF.left < 0.0f && f > itemRectF.right) {
            fWidth = itemRectF.right - f;
        }
        float f2 = i2;
        if (visibleRectF.height() >= f2) {
            fHeight = ((visibleRectF.height() - f2) / 2.0f) + visibleRectF.top;
        } else if (itemRectF.height() > f2 && itemRectF.top < 0.0f && f2 > itemRectF.bottom) {
            fHeight = itemRectF.bottom - f2;
        }
        return new RectF(fWidth, fHeight, f + fWidth, f2 + fHeight);
    }

    public void addCopyText(String str, float f, float f2) {
        RectF textRect = getTextRect();
        RectF layout = this.currentItemView.getLayout();
        textRect.offset((f - textRect.centerX()) + (layout.left > 0.0f ? -layout.left : 0.0f), (f2 - textRect.centerY()) + (layout.top > 0.0f ? -layout.top : 0.0f));
        this.currentItemView.addText(textRect, str);
    }

    private RectF getTextRect() {
        RectF itemRectF = getItemRectF();
        RectF visibleRectF = getVisibleRectF();
        int[] iArr = {this.mContextRef.get().getResources().getDimensionPixelSize(R.dimen.creation_copy_text_default_width), this.mContextRef.get().getResources().getDimensionPixelSize(R.dimen.creation_copy_text_default_height)};
        float fWidth = visibleRectF.left;
        float fHeight = visibleRectF.top;
        if (visibleRectF.width() >= iArr[0]) {
            fWidth = ((visibleRectF.width() - iArr[0]) / 2.0f) + visibleRectF.left;
        } else if (itemRectF.width() > iArr[0] && itemRectF.left < 0.0f && iArr[0] > itemRectF.right) {
            fWidth = itemRectF.right - iArr[0];
        }
        if (visibleRectF.height() >= iArr[1]) {
            fHeight = ((visibleRectF.height() - iArr[1]) / 2.0f) + visibleRectF.top;
        } else if (itemRectF.height() > iArr[1] && itemRectF.top < 0.0f && iArr[1] > itemRectF.bottom) {
            fHeight = itemRectF.bottom - iArr[1];
        }
        return new RectF(fWidth, fHeight, iArr[0] + fWidth, iArr[1] + fHeight);
    }

    public void addText(RectF rectF) {
        float f;
        if (invalidPosition()) {
            return;
        }
        float scale = this.layoutManager.getScale();
        RectF textLocation = getTextLocation((int) (rectF.width() * scale), (int) (rectF.height() * scale));
        if (this.currentItemView.getMode() != null && this.currentItemView.isTextSizeMax(1)) {
            if (CreationPageSDK.sErrorCallback != null) {
                CreationPageSDK.sErrorCallback.onError(3);
                return;
            }
            return;
        }
        RectF itemRectF = getItemRectF();
        RectF itemVisibleRectF = getItemVisibleRectF(this.currentPosition);
        RectF rectF2 = new RectF(textLocation);
        rectF2.offset(itemRectF.left, itemRectF.top);
        if (!itemVisibleRectF.contains(rectF2)) {
            if (textLocation.top < 0.0f) {
                f = -textLocation.top;
            } else {
                f = itemRectF.top > textLocation.top ? textLocation.top - itemRectF.top : 0.0f;
            }
            scrollBy(0, (int) f);
            updateCurrentViewItem(itemRectF.left + 1.0f, itemRectF.top + 1.0f);
            textLocation.offset(0.0f, Math.max(0.0f, f));
            if (this.currentItemView.getMode() != null) {
                this.currentItemView.getMode().setTextBgColor(0);
            }
            this.currentItemView.addText(new RectF(textLocation), null);
            return;
        }
        updateCurrentViewItem(itemRectF.left + 1.0f, itemRectF.top + 1.0f);
        if (this.currentItemView.getMode() != null) {
            this.currentItemView.getMode().setTextBgColor(0);
        }
        this.currentItemView.addText(new RectF(textLocation), null);
    }

    public void addText(TextInfo textInfo, boolean z) {
        if (invalidPosition()) {
            return;
        }
        float f = this.windowInfo.mWindowWidth / 1000.0f;
        RectF rectF = new RectF(textInfo.getRectF().left * f, textInfo.getRectF().top * f, textInfo.getRectF().right * f, textInfo.getRectF().bottom * f);
        if (this.currentItemView.getMode() != null && this.currentItemView.isTextSizeMax(1)) {
            if (CreationPageSDK.sErrorCallback != null) {
                CreationPageSDK.sErrorCallback.onError(3);
                return;
            }
            return;
        }
        if (this.currentItemView.getMode() != null) {
            this.currentItemView.getMode().setTextBgColor(0);
        }
        this.currentItemView.updateSpanScaleInfo();
        SpannedScaleInfo scaleInfo = this.currentItemView.getMode().getScaleInfo();
        textInfo.setRectF(MathUtils.inverseRectF(rectF, scaleInfo.offsetX, scaleInfo.offsetY, scaleInfo.scale));
        if (this.adapter.getStepHelper() != null) {
            this.adapter.getStepHelper().setStepCount(z ? 2 : 0);
        }
        this.currentItemView.addText(textInfo);
        if (z) {
            this.currentItemView.deleteCurve();
        } else {
            this.currentItemView.cancelSelect(false);
        }
    }

    public void scrollOnKeyBoardHeightChanged(Activity activity, int i, boolean z) {
        if (this.currentItemView == null || this.mSelectViewGroup.mSpannedEditMode == null) {
            return;
        }
        WeakReference weakReference = new WeakReference(activity);
        if (weakReference.get() == null) {
            return;
        }
        if (UIUtils.isInMultiWindowMode((Activity) weakReference.get())) {
            if (UIUtils.isSmallWindowMode((Context) weakReference.get())) {
                this.fenestrule = true;
                if (z && this.totalScrollDy == 0) {
                    float fMin = Math.min(this.currentItemView.getCursorHeight() + Math.max(this.currentItemView.getLayout().top, 0.0f), getItemRectF().bottom);
                    this.mCursorHeight = fMin;
                    this.totalScrollDy = (int) Math.ceil((fMin + i) - UIUtils.getAppHeight(((Activity) weakReference.get()).getWindow()));
                    scrollByKeyboard(this.fenestraRunnable, 300L);
                    return;
                }
                if (z || this.mKeyBoardState != 2 || this.totalScrollDy <= 0) {
                    return;
                }
                this.mKeyBoardState = 3;
                this.mCursorHeight = 0.0f;
                scrollByKeyboard(this.fenestraRunnable, 300L);
                return;
            }
            this.fenestrule = false;
            if (this.mKeyBoardState == 1) {
                this.mCursorHeight = this.currentItemView.getCursorHeight() + Math.max(this.currentItemView.getLayout().top, 0.0f);
            }
            float f = i;
            int iMin = (int) Math.min((this.mCursorHeight + f) - this.mVisibleHeight, f);
            if (iMin != 0 && iMin >= this.totalScrollDy && this.mKeyBoardState == 1) {
                this.totalScrollDy = iMin;
                scrollByKeyboard(this.fullScreenRunnable, 20L);
            } else {
                int i2 = this.totalScrollDy;
                if (iMin >= i2 || i2 == 0 || this.mKeyBoardState != 2) {
                    return;
                }
                this.mKeyBoardState = 3;
                this.mCursorHeight = 0.0f;
                scrollByKeyboard(this.fullScreenRunnable, 20L);
            }
        }
        this.fenestrule = false;
        if (this.mKeyBoardState == 1 && i == 0) {
            this.mCursorHeight = this.currentItemView.getCursorHeight() + Math.max(this.currentItemView.getLayout().top, 0.0f);
        }
        int i3 = (int) ((this.mCursorHeight + i) - this.mVisibleHeight);
        if (i3 != 0 && i3 >= this.totalScrollDy && this.mKeyBoardState == 1) {
            this.totalScrollDy = i3;
            scrollByKeyboard(this.fullScreenRunnable, 300L);
            return;
        }
        int i4 = this.totalScrollDy;
        if (i3 >= i4 || i4 == 0 || this.mKeyBoardState != 2) {
            return;
        }
        this.mKeyBoardState = 3;
        this.mCursorHeight = 0.0f;
        scrollByKeyboard(this.fullScreenRunnable, 300L);
    }

    public void scrollByKeyboard(Runnable runnable, long j) {
        CreationPageSDK.with(this.mContextRef.get()).removeCallbacks(runnable);
        CreationPageSDK.with(this.mContextRef.get()).postDelayed(runnable, j);
    }

    /* JADX WARN: Code duplicated, block: B:49:0x013c  */
    public void setPageBackground(Map<Integer, PagePreviewData> map) {
        boolean z;
        boolean z2;
        if (map != null) {
            boolean z3 = false;
            for (Integer num : map.keySet()) {
                if (num.intValue() < this.mList.size()) {
                    PageItemData pageItemData = this.mList.get(num.intValue());
                    PagePreviewData pagePreviewData = map.get(num);
                    if (pagePreviewData != null) {
                        if (pageItemData.bgColor != pagePreviewData.bgColor) {
                            pageItemData.bgColor = pagePreviewData.bgColor;
                            PreviewListener previewListener = this.previewListener;
                            if (previewListener != null) {
                                previewListener.onBgColorItemUpdated(num.intValue(), pageItemData.bgColor);
                            }
                            if (getDrawView().getCreationEngine().getCurveEngine() != null) {
                                getDrawView().getCreationEngine().getCurveEngine().getHighLightSystem().shutdownHighlight();
                            }
                            setPageBGColor(num.intValue(), pageItemData.bgColor);
                            reDrawSpan(this.currentItemView);
                            this.mSelectViewGroup.cancelSelect(false);
                        }
                        if (pageItemData.bgGrid != pagePreviewData.bgGrid || pageItemData.bgConfig != pagePreviewData.bgConfig || pageItemData.bgImageMode != pagePreviewData.bgImageMode) {
                            pageItemData.bgGrid = pagePreviewData.bgGrid;
                            pageItemData.bgConfig = pagePreviewData.bgConfig;
                            pageItemData.bgImageMode = pagePreviewData.bgImageMode;
                            pageItemData.isBgChanged = true;
                            setPageBGStyle(num.intValue(), pageItemData.bgGrid);
                            this.mSelectViewGroup.cancelSelect(false);
                        }
                        if (pagePreviewData.bgImage.isEmpty()) {
                            z = false;
                        } else {
                            String bgFileName = pagePreviewData.bgImage;
                            if (new File(bgFileName).exists()) {
                                bgFileName = FileUtil.getBgFileName(bgFileName);
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (Objects.equals(pageItemData.bgImage, bgFileName)) {
                                z = false;
                            } else {
                                pageItemData.bgImage = bgFileName;
                                File file = new File(this.mPageBean.path, pageItemData.bgImage);
                                if (!file.exists() && z2) {
                                    FileUtil.copyFile(new File(pagePreviewData.bgImage), new File(file.getAbsolutePath()));
                                    Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    if (bitmapDecodeFile != null && bitmapDecodeFile.getWidth() > 0 && bitmapDecodeFile.getHeight() > 0) {
                                        pageItemData.width = Math.max(bitmapDecodeFile.getWidth(), pageItemData.width);
                                        pageItemData.height = Math.max(bitmapDecodeFile.getHeight(), pageItemData.height);
                                        pageItemData.orientation = 0;
                                        z3 = true;
                                    }
                                    if (bitmapDecodeFile != null && !bitmapDecodeFile.isRecycled()) {
                                        bitmapDecodeFile.recycle();
                                    }
                                }
                                z = true;
                            }
                        }
                        if (z) {
                            pageItemData.isBgChanged = true;
                            startBitmapTask(pageItemData, num.intValue());
                        } else if (this.currentItemView != null) {
                            this.currentItemView.updateBackground(this.mPageBean.path, pageItemData);
                        }
                    }
                }
            }
            if (z3) {
                onPageChanged();
            }
        }
    }

    private void reDrawSpan(PageItemView pageItemView) {
        if (pageItemView != null) {
            pageItemView.getCreationEngine().getSelectOperator().forceRedraw();
            pageItemView.getCreationEngine().getCanvasOperator().forceRedraw();
            pageItemView.getMode().getSpannedSelectMode().forceRedraw();
            pageItemView.getMode().forceReDraw();
        }
    }

    public SelectCopyBean getSelectCopyData() {
        return this.mSelectViewGroup.getCopyData();
    }

    public void setPageCopyData(SelectCopyBean selectCopyBean) {
        this.mSelectViewGroup.setCopyData(selectCopyBean);
    }

    public List<PageData> getPageDatas() {
        List<SpannedData> dataList;
        List<PageItemData> itemDataList = getItemDataList();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < itemDataList.size(); i++) {
            PageItemData pageItemData = itemDataList.get(i);
            PageData pageData = new PageData();
            pageData.bgImagePathList = new ArrayList();
            pageData.bitmapPathList = new ArrayList();
            pageData.bgImage = pageItemData.bgImage;
            pageData.inkPath = pageItemData.filePath;
            if (!TextUtils.isEmpty(pageItemData.pdfId)) {
                pageData.pdfPath = getDirPath() + File.separator + pageItemData.pdfId;
            }
            if (!TextUtils.isEmpty(pageItemData.bgImage)) {
                pageData.bgImagePathList.add(getDirPath() + File.separator + pageData.bgImage);
            }
            if (pageItemData.spannedData != null) {
                dataList = pageItemData.spannedData.getDataList(7);
            } else {
                dataList = this.currentItemView != null ? this.currentItemView.getDataList(0, 7) : null;
            }
            if (dataList != null) {
                for (SpannedData spannedData : dataList) {
                    if (!TextUtils.isEmpty(spannedData.getBitmapPath())) {
                        pageData.bitmapPathList.add(getDirPath() + File.separator + new File(spannedData.getBitmapPath()).getName());
                    }
                }
            } else {
                new PageEntUtil().loadEnt(getDirPath(), pageData, i);
            }
            arrayList.add(pageData);
        }
        return arrayList;
    }

    public List<String> getImagePaths() {
        final ArrayList arrayList = new ArrayList();
        getPageDatas().forEach(new Consumer() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PageInkModel.lambda$getImagePaths$6(arrayList, (PageData) obj);
            }
        });
        return arrayList;
    }

    static /* synthetic */ void lambda$getImagePaths$6(ArrayList arrayList, PageData pageData) {
        if (pageData.bitmapPathList != null) {
            arrayList.addAll(pageData.bitmapPathList);
        }
    }

    public List<String> getAllSpanBitmapPaths() {
        List<SpannedData> allDataList;
        final ArrayList arrayList = new ArrayList();
        if (getDrawView() != null && getDrawView().getCreationEngine() != null && getDrawView().getCreationEngine().getCurveEngine() != null && getDrawView().getCreationEngine().getCurveEngine().getSpannedLayoutModel() != null && (allDataList = getDrawView().getCreationEngine().getCurveEngine().getSpannedLayoutModel().getEngineBindContext().getAllDataList(7)) != null && !allDataList.isEmpty()) {
            allDataList.forEach(new Consumer() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add(((SpannedData) obj).mBitmapPath);
                }
            });
        }
        return arrayList;
    }

    public List<String> getDeleteSpanBitmapPaths() {
        if (getDrawView() != null && getDrawView().getCreationEngine() != null && getDrawView().getCreationEngine().getCurveEngine() != null && getDrawView().getCreationEngine().getCurveEngine().getSpannedLayoutModel() != null) {
            return getDrawView().getCreationEngine().getCurveEngine().getSpannedLayoutModel().getEngineBindContext().getDeleteImages();
        }
        return Collections.emptyList();
    }

    public void startBitmapTask(final PageItemData pageItemData, int i) {
        CreationPageSDK.sExecutorService.submit(new FutureTask<Integer>(new BitmapTask(pageItemData, i)) { // from class: com.miui.libhandwritingpage.model.PageInkModel.6
            @Override // java.util.concurrent.FutureTask
            protected void done() {
                if (PageInkModel.this.currentItemView != null) {
                    PageInkModel.this.currentItemView.updateBackground(PageInkModel.this.mPageBean.path, pageItemData);
                }
            }
        });
    }

    public class BitmapTask implements Callable<Integer> {
        private final PageItemData itemData;
        private final int position;

        public BitmapTask(PageItemData pageItemData, int i) {
            this.itemData = pageItemData;
            this.position = i;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public Integer call() {
            if (!Objects.equals(this.itemData.bgImage, "") && new File(PageInkModel.this.mPageBean.path, this.itemData.bgImage).exists()) {
                Bitmap optimizedBitmap = BitmapUtil.getOptimizedBitmap(PageInkModel.this.mPageBean.path, this.itemData);
                if (optimizedBitmap != null) {
                    LruCacheUtil.addBitmapToMemoryCache(this.itemData.bgImage, optimizedBitmap);
                }
                return Integer.valueOf(this.position);
            }
            LruCacheUtil.removeBitmapFromMemCache(this.itemData.bgImage);
            return Integer.valueOf(this.position);
        }
    }

    public void setOneShape(boolean z, ParamsRecognizeShape paramsRecognizeShape) {
        this.engineConfig.oneShape = z;
        if (paramsRecognizeShape != null) {
            this.engineConfig.oneShapeParams = paramsRecognizeShape;
        }
        if (this.currentItemView != null) {
            this.currentItemView.setOneShape(z, paramsRecognizeShape);
        }
    }

    public void setDoubleFingerClickUndo(boolean z) {
        this.isDoubleFingerClickUndo = z;
    }

    private void parsePdfFile(String str, String str2, final PageLoadListener pageLoadListener) {
        new ImportPDFTask(this.mPageBean.path, str, Math.min(500, 500 - this.mList.size()), str2, new PDFImportListener() { // from class: com.miui.libhandwritingpage.model.PageInkModel.7
            @Override // com.miui.libhandwritingpage.listener.PDFImportListener
            public void onImportCompleted(List<PageItemData> list) {
                boolean z = false;
                if (list != null && list.size() > 0) {
                    PageInkModel.this.mPageBean.width = list.get(0).width;
                    PageInkModel.this.mPageBean.height = list.get(0).height;
                }
                for (PageItemData pageItemData : list) {
                    pageItemData.isFixSize = true;
                    pageItemData.fixWidth = UIUtils.getRealWidth(PageInkModel.this.mContextRef.get());
                }
                PageInkModel.this.addItemList(list);
                PageInkModel.this.initEntPages();
                if (!list.isEmpty() && PageInkModel.this.getDrawView() != null && PageInkModel.this.getDrawView().getCreationEngine() != null && PageInkModel.this.getDrawView().getCreationEngine().getCurveEngine() != null) {
                    PageInkModel.this.addSystemPages();
                    PageInkModel.this.getDrawView().getCreationEngine().getCurveEngine().getPageSystem().setPdfMode(true);
                    PageInkModel.this.mIsFirstjump = false;
                    PageInkModel.this.loadPdfBitmap(list);
                }
                PageLoadListener pageLoadListener2 = pageLoadListener;
                if (pageLoadListener2 != null) {
                    if (list != null && !list.isEmpty()) {
                        z = true;
                    }
                    pageLoadListener2.onLoadCompleted(z, !TextUtils.isEmpty(PageInkModel.this.mPageBean.pdfPath), PageInkModel.this.mEntFilePath);
                }
                if (PageInkModel.this.mStateListener != null) {
                    PageInkModel.this.mStateListener.onContentChanged(-1);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PDFImportListener
            public void onPdfPageCount(int i) {
                PageLoadListener pageLoadListener2 = pageLoadListener;
                if (pageLoadListener2 != null) {
                    pageLoadListener2.onPageCount(i);
                }
            }

            @Override // com.miui.libhandwritingpage.listener.PDFImportListener
            public void onPdfError(int i) {
                Log.d(PageInkModel.TAG, "onPdfError: type:" + i);
                PageLoadListener pageLoadListener2 = pageLoadListener;
                if (pageLoadListener2 != null) {
                    pageLoadListener2.onPdfError(i);
                }
            }
        }).execute(new Void[0]);
    }

    public void addItemList(List<PageItemData> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.mList.addAll(list);
        onPageChanged();
    }

    private void cancelPageSelect(int i) {
        if (i < 0 || this.mList.size() == 0 || i >= this.mList.size()) {
            return;
        }
        this.mSelectViewGroup.cancelSelect(false);
    }

    public void setPreviewListener(PreviewListener previewListener) {
        this.previewListener = previewListener;
    }

    public void jumpPage(int i, boolean z) {
        Log.d(TAG, "jumpPage: position:" + i);
        if (this.mIsFirstjump) {
            this.mFirstJumpPosition = i;
        }
        this.layoutManager.jumpTo(i, z);
    }

    public void resetScale(Boolean bool, Float f) {
        getMiCurveEngine().getCamera().resetScale(bool.booleanValue(), f.floatValue());
    }

    public void resetCurveCenter() {
        getMiCurveEngine().getCamera().resetDataCenter();
    }

    public boolean isContentChanged() {
        if (this.pageChange || this.adapter.canUndo() || this.adapter.canRedo()) {
            return true;
        }
        for (int i = 0; i < this.mList.size(); i++) {
            PageItemData pageItemData = this.mList.get(i);
            if (pageItemData.isBgChanged || pageItemData.isModified) {
                return true;
            }
        }
        return false;
    }

    public boolean isContentEmpty() {
        boolean zIsContentEmpty = true;
        for (int i = 0; i < this.mList.size(); i++) {
            zIsContentEmpty = isContentEmpty(i);
            if (!zIsContentEmpty) {
                return false;
            }
        }
        return zIsContentEmpty;
    }

    public boolean isContentEmpty(int i) {
        if (i >= this.mList.size()) {
            return true;
        }
        PageItemData pageItemData = this.mList.get(i);
        if (TextUtils.isEmpty(pageItemData.pdfId) && TextUtils.isEmpty(pageItemData.bgImage)) {
            if (this.currentItemView != null) {
                return this.currentItemView.isContentEmpty(i);
            }
            if (pageItemData.isEngineDataEmpty()) {
                return false;
            }
            ItemSpannedData itemSpannedData = pageItemData.spannedData;
            if ((itemSpannedData == null || itemSpannedData.getSpannedInfo() == null || itemSpannedData.getSpannedInfo().isContentEmpty()) && pageItemData.isContentEmpty) {
                return true;
            }
        }
        return false;
    }

    private void updateContentEmpty() {
        for (int i = 0; i < this.mList.size(); i++) {
            PageItemData pageItemData = this.mList.get(i);
            if (!TextUtils.isEmpty(pageItemData.pdfId) || !TextUtils.isEmpty(pageItemData.bgImage)) {
                pageItemData.isContentEmpty = false;
            } else if (this.currentItemView != null) {
                if (!this.currentItemView.isContentEmpty(i)) {
                    pageItemData.isContentEmpty = false;
                }
            } else if (pageItemData.isEngineDataEmpty()) {
                pageItemData.isContentEmpty = false;
            } else {
                ItemSpannedData itemSpannedData = pageItemData.spannedData;
                if (itemSpannedData != null && itemSpannedData.getSpannedInfo() != null && !itemSpannedData.getSpannedInfo().isContentEmpty()) {
                    pageItemData.isContentEmpty = false;
                }
            }
        }
    }

    public List<CurveBean> getSelectedDataList() {
        List<ICurve> selectCurves;
        if (this.currentItemView == null || (selectCurves = this.currentItemView.getSelectCurves()) == null || selectCurves.size() == 0) {
            return null;
        }
        return PageEntHelper.getCurveDataList(selectCurves);
    }

    public List<StrokeClassificationCurve> getSelectedStrokeClassificationCurves() {
        PageItemView pageItemView;
        List<ICurve> selectCurves;
        if (this.currentItemView == null || (selectCurves = (pageItemView = this.currentItemView).getSelectCurves()) == null || selectCurves.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(PageEntHelper.getClassificationCurves(selectCurves, true));
        int iMax = Integer.MIN_VALUE;
        int iMin = Integer.MAX_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        int iMax2 = Integer.MIN_VALUE;
        for (ICurve iCurve : selectCurves) {
            if (iCurve instanceof DataCurve) {
                DataCurve dataCurve = (DataCurve) iCurve;
                iMin = Math.min(iMin, (int) dataCurve.mRect.left);
                iMax = Math.max(iMax, (int) dataCurve.mRect.right);
                iMin2 = Math.min(iMin2, (int) dataCurve.mRect.top);
                iMax2 = Math.max(iMax2, (int) dataCurve.mRect.bottom);
            }
        }
        Rect rect = new Rect(iMin, iMin2, iMax, iMax2);
        if (!rect.isEmpty()) {
            rect.left -= (int) (((double) rect.width()) * 0.25d);
            rect.right += (int) (((double) rect.width()) * 0.25d);
            rect.top -= (int) (((double) rect.height()) * 0.25d);
            rect.bottom += (int) (((double) rect.height()) * 0.25d);
            List<ICurve> curveData = pageItemView.getCreationEngine().getDataAccess().getCurveData(rect);
            Log.i(TAG, "getSelectedStrokeClassificationCurves: " + selectCurves.size() + StringUtils.SPACE + curveData.size() + StringUtils.SPACE);
            arrayList.addAll(PageEntHelper.getClassificationCurves(curveData, false));
        }
        arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda14
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((StrokeClassificationCurve) obj).index;
            }
        }));
        return arrayList;
    }

    public List<CurveBean> getAllSelectedDataList() {
        List<ICurve> allSelectCurves;
        if (this.currentItemView == null || (allSelectCurves = this.currentItemView.getAllSelectCurves()) == null || allSelectCurves.size() == 0) {
            return null;
        }
        return PageEntHelper.getCurveDataList(allSelectCurves);
    }

    public List<SpannedData> getSelectedSpanList() {
        if (this.currentItemView == null) {
            return null;
        }
        return this.currentItemView.getSelectSpanList();
    }

    public void copyTempSelectData() {
        if (this.currentItemView == null) {
            return;
        }
        this.currentItemView.copyTempSelectData();
    }

    public void setSearchResult(int i, int i2, int i3, SearchWrapper searchWrapper) {
        int iCenterX;
        int iCenterX2;
        if (searchWrapper.searchRectMap != null) {
            this.searchWrapper.searchRectMap = new HashMap(searchWrapper.searchRectMap);
        }
        this.searchWrapper.searchPageIndex = i;
        this.searchWrapper.searchColor = i2;
        this.searchWrapper.searchAlpha = i3;
        this.searchWrapper.searchText = searchWrapper.searchText;
        this.searchWrapper.searchSpannedRect = searchWrapper.searchSpannedRect;
        this.searchWrapper.searchSpannedRectContent = searchWrapper.searchSpannedRectContent;
        this.searchWrapper.searchSpannedTextSize = searchWrapper.searchSpannedTextSize;
        if (!this.mList.isEmpty()) {
            if (i != this.currentPosition || this.currentItemView == null) {
                return;
            }
            RectF layout = this.currentItemView.getLayout();
            if (this.searchTop == 0) {
                if (searchWrapper.searchSpannedRect != null && !"".equals(this.searchWrapper.searchText)) {
                    RectF rectF = new RectF(searchWrapper.searchSpannedRect);
                    if (!rectF.isEmpty()) {
                        if (this.currentItemView.isUnLimitToNormal) {
                            float fMin = Math.min(layout.width() - (this.currentItemView.getShieldWidth() * 2), Math.min(this.currentItemView.getVisWidth(), this.currentItemView.getVisHeight()));
                            Matrix matrix = new Matrix();
                            float f = fMin / 1000.0f;
                            matrix.setScale(f, f);
                            matrix.mapRect(rectF);
                            if (layout.width() - (this.currentItemView.getShieldWidth() * 2) > this.currentItemView.getVisWidth()) {
                                float fWidth = layout.width() / 2.0f;
                                iCenterX2 = (int) (fWidth - rectF.centerX());
                                float fAbs = Math.abs((fWidth - this.currentItemView.getShieldWidth()) - (this.currentItemView.getVisWidth() / 2.0f));
                                if (Math.abs(iCenterX2) > fAbs) {
                                    iCenterX2 = (int) ((iCenterX2 / Math.abs(iCenterX2)) * fAbs);
                                }
                            } else {
                                iCenterX2 = 0;
                            }
                            int iHeight = (int) (layout.top + rectF.top);
                            StaticLayout.Builder builderObtainBuilder = new TextLayoutUtil().obtainBuilder(searchWrapper.searchSpannedRectContent, searchWrapper.searchSpannedRect, searchWrapper.searchSpannedTextSize);
                            if (builderObtainBuilder != null) {
                                StaticLayout staticLayoutBuild = builderObtainBuilder.build();
                                List<RectF> listGenSearchTextPath = TextLayoutUtil.genSearchTextPath(staticLayoutBuild, searchWrapper.searchText);
                                if (listGenSearchTextPath.size() > 0) {
                                    iHeight = (int) (iHeight + ((listGenSearchTextPath.get(0).top * rectF.height()) / staticLayoutBuild.getHeight()));
                                }
                            }
                            if (iHeight > 0) {
                                this.isSearchScrollBy = false;
                                scrollBy(iCenterX2, -iHeight);
                                return;
                            }
                            return;
                        }
                        Matrix matrix2 = new Matrix();
                        matrix2.setScale(layout.width() / 1000.0f, layout.width() / 1000.0f);
                        matrix2.mapRect(rectF);
                        int iHeight2 = (int) (layout.top + rectF.top);
                        StaticLayout.Builder builderObtainBuilder2 = new TextLayoutUtil().obtainBuilder(searchWrapper.searchSpannedRectContent, searchWrapper.searchSpannedRect, searchWrapper.searchSpannedTextSize);
                        if (builderObtainBuilder2 != null) {
                            StaticLayout staticLayoutBuild2 = builderObtainBuilder2.build();
                            List<RectF> listGenSearchTextPath2 = TextLayoutUtil.genSearchTextPath(staticLayoutBuild2, searchWrapper.searchText);
                            if (listGenSearchTextPath2.size() > 0) {
                                iHeight2 = (int) (iHeight2 + ((listGenSearchTextPath2.get(0).top * rectF.height()) / staticLayoutBuild2.getHeight()));
                            }
                        }
                        if (!this.currentItemView.isUnLimit()) {
                            if (iHeight2 > 0) {
                                this.isSearchScrollBy = false;
                                scrollBy(0, -iHeight2);
                                return;
                            }
                            return;
                        }
                        int visWidth = this.currentItemView.getVisWidth();
                        int visHeight = this.currentItemView.getVisHeight();
                        int iCenterX3 = (int) ((layout.left + rectF.centerX()) - (visWidth / 2.0f));
                        int iCenterY = (int) ((layout.top + rectF.centerY()) - (visHeight / 2.0f));
                        this.isSearchScrollBy = false;
                        scrollBy(-iCenterX3, -iCenterY);
                        return;
                    }
                    this.isSearchScrollBy = false;
                    return;
                }
                if (this.searchWrapper.searchRectMap == null || this.searchWrapper.searchRectMap.isEmpty()) {
                    return;
                }
                int i4 = -1;
                int i5 = -1;
                for (String str : this.searchWrapper.searchRectMap.keySet()) {
                    int i6 = Integer.parseInt(str.split("-")[0]);
                    int i7 = Integer.parseInt(str.split("-")[1]);
                    if (i4 == -1 || i4 > i6) {
                        i5 = i7;
                        i4 = i6;
                    } else if (i4 == i6 && i5 > i7) {
                        i5 = i7;
                    }
                }
                RectF rectF2 = new RectF(this.searchWrapper.searchRectMap.get("" + i4 + "-" + i5));
                if (!rectF2.isEmpty()) {
                    int visHeight2 = this.currentItemView.getVisHeight();
                    if (this.currentItemView.isUnLimitToNormal) {
                        float fMin2 = Math.min(layout.width() - (this.currentItemView.getShieldWidth() * 2), Math.min(this.currentItemView.getVisWidth(), this.currentItemView.getVisHeight()));
                        Matrix matrix3 = new Matrix();
                        float f2 = fMin2 / 1000.0f;
                        matrix3.setScale(f2, f2);
                        matrix3.mapRect(rectF2);
                        if (layout.width() - (this.currentItemView.getShieldWidth() * 2) > this.currentItemView.getVisWidth()) {
                            float fWidth2 = layout.width() / 2.0f;
                            iCenterX = (int) (fWidth2 - rectF2.centerX());
                            float fAbs2 = Math.abs((fWidth2 - this.currentItemView.getShieldWidth()) - (this.currentItemView.getVisWidth() / 2.0f));
                            if (Math.abs(iCenterX) > fAbs2) {
                                iCenterX = (int) ((iCenterX / Math.abs(iCenterX)) * fAbs2);
                            }
                        } else {
                            iCenterX = 0;
                        }
                        int iMax = (int) Math.max(0.0f, rectF2.centerY() - (visHeight2 / 2.0f));
                        this.searchTop = iMax;
                        int i8 = (int) (iMax + layout.top);
                        if (i8 > 0) {
                            this.isSearchScrollBy = false;
                            scrollBy(iCenterX, -i8);
                            return;
                        }
                        return;
                    }
                    if (this.currentItemView.isUnLimit()) {
                        Matrix matrix4 = new Matrix();
                        matrix4.setScale(layout.width() / 1000.0f, layout.width() / 1000.0f);
                        matrix4.mapRect(rectF2);
                        int iCenterY2 = (int) (rectF2.centerY() - (visHeight2 / 2.0f));
                        this.searchTop = iCenterY2;
                        int i9 = (int) (iCenterY2 + layout.top);
                        int iCenterX4 = (int) (rectF2.centerX() - (this.currentItemView.getVisWidth() / 2.0f));
                        this.isSearchScrollBy = false;
                        scrollBy(-iCenterX4, -i9);
                        return;
                    }
                    Matrix matrix5 = new Matrix();
                    matrix5.setScale(layout.width() / 1000.0f, layout.width() / 1000.0f);
                    matrix5.mapRect(rectF2);
                    float f3 = visHeight2 / 2.0f;
                    if (layout.bottom - rectF2.centerY() < f3) {
                        this.searchTop = (int) (rectF2.centerY() - f3);
                    } else {
                        this.searchTop = (int) Math.max(0.0f, rectF2.centerY() - f3);
                    }
                    int i10 = (int) (this.searchTop + layout.top);
                    if (i10 > 0) {
                        this.isSearchScrollBy = false;
                        scrollBy(0, -i10);
                        return;
                    }
                    return;
                }
                this.isSearchScrollBy = false;
                return;
            }
            return;
        }
        jumpPage(i, false);
    }

    public List<TouchPosition> getTouchPositionList() {
        return this.mSelectViewGroup.getTouchPositionList();
    }

    public List<SpannedData> getAllSpanByRectF(RectF rectF) {
        return this.mSelectViewGroup.getAllSpanByRectF(rectF);
    }

    public boolean selectByRect(RectF rectF, List<DataCurve> list, boolean z) {
        if (rectF == null || rectF.isEmpty() || this.currentItemView == null) {
            return false;
        }
        if (!this.isShowSelect) {
            setSelect(true, z);
        }
        showSelectViewGroup();
        if (this.currentItemView != null) {
            this.currentItemView.setOperatedType(1);
        }
        RectF rectF2 = new RectF(rectF);
        this.mSelectViewGroup.toOriginRectF(rectF2);
        Log.d("MathSelect", "selectByRect: pageRect toOriginRectF=" + rectF2);
        if (this.currentItemView == null) {
            Log.e("MathSelect", "selectByRect: currentItemView null");
            return false;
        }
        if (this.currentItemView.getCreationEngine() == null) {
            Log.e("MathSelect", "selectByRect: CreationEngine null");
            return false;
        }
        if (this.currentItemView.getCreationEngine().getSelectOperator() == null) {
            Log.e(TAG, "selectByRect: SelectOperator null");
            return false;
        }
        try {
            getMiCurveEngine().getMathDataManager().setSelectState(1);
            directSelectByRect(rectF2, list);
        } catch (Exception e) {
            Log.e(TAG, "selectByRect: Fail", e);
        }
        this.mSelectViewGroup.post(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel.8
            @Override // java.lang.Runnable
            public void run() {
                if (PageInkModel.this.mSelectViewGroup == null || PageInkModel.this.mSelectViewGroup.getSelectView() == null) {
                    return;
                }
                if (PageInkModel.this.mSelectViewGroup.getSelectView().isSelectRectNotEmpty()) {
                    PageInkModel.this.mSelectViewGroup.setButtonVisible(true);
                } else {
                    Log.w(PageInkModel.TAG, "selectByRect: Fail");
                }
            }
        });
        return true;
    }

    public boolean selectByRect(RectF rectF, boolean z) {
        return selectByRect(rectF, new ArrayList(), z);
    }

    public boolean selectByRect(RectF rectF) {
        return selectByRect(rectF, false);
    }

    public void toDrawPointF(PointF pointF) {
        this.mSelectViewGroup.toDrawPointF(pointF);
    }

    public void toDrawReactF(RectF rectF) {
        this.mSelectViewGroup.toDrawReactF(rectF);
    }

    public RectF toOriginRectF(RectF rectF) {
        return this.mSelectViewGroup.toOriginRectF(rectF);
    }

    public void toOriginPointFIgnoreScale(PointF pointF) {
        this.mSelectViewGroup.toOriginPointFIgnoreScale(pointF);
    }

    public Bitmap getSelectScreenshot(boolean z) {
        return this.mSelectViewGroup.getBitmap(z);
    }

    public Bitmap getSelectScreenshotWithoutSpanRotate(boolean z) {
        return this.mSelectViewGroup.getBitmap(z, true);
    }

    public CanvasMaskEntity getSelectScreenshotAndMask(boolean z, boolean z2, float f, float f2, int i) {
        return this.mSelectViewGroup.getBitmapAndMask(z, z2, f, f2, i);
    }

    public Boolean isFromAi() {
        return Boolean.valueOf(this.mSelectViewGroup.isFromAi());
    }

    public List<SpannedData> getAllFromAISpanned() {
        return this.mSelectViewGroup.getAllFromAISpanned();
    }

    public List<SpannedData> getAllFromInsertSpanned() {
        return this.mSelectViewGroup.getAllFromInsertSpanned();
    }

    public void simulateClick(TouchPosition touchPosition) {
        MotionEvent motionEventObtain = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, touchPosition.x, touchPosition.y, 1);
        MotionEvent motionEventObtain2 = MotionEvent.obtain(SystemClock.uptimeMillis() + 200, SystemClock.uptimeMillis() + 200, 1, touchPosition.x, touchPosition.y, 1);
        this.mSelectViewGroup.onTouchEvent(motionEventObtain);
        this.mSelectViewGroup.onTouchEvent(motionEventObtain2);
    }

    public String getAiBitmapPath() {
        if (isFromAi().booleanValue()) {
            return this.mSelectViewGroup.getAiPath();
        }
        return "";
    }

    public SpannedData getClickImage() {
        SelectViewGroup selectViewGroup = this.mSelectViewGroup;
        if (selectViewGroup != null) {
            return selectViewGroup.getClickImage();
        }
        return null;
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        SelectViewGroup selectViewGroup;
        if (i == 0 || i2 == 0) {
            return;
        }
        if (this.mVisibleWidth == i && this.mVisibleHeight == i2) {
            return;
        }
        if (this.isPageSizeDialogShow) {
            this.isPageSizeDialogShow = false;
            return;
        }
        this.mVisibleWidth = i;
        this.mVisibleHeight = i2;
        this.windowInfo.onSizeChanged(i, i2);
        this.layoutManager.onSizeChanged(i, i2);
        this.mGlobalRulerView.onSizeChanged(i, i2);
        if (!this.mList.isEmpty()) {
            PageItemData pageItemData = this.mList.get(0);
            if (pageItemData.isUnLimit) {
                this.currentItemView.setUnlimitedSize(i, i2);
            }
            this.currentItemView.setVisibleSize(i, i2);
            this.currentItemView.getCreationEngine().getCanvasOperator().setCanvasDrawWidth(this.windowInfo.mWindowWidth);
            float f = i;
            this.currentItemView.setCanvasSize(f, Math.max(i2, (int) pageItemData.getItemHeight(i, 1.0f)));
            if (pageItemData.sizeUnit == 0 && !pageItemData.isFixSize) {
                this.currentItemView.getCreationEngine().getCanvasOperator().setVisibleFixedWidth(f);
            } else {
                this.currentItemView.getCreationEngine().getCanvasOperator().setVisibleFixedWidth(Math.max(pageItemData.maxWidth / this.layoutManager.getPageScaleRatio(), f));
            }
            initEntPages();
            getDrawView().getCreationEngine().getCurveEngine().getPageSystem().adjustAllPageSize((List) this.mList.stream().map(new Function() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    PageItemData pageItemData2 = (PageItemData) obj;
                    return Arrays.asList(Integer.valueOf(pageItemData2.width), Integer.valueOf(pageItemData2.height), Integer.valueOf(pageItemData2.maxWidth), Integer.valueOf(pageItemData2.maxHeight));
                }
            }).collect(Collectors.toList()));
            this.currentItemView.getCreationEngine().getCurveEngine().getCamera().setRealtimeDrawRatio(this.windowInfo.mWindowWidth / 1000.0f);
            this.currentItemView.getCreationEngine().getCurveEngine().getCamera().setScreenSize(i, i2);
        }
        this.layoutManager.onDataChanged();
        if ((i != i3 || UIUtils.isActiveDekstopMode(this.mContextRef.get())) && (selectViewGroup = this.mSelectViewGroup) != null) {
            selectViewGroup.cancelSelect(false);
            this.mSelectViewGroup.setViewModel(this.currentItemView, this.engineConfig);
            if (this.currentItemView != null) {
                this.mSelectViewGroup.setSpanned(this.currentItemView.getMode());
            }
        }
        this.mDrawView.getView().post(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m852xca58e893();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onSizeChanged$10$com-miui-libhandwritingpage-model-PageInkModel, reason: not valid java name */
    /* synthetic */ void m852xca58e893() {
        updateStateView(this.currentPosition);
    }

    @Override // com.miui.libhandwritingpage.model.IPageInkModel
    public void onConfigurationChanged(Configuration configuration) {
        if (UIUtils.isNightMode() != this.isNightMode) {
            this.isNightMode = UIUtils.isNightMode();
            if (getDrawView().getCreationEngine().getCurveEngine() != null) {
                getDrawView().getCreationEngine().getCurveEngine().getHighLightSystem().shutdownHighlight();
            }
        }
        if (this.orientation == this.mContextRef.get().getResources().getConfiguration().orientation) {
            return;
        }
        SelectViewGroup selectViewGroup = this.mSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.cancelSelect(false);
        }
        super.onConfigurationChanged(configuration);
        if (getDrawView().getCreationEngine() != null) {
            getDrawView().getCreationEngine().getCurveEngine().getPenEngine().getEngineHandler().sendMessageDelayed(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m849x12a6c095();
                }
            }, 0, 150L);
        }
    }

    /* JADX INFO: renamed from: lambda$onConfigurationChanged$11$com-miui-libhandwritingpage-model-PageInkModel, reason: not valid java name */
    /* synthetic */ void m849x12a6c095() {
        if (getDrawView().getCreationEngine().getCurveEngine().getCamera().isNeedToScrollBackToBottom()) {
            jumpPage(getPageCount() - 1, false);
        }
    }

    public void clearPage(int i) {
        clearPage(i, true);
    }

    private void clearPage(int i, boolean z) {
        if (invalidPosition()) {
            return;
        }
        boolean zIsCurveEmpty = this.currentItemView.isCurveEmpty(i);
        if (this.currentItemView.isContentEmpty(i)) {
            return;
        }
        if (this.adapter.getStepHelper() != null) {
            this.adapter.getStepHelper().setStepCount(zIsCurveEmpty ? 1 : 2);
        }
        if (this.searchWrapper.searchRectMap != null) {
            String str = "";
            for (String str2 : this.searchWrapper.searchRectMap.keySet()) {
                if (i == Integer.parseInt(str2.split("-")[0])) {
                    str = str2;
                }
            }
            if (!str.isEmpty()) {
                this.searchWrapper.searchRectMap.remove(str);
            }
            if (i == this.currentPosition) {
                this.currentItemView.setSearchRectList(true, 0);
            }
        }
        if (getDrawView().getCreationEngine().getCurveEngine() != null) {
            getDrawView().getCreationEngine().getCurveEngine().getHighLightSystem().shutdownHighlight();
        }
        this.mSelectViewGroup.cancelSelect(false);
        this.currentItemView.setClear(i, z);
        PageItemData pageItemData = this.mList.get(i);
        pageItemData.engineData = null;
        pageItemData.filePath = null;
        pageItemData.covePath = "";
        onPreviewChanged();
    }

    public void deletePage(List<Integer> list) {
        PageStepHelper stepHelper;
        Log.i(TAG, "deletePage " + Arrays.toString(list.toArray()));
        this.mIsScrollByTouch = false;
        if (list.isEmpty()) {
            return;
        }
        this.pageChange = true;
        this.layoutManager.resetDrag();
        cancelPageSelect(getCurrentPosition());
        ArrayList arrayList = new ArrayList();
        int i = -1;
        for (int i2 = 0; i2 < list.size(); i2++) {
            int iIntValue = list.get(i2).intValue();
            if (iIntValue >= 0 && iIntValue < this.mList.size()) {
                if (this.currentItemView != null) {
                    this.currentItemView.setSearchRectList(true, 0);
                }
                arrayList.add(this.mList.get(iIntValue));
                LruCacheUtil.removeBitmapFromMemCache(this.mList.get(iIntValue).bgImage);
                i = iIntValue;
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() == 1 && (stepHelper = this.adapter.getStepHelper()) != null) {
            stepHelper.removeAllStepByViewId(i, this.mList.get(i).viewId);
        }
        onPageDelete(arrayList);
    }

    private void onPageChanged() {
        this.layoutManager.onDataChanged();
        onPreviewChanged();
    }

    private void onPreviewChanged() {
        this.previewBeans.clear();
        for (int i = 0; i < this.mList.size(); i++) {
            PageItemData pageItemData = this.mList.get(i);
            this.previewBeans.add(new PagePreviewData(pageItemData.pdfId, pageItemData.bgConfig, pageItemData.bookmark, pageItemData.bgImageMode, this.mPageBean.path + File.separator + pageItemData.bgImage, pageItemData.bgColor, pageItemData.viewId, pageItemData.covePath, pageItemData.bgGrid));
        }
        PreviewListener previewListener = this.previewListener;
        if (previewListener != null) {
            previewListener.onPreviewUpdate(this.previewBeans);
        }
    }

    public void enableContourAntLine(boolean z) {
        this.engineConfig.enableSelectedContour = z;
        this.mSelectViewGroup.enableContourAntLine(z);
    }

    public void setSelectEdit(boolean z) {
        this.mSelectViewGroup.setEdit(z);
    }

    public void scrollBy(int i, int i2) {
        this.layoutManager.scrollBy(i, i2);
        this.layoutManager.onScrollEnd();
    }

    public void scrollBy(float f, float f2) {
        this.layoutManager.scrollBy(f, f2);
        this.layoutManager.onScrollEnd();
    }

    public void scrollEditTextBy(int i) {
        this.currentItemView.scrollEditTextBy(i);
    }

    public void resetScrolledEditTextBy(int i) {
        this.currentItemView.resetScrolledEditTextBy(i);
    }

    public boolean isTextEditing() {
        if (this.currentItemView == null) {
            return false;
        }
        return this.currentItemView.isTextEditing();
    }

    public void destroy() {
        CreationPageSDK.removeToken(this.mContextRef.get());
        CreationPageSDK.clearHandler(CreationPageSDK.getToken(this.mContextRef.get()));
        ImportPdfBitmap importPdfBitmap = this.mImportPdfBitmap;
        if (importPdfBitmap != null) {
            importPdfBitmap.release();
        }
        ImportPdfBitmap importPdfBitmap2 = this.mExportPdfBitmap;
        if (importPdfBitmap2 != null) {
            importPdfBitmap2.release();
            this.mExportPdfBitmap = null;
        }
        ImportPdfBitmap importPdfBitmap3 = this.mImportPdfThumbnail;
        if (importPdfBitmap3 != null) {
            importPdfBitmap3.release();
            this.mImportPdfThumbnail = null;
        }
        if (this.currentItemView != null) {
            this.currentItemView.release();
            this.currentItemView = null;
        }
        if (this.mList.size() > 0) {
            Iterator<PageItemData> it = this.mList.iterator();
            while (it.hasNext()) {
                it.next().release();
            }
        }
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView != null) {
            View view = pageDrawView.getView();
            if (view instanceof PageSurfaceView) {
                ((PageSurfaceView) view).release();
            }
        }
        this.mSelectViewGroup.destroy();
        LruCacheUtil.clearCache();
        this.adapter.release();
        FileUtil.deleteDir(new File(this.pageDataTempDir));
        CreationPageSDK.sOffX = 0.0f;
        this.searchWrapper.searchRectMap = null;
        this.searchWrapper.searchText = null;
        this.mGlobalRulerView.removeListener();
        this.mIsFirstjump = true;
        this.mFirstJumpPosition = -1;
        this.mLoadListener = null;
    }

    private void onPageDelete(List<PageItemData> list) {
        Log.i(TAG, "onPageDelete");
        ArrayList arrayList = new ArrayList();
        int i = -1;
        for (PageItemData pageItemData : list) {
            int pageIndex = pageItemData.getPageIndex();
            clearPage(pageIndex, false);
            updateContentEmpty();
            getDrawView().getCreationEngine().deletePage(pageIndex);
            this.currentItemView.removeExtraData(pageIndex);
            this.adapter.removeStep(pageIndex);
            pageItemData.engineData = null;
            pageItemData.spannedData = null;
            pageItemData.undoList = null;
            pageItemData.redoList = null;
            pageItemData.bgConfig = null;
            this.mList.remove(pageItemData);
            arrayList.add(Integer.valueOf(pageIndex));
            i = pageIndex;
        }
        for (int i2 = i; i2 < this.mList.size(); i2++) {
            Log.i(TAG, String.format("[onPageDelete] change page[%d] index -->page[%d]", Integer.valueOf(this.mList.get(i2).getPageIndex()), Integer.valueOf(i2)));
            this.mList.get(i2).setPageIndex(i2);
            this.mList.get(i2).isModified = true;
        }
        list.clear();
        onPageChanged();
        PageChangeListener pageChangeListener = this.mPageChangeListener;
        if (pageChangeListener != null) {
            pageChangeListener.onPageDelete(arrayList);
        }
        loadPdfBitmap(this.mList);
        if (this.mStateView == null) {
            return;
        }
        getDrawView().getCreationEngine().getCurveEngine().getPenEngine().getEngineHandler().sendMessageDelayed(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m851xcd27eeb0();
            }
        }, 0, 150L);
        if (i >= getPageCount()) {
            this.currentPosition--;
        }
        updateStateView(this.currentPosition);
        new Handler().postDelayed(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.bindItemData();
            }
        }, 50L);
    }

    /* JADX INFO: renamed from: lambda$onPageDelete$12$com-miui-libhandwritingpage-model-PageInkModel, reason: not valid java name */
    /* synthetic */ void m851xcd27eeb0() {
        if (getDrawView().getCreationEngine().getCurveEngine().getCamera().isNeedToScrollBackToBottom()) {
            jumpPage(getPageCount() - 1, true);
        }
    }

    public void updateStateView(int i) {
        if (this.mStateView == null) {
            return;
        }
        float[] location = this.layoutManager.getLocation();
        this.mStateView.update(i, getPageCount(), this.layoutManager.getFactorScale(), location[0], location[1]);
    }

    public void setStateView(PageStateView pageStateView) {
        this.mStateView = pageStateView;
    }

    public void setMultiStateViewVisibility(int i) {
        this.mStateView.getView().setVisibility(i);
    }

    public void finishSelect() {
        this.mSelectViewGroup.cancelSelect(false);
    }

    public void setPageSizeConfig(PageSizeConfig pageSizeConfig, boolean z) {
        Log.d(TAG, "setPageSizeConfig   " + pageSizeConfig + "; isFromCancel " + z);
        SelectViewGroup selectViewGroup = this.mSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.cancelSelect(false);
        }
        if (canChangeSizeConfig(pageSizeConfig.position)) {
            if (pageSizeConfig.isUnLimit) {
                normalToUnLimit(pageSizeConfig);
                getDrawView().getCreationEngine().getCurveEngine().getCamera().resetViewPortCenter();
                return;
            }
            if (isUnlimited()) {
                unLimitToNormal(pageSizeConfig, z);
                getDrawView().getCreationEngine().getCurveEngine().getCamera().resetViewPortCenter();
                return;
            }
            final int i = pageSizeConfig.position;
            PageItemData pageItemData = this.mList.get(i);
            int i2 = pageItemData.maxWidth;
            int i3 = pageItemData.maxHeight;
            changedPageSizeConfig(pageSizeConfig, z);
            int i4 = i2 - pageItemData.maxWidth;
            int i5 = pageItemData.maxHeight - i3;
            onPageChanged();
            this.pageChange = true;
            CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m854x4cf22c30(i);
                }
            });
            this.mPageBean.width = pageSizeConfig.width;
            this.mPageBean.height = pageSizeConfig.height;
            this.mPageBean.orientation = (short) pageSizeConfig.orientation;
            this.mPageBean.sizeType = pageSizeConfig.type;
            PageItemData pageItemData2 = this.mList.get(i);
            this.currentItemView.moveAllSpanAfterPage(i, i4, i5);
            getDrawView().getCreationEngine().moveAllCurveAfterPage(i, i4, i5);
            getDrawView().getCreationEngine().getCurveEngine().getPageSystem().setPageSize(i, pageItemData2.width, pageItemData2.height, pageItemData2.maxWidth, pageItemData2.maxHeight);
            if (getDrawView().getCreationEngine().getCurveEngine().getCamera().isNeedToScrollBackToBottom()) {
                jumpPage(getPageCount() - 1, true);
            }
            PageSizeListener pageSizeListener = this.mPageSizeListener;
            if (pageSizeListener != null) {
                pageSizeListener.onPageSizeChanged(this.isUnlimited);
            }
        }
    }

    public void normalToUnLimit(PageSizeConfig pageSizeConfig) {
        PageInkView.ShieldEventListener shieldEventListener = this.shieldEventListener;
        if (shieldEventListener != null) {
            shieldEventListener.shieldEvent(false);
        }
        this.isUnlimited = true;
        this.windowInfo.onSizeChanged(this.mVisibleWidth, this.mVisibleHeight);
        this.currentItemView.getCreationEngine().getCanvasOperator().resetInfiniteCanvas();
        PageItemData pageItemData = this.mList.get(pageSizeConfig.position);
        if (pageItemData.unLimitToNormal) {
            int[] unLimitBound = this.layoutManager.getUnLimitBound();
            this.currentItemView.moveAllSpan(-unLimitBound[0], -unLimitBound[1]);
            this.currentItemView.moveAllCurve(-unLimitBound[0], -unLimitBound[1]);
        }
        pageItemData.unLimitToNormal = false;
        pageItemData.isUnLimit = true;
        if (pageItemData.isFixSize) {
            pageItemData.fixWidth = this.currentItemView.getCreationEngine().getCanvasOperator().getCanvasDrawWidth();
        } else {
            pageItemData.fixWidth = this.currentItemView.getCreationEngine().getCanvasOperator().getVisibleWidth();
        }
        pageItemData.isFixSize = true;
        pageItemData.pageSizeType = pageSizeConfig.pageType;
        this.currentItemView.setSpannedLayoutFixMode(true);
        this.currentItemView.setSpannedLayoutFixWidth((int) pageItemData.fixWidth);
        this.currentItemView.setSpanVisibleSize((int) pageItemData.fixWidth, this.mVisibleHeight);
        this.currentItemView.setUnLimit(true);
        this.currentItemView.isUnLimitToNormal = false;
        this.currentItemView.setSearchRectList(true, 0);
        this.currentItemView.getCreationEngine().getCurveEngine().getCurveSystem().clearAllTiles();
        onPageChanged();
        this.currentItemView.getCreationEngine().getCurveEngine().setPageMode(MiCurveEngine.EPageMode.Infinite);
        this.currentItemView.getCreationEngine().getCurveEngine().getBgSystem().markDirty();
        this.currentItemView.getCreationEngine().getCurveEngine().requestMultiBufferRender(MiCurveEngine.ERefreshReason.Force);
        PageSizeListener pageSizeListener = this.mPageSizeListener;
        if (pageSizeListener != null) {
            pageSizeListener.onPageSizeChanged(this.isUnlimited);
        }
    }

    public void unLimitToNormal(PageSizeConfig pageSizeConfig, boolean z) {
        int iMm2px;
        int iMm2px2;
        PageInkView.ShieldEventListener shieldEventListener = this.shieldEventListener;
        if (shieldEventListener != null) {
            shieldEventListener.shieldEvent(false);
        }
        this.isUnLimitToNormal = true;
        this.windowInfo.onSizeChanged(this.mVisibleWidth, this.mVisibleHeight);
        int[] centerPoint = this.layoutManager.getCenterPoint();
        int[] unLimitBound = this.layoutManager.getUnLimitBound();
        if (!pageSizeConfig.isUnLimitToNormal && z && !pageSizeConfig.isFixSize) {
            this.layoutManager.scrollTo(0.0f, 0.0f);
        } else {
            this.layoutManager.scrollTo(centerPoint[0], centerPoint[1]);
            this.currentItemView.moveAllSpan(unLimitBound[0], unLimitBound[1]);
            this.currentItemView.moveAllCurve(unLimitBound[0], unLimitBound[1]);
        }
        this.currentItemView.getCreationEngine().getCanvasOperator().resetInfiniteCanvas();
        PageItemData pageItemData = this.mList.get(pageSizeConfig.position);
        pageItemData.orientation = pageSizeConfig.orientation;
        if (pageSizeConfig.type == 0) {
            pageItemData.width = pageSizeConfig.orientation == 0 ? pageSizeConfig.width : pageSizeConfig.height;
            pageItemData.height = pageSizeConfig.orientation == 0 ? pageSizeConfig.height : pageSizeConfig.width;
        } else {
            pageItemData.width = pageSizeConfig.width;
            pageItemData.height = pageSizeConfig.height;
        }
        pageItemData.isUnLimit = false;
        pageItemData.unLimitToNormal = true;
        pageItemData.pageSizeType = pageSizeConfig.pageType;
        pageItemData.maxHeight = unLimitBound[1] - unLimitBound[3];
        pageItemData.maxWidth = unLimitBound[0] - unLimitBound[2];
        pageItemData.sizeUnit = pageSizeConfig.type;
        pageItemData.useNewSize = true;
        pageItemData.isFixSize = true;
        pageItemData.fixWidth = this.currentItemView.getCreationEngine().getCanvasOperator().getCanvasDrawWidth();
        if (z && !pageSizeConfig.isFixSize) {
            pageItemData.isUnLimit = false;
            pageItemData.unLimitToNormal = pageSizeConfig.isUnLimitToNormal;
            pageItemData.pageSizeType = pageSizeConfig.pageType;
            pageItemData.maxHeight = pageSizeConfig.maxHeight;
            pageItemData.maxWidth = pageSizeConfig.maxWidth;
            pageItemData.sizeUnit = pageSizeConfig.type;
            pageItemData.isFixSize = pageSizeConfig.isFixSize;
            if (!pageItemData.unLimitToNormal) {
                this.currentItemView.isUnLimitToNormal = false;
                this.currentItemView.setUnLimit(false);
            }
        }
        this.isUnlimited = false;
        this.isUnLimitToNormal = true;
        int i = pageSizeConfig.type;
        if (i == 0) {
            iMm2px = (pageItemData.height * this.windowInfo.mWindowWidth) / pageItemData.width;
            int i2 = this.windowInfo.mWindowWidth;
            if (!z || pageSizeConfig.isFixSize) {
                pageItemData.height = iMm2px;
                pageItemData.width = i2;
            }
            iMm2px2 = i2;
        } else if (i == 1) {
            iMm2px2 = pageItemData.width;
            iMm2px = pageItemData.height;
        } else if (i != 2) {
            iMm2px2 = 0;
            iMm2px = 0;
        } else {
            iMm2px2 = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData.width);
            iMm2px = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData.height);
        }
        if (!pageSizeConfig.isUnLimit) {
            pageItemData.maxWidth = iMm2px2;
            pageItemData.maxHeight = iMm2px;
            this.currentItemView.getCreationEngine().getCanvasOperator().setCanvasDrawWidth(this.windowInfo.mWindowWidth);
            this.currentItemView.setSpannedLayoutFixMode(false);
            this.currentItemView.setSpanVisibleSize(this.mVisibleWidth, this.mVisibleHeight);
        }
        this.currentItemView.isUnLimitToNormal = true;
        this.currentItemView.setBottomShieldHeight(0.0f);
        this.currentItemView.setShieldWidth((pageItemData.maxWidth - iMm2px2) / 2);
        this.currentItemView.setSearchRectList(true, 0);
        this.layoutManager.setContentWidth(Math.max(this.mVisibleWidth, this.layoutManager.getStandardWidth()));
        this.currentItemView.getCreationEngine().getCurveEngine().getCurveSystem().clearAllTiles();
        onPageChanged();
        if (getDrawView().getCreationEngine().getCurveEngine().getPageSystem().getPageCount() > 0) {
            this.currentItemView.getCreationEngine().getCurveEngine().setPageMode(MiCurveEngine.EPageMode.Paginated2);
            getDrawView().getCreationEngine().getCurveEngine().getPageSystem().setPageSize(0, pageItemData.width, pageItemData.height, pageItemData.maxWidth, pageItemData.maxHeight);
            setPageBGStyle(0, pageItemData.bgGrid);
            setPageBGColor(0, pageItemData.bgColor);
        } else {
            addSystemPage(pageItemData, false);
        }
        PageSizeListener pageSizeListener = this.mPageSizeListener;
        if (pageSizeListener != null) {
            pageSizeListener.onPageSizeChanged(this.isUnlimited);
        }
    }

    private void changedPageSizeConfig(PageSizeConfig pageSizeConfig, boolean z) {
        int iMm2px;
        int iMm2px2;
        Log.d(TAG, "changedPageSizeConfig   " + pageSizeConfig + "; isFromCancel " + z);
        PageItemData pageItemData = this.mList.get(pageSizeConfig.position);
        pageItemData.orientation = pageSizeConfig.orientation;
        if (pageSizeConfig.type == 0) {
            pageItemData.width = pageSizeConfig.orientation == 0 ? pageSizeConfig.width : pageSizeConfig.height;
            pageItemData.height = pageSizeConfig.orientation == 0 ? pageSizeConfig.height : pageSizeConfig.width;
        } else {
            pageItemData.width = pageSizeConfig.width;
            pageItemData.height = pageSizeConfig.height;
        }
        pageItemData.sizeUnit = pageSizeConfig.type;
        pageItemData.pageSizeType = pageSizeConfig.pageType;
        int i = pageSizeConfig.type;
        if (i == 0) {
            iMm2px = (pageItemData.height * this.windowInfo.mWindowWidth) / pageItemData.width;
            iMm2px2 = this.windowInfo.mWindowWidth;
            pageItemData.height = iMm2px;
            pageItemData.width = iMm2px2;
        } else if (i == 1) {
            iMm2px2 = pageItemData.width;
            iMm2px = pageItemData.height;
            pageItemData.isFixSize = true;
        } else if (i != 2) {
            iMm2px2 = 0;
            iMm2px = 0;
        } else {
            iMm2px2 = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData.width);
            iMm2px = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData.height);
            pageItemData.isFixSize = true;
        }
        pageItemData.useNewSize = (this.isUnlimited && pageSizeConfig.type == 0) ? false : true;
        this.isUnlimited = false;
        int i2 = pageItemData.maxWidth;
        if (z && pageSizeConfig.isChangeFromUndo) {
            if (pageSizeConfig.isFixSize) {
                pageItemData.maxWidth = Math.max(pageSizeConfig.maxWidth, pageItemData.maxWidth);
                pageItemData.maxHeight = pageSizeConfig.maxHeight;
            } else {
                float f = pageSizeConfig.maxHeight / pageSizeConfig.maxWidth;
                pageItemData.maxWidth = Math.max(this.windowInfo.mWindowWidth, pageItemData.maxWidth);
                pageItemData.maxHeight = (int) (this.windowInfo.mWindowWidth * f);
                this.currentItemView.getCreationEngine().getCanvasOperator().setCanvasDrawWidth(this.windowInfo.mWindowWidth);
                this.currentItemView.setSpannedLayoutFixMode(false);
                this.currentItemView.setSpanVisibleSize(this.mVisibleWidth, this.mVisibleHeight);
            }
            pageItemData.isFixSize = pageSizeConfig.isFixSize;
            pageItemData.fixWidth = pageSizeConfig.fixWidth;
        } else {
            pageItemData.maxWidth = Math.max(iMm2px2, pageItemData.maxWidth);
            pageItemData.maxHeight = iMm2px;
        }
        if (pageSizeConfig.type == 0 && pageItemData.maxWidth != iMm2px2 && (!z || !pageSizeConfig.isChangeFromUndo)) {
            pageItemData.isFixSize = true;
        }
        pageItemData.isAdd = false;
        if (this.currentItemView != null) {
            if (pageItemData.isFixSize) {
                pageItemData.fixWidth = this.currentItemView.getCreationEngine().getCanvasOperator().getCanvasDrawWidth();
            } else {
                this.currentItemView.setVisibleSize(this.mVisibleWidth, this.mVisibleHeight);
            }
            this.currentItemView.setSpannedLayoutFixMode(pageItemData.isFixSize);
            this.currentItemView.setSpannedLayoutFixWidth((int) pageItemData.fixWidth);
            this.currentItemView.setSpanVisibleSize(pageItemData.isFixSize ? (int) pageItemData.fixWidth : this.mVisibleWidth, this.mVisibleHeight);
            this.currentItemView.getCreationEngine().getCanvasOperator().resetInfiniteCanvas();
            if (pageItemData.maxWidth > this.windowInfo.mWindowWidth || i2 > this.windowInfo.mWindowWidth) {
                this.currentItemView.moveAllCurve((pageItemData.maxWidth - i2) / 2.0f, 0.0f);
                this.currentItemView.moveAllSpan((pageItemData.maxWidth - i2) / 2.0f, 0.0f);
                this.currentItemView.getCreationEngine().getCanvasOperator().resetInfiniteCanvas();
                this.currentItemView.getCreationEngine().getCanvasOperator().setVisibleFixedWidth(pageItemData.maxWidth / this.layoutManager.getPageScaleRatio());
            }
            float itemHeight = pageItemData.getItemHeight(this.mVisibleWidth, 1.0f);
            this.currentItemView.setBottomShieldHeight(pageItemData.maxHeight - iMm2px);
            this.currentItemView.setShieldWidth((pageItemData.maxWidth - iMm2px2) / 2);
            this.currentItemView.setCanvasSize(this.mVisibleWidth, pageItemData.sizeUnit == 0 ? (int) Math.max(Math.max(this.mVisibleHeight, itemHeight), (((double) (this.mVisibleWidth * pageItemData.height)) * 1.0d) / ((double) pageItemData.width)) : (int) Math.max(this.mVisibleHeight, itemHeight));
        }
        if (this.currentPosition == pageSizeConfig.position) {
            float fComputeItemTopByPosition = this.layoutManager.computeItemTopByPosition(this.currentPosition) + ((iMm2px - UIUtils.dip2px(this.mContextRef.get(), 200.0f)) * getScale());
            float[] location = this.layoutManager.getLocation();
            float f2 = -fComputeItemTopByPosition;
            if (location[1] < f2) {
                this.layoutManager.scrollTo(location[0], f2);
            }
        }
        for (int i3 = pageSizeConfig.position; i3 < this.mList.size(); i3++) {
            this.mList.get(i3).isModified = true;
        }
    }

    public void addChangePageSizeStep(PageSizeConfig pageSizeConfig, PageSizeConfig pageSizeConfig2) {
        if (canChangeSizeConfig(this.currentPosition) && this.currentItemView != null) {
            this.currentItemView.getCreationEngine().getStepManager().addStep(new StepChangePageSize((PenEngine) this.currentItemView.getCreationEngine(), pageSizeConfig, pageSizeConfig2, this.currentPosition));
        }
    }

    public boolean canChangeSizeConfig(int i) {
        List<PageItemData> list = this.mList;
        if (list != null && list.size() != 0 && i >= 0 && i < this.mList.size()) {
            PageItemData pageItemData = this.mList.get(i);
            boolean zIsEmpty = TextUtils.isEmpty(pageItemData.pdfId);
            boolean zIsEmpty2 = TextUtils.isEmpty(pageItemData.bgImage);
            if (zIsEmpty && zIsEmpty2) {
                return true;
            }
        }
        return false;
    }

    public RectF getNewSelectSaveRectF(RectF rectF, int i) {
        RectF rectF2 = new RectF(rectF);
        if (rectF != null) {
            Log.d(TAG, "getNewSelectSaveRectF: ViewIndex:" + i + ",CurrentPosition:" + this.currentPosition);
            if (this.currentItemView != null && this.currentPosition == i) {
                float drawRatio = this.currentItemView.getCreationEngine().getCanvasOperator().getDrawRatio();
                RectF rectF3 = new RectF(rectF.left * drawRatio, rectF.top * drawRatio, rectF.right * drawRatio, rectF.bottom * drawRatio);
                ScaleInfo canvasScaleInfo = this.currentItemView.getCreationEngine().getCanvasOperator().getCanvasScaleInfo();
                RectF rectFInverseRectF = MathUtils.inverseRectF(rectF3, canvasScaleInfo.getTranslate().x, canvasScaleInfo.getTranslate().y, canvasScaleInfo.getScaleRate());
                RectF layout = this.currentItemView.getLayout();
                rectFInverseRectF.offset(layout.left, layout.top);
                return rectFInverseRectF;
            }
        }
        return rectF2;
    }

    public RectF getSelectRectFWithoutOffset(RectF rectF, int i) {
        RectF rectF2 = new RectF(rectF);
        if (this.currentItemView == null) {
            return rectF2;
        }
        float drawRatio = this.currentItemView.getCreationEngine().getCanvasOperator().getDrawRatio();
        RectF rectF3 = new RectF(rectF.left * drawRatio, rectF.top * drawRatio, rectF.right * drawRatio, rectF.bottom * drawRatio);
        ScaleInfo canvasScaleInfo = this.currentItemView.getCreationEngine().getCanvasOperator().getCanvasScaleInfo();
        RectF rectFInverseRectF = MathUtils.inverseRectF(rectF3, canvasScaleInfo.getTranslate().x, canvasScaleInfo.getTranslate().y, canvasScaleInfo.getScaleRate());
        RectF layout = this.currentItemView.getLayout();
        Log.d(TAG, "getSelectRectFWithoutOffset, layoutRect: " + layout);
        if (!this.isUnlimited && !this.isUnLimitToNormal) {
            rectFInverseRectF.offset(getScale() <= 1.0f ? layout.left : 0.0f, Math.max(0.0f, layout.top));
        }
        if (this.currentItemView.isUnLimitToNormal) {
            rectFInverseRectF.offset(0.0f, Math.max(0.0f, layout.top));
        }
        if (this.isUnLimitToNormal && !this.currentItemView.isUnLimitToNormal) {
            rectFInverseRectF.offset(Math.max(layout.left, 0.0f), Math.max(0.0f, layout.top));
        }
        return rectFInverseRectF;
    }

    public void deleteNewSelectSaveData(List<TouchPosition> list) {
        if (list != null) {
            this.mSelectViewGroup.replaceView(list);
        }
    }

    public void insertPage(int i, int i2, int i3) {
        insertPage(i, i2, i3, true);
    }

    public void insertPage(int i, int i2, int i3, boolean z) {
        boolean z2;
        if (this.mList.size() >= 500 || i < 0 || i > this.mList.size() || this.mPageBean == null) {
            return;
        }
        this.pageChange = true;
        PageItemData pageItemData = new PageItemData(new BgGridConfiguration(), "", "" + (this.mList.size() - 1), 0, "", 0, this.mPageBean.orientation, -1, i2, i3, (int) System.currentTimeMillis(), "");
        if (i > 0) {
            PageItemData pageItemData2 = this.mList.get(this.currentPosition);
            PageItemData pageItemData3 = new PageItemData(new BgGridConfiguration(), "", "" + (this.mList.size() - 1), 0, "", 0, pageItemData2.orientation, -1, pageItemData2.width, pageItemData2.height, (int) System.currentTimeMillis(), "");
            pageItemData3.sizeUnit = pageItemData2.sizeUnit;
            pageItemData3.pageSizeType = pageItemData2.pageSizeType;
            if (pageItemData3.sizeUnit == 2) {
                pageItemData3.maxWidth = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData2.width);
                pageItemData3.maxHeight = (int) UIUtils.mm2px(this.mContextRef.get(), pageItemData2.height);
                z2 = true;
                pageItemData3.isAIAdd = true;
            } else {
                z2 = true;
                if (pageItemData3.sizeUnit == 1) {
                    pageItemData3.maxWidth = pageItemData2.width;
                    pageItemData3.maxHeight = pageItemData2.height;
                    pageItemData3.isAIAdd = true;
                } else {
                    pageItemData3.maxWidth = pageItemData2.width;
                    pageItemData3.maxHeight = pageItemData2.height;
                }
            }
            pageItemData3.isFixSize = pageItemData2.isFixSize;
            pageItemData3.isAdd = z2;
            if (pageItemData3.sizeUnit != 0 || pageItemData3.isFixSize) {
                pageItemData3.height = pageItemData2.height;
                pageItemData3.width = pageItemData2.width;
                pageItemData3.fixWidth = pageItemData2.fixWidth;
                if (!TextUtils.isEmpty(pageItemData2.bgImage) || !TextUtils.isEmpty(pageItemData2.pdfId)) {
                    pageItemData3.width = pageItemData2.maxWidth;
                    pageItemData3.height = pageItemData2.maxHeight;
                    pageItemData3.maxWidth = pageItemData2.maxWidth;
                    pageItemData3.maxHeight = pageItemData2.maxHeight;
                }
                pageItemData3.useNewSize = true;
            }
            pageItemData = pageItemData3;
        }
        this.mList.add(i, pageItemData);
        PageChangeListener pageChangeListener = this.mPageChangeListener;
        if (pageChangeListener != null) {
            pageChangeListener.onPageInsert(i, getPageCount());
        }
        for (int i4 = i; i4 < this.mList.size(); i4++) {
            PageItemData pageItemData4 = this.mList.get(i4);
            int i5 = i;
            while (i5 < this.mList.size()) {
                PageItemData pageItemData5 = this.mList.get(i5);
                i5++;
                pageItemData5.setPageIndex(i5);
            }
            pageItemData4.setPageIndex(i4);
            startBitmapTask(pageItemData4, i4);
        }
        onPageChanged();
        addSystemPage(this.mList.get(i), false);
        if (z) {
            jumpPage(i, true);
        }
        removePageChangeStep(true);
    }

    public boolean invalidPosition() {
        return this.currentPosition == -1 || this.currentItemView == null;
    }

    public void setPcMode(boolean z) {
        this.pcMode = z;
    }

    public boolean isPcMode() {
        return this.pcMode;
    }

    public PageLayoutManager getLayoutManager() {
        return this.layoutManager;
    }

    public float[] calcAllCurveBound() {
        if (this.currentItemView == null || this.currentItemView.getCreationEngine() == null) {
            return new float[]{-2.1474836E9f, -2.1474836E9f, 2.1474836E9f, 2.1474836E9f};
        }
        return this.currentItemView.getCreationEngine().calcAllCurveBound();
    }

    public float[] calcAllSpanBound() {
        RectF allSpanBound;
        if (this.currentItemView != null && (allSpanBound = this.currentItemView.getAllSpanBound()) != null) {
            return new float[]{-allSpanBound.left, -allSpanBound.top, -allSpanBound.right, -allSpanBound.bottom};
        }
        return new float[]{-2.1474836E9f, -2.1474836E9f, 2.1474836E9f, 2.1474836E9f};
    }

    public void calculateUnLimitExportArea() {
        if (this.layoutManager != null) {
            this.layoutManager.calculateUnLimitExportArea();
        }
    }

    public void windowFocusChange() {
        try {
            MiCurveEngine miCurveEngine = getMiCurveEngine();
            if (miCurveEngine != null) {
                ScreenRenderUtil.setProvider(miCurveEngine.getRenderService().getProvider());
            }
        } catch (Exception e) {
            Log.e(TAG, "windowFocusChange: " + e.getMessage());
        }
    }

    public void selectMoveEnable(boolean z) {
        this.mSelectViewGroup.enableMove(z);
    }

    public void resume() {
        this.mSelectViewGroup.resume();
    }

    public void pause() {
        this.mSelectViewGroup.pause();
    }

    public void pageLoadingFinish() {
        Log.d(TAG, "pageLoadingFinish: ");
        if (this.mStateListener != null) {
            this.mStateListener.onPageLoadingFinish();
        }
    }

    public void dataLoadFinish() {
        Log.d(TAG, "dataLoadFinish: ");
        if (this.mStateListener != null) {
            this.mStateListener.dataLoadFinish();
        }
    }

    public void setPageDivider(int i) {
        this.layoutManager.setPageDivider(i);
    }

    public void setPageViewPadding(int i) {
        this.layoutManager.setPageViewPadding(i);
    }

    public void expanseUnLimitedPage(float f, float f2, boolean z) {
        if (this.layoutManager != null) {
            this.layoutManager.checkEdgePoint(f, f2, z);
        }
    }

    public boolean isAiModel() {
        return this.isAiModel;
    }

    public void setShieldEventListener(PageInkView.ShieldEventListener shieldEventListener) {
        this.shieldEventListener = shieldEventListener;
    }

    public List<PagePreviewData> getPreviewBeans() {
        return this.previewBeans;
    }

    private String getBgImagePath(PageItemData pageItemData) {
        if (pageItemData.bgImage != null && !pageItemData.bgImage.isEmpty()) {
            File file = new File(pageItemData.bgImage);
            if (file.exists()) {
                return file.getPath();
            }
            File file2 = new File(this.mPageBean.path, pageItemData.bgImage);
            if (file2.exists()) {
                return file2.getPath();
            }
        }
        return null;
    }

    private String getBgImagePath(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        File file = new File(str);
        if (file.exists()) {
            return file.getPath();
        }
        return null;
    }

    public void setPageSizeListener(PageSizeListener pageSizeListener) {
        this.mPageSizeListener = pageSizeListener;
    }

    public void setPageChangeListener(PageChangeListener pageChangeListener) {
        this.mPageChangeListener = pageChangeListener;
    }

    private void setMathAutoSelectFlag(boolean z) {
        SpannedSelectModel spannedSelectMode;
        SelectViewGroup selectViewGroup = this.mSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.setMathAutoSelect(z);
        }
        SelectViewGroup selectViewGroup2 = this.mSelectViewGroup;
        if (selectViewGroup2 == null || selectViewGroup2.mSpannedEditMode == null || (spannedSelectMode = this.mSelectViewGroup.mSpannedEditMode.getSpannedSelectMode()) == null) {
            return;
        }
        spannedSelectMode.setMathAutoSelect(z);
        Log.d(TAG, "setMathAutoSelectFlag: " + z);
    }

    private List<DataCurve> getCurvesFromMathBlock() {
        List<DataCurve> list = this.mMathCurves;
        if (list != null && !list.isEmpty()) {
            return new ArrayList(this.mMathCurves);
        }
        Log.w("MathSelect", "getCurvesFromMathBlock:null!");
        return new ArrayList();
    }

    private void directSelectByRect(RectF rectF, List<DataCurve> list) {
        if (this.currentItemView == null) {
            Log.e("MathSelect", "directSelectByRect: currentItemView null");
            return;
        }
        if (this.mSelectViewGroup == null) {
            Log.e("MathSelect", "directSelectByRect: SelectViewGroup null");
            return;
        }
        try {
            List<DataCurve> curvesFromMathBlock = getCurvesFromMathBlock();
            if (curvesFromMathBlock.isEmpty()) {
                return;
            }
            ICreationEngine creationEngine = this.currentItemView.getCreationEngine();
            if (creationEngine != null) {
                ISelectOperator selectOperator = creationEngine.getSelectOperator();
                if (selectOperator != null) {
                    Log.d("MathSelect", "directSelectByRect: 一：调用SelectOperator.doRectSelect");
                    selectOperator.doRectSelect(rectF, list, false);
                    Log.d("MathSelect", "directSelectByRect: 二: 触发UI回调链");
                    this.mSelectViewGroup.setSelectStateI(true);
                    this.mSelectViewGroup.onSelectCount(1, "");
                    SRectF sRectF = new SRectF(rectF.left, rectF.top, rectF.right, rectF.bottom, 0.0f);
                    int iHashCode = curvesFromMathBlock.hashCode();
                    if (iHashCode == 0) {
                        iHashCode = Integer.MAX_VALUE & (rectF.hashCode() + ((int) System.currentTimeMillis()));
                    }
                    sRectF.mSelectHasCode = iHashCode;
                    this.mSelectViewGroup.getSelectView().onObjectSelected(sRectF, curvesFromMathBlock, false);
                    Log.d("MathSelect", "directSelectByRect success");
                    return;
                }
                Log.e("MathSelect", "directSelectByRect: SelectOperator null");
                return;
            }
            Log.e("MathSelect", "directSelectByRect: CreationEngine null");
        } catch (Exception e) {
            Log.e("MathSelect", "directSelectByRect Fail", e);
        }
    }

    public void getPdfBitmapForExport(List<Integer> list, final PDFExportListener pDFExportListener) {
        List<PageItemData> list2;
        if (list == null || list.isEmpty() || (list2 = this.mList) == null || list2.isEmpty() || this.mList.get(0).pdfId.isEmpty()) {
            return;
        }
        if (this.mExportPdfBitmap == null) {
            ImportPdfBitmap importPdfBitmap = new ImportPdfBitmap();
            this.mExportPdfBitmap = importPdfBitmap;
            importPdfBitmap.setLoadListener(new PDFLoadingListener() { // from class: com.miui.libhandwritingpage.model.PageInkModel.9
                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded() {
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onRemove(int i) {
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded(int i) {
                    Bitmap bitmap = PageInkModel.this.mExportPdfBitmap.getBitmap(i);
                    if (bitmap != null) {
                        Log.d(PageInkModel.TAG, "exportPdfBitmap onLoaded:" + i);
                        PageInkModel.this.getDrawView().getCreationEngine().getCurveEngine().getPageSystem().setPageBgImage(i, bitmap, false);
                    }
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onFinish() {
                    Log.d(PageInkModel.TAG, "onFinish");
                    PDFExportListener pDFExportListener2 = pDFExportListener;
                    if (pDFExportListener2 != null) {
                        pDFExportListener2.onFinish();
                    }
                    PageInkModel.this.mExportPdfBitmap.release();
                    PageInkModel.this.mExportPdfBitmap = null;
                }
            });
        }
        if (this.mExportPdfBitmap.isLoading()) {
            if (pDFExportListener != null) {
                pDFExportListener.onLoading();
                return;
            }
            return;
        }
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        for (Integer num : list) {
            linkedHashMap.put(num, Integer.valueOf(this.mList.get(num.intValue()).pdfIndex));
        }
        this.mExportPdfBitmap.setVisibleRect(this.mVisibleWidth);
        this.mExportPdfBitmap.setData(getDirPath() + File.separator + this.mList.get(0).pdfId, linkedHashMap);
    }

    private void loadPdfThumbnail(List<PageItemData> list) {
        if (list == null || list.isEmpty() || list.get(0).pdfId.isEmpty()) {
            return;
        }
        if (this.mImportPdfThumbnail == null) {
            ImportPdfBitmap importPdfBitmap = new ImportPdfBitmap();
            this.mImportPdfThumbnail = importPdfBitmap;
            importPdfBitmap.setLoadListener(new PDFLoadingListener() { // from class: com.miui.libhandwritingpage.model.PageInkModel.10
                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded() {
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onRemove(int i) {
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onLoaded(int i) {
                    Bitmap bitmap;
                    if (PageInkModel.this.mImportPdfThumbnail == null || (bitmap = PageInkModel.this.mImportPdfThumbnail.getBitmap(i)) == null) {
                        return;
                    }
                    Log.d(PageInkModel.TAG, String.format(" loadPdfThumbnail onLoaded :%d %d %d", Integer.valueOf(i), Integer.valueOf(bitmap.getWidth()), Integer.valueOf(bitmap.getHeight())));
                    PageInkModel.this.getDrawView().getCreationEngine().getCurveEngine().getPageSystem().setPageBgImage(i, bitmap, true);
                }

                @Override // com.miui.libhandwritingpage.listener.PDFLoadingListener
                public void onFinish() {
                    if (PageInkModel.this.mImportPdfThumbnail == null) {
                        return;
                    }
                    Log.d(PageInkModel.TAG, "loadPdfThumbnail onFinish");
                    PageInkModel.this.mPdfThumbnailIsLoaded = true;
                    if (PageInkModel.this.mPdfThumbnailLoadListener != null) {
                        PageInkModel.this.mPdfThumbnailLoadListener.onFinish();
                    }
                    PageInkModel.this.mImportPdfThumbnail.release();
                    PageInkModel.this.mImportPdfThumbnail = null;
                }
            });
        }
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        for (PageItemData pageItemData : list) {
            linkedHashMap.put(Integer.valueOf(pageItemData.getPageIndex()), Integer.valueOf(pageItemData.pdfIndex));
        }
        this.mImportPdfThumbnail.setVisibleRect(200);
        this.mImportPdfThumbnail.setData(getDirPath() + File.separator + list.get(0).pdfId, linkedHashMap);
    }

    public void getPdfThumbnailLoadStatus(PdfThumbnailLoadListener pdfThumbnailLoadListener) {
        this.mPdfThumbnailLoadListener = pdfThumbnailLoadListener;
        if (checkLoadFinish()) {
            loadFinish();
        } else {
            loadPdfThumbnail(this.mList);
            bindItemDataForExport();
        }
    }

    public void getMultiPageThumbnailStatus(PdfThumbnailLoadListener pdfThumbnailLoadListener) {
        this.mPdfThumbnailLoadListener = pdfThumbnailLoadListener;
        this.mPdfThumbnailIsLoaded = true;
        if (checkLoadFinish()) {
            loadFinish();
        } else {
            bindItemDataForExport();
        }
    }

    public int getWindowWidth() {
        return this.windowInfo.mWindowWidth;
    }

    private class WindowInfo {
        private int mWindowWidth;

        private WindowInfo() {
            this.mWindowWidth = -1;
            int realWidth = UIUtils.getRealWidth(PageInkModel.this.mContextRef.get());
            int realHeight = UIUtils.getRealHeight(PageInkModel.this.mContextRef.get());
            if (PageInkModel.this.currentItemView != null && PageInkModel.this.currentItemView.getWidth() != 0 && PageInkModel.this.currentItemView.getHeight() != 0) {
                realWidth = PageInkModel.this.currentItemView.getWidth();
                realHeight = PageInkModel.this.currentItemView.getHeight();
            }
            onSizeChanged(realWidth, realHeight);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSizeChanged(int i, int i2) {
            this.mWindowWidth = Math.max(i, i2 + PageInkModel.this.mPageInkView.getToolViewHeight());
            if (PageInkModel.this.isUnlimited) {
                this.mWindowWidth = i;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2, types: [java.lang.String] */
    @Override // xc.engine.math.iink.callback.MathSelectCallback
    public void onMathSelectRequired(RectF rectF, List<DataCurve> list) {
        String str = TAG;
        Log.d(str, "=======onMathSelectRequired START=======");
        try {
            try {
                this.mMathCurves = list;
                setMathAutoSelectFlag(true);
                Log.d(str, "onMathSelectRequired: selectByRect =" + selectByRect(rectF, list, false));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mMathCurves = null;
            this = TAG;
            Log.d(this, "=======onMathSelectRequired END=======");
        } catch (Throwable th) {
            this.mMathCurves = null;
            throw th;
        }
    }

    @Override // xc.engine.math.iink.callback.MathSelectCallback
    public void onExitSelect() {
        Log.w("MathSelect", "check onExitSelect");
        getMiCurveEngine().getMathDataManager().setSelectState(0);
        setSelect(false);
    }

    public void generateBrushPreview(CurveProp curveProp, Bitmap bitmap) {
        if (this.currentItemView != null) {
            this.currentItemView.generateBrushPreviewBitmap(curveProp, bitmap);
        }
    }

    public PageAdapter getAdapter() {
        return this.adapter;
    }

    public void bindItemDataForExport() {
        if (this.adapter != null) {
            Log.d(TAG, String.format("bindItemData: %d %d", Integer.valueOf(this.currentPosition), Integer.valueOf(this.mList.size())));
            List<Integer> list = (List) this.mList.stream().filter(new Predicate() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return PageInkModel.lambda$bindItemDataForExport$14((PageItemData) obj);
                }
            }).map(new Function() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((PageItemData) obj).getPageIndex());
                }
            }).collect(Collectors.toList());
            if (list.isEmpty()) {
                bindExportFinish();
            } else {
                this.adapter.bindItemForExport(this.currentItemView, list);
            }
        }
    }

    static /* synthetic */ boolean lambda$bindItemDataForExport$14(PageItemData pageItemData) {
        return !pageItemData.hadLoad;
    }

    public void bindExportFinish() {
        this.mExportLoaded = true;
        loadFinish();
    }

    private boolean checkLoadFinish() {
        return this.mExportLoaded && this.mPdfThumbnailIsLoaded;
    }

    private void loadFinish() {
        if (checkLoadFinish()) {
            this.currentItemView.postDelayed(new Runnable() { // from class: com.miui.libhandwritingpage.model.PageInkModel$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m848x9f5cc4d();
                }
            }, 2000L);
        }
    }

    /* JADX INFO: renamed from: lambda$loadFinish$15$com-miui-libhandwritingpage-model-PageInkModel, reason: not valid java name */
    /* synthetic */ void m848x9f5cc4d() {
        PdfThumbnailLoadListener pdfThumbnailLoadListener = this.mPdfThumbnailLoadListener;
        if (pdfThumbnailLoadListener != null) {
            pdfThumbnailLoadListener.onFinish();
        }
    }

    public void bindPageImage(int i) {
        if (this.isUnlimited) {
            return;
        }
        if ((this.currentPosition >= 3 || i >= 3) && this.currentItemView.getCreationEngine().getCurveEngine().getSpannedLayoutModel() != null) {
            this.currentItemView.getCreationEngine().getCurveEngine().getSpannedLayoutModel().bindExportImage(i);
        }
    }

    public MiCurveEngine getMiCurveEngine() {
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView == null || pageDrawView.getCreationEngine() == null) {
            return null;
        }
        return this.mDrawView.getCreationEngine().getCurveEngine();
    }
}
