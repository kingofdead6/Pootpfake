package models;

import enums.ServiceSafetyIssueType;

public class ServiceSafetyComplaint extends Complaint {
    private ServiceSafetyIssueType issueType;
    
    // Existing constructor
    public ServiceSafetyComplaint(int id, String description, Station station, 
                                 ServiceSafetyIssueType issueType) {
        super(id, description, station);
        this.issueType = issueType;
    }
    
    // New constructor to match Main class usage
    public ServiceSafetyComplaint(int id, String description, Station station) {
        super(id, description, station);
        this.issueType = ServiceSafetyIssueType.THEFT; // Default to THEFT as per Main's description
    }
    
    @Override
    public void processComplaint() {
        // Specific processing for safety complaints
        // Could involve HR actions, security measures, etc.
    }

    // Getters
    public ServiceSafetyIssueType getIssueType() { return issueType; }
}