package com.miui.handwritecommon.api;

import android.app.Application;
import android.content.Context;
import android.view.ViewGroup;
import com.miui.handwritecommon.api.callback.SinglePageRecListener;
import com.miui.handwritecommon.api.multipage.IMultiPageHandView;
import com.miui.handwritecommon.api.multipage.MultiPageRecListener;
import com.miui.handwritecommon.api.singlepage.ISinglePageHandView;

/* JADX INFO: loaded from: classes2.dex */
public interface IHandViewBusiness {
    IMultiPageHandView addMultiPageView(ViewGroup viewGroup);

    ISinglePageHandView addSinglePageView(ViewGroup viewGroup, int i, boolean z, boolean z2);

    void applicationOnCreate(Context context);

    void attachBaseContext(Application application);

    int getSinglePageBlackBg();

    int getSinglePageDotBg();

    int getSinglePageWhiteBg();

    void initRecognizedEngine(Context context);

    void multiPageWholeRecognize(long j, String str, MultiPageRecListener multiPageRecListener);

    void singlePageWholeRecognize(String str, SinglePageRecListener singlePageRecListener);
}
