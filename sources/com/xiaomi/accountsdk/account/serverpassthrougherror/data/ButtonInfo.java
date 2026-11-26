package com.xiaomi.accountsdk.account.serverpassthrougherror.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorConstants;
import com.xiaomi.accountsdk.account.serverpassthrougherror.base.ButtonLocalClickListener;
import com.xiaomi.accountsdk.utils.ObjectUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class ButtonInfo implements Parcelable {
    public static final Parcelable.Creator<ButtonInfo> CREATOR = new Parcelable.Creator<ButtonInfo>() { // from class: com.xiaomi.accountsdk.account.serverpassthrougherror.data.ButtonInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ButtonInfo createFromParcel(Parcel parcel) {
            return new ButtonInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ButtonInfo[] newArray(int i) {
            return new ButtonInfo[i];
        }
    };
    private String extraWebUrl;
    private ButtonLocalClickListener localClickListener;
    private String nativePage;
    private Map<String, Object> pageParams;
    private String text;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ButtonInfo(String str, String str2, String str3, Map<String, Object> map, ButtonLocalClickListener buttonLocalClickListener) {
        this.text = str;
        this.nativePage = str2;
        this.extraWebUrl = str3;
        this.pageParams = map;
        this.localClickListener = buttonLocalClickListener;
    }

    public ButtonInfo(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return;
        }
        this.text = jSONObject.optString("button_text");
        this.nativePage = jSONObject.optString("native_page");
        this.extraWebUrl = jSONObject.optString("extra_web_url");
        if (jSONObject.has("page_params")) {
            this.pageParams = ObjectUtils.jsonToMap(jSONObject.getJSONObject("page_params"));
        }
        addJumpFlagToParams();
    }

    public ButtonInfo(Map map) {
        if (map == null) {
            return;
        }
        Object obj = map.get("button_text");
        if (obj instanceof String) {
            this.text = (String) obj;
        }
        Object obj2 = map.get("native_page");
        if (obj2 instanceof String) {
            this.nativePage = (String) obj2;
        }
        Object obj3 = map.get("extra_web_url");
        if (obj3 instanceof String) {
            this.extraWebUrl = (String) obj3;
        }
        Object obj4 = map.get("page_params");
        if (obj4 instanceof Map) {
            this.pageParams = (Map) obj4;
        }
        addJumpFlagToParams();
    }

    protected ButtonInfo(Parcel parcel) {
        this.text = parcel.readString();
        this.nativePage = parcel.readString();
        this.extraWebUrl = parcel.readString();
        HashMap map = new HashMap();
        this.pageParams = map;
        parcel.readMap(map, ButtonLocalClickListener.class.getClassLoader());
    }

    private void addJumpFlagToParams() {
        if (this.pageParams == null) {
            this.pageParams = new HashMap();
        }
        this.pageParams.put(ServerPassThroughErrorConstants.PARAM_BOOLEAN_IS_FROM_PASS_THROUGH_ERROR_JUMP, true);
    }

    public void coverEmptyValueByLocalInfo(ButtonInfo buttonInfo) {
        if (buttonInfo == null) {
            return;
        }
        if (TextUtils.isEmpty(this.text)) {
            this.text = buttonInfo.text;
        }
        if (TextUtils.isEmpty(this.nativePage)) {
            this.nativePage = buttonInfo.nativePage;
        }
        if (TextUtils.isEmpty(this.extraWebUrl)) {
            this.extraWebUrl = buttonInfo.extraWebUrl;
        }
        Map<String, Object> map = this.pageParams;
        if (map == null) {
            this.pageParams = buttonInfo.pageParams;
        } else {
            Map<String, Object> map2 = buttonInfo.pageParams;
            if (map2 != null) {
                map.putAll(map2);
            }
        }
        if (this.localClickListener == null) {
            this.localClickListener = buttonInfo.localClickListener;
        }
    }

    public String getText() {
        return this.text;
    }

    public String getNativePage() {
        return this.nativePage;
    }

    public String getExtraWebUrl() {
        return this.extraWebUrl;
    }

    public Map<String, Object> getPageParams() {
        return this.pageParams;
    }

    public ButtonLocalClickListener getLocalClickListener() {
        return this.localClickListener;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.text);
        parcel.writeString(this.nativePage);
        parcel.writeString(this.extraWebUrl);
        parcel.writeMap(this.pageParams);
    }

    public ButtonInfo copy() {
        return new Builder().setText(this.text).setExtraWebUrl(this.extraWebUrl).setNativePage(this.nativePage).setPageParams(new HashMap(this.pageParams)).setLocalClickListener(this.localClickListener).build();
    }

    public static class Builder {
        private String extraWebUrl;
        private ButtonLocalClickListener localClickListener;
        private String nativePage;
        private Map<String, Object> pageParams;
        private String text;

        public Builder setText(String str) {
            this.text = str;
            return this;
        }

        public Builder setNativePage(String str) {
            this.nativePage = str;
            return this;
        }

        public Builder setExtraWebUrl(String str) {
            this.extraWebUrl = str;
            return this;
        }

        public Builder setPageParams(Map<String, Object> map) {
            this.pageParams = map;
            return this;
        }

        public Builder setLocalClickListener(ButtonLocalClickListener buttonLocalClickListener) {
            this.localClickListener = buttonLocalClickListener;
            return this;
        }

        public ButtonInfo build() {
            return new ButtonInfo(this.text, this.nativePage, this.extraWebUrl, this.pageParams, this.localClickListener);
        }
    }

    public String toString() {
        return "ButtonInfo{text='" + this.text + "', nativePage='" + this.nativePage + "', extraWebUrl='" + this.extraWebUrl + "', pageParams=" + this.pageParams + ", localClickListener=" + this.localClickListener + CoreConstants.CURLY_RIGHT;
    }
}
