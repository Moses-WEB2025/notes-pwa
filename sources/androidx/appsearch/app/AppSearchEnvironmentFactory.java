package androidx.appsearch.app;

/* JADX INFO: loaded from: classes.dex */
public class AppSearchEnvironmentFactory {
    private static volatile AppSearchEnvironment sAppSearchEnvironment;

    public static AppSearchEnvironment getEnvironmentInstance() {
        AppSearchEnvironment jetpackAppSearchEnvironment;
        AppSearchEnvironment appSearchEnvironment = sAppSearchEnvironment;
        if (appSearchEnvironment != null) {
            return appSearchEnvironment;
        }
        synchronized (AppSearchEnvironmentFactory.class) {
            jetpackAppSearchEnvironment = sAppSearchEnvironment;
            if (jetpackAppSearchEnvironment == null) {
                jetpackAppSearchEnvironment = new JetpackAppSearchEnvironment();
                sAppSearchEnvironment = jetpackAppSearchEnvironment;
            }
        }
        return jetpackAppSearchEnvironment;
    }

    public static void setEnvironmentInstanceForTest(AppSearchEnvironment appSearchEnvironment) {
        synchronized (AppSearchEnvironmentFactory.class) {
            sAppSearchEnvironment = appSearchEnvironment;
        }
    }

    private AppSearchEnvironmentFactory() {
    }
}
