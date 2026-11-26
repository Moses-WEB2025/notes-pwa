package com.miui.notes.ai;

import android.view.View;
import android.widget.TextView;
import com.miui.common.tool.RomUtils;
import com.miui.notes.NoteApp;
import com.miui.notes.R;
import com.miui.notes.ai.request.LanguageItem;
import com.miui.notes.base.utils.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import miuix.popupwidget.widget.DropDownSingleChoiceMenu;

/* JADX INFO: loaded from: classes4.dex */
public class AiTranslateController implements View.OnClickListener {
    private static int ACTION_SRC = 0;
    private static int ACTION_TARGET = 1;
    private static final String TAG = "AiTranslateController";
    private DropDownSingleChoiceMenu mDropDownSingleChoiceMenu;
    private ItemSelectedListener mItemSelectedListener;
    private View mRootView;
    private String mSrcCode;
    private TextView mSrcLangTextView;
    private String mTargetCode;
    private TextView mTargetLangTextView;
    private View mTranslateViewAfter;
    private View mTranslateViewBefore;
    private int mSrcIndex = -1;
    private int mTargetIndex = -1;
    private List<String> mLangCodeList = new ArrayList();
    private List<String> mSrcLangNameList = new ArrayList();
    private List<String> mTargetLangNameList = new ArrayList();
    private int mActionSelected = 0;

    interface ItemSelectedListener {
        void onItemSelected(String str, String str2);
    }

    public AiTranslateController() {
    }

    public void setAndInitView(View view) {
        this.mRootView = view;
        this.mTranslateViewBefore = view.findViewById(R.id.translate_src);
        this.mSrcLangTextView = (TextView) view.findViewById(R.id.src_language);
        this.mTranslateViewAfter = view.findViewById(R.id.translate_target);
        this.mTargetLangTextView = (TextView) view.findViewById(R.id.target_language);
        this.mTranslateViewBefore.setOnClickListener(this);
        this.mTranslateViewAfter.setOnClickListener(this);
    }

    public AiTranslateController(View view) {
        this.mRootView = view;
        this.mTranslateViewBefore = view.findViewById(R.id.translate_src);
        this.mSrcLangTextView = (TextView) view.findViewById(R.id.src_language);
        this.mTranslateViewAfter = view.findViewById(R.id.translate_target);
        this.mTargetLangTextView = (TextView) view.findViewById(R.id.target_language);
        this.mTranslateViewBefore.setOnClickListener(this);
        this.mTranslateViewAfter.setOnClickListener(this);
    }

    public void reset() {
        this.mSrcIndex = -1;
        this.mTargetIndex = -1;
        this.mSrcCode = null;
        this.mTargetCode = null;
    }

    public void setLangListData(List<LanguageItem> list) {
        this.mLangCodeList.clear();
        this.mSrcLangNameList.clear();
        this.mSrcLangNameList.add(NoteApp.getInstance().getResources().getString(R.string.auto_detected));
        this.mTargetLangNameList.clear();
        for (int i = 0; i < list.size(); i++) {
            try {
                this.mSrcLangNameList.add(list.get(i).getName());
                this.mTargetLangNameList.add(list.get(i).getName());
                this.mLangCodeList.add(list.get(i).getCode());
            } catch (Exception e) {
                Logger.INSTANCE.e(TAG, "setLangListData error: " + e);
                return;
            }
        }
    }

    public void setResultLangCode(String str, String str2) {
        this.mSrcCode = str;
        this.mTargetCode = str2;
    }

    public String getSrcCode() {
        return this.mSrcCode;
    }

    public String getTargetCode() {
        return this.mTargetCode;
    }

    public void showList() {
        String str = this.mSrcCode;
        if (str == null || this.mTargetCode == null) {
            Logger.INSTANCE.e(TAG, "showList error");
            return;
        }
        if (Objects.equals(str, "")) {
            this.mSrcIndex = 0;
        } else {
            int iIndexOf = this.mLangCodeList.indexOf(this.mSrcCode);
            if (iIndexOf < 0) {
                Logger.INSTANCE.e(TAG, "mSrcIndex error");
                return;
            }
            this.mSrcIndex = iIndexOf + 1;
        }
        int iIndexOf2 = this.mLangCodeList.indexOf(this.mTargetCode);
        this.mTargetIndex = iIndexOf2;
        if (iIndexOf2 < 0) {
            Logger.INSTANCE.e(TAG, "mTargetIndex error");
        } else {
            if (this.mSrcIndex >= this.mSrcLangNameList.size() || this.mTargetIndex >= this.mTargetLangNameList.size()) {
                return;
            }
            this.mSrcLangTextView.setText(this.mSrcLangNameList.get(this.mSrcIndex));
            this.mTargetLangTextView.setText(this.mTargetLangNameList.get(this.mTargetIndex));
            this.mRootView.setVisibility(0);
        }
    }

    public void setItemSelectedListener(ItemSelectedListener itemSelectedListener) {
        this.mItemSelectedListener = itemSelectedListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.translate_src /* 2131363688 */:
                this.mActionSelected = ACTION_SRC;
                showMenu();
                break;
            case R.id.translate_target /* 2131363689 */:
                this.mActionSelected = ACTION_TARGET;
                showMenu();
                break;
        }
    }

    private void showMenu() {
        if (this.mDropDownSingleChoiceMenu == null) {
            DropDownSingleChoiceMenu dropDownSingleChoiceMenu = new DropDownSingleChoiceMenu(this.mRootView.getContext());
            this.mDropDownSingleChoiceMenu = dropDownSingleChoiceMenu;
            dropDownSingleChoiceMenu.setOnMenuListener(new DropDownSingleChoiceMenu.OnMenuListener() { // from class: com.miui.notes.ai.AiTranslateController.1
                @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
                public void onDismiss() {
                }

                @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
                public void onShow() {
                }

                @Override // miuix.popupwidget.widget.DropDownSingleChoiceMenu.OnMenuListener
                public void onItemSelected(DropDownSingleChoiceMenu dropDownSingleChoiceMenu2, int i) {
                    try {
                        String str = "";
                        if (AiTranslateController.this.mActionSelected == AiTranslateController.ACTION_SRC) {
                            AiTranslateController.this.mSrcIndex = i;
                            AiTranslateController.this.mSrcLangTextView.setText((CharSequence) AiTranslateController.this.mSrcLangNameList.get(i));
                            AiTranslateController aiTranslateController = AiTranslateController.this;
                            aiTranslateController.mSrcCode = aiTranslateController.mSrcIndex == 0 ? "" : (String) AiTranslateController.this.mLangCodeList.get(AiTranslateController.this.mSrcIndex - 1);
                        } else {
                            AiTranslateController.this.mTargetIndex = i;
                            AiTranslateController.this.mTargetLangTextView.setText((CharSequence) AiTranslateController.this.mTargetLangNameList.get(i));
                            AiTranslateController aiTranslateController2 = AiTranslateController.this;
                            aiTranslateController2.mTargetCode = (String) aiTranslateController2.mLangCodeList.get(AiTranslateController.this.mTargetIndex);
                        }
                        if (AiTranslateController.this.mItemSelectedListener != null) {
                            ItemSelectedListener itemSelectedListener = AiTranslateController.this.mItemSelectedListener;
                            if (AiTranslateController.this.mSrcIndex != 0) {
                                str = (String) AiTranslateController.this.mLangCodeList.get(AiTranslateController.this.mSrcIndex - 1);
                            }
                            itemSelectedListener.onItemSelected(str, (String) AiTranslateController.this.mLangCodeList.get(AiTranslateController.this.mTargetIndex));
                        }
                    } catch (Exception e) {
                        Logger.INSTANCE.e(AiTranslateController.TAG, "onItemSelected error: " + e);
                    }
                }
            });
        }
        this.mDropDownSingleChoiceMenu.setSelectedItem(this.mActionSelected == ACTION_SRC ? this.mSrcIndex : this.mTargetIndex);
        this.mDropDownSingleChoiceMenu.setItems(this.mActionSelected == ACTION_SRC ? this.mSrcLangNameList : this.mTargetLangNameList);
        this.mDropDownSingleChoiceMenu.setAnchorView(this.mActionSelected == ACTION_SRC ? this.mTranslateViewBefore : this.mTranslateViewAfter);
        this.mDropDownSingleChoiceMenu.show();
    }

    public void dismiss() {
        DropDownSingleChoiceMenu dropDownSingleChoiceMenu = this.mDropDownSingleChoiceMenu;
        if (dropDownSingleChoiceMenu != null) {
            dropDownSingleChoiceMenu.dismiss();
            this.mDropDownSingleChoiceMenu = null;
        }
    }

    public String getDefaultLanCode() {
        String strReplace = Locale.getDefault().toString().replace("_", "-");
        List<String> list = this.mLangCodeList;
        if (list == null || !list.contains(strReplace)) {
            return RomUtils.isInternationalBuild() ? "en-US" : "zh-CN";
        }
        return strReplace;
    }
}
