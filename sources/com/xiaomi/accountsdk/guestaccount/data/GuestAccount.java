package com.xiaomi.accountsdk.guestaccount.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes4.dex */
public final class GuestAccount implements Parcelable {
    public static final Parcelable.Creator<GuestAccount> CREATOR = new Parcelable.Creator<GuestAccount>() { // from class: com.xiaomi.accountsdk.guestaccount.data.GuestAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GuestAccount createFromParcel(Parcel parcel) {
            return new GuestAccount(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GuestAccount[] newArray(int i) {
            return new GuestAccount[i];
        }
    };
    private static final String KEY_CALL_BACK = "callback";
    private static final String KEY_CUSER_ID = "cuserid";
    private static final String KEY_PASS_TOKEN = "passtoken";
    private static final String KEY_PH = "ph";
    private static final String KEY_SECURITY = "security";
    private static final String KEY_SERVICE_TOKEN = "servicetoken";
    private static final String KEY_SID = "sid";
    private static final String KEY_SLH = "slh";
    private static final String KEY_TYPE = "type";
    private static final String KEY_USER_ID = "userid";
    public final String cUserId;
    public final String callback;
    public final String passToken;
    public final String ph;
    public final String security;
    public final String serviceToken;
    public final String sid;
    public final String slh;
    public final GuestAccountType type;
    public final String userId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private GuestAccount(Builder builder) {
        this.userId = builder.userId;
        this.cUserId = builder.cUserId;
        this.sid = builder.sid;
        this.serviceToken = builder.serviceToken;
        this.security = builder.security;
        this.passToken = builder.passToken;
        this.callback = builder.callback;
        this.slh = builder.slh;
        this.ph = builder.ph;
        this.type = builder.type;
    }

    protected GuestAccount(Parcel parcel) {
        Bundle bundle = parcel.readBundle();
        this.userId = bundle.getString(KEY_USER_ID);
        this.cUserId = bundle.getString(KEY_CUSER_ID);
        this.sid = bundle.getString(KEY_SID);
        this.serviceToken = bundle.getString(KEY_SERVICE_TOKEN);
        this.security = bundle.getString(KEY_SECURITY);
        this.passToken = bundle.getString(KEY_PASS_TOKEN);
        this.callback = bundle.getString("callback");
        this.slh = bundle.getString(KEY_SLH);
        this.ph = bundle.getString(KEY_PH);
        this.type = GuestAccountType.getFromServerValue(bundle.getInt("type"));
    }

    public GuestAccount maskPassToken() {
        return new Builder().sid(this.sid).userId(this.userId).cUserId(this.cUserId).passToken(null).serviceToken(this.serviceToken).security(this.security).callback(this.callback).slh(this.slh).ph(this.ph).type(this.type).build();
    }

    public GuestAccount copyWithNewType(GuestAccountType guestAccountType) {
        return new Builder().sid(this.sid).userId(this.userId).cUserId(this.cUserId).passToken(this.passToken).serviceToken(this.serviceToken).security(this.security).callback(this.callback).slh(this.slh).ph(this.ph).type(guestAccountType).build();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("GuestAccount{userId='");
        stringBuffer.append(this.userId).append("'cUserId='");
        stringBuffer.append(this.cUserId).append("', sid='");
        stringBuffer.append(this.sid).append("', serviceToken='");
        stringBuffer.append(this.serviceToken).append("', security='");
        stringBuffer.append(this.security).append("', passToken='");
        stringBuffer.append(this.passToken).append("', callback='");
        stringBuffer.append(this.callback).append("', slh='");
        stringBuffer.append(this.slh).append("', ph='");
        stringBuffer.append(this.ph).append("', type=");
        stringBuffer.append(this.type);
        stringBuffer.append(CoreConstants.CURLY_RIGHT);
        return stringBuffer.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GuestAccount)) {
            return false;
        }
        GuestAccount guestAccount = (GuestAccount) obj;
        String str = this.userId;
        if (str == null ? guestAccount.userId != null : !str.equals(guestAccount.userId)) {
            return false;
        }
        String str2 = this.cUserId;
        if (str2 == null ? guestAccount.cUserId != null : !str2.equals(guestAccount.cUserId)) {
            return false;
        }
        String str3 = this.sid;
        if (str3 == null ? guestAccount.sid != null : !str3.equals(guestAccount.sid)) {
            return false;
        }
        String str4 = this.serviceToken;
        if (str4 == null ? guestAccount.serviceToken != null : !str4.equals(guestAccount.serviceToken)) {
            return false;
        }
        String str5 = this.security;
        if (str5 == null ? guestAccount.security != null : !str5.equals(guestAccount.security)) {
            return false;
        }
        String str6 = this.passToken;
        if (str6 == null ? guestAccount.passToken != null : !str6.equals(guestAccount.passToken)) {
            return false;
        }
        String str7 = this.callback;
        if (str7 == null ? guestAccount.callback != null : !str7.equals(guestAccount.callback)) {
            return false;
        }
        String str8 = this.slh;
        if (str8 == null ? guestAccount.slh != null : !str8.equals(guestAccount.slh)) {
            return false;
        }
        String str9 = this.ph;
        if (str9 == null ? guestAccount.ph == null : str9.equals(guestAccount.ph)) {
            return this.type == guestAccount.type;
        }
        return false;
    }

    public int hashCode() {
        String str = this.userId;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.cUserId;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.sid;
        int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.serviceToken;
        int iHashCode4 = (iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.security;
        int iHashCode5 = (iHashCode4 + (str5 != null ? str5.hashCode() : 0)) * 31;
        String str6 = this.passToken;
        int iHashCode6 = (iHashCode5 + (str6 != null ? str6.hashCode() : 0)) * 31;
        String str7 = this.callback;
        int iHashCode7 = (iHashCode6 + (str7 != null ? str7.hashCode() : 0)) * 31;
        String str8 = this.slh;
        int iHashCode8 = (iHashCode7 + (str8 != null ? str8.hashCode() : 0)) * 31;
        String str9 = this.ph;
        int iHashCode9 = (iHashCode8 + (str9 != null ? str9.hashCode() : 0)) * 31;
        GuestAccountType guestAccountType = this.type;
        return iHashCode9 + (guestAccountType != null ? guestAccountType.hashCode() : 0);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_USER_ID, this.userId);
        bundle.putString(KEY_CUSER_ID, this.cUserId);
        bundle.putString(KEY_SID, this.sid);
        bundle.putString(KEY_SERVICE_TOKEN, this.serviceToken);
        bundle.putString(KEY_SECURITY, this.security);
        bundle.putString(KEY_PASS_TOKEN, this.passToken);
        bundle.putString("callback", this.callback);
        bundle.putString(KEY_SLH, this.slh);
        bundle.putString(KEY_PH, this.ph);
        GuestAccountType guestAccountType = this.type;
        bundle.putInt("type", guestAccountType == null ? -1 : guestAccountType.serverValue);
        parcel.writeBundle(bundle);
    }

    public static final class Builder {
        private String cUserId;
        private String callback;
        private String passToken;
        private String ph;
        private String security;
        private String serviceToken;
        private String sid;
        private String slh;
        private GuestAccountType type;
        private String userId;

        public Builder userId(String str) {
            this.userId = str;
            return this;
        }

        public Builder cUserId(String str) {
            this.cUserId = str;
            return this;
        }

        public Builder sid(String str) {
            this.sid = str;
            return this;
        }

        public Builder serviceToken(String str) {
            this.serviceToken = str;
            return this;
        }

        public Builder security(String str) {
            this.security = str;
            return this;
        }

        public Builder passToken(String str) {
            this.passToken = str;
            return this;
        }

        public Builder callback(String str) {
            this.callback = str;
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

        public Builder type(GuestAccountType guestAccountType) {
            this.type = guestAccountType;
            return this;
        }

        public GuestAccount build() {
            return new GuestAccount(this);
        }
    }
}
