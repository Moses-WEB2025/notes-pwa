package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.Required;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class KgEntity {

    public static class Candidate {

        @Required
        private List<EntityLink> candidates;

        @Required
        private int len;

        @Required
        private String mention;

        @Required
        private int offset;

        public Candidate() {
        }

        public Candidate(String str, int i, int i2, List<EntityLink> list) {
            this.mention = str;
            this.len = i;
            this.offset = i2;
            this.candidates = list;
        }

        @Required
        public Candidate setMention(String str) {
            this.mention = str;
            return this;
        }

        @Required
        public String getMention() {
            return this.mention;
        }

        @Required
        public Candidate setLen(int i) {
            this.len = i;
            return this;
        }

        @Required
        public int getLen() {
            return this.len;
        }

        @Required
        public Candidate setOffset(int i) {
            this.offset = i;
            return this;
        }

        @Required
        public int getOffset() {
            return this.offset;
        }

        @Required
        public Candidate setCandidates(List<EntityLink> list) {
            this.candidates = list;
            return this;
        }

        @Required
        public List<EntityLink> getCandidates() {
            return this.candidates;
        }
    }

    public static class EntityLink {

        @Required
        private String desc;

        @Required
        private String miId;

        @Required
        private double rankScore;

        @Required
        private List<String> tags;

        @Required
        private String title;

        public EntityLink() {
        }

        public EntityLink(String str, String str2, List<String> list, double d, String str3) {
            this.title = str;
            this.miId = str2;
            this.tags = list;
            this.rankScore = d;
            this.desc = str3;
        }

        @Required
        public EntityLink setTitle(String str) {
            this.title = str;
            return this;
        }

        @Required
        public String getTitle() {
            return this.title;
        }

        @Required
        public EntityLink setMiId(String str) {
            this.miId = str;
            return this;
        }

        @Required
        public String getMiId() {
            return this.miId;
        }

        @Required
        public EntityLink setTags(List<String> list) {
            this.tags = list;
            return this;
        }

        @Required
        public List<String> getTags() {
            return this.tags;
        }

        @Required
        public EntityLink setRankScore(double d) {
            this.rankScore = d;
            return this;
        }

        @Required
        public double getRankScore() {
            return this.rankScore;
        }

        @Required
        public EntityLink setDesc(String str) {
            this.desc = str;
            return this;
        }

        @Required
        public String getDesc() {
            return this.desc;
        }
    }

    public static class EntityLinkRequest {

        @Required
        private String text;

        public EntityLinkRequest() {
        }

        public EntityLinkRequest(String str) {
            this.text = str;
        }

        @Required
        public EntityLinkRequest setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }
    }

    public static class EntityLinkResponse {

        @Required
        private List<Candidate> entities;

        public EntityLinkResponse() {
        }

        public EntityLinkResponse(List<Candidate> list) {
            this.entities = list;
        }

        @Required
        public EntityLinkResponse setEntities(List<Candidate> list) {
            this.entities = list;
            return this;
        }

        @Required
        public List<Candidate> getEntities() {
            return this.entities;
        }
    }

    public static class EntitySearchRequest {
        private Optional<String> eid = Optional.empty();
        private Optional<String> e = Optional.empty();
        private Optional<String> epy = Optional.empty();
        private Optional<Integer> start = Optional.empty();
        private Optional<Integer> pageSize = Optional.empty();

        public EntitySearchRequest setEid(String str) {
            this.eid = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEid() {
            return this.eid;
        }

        public EntitySearchRequest setE(String str) {
            this.e = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getE() {
            return this.e;
        }

        public EntitySearchRequest setEpy(String str) {
            this.epy = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getEpy() {
            return this.epy;
        }

        public EntitySearchRequest setStart(int i) {
            this.start = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getStart() {
            return this.start;
        }

        public EntitySearchRequest setPageSize(int i) {
            this.pageSize = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getPageSize() {
            return this.pageSize;
        }
    }

    public static class EntitySearchResponse {
        private Optional<List<SearchEntity>> entities = Optional.empty();

        public EntitySearchResponse setEntities(List<SearchEntity> list) {
            this.entities = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SearchEntity>> getEntities() {
            return this.entities;
        }
    }

    public static class InfoValue {
        private Optional<String> object = Optional.empty();
        private Optional<Float> confidence = Optional.empty();
        private Optional<String> target = Optional.empty();
        private Optional<List<String>> src = Optional.empty();

        public InfoValue setObject(String str) {
            this.object = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getObject() {
            return this.object;
        }

        public InfoValue setConfidence(float f) {
            this.confidence = Optional.ofNullable(Float.valueOf(f));
            return this;
        }

        public Optional<Float> getConfidence() {
            return this.confidence;
        }

        public InfoValue setTarget(String str) {
            this.target = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTarget() {
            return this.target;
        }

        public InfoValue setSrc(List<String> list) {
            this.src = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getSrc() {
            return this.src;
        }
    }

    public static class SearchEntity {

        @Required
        private List<String> domain;

        @Required
        private String id;

        @Required
        private String label;

        @Required
        private int popularity;
        private Optional<String> desc = Optional.empty();
        private Optional<String> summary = Optional.empty();
        private Optional<List<String>> pinyin = Optional.empty();
        private Optional<List<String>> landingPage = Optional.empty();
        private Optional<List<String>> synonym = Optional.empty();
        private Optional<List<String>> type = Optional.empty();
        private Optional<List<String>> oakType = Optional.empty();
        private Optional<List<SearchImage>> IMAGE = Optional.empty();
        private Optional<String> hudongbaike_identifier = Optional.empty();
        private Optional<String> baidubaike_url = Optional.empty();
        private Optional<List<SearchInfo>> INFO = Optional.empty();

        public SearchEntity() {
        }

        public SearchEntity(String str, String str2, int i, List<String> list) {
            this.id = str;
            this.label = str2;
            this.popularity = i;
            this.domain = list;
        }

        @Required
        public SearchEntity setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public SearchEntity setLabel(String str) {
            this.label = str;
            return this;
        }

        @Required
        public String getLabel() {
            return this.label;
        }

        @Required
        public SearchEntity setPopularity(int i) {
            this.popularity = i;
            return this;
        }

        @Required
        public int getPopularity() {
            return this.popularity;
        }

        public SearchEntity setDesc(String str) {
            this.desc = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDesc() {
            return this.desc;
        }

        public SearchEntity setSummary(String str) {
            this.summary = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getSummary() {
            return this.summary;
        }

        @Required
        public SearchEntity setDomain(List<String> list) {
            this.domain = list;
            return this;
        }

        @Required
        public List<String> getDomain() {
            return this.domain;
        }

        public SearchEntity setPinyin(List<String> list) {
            this.pinyin = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getPinyin() {
            return this.pinyin;
        }

        public SearchEntity setLandingPage(List<String> list) {
            this.landingPage = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getLandingPage() {
            return this.landingPage;
        }

        public SearchEntity setSynonym(List<String> list) {
            this.synonym = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getSynonym() {
            return this.synonym;
        }

        public SearchEntity setType(List<String> list) {
            this.type = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getType() {
            return this.type;
        }

        public SearchEntity setOakType(List<String> list) {
            this.oakType = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getOakType() {
            return this.oakType;
        }

        public SearchEntity setIMAGE(List<SearchImage> list) {
            this.IMAGE = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SearchImage>> getIMAGE() {
            return this.IMAGE;
        }

        public SearchEntity setHudongbaikeIdentifier(String str) {
            this.hudongbaike_identifier = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getHudongbaikeIdentifier() {
            return this.hudongbaike_identifier;
        }

        public SearchEntity setBaidubaikeUrl(String str) {
            this.baidubaike_url = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getBaidubaikeUrl() {
            return this.baidubaike_url;
        }

        public SearchEntity setINFO(List<SearchInfo> list) {
            this.INFO = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<SearchInfo>> getINFO() {
            return this.INFO;
        }
    }

    public static class SearchImage {
        private Optional<Float> confidence = Optional.empty();
        private Optional<String> image = Optional.empty();

        public SearchImage setConfidence(float f) {
            this.confidence = Optional.ofNullable(Float.valueOf(f));
            return this;
        }

        public Optional<Float> getConfidence() {
            return this.confidence;
        }

        public SearchImage setImage(String str) {
            this.image = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getImage() {
            return this.image;
        }
    }

    public static class SearchInfo {
        private Optional<String> key = Optional.empty();
        private Optional<String> keyId = Optional.empty();
        private Optional<List<InfoValue>> value = Optional.empty();

        public SearchInfo setKey(String str) {
            this.key = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getKey() {
            return this.key;
        }

        public SearchInfo setKeyId(String str) {
            this.keyId = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getKeyId() {
            return this.keyId;
        }

        public SearchInfo setValue(List<InfoValue> list) {
            this.value = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<InfoValue>> getValue() {
            return this.value;
        }
    }
}
