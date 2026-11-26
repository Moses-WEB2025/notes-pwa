package com.xiaomi.mirror.synergy;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import java.lang.ref.SoftReference;

/* JADX INFO: loaded from: classes4.dex */
public class RelayIconHelper implements IBinder.DeathRecipient {
    public IBinder mAliveBinder;
    public volatile boolean mIsRegister = false;
    public SoftReference<RelayIconCallback> mRefCallback;
    public ContentObserver mUpdateObserver;
    public ContentObserver mVisibleObserver;
    public static final Uri URI_VISIBLE = MiuiSynergySdk.getUriFor("mirror_relay_icon_visible");
    public static final Uri URI_UPDATE = MiuiSynergySdk.getUriFor("mirror_relay_icon_update");

    public static class RelayIcon {
        public final Bitmap bitmap;
        public final String description;
        public final boolean isSupportHandoff;
        public final String packageName;
        public final String remoteDeviceId;
        public final String remoteDeviceName;
        public final int remoteDeviceType;

        public RelayIcon(String str, Bitmap bitmap, String str2, String str3, int i, String str4, boolean z) {
            this.packageName = str;
            this.bitmap = bitmap;
            this.description = str2;
            this.remoteDeviceName = str3;
            this.remoteDeviceType = i;
            this.remoteDeviceId = str4;
            this.isSupportHandoff = z;
        }

        public Bitmap getBitmap() {
            return this.bitmap;
        }

        public String getDescription() {
            return this.description;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public String getRemoteDeviceId() {
            return this.remoteDeviceId;
        }

        public String getRemoteDeviceName() {
            return this.remoteDeviceName;
        }

        public int getRemoteDeviceType() {
            return this.remoteDeviceType;
        }

        public boolean isSupportHandoff() {
            return this.isSupportHandoff;
        }
    }

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

    /* JADX INFO: Access modifiers changed from: private */
    public RelayIcon getRelayIcon(Context context, Uri uri) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(CallMethod.ARG_URI, uri);
        try {
            Bundle bundleDoCall = CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_GET_UPDATE_ICON, null, bundle);
            if (bundleDoCall == null) {
                return null;
            }
            return new RelayIcon(bundleDoCall.getString("value"), (Bitmap) bundleDoCall.getParcelable(CallMethod.RESULT_ICON), bundleDoCall.getString("description"), bundleDoCall.getString(CallMethod.RESULT_REMOTE_DEVICE_NAME), bundleDoCall.getInt(CallMethod.RESULT_REMOTE_DEVICE_TYPE), bundleDoCall.getString(CallMethod.RESULT_REMOTE_DEVICE_ID), Boolean.valueOf(bundleDoCall.getBoolean(CallMethod.RESULT_SUPPORT_HANDOFF, false)).booleanValue());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void notifyUpdateIcon(Context context) {
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_NOTIFY_UPDATE_ICON, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performRelayIconClick(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(CallMethod.ARG_EXTRA_STRING, str);
        try {
            CallMethod.doCall(context.getContentResolver(), CallMethod.METHOD_PERFORM_RELAY_ICON_CLICK, null, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        SoftReference<RelayIconCallback> softReference = this.mRefCallback;
        if (softReference != null && softReference.get() != null) {
            this.mRefCallback.get().onIconHide();
        }
        IBinder iBinder = this.mAliveBinder;
        if (iBinder != null) {
            iBinder.unlinkToDeath(this, 0);
            this.mAliveBinder = null;
        }
    }

    public void performIconClick(Context context, String str) {
        performRelayIconClick(context, str);
    }

    public void registerCallback(final Context context, RelayIconCallback relayIconCallback, Handler handler) {
        if (relayIconCallback == null) {
            this.mRefCallback = null;
            return;
        }
        this.mRefCallback = new SoftReference<>(relayIconCallback);
        this.mIsRegister = true;
        this.mVisibleObserver = new ContentObserver(handler) { // from class: com.xiaomi.mirror.synergy.RelayIconHelper.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if ((RelayIconHelper.this.mIsRegister & (RelayIconHelper.this.mRefCallback != null)) && RelayIconHelper.this.mRefCallback.get() != null) {
                    if (MiuiSynergySdk.getInstance().getInt(context, RelayIconHelper.URI_VISIBLE, 0) != 0) {
                        ((RelayIconCallback) RelayIconHelper.this.mRefCallback.get()).onIconShow();
                    } else {
                        ((RelayIconCallback) RelayIconHelper.this.mRefCallback.get()).onIconHide();
                    }
                }
                RelayIconHelper.this.checkRemoteProcess(context);
            }
        };
        context.getContentResolver().registerContentObserver(URI_VISIBLE, false, this.mVisibleObserver);
        this.mUpdateObserver = new ContentObserver(handler) { // from class: com.xiaomi.mirror.synergy.RelayIconHelper.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                RelayIcon relayIcon;
                if (!RelayIconHelper.this.mIsRegister || RelayIconHelper.this.mRefCallback == null || RelayIconHelper.this.mRefCallback.get() == null || (relayIcon = RelayIconHelper.this.getRelayIcon(context, RelayIconHelper.URI_UPDATE)) == null) {
                    return;
                }
                ((RelayIconCallback) RelayIconHelper.this.mRefCallback.get()).onIconUpdate(relayIcon.getPackageName(), relayIcon.getBitmap(), relayIcon.getDescription(), relayIcon.getRemoteDeviceName(), relayIcon.getRemoteDeviceType(), relayIcon.getRemoteDeviceId(), relayIcon.isSupportHandoff());
            }
        };
        context.getContentResolver().registerContentObserver(URI_UPDATE, false, this.mUpdateObserver);
        checkRemoteProcess(context);
        notifyUpdateIcon(context);
    }

    public void unRegisterCallback(Context context) {
        this.mIsRegister = false;
        if (this.mVisibleObserver != null) {
            context.getContentResolver().unregisterContentObserver(this.mVisibleObserver);
        }
        if (this.mUpdateObserver != null) {
            context.getContentResolver().unregisterContentObserver(this.mUpdateObserver);
        }
        IBinder iBinder = this.mAliveBinder;
        if (iBinder != null) {
            iBinder.unlinkToDeath(this, 0);
            this.mAliveBinder = null;
        }
    }
}
