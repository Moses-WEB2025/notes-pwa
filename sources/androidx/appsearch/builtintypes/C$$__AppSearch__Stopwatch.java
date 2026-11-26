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

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__Stopwatch, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__Stopwatch implements DocumentClassFactory<Stopwatch> {
    public static final String SCHEMA_NAME = "builtin:Stopwatch";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("alternateNames").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(AITextWidgetStat.TYPE_IMAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("url").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("potentialActions", C$$__AppSearch__PotentialAction.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("baseTimeMillis").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("baseTimeMillisInElapsedRealtime").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("bootCount").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("status").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("accumulatedDurationMillis").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("laps", C$$__AppSearch__StopwatchLap.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PotentialAction.class);
        arrayList.add(StopwatchLap.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(Stopwatch stopwatch) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(stopwatch.getNamespace(), stopwatch.getId(), SCHEMA_NAME);
        builder.setScore(stopwatch.getDocumentScore());
        builder.setCreationTimestampMillis(stopwatch.getCreationTimestampMillis());
        builder.setTtlMillis(stopwatch.getDocumentTtlMillis());
        String name = stopwatch.getName();
        int i = 0;
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        List<String> alternateNames = stopwatch.getAlternateNames();
        if (alternateNames != null) {
            builder.setPropertyString("alternateNames", (String[]) alternateNames.toArray(new String[0]));
        }
        String description = stopwatch.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String image = stopwatch.getImage();
        if (image != null) {
            builder.setPropertyString(AITextWidgetStat.TYPE_IMAGE, image);
        }
        String url = stopwatch.getUrl();
        if (url != null) {
            builder.setPropertyString("url", url);
        }
        List<PotentialAction> potentialActions = stopwatch.getPotentialActions();
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
        builder.setPropertyLong("baseTimeMillis", stopwatch.getBaseTimeMillis());
        builder.setPropertyLong("baseTimeMillisInElapsedRealtime", stopwatch.getBaseTimeMillisInElapsedRealtime());
        builder.setPropertyLong("bootCount", stopwatch.getBootCount());
        builder.setPropertyLong("status", stopwatch.getStatus());
        builder.setPropertyLong("accumulatedDurationMillis", stopwatch.getAccumulatedDurationMillis());
        List<StopwatchLap> laps = stopwatch.getLaps();
        if (laps != null) {
            GenericDocument[] genericDocumentArr2 = new GenericDocument[laps.size()];
            Iterator<StopwatchLap> it2 = laps.iterator();
            while (it2.hasNext()) {
                genericDocumentArr2[i] = GenericDocument.fromDocumentClass(it2.next());
                i++;
            }
            builder.setPropertyDocument("laps", genericDocumentArr2);
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public Stopwatch fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        ArrayList arrayList;
        ArrayList arrayList2;
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
        long propertyLong = genericDocument.getPropertyLong("baseTimeMillis");
        long propertyLong2 = genericDocument.getPropertyLong("baseTimeMillisInElapsedRealtime");
        int propertyLong3 = (int) genericDocument.getPropertyLong("bootCount");
        int propertyLong4 = (int) genericDocument.getPropertyLong("status");
        long propertyLong5 = genericDocument.getPropertyLong("accumulatedDurationMillis");
        GenericDocument[] propertyDocumentArray2 = genericDocument.getPropertyDocumentArray("laps");
        if (propertyDocumentArray2 != null) {
            ArrayList arrayList3 = new ArrayList(propertyDocumentArray2.length);
            for (GenericDocument genericDocument3 : propertyDocumentArray2) {
                arrayList3.add((StopwatchLap) genericDocument3.toDocumentClass(StopwatchLap.class, documentClassMappingContext));
            }
            arrayList2 = arrayList3;
        } else {
            arrayList2 = null;
        }
        return new Stopwatch(namespace, id, score, creationTimestampMillis, ttlMillis, str, listAsList, str2, str3, str4, arrayList, propertyLong, propertyLong2, propertyLong3, propertyLong4, propertyLong5, arrayList2);
    }
}
