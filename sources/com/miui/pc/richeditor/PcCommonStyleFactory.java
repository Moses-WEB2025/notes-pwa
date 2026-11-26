package com.miui.pc.richeditor;

import android.content.Context;
import com.miui.pc.richeditor.style.PcCheckboxSpan;
import com.miui.pc.richeditor.style.PcEditLineHeightSpan;
import com.miui.pc.richeditor.style.PcEditorBulletSpan;
import com.miui.pc.richeditor.style.PcLinkCardSpan;
import com.miui.pc.richeditor.style.PcNoteAudioSpan;
import com.miui.richeditor.CommonStyleFactory;
import com.miui.richeditor.IAudioEditorContext;
import com.miui.richeditor.schema.HtmlParser;
import com.miui.richeditor.style.BaseAudioSpan;
import com.miui.richeditor.style.BaseImageSpan;
import com.miui.richeditor.style.CheckboxSpan;
import com.miui.richeditor.style.EditLineHeightSpan;
import com.miui.richeditor.style.EditorBulletSpan;
import com.miui.richeditor.style.LinkCardSpan;

/* JADX INFO: loaded from: classes4.dex */
public class PcCommonStyleFactory extends CommonStyleFactory {
    public PcCommonStyleFactory(Context context, IAudioEditorContext iAudioEditorContext) {
        super(context, iAudioEditorContext);
    }

    @Override // com.miui.richeditor.VisualStyleFactory
    public BaseImageSpan createImageSpan(HtmlParser.ImageElement imageElement) {
        return new PcNormalImageSpan(this.mEditorContext, imageElement);
    }

    @Override // com.miui.richeditor.VisualStyleFactory
    public CheckboxSpan createCheckboxSpanByElement(HtmlParser.CheckBoxElement checkBoxElement) {
        return new PcCheckboxSpan(this.mEditorContext, checkBoxElement);
    }

    @Override // com.miui.richeditor.VisualStyleFactory
    public CheckboxSpan createCheckboxSpan(boolean z) {
        return new PcCheckboxSpan(this.mEditorContext, z);
    }

    @Override // com.miui.richeditor.VisualStyleFactory
    public EditorBulletSpan createEditorBulletSpan() {
        return new PcEditorBulletSpan(this.mEditorContext);
    }

    @Override // com.miui.richeditor.VisualStyleFactory
    public LinkCardSpan createLinkCardSpan(HtmlParser.GeneralElement generalElement) {
        return new PcLinkCardSpan(this.mEditorContext, generalElement);
    }

    @Override // com.miui.richeditor.VisualStyleFactory
    public EditLineHeightSpan createEditLineHeightSpan(int i) {
        return new PcEditLineHeightSpan(this.mEditorContext, i);
    }

    @Override // com.miui.richeditor.CommonStyleFactory, com.miui.richeditor.VisualStyleFactory
    public BaseAudioSpan createAudioSpan(HtmlParser.SoundElement soundElement) {
        return new PcNoteAudioSpan(this.mEditorContext, soundElement);
    }
}
