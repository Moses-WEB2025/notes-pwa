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

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__Timer, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__Timer implements DocumentClassFactory<Timer> {
    public static final String SCHEMA_NAME = "builtin:Timer";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("alternateNames").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(AITextWidgetStat.TYPE_IMAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("url").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("potentialActions", C$$__AppSearch__PotentialAction.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("durationMillis").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("originalDurationMillis").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("startTimeMillis").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("baseTimeMillis").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("baseTimeMillisInElapsedRealtime").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("bootCount").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("remainingDurationMillis").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("ringtone").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("status").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.BooleanPropertyConfig.Builder("shouldVibrate").setCardinality(2).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PotentialAction.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(Timer timer) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(timer.getNamespace(), timer.getId(), SCHEMA_NAME);
        builder.setScore(timer.getDocumentScore());
        builder.setCreationTimestampMillis(timer.getCreationTimestampMillis());
        builder.setTtlMillis(timer.getDocumentTtlMillis());
        String name = timer.getName();
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        List<String> alternateNames = timer.getAlternateNames();
        if (alternateNames != null) {
            builder.setPropertyString("alternateNames", (String[]) alternateNames.toArray(new String[0]));
        }
        String description = timer.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String image = timer.getImage();
        if (image != null) {
            builder.setPropertyString(AITextWidgetStat.TYPE_IMAGE, image);
        }
        String url = timer.getUrl();
        if (url != null) {
            builder.setPropertyString("url", url);
        }
        List<PotentialAction> potentialActions = timer.getPotentialActions();
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
        builder.setPropertyLong("durationMillis", timer.getDurationMillis());
        builder.setPropertyLong("originalDurationMillis", timer.getOriginalDurationMillis());
        builder.setPropertyLong("startTimeMillis", timer.getStartTimeMillis());
        builder.setPropertyLong("baseTimeMillis", timer.getBaseTimeMillis());
        builder.setPropertyLong("baseTimeMillisInElapsedRealtime", timer.getBaseTimeMillisInElapsedRealtime());
        builder.setPropertyLong("bootCount", timer.getBootCount());
        builder.setPropertyLong("remainingDurationMillis", timer.getRemainingDurationMillis());
        String ringtone = timer.getRingtone();
        if (ringtone != null) {
            builder.setPropertyString("ringtone", ringtone);
        }
        builder.setPropertyLong("status", timer.getStatus());
        builder.setPropertyBoolean("shouldVibrate", timer.shouldVibrate());
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public Timer fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        char c;
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
            c = 0;
            arrayList = new ArrayList(propertyDocumentArray.length);
            int i = 0;
            while (i < propertyDocumentArray.length) {
                arrayList.add((PotentialAction) propertyDocumentArray[i].toDocumentClass(PotentialAction.class, documentClassMappingContext));
                i++;
                id = id;
                score = score;
            }
        } else {
            c = 0;
            arrayList = null;
        }
        String str5 = id;
        int i2 = score;
        long propertyLong = genericDocument.getPropertyLong("durationMillis");
        long propertyLong2 = genericDocument.getPropertyLong("originalDurationMillis");
        long propertyLong3 = genericDocument.getPropertyLong("startTimeMillis");
        long propertyLong4 = genericDocument.getPropertyLong("baseTimeMillis");
        long propertyLong5 = genericDocument.getPropertyLong("baseTimeMillisInElapsedRealtime");
        int propertyLong6 = (int) genericDocument.getPropertyLong("bootCount");
        long propertyLong7 = genericDocument.getPropertyLong("remainingDurationMillis");
        String[] propertyStringArray6 = genericDocument.getPropertyStringArray("ringtone");
        return new Timer(namespace, str5, i2, creationTimestampMillis, ttlMillis, str, listAsList, str2, str3, str4, arrayList, propertyLong, propertyLong2, propertyLong3, propertyLong4, propertyLong5, propertyLong6, propertyLong7, (propertyStringArray6 == null || propertyStringArray6.length == 0) ? null : propertyStringArray6[c], (int) genericDocument.getPropertyLong("status"), genericDocument.getPropertyBoolean("shouldVibrate"));
    }
}
