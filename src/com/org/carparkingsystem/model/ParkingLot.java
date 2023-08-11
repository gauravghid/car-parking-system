package com.org.carparkingsystem.model;

import java.util.ArrayList;

public class ParkingLot
{
    private static final int numberOfSlots = 100;

    private ArrayList<ParkingSlot> listOfSlots = null;

    public ParkingLot()
    {
        listOfSlots = new ArrayList<>();
    }

    /**
     * This method returns list of all the parking slots
     *
     * @return list of the slots in the parking lot
     */
    public ArrayList<ParkingSlot> getParkingSlots()
    {
        for (int i = 1; i <= numberOfSlots; i++)
        {
            ParkingSlot slot = new ParkingSlot(i, true);
            listOfSlots.add(slot);
        }
        return listOfSlots;
    }

}
