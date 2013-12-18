package com.haunted.marketbin.sample;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.haunted.marketbin.IMarketDescriptor;
import com.haunted.marketbin.MarketBin;
import com.haunted.marketbin.MarketDescriptor;
import com.haunted.marketbin.MarketLocator;
import com.haunted.marketbin.MarketUI;

import java.util.Arrays;

public class FragmentCredits extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_credits, container, false);
        assert v != null;
        TextView tvOfficeBuzz = (TextView) v.findViewById(R.id.tvOfficeBuzz);
        TextView tvTkShrink = (TextView) v.findViewById(R.id.tvTkShrink);

        MarketLocator locator = new MarketLocator(getActivity());

        String officeBuzz = getString(R.string.office_buzz);
        IMarketDescriptor[] officeBuzzPublishedIn = {
                new AppDescriptor(MarketBin.getGooglePlay(), officeBuzz),
                new AppDescriptor(MarketBin.getSamsungApps(), officeBuzz),
                new AppDescriptor(MarketBin.getSlideMe(), officeBuzz),
                new AppDescriptor(MarketBin.getGooglePlayWeb(), officeBuzz)
        };
        IMarketDescriptor market = locator.findFirst(Arrays.asList(officeBuzzPublishedIn));
        MarketUI.bindAppLink(tvOfficeBuzz, market, "com.haunted.office.buzz");

        String tkShrink = getString(R.string.tk_shrink);
        IMarketDescriptor[] tkShrinkPublishedIn = {
                new AppDescriptor(MarketBin.getGooglePlay(), tkShrink),
                new AppDescriptor(MarketBin.getGooglePlayWeb(), tkShrink)
        };
        market = locator.findFirst(Arrays.asList(tkShrinkPublishedIn));
        MarketUI.bindAppLink(tvTkShrink, market, "com.haunted.tkshrink");

        bindDonate("bitcoin:%s", R.string.bitcoin_address, R.id.tvBtcAddress, R.id.cpBtcAddress, v, locator);
        bindDonate("litecoin:%s", R.string.litecoin_address, R.id.tvLtcAddress, R.id.cpLtcAddress, v, locator);

        return v;
    }

    private void bindDonate(String scheme, int addressResId, int linkViewId, int cpAddressButtonId,
                            View rootView, MarketLocator locator) {
        TextView tvLink = (TextView) rootView.findViewById(linkViewId);
        Button copyAddress = (Button) rootView.findViewById(cpAddressButtonId);
        copyAddress.setOnClickListener(this);

        IMarketDescriptor donation = new MarketDescriptor(getString(R.string.donate), scheme, null);
        boolean found = locator.findByIntent(Arrays.asList(donation)) != null;
        tvLink.setVisibility(found ? View.VISIBLE : View.GONE);
        copyAddress.setVisibility(found ? View.GONE : View.VISIBLE);
        if (found)
            MarketUI.bindAppLink(tvLink, donation, getString(addressResId));
    }

    @Override
    public void onClick(View v) {
        String address;
        switch (v.getId()) {
            case R.id.cpBtcAddress:
                address = getString(R.string.bitcoin_address);
                break;
            case R.id.cpLtcAddress:
                address = getString(R.string.litecoin_address);
                break;
            default:
                return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            @SuppressWarnings("deprecation")
            android.text.ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(address);
        }
        else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(address, address);
            clipboard.setPrimaryClip(clip);
        }
        String message = String.format(getString(R.string.copied_to_clipboard, address));
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    private static class AppDescriptor implements IMarketDescriptor {
        private final IMarketDescriptor marketDescriptor;
        private final String appName;
        public AppDescriptor(IMarketDescriptor marketDescriptor, String appName) {
            this.marketDescriptor = marketDescriptor;
            this.appName = appName;
        }

        @Override
        public String getMarketName() {
            return appName;
        }

        @Override
        public String getPackageName() {
            return marketDescriptor.getPackageName();
        }

        @Override
        public String getIntentFormat() {
            return marketDescriptor.getIntentFormat();
        }
    }
}
