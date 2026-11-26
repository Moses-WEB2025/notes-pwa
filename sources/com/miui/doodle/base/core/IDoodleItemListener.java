package com.miui.doodle.base.core;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Metadata;

/* JADX INFO: compiled from: IDoodleItemListener.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007À\u0006\u0003"}, d2 = {"Lcom/miui/doodle/base/core/IDoodleItemListener;", "", "onPropertyChanged", "", "property", "", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IDoodleItemListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final int PROPERTY_COLOR = 6;
    public static final int PROPERTY_LOCATION = 7;
    public static final int PROPERTY_PIVOT_POINT = 3;
    public static final int PROPERTY_ROTATE = 2;
    public static final int PROPERTY_SCALE = 1;
    public static final int PROPERTY_SIZE = 5;

    void onPropertyChanged(int property);

    /* JADX INFO: compiled from: IDoodleItemListener.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/miui/doodle/base/core/IDoodleItemListener$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "PROPERTY_SCALE", "", "PROPERTY_ROTATE", "PROPERTY_PIVOT_POINT", "PROPERTY_SIZE", "PROPERTY_COLOR", "PROPERTY_LOCATION", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int PROPERTY_COLOR = 6;
        public static final int PROPERTY_LOCATION = 7;
        public static final int PROPERTY_PIVOT_POINT = 3;
        public static final int PROPERTY_ROTATE = 2;
        public static final int PROPERTY_SCALE = 1;
        public static final int PROPERTY_SIZE = 5;

        private Companion() {
        }
    }
}
