package com.miui.pad.richeditor;

import android.R;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.editor.NoteRichEditor;
import com.miui.richeditor.MindDrawable;

/* JADX INFO: loaded from: classes4.dex */
public class PadNoteRichEditor extends NoteRichEditor {
    private int mMeetingAudioStart;

    @Override // com.miui.richeditor.RichEditTextView
    protected void initAccessibility() {
    }

    public PadNoteRichEditor(Context context) {
        super(context);
        this.mMeetingAudioStart = 0;
    }

    public PadNoteRichEditor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public PadNoteRichEditor(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMeetingAudioStart = 0;
    }

    @Override // com.miui.notes.editor.NoteRichEditor, com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public void initStyleFactory() {
        this.mStyleFactory = new PadCommonStyleFactory(getContext(), this);
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor
    public void initImageRender() {
        this.mEditorImageRender = new PadEditorImageRender(this, getContentWidth());
    }

    @Override // com.miui.notes.editor.NoteRichEditor
    protected MindDrawable createMindEntryDrawable(int i, int i2) {
        if (RomUtils.isFoldDevice()) {
            return super.createMindEntryDrawable(i, i2);
        }
        return new PadMindDrawable(NoteApp.getInstance(), i, i2);
    }

    @Override // com.miui.richeditor.RichEditTextView
    protected int getSelectLineWidth() {
        return getContext().getResources().getDimensionPixelSize(com.miui.notes.R.dimen.pad_note_edit_selectline_height);
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor, com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean canAlignNormal() {
        int iMin = Math.min(getSelectionStart(), getSelectionEnd());
        int iMax = Math.max(getSelectionStart(), getSelectionEnd());
        return isParagraphCentered(iMin, iMax) || isParagraphRighted(iMin, iMax);
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor, com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean canAlignCenter() {
        if (this.mEnableToggleAlignment) {
            return super.canAlignCenter();
        }
        if (super.canAlignCenter()) {
            return !isParagraphCentered(Math.min(getSelectionStart(), getSelectionEnd()), Math.max(getSelectionStart(), getSelectionEnd()));
        }
        return false;
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IRichEditor, com.miui.notes.basefeature.noteedit.INoteRichEditor
    public boolean canAlignRight() {
        if (this.mEnableToggleAlignment) {
            return super.canAlignRight();
        }
        if (super.canAlignRight()) {
            return !isParagraphRighted(Math.min(getSelectionStart(), getSelectionEnd()), Math.max(getSelectionStart(), getSelectionEnd()));
        }
        return false;
    }

    @Override // com.miui.richeditor.RichEditTextView, com.miui.richeditor.IAudioEditorContext, com.miui.notes.basefeature.noteedit.INoteRichEditor
    public void startAudioInput() {
        super.startAudioInput();
        this.mMeetingAudioStart = this.mAudioInputStart;
    }

    public void onMeetingTextResult(String str, boolean z) {
        if (!z) {
            Editable text = getText();
            if (text != null) {
                text.replace(this.mAudioInputStart, this.mAudioInputEnd, str);
            }
            this.mAudioInputStart += str.length();
            if (this.mAudioInputStart > 20000) {
                this.mAudioInputStart = 20000;
            }
            this.mAudioInputEnd = this.mAudioInputStart;
            setSelection(this.mAudioInputEnd);
            return;
        }
        Editable text2 = getText();
        if (text2 != null) {
            text2.replace(this.mAudioInputStart, this.mAudioInputEnd, str);
        }
        this.mAudioInputEnd = this.mAudioInputStart + str.length();
        if (this.mAudioInputEnd > 20000) {
            this.mAudioInputEnd = 20000;
        }
        setSelection(this.mAudioInputEnd);
    }

    public void onMeetingTextFull(String str) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getText());
        int length = spannableStringBuilder.length();
        if (this.mMeetingAudioStart >= 0 && this.mAudioInputEnd <= length) {
            spannableStringBuilder.replace(this.mMeetingAudioStart, this.mAudioInputEnd, (CharSequence) str);
        }
        setText(spannableStringBuilder);
        this.mAudioInputStart = this.mMeetingAudioStart + str.length();
        if (this.mAudioInputStart > 20000) {
            this.mAudioInputStart = 20000;
        }
        this.mAudioInputEnd = this.mAudioInputStart;
        setSelection(this.mAudioInputEnd);
    }
}
