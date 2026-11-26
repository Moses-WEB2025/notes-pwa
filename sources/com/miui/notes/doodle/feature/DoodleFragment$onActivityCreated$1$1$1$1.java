package com.miui.notes.doodle.feature;

import android.R;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import com.miui.common.stat.EditStat;
import com.miui.doodle.base.DoodleParams;
import com.miui.doodle.base.core.IDoodle;
import com.miui.doodle.document.DocumentInfo;
import com.miui.doodle.document.DoodleDocument;
import com.miui.doodle.feature.draw.DoodleView;
import com.miui.doodle.utils.DoodleCacheManager;
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
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: DoodleFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.notes.doodle.feature.DoodleFragment$onActivityCreated$1$1$1$1", f = "DoodleFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class DoodleFragment$onActivityCreated$1$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ FragmentActivity $act;
    final /* synthetic */ Ref.ObjectRef<String> $restoreEditImageId;
    final /* synthetic */ Bundle $savedInstanceState;
    int label;
    final /* synthetic */ DoodleFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DoodleFragment$onActivityCreated$1$1$1$1(Bundle bundle, DoodleFragment doodleFragment, Ref.ObjectRef<String> objectRef, FragmentActivity fragmentActivity, Continuation<? super DoodleFragment$onActivityCreated$1$1$1$1> continuation) {
        super(2, continuation);
        this.$savedInstanceState = bundle;
        this.this$0 = doodleFragment;
        this.$restoreEditImageId = objectRef;
        this.$act = fragmentActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DoodleFragment$onActivityCreated$1$1$1$1(this.$savedInstanceState, this.this$0, this.$restoreEditImageId, this.$act, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((DoodleFragment$onActivityCreated$1$1$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [T, java.lang.String] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws InterruptedException {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        Bundle bundle = this.$savedInstanceState;
        if (bundle == null || !bundle.containsKey("doodle_snapshot")) {
            IDoodle iDoodle = this.this$0.doodle;
            if (iDoodle == null) {
                Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                iDoodle = null;
            }
            iDoodle.setFromRebuild(false);
        } else {
            this.this$0.imagePath = this.$savedInstanceState.getString("doodle_snapshot");
            IDoodle iDoodle2 = this.this$0.doodle;
            if (iDoodle2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                iDoodle2 = null;
            }
            iDoodle2.setCreateNewDoodle(this.$savedInstanceState.getBoolean("doodle_is_create_new", true));
            if (this.$savedInstanceState.containsKey("doodle_edit_image_id")) {
                this.$restoreEditImageId.element = this.$savedInstanceState.getString("doodle_edit_image_id");
            }
            IDoodle iDoodle3 = this.this$0.doodle;
            if (iDoodle3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                iDoodle3 = null;
            }
            iDoodle3.setFromRebuild(true);
            DoodleView doodleView = this.this$0.getDoodleView();
            if (doodleView != null) {
                doodleView.setTempDoodleFilePath(this.this$0.imagePath);
            }
            if (!TextUtils.isEmpty(this.this$0.imagePath)) {
                booleanRef.element = true;
            }
        }
        BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(this.this$0, booleanRef, this.$restoreEditImageId, this.$act, null), 1, null);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.miui.notes.doodle.feature.DoodleFragment$onActivityCreated$1$1$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: DoodleFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.doodle.feature.DoodleFragment$onActivityCreated$1$1$1$1$1", f = "DoodleFragment.kt", i = {0}, l = {R2.attr.buttonIconTintMode}, m = "invokeSuspend", n = {"$this$runBlocking"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ FragmentActivity $act;
        final /* synthetic */ Ref.BooleanRef $isSnapshot;
        final /* synthetic */ Ref.ObjectRef<String> $restoreEditImageId;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;
        final /* synthetic */ DoodleFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(DoodleFragment doodleFragment, Ref.BooleanRef booleanRef, Ref.ObjectRef<String> objectRef, FragmentActivity fragmentActivity, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = doodleFragment;
            this.$isSnapshot = booleanRef;
            this.$restoreEditImageId = objectRef;
            this.$act = fragmentActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$isSnapshot, this.$restoreEditImageId, this.$act, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            DoodleDocument myDoodleDocument;
            DoodleFragment doodleFragment;
            DoodleDocument myDoodleDocument2;
            DocumentInfo documentInfo;
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            boolean editImage = false;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (!TextUtils.isEmpty(this.this$0.imagePath)) {
                    String str = this.this$0.imagePath;
                    Intrinsics.checkNotNull(str);
                    if (str.equals(DoodleCacheManager.INSTANCE.getPreLoadImagePath()) && DoodleCacheManager.INSTANCE.getPreLoadJob() != null) {
                        DoodleFragment doodleFragment2 = this.this$0;
                        Deferred<DoodleDocument> preLoadJob = DoodleCacheManager.INSTANCE.getPreLoadJob();
                        Intrinsics.checkNotNull(preLoadJob);
                        this.L$0 = coroutineScope;
                        this.L$1 = doodleFragment2;
                        this.label = 1;
                        Object objAwait = preLoadJob.await(this);
                        if (objAwait == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        doodleFragment = doodleFragment2;
                        obj = objAwait;
                    } else {
                        DoodleFragment doodleFragment3 = this.this$0;
                        doodleFragment3.setMyDoodleDocument(doodleFragment3.loadDoodleDocFromFile(doodleFragment3.imagePath, this.$isSnapshot.element));
                        if (this.$isSnapshot.element && this.$restoreEditImageId.element != null && (myDoodleDocument = this.this$0.getMyDoodleDocument()) != null) {
                            myDoodleDocument.setEditImageId(this.$restoreEditImageId.element);
                        }
                    }
                } else {
                    DoodleFragment doodleFragment4 = this.this$0;
                    doodleFragment4.setMyDoodleDocument(doodleFragment4.loadDoodleDocFromFile(doodleFragment4.imagePath, false));
                }
                BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getMain(), null, new C00901(this.this$0, this.$act, null), 2, null);
                return Unit.INSTANCE;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            doodleFragment = (DoodleFragment) this.L$1;
            ResultKt.throwOnFailure(obj);
            doodleFragment.setMyDoodleDocument((DoodleDocument) obj);
            DoodleDocument myDoodleDocument3 = this.this$0.getMyDoodleDocument();
            if (myDoodleDocument3 != null && (documentInfo = myDoodleDocument3.getDocumentInfo()) != null) {
                editImage = documentInfo.getEditImage();
            }
            IDoodle iDoodle = this.this$0.doodle;
            if (iDoodle == null) {
                Intrinsics.throwUninitializedPropertyAccessException(EditStat.ELEMENT_DOODLE);
                iDoodle = null;
            }
            iDoodle.setClipImageOnSave(!editImage);
            DoodleView doodleView = this.this$0.getDoodleView();
            if (doodleView != null) {
                DoodleDocument myDoodleDocument4 = this.this$0.getMyDoodleDocument();
                doodleView.setTempDoodleFilePath(myDoodleDocument4 != null ? myDoodleDocument4.getSnapShotPath() : null);
            }
            DoodleParams doodleParams = this.this$0.doodleParams;
            if (doodleParams == null) {
                Intrinsics.throwUninitializedPropertyAccessException("doodleParams");
                doodleParams = null;
            }
            if (doodleParams.getSavePath() != null && (myDoodleDocument2 = this.this$0.getMyDoodleDocument()) != null) {
                DoodleParams doodleParams2 = this.this$0.doodleParams;
                if (doodleParams2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("doodleParams");
                    doodleParams2 = null;
                }
                myDoodleDocument2.setEditImageId(doodleParams2.getSavePath());
            }
            DoodleCacheManager.INSTANCE.setPreLoadJob(null);
            DoodleCacheManager.INSTANCE.clearPreloadCache();
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, Dispatchers.getMain(), null, new C00901(this.this$0, this.$act, null), 2, null);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.doodle.feature.DoodleFragment$onActivityCreated$1$1$1$1$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: DoodleFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.doodle.feature.DoodleFragment$onActivityCreated$1$1$1$1$1$1", f = "DoodleFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00901 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ FragmentActivity $act;
            private /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ DoodleFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00901(DoodleFragment doodleFragment, FragmentActivity fragmentActivity, Continuation<? super C00901> continuation) {
                super(2, continuation);
                this.this$0 = doodleFragment;
                this.$act = fragmentActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00901 c00901 = new C00901(this.this$0, this.$act, continuation);
                c00901.L$0 = obj;
                return c00901;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00901) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                if (this.this$0.getMyDoodleDocument() != null) {
                    DoodleDocument myDoodleDocument = this.this$0.getMyDoodleDocument();
                    if (myDoodleDocument != null) {
                        DoodleFragment doodleFragment = this.this$0;
                        doodleFragment.updateDoodleBackground(Intrinsics.areEqual(myDoodleDocument.getDocumentInfo().getBackgroundType(), "white") ? R.color.white : com.miui.notes.R.drawable.doodle_canvas_bg);
                        DoodleView doodleView = doodleFragment.getDoodleView();
                        if (doodleView != null) {
                            doodleView.initDocument(myDoodleDocument);
                        }
                        doodleFragment.hideProcessingDialog();
                        View view = null;
                        if (Intrinsics.areEqual(myDoodleDocument.getDocumentInfo().getBackgroundType(), "white")) {
                            View view2 = doodleFragment.backgroundWhiteConfigContainer;
                            if (view2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
                                view2 = null;
                            }
                            view2.setBackgroundResource(com.miui.notes.R.drawable.ic_background_selected);
                            View view3 = doodleFragment.backgroundDotConfigContainer;
                            if (view3 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
                            } else {
                                view = view3;
                            }
                            view.setBackgroundResource(R.color.transparent);
                        } else {
                            View view4 = doodleFragment.backgroundWhiteConfigContainer;
                            if (view4 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("backgroundWhiteConfigContainer");
                                view4 = null;
                            }
                            view4.setBackgroundResource(R.color.transparent);
                            View view5 = doodleFragment.backgroundDotConfigContainer;
                            if (view5 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("backgroundDotConfigContainer");
                            } else {
                                view = view5;
                            }
                            view.setBackgroundResource(com.miui.notes.R.drawable.ic_background_selected);
                        }
                    } else {
                        DoodleFragment doodleFragment2 = this.this$0;
                        FragmentActivity fragmentActivity = this.$act;
                        Logger.INSTANCE.d("DoodleFragment", "waring myDoodleDocument is null 2");
                        doodleFragment2.hideProcessingDialog();
                        fragmentActivity.finish();
                    }
                } else {
                    Logger.INSTANCE.d("DoodleFragment", "waring myDoodleDocument is null 1");
                    this.this$0.hideProcessingDialog();
                    this.$act.finish();
                }
                return Unit.INSTANCE;
            }
        }
    }
}
