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
public final class TodoAppConfigProtos {

    public interface AppConfigOrBuilder extends MessageLiteOrBuilder {
        String getAlertMode();

        ByteString getAlertModeBytes();

        String getEnableFloatMode();

        ByteString getEnableFloatModeBytes();

        String getFinishShowMode();

        ByteString getFinishShowModeBytes();

        int getSortMode();

        boolean hasAlertMode();

        boolean hasEnableFloatMode();

        boolean hasFinishShowMode();

        boolean hasSortMode();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private TodoAppConfigProtos() {
    }

    public static final class AppConfig extends GeneratedMessageLite<AppConfig, Builder> implements AppConfigOrBuilder {
        public static final int ALERTMODE_FIELD_NUMBER = 3;
        private static final AppConfig DEFAULT_INSTANCE = new AppConfig();
        public static final int ENABLEFLOATMODE_FIELD_NUMBER = 1;
        public static final int FINISHSHOWMODE_FIELD_NUMBER = 2;
        private static volatile Parser<AppConfig> PARSER = null;
        public static final int SORTMODE_FIELD_NUMBER = 4;
        private int bitField0_;
        private int sortMode_;
        private String enableFloatMode_ = "";
        private String finishShowMode_ = "";
        private String alertMode_ = "";

        private AppConfig() {
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public boolean hasEnableFloatMode() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public String getEnableFloatMode() {
            return this.enableFloatMode_;
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public ByteString getEnableFloatModeBytes() {
            return ByteString.copyFromUtf8(this.enableFloatMode_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEnableFloatMode(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.enableFloatMode_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearEnableFloatMode() {
            this.bitField0_ &= -2;
            this.enableFloatMode_ = getDefaultInstance().getEnableFloatMode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEnableFloatModeBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 1;
            this.enableFloatMode_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public boolean hasFinishShowMode() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public String getFinishShowMode() {
            return this.finishShowMode_;
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public ByteString getFinishShowModeBytes() {
            return ByteString.copyFromUtf8(this.finishShowMode_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFinishShowMode(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.finishShowMode_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFinishShowMode() {
            this.bitField0_ &= -3;
            this.finishShowMode_ = getDefaultInstance().getFinishShowMode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFinishShowModeBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 2;
            this.finishShowMode_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public boolean hasAlertMode() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public String getAlertMode() {
            return this.alertMode_;
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public ByteString getAlertModeBytes() {
            return ByteString.copyFromUtf8(this.alertMode_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAlertMode(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.alertMode_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAlertMode() {
            this.bitField0_ &= -5;
            this.alertMode_ = getDefaultInstance().getAlertMode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAlertModeBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 4;
            this.alertMode_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public boolean hasSortMode() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
        public int getSortMode() {
            return this.sortMode_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSortMode(int i) {
            this.bitField0_ |= 8;
            this.sortMode_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSortMode() {
            this.bitField0_ &= -9;
            this.sortMode_ = 0;
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeString(1, getEnableFloatMode());
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeString(2, getFinishShowMode());
            }
            if ((this.bitField0_ & 4) != 0) {
                codedOutputStream.writeString(3, getAlertMode());
            }
            if ((this.bitField0_ & 8) != 0) {
                codedOutputStream.writeInt32(4, this.sortMode_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeStringSize = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeStringSize(1, getEnableFloatMode()) : 0;
            if ((this.bitField0_ & 2) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(2, getFinishShowMode());
            }
            if ((this.bitField0_ & 4) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(3, getAlertMode());
            }
            if ((this.bitField0_ & 8) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(4, this.sortMode_);
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static AppConfig parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static AppConfig parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static AppConfig parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static AppConfig parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static AppConfig parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static AppConfig parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static AppConfig parseFrom(InputStream inputStream) throws IOException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static AppConfig parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AppConfig parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AppConfig) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static AppConfig parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AppConfig) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AppConfig parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static AppConfig parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AppConfig) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AppConfig appConfig) {
            return DEFAULT_INSTANCE.createBuilder(appConfig);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<AppConfig, Builder> implements AppConfigOrBuilder {
            private Builder() {
                super(AppConfig.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public boolean hasEnableFloatMode() {
                return ((AppConfig) this.instance).hasEnableFloatMode();
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public String getEnableFloatMode() {
                return ((AppConfig) this.instance).getEnableFloatMode();
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public ByteString getEnableFloatModeBytes() {
                return ((AppConfig) this.instance).getEnableFloatModeBytes();
            }

            public Builder setEnableFloatMode(String str) {
                copyOnWrite();
                ((AppConfig) this.instance).setEnableFloatMode(str);
                return this;
            }

            public Builder clearEnableFloatMode() {
                copyOnWrite();
                ((AppConfig) this.instance).clearEnableFloatMode();
                return this;
            }

            public Builder setEnableFloatModeBytes(ByteString byteString) {
                copyOnWrite();
                ((AppConfig) this.instance).setEnableFloatModeBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public boolean hasFinishShowMode() {
                return ((AppConfig) this.instance).hasFinishShowMode();
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public String getFinishShowMode() {
                return ((AppConfig) this.instance).getFinishShowMode();
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public ByteString getFinishShowModeBytes() {
                return ((AppConfig) this.instance).getFinishShowModeBytes();
            }

            public Builder setFinishShowMode(String str) {
                copyOnWrite();
                ((AppConfig) this.instance).setFinishShowMode(str);
                return this;
            }

            public Builder clearFinishShowMode() {
                copyOnWrite();
                ((AppConfig) this.instance).clearFinishShowMode();
                return this;
            }

            public Builder setFinishShowModeBytes(ByteString byteString) {
                copyOnWrite();
                ((AppConfig) this.instance).setFinishShowModeBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public boolean hasAlertMode() {
                return ((AppConfig) this.instance).hasAlertMode();
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public String getAlertMode() {
                return ((AppConfig) this.instance).getAlertMode();
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public ByteString getAlertModeBytes() {
                return ((AppConfig) this.instance).getAlertModeBytes();
            }

            public Builder setAlertMode(String str) {
                copyOnWrite();
                ((AppConfig) this.instance).setAlertMode(str);
                return this;
            }

            public Builder clearAlertMode() {
                copyOnWrite();
                ((AppConfig) this.instance).clearAlertMode();
                return this;
            }

            public Builder setAlertModeBytes(ByteString byteString) {
                copyOnWrite();
                ((AppConfig) this.instance).setAlertModeBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public boolean hasSortMode() {
                return ((AppConfig) this.instance).hasSortMode();
            }

            @Override // com.miui.notes.backup.TodoAppConfigProtos.AppConfigOrBuilder
            public int getSortMode() {
                return ((AppConfig) this.instance).getSortMode();
            }

            public Builder setSortMode(int i) {
                copyOnWrite();
                ((AppConfig) this.instance).setSortMode(i);
                return this;
            }

            public Builder clearSortMode() {
                copyOnWrite();
                ((AppConfig) this.instance).clearSortMode();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new AppConfig();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    AppConfig appConfig = (AppConfig) obj2;
                    this.enableFloatMode_ = visitor.visitString(hasEnableFloatMode(), this.enableFloatMode_, appConfig.hasEnableFloatMode(), appConfig.enableFloatMode_);
                    this.finishShowMode_ = visitor.visitString(hasFinishShowMode(), this.finishShowMode_, appConfig.hasFinishShowMode(), appConfig.finishShowMode_);
                    this.alertMode_ = visitor.visitString(hasAlertMode(), this.alertMode_, appConfig.hasAlertMode(), appConfig.alertMode_);
                    this.sortMode_ = visitor.visitInt(hasSortMode(), this.sortMode_, appConfig.hasSortMode(), appConfig.sortMode_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= appConfig.bitField0_;
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
                                    this.enableFloatMode_ = string;
                                } else if (tag == 18) {
                                    String string2 = codedInputStream.readString();
                                    this.bitField0_ |= 2;
                                    this.finishShowMode_ = string2;
                                } else if (tag == 26) {
                                    String string3 = codedInputStream.readString();
                                    this.bitField0_ |= 4;
                                    this.alertMode_ = string3;
                                } else if (tag == 32) {
                                    this.bitField0_ |= 8;
                                    this.sortMode_ = codedInputStream.readInt32();
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
                    Parser<AppConfig> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (AppConfig.class) {
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

        public static AppConfig getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AppConfig> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.backup.TodoAppConfigProtos$1, reason: invalid class name */
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
