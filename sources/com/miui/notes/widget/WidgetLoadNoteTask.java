package com.miui.notes.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.widget.view.WidgetViewFactory;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.ExceptionsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J(\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J*\u0010\u0013\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0011J\u0012\u0010\u0015\u001a\u00020\u000b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H&R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0019"}, d2 = {"Lcom/miui/notes/widget/WidgetLoadNoteTask;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "worker", "Lkotlinx/coroutines/CoroutineScope;", "getWorker", "()Lkotlinx/coroutines/CoroutineScope;", "worker$delegate", "Lkotlin/Lazy;", "execute", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "appWidgetManager", "Landroid/appwidget/AppWidgetManager;", "appWidgetId", "", "widgetType", "executeWidget_2x1", "updateNumber", CallMethod.ARG_CALLBACK, "wrap", "Landroid/widget/RemoteViews;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public abstract class WidgetLoadNoteTask {
    private static final String TAG = "WidgetLoadNoteTask";

    /* JADX INFO: renamed from: worker$delegate, reason: from kotlin metadata */
    private final Lazy worker = LazyKt.lazy(new Function0() { // from class: com.miui.notes.widget.WidgetLoadNoteTask$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return WidgetLoadNoteTask.worker_delegate$lambda$0();
        }
    });

    public abstract void callback(RemoteViews wrap);

    /* JADX INFO: Access modifiers changed from: private */
    public static final CoroutineScope worker_delegate$lambda$0() {
        return CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    }

    public final CoroutineScope getWorker() {
        return (CoroutineScope) this.worker.getValue();
    }

    /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$execute$1, reason: invalid class name */
    /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$execute$1", f = "WidgetLoadNoteTask.kt", i = {}, l = {55}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $appWidgetId;
        final /* synthetic */ AppWidgetManager $appWidgetManager;
        final /* synthetic */ Context $context;
        final /* synthetic */ int $widgetType;
        int label;
        final /* synthetic */ WidgetLoadNoteTask this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Context context, int i, AppWidgetManager appWidgetManager, int i2, WidgetLoadNoteTask widgetLoadNoteTask, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$context = context;
            this.$appWidgetId = i;
            this.$appWidgetManager = appWidgetManager;
            this.$widgetType = i2;
            this.this$0 = widgetLoadNoteTask;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$context, this.$appWidgetId, this.$appWidgetManager, this.$widgetType, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$execute$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/widget/RemoteViews;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$execute$1$1", f = "WidgetLoadNoteTask.kt", i = {0, 0, 0, 0}, l = {48}, m = "invokeSuspend", n = {"$this$flow", "note", "$this$invokeSuspend_u24lambda_u240", "$i$a$-run-WidgetLoadNoteTask$execute$1$1$1"}, s = {"L$0", "L$1", "L$2", "I$0"})
        static final class C01061 extends SuspendLambda implements Function2<FlowCollector<? super RemoteViews>, Continuation<? super Unit>, Object> {
            final /* synthetic */ int $appWidgetId;
            final /* synthetic */ AppWidgetManager $appWidgetManager;
            final /* synthetic */ Context $context;
            final /* synthetic */ int $widgetType;
            int I$0;
            private /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01061(Context context, int i, AppWidgetManager appWidgetManager, int i2, Continuation<? super C01061> continuation) {
                super(2, continuation);
                this.$context = context;
                this.$appWidgetId = i;
                this.$appWidgetManager = appWidgetManager;
                this.$widgetType = i2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C01061 c01061 = new C01061(this.$context, this.$appWidgetId, this.$appWidgetManager, this.$widgetType, continuation);
                c01061.L$0 = obj;
                return c01061;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super RemoteViews> flowCollector, Continuation<? super Unit> continuation) {
                return ((C01061) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    NoteEntity noteEntityLoadByDB = NoteEntityHelper.loadByDB(this.$context, "widget_id=? AND parent_id!=-3", new String[]{String.valueOf(this.$appWidgetId)});
                    RemoteViews remoteViewsCreateWidgetView = WidgetViewFactory.createWidgetView(this.$context, this.$appWidgetManager, this.$appWidgetId, this.$widgetType, noteEntityLoadByDB, Integer.MAX_VALUE);
                    if (remoteViewsCreateWidgetView != null) {
                        this.L$0 = SpillingKt.nullOutSpilledVariable(flowCollector);
                        this.L$1 = SpillingKt.nullOutSpilledVariable(noteEntityLoadByDB);
                        this.L$2 = SpillingKt.nullOutSpilledVariable(remoteViewsCreateWidgetView);
                        this.I$0 = 0;
                        this.label = 1;
                        if (flowCollector.emit(remoteViewsCreateWidgetView, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowOnCompletion = FlowKt.onCompletion(FlowKt.m4091catch(FlowKt.onEmpty(FlowKt.flowOn(FlowKt.flow(new C01061(this.$context, this.$appWidgetId, this.$appWidgetManager, this.$widgetType, null)), Dispatchers.getIO()), new AnonymousClass2(null)), new AnonymousClass3(null)), new AnonymousClass4(null));
                final WidgetLoadNoteTask widgetLoadNoteTask = this.this$0;
                this.label = 1;
                if (flowOnCompletion.collect(new FlowCollector() { // from class: com.miui.notes.widget.WidgetLoadNoteTask.execute.1.5
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((RemoteViews) obj2, (Continuation<? super Unit>) continuation);
                    }

                    public final Object emit(RemoteViews remoteViews, Continuation<? super Unit> continuation) {
                        widgetLoadNoteTask.callback(remoteViews);
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

        /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$execute$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/widget/RemoteViews;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$execute$1$2", f = "WidgetLoadNoteTask.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<FlowCollector<? super RemoteViews>, Continuation<? super Unit>, Object> {
            int label;

            AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super RemoteViews> flowCollector, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.d(WidgetLoadNoteTask.TAG, "empty remote views");
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$execute$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/widget/RemoteViews;", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$execute$1$3", f = "WidgetLoadNoteTask.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function3<FlowCollector<? super RemoteViews>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;

            AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super RemoteViews> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
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
                Logger.INSTANCE.d(WidgetLoadNoteTask.TAG, "on error " + ExceptionsKt.stackTraceToString(th));
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$execute$1$4, reason: invalid class name */
        /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/widget/RemoteViews;", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$execute$1$4", f = "WidgetLoadNoteTask.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass4 extends SuspendLambda implements Function3<FlowCollector<? super RemoteViews>, Throwable, Continuation<? super Unit>, Object> {
            int label;

            AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super RemoteViews> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
                return new AnonymousClass4(continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    Logger.INSTANCE.d(WidgetLoadNoteTask.TAG, "on task complete");
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    public final void execute(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int widgetType) {
        Intrinsics.checkNotNullParameter(context, "context");
        BuildersKt__Builders_commonKt.launch$default(getWorker(), null, null, new AnonymousClass1(context, appWidgetId, appWidgetManager, widgetType, this, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1", f = "WidgetLoadNoteTask.kt", i = {}, l = {97}, m = "invokeSuspend", n = {}, s = {})
    static final class C03271 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ AppWidgetManager $appWidgetManager;
        final /* synthetic */ Context $context;
        final /* synthetic */ int $updateNumber;
        final /* synthetic */ int $widgetType;
        int label;
        final /* synthetic */ WidgetLoadNoteTask this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03271(int i, Context context, int i2, AppWidgetManager appWidgetManager, WidgetLoadNoteTask widgetLoadNoteTask, Continuation<? super C03271> continuation) {
            super(2, continuation);
            this.$widgetType = i;
            this.$context = context;
            this.$updateNumber = i2;
            this.$appWidgetManager = appWidgetManager;
            this.this$0 = widgetLoadNoteTask;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C03271(this.$widgetType, this.$context, this.$updateNumber, this.$appWidgetManager, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03271) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/widget/RemoteViews;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1$1", f = "WidgetLoadNoteTask.kt", i = {0, 0, 0, 0}, l = {91}, m = "invokeSuspend", n = {"$this$flow", "$this$invokeSuspend_u24lambda_u240", "numbers", "$i$a$-run-WidgetLoadNoteTask$executeWidget_2x1$1$1$1"}, s = {"L$0", "L$1", "I$0", "I$1"})
        static final class C01071 extends SuspendLambda implements Function2<FlowCollector<? super RemoteViews>, Continuation<? super Unit>, Object> {
            final /* synthetic */ AppWidgetManager $appWidgetManager;
            final /* synthetic */ Context $context;
            final /* synthetic */ int $updateNumber;
            final /* synthetic */ int $widgetType;
            int I$0;
            int I$1;
            private /* synthetic */ Object L$0;
            Object L$1;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01071(int i, Context context, int i2, AppWidgetManager appWidgetManager, Continuation<? super C01071> continuation) {
                super(2, continuation);
                this.$widgetType = i;
                this.$context = context;
                this.$updateNumber = i2;
                this.$appWidgetManager = appWidgetManager;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C01071 c01071 = new C01071(this.$widgetType, this.$context, this.$updateNumber, this.$appWidgetManager, continuation);
                c01071.L$0 = obj;
                return c01071;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super RemoteViews> flowCollector, Continuation<? super Unit> continuation) {
                return ((C01071) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code duplicated, block: B:20:0x004e  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Cursor cursorQueryTodoNumbers;
                int count;
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    int i2 = this.$widgetType;
                    if (5 == i2) {
                        Cursor cursorQueryNotesNumbers = WidgetProcessDatabaseUtils.queryNotesNumbers(this.$context);
                        if (cursorQueryNotesNumbers != null) {
                            count = cursorQueryNotesNumbers.getCount();
                            cursorQueryNotesNumbers.close();
                        } else {
                            count = Integer.MAX_VALUE;
                        }
                    } else if (7 != i2 || this.$updateNumber >= 0 || (cursorQueryTodoNumbers = WidgetProcessDatabaseUtils.queryTodoNumbers(this.$context)) == null) {
                        count = Integer.MAX_VALUE;
                    } else {
                        count = cursorQueryTodoNumbers.getCount();
                        cursorQueryTodoNumbers.close();
                    }
                    Context context = this.$context;
                    AppWidgetManager appWidgetManager = this.$appWidgetManager;
                    int i3 = this.$widgetType;
                    int i4 = this.$updateNumber;
                    RemoteViews remoteViewsCreateWidgetView = WidgetViewFactory.createWidgetView(context, appWidgetManager, 0, i3, null, i4 >= 0 ? i4 : count);
                    if (remoteViewsCreateWidgetView != null) {
                        this.L$0 = SpillingKt.nullOutSpilledVariable(flowCollector);
                        this.L$1 = SpillingKt.nullOutSpilledVariable(remoteViewsCreateWidgetView);
                        this.I$0 = count;
                        this.I$1 = 0;
                        this.label = 1;
                        if (flowCollector.emit(remoteViewsCreateWidgetView, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowOnCompletion = FlowKt.onCompletion(FlowKt.m4091catch(FlowKt.onEmpty(FlowKt.flowOn(FlowKt.flow(new C01071(this.$widgetType, this.$context, this.$updateNumber, this.$appWidgetManager, null)), Dispatchers.getIO()), new AnonymousClass2(null)), new AnonymousClass3(null)), new AnonymousClass4(null));
                final WidgetLoadNoteTask widgetLoadNoteTask = this.this$0;
                this.label = 1;
                if (flowOnCompletion.collect(new FlowCollector() { // from class: com.miui.notes.widget.WidgetLoadNoteTask.executeWidget_2x1.1.5
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit((RemoteViews) obj2, (Continuation<? super Unit>) continuation);
                    }

                    public final Object emit(RemoteViews remoteViews, Continuation<? super Unit> continuation) {
                        widgetLoadNoteTask.callback(remoteViews);
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

        /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/widget/RemoteViews;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1$2", f = "WidgetLoadNoteTask.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<FlowCollector<? super RemoteViews>, Continuation<? super Unit>, Object> {
            int label;

            AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super RemoteViews> flowCollector, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Logger.INSTANCE.d(WidgetLoadNoteTask.TAG, "empty remote views");
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/widget/RemoteViews;", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1$3", f = "WidgetLoadNoteTask.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function3<FlowCollector<? super RemoteViews>, Throwable, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;

            AnonymousClass3(Continuation<? super AnonymousClass3> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super RemoteViews> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(continuation);
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
                Logger.INSTANCE.d(WidgetLoadNoteTask.TAG, "on error " + ExceptionsKt.stackTraceToString(th));
                return Unit.INSTANCE;
            }
        }

        /* JADX INFO: renamed from: com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1$4, reason: invalid class name */
        /* JADX INFO: compiled from: WidgetLoadNoteTask.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroid/widget/RemoteViews;", "it", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.widget.WidgetLoadNoteTask$executeWidget_2x1$1$4", f = "WidgetLoadNoteTask.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass4 extends SuspendLambda implements Function3<FlowCollector<? super RemoteViews>, Throwable, Continuation<? super Unit>, Object> {
            int label;

            AnonymousClass4(Continuation<? super AnonymousClass4> continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(FlowCollector<? super RemoteViews> flowCollector, Throwable th, Continuation<? super Unit> continuation) {
                return new AnonymousClass4(continuation).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    Logger.INSTANCE.d(WidgetLoadNoteTask.TAG, "on task complete");
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    public final void executeWidget_2x1(Context context, AppWidgetManager appWidgetManager, int widgetType, int updateNumber) {
        Logger.INSTANCE.d(TAG, "executeWidget_2x1.....");
        BuildersKt__Builders_commonKt.launch$default(getWorker(), null, null, new C03271(widgetType, context, updateNumber, appWidgetManager, this, null), 3, null);
    }
}
