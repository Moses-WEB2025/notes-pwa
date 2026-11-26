package androidx.appsearch.builtintypes;

import androidx.appsearch.app.AppSearchDocumentClassMap;
import androidx.appsearch.builtintypes.properties.C$$__AppSearch__Keyword;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__DocumentClassMap_3c537ab9c5c490eb55217815ca05de28cfb02c3269cd88bf53f39393216cb9e9_0, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__DocumentClassMap_3c537ab9c5c490eb55217815ca05de28cfb02c3269cd88bf53f39393216cb9e9_0 extends AppSearchDocumentClassMap {
    @Override // androidx.appsearch.app.AppSearchDocumentClassMap
    protected Map<String, List<String>> getMap() {
        HashMap map = new HashMap();
        map.put(C$$__AppSearch__StopwatchLap.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.StopwatchLap"));
        map.put(C$$__AppSearch__ContactPoint.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.ContactPoint"));
        map.put(C$$__AppSearch__Thing.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.Thing"));
        map.put(C$$__AppSearch__GlobalSearchApplicationInfo.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.GlobalSearchApplicationInfo"));
        map.put(C$$__AppSearch__Person.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.Person"));
        map.put(C$$__AppSearch__AlarmInstance.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.AlarmInstance"));
        map.put(C$$__AppSearch__Keyword.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.properties.Keyword"));
        map.put(C$$__AppSearch__ImageObject.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.ImageObject"));
        map.put("builtin:WebPage", Arrays.asList("androidx.appsearch.builtintypes.WebPage"));
        map.put(C$$__AppSearch__MobileApplication.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.MobileApplication"));
        map.put(C$$__AppSearch__Alarm.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.Alarm"));
        map.put(C$$__AppSearch__Timer.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.Timer"));
        map.put(C$$__AppSearch__PotentialAction.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.PotentialAction"));
        map.put(C$$__AppSearch__Stopwatch.SCHEMA_NAME, Arrays.asList("androidx.appsearch.builtintypes.Stopwatch"));
        return map;
    }
}
