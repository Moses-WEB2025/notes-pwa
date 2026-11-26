package com.miui.notes.backup;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes4.dex */
public final class SyncRootProtos {

    public interface SyncRootOrBuilder extends MessageLiteOrBuilder {
        AppConfigProtos.AppConfig getConfig();

        NoteProtos.Notes getNote();

        TodoProtos.Todos getTodo();

        TodoAppConfigProtos.AppConfig getTodoconfig();

        boolean hasConfig();

        boolean hasNote();

        boolean hasTodo();

        boolean hasTodoconfig();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private SyncRootProtos() {
    }

    public static final class SyncRoot extends GeneratedMessageLite<SyncRoot, Builder> implements SyncRootOrBuilder {
        public static final int CONFIG_FIELD_NUMBER = 7;
        private static final SyncRoot DEFAULT_INSTANCE = new SyncRoot();
        public static final int NOTE_FIELD_NUMBER = 6;
        private static volatile Parser<SyncRoot> PARSER = null;
        public static final int TODOCONFIG_FIELD_NUMBER = 9;
        public static final int TODO_FIELD_NUMBER = 8;
        private int bitField0_;
        private AppConfigProtos.AppConfig config_;
        private NoteProtos.Notes note_;
        private TodoProtos.Todos todo_;
        private TodoAppConfigProtos.AppConfig todoconfig_;

        private SyncRoot() {
        }

        @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
        public boolean hasNote() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
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

        @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
        public boolean hasConfig() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
        public AppConfigProtos.AppConfig getConfig() {
            AppConfigProtos.AppConfig appConfig = this.config_;
            return appConfig == null ? AppConfigProtos.AppConfig.getDefaultInstance() : appConfig;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setConfig(AppConfigProtos.AppConfig appConfig) {
            appConfig.getClass();
            this.config_ = appConfig;
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setConfig(AppConfigProtos.AppConfig.Builder builder) {
            this.config_ = builder.build();
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeConfig(AppConfigProtos.AppConfig appConfig) {
            appConfig.getClass();
            AppConfigProtos.AppConfig appConfig2 = this.config_;
            if (appConfig2 != null && appConfig2 != AppConfigProtos.AppConfig.getDefaultInstance()) {
                this.config_ = AppConfigProtos.AppConfig.newBuilder(this.config_).mergeFrom(appConfig).buildPartial();
            } else {
                this.config_ = appConfig;
            }
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearConfig() {
            this.config_ = null;
            this.bitField0_ &= -3;
        }

        @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
        public boolean hasTodo() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
        public TodoProtos.Todos getTodo() {
            TodoProtos.Todos todos = this.todo_;
            return todos == null ? TodoProtos.Todos.getDefaultInstance() : todos;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTodo(TodoProtos.Todos todos) {
            todos.getClass();
            this.todo_ = todos;
            this.bitField0_ |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTodo(TodoProtos.Todos.Builder builder) {
            this.todo_ = builder.build();
            this.bitField0_ |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeTodo(TodoProtos.Todos todos) {
            todos.getClass();
            TodoProtos.Todos todos2 = this.todo_;
            if (todos2 != null && todos2 != TodoProtos.Todos.getDefaultInstance()) {
                this.todo_ = TodoProtos.Todos.newBuilder(this.todo_).mergeFrom(todos).buildPartial();
            } else {
                this.todo_ = todos;
            }
            this.bitField0_ |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTodo() {
            this.todo_ = null;
            this.bitField0_ &= -5;
        }

        @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
        public boolean hasTodoconfig() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
        public TodoAppConfigProtos.AppConfig getTodoconfig() {
            TodoAppConfigProtos.AppConfig appConfig = this.todoconfig_;
            return appConfig == null ? TodoAppConfigProtos.AppConfig.getDefaultInstance() : appConfig;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTodoconfig(TodoAppConfigProtos.AppConfig appConfig) {
            appConfig.getClass();
            this.todoconfig_ = appConfig;
            this.bitField0_ |= 8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTodoconfig(TodoAppConfigProtos.AppConfig.Builder builder) {
            this.todoconfig_ = builder.build();
            this.bitField0_ |= 8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeTodoconfig(TodoAppConfigProtos.AppConfig appConfig) {
            appConfig.getClass();
            TodoAppConfigProtos.AppConfig appConfig2 = this.todoconfig_;
            if (appConfig2 != null && appConfig2 != TodoAppConfigProtos.AppConfig.getDefaultInstance()) {
                this.todoconfig_ = TodoAppConfigProtos.AppConfig.newBuilder(this.todoconfig_).mergeFrom(appConfig).buildPartial();
            } else {
                this.todoconfig_ = appConfig;
            }
            this.bitField0_ |= 8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTodoconfig() {
            this.todoconfig_ = null;
            this.bitField0_ &= -9;
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeMessage(6, getNote());
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeMessage(7, getConfig());
            }
            if ((this.bitField0_ & 4) != 0) {
                codedOutputStream.writeMessage(8, getTodo());
            }
            if ((this.bitField0_ & 8) != 0) {
                codedOutputStream.writeMessage(9, getTodoconfig());
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeMessageSize = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeMessageSize(6, getNote()) : 0;
            if ((this.bitField0_ & 2) != 0) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(7, getConfig());
            }
            if ((this.bitField0_ & 4) != 0) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(8, getTodo());
            }
            if ((this.bitField0_ & 8) != 0) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(9, getTodoconfig());
            }
            int serializedSize = iComputeMessageSize + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static SyncRoot parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static SyncRoot parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static SyncRoot parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static SyncRoot parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static SyncRoot parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static SyncRoot parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static SyncRoot parseFrom(InputStream inputStream) throws IOException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static SyncRoot parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SyncRoot parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SyncRoot) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static SyncRoot parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SyncRoot) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SyncRoot parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static SyncRoot parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SyncRoot) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SyncRoot syncRoot) {
            return DEFAULT_INSTANCE.createBuilder(syncRoot);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SyncRoot, Builder> implements SyncRootOrBuilder {
            private Builder() {
                super(SyncRoot.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
            public boolean hasNote() {
                return ((SyncRoot) this.instance).hasNote();
            }

            @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
            public NoteProtos.Notes getNote() {
                return ((SyncRoot) this.instance).getNote();
            }

            public Builder setNote(NoteProtos.Notes notes) {
                copyOnWrite();
                ((SyncRoot) this.instance).setNote(notes);
                return this;
            }

            public Builder setNote(NoteProtos.Notes.Builder builder) {
                copyOnWrite();
                ((SyncRoot) this.instance).setNote(builder);
                return this;
            }

            public Builder mergeNote(NoteProtos.Notes notes) {
                copyOnWrite();
                ((SyncRoot) this.instance).mergeNote(notes);
                return this;
            }

            public Builder clearNote() {
                copyOnWrite();
                ((SyncRoot) this.instance).clearNote();
                return this;
            }

            @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
            public boolean hasConfig() {
                return ((SyncRoot) this.instance).hasConfig();
            }

            @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
            public AppConfigProtos.AppConfig getConfig() {
                return ((SyncRoot) this.instance).getConfig();
            }

            public Builder setConfig(AppConfigProtos.AppConfig appConfig) {
                copyOnWrite();
                ((SyncRoot) this.instance).setConfig(appConfig);
                return this;
            }

            public Builder setConfig(AppConfigProtos.AppConfig.Builder builder) {
                copyOnWrite();
                ((SyncRoot) this.instance).setConfig(builder);
                return this;
            }

            public Builder mergeConfig(AppConfigProtos.AppConfig appConfig) {
                copyOnWrite();
                ((SyncRoot) this.instance).mergeConfig(appConfig);
                return this;
            }

            public Builder clearConfig() {
                copyOnWrite();
                ((SyncRoot) this.instance).clearConfig();
                return this;
            }

            @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
            public boolean hasTodo() {
                return ((SyncRoot) this.instance).hasTodo();
            }

            @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
            public TodoProtos.Todos getTodo() {
                return ((SyncRoot) this.instance).getTodo();
            }

            public Builder setTodo(TodoProtos.Todos todos) {
                copyOnWrite();
                ((SyncRoot) this.instance).setTodo(todos);
                return this;
            }

            public Builder setTodo(TodoProtos.Todos.Builder builder) {
                copyOnWrite();
                ((SyncRoot) this.instance).setTodo(builder);
                return this;
            }

            public Builder mergeTodo(TodoProtos.Todos todos) {
                copyOnWrite();
                ((SyncRoot) this.instance).mergeTodo(todos);
                return this;
            }

            public Builder clearTodo() {
                copyOnWrite();
                ((SyncRoot) this.instance).clearTodo();
                return this;
            }

            @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
            public boolean hasTodoconfig() {
                return ((SyncRoot) this.instance).hasTodoconfig();
            }

            @Override // com.miui.notes.backup.SyncRootProtos.SyncRootOrBuilder
            public TodoAppConfigProtos.AppConfig getTodoconfig() {
                return ((SyncRoot) this.instance).getTodoconfig();
            }

            public Builder setTodoconfig(TodoAppConfigProtos.AppConfig appConfig) {
                copyOnWrite();
                ((SyncRoot) this.instance).setTodoconfig(appConfig);
                return this;
            }

            public Builder setTodoconfig(TodoAppConfigProtos.AppConfig.Builder builder) {
                copyOnWrite();
                ((SyncRoot) this.instance).setTodoconfig(builder);
                return this;
            }

            public Builder mergeTodoconfig(TodoAppConfigProtos.AppConfig appConfig) {
                copyOnWrite();
                ((SyncRoot) this.instance).mergeTodoconfig(appConfig);
                return this;
            }

            public Builder clearTodoconfig() {
                copyOnWrite();
                ((SyncRoot) this.instance).clearTodoconfig();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new SyncRoot();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    SyncRoot syncRoot = (SyncRoot) obj2;
                    this.note_ = (NoteProtos.Notes) visitor.visitMessage(this.note_, syncRoot.note_);
                    this.config_ = (AppConfigProtos.AppConfig) visitor.visitMessage(this.config_, syncRoot.config_);
                    this.todo_ = (TodoProtos.Todos) visitor.visitMessage(this.todo_, syncRoot.todo_);
                    this.todoconfig_ = (TodoAppConfigProtos.AppConfig) visitor.visitMessage(this.todoconfig_, syncRoot.todoconfig_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= syncRoot.bitField0_;
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
                                if (tag == 50) {
                                    NoteProtos.Notes.Builder builder = (this.bitField0_ & 1) != 0 ? this.note_.toBuilder() : null;
                                    NoteProtos.Notes notes = (NoteProtos.Notes) codedInputStream.readMessage(NoteProtos.Notes.parser(), extensionRegistryLite);
                                    this.note_ = notes;
                                    if (builder != null) {
                                        builder.mergeFrom(notes);
                                        this.note_ = builder.buildPartial();
                                    }
                                    this.bitField0_ |= 1;
                                } else if (tag == 58) {
                                    AppConfigProtos.AppConfig.Builder builder2 = (this.bitField0_ & 2) != 0 ? this.config_.toBuilder() : null;
                                    AppConfigProtos.AppConfig appConfig = (AppConfigProtos.AppConfig) codedInputStream.readMessage(AppConfigProtos.AppConfig.parser(), extensionRegistryLite);
                                    this.config_ = appConfig;
                                    if (builder2 != null) {
                                        builder2.mergeFrom(appConfig);
                                        this.config_ = builder2.buildPartial();
                                    }
                                    this.bitField0_ |= 2;
                                } else if (tag == 66) {
                                    TodoProtos.Todos.Builder builder3 = (this.bitField0_ & 4) != 0 ? this.todo_.toBuilder() : null;
                                    TodoProtos.Todos todos = (TodoProtos.Todos) codedInputStream.readMessage(TodoProtos.Todos.parser(), extensionRegistryLite);
                                    this.todo_ = todos;
                                    if (builder3 != null) {
                                        builder3.mergeFrom(todos);
                                        this.todo_ = builder3.buildPartial();
                                    }
                                    this.bitField0_ |= 4;
                                } else if (tag == 74) {
                                    TodoAppConfigProtos.AppConfig.Builder builder4 = (this.bitField0_ & 8) != 0 ? this.todoconfig_.toBuilder() : null;
                                    TodoAppConfigProtos.AppConfig appConfig2 = (TodoAppConfigProtos.AppConfig) codedInputStream.readMessage(TodoAppConfigProtos.AppConfig.parser(), extensionRegistryLite);
                                    this.todoconfig_ = appConfig2;
                                    if (builder4 != null) {
                                        builder4.mergeFrom(appConfig2);
                                        this.todoconfig_ = builder4.buildPartial();
                                    }
                                    this.bitField0_ |= 8;
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
                    Parser<SyncRoot> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (SyncRoot.class) {
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

        public static SyncRoot getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SyncRoot> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.backup.SyncRootProtos$1, reason: invalid class name */
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
}
