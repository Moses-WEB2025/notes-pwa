package com.google.protobuf;

import defpackage.R2;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes2.dex */
public final class FloatValue extends GeneratedMessageV3 implements FloatValueOrBuilder {
    private static final FloatValue DEFAULT_INSTANCE = new FloatValue();
    private static final Parser<FloatValue> PARSER = new AbstractParser<FloatValue>() { // from class: com.google.protobuf.FloatValue.1
        @Override // com.google.protobuf.Parser
        public FloatValue parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new FloatValue(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    private float value_;

    private FloatValue(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
    }

    private FloatValue() {
        this.memoizedIsInitialized = (byte) -1;
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private FloatValue(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this();
        extensionRegistryLite.getClass();
        UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
        boolean z = false;
        while (!z) {
            try {
                try {
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 13) {
                            this.value_ = codedInputStream.readFloat();
                        } else if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                        }
                    }
                    z = true;
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
                }
            } catch (Throwable th) {
                this.unknownFields = builderNewBuilder.build();
                makeExtensionsImmutable();
                throw th;
            }
        }
        this.unknownFields = builderNewBuilder.build();
        makeExtensionsImmutable();
    }

    public static final Descriptors.Descriptor getDescriptor() {
        return WrappersProto.internal_static_google_protobuf_FloatValue_descriptor;
    }

    @Override // com.google.protobuf.GeneratedMessageV3
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_FloatValue_fieldAccessorTable.ensureFieldAccessorsInitialized(FloatValue.class, Builder.class);
    }

    @Override // com.google.protobuf.FloatValueOrBuilder
    public float getValue() {
        return this.value_;
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        byte b = this.memoizedIsInitialized;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        float f = this.value_;
        if (f != 0.0f) {
            codedOutputStream.writeFloat(1, f);
        }
        this.unknownFields.writeTo(codedOutputStream);
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSize;
        if (i != -1) {
            return i;
        }
        float f = this.value_;
        int iComputeFloatSize = (f != 0.0f ? CodedOutputStream.computeFloatSize(1, f) : 0) + this.unknownFields.getSerializedSize();
        this.memoizedSize = iComputeFloatSize;
        return iComputeFloatSize;
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FloatValue)) {
            return super.equals(obj);
        }
        FloatValue floatValue = (FloatValue) obj;
        return Float.floatToIntBits(getValue()) == Float.floatToIntBits(floatValue.getValue()) && this.unknownFields.equals(floatValue.unknownFields);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int iHashCode = ((((((R2.attr.colorSurface + getDescriptor().hashCode()) * 37) + 1) * 53) + Float.floatToIntBits(getValue())) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = iHashCode;
        return iHashCode;
    }

    public static FloatValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteBuffer);
    }

    public static FloatValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
    }

    public static FloatValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static FloatValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static FloatValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static FloatValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static FloatValue parseFrom(InputStream inputStream) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
    }

    public static FloatValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
    }

    public static FloatValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
    }

    public static FloatValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
    }

    public static FloatValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
    }

    public static FloatValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (FloatValue) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(FloatValue floatValue) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(floatValue);
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Builder toBuilder() {
        return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.protobuf.GeneratedMessageV3
    public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
        return new Builder(builderParent);
    }

    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements FloatValueOrBuilder {
        private float value_;

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return true;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_FloatValue_descriptor;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_FloatValue_fieldAccessorTable.ensureFieldAccessorsInitialized(FloatValue.class, Builder.class);
        }

        private Builder() {
            maybeForceBuilderInitialization();
        }

        private Builder(GeneratedMessageV3.BuilderParent builderParent) {
            super(builderParent);
            maybeForceBuilderInitialization();
        }

        private void maybeForceBuilderInitialization() {
            boolean z = GeneratedMessageV3.alwaysUseFieldBuilders;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder clear() {
            super.clear();
            this.value_ = 0.0f;
            return this;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
        public Descriptors.Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_FloatValue_descriptor;
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public FloatValue getDefaultInstanceForType() {
            return FloatValue.getDefaultInstance();
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public FloatValue build() {
            FloatValue floatValueBuildPartial = buildPartial();
            if (floatValueBuildPartial.isInitialized()) {
                return floatValueBuildPartial;
            }
            throw newUninitializedMessageException((Message) floatValueBuildPartial);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public FloatValue buildPartial() {
            FloatValue floatValue = new FloatValue(this);
            floatValue.value_ = this.value_;
            onBuilt();
            return floatValue;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
        /* JADX INFO: renamed from: clone */
        public Builder mo480clone() {
            return (Builder) super.mo480clone();
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            return (Builder) super.setField(fieldDescriptor, obj);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
            return (Builder) super.clearField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            return (Builder) super.clearOneof(oneofDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj) {
            return (Builder) super.setRepeatedField(fieldDescriptor, i, obj);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            return (Builder) super.addRepeatedField(fieldDescriptor, obj);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(Message message) {
            if (message instanceof FloatValue) {
                return mergeFrom((FloatValue) message);
            }
            super.mergeFrom(message);
            return this;
        }

        public Builder mergeFrom(FloatValue floatValue) {
            if (floatValue == FloatValue.getDefaultInstance()) {
                return this;
            }
            if (floatValue.getValue() != 0.0f) {
                setValue(floatValue.getValue());
            }
            mergeUnknownFields(floatValue.unknownFields);
            onChanged();
            return this;
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0023  */
        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            FloatValue floatValue = null;
            try {
                try {
                    FloatValue floatValue2 = (FloatValue) FloatValue.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (floatValue2 != null) {
                        mergeFrom(floatValue2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    FloatValue floatValue3 = (FloatValue) e.getUnfinishedMessage();
                    try {
                        throw e.unwrapIOException();
                    } catch (Throwable th) {
                        th = th;
                        floatValue = floatValue3;
                        if (floatValue != null) {
                            mergeFrom(floatValue);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (floatValue != null) {
                    mergeFrom(floatValue);
                }
                throw th;
            }
        }

        @Override // com.google.protobuf.FloatValueOrBuilder
        public float getValue() {
            return this.value_;
        }

        public Builder setValue(float f) {
            this.value_ = f;
            onChanged();
            return this;
        }

        public Builder clearValue() {
            this.value_ = 0.0f;
            onChanged();
            return this;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
            return (Builder) super.setUnknownFields(unknownFieldSet);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
            return (Builder) super.mergeUnknownFields(unknownFieldSet);
        }
    }

    public static FloatValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static FloatValue of(float f) {
        return newBuilder().setValue(f).build();
    }

    public static Parser<FloatValue> parser() {
        return PARSER;
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Parser<FloatValue> getParserForType() {
        return PARSER;
    }

    @Override // com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
    public FloatValue getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
