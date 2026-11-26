package com.miui.notes.backup;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public final class SecretSyncRootProtos {

    public interface SecretFileInfoOrBuilder extends MessageLiteOrBuilder {
        ByteString getFiledata();

        String getFilename();

        ByteString getFilenameBytes();

        boolean hasFiledata();

        boolean hasFilename();
    }

    public interface SecretNoteOrBuilder extends MessageLiteOrBuilder {
        NoteProtos.Notes getNote();

        boolean hasNote();
    }

    public interface TaskOrBuilder extends MessageLiteOrBuilder {
        SecretFileInfo getSecretFileInfo(int i);

        int getSecretFileInfoCount();

        List<SecretFileInfo> getSecretFileInfoList();

        SecretNote getSecretNote(int i);

        int getSecretNoteCount();

        List<SecretNote> getSecretNoteList();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private SecretSyncRootProtos() {
    }

    public static final class Task extends GeneratedMessageLite<Task, Builder> implements TaskOrBuilder {
        private static final Task DEFAULT_INSTANCE = new Task();
        private static volatile Parser<Task> PARSER = null;
        public static final int SECRETFILEINFO_FIELD_NUMBER = 2;
        public static final int SECRETNOTE_FIELD_NUMBER = 1;
        private Internal.ProtobufList<SecretNote> secretNote_ = emptyProtobufList();
        private Internal.ProtobufList<SecretFileInfo> secretFileInfo_ = emptyProtobufList();

        private Task() {
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
        public List<SecretNote> getSecretNoteList() {
            return this.secretNote_;
        }

        public List<? extends SecretNoteOrBuilder> getSecretNoteOrBuilderList() {
            return this.secretNote_;
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
        public int getSecretNoteCount() {
            return this.secretNote_.size();
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
        public SecretNote getSecretNote(int i) {
            return this.secretNote_.get(i);
        }

        public SecretNoteOrBuilder getSecretNoteOrBuilder(int i) {
            return this.secretNote_.get(i);
        }

        private void ensureSecretNoteIsMutable() {
            if (this.secretNote_.isModifiable()) {
                return;
            }
            this.secretNote_ = GeneratedMessageLite.mutableCopy(this.secretNote_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSecretNote(int i, SecretNote secretNote) {
            secretNote.getClass();
            ensureSecretNoteIsMutable();
            this.secretNote_.set(i, secretNote);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSecretNote(int i, SecretNote.Builder builder) {
            ensureSecretNoteIsMutable();
            this.secretNote_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSecretNote(SecretNote secretNote) {
            secretNote.getClass();
            ensureSecretNoteIsMutable();
            this.secretNote_.add(secretNote);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSecretNote(int i, SecretNote secretNote) {
            secretNote.getClass();
            ensureSecretNoteIsMutable();
            this.secretNote_.add(i, secretNote);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSecretNote(SecretNote.Builder builder) {
            ensureSecretNoteIsMutable();
            this.secretNote_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSecretNote(int i, SecretNote.Builder builder) {
            ensureSecretNoteIsMutable();
            this.secretNote_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllSecretNote(Iterable<? extends SecretNote> iterable) {
            ensureSecretNoteIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.secretNote_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSecretNote() {
            this.secretNote_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeSecretNote(int i) {
            ensureSecretNoteIsMutable();
            this.secretNote_.remove(i);
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
        public List<SecretFileInfo> getSecretFileInfoList() {
            return this.secretFileInfo_;
        }

        public List<? extends SecretFileInfoOrBuilder> getSecretFileInfoOrBuilderList() {
            return this.secretFileInfo_;
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
        public int getSecretFileInfoCount() {
            return this.secretFileInfo_.size();
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
        public SecretFileInfo getSecretFileInfo(int i) {
            return this.secretFileInfo_.get(i);
        }

        public SecretFileInfoOrBuilder getSecretFileInfoOrBuilder(int i) {
            return this.secretFileInfo_.get(i);
        }

        private void ensureSecretFileInfoIsMutable() {
            if (this.secretFileInfo_.isModifiable()) {
                return;
            }
            this.secretFileInfo_ = GeneratedMessageLite.mutableCopy(this.secretFileInfo_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSecretFileInfo(int i, SecretFileInfo secretFileInfo) {
            secretFileInfo.getClass();
            ensureSecretFileInfoIsMutable();
            this.secretFileInfo_.set(i, secretFileInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSecretFileInfo(int i, SecretFileInfo.Builder builder) {
            ensureSecretFileInfoIsMutable();
            this.secretFileInfo_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSecretFileInfo(SecretFileInfo secretFileInfo) {
            secretFileInfo.getClass();
            ensureSecretFileInfoIsMutable();
            this.secretFileInfo_.add(secretFileInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSecretFileInfo(int i, SecretFileInfo secretFileInfo) {
            secretFileInfo.getClass();
            ensureSecretFileInfoIsMutable();
            this.secretFileInfo_.add(i, secretFileInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSecretFileInfo(SecretFileInfo.Builder builder) {
            ensureSecretFileInfoIsMutable();
            this.secretFileInfo_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSecretFileInfo(int i, SecretFileInfo.Builder builder) {
            ensureSecretFileInfoIsMutable();
            this.secretFileInfo_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllSecretFileInfo(Iterable<? extends SecretFileInfo> iterable) {
            ensureSecretFileInfoIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.secretFileInfo_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSecretFileInfo() {
            this.secretFileInfo_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeSecretFileInfo(int i) {
            ensureSecretFileInfoIsMutable();
            this.secretFileInfo_.remove(i);
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            for (int i = 0; i < this.secretNote_.size(); i++) {
                codedOutputStream.writeMessage(1, this.secretNote_.get(i));
            }
            for (int i2 = 0; i2 < this.secretFileInfo_.size(); i2++) {
                codedOutputStream.writeMessage(2, this.secretFileInfo_.get(i2));
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeMessageSize = 0;
            for (int i2 = 0; i2 < this.secretNote_.size(); i2++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.secretNote_.get(i2));
            }
            for (int i3 = 0; i3 < this.secretFileInfo_.size(); i3++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(2, this.secretFileInfo_.get(i3));
            }
            int serializedSize = iComputeMessageSize + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static Task parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Task parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static Task parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Task parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Task parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Task parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Task parseFrom(InputStream inputStream) throws IOException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Task parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Task parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Task) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Task parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Task) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Task parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Task parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Task) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Task task) {
            return DEFAULT_INSTANCE.createBuilder(task);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Task, Builder> implements TaskOrBuilder {
            private Builder() {
                super(Task.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
            public List<SecretNote> getSecretNoteList() {
                return Collections.unmodifiableList(((Task) this.instance).getSecretNoteList());
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
            public int getSecretNoteCount() {
                return ((Task) this.instance).getSecretNoteCount();
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
            public SecretNote getSecretNote(int i) {
                return ((Task) this.instance).getSecretNote(i);
            }

            public Builder setSecretNote(int i, SecretNote secretNote) {
                copyOnWrite();
                ((Task) this.instance).setSecretNote(i, secretNote);
                return this;
            }

            public Builder setSecretNote(int i, SecretNote.Builder builder) {
                copyOnWrite();
                ((Task) this.instance).setSecretNote(i, builder);
                return this;
            }

            public Builder addSecretNote(SecretNote secretNote) {
                copyOnWrite();
                ((Task) this.instance).addSecretNote(secretNote);
                return this;
            }

            public Builder addSecretNote(int i, SecretNote secretNote) {
                copyOnWrite();
                ((Task) this.instance).addSecretNote(i, secretNote);
                return this;
            }

            public Builder addSecretNote(SecretNote.Builder builder) {
                copyOnWrite();
                ((Task) this.instance).addSecretNote(builder);
                return this;
            }

            public Builder addSecretNote(int i, SecretNote.Builder builder) {
                copyOnWrite();
                ((Task) this.instance).addSecretNote(i, builder);
                return this;
            }

            public Builder addAllSecretNote(Iterable<? extends SecretNote> iterable) {
                copyOnWrite();
                ((Task) this.instance).addAllSecretNote(iterable);
                return this;
            }

            public Builder clearSecretNote() {
                copyOnWrite();
                ((Task) this.instance).clearSecretNote();
                return this;
            }

            public Builder removeSecretNote(int i) {
                copyOnWrite();
                ((Task) this.instance).removeSecretNote(i);
                return this;
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
            public List<SecretFileInfo> getSecretFileInfoList() {
                return Collections.unmodifiableList(((Task) this.instance).getSecretFileInfoList());
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
            public int getSecretFileInfoCount() {
                return ((Task) this.instance).getSecretFileInfoCount();
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.TaskOrBuilder
            public SecretFileInfo getSecretFileInfo(int i) {
                return ((Task) this.instance).getSecretFileInfo(i);
            }

            public Builder setSecretFileInfo(int i, SecretFileInfo secretFileInfo) {
                copyOnWrite();
                ((Task) this.instance).setSecretFileInfo(i, secretFileInfo);
                return this;
            }

            public Builder setSecretFileInfo(int i, SecretFileInfo.Builder builder) {
                copyOnWrite();
                ((Task) this.instance).setSecretFileInfo(i, builder);
                return this;
            }

            public Builder addSecretFileInfo(SecretFileInfo secretFileInfo) {
                copyOnWrite();
                ((Task) this.instance).addSecretFileInfo(secretFileInfo);
                return this;
            }

            public Builder addSecretFileInfo(int i, SecretFileInfo secretFileInfo) {
                copyOnWrite();
                ((Task) this.instance).addSecretFileInfo(i, secretFileInfo);
                return this;
            }

            public Builder addSecretFileInfo(SecretFileInfo.Builder builder) {
                copyOnWrite();
                ((Task) this.instance).addSecretFileInfo(builder);
                return this;
            }

            public Builder addSecretFileInfo(int i, SecretFileInfo.Builder builder) {
                copyOnWrite();
                ((Task) this.instance).addSecretFileInfo(i, builder);
                return this;
            }

            public Builder addAllSecretFileInfo(Iterable<? extends SecretFileInfo> iterable) {
                copyOnWrite();
                ((Task) this.instance).addAllSecretFileInfo(iterable);
                return this;
            }

            public Builder clearSecretFileInfo() {
                copyOnWrite();
                ((Task) this.instance).clearSecretFileInfo();
                return this;
            }

            public Builder removeSecretFileInfo(int i) {
                copyOnWrite();
                ((Task) this.instance).removeSecretFileInfo(i);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new Task();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    this.secretNote_.makeImmutable();
                    this.secretFileInfo_.makeImmutable();
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    Task task = (Task) obj2;
                    this.secretNote_ = visitor.visitList(this.secretNote_, task.secretNote_);
                    this.secretFileInfo_ = visitor.visitList(this.secretFileInfo_, task.secretFileInfo_);
                    GeneratedMessageLite.MergeFromVisitor mergeFromVisitor = GeneratedMessageLite.MergeFromVisitor.INSTANCE;
                    return this;
                case 6:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    extensionRegistryLite.getClass();
                    boolean z = false;
                    while (!z) {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 10) {
                                    if (!this.secretNote_.isModifiable()) {
                                        this.secretNote_ = GeneratedMessageLite.mutableCopy(this.secretNote_);
                                    }
                                    this.secretNote_.add((SecretNote) codedInputStream.readMessage(SecretNote.parser(), extensionRegistryLite));
                                } else if (tag == 18) {
                                    if (!this.secretFileInfo_.isModifiable()) {
                                        this.secretFileInfo_ = GeneratedMessageLite.mutableCopy(this.secretFileInfo_);
                                    }
                                    this.secretFileInfo_.add((SecretFileInfo) codedInputStream.readMessage(SecretFileInfo.parser(), extensionRegistryLite));
                                } else if (!parseUnknownField(tag, codedInputStream)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case 7:
                    break;
                case 8:
                    Parser<Task> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (Task.class) {
                        defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = defaultInstanceBasedParser;
                        }
                        break;
                    }
                    return defaultInstanceBasedParser;
                case 9:
                    return (byte) 1;
                case 10:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        public static Task getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Task> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.backup.SecretSyncRootProtos$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.IS_INITIALIZED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.MAKE_IMMUTABLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.VISIT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.MERGE_FROM_STREAM.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public static final class SecretNote extends GeneratedMessageLite<SecretNote, Builder> implements SecretNoteOrBuilder {
        private static final SecretNote DEFAULT_INSTANCE = new SecretNote();
        public static final int NOTE_FIELD_NUMBER = 1;
        private static volatile Parser<SecretNote> PARSER;
        private int bitField0_;
        private NoteProtos.Notes note_;

        private SecretNote() {
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretNoteOrBuilder
        public boolean hasNote() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretNoteOrBuilder
        public NoteProtos.Notes getNote() {
            NoteProtos.Notes notes = this.note_;
            return notes == null ? NoteProtos.Notes.getDefaultInstance() : notes;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNote(NoteProtos.Notes notes) {
            notes.getClass();
            this.note_ = notes;
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNote(NoteProtos.Notes.Builder builder) {
            this.note_ = builder.build();
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeNote(NoteProtos.Notes notes) {
            notes.getClass();
            NoteProtos.Notes notes2 = this.note_;
            if (notes2 != null && notes2 != NoteProtos.Notes.getDefaultInstance()) {
                this.note_ = NoteProtos.Notes.newBuilder(this.note_).mergeFrom(notes).buildPartial();
            } else {
                this.note_ = notes;
            }
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNote() {
            this.note_ = null;
            this.bitField0_ &= -2;
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeMessage(1, getNote());
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeMessageSize = ((this.bitField0_ & 1) != 0 ? CodedOutputStream.computeMessageSize(1, getNote()) : 0) + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = iComputeMessageSize;
            return iComputeMessageSize;
        }

        public static SecretNote parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static SecretNote parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static SecretNote parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static SecretNote parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static SecretNote parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static SecretNote parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static SecretNote parseFrom(InputStream inputStream) throws IOException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static SecretNote parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SecretNote parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SecretNote) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static SecretNote parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SecretNote) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SecretNote parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static SecretNote parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SecretNote) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SecretNote secretNote) {
            return DEFAULT_INSTANCE.createBuilder(secretNote);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SecretNote, Builder> implements SecretNoteOrBuilder {
            private Builder() {
                super(SecretNote.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretNoteOrBuilder
            public boolean hasNote() {
                return ((SecretNote) this.instance).hasNote();
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretNoteOrBuilder
            public NoteProtos.Notes getNote() {
                return ((SecretNote) this.instance).getNote();
            }

            public Builder setNote(NoteProtos.Notes notes) {
                copyOnWrite();
                ((SecretNote) this.instance).setNote(notes);
                return this;
            }

            public Builder setNote(NoteProtos.Notes.Builder builder) {
                copyOnWrite();
                ((SecretNote) this.instance).setNote(builder);
                return this;
            }

            public Builder mergeNote(NoteProtos.Notes notes) {
                copyOnWrite();
                ((SecretNote) this.instance).mergeNote(notes);
                return this;
            }

            public Builder clearNote() {
                copyOnWrite();
                ((SecretNote) this.instance).clearNote();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new SecretNote();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    SecretNote secretNote = (SecretNote) obj2;
                    this.note_ = (NoteProtos.Notes) visitor.visitMessage(this.note_, secretNote.note_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= secretNote.bitField0_;
                    }
                    return this;
                case 6:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ExtensionRegistryLite extensionRegistryLite = (ExtensionRegistryLite) obj2;
                    extensionRegistryLite.getClass();
                    boolean z = false;
                    while (!z) {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 10) {
                                    NoteProtos.Notes.Builder builder = (this.bitField0_ & 1) != 0 ? this.note_.toBuilder() : null;
                                    NoteProtos.Notes notes = (NoteProtos.Notes) codedInputStream.readMessage(NoteProtos.Notes.parser(), extensionRegistryLite);
                                    this.note_ = notes;
                                    if (builder != null) {
                                        builder.mergeFrom(notes);
                                        this.note_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 1;
                                } else if (!parseUnknownField(tag, codedInputStream)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case 7:
                    break;
                case 8:
                    Parser<SecretNote> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (SecretNote.class) {
                        defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = defaultInstanceBasedParser;
                        }
                        break;
                    }
                    return defaultInstanceBasedParser;
                case 9:
                    return (byte) 1;
                case 10:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        public static SecretNote getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SecretNote> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class SecretFileInfo extends GeneratedMessageLite<SecretFileInfo, Builder> implements SecretFileInfoOrBuilder {
        private static final SecretFileInfo DEFAULT_INSTANCE = new SecretFileInfo();
        public static final int FILEDATA_FIELD_NUMBER = 2;
        public static final int FILENAME_FIELD_NUMBER = 1;
        private static volatile Parser<SecretFileInfo> PARSER;
        private int bitField0_;
        private String filename_ = "";
        private ByteString filedata_ = ByteString.EMPTY;

        private SecretFileInfo() {
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
        public boolean hasFilename() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
        public String getFilename() {
            return this.filename_;
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
        public ByteString getFilenameBytes() {
            return ByteString.copyFromUtf8(this.filename_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFilename(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.filename_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFilename() {
            this.bitField0_ &= -2;
            this.filename_ = getDefaultInstance().getFilename();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFilenameBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 1;
            this.filename_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
        public boolean hasFiledata() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
        public ByteString getFiledata() {
            return this.filedata_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFiledata(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 2;
            this.filedata_ = byteString;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFiledata() {
            this.bitField0_ &= -3;
            this.filedata_ = getDefaultInstance().getFiledata();
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeString(1, getFilename());
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeBytes(2, this.filedata_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeStringSize = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeStringSize(1, getFilename()) : 0;
            if ((this.bitField0_ & 2) != 0) {
                iComputeStringSize += CodedOutputStream.computeBytesSize(2, this.filedata_);
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static SecretFileInfo parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static SecretFileInfo parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static SecretFileInfo parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static SecretFileInfo parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static SecretFileInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static SecretFileInfo parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static SecretFileInfo parseFrom(InputStream inputStream) throws IOException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static SecretFileInfo parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SecretFileInfo parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SecretFileInfo) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static SecretFileInfo parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SecretFileInfo) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SecretFileInfo parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static SecretFileInfo parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SecretFileInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SecretFileInfo secretFileInfo) {
            return DEFAULT_INSTANCE.createBuilder(secretFileInfo);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SecretFileInfo, Builder> implements SecretFileInfoOrBuilder {
            private Builder() {
                super(SecretFileInfo.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
            public boolean hasFilename() {
                return ((SecretFileInfo) this.instance).hasFilename();
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
            public String getFilename() {
                return ((SecretFileInfo) this.instance).getFilename();
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
            public ByteString getFilenameBytes() {
                return ((SecretFileInfo) this.instance).getFilenameBytes();
            }

            public Builder setFilename(String str) {
                copyOnWrite();
                ((SecretFileInfo) this.instance).setFilename(str);
                return this;
            }

            public Builder clearFilename() {
                copyOnWrite();
                ((SecretFileInfo) this.instance).clearFilename();
                return this;
            }

            public Builder setFilenameBytes(ByteString byteString) {
                copyOnWrite();
                ((SecretFileInfo) this.instance).setFilenameBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
            public boolean hasFiledata() {
                return ((SecretFileInfo) this.instance).hasFiledata();
            }

            @Override // com.miui.notes.backup.SecretSyncRootProtos.SecretFileInfoOrBuilder
            public ByteString getFiledata() {
                return ((SecretFileInfo) this.instance).getFiledata();
            }

            public Builder setFiledata(ByteString byteString) {
                copyOnWrite();
                ((SecretFileInfo) this.instance).setFiledata(byteString);
                return this;
            }

            public Builder clearFiledata() {
                copyOnWrite();
                ((SecretFileInfo) this.instance).clearFiledata();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new SecretFileInfo();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    SecretFileInfo secretFileInfo = (SecretFileInfo) obj2;
                    this.filename_ = visitor.visitString(hasFilename(), this.filename_, secretFileInfo.hasFilename(), secretFileInfo.filename_);
                    this.filedata_ = visitor.visitByteString(hasFiledata(), this.filedata_, secretFileInfo.hasFiledata(), secretFileInfo.filedata_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= secretFileInfo.bitField0_;
                    }
                    return this;
                case 6:
                    CodedInputStream codedInputStream = (CodedInputStream) obj;
                    ((ExtensionRegistryLite) obj2).getClass();
                    boolean z = false;
                    while (!z) {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag == 10) {
                                    String string = codedInputStream.readString();
                                    this.bitField0_ |= 1;
                                    this.filename_ = string;
                                } else if (tag == 18) {
                                    this.bitField0_ |= 2;
                                    this.filedata_ = codedInputStream.readBytes();
                                } else if (!parseUnknownField(tag, codedInputStream)) {
                                }
                            }
                            z = true;
                        } catch (InvalidProtocolBufferException e) {
                            throw new RuntimeException(e.setUnfinishedMessage(this));
                        } catch (IOException e2) {
                            throw new RuntimeException(new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this));
                        }
                    }
                    break;
                case 7:
                    break;
                case 8:
                    Parser<SecretFileInfo> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (SecretFileInfo.class) {
                        defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = defaultInstanceBasedParser;
                        }
                        break;
                    }
                    return defaultInstanceBasedParser;
                case 9:
                    return (byte) 1;
                case 10:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
            return DEFAULT_INSTANCE;
        }

        public static SecretFileInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SecretFileInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
