package com.miui.notes.tool;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.miui.notes.R;

/* JADX INFO: loaded from: classes4.dex */
public class NotesNotification {
    private Action mAction;
    private boolean mCanCancel;
    private PendingIntent mContentIntent;
    private String mContentText;
    private String mContentTitle;
    private PendingIntent mDeleteIntent;
    private boolean mEnableFloat;
    private int mFloatTime;
    private PendingIntent mFullscreenIntent;
    private boolean mIsAlarm;
    private int mNotificationId;
    private boolean mShouldVibration;
    private int mSmallIcon;
    private Uri mSoundUri;

    public int getNotificationId() {
        return this.mNotificationId;
    }

    public void setNotificationId(int i) {
        this.mNotificationId = i;
    }

    public String getContentTitle() {
        return this.mContentTitle;
    }

    public void setContentTitle(String str) {
        this.mContentTitle = str;
    }

    public String getContentText() {
        return this.mContentText;
    }

    public void setContentText(String str) {
        this.mContentText = str;
    }

    public int getSmallIcon() {
        return this.mSmallIcon;
    }

    public void setSmallIcon(int i) {
        this.mSmallIcon = i;
    }

    public Action getAction() {
        return this.mAction;
    }

    public void setAction(Action action) {
        this.mAction = action;
    }

    public PendingIntent getContentIntent() {
        return this.mContentIntent;
    }

    public void setContentIntent(PendingIntent pendingIntent) {
        this.mContentIntent = pendingIntent;
    }

    public PendingIntent getDeleteIntent() {
        return this.mDeleteIntent;
    }

    public void setDeleteIntent(PendingIntent pendingIntent) {
        this.mDeleteIntent = pendingIntent;
    }

    public PendingIntent getFullscreenIntent() {
        return this.mFullscreenIntent;
    }

    public void setFullscreenIntent(PendingIntent pendingIntent) {
        this.mFullscreenIntent = pendingIntent;
    }

    public Uri getSoundUri() {
        return this.mSoundUri;
    }

    public void setSoundUri(Uri uri) {
        this.mSoundUri = uri;
    }

    public boolean shouldVibration() {
        return this.mShouldVibration;
    }

    public void setShouldVibration(boolean z) {
        this.mShouldVibration = z;
    }

    public boolean isAlarm() {
        return this.mIsAlarm;
    }

    public void setAlarm(boolean z) {
        this.mIsAlarm = z;
    }

    public boolean canCancel() {
        return this.mCanCancel;
    }

    public void setCanCancel(boolean z) {
        this.mCanCancel = z;
    }

    public boolean isEnableFloat() {
        return this.mEnableFloat;
    }

    public void setEnableFloat(boolean z) {
        this.mEnableFloat = z;
    }

    public int getFloatTime() {
        return this.mFloatTime;
    }

    public void setFloatTime(int i) {
        this.mFloatTime = i;
    }

    public static class Builder {
        private Action mAction;
        private boolean mCanCancel;
        private PendingIntent mContentIntent;
        private String mContentText;
        private String mContentTitle;
        private PendingIntent mDeleteIntent;
        private boolean mEnableFloat;
        private int mFloatTime;
        private PendingIntent mFullscreenIntent;
        private boolean mIsAlarm;
        private int mNotificationId;
        private boolean mShouldVibration;
        private int mSmallIcon = R.drawable.icon_app;
        private Uri mSoundUri;

        public Builder contentTitle(String str) {
            this.mContentTitle = str;
            return this;
        }

        public Builder contentText(String str) {
            this.mContentText = str;
            return this;
        }

        public Builder smallIcon(int i) {
            this.mSmallIcon = i;
            return this;
        }

        public Builder contentIntent(PendingIntent pendingIntent) {
            this.mContentIntent = pendingIntent;
            return this;
        }

        public Builder deleteIntent(PendingIntent pendingIntent) {
            this.mDeleteIntent = pendingIntent;
            return this;
        }

        public Builder fullscreenIntent(PendingIntent pendingIntent) {
            this.mFullscreenIntent = pendingIntent;
            return this;
        }

        public Builder action(Action action) {
            this.mAction = action;
            return this;
        }

        public Builder soundUri(Uri uri) {
            this.mSoundUri = uri;
            return this;
        }

        public Builder shouldVibration(boolean z) {
            this.mShouldVibration = z;
            return this;
        }

        public Builder enableFloat(boolean z) {
            this.mEnableFloat = z;
            return this;
        }

        public Builder floatTime(int i) {
            this.mFloatTime = i;
            return this;
        }

        public Builder canCancel(boolean z) {
            this.mCanCancel = z;
            return this;
        }

        public Builder notificationId(int i) {
            this.mNotificationId = i;
            return this;
        }

        public Builder isAlarm(boolean z) {
            this.mIsAlarm = z;
            return this;
        }

        public NotesNotification build() {
            NotesNotification notesNotification = new NotesNotification();
            if (TextUtils.isEmpty(this.mContentTitle)) {
                this.mContentText = "(No title)";
            }
            notesNotification.mContentTitle = this.mContentTitle;
            notesNotification.mContentText = this.mContentText;
            notesNotification.mSmallIcon = this.mSmallIcon;
            notesNotification.mAction = this.mAction;
            notesNotification.mContentIntent = this.mContentIntent;
            notesNotification.mDeleteIntent = this.mDeleteIntent;
            notesNotification.mFullscreenIntent = this.mFullscreenIntent;
            notesNotification.mSoundUri = this.mSoundUri;
            notesNotification.mShouldVibration = this.mShouldVibration;
            notesNotification.mEnableFloat = this.mEnableFloat;
            notesNotification.mFloatTime = this.mFloatTime;
            notesNotification.mCanCancel = this.mCanCancel;
            notesNotification.mNotificationId = this.mNotificationId;
            notesNotification.mIsAlarm = this.mIsAlarm;
            return notesNotification;
        }
    }

    public static class Action {
        private int mIcon = 0;
        private PendingIntent mPendingIntent;
        private String mTitle;

        public static Action valueOf(String str, PendingIntent pendingIntent) {
            Action action = new Action();
            action.mTitle = str;
            action.mPendingIntent = pendingIntent;
            return action;
        }

        public static Action valueOf(Context context, int i, PendingIntent pendingIntent) {
            Action action = new Action();
            action.mTitle = context.getString(i);
            action.mPendingIntent = pendingIntent;
            return action;
        }

        public PendingIntent getPendingIntent() {
            return this.mPendingIntent;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public int getIcon() {
            return this.mIcon;
        }

        public void setIcon(int i) {
            this.mIcon = i;
        }
    }
}
