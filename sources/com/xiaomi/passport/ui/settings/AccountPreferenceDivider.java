package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.xiaomi.account.passportsdk.account_sso.R;

/* JADX INFO: loaded from: classes4.dex */
public class AccountPreferenceDivider extends RelativeLayout {
    public AccountPreferenceDivider(Context context) {
        super(context);
        initLayout();
    }

    public AccountPreferenceDivider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initLayout();
    }

    public AccountPreferenceDivider(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initLayout();
    }

    private void initLayout() {
        View view = new View(getContext());
        view.setBackground(new ColorDrawable(getResources().getColor(R.color.passport_preference_divider_color)));
        setAlpha(0.1f);
        addView(view);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = 1;
        layoutParams.addRule(13);
    }
}
