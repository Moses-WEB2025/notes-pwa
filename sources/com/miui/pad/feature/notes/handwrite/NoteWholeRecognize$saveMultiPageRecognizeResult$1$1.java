package com.miui.pad.feature.notes.handwrite;

import android.content.ContentValues;
import com.google.gson.Gson;
import com.miui.common.tool.Utils;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWriteDataUtils;
import com.miui.pad.feature.notes.multihandwrite.MultiHandWritePathUtils;
import java.util.ArrayList;
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
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: NoteWholeRecognize.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.pad.feature.notes.handwrite.NoteWholeRecognize$saveMultiPageRecognizeResult$1$1", f = "NoteWholeRecognize.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class NoteWholeRecognize$saveMultiPageRecognizeResult$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $entDir;
    final /* synthetic */ long $folderId;
    final /* synthetic */ String $label;
    final /* synthetic */ long $noteId;
    final /* synthetic */ String $recognizeJson;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NoteWholeRecognize$saveMultiPageRecognizeResult$1$1(String str, String str2, String str3, long j, long j2, Continuation<? super NoteWholeRecognize$saveMultiPageRecognizeResult$1$1> continuation) {
        super(2, continuation);
        this.$entDir = str;
        this.$recognizeJson = str2;
        this.$label = str3;
        this.$folderId = j;
        this.$noteId = j2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NoteWholeRecognize$saveMultiPageRecognizeResult$1$1(this.$entDir, this.$recognizeJson, this.$label, this.$folderId, this.$noteId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((NoteWholeRecognize$saveMultiPageRecognizeResult$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String multiPageRecognizeJsonPath = MultiHandWritePathUtils.getMultiPageRecognizeJsonPath(this.$entDir);
        Utils utils = Utils.INSTANCE;
        Intrinsics.checkNotNull(multiPageRecognizeJsonPath);
        utils.saveJsonToFile(multiPageRecognizeJsonPath, this.$recognizeJson);
        ContentValues contentValues = new ContentValues();
        contentValues.put("snippet", MultiHandWriteDataUtils.resetSnippet(this.$label, false));
        contentValues.put("parent_id", Boxing.boxLong(this.$folderId));
        ArrayList arrayList = new ArrayList();
        String relativePathByComplete = MultiHandWritePathUtils.getRelativePathByComplete(multiPageRecognizeJsonPath);
        Intrinsics.checkNotNullExpressionValue(relativePathByComplete, "getRelativePathByComplete(...)");
        arrayList.add(relativePathByComplete);
        contentValues.put("recognized_file_names", new Gson().toJson(arrayList));
        MultiHandWriteDataUtils.updateValues(contentValues, this.$noteId);
        return Unit.INSTANCE;
    }
}
