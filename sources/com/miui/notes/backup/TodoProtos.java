package com.miui.notes.backup;

import androidx.hardware.DataSpace;
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
import com.xiaomi.aicr.vision.VisionAttribute;
import defpackage.R2;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public final class TodoProtos {

    public interface TodoEntityOrBuilder extends MessageLiteOrBuilder {
        long getAccountId();

        String getAudioFileField();

        ByteString getAudioFileFieldBytes();

        String getAudioFileName();

        ByteString getAudioFileNameBytes();

        long getAudioFileSize();

        int getCategory();

        int getColorLabel();

        String getContent();

        ByteString getContentBytes();

        long getCreateTime();

        long getCustomSortId();

        long getExpiredTime();

        long getFirstRemindTime();

        int getFolderId();

        int getHideType();

        long getId();

        int getInputType();

        int getIsFinish();

        String getLabel();

        ByteString getLabelBytes();

        long getLastModifiedTime();

        int getListType();

        int getLocalStatus();

        long getMarkFinishTime();

        long getParenId();

        String getPlainText();

        ByteString getPlainTextBytes();

        int getPriority();

        int getRemindRepeatType();

        int getRemindTag();

        long getRemindTime();

        int getRemindType();

        int getServerStatus();

        String getSnippet();

        ByteString getSnippetBytes();

        long getSonId();

        long getSortId();

        int getSource();

        long getSyncEtag();

        int getSyncId();

        String getSyncTag();

        ByteString getSyncTagBytes();

        int getType();

        int getVersion();

        int getWordsCount();

        boolean hasAccountId();

        boolean hasAudioFileField();

        boolean hasAudioFileName();

        boolean hasAudioFileSize();

        boolean hasCategory();

        boolean hasColorLabel();

        boolean hasContent();

        boolean hasCreateTime();

        boolean hasCustomSortId();

        boolean hasExpiredTime();

        boolean hasFirstRemindTime();

        boolean hasFolderId();

        boolean hasHideType();

        boolean hasId();

        boolean hasInputType();

        boolean hasIsFinish();

        boolean hasLabel();

        boolean hasLastModifiedTime();

        boolean hasListType();

        boolean hasLocalStatus();

        boolean hasMarkFinishTime();

        boolean hasParenId();

        boolean hasPlainText();

        boolean hasPriority();

        boolean hasRemindRepeatType();

        boolean hasRemindTag();

        boolean hasRemindTime();

        boolean hasRemindType();

        boolean hasServerStatus();

        boolean hasSnippet();

        boolean hasSonId();

        boolean hasSortId();

        boolean hasSource();

        boolean hasSyncEtag();

        boolean hasSyncId();

        boolean hasSyncTag();

        boolean hasType();

        boolean hasVersion();

        boolean hasWordsCount();
    }

    public interface TodosOrBuilder extends MessageLiteOrBuilder {
        TodoEntity getTodo(int i);

        int getTodoCount();

        List<TodoEntity> getTodoList();

        int getVersion();

        boolean hasVersion();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private TodoProtos() {
    }

    public static final class TodoEntity extends GeneratedMessageLite<TodoEntity, Builder> implements TodoEntityOrBuilder {
        public static final int ACCOUNTID_FIELD_NUMBER = 27;
        public static final int AUDIOFILEFIELD_FIELD_NUMBER = 32;
        public static final int AUDIOFILENAME_FIELD_NUMBER = 11;
        public static final int AUDIOFILESIZE_FIELD_NUMBER = 37;
        public static final int CATEGORY_FIELD_NUMBER = 17;
        public static final int COLORLABEL_FIELD_NUMBER = 38;
        public static final int CONTENT_FIELD_NUMBER = 2;
        public static final int CREATETIME_FIELD_NUMBER = 9;
        public static final int CUSTOMSORTID_FIELD_NUMBER = 12;
        private static final TodoEntity DEFAULT_INSTANCE = new TodoEntity();
        public static final int EXPIREDTIME_FIELD_NUMBER = 4;
        public static final int FIRSTREMINDTIME_FIELD_NUMBER = 33;
        public static final int FOLDERID_FIELD_NUMBER = 34;
        public static final int HIDETYPE_FIELD_NUMBER = 14;
        public static final int ID_FIELD_NUMBER = 1;
        public static final int INPUTTYPE_FIELD_NUMBER = 10;
        public static final int ISFINISH_FIELD_NUMBER = 6;
        public static final int LABEL_FIELD_NUMBER = 18;
        public static final int LASTMODIFIEDTIME_FIELD_NUMBER = 8;
        public static final int LISTTYPE_FIELD_NUMBER = 39;
        public static final int LOCALSTATUS_FIELD_NUMBER = 35;
        public static final int MARKFINISHTIME_FIELD_NUMBER = 7;
        public static final int PARENID_FIELD_NUMBER = 23;
        private static volatile Parser<TodoEntity> PARSER = null;
        public static final int PLAINTEXT_FIELD_NUMBER = 13;
        public static final int PRIORITY_FIELD_NUMBER = 15;
        public static final int REMINDREPEATTYPE_FIELD_NUMBER = 16;
        public static final int REMINDTAG_FIELD_NUMBER = 21;
        public static final int REMINDTIME_FIELD_NUMBER = 5;
        public static final int REMINDTYPE_FIELD_NUMBER = 3;
        public static final int SERVERSTATUS_FIELD_NUMBER = 31;
        public static final int SNIPPET_FIELD_NUMBER = 29;
        public static final int SONID_FIELD_NUMBER = 24;
        public static final int SORTID_FIELD_NUMBER = 26;
        public static final int SOURCE_FIELD_NUMBER = 36;
        public static final int SYNCETAG_FIELD_NUMBER = 30;
        public static final int SYNCID_FIELD_NUMBER = 19;
        public static final int SYNCTAG_FIELD_NUMBER = 20;
        public static final int TYPE_FIELD_NUMBER = 25;
        public static final int VERSION_FIELD_NUMBER = 28;
        public static final int WORDSCOUNT_FIELD_NUMBER = 22;
        private long accountId_;
        private long audioFileSize_;
        private int bitField0_;
        private int bitField1_;
        private int category_;
        private int colorLabel_;
        private long createTime_;
        private long customSortId_;
        private long expiredTime_;
        private long firstRemindTime_;
        private int folderId_;
        private int hideType_;
        private long id_;
        private int inputType_;
        private int isFinish_;
        private long lastModifiedTime_;
        private int listType_;
        private int localStatus_;
        private long markFinishTime_;
        private long parenId_;
        private int priority_;
        private int remindRepeatType_;
        private int remindTag_;
        private long remindTime_;
        private int remindType_;
        private int serverStatus_;
        private long sonId_;
        private long sortId_;
        private int source_;
        private long syncEtag_;
        private int syncId_;
        private int type_;
        private int version_;
        private int wordsCount_;
        private String content_ = "";
        private String audioFileName_ = "";
        private String plainText_ = "";
        private String label_ = "";
        private String syncTag_ = "";
        private String snippet_ = "";
        private String audioFileField_ = "";

        private TodoEntity() {
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasId() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getId() {
            return this.id_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setId(long j) {
            this.bitField0_ |= 1;
            this.id_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearId() {
            this.bitField0_ &= -2;
            this.id_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasContent() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public String getContent() {
            return this.content_;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
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

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasRemindType() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getRemindType() {
            return this.remindType_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRemindType(int i) {
            this.bitField0_ |= 4;
            this.remindType_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRemindType() {
            this.bitField0_ &= -5;
            this.remindType_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasExpiredTime() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getExpiredTime() {
            return this.expiredTime_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setExpiredTime(long j) {
            this.bitField0_ |= 8;
            this.expiredTime_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearExpiredTime() {
            this.bitField0_ &= -9;
            this.expiredTime_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasRemindTime() {
            return (this.bitField0_ & 16) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getRemindTime() {
            return this.remindTime_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRemindTime(long j) {
            this.bitField0_ |= 16;
            this.remindTime_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRemindTime() {
            this.bitField0_ &= -17;
            this.remindTime_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasIsFinish() {
            return (this.bitField0_ & 32) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getIsFinish() {
            return this.isFinish_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIsFinish(int i) {
            this.bitField0_ |= 32;
            this.isFinish_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearIsFinish() {
            this.bitField0_ &= -33;
            this.isFinish_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasMarkFinishTime() {
            return (this.bitField0_ & 64) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getMarkFinishTime() {
            return this.markFinishTime_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMarkFinishTime(long j) {
            this.bitField0_ |= 64;
            this.markFinishTime_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMarkFinishTime() {
            this.bitField0_ &= -65;
            this.markFinishTime_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasLastModifiedTime() {
            return (this.bitField0_ & 128) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getLastModifiedTime() {
            return this.lastModifiedTime_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLastModifiedTime(long j) {
            this.bitField0_ |= 128;
            this.lastModifiedTime_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLastModifiedTime() {
            this.bitField0_ &= -129;
            this.lastModifiedTime_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasCreateTime() {
            return (this.bitField0_ & 256) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getCreateTime() {
            return this.createTime_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCreateTime(long j) {
            this.bitField0_ |= 256;
            this.createTime_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCreateTime() {
            this.bitField0_ &= -257;
            this.createTime_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasInputType() {
            return (this.bitField0_ & 512) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getInputType() {
            return this.inputType_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setInputType(int i) {
            this.bitField0_ |= 512;
            this.inputType_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearInputType() {
            this.bitField0_ &= -513;
            this.inputType_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasAudioFileName() {
            return (this.bitField0_ & 1024) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public String getAudioFileName() {
            return this.audioFileName_;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public ByteString getAudioFileNameBytes() {
            return ByteString.copyFromUtf8(this.audioFileName_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAudioFileName(String str) {
            str.getClass();
            this.bitField0_ |= 1024;
            this.audioFileName_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAudioFileName() {
            this.bitField0_ &= -1025;
            this.audioFileName_ = getDefaultInstance().getAudioFileName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAudioFileNameBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 1024;
            this.audioFileName_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasCustomSortId() {
            return (this.bitField0_ & 2048) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getCustomSortId() {
            return this.customSortId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCustomSortId(long j) {
            this.bitField0_ |= 2048;
            this.customSortId_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCustomSortId() {
            this.bitField0_ &= -2049;
            this.customSortId_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasPlainText() {
            return (this.bitField0_ & 4096) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public String getPlainText() {
            return this.plainText_;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public ByteString getPlainTextBytes() {
            return ByteString.copyFromUtf8(this.plainText_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPlainText(String str) {
            str.getClass();
            this.bitField0_ |= 4096;
            this.plainText_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPlainText() {
            this.bitField0_ &= -4097;
            this.plainText_ = getDefaultInstance().getPlainText();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPlainTextBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 4096;
            this.plainText_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasHideType() {
            return (this.bitField0_ & 8192) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getHideType() {
            return this.hideType_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setHideType(int i) {
            this.bitField0_ |= 8192;
            this.hideType_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHideType() {
            this.bitField0_ &= -8193;
            this.hideType_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasPriority() {
            return (this.bitField0_ & 16384) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getPriority() {
            return this.priority_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPriority(int i) {
            this.bitField0_ |= 16384;
            this.priority_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPriority() {
            this.bitField0_ &= -16385;
            this.priority_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasRemindRepeatType() {
            return (this.bitField0_ & 32768) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getRemindRepeatType() {
            return this.remindRepeatType_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRemindRepeatType(int i) {
            this.bitField0_ |= 32768;
            this.remindRepeatType_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRemindRepeatType() {
            this.bitField0_ &= -32769;
            this.remindRepeatType_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasCategory() {
            return (this.bitField0_ & 65536) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getCategory() {
            return this.category_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCategory(int i) {
            this.bitField0_ |= 65536;
            this.category_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCategory() {
            this.bitField0_ &= -65537;
            this.category_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasLabel() {
            return (this.bitField0_ & 131072) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public String getLabel() {
            return this.label_;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public ByteString getLabelBytes() {
            return ByteString.copyFromUtf8(this.label_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLabel(String str) {
            str.getClass();
            this.bitField0_ |= 131072;
            this.label_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLabel() {
            this.bitField0_ &= -131073;
            this.label_ = getDefaultInstance().getLabel();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLabelBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 131072;
            this.label_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasSyncId() {
            return (this.bitField0_ & 262144) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getSyncId() {
            return this.syncId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSyncId(int i) {
            this.bitField0_ |= 262144;
            this.syncId_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSyncId() {
            this.bitField0_ &= -262145;
            this.syncId_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasSyncTag() {
            return (this.bitField0_ & 524288) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public String getSyncTag() {
            return this.syncTag_;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public ByteString getSyncTagBytes() {
            return ByteString.copyFromUtf8(this.syncTag_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSyncTag(String str) {
            str.getClass();
            this.bitField0_ |= 524288;
            this.syncTag_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSyncTag() {
            this.bitField0_ &= -524289;
            this.syncTag_ = getDefaultInstance().getSyncTag();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSyncTagBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= 524288;
            this.syncTag_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasRemindTag() {
            return (this.bitField0_ & 1048576) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getRemindTag() {
            return this.remindTag_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRemindTag(int i) {
            this.bitField0_ |= 1048576;
            this.remindTag_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRemindTag() {
            this.bitField0_ &= -1048577;
            this.remindTag_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasWordsCount() {
            return (this.bitField0_ & 2097152) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getWordsCount() {
            return this.wordsCount_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setWordsCount(int i) {
            this.bitField0_ |= 2097152;
            this.wordsCount_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearWordsCount() {
            this.bitField0_ &= -2097153;
            this.wordsCount_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasParenId() {
            return (this.bitField0_ & 4194304) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getParenId() {
            return this.parenId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setParenId(long j) {
            this.bitField0_ |= 4194304;
            this.parenId_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearParenId() {
            this.bitField0_ &= -4194305;
            this.parenId_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasSonId() {
            return (this.bitField0_ & 8388608) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getSonId() {
            return this.sonId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSonId(long j) {
            this.bitField0_ |= 8388608;
            this.sonId_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSonId() {
            this.bitField0_ &= -8388609;
            this.sonId_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasType() {
            return (this.bitField0_ & 16777216) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getType() {
            return this.type_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setType(int i) {
            this.bitField0_ |= 16777216;
            this.type_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearType() {
            this.bitField0_ &= -16777217;
            this.type_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasSortId() {
            return (this.bitField0_ & 33554432) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getSortId() {
            return this.sortId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSortId(long j) {
            this.bitField0_ |= 33554432;
            this.sortId_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSortId() {
            this.bitField0_ &= -33554433;
            this.sortId_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasAccountId() {
            return (this.bitField0_ & 67108864) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getAccountId() {
            return this.accountId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAccountId(long j) {
            this.bitField0_ |= 67108864;
            this.accountId_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAccountId() {
            this.bitField0_ &= -67108865;
            this.accountId_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasVersion() {
            return (this.bitField0_ & DataSpace.RANGE_FULL) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getVersion() {
            return this.version_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVersion(int i) {
            this.bitField0_ |= DataSpace.RANGE_FULL;
            this.version_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearVersion() {
            this.bitField0_ &= -134217729;
            this.version_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasSnippet() {
            return (this.bitField0_ & DataSpace.RANGE_LIMITED) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public String getSnippet() {
            return this.snippet_;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public ByteString getSnippetBytes() {
            return ByteString.copyFromUtf8(this.snippet_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSnippet(String str) {
            str.getClass();
            this.bitField0_ |= DataSpace.RANGE_LIMITED;
            this.snippet_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSnippet() {
            this.bitField0_ &= -268435457;
            this.snippet_ = getDefaultInstance().getSnippet();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSnippetBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= DataSpace.RANGE_LIMITED;
            this.snippet_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasSyncEtag() {
            return (this.bitField0_ & VisionAttribute.VISION_TYPE_CLASS_ENTITY) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getSyncEtag() {
            return this.syncEtag_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSyncEtag(long j) {
            this.bitField0_ |= VisionAttribute.VISION_TYPE_CLASS_ENTITY;
            this.syncEtag_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSyncEtag() {
            this.bitField0_ &= -536870913;
            this.syncEtag_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasServerStatus() {
            return (this.bitField0_ & 1073741824) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getServerStatus() {
            return this.serverStatus_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setServerStatus(int i) {
            this.bitField0_ |= 1073741824;
            this.serverStatus_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearServerStatus() {
            this.bitField0_ &= -1073741825;
            this.serverStatus_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasAudioFileField() {
            return (this.bitField0_ & Integer.MIN_VALUE) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public String getAudioFileField() {
            return this.audioFileField_;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public ByteString getAudioFileFieldBytes() {
            return ByteString.copyFromUtf8(this.audioFileField_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAudioFileField(String str) {
            str.getClass();
            this.bitField0_ |= Integer.MIN_VALUE;
            this.audioFileField_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAudioFileField() {
            this.bitField0_ &= Integer.MAX_VALUE;
            this.audioFileField_ = getDefaultInstance().getAudioFileField();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAudioFileFieldBytes(ByteString byteString) {
            byteString.getClass();
            this.bitField0_ |= Integer.MIN_VALUE;
            this.audioFileField_ = byteString.toStringUtf8();
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasFirstRemindTime() {
            return (this.bitField1_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getFirstRemindTime() {
            return this.firstRemindTime_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFirstRemindTime(long j) {
            this.bitField1_ |= 1;
            this.firstRemindTime_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFirstRemindTime() {
            this.bitField1_ &= -2;
            this.firstRemindTime_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasFolderId() {
            return (this.bitField1_ & 2) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getFolderId() {
            return this.folderId_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFolderId(int i) {
            this.bitField1_ |= 2;
            this.folderId_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFolderId() {
            this.bitField1_ &= -3;
            this.folderId_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasLocalStatus() {
            return (this.bitField1_ & 4) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getLocalStatus() {
            return this.localStatus_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLocalStatus(int i) {
            this.bitField1_ |= 4;
            this.localStatus_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLocalStatus() {
            this.bitField1_ &= -5;
            this.localStatus_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasSource() {
            return (this.bitField1_ & 8) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getSource() {
            return this.source_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSource(int i) {
            this.bitField1_ |= 8;
            this.source_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSource() {
            this.bitField1_ &= -9;
            this.source_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasAudioFileSize() {
            return (this.bitField1_ & 16) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public long getAudioFileSize() {
            return this.audioFileSize_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAudioFileSize(long j) {
            this.bitField1_ |= 16;
            this.audioFileSize_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAudioFileSize() {
            this.bitField1_ &= -17;
            this.audioFileSize_ = 0L;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasColorLabel() {
            return (this.bitField1_ & 32) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getColorLabel() {
            return this.colorLabel_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setColorLabel(int i) {
            this.bitField1_ |= 32;
            this.colorLabel_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearColorLabel() {
            this.bitField1_ &= -33;
            this.colorLabel_ = 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public boolean hasListType() {
            return (this.bitField1_ & 64) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
        public int getListType() {
            return this.listType_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setListType(int i) {
            this.bitField1_ |= 64;
            this.listType_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearListType() {
            this.bitField1_ &= -65;
            this.listType_ = 0;
        }

        @Override // com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeInt64(1, this.id_);
            }
            if ((this.bitField0_ & 2) != 0) {
                codedOutputStream.writeString(2, getContent());
            }
            if ((this.bitField0_ & 4) != 0) {
                codedOutputStream.writeInt32(3, this.remindType_);
            }
            if ((this.bitField0_ & 8) != 0) {
                codedOutputStream.writeInt64(4, this.expiredTime_);
            }
            if ((this.bitField0_ & 16) != 0) {
                codedOutputStream.writeInt64(5, this.remindTime_);
            }
            if ((this.bitField0_ & 32) != 0) {
                codedOutputStream.writeInt32(6, this.isFinish_);
            }
            if ((this.bitField0_ & 64) != 0) {
                codedOutputStream.writeInt64(7, this.markFinishTime_);
            }
            if ((this.bitField0_ & 128) != 0) {
                codedOutputStream.writeInt64(8, this.lastModifiedTime_);
            }
            if ((this.bitField0_ & 256) != 0) {
                codedOutputStream.writeInt64(9, this.createTime_);
            }
            if ((this.bitField0_ & 512) != 0) {
                codedOutputStream.writeInt32(10, this.inputType_);
            }
            if ((this.bitField0_ & 1024) != 0) {
                codedOutputStream.writeString(11, getAudioFileName());
            }
            if ((this.bitField0_ & 2048) != 0) {
                codedOutputStream.writeInt64(12, this.customSortId_);
            }
            if ((this.bitField0_ & 4096) != 0) {
                codedOutputStream.writeString(13, getPlainText());
            }
            if ((this.bitField0_ & 8192) != 0) {
                codedOutputStream.writeInt32(14, this.hideType_);
            }
            if ((this.bitField0_ & 16384) != 0) {
                codedOutputStream.writeInt32(15, this.priority_);
            }
            if ((this.bitField0_ & 32768) != 0) {
                codedOutputStream.writeInt32(16, this.remindRepeatType_);
            }
            if ((this.bitField0_ & 65536) != 0) {
                codedOutputStream.writeInt32(17, this.category_);
            }
            if ((this.bitField0_ & 131072) != 0) {
                codedOutputStream.writeString(18, getLabel());
            }
            if ((this.bitField0_ & 262144) != 0) {
                codedOutputStream.writeInt32(19, this.syncId_);
            }
            if ((this.bitField0_ & 524288) != 0) {
                codedOutputStream.writeString(20, getSyncTag());
            }
            if ((this.bitField0_ & 1048576) != 0) {
                codedOutputStream.writeInt32(21, this.remindTag_);
            }
            if ((this.bitField0_ & 2097152) != 0) {
                codedOutputStream.writeInt32(22, this.wordsCount_);
            }
            if ((this.bitField0_ & 4194304) != 0) {
                codedOutputStream.writeInt64(23, this.parenId_);
            }
            if ((this.bitField0_ & 8388608) != 0) {
                codedOutputStream.writeInt64(24, this.sonId_);
            }
            if ((this.bitField0_ & 16777216) != 0) {
                codedOutputStream.writeInt32(25, this.type_);
            }
            if ((this.bitField0_ & 33554432) != 0) {
                codedOutputStream.writeInt64(26, this.sortId_);
            }
            if ((this.bitField0_ & 67108864) != 0) {
                codedOutputStream.writeInt64(27, this.accountId_);
            }
            if ((this.bitField0_ & DataSpace.RANGE_FULL) != 0) {
                codedOutputStream.writeInt32(28, this.version_);
            }
            if ((this.bitField0_ & DataSpace.RANGE_LIMITED) != 0) {
                codedOutputStream.writeString(29, getSnippet());
            }
            if ((this.bitField0_ & VisionAttribute.VISION_TYPE_CLASS_ENTITY) != 0) {
                codedOutputStream.writeInt64(30, this.syncEtag_);
            }
            if ((this.bitField0_ & 1073741824) != 0) {
                codedOutputStream.writeInt32(31, this.serverStatus_);
            }
            if ((this.bitField0_ & Integer.MIN_VALUE) != 0) {
                codedOutputStream.writeString(32, getAudioFileField());
            }
            if ((this.bitField1_ & 1) != 0) {
                codedOutputStream.writeInt64(33, this.firstRemindTime_);
            }
            if ((this.bitField1_ & 2) != 0) {
                codedOutputStream.writeInt32(34, this.folderId_);
            }
            if ((this.bitField1_ & 4) != 0) {
                codedOutputStream.writeInt32(35, this.localStatus_);
            }
            if ((this.bitField1_ & 8) != 0) {
                codedOutputStream.writeInt32(36, this.source_);
            }
            if ((this.bitField1_ & 16) != 0) {
                codedOutputStream.writeInt64(37, this.audioFileSize_);
            }
            if ((this.bitField1_ & 32) != 0) {
                codedOutputStream.writeInt32(38, this.colorLabel_);
            }
            if ((this.bitField1_ & 64) != 0) {
                codedOutputStream.writeInt32(39, this.listType_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeInt64Size = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeInt64Size(1, this.id_) : 0;
            if ((this.bitField0_ & 2) != 0) {
                iComputeInt64Size += CodedOutputStream.computeStringSize(2, getContent());
            }
            if ((this.bitField0_ & 4) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(3, this.remindType_);
            }
            if ((this.bitField0_ & 8) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(4, this.expiredTime_);
            }
            if ((this.bitField0_ & 16) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(5, this.remindTime_);
            }
            if ((this.bitField0_ & 32) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(6, this.isFinish_);
            }
            if ((this.bitField0_ & 64) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(7, this.markFinishTime_);
            }
            if ((this.bitField0_ & 128) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(8, this.lastModifiedTime_);
            }
            if ((this.bitField0_ & 256) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(9, this.createTime_);
            }
            if ((this.bitField0_ & 512) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(10, this.inputType_);
            }
            if ((this.bitField0_ & 1024) != 0) {
                iComputeInt64Size += CodedOutputStream.computeStringSize(11, getAudioFileName());
            }
            if ((this.bitField0_ & 2048) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(12, this.customSortId_);
            }
            if ((this.bitField0_ & 4096) != 0) {
                iComputeInt64Size += CodedOutputStream.computeStringSize(13, getPlainText());
            }
            if ((this.bitField0_ & 8192) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(14, this.hideType_);
            }
            if ((this.bitField0_ & 16384) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(15, this.priority_);
            }
            if ((this.bitField0_ & 32768) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(16, this.remindRepeatType_);
            }
            if ((this.bitField0_ & 65536) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(17, this.category_);
            }
            if ((this.bitField0_ & 131072) != 0) {
                iComputeInt64Size += CodedOutputStream.computeStringSize(18, getLabel());
            }
            if ((this.bitField0_ & 262144) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(19, this.syncId_);
            }
            if ((this.bitField0_ & 524288) != 0) {
                iComputeInt64Size += CodedOutputStream.computeStringSize(20, getSyncTag());
            }
            if ((this.bitField0_ & 1048576) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(21, this.remindTag_);
            }
            if ((this.bitField0_ & 2097152) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(22, this.wordsCount_);
            }
            if ((this.bitField0_ & 4194304) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(23, this.parenId_);
            }
            if ((this.bitField0_ & 8388608) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(24, this.sonId_);
            }
            if ((this.bitField0_ & 16777216) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(25, this.type_);
            }
            if ((this.bitField0_ & 33554432) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(26, this.sortId_);
            }
            if ((this.bitField0_ & 67108864) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(27, this.accountId_);
            }
            if ((this.bitField0_ & DataSpace.RANGE_FULL) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(28, this.version_);
            }
            if ((this.bitField0_ & DataSpace.RANGE_LIMITED) != 0) {
                iComputeInt64Size += CodedOutputStream.computeStringSize(29, getSnippet());
            }
            if ((this.bitField0_ & VisionAttribute.VISION_TYPE_CLASS_ENTITY) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(30, this.syncEtag_);
            }
            if ((this.bitField0_ & 1073741824) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(31, this.serverStatus_);
            }
            if ((this.bitField0_ & Integer.MIN_VALUE) != 0) {
                iComputeInt64Size += CodedOutputStream.computeStringSize(32, getAudioFileField());
            }
            if ((1 & this.bitField1_) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(33, this.firstRemindTime_);
            }
            if ((this.bitField1_ & 2) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(34, this.folderId_);
            }
            if ((this.bitField1_ & 4) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(35, this.localStatus_);
            }
            if ((this.bitField1_ & 8) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(36, this.source_);
            }
            if ((this.bitField1_ & 16) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt64Size(37, this.audioFileSize_);
            }
            if ((this.bitField1_ & 32) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(38, this.colorLabel_);
            }
            if ((this.bitField1_ & 64) != 0) {
                iComputeInt64Size += CodedOutputStream.computeInt32Size(39, this.listType_);
            }
            int serializedSize = iComputeInt64Size + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static TodoEntity parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static TodoEntity parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static TodoEntity parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static TodoEntity parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static TodoEntity parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static TodoEntity parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static TodoEntity parseFrom(InputStream inputStream) throws IOException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static TodoEntity parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static TodoEntity parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (TodoEntity) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static TodoEntity parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (TodoEntity) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static TodoEntity parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static TodoEntity parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (TodoEntity) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TodoEntity todoEntity) {
            return DEFAULT_INSTANCE.createBuilder(todoEntity);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<TodoEntity, Builder> implements TodoEntityOrBuilder {
            private Builder() {
                super(TodoEntity.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasId() {
                return ((TodoEntity) this.instance).hasId();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getId() {
                return ((TodoEntity) this.instance).getId();
            }

            public Builder setId(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setId(j);
                return this;
            }

            public Builder clearId() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearId();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasContent() {
                return ((TodoEntity) this.instance).hasContent();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public String getContent() {
                return ((TodoEntity) this.instance).getContent();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public ByteString getContentBytes() {
                return ((TodoEntity) this.instance).getContentBytes();
            }

            public Builder setContent(String str) {
                copyOnWrite();
                ((TodoEntity) this.instance).setContent(str);
                return this;
            }

            public Builder clearContent() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearContent();
                return this;
            }

            public Builder setContentBytes(ByteString byteString) {
                copyOnWrite();
                ((TodoEntity) this.instance).setContentBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasRemindType() {
                return ((TodoEntity) this.instance).hasRemindType();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getRemindType() {
                return ((TodoEntity) this.instance).getRemindType();
            }

            public Builder setRemindType(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setRemindType(i);
                return this;
            }

            public Builder clearRemindType() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearRemindType();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasExpiredTime() {
                return ((TodoEntity) this.instance).hasExpiredTime();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getExpiredTime() {
                return ((TodoEntity) this.instance).getExpiredTime();
            }

            public Builder setExpiredTime(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setExpiredTime(j);
                return this;
            }

            public Builder clearExpiredTime() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearExpiredTime();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasRemindTime() {
                return ((TodoEntity) this.instance).hasRemindTime();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getRemindTime() {
                return ((TodoEntity) this.instance).getRemindTime();
            }

            public Builder setRemindTime(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setRemindTime(j);
                return this;
            }

            public Builder clearRemindTime() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearRemindTime();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasIsFinish() {
                return ((TodoEntity) this.instance).hasIsFinish();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getIsFinish() {
                return ((TodoEntity) this.instance).getIsFinish();
            }

            public Builder setIsFinish(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setIsFinish(i);
                return this;
            }

            public Builder clearIsFinish() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearIsFinish();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasMarkFinishTime() {
                return ((TodoEntity) this.instance).hasMarkFinishTime();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getMarkFinishTime() {
                return ((TodoEntity) this.instance).getMarkFinishTime();
            }

            public Builder setMarkFinishTime(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setMarkFinishTime(j);
                return this;
            }

            public Builder clearMarkFinishTime() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearMarkFinishTime();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasLastModifiedTime() {
                return ((TodoEntity) this.instance).hasLastModifiedTime();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getLastModifiedTime() {
                return ((TodoEntity) this.instance).getLastModifiedTime();
            }

            public Builder setLastModifiedTime(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setLastModifiedTime(j);
                return this;
            }

            public Builder clearLastModifiedTime() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearLastModifiedTime();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasCreateTime() {
                return ((TodoEntity) this.instance).hasCreateTime();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getCreateTime() {
                return ((TodoEntity) this.instance).getCreateTime();
            }

            public Builder setCreateTime(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setCreateTime(j);
                return this;
            }

            public Builder clearCreateTime() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearCreateTime();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasInputType() {
                return ((TodoEntity) this.instance).hasInputType();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getInputType() {
                return ((TodoEntity) this.instance).getInputType();
            }

            public Builder setInputType(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setInputType(i);
                return this;
            }

            public Builder clearInputType() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearInputType();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasAudioFileName() {
                return ((TodoEntity) this.instance).hasAudioFileName();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public String getAudioFileName() {
                return ((TodoEntity) this.instance).getAudioFileName();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public ByteString getAudioFileNameBytes() {
                return ((TodoEntity) this.instance).getAudioFileNameBytes();
            }

            public Builder setAudioFileName(String str) {
                copyOnWrite();
                ((TodoEntity) this.instance).setAudioFileName(str);
                return this;
            }

            public Builder clearAudioFileName() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearAudioFileName();
                return this;
            }

            public Builder setAudioFileNameBytes(ByteString byteString) {
                copyOnWrite();
                ((TodoEntity) this.instance).setAudioFileNameBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasCustomSortId() {
                return ((TodoEntity) this.instance).hasCustomSortId();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getCustomSortId() {
                return ((TodoEntity) this.instance).getCustomSortId();
            }

            public Builder setCustomSortId(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setCustomSortId(j);
                return this;
            }

            public Builder clearCustomSortId() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearCustomSortId();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasPlainText() {
                return ((TodoEntity) this.instance).hasPlainText();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public String getPlainText() {
                return ((TodoEntity) this.instance).getPlainText();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public ByteString getPlainTextBytes() {
                return ((TodoEntity) this.instance).getPlainTextBytes();
            }

            public Builder setPlainText(String str) {
                copyOnWrite();
                ((TodoEntity) this.instance).setPlainText(str);
                return this;
            }

            public Builder clearPlainText() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearPlainText();
                return this;
            }

            public Builder setPlainTextBytes(ByteString byteString) {
                copyOnWrite();
                ((TodoEntity) this.instance).setPlainTextBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasHideType() {
                return ((TodoEntity) this.instance).hasHideType();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getHideType() {
                return ((TodoEntity) this.instance).getHideType();
            }

            public Builder setHideType(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setHideType(i);
                return this;
            }

            public Builder clearHideType() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearHideType();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasPriority() {
                return ((TodoEntity) this.instance).hasPriority();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getPriority() {
                return ((TodoEntity) this.instance).getPriority();
            }

            public Builder setPriority(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setPriority(i);
                return this;
            }

            public Builder clearPriority() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearPriority();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasRemindRepeatType() {
                return ((TodoEntity) this.instance).hasRemindRepeatType();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getRemindRepeatType() {
                return ((TodoEntity) this.instance).getRemindRepeatType();
            }

            public Builder setRemindRepeatType(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setRemindRepeatType(i);
                return this;
            }

            public Builder clearRemindRepeatType() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearRemindRepeatType();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasCategory() {
                return ((TodoEntity) this.instance).hasCategory();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getCategory() {
                return ((TodoEntity) this.instance).getCategory();
            }

            public Builder setCategory(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setCategory(i);
                return this;
            }

            public Builder clearCategory() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearCategory();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasLabel() {
                return ((TodoEntity) this.instance).hasLabel();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public String getLabel() {
                return ((TodoEntity) this.instance).getLabel();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public ByteString getLabelBytes() {
                return ((TodoEntity) this.instance).getLabelBytes();
            }

            public Builder setLabel(String str) {
                copyOnWrite();
                ((TodoEntity) this.instance).setLabel(str);
                return this;
            }

            public Builder clearLabel() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearLabel();
                return this;
            }

            public Builder setLabelBytes(ByteString byteString) {
                copyOnWrite();
                ((TodoEntity) this.instance).setLabelBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasSyncId() {
                return ((TodoEntity) this.instance).hasSyncId();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getSyncId() {
                return ((TodoEntity) this.instance).getSyncId();
            }

            public Builder setSyncId(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setSyncId(i);
                return this;
            }

            public Builder clearSyncId() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearSyncId();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasSyncTag() {
                return ((TodoEntity) this.instance).hasSyncTag();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public String getSyncTag() {
                return ((TodoEntity) this.instance).getSyncTag();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public ByteString getSyncTagBytes() {
                return ((TodoEntity) this.instance).getSyncTagBytes();
            }

            public Builder setSyncTag(String str) {
                copyOnWrite();
                ((TodoEntity) this.instance).setSyncTag(str);
                return this;
            }

            public Builder clearSyncTag() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearSyncTag();
                return this;
            }

            public Builder setSyncTagBytes(ByteString byteString) {
                copyOnWrite();
                ((TodoEntity) this.instance).setSyncTagBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasRemindTag() {
                return ((TodoEntity) this.instance).hasRemindTag();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getRemindTag() {
                return ((TodoEntity) this.instance).getRemindTag();
            }

            public Builder setRemindTag(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setRemindTag(i);
                return this;
            }

            public Builder clearRemindTag() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearRemindTag();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasWordsCount() {
                return ((TodoEntity) this.instance).hasWordsCount();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getWordsCount() {
                return ((TodoEntity) this.instance).getWordsCount();
            }

            public Builder setWordsCount(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setWordsCount(i);
                return this;
            }

            public Builder clearWordsCount() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearWordsCount();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasParenId() {
                return ((TodoEntity) this.instance).hasParenId();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getParenId() {
                return ((TodoEntity) this.instance).getParenId();
            }

            public Builder setParenId(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setParenId(j);
                return this;
            }

            public Builder clearParenId() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearParenId();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasSonId() {
                return ((TodoEntity) this.instance).hasSonId();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getSonId() {
                return ((TodoEntity) this.instance).getSonId();
            }

            public Builder setSonId(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setSonId(j);
                return this;
            }

            public Builder clearSonId() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearSonId();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasType() {
                return ((TodoEntity) this.instance).hasType();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getType() {
                return ((TodoEntity) this.instance).getType();
            }

            public Builder setType(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setType(i);
                return this;
            }

            public Builder clearType() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearType();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasSortId() {
                return ((TodoEntity) this.instance).hasSortId();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getSortId() {
                return ((TodoEntity) this.instance).getSortId();
            }

            public Builder setSortId(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setSortId(j);
                return this;
            }

            public Builder clearSortId() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearSortId();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasAccountId() {
                return ((TodoEntity) this.instance).hasAccountId();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getAccountId() {
                return ((TodoEntity) this.instance).getAccountId();
            }

            public Builder setAccountId(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setAccountId(j);
                return this;
            }

            public Builder clearAccountId() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearAccountId();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasVersion() {
                return ((TodoEntity) this.instance).hasVersion();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getVersion() {
                return ((TodoEntity) this.instance).getVersion();
            }

            public Builder setVersion(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setVersion(i);
                return this;
            }

            public Builder clearVersion() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearVersion();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasSnippet() {
                return ((TodoEntity) this.instance).hasSnippet();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public String getSnippet() {
                return ((TodoEntity) this.instance).getSnippet();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public ByteString getSnippetBytes() {
                return ((TodoEntity) this.instance).getSnippetBytes();
            }

            public Builder setSnippet(String str) {
                copyOnWrite();
                ((TodoEntity) this.instance).setSnippet(str);
                return this;
            }

            public Builder clearSnippet() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearSnippet();
                return this;
            }

            public Builder setSnippetBytes(ByteString byteString) {
                copyOnWrite();
                ((TodoEntity) this.instance).setSnippetBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasSyncEtag() {
                return ((TodoEntity) this.instance).hasSyncEtag();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getSyncEtag() {
                return ((TodoEntity) this.instance).getSyncEtag();
            }

            public Builder setSyncEtag(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setSyncEtag(j);
                return this;
            }

            public Builder clearSyncEtag() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearSyncEtag();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasServerStatus() {
                return ((TodoEntity) this.instance).hasServerStatus();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getServerStatus() {
                return ((TodoEntity) this.instance).getServerStatus();
            }

            public Builder setServerStatus(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setServerStatus(i);
                return this;
            }

            public Builder clearServerStatus() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearServerStatus();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasAudioFileField() {
                return ((TodoEntity) this.instance).hasAudioFileField();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public String getAudioFileField() {
                return ((TodoEntity) this.instance).getAudioFileField();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public ByteString getAudioFileFieldBytes() {
                return ((TodoEntity) this.instance).getAudioFileFieldBytes();
            }

            public Builder setAudioFileField(String str) {
                copyOnWrite();
                ((TodoEntity) this.instance).setAudioFileField(str);
                return this;
            }

            public Builder clearAudioFileField() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearAudioFileField();
                return this;
            }

            public Builder setAudioFileFieldBytes(ByteString byteString) {
                copyOnWrite();
                ((TodoEntity) this.instance).setAudioFileFieldBytes(byteString);
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasFirstRemindTime() {
                return ((TodoEntity) this.instance).hasFirstRemindTime();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getFirstRemindTime() {
                return ((TodoEntity) this.instance).getFirstRemindTime();
            }

            public Builder setFirstRemindTime(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setFirstRemindTime(j);
                return this;
            }

            public Builder clearFirstRemindTime() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearFirstRemindTime();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasFolderId() {
                return ((TodoEntity) this.instance).hasFolderId();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getFolderId() {
                return ((TodoEntity) this.instance).getFolderId();
            }

            public Builder setFolderId(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setFolderId(i);
                return this;
            }

            public Builder clearFolderId() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearFolderId();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasLocalStatus() {
                return ((TodoEntity) this.instance).hasLocalStatus();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getLocalStatus() {
                return ((TodoEntity) this.instance).getLocalStatus();
            }

            public Builder setLocalStatus(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setLocalStatus(i);
                return this;
            }

            public Builder clearLocalStatus() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearLocalStatus();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasSource() {
                return ((TodoEntity) this.instance).hasSource();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getSource() {
                return ((TodoEntity) this.instance).getSource();
            }

            public Builder setSource(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setSource(i);
                return this;
            }

            public Builder clearSource() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearSource();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasAudioFileSize() {
                return ((TodoEntity) this.instance).hasAudioFileSize();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public long getAudioFileSize() {
                return ((TodoEntity) this.instance).getAudioFileSize();
            }

            public Builder setAudioFileSize(long j) {
                copyOnWrite();
                ((TodoEntity) this.instance).setAudioFileSize(j);
                return this;
            }

            public Builder clearAudioFileSize() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearAudioFileSize();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasColorLabel() {
                return ((TodoEntity) this.instance).hasColorLabel();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getColorLabel() {
                return ((TodoEntity) this.instance).getColorLabel();
            }

            public Builder setColorLabel(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setColorLabel(i);
                return this;
            }

            public Builder clearColorLabel() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearColorLabel();
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public boolean hasListType() {
                return ((TodoEntity) this.instance).hasListType();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodoEntityOrBuilder
            public int getListType() {
                return ((TodoEntity) this.instance).getListType();
            }

            public Builder setListType(int i) {
                copyOnWrite();
                ((TodoEntity) this.instance).setListType(i);
                return this;
            }

            public Builder clearListType() {
                copyOnWrite();
                ((TodoEntity) this.instance).clearListType();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new TodoEntity();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    TodoEntity todoEntity = (TodoEntity) obj2;
                    this.id_ = visitor.visitLong(hasId(), this.id_, todoEntity.hasId(), todoEntity.id_);
                    this.content_ = visitor.visitString(hasContent(), this.content_, todoEntity.hasContent(), todoEntity.content_);
                    this.remindType_ = visitor.visitInt(hasRemindType(), this.remindType_, todoEntity.hasRemindType(), todoEntity.remindType_);
                    this.expiredTime_ = visitor.visitLong(hasExpiredTime(), this.expiredTime_, todoEntity.hasExpiredTime(), todoEntity.expiredTime_);
                    this.remindTime_ = visitor.visitLong(hasRemindTime(), this.remindTime_, todoEntity.hasRemindTime(), todoEntity.remindTime_);
                    this.isFinish_ = visitor.visitInt(hasIsFinish(), this.isFinish_, todoEntity.hasIsFinish(), todoEntity.isFinish_);
                    this.markFinishTime_ = visitor.visitLong(hasMarkFinishTime(), this.markFinishTime_, todoEntity.hasMarkFinishTime(), todoEntity.markFinishTime_);
                    this.lastModifiedTime_ = visitor.visitLong(hasLastModifiedTime(), this.lastModifiedTime_, todoEntity.hasLastModifiedTime(), todoEntity.lastModifiedTime_);
                    this.createTime_ = visitor.visitLong(hasCreateTime(), this.createTime_, todoEntity.hasCreateTime(), todoEntity.createTime_);
                    this.inputType_ = visitor.visitInt(hasInputType(), this.inputType_, todoEntity.hasInputType(), todoEntity.inputType_);
                    this.audioFileName_ = visitor.visitString(hasAudioFileName(), this.audioFileName_, todoEntity.hasAudioFileName(), todoEntity.audioFileName_);
                    this.customSortId_ = visitor.visitLong(hasCustomSortId(), this.customSortId_, todoEntity.hasCustomSortId(), todoEntity.customSortId_);
                    this.plainText_ = visitor.visitString(hasPlainText(), this.plainText_, todoEntity.hasPlainText(), todoEntity.plainText_);
                    this.hideType_ = visitor.visitInt(hasHideType(), this.hideType_, todoEntity.hasHideType(), todoEntity.hideType_);
                    this.priority_ = visitor.visitInt(hasPriority(), this.priority_, todoEntity.hasPriority(), todoEntity.priority_);
                    this.remindRepeatType_ = visitor.visitInt(hasRemindRepeatType(), this.remindRepeatType_, todoEntity.hasRemindRepeatType(), todoEntity.remindRepeatType_);
                    this.category_ = visitor.visitInt(hasCategory(), this.category_, todoEntity.hasCategory(), todoEntity.category_);
                    this.label_ = visitor.visitString(hasLabel(), this.label_, todoEntity.hasLabel(), todoEntity.label_);
                    this.syncId_ = visitor.visitInt(hasSyncId(), this.syncId_, todoEntity.hasSyncId(), todoEntity.syncId_);
                    this.syncTag_ = visitor.visitString(hasSyncTag(), this.syncTag_, todoEntity.hasSyncTag(), todoEntity.syncTag_);
                    this.remindTag_ = visitor.visitInt(hasRemindTag(), this.remindTag_, todoEntity.hasRemindTag(), todoEntity.remindTag_);
                    this.wordsCount_ = visitor.visitInt(hasWordsCount(), this.wordsCount_, todoEntity.hasWordsCount(), todoEntity.wordsCount_);
                    this.parenId_ = visitor.visitLong(hasParenId(), this.parenId_, todoEntity.hasParenId(), todoEntity.parenId_);
                    this.sonId_ = visitor.visitLong(hasSonId(), this.sonId_, todoEntity.hasSonId(), todoEntity.sonId_);
                    this.type_ = visitor.visitInt(hasType(), this.type_, todoEntity.hasType(), todoEntity.type_);
                    this.sortId_ = visitor.visitLong(hasSortId(), this.sortId_, todoEntity.hasSortId(), todoEntity.sortId_);
                    this.accountId_ = visitor.visitLong(hasAccountId(), this.accountId_, todoEntity.hasAccountId(), todoEntity.accountId_);
                    this.version_ = visitor.visitInt(hasVersion(), this.version_, todoEntity.hasVersion(), todoEntity.version_);
                    this.snippet_ = visitor.visitString(hasSnippet(), this.snippet_, todoEntity.hasSnippet(), todoEntity.snippet_);
                    this.syncEtag_ = visitor.visitLong(hasSyncEtag(), this.syncEtag_, todoEntity.hasSyncEtag(), todoEntity.syncEtag_);
                    this.serverStatus_ = visitor.visitInt(hasServerStatus(), this.serverStatus_, todoEntity.hasServerStatus(), todoEntity.serverStatus_);
                    this.audioFileField_ = visitor.visitString(hasAudioFileField(), this.audioFileField_, todoEntity.hasAudioFileField(), todoEntity.audioFileField_);
                    this.firstRemindTime_ = visitor.visitLong(hasFirstRemindTime(), this.firstRemindTime_, todoEntity.hasFirstRemindTime(), todoEntity.firstRemindTime_);
                    this.folderId_ = visitor.visitInt(hasFolderId(), this.folderId_, todoEntity.hasFolderId(), todoEntity.folderId_);
                    this.localStatus_ = visitor.visitInt(hasLocalStatus(), this.localStatus_, todoEntity.hasLocalStatus(), todoEntity.localStatus_);
                    this.source_ = visitor.visitInt(hasSource(), this.source_, todoEntity.hasSource(), todoEntity.source_);
                    this.audioFileSize_ = visitor.visitLong(hasAudioFileSize(), this.audioFileSize_, todoEntity.hasAudioFileSize(), todoEntity.audioFileSize_);
                    this.colorLabel_ = visitor.visitInt(hasColorLabel(), this.colorLabel_, todoEntity.hasColorLabel(), todoEntity.colorLabel_);
                    this.listType_ = visitor.visitInt(hasListType(), this.listType_, todoEntity.hasListType(), todoEntity.listType_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= todoEntity.bitField0_;
                        this.bitField1_ |= todoEntity.bitField1_;
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
                                case 8:
                                    this.bitField0_ |= 1;
                                    this.id_ = codedInputStream.readInt64();
                                    continue;
                                case 18:
                                    String string = codedInputStream.readString();
                                    this.bitField0_ |= 2;
                                    this.content_ = string;
                                    continue;
                                case 24:
                                    this.bitField0_ |= 4;
                                    this.remindType_ = codedInputStream.readInt32();
                                    continue;
                                case 32:
                                    this.bitField0_ |= 8;
                                    this.expiredTime_ = codedInputStream.readInt64();
                                    continue;
                                case 40:
                                    this.bitField0_ |= 16;
                                    this.remindTime_ = codedInputStream.readInt64();
                                    continue;
                                case 48:
                                    this.bitField0_ |= 32;
                                    this.isFinish_ = codedInputStream.readInt32();
                                    continue;
                                case 56:
                                    this.bitField0_ |= 64;
                                    this.markFinishTime_ = codedInputStream.readInt64();
                                    continue;
                                case 64:
                                    this.bitField0_ |= 128;
                                    this.lastModifiedTime_ = codedInputStream.readInt64();
                                    continue;
                                case 72:
                                    this.bitField0_ |= 256;
                                    this.createTime_ = codedInputStream.readInt64();
                                    continue;
                                case 80:
                                    this.bitField0_ |= 512;
                                    this.inputType_ = codedInputStream.readInt32();
                                    continue;
                                case 90:
                                    String string2 = codedInputStream.readString();
                                    this.bitField0_ |= 1024;
                                    this.audioFileName_ = string2;
                                    continue;
                                case 96:
                                    this.bitField0_ |= 2048;
                                    this.customSortId_ = codedInputStream.readInt64();
                                    continue;
                                case 106:
                                    String string3 = codedInputStream.readString();
                                    this.bitField0_ |= 4096;
                                    this.plainText_ = string3;
                                    continue;
                                case 112:
                                    this.bitField0_ |= 8192;
                                    this.hideType_ = codedInputStream.readInt32();
                                    continue;
                                case 120:
                                    this.bitField0_ |= 16384;
                                    this.priority_ = codedInputStream.readInt32();
                                    continue;
                                case 128:
                                    this.bitField0_ |= 32768;
                                    this.remindRepeatType_ = codedInputStream.readInt32();
                                    continue;
                                case 136:
                                    this.bitField0_ |= 65536;
                                    this.category_ = codedInputStream.readInt32();
                                    continue;
                                case R2.array.ai_material_default_blend_modes /* 146 */:
                                    String string4 = codedInputStream.readString();
                                    this.bitField0_ |= 131072;
                                    this.label_ = string4;
                                    continue;
                                case 152:
                                    this.bitField0_ |= 262144;
                                    this.syncId_ = codedInputStream.readInt32();
                                    continue;
                                case R2.array.ai_select_button_colors /* 162 */:
                                    String string5 = codedInputStream.readString();
                                    this.bitField0_ |= 524288;
                                    this.syncTag_ = string5;
                                    continue;
                                case 168:
                                    this.bitField0_ |= 1048576;
                                    this.remindTag_ = codedInputStream.readInt32();
                                    continue;
                                case 176:
                                    this.bitField0_ |= 2097152;
                                    this.wordsCount_ = codedInputStream.readInt32();
                                    continue;
                                case 184:
                                    this.bitField0_ |= 4194304;
                                    this.parenId_ = codedInputStream.readInt64();
                                    continue;
                                case 192:
                                    this.bitField0_ |= 8388608;
                                    this.sonId_ = codedInputStream.readInt64();
                                    continue;
                                case 200:
                                    this.bitField0_ |= 16777216;
                                    this.type_ = codedInputStream.readInt32();
                                    continue;
                                case 208:
                                    this.bitField0_ |= 33554432;
                                    this.sortId_ = codedInputStream.readInt64();
                                    continue;
                                case R2.array.global_ai_panel_blend_colors /* 216 */:
                                    this.bitField0_ |= 67108864;
                                    this.accountId_ = codedInputStream.readInt64();
                                    continue;
                                case R2.array.passport_account_user_gender_name /* 224 */:
                                    this.bitField0_ |= DataSpace.RANGE_FULL;
                                    this.version_ = codedInputStream.readInt32();
                                    continue;
                                case R2.array.week_days_shortest /* 234 */:
                                    String string6 = codedInputStream.readString();
                                    this.bitField0_ |= DataSpace.RANGE_LIMITED;
                                    this.snippet_ = string6;
                                    continue;
                                case R2.attr.StretchableWidgetStyle /* 240 */:
                                    this.bitField0_ |= VisionAttribute.VISION_TYPE_CLASS_ENTITY;
                                    this.syncEtag_ = codedInputStream.readInt64();
                                    continue;
                                case R2.attr.actionBarCutIcon /* 248 */:
                                    this.bitField0_ |= 1073741824;
                                    this.serverStatus_ = codedInputStream.readInt32();
                                    continue;
                                case R2.attr.actionBarExpandTabBarStyle /* 258 */:
                                    String string7 = codedInputStream.readString();
                                    this.bitField0_ |= Integer.MIN_VALUE;
                                    this.audioFileField_ = string7;
                                    continue;
                                case R2.attr.actionBarMovableLayoutStyle /* 264 */:
                                    this.bitField1_ |= 1;
                                    this.firstRemindTime_ = codedInputStream.readInt64();
                                    continue;
                                case 272:
                                    this.bitField1_ |= 2;
                                    this.folderId_ = codedInputStream.readInt32();
                                    continue;
                                case 280:
                                    this.bitField1_ |= 4;
                                    this.localStatus_ = codedInputStream.readInt32();
                                    continue;
                                case 288:
                                    this.bitField1_ |= 8;
                                    this.source_ = codedInputStream.readInt32();
                                    continue;
                                case R2.attr.actionBarTabActivatedTextSecondaryStyle /* 296 */:
                                    this.bitField1_ |= 16;
                                    this.audioFileSize_ = codedInputStream.readInt64();
                                    continue;
                                case 304:
                                    this.bitField1_ |= 32;
                                    this.colorLabel_ = codedInputStream.readInt32();
                                    continue;
                                case 312:
                                    this.bitField1_ |= 64;
                                    this.listType_ = codedInputStream.readInt32();
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
                    Parser<TodoEntity> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (TodoEntity.class) {
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

        public static TodoEntity getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TodoEntity> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* JADX INFO: renamed from: com.miui.notes.backup.TodoProtos$1, reason: invalid class name */
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

    public static final class Todos extends GeneratedMessageLite<Todos, Builder> implements TodosOrBuilder {
        private static final Todos DEFAULT_INSTANCE = new Todos();
        private static volatile Parser<Todos> PARSER = null;
        public static final int TODO_FIELD_NUMBER = 2;
        public static final int VERSION_FIELD_NUMBER = 1;
        private int bitField0_;
        private Internal.ProtobufList<TodoEntity> todo_ = emptyProtobufList();
        private int version_;

        private Todos() {
        }

        @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
        public List<TodoEntity> getTodoList() {
            return this.todo_;
        }

        public List<? extends TodoEntityOrBuilder> getTodoOrBuilderList() {
            return this.todo_;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
        public int getTodoCount() {
            return this.todo_.size();
        }

        @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
        public TodoEntity getTodo(int i) {
            return this.todo_.get(i);
        }

        public TodoEntityOrBuilder getTodoOrBuilder(int i) {
            return this.todo_.get(i);
        }

        private void ensureTodoIsMutable() {
            if (this.todo_.isModifiable()) {
                return;
            }
            this.todo_ = GeneratedMessageLite.mutableCopy(this.todo_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTodo(int i, TodoEntity todoEntity) {
            todoEntity.getClass();
            ensureTodoIsMutable();
            this.todo_.set(i, todoEntity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTodo(int i, TodoEntity.Builder builder) {
            ensureTodoIsMutable();
            this.todo_.set(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addTodo(TodoEntity todoEntity) {
            todoEntity.getClass();
            ensureTodoIsMutable();
            this.todo_.add(todoEntity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addTodo(int i, TodoEntity todoEntity) {
            todoEntity.getClass();
            ensureTodoIsMutable();
            this.todo_.add(i, todoEntity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addTodo(TodoEntity.Builder builder) {
            ensureTodoIsMutable();
            this.todo_.add(builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addTodo(int i, TodoEntity.Builder builder) {
            ensureTodoIsMutable();
            this.todo_.add(i, builder.build());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllTodo(Iterable<? extends TodoEntity> iterable) {
            ensureTodoIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.todo_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTodo() {
            this.todo_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeTodo(int i) {
            ensureTodoIsMutable();
            this.todo_.remove(i);
        }

        @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
        public boolean hasVersion() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
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
            if ((this.bitField0_ & 1) != 0) {
                codedOutputStream.writeInt32(1, this.version_);
            }
            for (int i = 0; i < this.todo_.size(); i++) {
                codedOutputStream.writeMessage(2, this.todo_.get(i));
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        @Override // com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i = this.memoizedSerializedSize;
            if (i != -1) {
                return i;
            }
            int iComputeInt32Size = (this.bitField0_ & 1) != 0 ? CodedOutputStream.computeInt32Size(1, this.version_) : 0;
            for (int i2 = 0; i2 < this.todo_.size(); i2++) {
                iComputeInt32Size += CodedOutputStream.computeMessageSize(2, this.todo_.get(i2));
            }
            int serializedSize = iComputeInt32Size + this.unknownFields.getSerializedSize();
            this.memoizedSerializedSize = serializedSize;
            return serializedSize;
        }

        public static Todos parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Todos parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static Todos parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Todos parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Todos parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Todos parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Todos parseFrom(InputStream inputStream) throws IOException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Todos parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Todos parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Todos) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Todos parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Todos) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Todos parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Todos parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Todos) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Todos todos) {
            return DEFAULT_INSTANCE.createBuilder(todos);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Todos, Builder> implements TodosOrBuilder {
            private Builder() {
                super(Todos.DEFAULT_INSTANCE);
            }

            @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
            public List<TodoEntity> getTodoList() {
                return Collections.unmodifiableList(((Todos) this.instance).getTodoList());
            }

            @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
            public int getTodoCount() {
                return ((Todos) this.instance).getTodoCount();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
            public TodoEntity getTodo(int i) {
                return ((Todos) this.instance).getTodo(i);
            }

            public Builder setTodo(int i, TodoEntity todoEntity) {
                copyOnWrite();
                ((Todos) this.instance).setTodo(i, todoEntity);
                return this;
            }

            public Builder setTodo(int i, TodoEntity.Builder builder) {
                copyOnWrite();
                ((Todos) this.instance).setTodo(i, builder);
                return this;
            }

            public Builder addTodo(TodoEntity todoEntity) {
                copyOnWrite();
                ((Todos) this.instance).addTodo(todoEntity);
                return this;
            }

            public Builder addTodo(int i, TodoEntity todoEntity) {
                copyOnWrite();
                ((Todos) this.instance).addTodo(i, todoEntity);
                return this;
            }

            public Builder addTodo(TodoEntity.Builder builder) {
                copyOnWrite();
                ((Todos) this.instance).addTodo(builder);
                return this;
            }

            public Builder addTodo(int i, TodoEntity.Builder builder) {
                copyOnWrite();
                ((Todos) this.instance).addTodo(i, builder);
                return this;
            }

            public Builder addAllTodo(Iterable<? extends TodoEntity> iterable) {
                copyOnWrite();
                ((Todos) this.instance).addAllTodo(iterable);
                return this;
            }

            public Builder clearTodo() {
                copyOnWrite();
                ((Todos) this.instance).clearTodo();
                return this;
            }

            public Builder removeTodo(int i) {
                copyOnWrite();
                ((Todos) this.instance).removeTodo(i);
                return this;
            }

            @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
            public boolean hasVersion() {
                return ((Todos) this.instance).hasVersion();
            }

            @Override // com.miui.notes.backup.TodoProtos.TodosOrBuilder
            public int getVersion() {
                return ((Todos) this.instance).getVersion();
            }

            public Builder setVersion(int i) {
                copyOnWrite();
                ((Todos) this.instance).setVersion(i);
                return this;
            }

            public Builder clearVersion() {
                copyOnWrite();
                ((Todos) this.instance).clearVersion();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new Todos();
                case 2:
                    return new Builder();
                case 3:
                    return DEFAULT_INSTANCE;
                case 4:
                    this.todo_.makeImmutable();
                    return null;
                case 5:
                    GeneratedMessageLite.Visitor visitor = (GeneratedMessageLite.Visitor) obj;
                    Todos todos = (Todos) obj2;
                    this.todo_ = visitor.visitList(this.todo_, todos.todo_);
                    this.version_ = visitor.visitInt(hasVersion(), this.version_, todos.hasVersion(), todos.version_);
                    if (visitor == GeneratedMessageLite.MergeFromVisitor.INSTANCE) {
                        this.bitField0_ |= todos.bitField0_;
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
                                    this.version_ = codedInputStream.readInt32();
                                } else if (tag == 18) {
                                    if (!this.todo_.isModifiable()) {
                                        this.todo_ = GeneratedMessageLite.mutableCopy(this.todo_);
                                    }
                                    this.todo_.add((TodoEntity) codedInputStream.readMessage(TodoEntity.parser(), extensionRegistryLite));
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
                    Parser<Todos> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (Todos.class) {
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

        public static Todos getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Todos> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
