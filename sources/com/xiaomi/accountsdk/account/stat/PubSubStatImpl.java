package com.xiaomi.accountsdk.account.stat;

import android.content.Context;
import com.ot.pubsub.Configuration;
import com.ot.pubsub.PubSubTrack;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class PubSubStatImpl extends AccountStatInterface {
    private static final String APP_ID = "31000401513";
    private static final String CHANNEL = "default";
    private static final String PRIVATE_KEY_ID = "05c0962f3ced93ddb5558f1c6fcb7f0ffa86d338";
    private static final String PROJECT_ID = "xiaomiaccount";
    private static final String TOPIC = "account-global";
    private final PubSubTrack mPubSubTrack;

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void statHttpEvent(String str, long j) {
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void statHttpEvent(String str, Exception exc) {
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void trackNetAvailable(NetStatParam netStatParam) {
    }

    public PubSubStatImpl(Context context, boolean z) {
        this.mPubSubTrack = PubSubTrack.createInstance(context, new Configuration.Builder().setAppId(APP_ID).setChannel("default").setInternational(z).setPrivateKeyId(PRIVATE_KEY_ID).setProjectId(PROJECT_ID).build());
        setCommonProperty(getDefaultCommonProperty(context));
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void enableNetworkAccess(Context context) {
        PubSubTrack.setAccessNetworkEnable(context, true);
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void track(String str, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        this.mPubSubTrack.track(TOPIC, str, map);
    }

    @Override // com.xiaomi.accountsdk.account.stat.AccountStatInterface
    public void setCommonProperty(Map<String, Object> map) {
        this.mPubSubTrack.setCommonProperty(map);
    }
}
