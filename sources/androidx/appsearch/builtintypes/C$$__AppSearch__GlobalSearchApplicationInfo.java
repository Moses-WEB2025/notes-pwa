package androidx.appsearch.builtintypes;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__GlobalSearchApplicationInfo, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__GlobalSearchApplicationInfo implements DocumentClassFactory<GlobalSearchApplicationInfo> {
    public static final String SCHEMA_NAME = "builtin:GlobalSearchApplicationInfo";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("applicationType").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("schemaTypes").setCardinality(1).setTokenizerType(2).setIndexingType(1).setJoinableValueType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        return Collections.emptyList();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(GlobalSearchApplicationInfo globalSearchApplicationInfo) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(globalSearchApplicationInfo.getNamespace(), globalSearchApplicationInfo.getId(), SCHEMA_NAME);
        builder.setPropertyLong("applicationType", globalSearchApplicationInfo.getApplicationType());
        List<String> schemaTypes = globalSearchApplicationInfo.getSchemaTypes();
        if (schemaTypes != null) {
            builder.setPropertyString("schemaTypes", (String[]) schemaTypes.toArray(new String[0]));
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public GlobalSearchApplicationInfo fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        int propertyLong = (int) genericDocument.getPropertyLong("applicationType");
        String[] propertyStringArray = genericDocument.getPropertyStringArray("schemaTypes");
        return new GlobalSearchApplicationInfo(namespace, id, propertyLong, propertyStringArray != null ? Arrays.asList(propertyStringArray) : null);
    }
}
