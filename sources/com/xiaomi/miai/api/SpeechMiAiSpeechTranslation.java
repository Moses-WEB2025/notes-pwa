package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.EventPayload;
import com.xiaomi.miai.api.common.InstructionPayload;
import com.xiaomi.miai.api.common.NamespaceName;
import com.xiaomi.miai.api.common.Required;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechMiAiSpeechTranslation {

    @NamespaceName(name = "ConferenceSpeechMessage", namespace = AIApiConstants.SpeechMiAiSpeechTranslation.NAME)
    public static class ConferenceSpeechMessage implements EventPayload {

        @Required
        private String detected_lang;

        @Required
        private boolean is_sentence_completed;

        @Required
        private int packet_id;

        @Required
        private int sentence_id;

        @Required
        private List<SpeechMiAiSpeechAsr.Sentence> sentences;

        @Required
        private int timeoffset;

        @Required
        private SpeechGeneral.Vendor vendor;

        public ConferenceSpeechMessage() {
        }

        public ConferenceSpeechMessage(SpeechGeneral.Vendor vendor, int i, int i2, int i3, String str, List<SpeechMiAiSpeechAsr.Sentence> list, boolean z) {
            this.vendor = vendor;
            this.sentence_id = i;
            this.packet_id = i2;
            this.timeoffset = i3;
            this.detected_lang = str;
            this.sentences = list;
            this.is_sentence_completed = z;
        }

        @Required
        public ConferenceSpeechMessage setVendor(SpeechGeneral.Vendor vendor) {
            this.vendor = vendor;
            return this;
        }

        @Required
        public SpeechGeneral.Vendor getVendor() {
            return this.vendor;
        }

        @Required
        public ConferenceSpeechMessage setSentenceId(int i) {
            this.sentence_id = i;
            return this;
        }

        @Required
        public int getSentenceId() {
            return this.sentence_id;
        }

        @Required
        public ConferenceSpeechMessage setPacketId(int i) {
            this.packet_id = i;
            return this;
        }

        @Required
        public int getPacketId() {
            return this.packet_id;
        }

        @Required
        public ConferenceSpeechMessage setTimeoffset(int i) {
            this.timeoffset = i;
            return this;
        }

        @Required
        public int getTimeoffset() {
            return this.timeoffset;
        }

        @Required
        public ConferenceSpeechMessage setDetectedLang(String str) {
            this.detected_lang = str;
            return this;
        }

        @Required
        public String getDetectedLang() {
            return this.detected_lang;
        }

        @Required
        public ConferenceSpeechMessage setSentences(List<SpeechMiAiSpeechAsr.Sentence> list) {
            this.sentences = list;
            return this;
        }

        @Required
        public List<SpeechMiAiSpeechAsr.Sentence> getSentences() {
            return this.sentences;
        }

        @Required
        public ConferenceSpeechMessage setIsSentenceCompleted(boolean z) {
            this.is_sentence_completed = z;
            return this;
        }

        @Required
        public boolean isSentenceCompleted() {
            return this.is_sentence_completed;
        }
    }

    @NamespaceName(name = "Recognize", namespace = AIApiConstants.SpeechMiAiSpeechTranslation.NAME)
    public static class Recognize implements EventPayload {

        @Required
        private String app_id;

        @Required
        private String from_lang;

        @Required
        private List<String> to_lang;

        public Recognize() {
        }

        public Recognize(String str, String str2, List<String> list) {
            this.app_id = str;
            this.from_lang = str2;
            this.to_lang = list;
        }

        @Required
        public Recognize setAppId(String str) {
            this.app_id = str;
            return this;
        }

        @Required
        public String getAppId() {
            return this.app_id;
        }

        @Required
        public Recognize setFromLang(String str) {
            this.from_lang = str;
            return this;
        }

        @Required
        public String getFromLang() {
            return this.from_lang;
        }

        @Required
        public Recognize setToLang(List<String> list) {
            this.to_lang = list;
            return this;
        }

        @Required
        public List<String> getToLang() {
            return this.to_lang;
        }
    }

    @NamespaceName(name = "TranslationItem", namespace = AIApiConstants.SpeechMiAiSpeechTranslation.NAME)
    public static class TranslationItem implements InstructionPayload {

        @Required
        private String detected_lang;

        @Required
        private boolean is_sentence_completed;

        @Required
        private boolean need_update;

        @Required
        private int packet_id;

        @Required
        private int sentence_id;

        @Required
        private List<SpeechMiAiSpeechAsr.Sentence> sentences;

        @Required
        private int timeoffset;
        private Optional<SpeechGeneral.Vendor> vendor = Optional.empty();

        public TranslationItem() {
        }

        public TranslationItem(int i, int i2, int i3, String str, List<SpeechMiAiSpeechAsr.Sentence> list, boolean z, boolean z2) {
            this.sentence_id = i;
            this.packet_id = i2;
            this.timeoffset = i3;
            this.detected_lang = str;
            this.sentences = list;
            this.is_sentence_completed = z;
            this.need_update = z2;
        }

        @Required
        public TranslationItem setSentenceId(int i) {
            this.sentence_id = i;
            return this;
        }

        @Required
        public int getSentenceId() {
            return this.sentence_id;
        }

        @Required
        public TranslationItem setPacketId(int i) {
            this.packet_id = i;
            return this;
        }

        @Required
        public int getPacketId() {
            return this.packet_id;
        }

        @Required
        public TranslationItem setTimeoffset(int i) {
            this.timeoffset = i;
            return this;
        }

        @Required
        public int getTimeoffset() {
            return this.timeoffset;
        }

        @Required
        public TranslationItem setDetectedLang(String str) {
            this.detected_lang = str;
            return this;
        }

        @Required
        public String getDetectedLang() {
            return this.detected_lang;
        }

        @Required
        public TranslationItem setSentences(List<SpeechMiAiSpeechAsr.Sentence> list) {
            this.sentences = list;
            return this;
        }

        @Required
        public List<SpeechMiAiSpeechAsr.Sentence> getSentences() {
            return this.sentences;
        }

        @Required
        public TranslationItem setIsSentenceCompleted(boolean z) {
            this.is_sentence_completed = z;
            return this;
        }

        @Required
        public boolean isSentenceCompleted() {
            return this.is_sentence_completed;
        }

        @Required
        public TranslationItem setNeedUpdate(boolean z) {
            this.need_update = z;
            return this;
        }

        @Required
        public boolean isNeedUpdate() {
            return this.need_update;
        }

        public TranslationItem setVendor(SpeechGeneral.Vendor vendor) {
            this.vendor = Optional.ofNullable(vendor);
            return this;
        }

        public Optional<SpeechGeneral.Vendor> getVendor() {
            return this.vendor;
        }
    }

    @NamespaceName(name = "TranslationResult", namespace = AIApiConstants.SpeechMiAiSpeechTranslation.NAME)
    public static class TranslationResult implements InstructionPayload {

        @Required
        private List<TranslationItem> result;

        public TranslationResult() {
        }

        public TranslationResult(List<TranslationItem> list) {
            this.result = list;
        }

        @Required
        public TranslationResult setResult(List<TranslationItem> list) {
            this.result = list;
            return this;
        }

        @Required
        public List<TranslationItem> getResult() {
            return this.result;
        }
    }
}
