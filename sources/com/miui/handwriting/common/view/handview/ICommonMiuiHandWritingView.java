package com.miui.handwriting.common.view.handview;

import android.graphics.RectF;
import com.miui.handwriting.common.ai.data.CreationImage;
import com.miui.handwriting.common.data.bean.PartiBean;
import com.miui.handwriting.common.listener.CanvasElementTransactCallback;
import com.miui.handwriting.common.mask.CanvasElementTransactionMask;
import com.miui.handwriting.common.view.check.ICheck;
import com.miui.handwriting.common.view.page.HeaderView;

/* JADX INFO: loaded from: classes3.dex */
public interface ICommonMiuiHandWritingView {
    void addPage(boolean z);

    void adjustPageSize(int i, int i2, int i3);

    void clearTitleCursor();

    void copyAsText(PartiBean partiBean);

    void dismissAILoadingDialog();

    void dismissLoadingDialog();

    RectF getSelectAiRect();

    String getTitle(int i);

    void initUIProperties();

    void insertAiImage(String str, CreationImage creationImage, CanvasElementTransactionMask canvasElementTransactionMask);

    void onDoubleUndo();

    void onStepChanged(int i, int i2, boolean z, boolean z2, boolean z3);

    void release();

    void replaceSelected(String str);

    void replaceSelected(String str, CreationImage creationImage, CanvasElementTransactionMask canvasElementTransactionMask);

    void setCanvasElementTransactListener(CanvasElementTransactCallback canvasElementTransactCallback);

    void setSelectCheck(ICheck iCheck);

    void setTitle(String str, int i);

    void setTitleAndTime(String str, long j);

    void setTitleChangedListener(HeaderView.OnTitleChangedListener onTitleChangedListener);

    void setTitleTime(long j);

    void setToolBarHeight(int i);

    void showLoadingDialog();

    void startAiFormClassification(boolean z, String str);

    void updateBackgroundMargin(float f);
}
