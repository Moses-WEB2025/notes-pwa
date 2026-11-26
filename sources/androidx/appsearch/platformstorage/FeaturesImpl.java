package androidx.appsearch.platformstorage;

import android.content.Context;
import android.os.Build;
import androidx.appsearch.app.Features;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.core.util.Preconditions;
import com.google.common.base.Ascii;

/* JADX INFO: loaded from: classes.dex */
final class FeaturesImpl implements Features {
    private final Context mContext;

    FeaturesImpl(Context context) {
        this.mContext = (Context) Preconditions.checkNotNull(context);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // androidx.appsearch.app.Features
    public boolean isFeatureSupported(String str) {
        if (Build.VERSION.SDK_INT < 33) {
            return false;
        }
        str.hashCode();
        byte b = -1;
        switch (str.hashCode()) {
            case -2129936336:
                if (str.equals(Features.SEARCH_AND_CLICK_ACCUMULATOR)) {
                    b = 0;
                }
                break;
            case -2126994802:
                if (str.equals(Features.GLOBAL_SEARCH_SESSION_GET_SCHEMA)) {
                    b = 1;
                }
                break;
            case -1990346520:
                if (str.equals(Features.SEARCH_SPEC_SET_SEARCH_SOURCE_LOG_TAG)) {
                    b = 2;
                }
                break;
            case -1870138211:
                if (str.equals(Features.ENTERPRISE_GLOBAL_SEARCH_SESSION)) {
                    b = 3;
                }
                break;
            case -1846846381:
                if (str.equals(Features.SCHEMA_ADD_PARENT_TYPE)) {
                    b = 4;
                }
                break;
            case -1699543354:
                if (str.equals(Features.SCHEMA_EMBEDDING_PROPERTY_CONFIG)) {
                    b = 5;
                }
                break;
            case -1563005231:
                if (str.equals(Features.SEARCH_SPEC_ADVANCED_RANKING_EXPRESSION)) {
                    b = 6;
                }
                break;
            case -1529577860:
                if (str.equals(Features.ADD_PERMISSIONS_AND_GET_VISIBILITY)) {
                    b = 7;
                }
                break;
            case -1500373575:
                if (str.equals(Features.SEARCH_SPEC_GROUPING_TYPE_PER_SCHEMA)) {
                    b = 8;
                }
                break;
            case -1469615115:
                if (str.equals(Features.JOIN_SPEC_AND_QUALIFIED_ID)) {
                    b = 9;
                }
                break;
            case -1355782489:
                if (str.equals(Features.SET_SCHEMA_CIRCULAR_REFERENCES)) {
                    b = 10;
                }
                break;
            case -1283455818:
                if (str.equals(Features.GLOBAL_SEARCH_SESSION_REGISTER_OBSERVER_CALLBACK)) {
                    b = 11;
                }
                break;
            case -947570458:
                if (str.equals(Features.TOKENIZER_TYPE_RFC822)) {
                    b = 12;
                }
                break;
            case -918282283:
                if (str.equals("LIST_FILTER_QUERY_LANGUAGE")) {
                    b = 13;
                }
                break;
            case -817612637:
                if (str.equals(Features.SET_SCHEMA_REQUEST_ADD_SCHEMA_TYPE_VISIBLE_TO_CONFIG)) {
                    b = 14;
                }
                break;
            case -367340628:
                if (str.equals(Features.SCHEMA_ADD_INDEXABLE_NESTED_PROPERTIES)) {
                    b = 15;
                }
                break;
            case -275187393:
                if (str.equals(Features.SEARCH_SPEC_ADD_INFORMATIONAL_RANKING_EXPRESSIONS)) {
                    b = 16;
                }
                break;
            case -111108017:
                if (str.equals(Features.SEARCH_RESULT_PARENT_TYPES)) {
                    b = 17;
                }
                break;
            case -110245711:
                if (str.equals(Features.SEARCH_RESULT_MATCH_INFO_SUBMATCH)) {
                    b = 18;
                }
                break;
            case 278741751:
                if (str.equals("LIST_FILTER_HAS_PROPERTY_FUNCTION")) {
                    b = 19;
                }
                break;
            case 564641745:
                if (str.equals("VERBATIM_SEARCH")) {
                    b = Ascii.DC4;
                }
                break;
            case 609101686:
                if (str.equals(Features.GLOBAL_SEARCH_SESSION_GET_BY_ID)) {
                    b = Ascii.NAK;
                }
                break;
            case 686278208:
                if (str.equals(Features.INDEXER_MOBILE_APPLICATIONS)) {
                    b = Ascii.SYN;
                }
                break;
            case 1315166622:
                if (str.equals(Features.SEARCH_SPEC_RANKING_FUNCTION_MAX_MIN_OR_DEFAULT)) {
                    b = Ascii.ETB;
                }
                break;
            case 1468618014:
                if (str.equals(Features.SEARCH_SPEC_PROPERTY_WEIGHTS)) {
                    b = Ascii.CAN;
                }
                break;
            case 1485936891:
                if (str.equals(Features.SEARCH_SUGGESTION)) {
                    b = Ascii.EM;
                }
                break;
            case 1595767610:
                if (str.equals("NUMERIC_SEARCH")) {
                    b = Ascii.SUB;
                }
                break;
            case 1613726827:
                if (str.equals(Features.SEARCH_SPEC_RANKING_FUNCTION_FILTER_BY_RANGE)) {
                    b = Ascii.ESC;
                }
                break;
            case 1655985039:
                if (str.equals(Features.SEARCH_SPEC_ADD_FILTER_PROPERTIES)) {
                    b = Ascii.FS;
                }
                break;
            case 1991860567:
                if (str.equals(Features.SET_SCHEMA_REQUEST_SET_PUBLICLY_VISIBLE)) {
                    b = Ascii.GS;
                }
                break;
        }
        switch (b) {
            case 0:
                return isFeatureSupported(Features.JOIN_SPEC_AND_QUALIFIED_ID);
            case 1:
            case 7:
            case 11:
            case 18:
            case 21:
                return Build.VERSION.SDK_INT >= 33;
            case 2:
            case 3:
            case 4:
            case 8:
            case 14:
            case 15:
            case 19:
            case 28:
            case 29:
                return Build.VERSION.SDK_INT >= 35;
            case 5:
            case 16:
            case 17:
                return AppSearchVersionUtil.isAtLeastB();
            case 6:
            case 9:
            case 10:
            case 12:
            case 13:
            case 20:
            case 24:
            case 25:
            case 26:
                return Build.VERSION.SDK_INT >= 34;
            case 22:
                return AppSearchVersionUtil.isAtLeastB() || AppSearchVersionUtil.getAppSearchVersionCode(this.mContext) >= AppSearchVersionUtil.APPSEARCH_M2024_11_VERSION_CODE;
            case 23:
            case 27:
                return Build.VERSION.SDK_INT >= 35 || AppSearchVersionUtil.getAppSearchVersionCode(this.mContext) >= AppSearchVersionUtil.APPSEARCH_V_BASE_VERSION_CODE;
            default:
                return false;
        }
    }

    @Override // androidx.appsearch.app.Features
    public int getMaxIndexedProperties() {
        if (Build.VERSION.SDK_INT >= 34) {
            return 64;
        }
        return (Build.VERSION.SDK_INT != 33 || AppSearchVersionUtil.getAppSearchVersionCode(this.mContext) < AppSearchVersionUtil.APPSEARCH_U_BASE_VERSION_CODE) ? 16 : 64;
    }
}
