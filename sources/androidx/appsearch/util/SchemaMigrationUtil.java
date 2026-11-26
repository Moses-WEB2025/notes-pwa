package androidx.appsearch.util;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.InternalSetSchemaResponse;
import androidx.appsearch.app.Migrator;
import androidx.appsearch.app.SetSchemaResponse;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class SchemaMigrationUtil {
    private SchemaMigrationUtil() {
    }

    public static Map<String, Migrator> getActiveMigrators(Set<AppSearchSchema> set, Map<String, Migrator> map, int i, int i2) {
        if (i == i2) {
            return Collections.emptyMap();
        }
        ArraySet arraySet = new ArraySet(set.size());
        Iterator<AppSearchSchema> it = set.iterator();
        while (it.hasNext()) {
            arraySet.add(it.next().getSchemaType());
        }
        ArrayMap arrayMap = new ArrayMap();
        for (Map.Entry<String, Migrator> entry : map.entrySet()) {
            String key = entry.getKey();
            Migrator value = entry.getValue();
            if (arraySet.contains(key) && value.shouldMigrate(i, i2)) {
                arrayMap.put(key, value);
            }
        }
        return arrayMap;
    }

    public static void checkDeletedAndIncompatibleAfterMigration(InternalSetSchemaResponse internalSetSchemaResponse, Set<String> set) throws AppSearchException {
        if (internalSetSchemaResponse.isSuccess()) {
            return;
        }
        SetSchemaResponse setSchemaResponse = internalSetSchemaResponse.getSetSchemaResponse();
        ArraySet arraySet = new ArraySet(setSchemaResponse.getIncompatibleTypes());
        arraySet.removeAll(set);
        ArraySet arraySet2 = new ArraySet(setSchemaResponse.getDeletedTypes());
        arraySet2.removeAll(set);
        if (!arraySet.isEmpty() || !arraySet2.isEmpty()) {
            throw new AppSearchException(7, internalSetSchemaResponse.getErrorMessage());
        }
    }
}
