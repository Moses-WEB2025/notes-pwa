package androidx.appfunctions.metadata;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.text.lookup.StringLookupFactory;

/* JADX INFO: renamed from: androidx.appfunctions.metadata.$$__AppSearch__AppFunctionDataTypeMetadataDocument, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__AppFunctionDataTypeMetadataDocument implements DocumentClassFactory<AppFunctionDataTypeMetadataDocument> {
    public static final String SCHEMA_NAME = "AppFunctionDataTypeMetadataDocument";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("type").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("itemType", SCHEMA_NAME).setCardinality(2).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder(StringLookupFactory.KEY_PROPERTIES, C$$__AppSearch__AppFunctionNamedDataTypeMetadataDocument.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("allOf", SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("oneOf", SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("required").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("dataTypeReference").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.BooleanPropertyConfig.Builder("isNullable").setCardinality(2).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("objectQualifiedName").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("enumValues").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(AppFunctionDataTypeMetadataDocument.class);
        arrayList.add(AppFunctionNamedDataTypeMetadataDocument.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(appFunctionDataTypeMetadataDocument.getNamespace(), appFunctionDataTypeMetadataDocument.getId(), SCHEMA_NAME);
        builder.setPropertyLong("type", appFunctionDataTypeMetadataDocument.getType());
        AppFunctionDataTypeMetadataDocument itemType = appFunctionDataTypeMetadataDocument.getItemType();
        if (itemType != null) {
            builder.setPropertyDocument("itemType", GenericDocument.fromDocumentClass(itemType));
        }
        List<AppFunctionNamedDataTypeMetadataDocument> properties = appFunctionDataTypeMetadataDocument.getProperties();
        if (properties != null) {
            GenericDocument[] genericDocumentArr = new GenericDocument[properties.size()];
            Iterator<AppFunctionNamedDataTypeMetadataDocument> it = properties.iterator();
            int i = 0;
            while (it.hasNext()) {
                genericDocumentArr[i] = GenericDocument.fromDocumentClass(it.next());
                i++;
            }
            builder.setPropertyDocument(StringLookupFactory.KEY_PROPERTIES, genericDocumentArr);
        }
        List<AppFunctionDataTypeMetadataDocument> allOf = appFunctionDataTypeMetadataDocument.getAllOf();
        if (allOf != null) {
            GenericDocument[] genericDocumentArr2 = new GenericDocument[allOf.size()];
            Iterator<AppFunctionDataTypeMetadataDocument> it2 = allOf.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                genericDocumentArr2[i2] = GenericDocument.fromDocumentClass(it2.next());
                i2++;
            }
            builder.setPropertyDocument("allOf", genericDocumentArr2);
        }
        List<AppFunctionDataTypeMetadataDocument> oneOf = appFunctionDataTypeMetadataDocument.getOneOf();
        if (oneOf != null) {
            GenericDocument[] genericDocumentArr3 = new GenericDocument[oneOf.size()];
            Iterator<AppFunctionDataTypeMetadataDocument> it3 = oneOf.iterator();
            int i3 = 0;
            while (it3.hasNext()) {
                genericDocumentArr3[i3] = GenericDocument.fromDocumentClass(it3.next());
                i3++;
            }
            builder.setPropertyDocument("oneOf", genericDocumentArr3);
        }
        List<String> required = appFunctionDataTypeMetadataDocument.getRequired();
        if (required != null) {
            builder.setPropertyString("required", (String[]) required.toArray(new String[0]));
        }
        String dataTypeReference = appFunctionDataTypeMetadataDocument.getDataTypeReference();
        if (dataTypeReference != null) {
            builder.setPropertyString("dataTypeReference", dataTypeReference);
        }
        builder.setPropertyBoolean("isNullable", appFunctionDataTypeMetadataDocument.isNullable());
        String objectQualifiedName = appFunctionDataTypeMetadataDocument.getObjectQualifiedName();
        if (objectQualifiedName != null) {
            builder.setPropertyString("objectQualifiedName", objectQualifiedName);
        }
        String description = appFunctionDataTypeMetadataDocument.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        List<String> enumValues = appFunctionDataTypeMetadataDocument.getEnumValues();
        if (enumValues != null) {
            builder.setPropertyString("enumValues", (String[]) enumValues.toArray(new String[0]));
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppFunctionDataTypeMetadataDocument fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        int propertyLong = (int) genericDocument.getPropertyLong("type");
        GenericDocument propertyDocument = genericDocument.getPropertyDocument("itemType");
        String str = null;
        AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument = propertyDocument != null ? (AppFunctionDataTypeMetadataDocument) propertyDocument.toDocumentClass(AppFunctionDataTypeMetadataDocument.class, documentClassMappingContext) : null;
        GenericDocument[] propertyDocumentArray = genericDocument.getPropertyDocumentArray(StringLookupFactory.KEY_PROPERTIES);
        List listEmptyList = Collections.emptyList();
        if (propertyDocumentArray != null) {
            listEmptyList = new ArrayList(propertyDocumentArray.length);
            for (GenericDocument genericDocument2 : propertyDocumentArray) {
                listEmptyList.add((AppFunctionNamedDataTypeMetadataDocument) genericDocument2.toDocumentClass(AppFunctionNamedDataTypeMetadataDocument.class, documentClassMappingContext));
            }
        }
        GenericDocument[] propertyDocumentArray2 = genericDocument.getPropertyDocumentArray("allOf");
        List listEmptyList2 = Collections.emptyList();
        if (propertyDocumentArray2 != null) {
            listEmptyList2 = new ArrayList(propertyDocumentArray2.length);
            for (GenericDocument genericDocument3 : propertyDocumentArray2) {
                listEmptyList2.add((AppFunctionDataTypeMetadataDocument) genericDocument3.toDocumentClass(AppFunctionDataTypeMetadataDocument.class, documentClassMappingContext));
            }
        }
        GenericDocument[] propertyDocumentArray3 = genericDocument.getPropertyDocumentArray("oneOf");
        List listEmptyList3 = Collections.emptyList();
        if (propertyDocumentArray3 != null) {
            listEmptyList3 = new ArrayList(propertyDocumentArray3.length);
            for (GenericDocument genericDocument4 : propertyDocumentArray3) {
                listEmptyList3.add((AppFunctionDataTypeMetadataDocument) genericDocument4.toDocumentClass(AppFunctionDataTypeMetadataDocument.class, documentClassMappingContext));
            }
        }
        List list = listEmptyList3;
        String[] propertyStringArray = genericDocument.getPropertyStringArray("required");
        List listEmptyList4 = Collections.emptyList();
        if (propertyStringArray != null) {
            listEmptyList4 = Arrays.asList(propertyStringArray);
        }
        String[] propertyStringArray2 = genericDocument.getPropertyStringArray("dataTypeReference");
        String str2 = (propertyStringArray2 == null || propertyStringArray2.length == 0) ? null : propertyStringArray2[0];
        boolean propertyBoolean = genericDocument.getPropertyBoolean("isNullable");
        String[] propertyStringArray3 = genericDocument.getPropertyStringArray("objectQualifiedName");
        String str3 = (propertyStringArray3 == null || propertyStringArray3.length == 0) ? null : propertyStringArray3[0];
        String[] propertyStringArray4 = genericDocument.getPropertyStringArray("description");
        if (propertyStringArray4 != null && propertyStringArray4.length != 0) {
            str = propertyStringArray4[0];
        }
        String[] propertyStringArray5 = genericDocument.getPropertyStringArray("enumValues");
        List listEmptyList5 = Collections.emptyList();
        if (propertyStringArray5 != null) {
            listEmptyList5 = Arrays.asList(propertyStringArray5);
        }
        return new AppFunctionDataTypeMetadataDocument(namespace, id, propertyLong, appFunctionDataTypeMetadataDocument, listEmptyList, listEmptyList2, list, listEmptyList4, str2, propertyBoolean, str3, str, listEmptyList5);
    }
}
