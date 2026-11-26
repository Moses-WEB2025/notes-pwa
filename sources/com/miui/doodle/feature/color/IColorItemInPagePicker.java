package com.miui.doodle.feature.color;

import kotlin.Metadata;

/* JADX INFO: compiled from: IColorItemInPagePicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\u000bÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/feature/color/IColorItemInPagePicker;", "", "setData", "", "colorData", "", "setColor", "indexInPage", "", "selectData", "selectColor", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IColorItemInPagePicker {
    void selectColor(int indexInPage);

    void selectData(int[] colorData);

    void setColor(int indexInPage);

    void setData(int[] colorData);
}
