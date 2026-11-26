package com.xiaomi.mirror.synergy;

import com.xiaomi.mirror.MirrorDesktopInfo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface MirrorDesktopCallback {
    void onDeviceListUpdate(List<RemoteDeviceInfo> list);

    void onMirrorDesktopConfigChanged(MirrorDesktopInfo mirrorDesktopInfo);

    void onMirrorDesktopHide();

    void onMirrorDesktopShow(MirrorDesktopInfo mirrorDesktopInfo);

    void onMirrorSeatChanged(int i);
}
