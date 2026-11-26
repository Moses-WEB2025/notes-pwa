package com.xiaomi.ai;

import android.text.TextUtils;
import com.google.common.net.HttpHeaders;
import com.xiaomi.ai.utils.Log;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class NlpRequest {
    private static final String TAG = "NlpRequest";
    JSONObject context;
    ConcurrentHashMap<String, String> contextMap;
    ConcurrentHashMap<String, String> contextStrMap;
    JSONObject device;
    String intention;
    Float latitude;
    String locale;
    boolean locationEnable;
    Float longitude;
    String mSessionId;
    String nlpVersion;
    JSONObject speechData;
    boolean startNewSession;
    String textToProcess;
    UserInfo userInfo;
    String requestId = null;
    int isPendingSend = 0;
    boolean discardInstructionOfNlpResult = true;

    public static class SoundboxContext {
        public static final int PLAYER_MODE_LOOP = 1;
        public static final int PLAYER_MODE_RANDOM = 3;
        public static final int PLAYER_MODE_SEQUENCE = 2;
        public static final int PLAYER_MODE_SINGLE = 0;
        public static final int PLAYER_STATUS_PAUSE = 2;
        public static final int PLAYER_STATUS_PLAY = 1;
        public static final int PLAYER_STATUS_STOP = 0;
        public static final int PLAYER_TYPE_MUSIC = 3;
        public static final int PLAYER_TYPE_STATION_SOUND = 4;
        String albumId;
        String albumName;
        String artistName;
        String globalId;
        String listId;
        String mediaId;
        String mediaName;
        String originl;
        int playerStatus = -1;
        int playerMode = -1;
        int playerVolume = -1;
        int playerType = -1;
        int offset = -1;

        public void setAlbumId(String str) {
            this.albumId = str;
        }

        public void setAlbumName(String str) {
            this.albumName = str;
        }

        public void setArtistName(String str) {
            this.artistName = str;
        }

        public void setGlobalId(String str) {
            this.globalId = str;
        }

        public void setListId(String str) {
            this.listId = str;
        }

        public void setMediaId(String str) {
            this.mediaId = str;
        }

        public void setMediaName(String str) {
            this.mediaName = str;
        }

        public void setOffset(int i) {
            this.offset = i;
        }

        public void setOriginl(String str) {
            this.originl = str;
        }

        public void setPlayerMode(int i) {
            this.playerMode = i;
        }

        public void setPlayerStatus(int i) {
            this.playerStatus = i;
        }

        public void setPlayerType(int i) {
            this.playerType = i;
        }

        public void setPlayerVolume(int i) {
            this.playerVolume = i;
        }

        public JSONObject toContextJson() {
            JSONObject jSONObject = new JSONObject();
            try {
                int i = this.playerStatus;
                if (i != -1) {
                    jSONObject.put("player_status", i);
                }
                int i2 = this.playerMode;
                if (i2 != -1) {
                    jSONObject.put("player_mode", i2);
                }
                int i3 = this.playerVolume;
                if (i3 != -1) {
                    jSONObject.put("player_volume", i3);
                }
                int i4 = this.playerType;
                if (i4 != -1) {
                    jSONObject.put("player_type", i4);
                }
                JSONObject jSONObject2 = new JSONObject();
                JSONObject jSONObject3 = new JSONObject();
                int i5 = this.offset;
                if (i5 != -1) {
                    jSONObject3.put("offset", i5);
                }
                if (!TextUtils.isEmpty(this.listId)) {
                    jSONObject3.put("list_id", this.listId);
                }
                JSONArray jSONArray = new JSONArray();
                JSONObject jSONObject4 = new JSONObject();
                String str = "";
                int i6 = this.playerType;
                if (i6 == 3) {
                    if (!TextUtils.isEmpty(this.mediaName)) {
                        jSONObject4.put("song", this.mediaName);
                    }
                    if (!TextUtils.isEmpty(this.artistName)) {
                        jSONObject4.put("artist_name", this.artistName);
                    }
                    if (!TextUtils.isEmpty(this.mediaId)) {
                        jSONObject4.put("id", this.mediaId);
                    }
                    if (!TextUtils.isEmpty(this.originl)) {
                        jSONObject4.put(HttpHeaders.ReferrerPolicyValues.ORIGIN, this.originl);
                    }
                    if (!TextUtils.isEmpty(this.globalId)) {
                        jSONObject4.put("global_id", this.globalId);
                    }
                    str = "musics";
                } else if (i6 == 4) {
                    if (!TextUtils.isEmpty(this.mediaName)) {
                        jSONObject4.put("title", this.mediaName);
                    }
                    if (!TextUtils.isEmpty(this.mediaId)) {
                        jSONObject4.put("id", this.mediaId);
                    }
                    if (!TextUtils.isEmpty(this.albumId)) {
                        jSONObject4.put("albumId", this.albumId);
                    }
                    if (!TextUtils.isEmpty(this.albumName)) {
                        jSONObject4.put("albumName", this.albumName);
                    }
                    str = "stations";
                }
                jSONArray.put(jSONObject4);
                jSONObject3.put(str, jSONArray);
                jSONObject2.put("detail", jSONObject3);
                jSONObject.put("player_detail", jSONObject2);
                return jSONObject;
            } catch (JSONException e) {
                Log.e(NlpRequest.TAG, "JSONException", e);
                return jSONObject;
            }
        }
    }

    public static class UserInfo {
        String extend;
        String userId;
        String userType;

        public void setExtend(String str) {
            this.extend = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public void setUserType(String str) {
            this.userType = str;
        }
    }

    public String getLocal() {
        return TextUtils.isEmpty(this.locale) ? "zh-CN" : this.locale;
    }

    public String getTextToProcess() {
        return this.textToProcess;
    }

    public void setContext(String str, String str2) {
        if (this.contextMap == null) {
            this.contextMap = new ConcurrentHashMap<>();
        }
        this.contextMap.put(str, str2);
    }

    public void setContext(JSONObject jSONObject) {
        this.context = jSONObject;
    }

    public void setContextStr(String str, String str2) {
        if (this.contextStrMap == null) {
            this.contextStrMap = new ConcurrentHashMap<>();
        }
        this.contextStrMap.put(str, str2);
    }

    public void setDevice(JSONObject jSONObject) {
        this.device = jSONObject;
    }

    public void setDiscardInstructionOfNlpResult(boolean z) {
        this.discardInstructionOfNlpResult = z;
    }

    public void setIntention(String str) {
        this.intention = str;
    }

    public void setIsPendingSend(int i) {
        this.isPendingSend = i;
    }

    public void setLatitude(float f) {
        this.latitude = Float.valueOf(f);
    }

    public void setLocale(String str) {
        this.locale = str;
    }

    public void setLocationEnable(boolean z) {
        this.locationEnable = z;
    }

    public void setLongitude(float f) {
        this.longitude = Float.valueOf(f);
    }

    public void setNlpVersion(String str) {
        this.nlpVersion = str;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public void setSessionId(String str) {
        this.mSessionId = str;
    }

    public void setSoundboxContext(SoundboxContext soundboxContext) {
        if (soundboxContext != null) {
            setContext("device_player_status", soundboxContext.toContextJson().toString());
        }
    }

    public void setSpeechData(JSONObject jSONObject) {
        this.speechData = jSONObject;
    }

    public void setTextToProcess(String str) {
        this.textToProcess = str;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void startNewSession() {
        startNewSession(true);
    }

    public void startNewSession(boolean z) {
        this.startNewSession = z;
    }
}
