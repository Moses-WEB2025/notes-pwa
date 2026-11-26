package com.miui.handwriting.common.taskservice;

import android.os.Bundle;
import android.os.RemoteException;
import com.miui.creation.taskservice.ICreationTaskInterface;
import com.miui.creation.taskservice.ISearchCallback;
import com.miui.creation.taskservice.ITaskServiceCallback;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: CreationServiceManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.handwriting.common.taskservice.CreationServiceManager$dispatchTask$1$1", f = "CreationServiceManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class CreationServiceManager$dispatchTask$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ ICreationTaskInterface $bridge;
    final /* synthetic */ Bundle $bundle;
    final /* synthetic */ ITaskServiceCallback $callback;
    final /* synthetic */ ISearchCallback $searchCallback;
    final /* synthetic */ int $taskCode;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CreationServiceManager$dispatchTask$1$1(int i, Bundle bundle, ITaskServiceCallback iTaskServiceCallback, ICreationTaskInterface iCreationTaskInterface, ISearchCallback iSearchCallback, Continuation<? super CreationServiceManager$dispatchTask$1$1> continuation) {
        super(2, continuation);
        this.$taskCode = i;
        this.$bundle = bundle;
        this.$callback = iTaskServiceCallback;
        this.$bridge = iCreationTaskInterface;
        this.$searchCallback = iSearchCallback;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CreationServiceManager$dispatchTask$1$1(this.$taskCode, this.$bundle, this.$callback, this.$bridge, this.$searchCallback, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CreationServiceManager$dispatchTask$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws RemoteException {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        switch (this.$taskCode) {
            case 2:
                CreationServiceManager creationServiceManager = CreationServiceManager.INSTANCE;
                Bundle bundle = this.$bundle;
                Intrinsics.checkNotNull(bundle);
                ITaskServiceCallback iTaskServiceCallback = this.$callback;
                Intrinsics.checkNotNull(iTaskServiceCallback);
                creationServiceManager.setLastData(2, bundle, iTaskServiceCallback);
                this.$bridge.exportPreviewImage(this.$bundle, this.$callback);
                break;
            case 3:
                this.$bridge.exportToAi(this.$bundle, this.$callback);
                break;
            case 5:
                CreationServiceManager creationServiceManager2 = CreationServiceManager.INSTANCE;
                Bundle bundle2 = this.$bundle;
                Intrinsics.checkNotNull(bundle2);
                ITaskServiceCallback iTaskServiceCallback2 = this.$callback;
                Intrinsics.checkNotNull(iTaskServiceCallback2);
                creationServiceManager2.setLastData(5, bundle2, iTaskServiceCallback2);
                this.$bridge.exportFile(this.$bundle, this.$callback);
                break;
            case 6:
                CreationServiceManager creationServiceManager3 = CreationServiceManager.INSTANCE;
                Bundle bundle3 = this.$bundle;
                Intrinsics.checkNotNull(bundle3);
                ITaskServiceCallback iTaskServiceCallback3 = this.$callback;
                Intrinsics.checkNotNull(iTaskServiceCallback3);
                creationServiceManager3.setLastData(6, bundle3, iTaskServiceCallback3);
                this.$bridge.saveThumbnailAndRecognition(this.$bundle, this.$callback);
                break;
            case 7:
                ICreationTaskInterface iCreationTaskInterface = this.$bridge;
                Bundle bundle4 = this.$bundle;
                ITaskServiceCallback iTaskServiceCallback4 = this.$callback;
                Intrinsics.checkNotNull(iTaskServiceCallback4);
                iCreationTaskInterface.selectRecognition(bundle4, iTaskServiceCallback4);
                break;
            case 8:
                this.$bridge.setSearchCallback(this.$searchCallback);
                break;
            case 9:
                this.$bridge.removeSearchCallback();
                break;
            case 10:
                ICreationTaskInterface iCreationTaskInterface2 = this.$bridge;
                Bundle bundle5 = this.$bundle;
                ITaskServiceCallback iTaskServiceCallback5 = this.$callback;
                Intrinsics.checkNotNull(iTaskServiceCallback5);
                iCreationTaskInterface2.selectClassificationForAi(bundle5, iTaskServiceCallback5);
                break;
            case 11:
                CreationServiceManager creationServiceManager4 = CreationServiceManager.INSTANCE;
                Bundle bundle6 = this.$bundle;
                Intrinsics.checkNotNull(bundle6);
                ITaskServiceCallback iTaskServiceCallback6 = this.$callback;
                Intrinsics.checkNotNull(iTaskServiceCallback6);
                creationServiceManager4.setLastData(11, bundle6, iTaskServiceCallback6);
                this.$bridge.saveRecognition(this.$bundle, this.$callback);
                break;
        }
        return Unit.INSTANCE;
    }
}
