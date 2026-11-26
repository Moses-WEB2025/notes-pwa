package com.xiaomi.accountsdk.account.serverpassthrougherror.data;

import android.R;
import android.app.Activity;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.xiaomi.accountsdk.account.serverpassthrougherror.ServerPassThroughErrorHandler;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class ErrorHandleInfo {
    public final HandleType handleType;
    public final String msgContent;
    public final CharSequence msgContentSpan;
    public final ButtonInfo negativeButtonInfo;
    public final ButtonInfo neutralButtonInfo;
    public final ButtonInfo positiveButtonInfo;
    public final String title;

    public enum HandleType {
        TOAST,
        DIALOG,
        NONE
    }

    public ErrorHandleInfo(Activity activity, PassThroughErrorInfo passThroughErrorInfo, PassThroughErrorInfo passThroughErrorInfo2) {
        String title = passThroughErrorInfo != null ? passThroughErrorInfo.getTitle() : null;
        title = TextUtils.isEmpty(title) ? passThroughErrorInfo2 != null ? passThroughErrorInfo2.getTitle() : null : title;
        this.title = title;
        String tips = passThroughErrorInfo != null ? passThroughErrorInfo.getTips() : null;
        String tips2 = passThroughErrorInfo2 != null ? passThroughErrorInfo2.getTips() : null;
        String str = !TextUtils.isEmpty(tips) ? tips : tips2;
        this.msgContent = str;
        if ((!TextUtils.isEmpty(title) && !TextUtils.isEmpty(str)) || !TextUtils.isEmpty(tips)) {
            this.handleType = HandleType.DIALOG;
        } else if (!TextUtils.isEmpty(tips2)) {
            this.handleType = HandleType.TOAST;
        } else {
            this.handleType = HandleType.NONE;
        }
        this.msgContentSpan = !TextUtils.isEmpty(str) ? parseMsgContentToSpan(activity, str) : "";
        ButtonInfo neutralButtonInfo = passThroughErrorInfo != null ? passThroughErrorInfo.getNeutralButtonInfo() : null;
        this.neutralButtonInfo = neutralButtonInfo;
        if (neutralButtonInfo != null && passThroughErrorInfo2 != null) {
            neutralButtonInfo.coverEmptyValueByLocalInfo(passThroughErrorInfo2.getNeutralButtonInfo());
        }
        ButtonInfo positiveButtonInfo = passThroughErrorInfo != null ? passThroughErrorInfo.getPositiveButtonInfo() : null;
        this.positiveButtonInfo = positiveButtonInfo;
        if (positiveButtonInfo != null && passThroughErrorInfo2 != null) {
            positiveButtonInfo.coverEmptyValueByLocalInfo(passThroughErrorInfo2.getPositiveButtonInfo());
        }
        ButtonInfo negativeButtonInfo = passThroughErrorInfo != null ? passThroughErrorInfo.getNegativeButtonInfo() : null;
        if (this.handleType == HandleType.DIALOG && negativeButtonInfo == null && neutralButtonInfo == null && positiveButtonInfo == null) {
            this.negativeButtonInfo = new ButtonInfo(activity.getString(R.string.ok), null, null, null, null);
        } else {
            this.negativeButtonInfo = negativeButtonInfo;
        }
        ButtonInfo buttonInfo = this.negativeButtonInfo;
        if (buttonInfo == null || passThroughErrorInfo2 == null) {
            return;
        }
        buttonInfo.coverEmptyValueByLocalInfo(passThroughErrorInfo2.getNegativeButtonInfo());
    }

    private CharSequence parseMsgContentToSpan(final Activity activity, String str) {
        Spanned spannedFromHtml = Html.fromHtml(str);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannedFromHtml);
        for (final URLSpan uRLSpan : (URLSpan[]) spannableStringBuilder.getSpans(0, spannedFromHtml.length(), URLSpan.class)) {
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.xiaomi.accountsdk.account.serverpassthrougherror.data.ErrorHandleInfo.1
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    ServerPassThroughErrorHandler.getControl().clickDialogClickable(activity, ErrorHandleInfo.this.parseSpanUrlToButtonInfo(uRLSpan.getURL()));
                }
            }, spannableStringBuilder.getSpanStart(uRLSpan), spannableStringBuilder.getSpanEnd(uRLSpan), spannableStringBuilder.getSpanFlags(uRLSpan));
            spannableStringBuilder.removeSpan(uRLSpan);
        }
        return spannableStringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ButtonInfo parseSpanUrlToButtonInfo(String str) {
        String queryParameter = "";
        String queryParameter2 = TextUtils.isEmpty(str) ? "" : str;
        HashMap map = new HashMap();
        Uri uri = Uri.parse(str);
        if ("native.jump".equals(uri.getHost())) {
            queryParameter2 = uri.getQueryParameter("extra_web_url");
            queryParameter = uri.getQueryParameter("native_page");
            for (String str2 : uri.getQueryParameterNames()) {
                map.put(str2, uri.getQueryParameter(str2));
            }
        }
        return new ButtonInfo.Builder().setNativePage(queryParameter).setExtraWebUrl(queryParameter2).setPageParams(map).build();
    }

    public String toString() {
        return "ErrorHandleInfo{handleType=" + this.handleType + ", title='" + this.title + "', msgContent='" + this.msgContent + "', negativeButtonInfo=" + this.negativeButtonInfo + ", neutralButtonInfo=" + this.neutralButtonInfo + ", positiveButtonInfo=" + this.positiveButtonInfo + CoreConstants.CURLY_RIGHT;
    }
}
