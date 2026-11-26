package com.xiaomi.passport.utils;

import android.text.TextUtils;
import android.util.Pair;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.market.sdk.utils.Region;
import com.miui.notes.tool.util.KoreaRegionUtils;
import com.xiaomi.accountsdk.utils.AccountLogger;
import com.xiaomi.uplink.SmsUplinkConfig;
import defpackage.R2;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class CountryCodePhoneNumber {
    private static final String CHINA_COUNTRY_CODE = "+86";
    private static final String PREFIX_COUNTRY_CODE = "+";
    public static final Map<String, Integer> sCountryISO$CodeMap;
    private static SoftReference<Pair<String, List<PhoneNumUtil.CountryPhoneNumData>>> mCountryCodeListHolder = new SoftReference<>(null);
    private static final Pattern sCountryCodePattern = Pattern.compile("^\\+?([0-9]{1,3})");
    private static final Pattern sPhoneNumberPattern = Pattern.compile("([0-9]{4,14})$");

    static {
        HashMap map = new HashMap();
        sCountryISO$CodeMap = map;
        map.put("AF", 93);
        map.put("AL", Integer.valueOf(R2.attr.actionOverflowMenuStyle));
        map.put("DZ", Integer.valueOf(R2.array.earthly_branches));
        map.put("AS", 1);
        map.put("AD", Integer.valueOf(R2.attr.addElevationShadow));
        map.put("AO", Integer.valueOf(R2.attr.actionBarBlacklistIcon));
        map.put("AI", 1);
        map.put("AQ", Integer.valueOf(R2.attr.chipStyle));
        map.put("AG", 1);
        map.put("AR", 54);
        map.put("AM", Integer.valueOf(R2.attr.activityName));
        map.put("AW", Integer.valueOf(R2.attr.actionBarTabBadgeIcon));
        map.put("AU", 61);
        map.put("AT", 43);
        map.put("AZ", Integer.valueOf(R2.attr.ease));
        map.put("BS", 1);
        map.put("BH", Integer.valueOf(R2.attr.drawableEndCompat));
        map.put("BD", Integer.valueOf(R2.attr.customNavigationLayout));
        map.put("BB", 1);
        map.put("BY", Integer.valueOf(R2.attr.adapter));
        map.put("BE", 32);
        map.put("BZ", 501);
        map.put("BJ", Integer.valueOf(R2.array.support_ai_devices_global));
        map.put("BM", 1);
        map.put("BT", Integer.valueOf(R2.attr.drawableRightCompat));
        map.put("BO", Integer.valueOf(R2.attr.carousel_emptyViewsBehavior));
        map.put("BA", Integer.valueOf(R2.attr.allowDividerBelow));
        map.put("BW", Integer.valueOf(R2.attr.actionBarPaddingEnd));
        map.put("BR", 55);
        map.put("IO", Integer.valueOf(R2.attr.actionBarButtonTextViewStyle));
        map.put("VG", 1);
        map.put("BN", Integer.valueOf(R2.attr.chipSurfaceColor));
        map.put("BG", Integer.valueOf(R2.attr.actionSheetContentLayout));
        map.put("BF", Integer.valueOf(R2.array.setting_phone_note_layout_way));
        map.put("BI", 257);
        map.put("KH", Integer.valueOf(R2.attr.cornerFamilyTopLeft));
        map.put("CM", Integer.valueOf(R2.attr.NormalTextAppearance));
        map.put("CA", 1);
        map.put("CV", Integer.valueOf(R2.attr.SharedValue));
        map.put("KY", 1);
        map.put("CF", Integer.valueOf(R2.attr.HighlightTextAppearance));
        map.put("TD", Integer.valueOf(R2.attr.CompleteTextAppearance));
        map.put("CL", 56);
        map.put("CN", 86);
        map.put("CX", 61);
        map.put("CC", 61);
        map.put("CO", 57);
        map.put("KM", Integer.valueOf(R2.attr.actionBarPasteIcon));
        map.put("CK", Integer.valueOf(R2.attr.circularflow_viewCenter));
        map.put("CR", 506);
        map.put("HR", Integer.valueOf(R2.attr.allowDividerAbove));
        map.put("CU", 53);
        Integer numValueOf = Integer.valueOf(R2.attr.carousel_touchUp_velocityThreshold);
        map.put("CW", numValueOf);
        map.put("CY", Integer.valueOf(R2.attr.actionSheetAlertStyle));
        map.put("CZ", 420);
        map.put("CD", Integer.valueOf(R2.attr.actionBarBackground));
        map.put("DK", 45);
        map.put("DJ", Integer.valueOf(R2.attr.actionBarEditMessageIcon));
        map.put("DM", 1);
        map.put("DO", 1);
        map.put("TL", 670);
        map.put("EC", Integer.valueOf(R2.attr.carousel_forwardTransition));
        map.put("EG", 20);
        map.put("SV", 503);
        map.put("GQ", Integer.valueOf(R2.attr.StretchableWidgetStyle));
        map.put("ER", Integer.valueOf(R2.attr.actionBarStickIcon));
        map.put("EE", Integer.valueOf(R2.attr.activityAction));
        map.put("ET", Integer.valueOf(R2.attr.actionBarDivider));
        map.put("FK", 500);
        map.put("FO", Integer.valueOf(R2.attr.actionBarTabBarStyle));
        map.put("FJ", Integer.valueOf(R2.attr.circularflow_defaultAngle));
        map.put("FI", Integer.valueOf(R2.attr.actionSheetArrowStyle));
        map.put("FR", 33);
        map.put("PF", Integer.valueOf(R2.attr.clickToClose));
        map.put("GA", Integer.valueOf(R2.attr.accessibilityEnable));
        map.put("GM", Integer.valueOf(R2.array.months_short));
        map.put("GE", Integer.valueOf(R2.attr.eclipseOffsetX));
        map.put("DE", 49);
        map.put("GH", Integer.valueOf(R2.array.week_days_short));
        map.put("GI", 350);
        map.put("GR", 30);
        map.put("GL", Integer.valueOf(R2.attr.actionBarTabExpandStyle));
        map.put("GD", 1);
        map.put("GU", 1);
        map.put("GT", 502);
        map.put("GG", 44);
        map.put("GN", Integer.valueOf(R2.array.passport_account_user_gender_name));
        map.put("GW", Integer.valueOf(R2.attr.actionBarButtonIconViewStyle));
        map.put("GY", Integer.valueOf(R2.attr.carousel_firstView));
        map.put("HT", 509);
        map.put("HN", 504);
        map.put("HK", Integer.valueOf(R2.attr.cornerFamily));
        map.put("HU", 36);
        map.put("IS", Integer.valueOf(R2.attr.actionOverflowButtonStyle));
        map.put(Region.IN, 91);
        map.put(Region.ID, 62);
        map.put("IR", 98);
        map.put("IQ", Integer.valueOf(R2.attr.dragAnimationEnable));
        map.put("IE", Integer.valueOf(R2.attr.actionModeWebSearchDrawable));
        map.put("IM", 44);
        map.put("IL", Integer.valueOf(R2.attr.drawableBottomCompat));
        map.put("IT", 39);
        map.put("CI", Integer.valueOf(R2.array.passport_alphabet_table));
        map.put("JM", 1);
        map.put("JP", 81);
        map.put("JE", 44);
        map.put("JO", Integer.valueOf(R2.attr.dividerVertical));
        map.put("KZ", 7);
        map.put("KE", Integer.valueOf(R2.attr.actionBarEmbedTabs));
        map.put("KI", Integer.valueOf(R2.attr.clearableEditTextStyle));
        map.put("XK", Integer.valueOf(R2.attr.alignContent));
        map.put("KW", Integer.valueOf(R2.attr.dragDirection));
        map.put("KG", Integer.valueOf(R2.attr.eclipseOffsetY));
        map.put("LA", Integer.valueOf(R2.attr.cornerFamilyTopRight));
        map.put("LV", Integer.valueOf(R2.attr.activeIndicatorLabelPadding));
        map.put("LB", Integer.valueOf(R2.attr.dividerThickness));
        map.put("LS", Integer.valueOf(R2.attr.actionBarNewIcon));
        map.put("LR", Integer.valueOf(R2.array.table_default));
        map.put("LY", Integer.valueOf(R2.array.heavenly_stems));
        map.put("LI", 423);
        map.put("LT", Integer.valueOf(R2.attr.activatedColor));
        map.put("LU", Integer.valueOf(R2.attr.actionModeTitleColor));
        map.put(SmsUplinkConfig.ACTIVATION_SMS_PREFIX, Integer.valueOf(R2.attr.cornerFamilyBottomLeft));
        map.put("MK", Integer.valueOf(R2.attr.alpha));
        map.put("MG", Integer.valueOf(R2.attr.actionBarItemBackground));
        map.put("MW", Integer.valueOf(R2.attr.actionBarMoveIcon));
        map.put("MY", 60);
        map.put("MV", 960);
        map.put("ML", Integer.valueOf(R2.array.note_sort_way));
        map.put("MT", Integer.valueOf(R2.attr.actionProviderClass));
        map.put("MH", Integer.valueOf(R2.attr.clockHandColor));
        map.put("MR", Integer.valueOf(R2.array.multi_floating_package_allow_list));
        map.put("MU", Integer.valueOf(R2.array.support_ai_devices_special));
        Integer numValueOf2 = Integer.valueOf(R2.attr.actionBarMaxSize);
        map.put("YT", numValueOf2);
        map.put("MX", 52);
        map.put("FM", Integer.valueOf(R2.attr.clockFaceBackgroundColor));
        map.put("MD", Integer.valueOf(R2.attr.activityChooserViewStyle));
        map.put("MC", Integer.valueOf(R2.attr.adjustable));
        map.put("MN", Integer.valueOf(R2.attr.drawableSize));
        map.put("ME", Integer.valueOf(R2.attr.alertDialogTheme));
        map.put("MS", 1);
        map.put("MA", 212);
        map.put("MZ", Integer.valueOf(R2.attr.actionBarExpandTabBarStyle));
        map.put("MM", 95);
        map.put("NA", Integer.valueOf(R2.attr.actionBarMovableLayoutStyle));
        map.put("NR", Integer.valueOf(R2.attr.choiceCategoryPreferenceStyle));
        map.put("NP", Integer.valueOf(R2.attr.drawableStartCompat));
        map.put("NL", 31);
        map.put("AN", numValueOf);
        map.put("NC", Integer.valueOf(R2.attr.clearsTag));
        map.put("NZ", 64);
        map.put("NI", 505);
        map.put("NE", Integer.valueOf(R2.array.solar_terms));
        map.put("NG", Integer.valueOf(R2.array.week_days_shortest));
        map.put("NU", Integer.valueOf(R2.attr.clearEditTextIcon));
        map.put("KP", 850);
        map.put("MP", 1);
        map.put("NO", 47);
        map.put("OM", Integer.valueOf(R2.attr.drag_edge));
        map.put("PK", 92);
        map.put("PW", Integer.valueOf(R2.attr.circularflow_defaultRadius));
        map.put("PS", Integer.valueOf(R2.attr.draggableMinPercentProgress));
        map.put("PA", 507);
        map.put("PG", Integer.valueOf(R2.attr.circleProgressBarColor));
        map.put("PY", Integer.valueOf(R2.attr.carousel_nextState));
        map.put("PE", 51);
        map.put("PH", 63);
        map.put("PN", 64);
        map.put("PL", 48);
        map.put("PT", Integer.valueOf(R2.attr.actionModeTheme));
        map.put("PR", 1);
        map.put("QA", Integer.valueOf(R2.attr.drawableLeftCompat));
        map.put("CG", Integer.valueOf(R2.attr.actionBarAddSecretIcon));
        map.put("RE", numValueOf2);
        map.put("RO", 40);
        map.put(Region.RU, 7);
        map.put("RW", 250);
        Integer numValueOf3 = Integer.valueOf(R2.attr.carousel_backwardTransition);
        map.put("BL", numValueOf3);
        map.put("SH", Integer.valueOf(R2.attr.actionBarStartIcon));
        map.put("KN", 1);
        map.put("LC", 1);
        map.put("MF", numValueOf3);
        map.put("PM", 508);
        map.put("VC", 1);
        map.put("WS", Integer.valueOf(R2.attr.clearableEditTextIcon));
        map.put("SM", Integer.valueOf(R2.attr.airBrushMode));
        map.put("ST", Integer.valueOf(R2.attr.SharedValueId));
        map.put("SA", Integer.valueOf(R2.attr.dragScale));
        map.put("SN", Integer.valueOf(R2.array.months_shortest));
        map.put("RS", Integer.valueOf(R2.attr.alertDialogStyle));
        map.put("SC", Integer.valueOf(R2.attr.actionBarCutIcon));
        map.put("SL", Integer.valueOf(R2.array.week_days));
        map.put("SG", 65);
        map.put("SX", 1);
        map.put("SK", 421);
        map.put("SI", Integer.valueOf(R2.attr.allowDividerAfterLastItem));
        map.put("SB", Integer.valueOf(R2.attr.circularProgressIndicatorStyle));
        map.put("SO", Integer.valueOf(R2.attr.actionBarEditIcon));
        map.put("ZA", 27);
        map.put(KoreaRegionUtils.REGION_KOREA, 82);
        map.put("SS", 211);
        map.put(Region.ES, 34);
        map.put("LK", 94);
        map.put("SD", Integer.valueOf(R2.attr.actionBarDeleteIcon));
        map.put("SR", Integer.valueOf(R2.attr.carousel_touchUpMode));
        map.put("SJ", 47);
        map.put("SZ", Integer.valueOf(R2.attr.actionBarPaddingStart));
        map.put("SE", 46);
        map.put("CH", 41);
        map.put("SY", Integer.valueOf(R2.attr.dividerWidth));
        map.put("TW", Integer.valueOf(R2.attr.dColor));
        map.put("TJ", Integer.valueOf(R2.attr.duration));
        map.put("TZ", 255);
        map.put("TH", 66);
        map.put("TG", Integer.valueOf(R2.array.support_ai_devices_cn));
        map.put("TK", Integer.valueOf(R2.attr.clickable));
        map.put("TO", Integer.valueOf(R2.attr.circleRadius));
        map.put("TT", 1);
        map.put("TN", Integer.valueOf(R2.array.global_ai_panel_blend_colors));
        map.put("TR", 90);
        map.put("TM", Integer.valueOf(R2.attr.dynamicColorThemeOverlay));
        map.put("TC", 1);
        map.put("TV", Integer.valueOf(R2.attr.clickAction));
        map.put("VI", 1);
        map.put("UG", 256);
        map.put("UA", Integer.valueOf(R2.attr.alertDialogCenterButtons));
        map.put("AE", Integer.valueOf(R2.attr.drawPath));
        map.put("GB", 44);
        map.put("US", 1);
        map.put("UY", Integer.valueOf(R2.attr.carousel_touchUp_dampeningFactor));
        map.put("UZ", Integer.valueOf(R2.attr.editTextBackground));
        map.put("VU", Integer.valueOf(R2.attr.circularflow_angles));
        map.put("VA", Integer.valueOf(R2.attr.alertDialogButtonGroupStyle));
        map.put("VE", 58);
        map.put("VN", 84);
        map.put("WF", Integer.valueOf(R2.attr.circularflow_radiusInDP));
        map.put("EH", 212);
        map.put("YE", Integer.valueOf(R2.attr.dragThreshold));
        map.put("ZM", Integer.valueOf(R2.attr.actionBarIndeterminateProgressStyle));
        map.put("ZW", Integer.valueOf(R2.attr.actionBarMaxSizeInLargeFont));
    }

    public static void saveCountryCodeOnLocale(List<PhoneNumUtil.CountryPhoneNumData> list, String str) {
        Pair<String, List<PhoneNumUtil.CountryPhoneNumData>> pair = mCountryCodeListHolder.get();
        if (pair == null || !TextUtils.equals((CharSequence) pair.first, str)) {
            mCountryCodeListHolder = new SoftReference<>(new Pair(str, list));
        }
    }

    public static List<PhoneNumUtil.CountryPhoneNumData> getCountryCodeOnLocale(String str) {
        Pair<String, List<PhoneNumUtil.CountryPhoneNumData>> pair = mCountryCodeListHolder.get();
        if (pair == null || !TextUtils.equals((CharSequence) pair.first, str)) {
            return null;
        }
        return (List) pair.second;
    }

    public static String getCountryCodeAsString(int i) {
        return "+" + i;
    }

    private static String getCountryCodeByPhone(String str) throws NumberParseException {
        return String.valueOf(PhoneNumberUtil.getInstance().parse(str, "").getCountryCode());
    }

    private static String getNationNumberByPhone(String str) throws NumberParseException {
        return String.valueOf(PhoneNumberUtil.getInstance().parse(str, "").getNationalNumber());
    }

    public static String addPhoneWithCountyCode(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            if (str.startsWith("+")) {
                return "+" + getCountryCodeByPhone(str) + StringUtils.SPACE + getNationNumberByPhone(str);
            }
            return "+86 " + str;
        } catch (NumberParseException e) {
            AccountLogger.log("phonenumber Failed get phone national number, phone:[{}].", str, e);
            return str;
        }
    }

    public static int getDefaultCountryCodeAsInt() {
        return getCountryCodeAsInt(Locale.getDefault().getCountry());
    }

    public static int getCountryCodeAsInt(String str) {
        Integer num = sCountryISO$CodeMap.get(str);
        if (num == null) {
            return 86;
        }
        return num.intValue();
    }

    public static int parseCountryCodeAsInt(String str) {
        String strReplaceAll = str.replaceAll("\\p{C}", "");
        if (TextUtils.isEmpty(strReplaceAll)) {
            return 0;
        }
        Matcher matcher = sCountryCodePattern.matcher(strReplaceAll);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    public static String parsePhoneNumber(String str) {
        String strReplaceAll = str.replaceAll("\\p{C}", "");
        if (TextUtils.isEmpty(strReplaceAll)) {
            return "";
        }
        Matcher matcher = sPhoneNumberPattern.matcher(strReplaceAll);
        return !matcher.find() ? "" : matcher.group(1);
    }

    public static String getCountryCodePhoneNumber(int i, String str) {
        return String.format("%s%d %s", "+", Integer.valueOf(i), str);
    }

    public static String invalidate(String str, String str2) {
        String strReplace = str.replace(StringUtils.SPACE, "");
        return !str2.equals(CHINA_COUNTRY_CODE) ? str2 + strReplace : strReplace;
    }
}
