package com.miui.handwriting.common.view.handview;

import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import com.miui.handwriting.common.ai.AiCreationHelper;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.view.thumbnail.ThumbnailRecyclerManager;
import com.miui.libhandwritingpage.listener.PreviewListener;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseMiuiHandWritingView implements ICommonMiuiHandWritingView {
    public abstract void allowBackByAI();

    public abstract void bindPageId(long j);

    public abstract void dismissAIPop();

    public abstract void enableAllPageAI(boolean z);

    public abstract AiCreationHelper getAiCreationHelper();

    public abstract ThumbnailRecyclerManager getThumbnailManager();

    public abstract void init();

    public abstract View initView(AttributeSet attributeSet, int i, IMiuiHandWritingModel iMiuiHandWritingModel);

    public abstract boolean isPreventBackByAI();

    public abstract void onBgColorItemUpdated(int i, int i2);

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onPreviewItemUpdated(int i, PagePreviewData pagePreviewData);

    public abstract void onPreviewUpdate(List<PagePreviewData> list);

    public abstract void preventBackByAI();

    public abstract void setToolBoxPreviewListener(PreviewListener previewListener);

    public abstract void showRuler(boolean z);
}
