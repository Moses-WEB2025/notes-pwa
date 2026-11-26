package com.miui.handwriting.common.view.popup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.timepicker.TimeModel;
import com.miui.creation.common.R;
import com.miui.handwriting.common.data.bean.PenBean;
import com.miui.handwriting.common.data.dataset.PenSet;
import com.miui.handwriting.common.tools.ColorUtils;
import com.miui.handwriting.common.tools.PreferenceUtils;
import com.miui.handwriting.common.tools.RendToBitmapHandlerThread;
import com.miui.handwriting.common.tools.ThreadPoolHelper;
import com.miui.handwriting.common.view.handview.pop.AIPenPopupWindow;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.miui.libhandwritingpage.utils.UIUtils;
import com.miui.libhandwritingpage.utils.Utils;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import miuix.animation.Folme;
import miuix.animation.property.ViewProperty;
import miuix.core.util.MiuixUIUtils;
import miuix.miuixbasewidget.widget.FilterSortView;
import xc.engine.core.graphics.pen.PenType;
import xc.engine.utils.DisplayUtils;

/* JADX INFO: loaded from: classes3.dex */
public class PenSetPopupWindow extends BaseNormalPopupWindow implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "PenSetPopupWindow";
    public static volatile boolean sForceHideBallpointPreviewAlpha = false;
    private final View.OnClickListener brushClickListener;
    private OnPenSettingConfirmListener confirmListener;
    private FilterSortView filterSortView;
    private float[] hsv;
    private boolean isPropChange;
    private boolean isShowBrush;
    private boolean isUnlimited;
    private ImageView ivPreview;
    private AIPenPopupWindow.OnAIPenClickListener listener;
    public View mAiParent;
    public View mAiSelect;
    private int mAiType;
    public View mBrushBallpoint;
    private View.OnClickListener mBrushClickListener;
    public View mBrushInk;
    public View mBrushMarkPen;
    public View mBrushParent;
    public View mBrushPencil;
    public View mBrushWaterColor;
    private PenSet mPenSet;
    private PenSet.PenSetChangeListener mPenSetChangeListener;
    private Bitmap reviewBitmap;
    private SelectPropertyView seekPenSensitivityView;
    private SelectAlphaView seekPenTextTransparentView;
    private SelectPropertyView seekPenThickness;
    private SelectPropertyView seekPenTipSizeView;
    private FilterSortView.TabView tabAreaView;
    private FilterSortView.TabView tabPageView;
    private TextView tvPenSensitivity;
    private TextView tvPenSensitivityNum;
    private TextView tvPenTextThickness;
    private TextView tvPenTextTransparent;
    private TextView tvPenTipSize;
    private View viewPenSensitivity;
    private View viewPenTextThickness;
    private View viewPenTextTransparent;
    private View viewPenTipSize;

    public interface OnPenSettingConfirmListener {
        void onConfirmClick(PenBean penBean);

        void onReviewBitmap(PenBean penBean, Bitmap bitmap);

        void upReivewBitmap(Map<PenType, Bitmap> map);
    }

    private void setListener() {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public PenSetPopupWindow(Context context, boolean z, boolean z2) {
        this(context, z, z2, false);
    }

    public PenSetPopupWindow(Context context, boolean z, boolean z2, boolean z3) {
        super(context);
        this.mAiType = 0;
        this.hsv = new float[3];
        this.isPropChange = false;
        this.isShowBrush = true;
        this.isUnlimited = false;
        this.mPenSetChangeListener = new PenSet.PenSetChangeListener() { // from class: com.miui.handwriting.common.view.popup.PenSetPopupWindow.1
            @Override // com.miui.handwriting.common.data.dataset.PenSet.PenSetChangeListener
            public void onPenBeanChanged(PenBean penBean) {
                PenSetPopupWindow.this.onPenSetChanged(penBean);
            }
        };
        this.brushClickListener = new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.PenSetPopupWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int id = view.getId();
                PenSetPopupWindow.this.isShowBrush = id != R.id.creation_multi_ai_select;
                if (PenSetPopupWindow.this.mBrushClickListener != null) {
                    PenSetPopupWindow.this.mBrushClickListener.onClick(view);
                }
            }
        };
        if (z3) {
            initLayout(context, R.layout.popup_layout_pen_set_simple, false, z2);
        } else if (z) {
            initLayout(context, R.layout.popup_layout_pen_set_multi, true, z2);
        } else {
            initLayout(context, R.layout.popup_layout_pen_set, false, z2);
        }
    }

    protected void initLayout(Context context, int i, boolean z, boolean z2) {
        super.initLayout(context, i);
        if (z) {
            initBrushes();
            initTools(z2);
        }
    }

    public PenBean getCurPenBean() {
        PenSet penSet = this.mPenSet;
        if (penSet != null) {
            return penSet.getCurPenBean();
        }
        return null;
    }

    public void setPenSet(PenSet penSet) {
        this.mPenSet = penSet;
        if (penSet != null) {
            penSet.addListener(this.mPenSetChangeListener);
            refreshUIAfterPenSetChanged();
        }
    }

    private void refreshUIAfterPenSetChanged() {
        if (this.mContentView == null || this.mPenSet == null) {
            return;
        }
        try {
            updateNumber();
            updateProgress();
            setListener();
        } catch (Exception e) {
            Log.e(TAG, "refreshUIAfterPenSetChanged error", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPenSetChanged(PenBean penBean) {
        if (penBean == null) {
            return;
        }
        try {
            updateNumber();
            updateProgress();
            setReviewBitmap();
        } catch (Exception e) {
            Log.e(TAG, "onPenSetChanged error", e);
        }
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected void initView() {
        boolean booleanExtra;
        boolean booleanExtra2;
        super.initView();
        this.ivPreview = (ImageView) this.mContentView.findViewById(R.id.iv_pen_review);
        this.viewPenSensitivity = this.mContentView.findViewById(R.id.cl_pen_sensitivity);
        this.viewPenTipSize = this.mContentView.findViewById(R.id.cl_pen_tip_size);
        this.viewPenTextTransparent = this.mContentView.findViewById(R.id.cl_pen_text_transparent);
        this.viewPenTextThickness = this.mContentView.findViewById(R.id.cl_pen_thickness);
        this.tvPenSensitivity = (TextView) this.mContentView.findViewById(R.id.tv_pen_sensitivity);
        this.tvPenSensitivityNum = (TextView) this.mContentView.findViewById(R.id.tv_pen_sensitivity_num);
        this.tvPenTextTransparent = (TextView) this.mContentView.findViewById(R.id.tv_pen_text_transparent_num);
        this.tvPenTextThickness = (TextView) this.mContentView.findViewById(R.id.tv_pen_thickness_num);
        this.tvPenTipSize = (TextView) this.mContentView.findViewById(R.id.tv_pen_tip_size_num);
        this.seekPenTipSizeView = (SelectPropertyView) this.mContentView.findViewById(R.id.seekbar_pen_tip_size);
        this.seekPenSensitivityView = (SelectPropertyView) this.mContentView.findViewById(R.id.seekbar_pen_sensitivity);
        this.seekPenThickness = (SelectPropertyView) this.mContentView.findViewById(R.id.seekbar_pen_thickness);
        this.seekPenTextTransparentView = (SelectAlphaView) this.mContentView.findViewById(R.id.seekbar_pen_text_transparent);
        boolean z = false;
        try {
            if (this.mContext instanceof Activity) {
                Intent intent = ((Activity) this.mContext).getIntent();
                booleanExtra = intent.getBooleanExtra("external_launch", false);
                try {
                    booleanExtra2 = intent.getBooleanExtra("limited_toolbar", false);
                } catch (Throwable unused) {
                    booleanExtra2 = false;
                }
            } else {
                booleanExtra2 = false;
                booleanExtra = false;
            }
        } catch (Throwable unused2) {
            booleanExtra = false;
        }
        if (getCurPenBean() != null && getCurPenBean().getPenType() == PenType.BALL) {
            z = true;
        }
        if ((sForceHideBallpointPreviewAlpha || booleanExtra || booleanExtra2) && z) {
            ImageView imageView = this.ivPreview;
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            View view = this.viewPenTextTransparent;
            if (view != null) {
                view.setVisibility(8);
            }
        }
        updateProgress();
        initReviewBitmap();
        setContent();
    }

    public void setOrignBitmap(Map<PenType, Bitmap> map) {
        Bitmap bitmap;
        if (map == null || map.isEmpty() || (bitmap = map.get(getCurPenBean().getPenType())) == null) {
            return;
        }
        this.ivPreview.setImageBitmap(bitmap);
    }

    private void initBrushes() {
        this.mBrushBallpoint = this.mContentView.findViewById(R.id.creation_multi_brush_ballpoint);
        this.mBrushPencil = this.mContentView.findViewById(R.id.creation_multi_brush_pencil);
        this.mBrushInk = this.mContentView.findViewById(R.id.creation_multi_brush_ink);
        this.mBrushMarkPen = this.mContentView.findViewById(R.id.creation_multi_brush_markpen);
        this.mBrushWaterColor = this.mContentView.findViewById(R.id.creation_multi_brush_watercolor);
        this.mAiSelect = this.mContentView.findViewById(R.id.creation_multi_ai_select);
        if (PreferenceUtils.getAIBrushPermission()) {
            this.mAiSelect.setVisibility(0);
        } else {
            this.mAiSelect.setVisibility(8);
        }
        this.mBrushParent = this.mContentView.findViewById(R.id.creation_multibrush_config);
        this.mAiParent = this.mContentView.findViewById(R.id.ai_controller);
    }

    private void initTools(final boolean z) {
        FilterSortView filterSortView = (FilterSortView) this.mContentView.findViewById(R.id.filterSortView);
        this.filterSortView = filterSortView;
        this.tabPageView = filterSortView.addTab(this.mContentView.getContext().getString(R.string.creation_ai_page));
        if (RomUtils.isUseNewCreationAI()) {
            this.tabPageView.setEnabled(false);
            return;
        }
        if (z) {
            this.tabPageView.setEnabled(false);
        } else {
            this.tabPageView.setEnabled(true);
        }
        this.tabPageView.setFilterHoverListener(null);
        this.tabPageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.PenSetPopupWindow$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m777x4cbb8625(z, view);
            }
        });
        FilterSortView.TabView tabViewAddTab = this.filterSortView.addTab(this.mContentView.getContext().getString(R.string.creation_ai_area));
        this.tabAreaView = tabViewAddTab;
        tabViewAddTab.setFilterHoverListener(null);
        this.tabAreaView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.PenSetPopupWindow$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m778xd9a89d44(view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initTools$0$com-miui-handwriting-common-view-popup-PenSetPopupWindow, reason: not valid java name */
    /* synthetic */ void m777x4cbb8625(boolean z, View view) {
        if (z) {
            Toast.makeText(this.mContext.getApplicationContext(), this.mContext.getApplicationContext().getString(R.string.creation_ai_page_select_limit_toast), 0).show();
            return;
        }
        this.mAiType = 1;
        this.filterSortView.setFilteredTab(this.tabPageView);
        AIPenPopupWindow.OnAIPenClickListener onAIPenClickListener = this.listener;
        if (onAIPenClickListener != null) {
            onAIPenClickListener.onFullPageClick();
        }
    }

    /* JADX INFO: renamed from: lambda$initTools$1$com-miui-handwriting-common-view-popup-PenSetPopupWindow, reason: not valid java name */
    /* synthetic */ void m778xd9a89d44(View view) {
        this.mAiType = 0;
        this.filterSortView.setFilteredTab(this.tabAreaView);
        AIPenPopupWindow.OnAIPenClickListener onAIPenClickListener = this.listener;
        if (onAIPenClickListener != null) {
            onAIPenClickListener.onSelectClick();
        }
    }

    public void setAiSelectListener(AIPenPopupWindow.OnAIPenClickListener onAIPenClickListener) {
        this.listener = onAIPenClickListener;
    }

    public void setAiType(int i) {
        this.mAiType = i;
        this.filterSortView.setFilteredTab(i == 0 ? this.tabAreaView : this.tabPageView);
    }

    public boolean getIsSelectBrush() {
        return this.isShowBrush;
    }

    public void setBrushChangeListener(View.OnClickListener onClickListener) {
        this.mBrushClickListener = onClickListener;
        this.mBrushBallpoint.setOnClickListener(this.brushClickListener);
        this.mBrushPencil.setOnClickListener(this.brushClickListener);
        this.mBrushInk.setOnClickListener(this.brushClickListener);
        this.mBrushMarkPen.setOnClickListener(this.brushClickListener);
        this.mBrushWaterColor.setOnClickListener(this.brushClickListener);
        this.mAiSelect.setOnClickListener(this.brushClickListener);
    }

    protected void setContent() {
        updateNumber();
        updateProgress();
        setListener();
    }

    private void updateNumber() {
        PenBean curPenBean = getCurPenBean();
        if (curPenBean == null) {
            return;
        }
        Log.d(TAG, "updateNumber:" + curPenBean.getPenSize() + " curPenBean is " + curPenBean);
        this.tvPenSensitivityNum.setText(String.format("%d%%", Integer.valueOf((int) (curPenBean.getSensitivity() * 100.0f))));
        this.tvPenTextTransparent.setText(String.format("%s", NumberFormat.getPercentInstance().format(Color.alpha(curPenBean.getPenColor()) / 255.0f)));
        this.tvPenTextThickness.setText(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf((int) curPenBean.getPenSize())));
        this.tvPenTipSize.setText(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf((int) curPenBean.getTipSize())));
    }

    private void updateProgress() {
        boolean booleanExtra;
        boolean booleanExtra2;
        PenBean curPenBean = getCurPenBean();
        if (curPenBean == null) {
            return;
        }
        setPenSizeSeekbar();
        setPenAlphaSeekbar();
        try {
            if (this.mContext instanceof Activity) {
                Intent intent = ((Activity) this.mContext).getIntent();
                booleanExtra = intent.getBooleanExtra("external_launch", false);
                try {
                    booleanExtra2 = intent.getBooleanExtra("limited_toolbar", false);
                } catch (Throwable unused) {
                    booleanExtra2 = false;
                }
            } else {
                booleanExtra2 = false;
                booleanExtra = false;
            }
        } catch (Throwable unused2) {
            booleanExtra = false;
        }
        View view = this.viewPenSensitivity;
        if (view != null && this.viewPenTipSize != null && this.viewPenTextTransparent != null && (!booleanExtra || !booleanExtra2)) {
            view.setVisibility(8);
            this.viewPenTipSize.setVisibility(8);
            if (curPenBean.getPenType() == PenType.INK || curPenBean.getPenType() == PenType.PENCIL) {
                if (curPenBean.getPenType() == PenType.PENCIL) {
                    this.tvPenSensitivity.setText(R.string.creation_pencil_sensitivity);
                    this.seekPenSensitivityView.setContentDescription(this.mContext.getString(R.string.creation_pencil_sensitivity));
                } else {
                    this.tvPenSensitivity.setText(R.string.creation_pen_sensitivity);
                    this.seekPenSensitivityView.setContentDescription(this.mContext.getString(R.string.creation_pen_sensitivity));
                }
                this.viewPenSensitivity.setVisibility(0);
                setPenSensitivitySeekbar();
            }
            if (curPenBean.getPenType() == PenType.INK) {
                this.viewPenTipSize.setVisibility(0);
                setPenTipSizeSeekbar();
                return;
            }
            return;
        }
        if (view != null) {
            view.setVisibility(8);
        }
        View view2 = this.viewPenTipSize;
        if (view2 != null) {
            view2.setVisibility(8);
        }
        View view3 = this.viewPenTextTransparent;
        if (view3 != null) {
            view3.setVisibility(8);
        }
    }

    private void setPenSizeSeekbar() {
        PenBean curPenBean = getCurPenBean();
        if (curPenBean == null) {
            return;
        }
        this.seekPenThickness.setOnSeekBarChangeListener(null);
        Log.d(TAG, "setPenSizeSeekbar" + curPenBean.getMaxSize() + "__" + getCurPenBean().getMinSize());
        this.seekPenThickness.setMax(curPenBean.getMaxSize());
        this.seekPenThickness.setMin(curPenBean.getMinSize());
        this.seekPenThickness.setProgress((int) curPenBean.getPenSize());
        this.seekPenThickness.setAccessibilityProcess(false);
        this.seekPenThickness.setOnSeekBarChangeListener(this);
    }

    private void setPenTipSizeSeekbar() {
        PenBean curPenBean = getCurPenBean();
        if (curPenBean == null) {
            return;
        }
        this.seekPenTipSizeView.setOnSeekBarChangeListener(null);
        this.seekPenTipSizeView.setMax(PenBean.maxTipSize);
        this.seekPenTipSizeView.setMin(PenBean.minTipSize);
        this.seekPenTipSizeView.setProgress((int) curPenBean.getTipSize());
        this.seekPenTipSizeView.setAccessibilityProcess(false);
        this.seekPenTipSizeView.setOnSeekBarChangeListener(this);
    }

    private void setPenSensitivitySeekbar() {
        PenBean curPenBean = getCurPenBean();
        if (curPenBean == null) {
            return;
        }
        this.seekPenSensitivityView.setOnSeekBarChangeListener(null);
        Log.d(TAG, "setPenSensitivitySeekbar:" + curPenBean.getSensitivity());
        this.seekPenSensitivityView.setMax(PenBean.maxSensitivity * 100);
        this.seekPenSensitivityView.setMin(PenBean.minSensitivity);
        this.seekPenSensitivityView.setProgress((int) (curPenBean.getSensitivity() * 100.0f));
        this.seekPenSensitivityView.setOnSeekBarChangeListener(this);
    }

    private void setPenAlphaSeekbar() {
        PenBean curPenBean = getCurPenBean();
        if (curPenBean == null) {
            return;
        }
        this.seekPenTextTransparentView.updateCurrentBackground(ColorUtils.calculateRGBColor(curPenBean.getPenColor()));
        this.seekPenTextTransparentView.setMax(PenBean.maxAlpha);
        this.seekPenTextTransparentView.setMin(PenBean.minAlpha);
        this.seekPenTextTransparentView.setProgress(Color.alpha(curPenBean.getPenColor()));
        this.seekPenTextTransparentView.setOnSeekBarChangeListener(this);
    }

    public void updatePenSet(boolean z) {
        this.isShowBrush = z;
        if (z) {
            this.mBrushParent.setVisibility(0);
        } else {
            this.filterSortView.post(new Runnable() { // from class: com.miui.handwriting.common.view.popup.PenSetPopupWindow$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m782x5ed76562();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$updatePenSet$2$com-miui-handwriting-common-view-popup-PenSetPopupWindow, reason: not valid java name */
    /* synthetic */ void m782x5ed76562() {
        this.mBrushParent.setVisibility(8);
    }

    public PenSetPopupWindow setConfirmListener(OnPenSettingConfirmListener onPenSettingConfirmListener) {
        this.confirmListener = onPenSettingConfirmListener;
        return this;
    }

    public void refreshPopWindow(boolean z) {
        updatePenSet(z);
        if (z) {
            clearSeekBarChangeListener();
            setReviewBitmap();
            setContent();
            updatePopupWindow();
            return;
        }
        clearSeekBarChangeListener();
    }

    private void clearSeekBarChangeListener() {
        SelectPropertyView selectPropertyView = this.seekPenThickness;
        if (selectPropertyView != null) {
            selectPropertyView.setOnSeekBarChangeListener(null);
        }
        SelectPropertyView selectPropertyView2 = this.seekPenTipSizeView;
        if (selectPropertyView2 != null) {
            selectPropertyView2.setOnSeekBarChangeListener(null);
        }
        SelectPropertyView selectPropertyView3 = this.seekPenSensitivityView;
        if (selectPropertyView3 != null) {
            selectPropertyView3.setOnSeekBarChangeListener(null);
        }
        SelectAlphaView selectAlphaView = this.seekPenTextTransparentView;
        if (selectAlphaView != null) {
            selectAlphaView.setOnSeekBarChangeListener(null);
        }
    }

    public void refreshBrushPreview() {
        setReviewBitmap();
    }

    private void setReviewBitmap() {
        final PenBean curPenBean;
        Log.d(TAG, "setReviewBitmap" + this.reviewBitmap);
        if (this.reviewBitmap == null || (curPenBean = getCurPenBean()) == null) {
            return;
        }
        Log.d(TAG, "confirmListener" + this.confirmListener);
        if (this.confirmListener != null) {
            ThreadPoolHelper.getInstance().execute(new Runnable() { // from class: com.miui.handwriting.common.view.popup.PenSetPopupWindow$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m781x161b4fc2(curPenBean);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$setReviewBitmap$5$com-miui-handwriting-common-view-popup-PenSetPopupWindow, reason: not valid java name */
    /* synthetic */ void m781x161b4fc2(final PenBean penBean) {
        FutureTask futureTask = new FutureTask(new Callable() { // from class: com.miui.handwriting.common.view.popup.PenSetPopupWindow$$ExternalSyntheticLambda3
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m779xfc412184(penBean);
            }
        });
        Log.e(TAG, "postRendToBitmapMsg ");
        RendToBitmapHandlerThread.postRendToBitmapMsg(futureTask);
        try {
            Log.e(TAG, "futureTask resulr" + futureTask.get());
            this.ivPreview.post(new Runnable() { // from class: com.miui.handwriting.common.view.popup.PenSetPopupWindow$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m780x892e38a3();
                }
            });
            HashMap map = new HashMap();
            map.put(penBean.getPenType(), this.reviewBitmap);
            this.confirmListener.upReivewBitmap(map);
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "onReviewBitmapError", e);
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: lambda$setReviewBitmap$3$com-miui-handwriting-common-view-popup-PenSetPopupWindow, reason: not valid java name */
    /* synthetic */ Bitmap m779xfc412184(PenBean penBean) throws Exception {
        Log.d(TAG, "onReviewBitmap");
        this.confirmListener.onReviewBitmap(penBean, this.reviewBitmap);
        return this.reviewBitmap;
    }

    /* JADX INFO: renamed from: lambda$setReviewBitmap$4$com-miui-handwriting-common-view-popup-PenSetPopupWindow, reason: not valid java name */
    /* synthetic */ void m780x892e38a3() {
        this.ivPreview.setImageBitmap(this.reviewBitmap);
    }

    public void setUp() {
        this.seekPenThickness.setProgress(this.seekPenThickness.getProgress() + 5);
        this.seekPenThickness.invalidate();
    }

    public void setPencilUp() {
        this.seekPenThickness.setProgress(this.seekPenThickness.getProgress() + 1);
        this.seekPenThickness.invalidate();
    }

    public void setDown() {
        this.seekPenThickness.setProgress(this.seekPenThickness.getProgress() - 5);
        this.seekPenThickness.invalidate();
    }

    public void setPencilDown() {
        this.seekPenThickness.setProgress(this.seekPenThickness.getProgress() - 1);
        this.seekPenThickness.invalidate();
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        PenBean curPenBean = getCurPenBean();
        if (curPenBean == null) {
            return;
        }
        this.isPropChange = true;
        if (seekBar == this.seekPenThickness) {
            curPenBean.setPenSize(i);
        } else if (seekBar == this.seekPenTextTransparentView) {
            Log.d(TAG, "setPenAlpha:" + i);
            int iMax = Math.max(PenBean.minAlphaValue, i);
            seekBar.setProgress(iMax);
            curPenBean.setPenAlpha(iMax);
            curPenBean.getHSLColor().setAlpha(iMax);
            curPenBean.setHSLColor(curPenBean.getHSLColor());
            curPenBean.setDefaultHSLArray(curPenBean.getCheckedItemIndex(), curPenBean.getHSLColor());
        } else if (seekBar == this.seekPenSensitivityView) {
            Log.d(TAG, "setSensitivity:" + i);
            getCurPenBean().setSensitivity((i * 1.0f) / 100.0f);
        } else if (seekBar == this.seekPenTipSizeView) {
            Log.d(TAG, "setTipSize:" + i);
            getCurPenBean().setTipSize(i * 1.0f);
        }
        updateNumber();
        setReviewBitmap();
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        PenSet.PenSetChangeListener penSetChangeListener;
        super.dismiss();
        if (this.isPropChange) {
            this.confirmListener.onConfirmClick(getCurPenBean());
        }
        Bitmap bitmap = this.reviewBitmap;
        if (bitmap != null && bitmap.isRecycled()) {
            this.reviewBitmap.recycle();
            this.reviewBitmap = null;
        }
        PenSet penSet = this.mPenSet;
        if (penSet == null || (penSetChangeListener = this.mPenSetChangeListener) == null) {
            return;
        }
        penSet.removeListener(penSetChangeListener);
        this.mPenSetChangeListener = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initReviewBitmap() {
        if (this.reviewBitmap == null) {
            int measuredWidth = this.ivPreview.getMeasuredWidth();
            int measuredHeight = this.ivPreview.getMeasuredHeight();
            if (measuredWidth == 0 || measuredHeight == 0) {
                return;
            }
            this.reviewBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
            setReviewBitmap();
        }
    }

    @Override // com.miui.handwriting.common.view.popup.ArrowPopupWindow, android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2) {
        super.showAsDropDown(view, i, i2);
        view.post(new Runnable() { // from class: com.miui.handwriting.common.view.popup.PenSetPopupWindow$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.initReviewBitmap();
            }
        });
    }

    public View getBrushLocation(int i) {
        if (i == 1) {
            return this.mBrushInk;
        }
        if (i == 2) {
            return this.mBrushBallpoint;
        }
        if (i == 4) {
            return this.mBrushMarkPen;
        }
        if (i == 8) {
            return this.mAiSelect;
        }
        if (i == 10) {
            return this.mBrushWaterColor;
        }
        return this.mBrushPencil;
    }

    public void setBrushLocation(View view) {
        if (view != null) {
            Folme.useAt(view).state().to(ViewProperty.TRANSLATION_Y, 0);
        }
    }

    public void updatePopupWindow() {
        try {
            Log.e(TAG, "start updatePopupWindow");
            int multiHeight = DisplayUtils.getMultiHeight(Utils.getActivity(this.mContext));
            int dimension = (int) this.mContext.getResources().getDimension(R.dimen.creation_brush_head_height);
            int statusBarHeight = UIUtils.getStatusBarHeight(this.mContext);
            int navigationBarHeightFromProp = UIUtils.getNavigationBarHeightFromProp(this.mContext);
            int i = ((multiHeight - dimension) - statusBarHeight) - navigationBarHeightFromProp;
            View viewFindViewById = this.mContentView.findViewById(R.id.sl);
            ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
            layoutParams.height = -2;
            viewFindViewById.setLayoutParams(layoutParams);
            this.mContentView.measure(0, 0);
            int measuredHeight = this.mContentView.getMeasuredHeight();
            Log.d(TAG, "updatePopupWindow contentH: " + i + ", offsetY: , statusBarH: " + statusBarHeight + ", navBarH: " + navigationBarHeightFromProp + ", popupWinH: " + measuredHeight);
            if (measuredHeight <= i || getCurPenBean().getPenType() != PenType.INK) {
                return;
            }
            layoutParams.height = (i - ((int) this.mContext.getResources().getDimension(R.dimen.creation_tools_head_height))) - MiuixUIUtils.dp2px(this.mContext, 200.0f);
            viewFindViewById.setLayoutParams(layoutParams);
        } catch (Exception unused) {
            Log.e(TAG, "updatePopupWindow fail");
        }
    }
}
