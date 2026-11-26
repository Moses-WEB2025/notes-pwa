package com.miui.richeditor;

import android.content.Context;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.AudioSpan;
import com.miui.richeditor.style.BaseAudioSpan;

/* JADX INFO: loaded from: classes4.dex */
public class CommonStyleFactory extends VisualStyleFactory {
    public CommonStyleFactory(Context context, IAudioEditorContext iAudioEditorContext) {
        super(context, iAudioEditorContext);
    }

    @Override // com.miui.richeditor.VisualStyleFactory
    public BaseAudioSpan createAudioSpan(HtmlParser.SoundElement soundElement) {
        return new AudioSpan(this.mEditorContext, soundElement);
    }
}
