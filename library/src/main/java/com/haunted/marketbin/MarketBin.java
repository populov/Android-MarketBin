package com.haunted.marketbin;

import java.util.ArrayList;

public class MarketBin {
    // All known market descriptors are got from static methods in order MarketBin
    // to consume less memory in runtime.
    // If you want to have them as static variables, you can always save any subset of
    // known markets as your own static variable.

    public static MarketDescriptor getGooglePlay() {
        return new MarketDescriptor("Google Play", "market://details?id=%s", "com.android.vending");
    }

    public static MarketDescriptor getGooglePlayWeb() {
        return new MarketDescriptor("Google Play WebSite", "https://play.google.com/store/apps/details?id=%s", null);
    }

    public static MarketDescriptor getSamsungApps() {
        return new MarketDescriptor("Samsung Apps", "samsungapps://ProductDetail/%s", "com.sec.android.app.samsungapps");
    }

    public static MarketDescriptor getSlideMe() {
        return new MarketDescriptor("SlideMe", "sam://details?id=%s", "com.slideme.sam.manager");
    }

    public static MarketDescriptor getYandexStore() {
        return new MarketDescriptor("Yandex.Store", "market://details?id=%s", "com.yandex.store");
    }

    public static ArrayList<MarketDescriptor> getAllKnown() {
        ArrayList<MarketDescriptor> result = new ArrayList<MarketDescriptor>(4);
        result.add(getSamsungApps());
        result.add(getSlideMe());
        result.add(getGooglePlay());
        result.add(getYandexStore());
        result.add(getGooglePlayWeb());
        return result;
    }
}
