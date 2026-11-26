package androidx.appsearch.usagereporting;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import java.util.Collections;
import java.util.List;
import miuix.smartaction.SmartAction;

/* JADX INFO: renamed from: androidx.appsearch.usagereporting.$$__AppSearch__ImpressionAction, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__ImpressionAction implements DocumentClassFactory<ImpressionAction> {
    public static final String SCHEMA_NAME = "builtin:ImpressionAction";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("actionType").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(SmartAction.Feature.QUERY).setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("referencedQualifiedId").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(1).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("resultRankInBlock").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("resultRankGlobal").setCardinality(2).setIndexingType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        return Collections.emptyList();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(ImpressionAction impressionAction) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(impressionAction.getNamespace(), impressionAction.getId(), SCHEMA_NAME);
        builder.setTtlMillis(impressionAction.getDocumentTtlMillis());
        builder.setCreationTimestampMillis(impressionAction.getActionTimestampMillis());
        builder.setPropertyLong("actionType", impressionAction.getActionType());
        String query = impressionAction.getQuery();
        if (query != null) {
            builder.setPropertyString(SmartAction.Feature.QUERY, query);
        }
        String referencedQualifiedId = impressionAction.getReferencedQualifiedId();
        if (referencedQualifiedId != null) {
            builder.setPropertyString("referencedQualifiedId", referencedQualifiedId);
        }
        builder.setPropertyLong("resultRankInBlock", impressionAction.getResultRankInBlock());
        builder.setPropertyLong("resultRankGlobal", impressionAction.getResultRankGlobal());
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public ImpressionAction fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        long ttlMillis = genericDocument.getTtlMillis();
        long creationTimestampMillis = genericDocument.getCreationTimestampMillis();
        int propertyLong = (int) genericDocument.getPropertyLong("actionType");
        String[] propertyStringArray = genericDocument.getPropertyStringArray(SmartAction.Feature.QUERY);
        String str = null;
        String str2 = (propertyStringArray == null || propertyStringArray.length == 0) ? null : propertyStringArray[0];
        String[] propertyStringArray2 = genericDocument.getPropertyStringArray("referencedQualifiedId");
        if (propertyStringArray2 != null && propertyStringArray2.length != 0) {
            str = propertyStringArray2[0];
        }
        String str3 = str;
        int propertyLong2 = (int) genericDocument.getPropertyLong("resultRankInBlock");
        int propertyLong3 = (int) genericDocument.getPropertyLong("resultRankGlobal");
        ImpressionAction.Builder builder = new ImpressionAction.Builder(namespace, id, creationTimestampMillis, propertyLong);
        builder.setDocumentTtlMillis(ttlMillis);
        builder.setQuery(str2);
        builder.setReferencedQualifiedId(str3);
        builder.setResultRankInBlock(propertyLong2);
        builder.setResultRankGlobal(propertyLong3);
        return builder.build();
    }
}
