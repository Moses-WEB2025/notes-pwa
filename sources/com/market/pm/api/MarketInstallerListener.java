package com.market.pm.api;

/* JADX INFO: loaded from: classes2.dex */
public interface MarketInstallerListener extends IMarketInstallerCode {
    void onRefuseInstall(String str, int i);

    void onServiceDead();

    void packageInstalled(String str, int i);
}
