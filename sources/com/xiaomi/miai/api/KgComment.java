package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.Required;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class KgComment {

    public static class Buckets {

        @Required
        private List<Tag> main;

        public Buckets() {
        }

        public Buckets(List<Tag> list) {
            this.main = list;
        }

        @Required
        public Buckets setMain(List<Tag> list) {
            this.main = list;
            return this;
        }

        @Required
        public List<Tag> getMain() {
            return this.main;
        }
    }

    public static class ClassParameter {

        @Required
        private String class_name;

        public ClassParameter() {
        }

        public ClassParameter(String str) {
            this.class_name = str;
        }

        @Required
        public ClassParameter setClassName(String str) {
            this.class_name = str;
            return this;
        }

        @Required
        public String getClassName() {
            return this.class_name;
        }
    }

    public static class CommentInfo {

        @Required
        private int _id;

        @Required
        private String comment_text;

        @Required
        private int gid;

        @Required
        private String gid_name;

        @Required
        private int pid;

        @Required
        private int score;
        private Optional<List<String>> new_category = Optional.empty();
        private Optional<ClassParameter> class_parameters = Optional.empty();

        public CommentInfo() {
        }

        public CommentInfo(int i, String str, int i2, String str2, int i3, int i4) {
            this._id = i;
            this.comment_text = str;
            this.gid = i2;
            this.gid_name = str2;
            this.pid = i3;
            this.score = i4;
        }

        @Required
        public CommentInfo setId(int i) {
            this._id = i;
            return this;
        }

        @Required
        public int getId() {
            return this._id;
        }

        @Required
        public CommentInfo setCommentText(String str) {
            this.comment_text = str;
            return this;
        }

        @Required
        public String getCommentText() {
            return this.comment_text;
        }

        @Required
        public CommentInfo setGid(int i) {
            this.gid = i;
            return this;
        }

        @Required
        public int getGid() {
            return this.gid;
        }

        @Required
        public CommentInfo setGidName(String str) {
            this.gid_name = str;
            return this;
        }

        @Required
        public String getGidName() {
            return this.gid_name;
        }

        @Required
        public CommentInfo setPid(int i) {
            this.pid = i;
            return this;
        }

        @Required
        public int getPid() {
            return this.pid;
        }

        @Required
        public CommentInfo setScore(int i) {
            this.score = i;
            return this;
        }

        @Required
        public int getScore() {
            return this.score;
        }

        public CommentInfo setNewCategory(List<String> list) {
            this.new_category = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<String>> getNewCategory() {
            return this.new_category;
        }

        public CommentInfo setClassParameters(ClassParameter classParameter) {
            this.class_parameters = Optional.ofNullable(classParameter);
            return this;
        }

        public Optional<ClassParameter> getClassParameters() {
            return this.class_parameters;
        }
    }

    public static class CommentRequest {

        @Required
        private List<CommentInfo> content;

        @Required
        private String data_type;

        public CommentRequest() {
        }

        public CommentRequest(String str, List<CommentInfo> list) {
            this.data_type = str;
            this.content = list;
        }

        @Required
        public CommentRequest setDataType(String str) {
            this.data_type = str;
            return this;
        }

        @Required
        public String getDataType() {
            return this.data_type;
        }

        @Required
        public CommentRequest setContent(List<CommentInfo> list) {
            this.content = list;
            return this;
        }

        @Required
        public List<CommentInfo> getContent() {
            return this.content;
        }
    }

    public static class CommentResponse {

        @Required
        private String data_type;

        @Required
        private List<ContentResult> result;

        public CommentResponse() {
        }

        public CommentResponse(String str, List<ContentResult> list) {
            this.data_type = str;
            this.result = list;
        }

        @Required
        public CommentResponse setDataType(String str) {
            this.data_type = str;
            return this;
        }

        @Required
        public String getDataType() {
            return this.data_type;
        }

        @Required
        public CommentResponse setResult(List<ContentResult> list) {
            this.result = list;
            return this;
        }

        @Required
        public List<ContentResult> getResult() {
            return this.result;
        }
    }

    public static class ContentResult {

        @Required
        private String $atthirdClassify;

        @Required
        private int _id;

        @Required
        private Buckets buckets;

        @Required
        private String gid;

        @Required
        private List<Tag> tags;

        @Required
        private long update_at_ms;

        public ContentResult() {
        }

        public ContentResult(int i, List<Tag> list, Buckets buckets, String str, long j, String str2) {
            this._id = i;
            this.tags = list;
            this.buckets = buckets;
            this.$atthirdClassify = str;
            this.update_at_ms = j;
            this.gid = str2;
        }

        @Required
        public ContentResult setId(int i) {
            this._id = i;
            return this;
        }

        @Required
        public int getId() {
            return this._id;
        }

        @Required
        public ContentResult setTags(List<Tag> list) {
            this.tags = list;
            return this;
        }

        @Required
        public List<Tag> getTags() {
            return this.tags;
        }

        @Required
        public ContentResult setBuckets(Buckets buckets) {
            this.buckets = buckets;
            return this;
        }

        @Required
        public Buckets getBuckets() {
            return this.buckets;
        }

        @Required
        public ContentResult set$atthirdClassify(String str) {
            this.$atthirdClassify = str;
            return this;
        }

        @Required
        public String get$atthirdClassify() {
            return this.$atthirdClassify;
        }

        @Required
        public ContentResult setUpdateAtMs(long j) {
            this.update_at_ms = j;
            return this;
        }

        @Required
        public long getUpdateAtMs() {
            return this.update_at_ms;
        }

        @Required
        public ContentResult setGid(String str) {
            this.gid = str;
            return this;
        }

        @Required
        public String getGid() {
            return this.gid;
        }
    }

    public static class Tag {

        @Required
        private String id;

        @Required
        private String name;

        @Required
        private int positive;

        public Tag() {
        }

        public Tag(String str, int i, String str2) {
            this.name = str;
            this.positive = i;
            this.id = str2;
        }

        @Required
        public Tag setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public Tag setPositive(int i) {
            this.positive = i;
            return this;
        }

        @Required
        public int getPositive() {
            return this.positive;
        }

        @Required
        public Tag setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }
    }
}
