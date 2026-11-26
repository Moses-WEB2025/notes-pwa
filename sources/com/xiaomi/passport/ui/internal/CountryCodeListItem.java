package com.xiaomi.passport.ui.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.passport.utils.PhoneNumUtil;

/* JADX INFO: loaded from: classes4.dex */
public class CountryCodeListItem extends LinearLayout {
    private TextView mAreaCodeView;
    private TextView mAreaView;
    private View mHeaderLayout;
    private TextView mSectionHeader;

    public CountryCodeListItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mAreaView = (TextView) findViewById(R.id.area);
        this.mAreaCodeView = (TextView) findViewById(R.id.area_code);
        this.mSectionHeader = (TextView) findViewById(R.id.section_header);
        this.mHeaderLayout = findViewById(R.id.section_header_layout);
    }

    public void bind(PhoneNumUtil.CountryPhoneNumData countryPhoneNumData, String str, boolean z) {
        this.mAreaView.setText(countryPhoneNumData.countryName);
        this.mAreaCodeView.setText(countryPhoneNumData.countryCode);
        if (!TextUtils.isEmpty(str)) {
            this.mSectionHeader.setText(str);
            this.mHeaderLayout.setVisibility(0);
        } else {
            this.mHeaderLayout.setVisibility(8);
        }
    }
}
