package com.google.i18n.phonenumbers;

/* JADX INFO: loaded from: classes2.dex */
interface MetadataSource {
    Phonemetadata.PhoneMetadata getMetadataForNonGeographicalRegion(int i);

    Phonemetadata.PhoneMetadata getMetadataForRegion(String str);
}
