package com.miui.doodle.feature.color;

import kotlin.Metadata;

/* JADX INFO: compiled from: IPalette.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J\u0012\u0010\f\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u000eH&J\u0012\u0010\u000f\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\u000eH&J\b\u0010\u0010\u001a\u00020\u0003H&R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007¨\u0006\u0011À\u0006\u0003"}, d2 = {"Lcom/miui/doodle/feature/color/IPalette;", "", "show", "", "dismiss", "isShow", "", "()Z", "setColor", "colorInt", "", "pickColor", "addPickListener", "listener", "Lcom/miui/doodle/feature/color/ColorPickListener;", "removePickListener", "clearViewAnimator", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IPalette {
    void addPickListener(ColorPickListener listener);

    void clearViewAnimator();

    void dismiss();

    boolean isShow();

    void pickColor(int colorInt);

    void removePickListener(ColorPickListener listener);

    void setColor(int colorInt);

    void show();
}
