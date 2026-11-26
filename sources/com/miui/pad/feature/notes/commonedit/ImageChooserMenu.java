package com.miui.pad.feature.notes.commonedit;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.miui.common.tool.Logger;
import com.miui.common.tool.RomUtils;
import com.miui.common.tool.UIUtils;
import com.miui.common.tool.Utils;
import com.miui.common.view.IPopMenu;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.base.accessibility.AccessibilityDelegateUtil;
import com.miui.notes.tool.MiuiPlusHelper;
import com.xiaomi.mirror.SynergyDeviceInfo;
import com.xiaomi.mirror.synergy.MiuiSynergySdk;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes4.dex */
public class ImageChooserMenu implements IPopMenu {
    private static String TAG = "ImageChooserMenu";
    private TextView mCamera;
    private final TransitionListener mDismissListener;
    private TextView mGallery;
    private boolean mHighLightDevices;
    private boolean mIsDismissing;
    private final View.OnClickListener mItemClickListener;
    private int mMaxContainerHeight;
    private IPopMenu.OnMenuListener mOnMenuListener;
    private TextView mPhoneCamera;
    private View mRootView;
    private final TransitionListener mShowListener;

    /* JADX INFO: Access modifiers changed from: private */
    public static void restoreAccessibilityForRootViews(ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        int[] iArr = {R.id.ll_note_edit, R.id.header, R.id.toolbar_container, R.id.navigation_decor_wrapper, R.id.fragment_content, R.id.action_bar_container, R.id.root, R.id.note_folder_info, R.id.content_container, R.id.editor_mode_tool_group};
        for (int i = 0; i < 10; i++) {
            View viewFindViewById = viewGroup.findViewById(iArr[i]);
            if (viewFindViewById != null) {
                viewFindViewById.setImportantForAccessibility(1);
            }
        }
    }

    public ImageChooserMenu(Context context) {
        int i;
        this.mIsDismissing = false;
        this.mMaxContainerHeight = 0;
        this.mItemClickListener = new View.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.ImageChooserMenu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.chooser_camera) {
                    if (ImageChooserMenu.this.mOnMenuListener != null) {
                        ImageChooserMenu.this.mOnMenuListener.onItemSelected(view, 1);
                    }
                    ImageChooserMenu.this.dismiss();
                } else if (id == R.id.chooser_gallery) {
                    if (ImageChooserMenu.this.mOnMenuListener != null) {
                        ImageChooserMenu.this.mOnMenuListener.onItemSelected(view, 0);
                    }
                    ImageChooserMenu.this.dismiss();
                } else if (id == R.id.chooser_phone_camera) {
                    if (ImageChooserMenu.this.mOnMenuListener != null) {
                        ImageChooserMenu.this.mOnMenuListener.onItemSelected(view, 2);
                    }
                    ImageChooserMenu.this.dismiss();
                } else {
                    if (ImageChooserMenu.this.mOnMenuListener != null) {
                        ImageChooserMenu.this.mOnMenuListener.onItemSelected(view, 3);
                    }
                    ImageChooserMenu.this.dismiss();
                }
            }
        };
        this.mOnMenuListener = null;
        this.mShowListener = new TransitionListener() { // from class: com.miui.pad.feature.notes.commonedit.ImageChooserMenu.2
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                if (ImageChooserMenu.this.mOnMenuListener != null) {
                    ImageChooserMenu.this.mOnMenuListener.onShow();
                }
            }
        };
        this.mDismissListener = new TransitionListener() { // from class: com.miui.pad.feature.notes.commonedit.ImageChooserMenu.3
            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                ImageChooserMenu.restoreAccessibilityForRootViews((ViewGroup) UIUtils.getActionBarOverlayLayout(ImageChooserMenu.this.mRootView));
                if (ImageChooserMenu.this.mRootView != null) {
                    Logger.INSTANCE.d("ImageChooserMenu", "removeView");
                    ((ViewGroup) ImageChooserMenu.this.mRootView.getParent()).removeView(ImageChooserMenu.this.mRootView);
                }
                if (ImageChooserMenu.this.mOnMenuListener != null) {
                    ImageChooserMenu.this.mOnMenuListener.onDismiss();
                }
            }
        };
        if (!RomUtils.isPadMode()) {
            i = R.layout.phone_note_image_chooser_menu;
        } else if (RomUtils.isFoldDevice()) {
            i = R.layout.fold_note_image_chooser_menu;
        } else {
            i = MiuiPlusHelper.isAboveV3() ? R.layout.pad_note_image_chooser_menu : R.layout.pad_note_image_chooser_menu_no_miui_plus;
        }
        View viewInflate = LayoutInflater.from(context).inflate(i, (ViewGroup) null, false);
        this.mRootView = viewInflate;
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.miui.pad.feature.notes.commonedit.ImageChooserMenu$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        initView(this.mRootView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        dismiss();
    }

    public ImageChooserMenu(Context context, int i, boolean z) {
        this(context);
        this.mMaxContainerHeight = i;
        this.mHighLightDevices = z;
    }

    private void initView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.chooser_gallery);
        this.mGallery = textView;
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(textView);
        this.mGallery.setOnClickListener(this.mItemClickListener);
        TextView textView2 = (TextView) view.findViewById(R.id.chooser_camera);
        this.mCamera = textView2;
        AccessibilityDelegateUtil.setButtonAccessibilityDelegate(textView2);
        this.mCamera.setOnClickListener(this.mItemClickListener);
        if (RomUtils.isFoldDevice()) {
            this.mCamera.setText(view.getContext().getResources().getStringArray(R.array.attachment_chooser)[0]);
        } else if (!RomUtils.isPadMode()) {
            String[] stringArray = view.getContext().getResources().getStringArray(R.array.attachment_chooser);
            this.mCamera.setText(stringArray[0]);
            this.mGallery.setText(stringArray[1]);
        }
        TextView textView3 = (TextView) view.findViewById(R.id.chooser_phone_camera);
        this.mPhoneCamera = textView3;
        if (textView3 != null) {
            textView3.setOnClickListener(this.mItemClickListener);
        }
    }

    private static void setNoHideDescendantsForRootViews(ViewGroup viewGroup) {
        Log.d(TAG, "setNoHideDescendantsForRootViews: rootView = " + viewGroup);
        if (viewGroup == null) {
            return;
        }
        int[] iArr = {R.id.ll_note_edit, R.id.action_bar_container, R.id.header, R.id.editor_mode_tool_group, R.id.note_folder_info};
        for (int i = 0; i < 5; i++) {
            View viewFindViewById = viewGroup.findViewById(iArr[i]);
            Log.d(TAG, "setNoHideDescendantsForRootViews: " + viewFindViewById);
            if (viewFindViewById != null) {
                viewFindViewById.setImportantForAccessibility(4);
            }
        }
    }

    @Override // com.miui.common.view.IPopMenu
    public void show(final View view, int[] iArr) {
        Logger.INSTANCE.d(TAG, "show insert img dialog ");
        ViewGroup viewGroup = (ViewGroup) UIUtils.getActionBarOverlayLayout(view);
        setNoHideDescendantsForRootViews(viewGroup);
        this.mRootView.setAlpha(0.0f);
        if (this.mRootView.getParent() != null) {
            ((ViewGroup) this.mRootView.getParent()).removeView(this.mRootView);
        }
        boolean z = RomUtils.isPadDevice() && Utils.isSupportLyra();
        boolean z2 = RomUtils.isPadDevice() && MiuiSynergySdk.getInstance().isSupportTakePhoto(this.mRootView.getContext(), z);
        Logger.INSTANCE.d(TAG, " isSupportLyra: " + z + "  canTakePhoto: " + z2);
        if (!z2) {
            TextView textView = this.mPhoneCamera;
            if (textView != null) {
                textView.setVisibility(8);
                this.mPhoneCamera.setEnabled(false);
            }
        } else if (z) {
            this.mCamera.setText(NoteApp.getInstance().getResources().getString(R.string.insert_image_menu_take_photo_by_local_device));
            TextView textView2 = this.mPhoneCamera;
            if (textView2 != null) {
                textView2.setEnabled(false);
                this.mPhoneCamera.setVisibility(8);
            }
            List<SynergyDeviceInfo> synergyDevices = MiuiSynergySdk.getInstance().getSynergyDevices(NoteApp.getInstance());
            if (synergyDevices != null) {
                int size = synergyDevices.size();
                Logger.INSTANCE.d(TAG, "getSynergyDevices, size: " + size);
                int i = 0;
                while (i < size) {
                    SynergyDeviceInfo synergyDeviceInfo = synergyDevices.get(i);
                    addCameraMenu(synergyDeviceInfo.getDisplayName(), synergyDeviceInfo.getDeviceId(), i == size + (-1));
                    i++;
                }
            }
        } else {
            TextView textView3 = this.mPhoneCamera;
            if (textView3 != null) {
                textView3.setEnabled(true);
            }
        }
        viewGroup.addView(this.mRootView, new ViewGroup.LayoutParams(-1, -1));
        this.mRootView.post(new Runnable() { // from class: com.miui.pad.feature.notes.commonedit.ImageChooserMenu$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$show$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$1(View view) {
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        view.getGlobalVisibleRect(rect);
        view.getLocalVisibleRect(rect2);
        int width = this.mRootView.findViewById(R.id.content).getWidth();
        int height = this.mRootView.findViewById(R.id.content).getHeight();
        int i = this.mMaxContainerHeight;
        if (i != 0 && height > i) {
            View viewFindViewById = this.mRootView.findViewById(R.id.content);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewFindViewById.getLayoutParams();
            layoutParams.height = this.mMaxContainerHeight;
            viewFindViewById.setLayoutParams(layoutParams);
            height = this.mMaxContainerHeight;
            Logger.INSTANCE.d(TAG, "set mMaxContainerHeight = " + this.mMaxContainerHeight);
        }
        int iWidth = (rect.right - (rect.width() / 2)) - (width / 2);
        int i2 = this.mRootView.getContext().getResources().getConfiguration().orientation;
        if (iWidth < 100) {
            iWidth = i2 == 2 ? this.mRootView.getContext().getResources().getDimensionPixelSize(R.dimen.pop_menu_left_size) : 100;
        }
        int width2 = this.mRootView.getRootView().getWidth();
        int height2 = this.mRootView.getRootView().getHeight();
        int i3 = iWidth + width;
        if (i3 > width2) {
            iWidth = (iWidth - (i3 - width2)) - UIUtils.dip2px(this.mRootView.getContext(), 10.0f);
        }
        this.mRootView.findViewById(R.id.content).setX(iWidth);
        Logger.INSTANCE.d(TAG, "run:anchorTop = " + rect.top);
        int iDip2px = (rect.top - height) - UIUtils.dip2px(this.mRootView.getContext(), 13.0f);
        int i4 = iDip2px + height;
        Logger.INSTANCE.d(TAG, "show:displayTop = " + iDip2px + " displayBottom = " + i4);
        if (i4 > height2) {
            iDip2px = (iDip2px - (i4 - height2)) - UIUtils.dip2px(this.mRootView.getContext(), 60.0f);
        }
        Logger.INSTANCE.d(TAG, "show: displayTop = " + iDip2px + " displayBottom = " + i4 + " windowHeight = " + height2 + " height = " + height);
        if (iDip2px < 50) {
            iDip2px = 50;
        }
        this.mRootView.findViewById(R.id.content).setY(iDip2px);
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(1.0f), new AnimConfig().addListeners(this.mShowListener));
        LottieAnimationView lottieAnimationView = (LottieAnimationView) this.mRootView.findViewById(R.id.test_id);
        if (lottieAnimationView != null) {
            if (Utils.isRTL()) {
                lottieAnimationView.setTranslationX(this.mRootView.findViewById(R.id.content).getTranslationX());
                lottieAnimationView.setTranslationY((this.mRootView.findViewById(R.id.content).getTranslationY() + height) - lottieAnimationView.getHeight());
            } else {
                lottieAnimationView.setTranslationX((this.mRootView.findViewById(R.id.content).getTranslationX() + width) - lottieAnimationView.getWidth());
                lottieAnimationView.setTranslationY((this.mRootView.findViewById(R.id.content).getTranslationY() + height) - lottieAnimationView.getHeight());
            }
        }
    }

    @Override // com.miui.common.view.IPopMenu
    public void dismiss() {
        if (this.mIsDismissing) {
            return;
        }
        Logger.INSTANCE.d("ImageChooserMenu", "dismiss");
        this.mIsDismissing = true;
        Folme.useAt(this.mRootView).state().to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().addListeners(this.mDismissListener));
    }

    @Override // com.miui.common.view.IPopMenu
    public void setOnMenuListener(IPopMenu.OnMenuListener onMenuListener) {
        this.mOnMenuListener = onMenuListener;
    }

    private void addCameraMenu(String str, String str2, boolean z) {
        LottieAnimationView lottieAnimationView;
        if (str == null || str2 == null) {
            return;
        }
        LinearLayout linearLayout = (LinearLayout) this.mRootView.findViewById(R.id.menu_container);
        TextView textView = new TextView(this.mRootView.getContext());
        textView.setTag(R.id.device_id, str2);
        textView.setTag(R.id.device_name, str);
        textView.setText(String.format(NoteApp.getInstance().getString(R.string.insert_image_menu_take_photo_by_remote_device), str));
        textView.setTypeface(this.mGallery.getTypeface());
        textView.setTextSize(0, NoteApp.getInstance().getResources().getDimension(R.dimen.pop_menu_item_text_size));
        if (z) {
            textView.setBackgroundResource(this.mHighLightDevices ? R.drawable.pad_note_pop_menu_item_foot_bg_high_light : R.drawable.pad_note_pop_menu_item_foot_bg);
        } else {
            textView.setBackgroundResource(R.drawable.pad_note_pop_menu_item_bg);
        }
        textView.setTextColor((this.mHighLightDevices && z) ? NoteApp.getInstance().getResources().getColor(R.color.pad_popmenu_item_common_color_hight_light) : NoteApp.getInstance().getResources().getColor(R.color.pad_note_popmenu_item_text_color));
        textView.setPadding(this.mCamera.getPaddingLeft(), this.mCamera.getPaddingTop(), (this.mHighLightDevices && z) ? this.mCamera.getPaddingRight() + 80 : this.mCamera.getPaddingRight(), z ? this.mGallery.getPaddingTop() : this.mCamera.getPaddingBottom());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 8388627;
        linearLayout.addView(textView, layoutParams);
        if (this.mHighLightDevices && z && (lottieAnimationView = (LottieAnimationView) this.mRootView.findViewById(R.id.test_id)) != null) {
            lottieAnimationView.setVisibility(0);
            lottieAnimationView.setRepeatCount(-1);
            lottieAnimationView.playAnimation();
        }
        textView.setOnClickListener(this.mItemClickListener);
    }
}
