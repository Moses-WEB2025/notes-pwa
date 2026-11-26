package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes2.dex */
public final class UnknownFieldSet implements MessageLite {
    private final Map<Integer, Field> fields;
    private static final UnknownFieldSet defaultInstance = new UnknownFieldSet(Collections.emptyMap(), Collections.emptyMap());
    private static final Parser PARSER = new Parser();

    @Override // com.google.protobuf.MessageLiteOrBuilder
    public boolean isInitialized() {
        return true;
    }

    private UnknownFieldSet() {
        this.fields = null;
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    public static Builder newBuilder(UnknownFieldSet unknownFieldSet) {
        return newBuilder().mergeFrom(unknownFieldSet);
    }

    public static UnknownFieldSet getDefaultInstance() {
        return defaultInstance;
    }

    @Override // com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
    public UnknownFieldSet getDefaultInstanceForType() {
        return defaultInstance;
    }

    UnknownFieldSet(Map<Integer, Field> map, Map<Integer, Field> map2) {
        this.fields = map;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof UnknownFieldSet) && this.fields.equals(((UnknownFieldSet) obj).fields);
    }

    public int hashCode() {
        return this.fields.hashCode();
    }

    public Map<Integer, Field> asMap() {
        return this.fields;
    }

    public boolean hasField(int i) {
        return this.fields.containsKey(Integer.valueOf(i));
    }

    public Field getField(int i) {
        Field field = this.fields.get(Integer.valueOf(i));
        return field == null ? Field.getDefaultInstance() : field;
    }

    @Override // com.google.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
            entry.getValue().writeTo(entry.getKey().intValue(), codedOutputStream);
        }
    }

    public String toString() {
        return TextFormat.printToString(this);
    }

    @Override // com.google.protobuf.MessageLite
    public ByteString toByteString() {
        try {
            ByteString.CodedBuilder codedBuilderNewCodedBuilder = ByteString.newCodedBuilder(getSerializedSize());
            writeTo(codedBuilderNewCodedBuilder.getCodedOutput());
            return codedBuilderNewCodedBuilder.build();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a ByteString threw an IOException (should never happen).", e);
        }
    }

    @Override // com.google.protobuf.MessageLite
    public byte[] toByteArray() {
        try {
            byte[] bArr = new byte[getSerializedSize()];
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(bArr);
            writeTo(codedOutputStreamNewInstance);
            codedOutputStreamNewInstance.checkNoSpaceLeft();
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    @Override // com.google.protobuf.MessageLite
    public void writeTo(OutputStream outputStream) throws IOException {
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputStream);
        writeTo(codedOutputStreamNewInstance);
        codedOutputStreamNewInstance.flush();
    }

    @Override // com.google.protobuf.MessageLite
    public void writeDelimitedTo(OutputStream outputStream) throws IOException {
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputStream);
        codedOutputStreamNewInstance.writeRawVarint32(getSerializedSize());
        writeTo(codedOutputStreamNewInstance);
        codedOutputStreamNewInstance.flush();
    }

    @Override // com.google.protobuf.MessageLite
    public int getSerializedSize() {
        int serializedSize = 0;
        for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
            serializedSize += entry.getValue().getSerializedSize(entry.getKey().intValue());
        }
        return serializedSize;
    }

    public void writeAsMessageSetTo(CodedOutputStream codedOutputStream) throws IOException {
        for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
            entry.getValue().writeAsMessageSetExtensionTo(entry.getKey().intValue(), codedOutputStream);
        }
    }

    public int getSerializedSizeAsMessageSet() {
        int serializedSizeAsMessageSetExtension = 0;
        for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
            serializedSizeAsMessageSetExtension += entry.getValue().getSerializedSizeAsMessageSetExtension(entry.getKey().intValue());
        }
        return serializedSizeAsMessageSetExtension;
    }

    public static UnknownFieldSet parseFrom(CodedInputStream codedInputStream) throws IOException {
        return newBuilder().mergeFrom(codedInputStream).build();
    }

    public static UnknownFieldSet parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return newBuilder().mergeFrom(byteString).build();
    }

    public static UnknownFieldSet parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return newBuilder().mergeFrom(bArr).build();
    }

    public static UnknownFieldSet parseFrom(InputStream inputStream) throws IOException {
        return newBuilder().mergeFrom(inputStream).build();
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Builder newBuilderForType() {
        return newBuilder();
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Builder toBuilder() {
        return newBuilder().mergeFrom(this);
    }

    public static final class Builder implements MessageLite.Builder {
        private Map<Integer, Field> fields;
        private Field.Builder lastField;
        private int lastFieldNumber;

        @Override // com.google.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            return true;
        }

        private Builder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Builder create() {
            Builder builder = new Builder();
            builder.reinitialize();
            return builder;
        }

        private Field.Builder getFieldBuilder(int i) {
            Field.Builder builder = this.lastField;
            if (builder != null) {
                int i2 = this.lastFieldNumber;
                if (i == i2) {
                    return builder;
                }
                addField(i2, builder.build());
            }
            if (i == 0) {
                return null;
            }
            Field field = this.fields.get(Integer.valueOf(i));
            this.lastFieldNumber = i;
            Field.Builder builderNewBuilder = Field.newBuilder();
            this.lastField = builderNewBuilder;
            if (field != null) {
                builderNewBuilder.mergeFrom(field);
            }
            return this.lastField;
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public UnknownFieldSet build() {
            UnknownFieldSet unknownFieldSet;
            getFieldBuilder(0);
            if (this.fields.isEmpty()) {
                unknownFieldSet = UnknownFieldSet.getDefaultInstance();
            } else {
                unknownFieldSet = new UnknownFieldSet(Collections.unmodifiableMap(this.fields), null);
            }
            this.fields = null;
            return unknownFieldSet;
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public UnknownFieldSet buildPartial() {
            return build();
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Builder m482clone() {
            getFieldBuilder(0);
            return UnknownFieldSet.newBuilder().mergeFrom(new UnknownFieldSet(this.fields, null));
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public UnknownFieldSet getDefaultInstanceForType() {
            return UnknownFieldSet.getDefaultInstance();
        }

        private void reinitialize() {
            this.fields = Collections.emptyMap();
            this.lastFieldNumber = 0;
            this.lastField = null;
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder clear() {
            reinitialize();
            return this;
        }

        public Builder clearField(int i) {
            if (i == 0) {
                throw new IllegalArgumentException("Zero is not a valid field number.");
            }
            if (this.lastField != null && this.lastFieldNumber == i) {
                this.lastField = null;
                this.lastFieldNumber = 0;
            }
            if (this.fields.containsKey(Integer.valueOf(i))) {
                this.fields.remove(Integer.valueOf(i));
            }
            return this;
        }

        public Builder mergeFrom(UnknownFieldSet unknownFieldSet) {
            if (unknownFieldSet != UnknownFieldSet.getDefaultInstance()) {
                for (Map.Entry entry : unknownFieldSet.fields.entrySet()) {
                    mergeField(((Integer) entry.getKey()).intValue(), (Field) entry.getValue());
                }
            }
            return this;
        }

        public Builder mergeField(int i, Field field) {
            if (i == 0) {
                throw new IllegalArgumentException("Zero is not a valid field number.");
            }
            if (hasField(i)) {
                getFieldBuilder(i).mergeFrom(field);
                return this;
            }
            addField(i, field);
            return this;
        }

        public Builder mergeVarintField(int i, int i2) {
            if (i == 0) {
                throw new IllegalArgumentException("Zero is not a valid field number.");
            }
            getFieldBuilder(i).addVarint(i2);
            return this;
        }

        public Builder mergeLengthDelimitedField(int i, ByteString byteString) {
            if (i == 0) {
                throw new IllegalArgumentException("Zero is not a valid field number.");
            }
            getFieldBuilder(i).addLengthDelimited(byteString);
            return this;
        }

        public boolean hasField(int i) {
            if (i != 0) {
                return i == this.lastFieldNumber || this.fields.containsKey(Integer.valueOf(i));
            }
            throw new IllegalArgumentException("Zero is not a valid field number.");
        }

        public Builder addField(int i, Field field) {
            if (i == 0) {
                throw new IllegalArgumentException("Zero is not a valid field number.");
            }
            if (this.lastField != null && this.lastFieldNumber == i) {
                this.lastField = null;
                this.lastFieldNumber = 0;
            }
            if (this.fields.isEmpty()) {
                this.fields = new TreeMap();
            }
            this.fields.put(Integer.valueOf(i), field);
            return this;
        }

        public Map<Integer, Field> asMap() {
            getFieldBuilder(0);
            return Collections.unmodifiableMap(this.fields);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream) throws IOException {
            int tag;
            do {
                tag = codedInputStream.readTag();
                if (tag == 0) {
                    break;
                }
            } while (mergeFieldFrom(tag, codedInputStream));
            return this;
        }

        public boolean mergeFieldFrom(int i, CodedInputStream codedInputStream) throws IOException {
            int tagFieldNumber = WireFormat.getTagFieldNumber(i);
            int tagWireType = WireFormat.getTagWireType(i);
            if (tagWireType == 0) {
                getFieldBuilder(tagFieldNumber).addVarint(codedInputStream.readInt64());
                return true;
            }
            if (tagWireType == 1) {
                getFieldBuilder(tagFieldNumber).addFixed64(codedInputStream.readFixed64());
                return true;
            }
            if (tagWireType == 2) {
                getFieldBuilder(tagFieldNumber).addLengthDelimited(codedInputStream.readBytes());
                return true;
            }
            if (tagWireType == 3) {
                Builder builderNewBuilder = UnknownFieldSet.newBuilder();
                codedInputStream.readGroup(tagFieldNumber, builderNewBuilder, ExtensionRegistry.getEmptyRegistry());
                getFieldBuilder(tagFieldNumber).addGroup(builderNewBuilder.build());
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType == 5) {
                getFieldBuilder(tagFieldNumber).addFixed32(codedInputStream.readFixed32());
                return true;
            }
            throw InvalidProtocolBufferException.invalidWireType();
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(ByteString byteString) throws InvalidProtocolBufferException {
            try {
                CodedInputStream codedInputStreamNewCodedInput = byteString.newCodedInput();
                mergeFrom(codedInputStreamNewCodedInput);
                codedInputStreamNewCodedInput.checkLastTagWas(0);
                return this;
            } catch (InvalidProtocolBufferException e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException("Reading from a ByteString threw an IOException (should never happen).", e2);
            }
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(byte[] bArr) throws InvalidProtocolBufferException {
            try {
                CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(bArr);
                mergeFrom(codedInputStreamNewInstance);
                codedInputStreamNewInstance.checkLastTagWas(0);
                return this;
            } catch (InvalidProtocolBufferException e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
            }
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(InputStream inputStream) throws IOException {
            CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(inputStream);
            mergeFrom(codedInputStreamNewInstance);
            codedInputStreamNewInstance.checkLastTagWas(0);
            return this;
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public boolean mergeDelimitedFrom(InputStream inputStream) throws IOException {
            int i = inputStream.read();
            if (i == -1) {
                return false;
            }
            mergeFrom((InputStream) new AbstractMessageLite.Builder.LimitedInputStream(inputStream, CodedInputStream.readRawVarint32(i, inputStream)));
            return true;
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public boolean mergeDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return mergeDelimitedFrom(inputStream);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return mergeFrom(codedInputStream);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return mergeFrom(byteString);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(byte[] bArr, int i, int i2) throws InvalidProtocolBufferException {
            try {
                CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(bArr, i, i2);
                mergeFrom(codedInputStreamNewInstance);
                codedInputStreamNewInstance.checkLastTagWas(0);
                return this;
            } catch (InvalidProtocolBufferException e) {
                throw e;
            } catch (IOException e2) {
                throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e2);
            }
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return mergeFrom(bArr);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(byte[] bArr, int i, int i2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return mergeFrom(bArr, i, i2);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return mergeFrom(inputStream);
        }

        @Override // com.google.protobuf.MessageLite.Builder
        public Builder mergeFrom(MessageLite messageLite) {
            if (messageLite instanceof UnknownFieldSet) {
                return mergeFrom((UnknownFieldSet) messageLite);
            }
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
    }

    public static final class Field {
        private static final Field fieldDefaultInstance = newBuilder().build();
        private List<Integer> fixed32;
        private List<Long> fixed64;
        private List<UnknownFieldSet> group;
        private List<ByteString> lengthDelimited;
        private List<Long> varint;

        private Field() {
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public static Builder newBuilder(Field field) {
            return newBuilder().mergeFrom(field);
        }

        public static Field getDefaultInstance() {
            return fieldDefaultInstance;
        }

        public List<Long> getVarintList() {
            return this.varint;
        }

        public List<Integer> getFixed32List() {
            return this.fixed32;
        }

        public List<Long> getFixed64List() {
            return this.fixed64;
        }

        public List<ByteString> getLengthDelimitedList() {
            return this.lengthDelimited;
        }

        public List<UnknownFieldSet> getGroupList() {
            return this.group;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Field) {
                return Arrays.equals(getIdentityArray(), ((Field) obj).getIdentityArray());
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(getIdentityArray());
        }

        private Object[] getIdentityArray() {
            return new Object[]{this.varint, this.fixed32, this.fixed64, this.lengthDelimited, this.group};
        }

        public ByteString toByteString(int i) {
            try {
                ByteString.CodedBuilder codedBuilderNewCodedBuilder = ByteString.newCodedBuilder(getSerializedSize(i));
                writeTo(i, codedBuilderNewCodedBuilder.getCodedOutput());
                return codedBuilderNewCodedBuilder.build();
            } catch (IOException e) {
                throw new RuntimeException("Serializing to a ByteString should never fail with an IOException", e);
            }
        }

        public void writeTo(int i, CodedOutputStream codedOutputStream) throws IOException {
            Iterator<Long> it = this.varint.iterator();
            while (it.hasNext()) {
                codedOutputStream.writeUInt64(i, it.next().longValue());
            }
            Iterator<Integer> it2 = this.fixed32.iterator();
            while (it2.hasNext()) {
                codedOutputStream.writeFixed32(i, it2.next().intValue());
            }
            Iterator<Long> it3 = this.fixed64.iterator();
            while (it3.hasNext()) {
                codedOutputStream.writeFixed64(i, it3.next().longValue());
            }
            Iterator<ByteString> it4 = this.lengthDelimited.iterator();
            while (it4.hasNext()) {
                codedOutputStream.writeBytes(i, it4.next());
            }
            Iterator<UnknownFieldSet> it5 = this.group.iterator();
            while (it5.hasNext()) {
                codedOutputStream.writeGroup(i, it5.next());
            }
        }

        public int getSerializedSize(int i) {
            Iterator<Long> it = this.varint.iterator();
            int iComputeGroupSize = 0;
            while (it.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeUInt64Size(i, it.next().longValue());
            }
            Iterator<Integer> it2 = this.fixed32.iterator();
            while (it2.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeFixed32Size(i, it2.next().intValue());
            }
            Iterator<Long> it3 = this.fixed64.iterator();
            while (it3.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeFixed64Size(i, it3.next().longValue());
            }
            Iterator<ByteString> it4 = this.lengthDelimited.iterator();
            while (it4.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeBytesSize(i, it4.next());
            }
            Iterator<UnknownFieldSet> it5 = this.group.iterator();
            while (it5.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeGroupSize(i, it5.next());
            }
            return iComputeGroupSize;
        }

        public void writeAsMessageSetExtensionTo(int i, CodedOutputStream codedOutputStream) throws IOException {
            Iterator<ByteString> it = this.lengthDelimited.iterator();
            while (it.hasNext()) {
                codedOutputStream.writeRawMessageSetExtension(i, it.next());
            }
        }

        public int getSerializedSizeAsMessageSetExtension(int i) {
            Iterator<ByteString> it = this.lengthDelimited.iterator();
            int iComputeRawMessageSetExtensionSize = 0;
            while (it.hasNext()) {
                iComputeRawMessageSetExtensionSize += CodedOutputStream.computeRawMessageSetExtensionSize(i, it.next());
            }
            return iComputeRawMessageSetExtensionSize;
        }

        public static final class Builder {
            private Field result;

            private Builder() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                Builder builder = new Builder();
                builder.result = new Field();
                return builder;
            }

            public Field build() {
                if (this.result.varint == null) {
                    this.result.varint = Collections.emptyList();
                } else {
                    Field field = this.result;
                    field.varint = Collections.unmodifiableList(field.varint);
                }
                if (this.result.fixed32 == null) {
                    this.result.fixed32 = Collections.emptyList();
                } else {
                    Field field2 = this.result;
                    field2.fixed32 = Collections.unmodifiableList(field2.fixed32);
                }
                if (this.result.fixed64 == null) {
                    this.result.fixed64 = Collections.emptyList();
                } else {
                    Field field3 = this.result;
                    field3.fixed64 = Collections.unmodifiableList(field3.fixed64);
                }
                if (this.result.lengthDelimited == null) {
                    this.result.lengthDelimited = Collections.emptyList();
                } else {
                    Field field4 = this.result;
                    field4.lengthDelimited = Collections.unmodifiableList(field4.lengthDelimited);
                }
                if (this.result.group == null) {
                    this.result.group = Collections.emptyList();
                } else {
                    Field field5 = this.result;
                    field5.group = Collections.unmodifiableList(field5.group);
                }
                Field field6 = this.result;
                this.result = null;
                return field6;
            }

            public Builder clear() {
                this.result = new Field();
                return this;
            }

            public Builder mergeFrom(Field field) {
                if (!field.varint.isEmpty()) {
                    if (this.result.varint == null) {
                        this.result.varint = new ArrayList();
                    }
                    this.result.varint.addAll(field.varint);
                }
                if (!field.fixed32.isEmpty()) {
                    if (this.result.fixed32 == null) {
                        this.result.fixed32 = new ArrayList();
                    }
                    this.result.fixed32.addAll(field.fixed32);
                }
                if (!field.fixed64.isEmpty()) {
                    if (this.result.fixed64 == null) {
                        this.result.fixed64 = new ArrayList();
                    }
                    this.result.fixed64.addAll(field.fixed64);
                }
                if (!field.lengthDelimited.isEmpty()) {
                    if (this.result.lengthDelimited == null) {
                        this.result.lengthDelimited = new ArrayList();
                    }
                    this.result.lengthDelimited.addAll(field.lengthDelimited);
                }
                if (!field.group.isEmpty()) {
                    if (this.result.group == null) {
                        this.result.group = new ArrayList();
                    }
                    this.result.group.addAll(field.group);
                }
                return this;
            }

            public Builder addVarint(long j) {
                if (this.result.varint == null) {
                    this.result.varint = new ArrayList();
                }
                this.result.varint.add(Long.valueOf(j));
                return this;
            }

            public Builder addFixed32(int i) {
                if (this.result.fixed32 == null) {
                    this.result.fixed32 = new ArrayList();
                }
                this.result.fixed32.add(Integer.valueOf(i));
                return this;
            }

            public Builder addFixed64(long j) {
                if (this.result.fixed64 == null) {
                    this.result.fixed64 = new ArrayList();
                }
                this.result.fixed64.add(Long.valueOf(j));
                return this;
            }

            public Builder addLengthDelimited(ByteString byteString) {
                if (this.result.lengthDelimited == null) {
                    this.result.lengthDelimited = new ArrayList();
                }
                this.result.lengthDelimited.add(byteString);
                return this;
            }

            public Builder addGroup(UnknownFieldSet unknownFieldSet) {
                if (this.result.group == null) {
                    this.result.group = new ArrayList();
                }
                this.result.group.add(unknownFieldSet);
                return this;
            }
        }
    }

    public static final class Parser extends AbstractParser<UnknownFieldSet> {
        @Override // com.google.protobuf.Parser
        public UnknownFieldSet parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            try {
                builderNewBuilder.mergeFrom(codedInputStream);
                return builderNewBuilder.buildPartial();
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(builderNewBuilder.buildPartial());
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(builderNewBuilder.buildPartial());
            }
        }
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public final Parser getParserForType() {
        return PARSER;
    }
}
