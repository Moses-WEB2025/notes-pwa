package com.xiaomi.passport.snscorelib.internal.entity;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class SNSBindParameter implements Parcelable {
    public static final Parcelable.Creator<SNSBindParameter> CREATOR = new Parcelable.Creator<SNSBindParameter>() { // from class: com.xiaomi.passport.snscorelib.internal.entity.SNSBindParameter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSBindParameter createFromParcel(Parcel parcel) {
            return new SNSBindParameter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SNSBindParameter[] newArray(int i) {
            return new SNSBindParameter[i];
        }
    };
    public final Boolean hasLocalChannel;
    public final String snsBindUrl;
    public final String snsSid;
    public final String sns_token_ph;
    public final String sns_weixin_openId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SNSBindParameter(Builder builder) {
        this.sns_token_ph = builder.sns_token_ph;
        this.sns_weixin_openId = builder.sns_weixin_openId;
        this.snsBindUrl = builder.snsBindUrl;
        this.snsSid = builder.snsSid;
        this.hasLocalChannel = builder.hasLocalChannel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2;
        parcel.writeString(this.sns_token_ph);
        parcel.writeString(this.sns_weixin_openId);
        parcel.writeString(this.snsBindUrl);
        parcel.writeString(this.snsSid);
        Boolean bool = this.hasLocalChannel;
        if (bool == null) {
            i2 = 0;
        } else {
            i2 = bool.booleanValue() ? 1 : 2;
        }
        parcel.writeByte((byte) i2);
    }

    private SNSBindParameter(Parcel parcel) {
        Boolean boolValueOf;
        this.sns_token_ph = parcel.readString();
        this.sns_weixin_openId = parcel.readString();
        this.snsBindUrl = parcel.readString();
        this.snsSid = parcel.readString();
        byte b = parcel.readByte();
        if (b == 0) {
            boolValueOf = null;
        } else {
            boolValueOf = Boolean.valueOf(b == 1);
        }
        this.hasLocalChannel = boolValueOf;
    }

    public static class Builder {
        private Boolean hasLocalChannel;
        private String snsBindUrl;
        private String snsSid;
        private String sns_token_ph;
        private String sns_weixin_openId;

        public Builder sns_token_ph(String str) {
            this.sns_token_ph = str;
            return this;
        }

        public Builder sns_weixin_openId(String str) {
            this.sns_weixin_openId = str;
            return this;
        }

        public Builder snsBindUrl(String str) {
            this.snsBindUrl = str;
            return this;
        }

        public Builder snsSid(String str) {
            this.snsSid = str;
            return this;
        }

        public Builder hasLocalChannel(Boolean bool) {
            this.hasLocalChannel = bool;
            return this;
        }

        public SNSBindParameter build() {
            return new SNSBindParameter(this);
        }
    }
}
