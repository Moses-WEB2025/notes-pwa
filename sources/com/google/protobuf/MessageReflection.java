package com.google.protobuf;

import ch.qos.logback.core.CoreConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes2.dex */
class MessageReflection {

    interface MergeTarget {

        public enum ContainerType {
            MESSAGE,
            EXTENSION_SET
        }

        MergeTarget addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj);

        MergeTarget clearField(Descriptors.FieldDescriptor fieldDescriptor);

        MergeTarget clearOneof(Descriptors.OneofDescriptor oneofDescriptor);

        ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry extensionRegistry, String str);

        ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry extensionRegistry, Descriptors.Descriptor descriptor, int i);

        Object finish();

        ContainerType getContainerType();

        Descriptors.Descriptor getDescriptorForType();

        Object getField(Descriptors.FieldDescriptor fieldDescriptor);

        Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor);

        WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor fieldDescriptor);

        boolean hasField(Descriptors.FieldDescriptor fieldDescriptor);

        boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor);

        MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor fieldDescriptor, Message message);

        MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor fieldDescriptor, Message message);

        Object parseGroup(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException;

        Object parseMessage(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException;

        Object parseMessageFromBytes(ByteString byteString, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException;

        MergeTarget setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj);

        MergeTarget setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj);
    }

    MessageReflection() {
    }

    static void writeMessageTo(Message message, Map<Descriptors.FieldDescriptor, Object> map, CodedOutputStream codedOutputStream, boolean z) throws IOException {
        boolean messageSetWireFormat = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        if (z) {
            TreeMap treeMap = new TreeMap(map);
            for (Descriptors.FieldDescriptor fieldDescriptor : message.getDescriptorForType().getFields()) {
                if (fieldDescriptor.isRequired() && !treeMap.containsKey(fieldDescriptor)) {
                    treeMap.put(fieldDescriptor, message.getField(fieldDescriptor));
                }
            }
            map = treeMap;
        }
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : map.entrySet()) {
            Descriptors.FieldDescriptor key = entry.getKey();
            Object value = entry.getValue();
            if (messageSetWireFormat && key.isExtension() && key.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && !key.isRepeated()) {
                codedOutputStream.writeMessageSetExtension(key.getNumber(), (Message) value);
            } else {
                FieldSet.writeField(key, value, codedOutputStream);
            }
        }
        UnknownFieldSet unknownFields = message.getUnknownFields();
        if (messageSetWireFormat) {
            unknownFields.writeAsMessageSetTo(codedOutputStream);
        } else {
            unknownFields.writeTo(codedOutputStream);
        }
    }

    static int getSerializedSize(Message message, Map<Descriptors.FieldDescriptor, Object> map) {
        int serializedSize;
        int iComputeFieldSize;
        boolean messageSetWireFormat = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        int i = 0;
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : map.entrySet()) {
            Descriptors.FieldDescriptor key = entry.getKey();
            Object value = entry.getValue();
            if (messageSetWireFormat && key.isExtension() && key.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && !key.isRepeated()) {
                iComputeFieldSize = CodedOutputStream.computeMessageSetExtensionSize(key.getNumber(), (Message) value);
            } else {
                iComputeFieldSize = FieldSet.computeFieldSize(key, value);
            }
            i += iComputeFieldSize;
        }
        UnknownFieldSet unknownFields = message.getUnknownFields();
        if (messageSetWireFormat) {
            serializedSize = unknownFields.getSerializedSizeAsMessageSet();
        } else {
            serializedSize = unknownFields.getSerializedSize();
        }
        return i + serializedSize;
    }

    static String delimitWithCommas(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(str);
        }
        return sb.toString();
    }

    static boolean isInitialized(MessageOrBuilder messageOrBuilder) {
        for (Descriptors.FieldDescriptor fieldDescriptor : messageOrBuilder.getDescriptorForType().getFields()) {
            if (fieldDescriptor.isRequired() && !messageOrBuilder.hasField(fieldDescriptor)) {
                return false;
            }
        }
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : messageOrBuilder.getAllFields().entrySet()) {
            Descriptors.FieldDescriptor key = entry.getKey();
            if (key.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (key.isRepeated()) {
                    Iterator it = ((List) entry.getValue()).iterator();
                    while (it.hasNext()) {
                        if (!((Message) it.next()).isInitialized()) {
                            return false;
                        }
                    }
                } else if (!((Message) entry.getValue()).isInitialized()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String subMessagePrefix(String str, Descriptors.FieldDescriptor fieldDescriptor, int i) {
        StringBuilder sb = new StringBuilder(str);
        if (fieldDescriptor.isExtension()) {
            sb.append(CoreConstants.LEFT_PARENTHESIS_CHAR).append(fieldDescriptor.getFullName()).append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        } else {
            sb.append(fieldDescriptor.getName());
        }
        if (i != -1) {
            sb.append('[').append(i).append(']');
        }
        sb.append('.');
        return sb.toString();
    }

    private static void findMissingFields(MessageOrBuilder messageOrBuilder, String str, List<String> list) {
        for (Descriptors.FieldDescriptor fieldDescriptor : messageOrBuilder.getDescriptorForType().getFields()) {
            if (fieldDescriptor.isRequired() && !messageOrBuilder.hasField(fieldDescriptor)) {
                list.add(str + fieldDescriptor.getName());
            }
        }
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : messageOrBuilder.getAllFields().entrySet()) {
            Descriptors.FieldDescriptor key = entry.getKey();
            Object value = entry.getValue();
            if (key.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (key.isRepeated()) {
                    Iterator it = ((List) value).iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        findMissingFields((MessageOrBuilder) it.next(), subMessagePrefix(str, key, i), list);
                        i++;
                    }
                } else if (messageOrBuilder.hasField(key)) {
                    findMissingFields((MessageOrBuilder) value, subMessagePrefix(str, key, -1), list);
                }
            }
        }
    }

    static List<String> findMissingFields(MessageOrBuilder messageOrBuilder) {
        ArrayList arrayList = new ArrayList();
        findMissingFields(messageOrBuilder, "", arrayList);
        return arrayList;
    }

    static class BuilderAdapter implements MergeTarget {
        private final Message.Builder builder;

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Descriptors.Descriptor getDescriptorForType() {
            return this.builder.getDescriptorForType();
        }

        public BuilderAdapter(Message.Builder builder) {
            this.builder = builder;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            return this.builder.getField(fieldDescriptor);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            return this.builder.hasField(fieldDescriptor);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            this.builder.setField(fieldDescriptor, obj);
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget clearField(Descriptors.FieldDescriptor fieldDescriptor) {
            this.builder.clearField(fieldDescriptor);
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj) {
            this.builder.setRepeatedField(fieldDescriptor, i, obj);
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            this.builder.addRepeatedField(fieldDescriptor, obj);
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            return this.builder.hasOneof(oneofDescriptor);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            this.builder.clearOneof(oneofDescriptor);
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor) {
            return this.builder.getOneofFieldDescriptor(oneofDescriptor);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget.ContainerType getContainerType() {
            return MergeTarget.ContainerType.MESSAGE;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry extensionRegistry, String str) {
            return extensionRegistry.findImmutableExtensionByName(str);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry extensionRegistry, Descriptors.Descriptor descriptor, int i) {
            return extensionRegistry.findImmutableExtensionByNumber(descriptor, i);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object parseGroup(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException {
            Message.Builder builderNewBuilderForField;
            Message message2;
            if (message != null) {
                builderNewBuilderForField = message.newBuilderForType();
            } else {
                builderNewBuilderForField = this.builder.newBuilderForField(fieldDescriptor);
            }
            if (!fieldDescriptor.isRepeated() && (message2 = (Message) getField(fieldDescriptor)) != null) {
                builderNewBuilderForField.mergeFrom(message2);
            }
            codedInputStream.readGroup(fieldDescriptor.getNumber(), builderNewBuilderForField, extensionRegistryLite);
            return builderNewBuilderForField.buildPartial();
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object parseMessage(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException {
            Message.Builder builderNewBuilderForField;
            Message message2;
            if (message != null) {
                builderNewBuilderForField = message.newBuilderForType();
            } else {
                builderNewBuilderForField = this.builder.newBuilderForField(fieldDescriptor);
            }
            if (!fieldDescriptor.isRepeated() && (message2 = (Message) getField(fieldDescriptor)) != null) {
                builderNewBuilderForField.mergeFrom(message2);
            }
            codedInputStream.readMessage(builderNewBuilderForField, extensionRegistryLite);
            return builderNewBuilderForField.buildPartial();
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object parseMessageFromBytes(ByteString byteString, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException {
            Message.Builder builderNewBuilderForField;
            Message message2;
            if (message != null) {
                builderNewBuilderForField = message.newBuilderForType();
            } else {
                builderNewBuilderForField = this.builder.newBuilderForField(fieldDescriptor);
            }
            if (!fieldDescriptor.isRepeated() && (message2 = (Message) getField(fieldDescriptor)) != null) {
                builderNewBuilderForField.mergeFrom(message2);
            }
            builderNewBuilderForField.mergeFrom(byteString, extensionRegistryLite);
            return builderNewBuilderForField.buildPartial();
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor fieldDescriptor, Message message) {
            Message.Builder builderNewBuilderForField;
            Message message2;
            if (message != null) {
                builderNewBuilderForField = message.newBuilderForType();
            } else {
                builderNewBuilderForField = this.builder.newBuilderForField(fieldDescriptor);
            }
            if (!fieldDescriptor.isRepeated() && (message2 = (Message) getField(fieldDescriptor)) != null) {
                builderNewBuilderForField.mergeFrom(message2);
            }
            return new BuilderAdapter(builderNewBuilderForField);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor fieldDescriptor, Message message) {
            Message.Builder builderNewBuilderForField;
            if (message != null) {
                builderNewBuilderForField = message.newBuilderForType();
            } else {
                builderNewBuilderForField = this.builder.newBuilderForField(fieldDescriptor);
            }
            return new BuilderAdapter(builderNewBuilderForField);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.needsUtf8Check()) {
                return WireFormat.Utf8Validation.STRICT;
            }
            if (!fieldDescriptor.isRepeated() && (this.builder instanceof GeneratedMessage.Builder)) {
                return WireFormat.Utf8Validation.LAZY;
            }
            return WireFormat.Utf8Validation.LOOSE;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object finish() {
            return this.builder.buildPartial();
        }
    }

    static class ExtensionAdapter implements MergeTarget {
        private final FieldSet<Descriptors.FieldDescriptor> extensions;

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor) {
            return null;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            return false;
        }

        ExtensionAdapter(FieldSet<Descriptors.FieldDescriptor> fieldSet) {
            this.extensions = fieldSet;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Descriptors.Descriptor getDescriptorForType() {
            throw new UnsupportedOperationException("getDescriptorForType() called on FieldSet object");
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            return this.extensions.getField(fieldDescriptor);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            return this.extensions.hasField(fieldDescriptor);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            this.extensions.setField(fieldDescriptor, obj);
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget clearField(Descriptors.FieldDescriptor fieldDescriptor) {
            this.extensions.clearField(fieldDescriptor);
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj) {
            this.extensions.setRepeatedField(fieldDescriptor, i, obj);
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            this.extensions.addRepeatedField(fieldDescriptor, obj);
            return this;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget.ContainerType getContainerType() {
            return MergeTarget.ContainerType.EXTENSION_SET;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public ExtensionRegistry.ExtensionInfo findExtensionByName(ExtensionRegistry extensionRegistry, String str) {
            return extensionRegistry.findImmutableExtensionByName(str);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public ExtensionRegistry.ExtensionInfo findExtensionByNumber(ExtensionRegistry extensionRegistry, Descriptors.Descriptor descriptor, int i) {
            return extensionRegistry.findImmutableExtensionByNumber(descriptor, i);
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object parseGroup(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException {
            Message message2;
            Message.Builder builderNewBuilderForType = message.newBuilderForType();
            if (!fieldDescriptor.isRepeated() && (message2 = (Message) getField(fieldDescriptor)) != null) {
                builderNewBuilderForType.mergeFrom(message2);
            }
            codedInputStream.readGroup(fieldDescriptor.getNumber(), builderNewBuilderForType, extensionRegistryLite);
            return builderNewBuilderForType.buildPartial();
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object parseMessage(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException {
            Message message2;
            Message.Builder builderNewBuilderForType = message.newBuilderForType();
            if (!fieldDescriptor.isRepeated() && (message2 = (Message) getField(fieldDescriptor)) != null) {
                builderNewBuilderForType.mergeFrom(message2);
            }
            codedInputStream.readMessage(builderNewBuilderForType, extensionRegistryLite);
            return builderNewBuilderForType.buildPartial();
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object parseMessageFromBytes(ByteString byteString, ExtensionRegistryLite extensionRegistryLite, Descriptors.FieldDescriptor fieldDescriptor, Message message) throws IOException {
            Message message2;
            Message.Builder builderNewBuilderForType = message.newBuilderForType();
            if (!fieldDescriptor.isRepeated() && (message2 = (Message) getField(fieldDescriptor)) != null) {
                builderNewBuilderForType.mergeFrom(message2);
            }
            builderNewBuilderForType.mergeFrom(byteString, extensionRegistryLite);
            return builderNewBuilderForType.buildPartial();
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget newMergeTargetForField(Descriptors.FieldDescriptor fieldDescriptor, Message message) {
            throw new UnsupportedOperationException("newMergeTargetForField() called on FieldSet object");
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public MergeTarget newEmptyTargetForField(Descriptors.FieldDescriptor fieldDescriptor, Message message) {
            throw new UnsupportedOperationException("newEmptyTargetForField() called on FieldSet object");
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public WireFormat.Utf8Validation getUtf8Validation(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.needsUtf8Check()) {
                return WireFormat.Utf8Validation.STRICT;
            }
            return WireFormat.Utf8Validation.LOOSE;
        }

        @Override // com.google.protobuf.MessageReflection.MergeTarget
        public Object finish() {
            throw new UnsupportedOperationException("finish() called on FieldSet object");
        }
    }

    /* JADX WARN: Code duplicated, block: B:26:0x006b  */
    /* JADX WARN: Code duplicated, block: B:29:0x006f  */
    static boolean mergeFieldFrom(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, Descriptors.Descriptor descriptor, MergeTarget mergeTarget, int i) throws IOException {
        Message message;
        boolean z;
        Object group;
        ExtensionRegistry.ExtensionInfo extensionInfoFindExtensionByNumber;
        if (descriptor.getOptions().getMessageSetWireFormat() && i == WireFormat.MESSAGE_SET_ITEM_TAG) {
            mergeMessageSetExtensionFromCodedStream(codedInputStream, builder, extensionRegistryLite, descriptor, mergeTarget);
            return true;
        }
        int tagWireType = WireFormat.getTagWireType(i);
        int tagFieldNumber = WireFormat.getTagFieldNumber(i);
        Descriptors.FieldDescriptor fieldDescriptorFindFieldByNumber = null;
        if (descriptor.isExtensionNumber(tagFieldNumber)) {
            if (!(extensionRegistryLite instanceof ExtensionRegistry) || (extensionInfoFindExtensionByNumber = mergeTarget.findExtensionByNumber((ExtensionRegistry) extensionRegistryLite, descriptor, tagFieldNumber)) == null) {
                message = null;
            } else {
                Descriptors.FieldDescriptor fieldDescriptor = extensionInfoFindExtensionByNumber.descriptor;
                Message message2 = extensionInfoFindExtensionByNumber.defaultInstance;
                if (message2 == null && fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    throw new IllegalStateException("Message-typed extension lacked default instance: " + fieldDescriptor.getFullName());
                }
                message = message2;
                fieldDescriptorFindFieldByNumber = fieldDescriptor;
            }
        } else if (mergeTarget.getContainerType() == MergeTarget.ContainerType.MESSAGE) {
            fieldDescriptorFindFieldByNumber = descriptor.findFieldByNumber(tagFieldNumber);
            message = null;
        } else {
            message = null;
        }
        boolean z2 = false;
        if (fieldDescriptorFindFieldByNumber == null) {
            z = false;
            z2 = true;
        } else if (tagWireType == FieldSet.getWireFormatForFieldType(fieldDescriptorFindFieldByNumber.getLiteType(), false)) {
            z = false;
        } else if (fieldDescriptorFindFieldByNumber.isPackable() && tagWireType == FieldSet.getWireFormatForFieldType(fieldDescriptorFindFieldByNumber.getLiteType(), true)) {
            z = true;
        } else {
            z = false;
            z2 = true;
        }
        if (z2) {
            if (builder != null) {
                return builder.mergeFieldFrom(i, codedInputStream);
            }
            return codedInputStream.skipField(i);
        }
        if (z) {
            int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
            if (fieldDescriptorFindFieldByNumber.getLiteType() == WireFormat.FieldType.ENUM) {
                while (codedInputStream.getBytesUntilLimit() > 0) {
                    int i2 = codedInputStream.readEnum();
                    if (fieldDescriptorFindFieldByNumber.getFile().supportsUnknownEnumValue()) {
                        mergeTarget.addRepeatedField(fieldDescriptorFindFieldByNumber, fieldDescriptorFindFieldByNumber.getEnumType().findValueByNumberCreatingIfUnknown(i2));
                    } else {
                        Descriptors.EnumValueDescriptor enumValueDescriptorFindValueByNumber = fieldDescriptorFindFieldByNumber.getEnumType().findValueByNumber(i2);
                        if (enumValueDescriptorFindValueByNumber == null) {
                            return true;
                        }
                        mergeTarget.addRepeatedField(fieldDescriptorFindFieldByNumber, enumValueDescriptorFindValueByNumber);
                    }
                }
            } else {
                while (codedInputStream.getBytesUntilLimit() > 0) {
                    mergeTarget.addRepeatedField(fieldDescriptorFindFieldByNumber, WireFormat.readPrimitiveField(codedInputStream, fieldDescriptorFindFieldByNumber.getLiteType(), mergeTarget.getUtf8Validation(fieldDescriptorFindFieldByNumber)));
                }
            }
            codedInputStream.popLimit(iPushLimit);
        } else {
            int i3 = AnonymousClass1.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[fieldDescriptorFindFieldByNumber.getType().ordinal()];
            if (i3 == 1) {
                group = mergeTarget.parseGroup(codedInputStream, extensionRegistryLite, fieldDescriptorFindFieldByNumber, message);
            } else if (i3 == 2) {
                group = mergeTarget.parseMessage(codedInputStream, extensionRegistryLite, fieldDescriptorFindFieldByNumber, message);
            } else if (i3 == 3) {
                int i4 = codedInputStream.readEnum();
                if (fieldDescriptorFindFieldByNumber.getFile().supportsUnknownEnumValue()) {
                    group = fieldDescriptorFindFieldByNumber.getEnumType().findValueByNumberCreatingIfUnknown(i4);
                } else {
                    Descriptors.EnumValueDescriptor enumValueDescriptorFindValueByNumber2 = fieldDescriptorFindFieldByNumber.getEnumType().findValueByNumber(i4);
                    if (enumValueDescriptorFindValueByNumber2 == null) {
                        if (builder != null) {
                            builder.mergeVarintField(tagFieldNumber, i4);
                        }
                        return true;
                    }
                    group = enumValueDescriptorFindValueByNumber2;
                }
            } else {
                group = WireFormat.readPrimitiveField(codedInputStream, fieldDescriptorFindFieldByNumber.getLiteType(), mergeTarget.getUtf8Validation(fieldDescriptorFindFieldByNumber));
            }
            if (fieldDescriptorFindFieldByNumber.isRepeated()) {
                mergeTarget.addRepeatedField(fieldDescriptorFindFieldByNumber, group);
            } else {
                mergeTarget.setField(fieldDescriptorFindFieldByNumber, group);
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: com.google.protobuf.MessageReflection$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type;

        static {
            int[] iArr = new int[Descriptors.FieldDescriptor.Type.values().length];
            $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type = iArr;
            try {
                iArr[Descriptors.FieldDescriptor.Type.GROUP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.MESSAGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$Type[Descriptors.FieldDescriptor.Type.ENUM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static void mergeMessageSetExtensionFromCodedStream(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, Descriptors.Descriptor descriptor, MergeTarget mergeTarget) throws IOException {
        int uInt32 = 0;
        ByteString bytes = null;
        ExtensionRegistry.ExtensionInfo extensionInfoFindExtensionByNumber = null;
        while (true) {
            int tag = codedInputStream.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                uInt32 = codedInputStream.readUInt32();
                if (uInt32 != 0 && (extensionRegistryLite instanceof ExtensionRegistry)) {
                    extensionInfoFindExtensionByNumber = mergeTarget.findExtensionByNumber((ExtensionRegistry) extensionRegistryLite, descriptor, uInt32);
                }
            } else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                if (uInt32 != 0 && extensionInfoFindExtensionByNumber != null && ExtensionRegistryLite.isEagerlyParseMessageSets()) {
                    eagerlyMergeMessageSetExtension(codedInputStream, extensionInfoFindExtensionByNumber, extensionRegistryLite, mergeTarget);
                    bytes = null;
                } else {
                    bytes = codedInputStream.readBytes();
                }
            } else if (!codedInputStream.skipField(tag)) {
                break;
            }
        }
        codedInputStream.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
        if (bytes == null || uInt32 == 0) {
            return;
        }
        if (extensionInfoFindExtensionByNumber != null) {
            mergeMessageSetExtensionFromBytes(bytes, extensionInfoFindExtensionByNumber, extensionRegistryLite, mergeTarget);
        } else {
            if (bytes == null || builder == null) {
                return;
            }
            builder.mergeField(uInt32, UnknownFieldSet.Field.newBuilder().addLengthDelimited(bytes).build());
        }
    }

    private static void mergeMessageSetExtensionFromBytes(ByteString byteString, ExtensionRegistry.ExtensionInfo extensionInfo, ExtensionRegistryLite extensionRegistryLite, MergeTarget mergeTarget) throws IOException {
        Descriptors.FieldDescriptor fieldDescriptor = extensionInfo.descriptor;
        if (mergeTarget.hasField(fieldDescriptor) || ExtensionRegistryLite.isEagerlyParseMessageSets()) {
            mergeTarget.setField(fieldDescriptor, mergeTarget.parseMessageFromBytes(byteString, extensionRegistryLite, fieldDescriptor, extensionInfo.defaultInstance));
        } else {
            mergeTarget.setField(fieldDescriptor, new LazyField(extensionInfo.defaultInstance, extensionRegistryLite, byteString));
        }
    }

    private static void eagerlyMergeMessageSetExtension(CodedInputStream codedInputStream, ExtensionRegistry.ExtensionInfo extensionInfo, ExtensionRegistryLite extensionRegistryLite, MergeTarget mergeTarget) throws IOException {
        Descriptors.FieldDescriptor fieldDescriptor = extensionInfo.descriptor;
        mergeTarget.setField(fieldDescriptor, mergeTarget.parseMessage(codedInputStream, extensionRegistryLite, fieldDescriptor, extensionInfo.defaultInstance));
    }
}
