package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.AppSearchSchema;
import android.os.Build;
import androidx.appsearch.app.GetSchemaResponse;
import androidx.appsearch.app.PackageIdentifier;
import androidx.appsearch.app.SchemaVisibilityConfig;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class GetSchemaResponseToPlatformConverter {
    private GetSchemaResponseToPlatformConverter() {
    }

    public static GetSchemaResponse toJetpackGetSchemaResponse(android.app.appsearch.GetSchemaResponse getSchemaResponse) {
        Preconditions.checkNotNull(getSchemaResponse);
        GetSchemaResponse.Builder builder = new GetSchemaResponse.Builder();
        if (Build.VERSION.SDK_INT < 33) {
            builder.setVisibilitySettingSupported(false);
        }
        Iterator<AppSearchSchema> it = getSchemaResponse.getSchemas().iterator();
        while (it.hasNext()) {
            builder.addSchema(SchemaToPlatformConverter.toJetpackSchema(it.next()));
        }
        builder.setVersion(getSchemaResponse.getVersion());
        if (Build.VERSION.SDK_INT >= 33) {
            Iterator<String> it2 = ApiHelperForT.getSchemaTypesNotDisplayedBySystem(getSchemaResponse).iterator();
            while (it2.hasNext()) {
                builder.addSchemaTypeNotDisplayedBySystem(it2.next());
            }
            convertSchemasVisibleToPackages(getSchemaResponse, builder);
            for (Map.Entry<String, Set<Set<Integer>>> entry : ApiHelperForT.getRequiredPermissionsForSchemaTypeVisibility(getSchemaResponse).entrySet()) {
                builder.setRequiredPermissionsForSchemaTypeVisibility(entry.getKey(), entry.getValue());
            }
        }
        if (Build.VERSION.SDK_INT >= 35) {
            Map<String, PackageIdentifier> publiclyVisibleSchemas = ApiHelperForV.getPubliclyVisibleSchemas(getSchemaResponse);
            if (!publiclyVisibleSchemas.isEmpty()) {
                for (Map.Entry<String, PackageIdentifier> entry2 : publiclyVisibleSchemas.entrySet()) {
                    builder.setPubliclyVisibleSchema(entry2.getKey(), entry2.getValue());
                }
            }
            Map<String, Set<SchemaVisibilityConfig>> schemasVisibleToConfigs = ApiHelperForV.getSchemasVisibleToConfigs(getSchemaResponse);
            if (!schemasVisibleToConfigs.isEmpty()) {
                for (Map.Entry<String, Set<SchemaVisibilityConfig>> entry3 : schemasVisibleToConfigs.entrySet()) {
                    builder.setSchemaTypeVisibleToConfigs(entry3.getKey(), entry3.getValue());
                }
            }
        }
        return builder.build();
    }

    private static void convertSchemasVisibleToPackages(android.app.appsearch.GetSchemaResponse getSchemaResponse, GetSchemaResponse.Builder builder) {
        Map<String, Set<android.app.appsearch.PackageIdentifier>> schemaTypesVisibleToPackage = ApiHelperForT.getSchemaTypesVisibleToPackage(getSchemaResponse);
        if (schemaTypesVisibleToPackage != null) {
            for (Map.Entry<String, Set<android.app.appsearch.PackageIdentifier>> entry : schemaTypesVisibleToPackage.entrySet()) {
                ArraySet arraySet = new ArraySet(entry.getValue().size());
                for (android.app.appsearch.PackageIdentifier packageIdentifier : entry.getValue()) {
                    arraySet.add(new PackageIdentifier(packageIdentifier.getPackageName(), packageIdentifier.getSha256Certificate()));
                }
                builder.setSchemaTypeVisibleToPackages(entry.getKey(), arraySet);
            }
        }
    }

    private static class ApiHelperForT {
        private ApiHelperForT() {
        }

        static Set<String> getSchemaTypesNotDisplayedBySystem(android.app.appsearch.GetSchemaResponse getSchemaResponse) {
            return getSchemaResponse.getSchemaTypesNotDisplayedBySystem();
        }

        static Map<String, Set<android.app.appsearch.PackageIdentifier>> getSchemaTypesVisibleToPackage(android.app.appsearch.GetSchemaResponse getSchemaResponse) {
            return getSchemaResponse.getSchemaTypesVisibleToPackages();
        }

        static Map<String, Set<Set<Integer>>> getRequiredPermissionsForSchemaTypeVisibility(android.app.appsearch.GetSchemaResponse getSchemaResponse) {
            return getSchemaResponse.getRequiredPermissionsForSchemaTypeVisibility();
        }
    }

    private static class ApiHelperForV {
        private ApiHelperForV() {
        }

        static Map<String, PackageIdentifier> getPubliclyVisibleSchemas(android.app.appsearch.GetSchemaResponse getSchemaResponse) {
            Map publiclyVisibleSchemas = getSchemaResponse.getPubliclyVisibleSchemas();
            if (publiclyVisibleSchemas.isEmpty()) {
                return Collections.emptyMap();
            }
            ArrayMap arrayMap = new ArrayMap(publiclyVisibleSchemas.size());
            for (Map.Entry entry : publiclyVisibleSchemas.entrySet()) {
                arrayMap.put((String) entry.getKey(), new PackageIdentifier(((android.app.appsearch.PackageIdentifier) entry.getValue()).getPackageName(), ((android.app.appsearch.PackageIdentifier) entry.getValue()).getSha256Certificate()));
            }
            return arrayMap;
        }

        static Map<String, Set<SchemaVisibilityConfig>> getSchemasVisibleToConfigs(android.app.appsearch.GetSchemaResponse getSchemaResponse) {
            Map schemaTypesVisibleToConfigs = getSchemaResponse.getSchemaTypesVisibleToConfigs();
            if (schemaTypesVisibleToConfigs.isEmpty()) {
                return Collections.emptyMap();
            }
            ArrayMap arrayMap = new ArrayMap(schemaTypesVisibleToConfigs.size());
            for (Map.Entry entry : schemaTypesVisibleToConfigs.entrySet()) {
                ArraySet arraySet = new ArraySet(((Set) entry.getValue()).size());
                Iterator it = ((Set) entry.getValue()).iterator();
                while (it.hasNext()) {
                    arraySet.add(toJetpackSchemaVisibilityConfig((android.app.appsearch.SchemaVisibilityConfig) it.next()));
                }
                arrayMap.put((String) entry.getKey(), arraySet);
            }
            return arrayMap;
        }

        private static SchemaVisibilityConfig toJetpackSchemaVisibilityConfig(android.app.appsearch.SchemaVisibilityConfig schemaVisibilityConfig) {
            Preconditions.checkNotNull(schemaVisibilityConfig);
            SchemaVisibilityConfig.Builder builder = new SchemaVisibilityConfig.Builder();
            List allowedPackages = schemaVisibilityConfig.getAllowedPackages();
            for (int i = 0; i < allowedPackages.size(); i++) {
                builder.addAllowedPackage(new PackageIdentifier(((android.app.appsearch.PackageIdentifier) allowedPackages.get(i)).getPackageName(), ((android.app.appsearch.PackageIdentifier) allowedPackages.get(i)).getSha256Certificate()));
            }
            Iterator it = schemaVisibilityConfig.getRequiredPermissions().iterator();
            while (it.hasNext()) {
                builder.addRequiredPermissions((Set) it.next());
            }
            android.app.appsearch.PackageIdentifier publiclyVisibleTargetPackage = schemaVisibilityConfig.getPubliclyVisibleTargetPackage();
            if (publiclyVisibleTargetPackage != null) {
                builder.setPubliclyVisibleTargetPackage(new PackageIdentifier(publiclyVisibleTargetPackage.getPackageName(), publiclyVisibleTargetPackage.getSha256Certificate()));
            }
            return builder.build();
        }
    }
}
