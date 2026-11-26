package androidx.appsearch.usagereporting;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: androidx.appsearch.usagereporting.$$__AppSearch__TakenAction, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__TakenAction implements DocumentClassFactory<TakenAction> {
    public static final String SCHEMA_NAME = "builtin:TakenAction";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("actionType").setCardinality(2).setIndexingType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        return Collections.emptyList();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(TakenAction takenAction) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(takenAction.getNamespace(), takenAction.getId(), SCHEMA_NAME);
        builder.setTtlMillis(takenAction.getDocumentTtlMillis());
        builder.setCreationTimestampMillis(takenAction.getActionTimestampMillis());
        builder.setPropertyLong("actionType", takenAction.getActionType());
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public TakenAction fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        long ttlMillis = genericDocument.getTtlMillis();
        TakenAction.Builder builder = new TakenAction.Builder(namespace, id, genericDocument.getCreationTimestampMillis(), (int) genericDocument.getPropertyLong("actionType"));
        builder.setDocumentTtlMillis(ttlMillis);
        return builder.build();
    }
}
