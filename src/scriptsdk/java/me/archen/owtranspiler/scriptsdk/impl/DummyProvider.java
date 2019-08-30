package me.archen.owtranspiler.scriptsdk.impl;

public interface DummyProvider {

    static <T> T createDummy(Object... data) {
        throw new UnsupportedOperationException("DummyProvider");
    }

}
