package com.xiaomi.miai.api;

import com.xiaomi.common.Optional;
import com.xiaomi.miai.api.common.Required;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class VisionOCR {

    public enum OcrOrientation {
        UNKNOWN("UNKNOWN", -1),
        UP("UP", 0),
        LEFT("LEFT", 1),
        RIGHT("RIGHT", 2),
        DOWN("DOWN", 3);

        private String _name;
        private int id;

        OcrOrientation(String str, int i) {
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

    public static class Line {
        private Optional<Double> angle = Optional.empty();

        @Required
        private List<Point> box;

        @Required
        private List<Point> rect;

        @Required
        private String text;

        public Line() {
        }

        public Line(String str, List<Point> list, List<Point> list2) {
            this.text = str;
            this.box = list;
            this.rect = list2;
        }

        @Required
        public Line setText(String str) {
            this.text = str;
            return this;
        }

        @Required
        public String getText() {
            return this.text;
        }

        @Required
        public Line setBox(List<Point> list) {
            this.box = list;
            return this;
        }

        @Required
        public List<Point> getBox() {
            return this.box;
        }

        @Required
        public Line setRect(List<Point> list) {
            this.rect = list;
            return this;
        }

        @Required
        public List<Point> getRect() {
            return this.rect;
        }

        public Line setAngle(double d) {
            this.angle = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getAngle() {
            return this.angle;
        }
    }

    public static class OcrGeneralRequest {
        private Optional<Boolean> detect_rotate = Optional.empty();

        @Required
        private String image;

        public OcrGeneralRequest() {
        }

        public OcrGeneralRequest(String str) {
            this.image = str;
        }

        @Required
        public OcrGeneralRequest setImage(String str) {
            this.image = str;
            return this;
        }

        @Required
        public String getImage() {
            return this.image;
        }

        public OcrGeneralRequest setDetectRotate(boolean z) {
            this.detect_rotate = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isDetectRotate() {
            return this.detect_rotate;
        }
    }

    public static class OcrGeneralResponse {

        @Required
        private OcrOrientation orientation;

        @Required
        private List<Region> regions;

        public OcrGeneralResponse() {
        }

        public OcrGeneralResponse(OcrOrientation ocrOrientation, List<Region> list) {
            this.orientation = ocrOrientation;
            this.regions = list;
        }

        @Required
        public OcrGeneralResponse setOrientation(OcrOrientation ocrOrientation) {
            this.orientation = ocrOrientation;
            return this;
        }

        @Required
        public OcrOrientation getOrientation() {
            return this.orientation;
        }

        @Required
        public OcrGeneralResponse setRegions(List<Region> list) {
            this.regions = list;
            return this;
        }

        @Required
        public List<Region> getRegions() {
            return this.regions;
        }
    }

    public static class Point {

        @Required
        private int x;

        @Required
        private int y;

        public Point() {
        }

        public Point(int i, int i2) {
            this.x = i;
            this.y = i2;
        }

        @Required
        public Point setX(int i) {
            this.x = i;
            return this;
        }

        @Required
        public int getX() {
            return this.x;
        }

        @Required
        public Point setY(int i) {
            this.y = i;
            return this;
        }

        @Required
        public int getY() {
            return this.y;
        }
    }

    public static class Region {

        @Required
        private List<Line> lines;

        @Required
        private List<Point> region_box;

        public Region() {
        }

        public Region(List<Point> list, List<Line> list2) {
            this.region_box = list;
            this.lines = list2;
        }

        @Required
        public Region setRegionBox(List<Point> list) {
            this.region_box = list;
            return this;
        }

        @Required
        public List<Point> getRegionBox() {
            return this.region_box;
        }

        @Required
        public Region setLines(List<Line> list) {
            this.lines = list;
            return this;
        }

        @Required
        public List<Line> getLines() {
            return this.lines;
        }
    }

    public static class VisionOcrResponse {

        @Required
        private OcrGeneralResponse content;

        @Required
        private int errorCode;

        @Required
        private String errorMsg;

        public VisionOcrResponse() {
        }

        public VisionOcrResponse(int i, String str, OcrGeneralResponse ocrGeneralResponse) {
            this.errorCode = i;
            this.errorMsg = str;
            this.content = ocrGeneralResponse;
        }

        @Required
        public VisionOcrResponse setErrorCode(int i) {
            this.errorCode = i;
            return this;
        }

        @Required
        public int getErrorCode() {
            return this.errorCode;
        }

        @Required
        public VisionOcrResponse setErrorMsg(String str) {
            this.errorMsg = str;
            return this;
        }

        @Required
        public String getErrorMsg() {
            return this.errorMsg;
        }

        @Required
        public VisionOcrResponse setContent(OcrGeneralResponse ocrGeneralResponse) {
            this.content = ocrGeneralResponse;
            return this;
        }

        @Required
        public OcrGeneralResponse getContent() {
            return this.content;
        }
    }
}
