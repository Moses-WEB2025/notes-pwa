package com.miui.notes.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.provider.Notes;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.notes.tool.DataUtils;
import defpackage.R2;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import kotlin.ExceptionsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import miuix.android.content.SystemIntent;

/* JADX INFO: compiled from: NoteWidgetProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000 *2\u00020\u0001:\u0001*B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u001a\u0010\u0017\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J*\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00032\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J6\u0010 \u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010!\u001a\u00020\u00192\u0006\u0010\"\u001a\u00020\u00192\b\u0010#\u001a\u0004\u0018\u00010\u001f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002J \u0010$\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0018\u0010%\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010&\u001a\u00020'H\u0016J \u0010(\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u0019H\u0004J\u0018\u0010)\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\b\u001a\n \n*\u0004\u0018\u00010\t0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010¨\u0006+"}, d2 = {"Lcom/miui/notes/widget/NoteWidgetProvider;", "Landroid/appwidget/AppWidgetProvider;", "widgetType", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(I)V", "getWidgetType", "()I", "TAG", "", "kotlin.jvm.PlatformType", "getTAG", "()Ljava/lang/String;", Action.SCOPE_ATTRIBUTE, "Lkotlinx/coroutines/CoroutineScope;", "getScope", "()Lkotlinx/coroutines/CoroutineScope;", "scope$delegate", "Lkotlin/Lazy;", "onEnabled", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "onDeleted", "appWidgetIds", "", "onAppWidgetOptionsChanged", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetId", "newOptions", "Landroid/os/Bundle;", "onIdRemap", "oldWidgetIds", "newWidgetIds", "options", "onUpdate", "onReceive", "intent", "Landroid/content/Intent;", "update", "dataMigration", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class NoteWidgetProvider extends AppWidgetProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String INTENT_EXTRA_FOLDER_ID = "com.miui.notes.folder_id";
    public static final String INTENT_EXTRA_IS_HIDDEN = "com.miui.notes.is_hidden";
    public static final String SCHEME_PAD_WIDGET_NOTE_LIST_PAGE = "pad_widget_note_list";
    public static final String SCHEME_PHONE_WIDGET_NOTE_LIST_PAGE = "phone_widget_note_list";
    public static final String SCHEME_WIDGET_NOTE_LIST_PAGE = "widget_note_list";
    private static final ExecutorService sExecutorService;
    private final String TAG = getClass().getSimpleName();

    /* JADX INFO: renamed from: scope$delegate, reason: from kotlin metadata */
    private final Lazy scope = LazyKt.lazy(new Function0() { // from class: com.miui.notes.widget.NoteWidgetProvider$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return NoteWidgetProvider.scope_delegate$lambda$0();
        }
    });
    private final int widgetType;

    @JvmStatic
    public static final void updateWidgetView(Context context, AppWidgetManager appWidgetManager, int i, int i2) {
        INSTANCE.updateWidgetView(context, appWidgetManager, i, i2);
    }

    public NoteWidgetProvider(int i) {
        this.widgetType = i;
    }

    public final int getWidgetType() {
        return this.widgetType;
    }

    public final String getTAG() {
        return this.TAG;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CoroutineScope scope_delegate$lambda$0() {
        return CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    }

    public final CoroutineScope getScope() {
        return (CoroutineScope) this.scope.getValue();
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Logger logger = Logger.INSTANCE;
        String TAG = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
        logger.d(TAG, "onEnabled");
        super.onEnabled(context);
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onDeleted(Context context, int[] appWidgetIds) {
        Intrinsics.checkNotNullParameter(appWidgetIds, "appWidgetIds");
        Logger logger = Logger.INSTANCE;
        String TAG = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
        logger.i(TAG, "onDeleted:" + Arrays.stream(appWidgetIds).asLongStream());
        ContentValues contentValues = new ContentValues();
        contentValues.put("widget_id", (Integer) 0);
        BuildersKt__Builders_commonKt.launch$default(getScope(), null, null, new C03251(appWidgetIds, this, context, contentValues, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.widget.NoteWidgetProvider$onDeleted$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteWidgetProvider.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.widget.NoteWidgetProvider$onDeleted$1", f = "NoteWidgetProvider.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class C03251 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int[] $appWidgetIds;
        final /* synthetic */ Context $context;
        final /* synthetic */ ContentValues $values;
        int label;
        final /* synthetic */ NoteWidgetProvider this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03251(int[] iArr, NoteWidgetProvider noteWidgetProvider, Context context, ContentValues contentValues, Continuation<? super C03251> continuation) {
            super(2, continuation);
            this.$appWidgetIds = iArr;
            this.this$0 = noteWidgetProvider;
            this.$context = context;
            this.$values = contentValues;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C03251(this.$appWidgetIds, this.this$0, this.$context, this.$values, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03251) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            int[] iArr = this.$appWidgetIds;
            NoteWidgetProvider noteWidgetProvider = this.this$0;
            Context context = this.$context;
            ContentValues contentValues = this.$values;
            for (int i : iArr) {
                Logger logger = Logger.INSTANCE;
                String tag = noteWidgetProvider.getTAG();
                Intrinsics.checkNotNullExpressionValue(tag, "<get-TAG>(...)");
                logger.i(tag, "-----onDeleted------ newWidgetIds:" + i);
                DataUtils.deleteWidget(context, contentValues, "widget_id=?", new String[]{String.valueOf(i)});
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.miui.notes.widget.NoteWidgetProvider] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.util.Iterator] */
    @Override // android.appwidget.AppWidgetProvider
    public void onAppWidgetOptionsChanged(Context context, final AppWidgetManager appWidgetManager, final int appWidgetId, Bundle newOptions) {
        ?? it;
        IllegalArgumentException illegalArgumentException;
        ?? r3;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
        Logger logger = Logger.INSTANCE;
        String TAG = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
        logger.i(TAG, "onAppWidgetOptionsChanged:" + appWidgetId);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Bundle appWidgetOptions = AppWidgetManager.getInstance(context).getAppWidgetOptions(appWidgetId);
        if (appWidgetOptions != null && (appWidgetOptions.get("miuiMinusSource") instanceof String) && TextUtils.equals((String) appWidgetOptions.get("miuiMinusSource"), "default")) {
            if (appWidgetId != 0) {
                dataMigration(context, appWidgetId);
                INSTANCE.updateWidgetView(context, appWidgetManager, appWidgetId, this.widgetType);
                return;
            }
            return;
        }
        if (newOptions != null) {
            try {
                HashSet hashSet = new HashSet();
                it = newOptions.keySet().iterator();
                while (it.hasNext()) {
                    try {
                        String str = (String) it.next();
                        if (!hashSet.add(str)) {
                            Logger logger2 = Logger.INSTANCE;
                            String TAG2 = this.TAG;
                            Intrinsics.checkNotNullExpressionValue(TAG2, "TAG");
                            logger2.e(TAG2, " Duplicate key found in newOptions: " + str);
                            return;
                        }
                    } catch (IllegalArgumentException e) {
                        illegalArgumentException = e;
                        r3 = this;
                    }
                }
                try {
                    if (!newOptions.getBoolean("miuiIdChanged")) {
                        if (appWidgetId != 0) {
                            Logger logger3 = Logger.INSTANCE;
                            String TAG3 = this.TAG;
                            Intrinsics.checkNotNullExpressionValue(TAG3, "TAG");
                            logger3.d(TAG3, "onAppWidgetOptionsChanged updateWidgetView");
                            new WidgetLoadNoteTask() { // from class: com.miui.notes.widget.NoteWidgetProvider.onAppWidgetOptionsChanged.1
                                @Override // com.miui.notes.widget.WidgetLoadNoteTask
                                public void callback(RemoteViews wrap) {
                                    Logger logger4 = Logger.INSTANCE;
                                    String tag = NoteWidgetProvider.this.getTAG();
                                    Intrinsics.checkNotNullExpressionValue(tag, "<get-TAG>(...)");
                                    logger4.d(tag, "callback for widget with loadNoteTask,update widget " + appWidgetId);
                                    appWidgetManager.updateAppWidget(appWidgetId, wrap);
                                }
                            }.execute(context, appWidgetManager, appWidgetId, this.widgetType);
                            return;
                        }
                        return;
                    }
                    Logger logger4 = Logger.INSTANCE;
                    String TAG4 = this.TAG;
                    Intrinsics.checkNotNullExpressionValue(TAG4, "TAG");
                    logger4.d(TAG4, "---------miuiIdChanged-----");
                    int[] intArray = newOptions.getIntArray("miuiOldIds");
                    Intrinsics.checkNotNull(intArray);
                    int[] intArray2 = newOptions.getIntArray("miuiNewIds");
                    Intrinsics.checkNotNull(intArray2);
                    onIdRemap(context, intArray, intArray2, newOptions, appWidgetManager);
                    return;
                } catch (IllegalArgumentException e2) {
                    e = e2;
                }
            } catch (IllegalArgumentException e3) {
                e = e3;
                it = this;
            }
            illegalArgumentException = e;
            r3 = it;
            Logger logger5 = Logger.INSTANCE;
            String TAG5 = r3.TAG;
            Intrinsics.checkNotNullExpressionValue(TAG5, "TAG");
            logger5.e(TAG5, "The system detected that the Bundle data has duplicate keys or null keys: " + illegalArgumentException.getMessage());
            return;
        }
        Logger logger6 = Logger.INSTANCE;
        String TAG6 = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG6, "TAG");
        logger6.d(TAG6, "newOptions is null.");
        if (appWidgetId != 0) {
            Logger logger7 = Logger.INSTANCE;
            String TAG7 = this.TAG;
            Intrinsics.checkNotNullExpressionValue(TAG7, "TAG");
            logger7.d(TAG7, "onAppWidgetOptionsChanged updateWidgetView");
            new WidgetLoadNoteTask() { // from class: com.miui.notes.widget.NoteWidgetProvider.onAppWidgetOptionsChanged.2
                @Override // com.miui.notes.widget.WidgetLoadNoteTask
                public void callback(RemoteViews wrap) {
                    Logger logger8 = Logger.INSTANCE;
                    String tag = NoteWidgetProvider.this.getTAG();
                    Intrinsics.checkNotNullExpressionValue(tag, "<get-TAG>(...)");
                    logger8.d(tag, "callback for widget with loadNoteTask,update widget " + appWidgetId);
                    appWidgetManager.updateAppWidget(appWidgetId, wrap);
                }
            }.execute(context, appWidgetManager, appWidgetId, this.widgetType);
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.widget.NoteWidgetProvider$onIdRemap$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteWidgetProvider.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.widget.NoteWidgetProvider$onIdRemap$1", f = "NoteWidgetProvider.kt", i = {}, l = {R2.array.calc_hand_write_white_global_device_list}, m = "invokeSuspend", n = {}, s = {})
    static final class C03261 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ AppWidgetManager $appWidgetManager;
        final /* synthetic */ Context $context;
        final /* synthetic */ int[] $newWidgetIds;
        final /* synthetic */ int[] $oldWidgetIds;
        final /* synthetic */ Bundle $options;
        int label;
        final /* synthetic */ NoteWidgetProvider this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03261(int[] iArr, NoteWidgetProvider noteWidgetProvider, int[] iArr2, Context context, AppWidgetManager appWidgetManager, Bundle bundle, Continuation<? super C03261> continuation) {
            super(2, continuation);
            this.$oldWidgetIds = iArr;
            this.this$0 = noteWidgetProvider;
            this.$newWidgetIds = iArr2;
            this.$context = context;
            this.$appWidgetManager = appWidgetManager;
            this.$options = bundle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C03261(this.$oldWidgetIds, this.this$0, this.$newWidgetIds, this.$context, this.$appWidgetManager, this.$options, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03261) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.notes.widget.NoteWidgetProvider$onIdRemap$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteWidgetProvider.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/miui/notes/widget/RemoteViewsWrap;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.NoteWidgetProvider$onIdRemap$1$1", f = "NoteWidgetProvider.kt", i = {0, 0, 0, 0, 0, 0, 0, 0, 0}, l = {R2.array.alphabet_table_with_starred}, m = "invokeSuspend", n = {"$this$flow", "note", "noteEntity", "remoteViews", "remoteViewsWrap", "length", IntegerTokenConverter.CONVERTER_KEY, "newWidgetId", "count"}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "I$0", "I$1", "I$2", "I$3"})
        static final class C01051 extends SuspendLambda implements Function2<FlowCollector<? super RemoteViewsWrap>, Continuation<? super Unit>, Object> {
            final /* synthetic */ AppWidgetManager $appWidgetManager;
            final /* synthetic */ Context $context;
            final /* synthetic */ int[] $newWidgetIds;
            final /* synthetic */ int[] $oldWidgetIds;
            int I$0;
            int I$1;
            int I$2;
            int I$3;
            private /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            int label;
            final /* synthetic */ NoteWidgetProvider this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01051(int[] iArr, NoteWidgetProvider noteWidgetProvider, int[] iArr2, Context context, AppWidgetManager appWidgetManager, Continuation<? super C01051> continuation) {
                super(2, continuation);
                this.$oldWidgetIds = iArr;
                this.this$0 = noteWidgetProvider;
                this.$newWidgetIds = iArr2;
                this.$context = context;
                this.$appWidgetManager = appWidgetManager;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C01051 c01051 = new C01051(this.$oldWidgetIds, this.this$0, this.$newWidgetIds, this.$context, this.$appWidgetManager, continuation);
                c01051.L$0 = obj;
                return c01051;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super RemoteViewsWrap> flowCollector, Continuation<? super Unit> continuation) {
                return ((C01051) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code duplicated, block: B:11:0x003f  */
            /* JADX WARN: Code duplicated, block: B:13:0x008e  */
            /* JADX WARN: Code duplicated, block: B:15:0x00a6  */
            /* JADX WARN: Code duplicated, block: B:17:0x0103 A[RETURN] */
            /* JADX WARN: Code duplicated, block: B:18:0x0104  */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:12:0x008c -> B:6:0x0029). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0101 -> B:19:0x0107). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0104 -> B:19:0x0107). Please report as a decompilation issue!!! */
            /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
                jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:15:0x00a6
                	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
                	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
                	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
                */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final java.lang.Object invokeSuspend(java.lang.Object r20) {
                /*
                    Method dump skipped, instruction units count: 271
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.miui.notes.widget.NoteWidgetProvider.C03261.C01051.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowFlowOn = FlowKt.flowOn(FlowKt.m4091catch(FlowKt.onCompletion(FlowKt.flow(new C01051(this.$oldWidgetIds, this.this$0, this.$newWidgetIds, this.$context, this.$appWidgetManager, null)), new AnonymousClass2(this.this$0, null)), new AnonymousClass3(this.this$0, null)), Dispatchers.getIO());
                final NoteWidgetProvider noteWidgetProvider = this.this$0;
                final Bundle bundle = this.$options;
                final Context context = this.$context;
                this.label = 1;
                if (flowFlowOn.collect(new FlowCollector() { // from class: com.miui.notes.widget.NoteWidgetProvider.onIdRemap.1.4
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((RemoteViewsWrap) obj2, (Continuation<? super Unit>) continuation);
                    }

                    public final Object emit(RemoteViewsWrap remoteViewsWrap, Continuation<? super Unit> continuation) {
                        Logger logger = Logger.INSTANCE;
                        String tag = noteWidgetProvider.getTAG();
                        Intrinsics.checkNotNullExpressionValue(tag, "<get-TAG>(...)");
                        logger.d(tag, "-----onIdRemap------onNext newWidgetIds:" + remoteViewsWrap.getWidgetId());
                        Bundle bundle2 = bundle;
                        if (bundle2 != null) {
                            bundle2.putBoolean("miuiIdChangedComplete", true);
                            AppWidgetManager.getInstance(context).updateAppWidgetOptions(remoteViewsWrap.getWidgetId(), bundle);
                        }
                        AppWidgetManager.getInstance(context).updateAppWidget(remoteViewsWrap.getWidgetId(), remoteViewsWrap.getRemoteViews());
                        return Unit.INSTANCE;
                    }
                }, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.widget.NoteWidgetProvider$onIdRemap$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: NoteWidgetProvider.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/miui/notes/widget/RemoteViewsWrap;", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.NoteWidgetProvider$onIdRemap$1$2", f = "NoteWidgetProvider.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function3<FlowCollector<? super RemoteViewsWrap>, Throwable, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ NoteWidgetProvider this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(NoteWidgetProvider noteWidgetProvider, Continuation<? super AnonymousClass2> continuation) {
                super(3, continuation);
                this.this$0 = noteWidgetProvider;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super RemoteViewsWrap> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
                return new AnonymousClass2(this.this$0, continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Logger logger = Logger.INSTANCE;
                String tag = this.this$0.getTAG();
                Intrinsics.checkNotNullExpressionValue(tag, "<get-TAG>(...)");
                logger.d(tag, "onComp");
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.notes.widget.NoteWidgetProvider$onIdRemap$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: NoteWidgetProvider.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lcom/miui/notes/widget/RemoteViewsWrap;", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.NoteWidgetProvider$onIdRemap$1$3", f = "NoteWidgetProvider.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function3<FlowCollector<? super RemoteViewsWrap>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ NoteWidgetProvider this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(NoteWidgetProvider noteWidgetProvider, Continuation<? super AnonymousClass3> continuation) {
                super(3, continuation);
                this.this$0 = noteWidgetProvider;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super RemoteViewsWrap> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, continuation);
                anonymousClass3.L$0 = th;
                return anonymousClass3.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Throwable th = (Throwable) this.L$0;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Logger logger = Logger.INSTANCE;
                String tag = this.this$0.getTAG();
                Intrinsics.checkNotNullExpressionValue(tag, "<get-TAG>(...)");
                logger.d(tag, ExceptionsKt.stackTraceToString(th));
                return Unit.INSTANCE;
            }
        }
    }

    private final void onIdRemap(Context context, int[] oldWidgetIds, int[] newWidgetIds, Bundle options, AppWidgetManager appWidgetManager) {
        Logger logger = Logger.INSTANCE;
        String TAG = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
        logger.d(TAG, "onIdRemap");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new C03261(oldWidgetIds, this, newWidgetIds, context, appWidgetManager, options, null), 3, null);
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
        Intrinsics.checkNotNullParameter(appWidgetIds, "appWidgetIds");
        Logger logger = Logger.INSTANCE;
        String TAG = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
        String string = Arrays.toString(appWidgetIds);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        logger.d(TAG, "NoteWidgetProvider/onUpdate:" + string);
        update(context, appWidgetManager, appWidgetIds);
    }

    @Override // android.appwidget.AppWidgetProvider, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Logger logger = Logger.INSTANCE;
        String TAG = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
        logger.i(TAG, "onReceive with action: " + intent.getAction());
        if (Intrinsics.areEqual("android.appwidget.action.APPWIDGET_UPDATE_OPTIONS", intent.getAction())) {
            ResourceManager.clearThemes();
        }
        super.onReceive(context, intent);
        String action = intent.getAction();
        Logger logger2 = Logger.INSTANCE;
        String TAG2 = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG2, "TAG");
        logger2.i(TAG2, "-action:" + action);
        if (Intrinsics.areEqual(Notes.Intents.INTENT_ACTION_REFRESH_WIDGET, action) || Intrinsics.areEqual("miui.appwidget.action.APPWIDGET_UPDATE", intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
            Intrinsics.checkNotNull(appWidgetIds);
            if (!(appWidgetIds.length == 0)) {
                Intrinsics.checkNotNull(appWidgetManager);
                update(context, appWidgetManager, appWidgetIds);
                return;
            } else {
                if (UIUtils.isNeedRefreshWidget()) {
                    NoteWidgetProvider_2x1_Icon.Companion companion = NoteWidgetProvider_2x1_Icon.INSTANCE;
                    Intrinsics.checkNotNull(appWidgetManager);
                    companion.updateWidget(context, appWidgetManager);
                    return;
                }
                return;
            }
        }
        if (Intrinsics.areEqual(SystemIntent.ACTION_BIND_WIDGET_COMPLETED, action)) {
            long[] longArrayExtra = intent.getLongArrayExtra(SystemIntent.EXTRA_BIND_WIDGET_RESULT);
            Intrinsics.checkNotNull(longArrayExtra);
            long j = longArrayExtra[0];
            int i = (int) longArrayExtra[1];
            Logger logger3 = Logger.INSTANCE;
            String TAG3 = this.TAG;
            Intrinsics.checkNotNullExpressionValue(TAG3, "TAG");
            logger3.i(TAG3, "receive broadcast from desktop success,note id:" + j + ",widget id:" + i);
            if (j != -2 && DataUtils.updateNoteWidgetId(context, j, i, this.widgetType) > 0) {
                Logger logger4 = Logger.INSTANCE;
                String TAG4 = this.TAG;
                Intrinsics.checkNotNullExpressionValue(TAG4, "TAG");
                logger4.i(TAG4, "cursor update success,note id:" + j + ",widget id:" + i);
                AppWidgetManager appWidgetManager2 = AppWidgetManager.getInstance(context);
                Intrinsics.checkNotNullExpressionValue(appWidgetManager2, "getInstance(...)");
                onUpdate(context, appWidgetManager2, new int[]{i});
            }
        }
    }

    protected final void update(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
        Intrinsics.checkNotNullParameter(appWidgetIds, "appWidgetIds");
        Logger logger = Logger.INSTANCE;
        String TAG = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
        String string = Arrays.toString(appWidgetIds);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        logger.i(TAG, "update with ids: " + string);
        for (int i : appWidgetIds) {
            if (i != 0) {
                dataMigration(context, i);
                INSTANCE.updateWidgetView(context, appWidgetManager, i, this.widgetType);
            }
        }
        Logger logger2 = Logger.INSTANCE;
        String TAG2 = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG2, "TAG");
        logger2.i(TAG2, "update with ids,finished");
    }

    private final void dataMigration(final Context context, final int appWidgetId) {
        Logger logger = Logger.INSTANCE;
        String TAG = this.TAG;
        Intrinsics.checkNotNullExpressionValue(TAG, "TAG");
        logger.d(TAG, "dataMigration for widgetID: " + appWidgetId);
        sExecutorService.execute(new Runnable() { // from class: com.miui.notes.widget.NoteWidgetProvider.dataMigration.1
            @Override // java.lang.Runnable
            public void run() {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
                if (appWidgetOptions != null) {
                    Object obj = appWidgetOptions.get("miuiMinusSource");
                    Logger logger2 = Logger.INSTANCE;
                    String tag = this.getTAG();
                    Intrinsics.checkNotNullExpressionValue(tag, "<get-TAG>(...)");
                    logger2.d(tag, "dataMigration appWidgetOptions != null   miuiMinusSource：(" + obj + ")");
                    if ((obj instanceof String) && TextUtils.equals((CharSequence) obj, "default")) {
                        String string = null;
                        Cursor cursorQueryNote = WidgetProcessDatabaseUtils.queryNote(context, new String[]{"_id"}, "type=0 AND widget_id=? AND parent_id!=-3", new String[]{String.valueOf(appWidgetId)}, null);
                        if (cursorQueryNote.getCount() > 0) {
                            cursorQueryNote.close();
                            appWidgetOptions.putString("miuiMinusSource", "");
                            appWidgetManager.updateAppWidgetOptions(appWidgetId, appWidgetOptions);
                            return;
                        }
                        cursorQueryNote.close();
                        try {
                            Bundle bundleCall = context.getContentResolver().call(Uri.parse("content://com.miui.personalassistant.widget.notes"), "queryNoteBoard", (String) null, (Bundle) null);
                            if (bundleCall != null) {
                                string = bundleCall.getString("note");
                                Logger logger3 = Logger.INSTANCE;
                                String tag2 = this.getTAG();
                                Intrinsics.checkNotNullExpressionValue(tag2, "<get-TAG>(...)");
                                logger3.d(tag2, "===note====" + string);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Logger logger4 = Logger.INSTANCE;
                            String tag3 = this.getTAG();
                            Intrinsics.checkNotNullExpressionValue(tag3, "<get-TAG>(...)");
                            logger4.d(tag3, "context.getContentResolver().call--" + e.getMessage());
                        }
                        if (!TextUtils.isEmpty(string)) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("snippet", string);
                            if (context.getResources() != null) {
                                contentValues.put("title", context.getResources().getString(R.string.widget_take_notes_title));
                            }
                            contentValues.put("widget_id", Integer.valueOf(appWidgetId));
                            contentValues.put("widget_type", Integer.valueOf(this.getWidgetType()));
                            WidgetProcessDatabaseUtils.insertPersonalAssistantNote(context, contentValues);
                        }
                        appWidgetOptions.putString("miuiMinusSource", "");
                        appWidgetManager.updateAppWidgetOptions(appWidgetId, appWidgetOptions);
                    }
                }
            }
        });
    }

    /* JADX INFO: compiled from: NoteWidgetProvider.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J(\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0007J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0013H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/miui/notes/widget/NoteWidgetProvider$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "INTENT_EXTRA_IS_HIDDEN", "", "INTENT_EXTRA_FOLDER_ID", "SCHEME_WIDGET_NOTE_LIST_PAGE", "SCHEME_PAD_WIDGET_NOTE_LIST_PAGE", "SCHEME_PHONE_WIDGET_NOTE_LIST_PAGE", "sExecutorService", "Ljava/util/concurrent/ExecutorService;", "updateWidgetView", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetId", "", "widgetType", "getWidgetEditPageUri", "Landroid/net/Uri;", "widgetId", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void updateWidgetView(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId, final int widgetType) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(appWidgetManager, "appWidgetManager");
            Logger.INSTANCE.i("NoteWidgetProvider", "before query updateWidgetView widgetId=" + appWidgetId + " widgetType=" + widgetType);
            new WidgetLoadNoteTask() { // from class: com.miui.notes.widget.NoteWidgetProvider$Companion$updateWidgetView$1
                @Override // com.miui.notes.widget.WidgetLoadNoteTask
                public void callback(RemoteViews wrap) {
                    if (UIUtils.isMiuiWidgetSupported(context)) {
                        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
                        appWidgetOptions.putString("miuiEditUri", NoteWidgetProvider.INSTANCE.getWidgetEditPageUri(widgetType, appWidgetId).toString());
                        appWidgetManager.updateAppWidgetOptions(appWidgetId, appWidgetOptions);
                    }
                    Logger.INSTANCE.i("NoteWidgetProvider", "updateAppWidgetView widgetId=" + appWidgetId + " widgetType=" + widgetType);
                    appWidgetManager.updateAppWidget(appWidgetId, wrap);
                }
            }.execute(context, appWidgetManager, appWidgetId, widgetType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Uri getWidgetEditPageUri(int widgetType, int widgetId) {
            String str;
            String str2;
            if (RomUtils.isPadDevice()) {
                str = NoteWidgetProvider.SCHEME_PAD_WIDGET_NOTE_LIST_PAGE;
                str2 = "PadWidgetNoteListActivity";
            } else {
                str = NoteWidgetProvider.SCHEME_WIDGET_NOTE_LIST_PAGE;
                str2 = "WidgetNoteListActivity";
            }
            Uri uriBuild = new Uri.Builder().scheme(str).authority("com.miui.notes").appendPath("widget").appendPath("notelist").appendPath(str2).appendPath(String.valueOf(widgetType)).appendQueryParameter("miuiWidgetId", String.valueOf(widgetId)).build();
            Intrinsics.checkNotNullExpressionValue(uriBuild, "build(...)");
            return uriBuild;
        }
    }

    static {
        ExecutorService executorServiceNewSingleThreadExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.miui.notes.widget.NoteWidgetProvider$$ExternalSyntheticLambda1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return NoteWidgetProvider.sExecutorService$lambda$2(runnable);
            }
        });
        Intrinsics.checkNotNullExpressionValue(executorServiceNewSingleThreadExecutor, "newSingleThreadExecutor(...)");
        sExecutorService = executorServiceNewSingleThreadExecutor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Thread sExecutorService$lambda$2(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName("widgetSingleWorker");
        return thread;
    }
}
