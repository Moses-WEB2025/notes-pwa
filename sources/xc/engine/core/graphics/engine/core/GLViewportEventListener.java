package xc.engine.core.graphics.engine.core;

import kotlin.Metadata;

/* JADX INFO: compiled from: Camera.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lxc/engine/core/graphics/engine/core/GLViewportEventListener;", "", "onScrollBack", "", "onTopPageChanged", "pageIndex", "", "onTitleChanged", "libXCEngine_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface GLViewportEventListener {
    void onScrollBack();

    void onTitleChanged();

    void onTopPageChanged(int pageIndex);
}
