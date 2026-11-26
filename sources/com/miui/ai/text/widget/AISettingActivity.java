package com.miui.ai.text.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.TabStopSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.hardware.DataSpace;
import ch.qos.logback.classic.net.SyslogAppender;
import com.miui.ai.text.widget.download.AIcrDownloadHelper;
import com.miui.notes.ai.stat.AITextWidgetStat;
import com.miui.notes.ai.utils.DisplayUtil;
import com.miui.notes.ai.utils.PreferenceUtils;
import com.miui.notes.ai.utils.RomUtils;
import com.miui.notes.ai.utils.UIUtils;
import com.miui.notes.base.utils.Logger;
import com.miui.notes.constant.NoteIntent;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.LayoutUiModeHelper;
import miuix.appcompat.widget.Button;
import miuix.container.ExtraPaddingPolicy;
import miuix.slidingwidget.widget.SlidingButton;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class AISettingActivity extends AppCompatActivity {
    private static final String TAG = "AISettingActivity";
    private static boolean expandedTitle = false;
    private Button mAiExperienceBtn;
    private TextView mAiUsageTextView;
    private RelativeLayout mContentItem;
    private TextView mContentTextView;
    private int mNavigationBarHeight;
    private SlidingButton mSwitch;
    private RelativeLayout mUploadItem;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        if (RomUtils.isPadDevice() || RomUtils.isFoldDevice()) {
            setExtraPaddingPolicy(new ExtraPaddingPolicy.Builder().setPaddingHorizontalCommonDp(12).setThresholds(420).setPaddingsDp(44, 44).create());
            setExtraHorizontalPaddingEnable(true);
            setExtraPaddingApplyToContentEnable(false);
        }
        if (getWindow() != null) {
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
        UIUtils.adaptationFoldDevice(this);
        initActionBar();
        setContentView(R.layout.activity_aisetting);
        init();
        if (!RomUtils.isPadDevice()) {
            setRequestedOrientation(1);
            ImageView imageView = (ImageView) findViewById(R.id.icon);
            imageView.setImageResource(R.drawable.phone_settings_icon);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.ai_text_settings_imageview_width_phone), (int) getResources().getDimension(R.dimen.ai_text_settings_imageview_height_phone));
            layoutParams.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.ai_text_settings_imageview_padding_v_phone));
            layoutParams.gravity = 1;
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            TextView textView = (TextView) findViewById(R.id.upload_summary);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            layoutParams2.setMarginEnd((int) getResources().getDimension(R.dimen.ai_text_settings_upload_summary_margin_end_phone));
            textView.setLayoutParams(layoutParams2);
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.upload_item);
        this.mSwitch = (SlidingButton) findViewById(R.id.upload_onoff);
        if (RomUtils.isSupportAiText(this) && RomUtils.isSupportLocalAI(this)) {
            relativeLayout.setVisibility(0);
        } else {
            relativeLayout.setVisibility(8);
            if (!RomUtils.isSupportLocalAI(this)) {
                PreferenceUtils.setModelSwitch(this, false);
            }
        }
        initClickEvent();
    }

    private void init() {
        this.mAiExperienceBtn = (Button) findViewById(R.id.ai_experience);
        this.mUploadItem = (RelativeLayout) findViewById(R.id.upload_item);
        this.mContentItem = (RelativeLayout) findViewById(R.id.content_item);
        if (RomUtils.isPadDevice() || RomUtils.isFoldDevice()) {
            updateLayoutMargins(getExtraHorizontalPadding());
        }
        int navigationBarHeightByWindow = UIUtils.getNavigationBarHeightByWindow(this, getWindow().getDecorView());
        this.mNavigationBarHeight = navigationBarHeightByWindow;
        setButtonBottomMargin(navigationBarHeightByWindow + getResources().getDimensionPixelSize(R.dimen.ai_experience_btn_to_navigationbar));
        this.mAiUsageTextView = (TextView) findViewById(R.id.ai_usage_text);
        TextView textView = (TextView) findViewById(R.id.content_introduction_text);
        this.mContentTextView = textView;
        setFormattedTextWithStyle(textView, R.string.ai_writing_content_text, 0, 1, 2, 3, 4);
        setFormattedTextWithStyle(this.mAiUsageTextView, R.string.ai_usage_content_text, R.drawable.ai_usage_text_icon, 1, 2);
    }

    private void initClickEvent() {
        this.mSwitch.setOnPerformCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.miui.ai.text.widget.AISettingActivity$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.lambda$initClickEvent$0(compoundButton, z);
            }
        });
        this.mAiExperienceBtn.setOnClickListener(new View.OnClickListener() { // from class: com.miui.ai.text.widget.AISettingActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initClickEvent$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initClickEvent$0(CompoundButton compoundButton, boolean z) {
        boolean modelStatus = AIcrDownloadHelper.getModelStatus(this);
        boolean cotConfig = AIcrDownloadHelper.getCotConfig(this);
        Logger.INSTANCE.d(TAG, "initClickEvent status:" + modelStatus + " ,cotStatus:" + cotConfig);
        if (!z) {
            PreferenceUtils.setModelSwitch(getApplicationContext(), false);
            AITextWidgetStat.reportAiLocalSettingClick("false", getApplicationContext());
            return;
        }
        if (!modelStatus) {
            PreferenceUtils.setModelSwitch(getApplicationContext(), false);
            this.mSwitch.setChecked(false);
            AIcrDownloadHelper.checkAndDownload(getApplicationContext(), getPackageName(), true);
        } else if (!cotConfig) {
            PreferenceUtils.setModelSwitch(getApplicationContext(), false);
            this.mSwitch.setChecked(false);
            AIcrDownloadHelper.checkAndDownload(getApplicationContext(), getPackageName(), true);
        } else {
            this.mSwitch.setChecked(true);
            PreferenceUtils.setModelSwitch(getApplicationContext(), true);
            AITextWidgetStat.reportAiLocalSettingClick("true", getApplicationContext());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initClickEvent$1(View view) {
        Intent intent;
        if (RomUtils.isFoldDevice()) {
            intent = new Intent();
            intent.setClassName("com.miui.notes", "com.miui.notes.ui.NotesListActivity");
            intent.setAction("android.intent.action.INSERT_OR_EDIT");
            intent.putExtra("com.miui.notes.folder_id", 0L);
            intent.setFlags(268468224);
            intent.putExtra("create_note_immediately", true);
        } else {
            intent = new Intent();
            intent.setClassName("com.miui.notes", "com.miui.notes.ui.activity.EditActivity");
            intent.putExtra("com.miui.notes.folder_id", 0L);
            intent.setFlags(268468224);
            intent.putExtra(NoteIntent.KEY_IS_PENDING, false);
            intent.setAction("android.intent.action.INSERT_OR_EDIT");
        }
        startActivity(intent);
    }

    public void setButtonBottomMargin(int i) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mAiExperienceBtn.getLayoutParams();
        layoutParams.bottomMargin = i;
        this.mAiExperienceBtn.setLayoutParams(layoutParams);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, miuix.container.ExtraPaddingObserver
    public void onExtraPaddingChanged(int i) {
        super.onExtraPaddingChanged(i);
        updateLayoutMargins(i);
    }

    private void updateLayoutMargins(int i) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.ai_text_settings_item_margin_start_land) + i;
        RelativeLayout relativeLayout = this.mUploadItem;
        if (relativeLayout != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
            layoutParams.setMarginStart(dimensionPixelSize);
            layoutParams.setMarginEnd(dimensionPixelSize);
            this.mUploadItem.setLayoutParams(layoutParams);
        }
        RelativeLayout relativeLayout2 = this.mContentItem;
        if (relativeLayout2 != null) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) relativeLayout2.getLayoutParams();
            layoutParams2.setMarginStart(dimensionPixelSize);
            layoutParams2.setMarginEnd(dimensionPixelSize);
            this.mContentItem.setLayoutParams(layoutParams2);
        }
    }

    public void setFormattedTextWithStyle(TextView textView, int i, int i2, Object... objArr) {
        setContentTextStyleFromString(textView.getContext().getString(i, objArr), textView, i2);
    }

    private void setContentTextStyleFromString(String str, TextView textView, int i) {
        int iIndexOf;
        String[] strArrSplit = str.split(StringUtils.LF);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int iMeasureText = (int) textView.getPaint().measureText("4. ");
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            String strTrim = strArrSplit[i2].trim();
            if (!strTrim.isEmpty()) {
                int length = spannableStringBuilder.length();
                if (strTrim.matches("^\\d+\\.\\s.*") && (iIndexOf = strTrim.indexOf(". ")) != -1) {
                    spannableStringBuilder.append((CharSequence) strTrim.substring(0, iIndexOf + 1)).append((CharSequence) SyslogAppender.DEFAULT_STACKTRACE_PATTERN).append((CharSequence) strTrim.substring(iIndexOf + 2));
                    spannableStringBuilder.setSpan(new TabStopSpan.Standard(iMeasureText), length, spannableStringBuilder.length(), 33);
                    spannableStringBuilder.setSpan(new LeadingMarginSpan.Standard(0, iMeasureText), length, spannableStringBuilder.length(), 17);
                } else {
                    spannableStringBuilder.append((CharSequence) strTrim);
                }
                if (i2 < strArrSplit.length - 1) {
                    spannableStringBuilder.append((CharSequence) StringUtils.LF);
                }
            }
        }
        if (i != 0) {
            int i3 = 0;
            while (true) {
                int iIndexOf2 = spannableStringBuilder.toString().indexOf("{icon}", i3);
                if (iIndexOf2 == -1) {
                    break;
                }
                spannableStringBuilder.replace(iIndexOf2, "{icon}".length() + iIndexOf2, (CharSequence) "￼");
                Drawable drawable = AppCompatResources.getDrawable(textView.getContext(), i);
                if (drawable != null) {
                    int textSize = (int) textView.getTextSize();
                    drawable.setBounds(0, 0, textSize, textSize);
                    spannableStringBuilder.setSpan(new ImageSpan(drawable, 2), iIndexOf2, iIndexOf2 + 1, 33);
                }
                i3 = iIndexOf2 + 1;
            }
        }
        textView.setText(spannableStringBuilder);
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        if (getWindow() != null) {
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
        ActionBar appCompatActionBar = getAppCompatActionBar();
        if (RomUtils.isPadDevice() || (RomUtils.isFoldDevice() && !UIUtils.isInNarrowScreen(this))) {
            if (expandedTitle) {
                appCompatActionBar.setExpandState(0);
                appCompatActionBar.setResizable(false);
                expandedTitle = false;
                return;
            }
            return;
        }
        if (expandedTitle) {
            return;
        }
        appCompatActionBar.setResizable(true);
        appCompatActionBar.setExpandState(1);
        expandedTitle = true;
    }

    private void initActionBar() {
        ActionBar appCompatActionBar = getAppCompatActionBar();
        if (appCompatActionBar != null) {
            appCompatActionBar.setTitle(R.string.ai_text_widget_title);
            appCompatActionBar.setDisplayHomeAsUpEnabled(false);
            ImageView backIcon = getBackIcon(this);
            backIcon.setContentDescription(getString(R.string.ai_text_widget_return));
            backIcon.setOnClickListener(new View.OnClickListener() { // from class: com.miui.ai.text.widget.AISettingActivity$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initActionBar$2(view);
                }
            });
            appCompatActionBar.setStartView(backIcon);
            if (RomUtils.isPadDevice() || (RomUtils.isFoldDevice() && !UIUtils.isInNarrowScreen(this))) {
                appCompatActionBar.setExpandState(0);
                appCompatActionBar.setResizable(false);
                expandedTitle = false;
            } else {
                appCompatActionBar.setExpandState(1);
                appCompatActionBar.setResizable(true);
                expandedTitle = true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initActionBar$2(View view) {
        onBackPressed();
    }

    public static ImageView getBackIcon(Context context) {
        ImageView imageView = new ImageView(context);
        if (!DisplayUtil.isNightMode(context)) {
            imageView.setBackgroundResource(R.drawable.miuix_action_icon_back_light);
            return imageView;
        }
        imageView.setBackgroundResource(R.drawable.miuix_action_icon_back_dark);
        return imageView;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        LayoutUiModeHelper.autoSetLayoutUiMode(this);
        if (getWindow() != null) {
            getWindow().addFlags(DataSpace.RANGE_FULL);
        }
        this.mSwitch.setChecked(PreferenceUtils.isAcceptedMode(getApplicationContext()));
    }

    @Override // miuix.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.mSwitch.setOnPerformCheckedChangeListener(null);
        getWindow().setCallback(null);
    }
}
