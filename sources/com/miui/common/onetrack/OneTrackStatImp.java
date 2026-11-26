package com.miui.common.onetrack;

import android.content.Context;
import android.text.TextUtils;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.BuildConfig;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.home.ContentFragment;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.ah;
import com.xiaomi.passport.ui.internal.SNSAuthProvider;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OneTrackStatImp.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010%\n\u0002\u0010\u0000\n\u0002\b\u0007\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J\u0012\u0010\u000f\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\tH\u0016J\u0012\u0010\u0011\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\tH\u0016J6\u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\t2\b\u0010\u0014\u001a\u0004\u0018\u00010\t2\u0018\u0010\u0015\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0018\u00010\u0016H\u0016J\u001c\u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\t2\b\u0010\u0018\u001a\u0004\u0018\u00010\tH\u0016J\u0012\u0010\u0019\u001a\u00020\r2\b\u0010\u0014\u001a\u0004\u0018\u00010\tH\u0016J\u001c\u0010\u001a\u001a\u00020\r2\b\u0010\u001b\u001a\u0004\u0018\u00010\t2\b\u0010\u001c\u001a\u0004\u0018\u00010\tH\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/miui/common/onetrack/OneTrackStatImp;", "Lcom/miui/common/onetrack/IOneTrackStat;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "sOneTrack", "Lcom/xiaomi/onetrack/OneTrack;", "mInit", "", "getAppId", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "init", "", "release", "trackPageStart", ContentFragment.ARG_PAGE, "trackPageEnd", "track", ah.ab, "event", "param", "", "", "key", "trackWithEnvironment", "trackStringPropertyEvent", "property", "value", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class OneTrackStatImp implements IOneTrackStat {
    private static final String CHANNEL = "miui";
    public static final String PRIVATE_KEY_ID = "e0c8a21dc4d22b6884eb9e8f348cc3416d13dfaf";
    public static final String PROJECT_ID = "xiaomi-notes-450908";
    private static final String TAG = "NOTE:OneTrackImpl";
    private boolean mInit;
    private OneTrack sOneTrack;

    @Override // com.miui.common.onetrack.IOneTrackStat
    public void trackPageEnd(String page) {
    }

    @Override // com.miui.common.onetrack.IOneTrackStat
    public void trackPageStart(String page) {
    }

    @Override // com.miui.common.onetrack.IOneTrackStat
    public void trackWithEnvironment(String event) {
    }

    private final String getAppId(Context context) {
        return context.getString(R.string.APPID_client1) + BuildConfig.APPID_CLIENT2;
    }

    @Override // com.miui.common.onetrack.IOneTrackStat
    public void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.mInit) {
            return;
        }
        try {
            this.sOneTrack = OneTrack.createInstance(context, new Configuration.Builder().setProjectId("xiaomi-notes-450908").setPrivateKeyId("e0c8a21dc4d22b6884eb9e8f348cc3416d13dfaf").setAppId(getAppId(context)).setChannel("miui").setMode(OneTrack.Mode.APP).setInternational(false).setExceptionCatcherEnable(true).setUseCustomPrivacyPolicy(false).build());
            this.mInit = true;
        } catch (Exception e) {
            Logger.INSTANCE.e(TAG, "OneTrack init error" + e);
        }
        Logger.INSTANCE.i(TAG, "onetrack has been initialized ");
    }

    @Override // com.miui.common.onetrack.IOneTrackStat
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

    @Override // com.miui.common.onetrack.IOneTrackStat
    public void track(String tip, String event, Map<String, Object> param) {
        if (this.mInit && param != null) {
            try {
                if (!TextUtils.isEmpty(tip)) {
                    param.put(ah.ab, tip);
                }
                OneTrack oneTrack = this.sOneTrack;
                Intrinsics.checkNotNull(oneTrack);
                oneTrack.track(event, param);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, " error" + e);
            }
        }
    }

    @Override // com.miui.common.onetrack.IOneTrackStat
    public void track(String tip, String key) {
        if (this.mInit) {
            try {
                if (!TextUtils.isEmpty(tip)) {
                    HashMap map = new HashMap();
                    map.put(ah.ab, tip);
                    OneTrack oneTrack = this.sOneTrack;
                    Intrinsics.checkNotNull(oneTrack);
                    oneTrack.track(key, map);
                    return;
                }
                OneTrack oneTrack2 = this.sOneTrack;
                Intrinsics.checkNotNull(oneTrack2);
                oneTrack2.track(key, null);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, SNSAuthProvider.VALUE_SNS_ERROR + e);
            }
        }
    }

    @Override // com.miui.common.onetrack.IOneTrackStat
    public void trackStringPropertyEvent(String property, String value) {
        if (this.mInit) {
            try {
                OneTrack oneTrack = this.sOneTrack;
                Intrinsics.checkNotNull(oneTrack);
                oneTrack.setUserProfile(property, value);
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, SNSAuthProvider.VALUE_SNS_ERROR + e);
            }
        }
    }
}
