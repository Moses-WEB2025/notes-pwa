package com.miui.notes.tool;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.miui.notes.base.utils.Logger;

/* JADX INFO: loaded from: classes4.dex */
public class DoubleClickDetector extends GestureDetector {
    private static final String TAG = "DoubleClickDetector";
    private DoubleClickListener mDoubleClickListener;

    public interface DoubleClickListener {
        void onDoubleClick(MotionEvent motionEvent);
    }

    public DoubleClickDetector(Context context) {
        super(context, new GestureDetector.SimpleOnGestureListener());
        setOnDoubleTapListener();
    }

    private void setOnDoubleTapListener() {
        setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() { // from class: com.miui.notes.tool.DoubleClickDetector.1
            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (DoubleClickDetector.this.mDoubleClickListener != null) {
                    DoubleClickDetector.this.mDoubleClickListener.onDoubleClick(motionEvent);
                    return false;
                }
                Logger.INSTANCE.e(DoubleClickDetector.TAG, "DoubleClickListener should not be null.");
                return false;
            }
        });
    }

    public void setDoubleClickListener(DoubleClickListener doubleClickListener) {
        this.mDoubleClickListener = doubleClickListener;
    }

    public void register(View view, DoubleClickListener doubleClickListener) {
        setDoubleClickListener(doubleClickListener);
        register(view);
    }

    public void register(View view) {
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.notes.tool.DoubleClickDetector.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                DoubleClickDetector.this.onTouchEvent(motionEvent);
                return true;
            }
        });
    }
}
