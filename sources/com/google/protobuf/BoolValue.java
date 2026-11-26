package com.google.protobuf;

import defpackage.R2;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes2.dex */
public final class BoolValue extends GeneratedMessageV3 implements BoolValueOrBuilder {
    private static final BoolValue DEFAULT_INSTANCE = new BoolValue();
    private static final Parser<BoolValue> PARSER = new AbstractParser<BoolValue>() { // from class: com.google.protobuf.BoolValue.1
        @Override // com.google.protobuf.Parser
        public BoolValue parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new BoolValue(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int VALUE_FIELD_NUMBER = 1;
    private static final long serialVersionUID = 0;
    private byte memoizedIsInitialized;
    private boolean value_;

    private BoolValue(GeneratedMessageV3.Builder<?> builder) {
        super(builder);
        this.memoizedIsInitialized = (byte) -1;
    }

    private BoolValue() {
        this.memoizedIsInitialized = (byte) -1;
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
    public final UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    private BoolValue(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        this();
        extensionRegistryLite.getClass();
        UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
        boolean z = false;
        while (!z) {
            try {
                try {
                    int tag = codedInputStream.readTag();
                    if (tag != 0) {
                        if (tag == 8) {
                            this.value_ = codedInputStream.readBool();
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
        return WrappersProto.internal_static_google_protobuf_BoolValue_descriptor;
    }

    @Override // com.google.protobuf.GeneratedMessageV3
    protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
        return WrappersProto.internal_static_google_protobuf_BoolValue_fieldAccessorTable.ensureFieldAccessorsInitialized(BoolValue.class, Builder.class);
    }

    @Override // com.google.protobuf.BoolValueOrBuilder
    public boolean getValue() {
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
        boolean z = this.value_;
        if (z) {
            codedOutputStream.writeBool(1, z);
        }
        this.unknownFields.writeTo(codedOutputStream);
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSize;
        if (i != -1) {
            return i;
        }
        boolean z = this.value_;
        int iComputeBoolSize = (z ? CodedOutputStream.computeBoolSize(1, z) : 0) + this.unknownFields.getSerializedSize();
        this.memoizedSize = iComputeBoolSize;
        return iComputeBoolSize;
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BoolValue)) {
            return super.equals(obj);
        }
        BoolValue boolValue = (BoolValue) obj;
        return getValue() == boolValue.getValue() && this.unknownFields.equals(boolValue.unknownFields);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        int iHashCode = ((((((R2.attr.colorSurface + getDescriptor().hashCode()) * 37) + 1) * 53) + Internal.hashBoolean(getValue())) * 29) + this.unknownFields.hashCode();
        this.memoizedHashCode = iHashCode;
        return iHashCode;
    }

    public static BoolValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteBuffer);
    }

    public static BoolValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
    }

    public static BoolValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static BoolValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static BoolValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static BoolValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static BoolValue parseFrom(InputStream inputStream) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
    }

    public static BoolValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
    }

    public static BoolValue parseDelimitedFrom(InputStream inputStream) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
    }

    public static BoolValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
    }

    public static BoolValue parseFrom(CodedInputStream codedInputStream) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
    }

    public static BoolValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return (BoolValue) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return DEFAULT_INSTANCE.toBuilder();
    }

    public static Builder newBuilder(BoolValue boolValue) {
        return DEFAULT_INSTANCE.toBuilder().mergeFrom(boolValue);
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

    public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements BoolValueOrBuilder {
        private boolean value_;

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return true;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return WrappersProto.internal_static_google_protobuf_BoolValue_descriptor;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return WrappersProto.internal_static_google_protobuf_BoolValue_fieldAccessorTable.ensureFieldAccessorsInitialized(BoolValue.class, Builder.class);
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
            this.value_ = false;
            return this;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
        public Descriptors.Descriptor getDescriptorForType() {
            return WrappersProto.internal_static_google_protobuf_BoolValue_descriptor;
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public BoolValue getDefaultInstanceForType() {
            return BoolValue.getDefaultInstance();
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public BoolValue build() {
            BoolValue boolValueBuildPartial = buildPartial();
            if (boolValueBuildPartial.isInitialized()) {
                return boolValueBuildPartial;
            }
            throw newUninitializedMessageException((Message) boolValueBuildPartial);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public BoolValue buildPartial() {
            BoolValue boolValue = new BoolValue(this);
            boolValue.value_ = this.value_;
            onBuilt();
            return boolValue;
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
            if (message instanceof BoolValue) {
                return mergeFrom((BoolValue) message);
            }
            super.mergeFrom(message);
            return this;
        }

        public Builder mergeFrom(BoolValue boolValue) {
            if (boolValue == BoolValue.getDefaultInstance()) {
                return this;
            }
            if (boolValue.getValue()) {
                setValue(boolValue.getValue());
            }
            mergeUnknownFields(boolValue.unknownFields);
            onChanged();
            return this;
        }

        /* JADX WARN: Code duplicated, block: B:16:0x0023  */
        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            BoolValue boolValue = null;
            try {
                try {
                    BoolValue boolValue2 = (BoolValue) BoolValue.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (boolValue2 != null) {
                        mergeFrom(boolValue2);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    BoolValue boolValue3 = (BoolValue) e.getUnfinishedMessage();
                    try {
                        throw e.unwrapIOException();
                    } catch (Throwable th) {
                        th = th;
                        boolValue = boolValue3;
                        if (boolValue != null) {
                            mergeFrom(boolValue);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (boolValue != null) {
                    mergeFrom(boolValue);
                }
                throw th;
            }
        }

        @Override // com.google.protobuf.BoolValueOrBuilder
        public boolean getValue() {
            return this.value_;
        }

        public Builder setValue(boolean z) {
            this.value_ = z;
            onChanged();
            return this;
        }

        public Builder clearValue() {
            this.value_ = false;
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

    public static BoolValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static BoolValue of(boolean z) {
        return newBuilder().setValue(z).build();
    }

    public static Parser<BoolValue> parser() {
        return PARSER;
    }

    @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Parser<BoolValue> getParserForType() {
        return PARSER;
    }

    @Override // com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
    public BoolValue getDefaultInstanceForType() {
        return DEFAULT_INSTANCE;
    }
}
