package com.miui.notes.component;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.miui.common.tool.UIUtils;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.cache.NoteCache;

/* JADX INFO: loaded from: classes4.dex */
public abstract class NoteBaseContent {
    protected String mContent;
    private View mItemView;

    public abstract void bind(NoteCache noteCache, BindContext bindContext, boolean z);

    public abstract View getContentView();

    protected static View inflateView(ViewGroup viewGroup, int i) {
        return UIUtils.inflateView(viewGroup, i);
    }

    protected NoteBaseContent(View view) {
        this.mItemView = view;
    }

    protected Context getContext() {
        return this.mItemView.getContext();
    }

    public View getItemView() {
        return this.mItemView;
    }

    protected String extractCleanContent(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            Character chValueOf = Character.valueOf(str.charAt(i));
            if (z || (chValueOf.charValue() != '\n' && chValueOf.charValue() != ' ' && chValueOf.charValue() != 12288)) {
                if (!z && chValueOf.charValue() != '\n' && chValueOf.charValue() != ' ' && chValueOf.charValue() != 12288) {
                    stringBuffer.append(chValueOf);
                    z = true;
                } else if (z) {
                    stringBuffer.append(chValueOf);
                }
            }
        }
        return stringBuffer.toString();
    }

    protected String extractTitle(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            Character chValueOf = Character.valueOf(str.charAt(i));
            if (z || chValueOf.charValue() != '\n') {
                if (!z && chValueOf.charValue() != '\n' && chValueOf.charValue() != ' ' && chValueOf.charValue() != 12288) {
                    stringBuffer.append(chValueOf);
                    z = true;
                } else if (z && chValueOf.charValue() != '\n') {
                    stringBuffer.append(chValueOf);
                } else if (z && chValueOf.charValue() == '\n') {
                    break;
                }
            }
        }
        return stringBuffer.toString();
    }

    public String getContent() {
        return this.mContent;
    }
}
