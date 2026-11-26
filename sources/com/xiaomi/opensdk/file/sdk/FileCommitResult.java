package com.xiaomi.opensdk.file.sdk;

import cn.kuaipan.android.exception.KscException;
import cn.kuaipan.android.utils.ApiDataHelper;
import java.util.Map;
import java.util.zip.DataFormatException;

/* JADX INFO: loaded from: classes4.dex */
public class FileCommitResult implements ApiDataHelper.IKscData {
    public static final ApiDataHelper.IKscData.Parser<FileCommitResult> PARSER = new ApiDataHelper.IKscData.Parser<FileCommitResult>() { // from class: com.xiaomi.opensdk.file.sdk.FileCommitResult.1
        @Override // cn.kuaipan.android.utils.ApiDataHelper.IKscData.Parser
        public /* bridge */ /* synthetic */ ApiDataHelper.IKscData parserMap(Map map, String[] strArr) throws DataFormatException, KscException {
            return parserMap((Map<String, Object>) map, strArr);
        }

        @Override // cn.kuaipan.android.utils.ApiDataHelper.IKscData.Parser
        public FileCommitResult parserMap(Map<String, Object> map, String... strArr) throws DataFormatException, KscException {
            return new FileCommitResult(map);
        }
    };
    public final String stat;

    private FileCommitResult(Map<String, Object> map) {
        this.stat = ApiDataHelper.asString(map, "stat");
    }
}
