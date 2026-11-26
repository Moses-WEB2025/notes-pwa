package androidx.appsearch.platformstorage.converter;

import android.app.appsearch.GenericDocument;
import android.os.Build;
import androidx.appsearch.app.AppSearchBlobHandle;
import androidx.appsearch.app.EmbeddingVector;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.core.util.Preconditions;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class GenericDocumentToPlatformConverter {
    public static GenericDocument toPlatformGenericDocument(androidx.appsearch.app.GenericDocument genericDocument) {
        Preconditions.checkNotNull(genericDocument);
        GenericDocument.Builder builder = new GenericDocument.Builder(genericDocument.getNamespace(), genericDocument.getId(), genericDocument.getSchemaType());
        builder.setScore(genericDocument.getScore()).setTtlMillis(genericDocument.getTtlMillis()).setCreationTimestampMillis(genericDocument.getCreationTimestampMillis());
        for (String str : genericDocument.getPropertyNames()) {
            Object property = genericDocument.getProperty(str);
            if (property instanceof String[]) {
                builder.setPropertyString(str, (String[]) property);
            } else if (property instanceof long[]) {
                builder.setPropertyLong(str, (long[]) property);
            } else if (property instanceof double[]) {
                builder.setPropertyDouble(str, (double[]) property);
            } else if (property instanceof boolean[]) {
                builder.setPropertyBoolean(str, (boolean[]) property);
            } else if (property instanceof byte[][]) {
                byte[][] bArr = (byte[][]) property;
                if ((Build.VERSION.SDK_INT != 31 && Build.VERSION.SDK_INT != 32) || bArr.length != 0) {
                    builder.setPropertyBytes(str, bArr);
                }
            } else if (property instanceof androidx.appsearch.app.GenericDocument[]) {
                androidx.appsearch.app.GenericDocument[] genericDocumentArr = (androidx.appsearch.app.GenericDocument[]) property;
                if ((Build.VERSION.SDK_INT != 31 && Build.VERSION.SDK_INT != 32) || genericDocumentArr.length != 0) {
                    GenericDocument[] genericDocumentArr2 = new GenericDocument[genericDocumentArr.length];
                    for (int i = 0; i < genericDocumentArr.length; i++) {
                        genericDocumentArr2[i] = toPlatformGenericDocument(genericDocumentArr[i]);
                    }
                    builder.setPropertyDocument(str, genericDocumentArr2);
                }
            } else if (property instanceof EmbeddingVector[]) {
                if (!AppSearchVersionUtil.isAtLeastB()) {
                    throw new UnsupportedOperationException("SCHEMA_EMBEDDING_PROPERTY_CONFIG is not available on this AppSearch implementation.");
                }
                ApiHelperForB.setPlatformPropertyEmbedding(builder, str, (EmbeddingVector[]) property);
            } else {
                if (property instanceof AppSearchBlobHandle[]) {
                    throw new UnsupportedOperationException("BLOB_STORAGE is not available on this AppSearch implementation.");
                }
                throw new IllegalStateException(String.format("Property \"%s\" has unsupported value type %s", str, property.getClass().toString()));
            }
        }
        return builder.build();
    }

    public static androidx.appsearch.app.GenericDocument toJetpackGenericDocument(GenericDocument genericDocument) {
        Preconditions.checkNotNull(genericDocument);
        androidx.appsearch.app.GenericDocument.Builder builder = new androidx.appsearch.app.GenericDocument.Builder(genericDocument.getNamespace(), genericDocument.getId(), genericDocument.getSchemaType());
        builder.setScore(genericDocument.getScore()).setTtlMillis(genericDocument.getTtlMillis()).setCreationTimestampMillis(genericDocument.getCreationTimestampMillis());
        for (String str : genericDocument.getPropertyNames()) {
            Object property = genericDocument.getProperty(str);
            if (str.equals(androidx.appsearch.app.GenericDocument.PARENT_TYPES_SYNTHETIC_PROPERTY)) {
                if (!(property instanceof String[])) {
                    throw new IllegalStateException(String.format("Parents list must be of String[] type, but got %s", property.getClass().toString()));
                }
                builder.setParentTypes(Arrays.asList((String[]) property));
            } else if (property instanceof String[]) {
                builder.setPropertyString(str, (String[]) property);
            } else if (property instanceof long[]) {
                builder.setPropertyLong(str, (long[]) property);
            } else if (property instanceof double[]) {
                builder.setPropertyDouble(str, (double[]) property);
            } else if (property instanceof boolean[]) {
                builder.setPropertyBoolean(str, (boolean[]) property);
            } else if (property instanceof byte[][]) {
                builder.setPropertyBytes(str, (byte[][]) property);
            } else if (property instanceof GenericDocument[]) {
                GenericDocument[] genericDocumentArr = (GenericDocument[]) property;
                androidx.appsearch.app.GenericDocument[] genericDocumentArr2 = new androidx.appsearch.app.GenericDocument[genericDocumentArr.length];
                for (int i = 0; i < genericDocumentArr.length; i++) {
                    genericDocumentArr2[i] = toJetpackGenericDocument(genericDocumentArr[i]);
                }
                builder.setPropertyDocument(str, genericDocumentArr2);
            } else if (AppSearchVersionUtil.isAtLeastB() && (property instanceof android.app.appsearch.EmbeddingVector[])) {
                ApiHelperForB.setJetpackPropertyEmbedding(builder, str, (android.app.appsearch.EmbeddingVector[]) property);
            } else {
                throw new IllegalStateException(String.format("Property \"%s\" has unsupported value type %s", str, property.getClass().toString()));
            }
        }
        return builder.build();
    }

    private GenericDocumentToPlatformConverter() {
    }

    private static class ApiHelperForB {
        private ApiHelperForB() {
        }

        static void setPlatformPropertyEmbedding(GenericDocument.Builder<GenericDocument.Builder<?>> builder, String str, EmbeddingVector[] embeddingVectorArr) {
            android.app.appsearch.EmbeddingVector[] embeddingVectorArr2 = new android.app.appsearch.EmbeddingVector[embeddingVectorArr.length];
            for (int i = 0; i < embeddingVectorArr.length; i++) {
                embeddingVectorArr2[i] = new android.app.appsearch.EmbeddingVector(embeddingVectorArr[i].getValues(), embeddingVectorArr[i].getModelSignature());
            }
            builder.setPropertyEmbedding(str, embeddingVectorArr2);
        }

        static void setJetpackPropertyEmbedding(androidx.appsearch.app.GenericDocument.Builder<androidx.appsearch.app.GenericDocument.Builder<?>> builder, String str, android.app.appsearch.EmbeddingVector[] embeddingVectorArr) {
            EmbeddingVector[] embeddingVectorArr2 = new EmbeddingVector[embeddingVectorArr.length];
            for (int i = 0; i < embeddingVectorArr.length; i++) {
                embeddingVectorArr2[i] = new EmbeddingVector(embeddingVectorArr[i].getValues(), embeddingVectorArr[i].getModelSignature());
            }
            builder.setPropertyEmbedding(str, embeddingVectorArr2);
        }
    }
}
