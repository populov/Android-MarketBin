package com.haunted.marketbin.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.haunted.marketbin.IMarketDescriptor;
import com.haunted.marketbin.MarketBin;
import com.haunted.marketbin.MarketLocator;
import com.haunted.marketbin.MarketUI;

public class FragmentSingle extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single, container, false);
        assert v != null;
        Button bShowIn = (Button) v.findViewById(R.id.bShowIn);
        TextView tvLink = (TextView) v.findViewById(R.id.tvLink);

        MarketLocator locator = new MarketLocator(getActivity());
        final IMarketDescriptor market = locator.findFirst(MarketBin.getAllKnown());
        bShowIn.setText(getString(R.string.show_in) + " " + market.getMarketName());
        bShowIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketUI.showAppInMarket(getActivity(), market, MainActivity.demoAppId);
            }
        });

        MarketUI.bindAppLink(tvLink, market, MainActivity.demoAppId);
        return v;
    }
}
