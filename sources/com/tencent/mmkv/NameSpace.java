package com.tencent.mmkv;

/* JADX INFO: loaded from: classes4.dex */
public final class NameSpace {
    private final String rootDir;

    NameSpace(String str) {
        this.rootDir = str;
    }

    public String getRootDir() {
        return this.rootDir;
    }

    public MMKV mmkvWithID(String str) throws RuntimeException {
        return MMKV.checkProcessMode(MMKV.getMMKVWithID(str, 1, null, this.rootDir, 0L), str, 1);
    }

    public MMKV mmkvWithID(String str, int i) throws RuntimeException {
        return MMKV.checkProcessMode(MMKV.getMMKVWithID(str, i, null, this.rootDir, 0L), str, i);
    }

    public MMKV mmkvWithID(String str, int i, long j) throws RuntimeException {
        return MMKV.checkProcessMode(MMKV.getMMKVWithID(str, i, null, this.rootDir, j), str, i);
    }

    public MMKV mmkvWithID(String str, int i, String str2) throws RuntimeException {
        return MMKV.checkProcessMode(MMKV.getMMKVWithID(str, i, str2, this.rootDir, 0L), str, i);
    }

    public MMKV mmkvWithID(String str, int i, String str2, long j) throws RuntimeException {
        return MMKV.checkProcessMode(MMKV.getMMKVWithID(str, i, str2, this.rootDir, j), str, i);
    }

    public boolean backupOneToDirectory(String str, String str2) {
        return MMKV.backupOneToDirectory(str, str2, this.rootDir);
    }

    public boolean restoreOneMMKVFromDirectory(String str, String str2) {
        return MMKV.restoreOneMMKVFromDirectory(str, str2, this.rootDir);
    }

    public boolean isFileValid(String str) {
        return MMKV.isFileValid(str, this.rootDir);
    }

    public boolean removeStorage(String str) {
        return MMKV.removeStorage(str, this.rootDir);
    }

    public boolean checkExist(String str) {
        return MMKV.checkExist(str, this.rootDir);
    }
}
