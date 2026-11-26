package com.xiaomi.opensdk.file.sdk;

import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.kss.DownloadRequestResult;
import cn.kuaipan.android.utils.ApiDataHelper;
import java.util.Map;
import java.util.zip.DataFormatException;

/* JADX INFO: loaded from: classes4.dex */
public class FileDownloadRequestResult extends DownloadRequestResult implements ApiDataHelper.IKscData {
    public static final ApiDataHelper.IKscData.Parser<FileDownloadRequestResult> PARSER = new ApiDataHelper.IKscData.Parser<FileDownloadRequestResult>() { // from class: com.xiaomi.opensdk.file.sdk.FileDownloadRequestResult.1
        @Override // cn.kuaipan.android.utils.ApiDataHelper.IKscData.Parser
        public /* bridge */ /* synthetic */ ApiDataHelper.IKscData parserMap(Map map, String[] strArr) throws DataFormatException, KscException {
            return parserMap((Map<String, Object>) map, strArr);
        }

        @Override // cn.kuaipan.android.utils.ApiDataHelper.IKscData.Parser
        public FileDownloadRequestResult parserMap(Map<String, Object> map, String... strArr) throws DataFormatException, KscException {
            return new FileDownloadRequestResult(map);
        }
    };

    public FileDownloadRequestResult(Map<String, Object> map) throws KscException {
        super(map);
    }
}
