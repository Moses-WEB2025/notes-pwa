package com.google.i18n.phonenumbers;

import androidx.lifecycle.AtomicReference$$ExternalSyntheticBackportWithForwarding0;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes2.dex */
final class MetadataManager {
    private static final String ALTERNATE_FORMATS_FILE_PREFIX = "/com/google/i18n/phonenumbers/data/PhoneNumberAlternateFormatsProto";
    static final String MULTI_FILE_PHONE_NUMBER_METADATA_FILE_PREFIX = "/com/google/i18n/phonenumbers/data/PhoneNumberMetadataProto";
    private static final String SHORT_NUMBER_METADATA_FILE_PREFIX = "/com/google/i18n/phonenumbers/data/ShortNumberMetadataProto";
    static final String SINGLE_FILE_PHONE_NUMBER_METADATA_FILE_NAME = "/com/google/i18n/phonenumbers/data/SingleFilePhoneNumberMetadataProto";
    static final MetadataLoader DEFAULT_METADATA_LOADER = new MetadataLoader() { // from class: com.google.i18n.phonenumbers.MetadataManager.1
        @Override // com.google.i18n.phonenumbers.MetadataLoader
        public InputStream loadMetadata(String str) {
            return MetadataManager.class.getResourceAsStream(str);
        }
    };
    private static final Logger logger = Logger.getLogger(MetadataManager.class.getName());
    private static final ConcurrentHashMap<Integer, Phonemetadata.PhoneMetadata> alternateFormatsMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Phonemetadata.PhoneMetadata> shortNumberMetadataMap = new ConcurrentHashMap<>();
    private static final Set<Integer> alternateFormatsCountryCodes = AlternateFormatsCountryCodeSet.getCountryCodeSet();
    private static final Set<String> shortNumberMetadataRegionCodes = ShortNumbersRegionCodeSet.getRegionCodeSet();

    private MetadataManager() {
    }

    static Phonemetadata.PhoneMetadata getAlternateFormatsForCountry(int i) {
        if (alternateFormatsCountryCodes.contains(Integer.valueOf(i))) {
            return getMetadataFromMultiFilePrefix(Integer.valueOf(i), alternateFormatsMap, ALTERNATE_FORMATS_FILE_PREFIX, DEFAULT_METADATA_LOADER);
        }
        return null;
    }

    static Phonemetadata.PhoneMetadata getShortNumberMetadataForRegion(String str) {
        if (shortNumberMetadataRegionCodes.contains(str)) {
            return getMetadataFromMultiFilePrefix(str, shortNumberMetadataMap, SHORT_NUMBER_METADATA_FILE_PREFIX, DEFAULT_METADATA_LOADER);
        }
        return null;
    }

    static Set<String> getSupportedShortNumberRegions() {
        return Collections.unmodifiableSet(shortNumberMetadataRegionCodes);
    }

    static <T> Phonemetadata.PhoneMetadata getMetadataFromMultiFilePrefix(T t, ConcurrentHashMap<T, Phonemetadata.PhoneMetadata> concurrentHashMap, String str, MetadataLoader metadataLoader) {
        Phonemetadata.PhoneMetadata phoneMetadata = concurrentHashMap.get(t);
        if (phoneMetadata != null) {
            return phoneMetadata;
        }
        String str2 = str + "_" + t;
        List<Phonemetadata.PhoneMetadata> metadataFromSingleFileName = getMetadataFromSingleFileName(str2, metadataLoader);
        if (metadataFromSingleFileName.size() > 1) {
            logger.log(Level.WARNING, "more than one metadata in file " + str2);
        }
        Phonemetadata.PhoneMetadata phoneMetadata2 = metadataFromSingleFileName.get(0);
        Phonemetadata.PhoneMetadata phoneMetadataPutIfAbsent = concurrentHashMap.putIfAbsent(t, phoneMetadata2);
        return phoneMetadataPutIfAbsent != null ? phoneMetadataPutIfAbsent : phoneMetadata2;
    }

    static class SingleFileMetadataMaps {
        private final Map<Integer, Phonemetadata.PhoneMetadata> countryCallingCodeToMetadata;
        private final Map<String, Phonemetadata.PhoneMetadata> regionCodeToMetadata;

        static SingleFileMetadataMaps load(String str, MetadataLoader metadataLoader) {
            List<Phonemetadata.PhoneMetadata> metadataFromSingleFileName = MetadataManager.getMetadataFromSingleFileName(str, metadataLoader);
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            for (Phonemetadata.PhoneMetadata phoneMetadata : metadataFromSingleFileName) {
                String id = phoneMetadata.getId();
                if (PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY.equals(id)) {
                    map2.put(Integer.valueOf(phoneMetadata.getCountryCode()), phoneMetadata);
                } else {
                    map.put(id, phoneMetadata);
                }
            }
            return new SingleFileMetadataMaps(map, map2);
        }

        private SingleFileMetadataMaps(Map<String, Phonemetadata.PhoneMetadata> map, Map<Integer, Phonemetadata.PhoneMetadata> map2) {
            this.regionCodeToMetadata = Collections.unmodifiableMap(map);
            this.countryCallingCodeToMetadata = Collections.unmodifiableMap(map2);
        }

        Phonemetadata.PhoneMetadata get(String str) {
            return this.regionCodeToMetadata.get(str);
        }

        Phonemetadata.PhoneMetadata get(int i) {
            return this.countryCallingCodeToMetadata.get(Integer.valueOf(i));
        }
    }

    static SingleFileMetadataMaps getSingleFileMetadataMaps(AtomicReference<SingleFileMetadataMaps> atomicReference, String str, MetadataLoader metadataLoader) {
        SingleFileMetadataMaps singleFileMetadataMaps = atomicReference.get();
        if (singleFileMetadataMaps != null) {
            return singleFileMetadataMaps;
        }
        AtomicReference$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, SingleFileMetadataMaps.load(str, metadataLoader));
        return atomicReference.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<Phonemetadata.PhoneMetadata> getMetadataFromSingleFileName(String str, MetadataLoader metadataLoader) {
        InputStream inputStreamLoadMetadata = metadataLoader.loadMetadata(str);
        if (inputStreamLoadMetadata == null) {
            throw new IllegalStateException("missing metadata: " + str);
        }
        List<Phonemetadata.PhoneMetadata> metadataList = loadMetadataAndCloseInput(inputStreamLoadMetadata).getMetadataList();
        if (metadataList.size() != 0) {
            return metadataList;
        }
        throw new IllegalStateException("empty metadata: " + str);
    }

    private static Phonemetadata.PhoneMetadataCollection loadMetadataAndCloseInput(InputStream inputStream) throws Throwable {
        ObjectInputStream objectInputStream = null;
        try {
            try {
                ObjectInputStream objectInputStream2 = new ObjectInputStream(inputStream);
                try {
                    Phonemetadata.PhoneMetadataCollection phoneMetadataCollection = new Phonemetadata.PhoneMetadataCollection();
                    try {
                        phoneMetadataCollection.readExternal(objectInputStream2);
                        try {
                            objectInputStream2.close();
                            return phoneMetadataCollection;
                        } catch (IOException e) {
                            logger.log(Level.WARNING, "error closing input stream (ignored)", (Throwable) e);
                            return phoneMetadataCollection;
                        }
                    } catch (IOException e2) {
                        throw new RuntimeException("cannot load/parse metadata", e2);
                    }
                } catch (Throwable th) {
                    th = th;
                    objectInputStream = objectInputStream2;
                    try {
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        } else {
                            inputStream.close();
                        }
                    } catch (IOException e3) {
                        logger.log(Level.WARNING, "error closing input stream (ignored)", (Throwable) e3);
                    }
                    throw th;
                }
            } catch (IOException e4) {
                throw new RuntimeException("cannot load/parse metadata", e4);
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
