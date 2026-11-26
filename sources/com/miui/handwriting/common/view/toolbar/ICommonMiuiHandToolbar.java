package com.miui.handwriting.common.view.toolbar;

import android.view.View;
import com.miui.handwriting.common.data.dataset.PenSet;
import com.miui.handwriting.common.listener.ToolBoxActionCallBack;
import com.miui.handwriting.common.view.check.ICheck;

/* JADX INFO: loaded from: classes3.dex */
public interface ICommonMiuiHandToolbar {
    void changeColorMode(int i, int i2);

    void dismissPopupWindow();

    void enableAi(boolean z);

    void enableViewSelect();

    PenSet getPenSet();

    int getToolBoxHeight();

    void onStepChanged(int i, boolean z, boolean z2);

    void setActionCallBack(ToolBoxActionCallBack toolBoxActionCallBack);

    void setBackClickEnable(boolean z);

    void setFromPc(boolean z);

    void setSaveClickListener(View.OnClickListener onClickListener);

    void setShareClickListener(View.OnClickListener onClickListener);

    void setShareIconState(boolean z);

    void setToolBoxViewTopPadding(int i, int i2);

    void setToolbarCheck(ICheck iCheck, boolean z);
}
