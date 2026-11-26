package miuix.search.recommendation;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObservable;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes6.dex */
public class SharedPreferencesStorage implements SimpleList {
    private static final String DEFAULT_FN_SEARCH_PREFERENCE = "search";
    private static final int DEFAULT_MAX_HISTORY_NUM = 8;
    private static final String DEFAULT_PREFERENCE_KEY_HISTORY = "search_history";
    private static final String TAG = "SharedPrefProvider";
    private List<CharSequence> mList;
    private int mMaxNum;
    private DataSetObservable mObservable;
    private String mPrefKey;
    private SharedPreferences mPreferences;

    public SharedPreferencesStorage(Context context) {
        this(context, 8, "search", DEFAULT_PREFERENCE_KEY_HISTORY);
    }

    public SharedPreferencesStorage(Context context, int i, String str, String str2) {
        this.mObservable = new DataSetObservable();
        this.mMaxNum = i;
        this.mPreferences = context.getSharedPreferences(str, 0);
        this.mPrefKey = str2;
    }

    @Override // miuix.search.recommendation.SimpleList
    public void add(int i, CharSequence charSequence) {
        ensureDataLoaded();
        this.mList.add(i, charSequence);
        this.mObservable.notifyChanged();
    }

    @Override // miuix.search.recommendation.SimpleList
    public void remove(int i) {
        ensureDataLoaded();
        this.mList.remove(i);
        this.mObservable.notifyChanged();
    }

    @Override // miuix.search.recommendation.SimpleList
    public void clear() {
        ensureDataLoaded();
        this.mList.clear();
        this.mObservable.notifyChanged();
    }

    @Override // miuix.search.recommendation.SimpleList
    public CharSequence get(int i) {
        ensureDataLoaded();
        return this.mList.get(i);
    }

    @Override // miuix.search.recommendation.SimpleList
    public int size() {
        ensureDataLoaded();
        return this.mList.size();
    }

    private void ensureDataLoaded() {
        if (this.mList != null) {
            return;
        }
        this.mList = Collections.synchronizedList(new ArrayList());
        String string = this.mPreferences.getString(this.mPrefKey, null);
        if (TextUtils.isEmpty(string)) {
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(string);
            for (int i = 0; i < jSONArray.length(); i++) {
                this.mList.add(jSONArray.getString(i));
            }
        } catch (JSONException e) {
            Log.w(TAG, "read list failed. ", e);
        }
    }

    public void syncData() {
        SharedPreferences.Editor editorEdit = this.mPreferences.edit();
        editorEdit.putString(DEFAULT_PREFERENCE_KEY_HISTORY, listToJson(this.mList).toString());
        editorEdit.apply();
    }

    private JSONArray listToJson(List<CharSequence> list) {
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < list.size() && i < this.mMaxNum; i++) {
            jSONArray.put(list.get(i));
        }
        return jSONArray;
    }
}
