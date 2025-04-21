package services;

import models.*;
import enums.PaymentMethod;
import java.time.LocalDateTime;
import java.util.List;

public class FareService {
    public void sellFareMedium(Passenger passenger, Station station, 
                             FareMedium fareMedium, StationAgent agent) {
        agent.sellFareMedium(passenger, fareMedium);
    }
    
    public List<FareMedium> getPurchaseHistoryByStation(Station station) {
        return station.getSoldFareMedia();
    }
    
    public double calculateRevenueByPaymentMethod(PaymentMethod method) {
        return SystemManager.getInstance().getStations().stream()
            .flatMap(s -> s.getSoldFareMedia().stream())
            .filter(f -> f.getPaymentMethod() == method)
            .mapToDouble(FareMedium::getPrice)
            .sum();
    }
    
    public int countVisitors(Station station, LocalDateTime start, LocalDateTime end) {
        return (int) station.getValidatedFareMedia().stream()
            .filter(f -> f.getActivationDateTime() != null)
            .filter(f -> !f.getActivationDateTime().isBefore(start) && !f.getActivationDateTime().isAfter(end))
            .count();
    }
}