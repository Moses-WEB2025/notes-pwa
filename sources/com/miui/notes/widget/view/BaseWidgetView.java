package com.miui.notes.widget.view;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;
import com.miui.notes.model.NoteEntity;

/* JADX INFO: loaded from: classes4.dex */
abstract class BaseWidgetView {
    protected abstract int getCommonNoteLayoutId();

    protected abstract int getDefaultCommonNoteLayoutId();

    protected abstract RemoteViews getRemoteViewsByWidgetId(Context context, int i, AppWidgetManager appWidgetManager, NoteEntity noteEntity, int i2);

    protected abstract int getWidgetType();

    BaseWidgetView() {
    }
}
