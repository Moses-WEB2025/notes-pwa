package com.miui.notes.feature.notelist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.app.BaseNoteApp;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.adapter.BindContext;
import com.miui.notes.ainlsquery.AiQueryCapabilityAccess;
import com.miui.notes.cache.ItemCache;
import com.miui.notes.component.BaseItem;
import java.text.NumberFormat;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: AiQueryProcess.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\"\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u000bH\u0016J\b\u0010\u0013\u001a\u00020\u000bH\u0016J\b\u0010\u0014\u001a\u00020\u0011H\u0016J\n\u0010\u0015\u001a\u0004\u0018\u00010\rH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/miui/notes/feature/notelist/AiQueryProcess;", "Lcom/miui/notes/component/BaseItem;", "itemView", "Landroid/view/View;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;)V", "mTextView", "Landroid/widget/TextView;", "mHintImageView", "Landroid/widget/ImageView;", "bind", "", "itemCache", "Lcom/miui/notes/cache/ItemCache;", "bindContext", "Lcom/miui/notes/adapter/BindContext;", "position", "", "onViewAttachedToWindow", "onViewDetachedFromWindow", "getContentType", "getItemCache", "Companion", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AiQueryProcess extends BaseItem {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "Notes:AiQueryProcess";
    private final ImageView mHintImageView;
    private final TextView mTextView;

    @JvmStatic
    public static final AiQueryProcess newInstance(ViewGroup viewGroup) {
        return INSTANCE.newInstance(viewGroup);
    }

    @Override // com.miui.notes.component.BaseItem
    public int getContentType() {
        return 0;
    }

    @Override // com.miui.notes.component.BaseItem
    public ItemCache getItemCache() {
        return null;
    }

    @Override // com.miui.notes.component.BaseItem
    public void onViewAttachedToWindow() {
    }

    @Override // com.miui.notes.component.BaseItem
    public void onViewDetachedFromWindow() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AiQueryProcess(View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        View viewFindViewById = itemView.findViewById(R.id.hint_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.mTextView = (TextView) viewFindViewById;
        View viewFindViewById2 = itemView.findViewById(R.id.hint_image);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.mHintImageView = (ImageView) viewFindViewById2;
    }

    @Override // com.miui.notes.component.BaseItem
    public void bind(ItemCache itemCache, final BindContext bindContext, int position) {
        Intrinsics.checkNotNullParameter(bindContext, "bindContext");
        int mAiQueryDBProgress = bindContext.getMAiQueryDBProgress();
        double d = ((double) mAiQueryDBProgress) / ((double) 100);
        if (bindContext.getIsNewGetProgress()) {
            if (2 == mAiQueryDBProgress) {
                this.mTextView.setText(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.new_ai_search_database_building_pause));
            } else if (1 == mAiQueryDBProgress) {
                this.mTextView.setText(BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.ai_search_database_building_progress));
            }
            this.mHintImageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.feature.notelist.AiQueryProcess$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AiQueryProcess.bind$lambda$0(bindContext, view);
                }
            });
            return;
        }
        TextView textView = this.mTextView;
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String string = BaseNoteApp.INSTANCE.getInstance().getResources().getString(R.string.note_ai_search_database_building_progress);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        String str = String.format(string, Arrays.copyOf(new Object[]{NumberFormat.getPercentInstance().format(d)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        textView.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bind$lambda$0(BindContext bindContext, View view) {
        AiQueryCapabilityAccess.INSTANCE.getInstance().startSearchActivity(bindContext);
    }

    /* JADX INFO: compiled from: AiQueryProcess.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lcom/miui/notes/feature/notelist/AiQueryProcess$Companion;", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "TAG", "", "newInstance", "Lcom/miui/notes/feature/notelist/AiQueryProcess;", "parent", "Landroid/view/ViewGroup;", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final AiQueryProcess newInstance(ViewGroup parent) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            View viewInflateView = UIUtils.inflateView(parent, R.layout.ai_query_process);
            Intrinsics.checkNotNull(viewInflateView);
            return new AiQueryProcess(viewInflateView);
        }
    }
}
