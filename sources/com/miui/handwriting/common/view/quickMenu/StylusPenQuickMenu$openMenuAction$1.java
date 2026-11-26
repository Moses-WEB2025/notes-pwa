package com.miui.handwriting.common.view.quickMenu;

import com.miui.handwriting.common.view.quickMenu.animationManager.ActionEnum;
import com.miui.handwriting.common.view.quickMenu.animationManager.ActionEvent;
import com.miui.handwriting.common.view.quickMenu.animationManager.onEventFinished;
import com.miui.handwriting.common.view.quickMenu.base.BaseMenuWrapper;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: StylusPenQuickMenu.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016¨\u0006\u0006"}, d2 = {"com/miui/handwriting/common/view/quickMenu/StylusPenQuickMenu$openMenuAction$1", "Lcom/miui/handwriting/common/view/quickMenu/animationManager/ActionEvent;", "handle", "", CallMethod.ARG_CALLBACK, "Lcom/miui/handwriting/common/view/quickMenu/animationManager/onEventFinished;", "libHandWritingCommon_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class StylusPenQuickMenu$openMenuAction$1 implements ActionEvent {
    final /* synthetic */ StylusPenQuickMenu this$0;

    StylusPenQuickMenu$openMenuAction$1(StylusPenQuickMenu stylusPenQuickMenu) {
        this.this$0 = stylusPenQuickMenu;
    }

    @Override // com.miui.handwriting.common.view.quickMenu.animationManager.ActionEvent
    public void handle(final onEventFinished callback) {
        BaseMenuWrapper baseMenuWrapper = this.this$0.mCurMenuWrapper;
        if (baseMenuWrapper != null) {
            final StylusPenQuickMenu stylusPenQuickMenu = this.this$0;
            baseMenuWrapper.notifyMenuOpened();
            baseMenuWrapper.executeOpenAnimation(new Function0() { // from class: com.miui.handwriting.common.view.quickMenu.StylusPenQuickMenu$openMenuAction$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return StylusPenQuickMenu$openMenuAction$1.handle$lambda$1$lambda$0(callback, stylusPenQuickMenu);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit handle$lambda$1$lambda$0(onEventFinished oneventfinished, StylusPenQuickMenu stylusPenQuickMenu) {
        if (oneventfinished != null) {
            oneventfinished.run();
        }
        stylusPenQuickMenu.mStateMachine.transaction(ActionEnum.ANIMATION_FINISH);
        return Unit.INSTANCE;
    }
}
