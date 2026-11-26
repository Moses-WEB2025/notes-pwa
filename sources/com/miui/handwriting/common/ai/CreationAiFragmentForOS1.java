package com.miui.handwriting.common.ai;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.miui.creation.common.R;
import com.miui.handwriting.common.HandWritingViewApplication;
import com.miui.handwriting.common.ai.data.CreationImageForOS1;
import com.miui.handwriting.common.ai.data.ThemeData;
import com.miui.handwriting.common.ai.data.ThemeItem;
import com.miui.handwriting.common.ai.viewmodel.AIViewModel;
import com.miui.handwriting.common.tools.ScreenModeUtils;
import com.miui.handwriting.common.tools.Utils;
import com.miui.handwriting.common.view.BaseFragment;
import com.miui.libhandwritingpage.utils.BitmapUtil;
import com.miui.libhandwritingpage.utils.RomUtils;
import com.miui.libhandwritingpage.utils.UIUtils;
import java.util.Arrays;
import java.util.List;
import miuix.androidbasewidget.widget.EditText;
import miuix.androidbasewidget.widget.SeekBar;
import miuix.appcompat.app.ActionBar;
import miuix.appcompat.app.AppCompatActivity;
import miuix.appcompat.app.ProgressDialog;
import miuix.graphics.BitmapFactory;
import miuix.micloudview.accounts.ExtraAccountManager;
import miuix.recyclerview.widget.MiuiDefaultItemAnimator;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes3.dex */
public class CreationAiFragmentForOS1 extends BaseFragment {
    public static String EMPTY_PIC = "EMPTY";
    public static String ERROR_PIC = "ERROR";
    private static final String INTENT_AI_CREATION_BG = "intent_ai_bg";
    private static final String INTENT_AI_CREATION_TYPE = "intent_ai_create_type";
    private static final String INTENT_AI_IMAGE_URL = "intent_ai_image_url";
    private static final int PAGE_CREATION = 1;
    public static final String TAG = "CreationAiFragmentForOS1";
    private ProgressDialog aiDialog;
    private AIViewModel aiViewModel;
    private String imageUrl;
    private Button mBtnBack;
    private Button mBtnNext;
    private Button mBtnStart;
    private View mDetailRoot;
    private EditText mEtInput;
    private RecommendKeyWordAdapter mKeywordAdapter;
    private RecyclerView mKeywordRv;
    private View mPreViewRoot;
    private PreviewAdapterForOS1 mPreviewAdapter;
    private RecyclerView mPreviewRv;
    private SeekBar mSeekbar;
    private PreviewImageView mSinglePreview;
    private TextView mTvProgress;

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setThemeRes(R.style.CreationAiFragmentStyle);
        this.aiViewModel = (AIViewModel) new ViewModelProvider(this).get(AIViewModel.class);
    }

    @Override // miuix.appcompat.app.Fragment, miuix.appcompat.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_creation_ai_os1, viewGroup, false);
        this.mPreViewRoot = viewInflate.findViewById(R.id.rl_preview);
        PreviewImageView previewImageView = (PreviewImageView) viewInflate.findViewById(R.id.iv_preview);
        this.mSinglePreview = previewImageView;
        previewImageView.useStroke(true, getResources().getDimension(R.dimen.creation_ai_preview_stroke_size), getResources().getColor(R.color.creation_ai_preview_item_stroke));
        float dimension = getResources().getDimension(R.dimen.creation_ai_preview_radius);
        this.mSinglePreview.setRoundRadius(dimension, dimension, dimension, dimension);
        if (getActivity() != null && getActivity().getIntent() != null) {
            Intent intent = getActivity().getIntent();
            this.imageUrl = intent.getStringExtra("intent_ai_image_url");
            Log.d(TAG, "imageUrl:" + this.imageUrl);
            this.aiViewModel.setImageUrl(this.imageUrl);
            this.aiViewModel.setCreationType(intent.getIntExtra("intent_ai_create_type", 1));
            int intExtra = intent.getIntExtra("intent_ai_bg", -1);
            if (intExtra == ContextCompat.getColor(getActivity(), R.color.paper_white)) {
                this.mSinglePreview.setBackground(getResources().getDrawable(R.drawable.creation_ai_preview_bg_white));
            } else if (intExtra == ContextCompat.getColor(getActivity(), R.color.paper_yellow)) {
                this.mSinglePreview.setBackground(getResources().getDrawable(R.drawable.creation_ai_preview_bg_yellow));
            } else if (intExtra == ContextCompat.getColor(getActivity(), R.color.paper_black)) {
                this.mSinglePreview.setBackground(getResources().getDrawable(R.drawable.creation_ai_preview_bg_black));
            }
            this.mSinglePreview.setBitmap(this.imageUrl, System.currentTimeMillis());
        }
        this.mKeywordAdapter = new RecommendKeyWordAdapter(getActivity());
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.rv_keyword);
        this.mKeywordRv = recyclerView;
        recyclerView.setLayoutManager(new FlexboxLayoutManager(getActivity(), 0, 1));
        this.mKeywordRv.setItemAnimator(new MiuiDefaultItemAnimator());
        this.mKeywordRv.setAdapter(this.mKeywordAdapter);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_progress);
        this.mTvProgress = textView;
        textView.setText(String.format("%d%%", 60));
        SeekBar seekBar = (SeekBar) viewInflate.findViewById(R.id.sb_participation);
        this.mSeekbar = seekBar;
        seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(android.widget.SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(android.widget.SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(android.widget.SeekBar seekBar2, int i, boolean z) {
                CreationAiFragmentForOS1.this.mTvProgress.setText(String.format("%d%%", Integer.valueOf(i * 10)));
            }
        });
        final Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(getContext());
        Button button = (Button) viewInflate.findViewById(R.id.btn_start);
        this.mBtnStart = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m711xaa2f447(xiaomiAccount, view);
            }
        });
        this.mEtInput = (EditText) viewInflate.findViewById(R.id.et_input);
        if (UIUtils.isInMultiWindowMode(getActivity())) {
            this.mEtInput.setHint(R.string.creation_ai_edit_simple_hint);
        } else {
            this.mEtInput.setHint(R.string.creation_ai_edit_hint);
        }
        this.mEtInput.addTextChangedListener(new TextWatcher() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                Editable text = CreationAiFragmentForOS1.this.mEtInput.getText();
                if (TextUtils.isEmpty(text) || text.length() < 200) {
                    return;
                }
                Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), CreationAiFragmentForOS1.this.getString(R.string.creation_ai_text_limit), 0).show();
            }
        });
        this.mDetailRoot = viewInflate.findViewById(R.id.rl_detail);
        RecyclerView recyclerView2 = (RecyclerView) viewInflate.findViewById(R.id.rv_ai_preview);
        this.mPreviewRv = recyclerView2;
        recyclerView2.setLayoutManager(new GridLayoutManager(getActivity(), 2) { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.3
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        });
        final int dimension2 = (int) getResources().getDimension(R.dimen.creation_ai_small_preview_distance);
        this.mPreviewRv.addItemDecoration(new androidx.recyclerview.widget.RecyclerView.ItemDecoration() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.4
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, androidx.recyclerview.widget.RecyclerView recyclerView3, androidx.recyclerview.widget.RecyclerView.State state) {
                int childAdapterPosition = recyclerView3.getChildAdapterPosition(view);
                if (childAdapterPosition == 0) {
                    rect.right = dimension2;
                    rect.bottom = dimension2;
                    return;
                }
                if (childAdapterPosition == 1) {
                    rect.left = dimension2;
                    rect.bottom = dimension2;
                } else if (childAdapterPosition == 2) {
                    rect.right = dimension2;
                    rect.top = dimension2;
                } else {
                    if (childAdapterPosition != 3) {
                        return;
                    }
                    rect.left = dimension2;
                    rect.top = dimension2;
                }
            }
        });
        this.mPreviewRv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.5
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (CreationAiFragmentForOS1.this.getActivity() == null || !((AppCompatActivity) CreationAiFragmentForOS1.this.getActivity()).isInFloatingWindowMode()) {
                    return;
                }
                for (int i = 0; i < CreationAiFragmentForOS1.this.mPreviewRv.getChildCount(); i++) {
                    View childAt = CreationAiFragmentForOS1.this.mPreviewRv.getChildAt(i);
                    ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                    int height = ((CreationAiFragmentForOS1.this.mPreviewRv.getHeight() - CreationAiFragmentForOS1.this.getResources().getDimensionPixelSize(R.dimen.creation_ai_small_preview_distance)) - (CreationAiFragmentForOS1.this.getResources().getDimensionPixelSize(R.dimen.creation_ai_small_preview_select_stroke) * 2)) / 2;
                    if (layoutParams.height != height) {
                        layoutParams.height = height;
                        childAt.setLayoutParams(layoutParams);
                    }
                }
            }
        });
        PreviewAdapterForOS1 previewAdapterForOS1 = new PreviewAdapterForOS1(getActivity());
        this.mPreviewAdapter = previewAdapterForOS1;
        this.mPreviewRv.setAdapter(previewAdapterForOS1);
        this.mPreviewAdapter.setSelectItemListener(new PreviewAdapterForOS1.SelectItemListener() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.6
            @Override // com.miui.handwriting.common.ai.PreviewAdapterForOS1.SelectItemListener
            public void selectItem(String str) {
                CreationAiFragmentForOS1.this.mBtnNext.setEnabled((str == null || TextUtils.equals(CreationAiFragmentForOS1.EMPTY_PIC, str) || TextUtils.equals(CreationAiFragmentForOS1.ERROR_PIC, str)) ? false : true);
            }
        });
        Button button2 = (Button) viewInflate.findViewById(R.id.btn_back);
        this.mBtnBack = button2;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m712x3036fd48(view);
            }
        });
        Button button3 = (Button) viewInflate.findViewById(R.id.btn_next);
        this.mBtnNext = button3;
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m713x55cb0649(xiaomiAccount, view);
            }
        });
        this.aiViewModel.getThemeLiveData().observe(this, new Observer<ThemeData>() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.7
            @Override // androidx.lifecycle.Observer
            public void onChanged(ThemeData themeData) {
                if (themeData != null) {
                    if (!themeData.getThemes().isEmpty()) {
                        CreationAiFragmentForOS1.this.mKeywordAdapter.setSelectThemes(themeData.getThemes().get(0));
                    }
                    CreationAiFragmentForOS1.this.mKeywordAdapter.setData(themeData.getThemes());
                    CreationAiFragmentForOS1.this.mKeywordAdapter.notifyDataSetChanged();
                }
            }
        });
        this.aiViewModel.requestListTheme();
        this.aiViewModel.getCreationSmallImageLiveData().observe(this, new Observer<CreationImageForOS1>() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.8
            @Override // androidx.lifecycle.Observer
            public void onChanged(CreationImageForOS1 creationImageForOS1) {
                List<CreationImageForOS1> previewItemList = CreationAiFragmentForOS1.this.aiViewModel.getPreviewItemList();
                boolean z = false;
                for (int i = 0; i < previewItemList.size(); i++) {
                    if (TextUtils.equals(previewItemList.get(i).getImageData(), CreationAiFragmentForOS1.EMPTY_PIC)) {
                        z = true;
                        break;
                    }
                }
                CreationAiFragmentForOS1.this.mPreviewAdapter.notifyDataSetChanged();
                if (z) {
                    return;
                }
                CreationAiFragmentForOS1.this.mPreviewAdapter.setLoadingFinish(true);
                CreationAiFragmentForOS1.this.mBtnNext.setEnabled(true);
            }
        });
        this.aiViewModel.getErrorCreation().observe(this, new Observer<Boolean>() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.9
            @Override // androidx.lifecycle.Observer
            public void onChanged(Boolean bool) {
                if (bool.booleanValue()) {
                    CreationAiFragmentForOS1.this.aiViewModel.setPreviewItemList(Arrays.asList(new CreationImageForOS1(CreationAiFragmentForOS1.ERROR_PIC), new CreationImageForOS1(CreationAiFragmentForOS1.ERROR_PIC), new CreationImageForOS1(CreationAiFragmentForOS1.ERROR_PIC), new CreationImageForOS1(CreationAiFragmentForOS1.ERROR_PIC)));
                    CreationAiFragmentForOS1.this.mPreviewAdapter.setData(CreationAiFragmentForOS1.this.aiViewModel.getPreviewItemList());
                    CreationAiFragmentForOS1.this.mPreviewAdapter.notifyDataSetChanged();
                }
            }
        });
        this.aiViewModel.getCreationBigImageLiveData().observe(this, new Observer<CreationImageForOS1>() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.10
            @Override // androidx.lifecycle.Observer
            public void onChanged(CreationImageForOS1 creationImageForOS1) {
                if (creationImageForOS1 != null && !TextUtils.isEmpty(creationImageForOS1.getImageData())) {
                    Log.d(CreationAiFragmentForOS1.TAG, "ai big image:" + creationImageForOS1.getImageData());
                    String strBitmapToPNG = BitmapUtil.bitmapToPNG(CreationAiFragmentForOS1.this.getBigImageByWatermark(creationImageForOS1), Utils.getTempDir(), System.currentTimeMillis() + ".png");
                    Intent intent2 = new Intent();
                    intent2.putExtra("ai_path", strBitmapToPNG);
                    CreationAiFragmentForOS1.this.getActivity().setResult(-1, intent2);
                    CreationAiFragmentForOS1.this.getActivity().finish();
                }
                CreationAiFragmentForOS1.this.dismissAIDialog();
                CreationAiFragmentForOS1.this.aiViewModel.setShowLoading(false);
            }
        });
        ViewGroup.LayoutParams layoutParams = this.mSinglePreview.getLayoutParams();
        if (ScreenModeUtils.isOneThird(getContext()) || ScreenModeUtils.isPortHalf(getContext())) {
            updateButtonMargin(getResources().getDimensionPixelSize(R.dimen.creation_ai_btn_result_margin_bottom_1_3));
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_width_1_3);
            this.mPreviewRv.setPadding(getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding_1_3), this.mPreviewRv.getPaddingTop(), getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding_1_3), this.mPreviewRv.getPaddingBottom());
        } else if (ScreenModeUtils.isHalf(getContext())) {
            updateButtonMargin(getResources().getDimensionPixelSize(R.dimen.creation_ai_btn_result_margin_bottom_1_2));
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_width);
            this.mPreviewRv.setPadding(getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding_1_2), this.mPreviewRv.getPaddingTop(), getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding_1_2), this.mPreviewRv.getPaddingBottom());
        } else {
            updateButtonMargin(getResources().getDimensionPixelSize(R.dimen.creation_ai_btn_result_margin_bottom));
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_width);
            this.mPreviewRv.setPadding(getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding), this.mPreviewRv.getPaddingTop(), getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding), this.mPreviewRv.getPaddingBottom());
        }
        if (UIUtils.isInMultiWindowMode(getActivity())) {
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_height_multi_window);
        } else {
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_height);
        }
        if (this.aiViewModel.getCurrentPage() == 1) {
            this.mPreViewRoot.setVisibility(8);
            this.mDetailRoot.setVisibility(0);
            List<CreationImageForOS1> previewItemList = this.aiViewModel.getPreviewItemList();
            this.mPreviewAdapter.setLoadingFinish(true);
            this.mBtnNext.setEnabled(true);
            this.mPreviewAdapter.setData(previewItemList);
            this.mPreviewAdapter.notifyDataSetChanged();
        }
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(R.drawable.creation_ai_back);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m714x7b5f0f4a(view);
                }
            });
            actionBar.setStartView(imageView);
            actionBar.setExpandState(0);
            actionBar.setResizable(false);
        }
        return viewInflate;
    }

    /* JADX INFO: renamed from: lambda$onInflateView$0$com-miui-handwriting-common-ai-CreationAiFragmentForOS1, reason: not valid java name */
    /* synthetic */ void m711xaa2f447(Account account, View view) {
        String strTrim;
        if (this.mEtInput.getText() == null) {
            strTrim = "";
        } else {
            strTrim = this.mEtInput.getText().toString().trim();
        }
        String str = strTrim;
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), getString(R.string.creation_prompt_add_tip), 0).show();
            return;
        }
        if (!Utils.isNetworkAvailable(HandWritingViewApplication.INSTANCE.getApplication())) {
            Toast.makeText(HandWritingViewApplication.INSTANCE.getApplication(), HandWritingViewApplication.INSTANCE.getApplication().getString(R.string.creation_ai_network_error_message), 0).show();
            return;
        }
        this.aiViewModel.setCurrentPage(1);
        startAiCreation();
        ThemeItem selectThemes = this.mKeywordAdapter.getSelectThemes();
        if (selectThemes != null) {
            this.aiViewModel.requestUploadMeta(account.name, this.imageUrl, selectThemes, str, this.mSeekbar.getProgress() / 10.0f);
        }
    }

    /* JADX INFO: renamed from: lambda$onInflateView$1$com-miui-handwriting-common-ai-CreationAiFragmentForOS1, reason: not valid java name */
    /* synthetic */ void m712x3036fd48(View view) {
        this.aiViewModel.setCurrentPage(0);
        this.aiViewModel.cancel();
        this.mBtnStart.setText(R.string.creation_ai_recreate);
        this.mDetailRoot.setVisibility(8);
        this.mPreViewRoot.setVisibility(0);
        this.aiViewModel.setPreviewItemList(Arrays.asList(new CreationImageForOS1(EMPTY_PIC), new CreationImageForOS1(EMPTY_PIC), new CreationImageForOS1(EMPTY_PIC), new CreationImageForOS1(EMPTY_PIC)));
        this.mPreviewAdapter.setData(this.aiViewModel.getPreviewItemList());
        this.mPreviewAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: renamed from: lambda$onInflateView$2$com-miui-handwriting-common-ai-CreationAiFragmentForOS1, reason: not valid java name */
    /* synthetic */ void m713x55cb0649(Account account, View view) {
        String strTrim;
        this.aiViewModel.setShowLoading(true);
        showAIDialog();
        ThemeItem selectThemes = this.mKeywordAdapter.getSelectThemes();
        if (this.mEtInput.getText() == null) {
            strTrim = "";
        } else {
            strTrim = this.mEtInput.getText().toString().trim();
        }
        String imageData = this.aiViewModel.getPreviewItemList().get(this.mPreviewAdapter.getSelectPosition()).getImageData();
        this.aiViewModel.requestEnhance(account.name, imageData, selectThemes, strTrim, this.mSeekbar.getProgress() * 10);
    }

    /* JADX INFO: renamed from: lambda$onInflateView$3$com-miui-handwriting-common-ai-CreationAiFragmentForOS1, reason: not valid java name */
    /* synthetic */ void m714x7b5f0f4a(View view) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getBigImageByWatermark(CreationImageForOS1 creationImageForOS1) {
        byte[] bArrDecode = Base64.decode(creationImageForOS1.getImageData(), 0);
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
        int i = 1;
        Bitmap bitmapCopy = bitmapDecodeByteArray.copy(Bitmap.Config.ARGB_8888, true);
        bitmapDecodeByteArray.recycle();
        Canvas canvas = new Canvas(bitmapCopy);
        android.graphics.BitmapFactory.Options options = new android.graphics.BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), getWatermarkDrawable(), options);
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        while (true) {
            if (i2 / i < bitmapCopy.getWidth() / 2 && i3 / i < bitmapCopy.getHeight() / 2) {
                Log.d(TAG, "originalWidth:" + i2 + " originalHeight:" + i3 + " sampleSize:" + i);
                android.graphics.BitmapFactory.Options options2 = new android.graphics.BitmapFactory.Options();
                options2.inSampleSize = i;
                Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(getResources(), getWatermarkDrawable(), options2);
                int width = (bitmapCopy.getWidth() - (getResources().getDimensionPixelSize(R.dimen.creation_ai_watermark_right) / i)) - bitmapDecodeResource.getWidth();
                int height = (bitmapCopy.getHeight() - (getResources().getDimensionPixelSize(R.dimen.creation_ai_watermark_bottom) / i)) - bitmapDecodeResource.getHeight();
                Log.d(TAG, " x:" + width + " y:" + height + " resultWidth:" + bitmapCopy.getWidth() + " resultHeight:" + bitmapCopy.getHeight() + " watermarkWidth:" + bitmapDecodeResource.getWidth() + " watermarkHeight:" + bitmapDecodeResource.getHeight());
                canvas.drawBitmap(bitmapDecodeResource, width, height, new Paint());
                bitmapDecodeResource.recycle();
                return bitmapCopy;
            }
            i *= 2;
        }
    }

    private int getWatermarkDrawable() {
        if (RomUtils.isInternationalBuild()) {
            return R.drawable.icon_ai_watermark_en;
        }
        return R.drawable.icon_ai_watermark_zh;
    }

    private void showAIDialog() {
        ProgressDialog progressDialog = this.aiDialog;
        if (progressDialog == null || !progressDialog.isShowing()) {
            Activity activity = Utils.getActivity(getActivity());
            if (this.aiDialog == null) {
                ProgressDialog progressDialog2 = new ProgressDialog(activity);
                this.aiDialog = progressDialog2;
                progressDialog2.setMessage(activity.getString(miuix.springback.R.string.miuix_sbl_tracking_progress_labe_up_refresh));
                this.aiDialog.setIndeterminate(true);
                this.aiDialog.setCanceledOnTouchOutside(false);
                this.aiDialog.setCancelable(false);
            }
            this.aiDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissAIDialog() {
        ProgressDialog progressDialog = this.aiDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.aiViewModel.getShowLoading()) {
            showAIDialog();
        }
    }

    public void startAiCreation() {
        this.mPreviewAdapter.setLoadingFinish(false);
        this.mBtnNext.setEnabled(false);
        this.mPreviewAdapter.setData(this.aiViewModel.getPreviewItemList());
        this.mPreviewAdapter.notifyDataSetChanged();
        this.mPreViewRoot.setVisibility(8);
        this.mDetailRoot.setVisibility(0);
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // miuix.appcompat.app.Fragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ViewGroup.LayoutParams layoutParams = this.mSinglePreview.getLayoutParams();
        if (ScreenModeUtils.isOneThird(getContext()) || ScreenModeUtils.isPortHalf(getContext())) {
            updateButtonMargin(getResources().getDimensionPixelSize(R.dimen.creation_ai_btn_result_margin_bottom_1_3));
            this.mPreviewRv.setPadding(getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding_1_3), this.mPreviewRv.getPaddingTop(), getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding_1_3), this.mPreviewRv.getPaddingBottom());
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_width_1_3);
        } else if (ScreenModeUtils.isHalf(getContext())) {
            updateButtonMargin(getResources().getDimensionPixelSize(R.dimen.creation_ai_btn_result_margin_bottom_1_2));
            this.mPreviewRv.setPadding(getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding_1_2), this.mPreviewRv.getPaddingTop(), getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding_1_2), this.mPreviewRv.getPaddingBottom());
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_width);
        } else {
            updateButtonMargin(getResources().getDimensionPixelSize(R.dimen.creation_ai_btn_result_margin_bottom));
            this.mPreviewRv.setPadding(getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding), this.mPreviewRv.getPaddingTop(), getResources().getDimensionPixelSize(R.dimen.creation_ai_result_preview_padding), this.mPreviewRv.getPaddingBottom());
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_width);
        }
        if (UIUtils.isInMultiWindowMode(getActivity())) {
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_height_multi_window);
        } else {
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.creation_ai_preview_height);
        }
        this.mPreviewRv.setAdapter(this.mPreviewAdapter);
        this.mPreviewRv.setLayoutManager(new GridLayoutManager(getActivity(), 2) { // from class: com.miui.handwriting.common.ai.CreationAiFragmentForOS1.11
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean canScrollVertically() {
                return false;
            }
        });
        this.mPreviewAdapter.notifyDataSetChanged();
        if (UIUtils.isInMultiWindowMode(getActivity())) {
            this.mEtInput.setHint(R.string.creation_ai_edit_simple_hint);
        } else {
            this.mEtInput.setHint(R.string.creation_ai_edit_hint);
        }
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setExpandState(0);
            actionBar.setResizable(false);
        }
    }

    public void updateButtonMargin(float f) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBtnStart.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mBtnBack.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mBtnNext.getLayoutParams();
        int i = (int) f;
        layoutParams.bottomMargin = i;
        layoutParams2.bottomMargin = i;
        layoutParams3.bottomMargin = i;
    }
}
