package ch.qos.logback.classic.net.server;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.net.server.ConcurrentServerRunner;
import ch.qos.logback.core.net.server.ServerListener;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
class RemoteAppenderServerRunner extends ConcurrentServerRunner<RemoteAppenderClient> {
    public RemoteAppenderServerRunner(ServerListener<RemoteAppenderClient> serverListener, Executor executor) {
        super(serverListener, executor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // ch.qos.logback.core.net.server.ConcurrentServerRunner
    public boolean configureClient(RemoteAppenderClient remoteAppenderClient) {
        remoteAppenderClient.setLoggerContext((LoggerContext) getContext());
        return true;
    }
}
