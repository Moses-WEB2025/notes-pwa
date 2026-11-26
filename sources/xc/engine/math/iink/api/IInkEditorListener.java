package xc.engine.math.iink.api;

import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import kotlin.Metadata;

/* JADX INFO: compiled from: IInkEngine.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J#\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&¢\u0006\u0002\u0010\nJ(\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lxc/engine/math/iink/api/IInkEditorListener;", "", "partChanged", "", "editor", "Lxc/engine/math/iink/api/IInkEngine;", "contentChanged", "blockIds", "", "", "(Lxc/engine/math/iink/api/IInkEngine;[Ljava/lang/String;)V", "onError", "blockId", SNSAuthProvider.VALUE_SNS_ERROR, "message", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IInkEditorListener {
    void contentChanged(IInkEngine editor, String[] blockIds);

    void onError(IInkEngine editor, String blockId, String error, String message);

    void partChanged(IInkEngine editor);
}
