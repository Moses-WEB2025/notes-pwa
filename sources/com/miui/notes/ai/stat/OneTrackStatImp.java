package com.miui.notes.ai.stat;

import android.content.Context;
import android.text.TextUtils;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.ah;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class OneTrackStatImp implements IOneTrackStat {
    private static final String APP_ID = "31000000655";
    private static final String CHANNEL = "miui";
    public static final String PRIVATE_KEY_ID = "e0c8a21dc4d22b6884eb9e8f348cc3416d13dfaf";
    public static final String PROJECT_ID = "xiaomi-notes-450908";
    private static String TAG = "NOTE:OneTrackImpl";
    private boolean mInit = false;
    private OneTrack sOneTrack;

    @Override // com.miui.notes.ai.stat.IOneTrackStat
    public void trackPageEnd(String str) {
    }

    @Override // com.miui.notes.ai.stat.IOneTrackStat
    public void trackPageStart(String str) {
    }

    @Override // com.miui.notes.ai.stat.IOneTrackStat
    public void trackWithEnvironment(String str) {
    }

    @Override // com.miui.notes.ai.stat.IOneTrackStat
    public void init(Context context) {
        if (this.mInit) {
            return;
        }
        try {
            this.sOneTrack = OneTrack.createInstance(context, new Configuration.Builder().setProjectId("xiaomi-notes-450908").setPrivateKeyId("e0c8a21dc4d22b6884eb9e8f348cc3416d13dfaf").setAppId(APP_ID).setChannel("miui").setMode(OneTrack.Mode.APP).setInternational(false).setExceptionCatcherEnable(true).setUseCustomPrivacyPolicy(false).build());
            this.mInit = true;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "OneTrack init error" + e);
        }
        Logger.INSTANCE.i(TAG, "onetrack has been initialized ");
    }

    @Override // com.miui.notes.ai.stat.IOneTrackStat
    public void release() {
        if (this.mInit) {
            try {
                Logger.INSTANCE.i(TAG, "OneTrack release");
                this.mInit = false;
                OneTrack.setDisable(true);
            } catch (Exception e) {
                Logger.INSTANCE.d(TAG, "release: " + e);
            }
        }
    }

    @Override // com.miui.notes.ai.stat.IOneTrackStat
    public void track(String str, String str2, Map<String, Object> map) {
        if (this.mInit && map != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    map.put(ah.ab, str);
                }
                this.sOneTrack.track(str2, map);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, " error" + e);
            }
        }
    }

    @Override // com.miui.notes.ai.stat.IOneTrackStat
    public void track(String str, String str2) {
        if (this.mInit) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    HashMap map = new HashMap();
                    map.put(ah.ab, str);
                    this.sOneTrack.track(str2, map);
                    return;
                }
                this.sOneTrack.track(str2, null);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, SNSAuthProvider.VALUE_SNS_ERROR + e);
            }
        }
    }

    @Override // com.miui.notes.ai.stat.IOneTrackStat
    public void trackStringPropertyEvent(String str, String str2) {
        if (this.mInit) {
            try {
                this.sOneTrack.setUserProfile(str, str2);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, SNSAuthProvider.VALUE_SNS_ERROR + e);
            }
        }
    }
}
