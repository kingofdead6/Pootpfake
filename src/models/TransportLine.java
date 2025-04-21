package models;

import java.time.LocalTime;
import java.util.List;

public class TransportLine {
    private int number;
    private double length;
    private List<Station> stations;
    private LocalTime firstDeparture;
    private LocalTime lastDeparture;
    
    public TransportLine(int number, double length, List<Station> stations, 
                        LocalTime firstDeparture, LocalTime lastDeparture) {
        if (stations.size() < 3) {
            throw new IllegalArgumentException("Transport line must have at least three stations");
        }
        this.number = number;
        this.length = length;
        this.stations = stations;
        this.firstDeparture = firstDeparture;
        this.lastDeparture = lastDeparture;
    }

    // Getters
    public int getNumber() { return number; }
    public double getLength() { return length; }
    public List<Station> getStations() { return stations; }
    public LocalTime getFirstDeparture() { return firstDeparture; }
    public LocalTime getLastDeparture() { return lastDeparture; }
}