package xc.engine.math.iink.callback;

import kotlin.Metadata;
import xc.engine.math.iink.bean.MathResult;

/* JADX INFO: compiled from: MathResultCallback.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lxc/engine/math/iink/callback/MathResultCallback;", "", "onMathResult", "", "mathResult", "Lxc/engine/math/iink/bean/MathResult;", "doDelete", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface MathResultCallback {
    void doDelete();

    void onMathResult(MathResult mathResult);
}
