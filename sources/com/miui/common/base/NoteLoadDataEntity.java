package com.miui.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.miui.notes.constant.NoteIntent;
import com.miui.notes.provider.Notes;
import java.io.Serializable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class NoteLoadDataEntity implements Serializable {
    public static final String CALL_DATE = "call_date";
    public static final String DATA_ID = "data_id";
    public static final String FOLDER_ID = "folder_id";
    public static final String FROM_FLOAT_WINDOW = "from_float_window";
    public static final String HIGHLIGHT_KEYWORDS = "highLight_keyWords";
    public static final String MIND_CONTENT_TYPE = "mind_content_type";
    public static final String MIND_EDIT_MODE = "mind_edit_mode";
    public static final String MODIFIED_TIME = "modified_time";
    public static final String PHONE_NUM = "phone_num";
    public static final String SEARCH_TEXT = "search_text";
    public static final String THEME_ID = "theme_id";
    public static final String TITLE = "note_title";
    public static final String TYPE = "note_type";
    public static final String WIDGET_ID = "widget_id";
    public static final String WIDGET_TYPE = "widget_type";
    private long mCallDate;
    private String mContactName;
    private long mDataId;
    private long mFolderId;
    private boolean mFromFloatWindow;
    public boolean mHasNewStyle;
    private ArrayList<String> mHighlightKeywords;
    private int mMindContentType;
    private int mMintEditMode;
    private long mModifiedTime;
    private int mNoteType;
    private String mPhoneNum;
    private String mSearchText;
    private boolean mShowGalleryPicker;
    private int mThemeId;
    private String mTitle;
    private int mWidgetId;
    private int mWidgetType;

    public void setShowGalleryPicker(boolean z) {
        this.mShowGalleryPicker = z;
    }

    public boolean getShowGalleryPicker() {
        return this.mShowGalleryPicker;
    }

    public NoteLoadDataEntity() {
        this.mDataId = 0L;
        this.mFolderId = Long.MIN_VALUE;
        this.mWidgetId = 0;
        this.mWidgetType = -1;
        this.mModifiedTime = Long.MIN_VALUE;
        this.mFromFloatWindow = false;
        this.mPhoneNum = null;
        this.mContactName = null;
        this.mCallDate = 0L;
        this.mHasNewStyle = false;
        this.mShowGalleryPicker = false;
    }

    public NoteLoadDataEntity(Bundle bundle) {
        this.mDataId = 0L;
        this.mFolderId = Long.MIN_VALUE;
        this.mWidgetId = 0;
        this.mWidgetType = -1;
        this.mModifiedTime = Long.MIN_VALUE;
        this.mFromFloatWindow = false;
        this.mPhoneNum = null;
        this.mContactName = null;
        this.mCallDate = 0L;
        this.mHasNewStyle = false;
        this.mShowGalleryPicker = false;
        this.mDataId = bundle.getLong("data_id", -1L);
        this.mFolderId = bundle.getLong("folder_id", 0L);
        this.mTitle = bundle.getString("note_title", "");
        this.mModifiedTime = bundle.getLong("modified_time", Long.MIN_VALUE);
        this.mSearchText = bundle.getString(SEARCH_TEXT, "");
        this.mMindContentType = bundle.getInt("mind_content_type", 0);
        this.mMintEditMode = bundle.getInt(MIND_EDIT_MODE, 0);
        this.mFromFloatWindow = bundle.getBoolean(FROM_FLOAT_WINDOW, false);
        this.mPhoneNum = bundle.getString(PHONE_NUM, "");
    }

    public NoteLoadDataEntity(int i, int i2, int i3, String str, boolean z) {
        this.mDataId = 0L;
        this.mFolderId = Long.MIN_VALUE;
        this.mWidgetId = 0;
        this.mWidgetType = -1;
        this.mModifiedTime = Long.MIN_VALUE;
        this.mPhoneNum = null;
        this.mContactName = null;
        this.mCallDate = 0L;
        this.mHasNewStyle = false;
        this.mShowGalleryPicker = false;
        this.mNoteType = i;
        this.mMindContentType = i2;
        this.mMintEditMode = i3;
        this.mTitle = str;
        this.mFromFloatWindow = z;
    }

    @Deprecated
    public NoteLoadDataEntity(long j, long j2, String str, int i, boolean z) {
        this.mWidgetId = 0;
        this.mWidgetType = -1;
        this.mModifiedTime = Long.MIN_VALUE;
        this.mPhoneNum = null;
        this.mContactName = null;
        this.mCallDate = 0L;
        this.mHasNewStyle = false;
        this.mShowGalleryPicker = false;
        this.mDataId = j;
        this.mFolderId = j2;
        this.mSearchText = str;
        this.mNoteType = i;
        this.mFromFloatWindow = z;
    }

    public NoteLoadDataEntity(long j, long j2, String str, int i, long j3, int i2, int i3, boolean z, String str2, long j4, String str3) {
        this.mHasNewStyle = false;
        this.mShowGalleryPicker = false;
        this.mDataId = j;
        this.mFolderId = j2;
        this.mSearchText = str;
        this.mWidgetId = i2;
        this.mWidgetType = i3;
        this.mNoteType = i;
        this.mModifiedTime = j3;
        this.mFromFloatWindow = z;
        this.mPhoneNum = str2;
        this.mCallDate = j4;
        this.mContactName = str3;
    }

    public NoteLoadDataEntity(long j, long j2, String str, int i, long j3, boolean z, ArrayList<String> arrayList) {
        this.mWidgetId = 0;
        this.mWidgetType = -1;
        this.mPhoneNum = null;
        this.mContactName = null;
        this.mCallDate = 0L;
        this.mHasNewStyle = false;
        this.mShowGalleryPicker = false;
        this.mDataId = j;
        this.mFolderId = j2;
        this.mSearchText = str;
        this.mNoteType = i;
        this.mModifiedTime = j3;
        this.mFromFloatWindow = z;
        this.mHighlightKeywords = arrayList;
    }

    public NoteLoadDataEntity(long j, long j2, String str, int i, long j3, int i2, int i3, String str2, boolean z, ArrayList<String> arrayList) {
        this.mWidgetId = 0;
        this.mWidgetType = -1;
        this.mPhoneNum = null;
        this.mContactName = null;
        this.mCallDate = 0L;
        this.mHasNewStyle = false;
        this.mShowGalleryPicker = false;
        this.mDataId = j;
        this.mFolderId = j2;
        this.mSearchText = str;
        this.mNoteType = i;
        this.mModifiedTime = j3;
        this.mMindContentType = i2;
        this.mMintEditMode = i3;
        this.mTitle = str2;
        this.mFromFloatWindow = z;
        this.mHighlightKeywords = arrayList;
    }

    public NoteLoadDataEntity(long j, long j2, String str, int i, long j3, int i2, int i3, String str2, boolean z, int i4, int i5) {
        this.mPhoneNum = null;
        this.mContactName = null;
        this.mCallDate = 0L;
        this.mHasNewStyle = false;
        this.mShowGalleryPicker = false;
        this.mDataId = j;
        this.mFolderId = j2;
        this.mSearchText = str;
        this.mNoteType = i;
        this.mModifiedTime = j3;
        this.mMindContentType = i2;
        this.mMintEditMode = i3;
        this.mTitle = str2;
        this.mFromFloatWindow = z;
        this.mWidgetId = i4;
        this.mWidgetType = i5;
    }

    public long getNoteId() {
        return this.mDataId;
    }

    public void setNoteId(long j) {
        this.mDataId = j;
    }

    public long getFolderId() {
        return this.mFolderId;
    }

    public void setFolderId(long j) {
        this.mFolderId = j;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public void setModifiedTime(long j) {
        this.mModifiedTime = j;
    }

    public long getModifiedTime() {
        return this.mModifiedTime;
    }

    public void setWidgetId(int i) {
        this.mWidgetId = i;
    }

    public int getWidgetId() {
        return this.mWidgetId;
    }

    public void setWidgetType(int i) {
        this.mWidgetType = i;
    }

    public int getWidgetType() {
        return this.mWidgetType;
    }

    public String getSearchText() {
        return this.mSearchText;
    }

    public void setSearchText(String str) {
        this.mSearchText = str;
    }

    public void setHighlightKeywords(ArrayList<String> arrayList) {
        this.mHighlightKeywords = arrayList;
    }

    public void setPhoneNum(String str) {
        this.mPhoneNum = str;
    }

    public String getPhoneNum() {
        return this.mPhoneNum;
    }

    public void setCallDate(long j) {
        this.mCallDate = j;
    }

    public long getCallDate() {
        return this.mCallDate;
    }

    public String getContactName() {
        return this.mContactName;
    }

    public void setContactName(String str) {
        this.mContactName = str;
    }

    public int getNoteType() {
        return this.mNoteType;
    }

    public void setNoteType(int i) {
        this.mNoteType = i;
    }

    public int getMindContentType() {
        return this.mMindContentType;
    }

    public void setMindContentType(int i) {
        this.mMindContentType = i;
    }

    public int getMindEditMode() {
        return this.mMintEditMode;
    }

    public void setMindEditMode(int i) {
        this.mMintEditMode = i;
    }

    public boolean isFromFloatWindow() {
        return this.mFromFloatWindow;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putLong("data_id", this.mDataId);
        bundle.putLong("folder_id", this.mFolderId);
        bundle.putString("note_title", this.mTitle);
        bundle.putLong("modified_time", this.mModifiedTime);
        bundle.putString(SEARCH_TEXT, this.mSearchText);
        bundle.putLong("widget_id", this.mWidgetId);
        bundle.putInt("widget_type", this.mWidgetType);
        bundle.putInt("mind_content_type", this.mMindContentType);
        bundle.putInt(MIND_EDIT_MODE, this.mMintEditMode);
        bundle.putBoolean(FROM_FLOAT_WINDOW, this.mFromFloatWindow);
        String str = this.mPhoneNum;
        if (str == null) {
            str = "";
        }
        bundle.putString(PHONE_NUM, str);
        bundle.putLong(CALL_DATE, this.mCallDate);
        bundle.putLong("theme_id", this.mThemeId);
        bundle.putStringArrayList(HIGHLIGHT_KEYWORDS, this.mHighlightKeywords);
        return bundle;
    }

    public Intent toIntent() {
        Intent intent = new Intent();
        intent.putExtra("android.intent.extra.UID", getNoteId());
        intent.putExtra("com.miui.notes.folder_id", getFolderId());
        intent.putExtra("intent_extra_data_key", getSearchText());
        intent.putExtra("modified_time", getModifiedTime());
        intent.putExtra("com.miui.notes.widget_id", getWidgetId());
        intent.putExtra("com.miui.notes.widget_type", getWidgetType());
        intent.putExtra(NoteIntent.KEY_IS_FROM_FLOAT_WINDOW, isFromFloatWindow());
        if (!TextUtils.isEmpty(getPhoneNum())) {
            intent.putExtra("android.intent.extra.PHONE_NUMBER", getPhoneNum());
            intent.putExtra(Notes.Intents.INTENT_EXTRA_CALL_DATE, getCallDate());
        }
        if (!TextUtils.isEmpty(getContactName())) {
            intent.putExtra(Notes.Intents.INTENT_EXTRA_CONTACT_NAME, getContactName());
        }
        ArrayList<String> arrayList = this.mHighlightKeywords;
        if (arrayList != null && !arrayList.isEmpty()) {
            intent.putStringArrayListExtra(NoteIntent.ACTION_WITH_AI_QUERY, this.mHighlightKeywords);
        }
        return intent;
    }
}
