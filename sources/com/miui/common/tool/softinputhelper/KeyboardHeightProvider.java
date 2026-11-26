package com.miui.common.tool.softinputhelper;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.PopupWindow;
import com.miui.notes.R;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes2.dex */
public class KeyboardHeightProvider extends PopupWindow {
    private static final String TAG = "sample_KeyboardHeightProvider";
    private Context context;
    private int keyboardLandscapeHeight;
    private int keyboardPortraitHeight;
    private int lastTimeKeyboardHeight;
    ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    private KeyboardHeightObserver observer;
    private View parentView;
    private View popupView;
    private Window window;

    public KeyboardHeightProvider(Context context, Window window) {
        super(context);
        this.lastTimeKeyboardHeight = 0;
        this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.common.tool.softinputhelper.KeyboardHeightProvider.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                Logger.INSTANCE.i("KHP", " onGlobalLayout  popupView " + KeyboardHeightProvider.this.popupView);
                if (KeyboardHeightProvider.this.popupView != null) {
                    KeyboardHeightProvider.this.handleOnGlobalLayout();
                }
            }
        };
        this.window = window;
        this.context = context;
        View viewInflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.keyboard_height_detector, (ViewGroup) null, false);
        this.popupView = viewInflate;
        setContentView(viewInflate);
        setWindowLayoutType(1003);
        setSoftInputMode(21);
        setInputMethodMode(1);
        this.parentView = window.findViewById(android.R.id.content);
        setWidth(0);
        setHeight(-1);
        this.popupView.getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
    }

    public View getMeasureView() {
        return this.popupView;
    }

    public void start() {
        if (isShowing() || this.parentView.getWindowToken() == null) {
            return;
        }
        setBackgroundDrawable(new ColorDrawable(0));
        showAtLocation(this.parentView, 0, 0, 0);
    }

    public void close() {
        this.observer = null;
        dismiss();
    }

    public void setKeyboardHeightObserver(KeyboardHeightObserver keyboardHeightObserver) {
        this.observer = keyboardHeightObserver;
    }

    private int getScreenOrientation() {
        return this.context.getResources().getConfiguration().orientation;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnGlobalLayout() {
        Point point = new Point();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.window.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        point.x = displayMetrics.widthPixels;
        point.y = displayMetrics.heightPixels;
        Rect rect = new Rect();
        this.popupView.getWindowVisibleDisplayFrame(rect);
        int screenOrientation = getScreenOrientation();
        int i = point.y - rect.bottom;
        if (this.lastTimeKeyboardHeight == i) {
            return;
        }
        this.lastTimeKeyboardHeight = i;
        if (i == 0) {
            notifyKeyboardHeightChanged(0, screenOrientation);
        } else if (screenOrientation == 1) {
            this.keyboardPortraitHeight = i;
            notifyKeyboardHeightChanged(i, screenOrientation);
        } else {
            this.keyboardLandscapeHeight = i;
            notifyKeyboardHeightChanged(i, screenOrientation);
        }
    }

    private void notifyKeyboardHeightChanged(int i, int i2) {
        KeyboardHeightObserver keyboardHeightObserver = this.observer;
        if (keyboardHeightObserver != null) {
            keyboardHeightObserver.onKeyboardHeightChanged(i, i2);
        }
    }

    public void recycle() {
        dismiss();
        this.popupView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        this.observer = null;
        this.window = null;
        this.parentView = null;
        this.popupView = null;
    }
}
