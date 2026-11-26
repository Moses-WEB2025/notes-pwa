package com.miui.duokantext;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import com.miui.duokantext.parser.FontCacheMgr;
import com.miui.notes.base.utils.Logger;
import defpackage.R2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class RichText {
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_JUSTIFY = 3;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 2;
    private static final int FORMAT_ALIGN;
    private static final int FORMAT_ALL = 65535;
    private static final int FORMAT_COLOR;
    private static final int FORMAT_DEFAULT_FONT_FILE;
    private static final int FORMAT_EMOJI_FONT_FILE;
    private static final int FORMAT_ENGLISH_FONT_FILE;
    private static final int FORMAT_FONT;
    private static final int FORMAT_FONT_SIZE;
    private static final int FORMAT_IGNORE_BEGIN_SPACE;
    private static final int FORMAT_INDENT;
    private static final int FORMAT_LINE_GAP;
    private static final int FORMAT_MARGIN_BOTTOM;
    private static final int FORMAT_MARGIN_LEFT;
    private static final int FORMAT_MARGIN_RIGHT;
    private static final int FORMAT_MARGIN_TOP;
    private static final int FORMAT_PARA_SPACING;
    private static final int FORMAT_STRIKE_THTOUGH;
    private static final int FORMAT_TYPE_BOOL = 4;
    private static final int FORMAT_TYPE_FLOAT = 2;
    private static final int FORMAT_TYPE_INT = 1;
    private static final int FORMAT_TYPE_OBJECT = 5;
    private static final int FORMAT_TYPE_STRING = 3;
    private static final int FORMAT_UNDER_LINE;
    private static final int FORMAT_VALIGN;
    private static final String LOG_TAG = "RichText";
    private static final String NULL_STRING = "";
    public static final int VALIGN_BASELINE = 0;
    public static final int VALIGN_BOTTOM = 3;
    public static final int VALIGN_MIDLE = 2;
    public static final int VALIGN_TOP = 1;
    private static Context mContext;
    private long mNativeHandle;
    private int mPreFontSize = 36;
    private String mText = "";

    private static final int makeId(int i, int i2) {
        return ((i & 15) << 12) | (i2 & R2.color.miuix_appcompat_btn_bg_warn_color_light);
    }

    private native void nativeAppendImage(Bitmap bitmap);

    private native void nativeAppendImage(Object obj);

    private native void nativeAppendSpace(int i, int i2);

    private native void nativeAppendText(String str);

    private native void nativeDestroy();

    private native void nativeEndFormat();

    private native void nativeInit();

    private native void nativeNewFormat();

    private native void nativeReset();

    private static native void nativeSetDefaultFormat(int i, float f);

    private static native void nativeSetDefaultFormat(int i, int i2);

    private static native void nativeSetDefaultFormat(int i, String str);

    private static native void nativeSetDefaultFormat(int i, boolean z);

    private native void nativeSetEnglishFont(String str, int i);

    private native void nativeSetFont(String str);

    private native void nativeSetFont(String str, int i);

    private native void nativeSetFormat(int i, float f);

    private native void nativeSetFormat(int i, int i2);

    private native void nativeSetFormat(int i, String str);

    private native void nativeSetFormat(int i, boolean z);

    private native void nativeSetFormat(int i, int... iArr);

    private native void nativeSetImageSize(int i, int i2);

    static {
        System.loadLibrary("duokantext");
        FORMAT_FONT = makeId(5, 1);
        FORMAT_FONT_SIZE = makeId(1, 2);
        FORMAT_COLOR = makeId(1, 3);
        FORMAT_ALIGN = makeId(1, 4);
        FORMAT_VALIGN = makeId(1, 5);
        FORMAT_INDENT = makeId(2, 6);
        FORMAT_LINE_GAP = makeId(2, 7);
        FORMAT_PARA_SPACING = makeId(2, 8);
        FORMAT_MARGIN_LEFT = makeId(1, 9);
        FORMAT_MARGIN_TOP = makeId(1, 10);
        FORMAT_MARGIN_RIGHT = makeId(1, 11);
        FORMAT_MARGIN_BOTTOM = makeId(1, 12);
        FORMAT_ENGLISH_FONT_FILE = makeId(3, 13);
        FORMAT_DEFAULT_FONT_FILE = makeId(3, 14);
        FORMAT_UNDER_LINE = makeId(4, 15);
        FORMAT_STRIKE_THTOUGH = makeId(4, 16);
        FORMAT_EMOJI_FONT_FILE = makeId(3, 17);
        FORMAT_IGNORE_BEGIN_SPACE = makeId(4, 18);
        init();
    }

    public RichText() {
        nativeInit();
    }

    public static void SetContext(Context context) {
        mContext = context;
    }

    public RichText appendText(String str) {
        this.mText += str;
        nativeAppendText(str);
        return this;
    }

    public String getText() {
        return this.mText;
    }

    private static class Segment {
        static final String LOG_TAG = "Segment";
        private int mLeft;
        private int mRight;
        private Vector<Object> mSpansList = new Vector<>();

        public Segment(int i, int i2, Object obj) {
            setLeft(i);
            setRight(i2);
            if (obj != null) {
                this.mSpansList.add(obj);
            }
        }

        void appendSpansList(Vector<Object> vector) {
            if (vector == null || vector.isEmpty()) {
                Logger.INSTANCE.e(LOG_TAG, "empty vector");
            }
            this.mSpansList.addAll(vector);
        }

        boolean isContains(Segment segment) {
            return this.mLeft <= segment.getLeft() && this.mRight >= segment.getRight();
        }

        Vector<Object> getDecorateList() {
            return this.mSpansList;
        }

        public int getLeft() {
            return this.mLeft;
        }

        public void setLeft(int i) {
            this.mLeft = i;
        }

        public int getRight() {
            return this.mRight;
        }

        public void setRight(int i) {
            this.mRight = i;
        }
    }

    private static int dipToPx(Context context, float f) {
        return Math.round((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private ArrayList<Segment> rerangeDecoratorSegment(Spannable spannable) {
        Object[] spans = spannable.getSpans(0, spannable.length(), Object.class);
        ArrayList<Segment> arrayList = new ArrayList();
        ArrayList<Segment> arrayList2 = new ArrayList<>();
        if (spans != null && spans.length > 0) {
            for (int i = 0; i < spans.length; i++) {
                arrayList.add(new Segment(spannable.getSpanStart(spans[i]), spannable.getSpanEnd(spans[i]), spans[i]));
            }
            HashSet hashSet = new HashSet();
            for (Segment segment : arrayList) {
                hashSet.add(Integer.valueOf(segment.getLeft()));
                hashSet.add(Integer.valueOf(segment.getRight()));
            }
            int size = hashSet.size();
            Integer[] numArr = new Integer[size];
            hashSet.toArray(numArr);
            ArrayList arrayList3 = new ArrayList();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList3.add(numArr[i2]);
            }
            Collections.sort(arrayList3);
            int i3 = 0;
            while (i3 < arrayList3.size() - 1) {
                int iIntValue = ((Integer) arrayList3.get(i3)).intValue();
                i3++;
                arrayList2.add(new Segment(iIntValue, ((Integer) arrayList3.get(i3)).intValue(), null));
            }
            for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                    if (((Segment) arrayList.get(i5)).isContains(arrayList2.get(i4))) {
                        arrayList2.get(i4).appendSpansList(((Segment) arrayList.get(i5)).getDecorateList());
                    }
                }
            }
        }
        return arrayList2;
    }

    private void appendSpannable(Spannable spannable) {
        this.mText += spannable.toString();
        this.mPreFontSize = 36;
        ArrayList<Segment> arrayListRerangeDecoratorSegment = rerangeDecoratorSegment(spannable);
        if (arrayListRerangeDecoratorSegment.size() == 0) {
            nativeAppendText(spannable.subSequence(0, spannable.length()).toString());
            return;
        }
        int right = -1;
        for (int i = 0; i < arrayListRerangeDecoratorSegment.size(); i++) {
            int left = arrayListRerangeDecoratorSegment.get(i).getLeft();
            if (right >= 0 && left > right) {
                String string = spannable.subSequence(right, left).toString();
                newFormat();
                nativeAppendText(string);
                endFormat();
            }
            right = arrayListRerangeDecoratorSegment.get(i).getRight();
            if (left > right) {
                Logger.INSTANCE.d(LOG_TAG, "range error: left > right");
            } else {
                if (i == 0 && left > 0) {
                    String string2 = spannable.subSequence(0, left).toString();
                    newFormat();
                    nativeAppendText(string2);
                    endFormat();
                }
                String string3 = spannable.subSequence(left, right).toString();
                newFormat();
                nativeAppendText(string3);
                setDecorators(arrayListRerangeDecoratorSegment.get(i).getDecorateList());
                endFormat();
            }
        }
        if (right < spannable.length()) {
            String string4 = spannable.subSequence(right, spannable.length()).toString();
            newFormat();
            appendText(string4);
            endFormat();
        }
    }

    private void setDecorators(Vector<Object> vector) {
        if (vector == null) {
            return;
        }
        String family = null;
        int style = 0;
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i) instanceof TypefaceSpan) {
                family = ((TypefaceSpan) TypefaceSpan.class.cast(vector.get(i))).getFamily();
            } else if (vector.get(i) instanceof AbsoluteSizeSpan) {
                AbsoluteSizeSpan absoluteSizeSpan = (AbsoluteSizeSpan) AbsoluteSizeSpan.class.cast(vector.get(i));
                int size = absoluteSizeSpan.getSize();
                if (absoluteSizeSpan.getDip()) {
                    size = dipToPx(mContext, size);
                }
                fontSize(size);
                this.mPreFontSize = size;
            } else if (vector.get(i) instanceof RelativeSizeSpan) {
                int iRound = Math.round(((RelativeSizeSpan) RelativeSizeSpan.class.cast(vector.get(i))).getSizeChange() * this.mPreFontSize);
                this.mPreFontSize = iRound;
                fontSize(iRound);
            } else if (vector.get(i) instanceof ForegroundColorSpan) {
                color(((ForegroundColorSpan) ForegroundColorSpan.class.cast(vector.get(i))).getForegroundColor());
            } else if (!(vector.get(i) instanceof BackgroundColorSpan)) {
                if (vector.get(i) instanceof StyleSpan) {
                    style = ((StyleSpan) StyleSpan.class.cast(vector.get(i))).getStyle();
                } else if (vector.get(i) instanceof UnderlineSpan) {
                    underLine();
                } else if (vector.get(i) instanceof StrikethroughSpan) {
                    strikeThrough();
                } else if (!(vector.get(i) instanceof SubscriptSpan) && !(vector.get(i) instanceof SuperscriptSpan) && !(vector.get(i) instanceof URLSpan) && !(vector.get(i) instanceof ScaleXSpan) && !(vector.get(i) instanceof TextAppearanceSpan) && !(vector.get(i) instanceof BulletSpan) && !(vector.get(i) instanceof ImageSpan)) {
                    Logger.INSTANCE.e(LOG_TAG, "error: unhandle case");
                }
            }
        }
        if (family != null) {
            font(family, style);
        }
    }

    public RichText appendText(CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = "";
        }
        if (charSequence instanceof String) {
            nativeAppendText((String) charSequence);
            return this;
        }
        if (charSequence instanceof Spannable) {
            if (mContext == null) {
                Logger.INSTANCE.e(LOG_TAG, "parameter error:context is null");
                return this;
            }
            appendSpannable((Spannable) charSequence);
            return this;
        }
        Logger.INSTANCE.i(LOG_TAG, "error: neither String nor spannable");
        return this;
    }

    public RichText appendImage(Object obj) {
        nativeAppendImage(obj);
        return this;
    }

    public RichText appendImage(Bitmap bitmap) {
        if (bitmap != null) {
            nativeAppendImage(bitmap);
            imageSize(bitmap.getWidth(), bitmap.getHeight());
        }
        return this;
    }

    public RichText imageSize(int i, int i2) {
        nativeSetImageSize(i, i2);
        return this;
    }

    public RichText underLine() {
        nativeSetFormat(FORMAT_UNDER_LINE, true);
        return this;
    }

    public RichText strikeThrough() {
        nativeSetFormat(FORMAT_STRIKE_THTOUGH, true);
        return this;
    }

    public RichText englishFont(String str) {
        nativeSetFormat(FORMAT_ENGLISH_FONT_FILE, str);
        return this;
    }

    public RichText englishFont(String str, int i) {
        nativeSetEnglishFont(str, i);
        return this;
    }

    public RichText emojiFont(String str) {
        nativeSetFormat(FORMAT_EMOJI_FONT_FILE, str);
        return this;
    }

    public RichText defaultFont(String str) {
        nativeSetFormat(FORMAT_DEFAULT_FONT_FILE, str);
        return this;
    }

    public RichText fontSize(int i) {
        nativeSetFormat(FORMAT_FONT_SIZE, i);
        return this;
    }

    public RichText font(String str) {
        nativeSetFont(str);
        return this;
    }

    public RichText font(String str, int i) {
        nativeSetFont(str, i);
        return this;
    }

    public RichText align(int i) {
        nativeSetFormat(FORMAT_ALIGN, i);
        return this;
    }

    public RichText valign(int i) {
        nativeSetFormat(FORMAT_VALIGN, i);
        return this;
    }

    public RichText justify() {
        return align(3);
    }

    public RichText left() {
        return align(0);
    }

    public RichText center() {
        return align(1);
    }

    public RichText right() {
        return align(2);
    }

    public RichText baseline() {
        return valign(0);
    }

    public RichText top() {
        return valign(1);
    }

    public RichText bottom() {
        return valign(3);
    }

    public RichText middle() {
        return valign(2);
    }

    public RichText indent(float f) {
        nativeSetFormat(FORMAT_INDENT, f);
        return this;
    }

    public RichText color(int i) {
        nativeSetFormat(FORMAT_COLOR, i);
        return this;
    }

    public RichText lineGap(float f) {
        nativeSetFormat(FORMAT_LINE_GAP, f);
        return this;
    }

    public RichText paraSpacing(float f) {
        nativeSetFormat(FORMAT_PARA_SPACING, f);
        return this;
    }

    public RichText margin(int i, int i2, int i3, int i4) {
        leftMargin(i);
        topMargin(i2);
        rightMargin(i3);
        bottomMargin(i4);
        return this;
    }

    public RichText margin(int i, int i2) {
        return margin(i, i, i2, i2);
    }

    public RichText margin(int i) {
        return margin(i, i);
    }

    public RichText leftMargin(int i) {
        nativeSetFormat(FORMAT_MARGIN_LEFT, i);
        return this;
    }

    public RichText topMargin(int i) {
        nativeSetFormat(FORMAT_MARGIN_TOP, i);
        return this;
    }

    public RichText rightMargin(int i) {
        nativeSetFormat(FORMAT_MARGIN_RIGHT, i);
        return this;
    }

    public RichText bottomMargin(int i) {
        nativeSetFormat(FORMAT_MARGIN_BOTTOM, i);
        return this;
    }

    public RichText newFormat() {
        nativeNewFormat();
        return this;
    }

    public RichText endFormat() {
        nativeEndFormat();
        return this;
    }

    public RichText appendSpace(int i, int i2) {
        nativeAppendSpace(i, i2);
        return this;
    }

    public RichText ignoreBeginSpace(boolean z) {
        nativeSetFormat(FORMAT_IGNORE_BEGIN_SPACE, z);
        return this;
    }

    public static void defaultIgnoreBeginSpace(boolean z) {
        nativeSetDefaultFormat(FORMAT_IGNORE_BEGIN_SPACE, z);
    }

    public static void defaultLineGap(float f) {
        nativeSetDefaultFormat(FORMAT_LINE_GAP, f);
    }

    public static void defaultFontSize(int i) {
        nativeSetDefaultFormat(FORMAT_FONT_SIZE, i);
    }

    public static void defaultColor(int i) {
        nativeSetDefaultFormat(FORMAT_COLOR, i);
    }

    public void destroy() {
        nativeDestroy();
        this.mNativeHandle = 0L;
    }

    public void reset() {
        this.mText = "";
        nativeReset();
    }

    private static File chooseFileFromVersion() {
        File file = new File("/system/etc/fonts.xml");
        if (file.exists()) {
            return file;
        }
        File file2 = new File("/system/etc/system_fonts.xml");
        if (file2.exists()) {
            return file2;
        }
        Logger.INSTANCE.e(LOG_TAG, "neither file: '/system/etc/fonts.xml' or '/system/etc/system_fonts.xml' exits ");
        return null;
    }

    private static void init() {
        File fileChooseFileFromVersion = chooseFileFromVersion();
        FontCacheMgr fontCacheMgr = new FontCacheMgr();
        try {
            fontCacheMgr.parse(new FileInputStream(fileChooseFileFromVersion));
            fontCacheMgr.dump();
        } catch (FileNotFoundException unused) {
            Logger.INSTANCE.e(LOG_TAG, "Error opening " + fileChooseFileFromVersion);
        } catch (IOException unused2) {
            Logger.INSTANCE.e(LOG_TAG, "Error reading " + fileChooseFileFromVersion);
        } catch (RuntimeException e) {
            Logger.INSTANCE.w(LOG_TAG, "Didn't create v12_theme_thumb_default family (most likely, non-Minikin build),exception:" + e);
        } catch (XmlPullParserException unused3) {
            Logger.INSTANCE.e(LOG_TAG, "XML parse exception for " + fileChooseFileFromVersion);
        }
    }
}
