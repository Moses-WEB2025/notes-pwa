package cn.kuaipan.android.kss.transferclient.context;

import cn.kuaipan.android.kss.transferclient.controller.UploadController;

/* JADX INFO: loaded from: classes.dex */
public class UploadFileTransferContext extends FileTransferContext {
    public final UploadController controller;

    public UploadFileTransferContext(int i, UploadController uploadController) {
        super(i);
        this.controller = uploadController;
    }
}
