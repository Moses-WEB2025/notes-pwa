package com.miui.notes.basefeature.noteedit;

import android.content.Context;
import android.net.Uri;
import com.miui.common.tool.Logger;
import com.miui.hybirdeditor.RichEditorWebView;
import com.miui.richeditor.share.render.Render;
import com.xiaomi.mirror.synergy.CallMethod;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$createShareAnimRenderUri$1$1", f = "BaseHybridNoteEditFragment.kt", i = {0, 0}, l = {R2.color.ai_widget_polish_title_color}, m = "invokeSuspend", n = {CallMethod.ARG_URI, "previewUri"}, s = {"L$0", "L$1"})
final class BaseHybridNoteEditFragment$createShareAnimRenderUri$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ BaseHybridNoteEditFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    BaseHybridNoteEditFragment$createShareAnimRenderUri$1$1(BaseHybridNoteEditFragment baseHybridNoteEditFragment, Continuation<? super BaseHybridNoteEditFragment$createShareAnimRenderUri$1$1> continuation) {
        super(2, continuation);
        this.this$0 = baseHybridNoteEditFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new BaseHybridNoteEditFragment$createShareAnimRenderUri$1$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((BaseHybridNoteEditFragment$createShareAnimRenderUri$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Ref.ObjectRef objectRef;
        Ref.ObjectRef objectRef2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            objectRef = new Ref.ObjectRef();
            Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
            this.L$0 = objectRef;
            this.L$1 = objectRef3;
            this.label = 1;
            if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(objectRef, this.this$0, objectRef3, null), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            objectRef2 = objectRef3;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            objectRef2 = (Ref.ObjectRef) this.L$1;
            objectRef = (Ref.ObjectRef) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        Logger.INSTANCE.d(this.this$0.getTAG(), "generateAnimRender uri: " + objectRef.element + " previewUri " + objectRef2.element);
        if (objectRef.element != 0 && objectRef2.element != 0) {
            this.this$0.startSharePageByRenderUri((Uri) objectRef.element, (Uri) objectRef2.element);
            this.this$0.dismissProgressDialog();
        }
        RichEditorWebView richEditorWebView = this.this$0.mEditorWebView;
        if (richEditorWebView != null) {
            richEditorWebView.removeView(this.this$0.mMamlViewContainer);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$createShareAnimRenderUri$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: BaseHybridNoteEditFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.basefeature.noteedit.BaseHybridNoteEditFragment$createShareAnimRenderUri$1$1$1", f = "BaseHybridNoteEditFragment.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.ObjectRef<Uri> $previewUri;
        final /* synthetic */ Ref.ObjectRef<Uri> $uri;
        int label;
        final /* synthetic */ BaseHybridNoteEditFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Ref.ObjectRef<Uri> objectRef, BaseHybridNoteEditFragment baseHybridNoteEditFragment, Ref.ObjectRef<Uri> objectRef2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$uri = objectRef;
            this.this$0 = baseHybridNoteEditFragment;
            this.$previewUri = objectRef2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$uri, this.this$0, this.$previewUri, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [T, android.net.Uri] */
        /* JADX WARN: Type inference failed for: r3v4, types: [T, android.net.Uri] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Ref.ObjectRef<Uri> objectRef = this.$uri;
            BaseHybridNoteEditFragment baseHybridNoteEditFragment = this.this$0;
            Context context = baseHybridNoteEditFragment.getContext();
            Render render = this.this$0.mRender;
            Intrinsics.checkNotNull(render);
            objectRef.element = baseHybridNoteEditFragment.createRenderTempUri(context, render);
            Render render2 = this.this$0.mRender;
            Intrinsics.checkNotNull(render2);
            int height = render2.getHeight();
            Render render3 = this.this$0.mRender;
            if (render3 != null) {
                int height2 = render3.getHeight();
                BaseHybridNoteEditFragment baseHybridNoteEditFragment2 = this.this$0;
                if (height2 > baseHybridNoteEditFragment2.SHARE_PREVIEW_IMAGE_MAX_HEIGHT) {
                    height = baseHybridNoteEditFragment2.SHARE_PREVIEW_IMAGE_MAX_HEIGHT;
                } else if (height2 < baseHybridNoteEditFragment2.SHARE_PREVIEW_ANIM_IMAGE_MIN_HEIGHT) {
                    Render render4 = baseHybridNoteEditFragment2.mRender;
                    Intrinsics.checkNotNull(render4);
                    render4.setHeight(baseHybridNoteEditFragment2.SHARE_PREVIEW_ANIM_IMAGE_MIN_HEIGHT);
                    height = baseHybridNoteEditFragment2.SHARE_PREVIEW_ANIM_IMAGE_MIN_HEIGHT;
                }
            }
            Ref.ObjectRef<Uri> objectRef2 = this.$previewUri;
            BaseHybridNoteEditFragment baseHybridNoteEditFragment3 = this.this$0;
            Context context2 = baseHybridNoteEditFragment3.getContext();
            Render render5 = this.this$0.mRender;
            Intrinsics.checkNotNull(render5);
            objectRef2.element = baseHybridNoteEditFragment3.createRenderTempUri(context2, render5, height);
            return Unit.INSTANCE;
        }
    }
}
