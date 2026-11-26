package com.miui.notes.page.viewmodel;

import android.app.Application;
import android.content.ContentValues;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelKt;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.stat.ToDoStat;
import com.miui.common.tool.AudioUtils;
import com.miui.floatwindow.FloatWindowManagerAgent;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.model.NoteEntity;
import com.miui.notes.model.NoteEntityHelper;
import com.miui.notes.page.BaseNoteListPageFragment;
import com.miui.notes.provider.Notes;
import com.miui.notes.store.FolderStore;
import com.miui.notes.store.ItemStore;
import com.miui.notes.store.NoteStore;
import com.miui.notes.ui.widget.EditableRecyclerViewWrapper;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils;
import com.xiaomi.verificationsdk.internal.Constants;
import defpackage.R2;
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
import miuix.android.content.MiuiIntent;

/* JADX INFO: compiled from: NoteOperationViewModel.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u001e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0016\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0013J\u0016\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\tJ\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010!\u001a\u00020\u000f2\u0006\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020$R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\b0\u000b8F¢\u0006\u0006\u001a\u0004\b \u0010\r¨\u0006%"}, d2 = {"Lcom/miui/notes/page/viewmodel/NoteOperationViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/app/Application;)V", "_noteOperation", "Landroidx/lifecycle/MutableLiveData;", "Lcom/miui/notes/page/viewmodel/Result;", "", "noteOperation", "Landroidx/lifecycle/LiveData;", "getNoteOperation", "()Landroidx/lifecycle/LiveData;", ToDoStat.DELETE, "", "ids", "", Constants.FORCE, "", "checkHasExcerptingNoteAndStop", "move", "folderId", "", "foldName", "", "stick", MiuiIntent.COMMAND_ICON_PANEL_HIDE, "toastId", "unHide", "_allCheckStatus", "allCheckStatus", "getAllCheckStatus", "doAllCheck", "check", "fragment", "Lcom/miui/notes/page/BaseNoteListPageFragment;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteOperationViewModel extends AndroidViewModel {
    private final MutableLiveData<Result<Boolean>> _allCheckStatus;
    private final MutableLiveData<Result<Integer>> _noteOperation;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteOperationViewModel(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this._noteOperation = new MutableLiveData<>();
        this._allCheckStatus = new MutableLiveData<>();
    }

    public final LiveData<Result<Integer>> getNoteOperation() {
        return this._noteOperation;
    }

    /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$delete$1, reason: invalid class name */
    /* JADX INFO: compiled from: NoteOperationViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$delete$1", f = "NoteOperationViewModel.kt", i = {0}, l = {43}, m = "invokeSuspend", n = {"count"}, s = {"L$0"})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $force;
        final /* synthetic */ long[] $ids;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(long[] jArr, boolean z, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$ids = jArr;
            this.$force = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return NoteOperationViewModel.this.new AnonymousClass1(this.$ids, this.$force, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Ref.IntRef intRef;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableLiveData mutableLiveData = NoteOperationViewModel.this._noteOperation;
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.dlg_delete_message);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                mutableLiveData.setValue(new Result.Loading(string));
                Ref.IntRef intRef2 = new Ref.IntRef();
                this.L$0 = intRef2;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00981(this.$ids, intRef2, this.$force, null), this) == coroutine_suspended) {
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
            NoteOperationViewModel.this.checkHasExcerptingNoteAndStop(this.$ids);
            AudioUtils.playDeleteRingtone();
            NoteOperationViewModel.this._noteOperation.setValue(new Result.Complete(Boxing.boxInt(intRef.element), Operation.DELETE));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$delete$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteOperationViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$delete$1$1", f = "NoteOperationViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00981 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.IntRef $count;
            final /* synthetic */ boolean $force;
            final /* synthetic */ long[] $ids;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00981(long[] jArr, Ref.IntRef intRef, boolean z, Continuation<? super C00981> continuation) {
                super(2, continuation);
                this.$ids = jArr;
                this.$count = intRef;
                this.$force = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00981(this.$ids, this.$count, this.$force, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00981) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                    try {
                        long[] jArr = this.$ids;
                        if (i >= jArr.length) {
                            break;
                        }
                        int iCoerceAtMost = RangesKt.coerceAtMost(i + 500, jArr.length);
                        this.$count.element += NoteStore.delete(BaseNoteApp.INSTANCE.getInstance(), this.$ids, i, iCoerceAtMost, this.$force);
                        i = iCoerceAtMost;
                    } catch (Exception e) {
                        Logger.INSTANCE.d("NotePageViewModel", e.getMessage());
                    }
                }
                if (this.$count.element > 0) {
                    Notes.Utils.updateAllWidgets(BaseNoteApp.INSTANCE.getInstance());
                }
                return Unit.INSTANCE;
            }
        }
    }

    public final void delete(long[] ids, boolean force) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(ids, force, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkHasExcerptingNoteAndStop(long[] ids) {
        if (FloatWindowManagerAgent.isContinuousExcerpt()) {
            for (long j : ids) {
                if (j == FloatWindowManagerAgent.getExcerptId()) {
                    FloatWindowManagerAgent.stopContinuousExcerpt();
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$move$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteOperationViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$move$1", f = "NoteOperationViewModel.kt", i = {0}, l = {85}, m = "invokeSuspend", n = {"count"}, s = {"L$0"})
    static final class C03111 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $foldName;
        final /* synthetic */ long $folderId;
        final /* synthetic */ long[] $ids;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03111(long j, String str, long[] jArr, Continuation<? super C03111> continuation) {
            super(2, continuation);
            this.$folderId = j;
            this.$foldName = str;
            this.$ids = jArr;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return NoteOperationViewModel.this.new C03111(this.$folderId, this.$foldName, this.$ids, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03111) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Ref.IntRef intRef;
            String quantityString;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableLiveData mutableLiveData = NoteOperationViewModel.this._noteOperation;
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.dlg_move_message);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                mutableLiveData.setValue(new Result.Loading(string));
                Ref.IntRef intRef2 = new Ref.IntRef();
                this.L$0 = intRef2;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C01011(this.$ids, intRef2, this.$folderId, null), this) == coroutine_suspended) {
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
            if (this.$folderId == 0) {
                quantityString = BaseNoteApp.INSTANCE.getInstance().getResources().getQuantityString(R.plurals.format_move_notes_out_folder, intRef.element, Boxing.boxInt(intRef.element));
                Intrinsics.checkNotNull(quantityString);
            } else {
                quantityString = BaseNoteApp.INSTANCE.getInstance().getResources().getQuantityString(R.plurals.format_move_notes_to_folder, intRef.element, Boxing.boxInt(intRef.element), this.$foldName);
                Intrinsics.checkNotNull(quantityString);
            }
            Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), quantityString, 0).show();
            NoteOperationViewModel.this._noteOperation.setValue(new Result.Complete(Boxing.boxInt(intRef.element), Operation.MOVE));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$move$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteOperationViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$move$1$1", f = "NoteOperationViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01011 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
            final /* synthetic */ Ref.IntRef $count;
            final /* synthetic */ long $folderId;
            final /* synthetic */ long[] $ids;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01011(long[] jArr, Ref.IntRef intRef, long j, Continuation<? super C01011> continuation) {
                super(2, continuation);
                this.$ids = jArr;
                this.$count = intRef;
                this.$folderId = j;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01011(this.$ids, this.$count, this.$folderId, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
                return ((C01011) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
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
                        int iMin = Math.min(i + 500, jArr.length);
                        this.$count.element += NoteStore.move(BaseNoteApp.INSTANCE.getInstance(), this.$ids, this.$folderId, i, iMin);
                        i = iMin;
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
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C03111(folderId, foldName, ids, null), 3, null);
    }

    public final void stick(long[] ids, boolean stick) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        Ref.IntRef intRef = new Ref.IntRef();
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C03121(new Ref.IntRef(), intRef, ids, stick, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$stick$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteOperationViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$stick$1", f = "NoteOperationViewModel.kt", i = {}, l = {118}, m = "invokeSuspend", n = {}, s = {})
    static final class C03121 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.IntRef $count;
        final /* synthetic */ long[] $ids;
        final /* synthetic */ Ref.IntRef $start;
        final /* synthetic */ boolean $stick;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03121(Ref.IntRef intRef, Ref.IntRef intRef2, long[] jArr, boolean z, Continuation<? super C03121> continuation) {
            super(2, continuation);
            this.$count = intRef;
            this.$start = intRef2;
            this.$ids = jArr;
            this.$stick = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return NoteOperationViewModel.this.new C03121(this.$count, this.$start, this.$ids, this.$stick, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03121) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableLiveData mutableLiveData = NoteOperationViewModel.this._noteOperation;
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.dlg_stick_message);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                mutableLiveData.setValue(new Result.Loading(string));
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C01021(this.$start, this.$ids, this.$count, this.$stick, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            NoteOperationViewModel.this._noteOperation.setValue(new Result.Complete(Boxing.boxInt(this.$count.element), Operation.STICK));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$stick$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteOperationViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$stick$1$1", f = "NoteOperationViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01021 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.IntRef $count;
            final /* synthetic */ long[] $ids;
            final /* synthetic */ Ref.IntRef $start;
            final /* synthetic */ boolean $stick;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01021(Ref.IntRef intRef, long[] jArr, Ref.IntRef intRef2, boolean z, Continuation<? super C01021> continuation) {
                super(2, continuation);
                this.$start = intRef;
                this.$ids = jArr;
                this.$count = intRef2;
                this.$stick = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01021(this.$start, this.$ids, this.$count, this.$stick, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01021) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                while (this.$start.element < this.$ids.length) {
                    int iMin = Math.min(this.$start.element + 500, this.$ids.length);
                    this.$count.element += ItemStore.stick(BaseNoteApp.INSTANCE.getInstance(), this.$ids, this.$stick, this.$start.element, iMin);
                    this.$start.element = iMin;
                }
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$hide$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteOperationViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$hide$1", f = "NoteOperationViewModel.kt", i = {0}, l = {R2.array.ai_edit_text_send_blend_colors}, m = "invokeSuspend", n = {"haveEmpty"}, s = {"L$0"})
    static final class C03101 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.IntRef $count;
        final /* synthetic */ long[] $ids;
        final /* synthetic */ int $toastId;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03101(Ref.IntRef intRef, int i, long[] jArr, Continuation<? super C03101> continuation) {
            super(2, continuation);
            this.$count = intRef;
            this.$toastId = i;
            this.$ids = jArr;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return NoteOperationViewModel.this.new C03101(this.$count, this.$toastId, this.$ids, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03101) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Ref.BooleanRef booleanRef;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
                MutableLiveData mutableLiveData = NoteOperationViewModel.this._noteOperation;
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.dlg_hide_message);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                mutableLiveData.setValue(new Result.Loading(string));
                this.L$0 = booleanRef2;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C01001(this.$ids, booleanRef2, this.$count, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                booleanRef = booleanRef2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                booleanRef = (Ref.BooleanRef) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            if (this.$count.element > 0) {
                Notes.Utils.updateAllWidgets(BaseNoteApp.INSTANCE.getInstance());
                Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), BaseNoteApp.INSTANCE.getInstance().getString(this.$toastId), 0).show();
            }
            if (booleanRef.element) {
                Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), R.string.error_hide_empty_note, 0).show();
            }
            NoteOperationViewModel.this._noteOperation.setValue(new Result.Complete(Boxing.boxInt(this.$count.element), Operation.HIDE));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$hide$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteOperationViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$hide$1$1", f = "NoteOperationViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01001 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.IntRef $count;
            final /* synthetic */ Ref.BooleanRef $haveEmpty;
            final /* synthetic */ long[] $ids;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01001(long[] jArr, Ref.BooleanRef booleanRef, Ref.IntRef intRef, Continuation<? super C01001> continuation) {
                super(2, continuation);
                this.$ids = jArr;
                this.$haveEmpty = booleanRef;
                this.$count = intRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01001(this.$ids, this.$haveEmpty, this.$count, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01001) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                long[] jArr = this.$ids;
                if (!(jArr.length == 0)) {
                    for (long j : jArr) {
                        NoteEntity noteEntityLoad = NoteEntityHelper.load(BaseNoteApp.INSTANCE.getInstance(), "_id=" + j, null);
                        if (noteEntityLoad == null || noteEntityLoad.isEmpty()) {
                            this.$haveEmpty.element = true;
                        } else if (noteEntityLoad.getParentId() != -4) {
                            noteEntityLoad.setParentId(-4L);
                            if (TextUtils.equals("multi_handwrite", noteEntityLoad.getNoteType())) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("parent_id", Boxing.boxLong(noteEntityLoad.getParentId()));
                                contentValues.put("modified_date", Boxing.boxLong(System.currentTimeMillis()));
                                contentValues.put("snippet", noteEntityLoad.getContent());
                                if (MultiHandWriteDataUtils.updateValues(contentValues, j)) {
                                    this.$count.element++;
                                }
                            } else if (noteEntityLoad.syncNote(BaseNoteApp.INSTANCE.getInstance())) {
                                this.$count.element++;
                            }
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }
    }

    public final void hide(long[] ids, int toastId) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C03101(new Ref.IntRef(), toastId, ids, null), 3, null);
    }

    /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$unHide$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteOperationViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$unHide$1", f = "NoteOperationViewModel.kt", i = {}, l = {R2.array.ai_widget_global_device_blacklist}, m = "invokeSuspend", n = {}, s = {})
    static final class C03131 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.IntRef $count;
        final /* synthetic */ long[] $ids;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03131(Ref.IntRef intRef, long[] jArr, Continuation<? super C03131> continuation) {
            super(2, continuation);
            this.$count = intRef;
            this.$ids = jArr;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return NoteOperationViewModel.this.new C03131(this.$count, this.$ids, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03131) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableLiveData mutableLiveData = NoteOperationViewModel.this._noteOperation;
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.dlg_unhide_message);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                mutableLiveData.setValue(new Result.Loading(string));
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C01031(this.$ids, this.$count, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            if (this.$count.element > 0) {
                Notes.Utils.updateAllWidgets(BaseNoteApp.INSTANCE.getInstance());
                String quantityString = BaseNoteApp.INSTANCE.getInstance().getResources().getQuantityString(R.plurals.toast_notes_unhidden_count, this.$count.element, Boxing.boxInt(this.$count.element));
                Intrinsics.checkNotNullExpressionValue(quantityString, "getQuantityString(...)");
                Toast.makeText(BaseNoteApp.INSTANCE.getInstance(), quantityString, 0).show();
            }
            NoteOperationViewModel.this._noteOperation.setValue(new Result.Complete(Boxing.boxInt(this.$count.element), Operation.UN_HIDE));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$unHide$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteOperationViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$unHide$1$1", f = "NoteOperationViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C01031 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Ref.IntRef $count;
            final /* synthetic */ long[] $ids;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C01031(long[] jArr, Ref.IntRef intRef, Continuation<? super C01031> continuation) {
                super(2, continuation);
                this.$ids = jArr;
                this.$count = intRef;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C01031(this.$ids, this.$count, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C01031) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                long[] jArr = this.$ids;
                if (!(jArr.length == 0)) {
                    for (long j : jArr) {
                        NoteEntity noteEntityLoad = NoteEntityHelper.load(BaseNoteApp.INSTANCE.getInstance(), "_id=" + j, null);
                        if (noteEntityLoad != null && noteEntityLoad.getParentId() == -4) {
                            if (TextUtils.equals("multi_handwrite", noteEntityLoad.getNoteType())) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("parent_id", Boxing.boxLong(0L));
                                contentValues.put("modified_date", Boxing.boxLong(System.currentTimeMillis()));
                                contentValues.put("snippet", noteEntityLoad.getContent());
                                if (MultiHandWriteDataUtils.updateValues(contentValues, j)) {
                                    this.$count.element++;
                                }
                            } else {
                                try {
                                    if (TextUtils.isEmpty(noteEntityLoad.getCallNumber())) {
                                        noteEntityLoad.setParentId(0L);
                                    } else {
                                        noteEntityLoad.setParentId(-2L);
                                    }
                                } catch (Exception unused) {
                                    noteEntityLoad.setParentId(0L);
                                }
                                if (noteEntityLoad.syncNote(BaseNoteApp.INSTANCE.getInstance())) {
                                    this.$count.element++;
                                }
                            }
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }
    }

    public final void unHide(long[] ids) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C03131(new Ref.IntRef(), ids, null), 3, null);
    }

    public final LiveData<Result<Boolean>> getAllCheckStatus() {
        return this._allCheckStatus;
    }

    /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$doAllCheck$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: NoteOperationViewModel.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$doAllCheck$1", f = "NoteOperationViewModel.kt", i = {}, l = {R2.array.week_days_shortest}, m = "invokeSuspend", n = {}, s = {})
    static final class C03091 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ boolean $check;
        final /* synthetic */ BaseNoteListPageFragment $fragment;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C03091(boolean z, BaseNoteListPageFragment baseNoteListPageFragment, Continuation<? super C03091> continuation) {
            super(2, continuation);
            this.$check = z;
            this.$fragment = baseNoteListPageFragment;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return NoteOperationViewModel.this.new C03091(this.$check, this.$fragment, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C03091) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MutableLiveData mutableLiveData = NoteOperationViewModel.this._allCheckStatus;
                String string = BaseNoteApp.INSTANCE.getInstance().getString(R.string.loading_text);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                mutableLiveData.setValue(new Result.Loading(string));
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new C00991(this.$fragment, this.$check, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            NoteOperationViewModel.this._allCheckStatus.setValue(new Result.Complete(Boxing.boxBoolean(this.$check), Operation.AllCheck));
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: com.miui.notes.page.viewmodel.NoteOperationViewModel$doAllCheck$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: NoteOperationViewModel.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
        @DebugMetadata(c = "com.miui.notes.page.viewmodel.NoteOperationViewModel$doAllCheck$1$1", f = "NoteOperationViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        static final class C00991 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ boolean $check;
            final /* synthetic */ BaseNoteListPageFragment $fragment;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00991(BaseNoteListPageFragment baseNoteListPageFragment, boolean z, Continuation<? super C00991> continuation) {
                super(2, continuation);
                this.$fragment = baseNoteListPageFragment;
                this.$check = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00991(this.$fragment, this.$check, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00991) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                EditableRecyclerViewWrapper<?> recyclerWrapper = this.$fragment.getRecyclerWrapper();
                Intrinsics.checkNotNull(recyclerWrapper);
                recyclerWrapper.asyncSetAllItemsChecked(this.$check);
                return Unit.INSTANCE;
            }
        }
    }

    public final void doAllCheck(boolean check, BaseNoteListPageFragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        BuildersKt__Builders_commonKt.launch$default(ViewModelKt.getViewModelScope(this), null, null, new C03091(check, fragment, null), 3, null);
    }
}
