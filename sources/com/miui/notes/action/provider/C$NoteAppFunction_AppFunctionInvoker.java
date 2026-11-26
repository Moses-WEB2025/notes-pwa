package com.miui.notes.action.provider;

import androidx.appfunctions.AppFunctionAppUnknownException;
import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionElementNotFoundException;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import androidx.appfunctions.service.internal.AppFunctionInvoker;
import androidx.appfunctions.service.internal.ConfigurableAppFunctionFactory;
import com.google.android.appfunctions.schema.notes.v1.CreateNoteParams;
import com.google.android.appfunctions.schema.notes.v1.FindNotesParams;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.excerpt.NotifyUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SpillingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: renamed from: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInvoker, reason: invalid class name */
/* JADX INFO: compiled from: $NoteAppFunction_AppFunctionInvoker.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J6\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00062\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\n0\u000fH\u0096@¢\u0006\u0002\u0010\u0010R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, d2 = {"com/miui/notes/action/provider/$NoteAppFunction_AppFunctionInvoker", "Landroidx/appfunctions/service/internal/AppFunctionInvoker;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "supportedFunctionIds", "", "", "getSupportedFunctionIds", "()Ljava/util/Set;", "unsafeInvoke", "", "appFunctionContext", "Landroidx/appfunctions/AppFunctionContext;", "functionIdentifier", "parameters", "", "(Landroidx/appfunctions/AppFunctionContext;Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class C$NoteAppFunction_AppFunctionInvoker implements AppFunctionInvoker {
    private final Set<String> supportedFunctionIds = SetsKt.setOf((Object[]) new String[]{NoteAppFunctionIds.CREATE_NOTE_ID, NoteAppFunctionIds.FIND_NOTES_ID, NoteAppFunctionIds.GET_NOTES_ID, NoteAppFunctionIds.SHOW_NOTE_ID});

    /* JADX INFO: renamed from: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInvoker$unsafeInvoke$1, reason: invalid class name */
    /* JADX INFO: compiled from: $NoteAppFunction_AppFunctionInvoker.kt */
    @Metadata(k = 3, mv = {2, 2, 0}, xi = 48)
    @DebugMetadata(c = "com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInvoker", f = "$NoteAppFunction_AppFunctionInvoker.kt", i = {0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3}, l = {33, 40, 47, 54}, m = "unsafeInvoke", n = {"appFunctionContext", "functionIdentifier", "parameters", "appFunctionContext", "functionIdentifier", "parameters", "appFunctionContext", "functionIdentifier", "parameters", "appFunctionContext", "functionIdentifier", "parameters"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$0", "L$1", "L$2"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return C$NoteAppFunction_AppFunctionInvoker.this.unsafeInvoke(null, null, null, this);
        }
    }

    @Override // androidx.appfunctions.service.internal.AppFunctionInvoker
    public Set<String> getSupportedFunctionIds() {
        return this.supportedFunctionIds;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:43:0x016f A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // androidx.appfunctions.service.internal.AppFunctionInvoker
    public Object unsafeInvoke(AppFunctionContext appFunctionContext, String str, Map<String, ? extends Object> map, Continuation<Object> continuation) throws AppFunctionAppUnknownException, AppFunctionFunctionNotFoundException, IOException, AppFunctionElementNotFoundException {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        if (i != 0) {
            if (i != 1 && i != 2 && i != 3 && i != 4) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        switch (str.hashCode()) {
            case -1611495351:
                if (str.equals(NoteAppFunctionIds.FIND_NOTES_ID)) {
                    NoteAppFunction noteAppFunction = (NoteAppFunction) new ConfigurableAppFunctionFactory(appFunctionContext.getContext(), new Function0() { // from class: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInvoker$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return C$NoteAppFunction_AppFunctionInvoker.unsafeInvoke$lambda$1();
                        }
                    }).createEnclosingClass(NoteAppFunction.class);
                    Object obj2 = map.get("findNotesParams");
                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type com.google.android.appfunctions.schema.notes.v1.FindNotesParams");
                    anonymousClass1.L$0 = SpillingKt.nullOutSpilledVariable(appFunctionContext);
                    anonymousClass1.L$1 = SpillingKt.nullOutSpilledVariable(str);
                    anonymousClass1.L$2 = SpillingKt.nullOutSpilledVariable(map);
                    anonymousClass1.label = 2;
                    Object objFindNotes = noteAppFunction.findNotes(appFunctionContext, (FindNotesParams) obj2, anonymousClass1);
                    if (objFindNotes == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return objFindNotes;
                }
                throw new AppFunctionFunctionNotFoundException("Unable to find " + str);
            case -1299780755:
                if (str.equals(NoteAppFunctionIds.CREATE_NOTE_ID)) {
                    NoteAppFunction noteAppFunction2 = (NoteAppFunction) new ConfigurableAppFunctionFactory(appFunctionContext.getContext(), new Function0() { // from class: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInvoker$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return C$NoteAppFunction_AppFunctionInvoker.unsafeInvoke$lambda$0();
                        }
                    }).createEnclosingClass(NoteAppFunction.class);
                    Object obj3 = map.get("createNoteParams");
                    Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type com.google.android.appfunctions.schema.notes.v1.CreateNoteParams");
                    anonymousClass1.L$0 = SpillingKt.nullOutSpilledVariable(appFunctionContext);
                    anonymousClass1.L$1 = SpillingKt.nullOutSpilledVariable(str);
                    anonymousClass1.L$2 = SpillingKt.nullOutSpilledVariable(map);
                    anonymousClass1.label = 1;
                    Object objCreateNote = noteAppFunction2.createNote(appFunctionContext, (CreateNoteParams) obj3, anonymousClass1);
                    if (objCreateNote == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return objCreateNote;
                }
                throw new AppFunctionFunctionNotFoundException("Unable to find " + str);
            case -1097086802:
                if (str.equals(NoteAppFunctionIds.SHOW_NOTE_ID)) {
                    NoteAppFunction noteAppFunction3 = (NoteAppFunction) new ConfigurableAppFunctionFactory(appFunctionContext.getContext(), new Function0() { // from class: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInvoker$$ExternalSyntheticLambda3
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return C$NoteAppFunction_AppFunctionInvoker.unsafeInvoke$lambda$3();
                        }
                    }).createEnclosingClass(NoteAppFunction.class);
                    Object obj4 = map.get(NotifyUtils.KEY_NOTE_ID);
                    Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.String");
                    anonymousClass1.L$0 = SpillingKt.nullOutSpilledVariable(appFunctionContext);
                    anonymousClass1.L$1 = SpillingKt.nullOutSpilledVariable(str);
                    anonymousClass1.L$2 = SpillingKt.nullOutSpilledVariable(map);
                    anonymousClass1.label = 4;
                    Object objShowNote = noteAppFunction3.showNote(appFunctionContext, (String) obj4, anonymousClass1);
                    if (objShowNote == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return objShowNote;
                }
                throw new AppFunctionFunctionNotFoundException("Unable to find " + str);
            case 1202750762:
                if (str.equals(NoteAppFunctionIds.GET_NOTES_ID)) {
                    NoteAppFunction noteAppFunction4 = (NoteAppFunction) new ConfigurableAppFunctionFactory(appFunctionContext.getContext(), new Function0() { // from class: com.miui.notes.action.provider.$NoteAppFunction_AppFunctionInvoker$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return C$NoteAppFunction_AppFunctionInvoker.unsafeInvoke$lambda$2();
                        }
                    }).createEnclosingClass(NoteAppFunction.class);
                    Object obj5 = map.get("noteIds");
                    Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.collections.List<kotlin.String>");
                    anonymousClass1.L$0 = SpillingKt.nullOutSpilledVariable(appFunctionContext);
                    anonymousClass1.L$1 = SpillingKt.nullOutSpilledVariable(str);
                    anonymousClass1.L$2 = SpillingKt.nullOutSpilledVariable(map);
                    anonymousClass1.label = 3;
                    Object notes = noteAppFunction4.getNotes(appFunctionContext, (List) obj5, anonymousClass1);
                    if (notes == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return notes;
                }
                throw new AppFunctionFunctionNotFoundException("Unable to find " + str);
            default:
                throw new AppFunctionFunctionNotFoundException("Unable to find " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NoteAppFunction unsafeInvoke$lambda$0() {
        return new NoteAppFunction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NoteAppFunction unsafeInvoke$lambda$1() {
        return new NoteAppFunction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NoteAppFunction unsafeInvoke$lambda$2() {
        return new NoteAppFunction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final NoteAppFunction unsafeInvoke$lambda$3() {
        return new NoteAppFunction();
    }
}
