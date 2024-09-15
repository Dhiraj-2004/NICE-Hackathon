package com.nice.coday;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResourceInfo {

    private static final Logger logger = Logger.getLogger(ResourceInfo.class.getName());

    private Path chargingStationInfoPath;
    private Path entryExitPointInfoPath;
    private Path timeToChargeVehicleInfoPath;
    private Path tripDetailsPath;
    private Path vehicleTypeInfoPath;

    public ResourceInfo(Path chargingStationInfoPath,
                        Path entryExitPointInfoPath,
                        Path timeToChargeVehicleInfoPath,
                        Path tripDetailsPath,
                        Path vehicleTypeInfoPath) {
        this.entryExitPointInfoPath = entryExitPointInfoPath;
        this.timeToChargeVehicleInfoPath = timeToChargeVehicleInfoPath;
        this.chargingStationInfoPath = chargingStationInfoPath;
        this.tripDetailsPath = tripDetailsPath;
        this.vehicleTypeInfoPath = vehicleTypeInfoPath;
    }

    public Path getChargingStationInfoPath() {
        return chargingStationInfoPath;
    }

    public void setChargingStationInfoPath(Path chargingStationInfoPath) {
        this.chargingStationInfoPath = chargingStationInfoPath;
    }

    public Path getEntryExitPointInfoPath() {
        return entryExitPointInfoPath;
    }

    public void setEntryExitPointInfoPath(Path entryExitPointInfoPath) {
        this.entryExitPointInfoPath = entryExitPointInfoPath;
    }

    public Path getTimeToChargeVehicleInfoPath() {
        return timeToChargeVehicleInfoPath;
    }

    public void setTimeToChargeVehicleInfoPath(Path timeToChargeVehicleInfoPath) {
        this.timeToChargeVehicleInfoPath = timeToChargeVehicleInfoPath;
    }

    public Path getTripDetailsPath() {
        return tripDetailsPath;
    }

    public void setTripDetailsPath(Path tripDetailsPath) {
        this.tripDetailsPath = tripDetailsPath;
    }

    public Path getVehicleTypeInfoPath() {
        return vehicleTypeInfoPath;
    }

    public void setVehicleTypeInfoPath(Path vehicleTypeInfoPath) {
        this.vehicleTypeInfoPath = vehicleTypeInfoPath;
    }

    //Parsing CSV files...
    public void parseCsvFile(Path csvFilePath) throws IOException {
        try(BufferedReader br = Files.newBufferedReader(csvFilePath)) {
            String line;
            boolean isFirstLine = true;

            while((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] values = line.split(",");
                
                try {
                    double numericValue = Double.parseDouble(values[0]);
                    
                }
                catch(NumberFormatException e) {
                    logger.log(Level.SEVERE, "Error parsing number in CSV file at " + csvFilePath + " - Value: " + values[0], e);
                    throw new IllegalArgumentException("Invalid number format in CSV: " + values[0], e);
                }
            }
        }
        catch(IOException e) {
            logger.log(Level.SEVERE, "Error reading file: " + csvFilePath, e);
            throw e;
        }
    }
}
