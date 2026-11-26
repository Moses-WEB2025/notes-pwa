package androidx.appsearch.builtintypes.properties;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.properties.$$__AppSearch__Keyword, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__Keyword implements DocumentClassFactory<Keyword> {
    public static final String SCHEMA_NAME = "Keyword";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("asText").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        return Collections.emptyList();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(Keyword keyword) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(keyword.mNamespace, keyword.mId, SCHEMA_NAME);
        String str = keyword.mAsText;
        if (str != null) {
            builder.setPropertyString("asText", str);
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public Keyword fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        String[] propertyStringArray = genericDocument.getPropertyStringArray("asText");
        Keyword keyword = new Keyword((propertyStringArray == null || propertyStringArray.length == 0) ? null : propertyStringArray[0]);
        keyword.mNamespace = namespace;
        keyword.mId = id;
        return keyword;
    }
}
