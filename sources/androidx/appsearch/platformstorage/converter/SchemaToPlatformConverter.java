package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.AppSearchSchema;
import android.os.Build;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.core.util.Preconditions;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class SchemaToPlatformConverter {
    private SchemaToPlatformConverter() {
    }

    public static AppSearchSchema toPlatformSchema(androidx.appsearch.app.AppSearchSchema appSearchSchema) {
        Preconditions.checkNotNull(appSearchSchema);
        AppSearchSchema.Builder builder = new AppSearchSchema.Builder(appSearchSchema.getSchemaType());
        if (!appSearchSchema.getDescription().isEmpty()) {
            throw new UnsupportedOperationException("SCHEMA_SET_DESCRIPTION is not available on this AppSearch implementation.");
        }
        if (!appSearchSchema.getParentTypes().isEmpty()) {
            if (Build.VERSION.SDK_INT < 35) {
                throw new UnsupportedOperationException("SCHEMA_ADD_PARENT_TYPE is not available on this AppSearch implementation.");
            }
            List<String> parentTypes = appSearchSchema.getParentTypes();
            for (int i = 0; i < parentTypes.size(); i++) {
                ApiHelperForV.addParentType(builder, parentTypes.get(i));
            }
        }
        List<androidx.appsearch.app.AppSearchSchema.PropertyConfig> properties = appSearchSchema.getProperties();
        for (int i2 = 0; i2 < properties.size(); i2++) {
            builder.addProperty(toPlatformProperty(properties.get(i2)));
        }
        return builder.build();
    }

    public static androidx.appsearch.app.AppSearchSchema toJetpackSchema(AppSearchSchema appSearchSchema) {
        Preconditions.checkNotNull(appSearchSchema);
        androidx.appsearch.app.AppSearchSchema.Builder builder = new androidx.appsearch.app.AppSearchSchema.Builder(appSearchSchema.getSchemaType());
        List<AppSearchSchema.PropertyConfig> properties = appSearchSchema.getProperties();
        if (Build.VERSION.SDK_INT >= 35) {
            List<String> parentTypes = ApiHelperForV.getParentTypes(appSearchSchema);
            for (int i = 0; i < parentTypes.size(); i++) {
                builder.addParentType(parentTypes.get(i));
            }
        }
        for (int i2 = 0; i2 < properties.size(); i2++) {
            builder.addProperty(toJetpackProperty(properties.get(i2)));
        }
        return builder.build();
    }

    private static AppSearchSchema.PropertyConfig toPlatformProperty(androidx.appsearch.app.AppSearchSchema.PropertyConfig propertyConfig) {
        Preconditions.checkNotNull(propertyConfig);
        if (!propertyConfig.getDescription().isEmpty()) {
            throw new UnsupportedOperationException("SCHEMA_SET_DESCRIPTION is not available on this AppSearch implementation.");
        }
        if (propertyConfig instanceof androidx.appsearch.app.AppSearchSchema.StringPropertyConfig) {
            androidx.appsearch.app.AppSearchSchema.StringPropertyConfig stringPropertyConfig = (androidx.appsearch.app.AppSearchSchema.StringPropertyConfig) propertyConfig;
            AppSearchSchema.StringPropertyConfig.Builder tokenizerType = new AppSearchSchema.StringPropertyConfig.Builder(stringPropertyConfig.getName()).setCardinality(stringPropertyConfig.getCardinality()).setIndexingType(stringPropertyConfig.getIndexingType()).setTokenizerType(stringPropertyConfig.getTokenizerType());
            if (Build.VERSION.SDK_INT == 33) {
                Preconditions.checkArgumentInRange(stringPropertyConfig.getTokenizerType(), 0, 1, "tokenizerType");
            }
            if (stringPropertyConfig.getJoinableValueType() == 1) {
                if (Build.VERSION.SDK_INT < 34) {
                    throw new UnsupportedOperationException("StringPropertyConfig.JOINABLE_VALUE_TYPE_QUALIFIED_ID is not supported on this AppSearch implementation.");
                }
                ApiHelperForU.setJoinableValueType(tokenizerType, stringPropertyConfig.getJoinableValueType());
            }
            if (stringPropertyConfig.getDeletePropagationType() != 0) {
                throw new UnsupportedOperationException("StringPropertyConfig.DELETE_PROPAGATION_TYPE_PROPAGATE_FROM is not supported on this AppSearch implementation.");
            }
            return tokenizerType.build();
        }
        if (propertyConfig instanceof androidx.appsearch.app.AppSearchSchema.LongPropertyConfig) {
            androidx.appsearch.app.AppSearchSchema.LongPropertyConfig longPropertyConfig = (androidx.appsearch.app.AppSearchSchema.LongPropertyConfig) propertyConfig;
            AppSearchSchema.LongPropertyConfig.Builder cardinality = new AppSearchSchema.LongPropertyConfig.Builder(propertyConfig.getName()).setCardinality(propertyConfig.getCardinality());
            if (longPropertyConfig.getIndexingType() == 1) {
                if (Build.VERSION.SDK_INT < 34) {
                    throw new UnsupportedOperationException("LongProperty.INDEXING_TYPE_RANGE is not supported on this AppSearch implementation.");
                }
                ApiHelperForU.setIndexingType(cardinality, longPropertyConfig.getIndexingType());
            }
            if (longPropertyConfig.isScoringEnabled()) {
                throw new UnsupportedOperationException("SCHEMA_SCORABLE_PROPERTY_CONFIG is not available on this AppSearch implementation.");
            }
            return cardinality.build();
        }
        if (propertyConfig instanceof androidx.appsearch.app.AppSearchSchema.DoublePropertyConfig) {
            if (((androidx.appsearch.app.AppSearchSchema.DoublePropertyConfig) propertyConfig).isScoringEnabled()) {
                throw new UnsupportedOperationException("SCHEMA_SCORABLE_PROPERTY_CONFIG is not available on this AppSearch implementation.");
            }
            return new AppSearchSchema.DoublePropertyConfig.Builder(propertyConfig.getName()).setCardinality(propertyConfig.getCardinality()).build();
        }
        if (propertyConfig instanceof androidx.appsearch.app.AppSearchSchema.BooleanPropertyConfig) {
            if (((androidx.appsearch.app.AppSearchSchema.BooleanPropertyConfig) propertyConfig).isScoringEnabled()) {
                throw new UnsupportedOperationException("SCHEMA_SCORABLE_PROPERTY_CONFIG is not available on this AppSearch implementation.");
            }
            return new AppSearchSchema.BooleanPropertyConfig.Builder(propertyConfig.getName()).setCardinality(propertyConfig.getCardinality()).build();
        }
        if (propertyConfig instanceof androidx.appsearch.app.AppSearchSchema.BytesPropertyConfig) {
            return new AppSearchSchema.BytesPropertyConfig.Builder(propertyConfig.getName()).setCardinality(propertyConfig.getCardinality()).build();
        }
        if (propertyConfig instanceof androidx.appsearch.app.AppSearchSchema.DocumentPropertyConfig) {
            androidx.appsearch.app.AppSearchSchema.DocumentPropertyConfig documentPropertyConfig = (androidx.appsearch.app.AppSearchSchema.DocumentPropertyConfig) propertyConfig;
            AppSearchSchema.DocumentPropertyConfig.Builder shouldIndexNestedProperties = new AppSearchSchema.DocumentPropertyConfig.Builder(documentPropertyConfig.getName(), documentPropertyConfig.getSchemaType()).setCardinality(documentPropertyConfig.getCardinality()).setShouldIndexNestedProperties(documentPropertyConfig.shouldIndexNestedProperties());
            if (!documentPropertyConfig.getIndexableNestedProperties().isEmpty()) {
                if (Build.VERSION.SDK_INT < 35) {
                    throw new UnsupportedOperationException("DocumentPropertyConfig.addIndexableNestedProperties is not supported on this AppSearch implementation.");
                }
                ApiHelperForV.addIndexableNestedProperties(shouldIndexNestedProperties, documentPropertyConfig.getIndexableNestedProperties());
            }
            return shouldIndexNestedProperties.build();
        }
        if (propertyConfig instanceof androidx.appsearch.app.AppSearchSchema.EmbeddingPropertyConfig) {
            if (!AppSearchVersionUtil.isAtLeastB()) {
                throw new UnsupportedOperationException("SCHEMA_EMBEDDING_PROPERTY_CONFIG is not available on this AppSearch implementation.");
            }
            androidx.appsearch.app.AppSearchSchema.EmbeddingPropertyConfig embeddingPropertyConfig = (androidx.appsearch.app.AppSearchSchema.EmbeddingPropertyConfig) propertyConfig;
            if (embeddingPropertyConfig.getQuantizationType() != 0) {
                throw new UnsupportedOperationException("SCHEMA_EMBEDDING_QUANTIZATION is not available on this AppSearch implementation.");
            }
            return ApiHelperForB.createPlatformEmbeddingPropertyConfig(embeddingPropertyConfig);
        }
        if (propertyConfig instanceof androidx.appsearch.app.AppSearchSchema.BlobHandlePropertyConfig) {
            throw new UnsupportedOperationException("BLOB_STORAGE is not available on this AppSearch implementation.");
        }
        throw new IllegalArgumentException("Invalid dataType: " + propertyConfig.getDataType());
    }

    private static androidx.appsearch.app.AppSearchSchema.PropertyConfig toJetpackProperty(AppSearchSchema.PropertyConfig propertyConfig) {
        Preconditions.checkNotNull(propertyConfig);
        if (propertyConfig instanceof AppSearchSchema.StringPropertyConfig) {
            AppSearchSchema.StringPropertyConfig stringPropertyConfig = (AppSearchSchema.StringPropertyConfig) propertyConfig;
            androidx.appsearch.app.AppSearchSchema.StringPropertyConfig.Builder tokenizerType = new androidx.appsearch.app.AppSearchSchema.StringPropertyConfig.Builder(stringPropertyConfig.getName()).setCardinality(stringPropertyConfig.getCardinality()).setIndexingType(stringPropertyConfig.getIndexingType()).setTokenizerType(stringPropertyConfig.getTokenizerType());
            if (Build.VERSION.SDK_INT >= 34) {
                tokenizerType.setJoinableValueType(ApiHelperForU.getJoinableValueType(stringPropertyConfig));
            }
            return tokenizerType.build();
        }
        if (propertyConfig instanceof AppSearchSchema.LongPropertyConfig) {
            AppSearchSchema.LongPropertyConfig longPropertyConfig = (AppSearchSchema.LongPropertyConfig) propertyConfig;
            androidx.appsearch.app.AppSearchSchema.LongPropertyConfig.Builder cardinality = new androidx.appsearch.app.AppSearchSchema.LongPropertyConfig.Builder(longPropertyConfig.getName()).setCardinality(longPropertyConfig.getCardinality());
            if (Build.VERSION.SDK_INT >= 34) {
                cardinality.setIndexingType(ApiHelperForU.getIndexingType(longPropertyConfig));
            }
            return cardinality.build();
        }
        if (propertyConfig instanceof AppSearchSchema.DoublePropertyConfig) {
            return new androidx.appsearch.app.AppSearchSchema.DoublePropertyConfig.Builder(propertyConfig.getName()).setCardinality(propertyConfig.getCardinality()).build();
        }
        if (propertyConfig instanceof AppSearchSchema.BooleanPropertyConfig) {
            return new androidx.appsearch.app.AppSearchSchema.BooleanPropertyConfig.Builder(propertyConfig.getName()).setCardinality(propertyConfig.getCardinality()).build();
        }
        if (propertyConfig instanceof AppSearchSchema.BytesPropertyConfig) {
            return new androidx.appsearch.app.AppSearchSchema.BytesPropertyConfig.Builder(propertyConfig.getName()).setCardinality(propertyConfig.getCardinality()).build();
        }
        if (propertyConfig instanceof AppSearchSchema.DocumentPropertyConfig) {
            AppSearchSchema.DocumentPropertyConfig documentPropertyConfig = (AppSearchSchema.DocumentPropertyConfig) propertyConfig;
            androidx.appsearch.app.AppSearchSchema.DocumentPropertyConfig.Builder shouldIndexNestedProperties = new androidx.appsearch.app.AppSearchSchema.DocumentPropertyConfig.Builder(documentPropertyConfig.getName(), documentPropertyConfig.getSchemaType()).setCardinality(documentPropertyConfig.getCardinality()).setShouldIndexNestedProperties(documentPropertyConfig.shouldIndexNestedProperties());
            if (Build.VERSION.SDK_INT >= 35) {
                shouldIndexNestedProperties.addIndexableNestedProperties(ApiHelperForV.getIndexableNestedProperties(documentPropertyConfig));
            }
            return shouldIndexNestedProperties.build();
        }
        if (AppSearchVersionUtil.isAtLeastB() && (propertyConfig instanceof AppSearchSchema.EmbeddingPropertyConfig)) {
            return ApiHelperForB.createJetpackEmbeddingPropertyConfig((AppSearchSchema.EmbeddingPropertyConfig) propertyConfig);
        }
        throw new IllegalArgumentException("Invalid property type " + propertyConfig.getClass() + ": " + propertyConfig);
    }

    private static class ApiHelperForU {
        private ApiHelperForU() {
        }

        static void setJoinableValueType(AppSearchSchema.StringPropertyConfig.Builder builder, int i) {
            builder.setJoinableValueType(i);
        }

        static int getJoinableValueType(AppSearchSchema.StringPropertyConfig stringPropertyConfig) {
            return stringPropertyConfig.getJoinableValueType();
        }

        static void setIndexingType(AppSearchSchema.LongPropertyConfig.Builder builder, int i) {
            builder.setIndexingType(i);
        }

        static int getIndexingType(AppSearchSchema.LongPropertyConfig longPropertyConfig) {
            return longPropertyConfig.getIndexingType();
        }
    }

    private static class ApiHelperForV {
        private ApiHelperForV() {
        }

        static void addParentType(AppSearchSchema.Builder builder, String str) {
            builder.addParentType(str);
        }

        static void addIndexableNestedProperties(AppSearchSchema.DocumentPropertyConfig.Builder builder, Collection<String> collection) {
            builder.addIndexableNestedProperties(collection);
        }

        static List<String> getParentTypes(AppSearchSchema appSearchSchema) {
            return appSearchSchema.getParentTypes();
        }

        static List<String> getIndexableNestedProperties(AppSearchSchema.DocumentPropertyConfig documentPropertyConfig) {
            return documentPropertyConfig.getIndexableNestedProperties();
        }
    }

    private static class ApiHelperForB {
        private ApiHelperForB() {
        }

        static AppSearchSchema.PropertyConfig createPlatformEmbeddingPropertyConfig(androidx.appsearch.app.AppSearchSchema.EmbeddingPropertyConfig embeddingPropertyConfig) {
            return new AppSearchSchema.EmbeddingPropertyConfig.Builder(embeddingPropertyConfig.getName()).setCardinality(embeddingPropertyConfig.getCardinality()).setIndexingType(embeddingPropertyConfig.getIndexingType()).build();
        }

        static androidx.appsearch.app.AppSearchSchema.EmbeddingPropertyConfig createJetpackEmbeddingPropertyConfig(AppSearchSchema.EmbeddingPropertyConfig embeddingPropertyConfig) {
            return new androidx.appsearch.app.AppSearchSchema.EmbeddingPropertyConfig.Builder(embeddingPropertyConfig.getName()).setCardinality(embeddingPropertyConfig.getCardinality()).setIndexingType(embeddingPropertyConfig.getIndexingType()).build();
        }
    }
}
