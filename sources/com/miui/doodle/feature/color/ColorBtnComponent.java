package com.miui.doodle.feature.color;

import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorBtnComponent.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0015\u001a\u00020\u0013H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0007@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lcom/miui/doodle/feature/color/ColorBtnComponent;", "Lcom/miui/doodle/feature/color/IColorBtn;", "colorContainer", "Landroid/view/View;", "paletteListener", "Lcom/miui/doodle/feature/color/PaletteListener;", "colorInt", "", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;Lcom/miui/doodle/feature/color/PaletteListener;I)V", "colorContent", "colorContainerAnimator", "Landroid/view/ViewPropertyAnimator;", "value", "getColorInt", "()I", "setColorInt", "(I)V", "showPalette", "", "dismissPalette", "clearViewAnimator", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ColorBtnComponent implements IColorBtn {
    private final View colorContainer;
    private ViewPropertyAnimator colorContainerAnimator;
    private final View colorContent;
    private int colorInt;
    private final PaletteListener paletteListener;

    public ColorBtnComponent(View colorContainer, PaletteListener paletteListener, int i) {
        Intrinsics.checkNotNullParameter(colorContainer, "colorContainer");
        this.colorContainer = colorContainer;
        this.paletteListener = paletteListener;
        this.colorInt = i;
        colorContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.doodle.feature.color.ColorBtnComponent$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.showPalette();
            }
        });
        View viewFindViewById = colorContainer.findViewById(R.id.palette_entry_color);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.colorContent = viewFindViewById;
        viewFindViewById.setClipToOutline(true);
        viewFindViewById.setOutlineProvider(new ViewOutlineProvider() { // from class: com.miui.doodle.feature.color.ColorBtnComponent.2
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(outline, "outline");
                outline.setOval(new Rect(0, 0, view.getWidth(), view.getHeight()));
            }
        });
    }

    public /* synthetic */ ColorBtnComponent(View view, PaletteListener paletteListener, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(view, paletteListener, (i2 & 4) != 0 ? -16777216 : i);
    }

    @Override // com.miui.doodle.feature.color.IColorBtn
    public int getColorInt() {
        return this.colorInt;
    }

    @Override // com.miui.doodle.feature.color.IColorBtn
    public void setColorInt(int i) {
        this.colorInt = i;
        this.colorContent.setBackgroundColor(i);
    }

    @Override // com.miui.doodle.feature.color.IColorBtn
    public void showPalette() {
        ViewPropertyAnimator viewPropertyAnimatorAnimate = this.colorContainer.animate();
        this.colorContainerAnimator = viewPropertyAnimatorAnimate;
        if (viewPropertyAnimatorAnimate != null) {
            viewPropertyAnimatorAnimate.translationX(-this.colorContainer.getWidth()).alpha(0.0f).setDuration(150L).start();
        }
        PaletteListener paletteListener = this.paletteListener;
        if (paletteListener != null) {
            paletteListener.show();
        }
    }

    @Override // com.miui.doodle.feature.color.IColorBtn
    public void dismissPalette() {
        PaletteListener paletteListener = this.paletteListener;
        if (paletteListener != null) {
            paletteListener.dismiss();
        }
    }

    @Override // com.miui.doodle.feature.color.IColorBtn
    public void clearViewAnimator() {
        ViewPropertyAnimator viewPropertyAnimator = this.colorContainerAnimator;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }
}
