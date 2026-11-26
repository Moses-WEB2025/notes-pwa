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
public final class AppConfigProtos {

    public interface AppConfigOrBuilder extends MessageLiteOrBuilder {
        int getAlertMode();

        String getBigScreenLayouts();

        ByteString getBigScreenLayoutsBytes();

        String getExcerptLinkRemind();

        ByteString getExcerptLinkRemindBytes();

        String getFontSize();

        ByteString getFontSizeBytes();

        int getListMode();

        String getQuickDelete();

        ByteString getQuickDeleteBytes();

        String getSortMode();

        ByteString getSortModeBytes();

        boolean hasAlertMode();

        boolean hasBigScreenLayouts();

        boolean hasExcerptLinkRemind();

        boolean hasFontSize();

        boolean hasListMode();

        boolean hasQuickDelete();

        boolean hasSortMode();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private AppConfigProtos() {
    }

    public static final class AppConfig extends GeneratedMessageLite<AppConfig, Builder> implements AppConfigOrBuilder {
        public static final int ALERTMODE_FIELD_NUMBER = 5;
        public static final int BIGSCREENLAYOUTS_FIELD_NUMBER = 7;
        private static final AppConfig DEFAULT_INSTANCE = new AppConfig();
        public static final int EXCERPTLINKREMIND_FIELD_NUMBER = 6;
        public static final int FONTSIZE_FIELD_NUMBER = 1;
        public static final int LISTMODE_FIELD_NUMBER = 4;
        private static volatile Parser<AppConfig> PARSER = null;
        public static final int QUICKDELETE_FIELD_NUMBER = 2;
        public static final int SORTMODE_FIELD_NUMBER = 3;
        private int alertMode_;
        private int bitField0_;
        private int listMode_;
        private String fontSize_ = "";
        private String quickDelete_ = "";
        private String sortMode_ = "";
        private String excerptLinkRemind_ = "";
        private String bigScreenLayouts_ = "";

        private AppConfig() {
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public boolean hasFontSize() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public String getFontSize() {
            return this.fontSize_;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public ByteString getFontSizeBytes() {
            return ByteString.copyFromUtf8(this.fontSize_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFontSize(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.fontSize_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFontSize() {
            this.bitField0_ &= -2;
            this.fontSize_ = getDefaultInstance().getFontSize();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFontSizeBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 1;
            this.fontSize_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public boolean hasQuickDelete() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public String getQuickDelete() {
            return this.quickDelete_;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public ByteString getQuickDeleteBytes() {
            return ByteString.copyFromUtf8(this.quickDelete_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setQuickDelete(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.quickDelete_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearQuickDelete() {
            this.bitField0_ &= -3;
            this.quickDelete_ = getDefaultInstance().getQuickDelete();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setQuickDeleteBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 2;
            this.quickDelete_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public boolean hasSortMode() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public String getSortMode() {
            return this.sortMode_;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public ByteString getSortModeBytes() {
            return ByteString.copyFromUtf8(this.sortMode_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSortMode(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.sortMode_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSortMode() {
            this.bitField0_ &= -5;
            this.sortMode_ = getDefaultInstance().getSortMode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSortModeBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 4;
            this.sortMode_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public boolean hasListMode() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public int getListMode() {
            return this.listMode_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setListMode(int i) {
            this.bitField0_ |= 8;
            this.listMode_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearListMode() {
            this.bitField0_ &= -9;
            this.listMode_ = 0;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public boolean hasAlertMode() {
            return (this.bitField0_ & 16) != 0;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public int getAlertMode() {
            return this.alertMode_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAlertMode(int i) {
            this.bitField0_ |= 16;
            this.alertMode_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAlertMode() {
            this.bitField0_ &= -17;
            this.alertMode_ = 0;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public boolean hasExcerptLinkRemind() {
            return (this.bitField0_ & 32) != 0;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public String getExcerptLinkRemind() {
            return this.excerptLinkRemind_;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public ByteString getExcerptLinkRemindBytes() {
            return ByteString.copyFromUtf8(this.excerptLinkRemind_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setExcerptLinkRemind(String str) {
            str.getClass();
            this.bitField0_ |= 32;
            this.excerptLinkRemind_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearExcerptLinkRemind() {
            this.bitField0_ &= -33;
            this.excerptLinkRemind_ = getDefaultInstance().getExcerptLinkRemind();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setExcerptLinkRemindBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 32;
            this.excerptLinkRemind_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public boolean hasBigScreenLayouts() {
            return (this.bitField0_ & 64) != 0;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public String getBigScreenLayouts() {
            return this.bigScreenLayouts_;
        }

        @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
        public ByteString getBigScreenLayoutsBytes() {
            return ByteString.copyFromUtf8(this.bigScreenLayouts_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBigScreenLayouts(String str) {
            str.getClass();
            this.bitField0_ |= 64;
            this.bigScreenLayouts_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearBigScreenLayouts() {
            this.bitField0_ &= -65;
            this.bigScreenLayouts_ = getDefaultInstance().getBigScreenLayouts();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBigScreenLayoutsBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 64;
            this.bigScreenLayouts_ = byteString.toStringUtf8();
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeString(1, getFontSize());
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeString(2, getQuickDelete());
            }
            if ((this.bitField0_ & 4) != 0) {
                codedOutputStream.writeString(3, getSortMode());
            }
            if ((this.bitField0_ & 8) != 0) {
                codedOutputStream.writeInt32(4, this.listMode_);
            }
            if ((this.bitField0_ & 16) != 0) {
                codedOutputStream.writeInt32(5, this.alertMode_);
            }
            if ((this.bitField0_ & 32) != 0) {
                codedOutputStream.writeString(6, getExcerptLinkRemind());
            }
            if ((this.bitField0_ & 64) != 0) {
                codedOutputStream.writeString(7, getBigScreenLayouts());
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeStringSize = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeStringSize(1, getFontSize()) : 0;
            if ((this.bitField0_ & 2) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(2, getQuickDelete());
            }
            if ((this.bitField0_ & 4) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(3, getSortMode());
            }
            if ((this.bitField0_ & 8) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(4, this.listMode_);
            }
            if ((this.bitField0_ & 16) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(5, this.alertMode_);
            }
            if ((this.bitField0_ & 32) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(6, getExcerptLinkRemind());
            }
            if ((this.bitField0_ & 64) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(7, getBigScreenLayouts());
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

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public boolean hasFontSize() {
                return ((AppConfig) this.instance).hasFontSize();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public String getFontSize() {
                return ((AppConfig) this.instance).getFontSize();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public ByteString getFontSizeBytes() {
                return ((AppConfig) this.instance).getFontSizeBytes();
            }

            public Builder setFontSize(String str) {
                copyOnWrite();
                ((AppConfig) this.instance).setFontSize(str);
                return this;
            }

            public Builder clearFontSize() {
                copyOnWrite();
                ((AppConfig) this.instance).clearFontSize();
                return this;
            }

            public Builder setFontSizeBytes(ByteString byteString) {
                copyOnWrite();
                ((AppConfig) this.instance).setFontSizeBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public boolean hasQuickDelete() {
                return ((AppConfig) this.instance).hasQuickDelete();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public String getQuickDelete() {
                return ((AppConfig) this.instance).getQuickDelete();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public ByteString getQuickDeleteBytes() {
                return ((AppConfig) this.instance).getQuickDeleteBytes();
            }

            public Builder setQuickDelete(String str) {
                copyOnWrite();
                ((AppConfig) this.instance).setQuickDelete(str);
                return this;
            }

            public Builder clearQuickDelete() {
                copyOnWrite();
                ((AppConfig) this.instance).clearQuickDelete();
                return this;
            }

            public Builder setQuickDeleteBytes(ByteString byteString) {
                copyOnWrite();
                ((AppConfig) this.instance).setQuickDeleteBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public boolean hasSortMode() {
                return ((AppConfig) this.instance).hasSortMode();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public String getSortMode() {
                return ((AppConfig) this.instance).getSortMode();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public ByteString getSortModeBytes() {
                return ((AppConfig) this.instance).getSortModeBytes();
            }

            public Builder setSortMode(String str) {
                copyOnWrite();
                ((AppConfig) this.instance).setSortMode(str);
                return this;
            }

            public Builder clearSortMode() {
                copyOnWrite();
                ((AppConfig) this.instance).clearSortMode();
                return this;
            }

            public Builder setSortModeBytes(ByteString byteString) {
                copyOnWrite();
                ((AppConfig) this.instance).setSortModeBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public boolean hasListMode() {
                return ((AppConfig) this.instance).hasListMode();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public int getListMode() {
                return ((AppConfig) this.instance).getListMode();
            }

            public Builder setListMode(int i) {
                copyOnWrite();
                ((AppConfig) this.instance).setListMode(i);
                return this;
            }

            public Builder clearListMode() {
                copyOnWrite();
                ((AppConfig) this.instance).clearListMode();
                return this;
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public boolean hasAlertMode() {
                return ((AppConfig) this.instance).hasAlertMode();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public int getAlertMode() {
                return ((AppConfig) this.instance).getAlertMode();
            }

            public Builder setAlertMode(int i) {
                copyOnWrite();
                ((AppConfig) this.instance).setAlertMode(i);
                return this;
            }

            public Builder clearAlertMode() {
                copyOnWrite();
                ((AppConfig) this.instance).clearAlertMode();
                return this;
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public boolean hasExcerptLinkRemind() {
                return ((AppConfig) this.instance).hasExcerptLinkRemind();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public String getExcerptLinkRemind() {
                return ((AppConfig) this.instance).getExcerptLinkRemind();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public ByteString getExcerptLinkRemindBytes() {
                return ((AppConfig) this.instance).getExcerptLinkRemindBytes();
            }

            public Builder setExcerptLinkRemind(String str) {
                copyOnWrite();
                ((AppConfig) this.instance).setExcerptLinkRemind(str);
                return this;
            }

            public Builder clearExcerptLinkRemind() {
                copyOnWrite();
                ((AppConfig) this.instance).clearExcerptLinkRemind();
                return this;
            }

            public Builder setExcerptLinkRemindBytes(ByteString byteString) {
                copyOnWrite();
                ((AppConfig) this.instance).setExcerptLinkRemindBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public boolean hasBigScreenLayouts() {
                return ((AppConfig) this.instance).hasBigScreenLayouts();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public String getBigScreenLayouts() {
                return ((AppConfig) this.instance).getBigScreenLayouts();
            }

            @Override // com.miui.notes.backup.AppConfigProtos.AppConfigOrBuilder
            public ByteString getBigScreenLayoutsBytes() {
                return ((AppConfig) this.instance).getBigScreenLayoutsBytes();
            }

            public Builder setBigScreenLayouts(String str) {
                copyOnWrite();
                ((AppConfig) this.instance).setBigScreenLayouts(str);
                return this;
            }

            public Builder clearBigScreenLayouts() {
                copyOnWrite();
                ((AppConfig) this.instance).clearBigScreenLayouts();
                return this;
            }

            public Builder setBigScreenLayoutsBytes(ByteString byteString) {
                copyOnWrite();
                ((AppConfig) this.instance).setBigScreenLayoutsBytes(byteString);
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
                    this.fontSize_ = visitor.visitString(hasFontSize(), this.fontSize_, appConfig.hasFontSize(), appConfig.fontSize_);
                    this.quickDelete_ = visitor.visitString(hasQuickDelete(), this.quickDelete_, appConfig.hasQuickDelete(), appConfig.quickDelete_);
                    this.sortMode_ = visitor.visitString(hasSortMode(), this.sortMode_, appConfig.hasSortMode(), appConfig.sortMode_);
                    this.listMode_ = visitor.visitInt(hasListMode(), this.listMode_, appConfig.hasListMode(), appConfig.listMode_);
                    this.alertMode_ = visitor.visitInt(hasAlertMode(), this.alertMode_, appConfig.hasAlertMode(), appConfig.alertMode_);
                    this.excerptLinkRemind_ = visitor.visitString(hasExcerptLinkRemind(), this.excerptLinkRemind_, appConfig.hasExcerptLinkRemind(), appConfig.excerptLinkRemind_);
                    this.bigScreenLayouts_ = visitor.visitString(hasBigScreenLayouts(), this.bigScreenLayouts_, appConfig.hasBigScreenLayouts(), appConfig.bigScreenLayouts_);
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
                                    this.fontSize_ = string;
                                } else if (tag == 18) {
                                    String string2 = codedInputStream.readString();
                                    this.bitField0_ |= 2;
                                    this.quickDelete_ = string2;
                                } else if (tag == 26) {
                                    String string3 = codedInputStream.readString();
                                    this.bitField0_ |= 4;
                                    this.sortMode_ = string3;
                                } else if (tag == 32) {
                                    this.bitField0_ |= 8;
                                    this.listMode_ = codedInputStream.readInt32();
                                } else if (tag == 40) {
                                    this.bitField0_ |= 16;
                                    this.alertMode_ = codedInputStream.readInt32();
                                } else if (tag == 50) {
                                    String string4 = codedInputStream.readString();
                                    this.bitField0_ = 32 | this.bitField0_;
                                    this.excerptLinkRemind_ = string4;
                                } else if (tag == 58) {
                                    String string5 = codedInputStream.readString();
                                    this.bitField0_ |= 64;
                                    this.bigScreenLayouts_ = string5;
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

    /* JADX INFO: renamed from: com.miui.notes.backup.AppConfigProtos$1, reason: invalid class name */
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
