package com.miui.notes.data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.widget.TextView;
import ch.qos.logback.core.CoreConstants;
import com.miui.common.tool.ContentUtils;
import com.miui.common.tool.async.Task;
import com.miui.common.tool.async.TaskManager;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.provider.Notes;
import com.miui.notes.provider.NotesProvider;
import com.miui.notes.tool.LogUtils;
import com.miui.notes.ui.view.SearchCallback;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes4.dex */
public class Contact {
    private static final String CALLER_ID_SELECTION_SINGLE = "PHONE_NUMBERS_EQUAL(data1,?) AND mimetype='vnd.android.cursor.item/phone_v2' AND raw_contact_id IN (SELECT raw_contact_id  FROM phone_lookup WHERE min_match = '+')";
    private static final int CONTACT_COLUMN_DISPLAY_NAME_INDEX = 2;
    private static final int CONTACT_COLUMN_ID_INDEX = 0;
    private static final int CONTACT_COLUMN_NUMBER_INDEX = 1;
    private static final int IDENTITY_COLUMN_CONTACT_ID_INDEX = 1;
    private static final int IDENTITY_COLUMN_LOOKUP_KEY_INDEX = 0;
    private static final String TAG = "Contact";
    private static HashMap<String, String> sContactCache;
    private static HashMap<String, String> sContactKeyMap;
    private static Locale sLocale;
    private static TaskManager sTaskManager;
    private String mName;
    private boolean mNameLoaded;
    private String mPhoneNumber;
    private String mSearchContent = "";
    private static final Contact EMPTY_CONTACT = new Contact("", "", true);
    private static final String[] CONTACT_PROJECTION = {"_id", "data1", "display_name"};
    private static final String[] IDENTITY_PROJECTION = {"lookup", "contact_id"};

    private static String getContactName(Context context, String str) {
        if (sContactCache == null) {
            sContactCache = new HashMap<>();
        }
        if (sContactCache.containsKey(str)) {
            return sContactCache.get(str);
        }
        Cursor cursorQueryContact = queryContact(context, ContactsContract.Data.CONTENT_URI, new String[]{"display_name"}, CALLER_ID_SELECTION_SINGLE.replace(Marker.ANY_NON_NULL_MARKER, PhoneNumberUtils.toCallerIDMinMatch(str)), new String[]{str}, null);
        if (cursorQueryContact == null || !cursorQueryContact.moveToFirst()) {
            LogUtils.d(TAG, "No contact matched with number");
            if (cursorQueryContact != null) {
                cursorQueryContact.close();
            }
            return null;
        }
        try {
            String string = cursorQueryContact.getString(0);
            sContactCache.put(str, string);
            return string;
        } catch (IndexOutOfBoundsException e) {
            Logger.INSTANCE.e(TAG, " Cursor get string error,exception:" + e);
            return null;
        } finally {
            cursorQueryContact.close();
        }
    }

    public static String getPhoneNumber(Context context, long j, boolean z) {
        Logger.INSTANCE.d(TAG, "getPhoneNumber noteId = " + j + " isPrivate = " + z);
        Cursor cursorQueryContact = queryContact(context, z ? NotesProvider.appendFlag(Notes.Data.CONTENT_URI, NotesProvider.CALLER_IS_PRIVACY) : Notes.Data.CONTENT_URI, new String[]{"data3"}, "note_id=? AND mime_type=?", new String[]{Long.toString(j), Notes.CallData.CONTENT_ITEM_TYPE}, null);
        if (cursorQueryContact != null) {
            try {
                Logger.INSTANCE.d(TAG, "getPhoneNumber: cursorCount = " + cursorQueryContact.getCount());
                if (cursorQueryContact.moveToNext()) {
                    String string = cursorQueryContact.getString(0);
                    cursorQueryContact.close();
                    return string;
                }
                cursorQueryContact.close();
            } catch (Throwable th) {
                cursorQueryContact.close();
                throw th;
            }
        }
        Logger.INSTANCE.e(TAG, "Fail to get contact for note, id=" + j);
        return null;
    }

    public static ArrayList<Contact> getContactListByUris(Context context, Uri[] uriArr) {
        if (uriArr == null || uriArr.length == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(uriArr.length);
        for (Uri uri : uriArr) {
            if (SearchCallback.TextSearch.PHONE_NUMBER_SEARCH_KEY.equals(uri.getAuthority())) {
                arrayList.add(Long.valueOf(Long.parseLong(uri.getLastPathSegment())));
            }
        }
        ArrayList<Contact> arrayList2 = new ArrayList<>(uriArr.length);
        Cursor cursorQueryContact = queryContact(context, ContactsContract.Data.CONTENT_URI, CONTACT_PROJECTION, String.format("%s IN (%s)", "_id", TextUtils.join(",", arrayList)), null, null);
        if (cursorQueryContact == null) {
            return arrayList2;
        }
        while (cursorQueryContact.moveToNext()) {
            try {
                arrayList2.add(new Contact(cursorQueryContact.getString(2), cursorQueryContact.getString(1), true));
            } catch (Throwable th) {
                cursorQueryContact.close();
                throw th;
            }
        }
        cursorQueryContact.close();
        return arrayList2;
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

    public static String parseContactTextFromIntent(Context context, Intent intent) {
        if (!"text/x-vcard".equals(intent.getType())) {
            return null;
        }
        if (sContactKeyMap == null || !Locale.getDefault().equals(sLocale)) {
            sLocale = Locale.getDefault();
            sContactKeyMap = new HashMap<>();
            String[] stringArray = context.getResources().getStringArray(R.array.contact_entries);
            String[] stringArray2 = context.getResources().getStringArray(R.array.contact_values);
            int length = stringArray.length;
            for (int i = 0; i < length; i++) {
                sContactKeyMap.put(stringArray2[i], stringArray[i]);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String str : sContactKeyMap.keySet()) {
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(str);
            if (stringArrayListExtra != null) {
                for (String str2 : stringArrayListExtra) {
                    if (!TextUtils.isEmpty(str2)) {
                        sb.append("[" + sContactKeyMap.get(str) + ']' + str2.replace(';', CoreConstants.COMMA_CHAR) + ";\n");
                    }
                }
            }
        }
        return sb.toString();
    }

    private static Cursor queryContact(Context context, Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        try {
            return context.getContentResolver().query(uri, strArr, str, strArr2, str2);
        } catch (SecurityException e) {
            Logger.INSTANCE.e(TAG, "Fail to query contact,exception:" + e);
            return null;
        }
    }

    public Contact(String str, String str2, boolean z) {
        this.mName = str;
        this.mPhoneNumber = str2;
        this.mNameLoaded = z;
    }

    public String getName() {
        return this.mName;
    }

    public String getPhoneNumber() {
        return this.mPhoneNumber;
    }

    public String getDisplayName() {
        if (TextUtils.isEmpty(this.mName)) {
            return this.mPhoneNumber;
        }
        return this.mName;
    }

    public void loadNameInto(TextView textView, long j, long j2, String str, String str2) {
        if (TextUtils.equals(str2, this.mName) && this.mNameLoaded && this.mSearchContent.contentEquals(str)) {
            return;
        }
        this.mName = str2;
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        this.mSearchContent = str;
        this.mNameLoaded = true;
        if (sTaskManager == null) {
            sTaskManager = new TaskManager(10, 1, 0);
        }
        sTaskManager.add(new NameLoadTask(textView, j, j2, this.mSearchContent));
    }

    private class NameLoadTask extends Task<String> {
        private long mFolderId;
        private long mNoteId;
        private WeakReference<TextView> mTextView;
        private String searchContent;

        public NameLoadTask(TextView textView, long j, long j2, String str) {
            textView.setTag(R.id.contact_note_id_key, Long.valueOf(j));
            this.mTextView = new WeakReference<>(textView);
            this.mNoteId = j;
            this.mFolderId = j2;
            this.searchContent = str;
        }

        @Override // com.miui.common.tool.async.Task
        public void onResult(TaskManager taskManager, String str) {
            TextView textView;
            if (TextUtils.isEmpty(str) || (textView = this.mTextView.get()) == null || this.mNoteId != ((Long) textView.getTag(R.id.contact_note_id_key)).longValue()) {
                return;
            }
            textView.setText(ContentUtils.changeHighLight(str, this.searchContent));
        }

        @Override // com.miui.common.tool.async.Task
        public String doLoad() throws Exception {
            String phoneNumber = Contact.getPhoneNumber(NoteApp.getInstance(), this.mNoteId, this.mFolderId == -4);
            if (phoneNumber != null) {
                Contact.this.mPhoneNumber = phoneNumber;
                if (!TextUtils.isEmpty(Contact.this.mName)) {
                    return Contact.this.mName;
                }
                if (!TextUtils.isEmpty(Contact.this.mPhoneNumber)) {
                    return Contact.this.mPhoneNumber;
                }
                return "";
            }
            return "";
        }
    }
}
