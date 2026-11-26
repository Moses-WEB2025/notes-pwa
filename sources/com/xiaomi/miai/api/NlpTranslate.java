package com.xiaomi.miai.api;

import com.market.sdk.utils.Language;
import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.Required;
import java.util.List;
import miuix.smartaction.SmartAction;

/* JADX INFO: loaded from: classes4.dex */
public class NlpTranslate {

    public enum Mode {
        translate(SmartAction.Feature.TRANSLATE, 0),
        dictionary("dictionary", 1);

        private String _name;
        private int id;

        Mode(String str, int i) {
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

    public enum SupportLanguage {
        en(Language.LA_EN, 0),
        zh_Hans("zh-Hans", 1);

        private String _name;
        private int id;

        SupportLanguage(String str, int i) {
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

    public static class Chinese {
        private Optional<String> ciyu = Optional.empty();
        private Optional<String> pinyin = Optional.empty();
        private Optional<String> jieshi = Optional.empty();
        private Optional<String> tongyi = Optional.empty();
        private Optional<String> fanyi = Optional.empty();

        public Chinese setCiyu(String str) {
            this.ciyu = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCiyu() {
            return this.ciyu;
        }

        public Chinese setPinyin(String str) {
            this.pinyin = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPinyin() {
            return this.pinyin;
        }

        public Chinese setJieshi(String str) {
            this.jieshi = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getJieshi() {
            return this.jieshi;
        }

        public Chinese setTongyi(String str) {
            this.tongyi = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTongyi() {
            return this.tongyi;
        }

        public Chinese setFanyi(String str) {
            this.fanyi = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getFanyi() {
            return this.fanyi;
        }
    }

    public static class ChineseSentence {
        private Optional<String> meaning = Optional.empty();
        private Optional<String> tag = Optional.empty();
        private Optional<String> word = Optional.empty();
        private Optional<List<Sentence>> sentences = Optional.empty();

        public ChineseSentence setMeaning(String str) {
            this.meaning = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getMeaning() {
            return this.meaning;
        }

        public ChineseSentence setTag(String str) {
            this.tag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTag() {
            return this.tag;
        }

        public ChineseSentence setWord(String str) {
            this.word = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWord() {
            return this.word;
        }

        public ChineseSentence setSentences(List<Sentence> list) {
            this.sentences = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Sentence>> getSentences() {
            return this.sentences;
        }
    }

    public static class DetectRequest {

        @Required
        private String text;

        public DetectRequest() {
        }

        public DetectRequest(String str) {
            this.text = str;
        }

        @Required
        public DetectRequest setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }
    }

    public static class DetectResponse {

        @Required
        private String language;

        @Required
        private double prob;

        public DetectResponse() {
        }

        public DetectResponse(String str, double d) {
            this.language = str;
            this.prob = d;
        }

        @Required
        public DetectResponse setLanguage(String str) {
            this.language = str;
            return this;
        }

        @Required
        public String getLanguage() {
            return this.language;
        }

        @Required
        public DetectResponse setProb(double d) {
            this.prob = d;
            return this;
        }

        @Required
        public double getProb() {
            return this.prob;
        }
    }

    public static class DictionaryChineseResponse {

        @Required
        private String word_name;
        private Optional<List<ChineseSentence>> sentence = Optional.empty();
        private Optional<List<SymbolsChinese>> symbols = Optional.empty();
        private Optional<Chinese> chinese = Optional.empty();

        public DictionaryChineseResponse() {
        }

        public DictionaryChineseResponse(String str) {
            this.word_name = str;
        }

        @Required
        public DictionaryChineseResponse setWordName(String str) {
            this.word_name = str;
            return this;
        }

        @Required
        public String getWordName() {
            return this.word_name;
        }

        public DictionaryChineseResponse setSentence(List<ChineseSentence> list) {
            this.sentence = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ChineseSentence>> getSentence() {
            return this.sentence;
        }

        public DictionaryChineseResponse setSymbols(List<SymbolsChinese> list) {
            this.symbols = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SymbolsChinese>> getSymbols() {
            return this.symbols;
        }

        public DictionaryChineseResponse setChinese(Chinese chinese) {
            this.chinese = Optional.ofNullable(chinese);
            return this;
        }

        public Optional<Chinese> getChinese() {
            return this.chinese;
        }
    }

    public static class DictionaryRequest {

        @Required
        private SupportLanguage from;

        @Required
        private String text;

        @Required
        private SupportLanguage to;

        public DictionaryRequest() {
        }

        public DictionaryRequest(SupportLanguage supportLanguage, SupportLanguage supportLanguage2, String str) {
            this.from = supportLanguage;
            this.to = supportLanguage2;
            this.text = str;
        }

        @Required
        public DictionaryRequest setFrom(SupportLanguage supportLanguage) {
            this.from = supportLanguage;
            return this;
        }

        @Required
        public SupportLanguage getFrom() {
            return this.from;
        }

        @Required
        public DictionaryRequest setTo(SupportLanguage supportLanguage) {
            this.to = supportLanguage;
            return this;
        }

        @Required
        public SupportLanguage getTo() {
            return this.to;
        }

        @Required
        public DictionaryRequest setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }
    }

    public static class DictionaryResponse {

        @Required
        private String word_name;
        private Optional<List<Sentence>> sentence = Optional.empty();
        private Optional<List<WordInformation>> synonym = Optional.empty();
        private Optional<List<WordInformation>> antonym = Optional.empty();
        private Optional<List<Integer>> word_tag = Optional.empty();
        private Optional<Parts> parts = Optional.empty();
        private Optional<Exchange> exchange = Optional.empty();
        private Optional<Symbols> symbols = Optional.empty();

        public DictionaryResponse() {
        }

        public DictionaryResponse(String str) {
            this.word_name = str;
        }

        @Required
        public DictionaryResponse setWordName(String str) {
            this.word_name = str;
            return this;
        }

        @Required
        public String getWordName() {
            return this.word_name;
        }

        public DictionaryResponse setSentence(List<Sentence> list) {
            this.sentence = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Sentence>> getSentence() {
            return this.sentence;
        }

        public DictionaryResponse setSynonym(List<WordInformation> list) {
            this.synonym = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<WordInformation>> getSynonym() {
            return this.synonym;
        }

        public DictionaryResponse setAntonym(List<WordInformation> list) {
            this.antonym = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<WordInformation>> getAntonym() {
            return this.antonym;
        }

        public DictionaryResponse setWordTag(List<Integer> list) {
            this.word_tag = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Integer>> getWordTag() {
            return this.word_tag;
        }

        public DictionaryResponse setParts(Parts parts) {
            this.parts = Optional.ofNullable(parts);
            return this;
        }

        public Optional<Parts> getParts() {
            return this.parts;
        }

        public DictionaryResponse setExchange(Exchange exchange) {
            this.exchange = Optional.ofNullable(exchange);
            return this;
        }

        public Optional<Exchange> getExchange() {
            return this.exchange;
        }

        public DictionaryResponse setSymbols(Symbols symbols) {
            this.symbols = Optional.ofNullable(symbols);
            return this;
        }

        public Optional<Symbols> getSymbols() {
            return this.symbols;
        }
    }

    public static class Exchange {
        private Optional<List<String>> word_noun = Optional.empty();
        private Optional<List<String>> word_third = Optional.empty();
        private Optional<List<String>> word_done = Optional.empty();
        private Optional<List<String>> word_pl = Optional.empty();
        private Optional<List<String>> word_est = Optional.empty();
        private Optional<List<String>> word_adv = Optional.empty();
        private Optional<List<String>> word_verb = Optional.empty();
        private Optional<List<String>> word_adj = Optional.empty();
        private Optional<List<String>> word_conn = Optional.empty();
        private Optional<List<String>> word_prep = Optional.empty();
        private Optional<List<String>> word_ing = Optional.empty();
        private Optional<List<String>> word_er = Optional.empty();
        private Optional<List<String>> word_past = Optional.empty();

        public Exchange setWordNoun(List<String> list) {
            this.word_noun = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordNoun() {
            return this.word_noun;
        }

        public Exchange setWordThird(List<String> list) {
            this.word_third = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordThird() {
            return this.word_third;
        }

        public Exchange setWordDone(List<String> list) {
            this.word_done = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordDone() {
            return this.word_done;
        }

        public Exchange setWordPl(List<String> list) {
            this.word_pl = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordPl() {
            return this.word_pl;
        }

        public Exchange setWordEst(List<String> list) {
            this.word_est = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordEst() {
            return this.word_est;
        }

        public Exchange setWordAdv(List<String> list) {
            this.word_adv = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordAdv() {
            return this.word_adv;
        }

        public Exchange setWordVerb(List<String> list) {
            this.word_verb = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordVerb() {
            return this.word_verb;
        }

        public Exchange setWordAdj(List<String> list) {
            this.word_adj = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordAdj() {
            return this.word_adj;
        }

        public Exchange setWordConn(List<String> list) {
            this.word_conn = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordConn() {
            return this.word_conn;
        }

        public Exchange setWordPrep(List<String> list) {
            this.word_prep = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordPrep() {
            return this.word_prep;
        }

        public Exchange setWordIng(List<String> list) {
            this.word_ing = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordIng() {
            return this.word_ing;
        }

        public Exchange setWordEr(List<String> list) {
            this.word_er = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordEr() {
            return this.word_er;
        }

        public Exchange setWordPast(List<String> list) {
            this.word_past = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getWordPast() {
            return this.word_past;
        }
    }

    public static class Means {
        private Optional<List<String>> cis = Optional.empty();
        private Optional<String> word_mean = Optional.empty();

        public Means setCis(List<String> list) {
            this.cis = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getCis() {
            return this.cis;
        }

        public Means setWordMean(String str) {
            this.word_mean = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWordMean() {
            return this.word_mean;
        }
    }

    public static class Parts {
        private Optional<List<String>> mean = Optional.empty();
        private Optional<List<String>> simpleMeans = Optional.empty();

        public Parts setMean(List<String> list) {
            this.mean = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getMean() {
            return this.mean;
        }

        public Parts setSimpleMeans(List<String> list) {
            this.simpleMeans = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getSimpleMeans() {
            return this.simpleMeans;
        }
    }

    public static class PunctuateRequest {

        @Required
        private SupportLanguage language;

        @Required
        private String text;

        public PunctuateRequest() {
        }

        public PunctuateRequest(String str, SupportLanguage supportLanguage) {
            this.text = str;
            this.language = supportLanguage;
        }

        @Required
        public PunctuateRequest setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public PunctuateRequest setLanguage(SupportLanguage supportLanguage) {
            this.language = supportLanguage;
            return this;
        }

        @Required
        public SupportLanguage getLanguage() {
            return this.language;
        }
    }

    public static class PunctuateResponse {

        @Required
        private String result;

        public PunctuateResponse() {
        }

        public PunctuateResponse(String str) {
            this.result = str;
        }

        @Required
        public PunctuateResponse setResult(String str) {
            this.result = str;
            return this;
        }

        @Required
        public String getResult() {
            return this.result;
        }
    }

    public static class Sentence {
        private Optional<String> ttsUrl = Optional.empty();
        private Optional<String> en = Optional.empty();

        /* JADX INFO: renamed from: cn, reason: collision with root package name */
        private Optional<String> f7cn = Optional.empty();

        public Sentence setTtsUrl(String str) {
            this.ttsUrl = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTtsUrl() {
            return this.ttsUrl;
        }

        public Sentence setEn(String str) {
            this.en = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEn() {
            return this.en;
        }

        public Sentence setCn(String str) {
            this.f7cn = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCn() {
            return this.f7cn;
        }
    }

    public static class Symbols {
        private Optional<String> ph_am = Optional.empty();
        private Optional<String> ph_am_mp3 = Optional.empty();
        private Optional<String> ph_en = Optional.empty();
        private Optional<String> ph_en_mp3 = Optional.empty();

        public Symbols setPhAm(String str) {
            this.ph_am = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPhAm() {
            return this.ph_am;
        }

        public Symbols setPhAmMp3(String str) {
            this.ph_am_mp3 = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPhAmMp3() {
            return this.ph_am_mp3;
        }

        public Symbols setPhEn(String str) {
            this.ph_en = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPhEn() {
            return this.ph_en;
        }

        public Symbols setPhEnMp3(String str) {
            this.ph_en_mp3 = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPhEnMp3() {
            return this.ph_en_mp3;
        }
    }

    public static class SymbolsChinese {
        private Optional<String> word_name = Optional.empty();
        private Optional<Boolean> in_en_dictionary = Optional.empty();
        private Optional<String> ph_am = Optional.empty();
        private Optional<String> ph_am_mp3 = Optional.empty();
        private Optional<String> ph_en = Optional.empty();
        private Optional<String> ph_en_mp3 = Optional.empty();
        private Optional<List<SymbolsParts>> parts = Optional.empty();

        public SymbolsChinese setWordName(String str) {
            this.word_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getWordName() {
            return this.word_name;
        }

        public SymbolsChinese setInEnDictionary(boolean z) {
            this.in_en_dictionary = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isInEnDictionary() {
            return this.in_en_dictionary;
        }

        public SymbolsChinese setPhAm(String str) {
            this.ph_am = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPhAm() {
            return this.ph_am;
        }

        public SymbolsChinese setPhAmMp3(String str) {
            this.ph_am_mp3 = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPhAmMp3() {
            return this.ph_am_mp3;
        }

        public SymbolsChinese setPhEn(String str) {
            this.ph_en = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPhEn() {
            return this.ph_en;
        }

        public SymbolsChinese setPhEnMp3(String str) {
            this.ph_en_mp3 = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPhEnMp3() {
            return this.ph_en_mp3;
        }

        public SymbolsChinese setParts(List<SymbolsParts> list) {
            this.parts = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SymbolsParts>> getParts() {
            return this.parts;
        }
    }

    public static class SymbolsParts {
        private Optional<String> part = Optional.empty();
        private Optional<List<String>> means = Optional.empty();

        public SymbolsParts setPart(String str) {
            this.part = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPart() {
            return this.part;
        }

        public SymbolsParts setMeans(List<String> list) {
            this.means = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getMeans() {
            return this.means;
        }
    }

    public static class TransChineseResponse {

        @Required
        private Mode mode;

        @Required
        private String word_name;
        private Optional<List<ChineseSentence>> sentence = Optional.empty();
        private Optional<List<SymbolsChinese>> symbols = Optional.empty();
        private Optional<Chinese> chinese = Optional.empty();

        public TransChineseResponse() {
        }

        public TransChineseResponse(Mode mode, String str) {
            this.mode = mode;
            this.word_name = str;
        }

        @Required
        public TransChineseResponse setMode(Mode mode) {
            this.mode = mode;
            return this;
        }

        @Required
        public Mode getMode() {
            return this.mode;
        }

        @Required
        public TransChineseResponse setWordName(String str) {
            this.word_name = str;
            return this;
        }

        @Required
        public String getWordName() {
            return this.word_name;
        }

        public TransChineseResponse setSentence(List<ChineseSentence> list) {
            this.sentence = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<ChineseSentence>> getSentence() {
            return this.sentence;
        }

        public TransChineseResponse setSymbols(List<SymbolsChinese> list) {
            this.symbols = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SymbolsChinese>> getSymbols() {
            return this.symbols;
        }

        public TransChineseResponse setChinese(Chinese chinese) {
            this.chinese = Optional.ofNullable(chinese);
            return this;
        }

        public Optional<Chinese> getChinese() {
            return this.chinese;
        }
    }

    public static class TransResponse {

        @Required
        private Mode mode;

        @Required
        private String word_name;
        private Optional<List<Sentence>> sentence = Optional.empty();
        private Optional<List<WordInformation>> synonym = Optional.empty();
        private Optional<List<WordInformation>> antonym = Optional.empty();
        private Optional<List<Integer>> word_tag = Optional.empty();
        private Optional<Parts> parts = Optional.empty();
        private Optional<Exchange> exchange = Optional.empty();
        private Optional<Symbols> symbols = Optional.empty();

        public TransResponse() {
        }

        public TransResponse(Mode mode, String str) {
            this.mode = mode;
            this.word_name = str;
        }

        @Required
        public TransResponse setMode(Mode mode) {
            this.mode = mode;
            return this;
        }

        @Required
        public Mode getMode() {
            return this.mode;
        }

        @Required
        public TransResponse setWordName(String str) {
            this.word_name = str;
            return this;
        }

        @Required
        public String getWordName() {
            return this.word_name;
        }

        public TransResponse setSentence(List<Sentence> list) {
            this.sentence = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Sentence>> getSentence() {
            return this.sentence;
        }

        public TransResponse setSynonym(List<WordInformation> list) {
            this.synonym = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<WordInformation>> getSynonym() {
            return this.synonym;
        }

        public TransResponse setAntonym(List<WordInformation> list) {
            this.antonym = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<WordInformation>> getAntonym() {
            return this.antonym;
        }

        public TransResponse setWordTag(List<Integer> list) {
            this.word_tag = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Integer>> getWordTag() {
            return this.word_tag;
        }

        public TransResponse setParts(Parts parts) {
            this.parts = Optional.ofNullable(parts);
            return this;
        }

        public Optional<Parts> getParts() {
            return this.parts;
        }

        public TransResponse setExchange(Exchange exchange) {
            this.exchange = Optional.ofNullable(exchange);
            return this;
        }

        public Optional<Exchange> getExchange() {
            return this.exchange;
        }

        public TransResponse setSymbols(Symbols symbols) {
            this.symbols = Optional.ofNullable(symbols);
            return this;
        }

        public Optional<Symbols> getSymbols() {
            return this.symbols;
        }
    }

    public static class TransTranslateResponse {

        @Required
        private Mode mode;
        private Optional<String> result = Optional.empty();

        public TransTranslateResponse() {
        }

        public TransTranslateResponse(Mode mode) {
            this.mode = mode;
        }

        @Required
        public TransTranslateResponse setMode(Mode mode) {
            this.mode = mode;
            return this;
        }

        @Required
        public Mode getMode() {
            return this.mode;
        }

        public TransTranslateResponse setResult(String str) {
            this.result = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResult() {
            return this.result;
        }
    }

    public static class TranslateRequest {

        @Required
        private String from;

        @Required
        private String text;

        @Required
        private String to;

        public TranslateRequest() {
        }

        public TranslateRequest(String str, String str2, String str3) {
            this.from = str;
            this.to = str2;
            this.text = str3;
        }

        @Required
        public TranslateRequest setFrom(String str) {
            this.from = str;
            return this;
        }

        @Required
        public String getFrom() {
            return this.from;
        }

        @Required
        public TranslateRequest setTo(String str) {
            this.to = str;
            return this;
        }

        @Required
        public String getTo() {
            return this.to;
        }

        @Required
        public TranslateRequest setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }
    }

    public static class TranslateResponse {
        private Optional<String> result = Optional.empty();

        public TranslateResponse setResult(String str) {
            this.result = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getResult() {
            return this.result;
        }
    }

    public static class WordInformation {
        private Optional<String> part_name = Optional.empty();
        private Optional<List<Means>> means = Optional.empty();

        public WordInformation setPartName(String str) {
            this.part_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPartName() {
            return this.part_name;
        }

        public WordInformation setMeans(List<Means> list) {
            this.means = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Means>> getMeans() {
            return this.means;
        }
    }
}
