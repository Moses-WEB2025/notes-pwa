package androidx.appsearch.observer;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.app.DocumentClassFactoryRegistry;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class ObserverSpec extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ObserverSpec> CREATOR = new StubCreators.ObserverSpecCreator();
    final List<String> mFilterSchemas;
    private volatile Set<String> mFilterSchemasCached;

    public ObserverSpec(List<String> list) {
        this.mFilterSchemas = (List) Preconditions.checkNotNull(list);
    }

    public Set<String> getFilterSchemas() {
        if (this.mFilterSchemasCached == null) {
            if (this.mFilterSchemas == null) {
                this.mFilterSchemasCached = Collections.emptySet();
            } else {
                this.mFilterSchemasCached = Collections.unmodifiableSet(new ArraySet(this.mFilterSchemas));
            }
        }
        return this.mFilterSchemasCached;
    }

    public static final class Builder {
        private ArrayList<String> mFilterSchemas = new ArrayList<>();
        private boolean mBuilt = false;

        public Builder addFilterSchemas(String... strArr) {
            Preconditions.checkNotNull(strArr);
            resetIfBuilt();
            return addFilterSchemas(Arrays.asList(strArr));
        }

        public Builder addFilterSchemas(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mFilterSchemas.addAll(collection);
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

        public ObserverSpec build() {
            this.mBuilt = true;
            return new ObserverSpec(this.mFilterSchemas);
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mFilterSchemas = new ArrayList<>(this.mFilterSchemas);
                this.mBuilt = false;
            }
        }
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.ObserverSpecCreator.writeToParcel(this, parcel, i);
    }
}
