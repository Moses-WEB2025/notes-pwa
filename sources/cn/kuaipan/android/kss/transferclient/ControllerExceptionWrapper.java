package cn.kuaipan.android.kss.transferclient;

/* JADX INFO: loaded from: classes.dex */
public class ControllerExceptionWrapper extends Exception {
    private ControllerExceptionWrapper(Throwable th) {
        super(th);
    }

    public static ControllerExceptionWrapper wrap(Throwable th) {
        return new ControllerExceptionWrapper(th);
    }
}
