package com.xiaomi.onetrack;

import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;

/* JADX INFO: loaded from: classes4.dex */
public class Configuration {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private boolean f;
    private String g;
    private OneTrack.Mode h;
    private boolean i;
    private boolean j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private String o;
    private boolean p;
    private String q;
    private OneTrack.IEventHook r;

    private Configuration(Builder builder) {
        this.h = OneTrack.Mode.APP;
        this.i = true;
        this.j = true;
        this.k = true;
        this.m = true;
        this.n = false;
        this.p = false;
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = builder.d;
        this.e = builder.e;
        this.f = builder.f;
        this.g = builder.g;
        this.h = builder.h;
        this.i = builder.i;
        this.k = builder.k;
        this.j = builder.j;
        this.l = builder.l;
        this.m = builder.m;
        this.n = builder.n;
        this.o = builder.o;
        this.p = builder.p;
        this.q = builder.q;
    }

    public String getAppId() {
        return this.a;
    }

    public String getProjectId() {
        return this.b;
    }

    public String getPrivateKeyId() {
        return this.c;
    }

    public String getPluginId() {
        return this.d;
    }

    public String getChannel() {
        return this.e;
    }

    public boolean isInternational() {
        return this.f;
    }

    public String getRegion() {
        return this.g;
    }

    public OneTrack.Mode getMode() {
        return this.h;
    }

    @Deprecated
    public boolean isGAIDEnable() {
        return this.i;
    }

    public boolean isExceptionCatcherEnable() {
        return this.l;
    }

    public boolean isIMSIEnable() {
        return this.j;
    }

    public boolean isIMEIEnable() {
        return this.k;
    }

    public boolean isAutoTrackActivityAction() {
        return this.m;
    }

    public boolean isOverrideMiuiRegionSetting() {
        return this.n;
    }

    public String getInstanceId() {
        return this.o;
    }

    public boolean isUseCustomPrivacyPolicy() {
        return this.p;
    }

    public String getAdEventAppId() {
        return this.q;
    }

    public static class Builder {
        private String a;
        private String b;
        private String c;
        private String d;
        private String e;
        private boolean f;
        private String g;
        private String o;
        private String q;
        private OneTrack.Mode h = OneTrack.Mode.APP;
        private boolean i = true;
        private boolean j = true;
        private boolean k = true;
        private boolean l = false;
        private boolean m = true;
        private boolean n = false;
        private boolean p = false;

        public Builder setAppId(String str) {
            this.a = str;
            return this;
        }

        public Builder setProjectId(String str) {
            this.b = str;
            return this;
        }

        public Builder setPrivateKeyId(String str) {
            this.c = str;
            return this;
        }

        public Builder setPluginId(String str) {
            this.d = str;
            return this;
        }

        public Builder setChannel(String str) {
            this.e = str;
            return this;
        }

        public Builder setInternational(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setRegion(String str) {
            this.g = str;
            return this;
        }

        public Builder setMode(OneTrack.Mode mode) {
            this.h = mode;
            return this;
        }

        @Deprecated
        public Builder setGAIDEnable(boolean z) {
            this.i = z;
            return this;
        }

        public Builder setImeiEnable(boolean z) {
            this.k = z;
            return this;
        }

        public Builder setImsiEnable(boolean z) {
            this.j = z;
            return this;
        }

        public Builder setExceptionCatcherEnable(boolean z) {
            this.l = z;
            return this;
        }

        public Builder setAutoTrackActivityAction(boolean z) {
            this.m = z;
            return this;
        }

        public Builder setOverrideMiuiRegionSetting(boolean z) {
            this.n = z;
            return this;
        }

        public Builder setInstanceId(String str) {
            this.o = str;
            return this;
        }

        public Builder setUseCustomPrivacyPolicy(boolean z) {
            this.p = z;
            return this;
        }

        public Builder setAdEventAppId(String str) {
            this.q = str;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }

    public String toString() {
        try {
            return "Configuration{appId='" + a(this.a) + "'projectId='" + a(this.b) + "'pKeyId='" + a(this.c) + "', pluginId='" + a(this.d) + "', channel='" + this.e + "', international=" + this.f + ", region='" + this.g + "', overrideMiuiRegionSetting=" + this.n + ", mode=" + this.h + ", GAIDEnable=" + this.i + ", IMSIEnable=" + this.j + ", IMEIEnable=" + this.k + ", ExceptionCatcherEnable=" + this.l + ", instanceId=" + a(this.o) + CoreConstants.CURLY_RIGHT;
        } catch (Exception unused) {
            return "";
        }
    }

    private String a(String str) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str) && str.length() > 4) {
            for (int i = 0; i < str.length(); i++) {
                if (i == 0 || i == 1 || i == str.length() - 2 || i == str.length() - 1) {
                    sb.append(str.charAt(i));
                } else {
                    sb.append("*");
                }
            }
        } else {
            sb.append(str);
        }
        return sb.toString();
    }
}
