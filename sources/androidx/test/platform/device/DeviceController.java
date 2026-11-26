package androidx.test.platform.device;

/* JADX INFO: loaded from: classes.dex */
public interface DeviceController {

    public enum ScreenOrientation {
        PORTRAIT,
        LANDSCAPE
    }

    void setDeviceMode(int deviceMode);

    void setScreenOrientation(int screenOrientation);
}
