package com.miui.richeditor.util;

import android.content.Context;
import android.net.Uri;
import com.miui.notes.tool.AttachmentUtils;
import com.miui.richeditor.schema.HtmlParser;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.CoroutineScope;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONObject;

/* JADX INFO: compiled from: NoteClipManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Lorg/json/JSONObject;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$buildJsonAndPaste$1$2$2$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class NoteClipManager$buildJsonAndPaste$1$2$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super JSONObject>, Object> {
    final /* synthetic */ Context $context;
    final /* synthetic */ Uri $uri;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NoteClipManager$buildJsonAndPaste$1$2$2$1(Context context, Uri uri, Continuation<? super NoteClipManager$buildJsonAndPaste$1$2$2$1> continuation) {
        super(2, continuation);
        this.$context = context;
        this.$uri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NoteClipManager$buildJsonAndPaste$1$2$2$1(this.$context, this.$uri, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super JSONObject> continuation) {
        return ((NoteClipManager$buildJsonAndPaste$1$2$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        JSONObject jSONObject = new JSONObject();
        String strSaveImageFile = AttachmentUtils.saveImageFile(this.$context, this.$uri, true);
        if (strSaveImageFile != null) {
            String strSubstringAfterLast$default = StringsKt.substringAfterLast$default(strSaveImageFile, InternalZipConstants.ZIP_FILE_SEPARATOR, (String) null, 2, (Object) null);
            if (Intrinsics.areEqual(strSubstringAfterLast$default, "")) {
                return null;
            }
            jSONObject.put("type", HtmlParser.ImageElement.TAG);
            jSONObject.put("content", strSubstringAfterLast$default);
            return jSONObject;
        }
        NoteClipManager noteClipManager = NoteClipManager.INSTANCE;
        NoteClipManager.isItemDeleted = true;
        return null;
    }
}
