package com.miui.notes.widget.view;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;
import com.miui.notes.model.NoteEntity;

/* JADX INFO: loaded from: classes4.dex */
public class WidgetViewFactory {
    public static RemoteViews createWidgetView(Context context, AppWidgetManager appWidgetManager, int i, int i2, NoteEntity noteEntity, int i3) {
        BaseWidgetView widgetView2x2;
        BaseWidgetView baseWidgetView;
        switch (i2) {
            case 1:
                widgetView2x2 = new WidgetView2x2();
                baseWidgetView = widgetView2x2;
                break;
            case 2:
                widgetView2x2 = new WidgetView4x2();
                baseWidgetView = widgetView2x2;
                break;
            case 3:
                widgetView2x2 = new WidgetView4x4();
                baseWidgetView = widgetView2x2;
                break;
            case 4:
                widgetView2x2 = new WidgetView8x4();
                baseWidgetView = widgetView2x2;
                break;
            case 5:
                widgetView2x2 = new WidgetView2x1_Notes();
                baseWidgetView = widgetView2x2;
                break;
            case 6:
                widgetView2x2 = new WidgetView2x1_Icon();
                baseWidgetView = widgetView2x2;
                break;
            case 7:
                widgetView2x2 = new WidgetView2x1_Todo();
                baseWidgetView = widgetView2x2;
                break;
            default:
                baseWidgetView = null;
                break;
        }
        if (baseWidgetView != null) {
            return baseWidgetView.getRemoteViewsByWidgetId(context, i, appWidgetManager, noteEntity, i3);
        }
        return null;
    }
}
