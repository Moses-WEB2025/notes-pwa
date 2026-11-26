package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.EventPayload;
import com.xiaomi.miai.api.common.InstructionPayload;
import com.xiaomi.miai.api.common.NamespaceName;
import com.xiaomi.miai.api.common.Required;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechMiAiSpeechConference {

    @NamespaceName(name = "ConferenceServiceInfoUpdate", namespace = AIApiConstants.SpeechMiAiSpeechConference.NAME)
    public static class ConferenceServiceInfoUpdate implements EventPayload {

        @Required
        private boolean is_fold;

        @Required
        private boolean is_on_translation;

        @Required
        private boolean is_single_person;

        public ConferenceServiceInfoUpdate() {
        }

        public ConferenceServiceInfoUpdate(boolean z, boolean z2, boolean z3) {
            this.is_single_person = z;
            this.is_on_translation = z2;
            this.is_fold = z3;
        }

        @Required
        public ConferenceServiceInfoUpdate setIsSinglePerson(boolean z) {
            this.is_single_person = z;
            return this;
        }

        @Required
        public boolean isSinglePerson() {
            return this.is_single_person;
        }

        @Required
        public ConferenceServiceInfoUpdate setIsOnTranslation(boolean z) {
            this.is_on_translation = z;
            return this;
        }

        @Required
        public boolean isOnTranslation() {
            return this.is_on_translation;
        }

        @Required
        public ConferenceServiceInfoUpdate setIsFold(boolean z) {
            this.is_fold = z;
            return this;
        }

        @Required
        public boolean isFold() {
            return this.is_fold;
        }
    }

    @NamespaceName(name = "SpeechConferenceResult", namespace = AIApiConstants.SpeechMiAiSpeechConference.NAME)
    public static class ConferenceServiceResult implements InstructionPayload {

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
        private String timestamp;
        private Optional<Boolean> need_update = Optional.empty();
        private Optional<SpeechGeneral.Vendor> vendor = Optional.empty();

        public ConferenceServiceResult() {
        }

        public ConferenceServiceResult(int i, int i2, String str, String str2, List<SpeechMiAiSpeechAsr.Sentence> list, boolean z) {
            this.sentence_id = i;
            this.packet_id = i2;
            this.timestamp = str;
            this.detected_lang = str2;
            this.sentences = list;
            this.is_sentence_completed = z;
        }

        @Required
        public ConferenceServiceResult setSentenceId(int i) {
            this.sentence_id = i;
            return this;
        }

        @Required
        public int getSentenceId() {
            return this.sentence_id;
        }

        @Required
        public ConferenceServiceResult setPacketId(int i) {
            this.packet_id = i;
            return this;
        }

        @Required
        public int getPacketId() {
            return this.packet_id;
        }

        @Required
        public ConferenceServiceResult setTimestamp(String str) {
            this.timestamp = str;
            return this;
        }

        @Required
        public String getTimestamp() {
            return this.timestamp;
        }

        @Required
        public ConferenceServiceResult setDetectedLang(String str) {
            this.detected_lang = str;
            return this;
        }

        @Required
        public String getDetectedLang() {
            return this.detected_lang;
        }

        @Required
        public ConferenceServiceResult setSentences(List<SpeechMiAiSpeechAsr.Sentence> list) {
            this.sentences = list;
            return this;
        }

        @Required
        public List<SpeechMiAiSpeechAsr.Sentence> getSentences() {
            return this.sentences;
        }

        @Required
        public ConferenceServiceResult setIsSentenceCompleted(boolean z) {
            this.is_sentence_completed = z;
            return this;
        }

        @Required
        public boolean isSentenceCompleted() {
            return this.is_sentence_completed;
        }

        public ConferenceServiceResult setNeedUpdate(boolean z) {
            this.need_update = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isNeedUpdate() {
            return this.need_update;
        }

        public ConferenceServiceResult setVendor(SpeechGeneral.Vendor vendor) {
            this.vendor = Optional.ofNullable(vendor);
            return this;
        }

        public Optional<SpeechGeneral.Vendor> getVendor() {
            return this.vendor;
        }
    }

    @NamespaceName(name = "SpeechConferenceVorResult", namespace = AIApiConstants.SpeechMiAiSpeechConference.NAME)
    public static class SpeechConferenceVorResult implements InstructionPayload {

        @Required
        private List<VorResult> vor_results;

        public SpeechConferenceVorResult() {
        }

        public SpeechConferenceVorResult(List<VorResult> list) {
            this.vor_results = list;
        }

        @Required
        public SpeechConferenceVorResult setVorResults(List<VorResult> list) {
            this.vor_results = list;
            return this;
        }

        @Required
        public List<VorResult> getVorResults() {
            return this.vor_results;
        }
    }

    public static class VorResult {

        @Required
        private int sentence_id;

        @Required
        private String vor_role;

        public VorResult() {
        }

        public VorResult(int i, String str) {
            this.sentence_id = i;
            this.vor_role = str;
        }

        @Required
        public VorResult setSentenceId(int i) {
            this.sentence_id = i;
            return this;
        }

        @Required
        public int getSentenceId() {
            return this.sentence_id;
        }

        @Required
        public VorResult setVorRole(String str) {
            this.vor_role = str;
            return this;
        }

        @Required
        public String getVorRole() {
            return this.vor_role;
        }
    }
}
