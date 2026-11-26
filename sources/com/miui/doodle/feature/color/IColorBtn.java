package com.miui.doodle.feature.color;

import kotlin.Metadata;

/* JADX INFO: compiled from: IColorBtn.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\tH&J\b\u0010\u000b\u001a\u00020\tH&R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\fÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/feature/color/IColorBtn;", "", "colorInt", "", "getColorInt", "()I", "setColorInt", "(I)V", "showPalette", "", "dismissPalette", "clearViewAnimator", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IColorBtn {
    void clearViewAnimator();

    void dismissPalette();

    int getColorInt();

    void setColorInt(int i);

    void showPalette();
}
