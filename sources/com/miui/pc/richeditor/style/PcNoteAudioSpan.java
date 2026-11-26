package com.miui.pc.richeditor.style;

import com.miui.notes.R;
import com.miui.richeditor.IAudioEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.AudioSpan;
import com.miui.richeditor.style.BaseAudioSpan;
import com.miui.richeditor.style.SelectableSpanListener;
import com.miui.richeditor.style.audio.AnimAudioSpanDrawable;
import com.miui.richeditor.style.audio.WaveBubbleDrawable;

/* JADX INFO: loaded from: classes4.dex */
public class PcNoteAudioSpan extends AudioSpan {
    public PcNoteAudioSpan(IAudioEditorContext iAudioEditorContext, HtmlParser.DisplayElement displayElement) {
        this(iAudioEditorContext, displayElement, iAudioEditorContext.getSelectableSpanListener());
    }

    public PcNoteAudioSpan(IAudioEditorContext iAudioEditorContext, HtmlParser.DisplayElement displayElement, SelectableSpanListener selectableSpanListener) {
        super(iAudioEditorContext, displayElement, selectableSpanListener);
    }

    @Override // com.miui.richeditor.style.AudioSpan
    protected void refreshAudioDrawableWidth() {
        this.mAudioDrawableWidth = getContext().getResources().getDimensionPixelSize(R.dimen.pc_bubble_width);
    }

    @Override // com.miui.richeditor.style.AudioSpan, com.miui.richeditor.style.BaseAudioSpan
    protected WaveBubbleDrawable createAudioDrawable(int i, int i2) {
        return new AnimAudioSpanDrawable(i, i2, (getEditorContext() == null || getEditorContext().getEditorTheme() == null) ? 0 : getEditorContext().getEditorTheme().getId(), this.mAudioDrawableWidth);
    }

    @Override // com.miui.richeditor.style.AudioSpan, com.miui.richeditor.style.BaseAudioSpan
    /* JADX INFO: renamed from: clone */
    public BaseAudioSpan mo2118clone() throws CloneNotSupportedException {
        return new PcNoteAudioSpan(getEditorContext(), (HtmlParser.SoundElement) getElement(), this.mSelectSpanListener);
    }
}
