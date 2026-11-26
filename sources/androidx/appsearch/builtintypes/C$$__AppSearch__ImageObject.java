package androidx.appsearch.builtintypes;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.builtintypes.properties.C$$__AppSearch__Keyword;
import androidx.appsearch.builtintypes.properties.Keyword;
import androidx.appsearch.exceptions.AppSearchException;
import com.miui.notes.ai.stat.AITextWidgetStat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__ImageObject, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__ImageObject implements DocumentClassFactory<ImageObject> {
    public static final String SCHEMA_NAME = "builtin:ImageObject";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("alternateNames").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(AITextWidgetStat.TYPE_IMAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("url").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("potentialActions", C$$__AppSearch__PotentialAction.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("keywords", C$$__AppSearch__Keyword.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(true).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("sha256").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("thumbnailSha256").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.BytesPropertyConfig.Builder("bytes").setCardinality(2).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PotentialAction.class);
        arrayList.add(Keyword.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(ImageObject imageObject) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(imageObject.getNamespace(), imageObject.getId(), SCHEMA_NAME);
        builder.setScore(imageObject.getDocumentScore());
        builder.setCreationTimestampMillis(imageObject.getCreationTimestampMillis());
        builder.setTtlMillis(imageObject.getDocumentTtlMillis());
        String name = imageObject.getName();
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        List<String> alternateNames = imageObject.getAlternateNames();
        if (alternateNames != null) {
            builder.setPropertyString("alternateNames", (String[]) alternateNames.toArray(new String[0]));
        }
        String description = imageObject.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String image = imageObject.getImage();
        if (image != null) {
            builder.setPropertyString(AITextWidgetStat.TYPE_IMAGE, image);
        }
        String url = imageObject.getUrl();
        if (url != null) {
            builder.setPropertyString("url", url);
        }
        List<PotentialAction> potentialActions = imageObject.getPotentialActions();
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
        List<Keyword> keywords = imageObject.getKeywords();
        if (keywords != null) {
            GenericDocument[] genericDocumentArr2 = new GenericDocument[keywords.size()];
            Iterator<Keyword> it2 = keywords.iterator();
            int i2 = 0;
            while (it2.hasNext()) {
                genericDocumentArr2[i2] = GenericDocument.fromDocumentClass(it2.next());
                i2++;
            }
            builder.setPropertyDocument("keywords", genericDocumentArr2);
        }
        String sha256 = imageObject.getSha256();
        if (sha256 != null) {
            builder.setPropertyString("sha256", sha256);
        }
        String thumbnailSha256 = imageObject.getThumbnailSha256();
        if (thumbnailSha256 != null) {
            builder.setPropertyString("thumbnailSha256", thumbnailSha256);
        }
        byte[] bytes = imageObject.getBytes();
        if (bytes != null) {
            builder.setPropertyBytes("bytes", bytes);
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public ImageObject fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
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
        GenericDocument[] propertyDocumentArray2 = genericDocument.getPropertyDocumentArray("keywords");
        if (propertyDocumentArray2 != null) {
            arrayList2 = new ArrayList(propertyDocumentArray2.length);
            for (GenericDocument genericDocument3 : propertyDocumentArray2) {
                arrayList2.add((Keyword) genericDocument3.toDocumentClass(Keyword.class, documentClassMappingContext));
            }
        } else {
            arrayList2 = null;
        }
        String[] propertyStringArray6 = genericDocument.getPropertyStringArray("sha256");
        String str5 = (propertyStringArray6 == null || propertyStringArray6.length == 0) ? null : propertyStringArray6[0];
        String[] propertyStringArray7 = genericDocument.getPropertyStringArray("thumbnailSha256");
        String str6 = (propertyStringArray7 == null || propertyStringArray7.length == 0) ? null : propertyStringArray7[0];
        byte[][] propertyBytesArray = genericDocument.getPropertyBytesArray("bytes");
        return new ImageObject(namespace, id, score, creationTimestampMillis, ttlMillis, str, listAsList, str2, str3, str4, arrayList, arrayList2, str5, str6, (propertyBytesArray == null || propertyBytesArray.length == 0) ? null : propertyBytesArray[0]);
    }
}
