package com.miui.common.app;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.base.NoteLoadDataEntity;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;

/* JADX INFO: compiled from: ShareViewModel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0010\t\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lcom/miui/common/app/ShareViewModel;", "Landroidx/lifecycle/ViewModel;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "isInMultiWindowMode", "Landroidx/lifecycle/MutableLiveData;", "", "()Landroidx/lifecycle/MutableLiveData;", "shareEntityData", "Lcom/miui/common/base/NoteLoadDataEntity;", "getShareEntityData", "createEntityData", "getCreateEntityData", "entityIdCountMap", "", "", "", "getEntityIdCountMap", "()Ljava/util/Map;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ShareViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isInMultiWindowMode = new MutableLiveData<>();
    private final MutableLiveData<NoteLoadDataEntity> shareEntityData = new MutableLiveData<>();
    private final MutableLiveData<NoteLoadDataEntity> createEntityData = new MutableLiveData<>();
    private final Map<Long, Integer> entityIdCountMap = new LinkedHashMap();

    public final MutableLiveData<Boolean> isInMultiWindowMode() {
        return this.isInMultiWindowMode;
    }

    public final MutableLiveData<NoteLoadDataEntity> getShareEntityData() {
        return this.shareEntityData;
    }

    public final MutableLiveData<NoteLoadDataEntity> getCreateEntityData() {
        return this.createEntityData;
    }

    public final Map<Long, Integer> getEntityIdCountMap() {
        return this.entityIdCountMap;
    }
}
