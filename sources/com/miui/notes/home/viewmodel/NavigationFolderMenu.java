package com.miui.notes.home.viewmodel;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.model.FolderModel;
import com.xiaomi.mirror.RemoteDeviceInfo;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NavigationFolderMenu.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lcom/miui/notes/home/viewmodel/NavigationFolderMenu;", "", "title", "", "folderList", "", "Lcom/miui/notes/model/FolderModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Ljava/lang/String;Ljava/util/List;)V", "getTitle", "()Ljava/lang/String;", "getFolderList", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class NavigationFolderMenu {
    private final List<FolderModel> folderList;
    private final String title;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ NavigationFolderMenu copy$default(NavigationFolderMenu navigationFolderMenu, String str, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = navigationFolderMenu.title;
        }
        if ((i & 2) != 0) {
            list = navigationFolderMenu.folderList;
        }
        return navigationFolderMenu.copy(str, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    public final List<FolderModel> component2() {
        return this.folderList;
    }

    public final NavigationFolderMenu copy(String title, List<? extends FolderModel> folderList) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(folderList, "folderList");
        return new NavigationFolderMenu(title, folderList);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NavigationFolderMenu)) {
            return false;
        }
        NavigationFolderMenu navigationFolderMenu = (NavigationFolderMenu) other;
        return Intrinsics.areEqual(this.title, navigationFolderMenu.title) && Intrinsics.areEqual(this.folderList, navigationFolderMenu.folderList);
    }

    public int hashCode() {
        return (this.title.hashCode() * 31) + this.folderList.hashCode();
    }

    public String toString() {
        return "NavigationFolderMenu(title=" + this.title + ", folderList=" + this.folderList + ")";
    }

    /* JADX WARN: Multi-variable type inference failed */
    public NavigationFolderMenu(String title, List<? extends FolderModel> folderList) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(folderList, "folderList");
        this.title = title;
        this.folderList = folderList;
    }

    public final List<FolderModel> getFolderList() {
        return this.folderList;
    }

    public final String getTitle() {
        return this.title;
    }
}
