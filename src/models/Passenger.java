package models;

import java.util.ArrayList;
import java.util.List;

public class Passenger extends Person {
    private List<FareMedium> fareMedia;
    
    public Passenger(int id, String name) {
        super(id, name);
        this.fareMedia = new ArrayList<>();
    }
    
    public void purchaseFareMedium(Station station, FareMedium fareMedium) {
        station.sellFareMedium(this, fareMedium);
        fareMedia.add(fareMedium);
    }
    
    public void validateFareMedium(FareMedium fareMedium) {
        fareMedium.validate();
    }
    
    public void submitComplaint(Complaint complaint) {
        complaint.setSubmitter(this);
    }

    public List<FareMedium> getFareMedia() {
        return fareMedia;
    }
}