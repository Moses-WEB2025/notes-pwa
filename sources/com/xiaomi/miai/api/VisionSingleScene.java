package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.Required;

/* JADX INFO: loaded from: classes4.dex */
public class VisionSingleScene {

    public static class SingleSceneRequest {

        @Required
        private String url;
        private Optional<String> id = Optional.empty();
        private Optional<String> category = Optional.empty();
        private Optional<String> ctype = Optional.empty();

        public SingleSceneRequest() {
        }

        public SingleSceneRequest(String str) {
            this.url = str;
        }

        @Required
        public SingleSceneRequest setUrl(String str) {
            this.url = str;
            return this;
        }

        @Required
        public String getUrl() {
            return this.url;
        }

        public SingleSceneRequest setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }

        public SingleSceneRequest setCategory(String str) {
            this.category = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCategory() {
            return this.category;
        }

        public SingleSceneRequest setCtype(String str) {
            this.ctype = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCtype() {
            return this.ctype;
        }
    }

    public static class SingleSceneResponse {
        private Optional<Boolean> is_single_scene = Optional.empty();
        private Optional<Double> single_score = Optional.empty();
        private Optional<Double> not_single_score = Optional.empty();
        private Optional<String> desc_msg = Optional.empty();
        private Optional<String> error_msg = Optional.empty();

        public SingleSceneResponse setIsSingleScene(boolean z) {
            this.is_single_scene = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSingleScene() {
            return this.is_single_scene;
        }

        public SingleSceneResponse setSingleScore(double d) {
            this.single_score = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getSingleScore() {
            return this.single_score;
        }

        public SingleSceneResponse setNotSingleScore(double d) {
            this.not_single_score = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getNotSingleScore() {
            return this.not_single_score;
        }

        public SingleSceneResponse setDescMsg(String str) {
            this.desc_msg = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDescMsg() {
            return this.desc_msg;
        }

        public SingleSceneResponse setErrorMsg(String str) {
            this.error_msg = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getErrorMsg() {
            return this.error_msg;
        }
    }
}
