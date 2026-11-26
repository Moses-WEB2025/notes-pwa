package com.miui.notes.doodle.feature;

import android.R;
import android.view.View;
import com.miui.doodle.document.DoodleDocument;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.notes.base.utils.Logger;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import miuix.appcompat.app.ProgressDialog;

/* JADX INFO: compiled from: DoodleFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.notes.doodle.feature.DoodleFragment$resetDoodleDocument$1$3", f = "DoodleFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class DoodleFragment$resetDoodleDocument$1$3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ boolean $isFilePathNotEmpty;
    int label;
    final /* synthetic */ DoodleFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DoodleFragment$resetDoodleDocument$1$3(DoodleFragment doodleFragment, boolean z, Continuation<? super DoodleFragment$resetDoodleDocument$1$3> continuation) {
        super(2, continuation);
        this.this$0 = doodleFragment;
        this.$isFilePathNotEmpty = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DoodleFragment$resetDoodleDocument$1$3(this.this$0, this.$isFilePathNotEmpty, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((DoodleFragment$resetDoodleDocument$1$3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: renamed from: com.miui.notes.doodle.feature.DoodleFragment$resetDoodleDocument$1$3$1, reason: invalid class name */
    /* JADX INFO: compiled from: DoodleFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.doodle.feature.DoodleFragment$resetDoodleDocument$1$3$1", f = "DoodleFragment.kt", i = {0}, l = {R2.attr.miuiAlertDialogTheme}, m = "invokeSuspend", n = {"$this$runBlocking"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $isFilePathNotEmpty;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ DoodleFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(DoodleFragment doodleFragment, boolean z, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = doodleFragment;
            this.$isFilePathNotEmpty = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$isFilePathNotEmpty, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Deferred<Object> autoSaveJob;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DoodleView doodleView = this.this$0.getDoodleView();
                if (doodleView != null && (autoSaveJob = doodleView.getAutoSaveJob()) != null) {
                    this.L$0 = coroutineScope;
                    this.label = 1;
                    if (autoSaveJob.join(this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            DoodleFragment doodleFragment = this.this$0;
            DoodleView doodleView2 = doodleFragment.getDoodleView();
            DoodleDocument doodleDocumentLoadDoodleDocFromFile = doodleFragment.loadDoodleDocFromFile(doodleView2 != null ? doodleView2.getTempDoodleFilePath() : null, this.$isFilePathNotEmpty);
            if (this.$isFilePathNotEmpty && doodleDocumentLoadDoodleDocFromFile == null) {
                Runnable runnable = this.this$0.showLoadingDialogTask;
                if (runnable != null) {
                    this.this$0.getHandler().removeCallbacks(runnable);
                }
                this.this$0.showLoadingDialogTask = null;
                return Unit.INSTANCE;
            }
            this.this$0.setMyDoodleDocument(doodleDocumentLoadDoodleDocFromFile);
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getMain(), null, new AnonymousClass2(this.this$0, this.$isFilePathNotEmpty, null), 2, null);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.doodle.feature.DoodleFragment$resetDoodleDocument$1$3$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: DoodleFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.doodle.feature.DoodleFragment$resetDoodleDocument$1$3$1$2", f = "DoodleFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ boolean $isFilePathNotEmpty;
            int label;
            final /* synthetic */ DoodleFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(DoodleFragment doodleFragment, boolean z, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = doodleFragment;
                this.$isFilePathNotEmpty = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, this.$isFilePathNotEmpty, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                DoodleDocument myDoodleDocument = this.this$0.getMyDoodleDocument();
                if (myDoodleDocument != null) {
                    DoodleFragment doodleFragment = this.this$0;
                    boolean z = this.$isFilePathNotEmpty;
                    doodleFragment.updateDoodleBackground(Intrinsics.areEqual(myDoodleDocument.getDocumentInfo().getBackgroundType(), "white") ? R.color.white : com.miui.notes.R.drawable.doodle_canvas_bg);
                    DoodleView doodleView = doodleFragment.getDoodleView();
                    if (doodleView != null) {
                        doodleView.initDocument(myDoodleDocument);
                    }
                    Runnable runnable = doodleFragment.showLoadingDialogTask;
                    if (runnable != null) {
                        doodleFragment.getHandler().removeCallbacks(runnable);
                    }
                    if (Intrinsics.areEqual(myDoodleDocument.getDocumentInfo().getBackgroundType(), "white")) {
                        View view = doodleFragment.backgroundWhiteConfigContainer;
                        if (view == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
                            view = null;
                        }
                        view.setBackgroundResource(com.miui.notes.R.drawable.ic_background_selected);
                        View view2 = doodleFragment.backgroundDotConfigContainer;
                        if (view2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
                            view2 = null;
                        }
                        view2.setBackgroundResource(R.color.transparent);
                    } else {
                        View view3 = doodleFragment.backgroundWhiteConfigContainer;
                        if (view3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
                            view3 = null;
                        }
                        view3.setBackgroundResource(R.color.transparent);
                        View view4 = doodleFragment.backgroundDotConfigContainer;
                        if (view4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
                            view4 = null;
                        }
                        view4.setBackgroundResource(com.miui.notes.R.drawable.ic_background_selected);
                    }
                    Logger.INSTANCE.d("DoodleLoadTest", "loadDataDialog?.dismiss isAdded " + doodleFragment.isAdded());
                    if (z) {
                        if (doodleFragment.isAdded()) {
                            ProgressDialog progressDialog = doodleFragment.loadDataDialog;
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                        } else {
                            try {
                                ProgressDialog progressDialog2 = doodleFragment.loadDataDialog;
                                if (progressDialog2 != null) {
                                    progressDialog2.dismissWithoutAnimation();
                                    Unit unit = Unit.INSTANCE;
                                }
                            } catch (Exception unused) {
                                Unit unit2 = Unit.INSTANCE;
                            }
                        }
                    }
                    doodleFragment.showLoadingDialogTask = null;
                }
                return Unit.INSTANCE;
            }
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws InterruptedException {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(this.this$0, this.$isFilePathNotEmpty, null), 1, null);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}
