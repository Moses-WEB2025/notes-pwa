package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.Required;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class VisionSKY {

    public enum ImageType {
        jpg("jpg", 0),
        png("png", 1),
        webp("webp", 2);

        private String _name;
        private int id;

        ImageType(String str, int i) {
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

    public static class SkyRequest {
        private Optional<Integer> index = Optional.empty();

        @Required
        private String scene;

        public SkyRequest() {
        }

        public SkyRequest(String str) {
            this.scene = str;
        }

        @Required
        public SkyRequest setScene(String str) {
            this.scene = str;
            return this;
        }

        @Required
        public String getScene() {
            return this.scene;
        }

        public SkyRequest setIndex(int i) {
            this.index = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getIndex() {
            return this.index;
        }
    }

    public static class SkyResponse {

        @Required
        private String image;

        @Required
        private ImageType image_type;

        @Required
        private int index;

        @Required
        private String scene;

        public SkyResponse() {
        }

        public SkyResponse(String str, ImageType imageType, String str2, int i) {
            this.image = str;
            this.image_type = imageType;
            this.scene = str2;
            this.index = i;
        }

        @Required
        public SkyResponse setImage(String str) {
            this.image = str;
            return this;
        }

        @Required
        public String getImage() {
            return this.image;
        }

        @Required
        public SkyResponse setImageType(ImageType imageType) {
            this.image_type = imageType;
            return this;
        }

        @Required
        public ImageType getImageType() {
            return this.image_type;
        }

        @Required
        public SkyResponse setScene(String str) {
            this.scene = str;
            return this;
        }

        @Required
        public String getScene() {
            return this.scene;
        }

        @Required
        public SkyResponse setIndex(int i) {
            this.index = i;
            return this;
        }

        @Required
        public int getIndex() {
            return this.index;
        }
    }

    public static class ZkSkyData {

        @Required
        private List<ZkSkySence> sceneInfo;

        public ZkSkyData() {
        }

        public ZkSkyData(List<ZkSkySence> list) {
            this.sceneInfo = list;
        }

        @Required
        public ZkSkyData setSceneInfo(List<ZkSkySence> list) {
            this.sceneInfo = list;
            return this;
        }

        @Required
        public List<ZkSkySence> getSceneInfo() {
            return this.sceneInfo;
        }
    }

    public static class ZkSkySence {

        @Required
        private String date;

        @Required
        private String scene;

        @Required
        private String suffix;

        @Required
        private int total;

        public ZkSkySence() {
        }

        public ZkSkySence(String str, String str2, String str3, int i) {
            this.date = str;
            this.scene = str2;
            this.suffix = str3;
            this.total = i;
        }

        @Required
        public ZkSkySence setDate(String str) {
            this.date = str;
            return this;
        }

        @Required
        public String getDate() {
            return this.date;
        }

        @Required
        public ZkSkySence setScene(String str) {
            this.scene = str;
            return this;
        }

        @Required
        public String getScene() {
            return this.scene;
        }

        @Required
        public ZkSkySence setSuffix(String str) {
            this.suffix = str;
            return this;
        }

        @Required
        public String getSuffix() {
            return this.suffix;
        }

        @Required
        public ZkSkySence setTotal(int i) {
            this.total = i;
            return this;
        }

        @Required
        public int getTotal() {
            return this.total;
        }
    }
}
