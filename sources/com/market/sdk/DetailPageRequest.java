package com.market.sdk;

import android.net.Uri;

/* JADX INFO: loaded from: classes2.dex */
public class DetailPageRequest {
    private static final String KEY_APP_CLIENT_ID = "appClientId";
    private static final String KEY_APP_SIGNATURE = "appSignature";
    private static final String KEY_BACK_URL = "backUrl";
    private static final String KEY_ID = "id";
    private static final String KEY_LAUNCH_INSTALL = "launchWhenInstalled";
    private static final String KEY_NEED_TASK_ROOT = "needTaskRoot";
    private static final String KEY_NONCE = "nonce";
    private static final String KEY_REF = "ref";
    private static final String KEY_START_DOWNLOAD = "startDownload";
    private Uri.Builder builder;

    public enum PageType {
        DETAILS("mimarket://details"),
        CARD("mimarket://details/detailcard"),
        CARD_MINI("mimarket://details/detailmini");

        private String data;

        PageType(String str) {
            this.data = str;
        }
    }

    public DetailPageRequest(PageType pageType) {
        this.builder = Uri.parse(pageType.data).buildUpon();
    }

    public DetailPageRequest(String str) {
        this.builder = Uri.parse(str).buildUpon();
    }

    public void setPackageName(String str) {
        this.builder.appendQueryParameter("id", str);
    }

    public void setRef(String str) {
        this.builder.appendQueryParameter("ref", str);
    }

    public void enableAutoDownload(String str, String str2, String str3) {
        enableAutoDownload(str, str2, str3, false);
    }

    public void enableAutoDownload(String str, String str2, String str3, boolean z) {
        this.builder.appendQueryParameter("startDownload", Boolean.TRUE.toString());
        this.builder.appendQueryParameter(KEY_APP_CLIENT_ID, str);
        this.builder.appendQueryParameter(KEY_APP_SIGNATURE, str2);
        this.builder.appendQueryParameter("nonce", str3);
        this.builder.appendQueryParameter(KEY_LAUNCH_INSTALL, String.valueOf(z));
    }

    public void setBackUrl(String str) {
        this.builder.appendQueryParameter(KEY_BACK_URL, str);
        this.builder.appendQueryParameter(KEY_NEED_TASK_ROOT, Boolean.FALSE.toString());
    }

    public void appendParam(String str, String str2) {
        this.builder.appendQueryParameter(str, str2);
    }

    String getData() {
        return this.builder.build().toString();
    }
}
