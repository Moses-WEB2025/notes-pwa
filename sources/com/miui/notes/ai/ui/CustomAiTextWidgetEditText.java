package com.miui.notes.ai.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.ArrowKeyMovementMethod;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.widget.EditText;
import android.widget.TextView;
import ch.qos.logback.core.CoreConstants;
import com.market.sdk.reflect.ReflectUtilsForMiui;
import com.miui.notes.ai.utils.EditModeUtils;
import com.miui.notes.base.utils.Logger;
import com.xiaomi.mirror.synergy.CallMethod;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CustomAiTextWidgetEditText.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 -2\u00020\u0001:\u0002,-B\u0013\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\u0004\u0010\bB%\b\u0016\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u0004\u0010\u000bJ\b\u0010\"\u001a\u00020#H\u0002J\u001a\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010(\u001a\u00020\nH\u0016J\u0010\u0010)\u001a\u00020#2\u0006\u0010*\u001a\u00020+H\u0015R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lcom/miui/notes/ai/ui/CustomAiTextWidgetEditText;", "Landroid/widget/EditText;", CoreConstants.CONTEXT_SCOPE_VALUE, "Landroid/content/Context;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "highlightPaint", "Landroid/graphics/Paint;", "highlightColorStart", "highlightColorEnd", "layout", "Landroid/text/Layout;", "selectionStart", "selectionEnd", "scrollY", "lineStart", "lineEnd", "selectedLeft", "", "selectedRight", "selectedTop", "selectedBottom", "shader", "Landroid/graphics/LinearGradient;", "lineBounds", "Landroid/graphics/Rect;", "mEditModeUtils", "Lcom/miui/notes/ai/utils/EditModeUtils;", "init", "", "startActionMode", "Landroid/view/ActionMode;", CallMethod.ARG_CALLBACK, "Landroid/view/ActionMode$Callback;", "type", "onDraw", "canvas", "Landroid/graphics/Canvas;", "GradientHighlightMovementMethod", "Companion", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
public final class CustomAiTextWidgetEditText extends EditText {
    private static final String TAG = "CustomAiTextWidgetEditT";
    public static boolean isCotMode;
    public static int padding;
    public static int viewMaxHeight;
    private final int highlightColorEnd;
    private final int highlightColorStart;
    private Paint highlightPaint;
    private Layout layout;
    private final Rect lineBounds;
    private int lineEnd;
    private int lineStart;
    private EditModeUtils mEditModeUtils;
    private int scrollY;
    private int selectedBottom;
    private float selectedLeft;
    private float selectedRight;
    private int selectedTop;
    private int selectionEnd;
    private int selectionStart;
    private LinearGradient shader;

    public CustomAiTextWidgetEditText(Context context) {
        super(context);
        this.highlightColorStart = 1726449151;
        this.highlightColorEnd = 1717085695;
        this.lineBounds = new Rect();
        if (context != null) {
            init();
        }
    }

    public CustomAiTextWidgetEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.highlightColorStart = 1726449151;
        this.highlightColorEnd = 1717085695;
        this.lineBounds = new Rect();
        if (context != null) {
            init();
        }
    }

    public CustomAiTextWidgetEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.highlightColorStart = 1726449151;
        this.highlightColorEnd = 1717085695;
        this.lineBounds = new Rect();
        if (context != null) {
            init();
        }
    }

    private final void init() {
        this.highlightPaint = new Paint();
        setMovementMethod(new GradientHighlightMovementMethod());
        setHighlightColor(0);
    }

    @Override // android.view.View
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        if (EditModeUtils.supportHook(callback, type)) {
            if (this.mEditModeUtils == null) {
                this.mEditModeUtils = new EditModeUtils();
            }
            EditModeUtils editModeUtils = this.mEditModeUtils;
            Intrinsics.checkNotNull(editModeUtils);
            ActionMode actionModeCreateActionMode = editModeUtils.createActionMode(type, callback, this);
            if (actionModeCreateActionMode != null && callback != null && callback.onCreateActionMode(actionModeCreateActionMode, actionModeCreateActionMode.getMenu())) {
                actionModeCreateActionMode.invalidate();
                return actionModeCreateActionMode;
            }
        }
        ActionMode actionModeStartActionMode = super.startActionMode(callback, type);
        Intrinsics.checkNotNullExpressionValue(actionModeStartActionMode, "startActionMode(...)");
        return actionModeStartActionMode;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00f9  */
    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        float lineLeft;
        float lineRight;
        Canvas canvas2;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        Logger.INSTANCE.d(TAG, "onDraw viewMaxHeight: " + viewMaxHeight + " padding : " + padding);
        this.layout = getLayout();
        this.selectionStart = getSelectionStart();
        this.selectionEnd = getSelectionEnd();
        this.scrollY = getScrollY() + padding;
        int i = this.selectionStart;
        if (i < 0 || this.selectionEnd <= i) {
            super.onDraw(canvas);
            return;
        }
        Layout layout = this.layout;
        Intrinsics.checkNotNull(layout);
        this.lineStart = layout.getLineForOffset(this.selectionStart);
        Layout layout2 = this.layout;
        Intrinsics.checkNotNull(layout2);
        int lineForOffset = layout2.getLineForOffset(this.selectionEnd);
        this.lineEnd = lineForOffset;
        int i2 = this.lineStart;
        if (i2 > lineForOffset) {
            return;
        }
        while (true) {
            Layout layout3 = this.layout;
            Intrinsics.checkNotNull(layout3);
            layout3.getLineBounds(i2, this.lineBounds);
            if (i2 == this.lineStart) {
                Layout layout4 = this.layout;
                Intrinsics.checkNotNull(layout4);
                lineLeft = layout4.getPrimaryHorizontal(this.selectionStart);
            } else {
                Layout layout5 = this.layout;
                Intrinsics.checkNotNull(layout5);
                lineLeft = layout5.getLineLeft(i2);
            }
            this.selectedLeft = lineLeft;
            if (i2 == this.lineEnd) {
                Layout layout6 = this.layout;
                Intrinsics.checkNotNull(layout6);
                lineRight = layout6.getPrimaryHorizontal(this.selectionEnd);
            } else {
                Layout layout7 = this.layout;
                Intrinsics.checkNotNull(layout7);
                lineRight = layout7.getLineRight(i2);
            }
            this.selectedRight = lineRight;
            boolean z = isCotMode;
            float f = this.selectedLeft;
            if (z) {
                f += padding;
            }
            this.selectedLeft = f;
            if (z) {
                lineRight += padding;
            }
            this.selectedRight = lineRight;
            Layout layout8 = this.layout;
            Intrinsics.checkNotNull(layout8);
            this.selectedTop = layout8.getLineTop(i2) + padding;
            Layout layout9 = this.layout;
            Intrinsics.checkNotNull(layout9);
            int lineBottom = layout9.getLineBottom(i2) + padding;
            this.selectedBottom = lineBottom;
            int i3 = this.selectedTop;
            int i4 = this.scrollY;
            if ((i3 > i4 || i4 >= lineBottom) && (i3 <= i4 || lineBottom >= viewMaxHeight + i4)) {
                int i5 = i3 + 1;
                int i6 = i4 + viewMaxHeight;
                if (i5 > i6 || i6 > lineBottom) {
                    canvas2 = canvas;
                } else {
                    this.shader = new LinearGradient(this.selectedLeft, 0.0f, this.selectedRight, 0.0f, this.highlightColorStart, this.highlightColorEnd, Shader.TileMode.CLAMP);
                    Paint paint = this.highlightPaint;
                    Intrinsics.checkNotNull(paint);
                    paint.setShader(this.shader);
                    float f2 = this.selectedLeft;
                    float fMax = (float) Math.max(this.selectedTop, this.scrollY);
                    float f3 = this.selectedRight;
                    float fMin = (float) Math.min(this.selectedBottom, this.scrollY + viewMaxHeight);
                    Paint paint2 = this.highlightPaint;
                    Intrinsics.checkNotNull(paint2);
                    canvas2 = canvas;
                    canvas2.drawRect(f2, fMax, f3, fMin, paint2);
                }
            } else {
                this.shader = new LinearGradient(this.selectedLeft, 0.0f, this.selectedRight, 0.0f, this.highlightColorStart, this.highlightColorEnd, Shader.TileMode.CLAMP);
                Paint paint3 = this.highlightPaint;
                Intrinsics.checkNotNull(paint3);
                paint3.setShader(this.shader);
                float f4 = this.selectedLeft;
                float fMax2 = (float) Math.max(this.selectedTop, this.scrollY);
                float f5 = this.selectedRight;
                float fMin2 = (float) Math.min(this.selectedBottom, this.scrollY + viewMaxHeight);
                Paint paint4 = this.highlightPaint;
                Intrinsics.checkNotNull(paint4);
                canvas2 = canvas;
                canvas2.drawRect(f4, fMax2, f5, fMin2, paint4);
            }
            if (i2 == lineForOffset) {
                return;
            }
            i2++;
            canvas = canvas2;
        }
    }

    /* JADX INFO: compiled from: CustomAiTextWidgetEditText.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/miui/notes/ai/ui/CustomAiTextWidgetEditText$GradientHighlightMovementMethod;", "Landroid/text/method/ArrowKeyMovementMethod;", ReflectUtilsForMiui.OBJECT_CONSTRUCTOR, "()V", "onTakeFocus", "", "view", "Landroid/widget/TextView;", "text", "Landroid/text/Spannable;", "dir", "", "libAiTextWidget_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    private static final class GradientHighlightMovementMethod extends ArrowKeyMovementMethod {
        @Override // android.text.method.ArrowKeyMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
        public void onTakeFocus(TextView view, Spannable text, int dir) {
            Intrinsics.checkNotNullParameter(view, "view");
            Intrinsics.checkNotNullParameter(text, "text");
            super.onTakeFocus(view, text, dir);
            view.invalidate();
        }
    }
}
