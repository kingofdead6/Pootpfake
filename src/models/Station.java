package models;

import java.util.ArrayList;
import java.util.List;
import enums.Zone;
import services.NotificationService;

public class Station {
    private String code;
    private String name;
    private String municipality;
    private Zone zone;
    private boolean isSuspended;
    private List<TransportLine> lines;
    private List<Complaint> complaints;
    private List<FareMedium> soldFareMedia;
    private List<FareMedium> validatedFareMedia;
    private int magneticReaderCount;
    
    public Station(String code, String name, String municipality, Zone zone, int magneticReaderCount) {
        if (magneticReaderCount < 1) {
            throw new IllegalArgumentException("Station must have at least one magnetic reader");
        }
        this.code = code;
        this.name = name;
        this.municipality = municipality;
        this.zone = zone;
        this.isSuspended = false;
        this.lines = new ArrayList<>();
        this.complaints = new ArrayList<>();
        this.soldFareMedia = new ArrayList<>();
        this.validatedFareMedia = new ArrayList<>();
        this.magneticReaderCount = magneticReaderCount;
    }
    
    public void sellFareMedium(Passenger passenger, FareMedium fareMedium) {
        if (!isSuspended) {
            passenger.getFareMedia().add(fareMedium);
            soldFareMedia.add(fareMedium);
        } else {
            throw new IllegalStateException("Cannot sell fare media at suspended station");
        }
    }
    
    public void validateFareMedium(FareMedium fareMedium) {
        if (!isSuspended) {
            fareMedium.validate();
            validatedFareMedia.add(fareMedium);
        } else {
            throw new IllegalStateException("Cannot validate fare media at suspended station");
        }
    }
    
    public void suspend() {
        this.isSuspended = true;
        NotificationService.notifyPassengers("Station " + name + " is suspended for maintenance.");
    }
    
    public void reactivate() {
        this.isSuspended = false;
        NotificationService.notifyPassengers("Station " + name + " is back in service.");
    }
    
    public void addComplaint(Complaint complaint) {
        complaints.add(complaint);
    }
    
    public void addTransportLine(TransportLine line) {
        lines.add(line);
    }

    // Getters and setters
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getMunicipality() { return municipality; }
    public Zone getZone() { return zone; }
    public boolean isSuspended() { return isSuspended; }
    public List<TransportLine> getLines() { return lines; }
    public List<Complaint> getComplaints() { return complaints; }
    public List<FareMedium> getSoldFareMedia() { return soldFareMedia; }
    public List<FareMedium> getValidatedFareMedia() { return validatedFareMedia; }
    public int getMagneticReaderCount() { return magneticReaderCount; }
}