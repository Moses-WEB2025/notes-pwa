package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class RemoveByDocumentIdRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<RemoveByDocumentIdRequest> CREATOR = new StubCreators.RemoveByDocumentIdRequestCreator();
    final List<String> mIds;
    private Set<String> mIdsCached;
    private final String mNamespace;

    RemoveByDocumentIdRequest(String str, List<String> list) {
        this.mNamespace = (String) Objects.requireNonNull(str);
        this.mIds = (List) Objects.requireNonNull(list);
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

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.RemoveByDocumentIdRequestCreator.writeToParcel(this, parcel, i);
    }

    public static final class Builder {
        private final String mNamespace;
        private ArraySet<String> mIds = new ArraySet<>();
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

        public RemoveByDocumentIdRequest build() {
            this.mBuilt = true;
            return new RemoveByDocumentIdRequest(this.mNamespace, new ArrayList(this.mIds));
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mIds = new ArraySet<>((ArraySet) this.mIds);
                this.mBuilt = false;
            }
        }
    }
}
