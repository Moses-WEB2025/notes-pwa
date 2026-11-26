package com.miui.notes.widget.view;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.miui.common.constant.FrameConstant;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.ui.NotesListActivity;
import com.miui.notes.ui.activity.EditActivity;
import com.miui.todo.constant.TodoIntent;

/* JADX INFO: loaded from: classes4.dex */
public class WidgetView2x1_Icon extends BaseWidgetView {
    private final int[] actions = {R.id.content_add, R.id.open_note_home, R.id.open_todo_home};

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getDefaultCommonNoteLayoutId() {
        return 0;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getWidgetType() {
        return 6;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected int getCommonNoteLayoutId() {
        return UIUtils.isNeedRefreshWidget() ? R.layout.widget_note_2x1_icon_600 : R.layout.widget_note_2x1_icon;
    }

    @Override // com.miui.notes.widget.view.BaseWidgetView
    protected RemoteViews getRemoteViewsByWidgetId(Context context, int i, AppWidgetManager appWidgetManager, NoteEntity noteEntity, int i2) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), getCommonNoteLayoutId());
        for (int i3 : this.actions) {
            Intent intent = new Intent();
            intent.putExtra("com.miui.notes.widget_id", i);
            intent.putExtra("com.miui.notes.widget_type", getWidgetType());
            intent.putExtra(FrameConstant.PARAM_FROM_WIDGET, true);
            intent.setFlags(603996160);
            if (i3 == R.id.content_add) {
                if (RomUtils.isPadMode()) {
                    intent.setClass(context, NotesListActivity.class);
                } else {
                    intent.setClass(context, EditActivity.class);
                }
                intent.setAction("android.intent.action.INSERT_OR_EDIT");
            } else if (i3 == R.id.open_note_home) {
                intent.setClass(context, NotesListActivity.class);
                intent.setAction(NoteIntent.ACTION_VIEW);
            } else if (i3 == R.id.open_todo_home) {
                intent.setClass(context, NotesListActivity.class);
                intent.setAction(TodoIntent.ACTION_VIEW);
            }
            remoteViews.setOnClickPendingIntent(i3, PendingIntent.getActivity(context, i, intent, 201326592));
        }
        return remoteViews;
    }
}
