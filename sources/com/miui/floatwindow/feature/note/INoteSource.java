package com.miui.floatwindow.feature.note;

import android.database.Cursor;
import kotlin.Metadata;

/* JADX INFO: compiled from: INoteSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&R\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\nÀ\u0006\u0003"}, d2 = {"Lcom/miui/floatwindow/feature/note/INoteSource;", "", "refreshData", "", "data", "Landroid/database/Cursor;", "getData", "()Landroid/database/Cursor;", "setData", "(Landroid/database/Cursor;)V", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface INoteSource {
    Cursor getData();

    void refreshData();

    void setData(Cursor cursor);
}
