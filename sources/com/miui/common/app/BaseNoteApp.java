package com.miui.common.app;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import androidx.lifecycle.ViewModelProvider;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.notes.tool.BitmapCacheManager;
import java.util.Iterator;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import miuix.app.Application;
import miuix.autodensity.IDensity;
import miuix.core.util.MiuiBlurUtils;

/* JADX INFO: compiled from: BaseNoteApp.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 &2\u00020\u00012\u00020\u0002:\u0001&B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0013H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0013H\u0016J\b\u0010\u001a\u001a\u00020\u0013H\u0016J\u0010\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u0006H\u0016J\u0006\u0010\u001d\u001a\u00020\u0013J\u0006\u0010\u001e\u001a\u00020\u0013J\u0006\u0010\u001f\u001a\u00020\u0013J\u0010\u0010 \u001a\u00020\u00132\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00132\u0006\u0010$\u001a\u00020%H\u0002R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u001b\u0010\f\u001a\u00020\r8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f¨\u0006'"}, d2 = {"Lcom/miui/common/app/BaseNoteApp;", "Lmiuix/app/Application;", "Lmiuix/autodensity/IDensity;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "value", "", "todoNumLimit", "getTodoNumLimit", "()I", "noteNumLimit", "getNoteNumLimit", "globalSharedViewModel", "Lcom/miui/common/app/ShareViewModel;", "getGlobalSharedViewModel", "()Lcom/miui/common/app/ShareViewModel;", "globalSharedViewModel$delegate", "Lkotlin/Lazy;", "attachBaseContext", "", "base", "Landroid/content/Context;", "onTerminate", "shouldAdaptAutoDensity", "", "onCreate", "onLowMemory", "onTrimMemory", "level", "addTodoNumLimit", "resetTodoNumLimitForFloat", "addNoteNumLimit", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "logDebug", "msg", "", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public class BaseNoteApp extends Application implements IDensity {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int FLOAT_TODO_LIMIT = 20;
    public static final int NOTE_LIMIT_STEP = 1000;
    public static final String TAG = "NoteApp";
    private static final int TODO_LIMIT_STEP = 1000;
    public static long appAttachTime;
    public static boolean isEditInForeground;
    private static boolean isFreeFormScreen;
    public static boolean isInForeground;
    private static boolean isMultiWindowMode;
    public static boolean isTodoInForeground;
    private static BaseNoteApp sInstance;
    private int todoNumLimit = 999;
    private int noteNumLimit = 1000;

    /* JADX INFO: renamed from: globalSharedViewModel$delegate, reason: from kotlin metadata */
    private final Lazy globalSharedViewModel = LazyKt.lazy(new Function0() { // from class: com.miui.common.app.BaseNoteApp$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return BaseNoteApp.globalSharedViewModel_delegate$lambda$0(this.f$0);
        }
    });

    public static final BaseNoteApp getInstance() {
        return INSTANCE.getInstance();
    }

    public final int getTodoNumLimit() {
        return this.todoNumLimit;
    }

    public final int getNoteNumLimit() {
        return this.noteNumLimit;
    }

    public final ShareViewModel getGlobalSharedViewModel() {
        return (ShareViewModel) this.globalSharedViewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ShareViewModel globalSharedViewModel_delegate$lambda$0(BaseNoteApp baseNoteApp) {
        return (ShareViewModel) ViewModelProvider.AndroidViewModelFactory.INSTANCE.getInstance(baseNoteApp).create(ShareViewModel.class);
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d("NoteApp", "attachBaseContext");
        sInstance = this;
        appAttachTime = System.currentTimeMillis();
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        Logger.INSTANCE.d("NoteApp", "onTerminate");
        NotesAutoAppInitiator.INSTANCE.onTerminate(this);
    }

    @Override // miuix.autodensity.IDensity
    public boolean shouldAdaptAutoDensity() {
        return NotesAutoAppInitiator.INSTANCE.shouldAdaptAutoDensity();
    }

    @Override // miuix.app.Application, android.app.Application
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        if (Intrinsics.areEqual(Application.getProcessName(), "com.miui.notes:calc")) {
            HandWritingViewApplication.INSTANCE.init(this);
        }
        NotesAutoAppInitiator.INSTANCE.onCreate(this);
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        logDebug("onLowMemory");
        BitmapCacheManager.getInstance().clear();
    }

    @Override // android.app.Application, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        logDebug("onTrimMemory");
        Iterator<T> it = Thread.getAllStackTraces().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Logger logger = Logger.INSTANCE;
            Object key = entry.getKey();
            Object value = entry.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
            logger.d("NoteApp", ".stackTrace.. " + key + "-" + (!(((Object[]) value).length == 0) ? ((StackTraceElement[]) entry.getValue())[0].getMethodName() : "-- "));
        }
        if (level >= 20) {
            BitmapCacheManager.getInstance().clear();
        }
    }

    public final void addTodoNumLimit() {
        this.todoNumLimit += 1000;
    }

    public final void resetTodoNumLimitForFloat() {
        this.todoNumLimit = 20;
    }

    public final void addNoteNumLimit() {
        this.noteNumLimit += 1000;
    }

    @Override // miuix.app.Application, android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        MiuiBlurUtils.clearEffectEnable();
    }

    private final void logDebug(String msg) {
        Logger.INSTANCE.d("NoteApp", msg);
    }

    /* JADX INFO: compiled from: BaseNoteApp.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000e\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R\u000e\u0010\u0014\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u0012\u0010\u0015\u001a\u00020\u000e8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0016\u001a\u00020\u000e8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0017\u001a\u00020\u000e8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0018\u001a\u00020\u00078FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u0003\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"Lcom/miui/common/app/BaseNoteApp$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "sInstance", "Lcom/miui/common/app/BaseNoteApp;", "appAttachTime", "", "TODO_LIMIT_STEP", "", "FLOAT_TODO_LIMIT", "isMultiWindowMode", "", "()Z", "setMultiWindowMode", "(Z)V", "isFreeFormScreen", "setFreeFormScreen", "NOTE_LIMIT_STEP", "isEditInForeground", "isTodoInForeground", "isInForeground", "instance", "getInstance$annotations", "getInstance", "()Lcom/miui/common/app/BaseNoteApp;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getInstance$annotations() {
        }

        private Companion() {
        }

        public final boolean isMultiWindowMode() {
            return BaseNoteApp.isMultiWindowMode;
        }

        public final void setMultiWindowMode(boolean z) {
            BaseNoteApp.isMultiWindowMode = z;
        }

        public final boolean isFreeFormScreen() {
            return BaseNoteApp.isFreeFormScreen;
        }

        public final void setFreeFormScreen(boolean z) {
            BaseNoteApp.isFreeFormScreen = z;
        }

        public final BaseNoteApp getInstance() {
            BaseNoteApp baseNoteApp = BaseNoteApp.sInstance;
            Intrinsics.checkNotNull(baseNoteApp);
            return baseNoteApp;
        }
    }
}
