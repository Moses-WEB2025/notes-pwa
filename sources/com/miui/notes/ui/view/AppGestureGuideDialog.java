package com.miui.notes.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.miui.common.tool.HapticFeedbackTool;
import com.miui.common.tool.UIUtils;
import com.miui.notes.R;
import com.miui.notes.home.ContentFragment;
import miuix.appcompat.app.AlertDialog;

/* JADX INFO: loaded from: classes4.dex */
public class AppGestureGuideDialog extends AlertDialog {
    private String[] mGuideContent;
    private int mGuideContentIndex;
    TextView mGuideDes;
    LottieAnimationView mGuideView;
    private Button mNegBtn;
    private Button mPosBtn;

    public AppGestureGuideDialog(Context context) {
        super(context);
        this.mGuideContentIndex = 1;
        this.mGuideContent = new String[3];
        initialize();
        setOwnerActivity((Activity) context);
    }

    private void initialize() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_user_guide_dialog_view, (ViewGroup) null);
        setView(viewInflate);
        setTitle(R.string.gesture_guide_title);
        this.mGuideContent[0] = getContext().getString(R.string.note_gesture_guide_step1);
        this.mGuideContent[1] = getContext().getString(R.string.note_gesture_guide_step2);
        this.mGuideContent[2] = getContext().getString(R.string.note_gesture_guide_step3);
        this.mGuideView = (LottieAnimationView) viewInflate.findViewById(R.id.guide_content);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_guide_des);
        this.mGuideDes = textView;
        textView.setText(this.mGuideContent[0]);
        this.mGuideView.setRepeatCount(-1);
        this.mGuideView.setAnimation(getAnimationByIndex(this.mGuideContentIndex));
        this.mGuideDes.setText(this.mGuideContent[this.mGuideContentIndex - 1]);
        setCancelable(false);
        setButton(-1, getContext().getString(R.string.btn_next), (DialogInterface.OnClickListener) null);
        setButton(-2, getContext().getString(R.string.gesture_guide_cancel), new DialogInterface.OnClickListener() { // from class: com.miui.notes.ui.view.AppGestureGuideDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog
    public void show() {
        super.show();
        Button button = getButton(-1);
        this.mPosBtn = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.miui.notes.ui.view.AppGestureGuideDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AppGestureGuideDialog.this.mGuideContentIndex++;
                HapticFeedbackTool.getInstance().performDialogButtonClick(view, true);
                if (AppGestureGuideDialog.this.mGuideContentIndex > 3) {
                    AppGestureGuideDialog.this.dismiss();
                    return;
                }
                AppGestureGuideDialog.this.mGuideDes.setText(AppGestureGuideDialog.this.mGuideContent[AppGestureGuideDialog.this.mGuideContentIndex - 1]);
                LottieAnimationView lottieAnimationView = AppGestureGuideDialog.this.mGuideView;
                AppGestureGuideDialog appGestureGuideDialog = AppGestureGuideDialog.this;
                lottieAnimationView.setAnimation(appGestureGuideDialog.getAnimationByIndex(appGestureGuideDialog.mGuideContentIndex));
                AppGestureGuideDialog.this.mGuideView.playAnimation();
                if (AppGestureGuideDialog.this.mGuideContentIndex == 3) {
                    AppGestureGuideDialog.this.mPosBtn.setText(android.R.string.ok);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAnimationByIndex(int i) {
        if (UIUtils.isNightMode(getContext())) {
            return ContentFragment.ARG_PAGE + i + "_dark.json";
        }
        return ContentFragment.ARG_PAGE + i + ".json";
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
    }
}
