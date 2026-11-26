package miuix.androidbasewidget.adapter;

import android.os.Parcelable;

/* JADX INFO: loaded from: classes5.dex */
public interface StatefulAdapter {
    void restoreState(Parcelable parcelable);

    Parcelable saveState();
}
