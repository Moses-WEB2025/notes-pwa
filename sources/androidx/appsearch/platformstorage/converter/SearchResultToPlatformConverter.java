package androidx.appsearch.platformstorage.converter;

import android.os.Build;
import android.util.Log;
import androidx.appsearch.app.SearchResult;
import androidx.appsearch.platformstorage.util.AppSearchVersionUtil;
import androidx.core.util.Preconditions;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SearchResultToPlatformConverter {
    private static final String TAG = "AppSearchSearchResPlatC";

    private SearchResultToPlatformConverter() {
    }

    public static SearchResult toJetpackSearchResult(android.app.appsearch.SearchResult searchResult) {
        Preconditions.checkNotNull(searchResult);
        SearchResult.Builder rankingSignal = new SearchResult.Builder(searchResult.getPackageName(), searchResult.getDatabaseName()).setGenericDocument(GenericDocumentToPlatformConverter.toJetpackGenericDocument(searchResult.getGenericDocument())).setRankingSignal(searchResult.getRankingSignal());
        List<android.app.appsearch.SearchResult.MatchInfo> matchInfos = searchResult.getMatchInfos();
        for (int i = 0; i < matchInfos.size(); i++) {
            rankingSignal.addMatchInfo(toJetpackMatchInfo(matchInfos.get(i)));
        }
        if (Build.VERSION.SDK_INT >= 34) {
            Iterator<android.app.appsearch.SearchResult> it = ApiHelperForU.getJoinedResults(searchResult).iterator();
            while (it.hasNext()) {
                rankingSignal.addJoinedResult(toJetpackSearchResult(it.next()));
            }
        }
        if (AppSearchVersionUtil.isAtLeastB()) {
            List<Double> informationalRankingSignals = ApiHelperForB.getInformationalRankingSignals(searchResult);
            for (int i2 = 0; i2 < informationalRankingSignals.size(); i2++) {
                rankingSignal.addInformationalRankingSignal(informationalRankingSignals.get(i2).doubleValue());
            }
            try {
                rankingSignal.setParentTypeMap(ApiHelperForB.getParentTypeMap(searchResult));
            } catch (NoSuchMethodError e) {
                Log.e(TAG, "Failed to set parent type map.", e);
            }
        }
        return rankingSignal.build();
    }

    private static SearchResult.MatchInfo toJetpackMatchInfo(android.app.appsearch.SearchResult.MatchInfo matchInfo) {
        Preconditions.checkNotNull(matchInfo);
        SearchResult.MatchInfo.Builder snippetRange = new SearchResult.MatchInfo.Builder(matchInfo.getPropertyPath()).setExactMatchRange(new SearchResult.MatchRange(matchInfo.getExactMatchRange().getStart(), matchInfo.getExactMatchRange().getEnd())).setSnippetRange(new SearchResult.MatchRange(matchInfo.getSnippetRange().getStart(), matchInfo.getSnippetRange().getEnd()));
        if (Build.VERSION.SDK_INT >= 33) {
            snippetRange.setSubmatchRange(new SearchResult.MatchRange(ApiHelperForT.getSubmatchRangeStart(matchInfo), ApiHelperForT.getSubmatchRangeEnd(matchInfo)));
        }
        return snippetRange.build();
    }

    private static class ApiHelperForT {
        private ApiHelperForT() {
        }

        static int getSubmatchRangeStart(android.app.appsearch.SearchResult.MatchInfo matchInfo) {
            return matchInfo.getSubmatchRange().getStart();
        }

        static int getSubmatchRangeEnd(android.app.appsearch.SearchResult.MatchInfo matchInfo) {
            return matchInfo.getSubmatchRange().getEnd();
        }
    }

    private static class ApiHelperForU {
        private ApiHelperForU() {
        }

        static List<android.app.appsearch.SearchResult> getJoinedResults(android.app.appsearch.SearchResult searchResult) {
            return searchResult.getJoinedResults();
        }
    }

    private static class ApiHelperForB {
        private ApiHelperForB() {
        }

        static List<Double> getInformationalRankingSignals(android.app.appsearch.SearchResult searchResult) {
            return searchResult.getInformationalRankingSignals();
        }

        static Map<String, List<String>> getParentTypeMap(android.app.appsearch.SearchResult searchResult) {
            return searchResult.getParentTypeMap();
        }
    }
}
