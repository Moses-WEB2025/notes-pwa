package com.miui.handwritecommon.api.multipage;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: IMultiPageLayoutListener.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\b\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0003H&J\u0012\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bH&J\b\u0010\f\u001a\u00020\u0003H&J\b\u0010\r\u001a\u00020\u0003H&¨\u0006\u000fÀ\u0006\u0003"}, d2 = {"Lcom/miui/handwritecommon/api/multipage/IMultiPageLayoutListener;", "", "onError", "", "errorType", "", "onSelectImage", "onShare", "onBtnSave", "onTitleChange", "title", "", "onInsertImageClick", "onContentChange", "Companion", "libHandWriteCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IMultiPageLayoutListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void onBtnSave();

    void onContentChange();

    void onError(int errorType);

    void onInsertImageClick();

    void onSelectImage();

    void onShare();

    void onTitleChange(String title);

    /* JADX INFO: compiled from: IMultiPageLayoutListener.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007R\u0011\u0010\f\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007R\u0011\u0010\u000e\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0007R\u0011\u0010\u0010\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/miui/handwritecommon/api/multipage/IMultiPageLayoutListener$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "ERROR_ENT_DIR_IS_EMPTY", "", "getERROR_ENT_DIR_IS_EMPTY", "()I", "ERROR_ENT_FILE_NOT_EXIST", "getERROR_ENT_FILE_NOT_EXIST", "ERROR_ENT_FILE_LOAD_FAIL", "getERROR_ENT_FILE_LOAD_FAIL", "ERROR_PDF_FILE_PASSWORD", "getERROR_PDF_FILE_PASSWORD", "ERROR_ENT_FILE_PDF_LOAD_FAIL", "getERROR_ENT_FILE_PDF_LOAD_FAIL", "ERROR_OTHER", "getERROR_OTHER", "libHandWriteCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public final int getERROR_ENT_DIR_IS_EMPTY() {
            return 1;
        }

        public final int getERROR_ENT_FILE_LOAD_FAIL() {
            return 3;
        }

        public final int getERROR_ENT_FILE_NOT_EXIST() {
            return 2;
        }

        public final int getERROR_ENT_FILE_PDF_LOAD_FAIL() {
            return 5;
        }

        public final int getERROR_OTHER() {
            return 8;
        }

        public final int getERROR_PDF_FILE_PASSWORD() {
            return 4;
        }

        private Companion() {
        }
    }
}
