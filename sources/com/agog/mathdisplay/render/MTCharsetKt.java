package com.agog.mathdisplay.render;

import com.agog.mathdisplay.parse.MTFontStyle;
import com.agog.mathdisplay.parse.MathDisplayException;
import defpackage.R2;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import miui.cloud.CloudPushConstants;

/* JADX INFO: compiled from: MTCharset.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u001f\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\t\u001a\u0016\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020'\u001a\u000e\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u0010+\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u0010,\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u0010-\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u0010.\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u0010/\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u00100\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u00101\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u00102\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u00103\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u00104\u001a\u0002052\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u00106\u001a\u0002052\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u00107\u001a\u0002052\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u00108\u001a\u0002052\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u00109\u001a\u0002052\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u0010:\u001a\u0002052\u0006\u0010*\u001a\u00020\u0004\u001a\u000e\u0010;\u001a\u00020\u00012\u0006\u0010<\u001a\u00020$\u001a\u0016\u0010=\u001a\u00020)2\u0006\u0010*\u001a\u00020\u00042\u0006\u0010&\u001a\u00020'\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0016\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0017\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0018\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0019\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001a\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001b\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001c\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001d\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001f\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010 \u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010!\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\"\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"kMTUnicodeGreekCapitalBoldItalicStart", "", "kMTUnicodeGreekCapitalBoldStart", "kMTUnicodeGreekCapitalEnd", "", "kMTUnicodeGreekCapitalItalicStart", "kMTUnicodeGreekCapitalStart", "kMTUnicodeGreekLowerBoldItalicStart", "kMTUnicodeGreekLowerBoldStart", "kMTUnicodeGreekLowerEnd", "kMTUnicodeGreekLowerItalicStart", "kMTUnicodeGreekLowerStart", "kMTUnicodeGreekSymbolBoldItalicStart", "kMTUnicodeGreekSymbolBoldStart", "kMTUnicodeGreekSymbolItalicStart", "kMTUnicodeMathCapitalBlackboardStart", "kMTUnicodeMathCapitalBoldItalicStart", "kMTUnicodeMathCapitalBoldStart", "kMTUnicodeMathCapitalFrakturStart", "kMTUnicodeMathCapitalItalicStart", "kMTUnicodeMathCapitalSansSerifStart", "kMTUnicodeMathCapitalScriptStart", "kMTUnicodeMathCapitalTTStart", "kMTUnicodeMathLowerBlackboardStart", "kMTUnicodeMathLowerBoldItalicStart", "kMTUnicodeMathLowerBoldStart", "kMTUnicodeMathLowerFrakturStart", "kMTUnicodeMathLowerItalicStart", "kMTUnicodeMathLowerSansSerifStart", "kMTUnicodeMathLowerTTStart", "kMTUnicodeNumberBlackboardStart", "kMTUnicodeNumberBoldStart", "kMTUnicodeNumberSansSerifStart", "kMTUnicodeNumberTTStart", "kMTUnicodePlanksConstant", "changeFont", "", "str", "fontStyle", "Lcom/agog/mathdisplay/parse/MTFontStyle;", "getBlackboard", "Lcom/agog/mathdisplay/render/MTCodepointChar;", "ch", "getBold", "getBoldItalic", "getCaligraphic", "getDefaultStyle", "getFraktur", "getItalicized", "getSansSerif", "getTypewriter", "greekSymbolOrder", "isCapitalGreek", "", "isGREEKSYMBOL", "isLowerEn", "isLowerGreek", "isNumber", "isUpperEn", "numberOfGlyphs", CloudPushConstants.WATERMARK_TYPE.SUBSCRIPTION, "styleCharacter", "mathdisplaylib_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
public final class MTCharsetKt {
    public static final int kMTUnicodeGreekCapitalBoldItalicStart = 120604;
    public static final int kMTUnicodeGreekCapitalBoldStart = 120488;
    public static final char kMTUnicodeGreekCapitalEnd = 937;
    public static final int kMTUnicodeGreekCapitalItalicStart = 120546;
    public static final char kMTUnicodeGreekCapitalStart = 913;
    public static final int kMTUnicodeGreekLowerBoldItalicStart = 120630;
    public static final int kMTUnicodeGreekLowerBoldStart = 120514;
    public static final char kMTUnicodeGreekLowerEnd = 969;
    public static final int kMTUnicodeGreekLowerItalicStart = 120572;
    public static final char kMTUnicodeGreekLowerStart = 945;
    public static final int kMTUnicodeGreekSymbolBoldItalicStart = 120656;
    public static final int kMTUnicodeGreekSymbolBoldStart = 120540;
    public static final int kMTUnicodeGreekSymbolItalicStart = 120598;
    public static final int kMTUnicodeMathCapitalBlackboardStart = 120120;
    public static final int kMTUnicodeMathCapitalBoldItalicStart = 119912;
    public static final int kMTUnicodeMathCapitalBoldStart = 119808;
    public static final int kMTUnicodeMathCapitalFrakturStart = 120068;
    public static final int kMTUnicodeMathCapitalItalicStart = 119860;
    public static final int kMTUnicodeMathCapitalSansSerifStart = 120224;
    public static final int kMTUnicodeMathCapitalScriptStart = 119964;
    public static final int kMTUnicodeMathCapitalTTStart = 120432;
    public static final int kMTUnicodeMathLowerBlackboardStart = 120146;
    public static final int kMTUnicodeMathLowerBoldItalicStart = 119938;
    public static final int kMTUnicodeMathLowerBoldStart = 119834;
    public static final int kMTUnicodeMathLowerFrakturStart = 120094;
    public static final int kMTUnicodeMathLowerItalicStart = 119886;
    public static final int kMTUnicodeMathLowerSansSerifStart = 120250;
    public static final int kMTUnicodeMathLowerTTStart = 120458;
    public static final int kMTUnicodeNumberBlackboardStart = 120792;
    public static final int kMTUnicodeNumberBoldStart = 120782;
    public static final int kMTUnicodeNumberSansSerifStart = 120802;
    public static final int kMTUnicodeNumberTTStart = 120822;
    public static final int kMTUnicodePlanksConstant = 8462;

    /* JADX INFO: compiled from: MTCharset.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MTFontStyle.values().length];
            iArr[MTFontStyle.KMTFontStyleDefault.ordinal()] = 1;
            iArr[MTFontStyle.KMTFontStyleRoman.ordinal()] = 2;
            iArr[MTFontStyle.KMTFontStyleBold.ordinal()] = 3;
            iArr[MTFontStyle.KMTFontStyleItalic.ordinal()] = 4;
            iArr[MTFontStyle.KMTFontStyleBoldItalic.ordinal()] = 5;
            iArr[MTFontStyle.KMTFontStyleCaligraphic.ordinal()] = 6;
            iArr[MTFontStyle.KMTFontStyleTypewriter.ordinal()] = 7;
            iArr[MTFontStyle.KMTFontStyleSansSerif.ordinal()] = 8;
            iArr[MTFontStyle.KMTFontStyleFraktur.ordinal()] = 9;
            iArr[MTFontStyle.KMTFontStyleBlackboard.ordinal()] = 10;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final int numberOfGlyphs(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
        return s.codePointCount(0, s.length());
    }

    public static final boolean isLowerEn(char c) {
        return Intrinsics.compare((int) c, 97) >= 0 && Intrinsics.compare((int) c, 122) <= 0;
    }

    public static final boolean isUpperEn(char c) {
        return Intrinsics.compare((int) c, 65) >= 0 && Intrinsics.compare((int) c, 90) <= 0;
    }

    public static final boolean isNumber(char c) {
        return Intrinsics.compare((int) c, 48) >= 0 && Intrinsics.compare((int) c, 57) <= 0;
    }

    public static final boolean isLowerGreek(char c) {
        return Intrinsics.compare((int) c, R2.attr.disabledColor) >= 0 && Intrinsics.compare((int) c, R2.attr.draggableMaxPercentProgress) <= 0;
    }

    public static final boolean isCapitalGreek(char c) {
        return Intrinsics.compare((int) c, R2.attr.dialogBgColor) >= 0 && Intrinsics.compare((int) c, R2.attr.dialogTheme) <= 0;
    }

    public static final int greekSymbolOrder(char c) {
        return ArraysKt.indexOf(new Integer[]{1013, Integer.valueOf(R2.attr.drawableStartCompat), 1008, Integer.valueOf(R2.attr.drawerArrowStyle), 1009, Integer.valueOf(R2.attr.drawerEnabledOrientation)}, Integer.valueOf(c));
    }

    public static final boolean isGREEKSYMBOL(char c) {
        return greekSymbolOrder(c) != -1;
    }

    public static final MTCodepointChar getItalicized(char c) {
        if (c == 'h') {
            return new MTCodepointChar(8462);
        }
        if (isUpperEn(c)) {
            return new MTCodepointChar(c - 11277);
        }
        if (isLowerEn(c)) {
            return new MTCodepointChar(c - 11283);
        }
        if (isCapitalGreek(c)) {
            return new MTCodepointChar(c - 11439);
        }
        if (isLowerGreek(c)) {
            return new MTCodepointChar(c - 11445);
        }
        if (isGREEKSYMBOL(c)) {
            return new MTCodepointChar(greekSymbolOrder(c) + kMTUnicodeGreekSymbolItalicStart);
        }
        return new MTCodepointChar(c);
    }

    public static final MTCodepointChar getBold(char c) {
        if (isUpperEn(c)) {
            return new MTCodepointChar(c - 11329);
        }
        if (isLowerEn(c)) {
            return new MTCodepointChar(c - 11335);
        }
        if (isCapitalGreek(c)) {
            return new MTCodepointChar(c - 11497);
        }
        if (isLowerGreek(c)) {
            return new MTCodepointChar(c - 11503);
        }
        if (isGREEKSYMBOL(c)) {
            return new MTCodepointChar(greekSymbolOrder(c) + kMTUnicodeGreekSymbolBoldStart);
        }
        if (isNumber(c)) {
            return new MTCodepointChar(c - 10338);
        }
        return new MTCodepointChar(c);
    }

    public static final MTCodepointChar getBoldItalic(char c) {
        if (isUpperEn(c)) {
            return new MTCodepointChar(c - 11225);
        }
        if (isLowerEn(c)) {
            return new MTCodepointChar(c - 11231);
        }
        if (isCapitalGreek(c)) {
            return new MTCodepointChar(c - 11381);
        }
        if (isLowerGreek(c)) {
            return new MTCodepointChar(c - 11387);
        }
        if (isGREEKSYMBOL(c)) {
            return new MTCodepointChar(greekSymbolOrder(c) + kMTUnicodeGreekSymbolBoldItalicStart);
        }
        if (isNumber(c)) {
            return getBold(c);
        }
        return new MTCodepointChar(c);
    }

    public static final MTCodepointChar getDefaultStyle(char c) throws MathDisplayException {
        if (isLowerEn(c) || isUpperEn(c) || isLowerGreek(c) || isGREEKSYMBOL(c)) {
            return getItalicized(c);
        }
        if (isNumber(c) || isCapitalGreek(c)) {
            return new MTCodepointChar(c);
        }
        if (c == '.') {
            return new MTCodepointChar(c);
        }
        throw new MathDisplayException("Unknown character " + c + " for default style.");
    }

    public static final MTCodepointChar getCaligraphic(char c) {
        if (c == 'B') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_popup_menu_min_height);
        }
        if (c == 'E') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_progress_bar_line_radius);
        }
        if (c == 'F') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_progress_indeterminate_size);
        }
        if (c == 'H') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_page_indicator_small_size_gap);
        }
        if (c == 'I') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_pairing_dialog_closable_icon_width);
        }
        if (c == 'L') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_pairing_dialog_content_padding_horizontal);
        }
        if (c == 'M') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_progressbar_large_text_bottom_padding);
        }
        if (c == 'R') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_pairing_dialog_parent_panel_fixed_height);
        }
        if (c == 'e') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_progress_bar_line_height);
        }
        if (c == 'g') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_page_indicator_small_size__vertical_padding);
        }
        if (c == 'o') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_progressbar_large_text_size);
        }
        if (isUpperEn(c)) {
            return new MTCodepointChar(c - 11173);
        }
        if (isLowerEn(c)) {
            return getDefaultStyle(c);
        }
        return getDefaultStyle(c);
    }

    public static final MTCodepointChar getTypewriter(char c) {
        if (isUpperEn(c)) {
            return new MTCodepointChar(c - 10705);
        }
        if (isLowerEn(c)) {
            return new MTCodepointChar(c - 10711);
        }
        if (isNumber(c)) {
            return new MTCodepointChar(c - 10298);
        }
        return getDefaultStyle(c);
    }

    public static final MTCodepointChar getSansSerif(char c) {
        if (isUpperEn(c)) {
            return new MTCodepointChar(c - 10913);
        }
        if (isLowerEn(c)) {
            return new MTCodepointChar(c - 10919);
        }
        if (isNumber(c)) {
            return new MTCodepointChar(c - 10318);
        }
        return getDefaultStyle(c);
    }

    public static final MTCodepointChar getFraktur(char c) {
        if (c == 'C') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_popup_menu_min_width);
        }
        if (c == 'H') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_page_indicator_small_size_horizontal_padding);
        }
        if (c == 'I') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_pairing_dialog_content_margin_top);
        }
        if (c == 'R') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_pairing_dialog_parent_panel_fixed_height_flip_tiny);
        }
        if (c == 'Z') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_popup_menu_item_padding_right);
        }
        if (isUpperEn(c)) {
            return new MTCodepointChar(c - 11069);
        }
        if (isLowerEn(c)) {
            return new MTCodepointChar(c - 11075);
        }
        return getDefaultStyle(c);
    }

    public static final MTCodepointChar getBlackboard(char c) {
        if (c == 'C') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_number_picker_text_size_hint_medium);
        }
        if (c == 'H') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_page_indicator_small_size_radius);
        }
        if (c == 'N') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_pairing_dialog_icon_position_margin_horizontal);
        }
        if (c == 'P') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_pairing_dialog_message_padding_horizontal);
        }
        if (c == 'Q') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_pairing_dialog_panel_padding_bottom);
        }
        if (c == 'R') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_pairing_dialog_parent_panel_fixed_height_large_font);
        }
        if (c == 'Z') {
            return new MTCodepointChar(R2.dimen.miuix_appcompat_popup_menu_item_min_height);
        }
        if (isUpperEn(c)) {
            return new MTCodepointChar(c - 11017);
        }
        if (isLowerEn(c)) {
            return new MTCodepointChar(c - 11023);
        }
        if (isNumber(c)) {
            return new MTCodepointChar(c - 10328);
        }
        return getDefaultStyle(c);
    }

    public static final MTCodepointChar styleCharacter(char c, MTFontStyle fontStyle) throws MathDisplayException {
        Intrinsics.checkNotNullParameter(fontStyle, "fontStyle");
        switch (WhenMappings.$EnumSwitchMapping$0[fontStyle.ordinal()]) {
            case 1:
                return getDefaultStyle(c);
            case 2:
                return new MTCodepointChar(c);
            case 3:
                return getBold(c);
            case 4:
                return getItalicized(c);
            case 5:
                return getBoldItalic(c);
            case 6:
                return getCaligraphic(c);
            case 7:
                return getTypewriter(c);
            case 8:
                return getSansSerif(c);
            case 9:
                return getFraktur(c);
            case 10:
                return getBlackboard(c);
            default:
                throw new MathDisplayException("Unknown style " + fontStyle + " for font.");
        }
    }

    public static final String changeFont(String str, MTFontStyle fontStyle) {
        Intrinsics.checkNotNullParameter(str, "str");
        Intrinsics.checkNotNullParameter(fontStyle, "fontStyle");
        StringBuffer stringBuffer = new StringBuffer();
        char[] charArray = str.toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
        int length = charArray.length;
        int i = 0;
        while (i < length) {
            char c = charArray[i];
            i++;
            stringBuffer.append(styleCharacter(c, fontStyle).toUnicodeString());
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "ret.toString()");
        return string;
    }
}
