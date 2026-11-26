package com.miui.notes.home.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.ToDoStat;
import com.miui.notes.R;
import com.miui.notes.assist.UnPeekLiveData;
import com.miui.notes.model.FolderModel;
import com.miui.notes.page.viewmodel.Operation;
import com.miui.notes.page.viewmodel.Result;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.ItemStore;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: FolderOperationViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0014\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\u001c\u0010\u0013\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lcom/miui/notes/home/viewmodel/FolderOperationViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/app/Application;)V", "_noteOperation", "Lcom/miui/notes/assist/UnPeekLiveData;", "Lcom/miui/notes/page/viewmodel/Result;", "", "noteOperation", "Landroidx/lifecycle/LiveData;", "getNoteOperation", "()Landroidx/lifecycle/LiveData;", ToDoStat.DELETE, "", "folderItems", "", "Lcom/miui/notes/model/FolderModel;", "stick", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class FolderOperationViewModel extends AndroidViewModel {
    private final UnPeekLiveData<Result<Integer>> _noteOperation;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FolderOperationViewModel(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this._noteOperation = new UnPeekLiveData<>();
    }

    public final LiveData<Result<Integer>> getNoteOperation() {
        return this._noteOperation;
    }

    /* JADX INFO: renamed from: com.miui.notes.home.viewmodel.FolderOperationViewModel$delete$1, reason: invalid class name */
    /* JADX INFO: compiled from: FolderOperationViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.home.viewmodel.FolderOperationViewModel$delete$1", f = "FolderOperationViewModel.kt", i = {}, l = {34}, m = "invokeSuspend", n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<FolderModel> $folderItems;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(List<? extends FolderModel> list, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$folderItems = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FolderOperationViewModel.this.new AnonymousClass1(this.$folderItems, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                UnPeekLiveData unPeekLiveData = FolderOperationViewModel.this._noteOperation;
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.dlg_delete_message);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                unPeekLiveData.setValue(new Result.Loading(string));
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00931(this.$folderItems, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            FolderOperationViewModel.this._noteOperation.setValue(new Result.Complete(Boxing.boxInt(0), Operation.DELETE));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.home.viewmodel.FolderOperationViewModel$delete$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: FolderOperationViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.home.viewmodel.FolderOperationViewModel$delete$1$1", f = "FolderOperationViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00931 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ List<FolderModel> $folderItems;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C00931(List<? extends FolderModel> list, Continuation<? super C00931> continuation) {
                super(2, continuation);
                this.$folderItems = list;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00931(this.$folderItems, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00931) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                long[] jArr = new long[this.$folderItems.size()];
                int size = this.$folderItems.size();
                for (int i = 0; i < size; i++) {
                    jArr[i] = this.$folderItems.get(i).getId();
                }
                FolderStore.delete(BaseNoteApp.INSTANCE.getInstance(), jArr);
                return Unit.INSTANCE;
            }
        }
    }

    public final void delete(List<? extends FolderModel> folderItems) {
        Intrinsics.checkNotNullParameter(folderItems, "folderItems");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(folderItems, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.home.viewmodel.FolderOperationViewModel$stick$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FolderOperationViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.home.viewmodel.FolderOperationViewModel$stick$1", f = "FolderOperationViewModel.kt", i = {0}, l = {51}, m = "invokeSuspend", n = {"count"}, s = {"L$0"})
    static final class C03001 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ List<FolderModel> $folderItems;
        final /* synthetic */ boolean $stick;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C03001(List<? extends FolderModel> list, boolean z, Continuation<? super C03001> continuation) {
            super(2, continuation);
            this.$folderItems = list;
            this.$stick = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return FolderOperationViewModel.this.new C03001(this.$folderItems, this.$stick, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03001) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Ref.IntRef intRef;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                UnPeekLiveData unPeekLiveData = FolderOperationViewModel.this._noteOperation;
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.dlg_stick_message);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                unPeekLiveData.setValue(new Result.Loading(string));
                Ref.IntRef intRef2 = new Ref.IntRef();
                this.L$0 = intRef2;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00941(this.$folderItems, intRef2, this.$stick, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                intRef = intRef2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                intRef = (Ref.IntRef) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            FolderOperationViewModel.this._noteOperation.setValue(new Result.Complete(Boxing.boxInt(intRef.element), Operation.STICK));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.home.viewmodel.FolderOperationViewModel$stick$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: FolderOperationViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.home.viewmodel.FolderOperationViewModel$stick$1$1", f = "FolderOperationViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00941 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.IntRef $count;
            final /* synthetic */ List<FolderModel> $folderItems;
            final /* synthetic */ boolean $stick;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C00941(List<? extends FolderModel> list, Ref.IntRef intRef, boolean z, Continuation<? super C00941> continuation) {
                super(2, continuation);
                this.$folderItems = list;
                this.$count = intRef;
                this.$stick = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00941(this.$folderItems, this.$count, this.$stick, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00941) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                long[] jArr = new long[this.$folderItems.size()];
                int size = this.$folderItems.size();
                for (int i = 0; i < size; i++) {
                    jArr[i] = this.$folderItems.get(i).getId();
                }
                this.$count.element = ItemStore.stick(BaseNoteApp.INSTANCE.getInstance(), jArr, this.$stick);
                return Unit.INSTANCE;
            }
        }
    }

    public final void stick(List<? extends FolderModel> folderItems, boolean stick) {
        Intrinsics.checkNotNullParameter(folderItems, "folderItems");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C03001(folderItems, stick, null), 3, null);
    }
}
