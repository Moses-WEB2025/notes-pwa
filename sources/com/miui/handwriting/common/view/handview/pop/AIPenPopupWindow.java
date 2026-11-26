package com.miui.handwriting.common.view.handview.pop;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.miui.creation.common.R;
import com.miui.handwriting.common.view.popup.BaseNormalPopupWindow;
import com.miui.libhandwritingpage.utils.RomUtils;
import miuix.miuixbasewidget.widget.FilterSortView;

/* JADX INFO: loaded from: classes3.dex */
public class AIPenPopupWindow extends BaseNormalPopupWindow {
    private int aiType;
    private FilterSortView filterSortView;
    private boolean isUnlimited;
    private OnAIPenClickListener listener;

    public interface OnAIPenClickListener {
        void onFullPageClick();

        void onSelectClick();
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected void initListener() {
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected boolean isReviceDataUpdateUi() {
        return true;
    }

    public AIPenPopupWindow(Context context) {
        super(context);
        this.aiType = 0;
        this.isUnlimited = false;
        initLayout(context, R.layout.popup_layout_ai_pen);
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected void initLayout(Context context, int i) {
        super.initLayout(context, i);
    }

    @Override // com.miui.handwriting.common.view.popup.BaseNormalPopupWindow
    protected void initView() {
        FilterSortView filterSortView = (FilterSortView) this.mContentView.findViewById(R.id.filterSortView);
        this.filterSortView = filterSortView;
        final FilterSortView.TabView tabViewAddTab = filterSortView.addTab(this.mContentView.getContext().getString(R.string.creation_ai_page));
        if (this.isUnlimited) {
            tabViewAddTab.setEnabled(false);
        } else {
            tabViewAddTab.setEnabled(true);
        }
        tabViewAddTab.setFilterHoverListener(null);
        if (!RomUtils.isUseNewCreationAI()) {
            tabViewAddTab.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.handview.pop.AIPenPopupWindow$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m751xe00ad5af(tabViewAddTab, view);
                }
            });
        }
        final FilterSortView.TabView tabViewAddTab2 = this.filterSortView.addTab(this.mContentView.getContext().getString(R.string.creation_ai_area));
        tabViewAddTab2.setFilterHoverListener(null);
        tabViewAddTab2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.view.handview.pop.AIPenPopupWindow$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m752x5e6bd98e(tabViewAddTab2, view);
            }
        });
        FilterSortView filterSortView2 = this.filterSortView;
        if (this.aiType == 0) {
            tabViewAddTab = tabViewAddTab2;
        }
        filterSortView2.setFilteredTab(tabViewAddTab);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-miui-handwriting-common-view-handview-pop-AIPenPopupWindow, reason: not valid java name */
    /* synthetic */ void m751xe00ad5af(FilterSortView.TabView tabView, View view) {
        if (this.isUnlimited) {
            Toast.makeText(this.mContext.getApplicationContext(), this.mContext.getApplicationContext().getString(R.string.creation_ai_page_select_limit_toast), 0).show();
            return;
        }
        this.aiType = 1;
        this.filterSortView.setFilteredTab(tabView);
        this.listener.onFullPageClick();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-miui-handwriting-common-view-handview-pop-AIPenPopupWindow, reason: not valid java name */
    /* synthetic */ void m752x5e6bd98e(FilterSortView.TabView tabView, View view) {
        this.aiType = 0;
        this.filterSortView.setFilteredTab(tabView);
        this.listener.onSelectClick();
    }

    public void setListener(OnAIPenClickListener onAIPenClickListener) {
        this.listener = onAIPenClickListener;
    }

    public void setUnlimited(boolean z) {
        this.isUnlimited = z;
    }

    public void setAiType(int i) {
        this.aiType = i;
    }
}
