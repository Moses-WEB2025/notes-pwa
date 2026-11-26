package com.miui.creation.logger.inner;

import android.util.Log;
import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.ClassicConstants;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.hook.DefaultShutdownHook;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.FileSize;
import com.miui.creation.logger.LoggerConfig;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryHijacker;
import org.slf4j.Marker;
import org.slf4j.helpers.SubstituteLogger;

/* JADX INFO: loaded from: classes2.dex */
public class XCLoggerFactory {
    private static final String APPENDER_APP_FILE = "APP_FILE";
    private static final String APPENDER_ASYNC_APP_FILE = "ASYNC_APP_FILE";
    private static final String APPENDER_ASYNC_ENGINE_FILE = "ASYNC_ENGINE_FILE";
    private static final String APPENDER_ENGINE_FILE = "ENGINE_FILE";
    private static final String APPENDER_LOGCAT = "LOGCAT";
    private static final String APP_LOGGER = "AppLogger";
    private static final int APP_LOG_FILE_SIZE = 15728640;
    private static final int APP_LOG_HISTORY = 14;
    private static final String ENGINE_LOGGER = "EngineLogger";
    private static final int ENGINE_LOG_FILE_SIZE = 15728640;
    private static final int ENGINE_LOG_HISTORY = 14;
    private static final String TAG = "XCLoggerFactory";
    private volatile boolean mIsConfigureFinished;
    private volatile boolean mIsConfigureStarted;
    private volatile boolean mIsConfigureSuccessful;

    private static class LoggerCache {
        private static final ConcurrentHashMap<Logger, RedirectLogger> sLoggerCache = new ConcurrentHashMap<>();

        private LoggerCache() {
        }

        public static RedirectLogger getRedirectLogger(Logger logger) {
            ConcurrentHashMap<Logger, RedirectLogger> concurrentHashMap = sLoggerCache;
            RedirectLogger redirectLogger = concurrentHashMap.get(logger);
            if (redirectLogger != null) {
                return redirectLogger;
            }
            RedirectLogger redirectLogger2 = new RedirectLogger(logger);
            concurrentHashMap.put(logger, redirectLogger2);
            return redirectLogger2;
        }

        public static void invalidateAll() {
            sLoggerCache.clear();
        }
    }

    private XCLoggerFactory() {
        configure();
    }

    private static final class SingletonHolder {
        private static final XCLoggerFactory INSTANCE = new XCLoggerFactory();

        private SingletonHolder() {
        }
    }

    static XCLoggerFactory instance() {
        return SingletonHolder.INSTANCE;
    }

    public static Logger getAppLogger(String str) {
        if (instance().initialized()) {
            return LoggerFactory.getLogger("AppLogger." + str);
        }
        Logger logger = LoggerFactoryHijacker.getLoggerFactory().getLogger("AppLogger." + str);
        if (!(logger instanceof SubstituteLogger)) {
            return logger;
        }
        Log.w(TAG, "getAppLogger failed, SubstituteLogger was returned for " + str);
        return LoggerCache.getRedirectLogger(logger);
    }

    public static Logger getEngineLogger(String str) {
        if (instance().initialized()) {
            return LoggerFactory.getLogger("EngineLogger." + str);
        }
        Logger logger = LoggerFactoryHijacker.getLoggerFactory().getLogger("EngineLogger." + str);
        if (!(logger instanceof SubstituteLogger)) {
            return logger;
        }
        Log.w(TAG, "getEngineLogger failed, SubstituteLogger was returned for " + str);
        return LoggerCache.getRedirectLogger(logger);
    }

    private synchronized void configure() {
        if (!this.mIsConfigureFinished && !this.mIsConfigureStarted) {
            Log.d(TAG, "Starting configure");
            this.mIsConfigureStarted = true;
            new Thread(new ConfigurationTask(), "LoggerInit").start();
        }
    }

    private boolean initialized() {
        if (this.mIsConfigureFinished) {
            return this.mIsConfigureSuccessful;
        }
        return LoggerFactoryHijacker.isInitializeStarted();
    }

    private static void configureLogger(LoggerContext loggerContext) {
        StatusManager statusManager = loggerContext.getStatusManager();
        if (statusManager != null) {
            statusManager.add(new StatusListener() { // from class: com.miui.creation.logger.inner.XCLoggerFactory$$ExternalSyntheticLambda0
                @Override // ch.qos.logback.core.status.StatusListener
                public final void addStatusEvent(Status status) {
                    XCLoggerFactory.lambda$configureLogger$0(status);
                }
            });
            statusManager.add(new InfoStatus("Setting up root logger.", loggerContext));
        }
        LogcatTurboFilter logcatTurboFilter = new LogcatTurboFilter();
        logcatTurboFilter.start();
        loggerContext.addTurboFilter(logcatTurboFilter);
        Level logcatLevel = LoggerConfig.getInstance().getLogcatLevel();
        LogcatFilter logcatFilter = new LogcatFilter();
        logcatFilter.setLevel(logcatLevel);
        LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder = new LayoutWrappingEncoder<>();
        layoutWrappingEncoder.setContext(loggerContext);
        LogcatLayout logcatLayout = new LogcatLayout();
        logcatLayout.setContext(loggerContext);
        logcatLayout.start();
        layoutWrappingEncoder.setLayout(logcatLayout);
        layoutWrappingEncoder.start();
        LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder2 = new LayoutWrappingEncoder<>();
        layoutWrappingEncoder2.setContext(loggerContext);
        LogcatTagLayout logcatTagLayout = new LogcatTagLayout();
        logcatTagLayout.setContext(loggerContext);
        logcatTagLayout.start();
        layoutWrappingEncoder2.setLayout(logcatTagLayout);
        layoutWrappingEncoder2.start();
        LogcatAppender logcatAppender = new LogcatAppender();
        logcatAppender.setContext(loggerContext);
        logcatAppender.setName(APPENDER_LOGCAT);
        logcatAppender.addFilter(logcatFilter);
        logcatAppender.setEncoder(layoutWrappingEncoder);
        logcatAppender.setTagEncoder(layoutWrappingEncoder2);
        logcatAppender.start();
        ch.qos.logback.classic.Logger logger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(logcatLevel);
        logger.addAppender(logcatAppender);
        LayoutWrappingEncoder layoutWrappingEncoder3 = new LayoutWrappingEncoder();
        layoutWrappingEncoder3.setContext(loggerContext);
        TTLLLayout tTLLLayout = new TTLLLayout();
        tTLLLayout.setContext(loggerContext);
        tTLLLayout.start();
        layoutWrappingEncoder3.setLayout(tTLLLayout);
        String fileLogPath = LoggerConfig.getInstance().getFileLogPath();
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setContext(loggerContext);
        rollingFileAppender.setName(APPENDER_ENGINE_FILE);
        rollingFileAppender.setLazy(true);
        rollingFileAppender.setFile(fileLogPath + File.separator + "engine.log");
        rollingFileAppender.setEncoder(layoutWrappingEncoder3);
        rollingFileAppender.addFilter(new Filter<ILoggingEvent>() { // from class: com.miui.creation.logger.inner.XCLoggerFactory.1
            @Override // ch.qos.logback.core.filter.Filter
            public FilterReply decide(ILoggingEvent iLoggingEvent) {
                Marker marker = iLoggingEvent.getMarker();
                if (marker != null && (marker.contains(Markers.getFileMarker()) || marker.contains(Markers.getFileOnlyMarker()))) {
                    return FilterReply.ACCEPT;
                }
                int i = iLoggingEvent.getLevel().toInt();
                if (i == 30000 || i == 40000) {
                    return FilterReply.ACCEPT;
                }
                return FilterReply.DENY;
            }
        });
        SizeAndTimeBasedRollingPolicy sizeAndTimeBasedRollingPolicy = new SizeAndTimeBasedRollingPolicy();
        sizeAndTimeBasedRollingPolicy.setContext(loggerContext);
        sizeAndTimeBasedRollingPolicy.setMaxHistory(14);
        sizeAndTimeBasedRollingPolicy.setMaxFileSize(new FileSize(15728640L));
        sizeAndTimeBasedRollingPolicy.setTotalSizeCap(FileSize.valueOf("35 mb"));
        sizeAndTimeBasedRollingPolicy.setCleanHistoryOnStart(true);
        sizeAndTimeBasedRollingPolicy.setFileNamePattern(fileLogPath + File.separator + "engine-%d{yyyy-MM-dd}.%i.gz");
        sizeAndTimeBasedRollingPolicy.setParent(rollingFileAppender);
        rollingFileAppender.setRollingPolicy(sizeAndTimeBasedRollingPolicy);
        sizeAndTimeBasedRollingPolicy.start();
        rollingFileAppender.start();
        AsyncAppender asyncAppender = new AsyncAppender();
        asyncAppender.setContext(loggerContext);
        asyncAppender.setName(APPENDER_ASYNC_ENGINE_FILE);
        asyncAppender.setQueueSize(256);
        asyncAppender.setDiscardingThreshold(32);
        asyncAppender.setNeverBlock(true);
        asyncAppender.addAppender(rollingFileAppender);
        asyncAppender.start();
        loggerContext.getLogger(ENGINE_LOGGER).addAppender(asyncAppender);
        RollingFileAppender rollingFileAppender2 = new RollingFileAppender();
        rollingFileAppender2.setContext(loggerContext);
        rollingFileAppender2.setName(APPENDER_APP_FILE);
        rollingFileAppender2.setLazy(true);
        rollingFileAppender2.setFile(fileLogPath + File.separator + "app.log");
        rollingFileAppender2.setEncoder(layoutWrappingEncoder3);
        rollingFileAppender2.addFilter(new Filter<ILoggingEvent>() { // from class: com.miui.creation.logger.inner.XCLoggerFactory.2
            @Override // ch.qos.logback.core.filter.Filter
            public FilterReply decide(ILoggingEvent iLoggingEvent) {
                Marker marker = iLoggingEvent.getMarker();
                if (marker != null && marker.contains(Markers.getFileMarker())) {
                    return FilterReply.ACCEPT;
                }
                int i = iLoggingEvent.getLevel().toInt();
                if (i == 30000 || i == 40000) {
                    return FilterReply.ACCEPT;
                }
                return FilterReply.DENY;
            }
        });
        SizeAndTimeBasedRollingPolicy sizeAndTimeBasedRollingPolicy2 = new SizeAndTimeBasedRollingPolicy();
        sizeAndTimeBasedRollingPolicy2.setContext(loggerContext);
        sizeAndTimeBasedRollingPolicy2.setMaxHistory(14);
        sizeAndTimeBasedRollingPolicy2.setMaxFileSize(new FileSize(15728640L));
        sizeAndTimeBasedRollingPolicy2.setTotalSizeCap(FileSize.valueOf("50 mb"));
        sizeAndTimeBasedRollingPolicy2.setCleanHistoryOnStart(true);
        sizeAndTimeBasedRollingPolicy2.setFileNamePattern(fileLogPath + File.separator + "app-%d{yyyy-MM-dd}.%i.gz");
        sizeAndTimeBasedRollingPolicy2.setParent(rollingFileAppender2);
        rollingFileAppender2.setRollingPolicy(sizeAndTimeBasedRollingPolicy2);
        sizeAndTimeBasedRollingPolicy2.start();
        rollingFileAppender2.start();
        AsyncAppender asyncAppender2 = new AsyncAppender();
        asyncAppender2.setContext(loggerContext);
        asyncAppender2.setName(APPENDER_ASYNC_APP_FILE);
        asyncAppender2.setQueueSize(128);
        asyncAppender2.setDiscardingThreshold(16);
        asyncAppender2.setNeverBlock(true);
        asyncAppender2.addAppender(rollingFileAppender2);
        asyncAppender2.start();
        loggerContext.getLogger(APP_LOGGER).addAppender(asyncAppender2);
    }

    static /* synthetic */ void lambda$configureLogger$0(Status status) {
        int level = status.getLevel();
        if (level == 1) {
            Log.w("logback", status.getMessage(), status.getThrowable());
        } else if (level == 2) {
            Log.e("logback", status.getMessage(), status.getThrowable());
        } else {
            Log.d("logback", status.getMessage(), status.getThrowable());
        }
    }

    public void doInit(LoggerContext loggerContext) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        configureLogger(loggerContext);
        if (((Thread) loggerContext.getObject(CoreConstants.SHUTDOWN_HOOK_THREAD)) == null) {
            Thread thread = new Thread(new DefaultShutdownHook(), "Logback shutdown hook [" + loggerContext.getName() + "]");
            loggerContext.putObject(CoreConstants.SHUTDOWN_HOOK_THREAD, thread);
            Runtime.getRuntime().addShutdownHook(thread);
        }
        Log.d(TAG, "doInit costs: " + (System.currentTimeMillis() - jCurrentTimeMillis));
    }

    private class ConfigurationTask implements Runnable {
        private ConfigurationTask() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r9v3, types: [com.miui.creation.logger.inner.XCLoggerFactory] */
        @Override // java.lang.Runnable
        public void run() {
            try {
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    System.setProperty(ClassicConstants.LOGBACK_CONTEXT_SELECTOR, "com.miui.creation.logger.inner.ContextSelectorHijacker");
                    LoggerFactory.getILoggerFactory();
                    XCLoggerFactory.this.mIsConfigureSuccessful = true;
                    Log.d(XCLoggerFactory.TAG, "Configure loggers costs " + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms");
                    LoggerCache.invalidateAll();
                    System.setProperty(ClassicConstants.LOGBACK_CONTEXT_SELECTOR, "");
                } catch (Exception e) {
                    XCLoggerFactory.this.mIsConfigureSuccessful = false;
                    Log.e(XCLoggerFactory.TAG, "Configure failed, what should not happen.", e);
                }
            } finally {
                XCLoggerFactory.this.mIsConfigureFinished = true;
            }
        }
    }
}
