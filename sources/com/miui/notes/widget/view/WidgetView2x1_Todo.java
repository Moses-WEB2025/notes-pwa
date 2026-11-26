package com.miui.notes.widget.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.miui.common.constant.FrameConstant;
import com.miui.notes.R;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.ui.NotesListActivity;
import com.miui.todo.constant.TodoIntent;

/* JADX INFO: loaded from: classes4.dex */
public class WidgetView2x1_Todo extends BaseWidgetView {
    private int[] actions = {R.id.all_title, R.id.numbers, R.id.open_todo, android.R.id.background};

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getCommonNoteLayoutId() {
        return R.layout.widget_note_2x1_todo;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getDefaultCommonNoteLayoutId() {
        return 0;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getWidgetType() {
        return 7;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected RemoteViews getRemoteViewsByWidgetId(Context context, int i, AppWidgetManager appWidgetManager, NoteEntity noteEntity, int i2) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getCommonNoteLayoutId());
        if (i2 == Integer.MAX_VALUE) {
            remoteViews.setTextViewText(R.id.numbers, context.getResources().getText(R.string.widget_click_check));
        } else {
            remoteViews.setTextViewText(R.id.numbers, context.getString(R.string.widget_total_todo, Integer.valueOf(i2)));
        }
        remoteViews.setTextViewText(R.id.all_title, context.getResources().getText(R.string.widget_all_todo_name));
        for (int i3 : this.actions) {
            Intent intent = new Intent();
            intent.putExtra("com.miui.notes.widget_id", i);
            intent.putExtra("com.miui.notes.widget_type", getWidgetType());
            intent.setClass(context, NotesListActivity.class);
            intent.putExtra(FrameConstant.PARAM_FROM_WIDGET, true);
            intent.setFlags(603996160);
            if (i3 == R.id.open_todo) {
                intent.setAction(TodoIntent.ACTION_SHORTCUT_INSERT_OR_EDIT);
                intent.setType(TodoIntent.TYPE_ADD_TEXT);
            } else {
                intent.setAction(TodoIntent.ACTION_VIEW);
            }
            remoteViews.setOnClickPendingIntent(i3, PendingIntent.getActivity(context, i, intent, 201326592));
        }
        return remoteViews;
    }
}
