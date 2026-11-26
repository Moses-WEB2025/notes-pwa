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
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class SearchSuggestionSpec extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SearchSuggestionSpec> CREATOR = new StubCreators.SearchSuggestionSpecCreator();
    public static final int SUGGESTION_RANKING_STRATEGY_DOCUMENT_COUNT = 0;
    public static final int SUGGESTION_RANKING_STRATEGY_NONE = 2;
    public static final int SUGGESTION_RANKING_STRATEGY_TERM_FREQUENCY = 1;
    final Bundle mFilterDocumentIds;
    private final List<String> mFilterNamespaces;
    final Bundle mFilterProperties;
    private final List<String> mFilterSchemas;
    private final int mMaximumResultCount;
    private final int mRankingStrategy;
    private final List<String> mSearchStringParameters;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SuggestionRankingStrategy {
    }

    public SearchSuggestionSpec(List<String> list, List<String> list2, Bundle bundle, Bundle bundle2, int i, int i2, List<String> list3) {
        List<String> listEmptyList;
        Preconditions.checkArgument(i2 >= 1, "MaximumResultCount must be positive.");
        this.mFilterNamespaces = (List) Preconditions.checkNotNull(list);
        this.mFilterSchemas = (List) Preconditions.checkNotNull(list2);
        this.mFilterProperties = (Bundle) Preconditions.checkNotNull(bundle);
        this.mFilterDocumentIds = (Bundle) Preconditions.checkNotNull(bundle2);
        this.mRankingStrategy = i;
        this.mMaximumResultCount = i2;
        if (list3 != null) {
            listEmptyList = Collections.unmodifiableList(list3);
        } else {
            listEmptyList = Collections.emptyList();
        }
        this.mSearchStringParameters = listEmptyList;
    }

    public int getMaximumResultCount() {
        return this.mMaximumResultCount;
    }

    public List<String> getFilterNamespaces() {
        List<String> list = this.mFilterNamespaces;
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list);
    }

    public int getRankingStrategy() {
        return this.mRankingStrategy;
    }

    public List<String> getFilterSchemas() {
        List<String> list = this.mFilterSchemas;
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list);
    }

    public Map<String, List<String>> getFilterProperties() {
        Set<String> setKeySet = this.mFilterProperties.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            arrayMap.put(str, (List) Preconditions.checkNotNull(this.mFilterProperties.getStringArrayList(str)));
        }
        return arrayMap;
    }

    public Map<String, List<String>> getFilterDocumentIds() {
        Set<String> setKeySet = this.mFilterDocumentIds.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            arrayMap.put(str, (List) Preconditions.checkNotNull(this.mFilterDocumentIds.getStringArrayList(str)));
        }
        return arrayMap;
    }

    public List<String> getSearchStringParameters() {
        return this.mSearchStringParameters;
    }

    public static final class Builder {
        private final int mTotalResultCount;
        private ArrayList<String> mNamespaces = new ArrayList<>();
        private ArrayList<String> mSchemas = new ArrayList<>();
        private Bundle mTypePropertyFilters = new Bundle();
        private Bundle mDocumentIds = new Bundle();
        private int mRankingStrategy = 0;
        private List<String> mSearchStringParameters = new ArrayList();
        private boolean mBuilt = false;

        public Builder(int i) {
            Preconditions.checkArgument(i >= 1, "maximumResultCount must be positive.");
            this.mTotalResultCount = i;
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

        public Builder setRankingStrategy(int i) {
            Preconditions.checkArgumentInRange(i, 0, 2, "Suggestion ranking strategy");
            resetIfBuilt();
            this.mRankingStrategy = i;
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

        public Builder addFilterDocumentClasses(Class<?>... clsArr) throws AppSearchException {
            Preconditions.checkNotNull(clsArr);
            resetIfBuilt();
            return addFilterDocumentClasses(Arrays.asList(clsArr));
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

        public Builder addFilterDocumentIds(String str, String... strArr) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addFilterDocumentIds(str, Arrays.asList(strArr));
        }

        public Builder addFilterDocumentIds(String str, Collection<String> collection) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            ArrayList<String> arrayList = new ArrayList<>(collection.size());
            Iterator<String> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add((String) Preconditions.checkNotNull(it.next()));
            }
            this.mDocumentIds.putStringArrayList(str, arrayList);
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

        public SearchSuggestionSpec build() {
            if (!this.mSchemas.isEmpty()) {
                ArraySet arraySet = new ArraySet(this.mSchemas);
                for (String str : this.mTypePropertyFilters.keySet()) {
                    if (!arraySet.contains(str)) {
                        throw new IllegalStateException("The schema: " + str + " exists in the property filter but doesn't exist in the schema filter.");
                    }
                }
            }
            if (!this.mNamespaces.isEmpty()) {
                ArraySet arraySet2 = new ArraySet(this.mNamespaces);
                for (String str2 : this.mDocumentIds.keySet()) {
                    if (!arraySet2.contains(str2)) {
                        throw new IllegalStateException("The namespace: " + str2 + " exists in the document id filter but doesn't exist in the namespace filter.");
                    }
                }
            }
            this.mBuilt = true;
            return new SearchSuggestionSpec(this.mNamespaces, this.mSchemas, this.mTypePropertyFilters, this.mDocumentIds, this.mRankingStrategy, this.mTotalResultCount, this.mSearchStringParameters);
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mNamespaces = new ArrayList<>(this.mNamespaces);
                this.mSchemas = new ArrayList<>(this.mSchemas);
                this.mTypePropertyFilters = BundleUtil.deepCopy(this.mTypePropertyFilters);
                this.mDocumentIds = BundleUtil.deepCopy(this.mDocumentIds);
                this.mSearchStringParameters = new ArrayList(this.mSearchStringParameters);
                this.mBuilt = false;
            }
        }
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.SearchSuggestionSpecCreator.writeToParcel(this, parcel, i);
    }
}
