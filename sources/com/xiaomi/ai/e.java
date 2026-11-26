package com.xiaomi.ai;

import com.xiaomi.ai.utils.UploadHelper;

/* JADX INFO: loaded from: classes4.dex */
class e implements Runnable {
    final /* synthetic */ SendWakeupDataStatusInterface a;
    final /* synthetic */ UploadHelper.ASRRecordInfo b;
    final /* synthetic */ byte[] c;
    final /* synthetic */ c d;

    e(c cVar, SendWakeupDataStatusInterface sendWakeupDataStatusInterface, UploadHelper.ASRRecordInfo aSRRecordInfo, byte[] bArr) {
        this.d = cVar;
        this.a = sendWakeupDataStatusInterface;
        this.b = aSRRecordInfo;
        this.c = bArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        UploadHelper uploadHelper = this.d.getUploadHelper(this.a);
        if (uploadHelper == null) {
            this.a.onSendError("getUploadHelper Error");
            return;
        }
        String strGenerateASRRecordMessage = UploadHelper.generateASRRecordMessage(this.d.mContext, this.b);
        if (strGenerateASRRecordMessage == null) {
            this.a.onSendError("getUploadHelper gen asr msg Error");
            return;
        }
        uploadHelper.addUploadData(strGenerateASRRecordMessage.getBytes(), true);
        uploadHelper.addUploadData(this.c, false);
        uploadHelper.endUploadData();
    }
}
