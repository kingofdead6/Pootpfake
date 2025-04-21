package services;

import models.*;
import enums.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SystemManager {
    private static SystemManager instance;
    private List<Station> stations;
    private List<TransportMode> transportModes;
    private List<Passenger> passengers;
    private List<Employee> employees;
    
    private FareService fareService;
    private ComplaintService complaintService;
    private ReportService reportService;
    
    private SystemManager() {
        this.stations = new ArrayList<>();
        this.transportModes = new ArrayList<>();
        this.passengers = new ArrayList<>();
        this.employees = new ArrayList<>();
        
        this.fareService = new FareService();
        this.complaintService = new ComplaintService();
        this.reportService = new ReportService();
    }
    
    public static SystemManager getInstance() {
        if (instance == null) {
            instance = new SystemManager();
        }
        return instance;
    }
    
    // Station management
    public void addStation(Station station) {
        stations.add(station);
    }
    
    // Transport management
    public void addTransportMode(TransportMode transport) {
        transportModes.add(transport);
    }
    
    // Passenger management
    public void registerPassenger(Passenger passenger) {
        passengers.add(passenger);
    }
    
    // Employee management
    public void hireEmployee(Employee employee) {
        employees.add(employee);
    }
    
    // Fare management
    public void processFareSale(Passenger passenger, Station station, 
                              FareMedium fareMedium, StationAgent agent) {
        fareService.sellFareMedium(passenger, station, fareMedium, agent);
    }
    
    // Complaint handling
    public void processComplaint(Complaint complaint) {
        complaintService.processComplaint(complaint);
    }
    
    // Reporting
    public void generateFareMediumReport(Station station) {
        List<FareMedium> history = fareService.getPurchaseHistoryByStation(station);
        System.out.println("Fare Medium Purchase History for " + station.getName() + ":");
        history.forEach(f -> System.out.println("ID: " + f.getId() + ", Price: " + f.getPrice() + ", Method: " + f.getPaymentMethod()));
    }
    
    public void generateRevenueReport() {
        Map<PaymentMethod, Double> report = reportService.generateRevenueReport();
        System.out.println("Revenue by Payment Method:");
        report.forEach((method, revenue) -> System.out.println(method + ": " + revenue));
    }
    
    public void analyzeTransportUsage(Station station, LocalDateTime start, LocalDateTime end) {
        int visitors = fareService.countVisitors(station, start, end);
        System.out.println("Visitors to " + station.getName() + " from " + start + " to " + end + ": " + visitors);
        
        Map<DayOfWeek, Long> peakStats = reportService.getPeakOffPeakStats(station, start, end);
        System.out.println("Peak/Off-Peak Statistics:");
        peakStats.forEach((day, count) -> System.out.println(day + ": " + count));
    }
    
    public void displayComplaintStatistics(Station station) {
        Map<String, Long> stats = reportService.getComplaintStatisticsByStation(station);
        System.out.println("Complaint Statistics for " + station.getName() + ":");
        stats.forEach((type, count) -> System.out.println(type + ": " + count));
    }
    
    public void displayComplaintStatistics(TransportMode transport) {
        Map<String, Long> stats = reportService.getComplaintStatisticsByTransport(transport);
        System.out.println("Complaint Statistics for Transport " + transport.getCode() + ":");
        stats.forEach((type, count) -> System.out.println(type + ": " + count));
    }
    
    public void displaySuspensionStatistics() {
        long suspendedStations = reportService.countSuspendedStations(stations);
        long suspendedTransports = reportService.countSuspendedTransports(transportModes);
        
        System.out.println("Suspended Stations: " + suspendedStations);
        System.out.println("Suspended Transport Modes: " + suspendedTransports);
    }
    
    public void suspendStation(Station station) {
        station.suspend();
        System.out.println("Station " + station.getName() + " suspended.");
    }

    public void suspendTransport(TransportMode transport) {
        transport.suspend();
        System.out.println("Transport " + transport.getCode() + " suspended.");
    }
    
    public void printPeakAndOffPeak() {
        // Example implementation: Define peak as 7-9 AM and 5-7 PM, off-peak otherwise
        for (Station station : stations) {
            System.out.println("Peak/Off-Peak for Station " + station.getName() + ":");
            long peakCount = station.getValidatedFareMedia().stream()
                .filter(f -> f.getActivationDateTime() != null)
                .filter(f -> {
                    int hour = f.getActivationDateTime().getHour();
                    return (hour >= 7 && hour < 9) || (hour >= 17 && hour < 19);
                })
                .count();
            long offPeakCount = station.getValidatedFareMedia().stream()
                .filter(f -> f.getActivationDateTime() != null)
                .filter(f -> {
                    int hour = f.getActivationDateTime().getHour();
                    return !((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19));
                })
                .count();
            System.out.println("Peak Hours (7-9 AM, 5-7 PM): " + peakCount);
            System.out.println("Off-Peak Hours: " + offPeakCount);
        }
    }
    
    // Getters
    public List<Station> getStations() { return stations; }
    public List<TransportMode> getTransportModes() { return transportModes; }
    public List<Passenger> getPassengers() { return passengers; }
    public List<Employee> getEmployees() { return employees; }
    public ComplaintService getComplaintService() { return complaintService; }
    public FareService getFareService() { return fareService; }
    public ReportService getReportService() { return reportService; }
}