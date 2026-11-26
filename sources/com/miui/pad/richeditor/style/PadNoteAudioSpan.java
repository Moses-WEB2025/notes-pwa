package com.miui.pad.richeditor.style;

import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.pad.richeditor.style.audio.PadAnimBubbleDrawable;
import com.miui.richeditor.IAudioEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.AudioSpan;
import com.miui.richeditor.style.BaseAudioSpan;
import com.miui.richeditor.style.SelectableSpanListener;
import com.miui.richeditor.style.audio.WaveBubbleDrawable;

/* JADX INFO: loaded from: classes4.dex */
public class PadNoteAudioSpan extends AudioSpan {
    public PadNoteAudioSpan(IAudioEditorContext iAudioEditorContext, HtmlParser.DisplayElement displayElement) {
        this(iAudioEditorContext, displayElement, iAudioEditorContext.getSelectableSpanListener());
    }

    public PadNoteAudioSpan(IAudioEditorContext iAudioEditorContext, HtmlParser.DisplayElement displayElement, SelectableSpanListener selectableSpanListener) {
        super(iAudioEditorContext, displayElement, selectableSpanListener);
    }

    public void updateRecordingTime(int i) {
        ((PadAnimBubbleDrawable) this.mAudioBubbleDrawable).updateRecordingTime(i);
    }

    @Override // com.miui.richeditor.style.AudioSpan
    protected void refreshAudioDrawableWidth() {
        this.mAudioDrawableWidth = getContext().getResources().getDimensionPixelSize(R.dimen.pad_audio_bubble_width);
        IAudioEditorContext editorContext = getEditorContext();
        if (editorContext == null || this.mAudioDrawableWidth <= editorContext.getContentWidth()) {
            return;
        }
        this.mAudioDrawableWidth = editorContext.getContentWidth();
        Logger.INSTANCE.d("AudioTest", "refreshAudioDrawableWidth mAudioDrawableWidth = " + this.mAudioDrawableWidth);
        if (this.mAudioBubbleDrawable.getIntrinsicWidth() != this.mAudioDrawableWidth) {
            Logger.INSTANCE.d("AudioTest", "refreshAudioDrawableWidth done");
            this.mDrawable = null;
        }
    }

    @Override // com.miui.richeditor.style.AudioSpan, com.miui.richeditor.style.BaseAudioSpan
    protected WaveBubbleDrawable createAudioDrawable(int i, int i2) {
        return new PadAnimBubbleDrawable(i, i2, (getEditorContext() == null || getEditorContext().getEditorTheme() == null) ? 0 : getEditorContext().getEditorTheme().getId(), this.mAudioDrawableWidth);
    }

    @Override // com.miui.richeditor.style.AudioSpan, com.miui.richeditor.style.BaseAudioSpan
    /* JADX INFO: renamed from: clone */
    public BaseAudioSpan mo2118clone() throws CloneNotSupportedException {
        return new PadNoteAudioSpan(getEditorContext(), (HtmlParser.SoundElement) getElement(), this.mSelectSpanListener);
    }
}
