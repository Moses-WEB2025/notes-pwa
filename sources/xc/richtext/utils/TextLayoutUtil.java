package xc.richtext.utils;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.LocaleList;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import xc.richtext.data.SpannedData;
import xc.richtext.data.bean.TextContentInfo;

/* JADX INFO: loaded from: classes2.dex */
public class TextLayoutUtil {
    private static final String TAG = "TextLayoutUtil";
    public TextPaint mTextPaint;

    public TextLayoutUtil() {
        init();
    }

    public static List<RectF> genSearchTextPath(StaticLayout staticLayout, String str) {
        int iIndexOf;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (staticLayout != null && staticLayout.getText() != null && !TextUtils.isEmpty(str)) {
            String lowerCase = str.toLowerCase();
            initTextInfoList(staticLayout, arrayList);
            int length = lowerCase.length();
            String lowerCase2 = staticLayout.getText().toString().toLowerCase();
            int iIndexOf2 = lowerCase2.indexOf(lowerCase);
            if (iIndexOf2 != -1) {
                int i = iIndexOf2 + length;
                while (iIndexOf2 < i && iIndexOf2 < arrayList.size()) {
                    TextContentInfo textContentInfo = (TextContentInfo) arrayList.get(iIndexOf2);
                    arrayList2.add(new RectF(textContentInfo.left, textContentInfo.top, textContentInfo.left + textContentInfo.width, textContentInfo.top + textContentInfo.height));
                    iIndexOf2++;
                }
                while (i < lowerCase2.length() && i + length <= lowerCase2.length() && (iIndexOf = lowerCase2.indexOf(lowerCase, i)) != -1) {
                    i = iIndexOf + length;
                    while (iIndexOf < i && iIndexOf < arrayList.size()) {
                        TextContentInfo textContentInfo2 = (TextContentInfo) arrayList.get(iIndexOf);
                        arrayList2.add(new RectF(textContentInfo2.left, textContentInfo2.top, textContentInfo2.left + textContentInfo2.width, textContentInfo2.top + textContentInfo2.height));
                        iIndexOf++;
                    }
                }
            }
        }
        return arrayList2;
    }

    public static RectF genSearchTextRect(StaticLayout staticLayout, RectF rectF, String str) {
        if (staticLayout == null || rectF == null || TextUtils.isEmpty(str)) {
            Log.e(TAG, "genSearchTextRect error");
            return new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        }
        int length = str.length();
        int iIndexOf = staticLayout.getText().toString().toLowerCase().indexOf(str);
        int i = length + iIndexOf;
        Paint.FontMetrics fontMetrics = staticLayout.getPaint().getFontMetrics();
        int lineForOffset = staticLayout.getLineForOffset(iIndexOf);
        staticLayout.getLineForOffset(i - 1);
        float primaryHorizontal = staticLayout.getPrimaryHorizontal(iIndexOf);
        float primaryHorizontal2 = staticLayout.getPrimaryHorizontal(i);
        float lineBaseline = staticLayout.getLineBaseline(lineForOffset);
        float f = fontMetrics.top + lineBaseline;
        float f2 = lineBaseline + fontMetrics.bottom;
        float width = primaryHorizontal / staticLayout.getWidth();
        float height = f / staticLayout.getHeight();
        float width2 = (primaryHorizontal2 - primaryHorizontal) / staticLayout.getWidth();
        float height2 = (f2 - f) / staticLayout.getHeight();
        float fWidth = rectF.left + (rectF.width() * width);
        float fHeight = rectF.top + (rectF.height() * height);
        return new RectF(fWidth, fHeight, (rectF.width() * width2) + fWidth, (rectF.height() * height2) + fHeight);
    }

    private static void initTextInfoList(StaticLayout staticLayout, ArrayList<TextContentInfo> arrayList) {
        int i = 0;
        while (i < staticLayout.getText().length()) {
            int i2 = i + 1;
            String string = staticLayout.getText().subSequence(i, i2).toString();
            Path path = new Path();
            staticLayout.getSelectionPath(i, i2, path);
            int lineForOffset = staticLayout.getLineForOffset(i);
            float lineTop = staticLayout.getLineTop(lineForOffset);
            float lineBottom = staticLayout.getLineBottom(lineForOffset);
            RectF rectF = new RectF();
            staticLayout.getCursorPath(i, path, string);
            path.computeBounds(rectF, false);
            float f = rectF.left;
            path.reset();
            staticLayout.getCursorPath(i2, path, string);
            rectF.setEmpty();
            path.computeBounds(rectF, false);
            float lineWidth = rectF.right;
            if (i2 == staticLayout.getLineEnd(lineForOffset)) {
                lineWidth = staticLayout.getLineWidth(lineForOffset);
            }
            arrayList.add(new TextContentInfo(f, lineTop, (lineWidth - f) + 0.3f, lineBottom - lineTop));
            i = i2;
        }
    }

    public StaticLayout.Builder obtainBuilder(SpannedData spannedData, boolean z) {
        init();
        float fWidth = spannedData.getRectF().width() / 1000.0f;
        float f = spannedData.mTextSize;
        String str = spannedData.mText;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        this.mTextPaint.setTextSize(f / fWidth);
        int i = spannedData.mTextColor;
        if (z) {
            if (i == -16777216 || i == -16777216) {
                i = -1;
            } else if (i == -1) {
                i = -16777216;
            }
        }
        this.mTextPaint.setColor(i);
        StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(str, 0, str.length(), this.mTextPaint, 1000);
        builderObtain.setLineSpacing(0.0f, 1.0f).setUseLineSpacingFromFallbacks(true).setTextDirection(TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
        return builderObtain;
    }

    public StaticLayout.Builder obtainBuilder(String str, RectF rectF, float f) {
        if (rectF == null) {
            return null;
        }
        init();
        this.mTextPaint.setTextSize(f / (rectF.width() / 1000.0f));
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        this.mTextPaint.setColor(-16777216);
        StaticLayout.Builder builderObtain = StaticLayout.Builder.obtain(str, 0, str.length(), this.mTextPaint, 1000);
        builderObtain.setLineSpacing(0.0f, 1.0f).setUseLineSpacingFromFallbacks(true).setTextDirection(TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
        return builderObtain;
    }

    private void init() {
        if (this.mTextPaint == null) {
            TextPaint textPaint = new TextPaint();
            this.mTextPaint = textPaint;
            textPaint.setStrokeJoin(Paint.Join.ROUND);
            this.mTextPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mTextPaint.setTextAlign(Paint.Align.LEFT);
            this.mTextPaint.setAntiAlias(true);
            this.mTextPaint.setTextLocales(LocaleList.getDefault());
        }
    }
}
