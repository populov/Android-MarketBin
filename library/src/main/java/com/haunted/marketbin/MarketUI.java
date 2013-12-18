package com.haunted.marketbin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class MarketUI {
    public static void bindAppLink(@NotNull TextView view,
                                   @NotNull IMarketDescriptor marketDescriptor,
                                   @NotNull String packageName) {
        String displayName = marketDescriptor.getMarketName();
        if (marketDescriptor.getIntentFormat() == null)
            throw new NullPointerException("MarketDescriptor.intentFormat == null for " + displayName);
        String url = String.format(marketDescriptor.getIntentFormat(), packageName);
        String href = String.format("<a href='%s'>%s</a>", url, displayName);
        view.setText(Html.fromHtml(href));
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void showAppInMarket(@NotNull Context context,
                                       @NotNull IMarketDescriptor marketDescriptor,
                                       @NotNull String packageName) {
        String url = String.format(marketDescriptor.getIntentFormat(), packageName);
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(appIntent);
    }
}
