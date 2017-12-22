package com.hyperats.cache;

public class CachedNull {
    public CachedNull() {}

    private static final CachedNull instance = new CachedNull();

    public static CachedNull getInstance() {
        return instance;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CachedNull;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
