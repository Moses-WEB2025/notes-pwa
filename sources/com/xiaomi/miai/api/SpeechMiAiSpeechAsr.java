package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.EventPayload;
import com.xiaomi.miai.api.common.InstructionPayload;
import com.xiaomi.miai.api.common.NamespaceName;
import com.xiaomi.miai.api.common.Required;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechMiAiSpeechAsr {

    @NamespaceName(name = "SpeechRecognizeFinish", namespace = AIApiConstants.SpeechMiAiSpeechAsr.NAME)
    public static class SpeechRecognizeFinish implements InstructionPayload {
    }

    public enum AsrCodec {
        pcm("pcm", 0);

        private String _name;
        private int id;

        AsrCodec(String str, int i) {
            this._name = str;
            this.id = i;
        }

        public int getId() {
            return this.id;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this._name;
        }
    }

    @NamespaceName(name = "ConferenceInfoUpdate", namespace = AIApiConstants.SpeechMiAiSpeechAsr.NAME)
    public static class ConferenceInfoUpdate implements EventPayload {

        @Required
        private boolean is_fold;

        @Required
        private boolean is_single_person;

        public ConferenceInfoUpdate() {
        }

        public ConferenceInfoUpdate(boolean z, boolean z2) {
            this.is_single_person = z;
            this.is_fold = z2;
        }

        @Required
        public ConferenceInfoUpdate setIsSinglePerson(boolean z) {
            this.is_single_person = z;
            return this;
        }

        @Required
        public boolean isSinglePerson() {
            return this.is_single_person;
        }

        @Required
        public ConferenceInfoUpdate setIsFold(boolean z) {
            this.is_fold = z;
            return this;
        }

        @Required
        public boolean isFold() {
            return this.is_fold;
        }
    }

    public static class Sentence {

        @Required
        private String lang;

        @Required
        private String text;

        public Sentence() {
        }

        public Sentence(String str, String str2) {
            this.lang = str;
            this.text = str2;
        }

        @Required
        public Sentence setLang(String str) {
            this.lang = str;
            return this;
        }

        @Required
        public String getLang() {
            return this.lang;
        }

        @Required
        public Sentence setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }
    }

    @NamespaceName(name = "SpeechConferenceResult", namespace = AIApiConstants.SpeechMiAiSpeechAsr.NAME)
    public static class SpeechConferenceResult implements InstructionPayload {

        @Required
        private String detected_lang;

        @Required
        private boolean is_sentence_completed;

        @Required
        private int packet_id;

        @Required
        private int sentence_id;

        @Required
        private List<Sentence> sentences;

        @Required
        private int timeoffset;

        @Required
        private SpeechGeneral.Vendor vendor;

        @Required
        private List<VprResult> vpr_results;

        public SpeechConferenceResult() {
        }

        public SpeechConferenceResult(SpeechGeneral.Vendor vendor, int i, int i2, int i3, String str, List<Sentence> list, boolean z, List<VprResult> list2) {
            this.vendor = vendor;
            this.sentence_id = i;
            this.packet_id = i2;
            this.timeoffset = i3;
            this.detected_lang = str;
            this.sentences = list;
            this.is_sentence_completed = z;
            this.vpr_results = list2;
        }

        @Required
        public SpeechConferenceResult setVendor(SpeechGeneral.Vendor vendor) {
            this.vendor = vendor;
            return this;
        }

        @Required
        public SpeechGeneral.Vendor getVendor() {
            return this.vendor;
        }

        @Required
        public SpeechConferenceResult setSentenceId(int i) {
            this.sentence_id = i;
            return this;
        }

        @Required
        public int getSentenceId() {
            return this.sentence_id;
        }

        @Required
        public SpeechConferenceResult setPacketId(int i) {
            this.packet_id = i;
            return this;
        }

        @Required
        public int getPacketId() {
            return this.packet_id;
        }

        @Required
        public SpeechConferenceResult setTimeoffset(int i) {
            this.timeoffset = i;
            return this;
        }

        @Required
        public int getTimeoffset() {
            return this.timeoffset;
        }

        @Required
        public SpeechConferenceResult setDetectedLang(String str) {
            this.detected_lang = str;
            return this;
        }

        @Required
        public String getDetectedLang() {
            return this.detected_lang;
        }

        @Required
        public SpeechConferenceResult setSentences(List<Sentence> list) {
            this.sentences = list;
            return this;
        }

        @Required
        public List<Sentence> getSentences() {
            return this.sentences;
        }

        @Required
        public SpeechConferenceResult setIsSentenceCompleted(boolean z) {
            this.is_sentence_completed = z;
            return this;
        }

        @Required
        public boolean isSentenceCompleted() {
            return this.is_sentence_completed;
        }

        @Required
        public SpeechConferenceResult setVprResults(List<VprResult> list) {
            this.vpr_results = list;
            return this;
        }

        @Required
        public List<VprResult> getVprResults() {
            return this.vpr_results;
        }
    }

    @NamespaceName(name = "SpeechRecognize", namespace = AIApiConstants.SpeechMiAiSpeechAsr.NAME)
    public static class SpeechRecognize implements EventPayload {

        @Required
        private int bit;

        @Required
        private int channel;

        @Required
        private AsrCodec codec;

        @Required
        private String model_type;

        @Required
        private int rate;

        @Required
        private List<SpeechGeneral.Vendor> vendor;
        private Optional<String> lang = Optional.empty();
        private Optional<String> hot_word = Optional.empty();
        private Optional<String> device_type = Optional.empty();

        public SpeechRecognize() {
        }

        public SpeechRecognize(String str, AsrCodec asrCodec, int i, int i2, int i3, List<SpeechGeneral.Vendor> list) {
            this.model_type = str;
            this.codec = asrCodec;
            this.bit = i;
            this.rate = i2;
            this.channel = i3;
            this.vendor = list;
        }

        @Required
        public SpeechRecognize setModelType(String str) {
            this.model_type = str;
            return this;
        }

        @Required
        public String getModelType() {
            return this.model_type;
        }

        @Required
        public SpeechRecognize setCodec(AsrCodec asrCodec) {
            this.codec = asrCodec;
            return this;
        }

        @Required
        public AsrCodec getCodec() {
            return this.codec;
        }

        @Required
        public SpeechRecognize setBit(int i) {
            this.bit = i;
            return this;
        }

        @Required
        public int getBit() {
            return this.bit;
        }

        @Required
        public SpeechRecognize setRate(int i) {
            this.rate = i;
            return this;
        }

        @Required
        public int getRate() {
            return this.rate;
        }

        @Required
        public SpeechRecognize setChannel(int i) {
            this.channel = i;
            return this;
        }

        @Required
        public int getChannel() {
            return this.channel;
        }

        public SpeechRecognize setLang(String str) {
            this.lang = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getLang() {
            return this.lang;
        }

        public SpeechRecognize setHotWord(String str) {
            this.hot_word = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHotWord() {
            return this.hot_word;
        }

        public SpeechRecognize setDeviceType(String str) {
            this.device_type = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDeviceType() {
            return this.device_type;
        }

        @Required
        public SpeechRecognize setVendor(List<SpeechGeneral.Vendor> list) {
            this.vendor = list;
            return this;
        }

        @Required
        public List<SpeechGeneral.Vendor> getVendor() {
            return this.vendor;
        }
    }

    @NamespaceName(name = "SpeechRecognizeError", namespace = AIApiConstants.SpeechMiAiSpeechAsr.NAME)
    public static class SpeechRecognizeError implements InstructionPayload {

        @Required
        private Status status;
        private Optional<SpeechGeneral.Vendor> vendor = Optional.empty();

        public SpeechRecognizeError() {
        }

        public SpeechRecognizeError(Status status) {
            this.status = status;
        }

        @Required
        public SpeechRecognizeError setStatus(Status status) {
            this.status = status;
            return this;
        }

        @Required
        public Status getStatus() {
            return this.status;
        }

        public SpeechRecognizeError setVendor(SpeechGeneral.Vendor vendor) {
            this.vendor = Optional.ofNullable(vendor);
            return this;
        }

        public Optional<SpeechGeneral.Vendor> getVendor() {
            return this.vendor;
        }
    }

    @NamespaceName(name = "SpeechRecognizeResult", namespace = AIApiConstants.SpeechMiAiSpeechAsr.NAME)
    public static class SpeechRecognizeResult implements InstructionPayload {

        @Required
        private boolean is_final;

        @Required
        private String query;

        @Required
        private SpeechGeneral.Vendor vendor;
        private Optional<Integer> volume = Optional.empty();
        private Optional<Integer> gender = Optional.empty();
        private Optional<String> pinyin = Optional.empty();
        private Optional<Integer> audio_duration = Optional.empty();

        public SpeechRecognizeResult() {
        }

        public SpeechRecognizeResult(SpeechGeneral.Vendor vendor, boolean z, String str) {
            this.vendor = vendor;
            this.is_final = z;
            this.query = str;
        }

        @Required
        public SpeechRecognizeResult setVendor(SpeechGeneral.Vendor vendor) {
            this.vendor = vendor;
            return this;
        }

        @Required
        public SpeechGeneral.Vendor getVendor() {
            return this.vendor;
        }

        @Required
        public SpeechRecognizeResult setIsFinal(boolean z) {
            this.is_final = z;
            return this;
        }

        @Required
        public boolean isFinal() {
            return this.is_final;
        }

        @Required
        public SpeechRecognizeResult setQuery(String str) {
            this.query = str;
            return this;
        }

        @Required
        public String getQuery() {
            return this.query;
        }

        public SpeechRecognizeResult setVolume(int i) {
            this.volume = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getVolume() {
            return this.volume;
        }

        public SpeechRecognizeResult setGender(int i) {
            this.gender = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getGender() {
            return this.gender;
        }

        public SpeechRecognizeResult setPinyin(String str) {
            this.pinyin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPinyin() {
            return this.pinyin;
        }

        public SpeechRecognizeResult setAudioDuration(int i) {
            this.audio_duration = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getAudioDuration() {
            return this.audio_duration;
        }
    }

    public static class VprResult {

        @Required
        private int sentence_id;

        @Required
        private String vpr_role;

        public VprResult() {
        }

        public VprResult(int i, String str) {
            this.sentence_id = i;
            this.vpr_role = str;
        }

        @Required
        public VprResult setSentenceId(int i) {
            this.sentence_id = i;
            return this;
        }

        @Required
        public int getSentenceId() {
            return this.sentence_id;
        }

        @Required
        public VprResult setVprRole(String str) {
            this.vpr_role = str;
            return this;
        }

        @Required
        public String getVprRole() {
            return this.vpr_role;
        }
    }
}
