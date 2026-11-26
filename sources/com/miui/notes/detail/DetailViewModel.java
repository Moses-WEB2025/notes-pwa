package com.miui.notes.detail;

import android.app.Application;
import android.widget.Toast;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.notes.R;
import com.miui.notes.page.viewmodel.Operation;
import com.miui.notes.page.viewmodel.Result;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.NoteStore;
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
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* JADX INFO: compiled from: DetailViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0016"}, d2 = {"Lcom/miui/notes/detail/DetailViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/app/Application;)V", "_noteOperation", "Landroidx/lifecycle/MutableLiveData;", "Lcom/miui/notes/page/viewmodel/Result;", "", "noteOperation", "Landroidx/lifecycle/LiveData;", "getNoteOperation", "()Landroidx/lifecycle/LiveData;", "move", "", "ids", "", "folderId", "", "foldName", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class DetailViewModel extends AndroidViewModel {
    private final MutableLiveData<Result<Integer>> _noteOperation;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DetailViewModel(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this._noteOperation = new MutableLiveData<>();
    }

    public final LiveData<Result<Integer>> getNoteOperation() {
        return this._noteOperation;
    }

    /* JADX INFO: renamed from: com.miui.notes.detail.DetailViewModel$move$1, reason: invalid class name */
    /* JADX INFO: compiled from: DetailViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.detail.DetailViewModel$move$1", f = "DetailViewModel.kt", i = {0}, l = {36}, m = "invokeSuspend", n = {"count"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $foldName;
        final /* synthetic */ long $folderId;
        final /* synthetic */ long[] $ids;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(long j, String str, long[] jArr, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$folderId = j;
            this.$foldName = str;
            this.$ids = jArr;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return DetailViewModel.this.new AnonymousClass1(this.$folderId, this.$foldName, this.$ids, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Ref.IntRef intRef;
            String quantityString;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableLiveData mutableLiveData = DetailViewModel.this._noteOperation;
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.dlg_move_message);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                mutableLiveData.setValue(new Result.Loading(string));
                Ref.IntRef intRef2 = new Ref.IntRef();
                this.L$0 = intRef2;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00891(this.$ids, intRef2, this.$folderId, null), this) == coroutine_suspended) {
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
            intRef.element = RangesKt.coerceAtLeast(intRef.element, 1);
            if (this.$folderId == 0) {
                quantityString = BaseNoteApp.INSTANCE.getInstance().getResources().getQuantityString(R.plurals.format_move_notes_out_folder, intRef.element, Boxing.boxInt(intRef.element));
                Intrinsics.checkNotNull(quantityString);
            } else {
                quantityString = BaseNoteApp.INSTANCE.getInstance().getResources().getQuantityString(R.plurals.format_move_notes_to_folder, intRef.element, Boxing.boxInt(intRef.element), this.$foldName);
                Intrinsics.checkNotNull(quantityString);
            }
            Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), quantityString, 0).show();
            DetailViewModel.this._noteOperation.setValue(new Result.Complete(Boxing.boxInt(intRef.element), Operation.MOVE));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.detail.DetailViewModel$move$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: DetailViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.detail.DetailViewModel$move$1$1", f = "DetailViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00891 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
            final /* synthetic */ Ref.IntRef $count;
            final /* synthetic */ long $folderId;
            final /* synthetic */ long[] $ids;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00891(long[] jArr, Ref.IntRef intRef, long j, Continuation<? super C00891> continuation) {
                super(2, continuation);
                this.$ids = jArr;
                this.$count = intRef;
                this.$folderId = j;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00891(this.$ids, this.$count, this.$folderId, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
                return ((C00891) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                int i = 0;
                while (true) {
                    long[] jArr = this.$ids;
                    if (i < jArr.length) {
                        int iCoerceAtMost = RangesKt.coerceAtMost(i + 500, jArr.length);
                        this.$count.element += NoteStore.move(BaseNoteApp.INSTANCE.getInstance(), this.$ids, this.$folderId, i, iCoerceAtMost);
                        i = iCoerceAtMost;
                    } else {
                        return Boxing.boxBoolean(FolderStore.updateFolderModifiedTime$default(FolderStore.INSTANCE, BaseNoteApp.INSTANCE.getInstance(), this.$folderId, 0L, 4, null));
                    }
                }
            }
        }
    }

    public final void move(long[] ids, long folderId, String foldName) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        Intrinsics.checkNotNullParameter(foldName, "foldName");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(folderId, foldName, ids, null), 3, null);
    }
}
