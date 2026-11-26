package com.google.protobuf;

import ch.qos.logback.core.CoreConstants;
import com.miui.maml.elements.ListScreenElement;
import com.xiaomi.mirror.synergy.CallMethod;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes2.dex */
public abstract class GeneratedMessage extends AbstractMessage implements Serializable {
    protected static boolean alwaysUseFieldBuilders = false;
    private static final long serialVersionUID = 1;
    protected UnknownFieldSet unknownFields;

    protected interface BuilderParent extends AbstractMessage.BuilderParent {
    }

    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage> extends MessageOrBuilder {
        @Override // com.google.protobuf.MessageOrBuilder
        Message getDefaultInstanceForType();

        <Type> Type getExtension(Extension<MessageType, Type> extension);

        <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int i);

        <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite);

        <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i);

        <Type> Type getExtension(GeneratedExtension<MessageType, Type> generatedExtension);

        <Type> Type getExtension(GeneratedExtension<MessageType, List<Type>> generatedExtension, int i);

        <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension);

        <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite);

        <Type> int getExtensionCount(GeneratedExtension<MessageType, List<Type>> generatedExtension);

        <Type> boolean hasExtension(Extension<MessageType, Type> extension);

        <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite);

        <Type> boolean hasExtension(GeneratedExtension<MessageType, Type> generatedExtension);
    }

    interface ExtensionDescriptorRetriever {
        Descriptors.FieldDescriptor getDescriptor();
    }

    protected abstract FieldAccessorTable internalGetFieldAccessorTable();

    protected void makeExtensionsImmutable() {
    }

    protected abstract Message.Builder newBuilderForType(BuilderParent builderParent);

    protected GeneratedMessage() {
        this.unknownFields = UnknownFieldSet.getDefaultInstance();
    }

    protected GeneratedMessage(Builder<?> builder) {
        this.unknownFields = builder.getUnknownFields();
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Parser<? extends GeneratedMessage> getParserForType() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    static void enableAlwaysUseFieldBuildersForTesting() {
        alwaysUseFieldBuilders = true;
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public Descriptors.Descriptor getDescriptorForType() {
        return internalGetFieldAccessorTable().descriptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:23:0x0066  */
    public Map<Descriptors.FieldDescriptor, Object> getAllFieldsMutable(boolean z) {
        TreeMap treeMap = new TreeMap();
        List<Descriptors.FieldDescriptor> fields = internalGetFieldAccessorTable().descriptor.getFields();
        int fieldCount = 0;
        while (fieldCount < fields.size()) {
            Descriptors.FieldDescriptor oneofFieldDescriptor = fields.get(fieldCount);
            Descriptors.OneofDescriptor containingOneof = oneofFieldDescriptor.getContainingOneof();
            if (containingOneof != null) {
                fieldCount += containingOneof.getFieldCount() - 1;
                if (hasOneof(containingOneof)) {
                    oneofFieldDescriptor = getOneofFieldDescriptor(containingOneof);
                    if (!z && oneofFieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.STRING) {
                        treeMap.put(oneofFieldDescriptor, getFieldRaw(oneofFieldDescriptor));
                    } else {
                        treeMap.put(oneofFieldDescriptor, getField(oneofFieldDescriptor));
                    }
                }
            } else if (oneofFieldDescriptor.isRepeated()) {
                List list = (List) getField(oneofFieldDescriptor);
                if (!list.isEmpty()) {
                    treeMap.put(oneofFieldDescriptor, list);
                }
            } else if (hasField(oneofFieldDescriptor)) {
                if (!z) {
                    treeMap.put(oneofFieldDescriptor, getField(oneofFieldDescriptor));
                } else {
                    treeMap.put(oneofFieldDescriptor, getField(oneofFieldDescriptor));
                }
            }
            fieldCount++;
        }
        return treeMap;
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
    public boolean isInitialized() {
        for (Descriptors.FieldDescriptor fieldDescriptor : getDescriptorForType().getFields()) {
            if (fieldDescriptor.isRequired() && !hasField(fieldDescriptor)) {
                return false;
            }
            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (fieldDescriptor.isRepeated()) {
                    Iterator it = ((List) getField(fieldDescriptor)).iterator();
                    while (it.hasNext()) {
                        if (!((Message) it.next()).isInitialized()) {
                            return false;
                        }
                    }
                } else if (hasField(fieldDescriptor) && !((Message) getField(fieldDescriptor)).isInitialized()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
        return Collections.unmodifiableMap(getAllFieldsMutable(false));
    }

    Map<Descriptors.FieldDescriptor, Object> getAllFieldsRaw() {
        return Collections.unmodifiableMap(getAllFieldsMutable(true));
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageOrBuilder
    public boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor) {
        return internalGetFieldAccessorTable().getOneof(oneofDescriptor).has(this);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageOrBuilder
    public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor) {
        return internalGetFieldAccessorTable().getOneof(oneofDescriptor).get(this);
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).has(this);
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).get(this);
    }

    Object getFieldRaw(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).getRaw(this);
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeatedCount(this);
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeated(this, i);
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public UnknownFieldSet getUnknownFields() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
        return builder.mergeFieldFrom(i, codedInputStream);
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, InputStream inputStream) throws IOException {
        try {
            return parser.parseFrom(inputStream);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        try {
            return parser.parseFrom(inputStream, extensionRegistryLite);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, CodedInputStream codedInputStream) throws IOException {
        try {
            return parser.parseFrom(codedInputStream);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        try {
            return parser.parseFrom(codedInputStream, extensionRegistryLite);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseDelimitedWithIOException(Parser<M> parser, InputStream inputStream) throws IOException {
        try {
            return parser.parseDelimitedFrom(inputStream);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseDelimitedWithIOException(Parser<M> parser, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        try {
            return parser.parseDelimitedFrom(inputStream, extensionRegistryLite);
        } catch (InvalidProtocolBufferException e) {
            throw e.unwrapIOException();
        }
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        MessageReflection.writeMessageTo(this, getAllFieldsRaw(), codedOutputStream, false);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSize;
        if (i != -1) {
            return i;
        }
        this.memoizedSize = MessageReflection.getSerializedSize(this, getAllFieldsRaw());
        return this.memoizedSize;
    }

    @Override // com.google.protobuf.AbstractMessage
    protected Message.Builder newBuilderForType(final AbstractMessage.BuilderParent builderParent) {
        return newBuilderForType(new BuilderParent() { // from class: com.google.protobuf.GeneratedMessage.1
            @Override // com.google.protobuf.AbstractMessage.BuilderParent
            public void markDirty() {
                builderParent.markDirty();
            }
        });
    }

    public static abstract class Builder<BuilderType extends Builder<BuilderType>> extends AbstractMessage.Builder<BuilderType> {
        private BuilderParent builderParent;
        private boolean isClean;
        private Builder<BuilderType>.BuilderParentImpl meAsParent;
        private UnknownFieldSet unknownFields;

        protected abstract FieldAccessorTable internalGetFieldAccessorTable();

        protected Builder() {
            this(null);
        }

        protected Builder(BuilderParent builderParent) {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            this.builderParent = builderParent;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder
        void dispose() {
            this.builderParent = null;
        }

        protected void onBuilt() {
            if (this.builderParent != null) {
                markClean();
            }
        }

        @Override // com.google.protobuf.AbstractMessage.Builder
        protected void markClean() {
            this.isClean = true;
        }

        protected boolean isClean() {
            return this.isClean;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
        /* JADX INFO: renamed from: clone */
        public BuilderType mo480clone() {
            BuilderType buildertype = (BuilderType) getDefaultInstanceForType().newBuilderForType();
            buildertype.mergeFrom(buildPartial());
            return buildertype;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public BuilderType clear() {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            onChanged();
            return this;
        }

        @Override // com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
        public Descriptors.Descriptor getDescriptorForType() {
            return internalGetFieldAccessorTable().descriptor;
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            return Collections.unmodifiableMap(getAllFieldsMutable());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<Descriptors.FieldDescriptor, Object> getAllFieldsMutable() {
            TreeMap treeMap = new TreeMap();
            List<Descriptors.FieldDescriptor> fields = internalGetFieldAccessorTable().descriptor.getFields();
            int fieldCount = 0;
            while (fieldCount < fields.size()) {
                Descriptors.FieldDescriptor oneofFieldDescriptor = fields.get(fieldCount);
                Descriptors.OneofDescriptor containingOneof = oneofFieldDescriptor.getContainingOneof();
                if (containingOneof != null) {
                    fieldCount += containingOneof.getFieldCount() - 1;
                    if (hasOneof(containingOneof)) {
                        oneofFieldDescriptor = getOneofFieldDescriptor(containingOneof);
                        treeMap.put(oneofFieldDescriptor, getField(oneofFieldDescriptor));
                    }
                } else if (oneofFieldDescriptor.isRepeated()) {
                    List list = (List) getField(oneofFieldDescriptor);
                    if (!list.isEmpty()) {
                        treeMap.put(oneofFieldDescriptor, list);
                    }
                } else if (hasField(oneofFieldDescriptor)) {
                    treeMap.put(oneofFieldDescriptor, getField(oneofFieldDescriptor));
                }
                fieldCount++;
            }
            return treeMap;
        }

        @Override // com.google.protobuf.Message.Builder
        public Message.Builder newBuilderForField(Descriptors.FieldDescriptor fieldDescriptor) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).newBuilder();
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public Message.Builder getFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).getBuilder(this);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public Message.Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor, int i) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeatedBuilder(this, i);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageOrBuilder
        public boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            return internalGetFieldAccessorTable().getOneof(oneofDescriptor).has(this);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageOrBuilder
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor) {
            return internalGetFieldAccessorTable().getOneof(oneofDescriptor).get(this);
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).has(this);
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            Object obj = internalGetFieldAccessorTable().getField(fieldDescriptor).get(this);
            return fieldDescriptor.isRepeated() ? Collections.unmodifiableList((List) obj) : obj;
        }

        @Override // com.google.protobuf.Message.Builder
        public BuilderType setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            internalGetFieldAccessorTable().getField(fieldDescriptor).set(this, obj);
            return this;
        }

        @Override // com.google.protobuf.Message.Builder
        public BuilderType clearField(Descriptors.FieldDescriptor fieldDescriptor) {
            internalGetFieldAccessorTable().getField(fieldDescriptor).clear(this);
            return this;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public BuilderType clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            internalGetFieldAccessorTable().getOneof(oneofDescriptor).clear(this);
            return this;
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeatedCount(this);
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i) {
            return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeated(this, i);
        }

        @Override // com.google.protobuf.Message.Builder
        public BuilderType setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj) {
            internalGetFieldAccessorTable().getField(fieldDescriptor).setRepeated(this, i, obj);
            return this;
        }

        @Override // com.google.protobuf.Message.Builder
        public BuilderType addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            internalGetFieldAccessorTable().getField(fieldDescriptor).addRepeated(this, obj);
            return this;
        }

        @Override // com.google.protobuf.Message.Builder
        public BuilderType setUnknownFields(UnknownFieldSet unknownFieldSet) {
            this.unknownFields = unknownFieldSet;
            onChanged();
            return this;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public BuilderType mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
            this.unknownFields = UnknownFieldSet.newBuilder(this.unknownFields).mergeFrom(unknownFieldSet).build();
            onChanged();
            return this;
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            for (Descriptors.FieldDescriptor fieldDescriptor : getDescriptorForType().getFields()) {
                if (fieldDescriptor.isRequired() && !hasField(fieldDescriptor)) {
                    return false;
                }
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    if (fieldDescriptor.isRepeated()) {
                        Iterator it = ((List) getField(fieldDescriptor)).iterator();
                        while (it.hasNext()) {
                            if (!((Message) it.next()).isInitialized()) {
                                return false;
                            }
                        }
                    } else if (hasField(fieldDescriptor) && !((Message) getField(fieldDescriptor)).isInitialized()) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
            return builder.mergeFieldFrom(i, codedInputStream);
        }

        private class BuilderParentImpl implements BuilderParent {
            private BuilderParentImpl() {
            }

            @Override // com.google.protobuf.AbstractMessage.BuilderParent
            public void markDirty() {
                Builder.this.onChanged();
            }
        }

        protected BuilderParent getParentForChildren() {
            if (this.meAsParent == null) {
                this.meAsParent = new BuilderParentImpl();
            }
            return this.meAsParent;
        }

        protected final void onChanged() {
            BuilderParent builderParent;
            if (!this.isClean || (builderParent = this.builderParent) == null) {
                return;
            }
            builderParent.markDirty();
            this.isClean = false;
        }

        protected MapField internalGetMapField(int i) {
            throw new RuntimeException("No map fields found in " + getClass().getName());
        }

        protected MapField internalGetMutableMapField(int i) {
            throw new RuntimeException("No map fields found in " + getClass().getName());
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage> extends GeneratedMessage implements ExtendableMessageOrBuilder<MessageType> {
        private static final long serialVersionUID = 1;
        private final FieldSet<Descriptors.FieldDescriptor> extensions;

        protected ExtendableMessage() {
            this.extensions = FieldSet.newFieldSet();
        }

        protected ExtendableMessage(ExtendableBuilder<MessageType, ?> extendableBuilder) {
            super(extendableBuilder);
            this.extensions = extendableBuilder.buildExtensions();
        }

        private void verifyExtensionContainingType(Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + getDescriptorForType().getFullName() + "\".");
            }
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            return this.extensions.hasField(extensionCheckNotLite.getDescriptor());
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            return this.extensions.getRepeatedFieldCount(extensionCheckNotLite.getDescriptor());
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            Descriptors.FieldDescriptor descriptor = extensionCheckNotLite.getDescriptor();
            Object field = this.extensions.getField(descriptor);
            if (field == null) {
                if (descriptor.isRepeated()) {
                    return (Type) Collections.emptyList();
                }
                if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    return (Type) extensionCheckNotLite.getMessageDefaultInstance();
                }
                return (Type) extensionCheckNotLite.fromReflectionType(descriptor.getDefaultValue());
            }
            return (Type) extensionCheckNotLite.fromReflectionType(field);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            return (Type) extensionCheckNotLite.singularFromReflectionType(this.extensions.getRepeatedField(extensionCheckNotLite.getDescriptor(), i));
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(Extension<MessageType, Type> extension) {
            return hasExtension((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(GeneratedExtension<MessageType, Type> generatedExtension) {
            return hasExtension((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension) {
            return getExtensionCount((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(GeneratedExtension<MessageType, List<Type>> generatedExtension) {
            return getExtensionCount((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(Extension<MessageType, Type> extension) {
            return (Type) getExtension((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(GeneratedExtension<MessageType, Type> generatedExtension) {
            return (Type) getExtension((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int i) {
            return (Type) getExtension((ExtensionLite) extension, i);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(GeneratedExtension<MessageType, List<Type>> generatedExtension, int i) {
            return (Type) getExtension((ExtensionLite) generatedExtension, i);
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        @Override // com.google.protobuf.GeneratedMessage, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            return super.isInitialized() && extensionsAreInitialized();
        }

        @Override // com.google.protobuf.GeneratedMessage
        protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
            return MessageReflection.mergeFieldFrom(codedInputStream, builder, extensionRegistryLite, getDescriptorForType(), new MessageReflection.ExtensionAdapter(this.extensions), i);
        }

        @Override // com.google.protobuf.GeneratedMessage
        protected void makeExtensionsImmutable() {
            this.extensions.makeImmutable();
        }

        protected class ExtensionWriter {
            private final Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Map.Entry<Descriptors.FieldDescriptor, Object> next;

            private ExtensionWriter(boolean z) {
                Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> it = ExtendableMessage.this.extensions.iterator();
                this.iter = it;
                if (it.hasNext()) {
                    this.next = it.next();
                }
                this.messageSetWireFormat = z;
            }

            public void writeUntil(int i, CodedOutputStream codedOutputStream) throws IOException {
                while (true) {
                    Map.Entry<Descriptors.FieldDescriptor, Object> entry = this.next;
                    if (entry == null || entry.getKey().getNumber() >= i) {
                        return;
                    }
                    Descriptors.FieldDescriptor key = this.next.getKey();
                    if (this.messageSetWireFormat && key.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !key.isRepeated()) {
                        if (this.next instanceof LazyField.LazyEntry) {
                            codedOutputStream.writeRawMessageSetExtension(key.getNumber(), ((LazyField.LazyEntry) this.next).getField().toByteString());
                        } else {
                            codedOutputStream.writeMessageSetExtension(key.getNumber(), (Message) this.next.getValue());
                        }
                    } else {
                        FieldSet.writeField(key, this.next.getValue(), codedOutputStream);
                    }
                    if (this.iter.hasNext()) {
                        this.next = this.iter.next();
                    } else {
                        this.next = null;
                    }
                }
            }
        }

        protected ExtendableMessage<MessageType>.ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter(false);
        }

        protected ExtendableMessage<MessageType>.ExtensionWriter newMessageSetExtensionWriter() {
            return new ExtensionWriter(true);
        }

        protected int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        protected int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }

        protected Map<Descriptors.FieldDescriptor, Object> getExtensionFields() {
            return this.extensions.getAllFields();
        }

        @Override // com.google.protobuf.GeneratedMessage, com.google.protobuf.MessageOrBuilder
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            Map allFieldsMutable = getAllFieldsMutable(false);
            allFieldsMutable.putAll(getExtensionFields());
            return Collections.unmodifiableMap(allFieldsMutable);
        }

        @Override // com.google.protobuf.GeneratedMessage
        public Map<Descriptors.FieldDescriptor, Object> getAllFieldsRaw() {
            Map allFieldsMutable = getAllFieldsMutable(false);
            allFieldsMutable.putAll(getExtensionFields());
            return Collections.unmodifiableMap(allFieldsMutable);
        }

        @Override // com.google.protobuf.GeneratedMessage, com.google.protobuf.MessageOrBuilder
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.hasField(fieldDescriptor);
            }
            return super.hasField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessage, com.google.protobuf.MessageOrBuilder
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                Object field = this.extensions.getField(fieldDescriptor);
                if (field != null) {
                    return field;
                }
                if (fieldDescriptor.isRepeated()) {
                    return Collections.emptyList();
                }
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    return DynamicMessage.getDefaultInstance(fieldDescriptor.getMessageType());
                }
                return fieldDescriptor.getDefaultValue();
            }
            return super.getField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessage, com.google.protobuf.MessageOrBuilder
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.getRepeatedFieldCount(fieldDescriptor);
            }
            return super.getRepeatedFieldCount(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessage, com.google.protobuf.MessageOrBuilder
        public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.getRepeatedField(fieldDescriptor, i);
            }
            return super.getRepeatedField(fieldDescriptor, i);
        }

        private void verifyContainingType(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends Builder<BuilderType> implements ExtendableMessageOrBuilder<MessageType> {
        private FieldSet<Descriptors.FieldDescriptor> extensions;

        protected ExtendableBuilder() {
            this.extensions = FieldSet.emptySet();
        }

        protected ExtendableBuilder(BuilderParent builderParent) {
            super(builderParent);
            this.extensions = FieldSet.emptySet();
        }

        void internalSetExtensionSet(FieldSet<Descriptors.FieldDescriptor> fieldSet) {
            this.extensions = fieldSet;
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public BuilderType clear() {
            this.extensions = FieldSet.emptySet();
            return (BuilderType) super.clear();
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
        /* JADX INFO: renamed from: clone */
        public BuilderType mo480clone() {
            return (BuilderType) super.mo480clone();
        }

        private void ensureExtensionsIsMutable() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.m481clone();
            }
        }

        private void verifyExtensionContainingType(Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + getDescriptorForType().getFullName() + "\".");
            }
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            return this.extensions.hasField(extensionCheckNotLite.getDescriptor());
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            return this.extensions.getRepeatedFieldCount(extensionCheckNotLite.getDescriptor());
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            Descriptors.FieldDescriptor descriptor = extensionCheckNotLite.getDescriptor();
            Object field = this.extensions.getField(descriptor);
            if (field == null) {
                if (descriptor.isRepeated()) {
                    return (Type) Collections.emptyList();
                }
                if (descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    return (Type) extensionCheckNotLite.getMessageDefaultInstance();
                }
                return (Type) extensionCheckNotLite.fromReflectionType(descriptor.getDefaultValue());
            }
            return (Type) extensionCheckNotLite.fromReflectionType(field);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            return (Type) extensionCheckNotLite.singularFromReflectionType(this.extensions.getRepeatedField(extensionCheckNotLite.getDescriptor(), i));
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> extensionLite, Type type) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            ensureExtensionsIsMutable();
            this.extensions.setField(extensionCheckNotLite.getDescriptor(), extensionCheckNotLite.toReflectionType(type));
            onChanged();
            return this;
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i, Type type) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            ensureExtensionsIsMutable();
            this.extensions.setRepeatedField(extensionCheckNotLite.getDescriptor(), i, extensionCheckNotLite.singularToReflectionType(type));
            onChanged();
            return this;
        }

        public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> extensionLite, Type type) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            ensureExtensionsIsMutable();
            this.extensions.addRepeatedField(extensionCheckNotLite.getDescriptor(), extensionCheckNotLite.singularToReflectionType(type));
            onChanged();
            return this;
        }

        public final <Type> BuilderType clearExtension(ExtensionLite<MessageType, ?> extensionLite) {
            Extension<MessageType, ?> extensionCheckNotLite = GeneratedMessage.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            ensureExtensionsIsMutable();
            this.extensions.clearField(extensionCheckNotLite.getDescriptor());
            onChanged();
            return this;
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(Extension<MessageType, Type> extension) {
            return hasExtension((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(GeneratedExtension<MessageType, Type> generatedExtension) {
            return hasExtension((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension) {
            return getExtensionCount((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(GeneratedExtension<MessageType, List<Type>> generatedExtension) {
            return getExtensionCount((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(Extension<MessageType, Type> extension) {
            return (Type) getExtension((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(GeneratedExtension<MessageType, Type> generatedExtension) {
            return (Type) getExtension((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int i) {
            return (Type) getExtension((ExtensionLite) extension, i);
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(GeneratedExtension<MessageType, List<Type>> generatedExtension, int i) {
            return (Type) getExtension((ExtensionLite) generatedExtension, i);
        }

        public final <Type> BuilderType setExtension(Extension<MessageType, Type> extension, Type type) {
            return (BuilderType) setExtension((ExtensionLite) extension, (Object) type);
        }

        public <Type> BuilderType setExtension(GeneratedExtension<MessageType, Type> generatedExtension, Type type) {
            return (BuilderType) setExtension((ExtensionLite) generatedExtension, (Object) type);
        }

        public final <Type> BuilderType setExtension(Extension<MessageType, List<Type>> extension, int i, Type type) {
            return (BuilderType) setExtension((ExtensionLite) extension, i, (Object) type);
        }

        public <Type> BuilderType setExtension(GeneratedExtension<MessageType, List<Type>> generatedExtension, int i, Type type) {
            return (BuilderType) setExtension((ExtensionLite) generatedExtension, i, (Object) type);
        }

        public final <Type> BuilderType addExtension(Extension<MessageType, List<Type>> extension, Type type) {
            return (BuilderType) addExtension((ExtensionLite) extension, (Object) type);
        }

        public <Type> BuilderType addExtension(GeneratedExtension<MessageType, List<Type>> generatedExtension, Type type) {
            return (BuilderType) addExtension((ExtensionLite) generatedExtension, (Object) type);
        }

        public final <Type> BuilderType clearExtension(Extension<MessageType, ?> extension) {
            return (BuilderType) clearExtension((ExtensionLite) extension);
        }

        public <Type> BuilderType clearExtension(GeneratedExtension<MessageType, ?> generatedExtension) {
            return (BuilderType) clearExtension((ExtensionLite) generatedExtension);
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FieldSet<Descriptors.FieldDescriptor> buildExtensions() {
            this.extensions.makeImmutable();
            return this.extensions;
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            return super.isInitialized() && extensionsAreInitialized();
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder
        protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i) throws IOException {
            return MessageReflection.mergeFieldFrom(codedInputStream, builder, extensionRegistryLite, getDescriptorForType(), new MessageReflection.BuilderAdapter(this), i);
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.MessageOrBuilder
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            Map allFieldsMutable = getAllFieldsMutable();
            allFieldsMutable.putAll(this.extensions.getAllFields());
            return Collections.unmodifiableMap(allFieldsMutable);
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.MessageOrBuilder
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                Object field = this.extensions.getField(fieldDescriptor);
                if (field != null) {
                    return field;
                }
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    return DynamicMessage.getDefaultInstance(fieldDescriptor.getMessageType());
                }
                return fieldDescriptor.getDefaultValue();
            }
            return super.getField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.MessageOrBuilder
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.getRepeatedFieldCount(fieldDescriptor);
            }
            return super.getRepeatedFieldCount(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.MessageOrBuilder
        public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.getRepeatedField(fieldDescriptor, i);
            }
            return super.getRepeatedField(fieldDescriptor, i);
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.MessageOrBuilder
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                return this.extensions.hasField(fieldDescriptor);
            }
            return super.hasField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.Message.Builder
        public BuilderType setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.setField(fieldDescriptor, obj);
                onChanged();
                return this;
            }
            return (BuilderType) super.setField(fieldDescriptor, obj);
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.Message.Builder
        public BuilderType clearField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.clearField(fieldDescriptor);
                onChanged();
                return this;
            }
            return (BuilderType) super.clearField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.Message.Builder
        public BuilderType setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i, Object obj) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.setRepeatedField(fieldDescriptor, i, obj);
                onChanged();
                return this;
            }
            return (BuilderType) super.setRepeatedField(fieldDescriptor, i, obj);
        }

        @Override // com.google.protobuf.GeneratedMessage.Builder, com.google.protobuf.Message.Builder
        public BuilderType addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.addRepeatedField(fieldDescriptor, obj);
                onChanged();
                return this;
            }
            return (BuilderType) super.addRepeatedField(fieldDescriptor, obj);
        }

        protected final void mergeExtensionFields(ExtendableMessage extendableMessage) {
            ensureExtensionsIsMutable();
            this.extensions.mergeFrom(extendableMessage.extensions);
            onChanged();
        }

        private void verifyContainingType(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newMessageScopedGeneratedExtension(final Message message, final int i, Class cls, Message message2) {
        return new GeneratedExtension<>(new CachedDescriptorRetriever() { // from class: com.google.protobuf.GeneratedMessage.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.protobuf.GeneratedMessage.CachedDescriptorRetriever
            public Descriptors.FieldDescriptor loadDescriptor() {
                return message.getDescriptorForType().getExtensions().get(i);
            }
        }, cls, message2, Extension.ExtensionType.IMMUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newFileScopedGeneratedExtension(Class cls, Message message) {
        return new GeneratedExtension<>(null, cls, message, Extension.ExtensionType.IMMUTABLE);
    }

    private static abstract class CachedDescriptorRetriever implements ExtensionDescriptorRetriever {
        private volatile Descriptors.FieldDescriptor descriptor;

        protected abstract Descriptors.FieldDescriptor loadDescriptor();

        private CachedDescriptorRetriever() {
        }

        @Override // com.google.protobuf.GeneratedMessage.ExtensionDescriptorRetriever
        public Descriptors.FieldDescriptor getDescriptor() {
            if (this.descriptor == null) {
                synchronized (this) {
                    if (this.descriptor == null) {
                        this.descriptor = loadDescriptor();
                    }
                }
            }
            return this.descriptor;
        }
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newMessageScopedGeneratedExtension(final Message message, final String str, Class cls, Message message2) {
        return new GeneratedExtension<>(new CachedDescriptorRetriever() { // from class: com.google.protobuf.GeneratedMessage.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.protobuf.GeneratedMessage.CachedDescriptorRetriever
            protected Descriptors.FieldDescriptor loadDescriptor() {
                return message.getDescriptorForType().findFieldByName(str);
            }
        }, cls, message2, Extension.ExtensionType.MUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newFileScopedGeneratedExtension(final Class cls, Message message, final String str, final String str2) {
        return new GeneratedExtension<>(new CachedDescriptorRetriever() { // from class: com.google.protobuf.GeneratedMessage.4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.google.protobuf.GeneratedMessage.CachedDescriptorRetriever
            protected Descriptors.FieldDescriptor loadDescriptor() {
                try {
                    return ((Descriptors.FileDescriptor) cls.getClassLoader().loadClass(str).getField("descriptor").get(null)).findExtensionByName(str2);
                } catch (Exception e) {
                    throw new RuntimeException("Cannot load descriptors: " + str + " is not a valid descriptor class name", e);
                }
            }
        }, cls, message, Extension.ExtensionType.MUTABLE);
    }

    public static class GeneratedExtension<ContainingType extends Message, Type> extends Extension<ContainingType, Type> {
        private ExtensionDescriptorRetriever descriptorRetriever;
        private final java.lang.reflect.Method enumGetValueDescriptor;
        private final java.lang.reflect.Method enumValueOf;
        private final Extension.ExtensionType extensionType;
        private final Message messageDefaultInstance;
        private final Class singularType;

        GeneratedExtension(ExtensionDescriptorRetriever extensionDescriptorRetriever, Class cls, Message message, Extension.ExtensionType extensionType) {
            if (Message.class.isAssignableFrom(cls) && !cls.isInstance(message)) {
                throw new IllegalArgumentException("Bad messageDefaultInstance for " + cls.getName());
            }
            this.descriptorRetriever = extensionDescriptorRetriever;
            this.singularType = cls;
            this.messageDefaultInstance = message;
            if (ProtocolMessageEnum.class.isAssignableFrom(cls)) {
                this.enumValueOf = GeneratedMessage.getMethodOrDie(cls, CoreConstants.VALUE_OF, Descriptors.EnumValueDescriptor.class);
                this.enumGetValueDescriptor = GeneratedMessage.getMethodOrDie(cls, "getValueDescriptor", new Class[0]);
            } else {
                this.enumValueOf = null;
                this.enumGetValueDescriptor = null;
            }
            this.extensionType = extensionType;
        }

        public void internalInit(final Descriptors.FieldDescriptor fieldDescriptor) {
            if (this.descriptorRetriever != null) {
                throw new IllegalStateException("Already initialized.");
            }
            this.descriptorRetriever = new ExtensionDescriptorRetriever() { // from class: com.google.protobuf.GeneratedMessage.GeneratedExtension.1
                @Override // com.google.protobuf.GeneratedMessage.ExtensionDescriptorRetriever
                public Descriptors.FieldDescriptor getDescriptor() {
                    return fieldDescriptor;
                }
            };
        }

        @Override // com.google.protobuf.Extension
        public Descriptors.FieldDescriptor getDescriptor() {
            ExtensionDescriptorRetriever extensionDescriptorRetriever = this.descriptorRetriever;
            if (extensionDescriptorRetriever == null) {
                throw new IllegalStateException("getDescriptor() called before internalInit()");
            }
            return extensionDescriptorRetriever.getDescriptor();
        }

        @Override // com.google.protobuf.ExtensionLite
        public Message getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        @Override // com.google.protobuf.Extension
        protected Extension.ExtensionType getExtensionType() {
            return this.extensionType;
        }

        @Override // com.google.protobuf.Extension
        protected Object fromReflectionType(Object obj) {
            Descriptors.FieldDescriptor descriptor = getDescriptor();
            if (descriptor.isRepeated()) {
                if (descriptor.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE && descriptor.getJavaType() != Descriptors.FieldDescriptor.JavaType.ENUM) {
                    return obj;
                }
                ArrayList arrayList = new ArrayList();
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    arrayList.add(singularFromReflectionType(it.next()));
                }
                return arrayList;
            }
            return singularFromReflectionType(obj);
        }

        @Override // com.google.protobuf.Extension
        protected Object singularFromReflectionType(Object obj) {
            int i = AnonymousClass5.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType[getDescriptor().getJavaType().ordinal()];
            if (i != 1) {
                if (i == 2) {
                    return GeneratedMessage.invokeOrDie(this.enumValueOf, null, (Descriptors.EnumValueDescriptor) obj);
                }
            } else if (!this.singularType.isInstance(obj)) {
                return this.messageDefaultInstance.newBuilderForType().mergeFrom((Message) obj).build();
            }
            return obj;
        }

        @Override // com.google.protobuf.Extension
        protected Object toReflectionType(Object obj) {
            Descriptors.FieldDescriptor descriptor = getDescriptor();
            if (descriptor.isRepeated()) {
                if (descriptor.getJavaType() != Descriptors.FieldDescriptor.JavaType.ENUM) {
                    return obj;
                }
                ArrayList arrayList = new ArrayList();
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    arrayList.add(singularToReflectionType(it.next()));
                }
                return arrayList;
            }
            return singularToReflectionType(obj);
        }

        @Override // com.google.protobuf.Extension
        protected Object singularToReflectionType(Object obj) {
            return AnonymousClass5.$SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType[getDescriptor().getJavaType().ordinal()] != 2 ? obj : GeneratedMessage.invokeOrDie(this.enumGetValueDescriptor, obj, new Object[0]);
        }

        @Override // com.google.protobuf.ExtensionLite
        public int getNumber() {
            return getDescriptor().getNumber();
        }

        @Override // com.google.protobuf.ExtensionLite
        public WireFormat.FieldType getLiteType() {
            return getDescriptor().getLiteType();
        }

        @Override // com.google.protobuf.ExtensionLite
        public boolean isRepeated() {
            return getDescriptor().isRepeated();
        }

        @Override // com.google.protobuf.ExtensionLite
        public Type getDefaultValue() {
            if (isRepeated()) {
                return (Type) Collections.emptyList();
            }
            if (getDescriptor().getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                return (Type) this.messageDefaultInstance;
            }
            return (Type) singularFromReflectionType(getDescriptor().getDefaultValue());
        }
    }

    /* JADX INFO: renamed from: com.google.protobuf.GeneratedMessage$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType;

        static {
            int[] iArr = new int[Descriptors.FieldDescriptor.JavaType.values().length];
            $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType = iArr;
            try {
                iArr[Descriptors.FieldDescriptor.JavaType.MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$Descriptors$FieldDescriptor$JavaType[Descriptors.FieldDescriptor.JavaType.ENUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.reflect.Method getMethodOrDie(Class cls, String str, Class... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Generated message class \"" + cls.getName() + "\" missing method \"" + str + "\".", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object invokeOrDie(java.lang.reflect.Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected MapField internalGetMapField(int i) {
        throw new RuntimeException("No map fields found in " + getClass().getName());
    }

    public static final class FieldAccessorTable {
        private String[] camelCaseNames;
        private final Descriptors.Descriptor descriptor;
        private final FieldAccessor[] fields;
        private volatile boolean initialized;
        private final OneofAccessor[] oneofs;

        private interface FieldAccessor {
            void addRepeated(Builder builder, Object obj);

            void clear(Builder builder);

            Object get(Builder builder);

            Object get(GeneratedMessage generatedMessage);

            Message.Builder getBuilder(Builder builder);

            Object getRaw(Builder builder);

            Object getRaw(GeneratedMessage generatedMessage);

            Object getRepeated(Builder builder, int i);

            Object getRepeated(GeneratedMessage generatedMessage, int i);

            Message.Builder getRepeatedBuilder(Builder builder, int i);

            int getRepeatedCount(Builder builder);

            int getRepeatedCount(GeneratedMessage generatedMessage);

            Object getRepeatedRaw(Builder builder, int i);

            Object getRepeatedRaw(GeneratedMessage generatedMessage, int i);

            boolean has(Builder builder);

            boolean has(GeneratedMessage generatedMessage);

            Message.Builder newBuilder();

            void set(Builder builder, Object obj);

            void setRepeated(Builder builder, int i, Object obj);
        }

        private boolean isMapFieldEnabled(Descriptors.FieldDescriptor fieldDescriptor) {
            return true;
        }

        public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] strArr, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
            this(descriptor, strArr);
            ensureFieldAccessorsInitialized(cls, cls2);
        }

        public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] strArr) {
            this.descriptor = descriptor;
            this.camelCaseNames = strArr;
            this.fields = new FieldAccessor[descriptor.getFields().size()];
            this.oneofs = new OneofAccessor[descriptor.getOneofs().size()];
            this.initialized = false;
        }

        public FieldAccessorTable ensureFieldAccessorsInitialized(Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
            Class<? extends GeneratedMessage> cls3;
            Class<? extends Builder> cls4;
            if (this.initialized) {
                return this;
            }
            synchronized (this) {
                if (this.initialized) {
                    return this;
                }
                int length = this.fields.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Descriptors.FieldDescriptor fieldDescriptor = this.descriptor.getFields().get(i);
                    String str = fieldDescriptor.getContainingOneof() != null ? this.camelCaseNames[fieldDescriptor.getContainingOneof().getIndex() + length] : null;
                    if (fieldDescriptor.isRepeated()) {
                        if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                            if (fieldDescriptor.isMapField() && isMapFieldEnabled(fieldDescriptor)) {
                                this.fields[i] = new MapFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2);
                            } else {
                                this.fields[i] = new RepeatedMessageFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2);
                            }
                        } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                            this.fields[i] = new RepeatedEnumFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2);
                        } else {
                            this.fields[i] = new RepeatedFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls, cls2);
                        }
                        cls3 = cls;
                        cls4 = cls2;
                    } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                        cls3 = cls;
                        cls4 = cls2;
                        this.fields[i] = new SingularMessageFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls3, cls4, str);
                    } else {
                        cls3 = cls;
                        cls4 = cls2;
                        if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                            this.fields[i] = new SingularEnumFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls3, cls4, str);
                        } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.STRING) {
                            this.fields[i] = new SingularStringFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls3, cls4, str);
                        } else {
                            this.fields[i] = new SingularFieldAccessor(fieldDescriptor, this.camelCaseNames[i], cls3, cls4, str);
                        }
                    }
                    i++;
                    cls = cls3;
                    cls2 = cls4;
                }
                Class<? extends GeneratedMessage> cls5 = cls;
                Class<? extends Builder> cls6 = cls2;
                int length2 = this.oneofs.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.oneofs[i2] = new OneofAccessor(this.descriptor, this.camelCaseNames[i2 + length], cls5, cls6);
                }
                this.initialized = true;
                this.camelCaseNames = null;
                return this;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FieldAccessor getField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.getContainingType() != this.descriptor) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
            if (fieldDescriptor.isExtension()) {
                throw new IllegalArgumentException("This type does not have extensions.");
            }
            return this.fields[fieldDescriptor.getIndex()];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public OneofAccessor getOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            if (oneofDescriptor.getContainingType() != this.descriptor) {
                throw new IllegalArgumentException("OneofDescriptor does not match message type.");
            }
            return this.oneofs[oneofDescriptor.getIndex()];
        }

        private static class OneofAccessor {
            private final java.lang.reflect.Method caseMethod;
            private final java.lang.reflect.Method caseMethodBuilder;
            private final java.lang.reflect.Method clearMethod;
            private final Descriptors.Descriptor descriptor;

            OneofAccessor(Descriptors.Descriptor descriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
                this.descriptor = descriptor;
                this.caseMethod = GeneratedMessage.getMethodOrDie(cls, CallMethod.METHOD_GET + str + "Case", new Class[0]);
                this.caseMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str + "Case", new Class[0]);
                this.clearMethod = GeneratedMessage.getMethodOrDie(cls2, "clear" + str, new Class[0]);
            }

            public boolean has(GeneratedMessage generatedMessage) {
                return ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, generatedMessage, new Object[0])).getNumber() != 0;
            }

            public boolean has(Builder builder) {
                return ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber() != 0;
            }

            public Descriptors.FieldDescriptor get(GeneratedMessage generatedMessage) {
                int number = ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, generatedMessage, new Object[0])).getNumber();
                if (number > 0) {
                    return this.descriptor.findFieldByNumber(number);
                }
                return null;
            }

            public Descriptors.FieldDescriptor get(Builder builder) {
                int number = ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
                if (number > 0) {
                    return this.descriptor.findFieldByNumber(number);
                }
                return null;
            }

            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static boolean supportFieldPresence(Descriptors.FileDescriptor fileDescriptor) {
            return fileDescriptor.getSyntax() == Descriptors.FileDescriptor.Syntax.PROTO2;
        }

        private static class SingularFieldAccessor implements FieldAccessor {
            protected final java.lang.reflect.Method caseMethod;
            protected final java.lang.reflect.Method caseMethodBuilder;
            protected final java.lang.reflect.Method clearMethod;
            protected final Descriptors.FieldDescriptor field;
            protected final java.lang.reflect.Method getMethod;
            protected final java.lang.reflect.Method getMethodBuilder;
            protected final boolean hasHasMethod;
            protected final java.lang.reflect.Method hasMethod;
            protected final java.lang.reflect.Method hasMethodBuilder;
            protected final boolean isOneofField;
            protected final java.lang.reflect.Method setMethod;
            protected final Class<?> type;

            SingularFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2, String str2) {
                this.field = fieldDescriptor;
                boolean z = fieldDescriptor.getContainingOneof() != null;
                this.isOneofField = z;
                boolean z2 = FieldAccessorTable.supportFieldPresence(fieldDescriptor.getFile()) || (!z && fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE);
                this.hasHasMethod = z2;
                java.lang.reflect.Method methodOrDie = GeneratedMessage.getMethodOrDie(cls, CallMethod.METHOD_GET + str, new Class[0]);
                this.getMethod = methodOrDie;
                this.getMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str, new Class[0]);
                Class<?> returnType = methodOrDie.getReturnType();
                this.type = returnType;
                this.setMethod = GeneratedMessage.getMethodOrDie(cls2, "set" + str, returnType);
                this.hasMethod = z2 ? GeneratedMessage.getMethodOrDie(cls, "has" + str, new Class[0]) : null;
                this.hasMethodBuilder = z2 ? GeneratedMessage.getMethodOrDie(cls2, "has" + str, new Class[0]) : null;
                this.clearMethod = GeneratedMessage.getMethodOrDie(cls2, "clear" + str, new Class[0]);
                this.caseMethod = z ? GeneratedMessage.getMethodOrDie(cls, CallMethod.METHOD_GET + str2 + "Case", new Class[0]) : null;
                this.caseMethodBuilder = z ? GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str2 + "Case", new Class[0]) : null;
            }

            private int getOneofFieldNumber(GeneratedMessage generatedMessage) {
                return ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, generatedMessage, new Object[0])).getNumber();
            }

            private int getOneofFieldNumber(Builder builder) {
                return ((Internal.EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessage generatedMessage) {
                return GeneratedMessage.invokeOrDie(this.getMethod, generatedMessage, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRaw(GeneratedMessage generatedMessage) {
                return get(generatedMessage);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRaw(Builder builder) {
                return get(builder);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                GeneratedMessage.invokeOrDie(this.setMethod, builder, obj);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessage generatedMessage, int i) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeatedRaw(GeneratedMessage generatedMessage, int i) {
                throw new UnsupportedOperationException("getRepeatedFieldRaw() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder builder, int i) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeatedRaw(Builder builder, int i) {
                throw new UnsupportedOperationException("getRepeatedFieldRaw() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder builder, int i, Object obj) {
                throw new UnsupportedOperationException("setRepeatedField() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder builder, Object obj) {
                throw new UnsupportedOperationException("addRepeatedField() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(GeneratedMessage generatedMessage) {
                if (this.hasHasMethod) {
                    return ((Boolean) GeneratedMessage.invokeOrDie(this.hasMethod, generatedMessage, new Object[0])).booleanValue();
                }
                if (this.isOneofField) {
                    return getOneofFieldNumber(generatedMessage) == this.field.getNumber();
                }
                return !get(generatedMessage).equals(this.field.getDefaultValue());
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(Builder builder) {
                if (this.hasHasMethod) {
                    return ((Boolean) GeneratedMessage.invokeOrDie(this.hasMethodBuilder, builder, new Object[0])).booleanValue();
                }
                if (this.isOneofField) {
                    return getOneofFieldNumber(builder) == this.field.getNumber();
                }
                return !get(builder).equals(this.field.getDefaultValue());
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(GeneratedMessage generatedMessage) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(Builder builder) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getRepeatedBuilder(Builder builder, int i) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
        }

        private static class RepeatedFieldAccessor implements FieldAccessor {
            protected final java.lang.reflect.Method addRepeatedMethod;
            protected final java.lang.reflect.Method clearMethod;
            protected final java.lang.reflect.Method getCountMethod;
            protected final java.lang.reflect.Method getCountMethodBuilder;
            protected final java.lang.reflect.Method getMethod;
            protected final java.lang.reflect.Method getMethodBuilder;
            protected final java.lang.reflect.Method getRepeatedMethod;
            protected final java.lang.reflect.Method getRepeatedMethodBuilder;
            protected final java.lang.reflect.Method setRepeatedMethod;
            protected final Class type;

            RepeatedFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
                this.getMethod = GeneratedMessage.getMethodOrDie(cls, CallMethod.METHOD_GET + str + ListScreenElement.TAG_NAME, new Class[0]);
                this.getMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str + ListScreenElement.TAG_NAME, new Class[0]);
                java.lang.reflect.Method methodOrDie = GeneratedMessage.getMethodOrDie(cls, CallMethod.METHOD_GET + str, Integer.TYPE);
                this.getRepeatedMethod = methodOrDie;
                this.getRepeatedMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str, Integer.TYPE);
                Class<?> returnType = methodOrDie.getReturnType();
                this.type = returnType;
                this.setRepeatedMethod = GeneratedMessage.getMethodOrDie(cls2, "set" + str, Integer.TYPE, returnType);
                this.addRepeatedMethod = GeneratedMessage.getMethodOrDie(cls2, "add" + str, returnType);
                this.getCountMethod = GeneratedMessage.getMethodOrDie(cls, CallMethod.METHOD_GET + str + "Count", new Class[0]);
                this.getCountMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str + "Count", new Class[0]);
                this.clearMethod = GeneratedMessage.getMethodOrDie(cls2, "clear" + str, new Class[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessage generatedMessage) {
                return GeneratedMessage.invokeOrDie(this.getMethod, generatedMessage, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRaw(GeneratedMessage generatedMessage) {
                return get(generatedMessage);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRaw(Builder builder) {
                return get(builder);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                clear(builder);
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    addRepeated(builder, it.next());
                }
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessage generatedMessage, int i) {
                return GeneratedMessage.invokeOrDie(this.getRepeatedMethod, generatedMessage, Integer.valueOf(i));
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder builder, int i) {
                return GeneratedMessage.invokeOrDie(this.getRepeatedMethodBuilder, builder, Integer.valueOf(i));
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeatedRaw(GeneratedMessage generatedMessage, int i) {
                return getRepeated(generatedMessage, i);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeatedRaw(Builder builder, int i) {
                return getRepeated(builder, i);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder builder, int i, Object obj) {
                GeneratedMessage.invokeOrDie(this.setRepeatedMethod, builder, Integer.valueOf(i), obj);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder builder, Object obj) {
                GeneratedMessage.invokeOrDie(this.addRepeatedMethod, builder, obj);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(GeneratedMessage generatedMessage) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(Builder builder) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(GeneratedMessage generatedMessage) {
                return ((Integer) GeneratedMessage.invokeOrDie(this.getCountMethod, generatedMessage, new Object[0])).intValue();
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(Builder builder) {
                return ((Integer) GeneratedMessage.invokeOrDie(this.getCountMethodBuilder, builder, new Object[0])).intValue();
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getRepeatedBuilder(Builder builder, int i) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
        }

        private static class MapFieldAccessor implements FieldAccessor {
            private final Descriptors.FieldDescriptor field;
            private final Message mapEntryMessageDefaultInstance;

            MapFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
                this.field = fieldDescriptor;
                this.mapEntryMessageDefaultInstance = getMapField((GeneratedMessage) GeneratedMessage.invokeOrDie(GeneratedMessage.getMethodOrDie(cls, "getDefaultInstance", new Class[0]), null, new Object[0])).getMapEntryMessageDefaultInstance();
            }

            private MapField<?, ?> getMapField(GeneratedMessage generatedMessage) {
                return generatedMessage.internalGetMapField(this.field.getNumber());
            }

            private MapField<?, ?> getMapField(Builder builder) {
                return builder.internalGetMapField(this.field.getNumber());
            }

            private MapField<?, ?> getMutableMapField(Builder builder) {
                return builder.internalGetMutableMapField(this.field.getNumber());
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessage generatedMessage) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < getRepeatedCount(generatedMessage); i++) {
                    arrayList.add(getRepeated(generatedMessage, i));
                }
                return Collections.unmodifiableList(arrayList);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(Builder builder) {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < getRepeatedCount(builder); i++) {
                    arrayList.add(getRepeated(builder, i));
                }
                return Collections.unmodifiableList(arrayList);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRaw(GeneratedMessage generatedMessage) {
                return get(generatedMessage);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRaw(Builder builder) {
                return get(builder);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                clear(builder);
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    addRepeated(builder, it.next());
                }
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessage generatedMessage, int i) {
                return getMapField(generatedMessage).getList().get(i);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder builder, int i) {
                return getMapField(builder).getList().get(i);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeatedRaw(GeneratedMessage generatedMessage, int i) {
                return getRepeated(generatedMessage, i);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeatedRaw(Builder builder, int i) {
                return getRepeated(builder, i);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder builder, int i, Object obj) {
                getMutableMapField(builder).getMutableList().set(i, (Message) obj);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder builder, Object obj) {
                getMutableMapField(builder).getMutableList().add((Message) obj);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(GeneratedMessage generatedMessage) {
                throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public boolean has(Builder builder) {
                throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(GeneratedMessage generatedMessage) {
                return getMapField(generatedMessage).getList().size();
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(Builder builder) {
                return getMapField(builder).getList().size();
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void clear(Builder builder) {
                getMutableMapField(builder).getMutableList().clear();
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                return this.mapEntryMessageDefaultInstance.newBuilderForType();
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("Nested builder not supported for map fields.");
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getRepeatedBuilder(Builder builder, int i) {
                throw new UnsupportedOperationException("Nested builder not supported for map fields.");
            }
        }

        private static final class SingularEnumFieldAccessor extends SingularFieldAccessor {
            private Descriptors.EnumDescriptor enumDescriptor;
            private java.lang.reflect.Method getValueDescriptorMethod;
            private java.lang.reflect.Method getValueMethod;
            private java.lang.reflect.Method getValueMethodBuilder;
            private java.lang.reflect.Method setValueMethod;
            private boolean supportUnknownEnumValue;
            private java.lang.reflect.Method valueOfMethod;

            SingularEnumFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2, String str2) {
                super(fieldDescriptor, str, cls, cls2, str2);
                this.enumDescriptor = fieldDescriptor.getEnumType();
                this.valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, CoreConstants.VALUE_OF, Descriptors.EnumValueDescriptor.class);
                this.getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
                boolean zSupportsUnknownEnumValue = fieldDescriptor.getFile().supportsUnknownEnumValue();
                this.supportUnknownEnumValue = zSupportsUnknownEnumValue;
                if (zSupportsUnknownEnumValue) {
                    this.getValueMethod = GeneratedMessage.getMethodOrDie(cls, CallMethod.METHOD_GET + str + "Value", new Class[0]);
                    this.getValueMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str + "Value", new Class[0]);
                    this.setValueMethod = GeneratedMessage.getMethodOrDie(cls2, "set" + str + "Value", Integer.TYPE);
                }
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessage generatedMessage) {
                if (this.supportUnknownEnumValue) {
                    return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessage.invokeOrDie(this.getValueMethod, generatedMessage, new Object[0])).intValue());
                }
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(generatedMessage), new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(Builder builder) {
                if (this.supportUnknownEnumValue) {
                    return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessage.invokeOrDie(this.getValueMethodBuilder, builder, new Object[0])).intValue());
                }
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(builder), new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                if (this.supportUnknownEnumValue) {
                    GeneratedMessage.invokeOrDie(this.setValueMethod, builder, Integer.valueOf(((Descriptors.EnumValueDescriptor) obj).getNumber()));
                } else {
                    super.set(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, obj));
                }
            }
        }

        private static final class RepeatedEnumFieldAccessor extends RepeatedFieldAccessor {
            private java.lang.reflect.Method addRepeatedValueMethod;
            private Descriptors.EnumDescriptor enumDescriptor;
            private java.lang.reflect.Method getRepeatedValueMethod;
            private java.lang.reflect.Method getRepeatedValueMethodBuilder;
            private final java.lang.reflect.Method getValueDescriptorMethod;
            private java.lang.reflect.Method setRepeatedValueMethod;
            private boolean supportUnknownEnumValue;
            private final java.lang.reflect.Method valueOfMethod;

            RepeatedEnumFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
                super(fieldDescriptor, str, cls, cls2);
                this.enumDescriptor = fieldDescriptor.getEnumType();
                this.valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, CoreConstants.VALUE_OF, Descriptors.EnumValueDescriptor.class);
                this.getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
                boolean zSupportsUnknownEnumValue = fieldDescriptor.getFile().supportsUnknownEnumValue();
                this.supportUnknownEnumValue = zSupportsUnknownEnumValue;
                if (zSupportsUnknownEnumValue) {
                    this.getRepeatedValueMethod = GeneratedMessage.getMethodOrDie(cls, CallMethod.METHOD_GET + str + "Value", Integer.TYPE);
                    this.getRepeatedValueMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str + "Value", Integer.TYPE);
                    this.setRepeatedValueMethod = GeneratedMessage.getMethodOrDie(cls2, "set" + str + "Value", Integer.TYPE, Integer.TYPE);
                    this.addRepeatedValueMethod = GeneratedMessage.getMethodOrDie(cls2, "add" + str + "Value", Integer.TYPE);
                }
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessage generatedMessage) {
                ArrayList arrayList = new ArrayList();
                int repeatedCount = getRepeatedCount(generatedMessage);
                for (int i = 0; i < repeatedCount; i++) {
                    arrayList.add(getRepeated(generatedMessage, i));
                }
                return Collections.unmodifiableList(arrayList);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object get(Builder builder) {
                ArrayList arrayList = new ArrayList();
                int repeatedCount = getRepeatedCount(builder);
                for (int i = 0; i < repeatedCount; i++) {
                    arrayList.add(getRepeated(builder, i));
                }
                return Collections.unmodifiableList(arrayList);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessage generatedMessage, int i) {
                if (this.supportUnknownEnumValue) {
                    return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessage.invokeOrDie(this.getRepeatedValueMethod, generatedMessage, Integer.valueOf(i))).intValue());
                }
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(generatedMessage, i), new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder builder, int i) {
                if (this.supportUnknownEnumValue) {
                    return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessage.invokeOrDie(this.getRepeatedValueMethodBuilder, builder, Integer.valueOf(i))).intValue());
                }
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(builder, i), new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder builder, int i, Object obj) {
                if (this.supportUnknownEnumValue) {
                    GeneratedMessage.invokeOrDie(this.setRepeatedValueMethod, builder, Integer.valueOf(i), Integer.valueOf(((Descriptors.EnumValueDescriptor) obj).getNumber()));
                } else {
                    super.setRepeated(builder, i, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, obj));
                }
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder builder, Object obj) {
                if (this.supportUnknownEnumValue) {
                    GeneratedMessage.invokeOrDie(this.addRepeatedValueMethod, builder, Integer.valueOf(((Descriptors.EnumValueDescriptor) obj).getNumber()));
                } else {
                    super.addRepeated(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, obj));
                }
            }
        }

        private static final class SingularStringFieldAccessor extends SingularFieldAccessor {
            private final java.lang.reflect.Method getBytesMethod;
            private final java.lang.reflect.Method getBytesMethodBuilder;
            private final java.lang.reflect.Method setBytesMethodBuilder;

            SingularStringFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2, String str2) {
                super(fieldDescriptor, str, cls, cls2, str2);
                this.getBytesMethod = GeneratedMessage.getMethodOrDie(cls, CallMethod.METHOD_GET + str + "Bytes", new Class[0]);
                this.getBytesMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str + "Bytes", new Class[0]);
                this.setBytesMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, "set" + str + "Bytes", ByteString.class);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRaw(GeneratedMessage generatedMessage) {
                return GeneratedMessage.invokeOrDie(this.getBytesMethod, generatedMessage, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Object getRaw(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getBytesMethodBuilder, builder, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                if (obj instanceof ByteString) {
                    GeneratedMessage.invokeOrDie(this.setBytesMethodBuilder, builder, obj);
                } else {
                    super.set(builder, obj);
                }
            }
        }

        private static final class SingularMessageFieldAccessor extends SingularFieldAccessor {
            private final java.lang.reflect.Method getBuilderMethodBuilder;
            private final java.lang.reflect.Method newBuilderMethod;

            SingularMessageFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2, String str2) {
                super(fieldDescriptor, str, cls, cls2, str2);
                this.newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);
                this.getBuilderMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str + "Builder", new Class[0]);
            }

            private Object coerceType(Object obj) {
                return this.type.isInstance(obj) ? obj : ((Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) obj).buildPartial();
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void set(Builder builder, Object obj) {
                super.set(builder, coerceType(obj));
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                return (Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder builder) {
                return (Message.Builder) GeneratedMessage.invokeOrDie(this.getBuilderMethodBuilder, builder, new Object[0]);
            }
        }

        private static final class RepeatedMessageFieldAccessor extends RepeatedFieldAccessor {
            private final java.lang.reflect.Method getBuilderMethodBuilder;
            private final java.lang.reflect.Method newBuilderMethod;

            RepeatedMessageFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class<? extends GeneratedMessage> cls, Class<? extends Builder> cls2) {
                super(fieldDescriptor, str, cls, cls2);
                this.newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);
                this.getBuilderMethodBuilder = GeneratedMessage.getMethodOrDie(cls2, CallMethod.METHOD_GET + str + "Builder", Integer.TYPE);
            }

            private Object coerceType(Object obj) {
                return this.type.isInstance(obj) ? obj : ((Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) obj).build();
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder builder, int i, Object obj) {
                super.setRepeated(builder, i, coerceType(obj));
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder builder, Object obj) {
                super.addRepeated(builder, coerceType(obj));
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                return (Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessage.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessage.FieldAccessorTable.FieldAccessor
            public Message.Builder getRepeatedBuilder(Builder builder, int i) {
                return (Message.Builder) GeneratedMessage.invokeOrDie(this.getBuilderMethodBuilder, builder, Integer.valueOf(i));
            }
        }
    }

    protected Object writeReplace() throws ObjectStreamException {
        return new GeneratedMessageLite.SerializedForm(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <MessageType extends ExtendableMessage<MessageType>, T> Extension<MessageType, T> checkNotLite(ExtensionLite<MessageType, T> extensionLite) {
        if (extensionLite.isLite()) {
            throw new IllegalArgumentException("Expected non-lite extension.");
        }
        return (Extension) extensionLite;
    }

    protected static int computeStringSize(int i, Object obj) {
        if (obj instanceof String) {
            return CodedOutputStream.computeStringSize(i, (String) obj);
        }
        return CodedOutputStream.computeBytesSize(i, (ByteString) obj);
    }

    protected static int computeStringSizeNoTag(Object obj) {
        if (obj instanceof String) {
            return CodedOutputStream.computeStringSizeNoTag((String) obj);
        }
        return CodedOutputStream.computeBytesSizeNoTag((ByteString) obj);
    }

    protected static void writeString(CodedOutputStream codedOutputStream, int i, Object obj) throws IOException {
        if (obj instanceof String) {
            codedOutputStream.writeString(i, (String) obj);
        } else {
            codedOutputStream.writeBytes(i, (ByteString) obj);
        }
    }

    protected static void writeStringNoTag(CodedOutputStream codedOutputStream, Object obj) throws IOException {
        if (obj instanceof String) {
            codedOutputStream.writeStringNoTag((String) obj);
        } else {
            codedOutputStream.writeBytesNoTag((ByteString) obj);
        }
    }
}
