package com.miui.notes.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import com.miui.notes.widget.view.WidgetViewFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NoteWidgetProvider_2x1_Todo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\b\u0016\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J \u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u0010"}, d2 = {"Lcom/miui/notes/widget/NoteWidgetProvider_2x1_Todo;", "Landroid/appwidget/AppWidgetProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onReceive", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "onUpdate", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetIds", "", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class NoteWidgetProvider_2x1_Todo extends AppWidgetProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    protected static final String TAG = "NoteWidgetProvider_2x1_TODO";

    @Override // android.appwidget.AppWidgetProvider, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onReceive(context, intent);
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (Intrinsics.areEqual(Notes.Intents.INTENT_ACTION_REFRESH_WIDGET, intent.getAction()) || Intrinsics.areEqual("miui.appwidget.action.APPWIDGET_UPDATE", intent.getAction())) {
            Companion companion = INSTANCE;
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            Intrinsics.checkNotNullExpressionValue(appWidgetManager, "getInstance(...)");
            companion.updateWidget(context, appWidgetManager, -1);
        }
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
        Intrinsics.checkNotNullParameter(appWidgetIds, "appWidgetIds");
        Logger.INSTANCE.d(TAG, "onUpdate: id = " + appWidgetIds[0]);
        appWidgetManager.updateAppWidget(appWidgetIds[0], WidgetViewFactory.createWidgetView(context, appWidgetManager, appWidgetIds[0], 7, null, Integer.MAX_VALUE));
        INSTANCE.updateWidget(context, appWidgetManager, -1);
    }

    /* JADX INFO: compiled from: NoteWidgetProvider_2x1_Todo.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/miui/notes/widget/NoteWidgetProvider_2x1_Todo$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "updateWidget", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "updateNumbers", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void updateWidget(final Context context, final AppWidgetManager appWidgetManager, final int updateNumbers) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
            Logger.INSTANCE.d(NoteWidgetProvider_2x1_Todo.TAG, "updateWidget " + updateNumbers);
            new WidgetLoadNoteTask() { // from class: com.miui.notes.widget.NoteWidgetProvider_2x1_Todo$Companion$updateWidget$1
                @Override // com.miui.notes.widget.WidgetLoadNoteTask
                public void callback(RemoteViews wrap) {
                    ComponentName componentName = new ComponentName(context, (Class<?>) NoteWidgetProvider_2x1_Todo.class);
                    Logger.INSTANCE.d("NoteWidgetProvider_2x1_TODO", "updateWidget for " + componentName + " with updateNumbers " + updateNumbers);
                    appWidgetManager.updateAppWidget(componentName, wrap);
                }
            }.executeWidget_2x1(context, appWidgetManager, 7, updateNumbers);
        }
    }
}
