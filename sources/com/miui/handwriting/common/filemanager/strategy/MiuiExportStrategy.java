package com.miui.handwriting.common.filemanager.strategy;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import ch.qos.logback.core.CoreConstants;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.data.bean.ExportSaveBean;
import com.miui.handwriting.common.filemanager.base.BaseExport;
import com.miui.handwriting.common.filemanager.base.ExportStrategy;
import com.miui.handwriting.common.listener.CommonPageSaveListener;
import com.miui.handwriting.common.model.IMiuiHandWritingModel;
import com.miui.handwriting.common.tools.constants.Constants;
import com.miui.libhandwritingpage.listener.PageSaveListener;
import com.miui.libhandwritingpage.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes3.dex */
public class MiuiExportStrategy extends BaseExport implements ExportStrategy {
    public boolean isComplete;
    public List<Integer> mExportPageIndexList;
    public ExportSaveBean mExportSaveBean;
    public String mFileTitle;

    public void setFileTitle(String str) {
        this.mFileTitle = str.substring(0, Math.min(str.length(), 80));
    }

    public void setExportPageIndexList(List<Integer> list) {
        this.mExportPageIndexList = list;
    }

    public void initState() {
        this.isComplete = false;
        this.mExportSaveBean = new ExportSaveBean();
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    public ExportSaveBean getExportSaveBean() {
        return this.mExportSaveBean;
    }

    @Override // com.miui.handwriting.common.filemanager.base.ExportStrategy
    public void exportPdf() {
        export(getDirPath(), getEntFilePath(), Utils.obtainDownloadFileDir(this.context).getAbsolutePath(), this.mFileTitle + ".pdf", false, 1);
    }

    @Override // com.miui.handwriting.common.filemanager.base.ExportStrategy
    public void exportImage() {
        export(getDirPath(), getEntFilePath(), Utils.obtainCameraFileDir(this.context).getAbsolutePath(), this.mFileTitle, true, 2);
    }

    public static void save(IMiuiHandWritingModel iMiuiHandWritingModel, final PageSaveListener pageSaveListener) {
        iMiuiHandWritingModel.saveEntFile(new CommonPageSaveListener() { // from class: com.miui.handwriting.common.filemanager.strategy.MiuiExportStrategy$$ExternalSyntheticLambda0
            @Override // com.miui.handwriting.common.listener.CommonPageSaveListener
            public final void onDataSaved(boolean z, String str, boolean z2) {
                MiuiExportStrategy.lambda$save$0(pageSaveListener, z, str, z2);
            }
        });
    }

    static /* synthetic */ void lambda$save$0(PageSaveListener pageSaveListener, boolean z, String str, boolean z2) {
        if (pageSaveListener != null) {
            pageSaveListener.onDataSaved(z, str, z2);
        }
    }

    private void export(String str, String str2, String str3, String str4, boolean z, int i) {
        Intent intent = new Intent();
        intent.setAction(Constants.OPERATION_INTENT);
        intent.putExtra("operationType", "export");
        intent.putExtra("dirPath", str);
        intent.putExtra("entFilePath", str2);
        intent.putExtra("folderPath", str3);
        intent.putExtra("folderName", filterFolderName(str4));
        intent.putExtra("isDir", z);
        intent.putExtra("exportType", i);
        intent.putIntegerArrayListExtra("exportPageIndexList", (ArrayList) this.mExportPageIndexList);
        LocalBroadcastManager.getInstance(HandWritingViewApplication.INSTANCE.getApplication()).sendBroadcast(intent);
    }

    private String filterFolderName(String str) {
        if (str == null) {
            return "";
        }
        return str.replace('\"', '_').replace('/', '_').replace(CoreConstants.ESCAPE_CHAR, '_').replace(CoreConstants.COLON_CHAR, '_').replace('*', '_').replace('?', '_').replace(Typography.less, '_').replace(Typography.greater, '_').replace('|', '_');
    }

    public void onProgress(int i) {
        ExportSaveBean exportSaveBean = this.mExportSaveBean;
        if (exportSaveBean != null) {
            exportSaveBean.setProgress(i);
        }
        if (getExportCallback() != null) {
            getExportCallback().onProgress(i);
        }
    }

    public void completeExport(boolean z, String str, int i) {
        File[] fileArrListFiles;
        if (!z || str == null) {
            if (getExportCallback() != null) {
                this.isComplete = true;
                this.mExportSaveBean.setData(i, z, null);
                getExportCallback().call(i, false, null);
                return;
            }
            return;
        }
        if (getExportCallback() != null) {
            ArrayList arrayList = new ArrayList();
            File file = new File(str);
            if (i != 2) {
                if (i == 1) {
                    arrayList.add(str);
                    this.isComplete = true;
                    this.mExportSaveBean.setData(i, z, arrayList);
                    getExportCallback().call(i, z, arrayList);
                    return;
                }
                return;
            }
            if (file.exists() && file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
                for (File file2 : fileArrListFiles) {
                    arrayList.add(file2.getAbsolutePath());
                }
            }
            this.isComplete = true;
            this.mExportSaveBean.setData(i, z, arrayList);
            getExportCallback().call(i, z, arrayList);
        }
    }
}
