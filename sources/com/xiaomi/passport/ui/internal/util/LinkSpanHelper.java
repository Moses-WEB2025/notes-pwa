package com.xiaomi.passport.ui.internal.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.view.View;
import com.xiaomi.account.passportsdk.account_sso.R;

/* JADX INFO: loaded from: classes4.dex */
public class LinkSpanHelper {

    public interface OnSpanLinkClickListener {
        void onLinkClicked(View view, String str);
    }

    public static Spannable adaptLinkStyleAndAction(Context context, String str, String str2, boolean z, OnSpanLinkClickListener onSpanLinkClickListener) {
        int color;
        Spannable spannable = (Spannable) Html.fromHtml(str);
        if (TextUtils.isEmpty(str2)) {
            color = context.getResources().getColor(R.color.passport_link_text);
        } else {
            color = Color.parseColor(str2);
        }
        for (URLSpan uRLSpan : (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class)) {
            int spanStart = spannable.getSpanStart(uRLSpan);
            int spanEnd = spannable.getSpanEnd(uRLSpan);
            spannable.removeSpan(uRLSpan);
            spannable.setSpan(new UrlSpanWithUnderline(uRLSpan.getURL(), onSpanLinkClickListener, color, z), spanStart, spanEnd, spannable.getSpanFlags(uRLSpan));
        }
        return spannable;
    }

    public static class UrlSpanWithUnderline extends URLSpan {
        private final boolean isShowClinkLineUnderLine;
        private final OnSpanLinkClickListener linkClickListener;
        private final int linkColor;

        public UrlSpanWithUnderline(String str, OnSpanLinkClickListener onSpanLinkClickListener, int i, boolean z) {
            super(str);
            this.linkClickListener = onSpanLinkClickListener;
            this.linkColor = i;
            this.isShowClinkLineUnderLine = z;
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(this.isShowClinkLineUnderLine);
            textPaint.setColor(this.linkColor);
        }

        @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
        public void onClick(View view) {
            OnSpanLinkClickListener onSpanLinkClickListener = this.linkClickListener;
            if (onSpanLinkClickListener != null) {
                onSpanLinkClickListener.onLinkClicked(view, getURL());
            }
        }
    }
}
