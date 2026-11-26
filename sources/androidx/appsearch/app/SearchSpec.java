package androidx.appsearch.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.appsearch.util.BundleUtil;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class SearchSpec extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SearchSpec> CREATOR = new StubCreators.SearchSpecCreator();
    public static final int DEFAULT_NUM_PER_PAGE = 10;
    public static final int EMBEDDING_SEARCH_METRIC_TYPE_COSINE = 1;
    public static final int EMBEDDING_SEARCH_METRIC_TYPE_DEFAULT = 0;
    public static final int EMBEDDING_SEARCH_METRIC_TYPE_DOT_PRODUCT = 2;
    public static final int EMBEDDING_SEARCH_METRIC_TYPE_EUCLIDEAN = 3;
    public static final int GROUPING_TYPE_PER_NAMESPACE = 2;
    public static final int GROUPING_TYPE_PER_PACKAGE = 1;
    public static final int GROUPING_TYPE_PER_SCHEMA = 4;
    private static final int MAX_NUM_PER_PAGE = 10000;
    private static final int MAX_SNIPPET_COUNT = 10000;
    private static final int MAX_SNIPPET_PER_PROPERTY_COUNT = 10000;
    private static final int MAX_SNIPPET_SIZE_LIMIT = 10000;
    public static final int ORDER_ASCENDING = 1;
    public static final int ORDER_DESCENDING = 0;

    @Deprecated
    public static final String PROJECTION_SCHEMA_TYPE_WILDCARD = "*";
    public static final int RANKING_STRATEGY_ADVANCED_RANKING_EXPRESSION = 9;
    public static final int RANKING_STRATEGY_CREATION_TIMESTAMP = 2;
    public static final int RANKING_STRATEGY_DOCUMENT_SCORE = 1;
    public static final int RANKING_STRATEGY_JOIN_AGGREGATE_SCORE = 8;
    public static final int RANKING_STRATEGY_NONE = 0;
    public static final int RANKING_STRATEGY_RELEVANCE_SCORE = 3;
    public static final int RANKING_STRATEGY_SYSTEM_USAGE_COUNT = 6;
    public static final int RANKING_STRATEGY_SYSTEM_USAGE_LAST_USED_TIMESTAMP = 7;
    public static final int RANKING_STRATEGY_USAGE_COUNT = 4;
    public static final int RANKING_STRATEGY_USAGE_LAST_USED_TIMESTAMP = 5;
    public static final String SCHEMA_TYPE_WILDCARD = "*";
    public static final int TERM_MATCH_EXACT_ONLY = 1;
    public static final int TERM_MATCH_PREFIX = 2;
    private final String mAdvancedRankingExpression;
    private final int mDefaultEmbeddingSearchMetricType;
    private final List<EmbeddingVector> mEmbeddingParameters;
    private final List<String> mEnabledFeatures;
    private final List<String> mFilterDocumentIds;
    private final int mGroupingLimit;
    private final List<String> mInformationalRankingExpressions;
    private final JoinSpec mJoinSpec;
    private final int mMaxSnippetSize;
    private final List<String> mNamespaces;
    private final int mOrder;
    private final List<String> mPackageNames;
    final Bundle mProjectionTypePropertyMasks;
    private final int mRankingStrategy;
    private final int mResultCountPerPage;
    private final int mResultGroupingTypeFlags;
    private final boolean mRetrieveEmbeddingMatchInfos;
    private final List<String> mSchemas;
    private final String mSearchSourceLogTag;
    private final List<String> mSearchStringParameters;
    private final int mSnippetCount;
    private final int mSnippetCountPerProperty;
    private final int mTermMatchType;
    final Bundle mTypePropertyFilters;
    final Bundle mTypePropertyWeightsField;

    @Retention(RetentionPolicy.SOURCE)
    public @interface EmbeddingSearchMetricType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GroupingType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Order {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RankingStrategy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TermMatch {
    }

    SearchSpec(int i, List<String> list, List<String> list2, Bundle bundle, List<String> list3, int i2, int i3, int i4, int i5, int i6, int i7, Bundle bundle2, int i8, int i9, Bundle bundle3, JoinSpec joinSpec, String str, List<String> list4, String str2, List<EmbeddingVector> list5, int i10, List<String> list6, List<String> list7, List<String> list8, boolean z) {
        List<String> listEmptyList;
        List<String> listEmptyList2;
        this.mTermMatchType = i;
        this.mSchemas = Collections.unmodifiableList((List) Preconditions.checkNotNull(list));
        this.mNamespaces = Collections.unmodifiableList((List) Preconditions.checkNotNull(list2));
        this.mTypePropertyFilters = (Bundle) Preconditions.checkNotNull(bundle);
        this.mPackageNames = Collections.unmodifiableList((List) Preconditions.checkNotNull(list3));
        this.mResultCountPerPage = i2;
        this.mRankingStrategy = i3;
        this.mOrder = i4;
        this.mSnippetCount = i5;
        this.mSnippetCountPerProperty = i6;
        this.mMaxSnippetSize = i7;
        this.mProjectionTypePropertyMasks = (Bundle) Preconditions.checkNotNull(bundle2);
        this.mResultGroupingTypeFlags = i8;
        this.mGroupingLimit = i9;
        this.mTypePropertyWeightsField = (Bundle) Preconditions.checkNotNull(bundle3);
        this.mJoinSpec = joinSpec;
        this.mAdvancedRankingExpression = (String) Preconditions.checkNotNull(str);
        this.mEnabledFeatures = Collections.unmodifiableList((List) Preconditions.checkNotNull(list4));
        this.mSearchSourceLogTag = str2;
        if (list5 != null) {
            this.mEmbeddingParameters = Collections.unmodifiableList(list5);
        } else {
            this.mEmbeddingParameters = Collections.emptyList();
        }
        this.mDefaultEmbeddingSearchMetricType = i10;
        if (list6 != null) {
            this.mInformationalRankingExpressions = Collections.unmodifiableList(list6);
        } else {
            this.mInformationalRankingExpressions = Collections.emptyList();
        }
        if (list7 != null) {
            listEmptyList = Collections.unmodifiableList(list7);
        } else {
            listEmptyList = Collections.emptyList();
        }
        this.mSearchStringParameters = listEmptyList;
        if (list8 != null) {
            listEmptyList2 = Collections.unmodifiableList(list8);
        } else {
            listEmptyList2 = Collections.emptyList();
        }
        this.mFilterDocumentIds = listEmptyList2;
        this.mRetrieveEmbeddingMatchInfos = z;
    }

    public int getTermMatch() {
        return this.mTermMatchType;
    }

    public List<String> getFilterSchemas() {
        List<String> list = this.mSchemas;
        return list == null ? Collections.emptyList() : list;
    }

    public Map<String, List<String>> getFilterProperties() {
        Set<String> setKeySet = this.mTypePropertyFilters.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            arrayMap.put(str, (List) Preconditions.checkNotNull(this.mTypePropertyFilters.getStringArrayList(str)));
        }
        return arrayMap;
    }

    public List<String> getFilterNamespaces() {
        List<String> list = this.mNamespaces;
        return list == null ? Collections.emptyList() : list;
    }

    public List<String> getFilterPackageNames() {
        List<String> list = this.mPackageNames;
        return list == null ? Collections.emptyList() : list;
    }

    public List<String> getFilterDocumentIds() {
        return this.mFilterDocumentIds;
    }

    public int getResultCountPerPage() {
        return this.mResultCountPerPage;
    }

    public int getRankingStrategy() {
        return this.mRankingStrategy;
    }

    public int getOrder() {
        return this.mOrder;
    }

    public int getSnippetCount() {
        return this.mSnippetCount;
    }

    public int getSnippetCountPerProperty() {
        return this.mSnippetCountPerProperty;
    }

    public int getMaxSnippetSize() {
        return this.mMaxSnippetSize;
    }

    public boolean shouldRetrieveEmbeddingMatchInfos() {
        return this.mRetrieveEmbeddingMatchInfos;
    }

    public Map<String, List<String>> getProjections() {
        Set<String> setKeySet = this.mProjectionTypePropertyMasks.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            arrayMap.put(str, (List) Objects.requireNonNull(this.mProjectionTypePropertyMasks.getStringArrayList(str)));
        }
        return arrayMap;
    }

    public Map<String, List<PropertyPath>> getProjectionPaths() {
        Set<String> setKeySet = this.mProjectionTypePropertyMasks.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            ArrayList<String> stringArrayList = this.mProjectionTypePropertyMasks.getStringArrayList(str);
            if (stringArrayList != null) {
                ArrayList arrayList = new ArrayList(stringArrayList.size());
                for (int i = 0; i < stringArrayList.size(); i++) {
                    arrayList.add(new PropertyPath(stringArrayList.get(i)));
                }
                arrayMap.put(str, arrayList);
            }
        }
        return arrayMap;
    }

    public Map<String, Map<String, Double>> getPropertyWeights() {
        Set<String> setKeySet = this.mTypePropertyWeightsField.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            Bundle bundle = this.mTypePropertyWeightsField.getBundle(str);
            if (bundle != null) {
                Set<String> setKeySet2 = bundle.keySet();
                ArrayMap arrayMap2 = new ArrayMap(setKeySet2.size());
                for (String str2 : setKeySet2) {
                    arrayMap2.put(str2, Double.valueOf(bundle.getDouble(str2)));
                }
                arrayMap.put(str, arrayMap2);
            }
        }
        return arrayMap;
    }

    public Map<String, Map<PropertyPath, Double>> getPropertyWeightPaths() {
        Set<String> setKeySet = this.mTypePropertyWeightsField.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            Bundle bundle = this.mTypePropertyWeightsField.getBundle(str);
            if (bundle != null) {
                Set<String> setKeySet2 = bundle.keySet();
                ArrayMap arrayMap2 = new ArrayMap(setKeySet2.size());
                for (String str2 : setKeySet2) {
                    arrayMap2.put(new PropertyPath(str2), Double.valueOf(bundle.getDouble(str2)));
                }
                arrayMap.put(str, arrayMap2);
            }
        }
        return arrayMap;
    }

    public int getResultGroupingTypeFlags() {
        return this.mResultGroupingTypeFlags;
    }

    public int getResultGroupingLimit() {
        return this.mGroupingLimit;
    }

    public JoinSpec getJoinSpec() {
        return this.mJoinSpec;
    }

    public String getAdvancedRankingExpression() {
        return this.mAdvancedRankingExpression;
    }

    public String getSearchSourceLogTag() {
        return this.mSearchSourceLogTag;
    }

    public List<EmbeddingVector> getEmbeddingParameters() {
        return this.mEmbeddingParameters;
    }

    public int getDefaultEmbeddingSearchMetricType() {
        return this.mDefaultEmbeddingSearchMetricType;
    }

    public List<String> getInformationalRankingExpressions() {
        return this.mInformationalRankingExpressions;
    }

    public List<String> getSearchStringParameters() {
        return this.mSearchStringParameters;
    }

    public boolean isNumericSearchEnabled() {
        return this.mEnabledFeatures.contains("NUMERIC_SEARCH");
    }

    public boolean isVerbatimSearchEnabled() {
        return this.mEnabledFeatures.contains("VERBATIM_SEARCH");
    }

    public boolean isListFilterQueryLanguageEnabled() {
        return this.mEnabledFeatures.contains("LIST_FILTER_QUERY_LANGUAGE");
    }

    public boolean isScorablePropertyRankingEnabled() {
        return this.mEnabledFeatures.contains("SCHEMA_SCORABLE_PROPERTY_CONFIG");
    }

    public boolean isListFilterHasPropertyFunctionEnabled() {
        return this.mEnabledFeatures.contains("LIST_FILTER_HAS_PROPERTY_FUNCTION");
    }

    public boolean isListFilterMatchScoreExpressionFunctionEnabled() {
        return this.mEnabledFeatures.contains("LIST_FILTER_MATCH_SCORE_EXPRESSION_FUNCTION");
    }

    public List<String> getEnabledFeatures() {
        return this.mEnabledFeatures;
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.SearchSpecCreator.writeToParcel(this, parcel, i);
    }

    public static final class Builder {
        private String mAdvancedRankingExpression;
        private boolean mBuilt;
        private int mDefaultEmbeddingSearchMetricType;
        private List<EmbeddingVector> mEmbeddingParameters;
        private ArraySet<String> mEnabledFeatures;
        private List<String> mFilterDocumentIds;
        private int mGroupingLimit;
        private int mGroupingTypeFlags;
        private List<String> mInformationalRankingExpressions;
        private JoinSpec mJoinSpec;
        private int mMaxSnippetSize;
        private List<String> mNamespaces;
        private int mOrder;
        private List<String> mPackageNames;
        private Bundle mProjectionTypePropertyMasks;
        private int mRankingStrategy;
        private int mResultCountPerPage;
        private boolean mRetrieveEmbeddingMatchInfos;
        private List<String> mSchemas;
        private String mSearchSourceLogTag;
        private List<String> mSearchStringParameters;
        private int mSnippetCount;
        private int mSnippetCountPerProperty;
        private int mTermMatchType;
        private Bundle mTypePropertyFilters;
        private Bundle mTypePropertyWeights;

        public Builder() {
            this.mSchemas = new ArrayList();
            this.mNamespaces = new ArrayList();
            this.mTypePropertyFilters = new Bundle();
            this.mPackageNames = new ArrayList();
            this.mEnabledFeatures = new ArraySet<>();
            this.mProjectionTypePropertyMasks = new Bundle();
            this.mTypePropertyWeights = new Bundle();
            this.mEmbeddingParameters = new ArrayList();
            this.mSearchStringParameters = new ArrayList();
            this.mFilterDocumentIds = new ArrayList();
            this.mResultCountPerPage = 10;
            this.mTermMatchType = 2;
            this.mDefaultEmbeddingSearchMetricType = 1;
            this.mSnippetCount = 0;
            this.mSnippetCountPerProperty = 10000;
            this.mMaxSnippetSize = 0;
            this.mRankingStrategy = 0;
            this.mOrder = 0;
            this.mGroupingTypeFlags = 0;
            this.mGroupingLimit = 0;
            this.mAdvancedRankingExpression = "";
            this.mInformationalRankingExpressions = new ArrayList();
            this.mRetrieveEmbeddingMatchInfos = false;
            this.mBuilt = false;
        }

        public Builder(SearchSpec searchSpec) {
            this.mSchemas = new ArrayList();
            this.mNamespaces = new ArrayList();
            this.mTypePropertyFilters = new Bundle();
            this.mPackageNames = new ArrayList();
            this.mEnabledFeatures = new ArraySet<>();
            this.mProjectionTypePropertyMasks = new Bundle();
            this.mTypePropertyWeights = new Bundle();
            this.mEmbeddingParameters = new ArrayList();
            this.mSearchStringParameters = new ArrayList();
            this.mFilterDocumentIds = new ArrayList();
            this.mResultCountPerPage = 10;
            this.mTermMatchType = 2;
            this.mDefaultEmbeddingSearchMetricType = 1;
            this.mSnippetCount = 0;
            this.mSnippetCountPerProperty = 10000;
            this.mMaxSnippetSize = 0;
            this.mRankingStrategy = 0;
            this.mOrder = 0;
            this.mGroupingTypeFlags = 0;
            this.mGroupingLimit = 0;
            this.mAdvancedRankingExpression = "";
            this.mInformationalRankingExpressions = new ArrayList();
            this.mRetrieveEmbeddingMatchInfos = false;
            this.mBuilt = false;
            Objects.requireNonNull(searchSpec);
            this.mSchemas = new ArrayList(searchSpec.getFilterSchemas());
            this.mNamespaces = new ArrayList(searchSpec.getFilterNamespaces());
            for (Map.Entry<String, List<String>> entry : searchSpec.getFilterProperties().entrySet()) {
                addFilterProperties(entry.getKey(), entry.getValue());
            }
            this.mPackageNames = new ArrayList(searchSpec.getFilterPackageNames());
            this.mEnabledFeatures = new ArraySet<>(searchSpec.getEnabledFeatures());
            for (Map.Entry<String, List<String>> entry2 : searchSpec.getProjections().entrySet()) {
                addProjection(entry2.getKey(), entry2.getValue());
            }
            for (Map.Entry<String, Map<String, Double>> entry3 : searchSpec.getPropertyWeights().entrySet()) {
                setPropertyWeights(entry3.getKey(), entry3.getValue());
            }
            this.mEmbeddingParameters = new ArrayList(searchSpec.getEmbeddingParameters());
            this.mSearchStringParameters = new ArrayList(searchSpec.getSearchStringParameters());
            this.mResultCountPerPage = searchSpec.getResultCountPerPage();
            this.mTermMatchType = searchSpec.getTermMatch();
            this.mDefaultEmbeddingSearchMetricType = searchSpec.getDefaultEmbeddingSearchMetricType();
            this.mSnippetCount = searchSpec.getSnippetCount();
            this.mSnippetCountPerProperty = searchSpec.getSnippetCountPerProperty();
            this.mMaxSnippetSize = searchSpec.getMaxSnippetSize();
            this.mRankingStrategy = searchSpec.getRankingStrategy();
            this.mOrder = searchSpec.getOrder();
            this.mGroupingTypeFlags = searchSpec.getResultGroupingTypeFlags();
            this.mGroupingLimit = searchSpec.getResultGroupingLimit();
            this.mJoinSpec = searchSpec.getJoinSpec();
            this.mAdvancedRankingExpression = searchSpec.getAdvancedRankingExpression();
            this.mInformationalRankingExpressions = new ArrayList(searchSpec.getInformationalRankingExpressions());
            this.mSearchSourceLogTag = searchSpec.getSearchSourceLogTag();
            this.mFilterDocumentIds = new ArrayList(searchSpec.getFilterDocumentIds());
            this.mRetrieveEmbeddingMatchInfos = searchSpec.shouldRetrieveEmbeddingMatchInfos();
        }

        public Builder setTermMatch(int i) {
            Preconditions.checkArgumentInRange(i, 1, 2, "Term match type");
            resetIfBuilt();
            this.mTermMatchType = i;
            return this;
        }

        public Builder addFilterSchemas(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addFilterSchemas(Arrays.asList(strArr));
        }

        public Builder addFilterSchemas(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mSchemas.addAll(collection);
            return this;
        }

        public Builder addFilterDocumentClasses(Collection<? extends Class<?>> collection) throws AppSearchException {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            ArrayList arrayList = new ArrayList(collection.size());
            DocumentClassFactoryRegistry documentClassFactoryRegistry = DocumentClassFactoryRegistry.getInstance();
            Iterator<? extends Class<?>> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(documentClassFactoryRegistry.getOrCreateFactory((Class) it.next()).getSchemaName());
            }
            addFilterSchemas(arrayList);
            return this;
        }

        public Builder addFilterDocumentClasses(Class<?>... clsArr) throws AppSearchException {
            Preconditions.checkNotNull(clsArr);
            resetIfBuilt();
            return addFilterDocumentClasses(Arrays.asList(clsArr));
        }

        public Builder clearFilterSchemas() {
            resetIfBuilt();
            this.mSchemas.clear();
            return this;
        }

        public Builder addFilterProperties(String str, Collection<String> collection) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            ArrayList<String> arrayList = new ArrayList<>(collection.size());
            for (String str2 : collection) {
                Preconditions.checkNotNull(str2);
                arrayList.add(str2);
            }
            this.mTypePropertyFilters.putStringArrayList(str, arrayList);
            return this;
        }

        public Builder addFilterPropertyPaths(String str, Collection<PropertyPath> collection) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(collection);
            ArrayList arrayList = new ArrayList(collection.size());
            Iterator<PropertyPath> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toString());
            }
            return addFilterProperties(str, arrayList);
        }

        public Builder addFilterProperties(Class<?> cls, Collection<String> collection) throws AppSearchException {
            Preconditions.checkNotNull(cls);
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            return addFilterProperties(DocumentClassFactoryRegistry.getInstance().getOrCreateFactory((Class) cls).getSchemaName(), collection);
        }

        public Builder addFilterPropertyPaths(Class<?> cls, Collection<PropertyPath> collection) throws AppSearchException {
            Preconditions.checkNotNull(cls);
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            return addFilterPropertyPaths(DocumentClassFactoryRegistry.getInstance().getOrCreateFactory((Class) cls).getSchemaName(), collection);
        }

        public Builder clearFilterProperties() {
            resetIfBuilt();
            this.mTypePropertyFilters.clear();
            return this;
        }

        public Builder addFilterNamespaces(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addFilterNamespaces(Arrays.asList(strArr));
        }

        public Builder addFilterNamespaces(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mNamespaces.addAll(collection);
            return this;
        }

        public Builder clearFilterNamespaces() {
            resetIfBuilt();
            this.mNamespaces.clear();
            return this;
        }

        public Builder addFilterPackageNames(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addFilterPackageNames(Arrays.asList(strArr));
        }

        public Builder addFilterPackageNames(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mPackageNames.addAll(collection);
            return this;
        }

        public Builder clearFilterPackageNames() {
            resetIfBuilt();
            this.mPackageNames.clear();
            return this;
        }

        public Builder addFilterDocumentIds(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addFilterDocumentIds(Arrays.asList(strArr));
        }

        public Builder addFilterDocumentIds(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mFilterDocumentIds.addAll(collection);
            return this;
        }

        public Builder clearFilterDocumentIds() {
            resetIfBuilt();
            this.mFilterDocumentIds.clear();
            return this;
        }

        public Builder setResultCountPerPage(int i) {
            Preconditions.checkArgumentInRange(i, 0, 10000, "resultCountPerPage");
            resetIfBuilt();
            this.mResultCountPerPage = i;
            return this;
        }

        public Builder setRankingStrategy(int i) {
            Preconditions.checkArgumentInRange(i, 0, 8, "Result ranking strategy");
            resetIfBuilt();
            this.mRankingStrategy = i;
            this.mAdvancedRankingExpression = "";
            return this;
        }

        public Builder setRankingStrategy(String str) {
            Preconditions.checkStringNotEmpty(str);
            resetIfBuilt();
            this.mRankingStrategy = 9;
            this.mAdvancedRankingExpression = str;
            return this;
        }

        public Builder addInformationalRankingExpressions(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addInformationalRankingExpressions(Arrays.asList(strArr));
        }

        public Builder addInformationalRankingExpressions(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mInformationalRankingExpressions.addAll(collection);
            return this;
        }

        public Builder clearInformationalRankingExpressions() {
            resetIfBuilt();
            this.mInformationalRankingExpressions.clear();
            return this;
        }

        public Builder setSearchSourceLogTag(String str) {
            Preconditions.checkStringNotEmpty(str);
            Preconditions.checkArgument(str.length() <= 100, "The maximum supported tag length is 100. This tag is too long: " + str.length());
            resetIfBuilt();
            this.mSearchSourceLogTag = str;
            return this;
        }

        public Builder clearSearchSourceLogTag() {
            resetIfBuilt();
            this.mSearchSourceLogTag = null;
            return this;
        }

        public Builder setOrder(int i) {
            Preconditions.checkArgumentInRange(i, 0, 1, "Result ranking order");
            resetIfBuilt();
            this.mOrder = i;
            return this;
        }

        public Builder setSnippetCount(int i) {
            Preconditions.checkArgumentInRange(i, 0, 10000, "snippetCount");
            resetIfBuilt();
            this.mSnippetCount = i;
            return this;
        }

        public Builder setSnippetCountPerProperty(int i) {
            Preconditions.checkArgumentInRange(i, 0, 10000, "snippetCountPerProperty");
            resetIfBuilt();
            this.mSnippetCountPerProperty = i;
            return this;
        }

        public Builder setMaxSnippetSize(int i) {
            Preconditions.checkArgumentInRange(i, 0, 10000, "maxSnippetSize");
            resetIfBuilt();
            this.mMaxSnippetSize = i;
            return this;
        }

        public Builder setRetrieveEmbeddingMatchInfos(boolean z) {
            resetIfBuilt();
            this.mRetrieveEmbeddingMatchInfos = z;
            return this;
        }

        public Builder addProjection(String str, Collection<String> collection) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            ArrayList<String> arrayList = new ArrayList<>(collection.size());
            for (String str2 : collection) {
                Preconditions.checkNotNull(str2);
                arrayList.add(str2);
            }
            this.mProjectionTypePropertyMasks.putStringArrayList(str, arrayList);
            return this;
        }

        public Builder addProjectionPaths(String str, Collection<PropertyPath> collection) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(collection);
            ArrayList arrayList = new ArrayList(collection.size());
            Iterator<PropertyPath> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toString());
            }
            return addProjection(str, arrayList);
        }

        public Builder addProjectionsForDocumentClass(Class<?> cls, Collection<String> collection) throws AppSearchException {
            Preconditions.checkNotNull(cls);
            resetIfBuilt();
            return addProjection(DocumentClassFactoryRegistry.getInstance().getOrCreateFactory((Class) cls).getSchemaName(), collection);
        }

        public Builder addProjectionPathsForDocumentClass(Class<?> cls, Collection<PropertyPath> collection) throws AppSearchException {
            Preconditions.checkNotNull(cls);
            resetIfBuilt();
            ArrayList arrayList = new ArrayList(collection.size());
            Iterator<PropertyPath> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().toString());
            }
            return addProjectionsForDocumentClass(cls, arrayList);
        }

        public Builder clearProjections() {
            resetIfBuilt();
            this.mProjectionTypePropertyMasks.clear();
            return this;
        }

        public Builder setResultGrouping(int i, int i2) {
            Preconditions.checkState(i != 0, "Result grouping type cannot be zero.");
            resetIfBuilt();
            this.mGroupingTypeFlags = i;
            this.mGroupingLimit = i2;
            return this;
        }

        public Builder clearResultGrouping() {
            resetIfBuilt();
            this.mGroupingTypeFlags = 0;
            this.mGroupingLimit = 0;
            return this;
        }

        public Builder setPropertyWeights(String str, Map<String, Double> map) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(map);
            Bundle bundle = new Bundle();
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                String str2 = (String) Preconditions.checkNotNull(entry.getKey());
                Double d = (Double) Preconditions.checkNotNull(entry.getValue());
                if (d.doubleValue() <= 0.0d) {
                    throw new IllegalArgumentException("Cannot set non-positive property weight value " + d + " for property path: " + str2);
                }
                bundle.putDouble(str2, d.doubleValue());
            }
            this.mTypePropertyWeights.putBundle(str, bundle);
            return this;
        }

        public Builder clearPropertyWeights() {
            resetIfBuilt();
            this.mTypePropertyWeights.clear();
            return this;
        }

        public Builder setJoinSpec(JoinSpec joinSpec) {
            resetIfBuilt();
            this.mJoinSpec = (JoinSpec) Preconditions.checkNotNull(joinSpec);
            return this;
        }

        public Builder clearJoinSpec() {
            resetIfBuilt();
            this.mJoinSpec = null;
            return this;
        }

        public Builder setPropertyWeightPaths(String str, Map<PropertyPath, Double> map) {
            Preconditions.checkNotNull(map);
            ArrayMap arrayMap = new ArrayMap(map.size());
            for (Map.Entry<PropertyPath, Double> entry : map.entrySet()) {
                arrayMap.put(((PropertyPath) Preconditions.checkNotNull(entry.getKey())).toString(), entry.getValue());
            }
            return setPropertyWeights(str, arrayMap);
        }

        public Builder setPropertyWeightsForDocumentClass(Class<?> cls, Map<String, Double> map) throws AppSearchException {
            Preconditions.checkNotNull(cls);
            return setPropertyWeights(DocumentClassFactoryRegistry.getInstance().getOrCreateFactory((Class) cls).getSchemaName(), map);
        }

        public Builder setPropertyWeightPathsForDocumentClass(Class<?> cls, Map<PropertyPath, Double> map) throws AppSearchException {
            Preconditions.checkNotNull(cls);
            return setPropertyWeightPaths(DocumentClassFactoryRegistry.getInstance().getOrCreateFactory((Class) cls).getSchemaName(), map);
        }

        public Builder addEmbeddingParameters(EmbeddingVector... embeddingVectorArr) {
            Preconditions.checkNotNull(embeddingVectorArr);
            resetIfBuilt();
            return addEmbeddingParameters(Arrays.asList(embeddingVectorArr));
        }

        public Builder addEmbeddingParameters(Collection<EmbeddingVector> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mEmbeddingParameters.addAll(collection);
            return this;
        }

        public Builder clearEmbeddingParameters() {
            resetIfBuilt();
            this.mEmbeddingParameters.clear();
            return this;
        }

        public Builder setDefaultEmbeddingSearchMetricType(int i) {
            Preconditions.checkArgumentInRange(i, 1, 3, "Embedding search metric type");
            resetIfBuilt();
            this.mDefaultEmbeddingSearchMetricType = i;
            return this;
        }

        public Builder addSearchStringParameters(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addSearchStringParameters(Arrays.asList(strArr));
        }

        public Builder addSearchStringParameters(List<String> list) {
            Preconditions.checkNotNull(list);
            resetIfBuilt();
            this.mSearchStringParameters.addAll(list);
            return this;
        }

        public Builder clearSearchStringParameters() {
            resetIfBuilt();
            this.mSearchStringParameters.clear();
            return this;
        }

        public Builder setNumericSearchEnabled(boolean z) {
            modifyEnabledFeature("NUMERIC_SEARCH", z);
            return this;
        }

        public Builder setVerbatimSearchEnabled(boolean z) {
            modifyEnabledFeature("VERBATIM_SEARCH", z);
            return this;
        }

        public Builder setListFilterQueryLanguageEnabled(boolean z) {
            modifyEnabledFeature("LIST_FILTER_QUERY_LANGUAGE", z);
            return this;
        }

        public Builder setListFilterHasPropertyFunctionEnabled(boolean z) {
            modifyEnabledFeature("LIST_FILTER_HAS_PROPERTY_FUNCTION", z);
            return this;
        }

        public Builder setListFilterMatchScoreExpressionFunctionEnabled(boolean z) {
            modifyEnabledFeature("LIST_FILTER_MATCH_SCORE_EXPRESSION_FUNCTION", z);
            return this;
        }

        public Builder setScorablePropertyRankingEnabled(boolean z) {
            modifyEnabledFeature("SCHEMA_SCORABLE_PROPERTY_CONFIG", z);
            return this;
        }

        public SearchSpec build() {
            int i;
            JoinSpec joinSpec = this.mJoinSpec;
            if (joinSpec != null) {
                if (this.mRankingStrategy != 8 && joinSpec.getAggregationScoringStrategy() != 0) {
                    throw new IllegalStateException("Aggregate scoring strategy has been set in the nested JoinSpec, but ranking strategy is not RANKING_STRATEGY_JOIN_AGGREGATE_SCORE");
                }
            } else if (this.mRankingStrategy == 8) {
                throw new IllegalStateException("Attempting to rank based on joined documents, but no JoinSpec provided");
            }
            if (!this.mTypePropertyWeights.isEmpty() && (i = this.mRankingStrategy) != 3 && i != 9) {
                throw new IllegalArgumentException("Property weights are only compatible with the RANKING_STRATEGY_RELEVANCE_SCORE and RANKING_STRATEGY_ADVANCED_RANKING_EXPRESSION ranking strategies.");
            }
            this.mBuilt = true;
            return new SearchSpec(this.mTermMatchType, this.mSchemas, this.mNamespaces, this.mTypePropertyFilters, this.mPackageNames, this.mResultCountPerPage, this.mRankingStrategy, this.mOrder, this.mSnippetCount, this.mSnippetCountPerProperty, this.mMaxSnippetSize, this.mProjectionTypePropertyMasks, this.mGroupingTypeFlags, this.mGroupingLimit, this.mTypePropertyWeights, this.mJoinSpec, this.mAdvancedRankingExpression, new ArrayList(this.mEnabledFeatures), this.mSearchSourceLogTag, this.mEmbeddingParameters, this.mDefaultEmbeddingSearchMetricType, this.mInformationalRankingExpressions, this.mSearchStringParameters, this.mFilterDocumentIds, this.mRetrieveEmbeddingMatchInfos);
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mSchemas = new ArrayList(this.mSchemas);
                this.mTypePropertyFilters = BundleUtil.deepCopy(this.mTypePropertyFilters);
                this.mNamespaces = new ArrayList(this.mNamespaces);
                this.mPackageNames = new ArrayList(this.mPackageNames);
                this.mProjectionTypePropertyMasks = BundleUtil.deepCopy(this.mProjectionTypePropertyMasks);
                this.mTypePropertyWeights = BundleUtil.deepCopy(this.mTypePropertyWeights);
                this.mEmbeddingParameters = new ArrayList(this.mEmbeddingParameters);
                this.mInformationalRankingExpressions = new ArrayList(this.mInformationalRankingExpressions);
                this.mSearchStringParameters = new ArrayList(this.mSearchStringParameters);
                this.mFilterDocumentIds = new ArrayList(this.mFilterDocumentIds);
                this.mBuilt = false;
            }
        }

        private void modifyEnabledFeature(String str, boolean z) {
            resetIfBuilt();
            if (z) {
                this.mEnabledFeatures.add(str);
            } else {
                this.mEnabledFeatures.remove(str);
            }
        }
    }
}
