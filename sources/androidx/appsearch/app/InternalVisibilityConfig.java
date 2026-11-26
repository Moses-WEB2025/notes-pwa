package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArraySet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class InternalVisibilityConfig extends AbstractSafeParcelable {
    public static final Parcelable.Creator<InternalVisibilityConfig> CREATOR = new StubCreators.InternalVisibilityConfigCreator();
    private final boolean mIsNotDisplayedBySystem;
    private final String mSchemaType;
    private final SchemaVisibilityConfig mVisibilityConfig;
    final List<SchemaVisibilityConfig> mVisibleToConfigs;

    public static List<InternalVisibilityConfig> toInternalVisibilityConfigs(SetSchemaRequest setSchemaRequest) {
        Set<AppSearchSchema> schemas = setSchemaRequest.getSchemas();
        Set<String> schemasNotDisplayedBySystem = setSchemaRequest.getSchemasNotDisplayedBySystem();
        Map<String, Set<PackageIdentifier>> schemasVisibleToPackages = setSchemaRequest.getSchemasVisibleToPackages();
        Map<String, Set<Set<Integer>>> requiredPermissionsForSchemaTypeVisibility = setSchemaRequest.getRequiredPermissionsForSchemaTypeVisibility();
        Map<String, PackageIdentifier> publiclyVisibleSchemas = setSchemaRequest.getPubliclyVisibleSchemas();
        Map<String, Set<SchemaVisibilityConfig>> schemasVisibleToConfigs = setSchemaRequest.getSchemasVisibleToConfigs();
        ArrayList arrayList = new ArrayList(schemas.size());
        Iterator<AppSearchSchema> it = schemas.iterator();
        while (it.hasNext()) {
            String schemaType = it.next().getSchemaType();
            Builder notDisplayedBySystem = new Builder(schemaType).setNotDisplayedBySystem(schemasNotDisplayedBySystem.contains(schemaType));
            Set<PackageIdentifier> set = schemasVisibleToPackages.get(schemaType);
            if (set != null) {
                Iterator<PackageIdentifier> it2 = set.iterator();
                while (it2.hasNext()) {
                    notDisplayedBySystem.addVisibleToPackage(it2.next());
                }
            }
            Set<Set<Integer>> set2 = requiredPermissionsForSchemaTypeVisibility.get(schemaType);
            if (set2 != null) {
                Iterator<Set<Integer>> it3 = set2.iterator();
                while (it3.hasNext()) {
                    notDisplayedBySystem.addVisibleToPermissions(it3.next());
                }
            }
            PackageIdentifier packageIdentifier = publiclyVisibleSchemas.get(schemaType);
            if (packageIdentifier != null) {
                notDisplayedBySystem.setPubliclyVisibleTargetPackage(packageIdentifier);
            }
            Set<SchemaVisibilityConfig> set3 = schemasVisibleToConfigs.get(schemaType);
            if (set3 != null) {
                Iterator<SchemaVisibilityConfig> it4 = set3.iterator();
                while (it4.hasNext()) {
                    notDisplayedBySystem.addVisibleToConfig(it4.next());
                }
            }
            arrayList.add(notDisplayedBySystem.build());
        }
        return arrayList;
    }

    public static List<InternalVisibilityConfig> toInternalVisibilityConfigs(SetBlobVisibilityRequest setBlobVisibilityRequest) {
        Set<String> namespacesNotDisplayedBySystem = setBlobVisibilityRequest.getNamespacesNotDisplayedBySystem();
        Map<String, Set<SchemaVisibilityConfig>> namespacesVisibleToConfigs = setBlobVisibilityRequest.getNamespacesVisibleToConfigs();
        ArraySet<String> arraySet = new ArraySet(namespacesNotDisplayedBySystem);
        arraySet.addAll(namespacesVisibleToConfigs.keySet());
        ArrayList arrayList = new ArrayList();
        for (String str : arraySet) {
            Builder notDisplayedBySystem = new Builder(str).setNotDisplayedBySystem(namespacesNotDisplayedBySystem.contains(str));
            Set<SchemaVisibilityConfig> set = namespacesVisibleToConfigs.get(str);
            if (set != null) {
                Iterator<SchemaVisibilityConfig> it = set.iterator();
                while (it.hasNext()) {
                    notDisplayedBySystem.addVisibleToConfig(it.next());
                }
            }
            arrayList.add(notDisplayedBySystem.build());
        }
        return arrayList;
    }

    InternalVisibilityConfig(String str, boolean z, SchemaVisibilityConfig schemaVisibilityConfig, List<SchemaVisibilityConfig> list) {
        this.mIsNotDisplayedBySystem = z;
        this.mSchemaType = (String) Objects.requireNonNull(str);
        this.mVisibilityConfig = (SchemaVisibilityConfig) Objects.requireNonNull(schemaVisibilityConfig);
        this.mVisibleToConfigs = (List) Objects.requireNonNull(list);
    }

    public String getSchemaType() {
        return this.mSchemaType;
    }

    public boolean isNotDisplayedBySystem() {
        return this.mIsNotDisplayedBySystem;
    }

    public SchemaVisibilityConfig getVisibilityConfig() {
        return this.mVisibilityConfig;
    }

    public Set<SchemaVisibilityConfig> getVisibleToConfigs() {
        return new ArraySet(this.mVisibleToConfigs);
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.InternalVisibilityConfigCreator.writeToParcel(this, parcel, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof InternalVisibilityConfig)) {
            return false;
        }
        InternalVisibilityConfig internalVisibilityConfig = (InternalVisibilityConfig) obj;
        return this.mIsNotDisplayedBySystem == internalVisibilityConfig.mIsNotDisplayedBySystem && Objects.equals(this.mSchemaType, internalVisibilityConfig.mSchemaType) && Objects.equals(this.mVisibilityConfig, internalVisibilityConfig.mVisibilityConfig) && Objects.equals(this.mVisibleToConfigs, internalVisibilityConfig.mVisibleToConfigs);
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mIsNotDisplayedBySystem), this.mSchemaType, this.mVisibilityConfig, this.mVisibleToConfigs);
    }

    public static final class Builder {
        private boolean mBuilt;
        private boolean mIsNotDisplayedBySystem;
        private String mSchemaType;
        private SchemaVisibilityConfig.Builder mVisibilityConfigBuilder;
        private List<SchemaVisibilityConfig> mVisibleToConfigs;

        public Builder(String str) {
            this.mVisibleToConfigs = new ArrayList();
            this.mSchemaType = (String) Objects.requireNonNull(str);
            this.mVisibilityConfigBuilder = new SchemaVisibilityConfig.Builder();
        }

        public Builder(InternalVisibilityConfig internalVisibilityConfig) {
            this.mVisibleToConfigs = new ArrayList();
            Objects.requireNonNull(internalVisibilityConfig);
            this.mSchemaType = internalVisibilityConfig.mSchemaType;
            this.mIsNotDisplayedBySystem = internalVisibilityConfig.mIsNotDisplayedBySystem;
            this.mVisibilityConfigBuilder = new SchemaVisibilityConfig.Builder(internalVisibilityConfig.getVisibilityConfig());
            this.mVisibleToConfigs = internalVisibilityConfig.mVisibleToConfigs;
        }

        public Builder setSchemaType(String str) {
            resetIfBuilt();
            this.mSchemaType = (String) Objects.requireNonNull(str);
            return this;
        }

        public Builder setVisibilityConfig(SchemaVisibilityConfig schemaVisibilityConfig) {
            resetIfBuilt();
            this.mVisibilityConfigBuilder = new SchemaVisibilityConfig.Builder(schemaVisibilityConfig);
            return this;
        }

        public Builder setNotDisplayedBySystem(boolean z) {
            resetIfBuilt();
            this.mIsNotDisplayedBySystem = z;
            return this;
        }

        public Builder addVisibleToPackage(PackageIdentifier packageIdentifier) {
            resetIfBuilt();
            this.mVisibilityConfigBuilder.addAllowedPackage(packageIdentifier);
            return this;
        }

        public Builder clearVisibleToPackages() {
            resetIfBuilt();
            this.mVisibilityConfigBuilder.clearAllowedPackages();
            return this;
        }

        public Builder addVisibleToPermissions(Set<Integer> set) {
            resetIfBuilt();
            this.mVisibilityConfigBuilder.addRequiredPermissions(set);
            return this;
        }

        public Builder clearVisibleToPermissions() {
            resetIfBuilt();
            this.mVisibilityConfigBuilder.clearRequiredPermissions();
            return this;
        }

        public Builder setPubliclyVisibleTargetPackage(PackageIdentifier packageIdentifier) {
            resetIfBuilt();
            this.mVisibilityConfigBuilder.setPubliclyVisibleTargetPackage(packageIdentifier);
            return this;
        }

        public Builder addVisibleToConfig(SchemaVisibilityConfig schemaVisibilityConfig) {
            Objects.requireNonNull(schemaVisibilityConfig);
            resetIfBuilt();
            this.mVisibleToConfigs.add(schemaVisibilityConfig);
            return this;
        }

        public Builder clearVisibleToConfig() {
            resetIfBuilt();
            this.mVisibleToConfigs.clear();
            return this;
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mVisibleToConfigs = new ArrayList(this.mVisibleToConfigs);
                this.mBuilt = false;
            }
        }

        public InternalVisibilityConfig build() {
            this.mBuilt = true;
            return new InternalVisibilityConfig(this.mSchemaType, this.mIsNotDisplayedBySystem, this.mVisibilityConfigBuilder.build(), this.mVisibleToConfigs);
        }
    }
}
