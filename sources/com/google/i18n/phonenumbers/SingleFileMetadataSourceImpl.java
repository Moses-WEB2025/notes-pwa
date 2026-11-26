package com.google.i18n.phonenumbers;

import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes2.dex */
final class SingleFileMetadataSourceImpl implements MetadataSource {
    private final MetadataLoader metadataLoader;
    private final String phoneNumberMetadataFileName;
    private final AtomicReference<MetadataManager.SingleFileMetadataMaps> phoneNumberMetadataRef;

    SingleFileMetadataSourceImpl(String str, MetadataLoader metadataLoader) {
        this.phoneNumberMetadataRef = new AtomicReference<>();
        this.phoneNumberMetadataFileName = str;
        this.metadataLoader = metadataLoader;
    }

    SingleFileMetadataSourceImpl(MetadataLoader metadataLoader) {
        this("/com/google/i18n/phonenumbers/data/SingleFilePhoneNumberMetadataProto", metadataLoader);
    }

    @Override // com.google.i18n.phonenumbers.MetadataSource
    public Phonemetadata.PhoneMetadata getMetadataForRegion(String str) {
        return MetadataManager.getSingleFileMetadataMaps(this.phoneNumberMetadataRef, this.phoneNumberMetadataFileName, this.metadataLoader).get(str);
    }

    @Override // com.google.i18n.phonenumbers.MetadataSource
    public Phonemetadata.PhoneMetadata getMetadataForNonGeographicalRegion(int i) {
        return MetadataManager.getSingleFileMetadataMaps(this.phoneNumberMetadataRef, this.phoneNumberMetadataFileName, this.metadataLoader).get(i);
    }
}
