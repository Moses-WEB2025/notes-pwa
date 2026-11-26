package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.GenericDocument;
import android.app.appsearch.SetSchemaRequest;
import android.os.Build;
import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.Migrator;
import androidx.appsearch.app.PackageIdentifier;
import androidx.appsearch.app.SchemaVisibilityConfig;
import androidx.appsearch.app.SetSchemaResponse;
import androidx.core.util.Preconditions;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: loaded from: classes.dex */
public final class SetSchemaRequestToPlatformConverter {
    private SetSchemaRequestToPlatformConverter() {
    }

    public static SetSchemaRequest toPlatformSetSchemaRequest(androidx.appsearch.app.SetSchemaRequest setSchemaRequest) {
        Preconditions.checkNotNull(setSchemaRequest);
        SetSchemaRequest.Builder builder = new SetSchemaRequest.Builder();
        Iterator<AppSearchSchema> it = setSchemaRequest.getSchemas().iterator();
        while (it.hasNext()) {
            builder.addSchemas(SchemaToPlatformConverter.toPlatformSchema(it.next()));
        }
        Iterator<String> it2 = setSchemaRequest.getSchemasNotDisplayedBySystem().iterator();
        while (it2.hasNext()) {
            builder.setSchemaTypeDisplayedBySystem(it2.next(), false);
        }
        for (Map.Entry<String, Set<PackageIdentifier>> entry : setSchemaRequest.getSchemasVisibleToPackagesInternal().entrySet()) {
            for (PackageIdentifier packageIdentifier : entry.getValue()) {
                builder.setSchemaTypeVisibilityForPackage(entry.getKey(), true, new android.app.appsearch.PackageIdentifier(packageIdentifier.getPackageName(), packageIdentifier.getSha256Certificate()));
            }
        }
        if (!setSchemaRequest.getRequiredPermissionsForSchemaTypeVisibility().isEmpty()) {
            if (Build.VERSION.SDK_INT < 33) {
                throw new UnsupportedOperationException("Set required permissions for schema type visibility are not supported with this backend/Android API level combination.");
            }
            for (Map.Entry<String, Set<Set<Integer>>> entry2 : setSchemaRequest.getRequiredPermissionsForSchemaTypeVisibility().entrySet()) {
                Iterator<Set<Integer>> it3 = entry2.getValue().iterator();
                while (it3.hasNext()) {
                    ApiHelperForT.addRequiredPermissionsForSchemaTypeVisibility(builder, entry2.getKey(), it3.next());
                }
            }
        }
        if (!setSchemaRequest.getPubliclyVisibleSchemas().isEmpty()) {
            if (Build.VERSION.SDK_INT < 35) {
                throw new UnsupportedOperationException("Publicly visible schema are not supported on this AppSearch implementation.");
            }
            for (Map.Entry<String, PackageIdentifier> entry3 : setSchemaRequest.getPubliclyVisibleSchemas().entrySet()) {
                PackageIdentifier value = entry3.getValue();
                ApiHelperForV.setPubliclyVisibleSchema(builder, entry3.getKey(), new android.app.appsearch.PackageIdentifier(value.getPackageName(), value.getSha256Certificate()));
            }
        }
        if (!setSchemaRequest.getSchemasVisibleToConfigs().isEmpty()) {
            if (Build.VERSION.SDK_INT < 35) {
                throw new UnsupportedOperationException("Schema visible to config are not supported on this AppSearch implementation.");
            }
            for (Map.Entry<String, Set<SchemaVisibilityConfig>> entry4 : setSchemaRequest.getSchemasVisibleToConfigs().entrySet()) {
                ApiHelperForV.addSchemaTypeVisibleToConfig(builder, entry4.getKey(), entry4.getValue());
            }
        }
        for (Map.Entry<String, Migrator> entry5 : setSchemaRequest.getMigrators().entrySet()) {
            final Migrator value2 = entry5.getValue();
            builder.setMigrator(entry5.getKey(), new android.app.appsearch.Migrator() { // from class: androidx.appsearch.platformstorage.converter.SetSchemaRequestToPlatformConverter.1
                @Override // android.app.appsearch.Migrator
                public boolean shouldMigrate(int i, int i2) {
                    return value2.shouldMigrate(i, i2);
                }

                @Override // android.app.appsearch.Migrator
                public GenericDocument onUpgrade(int i, int i2, GenericDocument genericDocument) {
                    androidx.appsearch.app.GenericDocument jetpackGenericDocument = GenericDocumentToPlatformConverter.toJetpackGenericDocument(genericDocument);
                    androidx.appsearch.app.GenericDocument genericDocumentOnUpgrade = value2.onUpgrade(i, i2, jetpackGenericDocument);
                    return jetpackGenericDocument.equals(genericDocumentOnUpgrade) ? genericDocument : GenericDocumentToPlatformConverter.toPlatformGenericDocument(genericDocumentOnUpgrade);
                }

                @Override // android.app.appsearch.Migrator
                public GenericDocument onDowngrade(int i, int i2, GenericDocument genericDocument) {
                    androidx.appsearch.app.GenericDocument jetpackGenericDocument = GenericDocumentToPlatformConverter.toJetpackGenericDocument(genericDocument);
                    androidx.appsearch.app.GenericDocument genericDocumentOnDowngrade = value2.onDowngrade(i, i2, jetpackGenericDocument);
                    return jetpackGenericDocument.equals(genericDocumentOnDowngrade) ? genericDocument : GenericDocumentToPlatformConverter.toPlatformGenericDocument(genericDocumentOnDowngrade);
                }
            });
        }
        return builder.setForceOverride(setSchemaRequest.isForceOverride()).setVersion(setSchemaRequest.getVersion()).build();
    }

    public static SetSchemaResponse toJetpackSetSchemaResponse(android.app.appsearch.SetSchemaResponse setSchemaResponse) {
        Preconditions.checkNotNull(setSchemaResponse);
        SetSchemaResponse.Builder builderAddMigratedTypes = new SetSchemaResponse.Builder().addDeletedTypes(setSchemaResponse.getDeletedTypes()).addIncompatibleTypes(setSchemaResponse.getIncompatibleTypes()).addMigratedTypes(setSchemaResponse.getMigratedTypes());
        for (android.app.appsearch.SetSchemaResponse.MigrationFailure migrationFailure : setSchemaResponse.getMigrationFailures()) {
            builderAddMigratedTypes.addMigrationFailure(new SetSchemaResponse.MigrationFailure(migrationFailure.getNamespace(), migrationFailure.getDocumentId(), migrationFailure.getSchemaType(), AppSearchResultToPlatformConverter.platformAppSearchResultToJetpack(migrationFailure.getAppSearchResult(), Function.identity())));
        }
        return builderAddMigratedTypes.build();
    }

    private static class ApiHelperForT {
        private ApiHelperForT() {
        }

        static void addRequiredPermissionsForSchemaTypeVisibility(SetSchemaRequest.Builder builder, String str, Set<Integer> set) {
            builder.addRequiredPermissionsForSchemaTypeVisibility(str, set);
        }
    }

    private static class ApiHelperForV {
        private ApiHelperForV() {
        }

        static void setPubliclyVisibleSchema(SetSchemaRequest.Builder builder, String str, android.app.appsearch.PackageIdentifier packageIdentifier) {
            builder.setPubliclyVisibleSchema(str, packageIdentifier);
        }

        public static void addSchemaTypeVisibleToConfig(SetSchemaRequest.Builder builder, String str, Set<SchemaVisibilityConfig> set) {
            Iterator<SchemaVisibilityConfig> it = set.iterator();
            while (it.hasNext()) {
                builder.addSchemaTypeVisibleToConfig(str, toPlatformSchemaVisibilityConfig(it.next()));
            }
        }

        private static android.app.appsearch.SchemaVisibilityConfig toPlatformSchemaVisibilityConfig(SchemaVisibilityConfig schemaVisibilityConfig) {
            Preconditions.checkNotNull(schemaVisibilityConfig);
            android.app.appsearch.SchemaVisibilityConfig.Builder builder = new android.app.appsearch.SchemaVisibilityConfig.Builder();
            List<PackageIdentifier> allowedPackages = schemaVisibilityConfig.getAllowedPackages();
            for (int i = 0; i < allowedPackages.size(); i++) {
                builder.addAllowedPackage(new android.app.appsearch.PackageIdentifier(allowedPackages.get(i).getPackageName(), allowedPackages.get(i).getSha256Certificate()));
            }
            Iterator<Set<Integer>> it = schemaVisibilityConfig.getRequiredPermissions().iterator();
            while (it.hasNext()) {
                builder.addRequiredPermissions(it.next());
            }
            PackageIdentifier publiclyVisibleTargetPackage = schemaVisibilityConfig.getPubliclyVisibleTargetPackage();
            if (publiclyVisibleTargetPackage != null) {
                builder.setPubliclyVisibleTargetPackage(new android.app.appsearch.PackageIdentifier(publiclyVisibleTargetPackage.getPackageName(), publiclyVisibleTargetPackage.getSha256Certificate()));
            }
            return builder.build();
        }
    }
}
