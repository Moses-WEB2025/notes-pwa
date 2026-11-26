package com.miui.note.adapter.page;

import android.content.Context;
import android.graphics.Path;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.miui.handwritecommon.api.bean.WebImage;
import com.miui.handwritecommon.api.callback.ExportListener;
import com.miui.handwritecommon.api.callback.NotaDataSaveListener;
import com.miui.handwritecommon.api.callback.NoteDataLoadListener;
import com.miui.handwritecommon.api.singlepage.ISinglePageHandView;
import com.miui.handwritecommon.api.singlepage.ISinglePageLayoutListener;
import com.miui.handwriting.common.MiuiHandWritingView;
import com.miui.handwriting.common.listener.CommonExportProcessListener;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class SinglePageHandWritingView extends MiuiHandWritingView implements ISinglePageHandView {
    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void autoSave(String str) {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public float getContentRangBottom() {
        return 0.0f;
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public ViewGroup getViewContainer() {
        return this;
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void hideLoading() {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void hideToolBoxView() {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void load(String str, NoteDataLoadListener noteDataLoadListener) {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void save(String str, NotaDataSaveListener notaDataSaveListener) {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public WebImage saveWebImage() {
        return null;
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void setAutoOptimizeHandwrite() {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void setBackgroundIndex(int i) {
    }

    @Override // com.miui.handwriting.common.MiuiHandWritingView, com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setCanWrite(boolean z) {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void setPreviewOnClick(View.OnClickListener onClickListener) {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void setSingePageOptionListener(ISinglePageLayoutListener iSinglePageLayoutListener) {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void setTitleTime(String str, String str2) {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void setToHead() {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void setToolBoxBackgroundIndex(int i) {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void showLoading(int i) {
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void showToolBoxView() {
    }

    public SinglePageHandWritingView(Context context) {
        super(context);
    }

    public SinglePageHandWritingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SinglePageHandWritingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.handwriting.common.MiuiHandWritingView, com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public String saveThumbnail() {
        return super.saveThumbnail();
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void addImage(Uri uri) {
        super.addImage(uri, 0);
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void setToolBoxViewTopPadding(int i) {
        super.setToolBoxViewTopPadding(i, 0);
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public boolean isDataChanged() {
        return super.isContentChanged();
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public int getCurrentBackgroundIndex() {
        return super.getCurrentPosition();
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public String getTitle() {
        return super.getTitle(0);
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void resetDataChangedState() {
        super.onStepChanged(3, false, false);
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public boolean isEmpty() {
        return super.isContentEmpty();
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public List<String> getBitmapsPath(String str) {
        return super.getImagesPaths();
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void clear() {
        super.onStepChanged(3, false, false);
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void exportImage(final ExportListener<Uri> exportListener) {
        super.exportImage(new CommonExportProcessListener<String>() { // from class: com.miui.note.adapter.page.SinglePageHandWritingView.1
            @Override // com.miui.handwriting.common.listener.CommonExportProcessListener
            public void exportFinish(Boolean bool, List<String> list) {
            }

            @Override // com.miui.handwriting.common.listener.CommonExportProcessListener
            public void exportStart() {
                exportListener.exportStart();
            }
        });
    }

    @Override // com.miui.handwritecommon.api.singlepage.ISinglePageHandView
    public void setTitle(String str) {
        super.setTitle(str, 0);
    }

    @Override // com.miui.handwriting.common.MiuiHandWritingView, com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSearchPath(List<Path> list) {
        super.setSearchPath(list);
    }
}
