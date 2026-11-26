package com.miui.doodle.feature.color;

import android.content.Context;
import android.view.View;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.doodle.R;
import com.miui.notes.base.utils.Logger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ColorGroupPicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/miui/doodle/feature/color/ColorGroupPicker;", "Lcom/miui/doodle/feature/color/IColorGroupPicker;", "view", "Landroid/view/View;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", "colorGroupData", "", "groupIndex", "", "listener", "Lcom/miui/doodle/feature/color/ColorGroupPickListener;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/view/View;Landroid/content/Context;[IILcom/miui/doodle/feature/color/ColorGroupPickListener;)V", "getView", "()Landroid/view/View;", "mColorGroupPickerView", "Lcom/miui/doodle/feature/color/ColorGroupPickerView;", "mColorGroupPickerBar", "Lcom/miui/doodle/feature/color/ColorGroupPickerBar;", "mCurrentGroup", "mColorGroupData", "mPickListener", "setGroup", "", "selectGroupEnd", "selectGroup", "unSelectGroup", "libDoodle_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class ColorGroupPicker implements IColorGroupPicker {
    private int[] mColorGroupData;
    private final ColorGroupPickerBar mColorGroupPickerBar;
    private final ColorGroupPickerView mColorGroupPickerView;
    private int mCurrentGroup;
    private final ColorGroupPickListener mPickListener;
    private final View view;

    @Override // com.miui.doodle.feature.color.IColorGroupPicker
    public void unSelectGroup(int groupIndex) {
    }

    public ColorGroupPicker(View view, Context context, int[] iArr, int i, ColorGroupPickListener colorGroupPickListener) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(context, "context");
        this.view = view;
        View viewFindViewById = getView().findViewById(R.id.color_group_picker_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        ColorGroupPickerView colorGroupPickerView = (ColorGroupPickerView) viewFindViewById;
        this.mColorGroupPickerView = colorGroupPickerView;
        View viewFindViewById2 = getView().findViewById(R.id.color_group_picker);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        ColorGroupPickerBar colorGroupPickerBar = (ColorGroupPickerBar) viewFindViewById2;
        this.mColorGroupPickerBar = colorGroupPickerBar;
        colorGroupPickerBar.setContentDescription(context.getString(R.string.doodle_color_picker_bar));
        this.mCurrentGroup = i;
        this.mColorGroupData = iArr;
        colorGroupPickerBar.setColorData(iArr);
        this.mPickListener = colorGroupPickListener;
        colorGroupPickerView.setData(this.mColorGroupData, this.mCurrentGroup);
        colorGroupPickerView.setGroupPickListener(new ColorGroupPickListener() { // from class: com.miui.doodle.feature.color.ColorGroupPicker.1
            @Override // com.miui.doodle.feature.color.ColorGroupPickListener
            public void onGroupPick(int groupIndex) {
                Logger.INSTANCE.i("ColorGroupPicker", "groupIndex = " + groupIndex);
                ColorGroupPicker.this.selectGroup(groupIndex);
            }

            @Override // com.miui.doodle.feature.color.ColorGroupPickListener
            public void onGroupPickEnd(int groupIndex) {
                Logger.INSTANCE.i("ColorGroupPicker", "onGroupPickEnd: = " + groupIndex);
                ColorGroupPicker.this.selectGroupEnd(groupIndex);
            }
        });
    }

    @Override // com.miui.doodle.feature.color.IColorGroupPicker
    public View getView() {
        return this.view;
    }

    @Override // com.miui.doodle.feature.color.IColorGroupPicker
    public void setGroup(int groupIndex) {
        unSelectGroup(this.mCurrentGroup);
        this.mCurrentGroup = groupIndex;
        this.mColorGroupPickerView.setSelectGroup(groupIndex);
    }

    @Override // com.miui.doodle.feature.color.IColorGroupPicker
    public void selectGroupEnd(int groupIndex) {
        ColorGroupPickListener colorGroupPickListener = this.mPickListener;
        if (colorGroupPickListener != null) {
            colorGroupPickListener.onGroupPickEnd(groupIndex);
        }
    }

    @Override // com.miui.doodle.feature.color.IColorGroupPicker
    public void selectGroup(int groupIndex) {
        unSelectGroup(this.mCurrentGroup);
        this.mCurrentGroup = groupIndex;
        ColorGroupPickListener colorGroupPickListener = this.mPickListener;
        if (colorGroupPickListener != null) {
            colorGroupPickListener.onGroupPick(groupIndex);
        }
    }
}
