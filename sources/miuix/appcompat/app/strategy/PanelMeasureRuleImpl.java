package miuix.appcompat.app.strategy;

import android.util.TypedValue;
import android.view.View;
import miuix.appcompat.app.DialogContract;

/* JADX INFO: loaded from: classes5.dex */
public class PanelMeasureRuleImpl implements IPanelMeasureRule {
    @Override // miuix.appcompat.app.strategy.IPanelMeasureRule
    public TypedValue selectLimitValue(boolean z, boolean z2, int i, DialogContract.ValueList valueList) {
        if (valueList == null) {
            return null;
        }
        if (z) {
            return valueList.getFull();
        }
        if (z2) {
            return valueList.getMinor();
        }
        return i >= 500 ? valueList.getMinor() : valueList.getMajor();
    }

    @Override // miuix.appcompat.app.strategy.IPanelMeasureRule
    public int measurePanelWidth(int i, int i2, int i3) {
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            if (i2 > 0) {
                return View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
            }
            if (i3 > 0) {
                return View.MeasureSpec.makeMeasureSpec(Math.min(i3, View.MeasureSpec.getSize(i)), Integer.MIN_VALUE);
            }
        }
        return i;
    }

    @Override // miuix.appcompat.app.strategy.IPanelMeasureRule
    public int measurePanelHeight(int i, int i2, int i3, int i4, boolean z) {
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            if (i2 > 0) {
                return View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
            }
            if (z) {
                i3 = i4;
            }
            int iMin = Math.min(i3, i4);
            if (iMin > 0) {
                return View.MeasureSpec.makeMeasureSpec(Math.min(iMin, View.MeasureSpec.getSize(i)), Integer.MIN_VALUE);
            }
        }
        return i;
    }
}
