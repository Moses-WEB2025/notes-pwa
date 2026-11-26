package com.miui.notes.home.viewmodel;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.xiaomi.mirror.RemoteDeviceInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NavigationFolderMenu.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0017"}, d2 = {"Lcom/miui/notes/home/viewmodel/NavigationMenuData;", "", "stickFolderMenu", "Lcom/miui/notes/home/viewmodel/NavigationFolderMenu;", "normalFolderMenu", "otherFolderMenu", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Lcom/miui/notes/home/viewmodel/NavigationFolderMenu;Lcom/miui/notes/home/viewmodel/NavigationFolderMenu;Lcom/miui/notes/home/viewmodel/NavigationFolderMenu;)V", "getStickFolderMenu", "()Lcom/miui/notes/home/viewmodel/NavigationFolderMenu;", "getNormalFolderMenu", "getOtherFolderMenu", "component1", "component2", "component3", "copy", "equals", "", RemoteDeviceInfo.MANUFACTURER_OTHER, "hashCode", "", "toString", "", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final /* data */ class NavigationMenuData {
    private final NavigationFolderMenu normalFolderMenu;
    private final NavigationFolderMenu otherFolderMenu;
    private final NavigationFolderMenu stickFolderMenu;

    public static /* synthetic */ NavigationMenuData copy$default(NavigationMenuData navigationMenuData, NavigationFolderMenu navigationFolderMenu, NavigationFolderMenu navigationFolderMenu2, NavigationFolderMenu navigationFolderMenu3, int i, Object obj) {
        if ((i & 1) != 0) {
            navigationFolderMenu = navigationMenuData.stickFolderMenu;
        }
        if ((i & 2) != 0) {
            navigationFolderMenu2 = navigationMenuData.normalFolderMenu;
        }
        if ((i & 4) != 0) {
            navigationFolderMenu3 = navigationMenuData.otherFolderMenu;
        }
        return navigationMenuData.copy(navigationFolderMenu, navigationFolderMenu2, navigationFolderMenu3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final NavigationFolderMenu getStickFolderMenu() {
        return this.stickFolderMenu;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final NavigationFolderMenu getNormalFolderMenu() {
        return this.normalFolderMenu;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final NavigationFolderMenu getOtherFolderMenu() {
        return this.otherFolderMenu;
    }

    public final NavigationMenuData copy(NavigationFolderMenu stickFolderMenu, NavigationFolderMenu normalFolderMenu, NavigationFolderMenu otherFolderMenu) {
        Intrinsics.checkNotNullParameter(stickFolderMenu, "stickFolderMenu");
        Intrinsics.checkNotNullParameter(normalFolderMenu, "normalFolderMenu");
        Intrinsics.checkNotNullParameter(otherFolderMenu, "otherFolderMenu");
        return new NavigationMenuData(stickFolderMenu, normalFolderMenu, otherFolderMenu);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NavigationMenuData)) {
            return false;
        }
        NavigationMenuData navigationMenuData = (NavigationMenuData) other;
        return Intrinsics.areEqual(this.stickFolderMenu, navigationMenuData.stickFolderMenu) && Intrinsics.areEqual(this.normalFolderMenu, navigationMenuData.normalFolderMenu) && Intrinsics.areEqual(this.otherFolderMenu, navigationMenuData.otherFolderMenu);
    }

    public int hashCode() {
        return (((this.stickFolderMenu.hashCode() * 31) + this.normalFolderMenu.hashCode()) * 31) + this.otherFolderMenu.hashCode();
    }

    public String toString() {
        return "NavigationMenuData(stickFolderMenu=" + this.stickFolderMenu + ", normalFolderMenu=" + this.normalFolderMenu + ", otherFolderMenu=" + this.otherFolderMenu + ")";
    }

    public NavigationMenuData(NavigationFolderMenu stickFolderMenu, NavigationFolderMenu normalFolderMenu, NavigationFolderMenu otherFolderMenu) {
        Intrinsics.checkNotNullParameter(stickFolderMenu, "stickFolderMenu");
        Intrinsics.checkNotNullParameter(normalFolderMenu, "normalFolderMenu");
        Intrinsics.checkNotNullParameter(otherFolderMenu, "otherFolderMenu");
        this.stickFolderMenu = stickFolderMenu;
        this.normalFolderMenu = normalFolderMenu;
        this.otherFolderMenu = otherFolderMenu;
    }

    public final NavigationFolderMenu getNormalFolderMenu() {
        return this.normalFolderMenu;
    }

    public final NavigationFolderMenu getOtherFolderMenu() {
        return this.otherFolderMenu;
    }

    public final NavigationFolderMenu getStickFolderMenu() {
        return this.stickFolderMenu;
    }
}
