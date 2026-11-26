package com.miui.pad.richeditor;

import android.content.Context;
import com.miui.pad.richeditor.style.PadNormalImageSpan;
import com.miui.pad.richeditor.style.PadNoteAudioSpan;
import com.miui.richeditor.CommonStyleFactory;
import com.miui.richeditor.IAudioEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.BaseAudioSpan;
import com.miui.richeditor.style.BaseImageSpan;

/* JADX INFO: loaded from: classes4.dex */
public class PadCommonStyleFactory extends CommonStyleFactory {
    public PadCommonStyleFactory(Context context, IAudioEditorContext iAudioEditorContext) {
        super(context, iAudioEditorContext);
    }

    @Override // com.miui.richeditor.CommonStyleFactory, com.miui.richeditor.VisualStyleFactory
    public BaseAudioSpan createAudioSpan(HtmlParser.SoundElement soundElement) {
        return new PadNoteAudioSpan(this.mEditorContext, soundElement);
    }

    @Override // com.miui.richeditor.VisualStyleFactory
    public BaseImageSpan createImageSpan(HtmlParser.ImageElement imageElement) {
        return new PadNormalImageSpan(this.mEditorContext, imageElement);
    }
}
