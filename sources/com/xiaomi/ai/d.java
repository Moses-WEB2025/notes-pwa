package com.xiaomi.ai;

import com.xiaomi.ai.utils.UploadHelper;

/* JADX INFO: loaded from: classes4.dex */
class d implements Runnable {
    final /* synthetic */ SendWakeupDataStatusInterface a;
    final /* synthetic */ UploadHelper.WakeupDataInfo b;
    final /* synthetic */ byte[] c;
    final /* synthetic */ c d;

    d(c cVar, SendWakeupDataStatusInterface sendWakeupDataStatusInterface, UploadHelper.WakeupDataInfo wakeupDataInfo, byte[] bArr) {
        this.d = cVar;
        this.a = sendWakeupDataStatusInterface;
        this.b = wakeupDataInfo;
        this.c = bArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        UploadHelper uploadHelper = this.d.getUploadHelper(this.a);
        if (uploadHelper == null) {
            this.a.onSendError("getUploadHelper Error");
            return;
        }
        String strGenerateWakeupDataMessage = UploadHelper.generateWakeupDataMessage(this.d.mContext, this.b);
        if (strGenerateWakeupDataMessage == null) {
            this.a.onSendError("getUploadHelper gen asr msg Error");
            return;
        }
        uploadHelper.addUploadData(strGenerateWakeupDataMessage.getBytes(), true);
        uploadHelper.addUploadData(this.c, false);
        uploadHelper.endUploadData();
    }
}
