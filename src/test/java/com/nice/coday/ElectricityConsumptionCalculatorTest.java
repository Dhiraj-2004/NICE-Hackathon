package com.nice.coday;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.nio.file.Paths;
import java.util.Set;

public class ElectricityConsumptionCalculatorTest {
    @InjectMocks
    private static ConsumptionResult resultData;

    private ResourceInfo resourceInfo;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test1() throws IOException {
        Path chargingStationInfoPath = Paths.get("src/main/resources/TestCase1/ChargingStationInfo.csv");
        Path entryExitPointInfoPath = Paths.get("src/main/resources/TestCase1/EntryExitPointInfo.csv");
        Path timeToChargeVehicleInfoPath = Paths.get("src/main/resources/TestCase1/TimeToChargeVehicleInfo.csv");
        Path tripDetailsPath = Paths.get("src/main/resources/TestCase1/TripDetails.csv");
        Path vehicleTypeInfoPath = Paths.get("src/main/resources/TestCase1/VehicleTypeInfo.csv");

        resourceInfo = new ResourceInfo(chargingStationInfoPath, entryExitPointInfoPath, timeToChargeVehicleInfoPath, tripDetailsPath, vehicleTypeInfoPath);
        ElectricityConsumptionCalculator analyzer = new ElectricityConsumptionCalculatorImpl();
        resultData = analyzer.calculateElectricityAndTimeConsumption(resourceInfo);

        // 1. Total Unit Consumed by all vehicles
        double totalUnitsConsumed = resultData.getVehicleTypes().stream()
            .mapToDouble(vehicleType -> resultData.getConsumptionDetails(vehicleType).getTotalUnitConsumed())
            .sum();
        double expectedTotalUnitsConsumed = 690.60;
        Assert.assertEquals(expectedTotalUnitsConsumed, totalUnitsConsumed, 690.60);

        // 2. Total Unit Consumed by Vehicle Type V1
        double expectedTotalUnitsConsumedByV1 = 534.06;
        double actualTotalUnitsConsumedByV1 = resultData.getConsumptionDetails("V1").getTotalUnitConsumed();
        Assert.assertEquals(expectedTotalUnitsConsumedByV1, actualTotalUnitsConsumedByV1, 534.06);

        // 3. Total Unit Consumed by Vehicle Type V2
        double expectedTotalUnitsConsumedByV2 = 156.53;
        double actualTotalUnitsConsumedByV2 = resultData.getConsumptionDetails("V2").getTotalUnitConsumed();
        Assert.assertEquals(expectedTotalUnitsConsumedByV2, actualTotalUnitsConsumedByV2, 156.53);

        // 4. Total Time required for charging Vehicle Type V2
        long expectedTotalTimeRequiredByV2 = 55022;
        long actualTotalTimeRequiredByV2 = resultData.getConsumptionDetails("V2").getTotalTimeRequired();
        Assert.assertEquals(expectedTotalTimeRequiredByV2, actualTotalTimeRequiredByV2, 55022);

        // 5. Total time required for charging any vehicle at Charging Station Ch2
        int expectedTotalTimeRequiredAtC2 = 10570;
        Long totalChargingStationTimeAtC2 = resultData.getTotalChargingStationTime().get("C2");
        System.out.println("Total time required at C2: " + totalChargingStationTimeAtC2); // Debugging...
        Assert.assertEquals(expectedTotalTimeRequiredAtC2, (totalChargingStationTimeAtC2 != null ? totalChargingStationTimeAtC2 : 0), 10570);

        // 6. Total time required for charging any vehicle at Charging Station Ch10
        int expectedTotalTimeRequiredAtC10 = 46500;
        Long totalChargingStationTimeAtC10 = resultData.getTotalChargingStationTime().get("C10");
        Assert.assertEquals(expectedTotalTimeRequiredAtC10, (totalChargingStationTimeAtC10 != null ? totalChargingStationTimeAtC10 : 0), 46500);

        // 7. Number of trips finished by Vehicle Type V2
        long expectedNumberOfTripsFinished = 16;
        long actualNumberOfTripsFinished = resultData.getConsumptionDetails("V2").getNumberOfTripsFinished();
        Assert.assertEquals(expectedNumberOfTripsFinished, actualNumberOfTripsFinished, 16);
    }

    @Test
    public void test2() throws IOException {
        Path chargingStationInfoPath = Paths.get("src/main/resources/TestCase2/ChargingStationInfo.csv");
        Path entryExitPointInfoPath = Paths.get("src/main/resources/TestCase2/EntryExitPointInfo.csv");
        Path timeToChargeVehicleInfoPath = Paths.get("src/main/resources/TestCase2/TimeToChargeVehicleInfo.csv");
        Path tripDetailsPath = Paths.get("src/main/resources/TestCase2/TripDetails.csv");
        Path vehicleTypeInfoPath = Paths.get("src/main/resources/TestCase2/VehicleTypeInfo.csv");

        resourceInfo = new ResourceInfo(chargingStationInfoPath, entryExitPointInfoPath, timeToChargeVehicleInfoPath, tripDetailsPath, vehicleTypeInfoPath);
        ElectricityConsumptionCalculator analyzer = new ElectricityConsumptionCalculatorImpl();
        resultData = analyzer.calculateElectricityAndTimeConsumption(resourceInfo);

        // 1. Total Unit Consumed by all vehicles
        double totalUnitsConsumed = resultData.getVehicleTypes().stream()
            .mapToDouble(vehicleType -> resultData.getConsumptionDetails(vehicleType).getTotalUnitConsumed())
            .sum();
        double expectedTotalUnitsConsumed = 3972;
        Assert.assertEquals(expectedTotalUnitsConsumed, totalUnitsConsumed, 3972.0);

        // 2. Total Unit Consumed by Vehicle Type V4
        double expectedTotalUnitsConsumedByV4 = 670.27;
        double actualTotalUnitsConsumedByV4 = resultData.getConsumptionDetails("V4").getTotalUnitConsumed();
        Assert.assertEquals(expectedTotalUnitsConsumedByV4, actualTotalUnitsConsumedByV4, 670.27);

        // 3. Total Time required for charging Vehicle Type V1
        int expectedTotalTimeRequiredByV1 = 238437;
        long actualTotalTimeRequiredByV1 = resultData.getConsumptionDetails("V1").getTotalTimeRequired();
        Assert.assertEquals(expectedTotalTimeRequiredByV1, actualTotalTimeRequiredByV1, 238437);
    }

    @Test
    public void test3() throws IOException {
        Path chargingStationInfoPath = Paths.get("src/main/resources/TestCase3/ChargingStationInfo.csv");
        Path entryExitPointInfoPath = Paths.get("src/main/resources/TestCase3/EntryExitPointInfo.csv");
        Path timeToChargeVehicleInfoPath = Paths.get("src/main/resources/TestCase3/TimeToChargeVehicleInfo.csv");
        Path tripDetailsPath = Paths.get("src/main/resources/TestCase3/TripDetails.csv");
        Path vehicleTypeInfoPath = Paths.get("src/main/resources/TestCase3/VehicleTypeInfo.csv");

        resourceInfo = new ResourceInfo(chargingStationInfoPath, entryExitPointInfoPath, timeToChargeVehicleInfoPath, tripDetailsPath, vehicleTypeInfoPath);
        ElectricityConsumptionCalculator analyzer = new ElectricityConsumptionCalculatorImpl();
        resultData = analyzer.calculateElectricityAndTimeConsumption(resourceInfo);

        // 1. Total Unit Consumed by all vehicles
        double totalUnitsConsumed = resultData.getVehicleTypes().stream()
            .mapToDouble(vehicleType -> resultData.getConsumptionDetails(vehicleType).getTotalUnitConsumed())
            .sum();
        double expectedTotalUnitsConsumed = 167.0;
        Assert.assertEquals(expectedTotalUnitsConsumed, totalUnitsConsumed, 0.0);

        // 2. Number of trips finished
        long expectedNumberOfTripsFinished = 145;
        long actualNumberOfTripsFinished = 0;
        //Iterates over all vehicle types
        for(String vehicleType : resultData.getVehicleTypes()) {
            ConsumptionDetails details = resultData.getConsumptionDetails(vehicleType);
            if(details != null) {
                long tripsFinished = details.getNumberOfTripsFinished();
                System.out.println("Vehicle Type: " + vehicleType + ", Trips Finished: " + tripsFinished);
                actualNumberOfTripsFinished += tripsFinished;
            }
        }
        Assert.assertEquals(expectedNumberOfTripsFinished, actualNumberOfTripsFinished, 145);
    }

    @Test
    public void test4() throws IOException {
        Path chargingStationInfoPath = Paths.get("src/main/resources/TestCase4/ChargingStationInfo.csv");
        Path entryExitPointInfoPath = Paths.get("src/main/resources/TestCase4/EntryExitPointInfo.csv");
        Path timeToChargeVehicleInfoPath = Paths.get("src/main/resources/TestCase4/TimeToChargeVehicleInfo.csv");
        Path tripDetailsPath = Paths.get("src/main/resources/TestCase4/TripDetails.csv");
        Path vehicleTypeInfoPath = Paths.get("src/main/resources/TestCase4/VehicleTypeInfo.csv");

        resourceInfo = new ResourceInfo(chargingStationInfoPath, entryExitPointInfoPath, timeToChargeVehicleInfoPath, tripDetailsPath, vehicleTypeInfoPath);
        ElectricityConsumptionCalculator analyzer = new ElectricityConsumptionCalculatorImpl();
        resultData = analyzer.calculateElectricityAndTimeConsumption(resourceInfo);

        // 1. Total Unit Consumed by all vehicles
        double totalUnitsConsumed = resultData.getVehicleTypes().stream()
            .mapToDouble(vehicleType -> resultData.getConsumptionDetails(vehicleType).getTotalUnitConsumed())
            .sum();
        double expectedTotalUnitsConsumed = 736.0;
        Assert.assertEquals(expectedTotalUnitsConsumed, totalUnitsConsumed, 736.0);

        // 2. Total Unit Consumed by Vehicle Type V4
        double expectedTotalUnitsConsumedByV4 = 174.0;
        double actualTotalUnitsConsumedByV4 = resultData.getConsumptionDetails("V4").getTotalUnitConsumed();
        Assert.assertEquals(expectedTotalUnitsConsumedByV4, actualTotalUnitsConsumedByV4, 174.0);

        // 3. Total Time required for charging Vehicle Type V2
        long expectedTotalTimeRequiredByV2 = 11649;
        long actualTotalTimeRequiredByV2 = resultData.getConsumptionDetails("V2").getTotalTimeRequired();
        Assert.assertEquals(expectedTotalTimeRequiredByV2, actualTotalTimeRequiredByV2, 11649);
    }

    @Test
    public void test5() throws IOException {

        Path chargingStationInfoPath = Paths.get("src/main/resources/TestCase5/ChargingStationInfo.csv");
        Path entryExitPointInfoPath = Paths.get("src/main/resources/TestCase5/EntryExitPointInfo.csv");
        Path timeToChargeVehicleInfoPath = Paths.get("src/main/resources/TestCase5/TimeToChargeVehicleInfo.csv");
        Path tripDetailsPath = Paths.get("src/main/resources/TestCase5/TripDetails.csv");
        Path vehicleTypeInfoPath = Paths.get("src/main/resources/TestCase5/VehicleTypeInfo.csv");

        resourceInfo = new ResourceInfo(chargingStationInfoPath, entryExitPointInfoPath, timeToChargeVehicleInfoPath, tripDetailsPath, vehicleTypeInfoPath);
        ElectricityConsumptionCalculator analyzer = new ElectricityConsumptionCalculatorImpl();
        resultData = analyzer.calculateElectricityAndTimeConsumption(resourceInfo);

        //1. Total Unit Consume by all vehicles
        Set<String> vehicleTypes = resultData.getVehicleTypes();
        double totalUnitsConsumed = 0.0;
        // Iterates over all vehicle types...
        for(String vehicleType : vehicleTypes) {
            ConsumptionDetails details = resultData.getConsumptionDetails(vehicleType);
            if(details != null) {
                totalUnitsConsumed += details.getTotalUnitConsumed();
            }
        }
        double expectedTotalUnitsConsumed = 351797;
        Assert.assertEquals(expectedTotalUnitsConsumed, totalUnitsConsumed, 351797.0);

        //2. Total Unit Consume by Vehicle Type V4
        double expectedTotalUnitsConsumedByV4 = 39110;
        double actualTotalUnitsConsumedByV4 = resultData.getConsumptionDetails("V4").getTotalUnitConsumed();
        Assert.assertEquals(expectedTotalUnitsConsumedByV4, actualTotalUnitsConsumedByV4, 39110.0);

        //3. Total Time required for charging Vehicle Type V2
        long expectedTotalTimeRequiredByV2 = 10716795;
        long actualTotalTotalTimeRequiredByV2 = resultData.getConsumptionDetails("V2").getTotalTimeRequired();
        Assert.assertEquals(expectedTotalTimeRequiredByV2, actualTotalTotalTimeRequiredByV2, 10716795);

        //4. Number of trips finished
        long expectedNumberOfTripsFinished = 10000;
        long actualNumberOfTripsFinished = 0;
        // Iterates over all vehicle types
        for(String vehicleType : resultData.getVehicleTypes()) {
            actualNumberOfTripsFinished += resultData.getConsumptionDetails(vehicleType).getNumberOfTripsFinished();
        }
        Assert.assertEquals(expectedNumberOfTripsFinished, actualNumberOfTripsFinished, 10000);
    }

    @Test
    public void test6() throws IOException {

        Path chargingStationInfoPath = Paths.get("src/main/resources/TestCase6/ChargingStationInfo.csv");
        Path entryExitPointInfoPath = Paths.get("src/main/resources/TestCase6/EntryExitPointInfo.csv");
        Path timeToChargeVehicleInfoPath = Paths.get("src/main/resources/TestCase6/TimeToChargeVehicleInfo.csv");
        Path tripDetailsPath = Paths.get("src/main/resources/TestCase6/TripDetails.csv");
        Path vehicleTypeInfoPath = Paths.get("src/main/resources/TestCase6/VehicleTypeInfo.csv");

        resourceInfo = new ResourceInfo(chargingStationInfoPath, entryExitPointInfoPath, timeToChargeVehicleInfoPath, tripDetailsPath, vehicleTypeInfoPath);
        ElectricityConsumptionCalculator analyzer = new ElectricityConsumptionCalculatorImpl();
        resultData = analyzer.calculateElectricityAndTimeConsumption(resourceInfo);

        //1. Total Unit Consume by all vehicles
        double expectedTotalUnitsConsumed = 2741580; // The expected sum of all TotalUnitConsumed
        Set<String> vehicleTypes = resultData.getVehicleTypes();
        double totalUnitsConsumed = 0.0;
        // Iterates over all vehicle types
        for(String vehicleType : vehicleTypes) {
            ConsumptionDetails details = resultData.getConsumptionDetails(vehicleType);
            if(details != null) {
                totalUnitsConsumed += details.getTotalUnitConsumed();
            }
        }
        Assert.assertEquals(expectedTotalUnitsConsumed, totalUnitsConsumed, 2741580.0);

        //2. Total Unit Consume by Vehicle Type V16
        double expectedTotalUnitsConsumedByV16 = 224771; // The expected sum of TotalUnitConsumed for VehicleType "V1"
        double actualTotalUnitsConsumedByV16 = resultData.getConsumptionDetails("V16").getTotalUnitConsumed();
        System.out.println("actualTotalUnitsConsumedByV16 = " + actualTotalUnitsConsumedByV16);
        Assert.assertEquals(expectedTotalUnitsConsumedByV16, actualTotalUnitsConsumedByV16, 224771.0);

        //3. Total Time required for charging Vehicle Type V12
        long expectedTotalTimeRequiredByV12 = 41961924L;
        long actualTotalTimeRequiredByV12 = resultData.getConsumptionDetails("V12").getTotalTimeRequired();
        Assert.assertEquals(expectedTotalTimeRequiredByV12, actualTotalTimeRequiredByV12, 41961924L);

        //4. Number of trips finished
        long expectedNumberOfTripsFinished = 29795;
        long actualNumberOfTripsFinished = 0;
        // Iterates over all vehicle types
        for(String vehicleType : resultData.getVehicleTypes()) {
            actualNumberOfTripsFinished += resultData.getConsumptionDetails(vehicleType).getNumberOfTripsFinished();
        }
        Assert.assertEquals(expectedNumberOfTripsFinished, actualNumberOfTripsFinished, 29795.0);
    }

    @Test
    public void test7() throws IOException {

        Path chargingStationInfoPath = Paths.get("src/main/resources/TestCase7/ChargingStationInfo.csv");
        Path entryExitPointInfoPath = Paths.get("src/main/resources/TestCase7/EntryExitPointInfo.csv");
        Path timeToChargeVehicleInfoPath = Paths.get("src/main/resources/TestCase7/TimeToChargeVehicleInfo.csv");
        Path tripDetailsPath = Paths.get("src/main/resources/TestCase7/TripDetails.csv");
        Path vehicleTypeInfoPath = Paths.get("src/main/resources/TestCase7/VehicleTypeInfo.csv");

        resourceInfo = new ResourceInfo(chargingStationInfoPath, entryExitPointInfoPath, timeToChargeVehicleInfoPath, tripDetailsPath, vehicleTypeInfoPath);
        ElectricityConsumptionCalculator analyzer = new ElectricityConsumptionCalculatorImpl();
        resultData = analyzer.calculateElectricityAndTimeConsumption(resourceInfo);

        //1. Total Unit Consume by all vehicles
        Set<String> vehicleTypes = resultData.getVehicleTypes();
        double expectedTotalUnitsConsumed = 12823458; // The expected sum of all TotalUnitConsumed
        double actualTotalUnitsConsumed  = 0.0;
        //2. Iterates over all vehicle types
        for(String vehicleType : vehicleTypes) {
                ConsumptionDetails details = resultData.getConsumptionDetails(vehicleType);
                if (details != null) {
                    actualTotalUnitsConsumed += details.getTotalUnitConsumed();
                }
            }
        Assert.assertEquals(expectedTotalUnitsConsumed, actualTotalUnitsConsumed, 12823458.0);

        //3. Total Unit Consume by Vehicle Type V29
        double expectedTotalUnitsConsumedByV29 = 143514.85; // The expected sum of TotalUnitConsumed for VehicleType "V1"
        double actualTotalUnitsConsumedByV29 = resultData.getConsumptionDetails("V29").getTotalUnitConsumed();
        Assert.assertEquals(expectedTotalUnitsConsumedByV29, actualTotalUnitsConsumedByV29, 143514.85);

        //4. Total time required for charging any vehicle at Charging Station Ch183
        long expectedTotalTimeRequiredAtC183 = 22999411L;
        Map<String, Long> totalChargingStationTime = resultData.getTotalChargingStationTime();
        Long actualTotalTimeRequiredAtC183 = totalChargingStationTime.get("C183");

        System.out.println("Total Charging Station Times: " + totalChargingStationTime); //Debugging Line...
        if(actualTotalTimeRequiredAtC183 == null) {
            System.out.println("Total Charging Station Times: " + totalChargingStationTime);
        }
        else {
            Assert.assertEquals("Total time required at charging station C183 should match", expectedTotalTimeRequiredAtC183, (long) actualTotalTimeRequiredAtC183);
        }

        //5. Number of trips finished
        long expectedNumberOfTripsFinished = 61122;
        long  actualNumberOfTripsFinished = 0;
        //Iterates over all vehicle types
        for(String vehicleType : resultData.getVehicleTypes()) {
            actualNumberOfTripsFinished += resultData.getConsumptionDetails(vehicleType).getNumberOfTripsFinished();
        }
        Assert.assertEquals(expectedNumberOfTripsFinished, actualNumberOfTripsFinished);
    }
}

