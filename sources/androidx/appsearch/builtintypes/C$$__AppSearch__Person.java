package androidx.appsearch.builtintypes;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import com.miui.notes.ai.stat.AITextWidgetStat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__Person, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__Person implements DocumentClassFactory<Person> {
    public static final String SCHEMA_NAME = "builtin:Person";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("alternateNames").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(AITextWidgetStat.TYPE_IMAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("url").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("potentialActions", C$$__AppSearch__PotentialAction.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("givenName").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("middleName").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("familyName").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("externalUri").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("imageUri").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.BooleanPropertyConfig.Builder("isImportant").setCardinality(2).build()).addProperty(new AppSearchSchema.BooleanPropertyConfig.Builder("isBot").setCardinality(2).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("notes").setCardinality(1).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("additionalNameTypes").setCardinality(1).setIndexingType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("additionalNames").setCardinality(1).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("affiliations").setCardinality(1).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("relations").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("contactPoints", C$$__AppSearch__ContactPoint.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(true).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PotentialAction.class);
        arrayList.add(ContactPoint.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(Person person) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(person.getNamespace(), person.getId(), SCHEMA_NAME);
        builder.setScore(person.getDocumentScore());
        builder.setCreationTimestampMillis(person.getCreationTimestampMillis());
        builder.setTtlMillis(person.getDocumentTtlMillis());
        String name = person.getName();
        int i = 0;
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        List<String> alternateNames = person.getAlternateNames();
        if (alternateNames != null) {
            builder.setPropertyString("alternateNames", (String[]) alternateNames.toArray(new String[0]));
        }
        String description = person.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String image = person.getImage();
        if (image != null) {
            builder.setPropertyString(AITextWidgetStat.TYPE_IMAGE, image);
        }
        String url = person.getUrl();
        if (url != null) {
            builder.setPropertyString("url", url);
        }
        List<PotentialAction> potentialActions = person.getPotentialActions();
        if (potentialActions != null) {
            GenericDocument[] genericDocumentArr = new GenericDocument[potentialActions.size()];
            Iterator<PotentialAction> it = potentialActions.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                genericDocumentArr[i2] = GenericDocument.fromDocumentClass(it.next());
                i2++;
            }
            builder.setPropertyDocument("potentialActions", genericDocumentArr);
        }
        String givenName = person.getGivenName();
        if (givenName != null) {
            builder.setPropertyString("givenName", givenName);
        }
        String middleName = person.getMiddleName();
        if (middleName != null) {
            builder.setPropertyString("middleName", middleName);
        }
        String familyName = person.getFamilyName();
        if (familyName != null) {
            builder.setPropertyString("familyName", familyName);
        }
        String str = person.mExternalUri;
        if (str != null) {
            builder.setPropertyString("externalUri", str);
        }
        String str2 = person.mImageUri;
        if (str2 != null) {
            builder.setPropertyString("imageUri", str2);
        }
        builder.setPropertyBoolean("isImportant", person.mIsImportant);
        builder.setPropertyBoolean("isBot", person.mIsBot);
        List<String> notes = person.getNotes();
        if (notes != null) {
            builder.setPropertyString("notes", (String[]) notes.toArray(new String[0]));
        }
        List<Long> list = person.mAdditionalNameTypes;
        if (list != null) {
            long[] jArr = new long[list.size()];
            Iterator<Long> it2 = list.iterator();
            int i3 = 0;
            while (it2.hasNext()) {
                jArr[i3] = it2.next().longValue();
                i3++;
            }
            builder.setPropertyLong("additionalNameTypes", jArr);
        }
        List<String> list2 = person.mAdditionalNames;
        if (list2 != null) {
            builder.setPropertyString("additionalNames", (String[]) list2.toArray(new String[0]));
        }
        List<String> affiliations = person.getAffiliations();
        if (affiliations != null) {
            builder.setPropertyString("affiliations", (String[]) affiliations.toArray(new String[0]));
        }
        List<String> relations = person.getRelations();
        if (relations != null) {
            builder.setPropertyString("relations", (String[]) relations.toArray(new String[0]));
        }
        List<ContactPoint> contactPoints = person.getContactPoints();
        if (contactPoints != null) {
            GenericDocument[] genericDocumentArr2 = new GenericDocument[contactPoints.size()];
            Iterator<ContactPoint> it3 = contactPoints.iterator();
            while (it3.hasNext()) {
                genericDocumentArr2[i] = GenericDocument.fromDocumentClass(it3.next());
                i++;
            }
            builder.setPropertyDocument("contactPoints", genericDocumentArr2);
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public Person fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        int score = genericDocument.getScore();
        long creationTimestampMillis = genericDocument.getCreationTimestampMillis();
        long ttlMillis = genericDocument.getTtlMillis();
        String[] propertyStringArray = genericDocument.getPropertyStringArray("name");
        String str = (propertyStringArray == null || propertyStringArray.length == 0) ? null : propertyStringArray[0];
        String[] propertyStringArray2 = genericDocument.getPropertyStringArray("alternateNames");
        List listAsList = propertyStringArray2 != null ? Arrays.asList(propertyStringArray2) : null;
        String[] propertyStringArray3 = genericDocument.getPropertyStringArray("description");
        String str2 = (propertyStringArray3 == null || propertyStringArray3.length == 0) ? null : propertyStringArray3[0];
        String[] propertyStringArray4 = genericDocument.getPropertyStringArray(AITextWidgetStat.TYPE_IMAGE);
        String str3 = (propertyStringArray4 == null || propertyStringArray4.length == 0) ? null : propertyStringArray4[0];
        String[] propertyStringArray5 = genericDocument.getPropertyStringArray("url");
        String str4 = (propertyStringArray5 == null || propertyStringArray5.length == 0) ? null : propertyStringArray5[0];
        GenericDocument[] propertyDocumentArray = genericDocument.getPropertyDocumentArray("potentialActions");
        if (propertyDocumentArray != null) {
            arrayList = new ArrayList(propertyDocumentArray.length);
            for (GenericDocument genericDocument2 : propertyDocumentArray) {
                arrayList.add((PotentialAction) genericDocument2.toDocumentClass(PotentialAction.class, documentClassMappingContext));
            }
        } else {
            arrayList = null;
        }
        String[] propertyStringArray6 = genericDocument.getPropertyStringArray("givenName");
        String str5 = (propertyStringArray6 == null || propertyStringArray6.length == 0) ? null : propertyStringArray6[0];
        String[] propertyStringArray7 = genericDocument.getPropertyStringArray("middleName");
        String str6 = (propertyStringArray7 == null || propertyStringArray7.length == 0) ? null : propertyStringArray7[0];
        String[] propertyStringArray8 = genericDocument.getPropertyStringArray("familyName");
        String str7 = str5;
        String str8 = (propertyStringArray8 == null || propertyStringArray8.length == 0) ? null : propertyStringArray8[0];
        String[] propertyStringArray9 = genericDocument.getPropertyStringArray("externalUri");
        String str9 = str8;
        String str10 = (propertyStringArray9 == null || propertyStringArray9.length == 0) ? null : propertyStringArray9[0];
        String[] propertyStringArray10 = genericDocument.getPropertyStringArray("imageUri");
        String str11 = str10;
        String str12 = (propertyStringArray10 == null || propertyStringArray10.length == 0) ? null : propertyStringArray10[0];
        boolean propertyBoolean = genericDocument.getPropertyBoolean("isImportant");
        String str13 = str12;
        boolean propertyBoolean2 = genericDocument.getPropertyBoolean("isBot");
        String[] propertyStringArray11 = genericDocument.getPropertyStringArray("notes");
        List listAsList2 = propertyStringArray11 != null ? Arrays.asList(propertyStringArray11) : null;
        long[] propertyLongArray = genericDocument.getPropertyLongArray("additionalNameTypes");
        if (propertyLongArray != null) {
            arrayList2 = new ArrayList(propertyLongArray.length);
            for (long j : propertyLongArray) {
                arrayList2.add(Long.valueOf(j));
            }
        } else {
            arrayList2 = null;
        }
        String[] propertyStringArray12 = genericDocument.getPropertyStringArray("additionalNames");
        List listAsList3 = propertyStringArray12 != null ? Arrays.asList(propertyStringArray12) : null;
        String[] propertyStringArray13 = genericDocument.getPropertyStringArray("affiliations");
        List listAsList4 = propertyStringArray13 != null ? Arrays.asList(propertyStringArray13) : null;
        String[] propertyStringArray14 = genericDocument.getPropertyStringArray("relations");
        List listAsList5 = propertyStringArray14 != null ? Arrays.asList(propertyStringArray14) : null;
        List list = listAsList3;
        GenericDocument[] propertyDocumentArray2 = genericDocument.getPropertyDocumentArray("contactPoints");
        if (propertyDocumentArray2 != null) {
            ArrayList arrayList4 = new ArrayList(propertyDocumentArray2.length);
            int i = 0;
            while (i < propertyDocumentArray2.length) {
                arrayList4.add((ContactPoint) propertyDocumentArray2[i].toDocumentClass(ContactPoint.class, documentClassMappingContext));
                i++;
                propertyDocumentArray2 = propertyDocumentArray2;
            }
            arrayList3 = arrayList4;
        } else {
            arrayList3 = null;
        }
        return new Person(namespace, id, score, creationTimestampMillis, ttlMillis, str, listAsList, str2, str3, str4, arrayList, str7, str6, str9, str11, str13, propertyBoolean, propertyBoolean2, listAsList2, arrayList2, list, listAsList4, listAsList5, arrayList3);
    }
}
