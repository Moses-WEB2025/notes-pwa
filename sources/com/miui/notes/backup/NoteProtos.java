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
import defpackage.R2;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public final class NoteProtos {

    public interface NoteDataOrBuilder extends MessageLiteOrBuilder {
        String getContent();

        ByteString getContentBytes();

        long getCreatedDate();

        long getData1();

        long getData2();

        String getData3();

        ByteString getData3Bytes();

        String getData4();

        ByteString getData4Bytes();

        String getData5();

        ByteString getData5Bytes();

        String getDataContentType();

        ByteString getDataContentTypeBytes();

        String getMimeType();

        ByteString getMimeTypeBytes();

        long getModifiedDate();

        boolean hasContent();

        boolean hasCreatedDate();

        boolean hasData1();

        boolean hasData2();

        boolean hasData3();

        boolean hasData4();

        boolean hasData5();

        boolean hasDataContentType();

        boolean hasMimeType();

        boolean hasModifiedDate();
    }

    public interface NoteEntityOrBuilder extends MessageLiteOrBuilder {
        long getAlertDate();

        int getBgColorId();

        long getCreatedDate();

        NoteData getData(int i);

        int getDataCount();

        List<NoteData> getDataList();

        String getEntName();

        ByteString getEntNameBytes();

        String getFolderTitle();

        ByteString getFolderTitleBytes();

        int getFolderType();

        String getGuid();

        ByteString getGuidBytes();

        boolean getHasAttachment();

        String getImages();

        ByteString getImagesBytes();

        int getIsEmpty();

        String getLuid();

        ByteString getLuidBytes();

        String getMindContent();

        ByteString getMindContentBytes();

        String getMindContentPlainText();

        ByteString getMindContentPlainTextBytes();

        long getModifiedDate();

        String getNoteContentType();

        ByteString getNoteContentTypeBytes();

        String getRecognizedFileNames();

        ByteString getRecognizedFileNamesBytes();

        String getSnippet();

        ByteString getSnippetBytes();

        long getStickDate();

        int getThemeId();

        String getThumbnail();

        ByteString getThumbnailBytes();

        String getTitle();

        ByteString getTitleBytes();

        boolean hasAlertDate();

        boolean hasBgColorId();

        boolean hasCreatedDate();

        boolean hasEntName();

        boolean hasFolderTitle();

        boolean hasFolderType();

        boolean hasGuid();

        boolean hasHasAttachment();

        boolean hasImages();

        boolean hasIsEmpty();

        boolean hasLuid();

        boolean hasMindContent();

        boolean hasMindContentPlainText();

        boolean hasModifiedDate();

        boolean hasNoteContentType();

        boolean hasRecognizedFileNames();

        boolean hasSnippet();

        boolean hasStickDate();

        boolean hasThemeId();

        boolean hasThumbnail();

        boolean hasTitle();
    }

    public interface NoteFolderOrBuilder extends MessageLiteOrBuilder {
        long getAlertDate();

        int getBgColorId();

        long getCreatedDate();

        String getGuid();

        ByteString getGuidBytes();

        boolean getHasAttachment();

        String getLuid();

        ByteString getLuidBytes();

        long getModifiedDate();

        String getSnippet();

        ByteString getSnippetBytes();

        String getSubject();

        ByteString getSubjectBytes();

        boolean hasAlertDate();

        boolean hasBgColorId();

        boolean hasCreatedDate();

        boolean hasGuid();

        boolean hasHasAttachment();

        boolean hasLuid();

        boolean hasModifiedDate();

        boolean hasSnippet();

        boolean hasSubject();
    }

    public interface NotesOrBuilder extends MessageLiteOrBuilder {
        NoteFolder getFolder(int i);

        int getFolderCount();

        List<NoteFolder> getFolderList();

        NoteEntity getNote(int i);

        int getNoteCount();

        List<NoteEntity> getNoteList();

        int getVersion();

        boolean hasVersion();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private NoteProtos() {
    }

    public static final class NoteData extends GeneratedMessageLite<NoteData, Builder> implements NoteDataOrBuilder {
        public static final int CONTENT_FIELD_NUMBER = 4;
        public static final int CREATEDDATE_FIELD_NUMBER = 2;
        public static final int DATA1_FIELD_NUMBER = 5;
        public static final int DATA2_FIELD_NUMBER = 6;
        public static final int DATA3_FIELD_NUMBER = 7;
        public static final int DATA4_FIELD_NUMBER = 8;
        public static final int DATA5_FIELD_NUMBER = 9;
        public static final int DATA_CONTENT_TYPE_FIELD_NUMBER = 10;
        private static final NoteData DEFAULT_INSTANCE = new NoteData();
        public static final int MIMETYPE_FIELD_NUMBER = 1;
        public static final int MODIFIEDDATE_FIELD_NUMBER = 3;
        private static volatile Parser<NoteData> PARSER;
        private int bitField0_;
        private long createdDate_;
        private long data1_;
        private long data2_;
        private long modifiedDate_;
        private String mimeType_ = "";
        private String content_ = "";
        private String data3_ = "";
        private String data4_ = "";
        private String data5_ = "";
        private String dataContentType_ = "";

        private NoteData() {
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasMimeType() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public String getMimeType() {
            return this.mimeType_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
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

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasCreatedDate() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public long getCreatedDate() {
            return this.createdDate_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCreatedDate(long j) {
            this.bitField0_ |= 2;
            this.createdDate_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCreatedDate() {
            this.bitField0_ &= -3;
            this.createdDate_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasModifiedDate() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public long getModifiedDate() {
            return this.modifiedDate_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setModifiedDate(long j) {
            this.bitField0_ |= 4;
            this.modifiedDate_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearModifiedDate() {
            this.bitField0_ &= -5;
            this.modifiedDate_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasContent() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public String getContent() {
            return this.content_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public ByteString getContentBytes() {
            return ByteString.copyFromUtf8(this.content_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setContent(String str) {
            str.getClass();
            this.bitField0_ |= 8;
            this.content_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearContent() {
            this.bitField0_ &= -9;
            this.content_ = getDefaultInstance().getContent();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setContentBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 8;
            this.content_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasData1() {
            return (this.bitField0_ & 16) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public long getData1() {
            return this.data1_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData1(long j) {
            this.bitField0_ |= 16;
            this.data1_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearData1() {
            this.bitField0_ &= -17;
            this.data1_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasData2() {
            return (this.bitField0_ & 32) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public long getData2() {
            return this.data2_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData2(long j) {
            this.bitField0_ |= 32;
            this.data2_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearData2() {
            this.bitField0_ &= -33;
            this.data2_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasData3() {
            return (this.bitField0_ & 64) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public String getData3() {
            return this.data3_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public ByteString getData3Bytes() {
            return ByteString.copyFromUtf8(this.data3_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData3(String str) {
            str.getClass();
            this.bitField0_ |= 64;
            this.data3_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearData3() {
            this.bitField0_ &= -65;
            this.data3_ = getDefaultInstance().getData3();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData3Bytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 64;
            this.data3_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasData4() {
            return (this.bitField0_ & 128) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public String getData4() {
            return this.data4_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public ByteString getData4Bytes() {
            return ByteString.copyFromUtf8(this.data4_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData4(String str) {
            str.getClass();
            this.bitField0_ |= 128;
            this.data4_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearData4() {
            this.bitField0_ &= -129;
            this.data4_ = getDefaultInstance().getData4();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData4Bytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 128;
            this.data4_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasData5() {
            return (this.bitField0_ & 256) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public String getData5() {
            return this.data5_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public ByteString getData5Bytes() {
            return ByteString.copyFromUtf8(this.data5_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData5(String str) {
            str.getClass();
            this.bitField0_ |= 256;
            this.data5_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearData5() {
            this.bitField0_ &= -257;
            this.data5_ = getDefaultInstance().getData5();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData5Bytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 256;
            this.data5_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public boolean hasDataContentType() {
            return (this.bitField0_ & 512) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public String getDataContentType() {
            return this.dataContentType_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
        public ByteString getDataContentTypeBytes() {
            return ByteString.copyFromUtf8(this.dataContentType_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataContentType(String str) {
            str.getClass();
            this.bitField0_ |= 512;
            this.dataContentType_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataContentType() {
            this.bitField0_ &= -513;
            this.dataContentType_ = getDefaultInstance().getDataContentType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataContentTypeBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 512;
            this.dataContentType_ = byteString.toStringUtf8();
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeString(1, getMimeType());
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeInt64(2, this.createdDate_);
            }
            if ((this.bitField0_ & 4) != 0) {
                codedOutputStream.writeInt64(3, this.modifiedDate_);
            }
            if ((this.bitField0_ & 8) != 0) {
                codedOutputStream.writeString(4, getContent());
            }
            if ((this.bitField0_ & 16) != 0) {
                codedOutputStream.writeInt64(5, this.data1_);
            }
            if ((this.bitField0_ & 32) != 0) {
                codedOutputStream.writeInt64(6, this.data2_);
            }
            if ((this.bitField0_ & 64) != 0) {
                codedOutputStream.writeString(7, getData3());
            }
            if ((this.bitField0_ & 128) != 0) {
                codedOutputStream.writeString(8, getData4());
            }
            if ((this.bitField0_ & 256) != 0) {
                codedOutputStream.writeString(9, getData5());
            }
            if ((this.bitField0_ & 512) != 0) {
                codedOutputStream.writeString(10, getDataContentType());
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
                iComputeStringSize += CodedOutputStream.computeInt64Size(2, this.createdDate_);
            }
            if ((this.bitField0_ & 4) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(3, this.modifiedDate_);
            }
            if ((this.bitField0_ & 8) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(4, getContent());
            }
            if ((this.bitField0_ & 16) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(5, this.data1_);
            }
            if ((this.bitField0_ & 32) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(6, this.data2_);
            }
            if ((this.bitField0_ & 64) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(7, getData3());
            }
            if ((this.bitField0_ & 128) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(8, getData4());
            }
            if ((this.bitField0_ & 256) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(9, getData5());
            }
            if ((this.bitField0_ & 512) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(10, getDataContentType());
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

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasMimeType() {
                return ((NoteData) this.instance).hasMimeType();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public String getMimeType() {
                return ((NoteData) this.instance).getMimeType();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
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

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasCreatedDate() {
                return ((NoteData) this.instance).hasCreatedDate();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public long getCreatedDate() {
                return ((NoteData) this.instance).getCreatedDate();
            }

            public Builder setCreatedDate(long j) {
                copyOnWrite();
                ((NoteData) this.instance).setCreatedDate(j);
                return this;
            }

            public Builder clearCreatedDate() {
                copyOnWrite();
                ((NoteData) this.instance).clearCreatedDate();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasModifiedDate() {
                return ((NoteData) this.instance).hasModifiedDate();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public long getModifiedDate() {
                return ((NoteData) this.instance).getModifiedDate();
            }

            public Builder setModifiedDate(long j) {
                copyOnWrite();
                ((NoteData) this.instance).setModifiedDate(j);
                return this;
            }

            public Builder clearModifiedDate() {
                copyOnWrite();
                ((NoteData) this.instance).clearModifiedDate();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasContent() {
                return ((NoteData) this.instance).hasContent();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public String getContent() {
                return ((NoteData) this.instance).getContent();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
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

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasData1() {
                return ((NoteData) this.instance).hasData1();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public long getData1() {
                return ((NoteData) this.instance).getData1();
            }

            public Builder setData1(long j) {
                copyOnWrite();
                ((NoteData) this.instance).setData1(j);
                return this;
            }

            public Builder clearData1() {
                copyOnWrite();
                ((NoteData) this.instance).clearData1();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasData2() {
                return ((NoteData) this.instance).hasData2();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public long getData2() {
                return ((NoteData) this.instance).getData2();
            }

            public Builder setData2(long j) {
                copyOnWrite();
                ((NoteData) this.instance).setData2(j);
                return this;
            }

            public Builder clearData2() {
                copyOnWrite();
                ((NoteData) this.instance).clearData2();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasData3() {
                return ((NoteData) this.instance).hasData3();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public String getData3() {
                return ((NoteData) this.instance).getData3();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public ByteString getData3Bytes() {
                return ((NoteData) this.instance).getData3Bytes();
            }

            public Builder setData3(String str) {
                copyOnWrite();
                ((NoteData) this.instance).setData3(str);
                return this;
            }

            public Builder clearData3() {
                copyOnWrite();
                ((NoteData) this.instance).clearData3();
                return this;
            }

            public Builder setData3Bytes(ByteString byteString) {
                copyOnWrite();
                ((NoteData) this.instance).setData3Bytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasData4() {
                return ((NoteData) this.instance).hasData4();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public String getData4() {
                return ((NoteData) this.instance).getData4();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public ByteString getData4Bytes() {
                return ((NoteData) this.instance).getData4Bytes();
            }

            public Builder setData4(String str) {
                copyOnWrite();
                ((NoteData) this.instance).setData4(str);
                return this;
            }

            public Builder clearData4() {
                copyOnWrite();
                ((NoteData) this.instance).clearData4();
                return this;
            }

            public Builder setData4Bytes(ByteString byteString) {
                copyOnWrite();
                ((NoteData) this.instance).setData4Bytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasData5() {
                return ((NoteData) this.instance).hasData5();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public String getData5() {
                return ((NoteData) this.instance).getData5();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public ByteString getData5Bytes() {
                return ((NoteData) this.instance).getData5Bytes();
            }

            public Builder setData5(String str) {
                copyOnWrite();
                ((NoteData) this.instance).setData5(str);
                return this;
            }

            public Builder clearData5() {
                copyOnWrite();
                ((NoteData) this.instance).clearData5();
                return this;
            }

            public Builder setData5Bytes(ByteString byteString) {
                copyOnWrite();
                ((NoteData) this.instance).setData5Bytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public boolean hasDataContentType() {
                return ((NoteData) this.instance).hasDataContentType();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public String getDataContentType() {
                return ((NoteData) this.instance).getDataContentType();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteDataOrBuilder
            public ByteString getDataContentTypeBytes() {
                return ((NoteData) this.instance).getDataContentTypeBytes();
            }

            public Builder setDataContentType(String str) {
                copyOnWrite();
                ((NoteData) this.instance).setDataContentType(str);
                return this;
            }

            public Builder clearDataContentType() {
                copyOnWrite();
                ((NoteData) this.instance).clearDataContentType();
                return this;
            }

            public Builder setDataContentTypeBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteData) this.instance).setDataContentTypeBytes(byteString);
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
                    this.createdDate_ = visitor.visitLong(hasCreatedDate(), this.createdDate_, noteData.hasCreatedDate(), noteData.createdDate_);
                    this.modifiedDate_ = visitor.visitLong(hasModifiedDate(), this.modifiedDate_, noteData.hasModifiedDate(), noteData.modifiedDate_);
                    this.content_ = visitor.visitString(hasContent(), this.content_, noteData.hasContent(), noteData.content_);
                    this.data1_ = visitor.visitLong(hasData1(), this.data1_, noteData.hasData1(), noteData.data1_);
                    this.data2_ = visitor.visitLong(hasData2(), this.data2_, noteData.hasData2(), noteData.data2_);
                    this.data3_ = visitor.visitString(hasData3(), this.data3_, noteData.hasData3(), noteData.data3_);
                    this.data4_ = visitor.visitString(hasData4(), this.data4_, noteData.hasData4(), noteData.data4_);
                    this.data5_ = visitor.visitString(hasData5(), this.data5_, noteData.hasData5(), noteData.data5_);
                    this.dataContentType_ = visitor.visitString(hasDataContentType(), this.dataContentType_, noteData.hasDataContentType(), noteData.dataContentType_);
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
                            switch (tag) {
                                case 0:
                                    break;
                                case 10:
                                    String string = codedInputStream.readString();
                                    this.bitField0_ |= 1;
                                    this.mimeType_ = string;
                                    continue;
                                case 16:
                                    this.bitField0_ |= 2;
                                    this.createdDate_ = codedInputStream.readInt64();
                                    continue;
                                case 24:
                                    this.bitField0_ |= 4;
                                    this.modifiedDate_ = codedInputStream.readInt64();
                                    continue;
                                case 34:
                                    String string2 = codedInputStream.readString();
                                    this.bitField0_ |= 8;
                                    this.content_ = string2;
                                    continue;
                                case 40:
                                    this.bitField0_ |= 16;
                                    this.data1_ = codedInputStream.readInt64();
                                    continue;
                                case 48:
                                    this.bitField0_ |= 32;
                                    this.data2_ = codedInputStream.readInt64();
                                    continue;
                                case 58:
                                    String string3 = codedInputStream.readString();
                                    this.bitField0_ |= 64;
                                    this.data3_ = string3;
                                    continue;
                                case 66:
                                    String string4 = codedInputStream.readString();
                                    this.bitField0_ |= 128;
                                    this.data4_ = string4;
                                    continue;
                                case 74:
                                    String string5 = codedInputStream.readString();
                                    this.bitField0_ |= 256;
                                    this.data5_ = string5;
                                    continue;
                                case 82:
                                    String string6 = codedInputStream.readString();
                                    this.bitField0_ |= 512;
                                    this.dataContentType_ = string6;
                                    continue;
                                default:
                                    if (!parseUnknownField(tag, codedInputStream)) {
                                        break;
                                    }
                                    break;
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

    /* JADX INFO: renamed from: com.miui.notes.backup.NoteProtos$1, reason: invalid class name */
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
        public static final int ALERTDATE_FIELD_NUMBER = 3;
        public static final int BGCOLORID_FIELD_NUMBER = 4;
        public static final int CREATEDDATE_FIELD_NUMBER = 5;
        public static final int DATA_FIELD_NUMBER = 9;
        private static final NoteEntity DEFAULT_INSTANCE = new NoteEntity();
        public static final int ENT_NAME_FIELD_NUMBER = 18;
        public static final int FOLDERTITLE_FIELD_NUMBER = 10;
        public static final int FOLDERTYPE_FIELD_NUMBER = 11;
        public static final int GUID_FIELD_NUMBER = 1;
        public static final int HASATTACHMENT_FIELD_NUMBER = 7;
        public static final int IMAGES_FIELD_NUMBER = 19;
        public static final int IS_EMPTY_FIELD_NUMBER = 22;
        public static final int LUID_FIELD_NUMBER = 2;
        public static final int MIND_CONTENT_FIELD_NUMBER = 16;
        public static final int MIND_CONTENT_PLAIN_TEXT_FIELD_NUMBER = 17;
        public static final int MODIFIEDDATE_FIELD_NUMBER = 6;
        public static final int NOTE_CONTENT_TYPE_FIELD_NUMBER = 15;
        private static volatile Parser<NoteEntity> PARSER = null;
        public static final int RECOGNIZED_FILE_NAMES_FIELD_NUMBER = 21;
        public static final int SNIPPET_FIELD_NUMBER = 8;
        public static final int STICKDATE_FIELD_NUMBER = 12;
        public static final int THEMEID_FIELD_NUMBER = 13;
        public static final int THUMBNAIL_FIELD_NUMBER = 20;
        public static final int TITLE_FIELD_NUMBER = 14;
        private long alertDate_;
        private int bgColorId_;
        private int bitField0_;
        private long createdDate_;
        private int folderType_;
        private boolean hasAttachment_;
        private int isEmpty_;
        private long modifiedDate_;
        private long stickDate_;
        private int themeId_;
        private String guid_ = "";
        private String luid_ = "";
        private String snippet_ = "";
        private Internal.ProtobufList<NoteData> data_ = emptyProtobufList();
        private String folderTitle_ = "";
        private String title_ = "";
        private String noteContentType_ = "";
        private String mindContent_ = "";
        private String mindContentPlainText_ = "";
        private String entName_ = "";
        private String images_ = "";
        private String thumbnail_ = "";
        private String recognizedFileNames_ = "";

        private NoteEntity() {
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasGuid() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getGuid() {
            return this.guid_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getGuidBytes() {
            return ByteString.copyFromUtf8(this.guid_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setGuid(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.guid_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearGuid() {
            this.bitField0_ &= -2;
            this.guid_ = getDefaultInstance().getGuid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setGuidBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 1;
            this.guid_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasLuid() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getLuid() {
            return this.luid_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getLuidBytes() {
            return ByteString.copyFromUtf8(this.luid_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLuid(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.luid_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLuid() {
            this.bitField0_ &= -3;
            this.luid_ = getDefaultInstance().getLuid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLuidBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 2;
            this.luid_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasAlertDate() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public long getAlertDate() {
            return this.alertDate_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAlertDate(long j) {
            this.bitField0_ |= 4;
            this.alertDate_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAlertDate() {
            this.bitField0_ &= -5;
            this.alertDate_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasBgColorId() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public int getBgColorId() {
            return this.bgColorId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBgColorId(int i) {
            this.bitField0_ |= 8;
            this.bgColorId_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearBgColorId() {
            this.bitField0_ &= -9;
            this.bgColorId_ = 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasCreatedDate() {
            return (this.bitField0_ & 16) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public long getCreatedDate() {
            return this.createdDate_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCreatedDate(long j) {
            this.bitField0_ |= 16;
            this.createdDate_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCreatedDate() {
            this.bitField0_ &= -17;
            this.createdDate_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasModifiedDate() {
            return (this.bitField0_ & 32) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public long getModifiedDate() {
            return this.modifiedDate_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setModifiedDate(long j) {
            this.bitField0_ |= 32;
            this.modifiedDate_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearModifiedDate() {
            this.bitField0_ &= -33;
            this.modifiedDate_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasHasAttachment() {
            return (this.bitField0_ & 64) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean getHasAttachment() {
            return this.hasAttachment_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setHasAttachment(boolean z) {
            this.bitField0_ |= 64;
            this.hasAttachment_ = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHasAttachment() {
            this.bitField0_ &= -65;
            this.hasAttachment_ = false;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasSnippet() {
            return (this.bitField0_ & 128) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getSnippet() {
            return this.snippet_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getSnippetBytes() {
            return ByteString.copyFromUtf8(this.snippet_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSnippet(String str) {
            str.getClass();
            this.bitField0_ |= 128;
            this.snippet_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSnippet() {
            this.bitField0_ &= -129;
            this.snippet_ = getDefaultInstance().getSnippet();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSnippetBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 128;
            this.snippet_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public List<NoteData> getDataList() {
            return this.data_;
        }

        public List<? extends NoteDataOrBuilder> getDataOrBuilderList() {
            return this.data_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public int getDataCount() {
            return this.data_.size();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
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

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasFolderTitle() {
            return (this.bitField0_ & 256) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getFolderTitle() {
            return this.folderTitle_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getFolderTitleBytes() {
            return ByteString.copyFromUtf8(this.folderTitle_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFolderTitle(String str) {
            str.getClass();
            this.bitField0_ |= 256;
            this.folderTitle_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFolderTitle() {
            this.bitField0_ &= -257;
            this.folderTitle_ = getDefaultInstance().getFolderTitle();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFolderTitleBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 256;
            this.folderTitle_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasFolderType() {
            return (this.bitField0_ & 512) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public int getFolderType() {
            return this.folderType_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFolderType(int i) {
            this.bitField0_ |= 512;
            this.folderType_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFolderType() {
            this.bitField0_ &= -513;
            this.folderType_ = 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasStickDate() {
            return (this.bitField0_ & 1024) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public long getStickDate() {
            return this.stickDate_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStickDate(long j) {
            this.bitField0_ |= 1024;
            this.stickDate_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStickDate() {
            this.bitField0_ &= -1025;
            this.stickDate_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasThemeId() {
            return (this.bitField0_ & 2048) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public int getThemeId() {
            return this.themeId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setThemeId(int i) {
            this.bitField0_ |= 2048;
            this.themeId_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearThemeId() {
            this.bitField0_ &= -2049;
            this.themeId_ = 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasTitle() {
            return (this.bitField0_ & 4096) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getTitle() {
            return this.title_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getTitleBytes() {
            return ByteString.copyFromUtf8(this.title_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTitle(String str) {
            str.getClass();
            this.bitField0_ |= 4096;
            this.title_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTitle() {
            this.bitField0_ &= -4097;
            this.title_ = getDefaultInstance().getTitle();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTitleBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 4096;
            this.title_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasNoteContentType() {
            return (this.bitField0_ & 8192) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getNoteContentType() {
            return this.noteContentType_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getNoteContentTypeBytes() {
            return ByteString.copyFromUtf8(this.noteContentType_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNoteContentType(String str) {
            str.getClass();
            this.bitField0_ |= 8192;
            this.noteContentType_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNoteContentType() {
            this.bitField0_ &= -8193;
            this.noteContentType_ = getDefaultInstance().getNoteContentType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNoteContentTypeBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 8192;
            this.noteContentType_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasMindContent() {
            return (this.bitField0_ & 16384) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getMindContent() {
            return this.mindContent_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getMindContentBytes() {
            return ByteString.copyFromUtf8(this.mindContent_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMindContent(String str) {
            str.getClass();
            this.bitField0_ |= 16384;
            this.mindContent_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMindContent() {
            this.bitField0_ &= -16385;
            this.mindContent_ = getDefaultInstance().getMindContent();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMindContentBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 16384;
            this.mindContent_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasMindContentPlainText() {
            return (this.bitField0_ & 32768) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getMindContentPlainText() {
            return this.mindContentPlainText_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getMindContentPlainTextBytes() {
            return ByteString.copyFromUtf8(this.mindContentPlainText_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMindContentPlainText(String str) {
            str.getClass();
            this.bitField0_ |= 32768;
            this.mindContentPlainText_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMindContentPlainText() {
            this.bitField0_ &= -32769;
            this.mindContentPlainText_ = getDefaultInstance().getMindContentPlainText();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMindContentPlainTextBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 32768;
            this.mindContentPlainText_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasEntName() {
            return (this.bitField0_ & 65536) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getEntName() {
            return this.entName_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getEntNameBytes() {
            return ByteString.copyFromUtf8(this.entName_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEntName(String str) {
            str.getClass();
            this.bitField0_ |= 65536;
            this.entName_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearEntName() {
            this.bitField0_ &= -65537;
            this.entName_ = getDefaultInstance().getEntName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEntNameBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 65536;
            this.entName_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasImages() {
            return (this.bitField0_ & 131072) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getImages() {
            return this.images_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getImagesBytes() {
            return ByteString.copyFromUtf8(this.images_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setImages(String str) {
            str.getClass();
            this.bitField0_ |= 131072;
            this.images_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearImages() {
            this.bitField0_ &= -131073;
            this.images_ = getDefaultInstance().getImages();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setImagesBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 131072;
            this.images_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasThumbnail() {
            return (this.bitField0_ & 262144) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getThumbnail() {
            return this.thumbnail_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getThumbnailBytes() {
            return ByteString.copyFromUtf8(this.thumbnail_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setThumbnail(String str) {
            str.getClass();
            this.bitField0_ |= 262144;
            this.thumbnail_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearThumbnail() {
            this.bitField0_ &= -262145;
            this.thumbnail_ = getDefaultInstance().getThumbnail();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setThumbnailBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 262144;
            this.thumbnail_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasRecognizedFileNames() {
            return (this.bitField0_ & 524288) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public String getRecognizedFileNames() {
            return this.recognizedFileNames_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public ByteString getRecognizedFileNamesBytes() {
            return ByteString.copyFromUtf8(this.recognizedFileNames_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRecognizedFileNames(String str) {
            str.getClass();
            this.bitField0_ |= 524288;
            this.recognizedFileNames_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRecognizedFileNames() {
            this.bitField0_ &= -524289;
            this.recognizedFileNames_ = getDefaultInstance().getRecognizedFileNames();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRecognizedFileNamesBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 524288;
            this.recognizedFileNames_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public boolean hasIsEmpty() {
            return (this.bitField0_ & 1048576) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
        public int getIsEmpty() {
            return this.isEmpty_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIsEmpty(int i) {
            this.bitField0_ |= 1048576;
            this.isEmpty_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearIsEmpty() {
            this.bitField0_ &= -1048577;
            this.isEmpty_ = 0;
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeString(1, getGuid());
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeString(2, getLuid());
            }
            if ((this.bitField0_ & 4) != 0) {
                codedOutputStream.writeInt64(3, this.alertDate_);
            }
            if ((this.bitField0_ & 8) != 0) {
                codedOutputStream.writeInt32(4, this.bgColorId_);
            }
            if ((this.bitField0_ & 16) != 0) {
                codedOutputStream.writeInt64(5, this.createdDate_);
            }
            if ((this.bitField0_ & 32) != 0) {
                codedOutputStream.writeInt64(6, this.modifiedDate_);
            }
            if ((this.bitField0_ & 64) != 0) {
                codedOutputStream.writeBool(7, this.hasAttachment_);
            }
            if ((this.bitField0_ & 128) != 0) {
                codedOutputStream.writeString(8, getSnippet());
            }
            for (int i = 0; i < this.data_.size(); i++) {
                codedOutputStream.writeMessage(9, this.data_.get(i));
            }
            if ((this.bitField0_ & 256) != 0) {
                codedOutputStream.writeString(10, getFolderTitle());
            }
            if ((this.bitField0_ & 512) != 0) {
                codedOutputStream.writeInt32(11, this.folderType_);
            }
            if ((this.bitField0_ & 1024) != 0) {
                codedOutputStream.writeInt64(12, this.stickDate_);
            }
            if ((this.bitField0_ & 2048) != 0) {
                codedOutputStream.writeInt32(13, this.themeId_);
            }
            if ((this.bitField0_ & 4096) != 0) {
                codedOutputStream.writeString(14, getTitle());
            }
            if ((this.bitField0_ & 8192) != 0) {
                codedOutputStream.writeString(15, getNoteContentType());
            }
            if ((this.bitField0_ & 16384) != 0) {
                codedOutputStream.writeString(16, getMindContent());
            }
            if ((this.bitField0_ & 32768) != 0) {
                codedOutputStream.writeString(17, getMindContentPlainText());
            }
            if ((this.bitField0_ & 65536) != 0) {
                codedOutputStream.writeString(18, getEntName());
            }
            if ((this.bitField0_ & 131072) != 0) {
                codedOutputStream.writeString(19, getImages());
            }
            if ((this.bitField0_ & 262144) != 0) {
                codedOutputStream.writeString(20, getThumbnail());
            }
            if ((this.bitField0_ & 524288) != 0) {
                codedOutputStream.writeString(21, getRecognizedFileNames());
            }
            if ((this.bitField0_ & 1048576) != 0) {
                codedOutputStream.writeInt32(22, this.isEmpty_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeStringSize = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeStringSize(1, getGuid()) : 0;
            if ((this.bitField0_ & 2) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(2, getLuid());
            }
            if ((this.bitField0_ & 4) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(3, this.alertDate_);
            }
            if ((this.bitField0_ & 8) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(4, this.bgColorId_);
            }
            if ((this.bitField0_ & 16) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(5, this.createdDate_);
            }
            if ((this.bitField0_ & 32) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(6, this.modifiedDate_);
            }
            if ((this.bitField0_ & 64) != 0) {
                iComputeStringSize += CodedOutputStream.computeBoolSize(7, this.hasAttachment_);
            }
            if ((this.bitField0_ & 128) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(8, getSnippet());
            }
            for (int i2 = 0; i2 < this.data_.size(); i2++) {
                iComputeStringSize += CodedOutputStream.computeMessageSize(9, this.data_.get(i2));
            }
            if ((this.bitField0_ & 256) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(10, getFolderTitle());
            }
            if ((this.bitField0_ & 512) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(11, this.folderType_);
            }
            if ((this.bitField0_ & 1024) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(12, this.stickDate_);
            }
            if ((this.bitField0_ & 2048) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(13, this.themeId_);
            }
            if ((this.bitField0_ & 4096) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(14, getTitle());
            }
            if ((this.bitField0_ & 8192) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(15, getNoteContentType());
            }
            if ((this.bitField0_ & 16384) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(16, getMindContent());
            }
            if ((this.bitField0_ & 32768) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(17, getMindContentPlainText());
            }
            if ((this.bitField0_ & 65536) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(18, getEntName());
            }
            if ((this.bitField0_ & 131072) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(19, getImages());
            }
            if ((this.bitField0_ & 262144) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(20, getThumbnail());
            }
            if ((this.bitField0_ & 524288) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(21, getRecognizedFileNames());
            }
            if ((this.bitField0_ & 1048576) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(22, this.isEmpty_);
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
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

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasGuid() {
                return ((NoteEntity) this.instance).hasGuid();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getGuid() {
                return ((NoteEntity) this.instance).getGuid();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getGuidBytes() {
                return ((NoteEntity) this.instance).getGuidBytes();
            }

            public Builder setGuid(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setGuid(str);
                return this;
            }

            public Builder clearGuid() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearGuid();
                return this;
            }

            public Builder setGuidBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setGuidBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasLuid() {
                return ((NoteEntity) this.instance).hasLuid();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getLuid() {
                return ((NoteEntity) this.instance).getLuid();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getLuidBytes() {
                return ((NoteEntity) this.instance).getLuidBytes();
            }

            public Builder setLuid(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setLuid(str);
                return this;
            }

            public Builder clearLuid() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearLuid();
                return this;
            }

            public Builder setLuidBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setLuidBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasAlertDate() {
                return ((NoteEntity) this.instance).hasAlertDate();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public long getAlertDate() {
                return ((NoteEntity) this.instance).getAlertDate();
            }

            public Builder setAlertDate(long j) {
                copyOnWrite();
                ((NoteEntity) this.instance).setAlertDate(j);
                return this;
            }

            public Builder clearAlertDate() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearAlertDate();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasBgColorId() {
                return ((NoteEntity) this.instance).hasBgColorId();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public int getBgColorId() {
                return ((NoteEntity) this.instance).getBgColorId();
            }

            public Builder setBgColorId(int i) {
                copyOnWrite();
                ((NoteEntity) this.instance).setBgColorId(i);
                return this;
            }

            public Builder clearBgColorId() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearBgColorId();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasCreatedDate() {
                return ((NoteEntity) this.instance).hasCreatedDate();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public long getCreatedDate() {
                return ((NoteEntity) this.instance).getCreatedDate();
            }

            public Builder setCreatedDate(long j) {
                copyOnWrite();
                ((NoteEntity) this.instance).setCreatedDate(j);
                return this;
            }

            public Builder clearCreatedDate() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearCreatedDate();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasModifiedDate() {
                return ((NoteEntity) this.instance).hasModifiedDate();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public long getModifiedDate() {
                return ((NoteEntity) this.instance).getModifiedDate();
            }

            public Builder setModifiedDate(long j) {
                copyOnWrite();
                ((NoteEntity) this.instance).setModifiedDate(j);
                return this;
            }

            public Builder clearModifiedDate() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearModifiedDate();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasHasAttachment() {
                return ((NoteEntity) this.instance).hasHasAttachment();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
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

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasSnippet() {
                return ((NoteEntity) this.instance).hasSnippet();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getSnippet() {
                return ((NoteEntity) this.instance).getSnippet();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getSnippetBytes() {
                return ((NoteEntity) this.instance).getSnippetBytes();
            }

            public Builder setSnippet(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setSnippet(str);
                return this;
            }

            public Builder clearSnippet() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearSnippet();
                return this;
            }

            public Builder setSnippetBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setSnippetBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public List<NoteData> getDataList() {
                return Collections.unmodifiableList(((NoteEntity) this.instance).getDataList());
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public int getDataCount() {
                return ((NoteEntity) this.instance).getDataCount();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
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

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasFolderTitle() {
                return ((NoteEntity) this.instance).hasFolderTitle();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getFolderTitle() {
                return ((NoteEntity) this.instance).getFolderTitle();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getFolderTitleBytes() {
                return ((NoteEntity) this.instance).getFolderTitleBytes();
            }

            public Builder setFolderTitle(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setFolderTitle(str);
                return this;
            }

            public Builder clearFolderTitle() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearFolderTitle();
                return this;
            }

            public Builder setFolderTitleBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setFolderTitleBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasFolderType() {
                return ((NoteEntity) this.instance).hasFolderType();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public int getFolderType() {
                return ((NoteEntity) this.instance).getFolderType();
            }

            public Builder setFolderType(int i) {
                copyOnWrite();
                ((NoteEntity) this.instance).setFolderType(i);
                return this;
            }

            public Builder clearFolderType() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearFolderType();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasStickDate() {
                return ((NoteEntity) this.instance).hasStickDate();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public long getStickDate() {
                return ((NoteEntity) this.instance).getStickDate();
            }

            public Builder setStickDate(long j) {
                copyOnWrite();
                ((NoteEntity) this.instance).setStickDate(j);
                return this;
            }

            public Builder clearStickDate() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearStickDate();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasThemeId() {
                return ((NoteEntity) this.instance).hasThemeId();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public int getThemeId() {
                return ((NoteEntity) this.instance).getThemeId();
            }

            public Builder setThemeId(int i) {
                copyOnWrite();
                ((NoteEntity) this.instance).setThemeId(i);
                return this;
            }

            public Builder clearThemeId() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearThemeId();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasTitle() {
                return ((NoteEntity) this.instance).hasTitle();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getTitle() {
                return ((NoteEntity) this.instance).getTitle();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getTitleBytes() {
                return ((NoteEntity) this.instance).getTitleBytes();
            }

            public Builder setTitle(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setTitle(str);
                return this;
            }

            public Builder clearTitle() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearTitle();
                return this;
            }

            public Builder setTitleBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setTitleBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasNoteContentType() {
                return ((NoteEntity) this.instance).hasNoteContentType();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getNoteContentType() {
                return ((NoteEntity) this.instance).getNoteContentType();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getNoteContentTypeBytes() {
                return ((NoteEntity) this.instance).getNoteContentTypeBytes();
            }

            public Builder setNoteContentType(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setNoteContentType(str);
                return this;
            }

            public Builder clearNoteContentType() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearNoteContentType();
                return this;
            }

            public Builder setNoteContentTypeBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setNoteContentTypeBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasMindContent() {
                return ((NoteEntity) this.instance).hasMindContent();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getMindContent() {
                return ((NoteEntity) this.instance).getMindContent();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getMindContentBytes() {
                return ((NoteEntity) this.instance).getMindContentBytes();
            }

            public Builder setMindContent(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setMindContent(str);
                return this;
            }

            public Builder clearMindContent() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearMindContent();
                return this;
            }

            public Builder setMindContentBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setMindContentBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasMindContentPlainText() {
                return ((NoteEntity) this.instance).hasMindContentPlainText();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getMindContentPlainText() {
                return ((NoteEntity) this.instance).getMindContentPlainText();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getMindContentPlainTextBytes() {
                return ((NoteEntity) this.instance).getMindContentPlainTextBytes();
            }

            public Builder setMindContentPlainText(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setMindContentPlainText(str);
                return this;
            }

            public Builder clearMindContentPlainText() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearMindContentPlainText();
                return this;
            }

            public Builder setMindContentPlainTextBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setMindContentPlainTextBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasEntName() {
                return ((NoteEntity) this.instance).hasEntName();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getEntName() {
                return ((NoteEntity) this.instance).getEntName();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getEntNameBytes() {
                return ((NoteEntity) this.instance).getEntNameBytes();
            }

            public Builder setEntName(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setEntName(str);
                return this;
            }

            public Builder clearEntName() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearEntName();
                return this;
            }

            public Builder setEntNameBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setEntNameBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasImages() {
                return ((NoteEntity) this.instance).hasImages();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getImages() {
                return ((NoteEntity) this.instance).getImages();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getImagesBytes() {
                return ((NoteEntity) this.instance).getImagesBytes();
            }

            public Builder setImages(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setImages(str);
                return this;
            }

            public Builder clearImages() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearImages();
                return this;
            }

            public Builder setImagesBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setImagesBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasThumbnail() {
                return ((NoteEntity) this.instance).hasThumbnail();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getThumbnail() {
                return ((NoteEntity) this.instance).getThumbnail();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getThumbnailBytes() {
                return ((NoteEntity) this.instance).getThumbnailBytes();
            }

            public Builder setThumbnail(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setThumbnail(str);
                return this;
            }

            public Builder clearThumbnail() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearThumbnail();
                return this;
            }

            public Builder setThumbnailBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setThumbnailBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasRecognizedFileNames() {
                return ((NoteEntity) this.instance).hasRecognizedFileNames();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public String getRecognizedFileNames() {
                return ((NoteEntity) this.instance).getRecognizedFileNames();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public ByteString getRecognizedFileNamesBytes() {
                return ((NoteEntity) this.instance).getRecognizedFileNamesBytes();
            }

            public Builder setRecognizedFileNames(String str) {
                copyOnWrite();
                ((NoteEntity) this.instance).setRecognizedFileNames(str);
                return this;
            }

            public Builder clearRecognizedFileNames() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearRecognizedFileNames();
                return this;
            }

            public Builder setRecognizedFileNamesBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteEntity) this.instance).setRecognizedFileNamesBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public boolean hasIsEmpty() {
                return ((NoteEntity) this.instance).hasIsEmpty();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteEntityOrBuilder
            public int getIsEmpty() {
                return ((NoteEntity) this.instance).getIsEmpty();
            }

            public Builder setIsEmpty(int i) {
                copyOnWrite();
                ((NoteEntity) this.instance).setIsEmpty(i);
                return this;
            }

            public Builder clearIsEmpty() {
                copyOnWrite();
                ((NoteEntity) this.instance).clearIsEmpty();
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
                    this.guid_ = visitor.visitString(hasGuid(), this.guid_, noteEntity.hasGuid(), noteEntity.guid_);
                    this.luid_ = visitor.visitString(hasLuid(), this.luid_, noteEntity.hasLuid(), noteEntity.luid_);
                    this.alertDate_ = visitor.visitLong(hasAlertDate(), this.alertDate_, noteEntity.hasAlertDate(), noteEntity.alertDate_);
                    this.bgColorId_ = visitor.visitInt(hasBgColorId(), this.bgColorId_, noteEntity.hasBgColorId(), noteEntity.bgColorId_);
                    this.createdDate_ = visitor.visitLong(hasCreatedDate(), this.createdDate_, noteEntity.hasCreatedDate(), noteEntity.createdDate_);
                    this.modifiedDate_ = visitor.visitLong(hasModifiedDate(), this.modifiedDate_, noteEntity.hasModifiedDate(), noteEntity.modifiedDate_);
                    this.hasAttachment_ = visitor.visitBoolean(hasHasAttachment(), this.hasAttachment_, noteEntity.hasHasAttachment(), noteEntity.hasAttachment_);
                    this.snippet_ = visitor.visitString(hasSnippet(), this.snippet_, noteEntity.hasSnippet(), noteEntity.snippet_);
                    this.data_ = visitor.visitList(this.data_, noteEntity.data_);
                    this.folderTitle_ = visitor.visitString(hasFolderTitle(), this.folderTitle_, noteEntity.hasFolderTitle(), noteEntity.folderTitle_);
                    this.folderType_ = visitor.visitInt(hasFolderType(), this.folderType_, noteEntity.hasFolderType(), noteEntity.folderType_);
                    this.stickDate_ = visitor.visitLong(hasStickDate(), this.stickDate_, noteEntity.hasStickDate(), noteEntity.stickDate_);
                    this.themeId_ = visitor.visitInt(hasThemeId(), this.themeId_, noteEntity.hasThemeId(), noteEntity.themeId_);
                    this.title_ = visitor.visitString(hasTitle(), this.title_, noteEntity.hasTitle(), noteEntity.title_);
                    this.noteContentType_ = visitor.visitString(hasNoteContentType(), this.noteContentType_, noteEntity.hasNoteContentType(), noteEntity.noteContentType_);
                    this.mindContent_ = visitor.visitString(hasMindContent(), this.mindContent_, noteEntity.hasMindContent(), noteEntity.mindContent_);
                    this.mindContentPlainText_ = visitor.visitString(hasMindContentPlainText(), this.mindContentPlainText_, noteEntity.hasMindContentPlainText(), noteEntity.mindContentPlainText_);
                    this.entName_ = visitor.visitString(hasEntName(), this.entName_, noteEntity.hasEntName(), noteEntity.entName_);
                    this.images_ = visitor.visitString(hasImages(), this.images_, noteEntity.hasImages(), noteEntity.images_);
                    this.thumbnail_ = visitor.visitString(hasThumbnail(), this.thumbnail_, noteEntity.hasThumbnail(), noteEntity.thumbnail_);
                    this.recognizedFileNames_ = visitor.visitString(hasRecognizedFileNames(), this.recognizedFileNames_, noteEntity.hasRecognizedFileNames(), noteEntity.recognizedFileNames_);
                    this.isEmpty_ = visitor.visitInt(hasIsEmpty(), this.isEmpty_, noteEntity.hasIsEmpty(), noteEntity.isEmpty_);
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
                            switch (tag) {
                                case 0:
                                    break;
                                case 10:
                                    String string = codedInputStream.readString();
                                    this.bitField0_ |= 1;
                                    this.guid_ = string;
                                    continue;
                                case 18:
                                    String string2 = codedInputStream.readString();
                                    this.bitField0_ |= 2;
                                    this.luid_ = string2;
                                    continue;
                                case 24:
                                    this.bitField0_ |= 4;
                                    this.alertDate_ = codedInputStream.readInt64();
                                    continue;
                                case 32:
                                    this.bitField0_ |= 8;
                                    this.bgColorId_ = codedInputStream.readInt32();
                                    continue;
                                case 40:
                                    this.bitField0_ |= 16;
                                    this.createdDate_ = codedInputStream.readInt64();
                                    continue;
                                case 48:
                                    this.bitField0_ |= 32;
                                    this.modifiedDate_ = codedInputStream.readInt64();
                                    continue;
                                case 56:
                                    this.bitField0_ |= 64;
                                    this.hasAttachment_ = codedInputStream.readBool();
                                    continue;
                                case 66:
                                    String string3 = codedInputStream.readString();
                                    this.bitField0_ |= 128;
                                    this.snippet_ = string3;
                                    continue;
                                case 74:
                                    if (!this.data_.isModifiable()) {
                                        this.data_ = GeneratedMessageLite.mutableCopy(this.data_);
                                    }
                                    this.data_.add((NoteData) codedInputStream.readMessage(NoteData.parser(), extensionRegistryLite));
                                    continue;
                                case 82:
                                    String string4 = codedInputStream.readString();
                                    this.bitField0_ |= 256;
                                    this.folderTitle_ = string4;
                                    continue;
                                case 88:
                                    this.bitField0_ |= 512;
                                    this.folderType_ = codedInputStream.readInt32();
                                    continue;
                                case 96:
                                    this.bitField0_ |= 1024;
                                    this.stickDate_ = codedInputStream.readInt64();
                                    continue;
                                case 104:
                                    this.bitField0_ |= 2048;
                                    this.themeId_ = codedInputStream.readInt32();
                                    continue;
                                case 114:
                                    String string5 = codedInputStream.readString();
                                    this.bitField0_ |= 4096;
                                    this.title_ = string5;
                                    continue;
                                case 122:
                                    String string6 = codedInputStream.readString();
                                    this.bitField0_ |= 8192;
                                    this.noteContentType_ = string6;
                                    continue;
                                case 130:
                                    String string7 = codedInputStream.readString();
                                    this.bitField0_ |= 16384;
                                    this.mindContent_ = string7;
                                    continue;
                                case R2.array.ai_edit_top_item_text_blend_colors /* 138 */:
                                    String string8 = codedInputStream.readString();
                                    this.bitField0_ |= 32768;
                                    this.mindContentPlainText_ = string8;
                                    continue;
                                case R2.array.ai_material_default_blend_modes /* 146 */:
                                    String string9 = codedInputStream.readString();
                                    this.bitField0_ |= 65536;
                                    this.entName_ = string9;
                                    continue;
                                case R2.array.ai_panel_widget_blend_modes /* 154 */:
                                    String string10 = codedInputStream.readString();
                                    this.bitField0_ |= 131072;
                                    this.images_ = string10;
                                    continue;
                                case R2.array.ai_select_button_colors /* 162 */:
                                    String string11 = codedInputStream.readString();
                                    this.bitField0_ |= 262144;
                                    this.thumbnail_ = string11;
                                    continue;
                                case R2.array.ai_shader_input_rim_colors /* 170 */:
                                    String string12 = codedInputStream.readString();
                                    this.bitField0_ |= 524288;
                                    this.recognizedFileNames_ = string12;
                                    continue;
                                case 176:
                                    this.bitField0_ |= 1048576;
                                    this.isEmpty_ = codedInputStream.readInt32();
                                    continue;
                                default:
                                    if (!parseUnknownField(tag, codedInputStream)) {
                                        break;
                                    }
                                    break;
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

    public static final class NoteFolder extends GeneratedMessageLite<NoteFolder, Builder> implements NoteFolderOrBuilder {
        public static final int ALERTDATE_FIELD_NUMBER = 3;
        public static final int BGCOLORID_FIELD_NUMBER = 4;
        public static final int CREATEDDATE_FIELD_NUMBER = 5;
        private static final NoteFolder DEFAULT_INSTANCE = new NoteFolder();
        public static final int GUID_FIELD_NUMBER = 1;
        public static final int HASATTACHMENT_FIELD_NUMBER = 7;
        public static final int LUID_FIELD_NUMBER = 2;
        public static final int MODIFIEDDATE_FIELD_NUMBER = 6;
        private static volatile Parser<NoteFolder> PARSER = null;
        public static final int SNIPPET_FIELD_NUMBER = 8;
        public static final int SUBJECT_FIELD_NUMBER = 9;
        private long alertDate_;
        private int bgColorId_;
        private int bitField0_;
        private long createdDate_;
        private boolean hasAttachment_;
        private long modifiedDate_;
        private String guid_ = "";
        private String luid_ = "";
        private String snippet_ = "";
        private String subject_ = "";

        private NoteFolder() {
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean hasGuid() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public String getGuid() {
            return this.guid_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public ByteString getGuidBytes() {
            return ByteString.copyFromUtf8(this.guid_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setGuid(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.guid_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearGuid() {
            this.bitField0_ &= -2;
            this.guid_ = getDefaultInstance().getGuid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setGuidBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 1;
            this.guid_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean hasLuid() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public String getLuid() {
            return this.luid_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public ByteString getLuidBytes() {
            return ByteString.copyFromUtf8(this.luid_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLuid(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.luid_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLuid() {
            this.bitField0_ &= -3;
            this.luid_ = getDefaultInstance().getLuid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLuidBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 2;
            this.luid_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean hasAlertDate() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public long getAlertDate() {
            return this.alertDate_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAlertDate(long j) {
            this.bitField0_ |= 4;
            this.alertDate_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAlertDate() {
            this.bitField0_ &= -5;
            this.alertDate_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean hasBgColorId() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public int getBgColorId() {
            return this.bgColorId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBgColorId(int i) {
            this.bitField0_ |= 8;
            this.bgColorId_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearBgColorId() {
            this.bitField0_ &= -9;
            this.bgColorId_ = 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean hasCreatedDate() {
            return (this.bitField0_ & 16) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public long getCreatedDate() {
            return this.createdDate_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCreatedDate(long j) {
            this.bitField0_ |= 16;
            this.createdDate_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCreatedDate() {
            this.bitField0_ &= -17;
            this.createdDate_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean hasModifiedDate() {
            return (this.bitField0_ & 32) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public long getModifiedDate() {
            return this.modifiedDate_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setModifiedDate(long j) {
            this.bitField0_ |= 32;
            this.modifiedDate_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearModifiedDate() {
            this.bitField0_ &= -33;
            this.modifiedDate_ = 0L;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean hasHasAttachment() {
            return (this.bitField0_ & 64) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean getHasAttachment() {
            return this.hasAttachment_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setHasAttachment(boolean z) {
            this.bitField0_ |= 64;
            this.hasAttachment_ = z;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHasAttachment() {
            this.bitField0_ &= -65;
            this.hasAttachment_ = false;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean hasSnippet() {
            return (this.bitField0_ & 128) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public String getSnippet() {
            return this.snippet_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public ByteString getSnippetBytes() {
            return ByteString.copyFromUtf8(this.snippet_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSnippet(String str) {
            str.getClass();
            this.bitField0_ |= 128;
            this.snippet_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSnippet() {
            this.bitField0_ &= -129;
            this.snippet_ = getDefaultInstance().getSnippet();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSnippetBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 128;
            this.snippet_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public boolean hasSubject() {
            return (this.bitField0_ & 256) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public String getSubject() {
            return this.subject_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
        public ByteString getSubjectBytes() {
            return ByteString.copyFromUtf8(this.subject_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSubject(String str) {
            str.getClass();
            this.bitField0_ |= 256;
            this.subject_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSubject() {
            this.bitField0_ &= -257;
            this.subject_ = getDefaultInstance().getSubject();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSubjectBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 256;
            this.subject_ = byteString.toStringUtf8();
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeString(1, getGuid());
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeString(2, getLuid());
            }
            if ((this.bitField0_ & 4) != 0) {
                codedOutputStream.writeInt64(3, this.alertDate_);
            }
            if ((this.bitField0_ & 8) != 0) {
                codedOutputStream.writeInt32(4, this.bgColorId_);
            }
            if ((this.bitField0_ & 16) != 0) {
                codedOutputStream.writeInt64(5, this.createdDate_);
            }
            if ((this.bitField0_ & 32) != 0) {
                codedOutputStream.writeInt64(6, this.modifiedDate_);
            }
            if ((this.bitField0_ & 64) != 0) {
                codedOutputStream.writeBool(7, this.hasAttachment_);
            }
            if ((this.bitField0_ & 128) != 0) {
                codedOutputStream.writeString(8, getSnippet());
            }
            if ((this.bitField0_ & 256) != 0) {
                codedOutputStream.writeString(9, getSubject());
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeStringSize = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeStringSize(1, getGuid()) : 0;
            if ((this.bitField0_ & 2) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(2, getLuid());
            }
            if ((this.bitField0_ & 4) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(3, this.alertDate_);
            }
            if ((this.bitField0_ & 8) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(4, this.bgColorId_);
            }
            if ((this.bitField0_ & 16) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(5, this.createdDate_);
            }
            if ((this.bitField0_ & 32) != 0) {
                iComputeStringSize += CodedOutputStream.computeInt64Size(6, this.modifiedDate_);
            }
            if ((this.bitField0_ & 64) != 0) {
                iComputeStringSize += CodedOutputStream.computeBoolSize(7, this.hasAttachment_);
            }
            if ((this.bitField0_ & 128) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(8, getSnippet());
            }
            if ((this.bitField0_ & 256) != 0) {
                iComputeStringSize += CodedOutputStream.computeStringSize(9, getSubject());
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static NoteFolder parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static NoteFolder parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static NoteFolder parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static NoteFolder parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static NoteFolder parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static NoteFolder parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static NoteFolder parseFrom(InputStream inputStream) throws IOException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static NoteFolder parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static NoteFolder parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (NoteFolder) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static NoteFolder parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NoteFolder) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static NoteFolder parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static NoteFolder parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NoteFolder) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(NoteFolder noteFolder) {
            return DEFAULT_INSTANCE.createBuilder(noteFolder);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<NoteFolder, Builder> implements NoteFolderOrBuilder {
            private Builder() {
                super(NoteFolder.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean hasGuid() {
                return ((NoteFolder) this.instance).hasGuid();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public String getGuid() {
                return ((NoteFolder) this.instance).getGuid();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public ByteString getGuidBytes() {
                return ((NoteFolder) this.instance).getGuidBytes();
            }

            public Builder setGuid(String str) {
                copyOnWrite();
                ((NoteFolder) this.instance).setGuid(str);
                return this;
            }

            public Builder clearGuid() {
                copyOnWrite();
                ((NoteFolder) this.instance).clearGuid();
                return this;
            }

            public Builder setGuidBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteFolder) this.instance).setGuidBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean hasLuid() {
                return ((NoteFolder) this.instance).hasLuid();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public String getLuid() {
                return ((NoteFolder) this.instance).getLuid();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public ByteString getLuidBytes() {
                return ((NoteFolder) this.instance).getLuidBytes();
            }

            public Builder setLuid(String str) {
                copyOnWrite();
                ((NoteFolder) this.instance).setLuid(str);
                return this;
            }

            public Builder clearLuid() {
                copyOnWrite();
                ((NoteFolder) this.instance).clearLuid();
                return this;
            }

            public Builder setLuidBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteFolder) this.instance).setLuidBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean hasAlertDate() {
                return ((NoteFolder) this.instance).hasAlertDate();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public long getAlertDate() {
                return ((NoteFolder) this.instance).getAlertDate();
            }

            public Builder setAlertDate(long j) {
                copyOnWrite();
                ((NoteFolder) this.instance).setAlertDate(j);
                return this;
            }

            public Builder clearAlertDate() {
                copyOnWrite();
                ((NoteFolder) this.instance).clearAlertDate();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean hasBgColorId() {
                return ((NoteFolder) this.instance).hasBgColorId();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public int getBgColorId() {
                return ((NoteFolder) this.instance).getBgColorId();
            }

            public Builder setBgColorId(int i) {
                copyOnWrite();
                ((NoteFolder) this.instance).setBgColorId(i);
                return this;
            }

            public Builder clearBgColorId() {
                copyOnWrite();
                ((NoteFolder) this.instance).clearBgColorId();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean hasCreatedDate() {
                return ((NoteFolder) this.instance).hasCreatedDate();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public long getCreatedDate() {
                return ((NoteFolder) this.instance).getCreatedDate();
            }

            public Builder setCreatedDate(long j) {
                copyOnWrite();
                ((NoteFolder) this.instance).setCreatedDate(j);
                return this;
            }

            public Builder clearCreatedDate() {
                copyOnWrite();
                ((NoteFolder) this.instance).clearCreatedDate();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean hasModifiedDate() {
                return ((NoteFolder) this.instance).hasModifiedDate();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public long getModifiedDate() {
                return ((NoteFolder) this.instance).getModifiedDate();
            }

            public Builder setModifiedDate(long j) {
                copyOnWrite();
                ((NoteFolder) this.instance).setModifiedDate(j);
                return this;
            }

            public Builder clearModifiedDate() {
                copyOnWrite();
                ((NoteFolder) this.instance).clearModifiedDate();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean hasHasAttachment() {
                return ((NoteFolder) this.instance).hasHasAttachment();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean getHasAttachment() {
                return ((NoteFolder) this.instance).getHasAttachment();
            }

            public Builder setHasAttachment(boolean z) {
                copyOnWrite();
                ((NoteFolder) this.instance).setHasAttachment(z);
                return this;
            }

            public Builder clearHasAttachment() {
                copyOnWrite();
                ((NoteFolder) this.instance).clearHasAttachment();
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean hasSnippet() {
                return ((NoteFolder) this.instance).hasSnippet();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public String getSnippet() {
                return ((NoteFolder) this.instance).getSnippet();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public ByteString getSnippetBytes() {
                return ((NoteFolder) this.instance).getSnippetBytes();
            }

            public Builder setSnippet(String str) {
                copyOnWrite();
                ((NoteFolder) this.instance).setSnippet(str);
                return this;
            }

            public Builder clearSnippet() {
                copyOnWrite();
                ((NoteFolder) this.instance).clearSnippet();
                return this;
            }

            public Builder setSnippetBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteFolder) this.instance).setSnippetBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public boolean hasSubject() {
                return ((NoteFolder) this.instance).hasSubject();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public String getSubject() {
                return ((NoteFolder) this.instance).getSubject();
            }

            @Override // com.miui.notes.backup.NoteProtos.NoteFolderOrBuilder
            public ByteString getSubjectBytes() {
                return ((NoteFolder) this.instance).getSubjectBytes();
            }

            public Builder setSubject(String str) {
                copyOnWrite();
                ((NoteFolder) this.instance).setSubject(str);
                return this;
            }

            public Builder clearSubject() {
                copyOnWrite();
                ((NoteFolder) this.instance).clearSubject();
                return this;
            }

            public Builder setSubjectBytes(ByteString byteString) {
                copyOnWrite();
                ((NoteFolder) this.instance).setSubjectBytes(byteString);
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new NoteFolder();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    NoteFolder noteFolder = (NoteFolder) obj2;
                    this.guid_ = visitor.visitString(hasGuid(), this.guid_, noteFolder.hasGuid(), noteFolder.guid_);
                    this.luid_ = visitor.visitString(hasLuid(), this.luid_, noteFolder.hasLuid(), noteFolder.luid_);
                    this.alertDate_ = visitor.visitLong(hasAlertDate(), this.alertDate_, noteFolder.hasAlertDate(), noteFolder.alertDate_);
                    this.bgColorId_ = visitor.visitInt(hasBgColorId(), this.bgColorId_, noteFolder.hasBgColorId(), noteFolder.bgColorId_);
                    this.createdDate_ = visitor.visitLong(hasCreatedDate(), this.createdDate_, noteFolder.hasCreatedDate(), noteFolder.createdDate_);
                    this.modifiedDate_ = visitor.visitLong(hasModifiedDate(), this.modifiedDate_, noteFolder.hasModifiedDate(), noteFolder.modifiedDate_);
                    this.hasAttachment_ = visitor.visitBoolean(hasHasAttachment(), this.hasAttachment_, noteFolder.hasHasAttachment(), noteFolder.hasAttachment_);
                    this.snippet_ = visitor.visitString(hasSnippet(), this.snippet_, noteFolder.hasSnippet(), noteFolder.snippet_);
                    this.subject_ = visitor.visitString(hasSubject(), this.subject_, noteFolder.hasSubject(), noteFolder.subject_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= noteFolder.bitField0_;
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
                                    this.guid_ = string;
                                } else if (tag == 18) {
                                    String string2 = codedInputStream.readString();
                                    this.bitField0_ |= 2;
                                    this.luid_ = string2;
                                } else if (tag == 24) {
                                    this.bitField0_ |= 4;
                                    this.alertDate_ = codedInputStream.readInt64();
                                } else if (tag == 32) {
                                    this.bitField0_ |= 8;
                                    this.bgColorId_ = codedInputStream.readInt32();
                                } else if (tag == 40) {
                                    this.bitField0_ |= 16;
                                    this.createdDate_ = codedInputStream.readInt64();
                                } else if (tag == 48) {
                                    this.bitField0_ |= 32;
                                    this.modifiedDate_ = codedInputStream.readInt64();
                                } else if (tag == 56) {
                                    this.bitField0_ |= 64;
                                    this.hasAttachment_ = codedInputStream.readBool();
                                } else if (tag == 66) {
                                    String string3 = codedInputStream.readString();
                                    this.bitField0_ |= 128;
                                    this.snippet_ = string3;
                                } else if (tag == 74) {
                                    String string4 = codedInputStream.readString();
                                    this.bitField0_ |= 256;
                                    this.subject_ = string4;
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
                    Parser<NoteFolder> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (NoteFolder.class) {
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

        public static NoteFolder getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<NoteFolder> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class Notes extends GeneratedMessageLite<Notes, Builder> implements NotesOrBuilder {
        private static final Notes DEFAULT_INSTANCE = new Notes();
        public static final int FOLDER_FIELD_NUMBER = 1;
        public static final int NOTE_FIELD_NUMBER = 2;
        private static volatile Parser<Notes> PARSER = null;
        public static final int VERSION_FIELD_NUMBER = 3;
        private int bitField0_;
        private Internal.ProtobufList<NoteFolder> folder_ = emptyProtobufList();
        private Internal.ProtobufList<NoteEntity> note_ = emptyProtobufList();
        private int version_;

        private Notes() {
        }

        @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
        public List<NoteFolder> getFolderList() {
            return this.folder_;
        }

        public List<? extends NoteFolderOrBuilder> getFolderOrBuilderList() {
            return this.folder_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
        public int getFolderCount() {
            return this.folder_.size();
        }

        @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
        public NoteFolder getFolder(int i) {
            return this.folder_.get(i);
        }

        public NoteFolderOrBuilder getFolderOrBuilder(int i) {
            return this.folder_.get(i);
        }

        private void ensureFolderIsMutable() {
            if (this.folder_.isModifiable()) {
                return;
            }
            this.folder_ = GeneratedMessageLite.mutableCopy(this.folder_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFolder(int i, NoteFolder noteFolder) {
            noteFolder.getClass();
            ensureFolderIsMutable();
            this.folder_.set(i, noteFolder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFolder(int i, NoteFolder.Builder builder) {
            ensureFolderIsMutable();
            this.folder_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addFolder(NoteFolder noteFolder) {
            noteFolder.getClass();
            ensureFolderIsMutable();
            this.folder_.add(noteFolder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addFolder(int i, NoteFolder noteFolder) {
            noteFolder.getClass();
            ensureFolderIsMutable();
            this.folder_.add(i, noteFolder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addFolder(NoteFolder.Builder builder) {
            ensureFolderIsMutable();
            this.folder_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addFolder(int i, NoteFolder.Builder builder) {
            ensureFolderIsMutable();
            this.folder_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllFolder(Iterable<? extends NoteFolder> iterable) {
            ensureFolderIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.folder_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFolder() {
            this.folder_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeFolder(int i) {
            ensureFolderIsMutable();
            this.folder_.remove(i);
        }

        @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
        public List<NoteEntity> getNoteList() {
            return this.note_;
        }

        public List<? extends NoteEntityOrBuilder> getNoteOrBuilderList() {
            return this.note_;
        }

        @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
        public int getNoteCount() {
            return this.note_.size();
        }

        @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
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

        @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
        public boolean hasVersion() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
        public int getVersion() {
            return this.version_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVersion(int i) {
            this.bitField0_ |= 1;
            this.version_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearVersion() {
            this.bitField0_ &= -2;
            this.version_ = 0;
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            for (int i = 0; i < this.folder_.size(); i++) {
                codedOutputStream.writeMessage(1, this.folder_.get(i));
            }
            for (int i2 = 0; i2 < this.note_.size(); i2++) {
                codedOutputStream.writeMessage(2, this.note_.get(i2));
            }
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeInt32(3, this.version_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeInt32Size = 0;
            for (int i2 = 0; i2 < this.folder_.size(); i2++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(1, this.folder_.get(i2));
            }
            for (int i3 = 0; i3 < this.note_.size(); i3++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.note_.get(i3));
            }
            if ((this.bitField0_ & 1) != 0) {
                iComputeInt32Size += CodedOutputStream.computeInt32Size(3, this.version_);
            }
            int serializedSize = iComputeInt32Size + this.unknownFields.getSerializedSize();
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

            @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
            public List<NoteFolder> getFolderList() {
                return Collections.unmodifiableList(((Notes) this.instance).getFolderList());
            }

            @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
            public int getFolderCount() {
                return ((Notes) this.instance).getFolderCount();
            }

            @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
            public NoteFolder getFolder(int i) {
                return ((Notes) this.instance).getFolder(i);
            }

            public Builder setFolder(int i, NoteFolder noteFolder) {
                copyOnWrite();
                ((Notes) this.instance).setFolder(i, noteFolder);
                return this;
            }

            public Builder setFolder(int i, NoteFolder.Builder builder) {
                copyOnWrite();
                ((Notes) this.instance).setFolder(i, builder);
                return this;
            }

            public Builder addFolder(NoteFolder noteFolder) {
                copyOnWrite();
                ((Notes) this.instance).addFolder(noteFolder);
                return this;
            }

            public Builder addFolder(int i, NoteFolder noteFolder) {
                copyOnWrite();
                ((Notes) this.instance).addFolder(i, noteFolder);
                return this;
            }

            public Builder addFolder(NoteFolder.Builder builder) {
                copyOnWrite();
                ((Notes) this.instance).addFolder(builder);
                return this;
            }

            public Builder addFolder(int i, NoteFolder.Builder builder) {
                copyOnWrite();
                ((Notes) this.instance).addFolder(i, builder);
                return this;
            }

            public Builder addAllFolder(Iterable<? extends NoteFolder> iterable) {
                copyOnWrite();
                ((Notes) this.instance).addAllFolder(iterable);
                return this;
            }

            public Builder clearFolder() {
                copyOnWrite();
                ((Notes) this.instance).clearFolder();
                return this;
            }

            public Builder removeFolder(int i) {
                copyOnWrite();
                ((Notes) this.instance).removeFolder(i);
                return this;
            }

            @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
            public List<NoteEntity> getNoteList() {
                return Collections.unmodifiableList(((Notes) this.instance).getNoteList());
            }

            @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
            public int getNoteCount() {
                return ((Notes) this.instance).getNoteCount();
            }

            @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
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

            @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
            public boolean hasVersion() {
                return ((Notes) this.instance).hasVersion();
            }

            @Override // com.miui.notes.backup.NoteProtos.NotesOrBuilder
            public int getVersion() {
                return ((Notes) this.instance).getVersion();
            }

            public Builder setVersion(int i) {
                copyOnWrite();
                ((Notes) this.instance).setVersion(i);
                return this;
            }

            public Builder clearVersion() {
                copyOnWrite();
                ((Notes) this.instance).clearVersion();
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
                    this.folder_.makeImmutable();
                    this.note_.makeImmutable();
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    Notes notes = (Notes) obj2;
                    this.folder_ = visitor.visitList(this.folder_, notes.folder_);
                    this.note_ = visitor.visitList(this.note_, notes.note_);
                    this.version_ = visitor.visitInt(hasVersion(), this.version_, notes.hasVersion(), notes.version_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= notes.bitField0_;
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
                                    if (!this.folder_.isModifiable()) {
                                        this.folder_ = GeneratedMessageLite.mutableCopy(this.folder_);
                                    }
                                    this.folder_.add((NoteFolder) codedInputStream.readMessage(NoteFolder.parser(), extensionRegistryLite));
                                } else if (tag == 18) {
                                    if (!this.note_.isModifiable()) {
                                        this.note_ = GeneratedMessageLite.mutableCopy(this.note_);
                                    }
                                    this.note_.add((NoteEntity) codedInputStream.readMessage(NoteEntity.parser(), extensionRegistryLite));
                                } else if (tag == 24) {
                                    this.bitField0_ |= 1;
                                    this.version_ = codedInputStream.readInt32();
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
}
