package com.miui.common.view;

import android.view.View;
import kotlin.Metadata;

/* JADX INFO: compiled from: IPopMenu.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\fJ\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\b\u0010\b\u001a\u00020\u0003H&J\u0012\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bH&¨\u0006\rÀ\u0006\u0003"}, d2 = {"Lcom/miui/common/view/IPopMenu;", "", "show", "", "anchor", "Landroid/view/View;", "eventPos", "", "dismiss", "setOnMenuListener", "listener", "Lcom/miui/common/view/IPopMenu$OnMenuListener;", "OnMenuListener", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IPopMenu {

    /* JADX INFO: compiled from: IPopMenu.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\u001a\u0010\u0005\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\nÀ\u0006\u0003"}, d2 = {"Lcom/miui/common/view/IPopMenu$OnMenuListener;", "", "onShow", "", "onDismiss", "onItemSelected", "view", "Landroid/view/View;", "index", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public interface OnMenuListener {
        void onDismiss();

        void onItemSelected(View view, int index);

        void onShow();
    }

    void dismiss();

    void setOnMenuListener(OnMenuListener listener);

    void show(View anchor, int[] eventPos);
}
