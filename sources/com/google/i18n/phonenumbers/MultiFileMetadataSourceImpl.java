package com.google.i18n.phonenumbers;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes2.dex */
final class MultiFileMetadataSourceImpl implements MetadataSource {
    private final ConcurrentHashMap<String, Phonemetadata.PhoneMetadata> geographicalRegions;
    private final MetadataLoader metadataLoader;
    private final ConcurrentHashMap<Integer, Phonemetadata.PhoneMetadata> nonGeographicalRegions;
    private final String phoneNumberMetadataFilePrefix;

    MultiFileMetadataSourceImpl(String str, MetadataLoader metadataLoader) {
        this.geographicalRegions = new ConcurrentHashMap<>();
        this.nonGeographicalRegions = new ConcurrentHashMap<>();
        this.phoneNumberMetadataFilePrefix = str;
        this.metadataLoader = metadataLoader;
    }

    MultiFileMetadataSourceImpl(MetadataLoader metadataLoader) {
        this("/com/google/i18n/phonenumbers/data/PhoneNumberMetadataProto", metadataLoader);
    }

    @Override // com.google.i18n.phonenumbers.MetadataSource
    public Phonemetadata.PhoneMetadata getMetadataForRegion(String str) {
        return MetadataManager.getMetadataFromMultiFilePrefix(str, this.geographicalRegions, this.phoneNumberMetadataFilePrefix, this.metadataLoader);
    }

    @Override // com.google.i18n.phonenumbers.MetadataSource
    public Phonemetadata.PhoneMetadata getMetadataForNonGeographicalRegion(int i) {
        if (isNonGeographical(i)) {
            return MetadataManager.getMetadataFromMultiFilePrefix(Integer.valueOf(i), this.nonGeographicalRegions, this.phoneNumberMetadataFilePrefix, this.metadataLoader);
        }
        return null;
    }

    private boolean isNonGeographical(int i) {
        List<String> list = CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap().get(Integer.valueOf(i));
        return list.size() == 1 && PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY.equals(list.get(0));
    }
}
