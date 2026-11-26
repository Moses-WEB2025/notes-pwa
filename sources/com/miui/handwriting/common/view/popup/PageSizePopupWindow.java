package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes3.dex */
public class PageSizePopupWindow extends PopupWindow {
    private static final String TAG = "PageSizePopupWindow";
    protected Context mContext;
    protected TextView mPage16V9H;
    protected TextView mPage16V9V;
    protected TextView mPage1V1;
    protected TextView mPageA4H;
    protected TextView mPageA4V;
    protected TextView mPageCustom;
    protected TextView mPageUnbounded;
    protected LinearLayout mSelectCardView;
    protected View view;

    public PageSizePopupWindow(Context context, int i) {
        super(context);
        this.mContext = context;
        this.view = View.inflate(context, i, null);
        setAnimationStyle(R.style.window_fade_anim);
        setContentView(this.view);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        initView();
    }

    protected void initView() {
        this.mPageA4V = (TextView) this.view.findViewById(R.id.page_a4_v);
        this.mPageA4H = (TextView) this.view.findViewById(R.id.page_a4_h);
        this.mPage16V9V = (TextView) this.view.findViewById(R.id.page_16_9_v);
        this.mPage16V9H = (TextView) this.view.findViewById(R.id.page_16_9_h);
        this.mPageCustom = (TextView) this.view.findViewById(R.id.page_custom);
        this.mPageUnbounded = (TextView) this.view.findViewById(R.id.page_unbounded);
        this.mPage1V1 = (TextView) this.view.findViewById(R.id.page_1_1);
        this.mSelectCardView = (LinearLayout) this.view.findViewById(R.id.creation_select_card_view);
        this.mPage16V9V.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_v), 16, 9));
        this.mPage16V9H.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_16_9_h), 16, 9));
        this.mPageCustom.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_custom));
        this.mPageUnbounded.setText(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_unbounded));
        this.mPage1V1.setText(String.format(HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_page_size_1_1), 1, 1));
        Folme.useAt(this.mPageA4V).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mPageA4V, new AnimConfig[0]);
        Folme.useAt(this.mPageA4H).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mPageA4H, new AnimConfig[0]);
        Folme.useAt(this.mPage16V9V).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mPage16V9V, new AnimConfig[0]);
        Folme.useAt(this.mPage16V9H).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mPage16V9H, new AnimConfig[0]);
        Folme.useAt(this.mPageCustom).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mPageCustom, new AnimConfig[0]);
        Folme.useAt(this.mPageUnbounded).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mPageUnbounded, new AnimConfig[0]);
        Folme.useAt(this.mPage1V1).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).setTintMode(1).handleTouchOf(this.mPage1V1, new AnimConfig[0]);
    }

    public void onConfigurationChanged(Configuration configuration) {
        initView();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mPageA4V.setOnClickListener(onClickListener);
        this.mPageA4H.setOnClickListener(onClickListener);
        this.mPage16V9V.setOnClickListener(onClickListener);
        this.mPage16V9H.setOnClickListener(onClickListener);
        this.mPageCustom.setOnClickListener(onClickListener);
        this.mPageUnbounded.setOnClickListener(onClickListener);
        this.mPage1V1.setOnClickListener(onClickListener);
    }

    public void setDefaultItem(PageSizeConfig pageSizeConfig, int i) {
        int i2 = pageSizeConfig.width;
        int i3 = pageSizeConfig.orientation;
        Drawable drawable = this.mContext.getResources().getDrawable(R.drawable.bg_page_size_selected, null);
        int color = this.mContext.getResources().getColor(R.color.spinner_text_checked, null);
        Log.d(TAG, "setDefaultItem " + pageSizeConfig + ";  pageCount " + i);
        if (pageSizeConfig.isUnLimit) {
            this.mPageUnbounded.setBackground(drawable);
            this.mPageUnbounded.setTextColor(color);
            return;
        }
        if (i > 1) {
            this.mPageUnbounded.setTextColor(this.mContext.getResources().getColor(R.color.creation_title_edit_hint, null));
        }
        if (pageSizeConfig.type != 0) {
            this.mPageCustom.setBackground(drawable);
            return;
        }
        if (pageSizeConfig.pageType != 0) {
            int i4 = pageSizeConfig.pageType;
            if (i4 == 1) {
                this.mPageA4H.setBackground(drawable);
                this.mPageA4H.setTextColor(color);
                return;
            }
            if (i4 == 2) {
                this.mPageA4V.setBackground(drawable);
                this.mPageA4V.setTextColor(color);
                return;
            }
            if (i4 == 3) {
                this.mPage16V9H.setBackground(drawable);
                this.mPage16V9H.setTextColor(color);
                return;
            }
            if (i4 == 4) {
                this.mPage16V9V.setBackground(drawable);
                this.mPage16V9V.setTextColor(color);
                return;
            } else if (i4 == 5) {
                this.mPage1V1.setBackground(drawable);
                this.mPage1V1.setTextColor(color);
                return;
            } else {
                if (i4 != 7) {
                    return;
                }
                this.mPageCustom.setBackground(drawable);
                return;
            }
        }
        if (i3 == 0) {
            if (i2 == 755) {
                this.mPageA4V.setBackground(drawable);
                this.mPageA4V.setTextColor(color);
                return;
            } else if (i2 == 650) {
                this.mPage16V9V.setBackground(drawable);
                this.mPage16V9V.setTextColor(color);
                return;
            } else if (i2 == 1156) {
                this.mPage1V1.setBackground(drawable);
                this.mPage1V1.setTextColor(color);
                return;
            } else {
                this.mPageCustom.setBackground(drawable);
                return;
            }
        }
        if (i2 == 755) {
            this.mPageA4H.setBackground(drawable);
            this.mPageA4H.setTextColor(color);
        } else if (i2 == 650) {
            this.mPage16V9H.setBackground(drawable);
            this.mPage16V9H.setTextColor(color);
        } else if (i2 == 1156) {
            this.mPage1V1.setBackground(drawable);
            this.mPage1V1.setTextColor(color);
        } else {
            this.mPageCustom.setBackground(drawable);
        }
    }

    public void hidePageInPc() {
        this.mPageCustom.setVisibility(8);
        this.mPageUnbounded.setVisibility(8);
    }
}
