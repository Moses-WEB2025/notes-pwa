package com.miui.pc.feature.notes;

import android.webkit.JavascriptInterface;
import com.miui.notes.basefeature.mindnote.BaseMindNoteBridgeInterface;

/* JADX INFO: loaded from: classes4.dex */
public class PcMindNoteBridgeInterface extends BaseMindNoteBridgeInterface<PcWebMindNoteFragment> {
    public PcMindNoteBridgeInterface(PcWebMindNoteFragment pcWebMindNoteFragment) {
        super(pcWebMindNoteFragment);
    }

    @JavascriptInterface
    public void downloadMind(String str) {
        if (hasReference()) {
            getBaseFragment().saveToSynergy(str);
        }
    }
}
