package com.xiaomi.phonenum.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.phonenum.procedure.AccountPhoneNumberSourceFlag;

/* JADX INFO: loaded from: classes4.dex */
public class AccountCertification implements Parcelable {
    public static final Parcelable.Creator<AccountCertification> CREATOR = new Parcelable.Creator<AccountCertification>() { // from class: com.xiaomi.phonenum.data.AccountCertification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountCertification createFromParcel(Parcel parcel) {
            return new AccountCertification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountCertification[] newArray(int i) {
            return new AccountCertification[i];
        }
    };
    public final String activatorToken;
    public final String hashedPhoneNumber;
    public final Source source;
    public final int subId;

    public interface Getter {
        AccountCertification[] get(Context context, AccountPhoneNumberSourceFlag accountPhoneNumberSourceFlag);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected AccountCertification(Parcel parcel) {
        this.subId = parcel.readInt();
        this.hashedPhoneNumber = parcel.readString();
        this.activatorToken = parcel.readString();
        this.source = (Source) parcel.readParcelable(Source.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.subId);
        parcel.writeString(this.hashedPhoneNumber);
        parcel.writeString(this.activatorToken);
        parcel.writeParcelable(this.source, i);
    }

    public static class Source implements Parcelable {
        public static final Parcelable.Creator<Source> CREATOR = new Parcelable.Creator<Source>() { // from class: com.xiaomi.phonenum.data.AccountCertification.Source.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Source createFromParcel(Parcel parcel) {
                return new Source(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Source[] newArray(int i) {
                return new Source[i];
            }
        };
        public static final String SOURCE_TYPE_ACTIVATION = "ACTIVATION";
        public static final String SOURCE_TYPE_CACHE = "CACHE";
        public static final String SOURCE_TYPE_OPERATOR_MOBILE = "MOBILE";
        public static final String SOURCE_TYPE_OPERATOR_TELECOM = "TELECOM";
        public static final String SOURCE_TYPE_OPERATOR_UNICOM = "UNICOM";
        public final String link;
        public final String type;

        public @interface SourceType {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Source(String str, String str2) {
            this.type = str;
            this.link = str2;
        }

        protected Source(Parcel parcel) {
            this.type = parcel.readString();
            this.link = parcel.readString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.type);
            parcel.writeString(this.link);
        }

        public String toString() {
            return "Source{type='" + this.type + "', link='" + this.link + "'}";
        }
    }

    public AccountCertification(int i, String str, String str2, Source source) {
        this.subId = i;
        this.hashedPhoneNumber = str;
        this.activatorToken = str2;
        this.source = source;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AccountCertification)) {
            return false;
        }
        AccountCertification accountCertification = (AccountCertification) obj;
        return this.subId == accountCertification.subId && TextUtils.equals(this.activatorToken, accountCertification.activatorToken) && TextUtils.equals(this.hashedPhoneNumber, accountCertification.hashedPhoneNumber);
    }

    public String toString() {
        return "AccountCertification{subId=" + this.subId + ", hashedPhoneNumber='" + this.hashedPhoneNumber + "', activatorToken=@TOKEN, source=" + this.source + CoreConstants.CURLY_RIGHT;
    }
}
