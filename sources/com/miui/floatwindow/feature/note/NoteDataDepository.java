package com.miui.floatwindow.feature.note;

import com.market.sdk.reflect.ReflectUtilsForMiui;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NoteDataDepository.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \n2\u00020\u0001:\u0001\nB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u001b\u0010\u0004\u001a\u00020\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lcom/miui/floatwindow/feature/note/NoteDataDepository;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "dataSource", "Lcom/miui/floatwindow/feature/note/FloatNoteSource;", "getDataSource", "()Lcom/miui/floatwindow/feature/note/FloatNoteSource;", "dataSource$delegate", "Lkotlin/Lazy;", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class NoteDataDepository {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static volatile NoteDataDepository mInstance;

    /* JADX INFO: renamed from: dataSource$delegate, reason: from kotlin metadata */
    private final Lazy dataSource;

    public /* synthetic */ NoteDataDepository(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private NoteDataDepository() {
        this.dataSource = LazyKt.lazy(new Function0() { // from class: com.miui.floatwindow.feature.note.NoteDataDepository$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return NoteDataDepository.dataSource_delegate$lambda$0();
            }
        });
    }

    /* JADX INFO: compiled from: NoteDataDepository.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u00058F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/miui/floatwindow/feature/note/NoteDataDepository$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "mInstance", "Lcom/miui/floatwindow/feature/note/NoteDataDepository;", "instance", "getInstance", "()Lcom/miui/floatwindow/feature/note/NoteDataDepository;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final NoteDataDepository getInstance() {
            if (NoteDataDepository.mInstance == null) {
                synchronized (NoteDataDepository.class) {
                    if (NoteDataDepository.mInstance == null) {
                        Companion companion = NoteDataDepository.INSTANCE;
                        NoteDataDepository.mInstance = new NoteDataDepository(null);
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            return NoteDataDepository.mInstance;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FloatNoteSource dataSource_delegate$lambda$0() {
        return new FloatNoteSource();
    }

    public final FloatNoteSource getDataSource() {
        return (FloatNoteSource) this.dataSource.getValue();
    }
}
