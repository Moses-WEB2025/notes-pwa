package com.miui.pad.richeditor;

import android.content.Context;
import android.graphics.Bitmap;
import com.miui.notes.R;
import com.miui.richeditor.MindDrawable;

/* JADX INFO: loaded from: classes4.dex */
public class PadMindDrawable extends MindDrawable {
    public PadMindDrawable(Context context, int i, int i2) {
        super(context, i, i2);
    }

    @Override // com.miui.richeditor.MindDrawable
    protected Bitmap createMindIcon(int i) {
        return super.createMindIcon(R.drawable.ic_note_mind_entrance);
    }
}
