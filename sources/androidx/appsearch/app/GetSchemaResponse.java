package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class GetSchemaResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GetSchemaResponse> CREATOR = new StubCreators.GetSchemaResponseCreator();
    private Map<String, PackageIdentifier> mPubliclyVisibleSchemasCached;
    final List<AppSearchSchema> mSchemas;
    private Set<AppSearchSchema> mSchemasCached;
    private Set<String> mSchemasNotDisplayedBySystemCached;
    private Map<String, Set<SchemaVisibilityConfig>> mSchemasVisibleToConfigsCached;
    private Map<String, Set<PackageIdentifier>> mSchemasVisibleToPackagesCached;
    private Map<String, Set<Set<Integer>>> mSchemasVisibleToPermissionsCached;
    private final int mVersion;
    final List<InternalVisibilityConfig> mVisibilityConfigs;

    GetSchemaResponse(int i, List<AppSearchSchema> list, List<InternalVisibilityConfig> list2) {
        this.mVersion = i;
        this.mSchemas = (List) Preconditions.checkNotNull(list);
        this.mVisibilityConfigs = list2;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public Set<AppSearchSchema> getSchemas() {
        if (this.mSchemasCached == null) {
            this.mSchemasCached = Collections.unmodifiableSet(new ArraySet(this.mSchemas));
        }
        return this.mSchemasCached;
    }

    public Set<String> getSchemaTypesNotDisplayedBySystem() {
        List<InternalVisibilityConfig> visibilityConfigsOrThrow = getVisibilityConfigsOrThrow();
        if (this.mSchemasNotDisplayedBySystemCached == null) {
            ArraySet arraySet = new ArraySet();
            for (int i = 0; i < visibilityConfigsOrThrow.size(); i++) {
                if (visibilityConfigsOrThrow.get(i).isNotDisplayedBySystem()) {
                    arraySet.add(visibilityConfigsOrThrow.get(i).getSchemaType());
                }
            }
            this.mSchemasNotDisplayedBySystemCached = Collections.unmodifiableSet(arraySet);
        }
        return this.mSchemasNotDisplayedBySystemCached;
    }

    public Map<String, Set<PackageIdentifier>> getSchemaTypesVisibleToPackages() {
        List<InternalVisibilityConfig> visibilityConfigsOrThrow = getVisibilityConfigsOrThrow();
        if (this.mSchemasVisibleToPackagesCached == null) {
            ArrayMap arrayMap = new ArrayMap();
            for (int i = 0; i < visibilityConfigsOrThrow.size(); i++) {
                InternalVisibilityConfig internalVisibilityConfig = visibilityConfigsOrThrow.get(i);
                List<PackageIdentifier> allowedPackages = internalVisibilityConfig.getVisibilityConfig().getAllowedPackages();
                if (!allowedPackages.isEmpty()) {
                    arrayMap.put(internalVisibilityConfig.getSchemaType(), Collections.unmodifiableSet(new ArraySet(allowedPackages)));
                }
            }
            this.mSchemasVisibleToPackagesCached = Collections.unmodifiableMap(arrayMap);
        }
        return this.mSchemasVisibleToPackagesCached;
    }

    public Map<String, Set<Set<Integer>>> getRequiredPermissionsForSchemaTypeVisibility() {
        List<InternalVisibilityConfig> visibilityConfigsOrThrow = getVisibilityConfigsOrThrow();
        if (this.mSchemasVisibleToPermissionsCached == null) {
            ArrayMap arrayMap = new ArrayMap();
            for (int i = 0; i < visibilityConfigsOrThrow.size(); i++) {
                InternalVisibilityConfig internalVisibilityConfig = visibilityConfigsOrThrow.get(i);
                Set<Set<Integer>> requiredPermissions = internalVisibilityConfig.getVisibilityConfig().getRequiredPermissions();
                if (!requiredPermissions.isEmpty()) {
                    arrayMap.put(internalVisibilityConfig.getSchemaType(), Collections.unmodifiableSet(requiredPermissions));
                }
            }
            this.mSchemasVisibleToPermissionsCached = Collections.unmodifiableMap(arrayMap);
        }
        return this.mSchemasVisibleToPermissionsCached;
    }

    public Map<String, PackageIdentifier> getPubliclyVisibleSchemas() {
        List<InternalVisibilityConfig> visibilityConfigsOrThrow = getVisibilityConfigsOrThrow();
        if (this.mPubliclyVisibleSchemasCached == null) {
            ArrayMap arrayMap = new ArrayMap();
            for (int i = 0; i < visibilityConfigsOrThrow.size(); i++) {
                InternalVisibilityConfig internalVisibilityConfig = visibilityConfigsOrThrow.get(i);
                PackageIdentifier publiclyVisibleTargetPackage = internalVisibilityConfig.getVisibilityConfig().getPubliclyVisibleTargetPackage();
                if (publiclyVisibleTargetPackage != null) {
                    arrayMap.put(internalVisibilityConfig.getSchemaType(), publiclyVisibleTargetPackage);
                }
            }
            this.mPubliclyVisibleSchemasCached = Collections.unmodifiableMap(arrayMap);
        }
        return this.mPubliclyVisibleSchemasCached;
    }

    public Map<String, Set<SchemaVisibilityConfig>> getSchemaTypesVisibleToConfigs() {
        List<InternalVisibilityConfig> visibilityConfigsOrThrow = getVisibilityConfigsOrThrow();
        if (this.mSchemasVisibleToConfigsCached == null) {
            ArrayMap arrayMap = new ArrayMap();
            for (int i = 0; i < visibilityConfigsOrThrow.size(); i++) {
                InternalVisibilityConfig internalVisibilityConfig = visibilityConfigsOrThrow.get(i);
                Set<SchemaVisibilityConfig> visibleToConfigs = internalVisibilityConfig.getVisibleToConfigs();
                if (!visibleToConfigs.isEmpty()) {
                    arrayMap.put(internalVisibilityConfig.getSchemaType(), Collections.unmodifiableSet(visibleToConfigs));
                }
            }
            this.mSchemasVisibleToConfigsCached = Collections.unmodifiableMap(arrayMap);
        }
        return this.mSchemasVisibleToConfigsCached;
    }

    private List<InternalVisibilityConfig> getVisibilityConfigsOrThrow() {
        List<InternalVisibilityConfig> list = this.mVisibilityConfigs;
        if (list != null) {
            return list;
        }
        throw new UnsupportedOperationException("Get visibility setting is not supported with this backend/Android API level combination.");
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.GetSchemaResponseCreator.writeToParcel(this, parcel, i);
    }

    public static final class Builder {
        private boolean mBuilt;
        private ArrayList<AppSearchSchema> mSchemas;
        private int mVersion;
        private Map<String, InternalVisibilityConfig.Builder> mVisibilityConfigBuilders;

        public Builder() {
            this.mVersion = 0;
            this.mSchemas = new ArrayList<>();
            this.mBuilt = false;
            setVisibilitySettingSupported(true);
        }

        public Builder(GetSchemaResponse getSchemaResponse) {
            this.mVersion = 0;
            this.mSchemas = new ArrayList<>();
            this.mBuilt = false;
            setVisibilitySettingSupported(true);
            this.mVersion = getSchemaResponse.mVersion;
            this.mSchemas.addAll(getSchemaResponse.mSchemas);
            if (getSchemaResponse.mVisibilityConfigs != null) {
                int size = getSchemaResponse.mVisibilityConfigs.size();
                for (int i = 0; i < size; i++) {
                    InternalVisibilityConfig internalVisibilityConfig = getSchemaResponse.mVisibilityConfigs.get(i);
                    this.mVisibilityConfigBuilders.put(internalVisibilityConfig.getSchemaType(), new InternalVisibilityConfig.Builder(internalVisibilityConfig));
                }
            }
        }

        public Builder setVersion(int i) {
            Preconditions.checkArgument(i >= 0, "Version must be a non-negative number.");
            resetIfBuilt();
            this.mVersion = i;
            return this;
        }

        public Builder addSchema(AppSearchSchema appSearchSchema) {
            Preconditions.checkNotNull(appSearchSchema);
            resetIfBuilt();
            this.mSchemas.add(appSearchSchema);
            return this;
        }

        public Builder clearSchemas() {
            resetIfBuilt();
            this.mSchemas.clear();
            return this;
        }

        public Builder addSchemaTypeNotDisplayedBySystem(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            getOrCreateVisibilityConfigBuilder(str).setNotDisplayedBySystem(true);
            return this;
        }

        public Builder clearSchemaTypeNotDisplayedBySystem(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            InternalVisibilityConfig.Builder visibilityConfigBuilder = getVisibilityConfigBuilder(str);
            if (visibilityConfigBuilder != null) {
                visibilityConfigBuilder.setNotDisplayedBySystem(false);
            }
            return this;
        }

        public Builder setSchemaTypeVisibleToPackages(String str, Set<PackageIdentifier> set) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(set);
            resetIfBuilt();
            InternalVisibilityConfig.Builder orCreateVisibilityConfigBuilder = getOrCreateVisibilityConfigBuilder(str);
            Iterator<PackageIdentifier> it = set.iterator();
            while (it.hasNext()) {
                orCreateVisibilityConfigBuilder.addVisibleToPackage(it.next());
            }
            return this;
        }

        public Builder clearSchemaTypeVisibleToPackages(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            InternalVisibilityConfig.Builder visibilityConfigBuilder = getVisibilityConfigBuilder(str);
            if (visibilityConfigBuilder != null) {
                visibilityConfigBuilder.clearVisibleToPackages();
            }
            return this;
        }

        public Builder setRequiredPermissionsForSchemaTypeVisibility(String str, Set<Set<Integer>> set) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(set);
            resetIfBuilt();
            InternalVisibilityConfig.Builder orCreateVisibilityConfigBuilder = getOrCreateVisibilityConfigBuilder(str);
            Iterator<Set<Integer>> it = set.iterator();
            while (it.hasNext()) {
                orCreateVisibilityConfigBuilder.addVisibleToPermissions(it.next());
            }
            return this;
        }

        public Builder clearRequiredPermissionsForSchemaTypeVisibility(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            InternalVisibilityConfig.Builder visibilityConfigBuilder = getVisibilityConfigBuilder(str);
            if (visibilityConfigBuilder != null) {
                visibilityConfigBuilder.clearVisibleToPermissions();
            }
            return this;
        }

        public Builder setPubliclyVisibleSchema(String str, PackageIdentifier packageIdentifier) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(packageIdentifier);
            resetIfBuilt();
            getOrCreateVisibilityConfigBuilder(str).setPubliclyVisibleTargetPackage(packageIdentifier);
            return this;
        }

        public Builder clearPubliclyVisibleSchema(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            InternalVisibilityConfig.Builder visibilityConfigBuilder = getVisibilityConfigBuilder(str);
            if (visibilityConfigBuilder != null) {
                visibilityConfigBuilder.setPubliclyVisibleTargetPackage(null);
            }
            return this;
        }

        public Builder setSchemaTypeVisibleToConfigs(String str, Set<SchemaVisibilityConfig> set) {
            Preconditions.checkNotNull(str);
            Preconditions.checkNotNull(set);
            resetIfBuilt();
            InternalVisibilityConfig.Builder orCreateVisibilityConfigBuilder = getOrCreateVisibilityConfigBuilder(str);
            Iterator<SchemaVisibilityConfig> it = set.iterator();
            while (it.hasNext()) {
                orCreateVisibilityConfigBuilder.addVisibleToConfig(it.next());
            }
            return this;
        }

        public Builder clearSchemaTypeVisibleToConfigs(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            InternalVisibilityConfig.Builder visibilityConfigBuilder = getVisibilityConfigBuilder(str);
            if (visibilityConfigBuilder != null) {
                visibilityConfigBuilder.clearVisibleToConfig();
            }
            return this;
        }

        public Builder setVisibilitySettingSupported(boolean z) {
            if (z) {
                this.mVisibilityConfigBuilders = new ArrayMap();
                return this;
            }
            this.mVisibilityConfigBuilders = null;
            return this;
        }

        public GetSchemaResponse build() {
            ArrayList arrayList;
            if (this.mVisibilityConfigBuilders != null) {
                arrayList = new ArrayList();
                Iterator<InternalVisibilityConfig.Builder> it = this.mVisibilityConfigBuilders.values().iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().build());
                }
            } else {
                arrayList = null;
            }
            this.mBuilt = true;
            return new GetSchemaResponse(this.mVersion, this.mSchemas, arrayList);
        }

        private InternalVisibilityConfig.Builder getOrCreateVisibilityConfigBuilder(String str) {
            Map<String, InternalVisibilityConfig.Builder> map = this.mVisibilityConfigBuilders;
            if (map == null) {
                throw new IllegalStateException("GetSchemaResponse is not configured withvisibility setting support");
            }
            InternalVisibilityConfig.Builder builder = map.get(str);
            if (builder != null) {
                return builder;
            }
            InternalVisibilityConfig.Builder builder2 = new InternalVisibilityConfig.Builder(str);
            this.mVisibilityConfigBuilders.put(str, builder2);
            return builder2;
        }

        private InternalVisibilityConfig.Builder getVisibilityConfigBuilder(String str) {
            Map<String, InternalVisibilityConfig.Builder> map = this.mVisibilityConfigBuilders;
            if (map == null) {
                throw new IllegalStateException("GetSchemaResponse is not configured withvisibility setting support");
            }
            return map.get(str);
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mSchemas = new ArrayList<>(this.mSchemas);
                this.mBuilt = false;
            }
        }
    }
}
