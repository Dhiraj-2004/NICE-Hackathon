package com.nice.coday;

public class ChargingStation {
    private String chargingStation;
    private int distanceFromStart;

    public ChargingStation(String chargingStation, int distanceFromStart) {
        this.chargingStation = chargingStation;
        this.distanceFromStart = distanceFromStart;
    }

    public String getChargingStation() {
        return chargingStation;
    }

    public void setChargingStation(String chargingStation) {
        this.chargingStation = chargingStation;
    }

    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(int distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }
}
