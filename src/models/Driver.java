package models;

import enums.ComplaintStatus;

public class Driver extends Employee {
    private TransportMode assignedTransport;
    
    public Driver(int id, String name, Station station, TransportMode transport) {
        super(id, name, station);
        this.assignedTransport = transport;
    }
    
    @Override
    public void processComplaint(Complaint complaint) {
        if (complaint instanceof TechnicalComplaint) {
            TechnicalComplaint techComplaint = (TechnicalComplaint) complaint;
            if (techComplaint.getTransport() == assignedTransport) {
                complaint.setStatus(ComplaintStatus.PENDING);
                assignedStation.addComplaint(complaint);
            }
        }
    }

    public TransportMode getAssignedTransport() {
        return assignedTransport;
    }
}