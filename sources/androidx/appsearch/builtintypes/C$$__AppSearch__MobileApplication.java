package androidx.appsearch.builtintypes;

import android.net.Uri;
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

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__MobileApplication, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__MobileApplication implements DocumentClassFactory<MobileApplication> {
    public static final String SCHEMA_NAME = "builtin:MobileApplication";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("alternateNames").setCardinality(1).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(AITextWidgetStat.TYPE_IMAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("url").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("potentialActions", C$$__AppSearch__PotentialAction.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("packageName").setCardinality(2).setTokenizerType(2).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("displayName").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("iconUri").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.BytesPropertyConfig.Builder("sha256Certificate").setCardinality(2).build()).addProperty(new AppSearchSchema.LongPropertyConfig.Builder("updatedTimestamp").setCardinality(2).setIndexingType(1).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("className").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PotentialAction.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(MobileApplication mobileApplication) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(mobileApplication.getNamespace(), mobileApplication.getId(), SCHEMA_NAME);
        builder.setScore(mobileApplication.getDocumentScore());
        builder.setCreationTimestampMillis(mobileApplication.getCreationTimestampMillis());
        builder.setTtlMillis(mobileApplication.getDocumentTtlMillis());
        String name = mobileApplication.getName();
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        List<String> alternateNames = mobileApplication.getAlternateNames();
        if (alternateNames != null) {
            builder.setPropertyString("alternateNames", (String[]) alternateNames.toArray(new String[0]));
        }
        String description = mobileApplication.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String image = mobileApplication.getImage();
        if (image != null) {
            builder.setPropertyString(AITextWidgetStat.TYPE_IMAGE, image);
        }
        String url = mobileApplication.getUrl();
        if (url != null) {
            builder.setPropertyString("url", url);
        }
        List<PotentialAction> potentialActions = mobileApplication.getPotentialActions();
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
        String packageName = mobileApplication.getPackageName();
        if (packageName != null) {
            builder.setPropertyString("packageName", packageName);
        }
        String displayName = mobileApplication.getDisplayName();
        if (displayName != null) {
            builder.setPropertyString("displayName", displayName);
        }
        Uri iconUri = mobileApplication.getIconUri();
        if (iconUri != null) {
            builder.setPropertyString("iconUri", new MobileApplication.IconUriAsUri().serialize(iconUri));
        }
        byte[] sha256Certificate = mobileApplication.getSha256Certificate();
        if (sha256Certificate != null) {
            builder.setPropertyBytes("sha256Certificate", sha256Certificate);
        }
        builder.setPropertyLong("updatedTimestamp", mobileApplication.getUpdatedTimestampMillis());
        String className = mobileApplication.getClassName();
        if (className != null) {
            builder.setPropertyString("className", className);
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public MobileApplication fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
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
        String[] propertyStringArray6 = genericDocument.getPropertyStringArray("packageName");
        String str6 = (propertyStringArray6 == null || propertyStringArray6.length == 0) ? null : propertyStringArray6[c];
        String[] propertyStringArray7 = genericDocument.getPropertyStringArray("displayName");
        String str7 = (propertyStringArray7 == null || propertyStringArray7.length == 0) ? null : propertyStringArray7[c];
        String propertyString = genericDocument.getPropertyString("iconUri");
        Uri uriDeserialize = propertyString != null ? new MobileApplication.IconUriAsUri().deserialize(propertyString) : null;
        byte[][] propertyBytesArray = genericDocument.getPropertyBytesArray("sha256Certificate");
        byte[] bArr = (propertyBytesArray == null || propertyBytesArray.length == 0) ? null : propertyBytesArray[c];
        long propertyLong = genericDocument.getPropertyLong("updatedTimestamp");
        String[] propertyStringArray8 = genericDocument.getPropertyStringArray("className");
        return new MobileApplication(namespace, str5, i2, creationTimestampMillis, ttlMillis, str, listAsList, str2, str3, str4, arrayList, str6, str7, uriDeserialize, bArr, propertyLong, (propertyStringArray8 == null || propertyStringArray8.length == 0) ? null : propertyStringArray8[c]);
    }
}
