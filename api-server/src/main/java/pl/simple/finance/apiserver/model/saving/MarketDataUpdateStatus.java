package pl.simple.finance.apiserver.model.saving;

import lombok.Getter;

public class MarketDataUpdateStatus {

    @Getter
    private boolean isUpdated;

    public MarketDataUpdateStatus() {
        this.isUpdated = false;
    }

    public void startUpdate() {
        this.isUpdated = true;
    }

    public void endUpdate() {
        this.isUpdated = false;
    }

}
