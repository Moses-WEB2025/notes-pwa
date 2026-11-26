package com.xiaomi.accountsdk.account.stat;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.account.passportsdk.account_sso.BuildConfig;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.api.ah;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AccountStatInterface {
    private static volatile AccountStatInterface sInstance;

    public enum AccountStatType {
        ONETRACK,
        PUBSUB,
        NONE
    }

    public abstract void enableNetworkAccess(Context context);

    public abstract void setCommonProperty(Map<String, Object> map);

    public abstract void statHttpEvent(String str, long j);

    public abstract void statHttpEvent(String str, Exception exc);

    public abstract void track(String str, Map<String, Object> map);

    public abstract void trackNetAvailable(NetStatParam netStatParam);

    public final void statEvent(String str, Object... objArr) {
        track(str, parseStatParams(objArr));
    }

    public final void statEvent(String str, Map<String, Object> map) {
        track(str, map);
    }

    public final void statEventWithTip(String str, String str2, Object... objArr) {
        track(str, str2, parseStatParams(objArr));
    }

    public final void statEventWithTip(String str, String str2, Map<String, Object> map) {
        track(str, str2, map);
    }

    private void track(String str, String str2, Map<String, Object> map) {
        if (!TextUtils.isEmpty(str2)) {
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(ah.ab, str2);
        }
        track(str, map);
    }

    protected static Map<String, Object> parseStatParams(Object... objArr) {
        if (objArr == null || objArr.length == 0) {
            return null;
        }
        if ((objArr.length & 1) != 0) {
            throw new IllegalArgumentException("paramsKeysAndValues length has to be even number!!!");
        }
        HashMap map = new HashMap();
        for (int i = 0; i < objArr.length; i += 2) {
            Object obj = objArr[i];
            if (!(obj instanceof String)) {
                throw new IllegalArgumentException("key has to be String!!!");
            }
            map.put((String) obj, objArr[i + 1]);
        }
        return map;
    }

    protected static Map<String, Object> getDefaultCommonProperty(Context context) {
        HashMap map = new HashMap();
        map.put("language", Locale.getDefault().getLanguage());
        map.put("account_sdk_version", BuildConfig.VERSION_NAME);
        return map;
    }

    /* JADX INFO: renamed from: com.xiaomi.accountsdk.account.stat.AccountStatInterface$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$accountsdk$account$stat$AccountStatInterface$AccountStatType;

        static {
            int[] iArr = new int[AccountStatType.values().length];
            $SwitchMap$com$xiaomi$accountsdk$account$stat$AccountStatInterface$AccountStatType = iArr;
            try {
                iArr[AccountStatType.ONETRACK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$stat$AccountStatInterface$AccountStatType[AccountStatType.PUBSUB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$accountsdk$account$stat$AccountStatInterface$AccountStatType[AccountStatType.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static void init(Context context, AccountStatType accountStatType, boolean z, boolean z2) {
        int i = AnonymousClass1.$SwitchMap$com$xiaomi$accountsdk$account$stat$AccountStatInterface$AccountStatType[accountStatType.ordinal()];
        if (i == 1) {
            sInstance = new OneTrackStatImpl(context, z, z2 ? OneTrack.Mode.SDK : OneTrack.Mode.APP);
        } else if (i == 2) {
            sInstance = new PubSubStatImpl(context, z);
        } else {
            if (i != 3) {
                return;
            }
            sInstance = new EmptyStatImpl();
        }
    }

    public static void init(AccountStatInterface accountStatInterface) {
        sInstance = accountStatInterface;
    }

    public static AccountStatInterface getInstance() {
        if (sInstance == null) {
            throw new IllegalArgumentException("please call init first!!!");
        }
        return sInstance;
    }

    public static void setSidCommonProperty(String str) {
        if (sInstance != null) {
            HashMap map = new HashMap();
            map.put("biz_sid", str);
            sInstance.setCommonProperty(map);
        }
    }
}
