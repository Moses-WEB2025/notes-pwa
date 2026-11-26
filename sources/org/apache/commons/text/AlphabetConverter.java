package org.apache.commons.text;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes6.dex */
public final class AlphabetConverter {
    private static final String ARROW = " -> ";
    private final int encodedLetterLength;
    private final Map<String, String> encodedToOriginal;
    private final Map<Integer, String> originalToEncoded;

    private static String codePointToString(int i) {
        if (Character.charCount(i) == 1) {
            return String.valueOf((char) i);
        }
        return new String(Character.toChars(i));
    }

    private static Integer[] convertCharsToIntegers(final Character[] chArr) {
        if (ArrayUtils.isEmpty(chArr)) {
            return ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY;
        }
        Integer[] numArr = new Integer[chArr.length];
        Arrays.setAll(numArr, new IntFunction() { // from class: org.apache.commons.text.AlphabetConverter$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return Integer.valueOf(chArr[i].charValue());
            }
        });
        return numArr;
    }

    public static AlphabetConverter createConverter(Integer[] numArr, Integer[] numArr2, Integer[] numArr3) {
        Integer num;
        LinkedHashSet linkedHashSet = new LinkedHashSet(Arrays.asList(numArr));
        LinkedHashSet linkedHashSet2 = new LinkedHashSet(Arrays.asList(numArr2));
        LinkedHashSet linkedHashSet3 = new LinkedHashSet(Arrays.asList(numArr3));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        HashMap map = new HashMap();
        Iterator it = linkedHashSet3.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            if (!linkedHashSet.contains(Integer.valueOf(iIntValue))) {
                throw new IllegalArgumentException("Can not use 'do not encode' list because original alphabet does not contain '" + codePointToString(iIntValue) + "'");
            }
            if (!linkedHashSet2.contains(Integer.valueOf(iIntValue))) {
                throw new IllegalArgumentException("Can not use 'do not encode' list because encoding alphabet does not contain '" + codePointToString(iIntValue) + "'");
            }
            map.put(Integer.valueOf(iIntValue), codePointToString(iIntValue));
        }
        if (linkedHashSet2.size() >= linkedHashSet.size()) {
            Iterator it2 = linkedHashSet2.iterator();
            Iterator it3 = linkedHashSet.iterator();
            while (it3.hasNext()) {
                int iIntValue2 = ((Integer) it3.next()).intValue();
                String strCodePointToString = codePointToString(iIntValue2);
                if (map.containsKey(Integer.valueOf(iIntValue2))) {
                    linkedHashMap.put(Integer.valueOf(iIntValue2), strCodePointToString);
                    linkedHashMap2.put(strCodePointToString, strCodePointToString);
                } else {
                    Object next = it2.next();
                    while (true) {
                        num = (Integer) next;
                        if (!linkedHashSet3.contains(num)) {
                            break;
                        }
                        next = it2.next();
                    }
                    String strCodePointToString2 = codePointToString(num.intValue());
                    linkedHashMap.put(Integer.valueOf(iIntValue2), strCodePointToString2);
                    linkedHashMap2.put(strCodePointToString2, strCodePointToString);
                }
            }
            return new AlphabetConverter(linkedHashMap, linkedHashMap2, 1);
        }
        if (linkedHashSet2.size() - linkedHashSet3.size() < 2) {
            throw new IllegalArgumentException("Must have at least two encoding characters (excluding those in the 'do not encode' list), but has " + (linkedHashSet2.size() - linkedHashSet3.size()));
        }
        int size = (linkedHashSet.size() - linkedHashSet3.size()) / (linkedHashSet2.size() - linkedHashSet3.size());
        int i = 1;
        while (size / linkedHashSet2.size() >= 1) {
            size /= linkedHashSet2.size();
            i++;
        }
        int i2 = i + 1;
        AlphabetConverter alphabetConverter = new AlphabetConverter(linkedHashMap, linkedHashMap2, i2);
        alphabetConverter.addSingleEncoding(i2, "", linkedHashSet2, linkedHashSet.iterator(), map);
        return alphabetConverter;
    }

    public static AlphabetConverter createConverterFromChars(Character[] chArr, Character[] chArr2, Character[] chArr3) {
        return createConverter(convertCharsToIntegers(chArr), convertCharsToIntegers(chArr2), convertCharsToIntegers(chArr3));
    }

    public static AlphabetConverter createConverterFromMap(Map<Integer, String> map) {
        Map mapUnmodifiableMap = Collections.unmodifiableMap(map);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int length = 1;
        for (Map.Entry entry : mapUnmodifiableMap.entrySet()) {
            linkedHashMap.put((String) entry.getValue(), codePointToString(((Integer) entry.getKey()).intValue()));
            if (((String) entry.getValue()).length() > length) {
                length = ((String) entry.getValue()).length();
            }
        }
        return new AlphabetConverter(mapUnmodifiableMap, linkedHashMap, length);
    }

    private AlphabetConverter(Map<Integer, String> map, Map<String, String> map2, int i) {
        this.originalToEncoded = map;
        this.encodedToOriginal = map2;
        this.encodedLetterLength = i;
    }

    private void addSingleEncoding(int i, String str, Collection<Integer> collection, Iterator<Integer> it, Map<Integer, String> map) {
        AlphabetConverter alphabetConverter;
        Collection<Integer> collection2;
        Iterator<Integer> it2;
        Map<Integer, String> map2;
        if (i > 0) {
            Iterator<Integer> it3 = collection.iterator();
            while (it3.hasNext()) {
                int iIntValue = it3.next().intValue();
                if (!it.hasNext()) {
                    return;
                }
                if (i == this.encodedLetterLength && map.containsKey(Integer.valueOf(iIntValue))) {
                    alphabetConverter = this;
                    collection2 = collection;
                    it2 = it;
                    map2 = map;
                } else {
                    alphabetConverter = this;
                    collection2 = collection;
                    it2 = it;
                    map2 = map;
                    alphabetConverter.addSingleEncoding(i - 1, str + codePointToString(iIntValue), collection2, it2, map2);
                }
                this = alphabetConverter;
                collection = collection2;
                it = it2;
                map = map2;
            }
            return;
        }
        Integer next = it.next();
        while (true) {
            Integer num = next;
            if (map.containsKey(num)) {
                String strCodePointToString = codePointToString(num.intValue());
                this.originalToEncoded.put(num, strCodePointToString);
                this.encodedToOriginal.put(strCodePointToString, strCodePointToString);
                if (!it.hasNext()) {
                    return;
                } else {
                    next = it.next();
                }
            } else {
                String strCodePointToString2 = codePointToString(num.intValue());
                this.originalToEncoded.put(num, str);
                this.encodedToOriginal.put(str, strCodePointToString2);
                return;
            }
        }
    }

    public String decode(String str) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            int iCodePointAt = str.codePointAt(i);
            String strCodePointToString = codePointToString(iCodePointAt);
            if (strCodePointToString.equals(this.originalToEncoded.get(Integer.valueOf(iCodePointAt)))) {
                sb.append(strCodePointToString);
                i++;
            } else {
                if (this.encodedLetterLength + i > str.length()) {
                    throw new UnsupportedEncodingException("Unexpected end of string while decoding " + str);
                }
                String strSubstring = str.substring(i, this.encodedLetterLength + i);
                String str2 = this.encodedToOriginal.get(strSubstring);
                if (str2 == null) {
                    throw new UnsupportedEncodingException("Unexpected string without decoding (" + strSubstring + ") in " + str);
                }
                sb.append(str2);
                i += this.encodedLetterLength;
            }
        }
        return sb.toString();
    }

    public String encode(String str) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int iCharCount = 0;
        while (iCharCount < str.length()) {
            int iCodePointAt = str.codePointAt(iCharCount);
            String str2 = this.originalToEncoded.get(Integer.valueOf(iCodePointAt));
            if (str2 == null) {
                throw new UnsupportedEncodingException("Couldn't find encoding for '" + codePointToString(iCodePointAt) + "' in " + str);
            }
            sb.append(str2);
            iCharCount += Character.charCount(iCodePointAt);
        }
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AlphabetConverter)) {
            return false;
        }
        AlphabetConverter alphabetConverter = (AlphabetConverter) obj;
        return this.originalToEncoded.equals(alphabetConverter.originalToEncoded) && this.encodedToOriginal.equals(alphabetConverter.encodedToOriginal) && this.encodedLetterLength == alphabetConverter.encodedLetterLength;
    }

    public int getEncodedCharLength() {
        return this.encodedLetterLength;
    }

    public Map<Integer, String> getOriginalToEncoded() {
        return Collections.unmodifiableMap(this.originalToEncoded);
    }

    public int hashCode() {
        return Objects.hash(this.originalToEncoded, this.encodedToOriginal, Integer.valueOf(this.encodedLetterLength));
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        this.originalToEncoded.forEach(new BiConsumer() { // from class: org.apache.commons.text.AlphabetConverter$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Integer num = (Integer) obj;
                sb.append(AlphabetConverter.codePointToString(num.intValue())).append(AlphabetConverter.ARROW).append(num).append(System.lineSeparator());
            }
        });
        return sb.toString();
    }
}
