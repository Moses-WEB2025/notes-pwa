package androidx.appfunctions.metadata;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: androidx.appfunctions.metadata.$$__AppSearch__AppFunctionComponentsMetadataDocument, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__AppFunctionComponentsMetadataDocument implements DocumentClassFactory<AppFunctionComponentsMetadataDocument> {
    public static final String SCHEMA_NAME = "AppFunctionComponentsMetadataDocument";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("dataTypes", C$$__AppSearch__AppFunctionNamedDataTypeMetadataDocument.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(AppFunctionNamedDataTypeMetadataDocument.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(AppFunctionComponentsMetadataDocument appFunctionComponentsMetadataDocument) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(appFunctionComponentsMetadataDocument.getNamespace(), appFunctionComponentsMetadataDocument.getId(), SCHEMA_NAME);
        List<AppFunctionNamedDataTypeMetadataDocument> dataTypes = appFunctionComponentsMetadataDocument.getDataTypes();
        if (dataTypes != null) {
            GenericDocument[] genericDocumentArr = new GenericDocument[dataTypes.size()];
            Iterator<AppFunctionNamedDataTypeMetadataDocument> it = dataTypes.iterator();
            int i = 0;
            while (it.hasNext()) {
                genericDocumentArr[i] = GenericDocument.fromDocumentClass(it.next());
                i++;
            }
            builder.setPropertyDocument("dataTypes", genericDocumentArr);
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppFunctionComponentsMetadataDocument fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        GenericDocument[] propertyDocumentArray = genericDocument.getPropertyDocumentArray("dataTypes");
        List listEmptyList = Collections.emptyList();
        if (propertyDocumentArray != null) {
            listEmptyList = new ArrayList(propertyDocumentArray.length);
            for (GenericDocument genericDocument2 : propertyDocumentArray) {
                listEmptyList.add((AppFunctionNamedDataTypeMetadataDocument) genericDocument2.toDocumentClass(AppFunctionNamedDataTypeMetadataDocument.class, documentClassMappingContext));
            }
        }
        return new AppFunctionComponentsMetadataDocument(namespace, id, listEmptyList);
    }
}
