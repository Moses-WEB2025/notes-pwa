package com.miui.notes.cloudservice;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseEntity {
    protected static final int ENDPOINT_LOCAL = 1;
    protected static final int ENDPOINT_SERVER = 2;

    public abstract String getCkey(String str);

    public abstract String getPath(String str) throws NoteSyncException;

    public abstract long getSyncId();

    public abstract String getSyncTag();

    public abstract boolean isDeleted();

    public abstract void persist() throws NoteSyncException;

    public abstract boolean readySync() throws NoteSyncException;

    public abstract void resolveConflict(JSONObject jSONObject) throws JSONException, NoteSyncException;

    public abstract void setCkey(String str);

    public abstract boolean shouldDeleteOnServer() throws NoteSyncException;

    public abstract JSONObject toJSON() throws JSONException, NoteSyncException;

    public JSONObject toBatchRequest(String str, String str2) throws JSONException, NoteSyncException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("ckey", getCkey(str));
        if (shouldDeleteOnServer()) {
            jSONObject.put("tag", getSyncTag());
        } else {
            jSONObject.put(NotesConstants.JSON_KEY_ENTRY, toJSON());
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("params", jSONObject);
        jSONObject2.put("path", getPath(str2));
        return jSONObject2;
    }
}
