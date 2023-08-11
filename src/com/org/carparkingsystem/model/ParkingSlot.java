package com.org.carparkingsystem.model;

public class ParkingSlot
{
    private int slotNumber;
    private boolean availability;

    public ParkingSlot(int slotNumber, boolean availability)
    {
        this.slotNumber = slotNumber;
        this.availability = availability;
    }

    public int  getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

}
