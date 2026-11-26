package com.google.i18n.phonenumbers.internal;

import com.google.i18n.phonenumbers.Phonemetadata;

/* JADX INFO: loaded from: classes2.dex */
public interface MatcherApi {
    boolean matchNationalNumber(CharSequence charSequence, Phonemetadata.PhoneNumberDesc phoneNumberDesc, boolean z);
}
