package xc.engine.math.iink.api;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: StubInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J#\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¢\u0006\u0002\u0010\fJ(\u0010\r\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000bH\u0016¨\u0006\u0011"}, d2 = {"Lxc/engine/math/iink/api/StubInkEditorListener;", "Lxc/engine/math/iink/api/IInkEditorListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "partChanged", "", "editor", "Lxc/engine/math/iink/api/IInkEngine;", "contentChanged", "blockIds", "", "", "(Lxc/engine/math/iink/api/IInkEngine;[Ljava/lang/String;)V", "onError", "blockId", SNSAuthProvider.VALUE_SNS_ERROR, "message", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class StubInkEditorListener implements IInkEditorListener {
    @Override // xc.engine.math.iink.api.IInkEditorListener
    public void contentChanged(IInkEngine editor, String[] blockIds) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(blockIds, "blockIds");
    }

    @Override // xc.engine.math.iink.api.IInkEditorListener
    public void onError(IInkEngine editor, String blockId, String error, String message) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(blockId, "blockId");
        Intrinsics.checkNotNullParameter(error, "error");
        Intrinsics.checkNotNullParameter(message, "message");
    }

    @Override // xc.engine.math.iink.api.IInkEditorListener
    public void partChanged(IInkEngine editor) {
        Intrinsics.checkNotNullParameter(editor, "editor");
    }
}
