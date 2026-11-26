package com.miui.libhandwritingpage.utils;

import android.content.Context;
import android.text.TextUtils;
import com.miui.libhandwritingpage.newbean.beanPageInfo.PageData;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import xc.engine.data.CsBufferBean;
import xc.engine.data.DataInterfaceSet;
import xc.engine.data.IDataSwapListener;
import xc.engine.data.PathInfo;
import xc.engine.utils.FileUtil;
import xc.richtext.data.SpannedData;
import xc.richtext.data.SpannedMap;

/* JADX INFO: loaded from: classes3.dex */
public class PageEntUtil {
    private final SpannedMap absSpannedMap = SpannedMap.createInstance();
    private List<PageData> pageDataList = new ArrayList();

    public void loadPageEnt(String str, Context context) {
        String entFilePath = getEntFilePath(str);
        if (TextUtils.isEmpty(entFilePath)) {
            return;
        }
        List<PageData> itemList = PageDataUtils.parseItemList(entFilePath, FileUtil.getDir(context, FileUtil.PAGE_EXPORT));
        this.pageDataList = itemList;
        for (PageData pageData : itemList) {
            pageData.bgImagePathList = new ArrayList();
            pageData.bitmapPathList = new ArrayList();
            if (!TextUtils.isEmpty(pageData.bgImage)) {
                pageData.bgImagePathList.add(str + File.separator + pageData.bgImage);
            }
            if (!TextUtils.isEmpty(pageData.pdfId)) {
                pageData.pdfPath = str + File.separator + pageData.pdfId;
            }
            loadEnt(str, pageData, this.pageDataList.indexOf(pageData));
        }
    }

    public void loadEnt(final String str, final PageData pageData, int i) {
        DataInterfaceSet dataInterfaceSet = new DataInterfaceSet();
        dataInterfaceSet.setDataSwapListener(new IDataSwapListener() { // from class: com.miui.libhandwritingpage.utils.PageEntUtil.1
            @Override // xc.engine.data.IDataSwapListener
            public CsBufferBean onSaveListener(int i2) {
                return null;
            }

            @Override // xc.engine.data.IDataSwapListener
            public void onLoadListener(int i2, int[] iArr, int i3, List<byte[]> list) {
                if (iArr == null || iArr.length == 0) {
                    return;
                }
                PageEntUtil.this.absSpannedMap.clear();
                PageEntUtil.this.absSpannedMap.loadTextFromBuffer(pageData.inkPath, i3, iArr, list);
                Iterator<SpannedData> it = PageEntUtil.this.absSpannedMap.getDataList(7).iterator();
                while (it.hasNext()) {
                    pageData.bitmapPathList.add(str + File.separator + new File(it.next().getBitmapPath()).getName());
                }
            }
        });
        PathInfo.loadEntFile(pageData.inkPath, dataInterfaceSet, i);
    }

    private String getEntFilePath(String str) {
        String[] list = new File(str).list();
        if (!TextUtils.isEmpty(str) && list != null) {
            for (String str2 : list) {
                int iLastIndexOf = str2.lastIndexOf(".");
                int iLastIndexOf2 = str2.lastIndexOf(File.separator);
                if (iLastIndexOf != -1 && iLastIndexOf2 < iLastIndexOf && "ent".equals(str2.substring(iLastIndexOf + 1))) {
                    return str + File.separator + str2;
                }
            }
        }
        return null;
    }

    public List<PageData> getPageDataList() {
        return this.pageDataList;
    }
}
