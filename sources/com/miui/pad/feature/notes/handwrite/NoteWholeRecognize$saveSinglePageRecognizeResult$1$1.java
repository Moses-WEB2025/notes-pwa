package com.miui.pad.feature.notes.handwrite;

import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.Logger;
import com.miui.notes.model.HandWriteEntity;
import com.miui.notes.tool.AttachmentUtils;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: NoteWholeRecognize.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.pad.feature.notes.handwrite.NoteWholeRecognize$saveSinglePageRecognizeResult$1$1", f = "NoteWholeRecognize.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class NoteWholeRecognize$saveSinglePageRecognizeResult$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $label;
    final /* synthetic */ long $noteId;
    final /* synthetic */ String $recognizeJson;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NoteWholeRecognize$saveSinglePageRecognizeResult$1$1(String str, long j, String str2, Continuation<? super NoteWholeRecognize$saveSinglePageRecognizeResult$1$1> continuation) {
        super(2, continuation);
        this.$recognizeJson = str;
        this.$noteId = j;
        this.$label = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NoteWholeRecognize$saveSinglePageRecognizeResult$1$1(this.$recognizeJson, this.$noteId, this.$label, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NoteWholeRecognize$saveSinglePageRecognizeResult$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String strSaveJsonToTemp = AttachmentUtils.INSTANCE.saveJsonToTemp(BaseNoteApp.INSTANCE.getInstance(), this.$recognizeJson);
        HandWriteDBHelper.HandWriteNoteInfo handWriteNoteInfoQuery = HandWriteDBHelper.query(BaseNoteApp.INSTANCE.getInstance(), "_id=" + this.$noteId);
        if (handWriteNoteInfoQuery != null) {
            String handWriteContent = handWriteNoteInfoQuery.getHandWriteContent();
            HandWriteEntity handWriteEntity = handWriteContent != null ? HandWriteEntity.INSTANCE.toHandWriteEntity(handWriteContent) : null;
            if (handWriteEntity != null) {
                handWriteEntity.textContent = this.$label;
                handWriteEntity.recognizedText = "";
                handWriteEntity.setRecognizedFile(strSaveJsonToTemp);
                Logger.INSTANCE.i(NoteWholeRecognize.TAG, "noteId:" + this.$noteId + " save entName:" + handWriteEntity.entName + "success:" + HandWriteDBHelper.updateHandWrite(this.$noteId, handWriteNoteInfoQuery.getParentId(), handWriteEntity, handWriteNoteInfoQuery.getModifiedDate()));
            }
        }
        return Unit.INSTANCE;
    }
}
