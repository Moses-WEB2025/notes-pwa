package com.miui.todo.base;

/* JADX INFO: loaded from: classes4.dex */
public interface IBaseContract {

    public interface Model<T> {
        void setPresenter(T t);
    }

    public interface Presenter {
        void destroy();

        void subscribe();

        void unsubscribe();
    }

    public interface View<T> {
        void setPresenter(T t);
    }
}
