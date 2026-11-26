package com.miui.notes.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.base.utils.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NoteWidgetProvider_2x1_Icon.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\b\u0016\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\r"}, d2 = {"Lcom/miui/notes/widget/NoteWidgetProvider_2x1_Icon;", "Landroid/appwidget/AppWidgetProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onUpdate", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetIds", "", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class NoteWidgetProvider_2x1_Icon extends AppWidgetProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    protected static final String TAG = "NoteWidgetProvider_2x1_ICON";

    @Override // android.appwidget.AppWidgetProvider
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
        Intrinsics.checkNotNullParameter(appWidgetIds, "appWidgetIds");
        INSTANCE.updateWidget(context, appWidgetManager);
    }

    /* JADX INFO: compiled from: NoteWidgetProvider_2x1_Icon.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0084T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/notes/widget/NoteWidgetProvider_2x1_Icon$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "updateWidget", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void updateWidget(final Context context, final AppWidgetManager appWidgetManager) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
            Logger.INSTANCE.d(NoteWidgetProvider_2x1_Icon.TAG, "updateWidget " + Log.getStackTraceString(new Throwable()));
            new WidgetLoadNoteTask() { // from class: com.miui.notes.widget.NoteWidgetProvider_2x1_Icon$Companion$updateWidget$1
                @Override // com.miui.notes.widget.WidgetLoadNoteTask
                public void callback(RemoteViews wrap) {
                    Logger.INSTANCE.d("NoteWidgetProvider_2x1_ICON", "updateWidget--");
                    appWidgetManager.updateAppWidget(new ComponentName(context, (Class<?>) NoteWidgetProvider_2x1_Icon.class), wrap);
                }
            }.executeWidget_2x1(context, appWidgetManager, 6, -1);
        }
    }
}
