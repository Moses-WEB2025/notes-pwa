package com.xiaomi.passport.ui.internal;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.utils.ScreenSize;
import com.xiaomi.accountsdk.utils.UIUtils;
import com.xiaomi.passport.ui.BaseActivity;
import com.xiaomi.passport.ui.utils.PassportPageIntent;

/* JADX INFO: loaded from: classes4.dex */
public abstract class LayoutWrapperActivity extends BaseActivity {
    public static final String TAG = "LayoutWrapperActivity";
    private ViewGroup mHeaderEndViewContainer;
    private ViewGroup mHeaderStartViewContainer;
    private TextView mHeaderTitle;

    public abstract void onCreateContentView(ViewGroup viewGroup);

    public void onCreateFooterView(ViewGroup viewGroup) {
    }

    public void onCreateHeaderView(ViewGroup viewGroup) {
        LayoutInflater.from(this).inflate(R.layout.passport_layout_page_header, viewGroup);
        this.mHeaderTitle = (TextView) viewGroup.findViewById(R.id.title);
        this.mHeaderStartViewContainer = (ViewGroup) viewGroup.findViewById(R.id.start_view_container);
        this.mHeaderEndViewContainer = (ViewGroup) viewGroup.findViewById(R.id.end_view_container);
        getHeaderStartView().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.LayoutWrapperActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LayoutWrapperActivity.this.finish();
            }
        });
        getHeaderEndView().setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.passport.ui.internal.LayoutWrapperActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LayoutWrapperActivity.this.statClick(R.string.passport_stat_tip_any_page_click_need_help);
                LayoutWrapperActivity layoutWrapperActivity = LayoutWrapperActivity.this;
                layoutWrapperActivity.startActivity(PassportPageIntent.getHelpCenterPageIntent(layoutWrapperActivity));
            }
        });
    }

    public void setHeaderTitle(String str) {
        TextView textView = this.mHeaderTitle;
        if (textView == null) {
            throw new IllegalStateException("do ont call this method when you override onCreateHeaderView");
        }
        textView.setText(str);
    }

    public void setHeaderStartView(View view) {
        ViewGroup viewGroup = this.mHeaderStartViewContainer;
        if (viewGroup == null) {
            throw new IllegalStateException("do ont call this method when you override onCreateHeaderView");
        }
        viewGroup.removeAllViews();
        if (view != null) {
            this.mHeaderStartViewContainer.addView(view);
        }
    }

    public View getHeaderStartView() {
        return this.mHeaderStartViewContainer.getChildAt(0);
    }

    public void setHeaderEndView(View view) {
        ViewGroup viewGroup = this.mHeaderEndViewContainer;
        if (viewGroup == null) {
            throw new IllegalStateException("do ont call this method when you override onCreateHeaderView");
        }
        viewGroup.removeAllViews();
        if (view != null) {
            this.mHeaderEndViewContainer.addView(view);
        }
    }

    public View getHeaderEndView() {
        return this.mHeaderEndViewContainer.getChildAt(0);
    }

    public void setHeaderVisible(boolean z) {
        findViewById(R.id.header_wrapper).setVisibility(z ? 0 : 8);
    }

    public void setPageBackupGround(int i) {
        findViewById(R.id.page).setBackgroundResource(i);
    }

    public void setPageHeight(int i, int i2) {
        View viewFindViewById = findViewById(R.id.page);
        ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
        layoutParams.height = i;
        viewFindViewById.setLayoutParams(layoutParams);
        ((RelativeLayout) viewFindViewById.getParent()).setGravity(i2);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(int i) {
        throw new IllegalStateException("do not invoke this method directly, implement onCreateHeaderView/onCreateContentView/onCreateFooterView");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(View view) {
        throw new IllegalStateException("do not invoke this method directly, implement onCreateHeaderView/onCreateContentView/onCreateFooterView");
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        throw new IllegalStateException("do not invoke this method directly, implement onCreateHeaderView/onCreateContentView/onCreateFooterView");
    }

    private void setupLayoutView() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.passport_activity_layout_wrapper, (ViewGroup) null);
        onCreateHeaderView((ViewGroup) viewInflate.findViewById(R.id.header_wrapper));
        onCreateContentView((ViewGroup) viewInflate.findViewById(R.id.content_wrapper));
        onCreateFooterView((ViewGroup) viewInflate.findViewById(R.id.footer_wrapper));
        super.setContentView(viewInflate);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.passport.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (needForcePortrait()) {
            setRequestedOrientation(1);
        }
        super.onCreate(bundle);
        setupLayoutView();
    }

    private boolean needForcePortrait() {
        Point contentSizeUnderCurrentRotation = ScreenSize.getContentSizeUnderCurrentRotation(this);
        int intExtra = getIntent().getIntExtra("support_landscape_min_height_dp", -1);
        if (intExtra > 0) {
            return UIUtils.dp2px((float) intExtra, this) > ((float) contentSizeUnderCurrentRotation.y);
        }
        return getResources().getDimension(R.dimen.passport_support_landscape_min_height) > ((float) contentSizeUnderCurrentRotation.y);
    }
}
