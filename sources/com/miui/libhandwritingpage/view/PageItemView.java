package com.miui.libhandwritingpage.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.view.ContextThemeWrapper;
import com.miui.common.stat.NotesTextStat;
import com.miui.libhandwritingpage.R;
import com.miui.libhandwritingpage.listener.StepChangedListener;
import com.miui.libhandwritingpage.model.PageInkModel;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.SearchWrapper;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import com.miui.libhandwritingpage.view.listenerProxy.OnExtraDataListenerProxy;
import com.miui.libhandwritingpage.view.listenerProxy.OnSelectModeListenerProxy;
import com.miui.libhandwritingpage.view.select.SelectViewGroup;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.appcompat.app.ProgressDialog;
import xc.engine.api.CreationPageSDK;
import xc.engine.api.ICreationEngine;
import xc.engine.core.algorithm.estimate.ParamsEstimate;
import xc.engine.core.algorithm.recognize.ParamsRecognizeShape;
import xc.engine.core.graphics.engine.MiCurveEngine;
import xc.engine.core.graphics.engine.systems.SelectSystem;
import xc.engine.data.CsBufferBean;
import xc.engine.data.CurveProp;
import xc.engine.data.IDataListener;
import xc.engine.data.OnExtraDataListener;
import xc.engine.data.PathInfo;
import xc.engine.data.pagedata.DataCurve;
import xc.engine.data.pagedata.ICurve;
import xc.engine.data.shape.ShapeProp;
import xc.engine.operate.canvas.draw.scale.ScaleInfo;
import xc.engine.operate.canvas.track.delete.DeleteProp;
import xc.engine.operate.edit.select.ISelectOperator;
import xc.engine.operate.edit.select.OnSelectModelListener;
import xc.engine.operate.edit.select.SRectF;
import xc.engine.operate.edit.select.ShapeEditAttr;
import xc.engine.operate.edit.select.limit.SelectLimit;
import xc.engine.operate.step.type.IStep;
import xc.engine.operate.touch.TouchPoint;
import xc.engine.utils.EngineConfig;
import xc.engine.utils.FileUtil;
import xc.engine.utils.PageSizeConfig;
import xc.engine.utils.XLog;
import xc.richtext.api.ItemSpannedData;
import xc.richtext.api.OnSpannedEditListener;
import xc.richtext.api.OnSpannedHightListener;
import xc.richtext.api.SpannedEditLayout;
import xc.richtext.api.SpannedEditModel;
import xc.richtext.api.SpannedLayoutInterface;
import xc.richtext.data.SpannedData;
import xc.richtext.data.TextInfo;
import xc.richtext.data.bean.BitmapBean;
import xc.richtext.data.bean.IBufferBean;
import xc.richtext.operate.edit.ISpannedEditObject;
import xc.richtext.operate.edit.ISpannedSelectObject;

/* JADX INFO: loaded from: classes3.dex */
public class PageItemView extends RelativeLayout implements OnSelectModelListener, OnExtraDataListener, SelectSystem.RenderTaskListener {
    private static final String TAG = "PageItemView";
    private float bottomShieldHeight;
    private final Path canvasPath;
    private final float[] canvasRadius;
    private float curX;
    private float curY;
    private int drawState;
    private String entFilePath;
    private float filletRadius;
    private int index;
    private boolean isBindSurfaceView;
    private boolean isEngineLoading;
    private boolean isInitNotFinished;
    private boolean isScaleWaitEnd;
    private boolean isUnLimit;
    public boolean isUnLimitToNormal;
    private boolean isVisible;
    private boolean lastEnableEstimate;
    private final ParamsEstimate lastEstimateParams;
    private float lastOffsetX;
    private float lastOffsetY;
    private float lastScale;
    private final ScaleInfo lastScaleInfo;
    private final RectF layoutRectF;
    private boolean loading;
    private final WeakReference<Context> mContextRef;
    private PageDrawView mDrawView;
    private final Handler mHandler;
    private SelectViewGroup mMultiSelectViewGroup;
    private RectF mPageLocation;
    private ProgressBar mProgressBar;
    private SpannedEditLayout mSpannedLayout;
    private TextView mTvLoading;
    public PageInkModel model;
    private int position;
    private ProgressDialog renderingDialog;
    private final ScaleInfo scaleInfo;
    private int searchAlpha;
    private int searchColor;
    private final List<Rect> searchDataList;
    private SearchWrapper searchWrapper;
    private int shieldWidth;
    private StepChangedListener stepChangedListener;
    private int unLimitHeight;
    private int unLimitWidth;
    private int viewWidth;
    private int visibleHeight;
    private int visibleWidth;

    private int changedToCurveType(int i) {
        if (i != 7) {
            return i != 11 ? 1 : 11;
        }
        return 7;
    }

    private int changedToSpannedType(short s) {
        if (s != 7) {
            return s != 11 ? 0 : 11;
        }
        return 7;
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onCanvasChanged() {
    }

    public void remove() {
    }

    public void setPdfData(String str, int i) {
    }

    public void setSelectGroup(SelectViewGroup selectViewGroup) {
    }

    public PageItemView(Context context) {
        super(context);
        this.searchWrapper = new SearchWrapper();
        this.isUnLimit = false;
        this.isBindSurfaceView = false;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.layoutRectF = new RectF();
        this.searchDataList = new ArrayList();
        this.lastEnableEstimate = false;
        this.lastEstimateParams = new ParamsEstimate();
        this.index = -1;
        this.searchColor = -1;
        this.searchAlpha = 255;
        this.canvasPath = new Path();
        this.canvasRadius = new float[8];
        this.position = -1;
        this.scaleInfo = new ScaleInfo(1.0f, new PointF(), new PointF());
        this.lastScaleInfo = new ScaleInfo(1.0f, new PointF(), new PointF());
        this.mContextRef = new WeakReference<>(context);
    }

    public void init(PageDrawView pageDrawView) {
        Log.d(TAG, "init");
        this.isInitNotFinished = true;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.mDrawView = pageDrawView;
        pageDrawView.getView().setLayoutParams(layoutParams);
        this.mDrawView.bindItemView(this);
        this.isBindSurfaceView = true;
        initDrawView(layoutParams);
        initSpanned(layoutParams);
        initLoadingIcon(false);
        this.loading = true;
        this.mDrawView.getCreationEngine().getCurveEngine().setSpannedLayoutModel(this.mSpannedLayout.getSpannedLayoutModel());
        getCreationEngine().getCurveEngine().getSelectSystem().setRenderTaskListener(this);
    }

    private void initDrawView(RelativeLayout.LayoutParams layoutParams) {
        this.mDrawView.getCreationEngine().getSelectOperator().setSelectListenerI(new OnSelectModeListenerProxy(this));
        this.mDrawView.getCreationEngine().getDataAccess().setOnExtraDataListener(new OnExtraDataListenerProxy(this));
        this.mDrawView.getCreationEngine().getCanvasOperator().setScaleMode(true);
        this.mDrawView.getCreationEngine().getCanvasOperator().setInfiniteCanvasMode(this.isUnLimit || this.isUnLimitToNormal);
        this.mDrawView.getCreationEngine().getCanvasOperator().setInfiniteCanvasDebuggable(false);
        this.mDrawView.getCreationEngine().getEditOperator().enableSelectedContour(true);
        this.mDrawView.getCreationEngine().getEditOperator().setSelectScaleLimit(initSelectLimit());
        this.mDrawView.getCreationEngine().getSelectOperator().setRenderTaskListener(this);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TextView textView = this.mTvLoading;
        if (textView != null) {
            textView.setText(R.string.creation_edit_loading);
        }
        if (this.mDrawView.getCreationEngine().getCurveEngine().isInfinite()) {
            return;
        }
        this.mDrawView.getCreationEngine().getCurveEngine().getCurveSystem().update(true);
    }

    public SelectLimit initSelectLimit() {
        SelectLimit selectLimit = new SelectLimit();
        selectLimit.setLimitMode(1);
        selectLimit.setPenSizeLimit(1, 60.0f, 0.1f);
        selectLimit.setPenSizeLimit(2, 60.0f, 0.1f);
        selectLimit.setPenSizeLimit(3, 60.0f, 0.1f);
        selectLimit.setPenSizeLimit(4, 70.0f, 0.1f);
        selectLimit.setPenSizeLimit(6, 15.0f, 0.0f);
        return selectLimit;
    }

    public List<SpannedData> getSelectSpanList() {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null && selectViewGroup.getSelectView() != null && this.mMultiSelectViewGroup.getSelectView().mSpannedMode != null) {
            return this.mMultiSelectViewGroup.getSelectView().mSpannedMode.getSelectedList();
        }
        return new ArrayList();
    }

    public List<ICurve> getAllSelectCurves() {
        return this.mDrawView.getCreationEngine().getSelectOperator().getCurrentAllSelectCurves();
    }

    public void initLoadingIcon(boolean z) {
        TextView textView = this.mTvLoading;
        if (textView != null) {
            removeView(textView);
        }
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null) {
            removeView(progressBar);
        }
        if (getContext() == null) {
            return;
        }
        this.mProgressBar = new ProgressBar(new ContextThemeWrapper(getContext(), z ? miuix.appcompat.R.style.Theme_Dark : miuix.appcompat.R.style.Theme_Light));
        this.mTvLoading = new TextView(new ContextThemeWrapper(getContext(), z ? miuix.appcompat.R.style.Theme_Dark : miuix.appcompat.R.style.Theme_Light));
        this.mProgressBar.setLayoutParams(new RelativeLayout.LayoutParams(-1, getContext().getResources().getDimensionPixelSize(R.dimen.creation_loading_icon_height)));
        this.mTvLoading.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        this.mTvLoading.setText(R.string.creation_edit_loading);
        this.mTvLoading.setPadding(0, getContext().getResources().getDimensionPixelSize(R.dimen.creation_loading_icon_text_padding), 0, 0);
        handleLoading(this.loading);
        addView(this.mProgressBar);
        addView(this.mTvLoading);
    }

    public void showLoadingBar(boolean z) {
        this.loading = z;
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar == null || this.mTvLoading == null) {
            return;
        }
        int i = z ? 0 : 4;
        progressBar.setVisibility(i);
        this.mTvLoading.setVisibility(i);
    }

    public void onCurrentPageChanged(int i) {
        this.model.onCurrentPageChanged(i);
        this.mSpannedLayout.mSpannedLayoutModel.onPageChanged(i);
    }

    public void onPDFOrEngineLoadFinish(boolean z) {
        handleLoading(false);
    }

    public void dataLoadFinish() {
        this.mHandler.post(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m858x85432c72();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$dataLoadFinish$0$com-miui-libhandwritingpage-view-PageItemView, reason: not valid java name */
    /* synthetic */ void m858x85432c72() {
        this.model.dataLoadFinish();
    }

    public void setSearchWrapper(SearchWrapper searchWrapper) {
        this.searchWrapper = searchWrapper;
    }

    public boolean hoverEvent(MotionEvent motionEvent, boolean z, float f) {
        return this.mDrawView.getCreationEngine().hoverEvent(motionEvent, z, f);
    }

    private void initSpanned(RelativeLayout.LayoutParams layoutParams) {
        SpannedEditLayout spannedEditLayout = new SpannedEditLayout(this.mContextRef.get(), this.mDrawView.getCreationEngine().getCurveEngine().getRenderService().getProvider());
        this.mSpannedLayout = spannedEditLayout;
        spannedEditLayout.setUnLimit(this.isUnLimit);
        SpannedEditLayout spannedEditLayout2 = this.mSpannedLayout;
        final PageInkModel pageInkModel = this.model;
        Objects.requireNonNull(pageInkModel);
        spannedEditLayout2.setGetToolViewHeight(new SpannedLayoutInterface() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda2
            @Override // xc.richtext.api.SpannedLayoutInterface
            public final int getToolBarHeight() {
                return pageInkModel.getToolViewHeight();
            }
        });
        this.mSpannedLayout.setLayoutParams(layoutParams);
        if (this.isBindSurfaceView) {
            this.mSpannedLayout.addView(this.mDrawView.getView());
        }
        addView(this.mSpannedLayout);
        this.mSpannedLayout.getSpannedEditModel().setModeEditListener(new OnSpannedEditListener() { // from class: com.miui.libhandwritingpage.view.PageItemView.1
            @Override // xc.richtext.api.OnSpannedEditListener
            public void onStepChanged(int i, int i2, boolean z) {
                if (PageItemView.this.stepChangedListener != null) {
                    PageItemView.this.stepChangedListener.onSpanStepChanged(i, i2, z);
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject) {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.onSpannedSelected(iSpannedSelectObject == null ? new RectF() : iSpannedSelectObject.getSelectRectF());
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSpannedSelected(ISpannedSelectObject iSpannedSelectObject, boolean z) {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.onSpannedSelected(iSpannedSelectObject == null ? new RectF() : iSpannedSelectObject.getSelectRectF(), z);
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onFinishSelect() {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.onFinishSelect();
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSelectedClick() {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.onSelectedClick();
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSpannedEdited(ISpannedEditObject iSpannedEditObject) {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.onSpannedEdited(iSpannedEditObject);
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onCopyData(List<SpannedData> list) {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.onCopyData(list);
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onCopyData(boolean z, List<SpannedData> list) {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.onCopyData(z, list);
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onLoadFinish(boolean z) {
                if (PageItemView.this.drawState == 0) {
                    PageItemView.this.drawState = 2;
                } else if (PageItemView.this.drawState == 1) {
                    PageItemView.this.setSearchRectList(true, 4);
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onStartAction() {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.onStartAction();
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void onSelectCount(int i, String str) {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.onSelectCount(i, str);
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void cancelSelectAction(float f, float f2) {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.cancelSelectAction(f, f2);
                }
            }

            @Override // xc.richtext.api.OnSpannedEditListener
            public void cancelSoftKeyboard() {
                if (PageItemView.this.mMultiSelectViewGroup != null) {
                    PageItemView.this.mMultiSelectViewGroup.cancelSoftKeyboard();
                }
            }
        });
    }

    public SpannedEditModel getMode() {
        return this.mSpannedLayout.getSpannedEditModel();
    }

    public void update(int i, RectF rectF, float f, float f2) {
        this.curX = f;
        this.curY = f2;
        if (this.position != i) {
            this.searchDataList.clear();
        }
        if (rectF == null || rectF.isEmpty()) {
            this.layoutRectF.setEmpty();
        } else {
            this.layoutRectF.set(0.0f, Math.max(rectF.top, 0.0f), this.unLimitWidth, this.unLimitHeight);
        }
        postInvalidate();
        getMode().setCurrentPageNumber(i);
    }

    public void updateLayout(int i, int i2, int i3) {
        if (i == 0 || i2 == 0) {
            return;
        }
        this.unLimitWidth = i;
        this.unLimitHeight = i2;
        int i4 = this.layoutRectF.left < 0.0f ? 0 : (int) this.layoutRectF.left;
        int i5 = this.layoutRectF.top < 0.0f ? 0 : (int) this.layoutRectF.top;
        float f = i;
        int i6 = this.layoutRectF.right > f ? i : (int) this.layoutRectF.right;
        float f2 = i2;
        int i7 = this.layoutRectF.bottom > f2 ? i2 : (int) this.layoutRectF.bottom;
        if (this.layoutRectF.left < f && 0.0f < this.layoutRectF.right && this.layoutRectF.top < f2 && 0.0f < this.layoutRectF.bottom) {
            this.isVisible = true;
            if (!this.isUnLimit) {
                RectF rectF = this.mPageLocation;
                if (rectF == null) {
                    this.mPageLocation = new RectF(i4, i5, i6, i7);
                } else {
                    rectF.set(i4, i5, i6, i7);
                }
            }
            this.viewWidth = i6 - i4;
            int i8 = i7 - i5;
            if (i8 > this.mProgressBar.getHeight() * 2) {
                ProgressBar progressBar = this.mProgressBar;
                progressBar.layout((this.viewWidth - progressBar.getWidth()) / 2, (i8 - this.mProgressBar.getHeight()) / 2, (this.viewWidth + this.mProgressBar.getWidth()) / 2, (this.mProgressBar.getHeight() + i8) / 2);
                TextView textView = this.mTvLoading;
                textView.layout((this.viewWidth - textView.getWidth()) / 2, (this.mProgressBar.getHeight() + i8) / 2, (this.viewWidth + this.mTvLoading.getWidth()) / 2, ((i8 + this.mProgressBar.getHeight()) / 2) + this.mTvLoading.getHeight());
            } else {
                handleLoading(false);
            }
        } else {
            this.isVisible = false;
            this.viewWidth = i6 - i4;
        }
        this.visibleWidth = i;
        this.visibleHeight = i2;
        if (this.layoutRectF.width() > i3) {
            i3 = Math.min(i, (int) this.layoutRectF.width());
        }
        if (this.isUnLimit) {
            this.mSpannedLayout.getSpannedEditModel().forceChildLayout((int) this.curX, (int) this.curY, i3, i2);
            return;
        }
        this.mSpannedLayout.getSpannedEditModel().forceChildLayout(0, 0, i3, i2);
        if (getContext() != null) {
            if ((UIUtils.isInMultiWindowMode(Utils.getActivity(getContext())) || UIUtils.isSmallWindowMode(getContext())) && !UIUtils.isActiveDekstopMode(getContext())) {
                this.mSpannedLayout.layout(0, 0, i3, i2);
            }
        }
    }

    private void updateShieldRound() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable2.setShape(0);
        gradientDrawable.setColor(getResources().getColor(R.color.creation_paint_scale_bg));
        gradientDrawable2.setColor(getResources().getColor(R.color.creation_paint_scale_bg));
    }

    public void setVisibleSize(int i, int i2) {
        handleLoading(true);
        this.mDrawView.getCreationEngine().getCanvasOperator().setVisibleSize(i, i2);
    }

    public RectF getLayout() {
        return new RectF(0.0f, 0.0f, getWidth(), getHeight());
    }

    private void cancelSelectingState() {
        if (getCreationEngine() != null) {
            getCreationEngine().setSelecting(false);
        }
    }

    public void cancelMathAnimation(MotionEvent motionEvent) {
        this.mDrawView.getCreationEngine().getCurveEngine().cancelMathAnimation();
    }

    public void onPageTouchEvent(MotionEvent motionEvent) {
        if (this.loading) {
            return;
        }
        cancelSelectingState();
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        if (motionEvent.getAction() == 0) {
            this.position = this.mDrawView.getCreationEngine().getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(new PointF(motionEvent.getX(), motionEvent.getY()));
        }
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout == null || spannedEditLayout.getSpannedEditModel().getSpannedSelectMode().isSelectMode()) {
            return;
        }
        this.mSpannedLayout.dispatchTouchEvent(motionEventObtain);
    }

    public boolean onTouchEventDraw(MotionEvent motionEvent, boolean z) {
        return this.mDrawView.onTouchEvent(motionEvent, z);
    }

    public void setPenType(CurveProp curveProp) {
        if (this.mDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().getTouchOperator().setPenProp(curveProp);
            this.mDrawView.getCreationEngine().getTouchOperator().setOperatorMode(2);
        }
    }

    public void initPreviewBitmap(CurveProp curveProp, Bitmap bitmap) {
        if (this.mDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().getCanvasOperator().initPreviewBitmap(curveProp, bitmap);
        }
    }

    public void generateBrushPreviewBitmap(CurveProp curveProp, Bitmap bitmap) {
        if (this.mDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().getCanvasOperator().generateBrushPreviewBitmap(curveProp, bitmap);
        }
    }

    public void setClearType(DeleteProp deleteProp) {
        if (this.mDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().setDeleteProp(deleteProp);
            this.mDrawView.getCreationEngine().getTouchOperator().setOperatorMode(1);
        }
    }

    public void setClear(int i, boolean z) {
        if (this.mDrawView.getCreationEngine() != null) {
            this.mSpannedLayout.getSpannedEditModel().clearPage(i, z);
            if (z) {
                this.mDrawView.getCreationEngine().getEditOperator().clearI(i);
            }
        }
    }

    public void setSelectType(int i) {
        if (this.mDrawView.getCreationEngine().getSelectOperator() != null) {
            this.mDrawView.getCreationEngine().getSelectOperator().setSelectAction(i);
        }
    }

    public void enableEstimate(boolean z, ParamsEstimate paramsEstimate) {
        if (this.mDrawView.getCreationEngine() != null) {
            if (paramsEstimate != null) {
                if (this.lastEnableEstimate == z && this.lastEstimateParams.getTime() == paramsEstimate.getTime() && this.lastEstimateParams.getHistoryCount() == paramsEstimate.getHistoryCount() && this.lastEstimateParams.getMiniTrace() == paramsEstimate.getMiniTrace()) {
                    return;
                }
                enableEstimateEngine(z, paramsEstimate);
                this.lastEnableEstimate = z;
                this.lastEstimateParams.set(paramsEstimate);
                return;
            }
            enableEstimateEngine(z, null);
            this.lastEnableEstimate = z;
        }
    }

    private void enableEstimateEngine(boolean z, ParamsEstimate paramsEstimate) {
        this.mDrawView.getCreationEngine().getAlgorithmInterface().enableEstimate(z);
        this.mDrawView.getCreationEngine().getAlgorithmInterface().setEstimateParams(paramsEstimate);
    }

    public void onCancelEvent() {
        if (this.mDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().getTouchOperator().onCancelEvent();
            this.mDrawView.getCreationEngine().getCurveEngine().getSelectSystem().clearTransformInfo();
            getMode().onCancelEdit();
            getMode().finishEdit();
        }
    }

    public void setOneShape(boolean z, ParamsRecognizeShape paramsRecognizeShape) {
        if (this.mDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().getAlgorithmInterface().setShapeRecognizeParams(paramsRecognizeShape);
            this.mDrawView.getCreationEngine().getAlgorithmInterface().enableShapeRecognize(z);
        }
    }

    public void setEngineTouchConfig(EngineConfig engineConfig) {
        if (engineConfig == null) {
            return;
        }
        if (this.mDrawView.getCreationEngine() != null) {
            if (this.model.getEngineConfig().operatorMode == 1) {
                this.mDrawView.getCreationEngine().setDeleteProp(engineConfig.deleteProp);
                this.mDrawView.getCreationEngine().getTouchOperator().setOperatorMode(1);
            } else if (this.model.getEngineConfig().operatorMode == 2) {
                this.mDrawView.getCreationEngine().getTouchOperator().setPenProp(engineConfig.penProp);
                this.mDrawView.getCreationEngine().getTouchOperator().setOperatorMode(2);
            }
        }
        enableEstimate(engineConfig.enableEstimate, engineConfig.estimateParams);
        setOneShape(engineConfig.oneShape, engineConfig.oneShapeParams);
        if (this.mDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().getCanvasOperator().setCanSelectInvisibleData(engineConfig.canSelectInvisibleData);
        }
    }

    public void setSelectView(SelectViewGroup selectViewGroup) {
        this.mMultiSelectViewGroup = selectViewGroup;
    }

    public void setStepChangedCallback(StepChangedListener stepChangedListener) {
        this.stepChangedListener = stepChangedListener;
    }

    public ICreationEngine getCreationEngine() {
        return this.mDrawView.getCreationEngine();
    }

    public MiCurveEngine getCurveEngine() {
        ICreationEngine creationEngine = getCreationEngine();
        if (creationEngine == null) {
            return null;
        }
        return creationEngine.getCurveEngine();
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelected(SRectF sRectF, List<DataCurve> list) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onObjectSelected(sRectF, list);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelected(SRectF sRectF, List<DataCurve> list, boolean z) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onObjectSelected(sRectF, list, z);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onDataCopied(int i, byte[] bArr, int i2) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onDataCopied(i, bArr, i2);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onSelectLimit(int i, float f) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onSelectLimit(i, f);
        }
    }

    @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
    public void onStepChanged(int i, int i2, boolean z) {
        this.loading = false;
        StepChangedListener stepChangedListener = this.stepChangedListener;
        if (stepChangedListener != null) {
            stepChangedListener.onStepChanged(i, i2, z);
        }
    }

    @Override // xc.engine.operate.edit.select.onSelectStepChangedListener
    public void onPageSizeChanged(PageSizeConfig pageSizeConfig) {
        StepChangedListener stepChangedListener = this.stepChangedListener;
        if (stepChangedListener != null) {
            stepChangedListener.onPageSizeChanged(pageSizeConfig);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onCancelSelect() {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onCancelSelect();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelectedPath(float f, float f2, Path path, boolean z) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onObjectSelectedPath(f, f2, path, z);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onObjectSelectedPathForOutLine(float f, float f2, Path path, boolean z) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onObjectSelectedPathForOutLine(f, f2, path, z);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onStartAction(int i) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onStartAction(i);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onDoingAction(int i, SRectF sRectF) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onDoingAction(i, sRectF);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onEndAction(int i) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onEndAction(i);
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onSelectedHashCode(int i) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onSelectedHashCode(i);
        }
    }

    public boolean canUndo() {
        if (this.mDrawView.getCreationEngine() != null) {
            return this.mDrawView.getCreationEngine().getStepManager().canUndo();
        }
        return false;
    }

    public boolean canRedo() {
        if (this.mDrawView.getCreationEngine() != null) {
            return this.mDrawView.getCreationEngine().getStepManager().canRedo();
        }
        return false;
    }

    public void undo(boolean z) {
        if (canUndo()) {
            Log.d(TAG, "undo ");
            this.loading = true;
            this.mDrawView.getCreationEngine().getSelectOperator().cancelSelect(false);
            getMode().finishEdit();
            this.mDrawView.getCreationEngine().getStepManager().undo(z);
        }
    }

    public void redo(boolean z) {
        if (canRedo()) {
            Log.d(TAG, NotesTextStat.REDO);
            this.loading = true;
            this.mDrawView.getCreationEngine().getSelectOperator().cancelSelect(false);
            getMode().finishEdit();
            this.mDrawView.getCreationEngine().getStepManager().redo(z);
        }
    }

    public void undoSpanned(boolean z) {
        if (this.mSpannedLayout.getSpannedEditModel() != null) {
            this.mSpannedLayout.getSpannedEditModel().getSpannedSelectMode().finishSelect();
            this.mSpannedLayout.getSpannedEditModel().undo(z);
        }
    }

    public void resetSelectData() {
        if (this.mSpannedLayout.getSpannedEditModel() != null) {
            this.mSpannedLayout.getSpannedEditModel().getSpannedSelectMode().resetSelect();
        }
    }

    public void redoSpanned(boolean z) {
        if (this.mSpannedLayout.getSpannedEditModel() != null) {
            this.mSpannedLayout.getSpannedEditModel().getSpannedSelectMode().finishSelect();
            this.mSpannedLayout.getSpannedEditModel().redo(z);
        }
    }

    public void doScale(float f, int i) {
        if (getCreationEngine() != null) {
            getCreationEngine().setSelecting(false);
        }
        if (this.visibleWidth == 0) {
            return;
        }
        if (!this.isScaleWaitEnd) {
            this.isScaleWaitEnd = true;
        }
        float scaleRate = f / this.lastScaleInfo.getScaleRate();
        if (this.isUnLimit || this.isUnLimitToNormal) {
            if (this.isUnLimitToNormal) {
                this.lastOffsetX = Math.min(this.layoutRectF.left, 0.0f);
                this.lastOffsetY = Math.min(this.curY, 0.0f);
            } else {
                this.lastOffsetY = this.curY;
                this.lastOffsetX = this.curX;
            }
        } else {
            this.lastOffsetX = Math.min(this.layoutRectF.left, 0.0f);
            this.lastOffsetY = this.isVisible ? Math.min(this.layoutRectF.top, 0.0f) : 0.0f;
        }
        this.scaleInfo.setScaleRate(scaleRate);
        this.scaleInfo.getScaleCenter().set(0.0f, 0.0f);
        this.scaleInfo.getTranslate().x = (this.lastOffsetX / scaleRate) - this.lastScaleInfo.getTranslate().x;
        this.scaleInfo.getTranslate().y = (this.lastOffsetY / scaleRate) - this.lastScaleInfo.getTranslate().y;
        if (this.mDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().getCanvasOperator().doScale(this.scaleInfo);
            this.mDrawView.getCreationEngine().getCurveEngine().getHighLightSystem().shutdownHighlight();
        }
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView != null) {
            pageDrawView.onCanvasDataChanged(null, 11);
        }
        this.lastScale = f;
    }

    public void onScrollEnd(float f, int i) {
        doScale(f, i);
        this.lastScaleInfo.setScaleRate(this.lastScale);
        this.lastScaleInfo.getTranslate().set(this.lastOffsetX, this.lastOffsetY);
        handleLoading(false);
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView == null) {
            return;
        }
        pageDrawView.onCanvasDataChanged(null, 11);
        this.isScaleWaitEnd = false;
        if (this.mDrawView.getCreationEngine().getCurveEngine().isInfinite()) {
            this.mDrawView.getCreationEngine().getCurveEngine().getCurveSystem().update(true);
        }
    }

    public void setEntPath(String str) {
        String entPathByDir = FileUtil.getEntPathByDir(str);
        if (entPathByDir != null) {
            str = entPathByDir;
        }
        this.entFilePath = str;
    }

    public void bindEngineData(PageItemData pageItemData) {
        if (pageItemData == null || pageItemData.engineData == null) {
            return;
        }
        this.isEngineLoading = true;
        this.mDrawView.getCreationEngine().getDataAccess().loadPathInfo(pageItemData.engineData, pageItemData.redoList, pageItemData.undoList, pageItemData.getPageIndex());
    }

    public void bindSpannedEngineData(PageItemData pageItemData) {
        this.mSpannedLayout.getEngineBindContext().getSpannedMap(pageItemData.getPageIndex()).bindSpannedInfo(pageItemData.spannedData.getSpannedInfo());
    }

    public PathInfo unBindEngineData() {
        return this.mDrawView.getCreationEngine().getDataAccess().getCurrentPathInfo();
    }

    public List<IStep> unBindRedoList() {
        return this.mDrawView.getCreationEngine().getDataAccess().getRedoList();
    }

    public List<IStep> unBindUndoList() {
        return this.mDrawView.getCreationEngine().getDataAccess().getUndoList();
    }

    public ItemSpannedData unBindSpannedEngineData(int i) {
        return this.mSpannedLayout.getEngineBindContext().unBindSpannedEngineData(i);
    }

    public void spannedEngineClear(int i) {
        getMode().removeSpannedItemView();
        this.mSpannedLayout.getEngineBindContext().getSpannedMap(i).clear();
        getMode().resetViewData();
    }

    public void cancelSelect(boolean z) {
        ISelectOperator selectOperator;
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView == null || pageDrawView.getCreationEngine() == null || (selectOperator = this.mDrawView.getCreationEngine().getSelectOperator()) == null) {
            return;
        }
        selectOperator.cancelSelect(z);
    }

    public void setOperatedType(int i) {
        this.mDrawView.getCreationEngine().setOperatedModeType(i);
    }

    public boolean isShapeEditMode() {
        return this.mDrawView.getCreationEngine().getSelectOperator().isShapeEditMode();
    }

    public void setSelectRectF(SRectF sRectF) {
        this.mDrawView.getCreationEngine().getSelectOperator().setMultiSelectRectF(sRectF);
    }

    public void onDrawCurSelected(Canvas canvas) {
        this.mDrawView.getCreationEngine().getSelectOperator().onDraw(canvas);
    }

    public void doDelete(boolean z) {
        this.mDrawView.getCreationEngine().getSelectOperator().doDelete(z);
    }

    public void doDelete(boolean z, boolean z2) {
        this.mDrawView.getCreationEngine().getSelectOperator().doDelete(z, z2);
    }

    public void deleteCurve() {
        this.mDrawView.getCreationEngine().getSelectOperator().doDeleteJustCurve();
    }

    public void doCopy(boolean z) {
        this.mDrawView.getCreationEngine().getSelectOperator().doCopy(z);
    }

    public void doCopyAndPaste(boolean z) {
        this.mDrawView.getCreationEngine().getSelectOperator().doCopyAndPaste(z);
    }

    public void setPaste(byte[] bArr, float f, float f2) {
        this.mDrawView.getCreationEngine().getSelectOperator().setPaste(bArr, f, f2);
    }

    public void save(PageItemData pageItemData) {
        synchronized (pageItemData.lock) {
            getCreationEngine().waitForIdle();
            String str = pageItemData.filePath;
            String str2 = TAG;
            Log.i(str2, String.format("save page[%d]", Integer.valueOf(pageItemData.getPageIndex())));
            if (!TextUtils.isEmpty(str) && this.mDrawView.getCreationEngine() != null && this.mDrawView.getCreationEngine().getDataAccess().saveEntFileSync(str, pageItemData.getPageIndex())) {
                pageItemData.isModified = false;
                Log.e(str2, String.format("save set page[%d] modified[false]", Integer.valueOf(pageItemData.getPageIndex())));
            }
        }
    }

    public void resetScale() {
        if (this.mDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().getCanvasOperator().resetScale();
            this.mDrawView.getCreationEngine().getCanvasOperator().doScaleUp();
        }
        this.lastScale = 1.0f;
        this.lastOffsetX = 0.0f;
        this.lastOffsetY = 0.0f;
        this.lastScaleInfo.setScaleRate(1.0f);
        this.lastScaleInfo.getTranslate().set(this.lastOffsetX, this.lastOffsetY);
    }

    private void updatePosition(RectF rectF) {
        this.position = this.mDrawView.getCreationEngine().getCurveEngine().getPageSystem().getPageIndexByScreenSpacePoint(new PointF(rectF.left, rectF.top));
        Log.i(TAG, "updatePosition: position = " + this.position);
    }

    public void addBitmap(String str, RectF rectF, float f) {
        updatePosition(rectF);
        addBitmap(str, rectF, f, false);
    }

    public void addBitmap(String str, RectF rectF, float f, boolean z) {
        this.mDrawView.getCreationEngine().getSelectOperator().cancelSelect(false);
        if (getMode() != null) {
            updatePosition(rectF);
            getMode().getSpannedSelectMode().finishSelect();
            if (getCreationEngine().getCurveEngine() != null) {
                getCreationEngine().getCurveEngine().getSelectSystem().selectOff(null, false, 0);
            }
            SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
            if (spannedEditLayout != null) {
                spannedEditLayout.invalidate();
                updateSpanScaleInfo();
            }
            getMode().addBitmap(str, new RectF(rectF), f, z);
        }
    }

    public void addText(RectF rectF, String str) {
        this.mDrawView.getCreationEngine().getSelectOperator().cancelSelect(false);
        if (getMode() != null) {
            updatePosition(rectF);
            getMode().getSpannedSelectMode().finishSelect();
            SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
            if (spannedEditLayout != null) {
                spannedEditLayout.invalidate();
                updateSpanScaleInfo();
            }
            getMode().addText(rectF, str);
        }
    }

    public void doSelectByTouchPoint(List<TouchPoint> list, boolean z) {
        this.mDrawView.getCreationEngine().getEditOperator().doPathSelect(list, z, false);
    }

    public void addText(TextInfo textInfo) {
        if (this.mSpannedLayout.getSpannedEditModel() != null) {
            updatePosition(textInfo.getRectF());
            getMode().getSpannedSelectMode().finishSelect();
            SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
            if (spannedEditLayout != null) {
                spannedEditLayout.invalidate();
                updateSpanScaleInfo();
                this.mSpannedLayout.getSpannedEditModel().addText(textInfo);
            }
        }
    }

    public void updateSpanScaleInfo() {
        MiCurveEngine curveEngine = getCurveEngine();
        if (curveEngine != null) {
            PointF viewportOffset = curveEngine.getViewportOffset();
            this.mSpannedLayout.getSpannedEditModel().doScaleReal(curveEngine.getScaleRatio(), viewportOffset.x, viewportOffset.y, 0.0f, 0.0f);
        } else {
            this.mSpannedLayout.getSpannedEditModel().doScaleReal(this.lastScale, this.lastOffsetX, this.lastOffsetY, 0.0f, 0.0f);
        }
    }

    @Override // xc.engine.data.OnExtraDataListener
    public void onLoadListener(int i, final short[] sArr, final int i2, final List<byte[]> list) {
        if (this.model.getItemDataList().size() < i2 || i2 < 0) {
            return;
        }
        final PageItemData pageItemData = this.model.getItemDataList().get(i2);
        if (sArr == null || sArr.length == 0) {
            pageItemData.hadSpannedLoad = true;
        } else {
            CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m863x93940989(sArr, i2, list, pageItemData);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$onLoadListener$1$com-miui-libhandwritingpage-view-PageItemView, reason: not valid java name */
    /* synthetic */ void m863x93940989(short[] sArr, int i, List list, PageItemData pageItemData) {
        int[] iArr = new int[sArr.length];
        for (int i2 = 0; i2 < sArr.length; i2++) {
            iArr[i2] = changedToSpannedType(sArr[i2]);
        }
        this.mSpannedLayout.getEngineBindContext().getSpannedMap(i).loadTextFromBuffer(new File(this.entFilePath).getParent(), i, iArr, list);
        pageItemData.hadUnBindSpanned = false;
        pageItemData.hadSpannedLoad = true;
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void setSelectStateI(boolean z) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.setSelectStateI(z);
        }
    }

    @Override // xc.engine.data.OnExtraDataListener
    public CsBufferBean onSaveExtraData(int i) {
        IBufferBean spannedBuffer = this.mSpannedLayout.getEngineBindContext().getSpannedMap(i).getSpannedBuffer(this.entFilePath, i);
        int length = spannedBuffer.getTypes().length;
        int[] iArr = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            iArr[i2] = changedToCurveType(spannedBuffer.getTypes()[i2]);
        }
        return new CsBufferBean(iArr, spannedBuffer.getBuffers());
    }

    public void removeExtraData(int i) {
        boolean zRemovePage = this.mSpannedLayout.getEngineBindContext().removePage(i);
        Log.i(TAG, String.format("[removeExtraData] removePage[%d] result[%b]", Integer.valueOf(i), Boolean.valueOf(zRemovePage)));
        if (zRemovePage) {
            this.mSpannedLayout.getEngineBindContext().shiftPages(i, -(this.model.getItemDataList().get(i).maxHeight + 50.0f));
        }
    }

    public void release() {
        cancelSelect(false);
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView != null && pageDrawView.getCreationEngine() != null) {
            this.mDrawView.getCreationEngine().release();
        }
        this.mHandler.removeCallbacksAndMessages(null);
        setSearchRectList(true, 0);
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            spannedEditLayout.release();
            this.mSpannedLayout.removeAllViews();
            if (this.mSpannedLayout.getParent() != null) {
                ((ViewGroup) this.mSpannedLayout.getParent()).removeView(this.mSpannedLayout);
            }
            this.mSpannedLayout.setGetToolViewHeight(null);
        }
        if (this.mDrawView.getCreationEngine() != null && this.mDrawView.getCreationEngine().getSelectOperator() != null) {
            this.mDrawView.getCreationEngine().getSelectOperator().setRenderTaskListener(null);
        }
        getCreationEngine().getCurveEngine().getSelectSystem().setRenderTaskListener(null);
        ProgressDialog progressDialog = this.renderingDialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.renderingDialog.dismiss();
        }
        this.renderingDialog = null;
        WeakReference<Context> weakReference = this.mContextRef;
        if (weakReference != null) {
            weakReference.clear();
        }
    }

    public void setCanvasSize(float f, float f2) {
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView != null) {
            pageDrawView.setCanvasSize(f, f2);
        }
    }

    public int getVisWidth() {
        return this.visibleWidth;
    }

    public int getVisHeight() {
        return this.visibleHeight;
    }

    public boolean isCurveEmpty(int i) {
        return !this.mDrawView.getCreationEngine().getDataAccess().hasValidCurVeData(i);
    }

    public boolean isSpanEmpty(int i) {
        return this.mSpannedLayout.getEngineBindContext().getSpannedMap(i).isContentEmpty();
    }

    public boolean isContentEmpty(int i) {
        return isCurveEmpty(i) && isSpanEmpty(i);
    }

    public void invalidateDrawView() {
        Log.i(TAG, "invalidateDrawView: ");
        this.mDrawView.getView().postInvalidate();
    }

    public void invalidateDrawViewNoPost() {
        Log.i(TAG, "invalidateDrawViewNoPost: ");
        this.mDrawView.getView().invalidate();
    }

    public void setSearchRectList(boolean z, int i) {
        if (!z) {
            if (i == 0 || i == 1) {
                if (this.searchWrapper.searchRectMap != null) {
                    for (String str : this.searchWrapper.searchRectMap.keySet()) {
                        Integer.parseInt(str.split("-")[0]);
                        this.searchDataList.add(this.searchWrapper.searchRectMap.get(str));
                    }
                }
                this.searchColor = this.searchWrapper.searchColor;
            }
            this.searchAlpha = this.searchWrapper.searchAlpha;
        } else if (TextUtils.isEmpty(this.searchWrapper.searchText)) {
            this.searchDataList.clear();
            this.searchWrapper.searchRectMap = null;
            this.searchWrapper.searchText = null;
        }
        if (i == 0 || i == 4) {
            getMode().setHightText(this.searchWrapper.searchText, this.searchWrapper.searchColor, this.searchAlpha, new OnSpannedHightListener() { // from class: com.miui.libhandwritingpage.view.PageItemView.2
                @Override // xc.richtext.api.OnSpannedHightListener
                public void onSpannedHightChanged(SpannedData spannedData, List<? extends RectF> list, float f, float f2) {
                    PageItemView.this.mDrawView.getCreationEngine().getCurveEngine().getHighLightSystem().setSpanHighlightRect(spannedData, list, PageItemView.this.searchWrapper.searchColor, PageItemView.this.searchWrapper.searchAlpha, f, f2);
                }
            });
        }
    }

    public boolean isSingleCurves() {
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView == null || pageDrawView.getView() == null) {
            return false;
        }
        return this.mDrawView.getCreationEngine().getSelectOperator().isSingleCurves();
    }

    public Bitmap getBitmap() {
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView == null || pageDrawView.getView() != null) {
            return this.mDrawView.getCreationEngine().getSelectOperator().getSelectScreenshot();
        }
        return null;
    }

    public List<ICurve> getSelectCurves() {
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView == null || pageDrawView.getView() != null) {
            return this.mDrawView.getCreationEngine().getSelectOperator().getCurrentSelectCurves();
        }
        return null;
    }

    public void copyTempSelectData() {
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView == null || pageDrawView.getView() == null) {
            return;
        }
        this.mDrawView.getCreationEngine().getSelectOperator().copyTempSelectData();
    }

    public void load(String str, PageItemData pageItemData, PageItemView pageItemView, IDataListener iDataListener) {
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView == null || pageDrawView.getView() == null) {
            return;
        }
        if (str != null) {
            this.isEngineLoading = true;
            pageItemData.hadSpannedLoad = false;
            this.mDrawView.getCreationEngine().getDataAccess().setIDataListener(iDataListener);
            this.mDrawView.getCreationEngine().getDataAccess().loadEntFile(str, pageItemData.getPageIndex());
            return;
        }
        handleLoading(false);
        this.mDrawView.getCreationEngine().getDataAccess().loadPathInfo(null, null, null);
    }

    public void unBind() {
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView == null || pageDrawView.getView() == null) {
            return;
        }
        this.mDrawView.getCreationEngine().getDataAccess().clearEngineData();
    }

    public void handleLoading(final boolean z) {
        if (z) {
            XLog.v("", "show loading icon", new Object[0]);
        } else {
            XLog.v("", "hide loading icon", new Object[0]);
        }
        this.mHandler.post(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m860x239873eb(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$handleLoading$2$com-miui-libhandwritingpage-view-PageItemView, reason: not valid java name */
    /* synthetic */ void m860x239873eb(boolean z) {
        PageInkModel pageInkModel;
        showLoadingBar(z);
        if (z || (pageInkModel = this.model) == null) {
            return;
        }
        pageInkModel.pageLoadingFinish();
    }

    public void setRadius(float f) {
        this.filletRadius = f;
        Arrays.fill(this.canvasRadius, f);
        postInvalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (!this.canvasPath.isEmpty()) {
            canvas.clipPath(this.canvasPath);
        }
        super.dispatchDraw(canvas);
    }

    public void onCanvasChanged(int i, final int i2) {
        SelectViewGroup selectViewGroup;
        if (i == 12) {
            post(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m861xe67c015();
                }
            });
            this.mDrawView.onCanvasDataChanged(null, 11);
            if (this.isInitNotFinished) {
                return;
            }
            handleLoading(false);
            return;
        }
        if (i == 13) {
            setSearchHighLine(1);
            post(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m862x83e1e656(i2);
                }
            });
            this.mDrawView.onCanvasDataChanged(null, 11);
        } else {
            if (i != 11 || (selectViewGroup = this.mMultiSelectViewGroup) == null) {
                return;
            }
            selectViewGroup.onCanvasChanged();
        }
    }

    /* JADX INFO: renamed from: lambda$onCanvasChanged$3$com-miui-libhandwritingpage-view-PageItemView, reason: not valid java name */
    /* synthetic */ void m861xe67c015() {
        doScale();
        int i = this.drawState;
        if (i == 0) {
            this.drawState = 1;
        } else if (i == 2) {
            setSearchHighLine(0);
        }
    }

    /* JADX INFO: renamed from: lambda$onCanvasChanged$4$com-miui-libhandwritingpage-view-PageItemView, reason: not valid java name */
    /* synthetic */ void m862x83e1e656(int i) {
        Log.d(TAG, "onCanvasChanged: LOAD_DATA_FINISH");
        doScale();
        this.isEngineLoading = false;
        onPDFOrEngineLoadFinish(false);
        dataLoadFinish();
        this.mDrawView.getCreationEngine().onPageDataLoaded(i);
        this.isInitNotFinished = false;
    }

    private void setSearchHighLine(int i) {
        setSearchRectList(false, i);
        if (this.searchWrapper.searchRectMap == null || this.searchWrapper.searchRectMap.isEmpty()) {
            return;
        }
        this.mDrawView.getCreationEngine().getCurveEngine().getHighLightSystem().setCurveHighLightRect(new ArrayList(this.searchWrapper.searchRectMap.values()), this.searchColor, this.searchAlpha, new ArrayList(this.searchWrapper.searchRectMap.keySet()));
    }

    public void doScale() {
        if (this.mDrawView.getCreationEngine() != null) {
            ScaleInfo scaleInfo = new ScaleInfo();
            scaleInfo.setScaleRate(this.lastScaleInfo.getScaleRate());
            scaleInfo.getTranslate().set(this.lastScaleInfo.getTranslate().x / this.lastScaleInfo.getScaleRate(), this.lastScaleInfo.getTranslate().y / this.lastScaleInfo.getScaleRate());
            this.mDrawView.getCreationEngine().getCanvasOperator().resetScale();
            this.mDrawView.getCreationEngine().getCanvasOperator().doScale(scaleInfo);
            this.mDrawView.getCreationEngine().getCanvasOperator().doScaleUp();
        }
    }

    public void onPointLimit() {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.isPointLimit();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onStartPasteFromCopy() {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onStartPasteFromCopy();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void isPointLimit() {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.isPointLimit();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public boolean isPagePointLimit(int i) {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        return selectViewGroup != null && selectViewGroup.isPagePointLimit(i);
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void finishSelect() {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.finishSelect();
        }
    }

    @Override // xc.engine.operate.edit.select.OnSelectModelListener
    public void onSelectedDeletedEnd() {
        SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
        if (selectViewGroup != null) {
            selectViewGroup.onSelectedDeletedEnd();
        }
    }

    public boolean isLoading() {
        return this.loading;
    }

    public void updateBackground(String str, PageItemData pageItemData) {
        int i;
        if (pageItemData.bgConfig == null) {
            pageItemData.bgConfig = new BgGridConfiguration();
        }
        this.mDrawView.getCreationEngine().getCurveEngine().getBgSystem().updateStyles(pageItemData.bgColor, pageItemData.bgGrid);
        if (pageItemData.bgColor == -1) {
            i = 0;
        } else {
            i = pageItemData.bgColor == -1296 ? 1 : 2;
        }
        this.model.onTitleColorChanged(i, -1);
        if (TextUtils.isEmpty(pageItemData.bgImage) && TextUtils.isEmpty(pageItemData.pdfId)) {
            initLoadingIcon(false);
        } else {
            setShieldWidth(0);
            setBottomShieldHeight(0.0f);
        }
    }

    public boolean isTextEditing() {
        return getMode() != null && getMode().isTextEditing();
    }

    public void setSelectLimit(boolean z, float f) {
        this.mDrawView.getCreationEngine().getSelectOperator().setSelectLimit(z, f);
    }

    public float getCursorHeight() {
        if (getMode() != null) {
            return getMode().getEditTextCursorHeight();
        }
        return 0.0f;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public boolean isSelectReady() {
        return this.mDrawView.getCreationEngine().getSelectOperator().isSelectReady();
    }

    public Path getSelectPath() {
        return this.mDrawView.getCreationEngine().getSelectOperator().getSelectPath();
    }

    public float[] getEditPosition() {
        return this.mDrawView.getCreationEngine().getSelectOperator().getEditPoint();
    }

    public void setShapeEditType(int i, PointF pointF, PointF pointF2) {
        this.mDrawView.getCreationEngine().getSelectOperator().setShapeEditType(i, pointF, pointF2);
    }

    public boolean isCloseShape() {
        return this.mDrawView.getCreationEngine().getSelectOperator().isCloseShape();
    }

    public boolean isClickShape(float f, float f2) {
        return this.mDrawView.getCreationEngine().getEditOperator().isClickOnShape(f, f2);
    }

    public boolean isSelectedObjectValid() {
        return this.mDrawView.getCreationEngine().getSelectOperator().isSelectedObjectValid();
    }

    public boolean isSelectedContour() {
        return this.mDrawView.getCreationEngine().getSelectOperator().isSelectedContour();
    }

    public ShapeProp getShapeData() {
        return this.mDrawView.getCreationEngine().getSelectOperator().getSelectShapeProp();
    }

    public boolean isShowEditPoint() {
        return this.mDrawView.getCreationEngine().getSelectOperator().isShowEditPoint();
    }

    public RectF getSelectRect() {
        return this.mDrawView.getCreationEngine().getSelectOperator().getSelectRect();
    }

    public void setSelectShapeAttr(ShapeEditAttr shapeEditAttr, boolean z) {
        this.mDrawView.getCreationEngine().getSelectOperator().setSelectShapeAttr(shapeEditAttr, z);
    }

    public void insertShapeProp(RectF rectF, ShapeProp shapeProp) {
        if (rectF == null || rectF.isEmpty()) {
            return;
        }
        this.mDrawView.getCreationEngine().getSelectOperator().cancelSelect(false);
        if (getMode() != null) {
            updatePosition(rectF);
            getMode().getSpannedSelectMode().finishSelect();
        }
        this.mDrawView.getCreationEngine().getEditOperator().doInsertShape(this.position, rectF.centerX(), rectF.centerY(), rectF.width(), rectF.height(), shapeProp);
    }

    public void setSelectEnable(boolean z) {
        this.mDrawView.getCreationEngine().getSelectOperator().setEnableSelect(z);
    }

    public float getScale() {
        return this.layoutRectF.width() / this.visibleWidth;
    }

    public void update(int i, RectF rectF, float f, int i2, float f2, float f3) {
        update(i, rectF, f2, f3);
        doScale(f, i2);
    }

    public void unBindData() {
        load(null, null, null, null);
        spannedEngineClear(0);
        remove();
    }

    public int getPosition() {
        return this.position;
    }

    public void setBottomShieldHeight(float f) {
        this.bottomShieldHeight = Math.max(0.0f, f);
    }

    public void setShieldWidth(int i) {
        this.shieldWidth = Math.max(0, i);
    }

    public int getShieldWidth() {
        return this.shieldWidth;
    }

    public void setUnLimit(boolean z) {
        this.isUnLimit = z;
        PageDrawView pageDrawView = this.mDrawView;
        if (pageDrawView != null && pageDrawView.getView() != null) {
            this.mDrawView.getCreationEngine().getCanvasOperator().setInfiniteCanvasMode(z);
        }
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            spannedEditLayout.setUnLimit(z);
        }
    }

    public void moveAllSpanAfterPage(final int i, float f, float f2) {
        if (f != 0.0f) {
            for (SpannedData spannedData : this.mSpannedLayout.getEngineBindContext().getSpannedMap(i).getDataListAll()) {
                RectF rectF = spannedData.mRatioRectF;
                rectF.offset(f, 0.0f);
                spannedData.mRatioRectF.set(rectF);
                spannedData.setRectF(rectF);
            }
        }
        for (SpannedData spannedData2 : (List) this.mSpannedLayout.getEngineBindContext().getAllDataList().stream().filter(new Predicate() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return PageItemView.lambda$moveAllSpanAfterPage$5(i, (SpannedData) obj);
            }
        }).collect(Collectors.toList())) {
            RectF rectF2 = spannedData2.mRatioRectF;
            rectF2.offset(0.0f, f2);
            spannedData2.mRatioRectF.set(rectF2);
            spannedData2.setRectF(rectF2);
        }
    }

    static /* synthetic */ boolean lambda$moveAllSpanAfterPage$5(int i, SpannedData spannedData) {
        return spannedData.getPageIndex() > i;
    }

    public void moveAllSpan(float f, float f2) {
        for (SpannedData spannedData : this.mSpannedLayout.getEngineBindContext().getAllDataList()) {
            RectF rectF = spannedData.mRatioRectF;
            rectF.offset(f, f2);
            spannedData.mRatioRectF.set(rectF);
            spannedData.setRectF(rectF);
        }
    }

    public void moveAllCurve(float f, float f2) {
        if (getCreationEngine() != null) {
            getCreationEngine().moveAllCurve(f, f2);
        }
    }

    public RectF getAllSpanBound() {
        List<SpannedData> allDataList = this.mSpannedLayout.getEngineBindContext().getAllDataList();
        if (allDataList.isEmpty()) {
            return null;
        }
        RectF rectF = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        for (SpannedData spannedData : allDataList) {
            if (spannedData.mSpannedStatus != 3) {
                rectF.union(new RectF(spannedData.mRatioRectF.left, spannedData.mRatioRectF.top, spannedData.mRatioRectF.right, spannedData.mRatioRectF.bottom));
            }
        }
        return rectF;
    }

    public List<SpannedData> getAllSpanData() {
        return this.mSpannedLayout.getEngineBindContext().getAllDataList();
    }

    public void setUnlimitedSize(int i, int i2) {
        this.unLimitWidth = i;
        this.unLimitHeight = i2;
    }

    public boolean isUnLimit() {
        return this.isUnLimit;
    }

    public float[] getLastOffset() {
        return new float[]{this.lastOffsetX, this.lastOffsetY};
    }

    public RectF getPageLocation() {
        return this.mPageLocation;
    }

    public void setSpannedLayoutFixWidth(int i) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            spannedEditLayout.setFixWidth(i);
        }
    }

    public void setSpannedLayoutFixMode(boolean z) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            spannedEditLayout.setFixMode(z);
        }
    }

    public void setSpanVisibleSize(float f, float f2) {
        if (getMode() != null) {
            getMode().setVisibleSize(f, f2);
        }
    }

    public List<BitmapBean> getSpannedBitmapList() {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            return spannedEditLayout.getSpannedLayoutModel().mCsImageDrawModel.mBitmapBeanList;
        }
        return null;
    }

    public Bitmap getSpannedBitmap() {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            return spannedEditLayout.getSpannedLayoutModel().mCsImageDrawModel.mBitmap;
        }
        return null;
    }

    public List<SpannedData> getDataList(RectF rectF) {
        return this.mSpannedLayout.getEngineBindContext().getAllDataList(rectF);
    }

    public List<SpannedData> getDataList(int i, int i2) {
        return this.mSpannedLayout.getEngineBindContext().getSpannedMap(i).getDataList(i2);
    }

    public boolean isBitmapSizeMax(int i) {
        return this.mSpannedLayout.getEngineBindContext().isBitmapSizeMax(this.model.getCurrentPosition(), i);
    }

    public boolean isBitmapSizeMax(List<SpannedData> list) {
        return this.mSpannedLayout.getEngineBindContext().isBitmapSizeMax(this.model.getCurrentPosition(), list);
    }

    public boolean isTextSizeMax(int i) {
        return this.mSpannedLayout.getEngineBindContext().isTextSizeMax(this.model.getCurrentPosition(), i);
    }

    public boolean isTextSizeMax(List<SpannedData> list) {
        return this.mSpannedLayout.getEngineBindContext().isTextSizeMax(this.model.getCurrentPosition(), list);
    }

    public boolean isOnSpannedData(float f, float f2) {
        return this.mSpannedLayout.getEngineBindContext().isOnSpannedData(f, f2);
    }

    public boolean isOnTextSpannedData(float f, float f2, int i) {
        return this.mSpannedLayout.getEngineBindContext().isOnTextSpannedData(f, f2, i);
    }

    public IBufferBean toBufferBean(List<SpannedData> list) {
        return this.mSpannedLayout.getEngineBindContext().toBufferBean(list);
    }

    public List<SpannedData> fromBufferBean(IBufferBean iBufferBean) {
        return this.mSpannedLayout.getEngineBindContext().fromBufferBean(iBufferBean);
    }

    private void showRenderDialog() {
        CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m865xf15c1c70();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showRenderDialog$6$com-miui-libhandwritingpage-view-PageItemView, reason: not valid java name */
    /* synthetic */ void m865xf15c1c70() {
        if (this.renderingDialog == null && getContext() != null) {
            ProgressDialog progressDialog = new ProgressDialog(Utils.getActivity(getContext()));
            this.renderingDialog = progressDialog;
            progressDialog.setMessage(getContext().getString(R.string.creation_edit_loading));
            this.renderingDialog.setCanceledOnTouchOutside(false);
            this.renderingDialog.setCancelable(false);
        }
        if (this.renderingDialog.isShowing()) {
            return;
        }
        this.renderingDialog.show();
    }

    private void dismissRenderDialog() {
        CreationPageSDK.with(this.mContextRef.get()).post(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m859xc0462124();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$dismissRenderDialog$7$com-miui-libhandwritingpage-view-PageItemView, reason: not valid java name */
    /* synthetic */ void m859xc0462124() {
        ProgressDialog progressDialog = this.renderingDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.renderingDialog.dismiss();
        this.renderingDialog = null;
    }

    @Override // xc.engine.core.graphics.engine.systems.SelectSystem.RenderTaskListener
    public void onRendering() {
        showRenderDialog();
        CreationPageSDK.with(this.mContextRef.get()).postDelayed(new Runnable() { // from class: com.miui.libhandwritingpage.view.PageItemView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m864xabfd95aa();
            }
        }, 5000L);
    }

    /* JADX INFO: renamed from: lambda$onRendering$8$com-miui-libhandwritingpage-view-PageItemView, reason: not valid java name */
    /* synthetic */ void m864xabfd95aa() {
        ProgressDialog progressDialog = this.renderingDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        this.renderingDialog.dismiss();
        this.renderingDialog = null;
    }

    @Override // xc.engine.core.graphics.engine.systems.SelectSystem.RenderTaskListener
    public void onRenderCompleted() {
        dismissRenderDialog();
    }

    public void scrollEditTextBy(int i) {
        if (this.mSpannedLayout != null) {
            SelectViewGroup selectViewGroup = this.mMultiSelectViewGroup;
            if (selectViewGroup != null) {
                selectViewGroup.onFinishSelect();
            }
            this.mSpannedLayout.getSpannedEditModel().moveEditText(i);
            this.mSpannedLayout.invalidate();
            updateSpanScaleInfo();
        }
    }

    public void resetScrolledEditTextBy(int i) {
        SpannedEditLayout spannedEditLayout = this.mSpannedLayout;
        if (spannedEditLayout != null) {
            spannedEditLayout.getSpannedEditModel().resetMovedEditText(i);
            this.mSpannedLayout.invalidate();
            updateSpanScaleInfo();
        }
    }
}
