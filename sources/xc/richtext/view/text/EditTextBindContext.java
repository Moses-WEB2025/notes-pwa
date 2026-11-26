package xc.richtext.view.text;

import xc.richtext.view.SpannedLayoutModel;

/* JADX INFO: loaded from: classes3.dex */
public class EditTextBindContext {
    private final CsEditText mCsEditText;
    private final SpannedLayoutModel mSpannedLayoutModel;

    public EditTextBindContext(SpannedLayoutModel spannedLayoutModel, CsEditText csEditText) {
        this.mCsEditText = csEditText;
        this.mSpannedLayoutModel = spannedLayoutModel;
    }

    public CsEditText getCsEditText() {
        return this.mCsEditText;
    }

    public SpannedLayoutModel getSpannedLayoutModel() {
        return this.mSpannedLayoutModel;
    }
}
