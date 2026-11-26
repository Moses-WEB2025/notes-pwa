package cn.kuaipan.android.kss.transferclient.context;

import cn.kuaipan.android.kss.transferclient.controller.DownloadController;

/* JADX INFO: loaded from: classes.dex */
public class DownloadFileTransferContext extends FileTransferContext {
    public final DownloadController controller;

    public DownloadFileTransferContext(int i, DownloadController downloadController) {
        super(i);
        this.controller = downloadController;
    }
}
