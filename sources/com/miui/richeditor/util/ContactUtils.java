package com.miui.richeditor.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.miui.notes.base.utils.Logger;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class ContactUtils {
    private static final int IDENTITY_COLUMN_CONTACT_ID_INDEX = 1;
    private static final int IDENTITY_COLUMN_LOOKUP_KEY_INDEX = 0;
    private static final String[] IDENTITY_PROJECTION = {"lookup", "contact_id"};
    private static final String TAG = "ContactUtils";

    private static Cursor queryContact(Context context, Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        try {
            return context.getContentResolver().query(uri, strArr, str, strArr2, str2);
        } catch (SecurityException e) {
            Logger.INSTANCE.e(TAG, "Fail to query contact,exception:" + e);
            return null;
        }
    }

    private static Cursor getContactCursor(Context context, String str, String str2) {
        return queryContact(context, ContactsContract.Data.CONTENT_URI, IDENTITY_PROJECTION, String.format(Locale.US, "%s=? AND PHONE_NUMBERS_EQUAL(%s,?)", "display_name", "data1"), new String[]{str, str2}, null);
    }

    public static Uri getContactUri(Context context, String str, String str2) {
        Cursor contactCursor = getContactCursor(context, str, str2);
        if (contactCursor == null) {
            return null;
        }
        try {
            if (!contactCursor.moveToNext()) {
                return null;
            }
            return ContactsContract.Contacts.CONTENT_LOOKUP_URI.buildUpon().appendPath(contactCursor.getString(0)).appendPath(Long.toString(contactCursor.getLong(1))).build();
        } finally {
            contactCursor.close();
        }
    }
}
