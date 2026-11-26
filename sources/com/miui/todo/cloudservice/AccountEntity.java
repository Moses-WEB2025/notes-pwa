package com.miui.todo.cloudservice;

import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public class AccountEntity implements Serializable {
    private static final long serialVersionUID = 201910281730L;
    private String accountName;
    private String accountType;
    private long id;
    private long lastSyncTime;
    private long syncStatus;
    private String token;
    private long waterMarker;

    AccountEntity() {
    }

    public AccountEntity(long j, String str, String str2, String str3, long j2, long j3, long j4) {
        this.id = j;
        this.accountName = str;
        this.accountType = str2;
        this.token = str3;
        this.waterMarker = j2;
        this.lastSyncTime = j3;
        this.syncStatus = j4;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String str) {
        this.accountName = str;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String str) {
        this.accountType = str;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public long getWaterMarker() {
        return this.waterMarker;
    }

    public void setWaterMarker(long j) {
        this.waterMarker = j;
    }

    public long getLastSyncTime() {
        return this.lastSyncTime;
    }

    public void setLastSyncTime(long j) {
        this.lastSyncTime = j;
    }

    public long getSyncStatus() {
        return this.syncStatus;
    }

    public void setSyncStatus(long j) {
        this.syncStatus = j;
    }
}
