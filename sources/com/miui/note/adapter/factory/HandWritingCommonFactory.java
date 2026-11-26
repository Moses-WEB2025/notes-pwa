package com.miui.note.adapter.factory;

import android.widget.FrameLayout;
import com.miui.handwritecommon.api.singlepage.ISinglePageHandView;
import com.miui.note.adapter.page.MultiPageHandWritingView;
import com.miui.note.adapter.page.SinglePageHandWritingView;

/* JADX INFO: loaded from: classes3.dex */
public class HandWritingCommonFactory {
    public static MultiPageHandWritingView buildMultiPage(FrameLayout frameLayout) {
        MultiPageHandWritingView multiPageHandWritingView = new MultiPageHandWritingView(frameLayout.getContext());
        frameLayout.addView(multiPageHandWritingView, 0);
        return multiPageHandWritingView;
    }

    public static ISinglePageHandView buildSinglePage(FrameLayout frameLayout) {
        frameLayout.addView(new MultiPageHandWritingView(frameLayout.getContext()), 0);
        return new SinglePageHandWritingView(frameLayout.getContext());
    }
}
