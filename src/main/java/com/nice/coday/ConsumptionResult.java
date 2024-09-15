package com.nice.coday;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConsumptionResult {
    private Map<String, ConsumptionDetails> consumptionDetailsMap = new HashMap<>();
    private Map<String, Long> totalChargingStationTime = new HashMap<>();

    public void addConsumptionDetails(ConsumptionDetails details) {
        consumptionDetailsMap.put(details.getVehicleType(), details);
    }

    public ConsumptionDetails getConsumptionDetails(String vehicleType) {
        return consumptionDetailsMap.get(vehicleType);
    }

    public void setTotalChargingStationTime(String stationName, Long time) {
        totalChargingStationTime.merge(stationName, time, Long::sum);
    }

    public Map<String, ConsumptionDetails> getConsumptionDetailsMap() {
        return consumptionDetailsMap;
    }

    public Map<String, Long> getTotalChargingStationTime() {
        return totalChargingStationTime;
    }

    public Set<String> getVehicleTypes() {
        return consumptionDetailsMap.keySet();
    }

}
