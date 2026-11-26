package com.miui.pc.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class CreateMenuView extends LinearLayout {
    private Context mContext;
    private int mHighLightIconResId;
    private int mIconResId;
    private ImageView mIconView;
    private ViewGroup mRootView;
    private String mTitle;
    private TextView mTitleView;

    public CreateMenuView(Context context) {
        super(context);
        this.mHighLightIconResId = -1;
        this.mContext = context;
        init();
    }

    public CreateMenuView(Context context, String str, int i) {
        super(context);
        this.mHighLightIconResId = -1;
        this.mContext = context;
        this.mTitle = str;
        this.mIconResId = i;
        init();
    }

    public CreateMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHighLightIconResId = -1;
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(this.mContext).inflate(R.layout.create_menu_view, (ViewGroup) this, true);
        this.mRootView = (ViewGroup) findViewById(R.id.create_menu_item_view);
        this.mTitleView = (TextView) findViewById(R.id.menu_title);
        this.mIconView = (ImageView) findViewById(R.id.menu_icon);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(this.mTitle)) {
            this.mTitleView.setText(this.mTitle);
        }
        int i = this.mIconResId;
        if (i > 0) {
            this.mIconView.setImageResource(i);
        }
    }

    public void setData(String str, int i, int i2) {
        this.mTitle = str;
        this.mIconResId = i;
        this.mHighLightIconResId = i2;
        initView();
    }

    @Override // android.view.View
    public void onHoverChanged(boolean z) {
        if (z) {
            this.mTitleView.setTextColor(Color.parseColor("#FFB005"));
            int i = this.mHighLightIconResId;
            if (i > 0) {
                this.mIconView.setImageResource(i);
            }
        } else {
            this.mTitleView.setTextColor(Color.parseColor("#000000"));
            int i2 = this.mIconResId;
            if (i2 > 0) {
                this.mIconView.setImageResource(i2);
            }
        }
        super.onHoverChanged(z);
    }
}
