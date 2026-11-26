package com.miui.todo.feature.todoedit;

import android.R;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContentInfo;
import com.miui.common.tool.AudioUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.RichEditTextView;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.schema.NoteSchema;

/* JADX INFO: loaded from: classes4.dex */
public class TodoRichEditor extends RichEditTextView {
    private boolean isSupportDrag;

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IEditorContext
    public boolean isNeedEditorLineHeight() {
        return false;
    }

    @Override // com.miui.richeditor.RichEditTextView
    protected boolean needHandleEditMenuItem() {
        return false;
    }

    public TodoRichEditor(Context context) {
        super(context);
        this.isSupportDrag = true;
        if (UIUtils.isAboveAndroidV()) {
            setLocalePreferredLineHeightForMinimumUsed(false);
        }
    }

    public TodoRichEditor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public TodoRichEditor(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isSupportDrag = true;
        this.mEditorTheme = ResourceManager.getTheme(0);
        if (UIUtils.isAboveAndroidV()) {
            setLocalePreferredLineHeightForMinimumUsed(false);
        }
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IAudioEditorContext
    public String getAudioFileDir() {
        return AudioUtils.TODO_MP3_DIR_NAME;
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public HtmlParser.IMediaHandler createMediaHandler() {
        return new TodoEditorContentHandler(this);
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public void parseRichTextData(CharSequence charSequence) {
        if (this.mRichTextData == null || !this.mRichTextData.equals(charSequence)) {
            this.mRichTextData = charSequence;
            syncHtmlParse(this.mRichTextData);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public ContentInfo onReceiveContent(ContentInfo contentInfo) {
        CharSequence charSequenceCoerceToStyledText;
        if (Build.VERSION.SDK_INT >= 31) {
            ClipData clip = contentInfo.getClip();
            Editable text = getText();
            Context context = getContext();
            StringBuilder sb = new StringBuilder();
            try {
                int flags = contentInfo.getFlags();
                for (int i = 0; i < clip.getItemCount(); i++) {
                    if ((flags & 1) != 0) {
                        charSequenceCoerceToStyledText = clip.getItemAt(i).coerceToText(context);
                        if (charSequenceCoerceToStyledText instanceof Spanned) {
                            charSequenceCoerceToStyledText = charSequenceCoerceToStyledText.toString();
                        }
                    } else {
                        charSequenceCoerceToStyledText = clip.getItemAt(i).coerceToStyledText(context);
                    }
                    if (!TextUtils.isEmpty(charSequenceCoerceToStyledText)) {
                        sb.append(charSequenceCoerceToStyledText);
                    }
                }
                replaceSelection(text, sb.toString());
                return null;
            } catch (Exception e) {
                Logger.INSTANCE.e("TodoRichEditor", "onReceiveContent error: " + e.getMessage());
                return super.onReceiveContent(contentInfo);
            }
        }
        return super.onReceiveContent(contentInfo);
    }

    private void syncHtmlParse(CharSequence charSequence) {
        SpannableStringBuilder spannableStringBuilder;
        String string = "";
        if (charSequence == null || charSequence.length() == 0) {
            Logger.INSTANCE.d("TodoRichEditor", "syncHtmlParse text is null");
            SpannableString.valueOf("");
        }
        if (charSequence != null) {
            string = charSequence.toString();
        }
        Spannable ancient = NoteSchema.parseAncient(string, getMediaHandler(), getContext());
        if (charSequence instanceof SpannableStringBuilder) {
            spannableStringBuilder = (SpannableStringBuilder) ancient;
        } else {
            spannableStringBuilder = new SpannableStringBuilder(ancient);
        }
        try {
            getStyleFactory().converterDataToView(spannableStringBuilder);
        } catch (InterruptedException e) {
            Logger.INSTANCE.d("TodoRichEditor", "syncHtmlParse error: " + e.getMessage());
        }
        doSetRichText(charSequence, spannableStringBuilder);
    }

    public boolean isInExtractedMode() {
        return !this.isSupportDrag;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean performLongClick() {
        this.isSupportDrag = false;
        boolean zPerformLongClick = super.performLongClick();
        this.isSupportDrag = true;
        return zPerformLongClick;
    }
}
