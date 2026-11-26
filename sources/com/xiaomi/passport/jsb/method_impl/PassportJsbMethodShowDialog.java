package com.xiaomi.passport.jsb.method_impl;

import android.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import com.xiaomi.passport.jsb.PassportFrontendMethodInvoker;
import com.xiaomi.passport.jsb.PassportJsbMethod;
import com.xiaomi.passport.jsb.PassportJsbMethodException;
import com.xiaomi.passport.jsb.PassportJsbMethodResult;
import com.xiaomi.passport.ui.settings.SimpleDialogFragment;
import com.xiaomi.passport.webview.PassportJsbWebView;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class PassportJsbMethodShowDialog extends PassportJsbMethod {
    private AlertDialog mDialog;

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public PassportJsbMethodResult invoke(final PassportJsbWebView passportJsbWebView, JSONObject jSONObject) throws PassportJsbMethodException {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(passportJsbWebView.getContext());
        CharSequence charSequenceOptString = jSONObject.optString("title");
        String paramsStringFieldOrThrow = getParamsStringFieldOrThrow(jSONObject, "message");
        boolean zOptBoolean = jSONObject.optBoolean(SimpleDialogFragment.ARG_CANCELABLE, false);
        if (!TextUtils.isEmpty(charSequenceOptString)) {
            builder.setTitle(charSequenceOptString);
        }
        Spanned spannedFromHtml = Html.fromHtml(paramsStringFieldOrThrow);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannedFromHtml);
        for (URLSpan uRLSpan : (URLSpan[]) spannableStringBuilder.getSpans(0, spannedFromHtml.length(), URLSpan.class)) {
            int spanStart = spannableStringBuilder.getSpanStart(uRLSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(uRLSpan);
            int spanFlags = spannableStringBuilder.getSpanFlags(uRLSpan);
            final String url = uRLSpan.getURL();
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShowDialog.1
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    passportJsbWebView.loadUrl(url);
                    PassportJsbMethodShowDialog.this.mDialog.dismiss();
                }
            }, spanStart, spanEnd, spanFlags);
            spannableStringBuilder.removeSpan(uRLSpan);
        }
        builder.setMessage(spannableStringBuilder);
        builder.setCancelable(zOptBoolean);
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("positiveButton");
        if (jSONObjectOptJSONObject != null) {
            CharSequence paramsStringFieldOrThrow2 = getParamsStringFieldOrThrow(jSONObjectOptJSONObject, "text");
            final String strOptString = jSONObjectOptJSONObject.optString("callbackId");
            builder.setPositiveButton(paramsStringFieldOrThrow2, !TextUtils.isEmpty(strOptString) ? new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShowDialog.2
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    PassportFrontendMethodInvoker.invokeCallback(passportJsbWebView, strOptString, new JSONObject());
                }
            } : null);
        }
        JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("negativeButton");
        if (jSONObjectOptJSONObject2 != null) {
            CharSequence paramsStringFieldOrThrow3 = getParamsStringFieldOrThrow(jSONObjectOptJSONObject2, "text");
            final String strOptString2 = jSONObjectOptJSONObject2.optString("callbackId");
            builder.setNegativeButton(paramsStringFieldOrThrow3, !TextUtils.isEmpty(strOptString2) ? new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShowDialog.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    PassportFrontendMethodInvoker.invokeCallback(passportJsbWebView, strOptString2, new JSONObject());
                }
            } : null);
        }
        JSONObject jSONObjectOptJSONObject3 = jSONObject.optJSONObject("neutralButton");
        if (jSONObjectOptJSONObject3 != null) {
            CharSequence paramsStringFieldOrThrow4 = getParamsStringFieldOrThrow(jSONObjectOptJSONObject3, "text");
            final String strOptString3 = jSONObjectOptJSONObject3.optString("callbackId");
            builder.setNeutralButton(paramsStringFieldOrThrow4, TextUtils.isEmpty(strOptString3) ? null : new DialogInterface.OnClickListener() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShowDialog.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    PassportFrontendMethodInvoker.invokeCallback(passportJsbWebView, strOptString3, new JSONObject());
                }
            });
        }
        AlertDialog alertDialogCreate = builder.create();
        this.mDialog = alertDialogCreate;
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.xiaomi.passport.jsb.method_impl.PassportJsbMethodShowDialog.5
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                TextView textView;
                if (PassportJsbMethodShowDialog.this.mDialog == null || (textView = (TextView) PassportJsbMethodShowDialog.this.mDialog.findViewById(R.id.message)) == null) {
                    return;
                }
                textView.setLinksClickable(true);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });
        this.mDialog.show();
        return new PassportJsbMethodResult(true);
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public String getName() {
        return "showDialog";
    }

    @Override // com.xiaomi.passport.jsb.PassportJsbMethod
    public void release(PassportJsbWebView passportJsbWebView) {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mDialog = null;
        }
    }
}
