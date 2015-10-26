package com.elegion.androidschool.finalproject.event;

/**
 * Created by Aleksandra on 26.10.15.
 */
public class MarketSelectedEvent {
    private long mMarketId;

    public MarketSelectedEvent(long marketId) {
        mMarketId = marketId;
    }

    public long getMarketId() {
        return mMarketId;
    }
}
