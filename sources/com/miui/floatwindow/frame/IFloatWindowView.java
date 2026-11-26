package com.miui.floatwindow.frame;

import kotlin.Metadata;

/* JADX INFO: compiled from: IFloatWindowView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&J\u001a\u0010\n\u001a\u00020\u00032\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0003H&R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0012À\u0006\u0003"}, d2 = {"Lcom/miui/floatwindow/frame/IFloatWindowView;", "", "preToFullWindow", "", "onFullWindowShow", "preChangeToGuide", "visibleViewWidth", "", "getVisibleViewWidth", "()I", "showToast", "content", "", "time", "", "canToGuide", "", "onDetachFromWindow", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IFloatWindowView {
    boolean canToGuide();

    int getVisibleViewWidth();

    void onDetachFromWindow();

    void onFullWindowShow();

    void preChangeToGuide();

    void preToFullWindow();

    void showToast(String content, long time);
}
