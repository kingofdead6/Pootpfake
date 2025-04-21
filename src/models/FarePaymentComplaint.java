package models;

import enums.PaymentMethod;

public class FarePaymentComplaint extends Complaint {
    private double amount;
    private PaymentMethod paymentMethod;

    public FarePaymentComplaint(int id, String description, Station station, double amount, PaymentMethod paymentMethod) {
        super(id, description, station);
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    @Override
    public void processComplaint() {
        // Specific processing for fare payment complaints
        // Could involve refund processing or payment verification
    }

    // Getters
    public double getAmount() { return amount; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
}