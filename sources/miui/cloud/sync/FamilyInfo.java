package miui.cloud.sync;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes5.dex */
public class FamilyInfo {
    public static final String ROLE_NONE = "None";
    public static final String ROLE_ORGANIZER = "Organizer";
    public static final String ROLE_SHARER = "Sharer";
    public static final String STATUS_NOT_ALLOW_SHARE = "NotAllowShare";
    public static final String STATUS_NOT_SHARING = "NotSharing";
    public static final String STATUS_SHARING = "Sharing";
    public final int memberCount;
    public final String role;
    public final String status;

    public FamilyInfo(String str, String str2) {
        this(str, str2, -1);
    }

    public FamilyInfo(String str, String str2, int i) {
        this.role = str;
        this.status = str2;
        this.memberCount = i;
    }

    public boolean isFamilyOrganizerSharing() {
        return TextUtils.equals(this.role, ROLE_ORGANIZER) && TextUtils.equals(this.status, STATUS_SHARING);
    }

    public boolean isFamilySharerSharing() {
        return TextUtils.equals(this.role, ROLE_SHARER) && TextUtils.equals(this.status, STATUS_SHARING);
    }
}
