package com.miui.notes.ai.stat;

import android.content.Context;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public interface IOneTrackStat {
    void init(Context context);

    void release();

    void track(String str, String str2);

    void track(String str, String str2, Map<String, Object> map);

    void trackPageEnd(String str);

    void trackPageStart(String str);

    void trackStringPropertyEvent(String str, String str2);

    void trackWithEnvironment(String str);
}
