package com.miui.notes.cloudservice;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.xiaomi.accountsdk.account.stat.StatConstants;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.request.utils.Request;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: NoteBatchRequest.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\bJ\u0006\u0010\u0010\u001a\u00020\u000eJ\u0006\u0010\u0011\u001a\u00020\u000eJ\b\u0010\u0012\u001a\u00020\u000eH\u0002J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0014H\u0002J\u0012\u0010\u0019\u001a\u00020\u00142\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001d"}, d2 = {"Lcom/miui/notes/cloudservice/NoteBatchRequest;", "", "mSyncContext", "Lcom/miui/notes/cloudservice/NoteSyncContext;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/cloudservice/NoteSyncContext;)V", "mEntities", "Ljava/util/ArrayList;", "Lcom/miui/notes/cloudservice/BaseEntity;", "Lkotlin/collections/ArrayList;", "mPushCount", "", "mPushSuccessCount", "add", "", "entity", "clear", "commit", StatConstants.Event.REQUEST, "requestJson", "Lorg/json/JSONArray;", "getRequestJson", "()Lorg/json/JSONArray;", "resolveResponse", "resArray", "sendRequest", "batchContent", "", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteBatchRequest {
    private static final String TAG = "NoteBatchRequest";
    private final ArrayList<BaseEntity> mEntities;
    private int mPushCount;
    private int mPushSuccessCount;
    private final NoteSyncContext mSyncContext;

    public NoteBatchRequest(NoteSyncContext mSyncContext) {
        Intrinsics.checkNotNullParameter(mSyncContext, "mSyncContext");
        this.mSyncContext = mSyncContext;
        this.mEntities = new ArrayList<>(10);
    }

    public final void add(BaseEntity entity) throws NoteSyncException {
        Logger.INSTANCE.i(TAG, "add entity");
        ArrayList<BaseEntity> arrayList = this.mEntities;
        Intrinsics.checkNotNull(entity);
        arrayList.add(entity);
        Logger.INSTANCE.i(TAG, "resolveResponse mEntities size: " + this.mEntities.size());
        if (this.mEntities.size() >= 10) {
            request();
            Logger.INSTANCE.d(TAG, "add,mEntities clear");
            this.mEntities.clear();
        }
    }

    public final void clear() {
        Logger.INSTANCE.d(TAG, "mEntities clear");
        this.mEntities.clear();
    }

    public final void commit() throws NoteSyncException {
        if (this.mEntities.isEmpty()) {
            return;
        }
        request();
        Logger.INSTANCE.d(TAG, "commit,mEntities clear");
        this.mEntities.clear();
    }

    private final void request() throws NoteSyncException {
        try {
            JSONArray requestJson = getRequestJson();
            Logger.INSTANCE.i(TAG, "request：");
            JSONArray jSONArraySendRequest = sendRequest(requestJson.toString());
            Logger.INSTANCE.i(TAG, "response：");
            resolveResponse(jSONArraySendRequest);
        } catch (AccessDeniedException e) {
            throw new NoteSyncException(e);
        } catch (AuthenticationFailureException e2) {
            throw new NoteSyncException(e2);
        } catch (CipherException e3) {
            throw new NoteSyncException(e3);
        } catch (InvalidResponseException e4) {
            throw new NoteSyncException(e4);
        } catch (CloudServerException e5) {
            throw new NoteSyncException(e5);
        } catch (IOException e6) {
            throw new NoteSyncException(e6);
        } catch (BadPaddingException e7) {
            throw new NoteSyncException(e7);
        } catch (IllegalBlockSizeException e8) {
            throw new NoteSyncException(e8);
        } catch (JSONException e9) {
            throw new NoteSyncException(e9);
        }
    }

    private final JSONArray getRequestJson() throws JSONException, NoteSyncException {
        JSONArray jSONArray = new JSONArray();
        String deviceDigest = this.mSyncContext.getDeviceDigest();
        String accountName = this.mSyncContext.getAccountEntity().getAccountName();
        this.mPushCount = this.mEntities.size();
        this.mPushSuccessCount = 0;
        Iterator<BaseEntity> it = this.mEntities.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            BaseEntity next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            NotesUtils.checkInterrupted();
            jSONArray.put(next.toBatchRequest(deviceDigest, accountName));
        }
        return jSONArray;
    }

    private final void resolveResponse(JSONArray resArray) throws JSONException, NoteSyncException {
        int length = resArray.length();
        NoteSyncException e = null;
        JSONException e2 = null;
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = resArray.getJSONObject(i);
            Logger.INSTANCE.i(TAG, "resolveResponse mEntities size: " + this.mEntities.isEmpty());
            BaseEntity baseEntity = this.mEntities.get(i);
            Intrinsics.checkNotNullExpressionValue(baseEntity, "get(...)");
            BaseEntity baseEntity2 = baseEntity;
            Logger.INSTANCE.i(TAG, "Pushing entity: " + baseEntity2.getSyncId());
            try {
                baseEntity2.resolveConflict(jSONObject);
                this.mSyncContext.getFilterTags().add(baseEntity2.getSyncTag());
                this.mPushSuccessCount++;
                Logger.INSTANCE.i(TAG, "Push entity success: " + baseEntity2.getSyncId());
            } catch (NoteSyncException e3) {
                e = e3;
                Logger.INSTANCE.e(TAG, "Fail to resolve response,NoteSyncException:" + e);
            } catch (JSONException e4) {
                e2 = e4;
                Logger.INSTANCE.e(TAG, "Fail to resolve response,jsonException:" + e2);
            }
        }
        int i2 = this.mPushCount;
        if (i2 > 0) {
            int i3 = (this.mPushSuccessCount * 100) / i2;
            if (i3 <= 100) {
                Logger.INSTANCE.w(TAG, "pushSuccessRateOk: successRate=" + i3 + " dataPushSuccessSize =" + this.mPushSuccessCount + " dataPushSize =" + this.mPushCount);
            } else {
                Logger.INSTANCE.w(TAG, "pushSuccessRateError: successRate=" + i3 + " dataPushSuccessSize =" + this.mPushSuccessCount + " dataPushSize =" + this.mPushCount);
            }
        }
        if (e != null) {
            throw e;
        }
        if (e2 != null) {
            throw e2;
        }
    }

    private final JSONArray sendRequest(String batchContent) throws JSONException, AccessDeniedException, BadPaddingException, CloudServerException, CipherException, IllegalBlockSizeException, IOException, InvalidResponseException, NoteSyncException, AuthenticationFailureException {
        String strConcatUrl = NotesUtils.concatUrl(NotesConstants.URL_BATCH, new Object[0]);
        String str = SyncManager.NOTES_VERSIONCODE;
        HashMap map = new HashMap();
        map.put("batchContent", batchContent);
        map.put(NotesConstants.PARAM_KEY_APK_VERSION, str);
        JSONArray batchResponseList = NoteSchemaUtils.getBatchResponseList(Request.securePost(strConcatUrl, map));
        Intrinsics.checkNotNull(batchResponseList);
        return batchResponseList;
    }
}
