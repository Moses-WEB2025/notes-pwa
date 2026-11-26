package androidx.appsearch.builtintypes;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import com.xiaomi.mirror.synergy.CallMethod;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__PotentialAction, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__PotentialAction implements DocumentClassFactory<PotentialAction> {
    public static final String SCHEMA_NAME = "builtin:PotentialAction";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(CallMethod.ARG_URI).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        return Collections.emptyList();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(PotentialAction potentialAction) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(potentialAction.mNamespace, potentialAction.mId, SCHEMA_NAME);
        String name = potentialAction.getName();
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        String description = potentialAction.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String uri = potentialAction.getUri();
        if (uri != null) {
            builder.setPropertyString(CallMethod.ARG_URI, uri);
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public PotentialAction fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        String[] propertyStringArray = genericDocument.getPropertyStringArray("name");
        String str = null;
        String str2 = (propertyStringArray == null || propertyStringArray.length == 0) ? null : propertyStringArray[0];
        String[] propertyStringArray2 = genericDocument.getPropertyStringArray("description");
        String str3 = (propertyStringArray2 == null || propertyStringArray2.length == 0) ? null : propertyStringArray2[0];
        String[] propertyStringArray3 = genericDocument.getPropertyStringArray(CallMethod.ARG_URI);
        if (propertyStringArray3 != null && propertyStringArray3.length != 0) {
            str = propertyStringArray3[0];
        }
        return new PotentialAction(namespace, id, str2, str3, str);
    }
}
