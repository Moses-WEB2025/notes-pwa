package com.miui.maml;

import com.miui.maml.elements.BitmapProvider;
import com.miui.maml.elements.ScreenElement;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ObjectFactory {
    public abstract String getName();

    public abstract ObjectFactory getOld();

    public abstract void setOld(ObjectFactory objectFactory);

    public static abstract class ObjectFactoryBase<T extends ObjectFactory> extends ObjectFactory {
        private String mName;
        protected T mOld;

        protected ObjectFactoryBase(String str) {
            this.mName = str;
        }

        @Override // com.miui.maml.ObjectFactory
        public T getOld() {
            return this.mOld;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.miui.maml.ObjectFactory
        public void setOld(ObjectFactory objectFactory) {
            this.mOld = objectFactory;
        }

        @Override // com.miui.maml.ObjectFactory
        public String getName() {
            return this.mName;
        }
    }

    public static abstract class BitmapProviderFactory extends ObjectFactoryBase<BitmapProviderFactory> {
        public static final String NAME = "BitmapProvider";

        protected abstract BitmapProvider doCreate(ScreenElementRoot screenElementRoot, String str);

        protected BitmapProviderFactory() {
            super(NAME);
        }

        public final BitmapProvider create(ScreenElementRoot screenElementRoot, String str) {
            BitmapProvider bitmapProviderDoCreate = doCreate(screenElementRoot, str);
            if (bitmapProviderDoCreate != null) {
                return bitmapProviderDoCreate;
            }
            if (this.mOld == 0) {
                return null;
            }
            return ((BitmapProviderFactory) this.mOld).create(screenElementRoot, str);
        }
    }

    public static abstract class ActionCommandFactory extends ObjectFactoryBase<ActionCommandFactory> {
        public static final String NAME = "ActionCommand";

        protected abstract ActionCommand doCreate(ScreenElement screenElement, Element element);

        protected ActionCommandFactory() {
            super("ActionCommand");
        }

        public final ActionCommand create(ScreenElement screenElement, Element element) {
            ActionCommand actionCommandDoCreate = doCreate(screenElement, element);
            if (actionCommandDoCreate != null) {
                return actionCommandDoCreate;
            }
            if (this.mOld == 0) {
                return null;
            }
            return ((ActionCommandFactory) this.mOld).create(screenElement, element);
        }
    }
}
