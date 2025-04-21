import models.*;
import services.*;
import enums.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        SystemManager manager = SystemManager.getInstance();

        // Setup stations
        Station station1 = new Station("S003", "Central", "Downtown", Zone.CENTER, 2);
        Station station2 = new Station("S004", "North Station", "Uptown", Zone.CENTER, 2);
        Station station3 = new Station("S005", "South Station", "Suburbs", Zone.BOTH, 2);
        manager.addStation(station1);
        manager.addStation(station2);
        manager.addStation(station3);

        // Setup transport line and mode
        TransportLine line = new TransportLine(1, 10.5,
                Arrays.asList(station1, station2, station3),
                LocalTime.of(6, 0), LocalTime.of(22, 0));
        TransportMode transport = new TransportMode("T005", line, PowerSupply.DIESEL);
        manager.addTransportMode(transport);

        // Setup employee
        StationAgent agent = new StationAgent(1, "Agent Smith", station1);
        manager.hireEmployee(agent);

        // Setup passenger
        Passenger p1 = new Passenger(1, "John Doe");
        Passenger p2 = new Passenger(2, "Jane Roe");
        manager.registerPassenger(p1);
        manager.registerPassenger(p2);

        // Sell and validate fare mediums
        FareMedium f1 = new FareMedium(1, "F001", Zone.CENTER, 120, PaymentMethod.CASH,
                LocalDateTime.of(2025, Month.APRIL, 3, 8, 15));
        FareMedium f2 = new FareMedium(2, "F002", Zone.BOTH, 200, PaymentMethod.DAHABIA_CARD,
                LocalDateTime.of(2025, Month.APRIL, 4, 9, 30));
        FareMedium f3 = new FareMedium(3, "F003", Zone.CENTER, 150, PaymentMethod.BARIDIMOB_APP,
                LocalDateTime.of(2025, Month.APRIL, 5, 10, 0));

        manager.processFareSale(p1, station1, f1, agent);
        manager.processFareSale(p2, station1, f2, agent);
        manager.processFareSale(p1, station1, f3, agent);

        // Validate fare media within the date range
        f1.validate();
        f1.setActivationDateTime(LocalDateTime.of(2025, Month.APRIL, 3, 8, 30));
        station1.validateFareMedium(f1);
        f2.validate();
        f2.setActivationDateTime(LocalDateTime.of(2025, Month.APRIL, 4, 9, 45));
        station1.validateFareMedium(f2);
        f3.validate();
        f3.setActivationDateTime(LocalDateTime.of(2025, Month.APRIL, 5, 10, 15));
        station1.validateFareMedium(f3);

        // Complaints
        TechnicalComplaint c1 = new TechnicalComplaint(112, "Issue", station1,
                TechnicalIssueType.GENERAL_FAILURE, transport);
        c1.setSeverity(SeverityLevel.HIGH);
        c1.setStatus(ComplaintStatus.APPROVED);
        c1.setDate(LocalDateTime.of(2025, Month.APRIL, 15, 14, 0));
        p1.submitComplaint(c1);
        station1.addComplaint(c1);
        manager.processComplaint(c1);

        Complaint c2 = new ServiceSafetyComplaint(113, "Theft", station1);
        c2.setSeverity(SeverityLevel.LOW);
        c2.setStatus(ComplaintStatus.REJECTED);
        c2.setDate(LocalDateTime.of(2025, Month.APRIL, 16, 12, 0));
        p2.submitComplaint(c2);
        station1.addComplaint(c2);
        manager.processComplaint(c2);

        TechnicalComplaint c3 = new TechnicalComplaint(114, "Fare Reader Issue", station1,
                TechnicalIssueType.FARE_READER, null);
        c3.setStatus(ComplaintStatus.RESOLVED);
        p1.submitComplaint(c3);
        station1.addComplaint(c3);
        manager.processComplaint(c3);

        TechnicalComplaint c4 = new TechnicalComplaint(115, "AC Failure", station1,
                TechnicalIssueType.GENERAL_FAILURE, transport);
        c4.setSeverity(SeverityLevel.HIGH);
        c4.setStatus(ComplaintStatus.APPROVED);
        c4.setDate(LocalDateTime.of(2025, Month.APRIL, 15, 15, 0));
        p1.submitComplaint(c4);
        station1.addComplaint(c4);
        manager.processComplaint(c4);

        TechnicalComplaint c5 = new TechnicalComplaint(116, "Schedule Display Issue", station1,
                TechnicalIssueType.GENERAL_FAILURE, transport);
        c5.setSeverity(SeverityLevel.HIGH);
        c5.setStatus(ComplaintStatus.APPROVED);
        c5.setDate(LocalDateTime.of(2025, Month.APRIL, 15, 16, 0));
        p1.submitComplaint(c5);
        station1.addComplaint(c5);
        manager.processComplaint(c5);

        // Add a Fare Payment complaint
        FarePaymentComplaint c6 = new FarePaymentComplaint(117, "Overcharged", station1, 50.0, PaymentMethod.BARIDIMOB_APP);
        c6.setSeverity(SeverityLevel.MEDIUM);
        c6.setStatus(ComplaintStatus.APPROVED);
        c6.setDate(LocalDateTime.of(2025, Month.APRIL, 16, 13, 0));
        p2.submitComplaint(c6);
        station1.addComplaint(c6);
        manager.processComplaint(c6);

        // Resolve some complaints to trigger reactivation
        manager.getComplaintService().resolveComplaint(c1);
        manager.getComplaintService().resolveComplaint(c4);
        manager.getComplaintService().resolveComplaint(c5);

        // Print Reports
        System.out.println("a.  Purchase History for Station " + station1.getName() + ":");
        System.out.println("----------------------------------------------------");
        manager.generateFareMediumReport(station1);

        System.out.println("...  Revenue Report:");
        System.out.println("---------------");
        manager.generateRevenueReport();

        System.out.println("Visitors at Station " + station1.getName() + " from 2025-04-01 to 2025-04-07:");
        System.out.println("-------------------------------------------------------------------");
        manager.analyzeTransportUsage(station1,
                LocalDateTime.of(2025, 4, 1, 0, 0),
                LocalDateTime.of(2025, 4, 7, 23, 59));

        System.out.println("Usage Statistics:");
        System.out.println("-----------------");
        manager.printPeakAndOffPeak();

        System.out.println("Complaints for Station " + station1.getName() + ":");
        System.out.println("---------------------------------------");
        manager.displayComplaintStatistics(station1);

        System.out.println("Complaints for Transport Code [" + transport.getCode() + "]:");
        System.out.println("--------------------------------------");
        manager.displayComplaintStatistics(transport);

        manager.displaySuspensionStatistics();

        // Print all complaints
        System.out.println("Complaint #C112 | Status: " + c1.getStatus() + " | Type: Technical | Severity: High | Concern: Transport " + transport.getCode() + " | Date: 2025-04-15");
        System.out.println("Complaint #C113 | Status: " + c2.getStatus() + " | Type: Service/Safety | Issue: Theft | Station: " + station1.getCode() + " | Date: 2025-04-16");
        System.out.println("Complaint #C114 | Status: " + c3.getStatus() + " | Type: Technical | Concern: Fare Reader | Station: " + station1.getCode());
        System.out.println("Complaint #C115 | Status: " + c4.getStatus() + " | Type: Technical | Severity: High | Concern: Transport " + transport.getCode() + " | Date: 2025-04-15");
        System.out.println("Complaint #C116 | Status: " + c5.getStatus() + " | Type: Technical | Severity: High | Concern: Transport " + transport.getCode() + " | Date: 2025-04-15");
        System.out.println("Complaint #C117 | Status: " + c6.getStatus() + " | Type: Fare Payment | Issue: Overcharged | Amount: 50.0 | Station: " + station1.getCode() + " | Date: 2025-04-16");
    }
}