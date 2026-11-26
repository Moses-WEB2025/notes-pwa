package miuix.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import miuix.internal.util.ViewUtils;
import miuix.transition.MiuixTransition;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes6.dex */
public abstract class SearchFragment extends Fragment {
    private static final String TAG_RECOMMENDATION = "miuix:search:recommendation";
    private static final String TAG_RESULT = "miuix:search:result";
    private View mBack;
    private View mEndView;
    private EditText mInput;
    private boolean mInputFromHistory;
    private ModelType mModel;
    private Fragment mRecommendation;
    private ResultFragment mResult;
    private ViewGroup mSearchContainer;
    private TextWatcher mTextWatcher = new TextWatcher() { // from class: miuix.search.SearchFragment.1
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 0) {
                SearchFragment.this.setEndViewEnabled(false);
                SearchFragment.this.ensureRecommendationFragment();
                SearchFragment.this.getChildFragmentManager().beginTransaction().replace(R.id.container, SearchFragment.this.mRecommendation, SearchFragment.TAG_RECOMMENDATION).commitAllowingStateLoss();
            } else {
                SearchFragment.this.setEndViewEnabled(true);
                if (SearchFragment.this.mModel == ModelType.MODEL_BACK) {
                    SearchFragment.this.ensureResultFragment();
                    SearchFragment.this.getChildFragmentManager().beginTransaction().replace(R.id.container, SearchFragment.this.mResult, SearchFragment.TAG_RESULT).commitNowAllowingStateLoss();
                    SearchFragment.this.mResult.onInputUpdate(editable, SearchFragment.this.mInputFromHistory);
                }
            }
            SearchFragment.this.mInputFromHistory = false;
        }
    };
    private View.OnClickListener mSearchListener = new View.OnClickListener() { // from class: miuix.search.SearchFragment.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            SearchFragment.this.doSearchEvent();
        }
    };
    private View.OnClickListener mBackListener = new View.OnClickListener() { // from class: miuix.search.SearchFragment.3
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            SearchFragment.this.onBack();
            HapticCompat.performHapticFeedback(view, HapticFeedbackConstants.MIUI_TAP_LIGHT);
        }
    };
    private TextView.OnEditorActionListener mEditorActionListener = new TextView.OnEditorActionListener() { // from class: miuix.search.SearchFragment.4
        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (SearchFragment.this.mModel == ModelType.MODEL_BACK && i == 3 && SearchFragment.this.mResult != null) {
                SearchFragment.this.mResult.onActionSearch(textView.getText());
            }
            if (SearchFragment.this.mModel != ModelType.MODEL_BACK_SEARCH) {
                return false;
            }
            SearchFragment.this.doSearchEvent();
            return false;
        }
    };

    public enum ModelType {
        MODEL_BACK,
        MODEL_BACK_SEARCH
    }

    protected abstract Fragment onCreateRecommendationView();

    protected abstract ResultFragment onCreateResultView();

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TypedValue typedValue = new TypedValue();
        if (getContext().getTheme().resolveAttribute(R.attr.searchTheme, typedValue, true)) {
            getContext().getTheme().applyStyle(typedValue.resourceId, false);
            this.mModel = onModelSelected();
            if (bundle != null) {
                this.mRecommendation = getChildFragmentManager().findFragmentByTag(TAG_RECOMMENDATION);
                this.mResult = (ResultFragment) getChildFragmentManager().findFragmentByTag(TAG_RESULT);
                return;
            } else {
                ensureRecommendationFragment();
                getChildFragmentManager().beginTransaction().add(R.id.container, this.mRecommendation, TAG_RECOMMENDATION).commit();
                return;
            }
        }
        throw new IllegalStateException("no searchTheme found in the theme");
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.search_fragment, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mSearchContainer = (ViewGroup) view.findViewById(R.id.search_container);
        EditText editText = (EditText) view.findViewById(android.R.id.input);
        this.mInput = editText;
        editText.setFocusable(true);
        this.mInput.requestFocus();
        View viewFindViewById = view.findViewById(R.id.endView);
        this.mEndView = viewFindViewById;
        viewFindViewById.setOnClickListener(this.mSearchListener);
        View viewFindViewById2 = view.findViewById(R.id.back);
        this.mBack = viewFindViewById2;
        viewFindViewById2.setOnClickListener(this.mBackListener);
        MiuixTransition miuixTransition = (MiuixTransition) getReturnTransition();
        if (miuixTransition != null) {
            miuixTransition.addTarget(R.id.endView);
            miuixTransition.addTarget(R.id.search_container);
            miuixTransition.addTarget(R.id.container);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mModel == ModelType.MODEL_BACK_SEARCH) {
            this.mEndView.setVisibility(0);
            setSearchContainerMargin(this.mSearchContainer.getContext());
        }
        EditText editText = this.mInput;
        if (!TextUtils.isEmpty(editText != null ? editText.getText() : null)) {
            setEndViewEnabled(true);
        } else {
            setEndViewEnabled(false);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        this.mInput.addTextChangedListener(this.mTextWatcher);
        this.mInput.setOnEditorActionListener(this.mEditorActionListener);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mInput.removeTextChangedListener(this.mTextWatcher);
        this.mInput = null;
    }

    void onInputUpdate(CharSequence charSequence) {
        this.mInputFromHistory = true;
        this.mInput.setText(charSequence);
        if (charSequence != null) {
            this.mInput.setSelection(charSequence.length());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureRecommendationFragment() {
        if (this.mRecommendation == null) {
            this.mRecommendation = onCreateRecommendationView();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureResultFragment() {
        if (this.mResult == null) {
            this.mResult = onCreateResultView();
        }
    }

    protected void onBack() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    private void setSearchContainerMargin(Context context) {
        View view = this.mEndView;
        boolean z = view != null && view.getVisibility() == 8;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mSearchContainer.getLayoutParams();
        int dimensionPixelSize = z ? context.getResources().getDimensionPixelSize(R.dimen.miuix_search_view_padding_horizontal) : 0;
        if (ViewUtils.isLayoutRtl(this.mSearchContainer) && marginLayoutParams.leftMargin != dimensionPixelSize) {
            marginLayoutParams.leftMargin = dimensionPixelSize;
        } else if (!ViewUtils.isLayoutRtl(this.mSearchContainer) && marginLayoutParams.rightMargin != dimensionPixelSize) {
            marginLayoutParams.rightMargin = dimensionPixelSize;
        }
        this.mSearchContainer.setLayoutParams(marginLayoutParams);
    }

    public ModelType onModelSelected() {
        return ModelType.MODEL_BACK;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEndViewEnabled(boolean z) {
        View view = this.mEndView;
        boolean z2 = view != null && view.getVisibility() == 0;
        if (this.mModel == ModelType.MODEL_BACK_SEARCH && z2 && this.mEndView.isEnabled() != z) {
            this.mEndView.setEnabled(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSearchEvent() {
        EditText editText = this.mInput;
        String string = editText != null ? editText.getText().toString() : null;
        if (string == null || TextUtils.isEmpty(string)) {
            return;
        }
        ensureResultFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.container, this.mResult, TAG_RESULT).commitNowAllowingStateLoss();
        this.mResult.onActionSearch(string);
        hideSoftIME(this.mInput);
    }

    private void hideSoftIME(View view) {
        InputMethodManager inputMethodManager;
        Context context = getContext();
        if (view == null || context == null || (inputMethodManager = (InputMethodManager) ContextCompat.getSystemService(context, InputMethodManager.class)) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
