package com.miui.floatwindow.feature.note;

import android.database.Cursor;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.provider.NoteProviderAccessUtils;
import kotlin.Metadata;

/* JADX INFO: compiled from: FloatNoteSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lcom/miui/floatwindow/feature/note/FloatNoteSource;", "Lcom/miui/floatwindow/feature/note/INoteSource;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "refreshData", "", "data", "Landroid/database/Cursor;", "getData", "()Landroid/database/Cursor;", "setData", "(Landroid/database/Cursor;)V", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FloatNoteSource implements INoteSource {
    public static final int FLOAT_LIMIT_COUNT = 11;
    private Cursor data;

    @Override // com.miui.floatwindow.feature.note.INoteSource
    public void refreshData() {
        setData(NoteProviderAccessUtils.queryFloatNoteItems(BaseNoteApp.INSTANCE.getInstance(), 11));
    }

    @Override // com.miui.floatwindow.feature.note.INoteSource
    public Cursor getData() {
        return this.data;
    }

    @Override // com.miui.floatwindow.feature.note.INoteSource
    public void setData(Cursor cursor) {
        this.data = cursor;
    }
}
