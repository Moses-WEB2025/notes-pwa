package com.xiaomi.passport;

/* JADX INFO: loaded from: classes4.dex */
public class PassportExternal {
    public static void initEnvironment(PassportUserEnvironment passportUserEnvironment) {
        PassportUserEnvironment.Holder.setInstance(passportUserEnvironment);
    }

    public static PassportUserEnvironment getEnvironment() {
        return PassportUserEnvironment.Holder.getInstance();
    }
}
