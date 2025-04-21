package services;

import models.*;
import enums.ComplaintStatus;
import enums.SeverityLevel;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class ComplaintService {
    public void processComplaint(Complaint complaint) {
        complaint.processComplaint();
        
        if (complaint instanceof TechnicalComplaint) {
            TechnicalComplaint techComplaint = (TechnicalComplaint) complaint;
            checkForSuspension(techComplaint);
        }
        
        // Simulate analysis within 24 hours
        if (ChronoUnit.HOURS.between(complaint.getReportDate(), LocalDateTime.now()) <= 24) {
            complaint.setStatus(ComplaintStatus.APPROVED); // Simplified for demo
        }
    }
    
    private void checkForSuspension(TechnicalComplaint complaint) {
        Station station = complaint.getStation();
        TransportMode transport = complaint.getTransport();
        
        if (complaint.getSeverity() == SeverityLevel.HIGH) {
            // Check for station complaints
            long highSeverityStationComplaints = station.getComplaints().stream()
                .filter(c -> c instanceof TechnicalComplaint)
                .filter(c -> ((TechnicalComplaint)c).getSeverity() == SeverityLevel.HIGH)
                .filter(c -> c.getStatus() == ComplaintStatus.APPROVED)
                .filter(c -> ChronoUnit.HOURS.between(c.getReportDate(), LocalDateTime.now()) <= 24)
                .count();
            
            if (highSeverityStationComplaints >= 3) {
                station.suspend();
            }
            
            // Check for transport complaints
            long highSeverityTransportComplaints = station.getComplaints().stream()
                .filter(c -> c instanceof TechnicalComplaint)
                .filter(c -> ((TechnicalComplaint)c).getTransport() == transport)
                .filter(c -> ((TechnicalComplaint)c).getSeverity() == SeverityLevel.HIGH)
                .filter(c -> c.getStatus() == ComplaintStatus.APPROVED)
                .filter(c -> ChronoUnit.HOURS.between(c.getReportDate(), LocalDateTime.now()) <= 24)
                .count();
            
            if (highSeverityTransportComplaints >= 3) {
                transport.suspend();
            }
        }
    }
    
    public void resolveComplaint(Complaint complaint) {
        complaint.setStatus(ComplaintStatus.RESOLVED);
        
        // Check if suspension can be lifted
        if (complaint instanceof TechnicalComplaint) {
            TechnicalComplaint techComplaint = (TechnicalComplaint) complaint;
            Station station = techComplaint.getStation();
            TransportMode transport = techComplaint.getTransport();
            
            long pendingHighSeverityStationComplaints = station.getComplaints().stream()
                .filter(c -> c instanceof TechnicalComplaint)
                .filter(c -> ((TechnicalComplaint)c).getSeverity() == SeverityLevel.HIGH)
                .filter(c -> c.getStatus() != ComplaintStatus.RESOLVED)
                .count();
            
            if (pendingHighSeverityStationComplaints == 0 && station.isSuspended()) {
                station.reactivate();
            }
            
            long pendingHighSeverityTransportComplaints = station.getComplaints().stream()
                .filter(c -> c instanceof TechnicalComplaint)
                .filter(c -> ((TechnicalComplaint)c).getTransport() == transport)
                .filter(c -> ((TechnicalComplaint)c).getSeverity() == SeverityLevel.HIGH)
                .filter(c -> c.getStatus() != ComplaintStatus.RESOLVED)
                .count();
            
            if (pendingHighSeverityTransportComplaints == 0 && transport.isSuspended()) {
                transport.reactivate();
            }
        }
    }
    
    public List<Complaint> getComplaintsByStation(Station station) {
        return station.getComplaints();
    }
    
    public List<Complaint> getComplaintsByTransport(TransportMode transport) {
        return transport.getLine().getStations().stream()
            .flatMap(s -> s.getComplaints().stream())
            .filter(c -> c instanceof TechnicalComplaint)
            .filter(c -> ((TechnicalComplaint)c).getTransport() == transport)
            .collect(Collectors.toList());
    }
}