package com.miui.common.onetrack;

import android.content.Context;
import ch.qos.logback.core.CoreConstants;
import com.miui.notes.home.ContentFragment;
import com.xiaomi.onetrack.api.ah;
import java.util.Map;
import kotlin.Metadata;

/* JADX INFO: compiled from: IOneTrackStat.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\u0012\u0010\u0007\u001a\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\tH&J\u0012\u0010\n\u001a\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\tH&J6\u0010\u000b\u001a\u00020\u00032\b\u0010\f\u001a\u0004\u0018\u00010\t2\b\u0010\r\u001a\u0004\u0018\u00010\t2\u0018\u0010\u000e\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u000fH&J\u001c\u0010\u000b\u001a\u00020\u00032\b\u0010\f\u001a\u0004\u0018\u00010\t2\b\u0010\r\u001a\u0004\u0018\u00010\tH&J\u0012\u0010\u0010\u001a\u00020\u00032\b\u0010\r\u001a\u0004\u0018\u00010\tH&J\u001c\u0010\u0011\u001a\u00020\u00032\b\u0010\u0012\u001a\u0004\u0018\u00010\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\tH&¨\u0006\u0014À\u0006\u0003"}, d2 = {"Lcom/miui/common/onetrack/IOneTrackStat;", "", "init", "", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "release", "trackPageStart", ContentFragment.ARG_PAGE, "", "trackPageEnd", "track", ah.ab, "event", "param", "", "trackWithEnvironment", "trackStringPropertyEvent", "property", "value", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IOneTrackStat {
    void init(Context context);

    void release();

    void track(String tip, String event);

    void track(String tip, String event, Map<String, Object> param);

    void trackPageEnd(String page);

    void trackPageStart(String page);

    void trackStringPropertyEvent(String property, String value);

    void trackWithEnvironment(String event);
}
