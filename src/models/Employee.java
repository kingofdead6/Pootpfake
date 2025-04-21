package models;

public abstract class Employee extends Person {
    protected Station assignedStation;
    
    public Employee(int id, String name, Station station) {
        super(id, name);
        this.assignedStation = station;
    }
    
    public abstract void processComplaint(Complaint complaint);

    public Station getAssignedStation() {
        return assignedStation;
    }
}