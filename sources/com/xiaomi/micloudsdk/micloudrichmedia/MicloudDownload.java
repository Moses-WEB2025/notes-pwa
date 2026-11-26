package com.xiaomi.micloudsdk.micloudrichmedia;

import cn.kuaipan.kss.KssDef;

/* JADX INFO: loaded from: classes4.dex */
public class MicloudDownload {

    public enum Result {
        OK,
        Error,
        Cancel,
        NetTimeout,
        NeedRequest,
        DataCorrupted,
        SpaceOver,
        ServerDenyReadOnly,
        Unsupported
    }

    /* JADX INFO: renamed from: com.xiaomi.micloudsdk.micloudrichmedia.MicloudDownload$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult;

        static {
            int[] iArr = new int[KssDef.KssAPIResult.values().length];
            $SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult = iArr;
            try {
                iArr[KssDef.KssAPIResult.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult[KssDef.KssAPIResult.Error.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult[KssDef.KssAPIResult.Cancel.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult[KssDef.KssAPIResult.NetTimeout.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult[KssDef.KssAPIResult.NeedRequest.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult[KssDef.KssAPIResult.DataCorrupted.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult[KssDef.KssAPIResult.SpaceOver.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult[KssDef.KssAPIResult.ServerDenyReadOnly.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public static Result getResult(KssDef.KssAPIResult kssAPIResult) {
        switch (AnonymousClass1.$SwitchMap$cn$kuaipan$kss$KssDef$KssAPIResult[kssAPIResult.ordinal()]) {
            case 1:
                return Result.OK;
            case 2:
                return Result.Error;
            case 3:
                return Result.Cancel;
            case 4:
                return Result.NetTimeout;
            case 5:
                return Result.NeedRequest;
            case 6:
                return Result.DataCorrupted;
            case 7:
                return Result.SpaceOver;
            case 8:
                return Result.ServerDenyReadOnly;
            default:
                return Result.Error;
        }
    }
}
