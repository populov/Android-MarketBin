package com.haunted.marketbin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MarketDescriptor implements IMarketDescriptor {
    private final String marketName;
    private final String intentFormat;
    private final String packageName;

    public MarketDescriptor(@NotNull String marketName,
                            @Nullable String intentFormat,
                            @Nullable String packageName) {
        this.marketName = marketName;
        this.intentFormat = intentFormat;
        this.packageName = packageName;
    }

    @Override
    public String getMarketName() {
        return marketName;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getIntentFormat() {
        return intentFormat;
    }
}
