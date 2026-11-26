package androidx.appsearch.builtintypes;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import com.miui.maml.data.VariableNames;
import com.miui.notes.ai.stat.AITextWidgetStat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__Alarm, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__Alarm implements DocumentClassFactory<Alarm> {
    public static final String SCHEMA_NAME = "builtin:Alarm";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("alternateNames").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(AITextWidgetStat.TYPE_IMAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("url").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("potentialActions", C$$__AppSearch__PotentialAction.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.BooleanPropertyConfig.Builder("enabled").setCardinality(2).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("daysOfWeek").setCardinality(1).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("hour").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder(VariableNames.VAR_MINUTE).setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("blackoutPeriodStartDate").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("blackoutPeriodEndDate").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("ringtone").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.BooleanPropertyConfig.Builder("shouldVibrate").setCardinality(2).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("previousInstance", C$$__AppSearch__AlarmInstance.SCHEMA_NAME).setCardinality(2).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("nextInstance", C$$__AppSearch__AlarmInstance.SCHEMA_NAME).setCardinality(2).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("computingDevice").setCardinality(2).setIndexingType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PotentialAction.class);
        arrayList.add(AlarmInstance.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(Alarm alarm) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(alarm.getNamespace(), alarm.getId(), SCHEMA_NAME);
        builder.setScore(alarm.getDocumentScore());
        builder.setCreationTimestampMillis(alarm.getCreationTimestampMillis());
        builder.setTtlMillis(alarm.getDocumentTtlMillis());
        String name = alarm.getName();
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        List<String> alternateNames = alarm.getAlternateNames();
        if (alternateNames != null) {
            builder.setPropertyString("alternateNames", (String[]) alternateNames.toArray(new String[0]));
        }
        String description = alarm.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String image = alarm.getImage();
        if (image != null) {
            builder.setPropertyString(AITextWidgetStat.TYPE_IMAGE, image);
        }
        String url = alarm.getUrl();
        if (url != null) {
            builder.setPropertyString("url", url);
        }
        List<PotentialAction> potentialActions = alarm.getPotentialActions();
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
        builder.setPropertyBoolean("enabled", alarm.isEnabled());
        int[] daysOfWeek = alarm.getDaysOfWeek();
        if (daysOfWeek != null) {
            long[] jArr = new long[daysOfWeek.length];
            for (int i2 = 0; i2 < daysOfWeek.length; i2++) {
                jArr[i2] = daysOfWeek[i2];
            }
            builder.setPropertyLong("daysOfWeek", jArr);
        }
        builder.setPropertyLong("hour", alarm.getHour());
        builder.setPropertyLong(VariableNames.VAR_MINUTE, alarm.getMinute());
        String blackoutPeriodStartDate = alarm.getBlackoutPeriodStartDate();
        if (blackoutPeriodStartDate != null) {
            builder.setPropertyString("blackoutPeriodStartDate", blackoutPeriodStartDate);
        }
        String blackoutPeriodEndDate = alarm.getBlackoutPeriodEndDate();
        if (blackoutPeriodEndDate != null) {
            builder.setPropertyString("blackoutPeriodEndDate", blackoutPeriodEndDate);
        }
        String ringtone = alarm.getRingtone();
        if (ringtone != null) {
            builder.setPropertyString("ringtone", ringtone);
        }
        builder.setPropertyBoolean("shouldVibrate", alarm.shouldVibrate());
        AlarmInstance previousInstance = alarm.getPreviousInstance();
        if (previousInstance != null) {
            builder.setPropertyDocument("previousInstance", GenericDocument.fromDocumentClass(previousInstance));
        }
        AlarmInstance nextInstance = alarm.getNextInstance();
        if (nextInstance != null) {
            builder.setPropertyDocument("nextInstance", GenericDocument.fromDocumentClass(nextInstance));
        }
        builder.setPropertyLong("computingDevice", alarm.getOriginatingDevice());
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public Alarm fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        ArrayList arrayList;
        int[] iArr;
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
        boolean propertyBoolean = genericDocument.getPropertyBoolean("enabled");
        long[] propertyLongArray = genericDocument.getPropertyLongArray("daysOfWeek");
        if (propertyLongArray != null) {
            int[] iArr2 = new int[propertyLongArray.length];
            int i = 0;
            while (i < propertyLongArray.length) {
                long[] jArr = propertyLongArray;
                int i2 = i;
                iArr2[i2] = (int) jArr[i2];
                i = i2 + 1;
                propertyLongArray = jArr;
            }
            iArr = iArr2;
        } else {
            iArr = null;
        }
        int[] iArr3 = iArr;
        int propertyLong = (int) genericDocument.getPropertyLong("hour");
        int propertyLong2 = (int) genericDocument.getPropertyLong(VariableNames.VAR_MINUTE);
        String[] propertyStringArray6 = genericDocument.getPropertyStringArray("blackoutPeriodStartDate");
        String str5 = (propertyStringArray6 == null || propertyStringArray6.length == 0) ? null : propertyStringArray6[0];
        String[] propertyStringArray7 = genericDocument.getPropertyStringArray("blackoutPeriodEndDate");
        String str6 = str5;
        String str7 = (propertyStringArray7 == null || propertyStringArray7.length == 0) ? null : propertyStringArray7[0];
        String[] propertyStringArray8 = genericDocument.getPropertyStringArray("ringtone");
        String str8 = str7;
        String str9 = (propertyStringArray8 == null || propertyStringArray8.length == 0) ? null : propertyStringArray8[0];
        boolean propertyBoolean2 = genericDocument.getPropertyBoolean("shouldVibrate");
        GenericDocument propertyDocument = genericDocument.getPropertyDocument("previousInstance");
        String str10 = str9;
        AlarmInstance alarmInstance = propertyDocument != null ? (AlarmInstance) propertyDocument.toDocumentClass(AlarmInstance.class, documentClassMappingContext) : null;
        GenericDocument propertyDocument2 = genericDocument.getPropertyDocument("nextInstance");
        AlarmInstance alarmInstance2 = alarmInstance;
        AlarmInstance alarmInstance3 = propertyDocument2 != null ? (AlarmInstance) propertyDocument2.toDocumentClass(AlarmInstance.class, documentClassMappingContext) : null;
        int propertyLong3 = (int) genericDocument.getPropertyLong("computingDevice");
        Alarm.Builder builder = new Alarm.Builder(namespace, id);
        builder.setDocumentScore(score);
        builder.setCreationTimestampMillis(creationTimestampMillis);
        builder.setDocumentTtlMillis(ttlMillis);
        builder.setName(str);
        builder.setAlternateNames(listAsList);
        builder.setDescription(str2);
        builder.setImage(str3);
        builder.setUrl(str4);
        builder.setPotentialActions(arrayList);
        builder.setEnabled(propertyBoolean);
        builder.setDaysOfWeek(iArr3);
        builder.setHour(propertyLong);
        builder.setMinute(propertyLong2);
        builder.setBlackoutPeriodStartDate(str6);
        builder.setBlackoutPeriodEndDate(str8);
        builder.setRingtone(str10);
        builder.setShouldVibrate(propertyBoolean2);
        builder.setPreviousInstance(alarmInstance2);
        builder.setNextInstance(alarmInstance3);
        builder.setOriginatingDevice(propertyLong3);
        return builder.build();
    }
}
