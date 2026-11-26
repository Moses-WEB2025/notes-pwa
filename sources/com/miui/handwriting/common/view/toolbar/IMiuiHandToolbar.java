package com.miui.handwriting.common.view.toolbar;

import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.view.handview.BaseMiuiHandWritingView;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PagePreviewData;
import java.util.List;
import xc.engine.utils.PageSizeConfig;

/* JADX INFO: loaded from: classes3.dex */
public interface IMiuiHandToolbar extends ICommonMiuiHandToolbar {
    void adjustPageSize(PageSizeConfig pageSizeConfig, PageSizeConfig pageSizeConfig2, boolean z);

    PageSizeConfig getPageSizeConfig(int i, boolean z);

    int getToolbarHeight();

    void initToolBox(IMiuiHandWritingModel iMiuiHandWritingModel, BaseMiuiHandWritingView baseMiuiHandWritingView);

    boolean isAISelect();

    boolean isFocus();

    void onPreviewItemUpdated(int i, PagePreviewData pagePreviewData);

    void onPreviewUpdate(List<PagePreviewData> list);

    @Override // com.miui.handwriting.common.view.toolbar.ICommonMiuiHandToolbar
    void onStepChanged(int i, boolean z, boolean z2);

    void release();

    void setCanWrite(boolean z);

    void setFocus(boolean z);
}
