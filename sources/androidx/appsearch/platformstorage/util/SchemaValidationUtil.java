package androidx.appsearch.platformstorage.util;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.exceptions.IllegalSchemaException;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class SchemaValidationUtil {
    private SchemaValidationUtil() {
    }

    public static void checkSchemasAreValidOrThrow(Set<AppSearchSchema> set, int i) throws IllegalSchemaException {
        ArrayMap arrayMap = new ArrayMap();
        for (AppSearchSchema appSearchSchema : set) {
            arrayMap.put(appSearchSchema.getSchemaType(), appSearchSchema);
        }
        ArrayMap arrayMap2 = new ArrayMap();
        Iterator<AppSearchSchema> it = set.iterator();
        while (it.hasNext()) {
            if (getNumSectionsInSchemaOrThrow(it.next(), arrayMap, arrayMap2, new ArraySet()) > i) {
                throw new IllegalSchemaException("Too many properties to be indexed, max number of properties allowed: " + i);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v10, types: [int] */
    /* JADX WARN: Type inference failed for: r2v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v8, types: [int] */
    private static int getNumSectionsInSchemaOrThrow(AppSearchSchema appSearchSchema, Map<String, AppSearchSchema> map, Map<String, Integer> map2, Set<String> set) throws IllegalSchemaException {
        int iIsPropertyIndexable;
        String schemaType = appSearchSchema.getSchemaType();
        if (set.contains(schemaType)) {
            throw new IllegalSchemaException("Invalid cycle detected in schema type configs. '" + schemaType + "' references itself.");
        }
        if (map2.containsKey(schemaType)) {
            return map2.get(schemaType).intValue();
        }
        set.add(schemaType);
        int i = 0;
        for (AppSearchSchema.PropertyConfig propertyConfig : appSearchSchema.getProperties()) {
            if (propertyConfig.getDataType() == 6) {
                AppSearchSchema.DocumentPropertyConfig documentPropertyConfig = (AppSearchSchema.DocumentPropertyConfig) propertyConfig;
                String schemaType2 = documentPropertyConfig.getSchemaType();
                if (!map.containsKey(schemaType2)) {
                    throw new IllegalSchemaException("Undefined schema type: " + schemaType2);
                }
                if (!documentPropertyConfig.shouldIndexNestedProperties()) {
                    iIsPropertyIndexable = documentPropertyConfig.getIndexableNestedProperties().size();
                } else {
                    iIsPropertyIndexable = getNumSectionsInSchemaOrThrow(map.get(schemaType2), map, map2, set);
                }
            } else {
                iIsPropertyIndexable = isPropertyIndexable(propertyConfig);
            }
            i += iIsPropertyIndexable;
        }
        set.remove(schemaType);
        map2.put(schemaType, Integer.valueOf(i));
        return i;
    }

    private static boolean isPropertyIndexable(AppSearchSchema.PropertyConfig propertyConfig) {
        int dataType = propertyConfig.getDataType();
        if (dataType == 1) {
            return ((AppSearchSchema.StringPropertyConfig) propertyConfig).getIndexingType() != 0;
        }
        if (dataType == 2) {
            return ((AppSearchSchema.LongPropertyConfig) propertyConfig).getIndexingType() != 0;
        }
        if (dataType != 6) {
            return false;
        }
        AppSearchSchema.DocumentPropertyConfig documentPropertyConfig = (AppSearchSchema.DocumentPropertyConfig) propertyConfig;
        return documentPropertyConfig.shouldIndexNestedProperties() || !documentPropertyConfig.getIndexableNestedProperties().isEmpty();
    }
}
