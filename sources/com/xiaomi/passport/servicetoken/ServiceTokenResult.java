package com.xiaomi.passport.servicetoken;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.account.data.CUserId;
import com.xiaomi.account.data.ServicePh;
import com.xiaomi.account.data.ServiceSlh;
import com.xiaomi.accountsdk.utils.Coder;
import com.xiaomi.passport.utils.HttpCookies;
import java.io.IOException;
import miuix.appcompat.view.menu.HyperMenuUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ServiceTokenResult implements Parcelable {
    private static final String AM_AUTH_TOKEN_SPLIT = ",";
    public static final Parcelable.Creator<ServiceTokenResult> CREATOR = new Parcelable.Creator<ServiceTokenResult>() { // from class: com.xiaomi.passport.servicetoken.ServiceTokenResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceTokenResult createFromParcel(Parcel parcel) {
            return new ServiceTokenResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceTokenResult[] newArray(int i) {
            return new ServiceTokenResult[i];
        }
    };
    public static final String ERROR_MSG_PREFIX = "error#";
    private static final String PARCEL_BUNDLE_KEY_C_USER_ID = "cUserId";
    private static final String PARCEL_BUNDLE_KEY_ERROR_CODE = "errorCode";
    private static final String PARCEL_BUNDLE_KEY_ERROR_MESSAGE = "errorMessage";
    private static final String PARCEL_BUNDLE_KEY_ERROR_STACK_TRACE = "stackTrace";
    private static final String PARCEL_BUNDLE_KEY_INTENT = "intent";
    private static final String PARCEL_BUNDLE_KEY_PEEKED = "peeked";
    private static final String PARCEL_BUNDLE_KEY_PH = "ph";
    private static final String PARCEL_BUNDLE_KEY_SECURITY = "security";
    private static final String PARCEL_BUNDLE_KEY_SERVICE_TOKEN = "serviceToken";
    private static final String PARCEL_BUNDLE_KEY_SID = "sid";
    private static final String PARCEL_BUNDLE_KEY_SLH = "slh";
    private static final String PARCEL_BUNDLE_KEY_STS_COOKIES = "stsCookies";
    private static final String PARCEL_BUNDLE_KEY_USER_ID = "userId";
    private static final String PARCEL_V2_FLAG = "V2#";
    public static final int TO_STRING_MASK_SHOW_SECURITY = 2;
    public static final int TO_STRING_MASK_SHOW_SERVICETOKEN = 1;
    public final String cUserId;
    public final ErrorCode errorCode;
    public final String errorMessage;
    public final String errorStackTrace;
    public final Intent intent;
    public final boolean peeked;
    public final String ph;
    public final String security;
    public final String serviceToken;
    public final String sid;
    public final String slh;
    public final String stsCookies;
    private final boolean useV1Parcel;
    public final String userId;

    public enum ErrorCode {
        ERROR_UNKNOWN,
        ERROR_NONE,
        ERROR_NO_ACCOUNT,
        ERROR_APP_PERMISSION_FORBIDDEN,
        ERROR_IOERROR,
        ERROR_OLD_MIUI_ACCOUNT_MANAGER_PERMISSION_ISSUE,
        ERROR_CANCELLED,
        ERROR_AUTHENTICATOR_ERROR,
        ERROR_TIME_OUT,
        ERROR_REMOTE_EXCEPTION,
        ERROR_USER_INTERACTION_NEEDED
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ServiceTokenResult(Builder builder) {
        this.sid = builder.sid;
        this.serviceToken = builder.serviceToken;
        this.stsCookies = builder.stsCookies;
        this.security = builder.security;
        this.errorMessage = builder.errorMessage;
        this.errorCode = builder.errorCode;
        this.intent = builder.intent;
        this.errorStackTrace = builder.errorStackTrace;
        this.slh = builder.slh;
        this.ph = builder.ph;
        this.cUserId = builder.cUserId;
        this.peeked = builder.peeked;
        this.useV1Parcel = builder.useV1Parcel;
        this.userId = builder.userId;
    }

    public ServiceTokenResult addAccountInfo(Context context, Account account) {
        if (this.errorCode != ErrorCode.ERROR_NONE || TextUtils.isEmpty(this.sid) || TextUtils.isEmpty(this.serviceToken)) {
            return this;
        }
        String md5DigestUpperCase = Coder.getMd5DigestUpperCase(this.serviceToken);
        String str = CUserId.get(context, account);
        String strCheckAsPart1AndGetPart2 = checkAsPart1AndGetPart2(md5DigestUpperCase, ServiceSlh.get(context, account, this.sid));
        return new Builder(this.sid).serviceToken(this.serviceToken).stsCookies(this.stsCookies).security(this.security).errorCode(this.errorCode).errorMessage(this.errorMessage).errorStackTrace(this.errorStackTrace).peeked(this.peeked).cUserId(str).slh(strCheckAsPart1AndGetPart2).ph(checkAsPart1AndGetPart2(md5DigestUpperCase, ServicePh.get(context, account, this.sid))).userId(account.name).build();
    }

    public String toString() {
        return toString(0);
    }

    public String toAuthToken() {
        String str = this.security;
        if (str == null) {
            return this.serviceToken;
        }
        return String.format("%s%s%s", this.serviceToken, ",", str);
    }

    public String toString(int i) {
        boolean z = (i & 1) == 1;
        boolean z2 = (i & 2) == 2;
        String str = z ? this.serviceToken : "serviceTokenMasked";
        String str2 = z2 ? this.security : "securityMasked";
        String str3 = (TextUtils.isEmpty(this.userId) || this.userId.length() <= 3) ? this.cUserId : TextUtils.substring(this.userId, 0, 2) + "****";
        StringBuffer stringBuffer = new StringBuffer("ServiceTokenResult{userId=");
        stringBuffer.append(str3).append("', sid='");
        stringBuffer.append(this.sid).append("', serviceToken='");
        stringBuffer.append(str).append("', stsCookies='");
        stringBuffer.append(HttpCookies.parse(this.stsCookies).keySet()).append("', security='");
        stringBuffer.append(str2).append("', errorCode=");
        stringBuffer.append(this.errorCode);
        stringBuffer.append(", errorMessage='").append(this.errorMessage).append("', errorStackTrace='");
        stringBuffer.append(this.errorStackTrace).append("', intent=");
        stringBuffer.append(this.intent);
        stringBuffer.append(", slh='").append(this.slh).append("', ph='");
        stringBuffer.append(this.ph).append("', cUserId='");
        stringBuffer.append(this.cUserId).append("', peeked=");
        stringBuffer.append(this.peeked).append("', useV1Parcel=");
        stringBuffer.append(this.useV1Parcel);
        stringBuffer.append(CoreConstants.CURLY_RIGHT);
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ServiceTokenResult)) {
            return false;
        }
        ServiceTokenResult serviceTokenResult = (ServiceTokenResult) obj;
        if (this.userId != serviceTokenResult.userId || this.peeked != serviceTokenResult.peeked || this.useV1Parcel != serviceTokenResult.useV1Parcel) {
            return false;
        }
        String str = this.sid;
        if (str == null ? serviceTokenResult.sid != null : !str.equals(serviceTokenResult.sid)) {
            return false;
        }
        String str2 = this.serviceToken;
        if (str2 == null ? serviceTokenResult.serviceToken != null : !str2.equals(serviceTokenResult.serviceToken)) {
            return false;
        }
        String str3 = this.security;
        if (str3 == null ? serviceTokenResult.security != null : !str3.equals(serviceTokenResult.security)) {
            return false;
        }
        if (this.errorCode != serviceTokenResult.errorCode) {
            return false;
        }
        String str4 = this.errorMessage;
        if (str4 == null ? serviceTokenResult.errorMessage != null : !str4.equals(serviceTokenResult.errorMessage)) {
            return false;
        }
        String str5 = this.errorStackTrace;
        if (str5 == null ? serviceTokenResult.errorStackTrace != null : !str5.equals(serviceTokenResult.errorStackTrace)) {
            return false;
        }
        Intent intent = this.intent;
        if (intent == null ? serviceTokenResult.intent != null : !intent.equals(serviceTokenResult.intent)) {
            return false;
        }
        String str6 = this.slh;
        if (str6 == null ? serviceTokenResult.slh != null : !str6.equals(serviceTokenResult.slh)) {
            return false;
        }
        String str7 = this.ph;
        if (str7 == null ? serviceTokenResult.ph != null : !str7.equals(serviceTokenResult.ph)) {
            return false;
        }
        String str8 = this.cUserId;
        if (str8 != null) {
            return str8.equals(serviceTokenResult.cUserId);
        }
        return serviceTokenResult.cUserId == null;
    }

    public int hashCode() {
        String str = this.sid;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.serviceToken;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.security;
        int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        ErrorCode errorCode = this.errorCode;
        int iHashCode4 = (iHashCode3 + (errorCode != null ? errorCode.hashCode() : 0)) * 31;
        String str4 = this.errorMessage;
        int iHashCode5 = (iHashCode4 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.errorStackTrace;
        int iHashCode6 = (iHashCode5 + (str5 != null ? str5.hashCode() : 0)) * 31;
        Intent intent = this.intent;
        int iHashCode7 = (iHashCode6 + (intent != null ? intent.hashCode() : 0)) * 31;
        String str6 = this.slh;
        int iHashCode8 = (iHashCode7 + (str6 != null ? str6.hashCode() : 0)) * 31;
        String str7 = this.ph;
        int iHashCode9 = (iHashCode8 + (str7 != null ? str7.hashCode() : 0)) * 31;
        String str8 = this.cUserId;
        int iHashCode10 = (((((iHashCode9 + (str8 != null ? str8.hashCode() : 0)) * 31) + (this.peeked ? 1 : 0)) * 31) + (this.useV1Parcel ? 1 : 0)) * 31;
        String str9 = this.userId;
        return iHashCode10 + (str9 != null ? str9.hashCode() : 0);
    }

    private static String checkAsPart1AndGetPart2(String str, String str2) {
        if (str != null && str2 != null) {
            String[] strArrSplit = str2.split(",");
            if (strArrSplit.length == 2 && str.equalsIgnoreCase(strArrSplit[0])) {
                return strArrSplit[1];
            }
        }
        return null;
    }

    public static ServiceTokenResult create(String str, Exception exc) {
        ErrorCode errorCode;
        if (exc instanceof OperationCanceledException) {
            errorCode = ErrorCode.ERROR_CANCELLED;
        } else if (exc instanceof IOException) {
            errorCode = ErrorCode.ERROR_IOERROR;
        } else if (exc instanceof AuthenticatorException) {
            errorCode = ErrorCode.ERROR_AUTHENTICATOR_ERROR;
        } else if (exc instanceof SecurityException) {
            errorCode = ErrorCode.ERROR_OLD_MIUI_ACCOUNT_MANAGER_PERMISSION_ISSUE;
        } else {
            errorCode = ErrorCode.ERROR_UNKNOWN;
        }
        return new Builder(str).errorCode(errorCode).errorMessage(ERROR_MSG_PREFIX + (exc != null ? exc.getMessage() : "")).errorStackTrace(Log.getStackTraceString(exc)).build();
    }

    public static ServiceTokenResult create(Bundle bundle, String str, String str2, boolean z) {
        String str3;
        String str4;
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        String[] strArrSplit = str2.split(",");
        if (str != null && str.startsWith("weblogin:")) {
            str4 = strArrSplit[0];
            if (TextUtils.isEmpty(str4)) {
                return null;
            }
            str3 = null;
        } else {
            if (strArrSplit.length != 2 || TextUtils.isEmpty(strArrSplit[0]) || TextUtils.isEmpty(strArrSplit[1])) {
                return null;
            }
            String str5 = strArrSplit[0];
            str3 = strArrSplit[1];
            str4 = str5;
        }
        return new Builder(str).errorCode(ErrorCode.ERROR_NONE).serviceToken(str4).stsCookies(bundle != null ? bundle.getString(PARCEL_BUNDLE_KEY_STS_COOKIES) : null).security(str3).peeked(z).build();
    }

    public static ServiceTokenResult create(Bundle bundle, String str) {
        ErrorCode errorCode;
        if (bundle == null) {
            return new Builder(str).errorCode(ErrorCode.ERROR_UNKNOWN).build();
        }
        if (bundle.containsKey("authtoken")) {
            ServiceTokenResult serviceTokenResultCreate = create(bundle, str, bundle.getString("authtoken"), false);
            return serviceTokenResultCreate != null ? serviceTokenResultCreate : new Builder(str).errorCode(ErrorCode.ERROR_AUTHENTICATOR_ERROR).errorMessage("invalid auth token").build();
        }
        Intent intent = (Intent) bundle.getParcelable("intent");
        if (intent != null) {
            return new Builder(str).errorCode(ErrorCode.ERROR_USER_INTERACTION_NEEDED).intent(intent).build();
        }
        if (!bundle.containsKey("errorCode")) {
            return new Builder(str).errorCode(ErrorCode.ERROR_UNKNOWN).build();
        }
        int i = bundle.getInt("errorCode");
        String string = bundle.getString("errorMessage");
        switch (i) {
            case 1:
                errorCode = ErrorCode.ERROR_REMOTE_EXCEPTION;
                break;
            case 2:
            default:
                errorCode = ErrorCode.ERROR_UNKNOWN;
                break;
            case 3:
                errorCode = ErrorCode.ERROR_IOERROR;
                break;
            case 4:
                errorCode = ErrorCode.ERROR_CANCELLED;
                break;
            case 5:
                errorCode = ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                break;
            case 6:
                errorCode = ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                break;
            case 7:
                errorCode = ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                break;
            case 8:
                errorCode = ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                break;
            case 9:
                errorCode = ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                break;
        }
        return new Builder(str).errorCode(errorCode).errorMessage(i + HyperMenuUtils.GROUP_TITLE_START + string).build();
    }

    public static final class Builder {
        private String cUserId;
        private ErrorCode errorCode = ErrorCode.ERROR_NONE;
        private String errorMessage;
        private String errorStackTrace;
        private Intent intent;
        private boolean peeked;
        private String ph;
        private String security;
        private String serviceToken;
        private final String sid;
        private String slh;
        private String stsCookies;
        private boolean useV1Parcel;
        private String userId;

        public Builder(String str) {
            this.sid = str;
        }

        public Builder serviceToken(String str) {
            this.serviceToken = str;
            return this;
        }

        public Builder stsCookies(String str) {
            this.stsCookies = str;
            return this;
        }

        public Builder security(String str) {
            this.security = str;
            return this;
        }

        public Builder errorMessage(String str) {
            this.errorMessage = str;
            return this;
        }

        public Builder errorStackTrace(String str) {
            this.errorStackTrace = str;
            return this;
        }

        public Builder errorCode(ErrorCode errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder intent(Intent intent) {
            this.intent = intent;
            return this;
        }

        public Builder slh(String str) {
            this.slh = str;
            return this;
        }

        public Builder ph(String str) {
            this.ph = str;
            return this;
        }

        public Builder cUserId(String str) {
            this.cUserId = str;
            return this;
        }

        public Builder peeked(boolean z) {
            this.peeked = z;
            return this;
        }

        public Builder useV1Parcel(boolean z) {
            this.useV1Parcel = z;
            return this;
        }

        public Builder userId(String str) {
            this.userId = str;
            return this;
        }

        public static Builder copyFrom(ServiceTokenResult serviceTokenResult) {
            return new Builder(serviceTokenResult.sid).serviceToken(serviceTokenResult.serviceToken).stsCookies(serviceTokenResult.stsCookies).security(serviceTokenResult.security).errorCode(serviceTokenResult.errorCode).errorMessage(serviceTokenResult.errorMessage).errorStackTrace(serviceTokenResult.errorStackTrace).intent(serviceTokenResult.intent).slh(serviceTokenResult.slh).ph(serviceTokenResult.ph).cUserId(serviceTokenResult.cUserId).peeked(serviceTokenResult.peeked).useV1Parcel(serviceTokenResult.useV1Parcel).userId(serviceTokenResult.userId);
        }

        public ServiceTokenResult build() {
            return new ServiceTokenResult(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.useV1Parcel) {
            writeToParcelV1(parcel, i);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(PARCEL_BUNDLE_KEY_SID, this.sid);
        bundle.putString(PARCEL_BUNDLE_KEY_SERVICE_TOKEN, this.serviceToken);
        bundle.putString(PARCEL_BUNDLE_KEY_STS_COOKIES, this.stsCookies);
        bundle.putString(PARCEL_BUNDLE_KEY_SECURITY, this.security);
        ErrorCode errorCode = this.errorCode;
        bundle.putInt("errorCode", errorCode == null ? -1 : errorCode.ordinal());
        bundle.putString("errorMessage", this.errorMessage);
        bundle.putString(PARCEL_BUNDLE_KEY_ERROR_STACK_TRACE, this.errorStackTrace);
        bundle.putParcelable("intent", this.intent);
        bundle.putString(PARCEL_BUNDLE_KEY_SLH, this.slh);
        bundle.putString(PARCEL_BUNDLE_KEY_PH, this.ph);
        bundle.putString(PARCEL_BUNDLE_KEY_C_USER_ID, this.cUserId);
        bundle.putBoolean(PARCEL_BUNDLE_KEY_PEEKED, this.peeked);
        bundle.putString("userId", this.userId);
        parcel.writeString(PARCEL_V2_FLAG);
        parcel.writeBundle(bundle);
    }

    protected ServiceTokenResult(Parcel parcel) {
        String string = parcel.readString();
        if (!TextUtils.equals(PARCEL_V2_FLAG, string)) {
            this.sid = string;
            this.serviceToken = parcel.readString();
            this.stsCookies = null;
            this.security = parcel.readString();
            int i = parcel.readInt();
            this.errorCode = i == -1 ? null : ErrorCode.values()[i];
            this.errorMessage = parcel.readString();
            this.errorStackTrace = parcel.readString();
            this.intent = (Intent) parcel.readParcelable(Intent.class.getClassLoader());
            this.slh = null;
            this.ph = null;
            this.cUserId = null;
            this.peeked = false;
            this.useV1Parcel = false;
            this.userId = null;
            return;
        }
        Bundle bundle = parcel.readBundle(Intent.class.getClassLoader());
        this.sid = bundle.getString(PARCEL_BUNDLE_KEY_SID);
        this.serviceToken = bundle.getString(PARCEL_BUNDLE_KEY_SERVICE_TOKEN);
        this.stsCookies = bundle.getString(PARCEL_BUNDLE_KEY_STS_COOKIES);
        this.security = bundle.getString(PARCEL_BUNDLE_KEY_SECURITY);
        int i2 = bundle.getInt("errorCode");
        this.errorCode = i2 != -1 ? ErrorCode.values()[i2] : null;
        this.errorMessage = bundle.getString("errorMessage");
        this.errorStackTrace = bundle.getString(PARCEL_BUNDLE_KEY_ERROR_STACK_TRACE);
        this.intent = (Intent) bundle.getParcelable("intent");
        this.slh = bundle.getString(PARCEL_BUNDLE_KEY_SLH);
        this.ph = bundle.getString(PARCEL_BUNDLE_KEY_PH);
        this.cUserId = bundle.getString(PARCEL_BUNDLE_KEY_C_USER_ID);
        this.peeked = bundle.getBoolean(PARCEL_BUNDLE_KEY_PEEKED);
        this.useV1Parcel = true;
        this.userId = bundle.getString("userId");
    }

    private void writeToParcelV1(Parcel parcel, int i) {
        parcel.writeString(this.sid);
        parcel.writeString(this.serviceToken);
        parcel.writeString(this.security);
        ErrorCode errorCode = this.errorCode;
        parcel.writeInt(errorCode == null ? -1 : errorCode.ordinal());
        parcel.writeString(this.errorMessage);
        parcel.writeString(this.errorStackTrace);
        parcel.writeParcelable(this.intent, i);
    }
}
