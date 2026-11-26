package com.miui.notes.excerpt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public class NotifyUtils {
    public static final String KEY_NOTE_ID = "noteId";
    public static final String KEY_RESULT = "result";
    public static final String KEY_UUID = "uuid";
    private static WeakReference<Handler> mHandler;
    private static volatile NotifyUtils mSingleton;

    private NotifyUtils() {
    }

    public static NotifyUtils getInstance() {
        if (mSingleton == null) {
            synchronized (NotifyUtils.class) {
                if (mSingleton == null) {
                    mSingleton = new NotifyUtils();
                }
            }
        }
        return mSingleton;
    }

    public void setHandler(Handler handler) {
        mHandler = new WeakReference<>(handler);
    }

    public void notifyExcerptLinkResult(UUID uuid, boolean z) {
        Handler handler = mHandler.get();
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putSerializable("uuid", uuid);
            bundle.putBoolean("result", z);
            messageObtainMessage.setData(bundle);
            handler.sendMessage(messageObtainMessage);
        }
    }

    public void notifyExcerptLinkResult(UUID uuid, boolean z, long j) {
        Handler handler = mHandler.get();
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putSerializable("uuid", uuid);
            bundle.putBoolean("result", z);
            bundle.putLong(KEY_NOTE_ID, j);
            messageObtainMessage.setData(bundle);
            handler.sendMessage(messageObtainMessage);
        }
    }
}
