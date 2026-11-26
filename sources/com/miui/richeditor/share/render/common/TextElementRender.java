package com.miui.richeditor.share.render.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.TextView;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.doodle.document.Layer;
import com.miui.notes.R;
import com.miui.notes.theme.util.ResourceManager;
import com.miui.richeditor.constant.MiuiFontName;
import com.miui.richeditor.share.element.TextElement;
import com.miui.richeditor.share.utils.TextUtils;
import com.miui.richeditor.style.FontH3SizeShareSpan;
import com.miui.richeditor.style.FontLargeSizeShareSpan;
import com.miui.richeditor.style.FontMidSizeShareSpan;
import com.miui.richeditor.style.lite.EditorQuoteLiteSpan;
import com.miui.richeditor.style.lite.FontH3SizeLiteSpan;
import com.miui.richeditor.style.lite.FontLargeSizeLiteSpan;
import com.miui.richeditor.style.lite.FontMidSizeLiteSpan;
import com.miui.richeditor.theme.Theme;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextElementRender.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B3\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0013H\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0002J\n\u0010\u0018\u001a\u0004\u0018\u00010\u000eH\u0002R\"\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u000e@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0019"}, d2 = {"Lcom/miui/richeditor/share/render/common/TextElementRender;", "Lcom/miui/richeditor/share/render/common/ElementRender;", "Lcom/miui/richeditor/share/element/TextElement;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "textElement", Layer.KEY_TOP, "", Layer.KEY_WIDTH, "mThemeId", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Lcom/miui/richeditor/share/element/TextElement;IIJ)V", "value", "Landroid/widget/TextView;", "textView", "getTextView", "()Landroid/widget/TextView;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "destroy", "layout", "createTextView", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class TextElementRender extends ElementRender<TextElement> {
    private TextView textView;

    public TextElementRender(Context context, TextElement textElement, int i, int i2, long j) {
        super(context, textElement, i, i2, j);
        layout();
    }

    public final TextView getTextView() {
        return this.textView;
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.mIsDestroying) {
            return;
        }
        canvas.save();
        TextView textView = this.textView;
        Intrinsics.checkNotNull(textView);
        float left = textView.getLeft();
        TextView textView2 = this.textView;
        Intrinsics.checkNotNull(textView2);
        canvas.translate(left, textView2.getTop());
        TextView textView3 = this.textView;
        Intrinsics.checkNotNull(textView3);
        textView3.draw(canvas);
        canvas.restore();
    }

    @Override // com.miui.richeditor.share.render.common.ElementRender
    public void destroy() {
        super.destroy();
        this.textView = null;
    }

    private final void layout() {
        this.textView = createTextView();
        int i = this.mWidth;
        T t = this.mElement;
        Intrinsics.checkNotNull(t);
        int marginLeft = i - ((TextElement) t).getMarginLeft();
        T t2 = this.mElement;
        Intrinsics.checkNotNull(t2);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(marginLeft - ((TextElement) t2).getMarginRight(), 1073741824);
        TextView textView = this.textView;
        Intrinsics.checkNotNull(textView);
        textView.measure(iMakeMeasureSpec, 0);
        TextView textView2 = this.textView;
        Intrinsics.checkNotNull(textView2);
        int measuredHeight = textView2.getMeasuredHeight();
        T t3 = this.mElement;
        Intrinsics.checkNotNull(t3);
        int marginTop = measuredHeight + ((TextElement) t3).getMarginTop();
        T t4 = this.mElement;
        Intrinsics.checkNotNull(t4);
        this.mHeight = marginTop + ((TextElement) t4).getMarginBottom();
        TextView textView3 = this.textView;
        Intrinsics.checkNotNull(textView3);
        T t5 = this.mElement;
        Intrinsics.checkNotNull(t5);
        int marginLeft2 = ((TextElement) t5).getMarginLeft();
        T t6 = this.mElement;
        Intrinsics.checkNotNull(t6);
        int marginTop2 = ((TextElement) t6).getMarginTop();
        int i2 = this.mWidth;
        T t7 = this.mElement;
        Intrinsics.checkNotNull(t7);
        int marginRight = i2 - ((TextElement) t7).getMarginRight();
        int i3 = this.mHeight;
        T t8 = this.mElement;
        Intrinsics.checkNotNull(t8);
        textView3.layout(marginLeft2, marginTop2, marginRight, i3 - ((TextElement) t8).getMarginBottom());
    }

    /* JADX WARN: Code duplicated, block: B:57:0x0248  */
    /* JADX WARN: Code duplicated, block: B:60:0x0262 A[LOOP:2: B:58:0x025c->B:60:0x0262, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:64:0x02b1  */
    /* JADX WARN: Code duplicated, block: B:65:0x02da  */
    /* JADX WARN: Code duplicated, block: B:68:0x02f6  */
    private final TextView createTextView() {
        JustifyTextView textView;
        T t;
        T t2;
        SpannableStringBuilder spannableStringBuilder;
        Iterator it;
        if (this.mIsDestroying) {
            return null;
        }
        T t3 = this.mElement;
        Intrinsics.checkNotNull(t3);
        if (((TextElement) t3).getGravity() == 3) {
            textView = new TextView(this.mContext);
            textView.setGravity(1);
            textView.setSingleLine(false);
        } else {
            T t4 = this.mElement;
            Intrinsics.checkNotNull(t4);
            if (((TextElement) t4).isCenter()) {
                textView = new TextView(this.mContext);
            } else {
                T t5 = this.mElement;
                Intrinsics.checkNotNull(t5);
                if (((TextElement) t5).isContainRichText()) {
                    textView = (RomUtils.isMiuiLayoutEngineOpen() && RomUtils.isSupportMiuiLayoutEngine()) ? new TextView(this.mContext) : new JustifyRichTextView(this.mContext);
                } else {
                    textView = (RomUtils.isMiuiLayoutEngineOpen() && RomUtils.isSupportMiuiLayoutEngine()) ? new TextView(this.mContext) : new JustifyTextView(this.mContext);
                }
            }
        }
        T t6 = this.mElement;
        Intrinsics.checkNotNull(t6);
        if (((TextElement) t6).isBgHighLight()) {
            textView = new TextView(this.mContext);
            Theme theme = ResourceManager.getTheme((int) this.mThemeId);
            T t7 = this.mElement;
            Intrinsics.checkNotNull(t7);
            ((TextElement) t7).updateHighLightSpan(this.mContext, theme);
        }
        T t8 = this.mElement;
        Intrinsics.checkNotNull(t8);
        textView.setTextSize(0, ((TextElement) t8).getFontSize());
        T t9 = this.mElement;
        Intrinsics.checkNotNull(t9);
        textView.setTextColor(((TextElement) t9).getFontColor());
        T t10 = this.mElement;
        Intrinsics.checkNotNull(t10);
        if (((TextElement) t10).isBold()) {
            textView.setTypeface(null, 1);
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sns_content_line_height) - textView.getLineHeight();
        T t11 = this.mElement;
        Intrinsics.checkNotNull(t11);
        CharSequence text = ((TextElement) t11).getText();
        Intrinsics.checkNotNull(text, "null cannot be cast to non-null type android.text.SpannableStringBuilder");
        SpannableStringBuilder spannableStringBuilder2 = (SpannableStringBuilder) text;
        Iterator it2 = ArrayIteratorKt.iterator((StyleSpan[]) spannableStringBuilder2.getSpans(0, spannableStringBuilder2.length(), StyleSpan.class));
        while (it2.hasNext()) {
            StyleSpan styleSpan = (StyleSpan) it2.next();
            spannableStringBuilder2.setSpan(new TypefaceSpan(MiuiFontName.REGULAR), spannableStringBuilder2.getSpanStart(styleSpan), spannableStringBuilder2.getSpanEnd(styleSpan), spannableStringBuilder2.getSpanFlags(styleSpan));
        }
        FontLargeSizeLiteSpan[] fontLargeSizeLiteSpanArr = (FontLargeSizeLiteSpan[]) spannableStringBuilder2.getSpans(0, spannableStringBuilder2.length(), FontLargeSizeLiteSpan.class);
        FontMidSizeLiteSpan[] fontMidSizeLiteSpanArr = (FontMidSizeLiteSpan[]) spannableStringBuilder2.getSpans(0, spannableStringBuilder2.length(), FontMidSizeLiteSpan.class);
        FontH3SizeLiteSpan[] fontH3SizeLiteSpanArr = (FontH3SizeLiteSpan[]) spannableStringBuilder2.getSpans(0, spannableStringBuilder2.length(), FontH3SizeLiteSpan.class);
        EditorQuoteLiteSpan[] editorQuoteLiteSpanArr = (EditorQuoteLiteSpan[]) spannableStringBuilder2.getSpans(0, spannableStringBuilder2.length(), EditorQuoteLiteSpan.class);
        int i = 18;
        if (fontLargeSizeLiteSpanArr.length > 0) {
            T t12 = this.mElement;
            Intrinsics.checkNotNull(t12);
            CharSequence text2 = ((TextElement) t12).getText();
            Intrinsics.checkNotNull(text2, "null cannot be cast to non-null type android.text.SpannableStringBuilder");
            SpannableStringBuilder spannableStringBuilder3 = (SpannableStringBuilder) text2;
            Iterator it3 = ArrayIteratorKt.iterator(fontLargeSizeLiteSpanArr);
            while (it3.hasNext()) {
                FontLargeSizeLiteSpan fontLargeSizeLiteSpan = (FontLargeSizeLiteSpan) it3.next();
                spannableStringBuilder3.setSpan(new FontLargeSizeShareSpan(), spannableStringBuilder3.getSpanStart(fontLargeSizeLiteSpan), spannableStringBuilder3.getSpanEnd(fontLargeSizeLiteSpan), spannableStringBuilder3.getSpanFlags(fontLargeSizeLiteSpan));
                spannableStringBuilder3.setSpan(new TypefaceSpan(MiuiFontName.REGULAR), spannableStringBuilder3.getSpanStart(fontLargeSizeLiteSpan), spannableStringBuilder3.getSpanEnd(fontLargeSizeLiteSpan), spannableStringBuilder3.getSpanFlags(fontLargeSizeLiteSpan));
            }
        } else if (fontMidSizeLiteSpanArr.length > 0) {
            T t13 = this.mElement;
            Intrinsics.checkNotNull(t13);
            CharSequence text3 = ((TextElement) t13).getText();
            Intrinsics.checkNotNull(text3, "null cannot be cast to non-null type android.text.SpannableStringBuilder");
            SpannableStringBuilder spannableStringBuilder4 = (SpannableStringBuilder) text3;
            Iterator it4 = ArrayIteratorKt.iterator(fontMidSizeLiteSpanArr);
            while (it4.hasNext()) {
                FontMidSizeLiteSpan fontMidSizeLiteSpan = (FontMidSizeLiteSpan) it4.next();
                spannableStringBuilder4.setSpan(new FontMidSizeShareSpan(), spannableStringBuilder4.getSpanStart(fontMidSizeLiteSpan), spannableStringBuilder4.getSpanEnd(fontMidSizeLiteSpan), spannableStringBuilder4.getSpanFlags(fontMidSizeLiteSpan));
                spannableStringBuilder4.setSpan(new TypefaceSpan(MiuiFontName.REGULAR), spannableStringBuilder4.getSpanStart(fontMidSizeLiteSpan), spannableStringBuilder4.getSpanEnd(fontMidSizeLiteSpan), spannableStringBuilder4.getSpanFlags(fontMidSizeLiteSpan));
            }
        } else {
            if (fontH3SizeLiteSpanArr.length > 0) {
                T t14 = this.mElement;
                Intrinsics.checkNotNull(t14);
                CharSequence text4 = ((TextElement) t14).getText();
                Intrinsics.checkNotNull(text4, "null cannot be cast to non-null type android.text.SpannableStringBuilder");
                SpannableStringBuilder spannableStringBuilder5 = (SpannableStringBuilder) text4;
                Iterator it5 = ArrayIteratorKt.iterator(fontH3SizeLiteSpanArr);
                while (it5.hasNext()) {
                    FontH3SizeLiteSpan fontH3SizeLiteSpan = (FontH3SizeLiteSpan) it5.next();
                    spannableStringBuilder5.setSpan(new FontH3SizeShareSpan(), spannableStringBuilder5.getSpanStart(fontH3SizeLiteSpan), spannableStringBuilder5.getSpanEnd(fontH3SizeLiteSpan), spannableStringBuilder5.getSpanFlags(fontH3SizeLiteSpan));
                    spannableStringBuilder5.setSpan(new TypefaceSpan(MiuiFontName.REGULAR), spannableStringBuilder5.getSpanStart(fontH3SizeLiteSpan), spannableStringBuilder5.getSpanEnd(fontH3SizeLiteSpan), spannableStringBuilder5.getSpanFlags(fontH3SizeLiteSpan));
                }
            } else {
                i = 0;
            }
            if (editorQuoteLiteSpanArr.length > 0) {
                T t15 = this.mElement;
                Intrinsics.checkNotNull(t15);
                CharSequence text5 = ((TextElement) t15).getText();
                Intrinsics.checkNotNull(text5, "null cannot be cast to non-null type android.text.SpannableStringBuilder");
                spannableStringBuilder = (SpannableStringBuilder) text5;
                it = ArrayIteratorKt.iterator(editorQuoteLiteSpanArr);
                while (it.hasNext()) {
                    EditorQuoteLiteSpan editorQuoteLiteSpan = (EditorQuoteLiteSpan) it.next();
                    spannableStringBuilder.setSpan(new EditorQuoteLiteSpan(), spannableStringBuilder.getSpanStart(editorQuoteLiteSpan), spannableStringBuilder.getSpanEnd(editorQuoteLiteSpan), spannableStringBuilder.getSpanFlags(editorQuoteLiteSpan));
                    T t16 = this.mElement;
                    Intrinsics.checkNotNull(t16);
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(TextUtils.getColorWithAlpha(0.5f, ((TextElement) t16).getFontColor())), spannableStringBuilder.getSpanStart(editorQuoteLiteSpan), spannableStringBuilder.getSpanEnd(editorQuoteLiteSpan), spannableStringBuilder.getSpanFlags(editorQuoteLiteSpan));
                }
                i = -1;
                dimensionPixelSize = 0;
            }
            t = this.mElement;
            Intrinsics.checkNotNull(t);
            if (((TextElement) t).isTitle()) {
                textView.setTypeface(Typeface.create(MiuiFontName.SEMIBOLD, 0));
                textView.setLineSpacing((float) Math.max(0.0d, this.mContext.getResources().getDimensionPixelSize(R.dimen.sns_title_line_height) - textView.getLineHeight()), 1.0f);
            } else {
                T t17 = this.mElement;
                Intrinsics.checkNotNull(t17);
                textView.setLineSpacing(dimensionPixelSize, ((TextElement) t17).getLineGap());
            }
            t2 = this.mElement;
            Intrinsics.checkNotNull(t2);
            if (((TextElement) t2).getMarginBottom() == 0) {
                Logger.INSTANCE.d("TextElementRender", "lineSpacingExtra = " + textView.getLineSpacingExtra());
                T t18 = this.mElement;
                Intrinsics.checkNotNull(t18);
                ((TextElement) t18).edit().setMarginTop((int) (textView.getLineSpacingExtra() + i)).apply();
            }
            T t19 = this.mElement;
            Intrinsics.checkNotNull(t19);
            textView.setText(((TextElement) t19).getText());
            return textView;
        }
        dimensionPixelSize = 0;
        if (editorQuoteLiteSpanArr.length > 0) {
            T t110 = this.mElement;
            Intrinsics.checkNotNull(t110);
            CharSequence text6 = ((TextElement) t110).getText();
            Intrinsics.checkNotNull(text6, "null cannot be cast to non-null type android.text.SpannableStringBuilder");
            spannableStringBuilder = (SpannableStringBuilder) text6;
            it = ArrayIteratorKt.iterator(editorQuoteLiteSpanArr);
            while (it.hasNext()) {
                EditorQuoteLiteSpan editorQuoteLiteSpan2 = (EditorQuoteLiteSpan) it.next();
                spannableStringBuilder.setSpan(new EditorQuoteLiteSpan(), spannableStringBuilder.getSpanStart(editorQuoteLiteSpan2), spannableStringBuilder.getSpanEnd(editorQuoteLiteSpan2), spannableStringBuilder.getSpanFlags(editorQuoteLiteSpan2));
                T t111 = this.mElement;
                Intrinsics.checkNotNull(t111);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(TextUtils.getColorWithAlpha(0.5f, ((TextElement) t111).getFontColor())), spannableStringBuilder.getSpanStart(editorQuoteLiteSpan2), spannableStringBuilder.getSpanEnd(editorQuoteLiteSpan2), spannableStringBuilder.getSpanFlags(editorQuoteLiteSpan2));
            }
            i = -1;
            dimensionPixelSize = 0;
        }
        t = this.mElement;
        Intrinsics.checkNotNull(t);
        if (((TextElement) t).isTitle()) {
            textView.setTypeface(Typeface.create(MiuiFontName.SEMIBOLD, 0));
            textView.setLineSpacing((float) Math.max(0.0d, this.mContext.getResources().getDimensionPixelSize(R.dimen.sns_title_line_height) - textView.getLineHeight()), 1.0f);
        } else {
            T t112 = this.mElement;
            Intrinsics.checkNotNull(t112);
            textView.setLineSpacing(dimensionPixelSize, ((TextElement) t112).getLineGap());
        }
        t2 = this.mElement;
        Intrinsics.checkNotNull(t2);
        if (((TextElement) t2).getMarginBottom() == 0) {
            Logger.INSTANCE.d("TextElementRender", "lineSpacingExtra = " + textView.getLineSpacingExtra());
            T t113 = this.mElement;
            Intrinsics.checkNotNull(t113);
            ((TextElement) t113).edit().setMarginTop((int) (textView.getLineSpacingExtra() + i)).apply();
        }
        T t114 = this.mElement;
        Intrinsics.checkNotNull(t114);
        textView.setText(((TextElement) t114).getText());
        return textView;
    }
}
