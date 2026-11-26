package com.xiaomi.opensdk.pdc;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class UserProfile extends Profile {
    static final String KEY_BIRTHDAY = "birthday";
    static final String KEY_GENDER = "gender";
    public long birthday;
    public Gender gender;

    public enum Gender {
        MALE,
        FEMALE
    }

    public UserProfile() {
    }

    public UserProfile(long j, Gender gender) {
        this.birthday = j;
        this.gender = gender;
    }

    @Override // com.xiaomi.opensdk.pdc.Profile
    JSONObject toJsonObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        long j = this.birthday;
        if (j > 0) {
            jSONObject.put("birthday", j);
        }
        Gender gender = this.gender;
        if (gender != null) {
            jSONObject.put("gender", getGenderInt(gender));
        }
        return jSONObject;
    }

    Gender getGenderEnum(int i) {
        if (i == 1) {
            return Gender.MALE;
        }
        if (i == 0) {
            return Gender.FEMALE;
        }
        return null;
    }

    int getGenderInt(Gender gender) {
        return gender == Gender.MALE ? 1 : 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof UserProfile) {
            UserProfile userProfile = (UserProfile) obj;
            if (this.birthday == userProfile.birthday && this.gender.equals(userProfile.gender)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return "Profile [birthday=" + this.birthday + ", gender=" + this.gender + "]";
    }
}
