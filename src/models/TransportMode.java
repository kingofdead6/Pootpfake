package models;

import enums.PowerSupply;

public class TransportMode {
    private String code;
    private TransportLine line;
    private PowerSupply powerSupply;
    private double kilometersTraveled;
    private boolean isSuspended;
    
    public TransportMode(String code, TransportLine line, PowerSupply powerSupply) {
        this.code = code;
        this.line = line;
        this.powerSupply = powerSupply;
        this.isSuspended = false;
    }
    
    public void suspend() {
        this.isSuspended = true;
    }
    
    public void reactivate() {
        this.isSuspended = false;
    }

    // Getters and setters
    public String getCode() { return code; }
    public TransportLine getLine() { return line; }
    public PowerSupply getPowerSupply() { return powerSupply; }
    public double getKilometersTraveled() { return kilometersTraveled; }
    public boolean isSuspended() { return isSuspended; }
    public void setKilometersTraveled(double kilometersTraveled) { this.kilometersTraveled = kilometersTraveled; }
}