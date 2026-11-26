package androidx.appsearch.safeparcel;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.core.util.ObjectsCompat;
import java.util.List;
import java.util.Objects;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes.dex */
public final class PropertyConfigParcel extends AbstractSafeParcelable {
    public static final Parcelable.Creator<PropertyConfigParcel> CREATOR = new StubCreators.PropertyConfigParcelCreator();
    private final int mCardinality;
    private final int mDataType;
    private final String mDescription;
    private final DocumentIndexingConfigParcel mDocumentIndexingConfigParcel;
    private final EmbeddingIndexingConfigParcel mEmbeddingIndexingConfigParcel;
    private Integer mHashCode;
    private final IntegerIndexingConfigParcel mIntegerIndexingConfigParcel;
    private final JoinableConfigParcel mJoinableConfigParcel;
    private final String mName;
    private final String mSchemaType;
    private final boolean mScoringEnabled;
    private final StringIndexingConfigParcel mStringIndexingConfigParcel;

    PropertyConfigParcel(String str, int i, int i2, String str2, StringIndexingConfigParcel stringIndexingConfigParcel, DocumentIndexingConfigParcel documentIndexingConfigParcel, IntegerIndexingConfigParcel integerIndexingConfigParcel, JoinableConfigParcel joinableConfigParcel, String str3, EmbeddingIndexingConfigParcel embeddingIndexingConfigParcel, boolean z) {
        this.mName = (String) Objects.requireNonNull(str);
        this.mDataType = i;
        this.mCardinality = i2;
        this.mSchemaType = str2;
        this.mStringIndexingConfigParcel = stringIndexingConfigParcel;
        this.mDocumentIndexingConfigParcel = documentIndexingConfigParcel;
        this.mIntegerIndexingConfigParcel = integerIndexingConfigParcel;
        this.mJoinableConfigParcel = joinableConfigParcel;
        this.mDescription = (String) Objects.requireNonNull(str3);
        this.mEmbeddingIndexingConfigParcel = embeddingIndexingConfigParcel;
        this.mScoringEnabled = z;
    }

    public static PropertyConfigParcel createForString(String str, String str2, int i, StringIndexingConfigParcel stringIndexingConfigParcel, JoinableConfigParcel joinableConfigParcel) {
        return new PropertyConfigParcel((String) Objects.requireNonNull(str), 1, i, null, (StringIndexingConfigParcel) Objects.requireNonNull(stringIndexingConfigParcel), null, null, (JoinableConfigParcel) Objects.requireNonNull(joinableConfigParcel), (String) Objects.requireNonNull(str2), null, false);
    }

    public static PropertyConfigParcel createForLong(String str, String str2, int i, int i2, boolean z) {
        return new PropertyConfigParcel((String) Objects.requireNonNull(str), 2, i, null, null, null, new IntegerIndexingConfigParcel(i2), null, (String) Objects.requireNonNull(str2), null, z);
    }

    public static PropertyConfigParcel createForDouble(String str, String str2, int i, boolean z) {
        return new PropertyConfigParcel((String) Objects.requireNonNull(str), 3, i, null, null, null, null, null, (String) Objects.requireNonNull(str2), null, z);
    }

    public static PropertyConfigParcel createForBoolean(String str, String str2, int i, boolean z) {
        return new PropertyConfigParcel((String) Objects.requireNonNull(str), 4, i, null, null, null, null, null, (String) Objects.requireNonNull(str2), null, z);
    }

    public static PropertyConfigParcel createForBytes(String str, String str2, int i) {
        return new PropertyConfigParcel((String) Objects.requireNonNull(str), 5, i, null, null, null, null, null, (String) Objects.requireNonNull(str2), null, false);
    }

    public static PropertyConfigParcel createForDocument(String str, String str2, int i, String str3, DocumentIndexingConfigParcel documentIndexingConfigParcel) {
        return new PropertyConfigParcel((String) Objects.requireNonNull(str), 6, i, (String) Objects.requireNonNull(str3), null, (DocumentIndexingConfigParcel) Objects.requireNonNull(documentIndexingConfigParcel), null, null, (String) Objects.requireNonNull(str2), null, false);
    }

    public static PropertyConfigParcel createForEmbedding(String str, String str2, int i, int i2, int i3) {
        return new PropertyConfigParcel((String) Objects.requireNonNull(str), 7, i, null, null, null, null, null, (String) Objects.requireNonNull(str2), new EmbeddingIndexingConfigParcel(i2, i3), false);
    }

    public static PropertyConfigParcel createForBlobHandle(String str, String str2, int i) {
        return new PropertyConfigParcel((String) Objects.requireNonNull(str), 8, i, null, null, null, null, null, (String) Objects.requireNonNull(str2), null, false);
    }

    public String getName() {
        return this.mName;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int getDataType() {
        return this.mDataType;
    }

    public int getCardinality() {
        return this.mCardinality;
    }

    public String getSchemaType() {
        return this.mSchemaType;
    }

    public StringIndexingConfigParcel getStringIndexingConfigParcel() {
        return this.mStringIndexingConfigParcel;
    }

    public DocumentIndexingConfigParcel getDocumentIndexingConfigParcel() {
        return this.mDocumentIndexingConfigParcel;
    }

    public IntegerIndexingConfigParcel getIntegerIndexingConfigParcel() {
        return this.mIntegerIndexingConfigParcel;
    }

    public JoinableConfigParcel getJoinableConfigParcel() {
        return this.mJoinableConfigParcel;
    }

    public EmbeddingIndexingConfigParcel getEmbeddingIndexingConfigParcel() {
        return this.mEmbeddingIndexingConfigParcel;
    }

    public boolean isScoringEnabled() {
        return this.mScoringEnabled;
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.PropertyConfigParcelCreator.writeToParcel(this, parcel, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PropertyConfigParcel)) {
            return false;
        }
        PropertyConfigParcel propertyConfigParcel = (PropertyConfigParcel) obj;
        return ObjectsCompat.equals(this.mName, propertyConfigParcel.mName) && Objects.equals(this.mDescription, propertyConfigParcel.mDescription) && ObjectsCompat.equals(Integer.valueOf(this.mDataType), Integer.valueOf(propertyConfigParcel.mDataType)) && ObjectsCompat.equals(Integer.valueOf(this.mCardinality), Integer.valueOf(propertyConfigParcel.mCardinality)) && ObjectsCompat.equals(this.mSchemaType, propertyConfigParcel.mSchemaType) && ObjectsCompat.equals(this.mStringIndexingConfigParcel, propertyConfigParcel.mStringIndexingConfigParcel) && ObjectsCompat.equals(this.mDocumentIndexingConfigParcel, propertyConfigParcel.mDocumentIndexingConfigParcel) && ObjectsCompat.equals(this.mIntegerIndexingConfigParcel, propertyConfigParcel.mIntegerIndexingConfigParcel) && ObjectsCompat.equals(this.mJoinableConfigParcel, propertyConfigParcel.mJoinableConfigParcel) && ObjectsCompat.equals(this.mEmbeddingIndexingConfigParcel, propertyConfigParcel.mEmbeddingIndexingConfigParcel) && this.mScoringEnabled == propertyConfigParcel.mScoringEnabled;
    }

    public int hashCode() {
        if (this.mHashCode == null) {
            this.mHashCode = Integer.valueOf(ObjectsCompat.hash(this.mName, this.mDescription, Integer.valueOf(this.mDataType), Integer.valueOf(this.mCardinality), this.mSchemaType, this.mStringIndexingConfigParcel, this.mDocumentIndexingConfigParcel, this.mIntegerIndexingConfigParcel, this.mJoinableConfigParcel, this.mEmbeddingIndexingConfigParcel, Boolean.valueOf(this.mScoringEnabled)));
        }
        return this.mHashCode.intValue();
    }

    public String toString() {
        return "{name: " + this.mName + ", description: " + this.mDescription + ", dataType: " + this.mDataType + ", cardinality: " + this.mCardinality + ", schemaType: " + this.mSchemaType + ", stringIndexingConfigParcel: " + this.mStringIndexingConfigParcel + ", documentIndexingConfigParcel: " + this.mDocumentIndexingConfigParcel + ", integerIndexingConfigParcel: " + this.mIntegerIndexingConfigParcel + ", joinableConfigParcel: " + this.mJoinableConfigParcel + ", embeddingIndexingConfigParcel: " + this.mEmbeddingIndexingConfigParcel + ", isScoringEnabled: " + this.mScoringEnabled + StringSubstitutor.DEFAULT_VAR_END;
    }

    public static class JoinableConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator<JoinableConfigParcel> CREATOR = new StubCreators.JoinableConfigParcelCreator();
        private final int mDeletePropagationType;
        private final int mJoinableValueType;

        public JoinableConfigParcel(int i, int i2) {
            this.mJoinableValueType = i;
            this.mDeletePropagationType = i2;
        }

        public int getJoinableValueType() {
            return this.mJoinableValueType;
        }

        public int getDeletePropagationType() {
            return this.mDeletePropagationType;
        }

        @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
        public void writeToParcel(Parcel parcel, int i) {
            StubCreators.JoinableConfigParcelCreator.writeToParcel(this, parcel, i);
        }

        public int hashCode() {
            return ObjectsCompat.hash(Integer.valueOf(this.mJoinableValueType), Integer.valueOf(this.mDeletePropagationType));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof JoinableConfigParcel)) {
                return false;
            }
            JoinableConfigParcel joinableConfigParcel = (JoinableConfigParcel) obj;
            return ObjectsCompat.equals(Integer.valueOf(this.mJoinableValueType), Integer.valueOf(joinableConfigParcel.mJoinableValueType)) && this.mDeletePropagationType == joinableConfigParcel.mDeletePropagationType;
        }

        public String toString() {
            return "{joinableValueType: " + this.mJoinableValueType + ", deletePropagationType: " + this.mDeletePropagationType + StringSubstitutor.DEFAULT_VAR_END;
        }
    }

    public static class StringIndexingConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator<StringIndexingConfigParcel> CREATOR = new StubCreators.StringIndexingConfigParcelCreator();
        private final int mIndexingType;
        private final int mTokenizerType;

        public StringIndexingConfigParcel(int i, int i2) {
            this.mIndexingType = i;
            this.mTokenizerType = i2;
        }

        public int getIndexingType() {
            return this.mIndexingType;
        }

        public int getTokenizerType() {
            return this.mTokenizerType;
        }

        @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
        public void writeToParcel(Parcel parcel, int i) {
            StubCreators.StringIndexingConfigParcelCreator.writeToParcel(this, parcel, i);
        }

        public int hashCode() {
            return ObjectsCompat.hash(Integer.valueOf(this.mIndexingType), Integer.valueOf(this.mTokenizerType));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StringIndexingConfigParcel)) {
                return false;
            }
            StringIndexingConfigParcel stringIndexingConfigParcel = (StringIndexingConfigParcel) obj;
            return this.mIndexingType == stringIndexingConfigParcel.mIndexingType && ObjectsCompat.equals(Integer.valueOf(this.mTokenizerType), Integer.valueOf(stringIndexingConfigParcel.mTokenizerType));
        }

        public String toString() {
            return "{indexingType: " + this.mIndexingType + ", tokenizerType: " + this.mTokenizerType + StringSubstitutor.DEFAULT_VAR_END;
        }
    }

    public static class IntegerIndexingConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator<IntegerIndexingConfigParcel> CREATOR = new StubCreators.IntegerIndexingConfigParcelCreator();
        private final int mIndexingType;

        public IntegerIndexingConfigParcel(int i) {
            this.mIndexingType = i;
        }

        public int getIndexingType() {
            return this.mIndexingType;
        }

        @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
        public void writeToParcel(Parcel parcel, int i) {
            StubCreators.IntegerIndexingConfigParcelCreator.writeToParcel(this, parcel, i);
        }

        public int hashCode() {
            return ObjectsCompat.hashCode(Integer.valueOf(this.mIndexingType));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof IntegerIndexingConfigParcel) && this.mIndexingType == ((IntegerIndexingConfigParcel) obj).mIndexingType;
        }

        public String toString() {
            return "{indexingType: " + this.mIndexingType + StringSubstitutor.DEFAULT_VAR_END;
        }
    }

    public static class DocumentIndexingConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator<DocumentIndexingConfigParcel> CREATOR = new StubCreators.DocumentIndexingConfigParcelCreator();
        private final boolean mIndexNestedProperties;
        private final List<String> mIndexableNestedPropertiesList;

        public DocumentIndexingConfigParcel(boolean z, List<String> list) {
            this.mIndexNestedProperties = z;
            this.mIndexableNestedPropertiesList = (List) Objects.requireNonNull(list);
        }

        public boolean shouldIndexNestedProperties() {
            return this.mIndexNestedProperties;
        }

        public List<String> getIndexableNestedPropertiesList() {
            return this.mIndexableNestedPropertiesList;
        }

        @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
        public void writeToParcel(Parcel parcel, int i) {
            StubCreators.DocumentIndexingConfigParcelCreator.writeToParcel(this, parcel, i);
        }

        public int hashCode() {
            return ObjectsCompat.hash(Boolean.valueOf(this.mIndexNestedProperties), this.mIndexableNestedPropertiesList);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DocumentIndexingConfigParcel)) {
                return false;
            }
            DocumentIndexingConfigParcel documentIndexingConfigParcel = (DocumentIndexingConfigParcel) obj;
            return ObjectsCompat.equals(Boolean.valueOf(this.mIndexNestedProperties), Boolean.valueOf(documentIndexingConfigParcel.mIndexNestedProperties)) && ObjectsCompat.equals(this.mIndexableNestedPropertiesList, documentIndexingConfigParcel.mIndexableNestedPropertiesList);
        }

        public String toString() {
            return "{indexNestedProperties: " + this.mIndexNestedProperties + ", indexableNestedPropertiesList: " + this.mIndexableNestedPropertiesList + StringSubstitutor.DEFAULT_VAR_END;
        }
    }

    public static class EmbeddingIndexingConfigParcel extends AbstractSafeParcelable {
        public static final Parcelable.Creator<EmbeddingIndexingConfigParcel> CREATOR = new StubCreators.EmbeddingIndexingConfigParcelCreator();
        private final int mIndexingType;
        private final int mQuantizationType;

        public EmbeddingIndexingConfigParcel(int i, int i2) {
            this.mIndexingType = i;
            this.mQuantizationType = i2;
        }

        public int getIndexingType() {
            return this.mIndexingType;
        }

        public int getQuantizationType() {
            return this.mQuantizationType;
        }

        @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
        public void writeToParcel(Parcel parcel, int i) {
            StubCreators.EmbeddingIndexingConfigParcelCreator.writeToParcel(this, parcel, i);
        }

        public int hashCode() {
            return ObjectsCompat.hash(Integer.valueOf(this.mIndexingType), Integer.valueOf(this.mQuantizationType));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EmbeddingIndexingConfigParcel)) {
                return false;
            }
            EmbeddingIndexingConfigParcel embeddingIndexingConfigParcel = (EmbeddingIndexingConfigParcel) obj;
            return this.mIndexingType == embeddingIndexingConfigParcel.mIndexingType && this.mQuantizationType == embeddingIndexingConfigParcel.mQuantizationType;
        }

        public String toString() {
            return "{indexingType: " + this.mIndexingType + ", quantizationType: " + this.mQuantizationType + StringSubstitutor.DEFAULT_VAR_END;
        }
    }
}
