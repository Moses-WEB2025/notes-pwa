package com.miui.notes.tool;

import android.accounts.Account;
import android.app.Activity;
import android.net.Uri;
import com.miui.common.tool.Logger;
import com.miui.notes.NoteApp;
import com.xiaomi.dist.handoff.sdk.Handoff;
import com.xiaomi.dist.handoff.sdk.HandoffSession;
import com.xiaomi.dist.handoff.sdk.callback.DeepLinkCallback;
import com.xiaomi.dist.handoff.sdk.callback.HandoffCallback;
import java.util.function.Supplier;
import miui.accounts.ExtraAccountManager;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes4.dex */
public class HandoffHelper {
    private static final String TAG = "HandoffHelper";
    public static final int TYPE_HOME = 0;
    public static final int TYPE_QUICK_SETTING = 1;
    public static final int TYPE_SETTING = 0;
    public static final int TYPE_VIEW_HANDWRITE = 3;
    public static final int TYPE_VIEW_MIND = 4;
    public static final int TYPE_VIEW_NOTES = 2;
    public static final int TYPE_VIEW_PAD = 5;

    public static HandoffSession onPhoneHomepage(final Activity activity) {
        Logger.INSTANCE.d(TAG, "onPhoneHomepage publish");
        return publishDeepLink(activity, new Supplier() { // from class: com.miui.notes.tool.HandoffHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return HandoffHelper.lambda$onPhoneHomepage$0(activity);
            }
        }, 0);
    }

    static /* synthetic */ Object lambda$onPhoneHomepage$0(Activity activity) {
        String str;
        StringBuilder sb = new StringBuilder("minote://data/notes/");
        Account xiaomiAccount = PreferenceUtils.isCTAAccepted(activity.getApplicationContext()) ? ExtraAccountManager.getXiaomiAccount(NoteApp.getInstance()) : null;
        if (xiaomiAccount == null) {
            str = "-1";
        } else {
            str = xiaomiAccount.name;
        }
        sb.append(str).append("/-1?index=0");
        Logger.INSTANCE.d(TAG, "get deeplink for homepage: ");
        return Uri.parse(sb.toString());
    }

    public static HandoffSession onSettings(Activity activity) {
        Logger.INSTANCE.d(TAG, "Settings publish");
        return publishDeepLink(activity, new Supplier() { // from class: com.miui.notes.tool.HandoffHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return Uri.parse("minote://page/navi/app_setting/");
            }
        }, 0);
    }

    public static HandoffSession onQuickNoteSettings(Activity activity) {
        Logger.INSTANCE.d(TAG, "QuickNoteSettings publish");
        return publishDeepLink(activity, new Supplier() { // from class: com.miui.notes.tool.HandoffHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Uri.parse("minote://page/navi/quick_note_setting/");
            }
        }, 1);
    }

    public static HandoffSession publishDeepLink(Activity activity, Supplier supplier, final int i) {
        if (activity == null || !PreferenceUtils.isCTAAccepted(activity.getApplicationContext())) {
            Logger.INSTANCE.d(TAG, "can't publishDeepLink,with cta not accepted ");
            return null;
        }
        HandoffSession handoffSessionBuild = Handoff.from(activity).setDeepLink(supplier, new DeepLinkCallback() { // from class: com.miui.notes.tool.HandoffHelper.1
            @Override // com.xiaomi.dist.handoff.sdk.callback.DeepLinkCallback
            public byte[] onSaveState() {
                Logger.INSTANCE.d(HandoffHelper.TAG, "onSaveState: " + i);
                return new byte[]{10};
            }

            @Override // com.xiaomi.dist.handoff.sdk.callback.DeepLinkCallback
            public void onRestoreState(byte[] bArr) {
                Logger.INSTANCE.d(HandoffHelper.TAG, "onRestoreState: " + i);
            }
        }).build();
        handoffSessionBuild.publish(new HandoffCallback() { // from class: com.miui.notes.tool.HandoffHelper.2
            @Override // com.xiaomi.dist.handoff.sdk.callback.HandoffCallback
            public void onHandoffDone() {
                Logger.INSTANCE.d(HandoffHelper.TAG, " onHandoffDone : " + i);
            }

            @Override // com.xiaomi.dist.handoff.sdk.callback.HandoffCallback
            public void onError(int i2, String str) {
                Logger.INSTANCE.d(HandoffHelper.TAG, "onQuickNoteSettings onError: " + i2 + StringUtils.SPACE + str + i);
            }
        });
        return handoffSessionBuild;
    }

    public static Uri getNoteDeepLink(long j, long j2) {
        String str;
        StringBuilder sb = new StringBuilder("minote://data/notes/");
        Account xiaomiAccount = PreferenceUtils.isCTAAccepted(NoteApp.getInstance()) ? ExtraAccountManager.getXiaomiAccount(NoteApp.getInstance()) : null;
        if (xiaomiAccount == null) {
            str = "-1";
        } else {
            str = xiaomiAccount.name;
        }
        sb.append(str).append(InternalZipConstants.ZIP_FILE_SEPARATOR).append(j).append("?index=1&folder=").append(j2);
        return Uri.parse(sb.toString());
    }
}
