package com.haunted.marketbin.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haunted.marketbin.IMarketDescriptor;
import com.haunted.marketbin.MarketBin;
import com.haunted.marketbin.MarketLocator;
import com.haunted.marketbin.MarketUI;

import java.util.ArrayList;
import java.util.List;

public class FragmentMultiple extends Fragment {
    private ViewGroup phAvailableMarkets;
    private ViewGroup phUnavailableMarkets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_multiple, container, false);
        assert v != null;
        phAvailableMarkets = (ViewGroup) v.findViewById(R.id.phAvailableMarkets);
        phUnavailableMarkets = (ViewGroup) v.findViewById(R.id.phUnavailableMarkets);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        MarketLocator locator = new MarketLocator(getActivity());
        ArrayList<? extends IMarketDescriptor> markets = MarketBin.getAllKnown();
        List<IMarketDescriptor> availableMarkets = locator.findAll(markets, true, true);
        if (availableMarkets != null && availableMarkets.size() > 0) {
            phAvailableMarkets.removeAllViews();
            for(IMarketDescriptor m: availableMarkets) {
                TextView tvLink = new TextView(getActivity());
                MarketUI.bindAppLink(tvLink, m, MainActivity.demoAppId);
                phAvailableMarkets.addView(tvLink);
                tvLink.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
                tvLink.setPadding(0, 7, 0, 7);
                markets.remove(m);
            }
        }

        if (markets.size() > 0) {
            phUnavailableMarkets.removeAllViews();
            for(IMarketDescriptor m: markets) {
                TextView tvName = new TextView(getActivity());
                tvName.setText(m.getMarketName());
                phUnavailableMarkets.addView(tvName);
            }
        }
    }
}
