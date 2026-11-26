package com.xiaomi.passport.ui.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.hardware.DataSpace;
import com.xiaomi.account.passportsdk.account_sso.R;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.internal.util.LinkSpanHelper;
import com.xiaomi.passport.ui.license.AgreementAndPrivacyHelper;
import com.xiaomi.passport.ui.license.LoginAgreementAndPrivacy;
import com.xiaomi.passport.ui.utils.PassportPageIntent;
import com.xiaomi.phonenum.data.AccountCertification;

/* JADX INFO: loaded from: classes4.dex */
public class AgreementView extends FrameLayout {
    private static final String TAG = "AgreementView";
    private CheckBox mCbUserAgreement;
    private AccountCertification.Source mLicenseExtraOperatorSource;
    private LoginAgreementAndPrivacy mLoginAgreementAndPrivacy;
    private TextView mTvUserAgreement;

    public AgreementView(Context context) {
        super(context);
        initView(context);
    }

    public AgreementView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public AgreementView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.passport_layout_agreement_view, this);
        this.mCbUserAgreement = (CheckBox) findViewById(R.id.user_agreement_checkbox);
        this.mTvUserAgreement = (TextView) findViewById(R.id.user_agreement_hint);
    }

    public void setCheckBoxVisibility(int i) {
        this.mCbUserAgreement.setVisibility(i);
    }

    public void setUserAgreementSelected(boolean z) {
        this.mCbUserAgreement.setChecked(z);
    }

    public void setLoginAgreementAndPrivacy(LoginAgreementAndPrivacy loginAgreementAndPrivacy) {
        this.mLoginAgreementAndPrivacy = loginAgreementAndPrivacy;
        AccountLogger.log(TAG, "setLoginAgreementAndPrivacy>>>" + this.mLoginAgreementAndPrivacy);
    }

    public boolean isUserAgreedProtocol() {
        return this.mCbUserAgreement.isChecked();
    }

    public void updateUserAgreement(PhoneAccount[] phoneAccountArr) {
        if (phoneAccountArr != null && phoneAccountArr.length > 0) {
            for (PhoneAccount phoneAccount : phoneAccountArr) {
                if (phoneAccount != null && this.mLicenseExtraOperatorSource == null) {
                    this.mLicenseExtraOperatorSource = phoneAccount.accountCertification.source;
                }
            }
        }
        LoginAgreementAndPrivacy loginAgreementAndPrivacy = this.mLoginAgreementAndPrivacy;
        if (loginAgreementAndPrivacy != null && !TextUtils.isEmpty(loginAgreementAndPrivacy.normalTextHexColor)) {
            this.mTvUserAgreement.setTextColor(Color.parseColor(this.mLoginAgreementAndPrivacy.normalTextHexColor));
        }
        String appAgreement = getAppAgreement();
        this.mTvUserAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        TextView textView = this.mTvUserAgreement;
        Context context = getContext();
        LoginAgreementAndPrivacy loginAgreementAndPrivacy2 = this.mLoginAgreementAndPrivacy;
        String str = loginAgreementAndPrivacy2 == null ? null : loginAgreementAndPrivacy2.clickLineHexColor;
        LoginAgreementAndPrivacy loginAgreementAndPrivacy3 = this.mLoginAgreementAndPrivacy;
        textView.setText(LinkSpanHelper.adaptLinkStyleAndAction(context, appAgreement, str, loginAgreementAndPrivacy3 == null || loginAgreementAndPrivacy3.isShowClinkLineUnderLine, new LinkClickListener(getContext().getApplicationContext())));
    }

    public String getAppAgreement() {
        LoginAgreementAndPrivacy loginAgreementAndPrivacy = this.mLoginAgreementAndPrivacy;
        LoginAgreementAndPrivacy.Type type = loginAgreementAndPrivacy == null ? LoginAgreementAndPrivacy.Type.DEF : loginAgreementAndPrivacy.type;
        Context context = getContext();
        String userAgreementUrl = AgreementAndPrivacyHelper.getUserAgreementUrl();
        String userPrivacyUrl = AgreementAndPrivacyHelper.getUserPrivacyUrl();
        if (type == LoginAgreementAndPrivacy.Type.APP_JOIN) {
            AccountCertification.Source source = this.mLicenseExtraOperatorSource;
            if (source != null) {
                return getOperatorAgreement(type, source.type, userAgreementUrl, userPrivacyUrl, this.mLicenseExtraOperatorSource.link);
            }
            return context.getString(R.string.passport_user_agreement_hint_3rd_app, this.mLoginAgreementAndPrivacy.tripartiteAppAgreementUrl, this.mLoginAgreementAndPrivacy.tripartiteAppPrivacyUrl, userAgreementUrl, userPrivacyUrl);
        }
        if (type == LoginAgreementAndPrivacy.Type.APP_CUSTOM) {
            return this.mLoginAgreementAndPrivacy.tripartiteAppCustomLicense;
        }
        AccountCertification.Source source2 = this.mLicenseExtraOperatorSource;
        if (source2 != null) {
            return getOperatorAgreement(type, source2.type, userAgreementUrl, userPrivacyUrl, this.mLicenseExtraOperatorSource.link);
        }
        return context.getString(R.string.passport_user_agreement_hint_default, userAgreementUrl, userPrivacyUrl);
    }

    private String getOperatorAgreement(LoginAgreementAndPrivacy.Type type, String str, String str2, String str3, String str4) {
        int i;
        str.hashCode();
        switch (str) {
            case "MOBILE":
                if (type == LoginAgreementAndPrivacy.Type.APP_JOIN) {
                    i = R.string.passport_user_agreement_hint_3rd_app_mobile;
                    break;
                } else {
                    i = R.string.passport_user_agreement_hint_with_mobile;
                    break;
                }
                break;
            case "UNICOM":
                if (type == LoginAgreementAndPrivacy.Type.APP_JOIN) {
                    i = R.string.passport_user_agreement_hint_3rd_app_with_unicom;
                    break;
                } else {
                    i = R.string.passport_user_agreement_hint_with_unicom;
                    break;
                }
                break;
            case "TELECOM":
                if (type == LoginAgreementAndPrivacy.Type.APP_JOIN) {
                    i = R.string.passport_user_agreement_hint_3rd_app_with_telecom;
                    break;
                } else {
                    i = R.string.passport_user_agreement_hint_with_telecom;
                    break;
                }
                break;
            default:
                if (type == LoginAgreementAndPrivacy.Type.APP_JOIN) {
                    i = R.string.passport_user_agreement_hint_3rd_app;
                    break;
                } else {
                    i = R.string.passport_user_agreement_hint_default;
                    break;
                }
                break;
        }
        if (type == LoginAgreementAndPrivacy.Type.APP_JOIN) {
            return getContext().getString(i, this.mLoginAgreementAndPrivacy.tripartiteAppAgreementUrl, this.mLoginAgreementAndPrivacy.tripartiteAppPrivacyUrl, str2, str3, str4);
        }
        return getContext().getString(i, str2, str3, str4);
    }

    private static class LinkClickListener implements LinkSpanHelper.OnSpanLinkClickListener {
        public final Context appContext;

        public LinkClickListener(Context context) {
            this.appContext = context;
        }

        @Override // com.xiaomi.passport.ui.internal.util.LinkSpanHelper.OnSpanLinkClickListener
        public void onLinkClicked(View view, String str) {
            Intent externalWebPageIntentWithBestEffort = PassportPageIntent.getExternalWebPageIntentWithBestEffort(this.appContext, str);
            externalWebPageIntentWithBestEffort.addFlags(DataSpace.RANGE_LIMITED);
            this.appContext.startActivity(externalWebPageIntentWithBestEffort);
        }
    }
}
