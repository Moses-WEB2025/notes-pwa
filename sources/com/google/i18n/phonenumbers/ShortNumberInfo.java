package com.google.i18n.phonenumbers;

import com.google.i18n.phonenumbers.internal.MatcherApi;
import com.google.i18n.phonenumbers.internal.RegexBasedMatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes2.dex */
public class ShortNumberInfo {
    private static final Set<String> REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT;
    private final Map<Integer, List<String>> countryCallingCodeToRegionCodeMap = CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap();
    private final MatcherApi matcherApi;
    private static final Logger logger = Logger.getLogger(ShortNumberInfo.class.getName());
    private static final ShortNumberInfo INSTANCE = new ShortNumberInfo(RegexBasedMatcher.create());

    public enum ShortNumberCost {
        TOLL_FREE,
        STANDARD_RATE,
        PREMIUM_RATE,
        UNKNOWN_COST
    }

    static {
        HashSet hashSet = new HashSet();
        REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT = hashSet;
        hashSet.add("BR");
        hashSet.add("CL");
        hashSet.add("NI");
    }

    public static ShortNumberInfo getInstance() {
        return INSTANCE;
    }

    ShortNumberInfo(MatcherApi matcherApi) {
        this.matcherApi = matcherApi;
    }

    private List<String> getRegionCodesForCountryCode(int i) {
        List<String> arrayList = this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf(i));
        if (arrayList == null) {
            arrayList = new ArrayList<>(0);
        }
        return Collections.unmodifiableList(arrayList);
    }

    private boolean regionDialingFromMatchesNumber(Phonenumber.PhoneNumber phoneNumber, String str) {
        return getRegionCodesForCountryCode(phoneNumber.getCountryCode()).contains(str);
    }

    public boolean isPossibleShortNumberForRegion(Phonenumber.PhoneNumber phoneNumber, String str) {
        Phonemetadata.PhoneMetadata shortNumberMetadataForRegion;
        if (regionDialingFromMatchesNumber(phoneNumber, str) && (shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(str)) != null) {
            return shortNumberMetadataForRegion.getGeneralDesc().getPossibleLengthList().contains(Integer.valueOf(getNationalSignificantNumber(phoneNumber).length()));
        }
        return false;
    }

    public boolean isPossibleShortNumber(Phonenumber.PhoneNumber phoneNumber) {
        List<String> regionCodesForCountryCode = getRegionCodesForCountryCode(phoneNumber.getCountryCode());
        int length = getNationalSignificantNumber(phoneNumber).length();
        Iterator<String> it = regionCodesForCountryCode.iterator();
        while (it.hasNext()) {
            Phonemetadata.PhoneMetadata shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(it.next());
            if (shortNumberMetadataForRegion != null && shortNumberMetadataForRegion.getGeneralDesc().getPossibleLengthList().contains(Integer.valueOf(length))) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidShortNumberForRegion(Phonenumber.PhoneNumber phoneNumber, String str) {
        Phonemetadata.PhoneMetadata shortNumberMetadataForRegion;
        if (!regionDialingFromMatchesNumber(phoneNumber, str) || (shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(str)) == null) {
            return false;
        }
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (matchesPossibleNumberAndNationalNumber(nationalSignificantNumber, shortNumberMetadataForRegion.getGeneralDesc())) {
            return matchesPossibleNumberAndNationalNumber(nationalSignificantNumber, shortNumberMetadataForRegion.getShortCode());
        }
        return false;
    }

    public boolean isValidShortNumber(Phonenumber.PhoneNumber phoneNumber) {
        List<String> regionCodesForCountryCode = getRegionCodesForCountryCode(phoneNumber.getCountryCode());
        String regionCodeForShortNumberFromRegionList = getRegionCodeForShortNumberFromRegionList(phoneNumber, regionCodesForCountryCode);
        if (regionCodesForCountryCode.size() <= 1 || regionCodeForShortNumberFromRegionList == null) {
            return isValidShortNumberForRegion(phoneNumber, regionCodeForShortNumberFromRegionList);
        }
        return true;
    }

    public ShortNumberCost getExpectedCostForRegion(Phonenumber.PhoneNumber phoneNumber, String str) {
        if (!regionDialingFromMatchesNumber(phoneNumber, str)) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        Phonemetadata.PhoneMetadata shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(str);
        if (shortNumberMetadataForRegion == null) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (!shortNumberMetadataForRegion.getGeneralDesc().getPossibleLengthList().contains(Integer.valueOf(nationalSignificantNumber.length()))) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        if (matchesPossibleNumberAndNationalNumber(nationalSignificantNumber, shortNumberMetadataForRegion.getPremiumRate())) {
            return ShortNumberCost.PREMIUM_RATE;
        }
        if (matchesPossibleNumberAndNationalNumber(nationalSignificantNumber, shortNumberMetadataForRegion.getStandardRate())) {
            return ShortNumberCost.STANDARD_RATE;
        }
        if (matchesPossibleNumberAndNationalNumber(nationalSignificantNumber, shortNumberMetadataForRegion.getTollFree())) {
            return ShortNumberCost.TOLL_FREE;
        }
        if (isEmergencyNumber(nationalSignificantNumber, str)) {
            return ShortNumberCost.TOLL_FREE;
        }
        return ShortNumberCost.UNKNOWN_COST;
    }

    public ShortNumberCost getExpectedCost(Phonenumber.PhoneNumber phoneNumber) {
        List<String> regionCodesForCountryCode = getRegionCodesForCountryCode(phoneNumber.getCountryCode());
        if (regionCodesForCountryCode.size() == 0) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        if (regionCodesForCountryCode.size() == 1) {
            return getExpectedCostForRegion(phoneNumber, regionCodesForCountryCode.get(0));
        }
        ShortNumberCost shortNumberCost = ShortNumberCost.TOLL_FREE;
        Iterator<String> it = regionCodesForCountryCode.iterator();
        while (it.hasNext()) {
            ShortNumberCost expectedCostForRegion = getExpectedCostForRegion(phoneNumber, it.next());
            int i = AnonymousClass1.$SwitchMap$com$google$i18n$phonenumbers$ShortNumberInfo$ShortNumberCost[expectedCostForRegion.ordinal()];
            if (i == 1) {
                return ShortNumberCost.PREMIUM_RATE;
            }
            if (i == 2) {
                shortNumberCost = ShortNumberCost.UNKNOWN_COST;
            } else if (i != 3) {
                if (i != 4) {
                    logger.log(Level.SEVERE, "Unrecognised cost for region: " + expectedCostForRegion);
                }
            } else if (shortNumberCost != ShortNumberCost.UNKNOWN_COST) {
                shortNumberCost = ShortNumberCost.STANDARD_RATE;
            }
        }
        return shortNumberCost;
    }

    /* JADX INFO: renamed from: com.google.i18n.phonenumbers.ShortNumberInfo$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$i18n$phonenumbers$ShortNumberInfo$ShortNumberCost;

        static {
            int[] iArr = new int[ShortNumberCost.values().length];
            $SwitchMap$com$google$i18n$phonenumbers$ShortNumberInfo$ShortNumberCost = iArr;
            try {
                iArr[ShortNumberCost.PREMIUM_RATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$ShortNumberInfo$ShortNumberCost[ShortNumberCost.UNKNOWN_COST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$ShortNumberInfo$ShortNumberCost[ShortNumberCost.STANDARD_RATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$ShortNumberInfo$ShortNumberCost[ShortNumberCost.TOLL_FREE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private String getRegionCodeForShortNumberFromRegionList(Phonenumber.PhoneNumber phoneNumber, List<String> list) {
        if (list.size() == 0) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        for (String str : list) {
            Phonemetadata.PhoneMetadata shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(str);
            if (shortNumberMetadataForRegion != null && matchesPossibleNumberAndNationalNumber(nationalSignificantNumber, shortNumberMetadataForRegion.getShortCode())) {
                return str;
            }
        }
        return null;
    }

    Set<String> getSupportedRegions() {
        return MetadataManager.getSupportedShortNumberRegions();
    }

    String getExampleShortNumber(String str) {
        Phonemetadata.PhoneMetadata shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(str);
        if (shortNumberMetadataForRegion == null) {
            return "";
        }
        Phonemetadata.PhoneNumberDesc shortCode = shortNumberMetadataForRegion.getShortCode();
        if (!shortCode.hasExampleNumber()) {
            return "";
        }
        return shortCode.getExampleNumber();
    }

    String getExampleShortNumberForCost(String str, ShortNumberCost shortNumberCost) {
        Phonemetadata.PhoneNumberDesc premiumRate;
        Phonemetadata.PhoneMetadata shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(str);
        if (shortNumberMetadataForRegion == null) {
            return "";
        }
        int i = AnonymousClass1.$SwitchMap$com$google$i18n$phonenumbers$ShortNumberInfo$ShortNumberCost[shortNumberCost.ordinal()];
        if (i == 1) {
            premiumRate = shortNumberMetadataForRegion.getPremiumRate();
        } else if (i != 3) {
            premiumRate = i != 4 ? null : shortNumberMetadataForRegion.getTollFree();
        } else {
            premiumRate = shortNumberMetadataForRegion.getStandardRate();
        }
        if (premiumRate == null || !premiumRate.hasExampleNumber()) {
            return "";
        }
        return premiumRate.getExampleNumber();
    }

    public boolean connectsToEmergencyNumber(String str, String str2) {
        return matchesEmergencyNumberHelper(str, str2, true);
    }

    public boolean isEmergencyNumber(CharSequence charSequence, String str) {
        return matchesEmergencyNumberHelper(charSequence, str, false);
    }

    private boolean matchesEmergencyNumberHelper(CharSequence charSequence, String str, boolean z) {
        Phonemetadata.PhoneMetadata shortNumberMetadataForRegion;
        CharSequence charSequenceExtractPossibleNumber = PhoneNumberUtil.extractPossibleNumber(charSequence);
        boolean z2 = false;
        if (PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(charSequenceExtractPossibleNumber).lookingAt() || (shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(str)) == null || !shortNumberMetadataForRegion.hasEmergency()) {
            return false;
        }
        String strNormalizeDigitsOnly = PhoneNumberUtil.normalizeDigitsOnly(charSequenceExtractPossibleNumber);
        if (z && !REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.contains(str)) {
            z2 = true;
        }
        return this.matcherApi.matchNationalNumber(strNormalizeDigitsOnly, shortNumberMetadataForRegion.getEmergency(), z2);
    }

    public boolean isCarrierSpecific(Phonenumber.PhoneNumber phoneNumber) {
        String regionCodeForShortNumberFromRegionList = getRegionCodeForShortNumberFromRegionList(phoneNumber, getRegionCodesForCountryCode(phoneNumber.getCountryCode()));
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        Phonemetadata.PhoneMetadata shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(regionCodeForShortNumberFromRegionList);
        return shortNumberMetadataForRegion != null && matchesPossibleNumberAndNationalNumber(nationalSignificantNumber, shortNumberMetadataForRegion.getCarrierSpecific());
    }

    public boolean isCarrierSpecificForRegion(Phonenumber.PhoneNumber phoneNumber, String str) {
        if (!regionDialingFromMatchesNumber(phoneNumber, str)) {
            return false;
        }
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        Phonemetadata.PhoneMetadata shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(str);
        return shortNumberMetadataForRegion != null && matchesPossibleNumberAndNationalNumber(nationalSignificantNumber, shortNumberMetadataForRegion.getCarrierSpecific());
    }

    public boolean isSmsServiceForRegion(Phonenumber.PhoneNumber phoneNumber, String str) {
        Phonemetadata.PhoneMetadata shortNumberMetadataForRegion;
        return regionDialingFromMatchesNumber(phoneNumber, str) && (shortNumberMetadataForRegion = MetadataManager.getShortNumberMetadataForRegion(str)) != null && matchesPossibleNumberAndNationalNumber(getNationalSignificantNumber(phoneNumber), shortNumberMetadataForRegion.getSmsServices());
    }

    private static String getNationalSignificantNumber(Phonenumber.PhoneNumber phoneNumber) {
        StringBuilder sb = new StringBuilder();
        if (phoneNumber.isItalianLeadingZero()) {
            char[] cArr = new char[phoneNumber.getNumberOfLeadingZeros()];
            Arrays.fill(cArr, '0');
            sb.append(new String(cArr));
        }
        sb.append(phoneNumber.getNationalNumber());
        return sb.toString();
    }

    private boolean matchesPossibleNumberAndNationalNumber(String str, Phonemetadata.PhoneNumberDesc phoneNumberDesc) {
        if (phoneNumberDesc.getPossibleLengthCount() <= 0 || phoneNumberDesc.getPossibleLengthList().contains(Integer.valueOf(str.length()))) {
            return this.matcherApi.matchNationalNumber(str, phoneNumberDesc, false);
        }
        return false;
    }
}
