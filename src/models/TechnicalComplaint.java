package models;

import enums.SeverityLevel;
import enums.TechnicalIssueType;

public class TechnicalComplaint extends Complaint {
    private TechnicalIssueType issueType;
    private TransportMode transport;
    private SeverityLevel severity;

    public TechnicalComplaint(int id, String description, Station station,
                             TechnicalIssueType issueType, TransportMode transport) {
        super(id, description, station);
        this.issueType = issueType;
        this.transport = transport;
        this.severity = SeverityLevel.MEDIUM; // Default severity
    }

    @Override
    public void processComplaint() {
        // Specific processing for technical complaints
        if (severity == SeverityLevel.HIGH && transport != null) {
            transport.suspend(); // Ensure TransportMode has suspend()
        }
    }

    // Getters and setters
    public TechnicalIssueType getIssueType() { return issueType; }
    public TransportMode getTransport() { return transport; }
    public SeverityLevel getSeverity() { return severity; }

    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }
}