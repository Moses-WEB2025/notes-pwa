package com.xiaomi.accountsdk.account.serverpassthrougherror.data;

import android.os.Parcel;
import android.os.Parcelable;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.accountsdk.request.SimpleRequest;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassThroughErrorInfo implements Parcelable {
    public static final Parcelable.Creator<PassThroughErrorInfo> CREATOR = new Parcelable.Creator<PassThroughErrorInfo>() { // from class: com.xiaomi.accountsdk.account.serverpassthrougherror.data.PassThroughErrorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PassThroughErrorInfo createFromParcel(Parcel parcel) {
            return new PassThroughErrorInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PassThroughErrorInfo[] newArray(int i) {
            return new PassThroughErrorInfo[i];
        }
    };
    private String msgContent;
    private ButtonInfo negativeButtonInfo;
    private ButtonInfo neutralButtonInfo;
    private ButtonInfo positiveButtonInfo;
    private String title;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PassThroughErrorInfo(String str, String str2, ButtonInfo buttonInfo, ButtonInfo buttonInfo2, ButtonInfo buttonInfo3) {
        this.title = str;
        this.msgContent = str2;
        this.negativeButtonInfo = buttonInfo;
        this.neutralButtonInfo = buttonInfo2;
        this.positiveButtonInfo = buttonInfo3;
    }

    public PassThroughErrorInfo(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return;
        }
        this.msgContent = jSONObject.optString("tips");
        this.title = jSONObject.optString("title");
        if (jSONObject.has("negative_button")) {
            this.negativeButtonInfo = new ButtonInfo(jSONObject.getJSONObject("negative_button"));
        }
        if (jSONObject.has("neutral_button")) {
            this.neutralButtonInfo = new ButtonInfo(jSONObject.getJSONObject("neutral_button"));
        }
        if (jSONObject.has("positive_button")) {
            this.positiveButtonInfo = new ButtonInfo(jSONObject.getJSONObject("positive_button"));
        }
    }

    public PassThroughErrorInfo(SimpleRequest.MapContent mapContent) {
        if (mapContent == null) {
            return;
        }
        Object fromBody = mapContent.getFromBody("title");
        if (fromBody instanceof String) {
            this.title = (String) fromBody;
        }
        Object fromBody2 = mapContent.getFromBody("tips");
        if (fromBody2 instanceof String) {
            this.msgContent = (String) fromBody2;
        }
        Object fromBody3 = mapContent.getFromBody("negative_button");
        if (fromBody3 instanceof Map) {
            this.negativeButtonInfo = new ButtonInfo((Map) fromBody3);
        }
        Object fromBody4 = mapContent.getFromBody("neutral_button");
        if (fromBody4 instanceof Map) {
            this.neutralButtonInfo = new ButtonInfo((Map) fromBody4);
        }
        Object fromBody5 = mapContent.getFromBody("positive_button");
        if (fromBody5 instanceof Map) {
            this.positiveButtonInfo = new ButtonInfo((Map) fromBody5);
        }
    }

    protected PassThroughErrorInfo(Parcel parcel) {
        this.title = parcel.readString();
        this.msgContent = parcel.readString();
        this.negativeButtonInfo = (ButtonInfo) parcel.readParcelable(ButtonInfo.class.getClassLoader());
        this.neutralButtonInfo = (ButtonInfo) parcel.readParcelable(ButtonInfo.class.getClassLoader());
        this.positiveButtonInfo = (ButtonInfo) parcel.readParcelable(ButtonInfo.class.getClassLoader());
    }

    public String getTitle() {
        return this.title;
    }

    public String getTips() {
        return this.msgContent;
    }

    public ButtonInfo getNegativeButtonInfo() {
        return this.negativeButtonInfo;
    }

    public ButtonInfo getNeutralButtonInfo() {
        return this.neutralButtonInfo;
    }

    public ButtonInfo getPositiveButtonInfo() {
        return this.positiveButtonInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.msgContent);
        parcel.writeParcelable(this.negativeButtonInfo, i);
        parcel.writeParcelable(this.neutralButtonInfo, i);
        parcel.writeParcelable(this.positiveButtonInfo, i);
    }

    public static class Builder {
        private String msgContent;
        private ButtonInfo negativeButtonInfo;
        private ButtonInfo neutralButtonInfo;
        private ButtonInfo positiveButtonInfo;
        private String title;

        public Builder setTitle(String str) {
            this.title = str;
            return this;
        }

        public Builder setMsgContent(String str) {
            this.msgContent = str;
            return this;
        }

        public Builder setNegativeButtonInfo(ButtonInfo buttonInfo) {
            this.negativeButtonInfo = buttonInfo;
            return this;
        }

        public Builder setNeutralButtonInfo(ButtonInfo buttonInfo) {
            this.neutralButtonInfo = buttonInfo;
            return this;
        }

        public Builder setPositiveButtonInfo(ButtonInfo buttonInfo) {
            this.positiveButtonInfo = buttonInfo;
            return this;
        }

        public PassThroughErrorInfo build() {
            return new PassThroughErrorInfo(this.title, this.msgContent, this.negativeButtonInfo, this.neutralButtonInfo, this.positiveButtonInfo);
        }
    }

    public String toString() {
        return "PassThroughErrorInfo{title='" + this.title + "', msgContent='" + this.msgContent + "', negativeButtonInfo=" + this.negativeButtonInfo + ", neutralButtonInfo=" + this.neutralButtonInfo + ", positiveButtonInfo=" + this.positiveButtonInfo + CoreConstants.CURLY_RIGHT;
    }
}
