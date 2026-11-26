package com.miui.notes.cta;

import android.content.Context;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.tool.PreferenceUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class CTAManager {
    private static final String OP_ADD = "add";
    private static final String OP_NOTIFY_ACCEPT_ALL = "accept_all";
    private static final String OP_NOTIFY_REJECT_ALL = "reject_all";
    private static final String OP_REMOVE = "remove";
    private static final String OP_REMOVE_ALL = "remove_all";
    private static final String TAG = "CTAManager";
    private List<CTAListener> mListeners;

    public interface CTAListener {
        void onAccept();

        void onReject();
    }

    private CTAManager() {
        this.mListeners = new ArrayList();
    }

    private static class Holder {
        private static final CTAManager INSTANCE = new CTAManager();

        private Holder() {
        }
    }

    public static CTAManager getInstance() {
        return Holder.INSTANCE;
    }

    public boolean isAccepted(Context context) {
        return PreferenceUtils.isCTAAccepted(context);
    }

    public void addListener(CTAListener cTAListener) {
        opListeners(OP_ADD, cTAListener);
    }

    public void removeListener(CTAListener cTAListener) {
        opListeners(OP_REMOVE, cTAListener);
    }

    void removeAllListener() {
        opListeners(OP_REMOVE_ALL, null);
    }

    public void notifyAccept() {
        opListeners(OP_NOTIFY_ACCEPT_ALL, null);
    }

    public void notifyReject() {
        opListeners(OP_NOTIFY_REJECT_ALL, null);
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0080 A[Catch: all -> 0x008b, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:4:0x0005, B:29:0x0080, B:6:0x000a, B:8:0x0013, B:9:0x001a, B:11:0x0022, B:12:0x0028, B:14:0x0031, B:15:0x0037, B:17:0x003d, B:18:0x0047, B:19:0x004d, B:21:0x0056, B:22:0x005c, B:24:0x0064, B:25:0x006a, B:27:0x0070, B:28:0x007a), top: B:35:0x0001 }] */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private synchronized void opListeners(String str, CTAListener cTAListener) {
        switch (str.hashCode()) {
            case -1226663446:
                if (!str.equals(OP_NOTIFY_ACCEPT_ALL)) {
                    Logger.INSTANCE.w(TAG, "No such OpMode!");
                } else {
                    Iterator<CTAListener> it = this.mListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onAccept();
                    }
                    this.mListeners.clear();
                }
                break;
            case -934610812:
                if (!str.equals(OP_REMOVE)) {
                    Logger.INSTANCE.w(TAG, "No such OpMode!");
                } else {
                    this.mListeners.remove(cTAListener);
                }
                break;
            case -649775871:
                if (!str.equals(OP_NOTIFY_REJECT_ALL)) {
                    Logger.INSTANCE.w(TAG, "No such OpMode!");
                } else {
                    Iterator<CTAListener> it2 = this.mListeners.iterator();
                    while (it2.hasNext()) {
                        it2.next().onReject();
                    }
                    this.mListeners.clear();
                }
                break;
            case 96417:
                if (!str.equals(OP_ADD)) {
                    Logger.INSTANCE.w(TAG, "No such OpMode!");
                } else {
                    this.mListeners.add(cTAListener);
                }
                break;
            case 1098891110:
                if (!str.equals(OP_REMOVE_ALL)) {
                    Logger.INSTANCE.w(TAG, "No such OpMode!");
                } else {
                    this.mListeners.clear();
                }
                break;
            default:
                Logger.INSTANCE.w(TAG, "No such OpMode!");
                break;
        }
    }
}
