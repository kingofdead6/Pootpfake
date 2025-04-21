package models;

import java.time.LocalDateTime;

import enums.ComplaintStatus;

public class StationAgent extends Employee {
    public StationAgent(int id, String name, Station station) {
        super(id, name, station);
    }
    
    public void sellFareMedium(Passenger passenger, FareMedium fareMedium) {
        fareMedium.setPurchaseStation(assignedStation);
        fareMedium.setPurchaseDateTime(LocalDateTime.now());
        passenger.purchaseFareMedium(assignedStation, fareMedium);
    }
    
    @Override
    public void processComplaint(Complaint complaint) {
        complaint.setStatus(ComplaintStatus.PENDING);
        assignedStation.addComplaint(complaint);
    }
}