package com.miui.handwritecommon.api.multipage;

import android.graphics.Path;
import android.net.Uri;
import android.view.ViewGroup;
import com.miui.handwritecommon.api.callback.ExportListener;
import com.miui.handwritecommon.api.callback.NotaDataSaveListener;
import com.miui.handwritecommon.api.callback.NoteDataLoadListener;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface IMultiPageHandView {
    void addImage(Uri uri);

    void clear();

    void clearTitleCursor();

    void create(String str, NoteDataLoadListener noteDataLoadListener);

    void exportImage(ExportListener<String> exportListener);

    List<String> getImagesPath();

    String getTitle();

    ViewGroup getViewContainer();

    boolean isChanged();

    boolean isEmpty();

    void isHeaderViewShow(boolean z);

    void load(String str, NoteDataLoadListener noteDataLoadListener);

    void release();

    void save(String str, NotaDataSaveListener notaDataSaveListener);

    String saveThumbnail(String str);

    void setAutoOptimizeHandwrite();

    void setCanWrite(boolean z);

    void setInnerLoadingVisible(boolean z);

    void setMultiPageLayoutListener(IMultiPageLayoutListener iMultiPageLayoutListener);

    void setPreviewOnClick(IMultiPagePreviewClickListener iMultiPagePreviewClickListener);

    void setSearchPathArray(int[] iArr, List<List<Path>> list);

    void setTitle(String str);

    void setTitleTime(long j);

    void setToolBoxViewTopPadding(int i);
}
