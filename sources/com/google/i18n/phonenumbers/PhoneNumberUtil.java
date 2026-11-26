package com.google.i18n.phonenumbers;

import ch.qos.logback.core.CoreConstants;
import com.google.i18n.phonenumbers.internal.MatcherApi;
import com.google.i18n.phonenumbers.internal.RegexBasedMatcher;
import com.google.i18n.phonenumbers.internal.RegexCache;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.text.Typography;
import miuix.appcompat.view.menu.HyperMenuUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes2.dex */
public class PhoneNumberUtil {
    private static final Map<Character, Character> ALL_PLUS_NUMBER_GROUPING_SYMBOLS;
    private static final Map<Character, Character> ALPHA_MAPPINGS;
    private static final Map<Character, Character> ALPHA_PHONE_MAPPINGS;
    private static final Pattern CAPTURING_DIGIT_PATTERN;
    private static final String CC_STRING = "$CC";
    private static final String COLOMBIA_MOBILE_TO_FIXED_LINE_PREFIX = "3";
    private static final String DEFAULT_EXTN_PREFIX = " ext. ";
    private static final Map<Character, Character> DIALLABLE_CHAR_MAPPINGS;
    private static final String DIGITS = "\\p{Nd}";
    private static final Pattern EXTN_PATTERN;
    static final String EXTN_PATTERNS_FOR_MATCHING;
    private static final String EXTN_PATTERNS_FOR_PARSING;
    private static final String FG_STRING = "$FG";
    private static final Pattern FIRST_GROUP_ONLY_PREFIX_PATTERN;
    private static final Pattern FIRST_GROUP_PATTERN;
    private static final Set<Integer> GEO_MOBILE_COUNTRIES;
    private static final Set<Integer> GEO_MOBILE_COUNTRIES_WITHOUT_MOBILE_AREA_CODES;
    private static final int MAX_INPUT_STRING_LENGTH = 250;
    static final int MAX_LENGTH_COUNTRY_CODE = 3;
    static final int MAX_LENGTH_FOR_NSN = 17;
    private static final int MIN_LENGTH_FOR_NSN = 2;
    private static final Map<Integer, String> MOBILE_TOKEN_MAPPINGS;
    private static final int NANPA_COUNTRY_CODE = 1;
    static final Pattern NON_DIGITS_PATTERN;
    private static final String NP_STRING = "$NP";
    static final String PLUS_CHARS = "+＋";
    static final Pattern PLUS_CHARS_PATTERN;
    static final char PLUS_SIGN = '+';
    static final int REGEX_FLAGS = 66;
    public static final String REGION_CODE_FOR_NON_GEO_ENTITY = "001";
    private static final String RFC3966_EXTN_PREFIX = ";ext=";
    private static final String RFC3966_ISDN_SUBADDRESS = ";isub=";
    private static final String RFC3966_PHONE_CONTEXT = ";phone-context=";
    private static final String RFC3966_PREFIX = "tel:";
    private static final String SECOND_NUMBER_START = "[\\\\/] *x";
    static final Pattern SECOND_NUMBER_START_PATTERN;
    private static final Pattern SEPARATOR_PATTERN;
    private static final Pattern SINGLE_INTERNATIONAL_PREFIX;
    private static final char STAR_SIGN = '*';
    private static final String UNKNOWN_REGION = "ZZ";
    private static final String UNWANTED_END_CHARS = "[[\\P{N}&&\\P{L}]&&[^#]]+$";
    static final Pattern UNWANTED_END_CHAR_PATTERN;
    private static final String VALID_ALPHA;
    private static final Pattern VALID_ALPHA_PHONE_PATTERN;
    private static final String VALID_PHONE_NUMBER;
    private static final Pattern VALID_PHONE_NUMBER_PATTERN;
    static final String VALID_PUNCTUATION = "-x‐-―−ー－-／  \u00ad\u200b\u2060\u3000()（）［］.\\[\\]/~⁓∼～";
    private static final String VALID_START_CHAR = "[+＋\\p{Nd}]";
    private static final Pattern VALID_START_CHAR_PATTERN;
    private static PhoneNumberUtil instance;
    private static final Logger logger = Logger.getLogger(PhoneNumberUtil.class.getName());
    private final Map<Integer, List<String>> countryCallingCodeToRegionCodeMap;
    private final MetadataSource metadataSource;
    private final MatcherApi matcherApi = RegexBasedMatcher.create();
    private final Set<String> nanpaRegions = new HashSet(35);
    private final RegexCache regexCache = new RegexCache(100);
    private final Set<String> supportedRegions = new HashSet(320);
    private final Set<Integer> countryCodesForNonGeographicalRegion = new HashSet();

    public enum Leniency {
        POSSIBLE { // from class: com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency.1
            @Override // com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency
            boolean verify(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence, PhoneNumberUtil phoneNumberUtil, PhoneNumberMatcher phoneNumberMatcher) {
                return phoneNumberUtil.isPossibleNumber(phoneNumber);
            }
        },
        VALID { // from class: com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency.2
            @Override // com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency
            boolean verify(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence, PhoneNumberUtil phoneNumberUtil, PhoneNumberMatcher phoneNumberMatcher) {
                if (phoneNumberUtil.isValidNumber(phoneNumber) && PhoneNumberMatcher.containsOnlyValidXChars(phoneNumber, charSequence.toString(), phoneNumberUtil)) {
                    return PhoneNumberMatcher.isNationalPrefixPresentIfRequired(phoneNumber, phoneNumberUtil);
                }
                return false;
            }
        },
        STRICT_GROUPING { // from class: com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency.3
            @Override // com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency
            boolean verify(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence, PhoneNumberUtil phoneNumberUtil, PhoneNumberMatcher phoneNumberMatcher) {
                String string = charSequence.toString();
                if (phoneNumberUtil.isValidNumber(phoneNumber) && PhoneNumberMatcher.containsOnlyValidXChars(phoneNumber, string, phoneNumberUtil) && !PhoneNumberMatcher.containsMoreThanOneSlashInNationalNumber(phoneNumber, string) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired(phoneNumber, phoneNumberUtil)) {
                    return phoneNumberMatcher.checkNumberGroupingIsValid(phoneNumber, charSequence, phoneNumberUtil, new PhoneNumberMatcher.NumberGroupingChecker() { // from class: com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency.3.1
                        @Override // com.google.i18n.phonenumbers.PhoneNumberMatcher.NumberGroupingChecker
                        public boolean checkGroups(PhoneNumberUtil phoneNumberUtil2, Phonenumber.PhoneNumber phoneNumber2, StringBuilder sb, String[] strArr) {
                            return PhoneNumberMatcher.allNumberGroupsRemainGrouped(phoneNumberUtil2, phoneNumber2, sb, strArr);
                        }
                    });
                }
                return false;
            }
        },
        EXACT_GROUPING { // from class: com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency.4
            @Override // com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency
            boolean verify(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence, PhoneNumberUtil phoneNumberUtil, PhoneNumberMatcher phoneNumberMatcher) {
                String string = charSequence.toString();
                if (phoneNumberUtil.isValidNumber(phoneNumber) && PhoneNumberMatcher.containsOnlyValidXChars(phoneNumber, string, phoneNumberUtil) && !PhoneNumberMatcher.containsMoreThanOneSlashInNationalNumber(phoneNumber, string) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired(phoneNumber, phoneNumberUtil)) {
                    return phoneNumberMatcher.checkNumberGroupingIsValid(phoneNumber, charSequence, phoneNumberUtil, new PhoneNumberMatcher.NumberGroupingChecker() { // from class: com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency.4.1
                        @Override // com.google.i18n.phonenumbers.PhoneNumberMatcher.NumberGroupingChecker
                        public boolean checkGroups(PhoneNumberUtil phoneNumberUtil2, Phonenumber.PhoneNumber phoneNumber2, StringBuilder sb, String[] strArr) {
                            return PhoneNumberMatcher.allNumberGroupsAreExactlyPresent(phoneNumberUtil2, phoneNumber2, sb, strArr);
                        }
                    });
                }
                return false;
            }
        };

        abstract boolean verify(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence, PhoneNumberUtil phoneNumberUtil, PhoneNumberMatcher phoneNumberMatcher);
    }

    public enum MatchType {
        NOT_A_NUMBER,
        NO_MATCH,
        SHORT_NSN_MATCH,
        NSN_MATCH,
        EXACT_MATCH
    }

    public enum PhoneNumberFormat {
        E164,
        INTERNATIONAL,
        NATIONAL,
        RFC3966
    }

    public enum PhoneNumberType {
        FIXED_LINE,
        MOBILE,
        FIXED_LINE_OR_MOBILE,
        TOLL_FREE,
        PREMIUM_RATE,
        SHARED_COST,
        VOIP,
        PERSONAL_NUMBER,
        PAGER,
        UAN,
        VOICEMAIL,
        UNKNOWN
    }

    public enum ValidationResult {
        IS_POSSIBLE,
        IS_POSSIBLE_LOCAL_ONLY,
        INVALID_COUNTRY_CODE,
        TOO_SHORT,
        INVALID_LENGTH,
        TOO_LONG
    }

    static {
        HashMap map = new HashMap();
        map.put(54, "9");
        MOBILE_TOKEN_MAPPINGS = Collections.unmodifiableMap(map);
        HashSet hashSet = new HashSet();
        hashSet.add(86);
        GEO_MOBILE_COUNTRIES_WITHOUT_MOBILE_AREA_CODES = Collections.unmodifiableSet(hashSet);
        HashSet hashSet2 = new HashSet();
        hashSet2.add(52);
        hashSet2.add(54);
        hashSet2.add(55);
        hashSet2.add(62);
        hashSet2.addAll(hashSet);
        GEO_MOBILE_COUNTRIES = Collections.unmodifiableSet(hashSet2);
        HashMap map2 = new HashMap();
        map2.put('0', '0');
        map2.put('1', '1');
        map2.put('2', '2');
        map2.put('3', '3');
        map2.put('4', '4');
        map2.put('5', '5');
        map2.put('6', '6');
        map2.put('7', '7');
        map2.put('8', '8');
        map2.put('9', '9');
        HashMap map3 = new HashMap(40);
        map3.put('A', '2');
        map3.put('B', '2');
        map3.put('C', '2');
        map3.put('D', '3');
        map3.put('E', '3');
        map3.put('F', '3');
        map3.put('G', '4');
        map3.put('H', '4');
        map3.put('I', '4');
        map3.put('J', '5');
        map3.put('K', '5');
        map3.put('L', '5');
        map3.put('M', '6');
        map3.put('N', '6');
        map3.put('O', '6');
        map3.put('P', '7');
        map3.put('Q', '7');
        map3.put('R', '7');
        map3.put('S', '7');
        map3.put('T', '8');
        map3.put('U', '8');
        map3.put('V', '8');
        map3.put('W', '9');
        map3.put('X', '9');
        map3.put('Y', '9');
        map3.put('Z', '9');
        Map<Character, Character> mapUnmodifiableMap = Collections.unmodifiableMap(map3);
        ALPHA_MAPPINGS = mapUnmodifiableMap;
        HashMap map4 = new HashMap(100);
        map4.putAll(mapUnmodifiableMap);
        map4.putAll(map2);
        ALPHA_PHONE_MAPPINGS = Collections.unmodifiableMap(map4);
        HashMap map5 = new HashMap();
        map5.putAll(map2);
        Character chValueOf = Character.valueOf(PLUS_SIGN);
        map5.put(chValueOf, chValueOf);
        Character chValueOf2 = Character.valueOf(STAR_SIGN);
        map5.put(chValueOf2, chValueOf2);
        map5.put('#', '#');
        DIALLABLE_CHAR_MAPPINGS = Collections.unmodifiableMap(map5);
        HashMap map6 = new HashMap();
        Iterator<Character> it = mapUnmodifiableMap.keySet().iterator();
        while (it.hasNext()) {
            char cCharValue = it.next().charValue();
            map6.put(Character.valueOf(Character.toLowerCase(cCharValue)), Character.valueOf(cCharValue));
            map6.put(Character.valueOf(cCharValue), Character.valueOf(cCharValue));
        }
        map6.putAll(map2);
        map6.put(Character.valueOf(CoreConstants.DASH_CHAR), Character.valueOf(CoreConstants.DASH_CHAR));
        map6.put((char) 65293, Character.valueOf(CoreConstants.DASH_CHAR));
        map6.put((char) 8208, Character.valueOf(CoreConstants.DASH_CHAR));
        map6.put((char) 8209, Character.valueOf(CoreConstants.DASH_CHAR));
        map6.put((char) 8210, Character.valueOf(CoreConstants.DASH_CHAR));
        map6.put(Character.valueOf(Typography.ndash), Character.valueOf(CoreConstants.DASH_CHAR));
        map6.put(Character.valueOf(Typography.mdash), Character.valueOf(CoreConstants.DASH_CHAR));
        map6.put((char) 8213, Character.valueOf(CoreConstants.DASH_CHAR));
        map6.put((char) 8722, Character.valueOf(CoreConstants.DASH_CHAR));
        map6.put('/', '/');
        map6.put((char) 65295, '/');
        map6.put(' ', ' ');
        map6.put((char) 12288, ' ');
        map6.put((char) 8288, ' ');
        map6.put('.', '.');
        map6.put((char) 65294, '.');
        ALL_PLUS_NUMBER_GROUPING_SYMBOLS = Collections.unmodifiableMap(map6);
        SINGLE_INTERNATIONAL_PREFIX = Pattern.compile("[\\d]+(?:[~⁓∼～][\\d]+)?");
        StringBuilder sb = new StringBuilder();
        Map<Character, Character> map7 = ALPHA_MAPPINGS;
        String string = sb.append(Arrays.toString(map7.keySet().toArray()).replaceAll("[, \\[\\]]", "")).append(Arrays.toString(map7.keySet().toArray()).toLowerCase().replaceAll("[, \\[\\]]", "")).toString();
        VALID_ALPHA = string;
        PLUS_CHARS_PATTERN = Pattern.compile("[+＋]+");
        SEPARATOR_PATTERN = Pattern.compile("[-x‐-―−ー－-／  \u00ad\u200b\u2060\u3000()（）［］.\\[\\]/~⁓∼～]+");
        CAPTURING_DIGIT_PATTERN = Pattern.compile("(\\p{Nd})");
        VALID_START_CHAR_PATTERN = Pattern.compile(VALID_START_CHAR);
        SECOND_NUMBER_START_PATTERN = Pattern.compile(SECOND_NUMBER_START);
        UNWANTED_END_CHAR_PATTERN = Pattern.compile(UNWANTED_END_CHARS);
        VALID_ALPHA_PHONE_PATTERN = Pattern.compile("(?:.*?[A-Za-z]){3}.*");
        String str = "\\p{Nd}{2}|[+＋]*+(?:[-x‐-―−ー－-／  \u00ad\u200b\u2060\u3000()（）［］.\\[\\]/~⁓∼～*]*\\p{Nd}){3,}[-x‐-―−ー－-／  \u00ad\u200b\u2060\u3000()（）［］.\\[\\]/~⁓∼～*" + string + "\\p{Nd}]*";
        VALID_PHONE_NUMBER = str;
        String strCreateExtnPattern = createExtnPattern(true);
        EXTN_PATTERNS_FOR_PARSING = strCreateExtnPattern;
        EXTN_PATTERNS_FOR_MATCHING = createExtnPattern(false);
        EXTN_PATTERN = Pattern.compile("(?:" + strCreateExtnPattern + ")$", 66);
        VALID_PHONE_NUMBER_PATTERN = Pattern.compile(str + "(?:" + strCreateExtnPattern + ")?", 66);
        NON_DIGITS_PATTERN = Pattern.compile("(\\D+)");
        FIRST_GROUP_PATTERN = Pattern.compile("(\\$\\d)");
        FIRST_GROUP_ONLY_PREFIX_PATTERN = Pattern.compile("\\(?\\$1\\)?");
        instance = null;
    }

    private static String extnDigits(int i) {
        return "(\\p{Nd}{1," + i + "})";
    }

    private static String createExtnPattern(boolean z) {
        String str = (RFC3966_EXTN_PREFIX + extnDigits(20)) + "|" + ("[  \\t,]*(?:e?xt(?:ensi(?:ó?|ó))?n?|ｅ?ｘｔｎ?|доб|anexo)[:\\.．]?[  \\t,-]*" + extnDigits(20) + "#?") + "|" + ("[  \\t,]*(?:[xｘ#＃~～]|int|ｉｎｔ)[:\\.．]?[  \\t,-]*" + extnDigits(9) + "#?") + "|" + ("[- ]+" + extnDigits(6) + HyperMenuUtils.GROUP_TITLE_START);
        if (!z) {
            return str;
        }
        return str + "|" + ("[  \\t]*(?:,{2}|;)[:\\.．]?[  \\t,-]*" + extnDigits(15) + "#?") + "|" + ("[  \\t]*(?:,)+[:\\.．]?[  \\t,-]*" + extnDigits(9) + "#?");
    }

    PhoneNumberUtil(MetadataSource metadataSource, Map<Integer, List<String>> map) {
        this.metadataSource = metadataSource;
        this.countryCallingCodeToRegionCodeMap = map;
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            List<String> value = entry.getValue();
            if (value.size() == 1 && REGION_CODE_FOR_NON_GEO_ENTITY.equals(value.get(0))) {
                this.countryCodesForNonGeographicalRegion.add(entry.getKey());
            } else {
                this.supportedRegions.addAll(value);
            }
        }
        if (this.supportedRegions.remove(REGION_CODE_FOR_NON_GEO_ENTITY)) {
            logger.log(Level.WARNING, "invalid metadata (country calling code was mapped to the non-geo entity as well as specific region(s))");
        }
        this.nanpaRegions.addAll(map.get(1));
    }

    static CharSequence extractPossibleNumber(CharSequence charSequence) {
        Matcher matcher = VALID_START_CHAR_PATTERN.matcher(charSequence);
        if (matcher.find()) {
            CharSequence charSequenceSubSequence = charSequence.subSequence(matcher.start(), charSequence.length());
            Matcher matcher2 = UNWANTED_END_CHAR_PATTERN.matcher(charSequenceSubSequence);
            if (matcher2.find()) {
                charSequenceSubSequence = charSequenceSubSequence.subSequence(0, matcher2.start());
            }
            Matcher matcher3 = SECOND_NUMBER_START_PATTERN.matcher(charSequenceSubSequence);
            return matcher3.find() ? charSequenceSubSequence.subSequence(0, matcher3.start()) : charSequenceSubSequence;
        }
        return "";
    }

    static boolean isViablePhoneNumber(CharSequence charSequence) {
        if (charSequence.length() < 2) {
            return false;
        }
        return VALID_PHONE_NUMBER_PATTERN.matcher(charSequence).matches();
    }

    static StringBuilder normalize(StringBuilder sb) {
        if (VALID_ALPHA_PHONE_PATTERN.matcher(sb).matches()) {
            sb.replace(0, sb.length(), normalizeHelper(sb, ALPHA_PHONE_MAPPINGS, true));
            return sb;
        }
        sb.replace(0, sb.length(), normalizeDigitsOnly(sb));
        return sb;
    }

    public static String normalizeDigitsOnly(CharSequence charSequence) {
        return normalizeDigits(charSequence, false).toString();
    }

    static StringBuilder normalizeDigits(CharSequence charSequence, boolean z) {
        StringBuilder sb = new StringBuilder(charSequence.length());
        for (int i = 0; i < charSequence.length(); i++) {
            char cCharAt = charSequence.charAt(i);
            int iDigit = Character.digit(cCharAt, 10);
            if (iDigit != -1) {
                sb.append(iDigit);
            } else if (z) {
                sb.append(cCharAt);
            }
        }
        return sb;
    }

    public static String normalizeDiallableCharsOnly(CharSequence charSequence) {
        return normalizeHelper(charSequence, DIALLABLE_CHAR_MAPPINGS, true);
    }

    public static String convertAlphaCharactersInNumber(CharSequence charSequence) {
        return normalizeHelper(charSequence, ALPHA_PHONE_MAPPINGS, false);
    }

    public int getLengthOfGeographicalAreaCode(Phonenumber.PhoneNumber phoneNumber) {
        Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(getRegionCodeForNumber(phoneNumber));
        if (metadataForRegion == null) {
            return 0;
        }
        if (!metadataForRegion.hasNationalPrefix() && !phoneNumber.isItalianLeadingZero()) {
            return 0;
        }
        PhoneNumberType numberType = getNumberType(phoneNumber);
        int countryCode = phoneNumber.getCountryCode();
        if (!(numberType == PhoneNumberType.MOBILE && GEO_MOBILE_COUNTRIES_WITHOUT_MOBILE_AREA_CODES.contains(Integer.valueOf(countryCode))) && isNumberGeographical(numberType, countryCode)) {
            return getLengthOfNationalDestinationCode(phoneNumber);
        }
        return 0;
    }

    public int getLengthOfNationalDestinationCode(Phonenumber.PhoneNumber phoneNumber) {
        Phonenumber.PhoneNumber phoneNumber2;
        if (phoneNumber.hasExtension()) {
            phoneNumber2 = new Phonenumber.PhoneNumber();
            phoneNumber2.mergeFrom(phoneNumber);
            phoneNumber2.clearExtension();
        } else {
            phoneNumber2 = phoneNumber;
        }
        String[] strArrSplit = NON_DIGITS_PATTERN.split(format(phoneNumber2, PhoneNumberFormat.INTERNATIONAL));
        if (strArrSplit.length <= 3) {
            return 0;
        }
        if (getNumberType(phoneNumber) == PhoneNumberType.MOBILE && !getCountryMobileToken(phoneNumber.getCountryCode()).equals("")) {
            return strArrSplit[2].length() + strArrSplit[3].length();
        }
        return strArrSplit[2].length();
    }

    public static String getCountryMobileToken(int i) {
        Map<Integer, String> map = MOBILE_TOKEN_MAPPINGS;
        if (map.containsKey(Integer.valueOf(i))) {
            return map.get(Integer.valueOf(i));
        }
        return "";
    }

    private static String normalizeHelper(CharSequence charSequence, Map<Character, Character> map, boolean z) {
        StringBuilder sb = new StringBuilder(charSequence.length());
        for (int i = 0; i < charSequence.length(); i++) {
            char cCharAt = charSequence.charAt(i);
            Character ch2 = map.get(Character.valueOf(Character.toUpperCase(cCharAt)));
            if (ch2 != null) {
                sb.append(ch2);
            } else if (!z) {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    static synchronized void setInstance(PhoneNumberUtil phoneNumberUtil) {
        instance = phoneNumberUtil;
    }

    public Set<String> getSupportedRegions() {
        return Collections.unmodifiableSet(this.supportedRegions);
    }

    public Set<Integer> getSupportedGlobalNetworkCallingCodes() {
        return Collections.unmodifiableSet(this.countryCodesForNonGeographicalRegion);
    }

    public Set<Integer> getSupportedCallingCodes() {
        return Collections.unmodifiableSet(this.countryCallingCodeToRegionCodeMap.keySet());
    }

    private static boolean descHasPossibleNumberData(Phonemetadata.PhoneNumberDesc phoneNumberDesc) {
        return (phoneNumberDesc.getPossibleLengthCount() == 1 && phoneNumberDesc.getPossibleLength(0) == -1) ? false : true;
    }

    private static boolean descHasData(Phonemetadata.PhoneNumberDesc phoneNumberDesc) {
        return phoneNumberDesc.hasExampleNumber() || descHasPossibleNumberData(phoneNumberDesc) || phoneNumberDesc.hasNationalNumberPattern();
    }

    private Set<PhoneNumberType> getSupportedTypesForMetadata(Phonemetadata.PhoneMetadata phoneMetadata) {
        TreeSet treeSet = new TreeSet();
        for (PhoneNumberType phoneNumberType : PhoneNumberType.values()) {
            if (phoneNumberType != PhoneNumberType.FIXED_LINE_OR_MOBILE && phoneNumberType != PhoneNumberType.UNKNOWN && descHasData(getNumberDescByType(phoneMetadata, phoneNumberType))) {
                treeSet.add(phoneNumberType);
            }
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public Set<PhoneNumberType> getSupportedTypesForRegion(String str) {
        if (!isValidRegionCode(str)) {
            logger.log(Level.WARNING, "Invalid or unknown region code provided: " + str);
            return Collections.unmodifiableSet(new TreeSet());
        }
        return getSupportedTypesForMetadata(getMetadataForRegion(str));
    }

    public Set<PhoneNumberType> getSupportedTypesForNonGeoEntity(int i) {
        Phonemetadata.PhoneMetadata metadataForNonGeographicalRegion = getMetadataForNonGeographicalRegion(i);
        if (metadataForNonGeographicalRegion == null) {
            logger.log(Level.WARNING, "Unknown country calling code for a non-geographical entity provided: " + i);
            return Collections.unmodifiableSet(new TreeSet());
        }
        return getSupportedTypesForMetadata(metadataForNonGeographicalRegion);
    }

    public static synchronized PhoneNumberUtil getInstance() {
        if (instance == null) {
            setInstance(createInstance(MetadataManager.DEFAULT_METADATA_LOADER));
        }
        return instance;
    }

    public static PhoneNumberUtil createInstance(MetadataLoader metadataLoader) {
        if (metadataLoader == null) {
            throw new IllegalArgumentException("metadataLoader could not be null.");
        }
        return createInstance(new MultiFileMetadataSourceImpl(metadataLoader));
    }

    private static PhoneNumberUtil createInstance(MetadataSource metadataSource) {
        if (metadataSource == null) {
            throw new IllegalArgumentException("metadataSource could not be null.");
        }
        return new PhoneNumberUtil(metadataSource, CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap());
    }

    static boolean formattingRuleHasFirstGroupOnly(String str) {
        return str.length() == 0 || FIRST_GROUP_ONLY_PREFIX_PATTERN.matcher(str).matches();
    }

    public boolean isNumberGeographical(Phonenumber.PhoneNumber phoneNumber) {
        return isNumberGeographical(getNumberType(phoneNumber), phoneNumber.getCountryCode());
    }

    public boolean isNumberGeographical(PhoneNumberType phoneNumberType, int i) {
        if (phoneNumberType == PhoneNumberType.FIXED_LINE || phoneNumberType == PhoneNumberType.FIXED_LINE_OR_MOBILE) {
            return true;
        }
        return GEO_MOBILE_COUNTRIES.contains(Integer.valueOf(i)) && phoneNumberType == PhoneNumberType.MOBILE;
    }

    private boolean isValidRegionCode(String str) {
        return str != null && this.supportedRegions.contains(str);
    }

    private boolean hasValidCountryCallingCode(int i) {
        return this.countryCallingCodeToRegionCodeMap.containsKey(Integer.valueOf(i));
    }

    public String format(Phonenumber.PhoneNumber phoneNumber, PhoneNumberFormat phoneNumberFormat) {
        if (phoneNumber.getNationalNumber() == 0 && phoneNumber.hasRawInput()) {
            String rawInput = phoneNumber.getRawInput();
            if (rawInput.length() > 0) {
                return rawInput;
            }
        }
        StringBuilder sb = new StringBuilder(20);
        format(phoneNumber, phoneNumberFormat, sb);
        return sb.toString();
    }

    public void format(Phonenumber.PhoneNumber phoneNumber, PhoneNumberFormat phoneNumberFormat, StringBuilder sb) {
        sb.setLength(0);
        int countryCode = phoneNumber.getCountryCode();
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (phoneNumberFormat == PhoneNumberFormat.E164) {
            sb.append(nationalSignificantNumber);
            prefixNumberWithCountryCallingCode(countryCode, PhoneNumberFormat.E164, sb);
        } else {
            if (!hasValidCountryCallingCode(countryCode)) {
                sb.append(nationalSignificantNumber);
                return;
            }
            Phonemetadata.PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
            sb.append(formatNsn(nationalSignificantNumber, metadataForRegionOrCallingCode, phoneNumberFormat));
            maybeAppendFormattedExtension(phoneNumber, metadataForRegionOrCallingCode, phoneNumberFormat, sb);
            prefixNumberWithCountryCallingCode(countryCode, phoneNumberFormat, sb);
        }
    }

    public String formatByPattern(Phonenumber.PhoneNumber phoneNumber, PhoneNumberFormat phoneNumberFormat, List<Phonemetadata.NumberFormat> list) {
        int countryCode = phoneNumber.getCountryCode();
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (!hasValidCountryCallingCode(countryCode)) {
            return nationalSignificantNumber;
        }
        Phonemetadata.PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
        StringBuilder sb = new StringBuilder(20);
        Phonemetadata.NumberFormat numberFormatChooseFormattingPatternForNumber = chooseFormattingPatternForNumber(list, nationalSignificantNumber);
        if (numberFormatChooseFormattingPatternForNumber == null) {
            sb.append(nationalSignificantNumber);
        } else {
            Phonemetadata.NumberFormat.Builder builderNewBuilder = Phonemetadata.NumberFormat.newBuilder();
            builderNewBuilder.mergeFrom(numberFormatChooseFormattingPatternForNumber);
            String nationalPrefixFormattingRule = numberFormatChooseFormattingPatternForNumber.getNationalPrefixFormattingRule();
            if (nationalPrefixFormattingRule.length() > 0) {
                String nationalPrefix = metadataForRegionOrCallingCode.getNationalPrefix();
                if (nationalPrefix.length() > 0) {
                    builderNewBuilder.setNationalPrefixFormattingRule(nationalPrefixFormattingRule.replace(NP_STRING, nationalPrefix).replace(FG_STRING, "$1"));
                } else {
                    builderNewBuilder.clearNationalPrefixFormattingRule();
                }
            }
            sb.append(formatNsnUsingPattern(nationalSignificantNumber, builderNewBuilder.build(), phoneNumberFormat));
        }
        maybeAppendFormattedExtension(phoneNumber, metadataForRegionOrCallingCode, phoneNumberFormat, sb);
        prefixNumberWithCountryCallingCode(countryCode, phoneNumberFormat, sb);
        return sb.toString();
    }

    public String formatNationalNumberWithCarrierCode(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence) {
        int countryCode = phoneNumber.getCountryCode();
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (!hasValidCountryCallingCode(countryCode)) {
            return nationalSignificantNumber;
        }
        Phonemetadata.PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
        StringBuilder sb = new StringBuilder(20);
        sb.append(formatNsn(nationalSignificantNumber, metadataForRegionOrCallingCode, PhoneNumberFormat.NATIONAL, charSequence));
        maybeAppendFormattedExtension(phoneNumber, metadataForRegionOrCallingCode, PhoneNumberFormat.NATIONAL, sb);
        prefixNumberWithCountryCallingCode(countryCode, PhoneNumberFormat.NATIONAL, sb);
        return sb.toString();
    }

    private Phonemetadata.PhoneMetadata getMetadataForRegionOrCallingCode(int i, String str) {
        if (REGION_CODE_FOR_NON_GEO_ENTITY.equals(str)) {
            return getMetadataForNonGeographicalRegion(i);
        }
        return getMetadataForRegion(str);
    }

    public String formatNationalNumberWithPreferredCarrierCode(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence) {
        if (phoneNumber.getPreferredDomesticCarrierCode().length() > 0) {
            charSequence = phoneNumber.getPreferredDomesticCarrierCode();
        }
        return formatNationalNumberWithCarrierCode(phoneNumber, charSequence);
    }

    public String formatNumberForMobileDialing(Phonenumber.PhoneNumber phoneNumber, String str, boolean z) {
        String nationalNumberWithCarrierCode;
        int countryCode = phoneNumber.getCountryCode();
        String nationalNumberWithPreferredCarrierCode = "";
        if (!hasValidCountryCallingCode(countryCode)) {
            return phoneNumber.hasRawInput() ? phoneNumber.getRawInput() : "";
        }
        Phonenumber.PhoneNumber phoneNumberClearExtension = new Phonenumber.PhoneNumber().mergeFrom(phoneNumber).clearExtension();
        String regionCodeForCountryCode = getRegionCodeForCountryCode(countryCode);
        PhoneNumberType numberType = getNumberType(phoneNumberClearExtension);
        boolean z2 = numberType != PhoneNumberType.UNKNOWN;
        if (str.equals(regionCodeForCountryCode)) {
            boolean z3 = numberType == PhoneNumberType.FIXED_LINE || numberType == PhoneNumberType.MOBILE || numberType == PhoneNumberType.FIXED_LINE_OR_MOBILE;
            if (regionCodeForCountryCode.equals("CO") && numberType == PhoneNumberType.FIXED_LINE) {
                nationalNumberWithCarrierCode = formatNationalNumberWithCarrierCode(phoneNumberClearExtension, "3");
            } else if (regionCodeForCountryCode.equals("BR") && z3) {
                if (phoneNumberClearExtension.getPreferredDomesticCarrierCode().length() > 0) {
                    nationalNumberWithPreferredCarrierCode = formatNationalNumberWithPreferredCarrierCode(phoneNumberClearExtension, "");
                }
            } else if (countryCode == 1) {
                Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(str);
                if (canBeInternationallyDialled(phoneNumberClearExtension) && testNumberLength(getNationalSignificantNumber(phoneNumberClearExtension), metadataForRegion) != ValidationResult.TOO_SHORT) {
                    nationalNumberWithCarrierCode = format(phoneNumberClearExtension, PhoneNumberFormat.INTERNATIONAL);
                } else {
                    nationalNumberWithCarrierCode = format(phoneNumberClearExtension, PhoneNumberFormat.NATIONAL);
                }
            } else if ((regionCodeForCountryCode.equals(REGION_CODE_FOR_NON_GEO_ENTITY) || ((regionCodeForCountryCode.equals("MX") || regionCodeForCountryCode.equals("CL") || regionCodeForCountryCode.equals("UZ")) && z3)) && canBeInternationallyDialled(phoneNumberClearExtension)) {
                nationalNumberWithCarrierCode = format(phoneNumberClearExtension, PhoneNumberFormat.INTERNATIONAL);
            } else {
                nationalNumberWithCarrierCode = format(phoneNumberClearExtension, PhoneNumberFormat.NATIONAL);
            }
            nationalNumberWithPreferredCarrierCode = nationalNumberWithCarrierCode;
        } else if (z2 && canBeInternationallyDialled(phoneNumberClearExtension)) {
            if (z) {
                return format(phoneNumberClearExtension, PhoneNumberFormat.INTERNATIONAL);
            }
            return format(phoneNumberClearExtension, PhoneNumberFormat.E164);
        }
        return z ? nationalNumberWithPreferredCarrierCode : normalizeDiallableCharsOnly(nationalNumberWithPreferredCarrierCode);
    }

    public String formatOutOfCountryCallingNumber(Phonenumber.PhoneNumber phoneNumber, String str) {
        if (!isValidRegionCode(str)) {
            logger.log(Level.WARNING, "Trying to format number from invalid region " + str + ". International formatting applied.");
            return format(phoneNumber, PhoneNumberFormat.INTERNATIONAL);
        }
        int countryCode = phoneNumber.getCountryCode();
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (!hasValidCountryCallingCode(countryCode)) {
            return nationalSignificantNumber;
        }
        if (countryCode == 1) {
            if (isNANPACountry(str)) {
                return countryCode + StringUtils.SPACE + format(phoneNumber, PhoneNumberFormat.NATIONAL);
            }
        } else if (countryCode == getCountryCodeForValidRegion(str)) {
            return format(phoneNumber, PhoneNumberFormat.NATIONAL);
        }
        Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(str);
        String internationalPrefix = metadataForRegion.getInternationalPrefix();
        if (metadataForRegion.hasPreferredInternationalPrefix()) {
            internationalPrefix = metadataForRegion.getPreferredInternationalPrefix();
        } else if (!SINGLE_INTERNATIONAL_PREFIX.matcher(internationalPrefix).matches()) {
            internationalPrefix = "";
        }
        Phonemetadata.PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
        StringBuilder sb = new StringBuilder(formatNsn(nationalSignificantNumber, metadataForRegionOrCallingCode, PhoneNumberFormat.INTERNATIONAL));
        maybeAppendFormattedExtension(phoneNumber, metadataForRegionOrCallingCode, PhoneNumberFormat.INTERNATIONAL, sb);
        if (internationalPrefix.length() > 0) {
            sb.insert(0, StringUtils.SPACE).insert(0, countryCode).insert(0, StringUtils.SPACE).insert(0, internationalPrefix);
        } else {
            prefixNumberWithCountryCallingCode(countryCode, PhoneNumberFormat.INTERNATIONAL, sb);
        }
        return sb.toString();
    }

    public String formatInOriginalFormat(Phonenumber.PhoneNumber phoneNumber, String str) {
        String outOfCountryCallingNumber;
        String nationalPrefixFormattingRule;
        int iIndexOf;
        if (phoneNumber.hasRawInput() && !hasFormattingPatternForNumber(phoneNumber)) {
            return phoneNumber.getRawInput();
        }
        if (!phoneNumber.hasCountryCodeSource()) {
            return format(phoneNumber, PhoneNumberFormat.NATIONAL);
        }
        int i = AnonymousClass2.$SwitchMap$com$google$i18n$phonenumbers$Phonenumber$PhoneNumber$CountryCodeSource[phoneNumber.getCountryCodeSource().ordinal()];
        if (i == 1) {
            outOfCountryCallingNumber = format(phoneNumber, PhoneNumberFormat.INTERNATIONAL);
        } else if (i == 2) {
            outOfCountryCallingNumber = formatOutOfCountryCallingNumber(phoneNumber, str);
        } else if (i == 3) {
            outOfCountryCallingNumber = format(phoneNumber, PhoneNumberFormat.INTERNATIONAL).substring(1);
        } else {
            String regionCodeForCountryCode = getRegionCodeForCountryCode(phoneNumber.getCountryCode());
            String nddPrefixForRegion = getNddPrefixForRegion(regionCodeForCountryCode, true);
            outOfCountryCallingNumber = format(phoneNumber, PhoneNumberFormat.NATIONAL);
            if (nddPrefixForRegion != null && nddPrefixForRegion.length() != 0 && !rawInputContainsNationalPrefix(phoneNumber.getRawInput(), nddPrefixForRegion, regionCodeForCountryCode)) {
                Phonemetadata.NumberFormat numberFormatChooseFormattingPatternForNumber = chooseFormattingPatternForNumber(getMetadataForRegion(regionCodeForCountryCode).getNumberFormatList(), getNationalSignificantNumber(phoneNumber));
                if (numberFormatChooseFormattingPatternForNumber != null && (iIndexOf = (nationalPrefixFormattingRule = numberFormatChooseFormattingPatternForNumber.getNationalPrefixFormattingRule()).indexOf("$1")) > 0 && normalizeDigitsOnly(nationalPrefixFormattingRule.substring(0, iIndexOf)).length() != 0) {
                    Phonemetadata.NumberFormat.Builder builderNewBuilder = Phonemetadata.NumberFormat.newBuilder();
                    builderNewBuilder.mergeFrom(numberFormatChooseFormattingPatternForNumber);
                    builderNewBuilder.clearNationalPrefixFormattingRule();
                    ArrayList arrayList = new ArrayList(1);
                    arrayList.add(builderNewBuilder.build());
                    outOfCountryCallingNumber = formatByPattern(phoneNumber, PhoneNumberFormat.NATIONAL, arrayList);
                }
            }
        }
        String rawInput = phoneNumber.getRawInput();
        return (outOfCountryCallingNumber == null || rawInput.length() <= 0 || normalizeDiallableCharsOnly(outOfCountryCallingNumber).equals(normalizeDiallableCharsOnly(rawInput))) ? outOfCountryCallingNumber : rawInput;
    }

    private boolean rawInputContainsNationalPrefix(String str, String str2, String str3) {
        String strNormalizeDigitsOnly = normalizeDigitsOnly(str);
        if (strNormalizeDigitsOnly.startsWith(str2)) {
            try {
                return isValidNumber(parse(strNormalizeDigitsOnly.substring(str2.length()), str3));
            } catch (NumberParseException unused) {
            }
        }
        return false;
    }

    private boolean hasFormattingPatternForNumber(Phonenumber.PhoneNumber phoneNumber) {
        int countryCode = phoneNumber.getCountryCode();
        Phonemetadata.PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
        if (metadataForRegionOrCallingCode == null) {
            return false;
        }
        return chooseFormattingPatternForNumber(metadataForRegionOrCallingCode.getNumberFormatList(), getNationalSignificantNumber(phoneNumber)) != null;
    }

    public String formatOutOfCountryKeepingAlphaChars(Phonenumber.PhoneNumber phoneNumber, String str) {
        String internationalPrefix;
        int iIndexOf;
        String rawInput = phoneNumber.getRawInput();
        if (rawInput.length() == 0) {
            return formatOutOfCountryCallingNumber(phoneNumber, str);
        }
        int countryCode = phoneNumber.getCountryCode();
        if (!hasValidCountryCallingCode(countryCode)) {
            return rawInput;
        }
        String strNormalizeHelper = normalizeHelper(rawInput, ALL_PLUS_NUMBER_GROUPING_SYMBOLS, true);
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (nationalSignificantNumber.length() > 3 && (iIndexOf = strNormalizeHelper.indexOf(nationalSignificantNumber.substring(0, 3))) != -1) {
            strNormalizeHelper = strNormalizeHelper.substring(iIndexOf);
        }
        Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(str);
        if (countryCode == 1) {
            if (isNANPACountry(str)) {
                return countryCode + StringUtils.SPACE + strNormalizeHelper;
            }
        } else if (metadataForRegion != null && countryCode == getCountryCodeForValidRegion(str)) {
            Phonemetadata.NumberFormat numberFormatChooseFormattingPatternForNumber = chooseFormattingPatternForNumber(metadataForRegion.getNumberFormatList(), nationalSignificantNumber);
            if (numberFormatChooseFormattingPatternForNumber == null) {
                return strNormalizeHelper;
            }
            Phonemetadata.NumberFormat.Builder builderNewBuilder = Phonemetadata.NumberFormat.newBuilder();
            builderNewBuilder.mergeFrom(numberFormatChooseFormattingPatternForNumber);
            builderNewBuilder.setPattern("(\\d+)(.*)");
            builderNewBuilder.setFormat("$1$2");
            return formatNsnUsingPattern(strNormalizeHelper, builderNewBuilder.build(), PhoneNumberFormat.NATIONAL);
        }
        if (metadataForRegion == null) {
            internationalPrefix = "";
        } else {
            internationalPrefix = metadataForRegion.getInternationalPrefix();
            if (!SINGLE_INTERNATIONAL_PREFIX.matcher(internationalPrefix).matches()) {
                internationalPrefix = metadataForRegion.getPreferredInternationalPrefix();
            }
        }
        StringBuilder sb = new StringBuilder(strNormalizeHelper);
        maybeAppendFormattedExtension(phoneNumber, getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode)), PhoneNumberFormat.INTERNATIONAL, sb);
        if (internationalPrefix.length() > 0) {
            sb.insert(0, StringUtils.SPACE).insert(0, countryCode).insert(0, StringUtils.SPACE).insert(0, internationalPrefix);
        } else {
            if (!isValidRegionCode(str)) {
                logger.log(Level.WARNING, "Trying to format number from invalid region " + str + ". International formatting applied.");
            }
            prefixNumberWithCountryCallingCode(countryCode, PhoneNumberFormat.INTERNATIONAL, sb);
        }
        return sb.toString();
    }

    public String getNationalSignificantNumber(Phonenumber.PhoneNumber phoneNumber) {
        StringBuilder sb = new StringBuilder();
        if (phoneNumber.isItalianLeadingZero() && phoneNumber.getNumberOfLeadingZeros() > 0) {
            char[] cArr = new char[phoneNumber.getNumberOfLeadingZeros()];
            Arrays.fill(cArr, '0');
            sb.append(new String(cArr));
        }
        sb.append(phoneNumber.getNationalNumber());
        return sb.toString();
    }

    private void prefixNumberWithCountryCallingCode(int i, PhoneNumberFormat phoneNumberFormat, StringBuilder sb) {
        int i2 = AnonymousClass2.$SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberFormat[phoneNumberFormat.ordinal()];
        if (i2 == 1) {
            sb.insert(0, i).insert(0, PLUS_SIGN);
        } else if (i2 == 2) {
            sb.insert(0, StringUtils.SPACE).insert(0, i).insert(0, PLUS_SIGN);
        } else {
            if (i2 != 3) {
                return;
            }
            sb.insert(0, "-").insert(0, i).insert(0, PLUS_SIGN).insert(0, "tel:");
        }
    }

    private String formatNsn(String str, Phonemetadata.PhoneMetadata phoneMetadata, PhoneNumberFormat phoneNumberFormat) {
        return formatNsn(str, phoneMetadata, phoneNumberFormat, null);
    }

    private String formatNsn(String str, Phonemetadata.PhoneMetadata phoneMetadata, PhoneNumberFormat phoneNumberFormat, CharSequence charSequence) {
        List<Phonemetadata.NumberFormat> numberFormatList;
        if (phoneMetadata.getIntlNumberFormatList().size() == 0 || phoneNumberFormat == PhoneNumberFormat.NATIONAL) {
            numberFormatList = phoneMetadata.getNumberFormatList();
        } else {
            numberFormatList = phoneMetadata.getIntlNumberFormatList();
        }
        Phonemetadata.NumberFormat numberFormatChooseFormattingPatternForNumber = chooseFormattingPatternForNumber(numberFormatList, str);
        return numberFormatChooseFormattingPatternForNumber == null ? str : formatNsnUsingPattern(str, numberFormatChooseFormattingPatternForNumber, phoneNumberFormat, charSequence);
    }

    Phonemetadata.NumberFormat chooseFormattingPatternForNumber(List<Phonemetadata.NumberFormat> list, String str) {
        for (Phonemetadata.NumberFormat numberFormat : list) {
            int leadingDigitsPatternCount = numberFormat.getLeadingDigitsPatternCount();
            if (leadingDigitsPatternCount == 0 || this.regexCache.getPatternForRegex(numberFormat.getLeadingDigitsPattern(leadingDigitsPatternCount - 1)).matcher(str).lookingAt()) {
                if (this.regexCache.getPatternForRegex(numberFormat.getPattern()).matcher(str).matches()) {
                    return numberFormat;
                }
            }
        }
        return null;
    }

    String formatNsnUsingPattern(String str, Phonemetadata.NumberFormat numberFormat, PhoneNumberFormat phoneNumberFormat) {
        return formatNsnUsingPattern(str, numberFormat, phoneNumberFormat, null);
    }

    private String formatNsnUsingPattern(String str, Phonemetadata.NumberFormat numberFormat, PhoneNumberFormat phoneNumberFormat, CharSequence charSequence) {
        String strReplaceAll;
        String format = numberFormat.getFormat();
        Matcher matcher = this.regexCache.getPatternForRegex(numberFormat.getPattern()).matcher(str);
        if (phoneNumberFormat == PhoneNumberFormat.NATIONAL && charSequence != null && charSequence.length() > 0 && numberFormat.getDomesticCarrierCodeFormattingRule().length() > 0) {
            strReplaceAll = matcher.replaceAll(FIRST_GROUP_PATTERN.matcher(format).replaceFirst(numberFormat.getDomesticCarrierCodeFormattingRule().replace(CC_STRING, charSequence)));
        } else {
            String nationalPrefixFormattingRule = numberFormat.getNationalPrefixFormattingRule();
            if (phoneNumberFormat == PhoneNumberFormat.NATIONAL && nationalPrefixFormattingRule != null && nationalPrefixFormattingRule.length() > 0) {
                strReplaceAll = matcher.replaceAll(FIRST_GROUP_PATTERN.matcher(format).replaceFirst(nationalPrefixFormattingRule));
            } else {
                strReplaceAll = matcher.replaceAll(format);
            }
        }
        if (phoneNumberFormat != PhoneNumberFormat.RFC3966) {
            return strReplaceAll;
        }
        Matcher matcher2 = SEPARATOR_PATTERN.matcher(strReplaceAll);
        if (matcher2.lookingAt()) {
            strReplaceAll = matcher2.replaceFirst("");
        }
        return matcher2.reset(strReplaceAll).replaceAll("-");
    }

    public Phonenumber.PhoneNumber getExampleNumber(String str) {
        return getExampleNumberForType(str, PhoneNumberType.FIXED_LINE);
    }

    /*  JADX ERROR: NullPointerException in pass: BlockProcessor
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.nodes.BlockNode.getPredecessors()" because "to" is null
        	at jadx.core.dex.visitors.blocks.BlockSplitter.connect(BlockSplitter.java:159)
        	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.connectSplittersAndHandlers(BlockExceptionHandler.java:501)
        	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.wrapBlocksWithTryCatch(BlockExceptionHandler.java:382)
        	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.connectExcHandlers(BlockExceptionHandler.java:91)
        	at jadx.core.dex.visitors.blocks.BlockExceptionHandler.process(BlockExceptionHandler.java:62)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.independentBlockTreeMod(BlockProcessor.java:422)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:57)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:50)
        */
    public com.google.i18n.phonenumbers.Phonenumber.PhoneNumber getInvalidExampleNumber(java.lang.String r6) {
        /*
            r5 = this;
            boolean r0 = r5.isValidRegionCode(r6)
            r1 = 0
            if (r0 != 0) goto L1e
            java.util.logging.Logger r5 = com.google.i18n.phonenumbers.PhoneNumberUtil.logger
            java.util.logging.Level r0 = java.util.logging.Level.WARNING
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Invalid or unknown region code provided: "
            r2.<init>(r3)
            java.lang.StringBuilder r6 = r2.append(r6)
            java.lang.String r6 = r6.toString()
            r5.log(r0, r6)
            return r1
        L1e:
            com.google.i18n.phonenumbers.Phonemetadata$PhoneMetadata r0 = r5.getMetadataForRegion(r6)
            com.google.i18n.phonenumbers.PhoneNumberUtil$PhoneNumberType r2 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.FIXED_LINE
            com.google.i18n.phonenumbers.Phonemetadata$PhoneNumberDesc r0 = r5.getNumberDescByType(r0, r2)
            boolean r2 = r0.hasExampleNumber()
            if (r2 != 0) goto L2f
            return r1
        L2f:
            java.lang.String r0 = r0.getExampleNumber()
            int r2 = r0.length()
            int r2 = r2 + (-1)
        L39:
            r3 = 2
            if (r2 < r3) goto L4f
            r3 = 0
            java.lang.String r3 = r0.substring(r3, r2)
            com.google.i18n.phonenumbers.Phonenumber$PhoneNumber r3 = r5.parse(r3, r6)     // Catch: com.google.i18n.phonenumbers.NumberParseException -> L4c
            boolean r4 = r5.isValidNumber(r3)     // Catch: com.google.i18n.phonenumbers.NumberParseException -> L4c
            if (r4 != 0) goto L4c
            return r3
        L4c:
            int r2 = r2 + (-1)
            goto L39
        L4f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.i18n.phonenumbers.PhoneNumberUtil.getInvalidExampleNumber(java.lang.String):com.google.i18n.phonenumbers.Phonenumber$PhoneNumber");
    }

    public Phonenumber.PhoneNumber getExampleNumberForType(String str, PhoneNumberType phoneNumberType) {
        if (!isValidRegionCode(str)) {
            logger.log(Level.WARNING, "Invalid or unknown region code provided: " + str);
            return null;
        }
        Phonemetadata.PhoneNumberDesc numberDescByType = getNumberDescByType(getMetadataForRegion(str), phoneNumberType);
        try {
            if (numberDescByType.hasExampleNumber()) {
                return parse(numberDescByType.getExampleNumber(), str);
            }
        } catch (NumberParseException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

    public Phonenumber.PhoneNumber getExampleNumberForType(PhoneNumberType phoneNumberType) {
        Iterator<String> it = getSupportedRegions().iterator();
        while (it.hasNext()) {
            Phonenumber.PhoneNumber exampleNumberForType = getExampleNumberForType(it.next(), phoneNumberType);
            if (exampleNumberForType != null) {
                return exampleNumberForType;
            }
        }
        Iterator<Integer> it2 = getSupportedGlobalNetworkCallingCodes().iterator();
        while (it2.hasNext()) {
            int iIntValue = it2.next().intValue();
            Phonemetadata.PhoneNumberDesc numberDescByType = this.getNumberDescByType(this.getMetadataForNonGeographicalRegion(iIntValue), phoneNumberType);
            try {
                if (numberDescByType.hasExampleNumber()) {
                    return this.parse(Marker.ANY_NON_NULL_MARKER + iIntValue + numberDescByType.getExampleNumber(), UNKNOWN_REGION);
                }
                continue;
            } catch (NumberParseException e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
        return null;
    }

    public Phonenumber.PhoneNumber getExampleNumberForNonGeoEntity(int i) {
        Phonemetadata.PhoneMetadata metadataForNonGeographicalRegion = getMetadataForNonGeographicalRegion(i);
        if (metadataForNonGeographicalRegion != null) {
            for (Phonemetadata.PhoneNumberDesc phoneNumberDesc : Arrays.asList(metadataForNonGeographicalRegion.getMobile(), metadataForNonGeographicalRegion.getTollFree(), metadataForNonGeographicalRegion.getSharedCost(), metadataForNonGeographicalRegion.getVoip(), metadataForNonGeographicalRegion.getVoicemail(), metadataForNonGeographicalRegion.getUan(), metadataForNonGeographicalRegion.getPremiumRate())) {
                if (phoneNumberDesc != null) {
                    try {
                        if (phoneNumberDesc.hasExampleNumber()) {
                            return this.parse(Marker.ANY_NON_NULL_MARKER + i + phoneNumberDesc.getExampleNumber(), UNKNOWN_REGION);
                        }
                        continue;
                    } catch (NumberParseException e) {
                        logger.log(Level.SEVERE, e.toString());
                    }
                }
            }
            return null;
        }
        logger.log(Level.WARNING, "Invalid or unknown country calling code provided: " + i);
        return null;
    }

    private void maybeAppendFormattedExtension(Phonenumber.PhoneNumber phoneNumber, Phonemetadata.PhoneMetadata phoneMetadata, PhoneNumberFormat phoneNumberFormat, StringBuilder sb) {
        if (!phoneNumber.hasExtension() || phoneNumber.getExtension().length() <= 0) {
            return;
        }
        if (phoneNumberFormat == PhoneNumberFormat.RFC3966) {
            sb.append(RFC3966_EXTN_PREFIX).append(phoneNumber.getExtension());
        } else if (phoneMetadata.hasPreferredExtnPrefix()) {
            sb.append(phoneMetadata.getPreferredExtnPrefix()).append(phoneNumber.getExtension());
        } else {
            sb.append(DEFAULT_EXTN_PREFIX).append(phoneNumber.getExtension());
        }
    }

    /* JADX INFO: renamed from: com.google.i18n.phonenumbers.PhoneNumberUtil$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberFormat;
        static final /* synthetic */ int[] $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType;
        static final /* synthetic */ int[] $SwitchMap$com$google$i18n$phonenumbers$Phonenumber$PhoneNumber$CountryCodeSource;

        static {
            int[] iArr = new int[PhoneNumberType.values().length];
            $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType = iArr;
            try {
                iArr[PhoneNumberType.PREMIUM_RATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.TOLL_FREE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.MOBILE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.FIXED_LINE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.FIXED_LINE_OR_MOBILE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.SHARED_COST.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.VOIP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.PERSONAL_NUMBER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.PAGER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.UAN.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[PhoneNumberType.VOICEMAIL.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr2 = new int[PhoneNumberFormat.values().length];
            $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberFormat = iArr2;
            try {
                iArr2[PhoneNumberFormat.E164.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberFormat[PhoneNumberFormat.INTERNATIONAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberFormat[PhoneNumberFormat.RFC3966.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberFormat[PhoneNumberFormat.NATIONAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused15) {
            }
            int[] iArr3 = new int[Phonenumber.PhoneNumber.CountryCodeSource.values().length];
            $SwitchMap$com$google$i18n$phonenumbers$Phonenumber$PhoneNumber$CountryCodeSource = iArr3;
            try {
                iArr3[Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN.ordinal()] = 1;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$Phonenumber$PhoneNumber$CountryCodeSource[Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_IDD.ordinal()] = 2;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$Phonenumber$PhoneNumber$CountryCodeSource[Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN.ordinal()] = 3;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$google$i18n$phonenumbers$Phonenumber$PhoneNumber$CountryCodeSource[Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY.ordinal()] = 4;
            } catch (NoSuchFieldError unused19) {
            }
        }
    }

    Phonemetadata.PhoneNumberDesc getNumberDescByType(Phonemetadata.PhoneMetadata phoneMetadata, PhoneNumberType phoneNumberType) {
        switch (AnonymousClass2.$SwitchMap$com$google$i18n$phonenumbers$PhoneNumberUtil$PhoneNumberType[phoneNumberType.ordinal()]) {
            case 1:
                return phoneMetadata.getPremiumRate();
            case 2:
                return phoneMetadata.getTollFree();
            case 3:
                return phoneMetadata.getMobile();
            case 4:
            case 5:
                return phoneMetadata.getFixedLine();
            case 6:
                return phoneMetadata.getSharedCost();
            case 7:
                return phoneMetadata.getVoip();
            case 8:
                return phoneMetadata.getPersonalNumber();
            case 9:
                return phoneMetadata.getPager();
            case 10:
                return phoneMetadata.getUan();
            case 11:
                return phoneMetadata.getVoicemail();
            default:
                return phoneMetadata.getGeneralDesc();
        }
    }

    public PhoneNumberType getNumberType(Phonenumber.PhoneNumber phoneNumber) {
        Phonemetadata.PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(phoneNumber.getCountryCode(), getRegionCodeForNumber(phoneNumber));
        if (metadataForRegionOrCallingCode == null) {
            return PhoneNumberType.UNKNOWN;
        }
        return getNumberTypeHelper(getNationalSignificantNumber(phoneNumber), metadataForRegionOrCallingCode);
    }

    private PhoneNumberType getNumberTypeHelper(String str, Phonemetadata.PhoneMetadata phoneMetadata) {
        if (!isNumberMatchingDesc(str, phoneMetadata.getGeneralDesc())) {
            return PhoneNumberType.UNKNOWN;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getPremiumRate())) {
            return PhoneNumberType.PREMIUM_RATE;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getTollFree())) {
            return PhoneNumberType.TOLL_FREE;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getSharedCost())) {
            return PhoneNumberType.SHARED_COST;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getVoip())) {
            return PhoneNumberType.VOIP;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getPersonalNumber())) {
            return PhoneNumberType.PERSONAL_NUMBER;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getPager())) {
            return PhoneNumberType.PAGER;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getUan())) {
            return PhoneNumberType.UAN;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getVoicemail())) {
            return PhoneNumberType.VOICEMAIL;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getFixedLine())) {
            if (phoneMetadata.getSameMobileAndFixedLinePattern()) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            if (isNumberMatchingDesc(str, phoneMetadata.getMobile())) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            return PhoneNumberType.FIXED_LINE;
        }
        if (!phoneMetadata.getSameMobileAndFixedLinePattern() && isNumberMatchingDesc(str, phoneMetadata.getMobile())) {
            return PhoneNumberType.MOBILE;
        }
        return PhoneNumberType.UNKNOWN;
    }

    Phonemetadata.PhoneMetadata getMetadataForRegion(String str) {
        if (isValidRegionCode(str)) {
            return this.metadataSource.getMetadataForRegion(str);
        }
        return null;
    }

    Phonemetadata.PhoneMetadata getMetadataForNonGeographicalRegion(int i) {
        if (this.countryCallingCodeToRegionCodeMap.containsKey(Integer.valueOf(i))) {
            return this.metadataSource.getMetadataForNonGeographicalRegion(i);
        }
        return null;
    }

    boolean isNumberMatchingDesc(String str, Phonemetadata.PhoneNumberDesc phoneNumberDesc) {
        int length = str.length();
        List<Integer> possibleLengthList = phoneNumberDesc.getPossibleLengthList();
        if (possibleLengthList.size() <= 0 || possibleLengthList.contains(Integer.valueOf(length))) {
            return this.matcherApi.matchNationalNumber(str, phoneNumberDesc, false);
        }
        return false;
    }

    public boolean isValidNumber(Phonenumber.PhoneNumber phoneNumber) {
        return isValidNumberForRegion(phoneNumber, getRegionCodeForNumber(phoneNumber));
    }

    public boolean isValidNumberForRegion(Phonenumber.PhoneNumber phoneNumber, String str) {
        int countryCode = phoneNumber.getCountryCode();
        Phonemetadata.PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, str);
        return metadataForRegionOrCallingCode != null && (REGION_CODE_FOR_NON_GEO_ENTITY.equals(str) || countryCode == getCountryCodeForValidRegion(str)) && getNumberTypeHelper(getNationalSignificantNumber(phoneNumber), metadataForRegionOrCallingCode) != PhoneNumberType.UNKNOWN;
    }

    public String getRegionCodeForNumber(Phonenumber.PhoneNumber phoneNumber) {
        int countryCode = phoneNumber.getCountryCode();
        List<String> list = this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf(countryCode));
        if (list == null) {
            logger.log(Level.INFO, "Missing/invalid country_code (" + countryCode + ")");
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        return getRegionCodeForNumberFromRegionList(phoneNumber, list);
    }

    private String getRegionCodeForNumberFromRegionList(Phonenumber.PhoneNumber phoneNumber, List<String> list) {
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        for (String str : list) {
            Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(str);
            if (metadataForRegion.hasLeadingDigits()) {
                if (this.regexCache.getPatternForRegex(metadataForRegion.getLeadingDigits()).matcher(nationalSignificantNumber).lookingAt()) {
                    return str;
                }
            } else if (getNumberTypeHelper(nationalSignificantNumber, metadataForRegion) != PhoneNumberType.UNKNOWN) {
                return str;
            }
        }
        return null;
    }

    public String getRegionCodeForCountryCode(int i) {
        List<String> list = this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf(i));
        return list == null ? UNKNOWN_REGION : list.get(0);
    }

    public List<String> getRegionCodesForCountryCode(int i) {
        List<String> arrayList = this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf(i));
        if (arrayList == null) {
            arrayList = new ArrayList<>(0);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public int getCountryCodeForRegion(String str) {
        if (!isValidRegionCode(str)) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            StringBuilder sb = new StringBuilder("Invalid or missing region code (");
            if (str == null) {
                str = "null";
            }
            logger2.log(level, sb.append(str).append(") provided.").toString());
            return 0;
        }
        return getCountryCodeForValidRegion(str);
    }

    private int getCountryCodeForValidRegion(String str) {
        Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(str);
        if (metadataForRegion == null) {
            throw new IllegalArgumentException("Invalid region code: " + str);
        }
        return metadataForRegion.getCountryCode();
    }

    public String getNddPrefixForRegion(String str, boolean z) {
        Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(str);
        if (metadataForRegion == null) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            StringBuilder sb = new StringBuilder("Invalid or missing region code (");
            if (str == null) {
                str = "null";
            }
            logger2.log(level, sb.append(str).append(") provided.").toString());
            return null;
        }
        String nationalPrefix = metadataForRegion.getNationalPrefix();
        if (nationalPrefix.length() == 0) {
            return null;
        }
        return z ? nationalPrefix.replace("~", "") : nationalPrefix;
    }

    public boolean isNANPACountry(String str) {
        return this.nanpaRegions.contains(str);
    }

    public boolean isAlphaNumber(CharSequence charSequence) {
        if (!isViablePhoneNumber(charSequence)) {
            return false;
        }
        StringBuilder sb = new StringBuilder(charSequence);
        maybeStripExtension(sb);
        return VALID_ALPHA_PHONE_PATTERN.matcher(sb).matches();
    }

    public boolean isPossibleNumber(Phonenumber.PhoneNumber phoneNumber) {
        ValidationResult validationResultIsPossibleNumberWithReason = isPossibleNumberWithReason(phoneNumber);
        return validationResultIsPossibleNumberWithReason == ValidationResult.IS_POSSIBLE || validationResultIsPossibleNumberWithReason == ValidationResult.IS_POSSIBLE_LOCAL_ONLY;
    }

    public boolean isPossibleNumberForType(Phonenumber.PhoneNumber phoneNumber, PhoneNumberType phoneNumberType) {
        ValidationResult validationResultIsPossibleNumberForTypeWithReason = isPossibleNumberForTypeWithReason(phoneNumber, phoneNumberType);
        return validationResultIsPossibleNumberForTypeWithReason == ValidationResult.IS_POSSIBLE || validationResultIsPossibleNumberForTypeWithReason == ValidationResult.IS_POSSIBLE_LOCAL_ONLY;
    }

    private ValidationResult testNumberLength(CharSequence charSequence, Phonemetadata.PhoneMetadata phoneMetadata) {
        return testNumberLength(charSequence, phoneMetadata, PhoneNumberType.UNKNOWN);
    }

    private ValidationResult testNumberLength(CharSequence charSequence, Phonemetadata.PhoneMetadata phoneMetadata, PhoneNumberType phoneNumberType) {
        List<Integer> possibleLengthList;
        Phonemetadata.PhoneNumberDesc numberDescByType = getNumberDescByType(phoneMetadata, phoneNumberType);
        List<Integer> possibleLengthList2 = numberDescByType.getPossibleLengthList().isEmpty() ? phoneMetadata.getGeneralDesc().getPossibleLengthList() : numberDescByType.getPossibleLengthList();
        List<Integer> possibleLengthLocalOnlyList = numberDescByType.getPossibleLengthLocalOnlyList();
        if (phoneNumberType == PhoneNumberType.FIXED_LINE_OR_MOBILE) {
            if (!descHasPossibleNumberData(getNumberDescByType(phoneMetadata, PhoneNumberType.FIXED_LINE))) {
                return testNumberLength(charSequence, phoneMetadata, PhoneNumberType.MOBILE);
            }
            Phonemetadata.PhoneNumberDesc numberDescByType2 = getNumberDescByType(phoneMetadata, PhoneNumberType.MOBILE);
            if (descHasPossibleNumberData(numberDescByType2)) {
                ArrayList arrayList = new ArrayList(possibleLengthList2);
                if (numberDescByType2.getPossibleLengthCount() == 0) {
                    possibleLengthList = phoneMetadata.getGeneralDesc().getPossibleLengthList();
                } else {
                    possibleLengthList = numberDescByType2.getPossibleLengthList();
                }
                arrayList.addAll(possibleLengthList);
                Collections.sort(arrayList);
                if (possibleLengthLocalOnlyList.isEmpty()) {
                    possibleLengthLocalOnlyList = numberDescByType2.getPossibleLengthLocalOnlyList();
                } else {
                    ArrayList arrayList2 = new ArrayList(possibleLengthLocalOnlyList);
                    arrayList2.addAll(numberDescByType2.getPossibleLengthLocalOnlyList());
                    Collections.sort(arrayList2);
                    possibleLengthLocalOnlyList = arrayList2;
                }
                possibleLengthList2 = arrayList;
            }
        }
        if (possibleLengthList2.get(0).intValue() == -1) {
            return ValidationResult.INVALID_LENGTH;
        }
        int length = charSequence.length();
        if (possibleLengthLocalOnlyList.contains(Integer.valueOf(length))) {
            return ValidationResult.IS_POSSIBLE_LOCAL_ONLY;
        }
        int iIntValue = possibleLengthList2.get(0).intValue();
        if (iIntValue == length) {
            return ValidationResult.IS_POSSIBLE;
        }
        if (iIntValue > length) {
            return ValidationResult.TOO_SHORT;
        }
        if (possibleLengthList2.get(possibleLengthList2.size() - 1).intValue() < length) {
            return ValidationResult.TOO_LONG;
        }
        return possibleLengthList2.subList(1, possibleLengthList2.size()).contains(Integer.valueOf(length)) ? ValidationResult.IS_POSSIBLE : ValidationResult.INVALID_LENGTH;
    }

    public ValidationResult isPossibleNumberWithReason(Phonenumber.PhoneNumber phoneNumber) {
        return isPossibleNumberForTypeWithReason(phoneNumber, PhoneNumberType.UNKNOWN);
    }

    public ValidationResult isPossibleNumberForTypeWithReason(Phonenumber.PhoneNumber phoneNumber, PhoneNumberType phoneNumberType) {
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        int countryCode = phoneNumber.getCountryCode();
        if (!hasValidCountryCallingCode(countryCode)) {
            return ValidationResult.INVALID_COUNTRY_CODE;
        }
        return testNumberLength(nationalSignificantNumber, getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode)), phoneNumberType);
    }

    public boolean isPossibleNumber(CharSequence charSequence, String str) {
        try {
            return isPossibleNumber(parse(charSequence, str));
        } catch (NumberParseException unused) {
            return false;
        }
    }

    public boolean truncateTooLongNumber(Phonenumber.PhoneNumber phoneNumber) {
        if (isValidNumber(phoneNumber)) {
            return true;
        }
        Phonenumber.PhoneNumber phoneNumber2 = new Phonenumber.PhoneNumber();
        phoneNumber2.mergeFrom(phoneNumber);
        long nationalNumber = phoneNumber.getNationalNumber();
        do {
            nationalNumber /= 10;
            phoneNumber2.setNationalNumber(nationalNumber);
            if (isPossibleNumberWithReason(phoneNumber2) == ValidationResult.TOO_SHORT || nationalNumber == 0) {
                return false;
            }
        } while (!isValidNumber(phoneNumber2));
        phoneNumber.setNationalNumber(nationalNumber);
        return true;
    }

    public AsYouTypeFormatter getAsYouTypeFormatter(String str) {
        return new AsYouTypeFormatter(str);
    }

    int extractCountryCode(StringBuilder sb, StringBuilder sb2) {
        if (sb.length() != 0 && sb.charAt(0) != '0') {
            int length = sb.length();
            for (int i = 1; i <= 3 && i <= length; i++) {
                int i2 = Integer.parseInt(sb.substring(0, i));
                if (this.countryCallingCodeToRegionCodeMap.containsKey(Integer.valueOf(i2))) {
                    sb2.append(sb.substring(i));
                    return i2;
                }
            }
        }
        return 0;
    }

    int maybeExtractCountryCode(CharSequence charSequence, Phonemetadata.PhoneMetadata phoneMetadata, StringBuilder sb, boolean z, Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        String internationalPrefix;
        if (charSequence.length() == 0) {
            return 0;
        }
        StringBuilder sb2 = new StringBuilder(charSequence);
        if (phoneMetadata == null) {
            internationalPrefix = "NonMatch";
        } else {
            internationalPrefix = phoneMetadata.getInternationalPrefix();
        }
        Phonenumber.PhoneNumber.CountryCodeSource countryCodeSourceMaybeStripInternationalPrefixAndNormalize = maybeStripInternationalPrefixAndNormalize(sb2, internationalPrefix);
        if (z) {
            phoneNumber.setCountryCodeSource(countryCodeSourceMaybeStripInternationalPrefixAndNormalize);
        }
        if (countryCodeSourceMaybeStripInternationalPrefixAndNormalize != Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            if (sb2.length() <= 2) {
                throw new NumberParseException(NumberParseException.ErrorType.TOO_SHORT_AFTER_IDD, "Phone number had an IDD, but after this was not long enough to be a viable phone number.");
            }
            int iExtractCountryCode = extractCountryCode(sb2, sb);
            if (iExtractCountryCode != 0) {
                phoneNumber.setCountryCode(iExtractCountryCode);
                return iExtractCountryCode;
            }
            throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "Country calling code supplied was not recognised.");
        }
        if (phoneMetadata != null) {
            int countryCode = phoneMetadata.getCountryCode();
            String strValueOf = String.valueOf(countryCode);
            String string = sb2.toString();
            if (string.startsWith(strValueOf)) {
                StringBuilder sb3 = new StringBuilder(string.substring(strValueOf.length()));
                Phonemetadata.PhoneNumberDesc generalDesc = phoneMetadata.getGeneralDesc();
                maybeStripNationalPrefixAndCarrierCode(sb3, phoneMetadata, null);
                if ((!this.matcherApi.matchNationalNumber(sb2, generalDesc, false) && this.matcherApi.matchNationalNumber(sb3, generalDesc, false)) || testNumberLength(sb2, phoneMetadata) == ValidationResult.TOO_LONG) {
                    sb.append((CharSequence) sb3);
                    if (z) {
                        phoneNumber.setCountryCodeSource(Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN);
                    }
                    phoneNumber.setCountryCode(countryCode);
                    return countryCode;
                }
            }
        }
        phoneNumber.setCountryCode(0);
        return 0;
    }

    private boolean parsePrefixAsIdd(Pattern pattern, StringBuilder sb) {
        Matcher matcher = pattern.matcher(sb);
        if (!matcher.lookingAt()) {
            return false;
        }
        int iEnd = matcher.end();
        Matcher matcher2 = CAPTURING_DIGIT_PATTERN.matcher(sb.substring(iEnd));
        if (matcher2.find() && normalizeDigitsOnly(matcher2.group(1)).equals("0")) {
            return false;
        }
        sb.delete(0, iEnd);
        return true;
    }

    Phonenumber.PhoneNumber.CountryCodeSource maybeStripInternationalPrefixAndNormalize(StringBuilder sb, String str) {
        if (sb.length() == 0) {
            return Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY;
        }
        Matcher matcher = PLUS_CHARS_PATTERN.matcher(sb);
        if (matcher.lookingAt()) {
            sb.delete(0, matcher.end());
            normalize(sb);
            return Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;
        }
        Pattern patternForRegex = this.regexCache.getPatternForRegex(str);
        normalize(sb);
        return parsePrefixAsIdd(patternForRegex, sb) ? Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_IDD : Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY;
    }

    boolean maybeStripNationalPrefixAndCarrierCode(StringBuilder sb, Phonemetadata.PhoneMetadata phoneMetadata, StringBuilder sb2) {
        int length = sb.length();
        String nationalPrefixForParsing = phoneMetadata.getNationalPrefixForParsing();
        if (length != 0 && nationalPrefixForParsing.length() != 0) {
            Matcher matcher = this.regexCache.getPatternForRegex(nationalPrefixForParsing).matcher(sb);
            if (matcher.lookingAt()) {
                Phonemetadata.PhoneNumberDesc generalDesc = phoneMetadata.getGeneralDesc();
                boolean zMatchNationalNumber = this.matcherApi.matchNationalNumber(sb, generalDesc, false);
                int iGroupCount = matcher.groupCount();
                String nationalPrefixTransformRule = phoneMetadata.getNationalPrefixTransformRule();
                if (nationalPrefixTransformRule == null || nationalPrefixTransformRule.length() == 0 || matcher.group(iGroupCount) == null) {
                    if (zMatchNationalNumber && !this.matcherApi.matchNationalNumber(sb.substring(matcher.end()), generalDesc, false)) {
                        return false;
                    }
                    if (sb2 != null && iGroupCount > 0 && matcher.group(iGroupCount) != null) {
                        sb2.append(matcher.group(1));
                    }
                    sb.delete(0, matcher.end());
                    return true;
                }
                StringBuilder sb3 = new StringBuilder(sb);
                sb3.replace(0, length, matcher.replaceFirst(nationalPrefixTransformRule));
                if (zMatchNationalNumber && !this.matcherApi.matchNationalNumber(sb3.toString(), generalDesc, false)) {
                    return false;
                }
                if (sb2 != null && iGroupCount > 1) {
                    sb2.append(matcher.group(1));
                }
                sb.replace(0, sb.length(), sb3.toString());
                return true;
            }
        }
        return false;
    }

    String maybeStripExtension(StringBuilder sb) {
        Matcher matcher = EXTN_PATTERN.matcher(sb);
        if (matcher.find() && isViablePhoneNumber(sb.substring(0, matcher.start()))) {
            int iGroupCount = matcher.groupCount();
            for (int i = 1; i <= iGroupCount; i++) {
                if (matcher.group(i) != null) {
                    String strGroup = matcher.group(i);
                    sb.delete(matcher.start(), sb.length());
                    return strGroup;
                }
            }
            return "";
        }
        return "";
    }

    private boolean checkRegionForParsing(CharSequence charSequence, String str) {
        if (isValidRegionCode(str)) {
            return true;
        }
        return (charSequence == null || charSequence.length() == 0 || !PLUS_CHARS_PATTERN.matcher(charSequence).lookingAt()) ? false : true;
    }

    public Phonenumber.PhoneNumber parse(CharSequence charSequence, String str) throws NumberParseException {
        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
        parse(charSequence, str, phoneNumber);
        return phoneNumber;
    }

    public void parse(CharSequence charSequence, String str, Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        parseHelper(charSequence, str, false, true, phoneNumber);
    }

    public Phonenumber.PhoneNumber parseAndKeepRawInput(CharSequence charSequence, String str) throws NumberParseException {
        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
        parseAndKeepRawInput(charSequence, str, phoneNumber);
        return phoneNumber;
    }

    public void parseAndKeepRawInput(CharSequence charSequence, String str, Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        parseHelper(charSequence, str, true, true, phoneNumber);
    }

    public Iterable<PhoneNumberMatch> findNumbers(CharSequence charSequence, String str) {
        return findNumbers(charSequence, str, Leniency.VALID, Long.MAX_VALUE);
    }

    public Iterable<PhoneNumberMatch> findNumbers(final CharSequence charSequence, final String str, final Leniency leniency, final long j) {
        return new Iterable<PhoneNumberMatch>() { // from class: com.google.i18n.phonenumbers.PhoneNumberUtil.1
            @Override // java.lang.Iterable
            public Iterator<PhoneNumberMatch> iterator() {
                return new PhoneNumberMatcher(PhoneNumberUtil.this, charSequence, str, leniency, j);
            }
        };
    }

    static void setItalianLeadingZerosForPhoneNumber(CharSequence charSequence, Phonenumber.PhoneNumber phoneNumber) {
        if (charSequence.length() <= 1 || charSequence.charAt(0) != '0') {
            return;
        }
        phoneNumber.setItalianLeadingZero(true);
        int i = 1;
        while (i < charSequence.length() - 1 && charSequence.charAt(i) == '0') {
            i++;
        }
        if (i != 1) {
            phoneNumber.setNumberOfLeadingZeros(i);
        }
    }

    private void parseHelper(CharSequence charSequence, String str, boolean z, boolean z2, Phonenumber.PhoneNumber phoneNumber) throws NumberParseException {
        int iMaybeExtractCountryCode;
        if (charSequence == null) {
            throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "The phone number supplied was null.");
        }
        if (charSequence.length() > 250) {
            throw new NumberParseException(NumberParseException.ErrorType.TOO_LONG, "The string supplied was too long to parse.");
        }
        StringBuilder sb = new StringBuilder();
        String string = charSequence.toString();
        buildNationalNumberForParsing(string, sb);
        if (!isViablePhoneNumber(sb)) {
            throw new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "The string supplied did not seem to be a phone number.");
        }
        if (z2 && !checkRegionForParsing(sb, str)) {
            throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "Missing or invalid default region.");
        }
        if (z) {
            phoneNumber.setRawInput(string);
        }
        String strMaybeStripExtension = maybeStripExtension(sb);
        if (strMaybeStripExtension.length() > 0) {
            phoneNumber.setExtension(strMaybeStripExtension);
        }
        Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(str);
        StringBuilder sb2 = new StringBuilder();
        boolean z3 = z;
        Phonenumber.PhoneNumber phoneNumber2 = phoneNumber;
        try {
            iMaybeExtractCountryCode = maybeExtractCountryCode(sb, metadataForRegion, sb2, z3, phoneNumber2);
        } catch (NumberParseException e) {
            Matcher matcher = PLUS_CHARS_PATTERN.matcher(sb);
            if (e.getErrorType() == NumberParseException.ErrorType.INVALID_COUNTRY_CODE && matcher.lookingAt()) {
                iMaybeExtractCountryCode = maybeExtractCountryCode(sb.substring(matcher.end()), metadataForRegion, sb2, z3, phoneNumber2);
                metadataForRegion = metadataForRegion;
                sb2 = sb2;
                z3 = z3;
                phoneNumber2 = phoneNumber2;
                if (iMaybeExtractCountryCode == 0) {
                    throw new NumberParseException(NumberParseException.ErrorType.INVALID_COUNTRY_CODE, "Could not interpret numbers after plus-sign.");
                }
            } else {
                throw new NumberParseException(e.getErrorType(), e.getMessage());
            }
        }
        if (iMaybeExtractCountryCode != 0) {
            String regionCodeForCountryCode = getRegionCodeForCountryCode(iMaybeExtractCountryCode);
            if (!regionCodeForCountryCode.equals(str)) {
                metadataForRegion = getMetadataForRegionOrCallingCode(iMaybeExtractCountryCode, regionCodeForCountryCode);
            }
        } else {
            sb2.append((CharSequence) normalize(sb));
            if (str != null) {
                phoneNumber2.setCountryCode(metadataForRegion.getCountryCode());
            } else if (z3) {
                phoneNumber2.clearCountryCodeSource();
            }
        }
        if (sb2.length() < 2) {
            throw new NumberParseException(NumberParseException.ErrorType.TOO_SHORT_NSN, "The string supplied is too short to be a phone number.");
        }
        if (metadataForRegion != null) {
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder(sb2);
            maybeStripNationalPrefixAndCarrierCode(sb4, metadataForRegion, sb3);
            ValidationResult validationResultTestNumberLength = testNumberLength(sb4, metadataForRegion);
            if (validationResultTestNumberLength != ValidationResult.TOO_SHORT && validationResultTestNumberLength != ValidationResult.IS_POSSIBLE_LOCAL_ONLY && validationResultTestNumberLength != ValidationResult.INVALID_LENGTH) {
                if (z3 && sb3.length() > 0) {
                    phoneNumber2.setPreferredDomesticCarrierCode(sb3.toString());
                }
                sb2 = sb4;
            }
        }
        int length = sb2.length();
        if (length < 2) {
            throw new NumberParseException(NumberParseException.ErrorType.TOO_SHORT_NSN, "The string supplied is too short to be a phone number.");
        }
        if (length > 17) {
            throw new NumberParseException(NumberParseException.ErrorType.TOO_LONG, "The string supplied is too long to be a phone number.");
        }
        setItalianLeadingZerosForPhoneNumber(sb2, phoneNumber2);
        phoneNumber2.setNationalNumber(Long.parseLong(sb2.toString()));
    }

    private void buildNationalNumberForParsing(String str, StringBuilder sb) {
        int iIndexOf = str.indexOf(RFC3966_PHONE_CONTEXT);
        if (iIndexOf >= 0) {
            int length = RFC3966_PHONE_CONTEXT.length() + iIndexOf;
            if (length < str.length() - 1 && str.charAt(length) == '+') {
                int iIndexOf2 = str.indexOf(59, length);
                if (iIndexOf2 > 0) {
                    sb.append(str.substring(length, iIndexOf2));
                } else {
                    sb.append(str.substring(length));
                }
            }
            int iIndexOf3 = str.indexOf("tel:");
            sb.append(str.substring(iIndexOf3 >= 0 ? iIndexOf3 + "tel:".length() : 0, iIndexOf));
        } else {
            sb.append(extractPossibleNumber(str));
        }
        int iIndexOf4 = sb.indexOf(RFC3966_ISDN_SUBADDRESS);
        if (iIndexOf4 > 0) {
            sb.delete(iIndexOf4, sb.length());
        }
    }

    private static Phonenumber.PhoneNumber copyCoreFieldsOnly(Phonenumber.PhoneNumber phoneNumber) {
        Phonenumber.PhoneNumber phoneNumber2 = new Phonenumber.PhoneNumber();
        phoneNumber2.setCountryCode(phoneNumber.getCountryCode());
        phoneNumber2.setNationalNumber(phoneNumber.getNationalNumber());
        if (phoneNumber.getExtension().length() > 0) {
            phoneNumber2.setExtension(phoneNumber.getExtension());
        }
        if (phoneNumber.isItalianLeadingZero()) {
            phoneNumber2.setItalianLeadingZero(true);
            phoneNumber2.setNumberOfLeadingZeros(phoneNumber.getNumberOfLeadingZeros());
        }
        return phoneNumber2;
    }

    public MatchType isNumberMatch(Phonenumber.PhoneNumber phoneNumber, Phonenumber.PhoneNumber phoneNumber2) {
        Phonenumber.PhoneNumber phoneNumberCopyCoreFieldsOnly = copyCoreFieldsOnly(phoneNumber);
        Phonenumber.PhoneNumber phoneNumberCopyCoreFieldsOnly2 = copyCoreFieldsOnly(phoneNumber2);
        if (phoneNumberCopyCoreFieldsOnly.hasExtension() && phoneNumberCopyCoreFieldsOnly2.hasExtension() && !phoneNumberCopyCoreFieldsOnly.getExtension().equals(phoneNumberCopyCoreFieldsOnly2.getExtension())) {
            return MatchType.NO_MATCH;
        }
        int countryCode = phoneNumberCopyCoreFieldsOnly.getCountryCode();
        int countryCode2 = phoneNumberCopyCoreFieldsOnly2.getCountryCode();
        if (countryCode != 0 && countryCode2 != 0) {
            if (phoneNumberCopyCoreFieldsOnly.exactlySameAs(phoneNumberCopyCoreFieldsOnly2)) {
                return MatchType.EXACT_MATCH;
            }
            if (countryCode == countryCode2 && isNationalNumberSuffixOfTheOther(phoneNumberCopyCoreFieldsOnly, phoneNumberCopyCoreFieldsOnly2)) {
                return MatchType.SHORT_NSN_MATCH;
            }
            return MatchType.NO_MATCH;
        }
        phoneNumberCopyCoreFieldsOnly.setCountryCode(countryCode2);
        if (phoneNumberCopyCoreFieldsOnly.exactlySameAs(phoneNumberCopyCoreFieldsOnly2)) {
            return MatchType.NSN_MATCH;
        }
        if (isNationalNumberSuffixOfTheOther(phoneNumberCopyCoreFieldsOnly, phoneNumberCopyCoreFieldsOnly2)) {
            return MatchType.SHORT_NSN_MATCH;
        }
        return MatchType.NO_MATCH;
    }

    private boolean isNationalNumberSuffixOfTheOther(Phonenumber.PhoneNumber phoneNumber, Phonenumber.PhoneNumber phoneNumber2) {
        String strValueOf = String.valueOf(phoneNumber.getNationalNumber());
        String strValueOf2 = String.valueOf(phoneNumber2.getNationalNumber());
        return strValueOf.endsWith(strValueOf2) || strValueOf2.endsWith(strValueOf);
    }

    public MatchType isNumberMatch(CharSequence charSequence, CharSequence charSequence2) {
        try {
            return isNumberMatch(parse(charSequence, UNKNOWN_REGION), charSequence2);
        } catch (NumberParseException e) {
            if (e.getErrorType() == NumberParseException.ErrorType.INVALID_COUNTRY_CODE) {
                try {
                    return isNumberMatch(parse(charSequence2, UNKNOWN_REGION), charSequence);
                } catch (NumberParseException e2) {
                    if (e2.getErrorType() == NumberParseException.ErrorType.INVALID_COUNTRY_CODE) {
                        try {
                            Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
                            Phonenumber.PhoneNumber phoneNumber2 = new Phonenumber.PhoneNumber();
                            parseHelper(charSequence, null, false, false, phoneNumber);
                            parseHelper(charSequence2, null, false, false, phoneNumber2);
                            return isNumberMatch(phoneNumber, phoneNumber2);
                        } catch (NumberParseException unused) {
                            return MatchType.NOT_A_NUMBER;
                        }
                    }
                    return MatchType.NOT_A_NUMBER;
                }
            }
            return MatchType.NOT_A_NUMBER;
        }
    }

    public MatchType isNumberMatch(Phonenumber.PhoneNumber phoneNumber, CharSequence charSequence) {
        try {
            return isNumberMatch(phoneNumber, parse(charSequence, UNKNOWN_REGION));
        } catch (NumberParseException e) {
            if (e.getErrorType() == NumberParseException.ErrorType.INVALID_COUNTRY_CODE) {
                String regionCodeForCountryCode = getRegionCodeForCountryCode(phoneNumber.getCountryCode());
                try {
                    if (!regionCodeForCountryCode.equals(UNKNOWN_REGION)) {
                        MatchType matchTypeIsNumberMatch = isNumberMatch(phoneNumber, parse(charSequence, regionCodeForCountryCode));
                        return matchTypeIsNumberMatch == MatchType.EXACT_MATCH ? MatchType.NSN_MATCH : matchTypeIsNumberMatch;
                    }
                    Phonenumber.PhoneNumber phoneNumber2 = new Phonenumber.PhoneNumber();
                    parseHelper(charSequence, null, false, false, phoneNumber2);
                    return isNumberMatch(phoneNumber, phoneNumber2);
                } catch (NumberParseException unused) {
                    return MatchType.NOT_A_NUMBER;
                }
            }
            return MatchType.NOT_A_NUMBER;
        }
    }

    public boolean canBeInternationallyDialled(Phonenumber.PhoneNumber phoneNumber) {
        Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(getRegionCodeForNumber(phoneNumber));
        if (metadataForRegion == null) {
            return true;
        }
        return !isNumberMatchingDesc(getNationalSignificantNumber(phoneNumber), metadataForRegion.getNoInternationalDialling());
    }

    public boolean isMobileNumberPortableRegion(String str) {
        Phonemetadata.PhoneMetadata metadataForRegion = getMetadataForRegion(str);
        if (metadataForRegion == null) {
            logger.log(Level.WARNING, "Invalid or unknown region code provided: " + str);
            return false;
        }
        return metadataForRegion.getMobileNumberPortableRegion();
    }
}
