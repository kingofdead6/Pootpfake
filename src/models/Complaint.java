package models;

import java.time.LocalDateTime;
import enums.ComplaintStatus;
import enums.SeverityLevel;

public abstract class Complaint {
    private int id;
    private Person submitter;
    private String description;
    private LocalDateTime reportDate;
    private Station station;
    private ComplaintStatus status;
    private Station departureStation;
    private Station arrivalStation;
    private SeverityLevel severity; // Added field

    public Complaint(int id, String description, Station station) {
        this.id = id;
        this.description = description;
        this.station = station;
        this.reportDate = LocalDateTime.now();
        this.status = ComplaintStatus.PENDING;
        this.severity = SeverityLevel.MEDIUM; // Default severity
    }

    public void setSubmitter(Person submitter) {
        this.submitter = submitter;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public void setDate(LocalDateTime date) {
        this.reportDate = date;
    }

    public void setSeverity(SeverityLevel severity) { // Added setter
        this.severity = severity;
    }

    public abstract void processComplaint();

    // Getters
    public int getId() { return id; }
    public Person getSubmitter() { return submitter; }
    public String getDescription() { return description; }
    public LocalDateTime getReportDate() { return reportDate; }
    public Station getStation() { return station; }
    public ComplaintStatus getStatus() { return status; }
    public Station getDepartureStation() { return departureStation; }
    public Station getArrivalStation() { return arrivalStation; }
    public SeverityLevel getSeverity() { return severity; } // Added getter

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public void setArrivalStation(Station arrivalStation) {
        this.arrivalStation = arrivalStation;
    }
}