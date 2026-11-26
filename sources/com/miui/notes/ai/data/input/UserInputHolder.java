package com.miui.notes.ai.data.input;

/* JADX INFO: loaded from: classes4.dex */
public class UserInputHolder {
    private static final UserInput INSTANCE = new UserInput();

    public static UserInput getInstance() {
        return INSTANCE;
    }

    public static void resetUserInputInstance() {
        INSTANCE.resetUserInput();
    }

    public static void setContentInput(String str) {
        INSTANCE.setContentInput(str);
    }

    public static void setEditInput(String str) {
        INSTANCE.setEditInput(str);
    }

    public static void setRequestType(RequestType requestType) {
        INSTANCE.setRequestType(requestType);
    }

    public static void setUserStatus(UserStatus userStatus) {
        INSTANCE.setUserStatus(userStatus);
    }

    public static void setPolishStyle(PolishStyle polishStyle) {
        INSTANCE.setPolishStyle(polishStyle);
    }
}
