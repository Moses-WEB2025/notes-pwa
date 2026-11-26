package androidx.appsearch.app;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.appsearch.util.BundleUtil;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
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
public final class GetByDocumentIdRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GetByDocumentIdRequest> CREATOR = new StubCreators.GetByDocumentIdRequestCreator();
    public static final String PROJECTION_SCHEMA_TYPE_WILDCARD = "*";
    final List<String> mIds;
    private Set<String> mIdsCached;
    private final String mNamespace;
    final Bundle mTypePropertyPaths;

    GetByDocumentIdRequest(String str, List<String> list, Bundle bundle) {
        this.mNamespace = (String) Objects.requireNonNull(str);
        this.mIds = (List) Objects.requireNonNull(list);
        this.mTypePropertyPaths = (Bundle) Objects.requireNonNull(bundle);
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public Set<String> getIds() {
        if (this.mIdsCached == null) {
            this.mIdsCached = Collections.unmodifiableSet(new ArraySet(this.mIds));
        }
        return this.mIdsCached;
    }

    public Map<String, List<String>> getProjections() {
        Set<String> setKeySet = this.mTypePropertyPaths.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            ArrayList<String> stringArrayList = this.mTypePropertyPaths.getStringArrayList(str);
            if (stringArrayList != null) {
                arrayMap.put(str, Collections.unmodifiableList(stringArrayList));
            }
        }
        return arrayMap;
    }

    public Map<String, List<PropertyPath>> getProjectionPaths() {
        Set<String> setKeySet = this.mTypePropertyPaths.keySet();
        ArrayMap arrayMap = new ArrayMap(setKeySet.size());
        for (String str : setKeySet) {
            ArrayList<String> stringArrayList = this.mTypePropertyPaths.getStringArrayList(str);
            if (stringArrayList != null) {
                int size = stringArrayList.size();
                ArrayList arrayList = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    arrayList.add(new PropertyPath(stringArrayList.get(i)));
                }
                arrayMap.put(str, Collections.unmodifiableList(arrayList));
            }
        }
        return arrayMap;
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.GetByDocumentIdRequestCreator.writeToParcel(this, parcel, i);
    }

    public static final class Builder {
        private final String mNamespace;
        private List<String> mIds = new ArrayList();
        private Bundle mProjectionTypePropertyPaths = new Bundle();
        private boolean mBuilt = false;

        public Builder(String str) {
            this.mNamespace = (String) Preconditions.checkNotNull(str);
        }

        public Builder addIds(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addIds(Arrays.asList(strArr));
        }

        public Builder addIds(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mIds.addAll(collection);
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
            this.mProjectionTypePropertyPaths.putStringArrayList(str, arrayList);
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

        public GetByDocumentIdRequest build() {
            this.mBuilt = true;
            return new GetByDocumentIdRequest(this.mNamespace, this.mIds, this.mProjectionTypePropertyPaths);
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mIds = new ArrayList(this.mIds);
                this.mProjectionTypePropertyPaths = BundleUtil.deepCopy(this.mProjectionTypePropertyPaths);
                this.mBuilt = false;
            }
        }
    }
}
