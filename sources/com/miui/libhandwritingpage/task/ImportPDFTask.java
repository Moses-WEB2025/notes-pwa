package com.miui.libhandwritingpage.task;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.miui.libhandwritingpage.listener.PDFImportListener;
import com.miui.libhandwritingpage.newbean.BgGridConfiguration;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageItemData;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import xc.engine.storage.pdf.PDFManager;
import xc.engine.storage.pdf.PDFReader;

/* JADX INFO: loaded from: classes3.dex */
public class ImportPDFTask extends AsyncTask<Void, Void, List<PageItemData>> {
    private static final String TAG = "ImportPDFTask";
    private final String dirPath;
    private final PDFImportListener importListener;
    private int maxCount;
    private String password;
    private final String pdfId;
    private final PDFReader pdfReader;

    public ImportPDFTask(String str, String str2, int i, String str3, PDFImportListener pDFImportListener) {
        this.dirPath = str;
        this.pdfId = str2;
        this.importListener = pDFImportListener;
        this.maxCount = i;
        this.password = str3;
        this.pdfReader = PDFManager.getInstance().createReader(new File(str, str2).getAbsolutePath());
    }

    @Override // android.os.AsyncTask
    public List<PageItemData> doInBackground(Void... voidArr) {
        File file = new File(this.dirPath, this.pdfId);
        if (!TextUtils.isEmpty(this.password)) {
            try {
                PDDocument pDDocumentLoad = PDDocument.load(file, this.password);
                pDDocumentLoad.setAllSecurityToBeRemoved(true);
                pDDocumentLoad.save(file.getAbsolutePath());
                pDDocumentLoad.close();
            } catch (Exception unused) {
            }
        }
        ArrayList arrayList = new ArrayList();
        PDFReader pDFReader = this.pdfReader;
        if (pDFReader != null) {
            parsePdfReader(arrayList, pDFReader);
        }
        return arrayList;
    }

    private void parsePdfReader(List<PageItemData> list, PDFReader pDFReader) {
        PDFImportListener pDFImportListener;
        if (pDFReader != null) {
            try {
                if (pDFReader.load() != 0 && (pDFImportListener = this.importListener) != null) {
                    pDFImportListener.onPdfError(pDFReader.load());
                }
                int pageCount = pDFReader.getPageCount();
                PDFImportListener pDFImportListener2 = this.importListener;
                if (pDFImportListener2 != null) {
                    pDFImportListener2.onPdfPageCount(pageCount);
                }
                int i = this.maxCount;
                if (i > 0) {
                    pageCount = Math.min(pageCount, i);
                }
                for (int i2 = 0; i2 < pageCount; i2++) {
                    int[] pageSize = pDFReader.getPageSize(i2);
                    PageItemData pageItemData = new PageItemData(new BgGridConfiguration(), UUID.randomUUID().toString() + ".png", "", i2, "", 0, 0, -1, pageSize == null ? 0 : pageSize[0], pageSize == null ? 0 : pageSize[1], 0, this.pdfId);
                    list.add(pageItemData);
                    pageItemData.setPageIndex(list.size() - 1);
                }
                pDFReader.close();
            } catch (Exception unused) {
            }
        }
    }

    @Override // android.os.AsyncTask
    public void onPostExecute(List<PageItemData> list) {
        PDFImportListener pDFImportListener = this.importListener;
        if (pDFImportListener != null) {
            pDFImportListener.onImportCompleted(list);
        }
    }
}
