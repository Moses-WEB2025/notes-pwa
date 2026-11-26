package com.xiaomi.aicr.vision.ocr;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes4.dex */
public class OCRRes {
    public static final int ENTITY_TYPE_DELIVERY_NUMBER = 3;
    public static final int ENTITY_TYPE_EMAIL = 2;
    public static final int ENTITY_TYPE_PHONE_NUMBER = 1;
    public static final int ENTITY_TYPE_WEBSITE = 0;
    public static final int ERR_CODE_MODEL_FAIL = -2;
    public static final int ERR_CODE_NOT_INIT = -1;

    public enum FocusFlags {
        FOCUS_TYPE_AUTO,
        FOCUS_TYPE_FORCE
    }

    public static class FocusPoint {
        public float norm_x;
        public float norm_y;
        public int type;
    }

    public static class RegionData {
        public Bitmap regionImg;
        public Location regionLoc;
        public Location[] texts_locations;
    }

    public static class RegionDetectResult {
        public long nextTimestamp;
        public Location regionLoc;
    }

    public enum RotateFlags {
        ROTATE_0,
        ROTATE_90,
        ROTATE_180,
        ROTATE_270
    }

    public static class YUVImage {
        public boolean is_dump;
        public int rotate;
        public long timestamp;
        public byte[] yuv_data;
        public int yuv_height;
        public int yuv_width;
    }

    public static class Location implements Parcelable {
        public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.Location.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Location createFromParcel(Parcel parcel) {
                return new Location(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Location[] newArray(int i) {
                return new Location[i];
            }
        };
        public float[] box;
        public float[] poly;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected Location(Parcel parcel) {
            this.poly = parcel.createFloatArray();
            this.box = parcel.createFloatArray();
        }

        public Location() {
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeFloatArray(this.poly);
            parcel.writeFloatArray(this.box);
        }
    }

    public static class OCRChar implements Parcelable {
        public static final Parcelable.Creator<OCRChar> CREATOR = new Parcelable.Creator<OCRChar>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.OCRChar.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRChar createFromParcel(Parcel parcel) {
                return new OCRChar(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRChar[] newArray(int i) {
                return new OCRChar[i];
            }
        };
        public float char_confidence;
        public String char_text;
        public Location location;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected OCRChar(Parcel parcel) {
            this.location = (Location) parcel.readParcelable(Location.class.getClassLoader());
            this.char_text = parcel.readString();
            this.char_confidence = parcel.readFloat();
        }

        public OCRChar() {
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.location, i);
            parcel.writeString(this.char_text);
            parcel.writeFloat(this.char_confidence);
        }
    }

    public static class OCRLine implements Parcelable {
        public static final Parcelable.Creator<OCRLine> CREATOR = new Parcelable.Creator<OCRLine>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.OCRLine.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRLine createFromParcel(Parcel parcel) {
                return new OCRLine(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRLine[] newArray(int i) {
                return new OCRLine[i];
            }
        };
        public OCRChar[] Character;
        public float line_confidence;
        public int line_id;
        public String line_text;
        public Location location;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public OCRLine() {
        }

        protected OCRLine(Parcel parcel) {
            this.location = (Location) parcel.readParcelable(Location.class.getClassLoader());
            this.line_text = parcel.readString();
            this.line_id = parcel.readInt();
            this.Character = (OCRChar[]) parcel.createTypedArray(OCRChar.CREATOR);
            this.line_confidence = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.location, i);
            parcel.writeString(this.line_text);
            parcel.writeInt(this.line_id);
            parcel.writeTypedArray(this.Character, i);
            parcel.writeFloat(this.line_confidence);
        }
    }

    public static class OCRParagraph implements Parcelable {
        public static final Parcelable.Creator<OCRParagraph> CREATOR = new Parcelable.Creator<OCRParagraph>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.OCRParagraph.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRParagraph createFromParcel(Parcel parcel) {
                return new OCRParagraph(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRParagraph[] newArray(int i) {
                return new OCRParagraph[i];
            }
        };
        public OCRLine[] lines;
        public String paragraph_text;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected OCRParagraph(Parcel parcel) {
            this.lines = (OCRLine[]) parcel.createTypedArray(OCRLine.CREATOR);
            this.paragraph_text = parcel.readString();
        }

        public OCRParagraph() {
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedArray(this.lines, i);
            parcel.writeString(this.paragraph_text);
        }
    }

    public static class OCREntity implements Parcelable {
        public static final Parcelable.Creator<OCREntity> CREATOR = new Parcelable.Creator<OCREntity>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.OCREntity.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCREntity createFromParcel(Parcel parcel) {
                return new OCREntity(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCREntity[] newArray(int i) {
                return new OCREntity[i];
            }
        };
        public int end_index;
        public String entity_text;
        public int entity_type;
        public int line_id;
        public Location location;
        public int start_index;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        protected OCREntity(Parcel parcel) {
            this.location = (Location) parcel.readParcelable(Location.class.getClassLoader());
            this.entity_text = parcel.readString();
            this.entity_type = parcel.readInt();
            this.line_id = parcel.readInt();
            this.start_index = parcel.readInt();
            this.end_index = parcel.readInt();
        }

        public OCREntity() {
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.location, i);
            parcel.writeString(this.entity_text);
            parcel.writeInt(this.entity_type);
            parcel.writeInt(this.line_id);
            parcel.writeInt(this.start_index);
            parcel.writeInt(this.end_index);
        }
    }

    public static class OCRResult implements Parcelable {
        public static final Parcelable.Creator<OCRResult> CREATOR = new Parcelable.Creator<OCRResult>() { // from class: com.xiaomi.aicr.vision.ocr.OCRRes.OCRResult.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRResult createFromParcel(Parcel parcel) {
                return new OCRResult(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public OCRResult[] newArray(int i) {
                return new OCRResult[i];
            }
        };
        public OCREntity[] entities;
        public OCRParagraph[] paragraphs;
        public String total_text;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public OCRResult() {
        }

        protected OCRResult(Parcel parcel) {
            this.paragraphs = (OCRParagraph[]) parcel.createTypedArray(OCRParagraph.CREATOR);
            this.total_text = parcel.readString();
            this.entities = (OCREntity[]) parcel.createTypedArray(OCREntity.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedArray(this.paragraphs, i);
            parcel.writeString(this.total_text);
            parcel.writeTypedArray(this.entities, i);
        }
    }
}
