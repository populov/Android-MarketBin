package com.haunted.marketbin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class MarketLocator {
    private final PackageManager packageManager;
    private List<PackageInfo> packages;


    public MarketLocator(Context context) {
        packageManager = context.getPackageManager();
    }

    private boolean isAppInstalled(String packageName) {
        if (packages == null) {
            if (packageManager == null)
                throw new NullPointerException("Can't get PackageManager");
            packages = packageManager.getInstalledPackages(0);
        }
        if (packages != null && packageName != null) {
            for (PackageInfo packageInfo : packages) {
                if (packageName.equals(packageInfo.packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isIntentAvailable(String appUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public IMarketDescriptor findByPackage(Iterable<? extends IMarketDescriptor> orderedMarketsList) {
        for (IMarketDescriptor m: orderedMarketsList) {
            if (m != null && isAppInstalled(m.getPackageName()))
                return m;
        }
        return null;
    }

    public IMarketDescriptor findByIntent(Iterable<? extends IMarketDescriptor> orderedMarketsList) {
        for (IMarketDescriptor m: orderedMarketsList) {
            if (m != null && isIntentAvailable(m.getIntentFormat()))
                return m;
        }
        return null;
    }

    public IMarketDescriptor findFirst(Iterable<? extends IMarketDescriptor> orderedMarketsList) {
        IMarketDescriptor result = findByPackage(orderedMarketsList);
        if (result != null)
            return result;
        return findByIntent(orderedMarketsList);
    }

    // WARNING: Does not check if application is present in listed AppMarkets, only checks if
    // market is available on current device.
    public List<IMarketDescriptor> findAll(Iterable<? extends IMarketDescriptor> orderedMarketsList,
                                          boolean checkPackage, boolean checkIntent) {
        List<IMarketDescriptor> result = new ArrayList<IMarketDescriptor>();
        for (IMarketDescriptor m: orderedMarketsList) {
            if ((checkPackage && isAppInstalled(m.getPackageName()))
                    || (checkIntent && isIntentAvailable(m.getIntentFormat())))
                result.add(m);
        }
        return result;
    }
}
