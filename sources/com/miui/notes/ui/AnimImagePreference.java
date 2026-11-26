package com.miui.notes.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceViewHolder;
import ch.qos.logback.core.CoreConstants;
import com.airbnb.lottie.LottieAnimationView;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.notes.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AnimImagePreference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\tR\u0014\u0010\b\u001a\u00020\tX\u0086D¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/miui/notes/ui/AnimImagePreference;", "Landroidx/preference/ListPreference;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "TAG", "", "getTAG", "()Ljava/lang/String;", "mLottieAnimationJson", "onBindViewHolder", "", "holder", "Landroidx/preference/PreferenceViewHolder;", "setLottieAnimationJson", "lottieAnimationJson", "app_PhoneGlobalRelease"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class AnimImagePreference extends ListPreference {
    private final String TAG;
    private String mLottieAnimationJson;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnimImagePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.TAG = "AnimImagePreference";
        setLayoutResource(!RomUtils.isPadDevice() ? R.layout.preference_anim_image : R.layout.pad_preference_anim_image);
    }

    public final String getTAG() {
        return this.TAG;
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onBindViewHolder(holder);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) holder.itemView.findViewById(R.id.guide_img);
        if (getIcon() != null) {
            lottieAnimationView.setImageDrawable(getIcon());
        }
        if (!TextUtils.isEmpty(this.mLottieAnimationJson)) {
            lottieAnimationView.setAnimation(this.mLottieAnimationJson);
        } else {
            Logger.INSTANCE.e(this.TAG, "mLottieAnimationJson is null or empty!!!");
        }
    }

    public final void setLottieAnimationJson(String lottieAnimationJson) {
        Logger.INSTANCE.d(this.TAG, "setLottieAnimationJson=" + this.mLottieAnimationJson);
        this.mLottieAnimationJson = lottieAnimationJson;
    }
}
