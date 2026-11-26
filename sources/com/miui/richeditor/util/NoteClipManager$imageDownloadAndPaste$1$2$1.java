package com.miui.richeditor.util;

import android.content.Context;
import com.miui.common.tool.ImageUtils;
import com.miui.notes.tool.AttachmentUtils;
import java.io.File;
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

/* JADX INFO: compiled from: NoteClipManager.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 2, 0}, xi = 48)
@DebugMetadata(c = "com.miui.richeditor.util.NoteClipManager$imageDownloadAndPaste$1$2$1", f = "NoteClipManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
final class NoteClipManager$imageDownloadAndPaste$1$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
    final /* synthetic */ NoteClipManager.IClipData $clipData;
    final /* synthetic */ Context $mContext;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NoteClipManager$imageDownloadAndPaste$1$2$1(Context context, NoteClipManager.IClipData iClipData, Continuation<? super NoteClipManager$imageDownloadAndPaste$1$2$1> continuation) {
        super(2, continuation);
        this.$mContext = context;
        this.$clipData = iClipData;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new NoteClipManager$imageDownloadAndPaste$1$2$1(this.$mContext, this.$clipData, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super String> continuation) {
        return ((NoteClipManager$imageDownloadAndPaste$1$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Context context = this.$mContext;
        AttachmentUtils attachmentUtils = AttachmentUtils.INSTANCE;
        String onlineUri = ((NoteClipManager.ImageElement) this.$clipData).getOnlineUri();
        Intrinsics.checkNotNull(onlineUri);
        String attachmentPath = AttachmentUtils.getAttachmentPath(context, attachmentUtils.downloadImageToTemp(onlineUri));
        if (attachmentPath == null) {
            return null;
        }
        File file = new File(attachmentPath);
        if (!file.exists()) {
            return null;
        }
        ((NoteClipManager.ImageElement) this.$clipData).setLocalValue(ImageUtils.INSTANCE.getImageContentUri(this.$mContext, file));
        return null;
    }
}
