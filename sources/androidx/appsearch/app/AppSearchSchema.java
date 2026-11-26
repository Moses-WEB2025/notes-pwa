package androidx.appsearch.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.appsearch.exceptions.IllegalSchemaException;
import androidx.appsearch.safeparcel.AbstractSafeParcelable;
import androidx.appsearch.safeparcel.PropertyConfigParcel;
import androidx.appsearch.safeparcel.stub.StubCreators;
import androidx.appsearch.util.IndentingStringBuilder;
import androidx.collection.ArraySet;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

/* JADX INFO: loaded from: classes.dex */
public final class AppSearchSchema extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AppSearchSchema> CREATOR = new StubCreators.AppSearchSchemaCreator();
    private final String mDescription;
    private final List<String> mParentTypes;
    final List<PropertyConfigParcel> mPropertyConfigParcels;
    private final String mSchemaType;

    AppSearchSchema(String str, List<PropertyConfigParcel> list, List<String> list2, String str2) {
        this.mSchemaType = (String) Preconditions.checkNotNull(str);
        this.mPropertyConfigParcels = (List) Preconditions.checkNotNull(list);
        this.mParentTypes = (List) Preconditions.checkNotNull(list2);
        this.mDescription = (String) Preconditions.checkNotNull(str2);
    }

    public String toString() {
        IndentingStringBuilder indentingStringBuilder = new IndentingStringBuilder();
        appendAppSearchSchemaString(indentingStringBuilder);
        return indentingStringBuilder.toString();
    }

    private void appendAppSearchSchemaString(IndentingStringBuilder indentingStringBuilder) {
        Preconditions.checkNotNull(indentingStringBuilder);
        indentingStringBuilder.append("{\n");
        indentingStringBuilder.increaseIndentLevel();
        indentingStringBuilder.append("schemaType: \"").append(getSchemaType()).append("\",\n");
        indentingStringBuilder.append("description: \"").append(getDescription()).append("\",\n");
        indentingStringBuilder.append("properties: [\n");
        PropertyConfig[] propertyConfigArr = (PropertyConfig[]) getProperties().toArray(new PropertyConfig[0]);
        Arrays.sort(propertyConfigArr, new Comparator() { // from class: androidx.appsearch.app.AppSearchSchema$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((AppSearchSchema.PropertyConfig) obj).getName().compareTo(((AppSearchSchema.PropertyConfig) obj2).getName());
            }
        });
        for (int i = 0; i < propertyConfigArr.length; i++) {
            PropertyConfig propertyConfig = propertyConfigArr[i];
            indentingStringBuilder.increaseIndentLevel();
            propertyConfig.appendPropertyConfigString(indentingStringBuilder);
            if (i != propertyConfigArr.length - 1) {
                indentingStringBuilder.append(",\n");
            }
            indentingStringBuilder.decreaseIndentLevel();
        }
        indentingStringBuilder.append(StringUtils.LF);
        indentingStringBuilder.append("]\n");
        indentingStringBuilder.decreaseIndentLevel();
        indentingStringBuilder.append(StringSubstitutor.DEFAULT_VAR_END);
    }

    public String getSchemaType() {
        return this.mSchemaType;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public List<PropertyConfig> getProperties() {
        if (this.mPropertyConfigParcels.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(this.mPropertyConfigParcels.size());
        for (int i = 0; i < this.mPropertyConfigParcels.size(); i++) {
            arrayList.add(PropertyConfig.fromParcel(this.mPropertyConfigParcels.get(i)));
        }
        return arrayList;
    }

    public List<String> getParentTypes() {
        return Collections.unmodifiableList(this.mParentTypes);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppSearchSchema)) {
            return false;
        }
        AppSearchSchema appSearchSchema = (AppSearchSchema) obj;
        if (getSchemaType().equals(appSearchSchema.getSchemaType()) && getDescription().equals(appSearchSchema.getDescription()) && getParentTypes().equals(appSearchSchema.getParentTypes())) {
            return getProperties().equals(appSearchSchema.getProperties());
        }
        return false;
    }

    public int hashCode() {
        return ObjectsCompat.hash(getSchemaType(), getProperties(), getParentTypes(), getDescription());
    }

    @Override // androidx.appsearch.safeparcel.AbstractSafeParcelable
    public void writeToParcel(Parcel parcel, int i) {
        StubCreators.AppSearchSchemaCreator.writeToParcel(this, parcel, i);
    }

    public static final class Builder {
        private boolean mBuilt;
        private String mDescription;
        private LinkedHashSet<String> mParentTypes;
        private ArrayList<PropertyConfigParcel> mPropertyConfigParcels;
        private final Set<String> mPropertyNames;
        private String mSchemaType;

        public Builder(String str) {
            this.mDescription = "";
            this.mPropertyConfigParcels = new ArrayList<>();
            this.mParentTypes = new LinkedHashSet<>();
            this.mPropertyNames = new ArraySet();
            this.mBuilt = false;
            this.mSchemaType = (String) Preconditions.checkNotNull(str);
        }

        public Builder(AppSearchSchema appSearchSchema) {
            this.mDescription = "";
            this.mPropertyConfigParcels = new ArrayList<>();
            this.mParentTypes = new LinkedHashSet<>();
            this.mPropertyNames = new ArraySet();
            this.mBuilt = false;
            this.mSchemaType = appSearchSchema.getSchemaType();
            this.mDescription = appSearchSchema.getDescription();
            this.mPropertyConfigParcels.addAll(appSearchSchema.mPropertyConfigParcels);
            this.mParentTypes.addAll(appSearchSchema.mParentTypes);
            for (int i = 0; i < this.mPropertyConfigParcels.size(); i++) {
                this.mPropertyNames.add(this.mPropertyConfigParcels.get(i).getName());
            }
        }

        public Builder setSchemaType(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            this.mSchemaType = str;
            return this;
        }

        public Builder setDescription(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            this.mDescription = str;
            return this;
        }

        public Builder addProperty(PropertyConfig propertyConfig) {
            Preconditions.checkNotNull(propertyConfig);
            resetIfBuilt();
            String name = propertyConfig.getName();
            if (!this.mPropertyNames.add(name)) {
                throw new IllegalSchemaException("Property defined more than once: " + name);
            }
            this.mPropertyConfigParcels.add(propertyConfig.mPropertyConfigParcel);
            return this;
        }

        public Builder clearProperties() {
            resetIfBuilt();
            this.mPropertyConfigParcels.clear();
            this.mPropertyNames.clear();
            return this;
        }

        public Builder addParentType(String str) {
            Preconditions.checkNotNull(str);
            resetIfBuilt();
            this.mParentTypes.add(str);
            return this;
        }

        public Builder clearParentTypes() {
            resetIfBuilt();
            this.mParentTypes.clear();
            return this;
        }

        public AppSearchSchema build() {
            this.mBuilt = true;
            return new AppSearchSchema(this.mSchemaType, this.mPropertyConfigParcels, new ArrayList(this.mParentTypes), this.mDescription);
        }

        private void resetIfBuilt() {
            if (this.mBuilt) {
                this.mPropertyConfigParcels = new ArrayList<>(this.mPropertyConfigParcels);
                this.mParentTypes = new LinkedHashSet<>(this.mParentTypes);
                this.mBuilt = false;
            }
        }
    }

    public static abstract class PropertyConfig {
        public static final int CARDINALITY_OPTIONAL = 2;
        public static final int CARDINALITY_REPEATED = 1;
        public static final int CARDINALITY_REQUIRED = 3;
        public static final int DATA_TYPE_BLOB_HANDLE = 8;
        public static final int DATA_TYPE_BOOLEAN = 4;
        public static final int DATA_TYPE_BYTES = 5;
        public static final int DATA_TYPE_DOCUMENT = 6;
        public static final int DATA_TYPE_DOUBLE = 3;
        public static final int DATA_TYPE_EMBEDDING = 7;
        public static final int DATA_TYPE_LONG = 2;
        public static final int DATA_TYPE_STRING = 1;
        final PropertyConfigParcel mPropertyConfigParcel;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Cardinality {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface DataType {
        }

        PropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            this.mPropertyConfigParcel = (PropertyConfigParcel) Preconditions.checkNotNull(propertyConfigParcel);
        }

        public String toString() {
            IndentingStringBuilder indentingStringBuilder = new IndentingStringBuilder();
            appendPropertyConfigString(indentingStringBuilder);
            return indentingStringBuilder.toString();
        }

        void appendPropertyConfigString(IndentingStringBuilder indentingStringBuilder) {
            Preconditions.checkNotNull(indentingStringBuilder);
            indentingStringBuilder.append("{\n");
            indentingStringBuilder.increaseIndentLevel();
            indentingStringBuilder.append("name: \"").append(getName()).append("\",\n");
            indentingStringBuilder.append("description: \"").append(getDescription()).append("\",\n");
            if (this instanceof StringPropertyConfig) {
                ((StringPropertyConfig) this).appendStringPropertyConfigFields(indentingStringBuilder);
            } else if (this instanceof DocumentPropertyConfig) {
                ((DocumentPropertyConfig) this).appendDocumentPropertyConfigFields(indentingStringBuilder);
            } else if (this instanceof LongPropertyConfig) {
                ((LongPropertyConfig) this).appendLongPropertyConfigFields(indentingStringBuilder);
            }
            int cardinality = getCardinality();
            if (cardinality == 1) {
                indentingStringBuilder.append("cardinality: CARDINALITY_REPEATED,\n");
            } else if (cardinality == 2) {
                indentingStringBuilder.append("cardinality: CARDINALITY_OPTIONAL,\n");
            } else if (cardinality == 3) {
                indentingStringBuilder.append("cardinality: CARDINALITY_REQUIRED,\n");
            } else {
                indentingStringBuilder.append("cardinality: CARDINALITY_UNKNOWN,\n");
            }
            switch (getDataType()) {
                case 1:
                    indentingStringBuilder.append("dataType: DATA_TYPE_STRING,\n");
                    break;
                case 2:
                    indentingStringBuilder.append("dataType: DATA_TYPE_LONG,\n");
                    break;
                case 3:
                    indentingStringBuilder.append("dataType: DATA_TYPE_DOUBLE,\n");
                    break;
                case 4:
                    indentingStringBuilder.append("dataType: DATA_TYPE_BOOLEAN,\n");
                    break;
                case 5:
                    indentingStringBuilder.append("dataType: DATA_TYPE_BYTES,\n");
                    break;
                case 6:
                    indentingStringBuilder.append("dataType: DATA_TYPE_DOCUMENT,\n");
                    break;
                case 7:
                    indentingStringBuilder.append("dataType: DATA_TYPE_EMBEDDING,\n");
                    break;
                case 8:
                    indentingStringBuilder.append("dataType: DATA_TYPE_BLOB_HANDLE,\n");
                    break;
                default:
                    indentingStringBuilder.append("dataType: DATA_TYPE_UNKNOWN,\n");
                    break;
            }
            indentingStringBuilder.decreaseIndentLevel();
            indentingStringBuilder.append(StringSubstitutor.DEFAULT_VAR_END);
        }

        public String getName() {
            return this.mPropertyConfigParcel.getName();
        }

        public String getDescription() {
            return this.mPropertyConfigParcel.getDescription();
        }

        public int getDataType() {
            return this.mPropertyConfigParcel.getDataType();
        }

        public int getCardinality() {
            return this.mPropertyConfigParcel.getCardinality();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof PropertyConfig) {
                return ObjectsCompat.equals(this.mPropertyConfigParcel, ((PropertyConfig) obj).mPropertyConfigParcel);
            }
            return false;
        }

        public int hashCode() {
            return this.mPropertyConfigParcel.hashCode();
        }

        public static PropertyConfig fromParcel(PropertyConfigParcel propertyConfigParcel) {
            Preconditions.checkNotNull(propertyConfigParcel);
            switch (propertyConfigParcel.getDataType()) {
                case 1:
                    return new StringPropertyConfig(propertyConfigParcel);
                case 2:
                    return new LongPropertyConfig(propertyConfigParcel);
                case 3:
                    return new DoublePropertyConfig(propertyConfigParcel);
                case 4:
                    return new BooleanPropertyConfig(propertyConfigParcel);
                case 5:
                    return new BytesPropertyConfig(propertyConfigParcel);
                case 6:
                    return new DocumentPropertyConfig(propertyConfigParcel);
                case 7:
                    return new EmbeddingPropertyConfig(propertyConfigParcel);
                case 8:
                    return new BlobHandlePropertyConfig(propertyConfigParcel);
                default:
                    throw new IllegalArgumentException("Unsupported property bundle of type " + propertyConfigParcel.getDataType() + "; contents: " + propertyConfigParcel);
            }
        }
    }

    public static final class StringPropertyConfig extends PropertyConfig {
        public static final int DELETE_PROPAGATION_TYPE_NONE = 0;
        public static final int DELETE_PROPAGATION_TYPE_PROPAGATE_FROM = 1;
        public static final int INDEXING_TYPE_EXACT_TERMS = 1;
        public static final int INDEXING_TYPE_NONE = 0;
        public static final int INDEXING_TYPE_PREFIXES = 2;
        public static final int JOINABLE_VALUE_TYPE_NONE = 0;
        public static final int JOINABLE_VALUE_TYPE_QUALIFIED_ID = 1;
        public static final int TOKENIZER_TYPE_NONE = 0;
        public static final int TOKENIZER_TYPE_PLAIN = 1;
        public static final int TOKENIZER_TYPE_RFC822 = 3;
        public static final int TOKENIZER_TYPE_VERBATIM = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface DeletePropagationType {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface IndexingType {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface JoinableValueType {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface TokenizerType {
        }

        StringPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public int getIndexingType() {
            PropertyConfigParcel.StringIndexingConfigParcel stringIndexingConfigParcel = this.mPropertyConfigParcel.getStringIndexingConfigParcel();
            if (stringIndexingConfigParcel == null) {
                return 0;
            }
            return stringIndexingConfigParcel.getIndexingType();
        }

        public int getTokenizerType() {
            PropertyConfigParcel.StringIndexingConfigParcel stringIndexingConfigParcel = this.mPropertyConfigParcel.getStringIndexingConfigParcel();
            if (stringIndexingConfigParcel == null) {
                return 0;
            }
            return stringIndexingConfigParcel.getTokenizerType();
        }

        public int getJoinableValueType() {
            PropertyConfigParcel.JoinableConfigParcel joinableConfigParcel = this.mPropertyConfigParcel.getJoinableConfigParcel();
            if (joinableConfigParcel == null) {
                return 0;
            }
            return joinableConfigParcel.getJoinableValueType();
        }

        public int getDeletePropagationType() {
            PropertyConfigParcel.JoinableConfigParcel joinableConfigParcel = this.mPropertyConfigParcel.getJoinableConfigParcel();
            if (joinableConfigParcel == null) {
                return 0;
            }
            return joinableConfigParcel.getDeletePropagationType();
        }

        public static final class Builder {
            private final String mPropertyName;
            private String mDescription = "";
            private int mCardinality = 2;
            private int mIndexingType = 0;
            private int mTokenizerType = 0;
            private int mJoinableValueType = 0;
            private int mDeletePropagationType = 0;

            public Builder(String str) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
            }

            public Builder setDescription(String str) {
                this.mDescription = (String) Preconditions.checkNotNull(str);
                return this;
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }

            public Builder setIndexingType(int i) {
                Preconditions.checkArgumentInRange(i, 0, 2, "indexingType");
                this.mIndexingType = i;
                return this;
            }

            public Builder setTokenizerType(int i) {
                Preconditions.checkArgumentInRange(i, 0, 3, "tokenizerType");
                this.mTokenizerType = i;
                return this;
            }

            public Builder setJoinableValueType(int i) {
                Preconditions.checkArgumentInRange(i, 0, 1, "joinableValueType");
                this.mJoinableValueType = i;
                return this;
            }

            public Builder setDeletePropagationType(int i) {
                Preconditions.checkArgumentInRange(i, 0, 1, "deletePropagationType");
                this.mDeletePropagationType = i;
                return this;
            }

            public StringPropertyConfig build() {
                if (this.mTokenizerType == 0) {
                    Preconditions.checkState(this.mIndexingType == 0, "Cannot set TOKENIZER_TYPE_NONE with an indexing type other than INDEXING_TYPE_NONE.");
                } else {
                    Preconditions.checkState(this.mIndexingType != 0, "Cannot set TOKENIZER_TYPE_PLAIN with INDEXING_TYPE_NONE.");
                }
                if (this.mJoinableValueType == 1) {
                    Preconditions.checkState(this.mCardinality != 1, "Cannot set JOINABLE_VALUE_TYPE_QUALIFIED_ID with CARDINALITY_REPEATED.");
                }
                if (this.mDeletePropagationType != 0) {
                    Preconditions.checkState(this.mJoinableValueType == 1, "Cannot set delete propagation without setting JOINABLE_VALUE_TYPE_QUALIFIED_ID.");
                }
                return new StringPropertyConfig(PropertyConfigParcel.createForString(this.mPropertyName, this.mDescription, this.mCardinality, new PropertyConfigParcel.StringIndexingConfigParcel(this.mIndexingType, this.mTokenizerType), new PropertyConfigParcel.JoinableConfigParcel(this.mJoinableValueType, this.mDeletePropagationType)));
            }
        }

        void appendStringPropertyConfigFields(IndentingStringBuilder indentingStringBuilder) {
            int indexingType = getIndexingType();
            if (indexingType == 0) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_NONE,\n");
            } else if (indexingType == 1) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_EXACT_TERMS,\n");
            } else if (indexingType == 2) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_PREFIXES,\n");
            } else {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_UNKNOWN,\n");
            }
            int tokenizerType = getTokenizerType();
            if (tokenizerType == 0) {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_NONE,\n");
            } else if (tokenizerType == 1) {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_PLAIN,\n");
            } else if (tokenizerType == 2) {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_VERBATIM,\n");
            } else if (tokenizerType == 3) {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_RFC822,\n");
            } else {
                indentingStringBuilder.append("tokenizerType: TOKENIZER_TYPE_UNKNOWN,\n");
            }
            int joinableValueType = getJoinableValueType();
            if (joinableValueType == 0) {
                indentingStringBuilder.append("joinableValueType: JOINABLE_VALUE_TYPE_NONE,\n");
            } else if (joinableValueType == 1) {
                indentingStringBuilder.append("joinableValueType: JOINABLE_VALUE_TYPE_QUALIFIED_ID,\n");
            } else {
                indentingStringBuilder.append("joinableValueType: JOINABLE_VALUE_TYPE_UNKNOWN,\n");
            }
            int deletePropagationType = getDeletePropagationType();
            if (deletePropagationType == 0) {
                indentingStringBuilder.append("deletePropagationType: DELETE_PROPAGATION_TYPE_NONE,\n");
            } else if (deletePropagationType == 1) {
                indentingStringBuilder.append("deletePropagationType: DELETE_PROPAGATION_TYPE_PROPAGATE_FROM,\n");
            } else {
                indentingStringBuilder.append("deletePropagationType: DELETE_PROPAGATION_TYPE_UNKNOWN,\n");
            }
        }
    }

    public static final class LongPropertyConfig extends PropertyConfig {
        public static final int INDEXING_TYPE_NONE = 0;
        public static final int INDEXING_TYPE_RANGE = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface IndexingType {
        }

        LongPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public int getIndexingType() {
            PropertyConfigParcel.IntegerIndexingConfigParcel integerIndexingConfigParcel = this.mPropertyConfigParcel.getIntegerIndexingConfigParcel();
            if (integerIndexingConfigParcel == null) {
                return 0;
            }
            return integerIndexingConfigParcel.getIndexingType();
        }

        public boolean isScoringEnabled() {
            return this.mPropertyConfigParcel.isScoringEnabled();
        }

        public static final class Builder {
            private final String mPropertyName;
            private String mDescription = "";
            private int mCardinality = 2;
            private int mIndexingType = 0;
            private boolean mScoringEnabled = false;

            public Builder(String str) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
            }

            public Builder setDescription(String str) {
                this.mDescription = (String) Preconditions.checkNotNull(str);
                return this;
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }

            public Builder setIndexingType(int i) {
                Preconditions.checkArgumentInRange(i, 0, 1, "indexingType");
                this.mIndexingType = i;
                return this;
            }

            public Builder setScoringEnabled(boolean z) {
                this.mScoringEnabled = z;
                return this;
            }

            public LongPropertyConfig build() {
                return new LongPropertyConfig(PropertyConfigParcel.createForLong(this.mPropertyName, this.mDescription, this.mCardinality, this.mIndexingType, this.mScoringEnabled));
            }
        }

        void appendLongPropertyConfigFields(IndentingStringBuilder indentingStringBuilder) {
            int indexingType = getIndexingType();
            if (indexingType == 0) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_NONE,\n");
            } else if (indexingType == 1) {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_RANGE,\n");
            } else {
                indentingStringBuilder.append("indexingType: INDEXING_TYPE_UNKNOWN,\n");
            }
        }
    }

    public static final class DoublePropertyConfig extends PropertyConfig {
        DoublePropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public boolean isScoringEnabled() {
            return this.mPropertyConfigParcel.isScoringEnabled();
        }

        public static final class Builder {
            private final String mPropertyName;
            private String mDescription = "";
            private int mCardinality = 2;
            private boolean mScoringEnabled = false;

            public Builder(String str) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
            }

            public Builder setDescription(String str) {
                this.mDescription = (String) Preconditions.checkNotNull(str);
                return this;
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }

            public Builder setScoringEnabled(boolean z) {
                this.mScoringEnabled = z;
                return this;
            }

            public DoublePropertyConfig build() {
                return new DoublePropertyConfig(PropertyConfigParcel.createForDouble(this.mPropertyName, this.mDescription, this.mCardinality, this.mScoringEnabled));
            }
        }
    }

    public static final class BooleanPropertyConfig extends PropertyConfig {
        BooleanPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public boolean isScoringEnabled() {
            return this.mPropertyConfigParcel.isScoringEnabled();
        }

        public static final class Builder {
            private final String mPropertyName;
            private String mDescription = "";
            private int mCardinality = 2;
            private boolean mScoringEnabled = false;

            public Builder(String str) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
            }

            public Builder setDescription(String str) {
                this.mDescription = (String) Preconditions.checkNotNull(str);
                return this;
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }

            public Builder setScoringEnabled(boolean z) {
                this.mScoringEnabled = z;
                return this;
            }

            public BooleanPropertyConfig build() {
                return new BooleanPropertyConfig(PropertyConfigParcel.createForBoolean(this.mPropertyName, this.mDescription, this.mCardinality, this.mScoringEnabled));
            }
        }
    }

    public static final class BytesPropertyConfig extends PropertyConfig {
        BytesPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public static final class Builder {
            private final String mPropertyName;
            private String mDescription = "";
            private int mCardinality = 2;

            public Builder(String str) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
            }

            public Builder setDescription(String str) {
                this.mDescription = (String) Preconditions.checkNotNull(str);
                return this;
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }

            public BytesPropertyConfig build() {
                return new BytesPropertyConfig(PropertyConfigParcel.createForBytes(this.mPropertyName, this.mDescription, this.mCardinality));
            }
        }
    }

    public static final class DocumentPropertyConfig extends PropertyConfig {
        DocumentPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public String getSchemaType() {
            return (String) Preconditions.checkNotNull(this.mPropertyConfigParcel.getSchemaType());
        }

        public boolean shouldIndexNestedProperties() {
            PropertyConfigParcel.DocumentIndexingConfigParcel documentIndexingConfigParcel = this.mPropertyConfigParcel.getDocumentIndexingConfigParcel();
            if (documentIndexingConfigParcel == null) {
                return false;
            }
            return documentIndexingConfigParcel.shouldIndexNestedProperties();
        }

        public List<String> getIndexableNestedProperties() {
            PropertyConfigParcel.DocumentIndexingConfigParcel documentIndexingConfigParcel = this.mPropertyConfigParcel.getDocumentIndexingConfigParcel();
            if (documentIndexingConfigParcel == null) {
                return Collections.emptyList();
            }
            List<String> indexableNestedPropertiesList = documentIndexingConfigParcel.getIndexableNestedPropertiesList();
            if (indexableNestedPropertiesList == null) {
                return Collections.emptyList();
            }
            return Collections.unmodifiableList(indexableNestedPropertiesList);
        }

        public static final class Builder {
            private final String mPropertyName;
            private final String mSchemaType;
            private String mDescription = "";
            private int mCardinality = 2;
            private boolean mShouldIndexNestedProperties = false;
            private final Set<String> mIndexableNestedPropertiesList = new ArraySet();

            public Builder(String str, String str2) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
                this.mSchemaType = (String) Preconditions.checkNotNull(str2);
            }

            public Builder setDescription(String str) {
                this.mDescription = (String) Preconditions.checkNotNull(str);
                return this;
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }

            public Builder setShouldIndexNestedProperties(boolean z) {
                this.mShouldIndexNestedProperties = z;
                return this;
            }

            public Builder addIndexableNestedProperties(String... strArr) {
                Preconditions.checkNotNull(strArr);
                return addIndexableNestedProperties(Arrays.asList(strArr));
            }

            public Builder addIndexableNestedPropertyPaths(PropertyPath... propertyPathArr) {
                Preconditions.checkNotNull(propertyPathArr);
                return addIndexableNestedPropertyPaths(Arrays.asList(propertyPathArr));
            }

            public Builder addIndexableNestedProperties(Collection<String> collection) {
                Preconditions.checkNotNull(collection);
                this.mIndexableNestedPropertiesList.addAll(collection);
                return this;
            }

            public Builder addIndexableNestedPropertyPaths(Collection<PropertyPath> collection) {
                Preconditions.checkNotNull(collection);
                ArrayList arrayList = new ArrayList(collection);
                for (int i = 0; i < collection.size(); i++) {
                    this.mIndexableNestedPropertiesList.add(((PropertyPath) arrayList.get(i)).toString());
                }
                return this;
            }

            public DocumentPropertyConfig build() {
                if (this.mShouldIndexNestedProperties && !this.mIndexableNestedPropertiesList.isEmpty()) {
                    throw new IllegalArgumentException("DocumentIndexingConfig#shouldIndexNestedProperties is required to be false when one or more indexableNestedProperties are provided.");
                }
                return new DocumentPropertyConfig(PropertyConfigParcel.createForDocument(this.mPropertyName, this.mDescription, this.mCardinality, this.mSchemaType, new PropertyConfigParcel.DocumentIndexingConfigParcel(this.mShouldIndexNestedProperties, new ArrayList(this.mIndexableNestedPropertiesList))));
            }
        }

        void appendDocumentPropertyConfigFields(IndentingStringBuilder indentingStringBuilder) {
            indentingStringBuilder.append("shouldIndexNestedProperties: ").append(Boolean.valueOf(shouldIndexNestedProperties())).append(",\n");
            indentingStringBuilder.append("indexableNestedProperties: ").append(getIndexableNestedProperties()).append(",\n");
            indentingStringBuilder.append("schemaType: \"").append(getSchemaType()).append("\",\n");
        }
    }

    public static final class EmbeddingPropertyConfig extends PropertyConfig {
        public static final int INDEXING_TYPE_NONE = 0;
        public static final int INDEXING_TYPE_SIMILARITY = 1;
        public static final int QUANTIZATION_TYPE_8_BIT = 1;
        public static final int QUANTIZATION_TYPE_NONE = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface IndexingType {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface QuantizationType {
        }

        EmbeddingPropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public int getIndexingType() {
            PropertyConfigParcel.EmbeddingIndexingConfigParcel embeddingIndexingConfigParcel = this.mPropertyConfigParcel.getEmbeddingIndexingConfigParcel();
            if (embeddingIndexingConfigParcel == null) {
                return 0;
            }
            return embeddingIndexingConfigParcel.getIndexingType();
        }

        public int getQuantizationType() {
            PropertyConfigParcel.EmbeddingIndexingConfigParcel embeddingIndexingConfigParcel = this.mPropertyConfigParcel.getEmbeddingIndexingConfigParcel();
            if (embeddingIndexingConfigParcel == null) {
                return 0;
            }
            return embeddingIndexingConfigParcel.getQuantizationType();
        }

        public static final class Builder {
            private final String mPropertyName;
            private String mDescription = "";
            private int mCardinality = 2;
            private int mIndexingType = 0;
            private int mQuantizationType = 0;

            public Builder(String str) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
            }

            public Builder setDescription(String str) {
                this.mDescription = (String) Preconditions.checkNotNull(str);
                return this;
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }

            public Builder setIndexingType(int i) {
                Preconditions.checkArgumentInRange(i, 0, 1, "indexingType");
                this.mIndexingType = i;
                return this;
            }

            public Builder setQuantizationType(int i) {
                Preconditions.checkArgumentInRange(i, 0, 1, "quantizationType");
                this.mQuantizationType = i;
                return this;
            }

            public EmbeddingPropertyConfig build() {
                return new EmbeddingPropertyConfig(PropertyConfigParcel.createForEmbedding(this.mPropertyName, this.mDescription, this.mCardinality, this.mIndexingType, this.mQuantizationType));
            }
        }
    }

    public static final class BlobHandlePropertyConfig extends PropertyConfig {
        BlobHandlePropertyConfig(PropertyConfigParcel propertyConfigParcel) {
            super(propertyConfigParcel);
        }

        public static final class Builder {
            private final String mPropertyName;
            private String mDescription = "";
            private int mCardinality = 2;

            public Builder(String str) {
                this.mPropertyName = (String) Preconditions.checkNotNull(str);
            }

            public Builder setDescription(String str) {
                this.mDescription = (String) Preconditions.checkNotNull(str);
                return this;
            }

            public Builder setCardinality(int i) {
                Preconditions.checkArgumentInRange(i, 1, 3, "cardinality");
                this.mCardinality = i;
                return this;
            }

            public BlobHandlePropertyConfig build() {
                return new BlobHandlePropertyConfig(PropertyConfigParcel.createForBlobHandle(this.mPropertyName, this.mDescription, this.mCardinality));
            }
        }
    }
}
