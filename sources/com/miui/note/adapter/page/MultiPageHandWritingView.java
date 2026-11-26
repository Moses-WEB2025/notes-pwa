package com.miui.note.adapter.page;

import android.content.Context;
import android.graphics.Path;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.miui.handwritecommon.api.callback.ExportListener;
import com.miui.handwritecommon.api.callback.NotaDataSaveListener;
import com.miui.handwritecommon.api.callback.NoteDataLoadListener;
import com.miui.handwritecommon.api.multipage.IMultiPageHandView;
import com.miui.handwritecommon.api.multipage.IMultiPageLayoutListener;
import com.miui.handwritecommon.api.multipage.IMultiPagePreviewClickListener;
import com.miui.handwriting.common.MiuiHandWritingView;
import com.miui.handwriting.common.data.bean.CommonPageBean;
import com.miui.handwriting.common.listener.CommonExportProcessListener;
import com.miui.handwriting.common.listener.CommonPageLoadListener;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.listener.CommonPreviewClickListener;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class MultiPageHandWritingView extends MiuiHandWritingView implements IMultiPageHandView {
    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public ViewGroup getViewContainer() {
        return this;
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void setAutoOptimizeHandwrite() {
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void setMultiPageLayoutListener(IMultiPageLayoutListener iMultiPageLayoutListener) {
    }

    public MultiPageHandWritingView(Context context) {
        super(context);
    }

    public MultiPageHandWritingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MultiPageHandWritingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void create(String str, final NoteDataLoadListener noteDataLoadListener) {
        CommonPageBean commonPageBean = new CommonPageBean();
        commonPageBean.width = 755;
        commonPageBean.height = 1156;
        commonPageBean.path = str;
        super.create(commonPageBean, new CommonPageLoadListener() { // from class: com.miui.note.adapter.page.MultiPageHandWritingView.1
            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPageCount(int i) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPdfError(int i) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onLoadCompleted(boolean z, boolean z2, String str2) {
                noteDataLoadListener.onLoadComplete(z);
            }
        }, false);
        noteDataLoadListener.onLoadComplete(false);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void load(String str, final NoteDataLoadListener noteDataLoadListener) {
        super.load(str + "/multi_page.ent", new CommonPageLoadListener() { // from class: com.miui.note.adapter.page.MultiPageHandWritingView.2
            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPageCount(int i) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onPdfError(int i) {
            }

            @Override // com.miui.handwriting.common.listener.CommonPageLoadListener
            public void onLoadCompleted(boolean z, boolean z2, String str2) {
                noteDataLoadListener.onLoadComplete(z);
            }
        });
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void save(String str, final NotaDataSaveListener notaDataSaveListener) {
        super.save(new CommonPageSaveListener() { // from class: com.miui.note.adapter.page.MultiPageHandWritingView.3
            @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
            public void onDataSaved(boolean z, String str2, boolean z2) {
                notaDataSaveListener.onSaveComplete(z);
            }
        });
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void addImage(Uri uri) {
        super.addImage(uri, 1);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void setToolBoxViewTopPadding(int i) {
        super.setToolBoxViewTopPadding(i, 1);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void clear() {
        super.onStepChanged(3, false, false);
    }

    @Override // com.miui.handwriting.common.MiuiHandWritingView, com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void clearTitleCursor() {
        super.clearTitleCursor();
    }

    @Override // com.miui.handwriting.common.MiuiHandWritingView, com.miui.handwriting.common.view.handview.ICommonMiuiHandWritingView
    public void setTitleTime(long j) {
        super.setTitleTime(j);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void setTitle(String str) {
        super.setTitle(str, 1);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public String getTitle() {
        return super.getTitle(1);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public boolean isEmpty() {
        return super.isContentEmpty();
    }

    @Override // com.miui.handwriting.common.MiuiHandWritingView, com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setCanWrite(boolean z) {
        super.setCanWrite(z);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void isHeaderViewShow(boolean z) {
        super.setHeaderViewShow(z);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void setPreviewOnClick(final IMultiPagePreviewClickListener iMultiPagePreviewClickListener) {
        Objects.requireNonNull(iMultiPagePreviewClickListener);
        super.setPreviewOnClick(new CommonPreviewClickListener() { // from class: com.miui.note.adapter.page.MultiPageHandWritingView$$ExternalSyntheticLambda0
            @Override // com.miui.handwriting.common.listener.CommonPreviewClickListener
            public final void onClick() {
                iMultiPagePreviewClickListener.onClick();
            }
        });
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public boolean isChanged() {
        return super.isContentChanged();
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public List<String> getImagesPath() {
        return super.getImagesPaths();
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void exportImage(final ExportListener<String> exportListener) {
        super.exportImage(new CommonExportProcessListener<String>() { // from class: com.miui.note.adapter.page.MultiPageHandWritingView.4
            @Override // com.miui.handwriting.common.listener.CommonExportProcessListener
            public void exportStart() {
                exportListener.exportStart();
            }

            @Override // com.miui.handwriting.common.listener.CommonExportProcessListener
            public void exportFinish(Boolean bool, List<String> list) {
                exportListener.exportFinish(bool.booleanValue(), list);
            }
        });
    }

    @Override // com.miui.handwriting.common.MiuiHandWritingView, com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public String saveThumbnail(String str) {
        return super.saveThumbnail(str);
    }

    @Override // com.miui.handwriting.common.MiuiHandWritingView, com.miui.handwriting.common.model.IMiuiHandWritingCommonModel
    public void setSearchPathArray(int[] iArr, List<List<Path>> list) {
        super.setSearchPathArray(iArr, list);
    }

    @Override // com.miui.handwritecommon.api.multipage.IMultiPageHandView
    public void setInnerLoadingVisible(boolean z) {
        if (z) {
            super.showLoadingDialog();
        } else {
            super.dismissLoadingDialog();
        }
    }
}
