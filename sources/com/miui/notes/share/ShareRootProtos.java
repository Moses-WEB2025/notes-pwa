package com.miui.notes.share;

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
public final class ShareRootProtos {

    public interface NoteDataOrBuilder extends MessageLiteOrBuilder {
        String getContent();

        ByteString getContentBytes();

        String getMimeType();

        ByteString getMimeTypeBytes();

        boolean hasContent();

        boolean hasMimeType();
    }

    public interface NoteEntityOrBuilder extends MessageLiteOrBuilder {
        NoteData getData(int i);

        int getDataCount();

        List<NoteData> getDataList();

        boolean getHasAttachment();

        boolean hasHasAttachment();
    }

    public interface NotesOrBuilder extends MessageLiteOrBuilder {
        NoteEntity getNote(int i);

        int getNoteCount();

        List<NoteEntity> getNoteList();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private ShareRootProtos() {
    }

    public static final class Notes extends GeneratedMessageLite<Notes, Builder> implements NotesOrBuilder {
        private static final Notes DEFAULT_INSTANCE = new Notes();
        public static final int NOTE_FIELD_NUMBER = 1;
        private static volatile Parser<Notes> PARSER;
        private Internal.ProtobufList<NoteEntity> note_ = emptyProtobufList();

        private Notes() {
        }

        @Override // com.miui.notes.share.ShareRootProtos.NotesOrBuilder
        public List<NoteEntity> getNoteList() {
            return this.note_;
        }

        public List<? extends NoteEntityOrBuilder> getNoteOrBuilderList() {
            return this.note_;
        }

        @Override // com.miui.notes.share.ShareRootProtos.NotesOrBuilder
        public int getNoteCount() {
            return this.note_.size();
        }

        @Override // com.miui.notes.share.ShareRootProtos.NotesOrBuilder
        public NoteEntity getNote(int i) {
            return this.note_.get(i);
        }

        public NoteEntityOrBuilder getNoteOrBuilder(int i) {
            return this.note_.get(i);
        }

        private void ensureNoteIsMutable() {
            if (this.note_.isModifiable()) {
                return;
            }
            this.note_ = GeneratedMessageLite.mutableCopy(this.note_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNote(int i, NoteEntity noteEntity) {
            noteEntity.getClass();
            ensureNoteIsMutable();
            this.note_.set(i, noteEntity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNote(int i, NoteEntity.Builder builder) {
            ensureNoteIsMutable();
            this.note_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addNote(NoteEntity noteEntity) {
            noteEntity.getClass();
            ensureNoteIsMutable();
            this.note_.add(noteEntity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addNote(int i, NoteEntity noteEntity) {
            noteEntity.getClass();
            ensureNoteIsMutable();
            this.note_.add(i, noteEntity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addNote(NoteEntity.Builder builder) {
            ensureNoteIsMutable();
            this.note_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addNote(int i, NoteEntity.Builder builder) {
            ensureNoteIsMutable();
            this.note_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllNote(Iterable<? extends NoteEntity> iterable) {
            ensureNoteIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.note_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNote() {
            this.note_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeNote(int i) {
            ensureNoteIsMutable();
            this.note_.remove(i);
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            for (int i = 0; i < this.note_.size(); i++) {
                codedOutputStream.writeMessage(1, this.note_.get(i));
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
            for (int i2 = 0; i2 < this.note_.size(); i2++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.note_.get(i2));
            }
            int serializedSize = iComputeMessageSize + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static Notes parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Notes parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static Notes parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Notes parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Notes parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Notes parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Notes parseFrom(InputStream inputStream) throws IOException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Notes parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Notes parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Notes) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Notes parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Notes) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Notes parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Notes parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Notes) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Notes notes) {
            return DEFAULT_INSTANCE.createBuilder(notes);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Notes, Builder> implements NotesOrBuilder {
            private Builder() {
                super(Notes.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.share.ShareRootProtos.NotesOrBuilder
            public List<NoteEntity> getNoteList() {
                return Collections.unmodifiableList(((Notes) this.instance).getNoteList());
            }

            @Override // com.miui.notes.share.ShareRootProtos.NotesOrBuilder
            public int getNoteCount() {
                return ((Notes) this.instance).getNoteCount();
            }

            @Override // com.miui.notes.share.ShareRootProtos.NotesOrBuilder
            public NoteEntity getNote(int i) {
                return ((Notes) this.instance).getNote(i);
            }

            public Builder setNote(int i, NoteEntity noteEntity) {
                copyOnWrite();
                ((Notes) this.instance).setNote(i, noteEntity);
                return this;
            }

            public Builder setNote(int i, NoteEntity.Builder builder) {
                copyOnWrite();
                ((Notes) this.instance).setNote(i, builder);
                return this;
            }

            public Builder addNote(NoteEntity noteEntity) {
                copyOnWrite();
                ((Notes) this.instance).addNote(noteEntity);
                return this;
            }

            public Builder addNote(int i, NoteEntity noteEntity) {
                copyOnWrite();
                ((Notes) this.instance).addNote(i, noteEntity);
                return this;
            }

            public Builder addNote(NoteEntity.Builder builder) {
                copyOnWrite();
                ((Notes) this.instance).addNote(builder);
                return this;
            }

            public Builder addNote(int i, NoteEntity.Builder builder) {
                copyOnWrite();
                ((Notes) this.instance).addNote(i, builder);
                return this;
            }

            public Builder addAllNote(Iterable<? extends NoteEntity> iterable) {
                copyOnWrite();
                ((Notes) this.instance).addAllNote(iterable);
                return this;
            }

            public Builder clearNote() {
                copyOnWrite();
                ((Notes) this.instance).clearNote();
                return this;
            }

            public Builder removeNote(int i) {
                copyOnWrite();
                ((Notes) this.instance).removeNote(i);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new Notes();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    this.note_.makeImmutable();
                    return null;
                case 5:
                    this.note_ = ((GeneratedMessageLite.Visitor) obj).visitList(this.note_, ((Notes) obj2).note_);
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
                                    if (!this.note_.isModifiable()) {
                                        this.note_ = GeneratedMessageLite.mutableCopy(this.note_);
                                    }
                                    this.note_.add((NoteEntity) codedInputStream.readMessage(NoteEntity.parser(), extensionRegistryLite));
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
                    Parser<Notes> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (Notes.class) {
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

        public static Notes getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Notes> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.share.ShareRootProtos$1, reason: invalid class name */
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

    public static final class NoteEntity extends GeneratedMessageLite<NoteEntity, Builder> implements NoteEntityOrBuilder {
        public static final int DATA_FIELD_NUMBER = 2;
        private static final NoteEntity DEFAULT_INSTANCE = new NoteEntity();
        public static final int HASATTACHMENT_FIELD_NUMBER = 1;
        private static volatile Parser<NoteEntity> PARSER;
        private int bitField0_;
        private Internal.ProtobufList<NoteData> data_ = emptyProtobufList();
        private boolean hasAttachment_;

        private NoteEntity() {
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
        public boolean hasHasAttachment() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
        public boolean getHasAttachment() {
            return this.hasAttachment_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setHasAttachment(boolean z) {
            this.bitField0_ |= 1;
            this.hasAttachment_ = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHasAttachment() {
            this.bitField0_ &= -2;
            this.hasAttachment_ = false;
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
        public List<NoteData> getDataList() {
            return this.data_;
        }

        public List<? extends NoteDataOrBuilder> getDataOrBuilderList() {
            return this.data_;
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
        public int getDataCount() {
            return this.data_.size();
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
        public NoteData getData(int i) {
            return this.data_.get(i);
        }

        public NoteDataOrBuilder getDataOrBuilder(int i) {
            return this.data_.get(i);
        }

        private void ensureDataIsMutable() {
            if (this.data_.isModifiable()) {
                return;
            }
            this.data_ = GeneratedMessageLite.mutableCopy(this.data_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData(int i, NoteData noteData) {
            noteData.getClass();
            ensureDataIsMutable();
            this.data_.set(i, noteData);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData(int i, NoteData.Builder builder) {
            ensureDataIsMutable();
            this.data_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addData(NoteData noteData) {
            noteData.getClass();
            ensureDataIsMutable();
            this.data_.add(noteData);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addData(int i, NoteData noteData) {
            noteData.getClass();
            ensureDataIsMutable();
            this.data_.add(i, noteData);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addData(NoteData.Builder builder) {
            ensureDataIsMutable();
            this.data_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addData(int i, NoteData.Builder builder) {
            ensureDataIsMutable();
            this.data_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllData(Iterable<? extends NoteData> iterable) {
            ensureDataIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.data_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearData() {
            this.data_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeData(int i) {
            ensureDataIsMutable();
            this.data_.remove(i);
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeBool(1, this.hasAttachment_);
            }
            for (int i = 0; i < this.data_.size(); i++) {
                codedOutputStream.writeMessage(2, this.data_.get(i));
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeBoolSize = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeBoolSize(1, this.hasAttachment_) : 0;
            for (int i2 = 0; i2 < this.data_.size(); i2++) {
                iComputeBoolSize += CodedOutputStream.computeMessageSize(2, this.data_.get(i2));
            }
            int serializedSize = iComputeBoolSize + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static NoteEntity parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static NoteEntity parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static NoteEntity parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static NoteEntity parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static NoteEntity parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static NoteEntity parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static NoteEntity parseFrom(InputStream inputStream) throws IOException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static NoteEntity parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static NoteEntity parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (NoteEntity) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static NoteEntity parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NoteEntity) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static NoteEntity parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static NoteEntity parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NoteEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(NoteEntity noteEntity) {
            return DEFAULT_INSTANCE.createBuilder(noteEntity);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<NoteEntity, Builder> implements NoteEntityOrBuilder {
            private Builder() {
                super(NoteEntity.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
            public boolean hasHasAttachment() {
                return ((NoteEntity) this.instance).hasHasAttachment();
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
            public boolean getHasAttachment() {
                return ((NoteEntity) this.instance).getHasAttachment();
            }

            public Builder setHasAttachment(boolean z) {
                copyOnWrite();
                ((NoteEntity) this.instance).setHasAttachment(z);
                return this;
            }

            public Builder clearHasAttachment() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearHasAttachment();
                return this;
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
            public List<NoteData> getDataList() {
                return Collections.unmodifiableList(((NoteEntity) this.instance).getDataList());
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
            public int getDataCount() {
                return ((NoteEntity) this.instance).getDataCount();
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteEntityOrBuilder
            public NoteData getData(int i) {
                return ((NoteEntity) this.instance).getData(i);
            }

            public Builder setData(int i, NoteData noteData) {
                copyOnWrite();
                ((NoteEntity) this.instance).setData(i, noteData);
                return this;
            }

            public Builder setData(int i, NoteData.Builder builder) {
                copyOnWrite();
                ((NoteEntity) this.instance).setData(i, builder);
                return this;
            }

            public Builder addData(NoteData noteData) {
                copyOnWrite();
                ((NoteEntity) this.instance).addData(noteData);
                return this;
            }

            public Builder addData(int i, NoteData noteData) {
                copyOnWrite();
                ((NoteEntity) this.instance).addData(i, noteData);
                return this;
            }

            public Builder addData(NoteData.Builder builder) {
                copyOnWrite();
                ((NoteEntity) this.instance).addData(builder);
                return this;
            }

            public Builder addData(int i, NoteData.Builder builder) {
                copyOnWrite();
                ((NoteEntity) this.instance).addData(i, builder);
                return this;
            }

            public Builder addAllData(Iterable<? extends NoteData> iterable) {
                copyOnWrite();
                ((NoteEntity) this.instance).addAllData(iterable);
                return this;
            }

            public Builder clearData() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearData();
                return this;
            }

            public Builder removeData(int i) {
                copyOnWrite();
                ((NoteEntity) this.instance).removeData(i);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new NoteEntity();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    this.data_.makeImmutable();
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    NoteEntity noteEntity = (NoteEntity) obj2;
                    this.hasAttachment_ = visitor.visitBoolean(hasHasAttachment(), this.hasAttachment_, noteEntity.hasHasAttachment(), noteEntity.hasAttachment_);
                    this.data_ = visitor.visitList(this.data_, noteEntity.data_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= noteEntity.bitField0_;
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
                                if (tag == 8) {
                                    this.bitField0_ |= 1;
                                    this.hasAttachment_ = codedInputStream.readBool();
                                } else if (tag == 18) {
                                    if (!this.data_.isModifiable()) {
                                        this.data_ = GeneratedMessageLite.mutableCopy(this.data_);
                                    }
                                    this.data_.add((NoteData) codedInputStream.readMessage(NoteData.parser(), extensionRegistryLite));
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
                    Parser<NoteEntity> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (NoteEntity.class) {
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

        public static NoteEntity getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<NoteEntity> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class NoteData extends GeneratedMessageLite<NoteData, Builder> implements NoteDataOrBuilder {
        public static final int CONTENT_FIELD_NUMBER = 2;
        private static final NoteData DEFAULT_INSTANCE = new NoteData();
        public static final int MIMETYPE_FIELD_NUMBER = 1;
        private static volatile Parser<NoteData> PARSER;
        private int bitField0_;
        private String mimeType_ = "";
        private String content_ = "";

        private NoteData() {
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
        public boolean hasMimeType() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
        public String getMimeType() {
            return this.mimeType_;
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
        public ByteString getMimeTypeBytes() {
            return ByteString.copyFromUtf8(this.mimeType_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMimeType(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.mimeType_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMimeType() {
            this.bitField0_ &= -2;
            this.mimeType_ = getDefaultInstance().getMimeType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMimeTypeBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 1;
            this.mimeType_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
        public boolean hasContent() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
        public String getContent() {
            return this.content_;
        }

        @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
        public ByteString getContentBytes() {
            return ByteString.copyFromUtf8(this.content_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setContent(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.content_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearContent() {
            this.bitField0_ &= -3;
            this.content_ = getDefaultInstance().getContent();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setContentBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 2;
            this.content_ = byteString.toStringUtf8();
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeString(1, getMimeType());
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeString(2, getContent());
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeStringSize = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeStringSize(1, getMimeType()) : 0;
            if ((this.bitField0_ & 2) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(2, getContent());
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static NoteData parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static NoteData parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static NoteData parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static NoteData parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static NoteData parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static NoteData parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static NoteData parseFrom(InputStream inputStream) throws IOException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static NoteData parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static NoteData parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (NoteData) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static NoteData parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NoteData) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static NoteData parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static NoteData parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NoteData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(NoteData noteData) {
            return DEFAULT_INSTANCE.createBuilder(noteData);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<NoteData, Builder> implements NoteDataOrBuilder {
            private Builder() {
                super(NoteData.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
            public boolean hasMimeType() {
                return ((NoteData) this.instance).hasMimeType();
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
            public String getMimeType() {
                return ((NoteData) this.instance).getMimeType();
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
            public ByteString getMimeTypeBytes() {
                return ((NoteData) this.instance).getMimeTypeBytes();
            }

            public Builder setMimeType(String str) {
                copyOnWrite();
                ((NoteData) this.instance).setMimeType(str);
                return this;
            }

            public Builder clearMimeType() {
                copyOnWrite();
                ((NoteData) this.instance).clearMimeType();
                return this;
            }

            public Builder setMimeTypeBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteData) this.instance).setMimeTypeBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
            public boolean hasContent() {
                return ((NoteData) this.instance).hasContent();
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
            public String getContent() {
                return ((NoteData) this.instance).getContent();
            }

            @Override // com.miui.notes.share.ShareRootProtos.NoteDataOrBuilder
            public ByteString getContentBytes() {
                return ((NoteData) this.instance).getContentBytes();
            }

            public Builder setContent(String str) {
                copyOnWrite();
                ((NoteData) this.instance).setContent(str);
                return this;
            }

            public Builder clearContent() {
                copyOnWrite();
                ((NoteData) this.instance).clearContent();
                return this;
            }

            public Builder setContentBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteData) this.instance).setContentBytes(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new NoteData();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    NoteData noteData = (NoteData) obj2;
                    this.mimeType_ = visitor.visitString(hasMimeType(), this.mimeType_, noteData.hasMimeType(), noteData.mimeType_);
                    this.content_ = visitor.visitString(hasContent(), this.content_, noteData.hasContent(), noteData.content_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= noteData.bitField0_;
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
                                    this.mimeType_ = string;
                                } else if (tag == 18) {
                                    String string2 = codedInputStream.readString();
                                    this.bitField0_ |= 2;
                                    this.content_ = string2;
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
                    Parser<NoteData> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (NoteData.class) {
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

        public static NoteData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<NoteData> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
