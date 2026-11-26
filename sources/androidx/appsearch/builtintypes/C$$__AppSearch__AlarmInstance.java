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

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__AlarmInstance, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__AlarmInstance implements DocumentClassFactory<AlarmInstance> {
    public static final String SCHEMA_NAME = "builtin:AlarmInstance";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("alternateNames").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(AITextWidgetStat.TYPE_IMAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("url").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("potentialActions", C$$__AppSearch__PotentialAction.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("scheduledTime").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("status").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("snoozeDurationMillis").setCardinality(2).setIndexingType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PotentialAction.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(AlarmInstance alarmInstance) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(alarmInstance.getNamespace(), alarmInstance.getId(), SCHEMA_NAME);
        builder.setScore(alarmInstance.getDocumentScore());
        builder.setCreationTimestampMillis(alarmInstance.getCreationTimestampMillis());
        builder.setTtlMillis(alarmInstance.getDocumentTtlMillis());
        String name = alarmInstance.getName();
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        List<String> alternateNames = alarmInstance.getAlternateNames();
        if (alternateNames != null) {
            builder.setPropertyString("alternateNames", (String[]) alternateNames.toArray(new String[0]));
        }
        String description = alarmInstance.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String image = alarmInstance.getImage();
        if (image != null) {
            builder.setPropertyString(AITextWidgetStat.TYPE_IMAGE, image);
        }
        String url = alarmInstance.getUrl();
        if (url != null) {
            builder.setPropertyString("url", url);
        }
        List<PotentialAction> potentialActions = alarmInstance.getPotentialActions();
        if (potentialActions != null) {
            GenericDocument[] genericDocumentArr = new GenericDocument[potentialActions.size()];
            Iterator<PotentialAction> it = potentialActions.iterator();
            int i = 0;
            while (it.hasNext()) {
                genericDocumentArr[i] = GenericDocument.fromDocumentClass(it.next());
                i++;
            }
            builder.setPropertyDocument("potentialActions", genericDocumentArr);
        }
        String scheduledTime = alarmInstance.getScheduledTime();
        if (scheduledTime != null) {
            builder.setPropertyString("scheduledTime", scheduledTime);
        }
        builder.setPropertyLong("status", alarmInstance.getStatus());
        builder.setPropertyLong("snoozeDurationMillis", alarmInstance.getSnoozeDurationMillis());
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public AlarmInstance fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        ArrayList arrayList;
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
            int i = 0;
            while (i < propertyDocumentArray.length) {
                arrayList.add((PotentialAction) propertyDocumentArray[i].toDocumentClass(PotentialAction.class, documentClassMappingContext));
                i++;
                propertyDocumentArray = propertyDocumentArray;
            }
        } else {
            arrayList = null;
        }
        String[] propertyStringArray6 = genericDocument.getPropertyStringArray("scheduledTime");
        String str5 = (propertyStringArray6 == null || propertyStringArray6.length == 0) ? null : propertyStringArray6[0];
        int propertyLong = (int) genericDocument.getPropertyLong("status");
        long propertyLong2 = genericDocument.getPropertyLong("snoozeDurationMillis");
        AlarmInstance.Builder builder = new AlarmInstance.Builder(namespace, id, str5);
        builder.setDocumentScore(score);
        builder.setCreationTimestampMillis(creationTimestampMillis);
        builder.setDocumentTtlMillis(ttlMillis);
        builder.setName(str);
        builder.setAlternateNames(listAsList);
        builder.setDescription(str2);
        builder.setImage(str3);
        builder.setUrl(str4);
        builder.setPotentialActions(arrayList);
        builder.setStatus(propertyLong);
        builder.setSnoozeDurationMillis(propertyLong2);
        return builder.build();
    }
}
