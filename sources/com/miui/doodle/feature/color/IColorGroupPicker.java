package com.miui.doodle.feature.color;

import android.view.View;
import kotlin.Metadata;

/* JADX INFO: compiled from: IColorGroupPicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&R\u0014\u0010\t\u001a\u0004\u0018\u00010\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\rÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/feature/color/IColorGroupPicker;", "", "setGroup", "", "groupIndex", "", "selectGroup", "selectGroupEnd", "unSelectGroup", "view", "Landroid/view/View;", "getView", "()Landroid/view/View;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IColorGroupPicker {
    View getView();

    void selectGroup(int groupIndex);

    void selectGroupEnd(int groupIndex);

    void setGroup(int groupIndex);

    void unSelectGroup(int groupIndex);
}
