package com.miui.notes.tool;

import android.content.Context;
import android.text.TextUtils;
import com.miui.common.tool.TextProcessUtils;
import com.miui.richeditor.util.EditorUtils;

/* JADX INFO: loaded from: classes4.dex */
public class SnippetFormatter {
    private static final int SNIPPET_MAX_LENGTH = 150;

    private SnippetFormatter() {
    }

    public static CharSequence format(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            CharSequence charSequenceNormalizeSnippet = EditorUtils.normalizeSnippet(TextProcessUtils.trimEmptyLineSequence(str).toString(), new NormalizeHandler(context), context);
            return charSequenceNormalizeSnippet.length() > 150 ? charSequenceNormalizeSnippet.subSequence(0, 150) : charSequenceNormalizeSnippet;
        }
        return getFirstSearchLine(EditorUtils.normalizeSnippet(TextProcessUtils.trimEmptyLineSequence(str).toString(), new NormalizeHandler(context), context).toString(), str2);
    }

    private static String getFirstSearchLine(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return getFormattedSnippet(str).toString();
        }
        int iIndexOf = str.toLowerCase().indexOf(str2.toLowerCase());
        if (iIndexOf < 0) {
            return getFormattedSnippet(str).toString();
        }
        if (str2.length() < 150) {
            int iLastIndexOf = str.lastIndexOf(10, iIndexOf);
            iIndexOf = iLastIndexOf < 0 ? 0 : iLastIndexOf + 1;
        }
        return str.substring(iIndexOf, Math.min(str.length(), iIndexOf + 150));
    }

    public static CharSequence getFormattedSnippet(CharSequence charSequence) {
        if (charSequence == null) {
            return charSequence;
        }
        CharSequence charSequenceTrimEmptyLineSequence = TextProcessUtils.trimEmptyLineSequence(charSequence);
        int iIndexOf = charSequenceTrimEmptyLineSequence.toString().indexOf(10);
        return iIndexOf >= 0 ? charSequenceTrimEmptyLineSequence.subSequence(0, iIndexOf) : charSequenceTrimEmptyLineSequence;
    }

    private static class NormalizeHandler extends EditorUtils.SnippetMediaHandler {
        public NormalizeHandler(Context context) {
            super(context);
        }

        @Override // com.miui.richeditor.util.EditorUtils.SnippetMediaHandler
        protected CharSequence getImageText(String str) {
            return "";
        }

        @Override // com.miui.richeditor.util.EditorUtils.SnippetMediaHandler
        protected CharSequence getAudioText(String str) {
            return "";
        }
    }
}
