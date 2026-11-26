package com.xiaomi.accountsdk.account;

/* JADX INFO: loaded from: classes4.dex */
public interface PasswordEncryptor {
    EncryptedValue getEncryptedValue(String str) throws PasswordEncryptorException;

    public static class EncryptedValue {
        public final String encryptedEui;
        public final String encryptedPassword;

        public EncryptedValue(String str, String str2) {
            this.encryptedEui = str;
            this.encryptedPassword = str2;
        }
    }

    public static class PasswordEncryptorException extends Exception {
        public PasswordEncryptorException(String str) {
            super(str);
        }

        public PasswordEncryptorException(String str, Throwable th) {
            super(str, th);
        }

        public PasswordEncryptorException(Throwable th) {
            super(th);
        }
    }
}
