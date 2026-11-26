package com.xiaomi.mirror.synergy;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import com.xiaomi.mirror.SystemSettingsUtils;
import java.lang.ref.SoftReference;

/* JADX INFO: loaded from: classes4.dex */
public class MirrorDesktopHelper implements IBinder.DeathRecipient {
    public IBinder mAliveBinder;
    public ContentObserver mConfigurationObserver;
    public ContentObserver mDesktopVisibilityObserver;
    public ContentObserver mDeviceUpdateObserver;
    public volatile boolean mIsRegister = false;
    public SoftReference<MirrorDesktopCallback> mMirrorDesktopCallback;
    public ContentObserver mSeatObserver;
    public static final Uri URI_MIRROR_DESKTOP_VISIBILITY = MiuiSynergySdk.getUriFor("mirror_desktop_device_visibility");
    public static final Uri URI_MIRROR_DEVICE_UPDATE = MiuiSynergySdk.getUriFor("mirror_device_update");
    public static final Uri URI_MIRROR_CONFIGURATION = MiuiSynergySdk.getUriFor("mirror_configuration");
    public static final Uri URI_MIRROR_SETTINGS_SWITCH = Settings.Secure.getUriFor(SystemSettingsUtils.KEY_MIRROR_SWITCH);
    public static final Uri URI_MIRROR_SETTINGS_SEAT = Settings.Secure.getUriFor(SystemSettingsUtils.KEY_MIRROR_SEAT);

    /* JADX INFO: Access modifiers changed from: private */
    public void checkRemoteProcess(Context context) {
        if (this.mAliveBinder != null) {
            return;
        }
        IBinder aliveBinder = MiuiSynergySdk.getInstance().getAliveBinder(context);
        this.mAliveBinder = aliveBinder;
        if (aliveBinder != null) {
            try {
                aliveBinder.linkToDeath(this, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        SoftReference<MirrorDesktopCallback> softReference = this.mMirrorDesktopCallback;
        if (softReference != null && softReference.get() != null) {
            this.mMirrorDesktopCallback.get().onDeviceListUpdate(null);
        }
        IBinder iBinder = this.mAliveBinder;
        if (iBinder != null) {
            iBinder.unlinkToDeath(this, 0);
            this.mAliveBinder = null;
        }
    }

    public void performExitMirrorClick(Context context) {
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_PERFORM_EXIT_MIRROR, null, new Bundle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void performMirrorDeviceIconClick(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_EXTRA_STRING, str);
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_PERFORM_MIRROR_DEVICE_ICON_CLICK, null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerMirrorDesktopCallback(final Context context, MirrorDesktopCallback mirrorDesktopCallback, Handler handler) {
        if (mirrorDesktopCallback == null) {
            this.mMirrorDesktopCallback = null;
            return;
        }
        this.mMirrorDesktopCallback = new SoftReference<>(mirrorDesktopCallback);
        this.mIsRegister = true;
        this.mDeviceUpdateObserver = new ContentObserver(handler) { // from class: com.xiaomi.mirror.synergy.MirrorDesktopHelper.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (MirrorDesktopHelper.this.mIsRegister && MirrorDesktopHelper.this.mMirrorDesktopCallback != null && MirrorDesktopHelper.this.mMirrorDesktopCallback.get() != null) {
                    ((MirrorDesktopCallback) MirrorDesktopHelper.this.mMirrorDesktopCallback.get()).onDeviceListUpdate(MiuiSynergySdk.getInstance().queryMirrorDesktopDevices(context));
                }
                MirrorDesktopHelper.this.checkRemoteProcess(context);
            }
        };
        context.getContentResolver().registerContentObserver(URI_MIRROR_DEVICE_UPDATE, false, this.mDeviceUpdateObserver);
        this.mDesktopVisibilityObserver = new ContentObserver(handler) { // from class: com.xiaomi.mirror.synergy.MirrorDesktopHelper.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (!MirrorDesktopHelper.this.mIsRegister || MirrorDesktopHelper.this.mMirrorDesktopCallback == null || MirrorDesktopHelper.this.mMirrorDesktopCallback.get() == null) {
                    return;
                }
                if (MiuiSynergySdk.getInstance().getInt(context, MirrorDesktopHelper.URI_MIRROR_DESKTOP_VISIBILITY, 0) == 0) {
                    ((MirrorDesktopCallback) MirrorDesktopHelper.this.mMirrorDesktopCallback.get()).onMirrorDesktopHide();
                } else {
                    ((MirrorDesktopCallback) MirrorDesktopHelper.this.mMirrorDesktopCallback.get()).onMirrorDesktopShow(MiuiSynergySdk.getInstance().queryMirrorDesktopInfo(context));
                }
            }
        };
        context.getContentResolver().registerContentObserver(URI_MIRROR_DESKTOP_VISIBILITY, false, this.mDesktopVisibilityObserver);
        this.mConfigurationObserver = new ContentObserver(handler) { // from class: com.xiaomi.mirror.synergy.MirrorDesktopHelper.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (!MirrorDesktopHelper.this.mIsRegister || MirrorDesktopHelper.this.mMirrorDesktopCallback == null || MirrorDesktopHelper.this.mMirrorDesktopCallback.get() == null) {
                    return;
                }
                ((MirrorDesktopCallback) MirrorDesktopHelper.this.mMirrorDesktopCallback.get()).onMirrorDesktopConfigChanged(MiuiSynergySdk.getInstance().queryMirrorDesktopInfo(context));
            }
        };
        context.getContentResolver().registerContentObserver(URI_MIRROR_CONFIGURATION, false, this.mConfigurationObserver);
        this.mSeatObserver = new ContentObserver(handler) { // from class: com.xiaomi.mirror.synergy.MirrorDesktopHelper.4
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (!MirrorDesktopHelper.this.mIsRegister || MirrorDesktopHelper.this.mMirrorDesktopCallback == null || MirrorDesktopHelper.this.mMirrorDesktopCallback.get() == null) {
                    return;
                }
                ((MirrorDesktopCallback) MirrorDesktopHelper.this.mMirrorDesktopCallback.get()).onMirrorSeatChanged(SystemSettingsUtils.getInt(context, SystemSettingsUtils.KEY_MIRROR_SEAT, 0));
            }
        };
        context.getContentResolver().registerContentObserver(URI_MIRROR_SETTINGS_SEAT, false, this.mSeatObserver);
        checkRemoteProcess(context);
    }

    public void unRegisterMirrorDesktopCallback(Context context) {
        this.mIsRegister = false;
        if (this.mDeviceUpdateObserver != null) {
            context.getContentResolver().unregisterContentObserver(this.mDeviceUpdateObserver);
        }
        if (this.mDesktopVisibilityObserver != null) {
            context.getContentResolver().unregisterContentObserver(this.mDesktopVisibilityObserver);
        }
        if (this.mConfigurationObserver != null) {
            context.getContentResolver().unregisterContentObserver(this.mConfigurationObserver);
        }
        IBinder iBinder = this.mAliveBinder;
        if (iBinder != null) {
            iBinder.unlinkToDeath(this, 0);
            this.mAliveBinder = null;
        }
    }
}
