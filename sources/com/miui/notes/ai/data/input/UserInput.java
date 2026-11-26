package com.miui.notes.ai.data.input;

/* JADX INFO: loaded from: classes4.dex */
public class UserInput {
    private String mContentInput;
    private String mEditInput;
    private PolishStyle mPolishStyle;
    private RequestType mRequestType;
    private volatile UserStatus mUserStatus;

    public String getContentInput() {
        return this.mContentInput;
    }

    public void setContentInput(String str) {
        this.mContentInput = str;
    }

    public String getEditInput() {
        return this.mEditInput;
    }

    public void setEditInput(String str) {
        this.mEditInput = str;
    }

    public RequestType getRequestType() {
        return this.mRequestType;
    }

    public void setRequestType(RequestType requestType) {
        this.mRequestType = requestType;
    }

    public UserStatus getUserStatus() {
        return this.mUserStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.mUserStatus = userStatus;
    }

    public PolishStyle getPolishStyle() {
        return this.mPolishStyle;
    }

    public void setPolishStyle(PolishStyle polishStyle) {
        this.mPolishStyle = polishStyle;
    }

    public UserInput() {
        this.mContentInput = "";
        this.mEditInput = "";
        this.mRequestType = RequestType.TYPE_UNKNOWN;
        this.mPolishStyle = PolishStyle.TYPE_UNKNOWN;
        this.mUserStatus = UserStatus.NOTREADY;
    }

    public UserInput(String str, String str2, RequestType requestType, UserStatus userStatus) {
        this.mContentInput = "";
        this.mEditInput = "";
        this.mRequestType = RequestType.TYPE_UNKNOWN;
        this.mPolishStyle = PolishStyle.TYPE_UNKNOWN;
        this.mUserStatus = UserStatus.NOTREADY;
        this.mContentInput = str;
        this.mEditInput = str2;
        this.mRequestType = requestType;
        this.mUserStatus = userStatus;
    }

    public UserInput(String str, String str2, RequestType requestType, UserStatus userStatus, PolishStyle polishStyle) {
        this.mContentInput = "";
        this.mEditInput = "";
        this.mRequestType = RequestType.TYPE_UNKNOWN;
        this.mPolishStyle = PolishStyle.TYPE_UNKNOWN;
        this.mUserStatus = UserStatus.NOTREADY;
        this.mContentInput = str;
        this.mEditInput = str2;
        this.mRequestType = requestType;
        this.mUserStatus = userStatus;
        this.mPolishStyle = polishStyle;
    }

    public void resetUserInput() {
        this.mContentInput = null;
        this.mEditInput = null;
        this.mUserStatus = UserStatus.NOTREADY;
        this.mRequestType = RequestType.TYPE_UNKNOWN;
        this.mPolishStyle = PolishStyle.TYPE_UNKNOWN;
    }
}
