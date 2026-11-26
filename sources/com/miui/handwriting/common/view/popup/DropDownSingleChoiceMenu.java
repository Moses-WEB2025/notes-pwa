package com.miui.handwriting.common.view.popup;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.miui.creation.common.R;
import com.miui.libhandwritingpage.utils.UIUtils;
import java.util.Arrays;
import java.util.List;
import miuix.popupwidget.widget.DropDownPopupWindow;

/* JADX INFO: loaded from: classes3.dex */
public class DropDownSingleChoiceMenu implements DropDownPopupWindow.Controller {
    private final String TAG = getClass().getSimpleName();
    private boolean isCustomSelected;
    private boolean isUnlimitedDisable;
    private View mAnchorView;
    private Context mContext;
    private List<String> mItems;
    private OnMenuListener mListener;
    private DropDownPopupWindow mPopupWindow;
    private int mSelectedItem;

    public interface OnMenuListener {
        void onDismiss();

        void onItemSelected(DropDownSingleChoiceMenu dropDownSingleChoiceMenu, int i);

        void onShow();
    }

    @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
    public void onAnimationUpdate(View view, float f) {
    }

    @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
    public void onShow() {
    }

    public void setUnlimitedDisable(boolean z) {
        this.isUnlimitedDisable = z;
    }

    public void setCustomSelected(boolean z) {
        this.isCustomSelected = z;
    }

    public DropDownSingleChoiceMenu(Context context) {
        this.mContext = context;
    }

    public void setItems(List<String> list) {
        this.mItems = list;
    }

    public void setItems(String[] strArr) {
        this.mItems = Arrays.asList(strArr);
    }

    public void setSelectedItem(int i) {
        this.mSelectedItem = i;
    }

    public int getSelectedItem() {
        return this.mSelectedItem;
    }

    public void setAnchorView(View view) {
        this.mAnchorView = view;
        setAccessibilityDelegate(view);
    }

    public List<String> getItems() {
        return this.mItems;
    }

    public void setOnMenuListener(OnMenuListener onMenuListener) {
        this.mListener = onMenuListener;
    }

    public void show() {
        if (this.mItems == null || this.mAnchorView == null) {
            return;
        }
        if (this.mPopupWindow == null) {
            DropDownPopupWindow dropDownPopupWindow = new DropDownPopupWindow(this.mContext, null, 0);
            this.mPopupWindow = dropDownPopupWindow;
            dropDownPopupWindow.setContainerController(new DropDownPopupWindow.DefaultContainerController() { // from class: com.miui.handwriting.common.view.popup.DropDownSingleChoiceMenu.1
                @Override // miuix.popupwidget.widget.DropDownPopupWindow.DefaultContainerController, miuix.popupwidget.widget.DropDownPopupWindow.Controller
                public void onShow() {
                    if (DropDownSingleChoiceMenu.this.mListener != null) {
                        DropDownSingleChoiceMenu.this.mListener.onShow();
                    }
                }

                @Override // miuix.popupwidget.widget.DropDownPopupWindow.DefaultContainerController, miuix.popupwidget.widget.DropDownPopupWindow.Controller
                public void onDismiss() {
                    DropDownSingleChoiceMenu.this.internalDismiss();
                }
            });
            this.mPopupWindow.setDropDownController(this);
            ListView listView = new DropDownPopupWindow.ListController(this.mPopupWindow).getListView();
            listView.setAdapter((ListAdapter) new AnonymousClass2(this.mContext, R.layout.miuix_appcompat_select_dropdown_popup_singlechoice, this.mItems));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.miui.handwriting.common.view.popup.DropDownSingleChoiceMenu.3
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    DropDownSingleChoiceMenu.this.mSelectedItem = i;
                    if (DropDownSingleChoiceMenu.this.mListener != null) {
                        DropDownSingleChoiceMenu.this.mListener.onItemSelected(DropDownSingleChoiceMenu.this, i);
                    }
                }
            });
            listView.setChoiceMode(1);
            listView.setItemChecked(this.mSelectedItem, true);
            this.mPopupWindow.setAnchor(this.mAnchorView);
        }
        this.mPopupWindow.show();
    }

    /* JADX INFO: renamed from: com.miui.handwriting.common.view.popup.DropDownSingleChoiceMenu$2, reason: invalid class name */
    class AnonymousClass2 extends ArrayAdapter<String> {
        AnonymousClass2(Context context, int i, List list) {
            super(context, i, list);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View viewInner = getViewInner(getContext(), getCount(), i, super.getView(i, view, viewGroup));
            viewInner.setForeground(ContextCompat.getDrawable(getContext(), miuix.popupwidget.R.drawable.miuix_popup_window_list_item_fg));
            if (DropDownSingleChoiceMenu.this.isCustomSelected && i == 6) {
                if (UIUtils.isNightMode()) {
                    viewInner.setBackground(ContextCompat.getDrawable(getContext(), miuix.popupwidget.R.color.miuix_appcompat_dropdown_popup_list_item_bg_normal_dark));
                } else {
                    viewInner.setBackground(ContextCompat.getDrawable(getContext(), miuix.popupwidget.R.color.miuix_appcompat_dropdown_popup_list_item_bg_normal_light));
                }
            }
            if (DropDownSingleChoiceMenu.this.isCustomSelected && i == 0 && UIUtils.isNightMode()) {
                viewInner.setBackground(ContextCompat.getDrawable(getContext(), miuix.popupwidget.R.drawable.miuix_popup_window_list_item_fg));
            }
            if (!viewInner.isClickable()) {
                viewInner.setOnHoverListener(new View.OnHoverListener() { // from class: com.miui.handwriting.common.view.popup.DropDownSingleChoiceMenu$2$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnHoverListener
                    public final boolean onHover(View view2, MotionEvent motionEvent) {
                        return DropDownSingleChoiceMenu.AnonymousClass2.lambda$getView$0(view2, motionEvent);
                    }
                });
            }
            TextView textView = (TextView) viewInner.findViewById(R.id.ll1_root);
            if (textView != null && i == 0 && DropDownSingleChoiceMenu.this.isUnlimitedDisable) {
                textView.setTextColor(DropDownSingleChoiceMenu.this.mContext.getResources().getColor(R.color.creation_title_edit_hint, null));
            }
            return viewInner;
        }

        static /* synthetic */ boolean lambda$getView$0(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 9) {
                view.setHovered(true);
            } else if (motionEvent.getAction() == 10) {
                view.setHovered(false);
            }
            return false;
        }

        private View getViewInner(Context context, int i, int i2, View view) {
            int dimensionPixelSize;
            int dimensionPixelSize2;
            view.getLayoutParams();
            int paddingStart = view.getPaddingStart();
            view.getPaddingTop();
            int paddingEnd = view.getPaddingEnd();
            view.getPaddingBottom();
            if (i == 1) {
                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_padding_small);
                dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_padding_small);
            } else if (i2 == 0) {
                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_padding_large);
                dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_padding_small);
            } else if (i2 == i - 1) {
                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_padding_small);
                dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_padding_large);
            } else {
                dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_padding_small);
                dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_drop_down_menu_padding_small);
            }
            view.setPaddingRelative(paddingStart, dimensionPixelSize, paddingEnd, dimensionPixelSize2);
            return view;
        }
    }

    public void dismiss() {
        DropDownPopupWindow dropDownPopupWindow = this.mPopupWindow;
        if (dropDownPopupWindow != null) {
            dropDownPopupWindow.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void internalDismiss() {
        this.mPopupWindow = null;
    }

    @Override // miuix.popupwidget.widget.DropDownPopupWindow.Controller
    public void onDismiss() {
        OnMenuListener onMenuListener = this.mListener;
        if (onMenuListener != null) {
            onMenuListener.onDismiss();
        }
    }

    private void setAccessibilityDelegate(View view) {
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.miui.handwriting.common.view.popup.DropDownSingleChoiceMenu.4
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
                super.onInitializeAccessibilityEvent(view2, accessibilityEvent);
                accessibilityEvent.setClassName(Spinner.class.getName());
            }

            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.setClassName(Spinner.class.getName());
            }
        });
    }
}
