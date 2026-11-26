package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes.dex */
public final class SetSchemaResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SetSchemaResponse> CREATOR = new StubCreators.SetSchemaResponseCreator();
    final List<String> mDeletedTypes;
    private Set<String> mDeletedTypesCached;
    final List<String> mIncompatibleTypes;
    private Set<String> mIncompatibleTypesCached;
    final List<String> mMigratedTypes;
    private Set<String> mMigratedTypesCached;
    private final List<MigrationFailure> mMigrationFailures;

    SetSchemaResponse(List<String> list, List<String> list2, List<String> list3) {
        this.mDeletedTypes = list;
        this.mIncompatibleTypes = list2;
        this.mMigratedTypes = list3;
        this.mMigrationFailures = Collections.emptyList();
    }

    SetSchemaResponse(List<String> list, List<String> list2, List<String> list3, List<MigrationFailure> list4) {
        this.mDeletedTypes = list;
        this.mIncompatibleTypes = list2;
        this.mMigratedTypes = list3;
        this.mMigrationFailures = (List) Preconditions.checkNotNull(list4);
    }

    public List<MigrationFailure> getMigrationFailures() {
        return Collections.unmodifiableList(this.mMigrationFailures);
    }

    public Set<String> getDeletedTypes() {
        if (this.mDeletedTypesCached == null) {
            this.mDeletedTypesCached = new ArraySet((Collection) Preconditions.checkNotNull(this.mDeletedTypes));
        }
        return Collections.unmodifiableSet(this.mDeletedTypesCached);
    }

    public Set<String> getMigratedTypes() {
        if (this.mMigratedTypesCached == null) {
            this.mMigratedTypesCached = new ArraySet((Collection) Preconditions.checkNotNull(this.mMigratedTypes));
        }
        return Collections.unmodifiableSet(this.mMigratedTypesCached);
    }

    public Set<String> getIncompatibleTypes() {
        if (this.mIncompatibleTypesCached == null) {
            this.mIncompatibleTypesCached = new ArraySet((Collection) Preconditions.checkNotNull(this.mIncompatibleTypes));
        }
        return Collections.unmodifiableSet(this.mIncompatibleTypesCached);
    }

    public static final class Builder {
        private List<MigrationFailure> mMigrationFailures = new ArrayList();
        private ArrayList<String> mDeletedTypes = new ArrayList<>();
        private ArrayList<String> mMigratedTypes = new ArrayList<>();
        private ArrayList<String> mIncompatibleTypes = new ArrayList<>();
        private boolean mBuilt = false;

        public Builder(SetSchemaResponse setSchemaResponse) {
            Preconditions.checkNotNull(setSchemaResponse);
            this.mDeletedTypes.addAll(setSchemaResponse.getDeletedTypes());
            this.mIncompatibleTypes.addAll(setSchemaResponse.getIncompatibleTypes());
            this.mMigratedTypes.addAll(setSchemaResponse.getMigratedTypes());
            this.mMigrationFailures.addAll(setSchemaResponse.getMigrationFailures());
        }

        public Builder() {
        }

        public Builder addMigrationFailures(Collection<MigrationFailure> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mMigrationFailures.addAll(collection);
            return this;
        }

        public Builder addMigrationFailure(MigrationFailure migrationFailure) {
            Preconditions.checkNotNull(migrationFailure);
            resetIfBuilt();
            this.mMigrationFailures.add(migrationFailure);
            return this;
        }

        public Builder addDeletedTypes(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mDeletedTypes.addAll(collection);
            return this;
        }

        public Builder addDeletedType(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            this.mDeletedTypes.add(str);
            return this;
        }

        public Builder addIncompatibleTypes(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mIncompatibleTypes.addAll(collection);
            return this;
        }

        public Builder addIncompatibleType(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            this.mIncompatibleTypes.add(str);
            return this;
        }

        public Builder addMigratedTypes(Collection<String> collection) {
            Preconditions.checkNotNull(collection);
            resetIfBuilt();
            this.mMigratedTypes.addAll(collection);
            return this;
        }

        public Builder addMigratedType(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            this.mMigratedTypes.add(str);
            return this;
        }

        public SetSchemaResponse build() {
            this.mBuilt = true;
            return new SetSchemaResponse(this.mDeletedTypes, this.mIncompatibleTypes, this.mMigratedTypes, this.mMigrationFailures);
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mMigrationFailures = new ArrayList(this.mMigrationFailures);
                this.mDeletedTypes = new ArrayList<>(this.mDeletedTypes);
                this.mMigratedTypes = new ArrayList<>(this.mMigratedTypes);
                this.mIncompatibleTypes = new ArrayList<>(this.mIncompatibleTypes);
                this.mBuilt = false;
            }
        }
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.SetSchemaResponseCreator.writeToParcel(this, parcel, i);
    }

    public static class MigrationFailure extends AbstractSafeParcelable {
        public static final Parcelable.Creator<MigrationFailure> CREATOR = new StubCreators.MigrationFailureCreator();
        private final String mDocumentId;
        final String mErrorMessage;
        private final String mNamespace;
        final int mResultCode;
        private final String mSchemaType;

        MigrationFailure(String str, String str2, String str3, String str4, int i) {
            this.mNamespace = str;
            this.mDocumentId = str2;
            this.mSchemaType = str3;
            this.mErrorMessage = str4;
            this.mResultCode = i;
        }

        public MigrationFailure(String str, String str2, String str3, AppSearchResult<?> appSearchResult) {
            this.mNamespace = str;
            this.mDocumentId = str2;
            this.mSchemaType = str3;
            Preconditions.checkNotNull(appSearchResult);
            Preconditions.checkArgument(!appSearchResult.isSuccess(), "failedResult was actually successful");
            this.mErrorMessage = appSearchResult.getErrorMessage();
            this.mResultCode = appSearchResult.getResultCode();
        }

        public String getNamespace() {
            return this.mNamespace;
        }

        public String getDocumentId() {
            return this.mDocumentId;
        }

        public String getSchemaType() {
            return this.mSchemaType;
        }

        public AppSearchResult<Void> getAppSearchResult() {
            return AppSearchResult.newFailedResult(this.mResultCode, this.mErrorMessage);
        }

        public String toString() {
            return "MigrationFailure { schemaType: " + getSchemaType() + ", namespace: " + getNamespace() + ", documentId: " + getDocumentId() + ", appSearchResult: " + getAppSearchResult().toString() + StringSubstitutor.DEFAULT_VAR_END;
        }

        @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
        public void writeToParcel(Parcel parcel, int i) {
            StubCreators.MigrationFailureCreator.writeToParcel(this, parcel, i);
        }
    }
}
