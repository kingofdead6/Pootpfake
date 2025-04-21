package services;

import models.*;
import enums.PaymentMethod;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {
    public Map<PaymentMethod, Double> generateRevenueReport() {
        return SystemManager.getInstance().getStations().stream()
            .flatMap(s -> s.getSoldFareMedia().stream())
            .collect(Collectors.groupingBy(
                FareMedium::getPaymentMethod,
                Collectors.summingDouble(FareMedium::getPrice)
            ));
    }
    
    public Map<String, Long> getComplaintStatisticsByStation(Station station) {
        return station.getComplaints().stream()
            .collect(Collectors.groupingBy(
                c -> c.getClass().getSimpleName(),
                Collectors.counting()
            ));
    }
    
    public Map<String, Long> getComplaintStatisticsByTransport(TransportMode transport) {
        return transport.getLine().getStations().stream()
            .flatMap(s -> s.getComplaints().stream())
            .filter(c -> c instanceof TechnicalComplaint)
            .filter(c -> ((TechnicalComplaint)c).getTransport() == transport)
            .collect(Collectors.groupingBy(
                c -> c.getClass().getSimpleName(),
                Collectors.counting()
            ));
    }
    
    public long countSuspendedStations(List<Station> stations) {
        return stations.stream()
            .filter(Station::isSuspended)
            .count();
    }
    
    public long countSuspendedTransports(List<TransportMode> transports) {
        return transports.stream()
            .filter(TransportMode::isSuspended)
            .count();
    }
    
    public Map<DayOfWeek, Long> getPeakOffPeakStats(Station station, LocalDateTime start, LocalDateTime end) {
        return station.getValidatedFareMedia().stream()
            .filter(f -> f.getActivationDateTime() != null)
            .filter(f -> !f.getActivationDateTime().isBefore(start) && !f.getActivationDateTime().isAfter(end))
            .collect(Collectors.groupingBy(
                f -> f.getActivationDateTime().getDayOfWeek(),
                Collectors.counting()
            ));
    }
}