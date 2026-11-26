package cn.kuaipan.android.kss.transferclient.controller;

import cn.kuaipan.android.kss.transferclient.exception.SFSNetworkNotAvailableException;

/* JADX INFO: loaded from: classes.dex */
public interface TransferController {
    void checkNetwork() throws SFSNetworkNotAvailableException;
}
