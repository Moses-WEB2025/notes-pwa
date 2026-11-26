package androidx.appsearch.builtintypes;

import androidx.appsearch.app.AppSearchSchema;
import androidx.appsearch.app.DocumentClassFactory;
import androidx.appsearch.app.DocumentClassMappingContext;
import androidx.appsearch.app.GenericDocument;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.core.app.NotificationCompat;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.todo.data.Todo;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: androidx.appsearch.builtintypes.$$__AppSearch__ContactPoint, reason: invalid class name */
/* JADX INFO: loaded from: classes.dex */
public final class C$$__AppSearch__ContactPoint implements DocumentClassFactory<ContactPoint> {
    public static final String SCHEMA_NAME = "builtin:ContactPoint";

    @Override // androidx.appsearch.app.DocumentClassFactory
    public String getSchemaName() {
        return SCHEMA_NAME;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public AppSearchSchema getSchema() throws AppSearchException {
        return new AppSearchSchema.Builder(SCHEMA_NAME).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("name").setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("alternateNames").setCardinality(1).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("description").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(AITextWidgetStat.TYPE_IMAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("url").setCardinality(2).setTokenizerType(0).setIndexingType(0).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.DocumentPropertyConfig.Builder("potentialActions", C$$__AppSearch__PotentialAction.SCHEMA_NAME).setCardinality(1).setShouldIndexNestedProperties(false).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(Todo.LABEL).setCardinality(2).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(RemoteDeviceInfo.KEY_ADDRESS).setCardinality(1).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder(NotificationCompat.CATEGORY_EMAIL).setCardinality(1).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).addProperty(new AppSearchSchema.StringPropertyConfig.Builder("telephone").setCardinality(1).setTokenizerType(1).setIndexingType(2).setJoinableValueType(0).build()).build();
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public List<Class<?>> getDependencyDocumentClasses() throws AppSearchException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(PotentialAction.class);
        return arrayList;
    }

    @Override // androidx.appsearch.app.DocumentClassFactory
    public GenericDocument toGenericDocument(ContactPoint contactPoint) throws AppSearchException {
        GenericDocument.Builder builder = new GenericDocument.Builder(contactPoint.getNamespace(), contactPoint.getId(), SCHEMA_NAME);
        builder.setScore(contactPoint.getDocumentScore());
        builder.setCreationTimestampMillis(contactPoint.getCreationTimestampMillis());
        builder.setTtlMillis(contactPoint.getDocumentTtlMillis());
        String name = contactPoint.getName();
        if (name != null) {
            builder.setPropertyString("name", name);
        }
        List<String> alternateNames = contactPoint.getAlternateNames();
        if (alternateNames != null) {
            builder.setPropertyString("alternateNames", (String[]) alternateNames.toArray(new String[0]));
        }
        String description = contactPoint.getDescription();
        if (description != null) {
            builder.setPropertyString("description", description);
        }
        String image = contactPoint.getImage();
        if (image != null) {
            builder.setPropertyString(AITextWidgetStat.TYPE_IMAGE, image);
        }
        String url = contactPoint.getUrl();
        if (url != null) {
            builder.setPropertyString("url", url);
        }
        List<PotentialAction> potentialActions = contactPoint.getPotentialActions();
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
        String label = contactPoint.getLabel();
        if (label != null) {
            builder.setPropertyString(Todo.LABEL, label);
        }
        List<String> addresses = contactPoint.getAddresses();
        if (addresses != null) {
            builder.setPropertyString(RemoteDeviceInfo.KEY_ADDRESS, (String[]) addresses.toArray(new String[0]));
        }
        List<String> emails = contactPoint.getEmails();
        if (emails != null) {
            builder.setPropertyString(NotificationCompat.CATEGORY_EMAIL, (String[]) emails.toArray(new String[0]));
        }
        List<String> telephones = contactPoint.getTelephones();
        if (telephones != null) {
            builder.setPropertyString("telephone", (String[]) telephones.toArray(new String[0]));
        }
        return builder.build();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.appsearch.app.DocumentClassFactory
    public ContactPoint fromGenericDocument(GenericDocument genericDocument, DocumentClassMappingContext documentClassMappingContext) throws AppSearchException {
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
        String[] propertyStringArray6 = genericDocument.getPropertyStringArray(Todo.LABEL);
        String str5 = (propertyStringArray6 == null || propertyStringArray6.length == 0) ? null : propertyStringArray6[0];
        String[] propertyStringArray7 = genericDocument.getPropertyStringArray(RemoteDeviceInfo.KEY_ADDRESS);
        List listAsList2 = propertyStringArray7 != null ? Arrays.asList(propertyStringArray7) : null;
        String[] propertyStringArray8 = genericDocument.getPropertyStringArray(NotificationCompat.CATEGORY_EMAIL);
        List listAsList3 = propertyStringArray8 != null ? Arrays.asList(propertyStringArray8) : null;
        String[] propertyStringArray9 = genericDocument.getPropertyStringArray("telephone");
        List listAsList4 = propertyStringArray9 != null ? Arrays.asList(propertyStringArray9) : null;
        ContactPoint.Builder builder = new ContactPoint.Builder(namespace, id, str5);
        builder.setDocumentScore(score);
        builder.setCreationTimestampMillis(creationTimestampMillis);
        builder.setDocumentTtlMillis(ttlMillis);
        builder.setName(str);
        builder.setAlternateNames(listAsList);
        builder.setDescription(str2);
        builder.setImage(str3);
        builder.setUrl(str4);
        builder.setPotentialActions(arrayList);
        builder.setAddresses(listAsList2);
        builder.setEmails(listAsList3);
        builder.setTelephones(listAsList4);
        return builder.build();
    }
}
