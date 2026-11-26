package com.xiaomi.opensdk.pdc;

import com.xiaomi.onetrack.api.ba;
import com.xiaomi.opensdk.exception.AuthenticationException;
import com.xiaomi.opensdk.exception.RetriableException;
import com.xiaomi.opensdk.exception.UnretriableException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class OpenProfileManager {
    private static final String APP_ID = "sysFit";
    private static final String RECORD_ID = "1";
    private static final String SPACE_ID = "default";
    private static final String TYPE = "fit";
    private final SyncFactory mSyncFactory;
    private final OpenPdcSyncServerAdapter mSyncServer;

    public OpenProfileManager(OpenPdcSyncServerAdapter.Environment environment, String str, String str2) {
        OpenPdcSyncServerAdapter openPdcSyncServerAdapter = new OpenPdcSyncServerAdapter(environment, str, str2, APP_ID, "default");
        this.mSyncServer = openPdcSyncServerAdapter;
        this.mSyncFactory = new SyncFactory(openPdcSyncServerAdapter);
    }

    public void setProfile(Profile profile) throws AuthenticationException, RetriableException, UnretriableException {
        try {
            UploadOperation.Result resultCreate = this.mSyncFactory.create(TYPE, "1", null, null, profile.toJsonObject());
            if (resultCreate.isSuccess()) {
                return;
            }
            if (resultCreate.getErrorType() != null) {
                int i = AnonymousClass1.$SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType[resultCreate.getErrorType().ordinal()];
                if (i == 1) {
                    throw new UnretriableException(resultCreate.getDescription());
                }
                if (i == 2) {
                    throw new RetriableException(resultCreate.getDescription(), resultCreate.getRetryTime());
                }
                if (i == 3) {
                    throw new AuthenticationException();
                }
                return;
            }
            throw new UnretriableException(resultCreate.getDescription());
        } catch (SyncException e) {
            throw new UnretriableException(e);
        } catch (JSONException e2) {
            throw new UnretriableException(e2);
        }
    }

    /* JADX INFO: renamed from: com.xiaomi.opensdk.pdc.OpenProfileManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType;

        static {
            int[] iArr = new int[Constants.ErrorType.values().length];
            $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType = iArr;
            try {
                iArr[Constants.ErrorType.UNRETRIABLE_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType[Constants.ErrorType.RETRIABLE_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType[Constants.ErrorType.AUTH_FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void setBirthday(long j) throws AuthenticationException, RetriableException, UnretriableException {
        if (j <= 0) {
            throw new IllegalArgumentException("参数错误，请参考API文档");
        }
        UserProfile userProfile = new UserProfile();
        userProfile.birthday = j;
        setProfile(userProfile);
    }

    public void setGender(UserProfile.Gender gender) throws AuthenticationException, RetriableException, UnretriableException {
        if (gender != UserProfile.Gender.MALE && gender != UserProfile.Gender.FEMALE) {
            throw new IllegalArgumentException("参数错误，请参考API文档");
        }
        UserProfile userProfile = new UserProfile();
        userProfile.gender = gender;
        setProfile(userProfile);
    }

    public UserProfile getPhysicalProfile() throws AuthenticationException, RetriableException, UnretriableException {
        try {
            FetchRecordOperation.Result resultFetchRecord = this.mSyncFactory.fetchRecord("1");
            if (!resultFetchRecord.isSuccess()) {
                if (resultFetchRecord.getErrorType() != null) {
                    int i = AnonymousClass1.$SwitchMap$com$xiaomi$opensdk$pdc$Constants$ErrorType[resultFetchRecord.getErrorType().ordinal()];
                    if (i == 1) {
                        throw new UnretriableException(resultFetchRecord.getDescription());
                    }
                    if (i == 2) {
                        throw new RetriableException(resultFetchRecord.getDescription(), resultFetchRecord.getRetryTime());
                    }
                    if (i == 3) {
                        throw new AuthenticationException();
                    }
                } else {
                    throw new UnretriableException(resultFetchRecord.getDescription());
                }
            }
            if (resultFetchRecord.getRecord() != null) {
                JSONObject jSONObject = resultFetchRecord.getRecord().contentJson;
                if (jSONObject != null) {
                    try {
                        return getProfileFromJson(jSONObject);
                    } catch (JSONException e) {
                        throw new UnretriableException(e);
                    }
                }
                throw new UnretriableException("SyncException in getProfile(), profile not exist.");
            }
            throw new UnretriableException("SyncException in getProfile(), record not exist.");
        } catch (SyncException e2) {
            throw new UnretriableException(e2);
        }
    }

    private UserProfile getProfileFromJson(JSONObject jSONObject) throws JSONException {
        UserProfile userProfile = new UserProfile();
        if (jSONObject.has(ba.c)) {
            userProfile.birthday = jSONObject.getLong(ba.c);
        }
        if (jSONObject.has(ba.b)) {
            userProfile.gender = userProfile.getGenderEnum(jSONObject.getInt(ba.b));
        }
        return userProfile;
    }
}
