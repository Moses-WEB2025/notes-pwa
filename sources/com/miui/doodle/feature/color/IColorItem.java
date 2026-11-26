package com.miui.doodle.feature.color;

import android.view.View;
import kotlin.Metadata;
import miuix.smartaction.SmartAction;

/* JADX INFO: compiled from: IColorItem.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\b\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\u0003H&R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\n\u001a\u0004\u0018\u00010\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u000eÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/feature/color/IColorItem;", "", "setColor", "", "colorInt", "", "getColorInt", "()I", SmartAction.Feature.SELECT, "unselect", "view", "Landroid/view/View;", "getView", "()Landroid/view/View;", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IColorItem {
    int getColorInt();

    View getView();

    void select();

    void setColor(int colorInt);

    void unselect();
}
