package com.miui.todo.feature.todoedit;

import android.text.SpannableStringBuilder;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.schema.RichEditorContentHandler;
import com.miui.richeditor.style.BaseAudioSpan;

/* JADX INFO: loaded from: classes4.dex */
class TodoEditorContentHandler extends RichEditorContentHandler {
    public TodoEditorContentHandler(RichEditTextView richEditTextView) {
        super(richEditTextView);
    }

    @Override // com.miui.richeditor.schema.RichEditorContentHandler, com.miui.richeditor.schema.HtmlParser.IMediaHandler
    public HtmlParser.IElementSpan handleAudio(SpannableStringBuilder spannableStringBuilder, HtmlParser.SoundElement soundElement) {
        return new BaseAudioSpan(this.mEditor, soundElement);
    }
}
