package com.miui.pad.feature.notes.handwrite;

import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.handwritecommon.api.HandViewProvider;
import com.miui.handwritecommon.api.callback.SinglePageRecListener;
import com.miui.handwritecommon.api.multipage.MultiPageRecListener;
import com.miui.notes.excerpt.NotifyUtils;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* JADX INFO: compiled from: NoteWholeRecognize.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0005J \u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0005R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/NoteWholeRecognize;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "defaultScope", "Lkotlinx/coroutines/CoroutineScope;", "getDefaultScope", "()Lkotlinx/coroutines/CoroutineScope;", "defaultScope$delegate", "Lkotlin/Lazy;", "saveSinglePageRecognizeResult", "", NotifyUtils.KEY_NOTE_ID, "", "entPath", "saveMultiPageRecognizeResult", "folderId", "entDir", "HandleCoroutineException", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteWholeRecognize {
    public static final String TAG = "NoteWholeRecognize";
    public static final NoteWholeRecognize INSTANCE = new NoteWholeRecognize();

    /* JADX INFO: renamed from: defaultScope$delegate, reason: from kotlin metadata */
    private static final Lazy defaultScope = LazyKt.lazy(new Function0() { // from class: com.miui.pad.feature.notes.handwrite.NoteWholeRecognize$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return NoteWholeRecognize.defaultScope_delegate$lambda$0();
        }
    });

    private NoteWholeRecognize() {
    }

    private final CoroutineScope getDefaultScope() {
        return (CoroutineScope) defaultScope.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CoroutineScope defaultScope_delegate$lambda$0() {
        return CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
    }

    /* JADX INFO: compiled from: NoteWholeRecognize.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lcom/miui/pad/feature/notes/handwrite/NoteWholeRecognize$HandleCoroutineException;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlinx/coroutines/CoroutineExceptionHandler;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "handleException", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Lkotlin/coroutines/CoroutineContext;", "exception", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class HandleCoroutineException extends AbstractCoroutineContextElement implements CoroutineExceptionHandler {
        public HandleCoroutineException() {
            super(CoroutineExceptionHandler.INSTANCE);
        }

        @Override // kotlinx.coroutines.CoroutineExceptionHandler
        public void handleException(CoroutineContext context, Throwable exception) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(exception, "exception");
            Logger.INSTANCE.d(NoteWholeRecognize.TAG, "handleException: Task exception" + exception);
        }
    }

    public final void saveSinglePageRecognizeResult(final long noteId, String entPath) {
        HandViewProvider.singlePageWholeRecognize(entPath, new SinglePageRecListener() { // from class: com.miui.pad.feature.notes.handwrite.NoteWholeRecognize$$ExternalSyntheticLambda0
            @Override // com.miui.handwritecommon.api.callback.SinglePageRecListener
            public final void callback(String str, String str2) {
                NoteWholeRecognize.saveSinglePageRecognizeResult$lambda$1(noteId, str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveSinglePageRecognizeResult$lambda$1(long j, String str, String str2) {
        BuildersKt__Builders_commonKt.launch$default(INSTANCE.getDefaultScope(), new HandleCoroutineException(), null, new NoteWholeRecognize$saveSinglePageRecognizeResult$1$1(str2, j, str, null), 2, null);
    }

    public final void saveMultiPageRecognizeResult(final long noteId, final long folderId, final String entDir) {
        if (HandViewProvider.init.get() && HandViewProvider.initEngine.get()) {
            HandViewProvider.multiPageRecognize(noteId, entDir, new MultiPageRecListener() { // from class: com.miui.pad.feature.notes.handwrite.NoteWholeRecognize$$ExternalSyntheticLambda1
                @Override // com.miui.handwritecommon.api.multipage.MultiPageRecListener
                public final void callback(String str, String str2) {
                    NoteWholeRecognize.saveMultiPageRecognizeResult$lambda$2(entDir, folderId, noteId, str, str2);
                }
            });
        } else {
            Logger.INSTANCE.i(TAG, "HandViewProvider not initialized");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveMultiPageRecognizeResult$lambda$2(String str, long j, long j2, String str2, String str3) {
        Logger.INSTANCE.d(TAG, "saveMultiPageRecognizeResult CallBack");
        BuildersKt__Builders_commonKt.launch$default(INSTANCE.getDefaultScope(), new HandleCoroutineException(), null, new NoteWholeRecognize$saveMultiPageRecognizeResult$1$1(str, str3, str2, j, j2, null), 2, null);
    }
}
