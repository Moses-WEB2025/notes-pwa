package cn.kuaipan.kss.implement;

import cn.kuaipan.kss.KssDef;
import cn.kuaipan.kss.KssUpload;

/* JADX INFO: compiled from: HttpClientUpload.java */
/* JADX INFO: loaded from: classes.dex */
class _UploadProgress {
    private KssDef.OnUpDownload m_onUpload = null;
    private boolean m_isCanceled = false;
    private int m_transSize = 0;

    _UploadProgress() {
    }

    public void init(KssUpload.RequestUploadInfo requestUploadInfo, KssDef.OnUpDownload onUpDownload) {
        if (requestUploadInfo == null) {
            return;
        }
        this.m_onUpload = onUpDownload;
    }

    public boolean TransBytes(int i) {
        if (i > 0) {
            int i2 = this.m_transSize + i;
            this.m_transSize = i2;
            KssDef.OnUpDownload onUpDownload = this.m_onUpload;
            if (onUpDownload != null) {
                this.m_isCanceled = onUpDownload.OnTransData(i2);
            }
        }
        return this.m_isCanceled;
    }

    public boolean getIsCanceled() {
        return this.m_isCanceled;
    }

    public void setIsCanceled(boolean z) {
        this.m_isCanceled = z;
    }
}
