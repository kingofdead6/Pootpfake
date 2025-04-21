package models;

import java.time.LocalDateTime;
import enums.PaymentMethod;
import enums.Zone;

public class FareMedium {
    private int id;
    private String code;
    private LocalDateTime purchaseDateTime;
    private Zone zones;
    private LocalDateTime activationDateTime;
    private double price;
    private PaymentMethod paymentMethod;
    private Station purchaseStation;

    public FareMedium(int id, String code, Zone zones, double price, PaymentMethod paymentMethod, LocalDateTime purchaseDateTime) {
        this.id = id;
        this.code = code;
        this.zones = zones;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.purchaseDateTime = purchaseDateTime;
    }

    public void validate() {
        if (activationDateTime == null) {
            activationDateTime = LocalDateTime.now();
        }
    }

    public void setPurchaseStation(Station station) {
        this.purchaseStation = station;
    }

    public void setPurchaseDateTime(LocalDateTime purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }

    public void setActivationDateTime(LocalDateTime activationDateTime) { // Added
        this.activationDateTime = activationDateTime;
    }

    // Getters
    public int getId() { return id; }
    public String getCode() { return code; }
    public LocalDateTime getPurchaseDateTime() { return purchaseDateTime; }
    public Zone getZones() { return zones; }
    public LocalDateTime getActivationDateTime() { return activationDateTime; }
    public double getPrice() { return price; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public Station getPurchaseStation() { return purchaseStation; }

    public void setCode(String code) { this.code = code; }
}