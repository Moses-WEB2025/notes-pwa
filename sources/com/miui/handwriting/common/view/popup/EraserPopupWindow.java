package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.material.timepicker.TimeModel;
import com.miui.creation.common.R;
import com.miui.handwriting.common.data.bean.MiuiDeleteProp;
import miuix.miuixbasewidget.widget.FilterSortView;
import xc.engine.operate.canvas.track.delete.DeleteType;

/* JADX INFO: loaded from: classes3.dex */
public class EraserPopupWindow extends BaseNormalPopupWindow {
    private static final String TAG = "EraserPopupWindow";
    private OnEraserSettingConfirmListener confirmListener;
    private MiuiDeleteProp deleteProp;
    private FilterSortView filterSortView;
    private boolean isChange;
    private TextView mEraserSize;
    private SelectAlphaView seekbarEraser;

    public interface OnEraserSettingConfirmListener {
        void onConfirmCircleClick();
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected boolean isReviceDataUpdateUi() {
        return true;
    }

    public void setDeleteProp(MiuiDeleteProp miuiDeleteProp) {
        this.deleteProp = miuiDeleteProp;
    }

    public void setConfirmListener(OnEraserSettingConfirmListener onEraserSettingConfirmListener) {
        this.confirmListener = onEraserSettingConfirmListener;
    }

    public EraserPopupWindow(Context context) {
        super(context);
        this.isChange = false;
        initLayout(context, R.layout.popup_layout_eraser_set);
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected void initLayout(Context context, int i) {
        super.initLayout(context, i);
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected void initData() {
        super.initData();
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected void initView() {
        super.initView();
        this.filterSortView = (FilterSortView) this.mContentView.findViewById(R.id.filterSortView);
        this.seekbarEraser = (SelectAlphaView) this.mContentView.findViewById(R.id.seekbar_eraser);
        this.mEraserSize = (TextView) this.mContentView.findViewById(R.id.tv_eraser_size_num);
        int dimension = (int) this.mContentView.getContext().getResources().getDimension(R.dimen.eraser_popup_tab_horizontal_padding);
        final FilterSortView.TabView tabViewAddTab = this.filterSortView.addTab(this.mContentView.getContext().getString(R.string.creation_eraser_pixel));
        tabViewAddTab.setPadding(dimension, 0, dimension, 0);
        tabViewAddTab.setFilterHoverListener(null);
        tabViewAddTab.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.EraserPopupWindow$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m762xb48e1bbe(tabViewAddTab, view);
            }
        });
        final FilterSortView.TabView tabViewAddTab2 = this.filterSortView.addTab(this.mContentView.getContext().getString(R.string.creation_eraser_stroke));
        tabViewAddTab2.setPadding(dimension, 0, dimension, 0);
        tabViewAddTab2.setFilterHoverListener(null);
        tabViewAddTab2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.popup.EraserPopupWindow$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m763x417b32dd(tabViewAddTab2, view);
            }
        });
        FilterSortView filterSortView = this.filterSortView;
        if (this.deleteProp.getDeleteType() == DeleteType.LINE) {
            tabViewAddTab = tabViewAddTab2;
        }
        filterSortView.setFilteredTab(tabViewAddTab);
        this.seekbarEraser.setMin(4);
        this.seekbarEraser.setMax(32);
        int deleteRadius = (int) this.deleteProp.getDeleteRadius();
        this.seekbarEraser.setProgress(deleteRadius);
        this.seekbarEraser.setAccessibilityProcess(false);
        this.mEraserSize.setText(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(deleteRadius)));
        Log.d(TAG, "getDeleteRadius" + this.deleteProp.getDeleteRadius());
        this.seekbarEraser.updateCurrentBackground(0);
        this.seekbarEraser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.miui.handwriting.common.view.popup.EraserPopupWindow.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                EraserPopupWindow.this.isChange = true;
                EraserPopupWindow.this.deleteProp.setDeleteRadius(i);
                EraserPopupWindow.this.mEraserSize.setText(String.format(TimeModel.NUMBER_FORMAT, Integer.valueOf(i)));
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-miui-handwriting-common-view-popup-EraserPopupWindow, reason: not valid java name */
    /* synthetic */ void m762xb48e1bbe(FilterSortView.TabView tabView, View view) {
        this.isChange = true;
        this.deleteProp.setDeleteType(DeleteType.POINT);
        this.filterSortView.setFilteredTab(tabView);
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-miui-handwriting-common-view-popup-EraserPopupWindow, reason: not valid java name */
    /* synthetic */ void m763x417b32dd(FilterSortView.TabView tabView, View view) {
        this.isChange = true;
        this.deleteProp.setDeleteType(DeleteType.LINE);
        this.filterSortView.setFilteredTab(tabView);
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        super.dismiss();
        OnEraserSettingConfirmListener onEraserSettingConfirmListener = this.confirmListener;
        if (onEraserSettingConfirmListener == null || !this.isChange) {
            return;
        }
        onEraserSettingConfirmListener.onConfirmCircleClick();
    }
}
