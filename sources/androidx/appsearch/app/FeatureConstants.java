package androidx.appsearch.app;

import androidx.collection.ArraySet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class FeatureConstants {
    public static final String EMBEDDING_SEARCH = "EMBEDDING_SEARCH";
    public static final String LIST_FILTER_HAS_PROPERTY_FUNCTION = "LIST_FILTER_HAS_PROPERTY_FUNCTION";
    public static final String LIST_FILTER_MATCH_SCORE_EXPRESSION_FUNCTION = "LIST_FILTER_MATCH_SCORE_EXPRESSION_FUNCTION";
    public static final String LIST_FILTER_QUERY_LANGUAGE = "LIST_FILTER_QUERY_LANGUAGE";
    public static final String NUMERIC_SEARCH = "NUMERIC_SEARCH";
    public static final String SCHEMA_SCORABLE_PROPERTY_CONFIG = "SCHEMA_SCORABLE_PROPERTY_CONFIG";
    public static final Set<String> SCORABLE_FEATURE_SET = Collections.unmodifiableSet(new ArraySet(Arrays.asList("SCHEMA_SCORABLE_PROPERTY_CONFIG")));
    public static final String VERBATIM_SEARCH = "VERBATIM_SEARCH";

    private FeatureConstants() {
    }
}
