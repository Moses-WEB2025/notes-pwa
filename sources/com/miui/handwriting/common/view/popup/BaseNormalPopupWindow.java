package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes3.dex */
public class BaseNormalPopupWindow extends BaseAppPopupWindow {
    private static final String TAG = "BaseNormalPopupWindow";
    public View mContentView;
    protected Context mContext;

    protected void initData() {
    }

    protected void initHeight() {
    }

    protected void initListener() {
    }

    protected void initView() {
    }

    protected boolean isReviceDataUpdateUi() {
        return false;
    }

    public BaseNormalPopupWindow(Context context) {
        super(context);
        this.mContext = context;
        initData();
    }

    protected void initLayout(Context context, int i) {
        Log.d(TAG, "layoutId:" + i);
        View viewInflate = View.inflate(context, i, null);
        this.mContentView = viewInflate;
        setContentView(viewInflate);
        setWidth(-2);
        setHeight(-2);
        Log.d(TAG, "mContentView:" + this.mContentView);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(null);
        if (isReviceDataUpdateUi()) {
            return;
        }
        initUi();
    }

    public void initUi() {
        initView();
        initListener();
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view) {
        initHeight();
        super.showAsDropDown(view);
    }

    @Override // com.miui.handwriting.common.view.popup.ArrowPopupWindow, android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2, int i3) {
        initHeight();
        super.showAsDropDown(view, i, i2, i3);
    }

    protected void setViewHeightFull(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.height = this.mContentView.getHeight();
        view.setLayoutParams(layoutParams);
    }
}
