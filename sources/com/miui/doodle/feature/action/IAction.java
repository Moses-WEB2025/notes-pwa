package com.miui.doodle.feature.action;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.stat.NotesTextStat;
import com.miui.doodle.feature.draw.DoodleView;
import kotlin.Metadata;

/* JADX INFO: compiled from: IAction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0007\u001a\u00020\u0003H&J\b\u0010\b\u001a\u00020\u0003H&J\b\u0010\t\u001a\u00020\u0003H&J\b\u0010\n\u001a\u00020\u0003H&¨\u0006\fÀ\u0006\u0003"}, d2 = {"Lcom/miui/doodle/feature/action/IAction;", "", NotesTextStat.UNDO, "", "doodleView", "Lcom/miui/doodle/feature/draw/DoodleView;", NotesTextStat.REDO, "cacheToFile", "restoreFromFile", "releaseJobs", "destroy", "Companion", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public interface IAction {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final int TYPE_ADD = 0;
    public static final int TYPE_ADD_LAYER_BM = 5;
    public static final int TYPE_ERASE = 4;
    public static final int TYPE_REMOVE = 1;
    public static final int TYPE_REMOVE_LAYER_BM = 6;
    public static final int TYPE_REPLACE = 2;
    public static final int TYPE_TRANSFORM = 3;

    void cacheToFile();

    void destroy();

    void redo(DoodleView doodleView);

    void releaseJobs();

    void restoreFromFile();

    void undo(DoodleView doodleView);

    /* JADX INFO: compiled from: IAction.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/miui/doodle/feature/action/IAction$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TYPE_ADD", "", "TYPE_REMOVE", "TYPE_REPLACE", "TYPE_TRANSFORM", "TYPE_ERASE", "TYPE_ADD_LAYER_BM", "TYPE_REMOVE_LAYER_BM", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int TYPE_ADD = 0;
        public static final int TYPE_ADD_LAYER_BM = 5;
        public static final int TYPE_ERASE = 4;
        public static final int TYPE_REMOVE = 1;
        public static final int TYPE_REMOVE_LAYER_BM = 6;
        public static final int TYPE_REPLACE = 2;
        public static final int TYPE_TRANSFORM = 3;

        private Companion() {
        }
    }
}
