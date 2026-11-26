package androidx.appsearch.usagereporting;

import androidx.appsearch.app.AppSearchDocumentClassMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: androidx.appsearch.usagereporting.$$__AppSearch__DocumentClassMap_d8c38be6e7324bce3bef36b39418d987bb4285b33ec2e33e3aea0d9ccb6519d7_0, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__DocumentClassMap_d8c38be6e7324bce3bef36b39418d987bb4285b33ec2e33e3aea0d9ccb6519d7_0 extends AppSearchDocumentClassMap {
    @Override // androidx.appsearch.app.AppSearchDocumentClassMap
    protected Map<String, List<String>> getMap() {
        HashMap map = new HashMap();
        map.put(C$$__AppSearch__ClickAction.SCHEMA_NAME, Arrays.asList("androidx.appsearch.usagereporting.ClickAction"));
        map.put(C$$__AppSearch__SearchAction.SCHEMA_NAME, Arrays.asList("androidx.appsearch.usagereporting.SearchAction"));
        map.put(C$$__AppSearch__DismissAction.SCHEMA_NAME, Arrays.asList("androidx.appsearch.usagereporting.DismissAction"));
        map.put(C$$__AppSearch__ImpressionAction.SCHEMA_NAME, Arrays.asList("androidx.appsearch.usagereporting.ImpressionAction"));
        map.put(C$$__AppSearch__TakenAction.SCHEMA_NAME, Arrays.asList("androidx.appsearch.usagereporting.TakenAction"));
        return map;
    }
}
