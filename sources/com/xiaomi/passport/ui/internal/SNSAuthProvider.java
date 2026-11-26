package com.xiaomi.passport.ui.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.passport.PassportSDK;
import com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter;
import com.xiaomi.passport.snscorelib.internal.entity.SNSLoginParameter;
import com.xiaomi.passport.snscorelib.internal.request.SNSRequest;
import com.xiaomi.passport.ui.data.PhoneAccount;
import com.xiaomi.passport.ui.presenter.PhoneAutoLoginFragmentPresenter;
import com.xiaomi.phonenum.procedure.AccountPhoneNumberSourceFlag;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class SNSAuthProvider extends AuthProvider {
    public static final String SNS_TYPE_FACEBOOK = "facebook";
    public static final String SNS_TYPE_GOOGLE = "google";
    public static final String SNS_TYPE_QQ = "qq";
    public static final String SNS_TYPE_WEIBO = "weibo";
    public static final String SNS_TYPE_WEIXIN = "weixin";
    public static final String VALUE_SNS_CANCELLED = "cancelled";
    public static final String VALUE_SNS_ERROR = "error";
    public static final String VALUE_SNS_OK = "ok";
    public static volatile SNSAuthCredential sAuthCredential;
    public static volatile SNSBindParameter sSNSBindParameter;
    private String sid;

    public void dispatchResult(Context context, String str) {
    }

    public void doRecycle() {
    }

    public abstract String getAppId(Context context);

    public abstract int getIconRes();

    public int getRequestCode() {
        return -100;
    }

    public int getTintColor() {
        return -1;
    }

    public boolean isServiceAvailable(Context context) {
        return true;
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
    }

    public abstract void startLogin(Activity activity);

    public SNSAuthProvider(String str) {
        super(str);
    }

    public static boolean isBindingSnsAccount() {
        return sSNSBindParameter != null;
    }

    public static void invalidBindParameter() {
        sSNSBindParameter = null;
    }

    public static void invalidAuthCredential() {
        sAuthCredential = null;
    }

    private void storeBindParameter(SNSBindParameter sNSBindParameter) {
        sSNSBindParameter = sNSBindParameter;
    }

    protected void storeSnsCode(Context context, String str) {
        sAuthCredential = new SnsCodeAuthCredential(this.name, getAppId(context), str, this.sid);
    }

    public void storeSnsToken(Context context, String str) {
        sAuthCredential = new SnsTokenAuthCredential(this.name, getAppId(context), str, this.sid);
    }

    public void startLogin(Activity activity, String str, String str2) {
        this.sid = str;
        startLogin(activity);
    }

    @Override // com.xiaomi.passport.ui.internal.AuthProvider
    protected AccountInfo signInWithAuthCredential(Context context, AuthCredential authCredential) throws Throwable {
        if (authCredential instanceof SnsCodeAuthCredential) {
            return signInWithSnsCodeAuthCredential(context, (SnsCodeAuthCredential) authCredential);
        }
        if (authCredential instanceof SnsTokenAuthCredential) {
            return signInWithSnsTokenAuthCredential(context, (SnsTokenAuthCredential) authCredential);
        }
        throw new IllegalStateException("not support originAuthCredential:$credential");
    }

    private AccountInfo signInWithSnsCodeAuthCredential(Context context, SnsCodeAuthCredential snsCodeAuthCredential) throws Throwable {
        return SNSRequest.snsLoginByCode(new SNSLoginParameter.Builder().phones(getPhoneNumInfo(context)).code(snsCodeAuthCredential.code).sid(snsCodeAuthCredential.sid).appid(snsCodeAuthCredential.appId).build());
    }

    private AccountInfo signInWithSnsTokenAuthCredential(Context context, SnsTokenAuthCredential snsTokenAuthCredential) throws Throwable {
        return SNSRequest.snsLoginByAccessToken(new SNSLoginParameter.Builder().phones(getPhoneNumInfo(context)).token(snsTokenAuthCredential.token).sid(snsTokenAuthCredential.sid).appid(snsTokenAuthCredential.appId).build());
    }

    private String getPhoneNumInfo(Context context) {
        List<PhoneAccount> phoneAccount;
        JSONObject jSONObject = new JSONObject();
        if (!PassportSDK.isInternational() && (phoneAccount = PhoneAutoLoginFragmentPresenter.getPhoneAccount(context, this.sid, AccountPhoneNumberSourceFlag.getFlag(0))) != null && !phoneAccount.isEmpty()) {
            for (PhoneAccount phoneAccount2 : phoneAccount) {
                try {
                    jSONObject.putOpt(phoneAccount2.accountCertification.hashedPhoneNumber, phoneAccount2.accountCertification.activatorToken);
                } catch (JSONException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return jSONObject.toString();
    }
}
