package com.miui.richeditor;

import android.text.Layout;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import com.miui.richeditor.style.CheckboxSpan;
import com.miui.richeditor.style.EditLineHeightSpan;
import com.miui.richeditor.style.FontLargeSizeSpan;
import com.miui.richeditor.style.FontMidSizeSpan;
import com.miui.richeditor.style.KeywordsSpan;

/* JADX INFO: loaded from: classes4.dex */
public class SpanInfo extends RegionInfo {
    public static final int STYLE_BGHIGHLIGHT = 11;
    public static final int STYLE_BOLD = 1;
    static final int STYLE_BULLET = 9;
    static final int STYLE_CENTER = 4;
    static final int STYLE_CHECKBOX = 13;
    static final int STYLE_DEFAULT_SIZE = 12;
    public static final int STYLE_ITALIC = 2;
    static final int STYLE_KEYWORDS = 7;
    static final int STYLE_LARGE_SIZE = 3;
    static final int STYLE_LINEHEIGHT = 14;
    static final int STYLE_MIDDLE_SIZE = 10;
    static final int STYLE_RELATIVE_SIZE = 8;
    static final int STYLE_RIGHT = 6;
    static final int STYLE_TYPEFACE = 14;
    static final int STYLE_UNDEFINED = -1;
    public static final int STYLE_UNDERLINE = 5;
    private Object mSpan;
    private int mStyle;

    @Override // com.miui.richeditor.RegionInfo
    public /* bridge */ /* synthetic */ int compareTo(RegionInfo regionInfo) {
        return super.compareTo(regionInfo);
    }

    @Override // com.miui.richeditor.RegionInfo
    public /* bridge */ /* synthetic */ int getEnd() {
        return super.getEnd();
    }

    @Override // com.miui.richeditor.RegionInfo
    public /* bridge */ /* synthetic */ int getFlag() {
        return super.getFlag();
    }

    @Override // com.miui.richeditor.RegionInfo
    public /* bridge */ /* synthetic */ int getStart() {
        return super.getStart();
    }

    @Override // com.miui.richeditor.RegionInfo
    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Override // com.miui.richeditor.RegionInfo
    public /* bridge */ /* synthetic */ void setEnd(int i) {
        super.setEnd(i);
    }

    @Override // com.miui.richeditor.RegionInfo
    public /* bridge */ /* synthetic */ void setStart(int i) {
        super.setStart(i);
    }

    @Override // com.miui.richeditor.RegionInfo
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public SpanInfo(Object obj, int i, int i2, int i3, float f) {
        super(i, i2, i3);
        this.mStyle = -1;
        this.mSpan = obj;
        if (obj instanceof FontLargeSizeSpan) {
            this.mStyle = 3;
        } else if (obj instanceof FontMidSizeSpan) {
            this.mStyle = 10;
        } else if (obj instanceof RelativeSizeSpan) {
            this.mStyle = 8;
        } else if (obj instanceof AbsoluteSizeSpan) {
            this.mStyle = 12;
        } else if (obj instanceof StyleSpan) {
            this.mStyle = ((StyleSpan) obj).getStyle();
        } else if (obj instanceof AlignmentSpan) {
            AlignmentSpan alignmentSpan = (AlignmentSpan) obj;
            if (alignmentSpan.getAlignment() == Layout.Alignment.ALIGN_CENTER) {
                this.mStyle = 4;
            } else if (alignmentSpan.getAlignment() == Layout.Alignment.ALIGN_OPPOSITE) {
                this.mStyle = 6;
            }
        } else if (obj instanceof UnderlineSpan) {
            this.mStyle = 5;
        } else if (obj instanceof BackgroundColorSpan) {
            this.mStyle = 11;
        } else if (obj instanceof KeywordsSpan) {
            this.mStyle = 7;
        } else if (obj instanceof CheckboxSpan) {
            this.mStyle = 13;
        } else if (obj instanceof BulletSpan) {
            this.mStyle = 9;
        } else if ((obj instanceof EditLineHeightSpan) || (obj instanceof TypefaceSpan)) {
            this.mStyle = 14;
        }
        if (this.mStyle == -1) {
            throw new IllegalArgumentException("Span style must be defined!");
        }
    }

    @Override // com.miui.richeditor.RegionInfo
    public boolean isValid() {
        return getStart() >= 0 && getStart() <= getEnd();
    }

    public Object getSpan() {
        return this.mSpan;
    }

    public int getStyle() {
        return this.mStyle;
    }

    public boolean isStyleDesigned() {
        return this.mStyle != -1;
    }

    @Override // com.miui.richeditor.RegionInfo
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return super.equals(obj) && this.mStyle == ((SpanInfo) obj).getStyle();
        }
        return false;
    }

    @Override // com.miui.richeditor.RegionInfo
    public int hashCode() {
        int iHashCode = super.hashCode();
        long j = this.mStyle;
        return (iHashCode * 37) + ((int) (j ^ (j >>> 32)));
    }
}
