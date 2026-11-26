package com.miui.floatwindow.feature.note;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: FwNoteListPresenter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/miui/floatwindow/feature/note/FwNoteListPresenter;", "", "mView", "Lcom/miui/floatwindow/feature/note/FwNoteListView;", "provider", "Lcom/miui/floatwindow/feature/note/NoteSchedulerProvider;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/floatwindow/feature/note/FwNoteListView;Lcom/miui/floatwindow/feature/note/NoteSchedulerProvider;)V", "mNoteSource", "Lcom/miui/floatwindow/feature/note/INoteSource;", "mSchedulerProvider", "subscribe", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FwNoteListPresenter {
    private final INoteSource mNoteSource;
    private final NoteSchedulerProvider mSchedulerProvider;
    private final FwNoteListView mView;

    public FwNoteListPresenter(FwNoteListView mView, NoteSchedulerProvider provider) {
        Intrinsics.checkNotNullParameter(mView, "mView");
        Intrinsics.checkNotNullParameter(provider, "provider");
        this.mView = mView;
        mView.setPresenter(this);
        this.mSchedulerProvider = provider;
        NoteDataDepository companion = NoteDataDepository.INSTANCE.getInstance();
        Intrinsics.checkNotNull(companion);
        this.mNoteSource = companion.getDataSource();
    }

    /* JADX INFO: renamed from: com.miui.floatwindow.feature.note.FwNoteListPresenter$subscribe$1, reason: invalid class name */
    /* JADX INFO: compiled from: FwNoteListPresenter.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.floatwindow.feature.note.FwNoteListPresenter$subscribe$1", f = "FwNoteListPresenter.kt", i = {}, l = {27}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FwNoteListPresenter.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: com.miui.floatwindow.feature.note.FwNoteListPresenter$subscribe$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: FwNoteListPresenter.kt */
        @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", ""}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.floatwindow.feature.note.FwNoteListPresenter$subscribe$1$1", f = "FwNoteListPresenter.kt", i = {0}, l = {26}, m = "invokeSuspend", n = {"$this$flow"}, s = {"L$0"})
        static final class C00461 extends SuspendLambda implements Function2<FlowCollector<? super Boolean>, Continuation<? super Unit>, Object> {
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ FwNoteListPresenter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00461(FwNoteListPresenter fwNoteListPresenter, Continuation<? super C00461> continuation) {
                super(2, continuation);
                this.this$0 = fwNoteListPresenter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00461 c00461 = new C00461(this.this$0, continuation);
                c00461.L$0 = obj;
                return c00461;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(FlowCollector<? super Boolean> flowCollector, Continuation<? super Unit> continuation) {
                return ((C00461) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.mNoteSource.refreshData();
                    this.L$0 = SpillingKt.nullOutSpilledVariable(flowCollector);
                    this.label = 1;
                    if (flowCollector.emit(Boxing.boxBoolean(true), this) == coroutine_suspended) {
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
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowFlowOn = FlowKt.flowOn(FlowKt.flow(new C00461(FwNoteListPresenter.this, null)), Dispatchers.getIO());
                final FwNoteListPresenter fwNoteListPresenter = FwNoteListPresenter.this;
                this.label = 1;
                if (flowFlowOn.collect(new FlowCollector() { // from class: com.miui.floatwindow.feature.note.FwNoteListPresenter.subscribe.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Boolean) obj2).booleanValue(), (Continuation<? super Unit>) continuation);
                    }

                    public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                        if (z) {
                            if (fwNoteListPresenter.mView.getMAdapter() != null) {
                                FwNoteListAdapter listAdapter = fwNoteListPresenter.mView.getMAdapter();
                                Intrinsics.checkNotNull(listAdapter);
                                listAdapter.bindDataSource(fwNoteListPresenter.mNoteSource);
                                fwNoteListPresenter.mView.onDataLoaded();
                            } else {
                                Logger.INSTANCE.e("FwNoteListPresenter", "mView or listAdapter is null");
                            }
                        }
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
    }

    public final void subscribe() {
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.MainScope(), null, null, new AnonymousClass1(null), 3, null);
    }
}
