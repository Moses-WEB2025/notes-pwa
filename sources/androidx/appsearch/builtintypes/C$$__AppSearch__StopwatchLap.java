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

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__StopwatchLap, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__StopwatchLap implements DocumentClassFactory<StopwatchLap> {
    public static final String SCHEMA_NAME = "builtin:StopwatchLap";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("alternateNames").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(AITextWidgetStat.TYPE_IMAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("url").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("potentialActions", C$$__AppSearch__PotentialAction.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("lapNumber").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("lapDurationMillis").setCardinality(2).setIndexingType(0).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("accumulatedLapDurationMillis").setCardinality(2).setIndexingType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PotentialAction.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(StopwatchLap stopwatchLap) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(stopwatchLap.getNamespace(), stopwatchLap.getId(), SCHEMA_NAME);
        builder.setScore(stopwatchLap.getDocumentScore());
        builder.setCreationTimestampMillis(stopwatchLap.getCreationTimestampMillis());
        builder.setTtlMillis(stopwatchLap.getDocumentTtlMillis());
        String name = stopwatchLap.getName();
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        List<String> alternateNames = stopwatchLap.getAlternateNames();
        if (alternateNames != null) {
            builder.setPropertyString("alternateNames", (String[]) alternateNames.toArray(new String[0]));
        }
        String description = stopwatchLap.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String image = stopwatchLap.getImage();
        if (image != null) {
            builder.setPropertyString(AITextWidgetStat.TYPE_IMAGE, image);
        }
        String url = stopwatchLap.getUrl();
        if (url != null) {
            builder.setPropertyString("url", url);
        }
        List<PotentialAction> potentialActions = stopwatchLap.getPotentialActions();
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
        builder.setPropertyLong("lapNumber", stopwatchLap.getLapNumber());
        builder.setPropertyLong("lapDurationMillis", stopwatchLap.getLapDurationMillis());
        builder.setPropertyLong("accumulatedLapDurationMillis", stopwatchLap.getAccumulatedLapDurationMillis());
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public StopwatchLap fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
        String namespace = genericDocument.getNamespace();
        String id = genericDocument.getId();
        int score = genericDocument.getScore();
        long creationTimestampMillis = genericDocument.getCreationTimestampMillis();
        long ttlMillis = genericDocument.getTtlMillis();
        String[] propertyStringArray = genericDocument.getPropertyStringArray("name");
        ArrayList arrayList = null;
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
        }
        int propertyLong = (int) genericDocument.getPropertyLong("lapNumber");
        long propertyLong2 = genericDocument.getPropertyLong("lapDurationMillis");
        long propertyLong3 = genericDocument.getPropertyLong("accumulatedLapDurationMillis");
        StopwatchLap.Builder builder = new StopwatchLap.Builder(namespace, id);
        builder.setDocumentScore(score);
        builder.setCreationTimestampMillis(creationTimestampMillis);
        builder.setDocumentTtlMillis(ttlMillis);
        builder.setName(str);
        builder.setAlternateNames(listAsList);
        builder.setDescription(str2);
        builder.setImage(str3);
        builder.setUrl(str4);
        builder.setPotentialActions(arrayList);
        builder.setLapNumber(propertyLong);
        builder.setLapDurationMillis(propertyLong2);
        builder.setAccumulatedLapDurationMillis(propertyLong3);
        return builder.build();
    }
}
