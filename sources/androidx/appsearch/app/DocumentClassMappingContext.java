package androidx.appsearch.app;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DocumentClassMappingContext {
    public static final DocumentClassMappingContext EMPTY = new DocumentClassMappingContext(null, null);
    private final Map<String, List<String>> mDocumentClassMap;
    private final Map<String, List<String>> mParentTypeMap;

    public DocumentClassMappingContext(Map<String, List<String>> map, Map<String, List<String>> map2) {
        Map<String, List<String>> mapEmptyMap;
        Map<String, List<String>> mapEmptyMap2;
        if (map != null) {
            mapEmptyMap = Collections.unmodifiableMap(map);
        } else {
            mapEmptyMap = Collections.emptyMap();
        }
        this.mDocumentClassMap = mapEmptyMap;
        if (map2 != null) {
            mapEmptyMap2 = Collections.unmodifiableMap(map2);
        } else {
            mapEmptyMap2 = Collections.emptyMap();
        }
        this.mParentTypeMap = mapEmptyMap2;
    }

    public Map<String, List<String>> getDocumentClassMap() {
        return this.mDocumentClassMap;
    }

    public Map<String, List<String>> getParentTypeMap() {
        return this.mParentTypeMap;
    }
}
