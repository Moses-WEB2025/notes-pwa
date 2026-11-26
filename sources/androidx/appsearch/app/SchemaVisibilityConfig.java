package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.PackageIdentifierParcel;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArraySet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class SchemaVisibilityConfig extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SchemaVisibilityConfig> CREATOR = new StubCreators.VisibilityConfigCreator();
    final List<PackageIdentifierParcel> mAllowedPackages;
    private List<PackageIdentifier> mAllowedPackagesCached;
    private Integer mHashCode;
    final PackageIdentifierParcel mPubliclyVisibleTargetPackage;
    final List<VisibilityPermissionConfig> mRequiredPermissions;
    private Set<Set<Integer>> mRequiredPermissionsCached;

    SchemaVisibilityConfig(List<PackageIdentifierParcel> list, List<VisibilityPermissionConfig> list2, PackageIdentifierParcel packageIdentifierParcel) {
        this.mAllowedPackages = (List) Objects.requireNonNull(list);
        this.mRequiredPermissions = (List) Objects.requireNonNull(list2);
        this.mPubliclyVisibleTargetPackage = packageIdentifierParcel;
    }

    public List<PackageIdentifier> getAllowedPackages() {
        if (this.mAllowedPackagesCached == null) {
            this.mAllowedPackagesCached = new ArrayList(this.mAllowedPackages.size());
            for (int i = 0; i < this.mAllowedPackages.size(); i++) {
                this.mAllowedPackagesCached.add(new PackageIdentifier(this.mAllowedPackages.get(i)));
            }
        }
        return this.mAllowedPackagesCached;
    }

    public Set<Set<Integer>> getRequiredPermissions() {
        if (this.mRequiredPermissionsCached == null) {
            this.mRequiredPermissionsCached = new ArraySet(this.mRequiredPermissions.size());
            for (int i = 0; i < this.mRequiredPermissions.size(); i++) {
                Set<Integer> allRequiredPermissions = this.mRequiredPermissions.get(i).getAllRequiredPermissions();
                Set<Set<Integer>> set = this.mRequiredPermissionsCached;
                if (set != null && allRequiredPermissions != null) {
                    set.add(allRequiredPermissions);
                }
            }
        }
        return (Set) Objects.requireNonNull(this.mRequiredPermissionsCached);
    }

    public PackageIdentifier getPubliclyVisibleTargetPackage() {
        if (this.mPubliclyVisibleTargetPackage == null) {
            return null;
        }
        return new PackageIdentifier(this.mPubliclyVisibleTargetPackage);
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.VisibilityConfigCreator.writeToParcel(this, parcel, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SchemaVisibilityConfig)) {
            return false;
        }
        SchemaVisibilityConfig schemaVisibilityConfig = (SchemaVisibilityConfig) obj;
        return Objects.equals(this.mAllowedPackages, schemaVisibilityConfig.mAllowedPackages) && Objects.equals(this.mRequiredPermissions, schemaVisibilityConfig.mRequiredPermissions) && Objects.equals(this.mPubliclyVisibleTargetPackage, schemaVisibilityConfig.mPubliclyVisibleTargetPackage);
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(Objects.hash(this.mAllowedPackages, this.mRequiredPermissions, this.mPubliclyVisibleTargetPackage));
        }
        return this.mHashCode.intValue();
    }

    public static final class Builder {
        private List<PackageIdentifierParcel> mAllowedPackages;
        private boolean mBuilt;
        private PackageIdentifierParcel mPubliclyVisibleTargetPackage;
        private List<VisibilityPermissionConfig> mRequiredPermissions;

        public Builder() {
            this.mAllowedPackages = new ArrayList();
            this.mRequiredPermissions = new ArrayList();
        }

        public Builder(SchemaVisibilityConfig schemaVisibilityConfig) {
            this.mAllowedPackages = new ArrayList();
            this.mRequiredPermissions = new ArrayList();
            Objects.requireNonNull(schemaVisibilityConfig);
            this.mAllowedPackages = new ArrayList(schemaVisibilityConfig.mAllowedPackages);
            this.mRequiredPermissions = new ArrayList(schemaVisibilityConfig.mRequiredPermissions);
            this.mPubliclyVisibleTargetPackage = schemaVisibilityConfig.mPubliclyVisibleTargetPackage;
        }

        public Builder addAllowedPackage(PackageIdentifier packageIdentifier) {
            Objects.requireNonNull(packageIdentifier);
            resetIfBuilt();
            this.mAllowedPackages.add(packageIdentifier.getPackageIdentifierParcel());
            return this;
        }

        public Builder clearAllowedPackages() {
            resetIfBuilt();
            this.mAllowedPackages.clear();
            return this;
        }

        public Builder addRequiredPermissions(Set<Integer> set) {
            Objects.requireNonNull(set);
            resetIfBuilt();
            this.mRequiredPermissions.add(new VisibilityPermissionConfig(set));
            return this;
        }

        public Builder clearRequiredPermissions() {
            resetIfBuilt();
            this.mRequiredPermissions.clear();
            return this;
        }

        public Builder setPubliclyVisibleTargetPackage(PackageIdentifier packageIdentifier) {
            resetIfBuilt();
            if (packageIdentifier == null) {
                this.mPubliclyVisibleTargetPackage = null;
                return this;
            }
            this.mPubliclyVisibleTargetPackage = packageIdentifier.getPackageIdentifierParcel();
            return this;
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mAllowedPackages = new ArrayList(this.mAllowedPackages);
                this.mRequiredPermissions = new ArrayList(this.mRequiredPermissions);
                this.mBuilt = false;
            }
        }

        public SchemaVisibilityConfig build() {
            this.mBuilt = true;
            return new SchemaVisibilityConfig(this.mAllowedPackages, this.mRequiredPermissions, this.mPubliclyVisibleTargetPackage);
        }
    }
}
