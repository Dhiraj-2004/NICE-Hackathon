package com.nice.coday;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ElectricityConsumptionCalculatorImpl implements ElectricityConsumptionCalculator {

    @Override
    public ConsumptionResult calculateElectricityAndTimeConsumption(ResourceInfo resourceInfo) throws IOException {
        ConsumptionResult consumptionResult = new ConsumptionResult();

        //Loading data from CSV files
        Map<String, ChargingStation> chargingStations = loadChargingStations(resourceInfo.getChargingStationInfoPath());
        Map<String, EntryExitPoint> entryExitPoints = loadEntryExitPoints(resourceInfo.getEntryExitPointInfoPath());
        Map<String, VehicleType> vehicleTypes = loadVehicleTypes(resourceInfo.getVehicleTypeInfoPath());
        Map<String, Map<String, Integer>> chargeTimes = loadChargeTimes(resourceInfo.getTimeToChargeVehicleInfoPath());
        Map<String, TripDetail> tripDetails = loadTripDetails(resourceInfo.getTripDetailsPath());

        //Processing each trip
        for(TripDetail trip : tripDetails.values()) {
            VehicleType vehicle = vehicleTypes.get(trip.getVehicleType());
            int distance = entryExitPoints.get(trip.getExitPoint()).getDistanceFromStart() - entryExitPoints.get(trip.getEntryPoint()).getDistanceFromStart();
            int batteryCapacity = vehicle.getNumberOfUnitsForFullyCharge();
            int remainingBattery = (int) (batteryCapacity * (trip.getRemainingBatteryPercentage() / 100.0));

            int energyNeeded = Math.max(0, (distance / vehicle.getMileage()) - remainingBattery);
            if(energyNeeded > 0) {
                String lastChargingStation = findLastChargingStation(chargingStations, distance, vehicle.getMileage());
                if(lastChargingStation != null) {
                    int chargeTime = chargeTimes.get(vehicle.getVehicleType()).get(lastChargingStation) * energyNeeded;
                    ConsumptionDetails details = consumptionResult.getConsumptionDetails(vehicle.getVehicleType());
                    if(details == null) {
                        details = new ConsumptionDetails(vehicle.getVehicleType());
                        consumptionResult.addConsumptionDetails(details);
                    }
                    details.setTotalUnitConsumed(energyNeeded);
                    details.setTotalTimeRequired(chargeTime);
                    consumptionResult.setTotalChargingStationTime(lastChargingStation, (long) chargeTime);
                }
            } else {
                ConsumptionDetails details = consumptionResult.getConsumptionDetails(vehicle.getVehicleType());
                if(details == null) {
                    details = new ConsumptionDetails(vehicle.getVehicleType());
                    consumptionResult.addConsumptionDetails(details);
                }
                details.incrementtripsFinished();
            }
        }

        return consumptionResult;
    }

    //Loading charging stations from CSV
    private Map<String, ChargingStation> loadChargingStations(Path path) throws IOException {
        Map<String, ChargingStation> chargingStations = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            br.readLine();
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                chargingStations.put(values[0], new ChargingStation(values[0], Integer.parseInt(values[1])));
            }
        }
        return chargingStations;
    }

    //Loading entry-exit points from CSV
    private Map<String, EntryExitPoint> loadEntryExitPoints(Path path) throws IOException {
        Map<String, EntryExitPoint> entryExitPoints = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            br.readLine(); // Skip header
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                entryExitPoints.put(values[0], new EntryExitPoint(values[0], Integer.parseInt(values[1])));
            }
        }
        return entryExitPoints;
    }

    //Loading vehicle types from CSV
    private Map<String, VehicleType> loadVehicleTypes(Path path) throws IOException {
        Map<String, VehicleType> vehicleTypes = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            br.readLine(); // Skip header
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                vehicleTypes.put(values[0], new VehicleType(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2])));
            }
        }
        return vehicleTypes;
    }

    //Loading charge times from CSV
    private Map<String, Map<String, Integer>> loadChargeTimes(Path path) throws IOException {
        Map<String, Map<String, Integer>> chargeTimes = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            br.readLine(); // Skip header
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                chargeTimes.putIfAbsent(values[0], new HashMap<>());
                chargeTimes.get(values[0]).put(values[1], Integer.parseInt(values[2]));
            }
        }
        return chargeTimes;
    }

    //Loading trip details from CSV
    private Map<String, TripDetail> loadTripDetails(Path path) throws IOException {
        Map<String, TripDetail> tripDetails = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                tripDetails.put(values[0], new TripDetail(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]));
            }
        }
        return tripDetails;
    }

    //Finding the last charging station that the vehicle can reach
    private String findLastChargingStation(Map<String, ChargingStation> chargingStations, int distance, int mileage) {
        return chargingStations.values().stream()
                .filter(station -> station.getDistanceFromStart() <= distance)
                .map(ChargingStation::getChargingStation)
                .reduce((first, second) -> second)
                .orElse(null);
    }
}
