package org.jsoup.helper;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/* JADX INFO: loaded from: classes6.dex */
class AuthenticationHandler extends Authenticator {
    static final int MaxAttempts = 3;
    static AuthShim handler;
    int attemptCount = 0;
    RequestAuthenticator auth;

    interface AuthShim {
        void enable(RequestAuthenticator requestAuthenticator, Object obj);

        AuthenticationHandler get(AuthenticationHandler authenticationHandler);

        void remove();
    }

    static {
        try {
            handler = (AuthShim) Class.forName("org.jsoup.helper.RequestAuthHandler").getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (ClassNotFoundException unused) {
            handler = new GlobalHandler();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    AuthenticationHandler() {
    }

    AuthenticationHandler(RequestAuthenticator requestAuthenticator) {
        this.auth = requestAuthenticator;
    }

    @Override // java.net.Authenticator
    public final PasswordAuthentication getPasswordAuthentication() {
        AuthenticationHandler authenticationHandler = handler.get(this);
        if (authenticationHandler == null) {
            return null;
        }
        int i = authenticationHandler.attemptCount + 1;
        authenticationHandler.attemptCount = i;
        if (i > 3 || authenticationHandler.auth == null) {
            return null;
        }
        return authenticationHandler.auth.authenticate(new RequestAuthenticator.Context(getRequestingURL(), getRequestorType(), getRequestingPrompt()));
    }

    static class GlobalHandler implements AuthShim {
        static ThreadLocal<AuthenticationHandler> authenticators = new ThreadLocal<>();

        GlobalHandler() {
        }

        static {
            Authenticator.setDefault(new AuthenticationHandler());
        }

        @Override // org.jsoup.helper.AuthenticationHandler.AuthShim
        public void enable(RequestAuthenticator requestAuthenticator, Object obj) {
            authenticators.set(new AuthenticationHandler(requestAuthenticator));
        }

        @Override // org.jsoup.helper.AuthenticationHandler.AuthShim
        public void remove() {
            authenticators.remove();
        }

        @Override // org.jsoup.helper.AuthenticationHandler.AuthShim
        public AuthenticationHandler get(AuthenticationHandler authenticationHandler) {
            return authenticators.get();
        }
    }
}
