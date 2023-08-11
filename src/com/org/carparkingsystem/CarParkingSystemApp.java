package com.org.carparkingsystem;

import com.org.carparkingsystem.model.Car;
import com.org.carparkingsystem.model.ParkingLot;
import com.org.carparkingsystem.model.ParkingSlot;
import com.org.carparkingsystem.model.ParkingTicket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class CarParkingSystemApp {

    private ArrayList<ParkingSlot> slots = null;
    ArrayList<ParkingTicket> ticketList = null;

    private final double parkingRate = 2;
    public CarParkingSystemApp()
    {
        ParkingLot lot = new ParkingLot();
        slots = lot.getParkingSlots();

        ticketList = new ArrayList<>(); // to save tickets
    }

    /**
     *  This method checks for available slots in the parking lot
     *
     * @return slot if available or null if no slots are available
     */
    public ParkingSlot checkAvailability()
    {
        ParkingSlot slot;
        for(int i = 0; i < slots.size(); i++)
        {
            slot = slots.get(i);

            // check availability
            if(slot.getAvailability() == true)
            {
                return slot;
            }
        }
        return null;
    }


    /**
     * This method gets a parking slot and returns a parking ticket.
     * @param carNumber
     * @return
     */
    public ParkingTicket park(String carNumber)
    {
        ParkingSlot slot = checkAvailability(); // check for available slots

        if (slot != null)
        {
            Car car = new Car(carNumber);
            Date date = new Date();
            long startTimeMilliseconds = System.currentTimeMillis();

            ParkingTicket ticket = new ParkingTicket(car,slot.getSlotNumber(), startTimeMilliseconds, date);
            ticketList.add(ticket); // save the ticket in ticketList
            slot.setAvailability(false); // this slot is no more available
            return ticket;
        }
        return null;
    }

    /**
     * This method checks whether the provided car number is in parking lot or not. If present it calculates the fare and
     * release the slot.
     * @param carNumber
     * @return
     */
    public void makeSlotAvailable(String carNumber)
    {
        Optional<ParkingTicket> parkTicket = ticketList.stream().filter(ticket -> ticket.getCar().getCarNumber().equalsIgnoreCase(carNumber)).findFirst();
        if(parkTicket.isPresent()) {
            deleteTicketFromList(parkTicket.get());
            calculateFare(parkTicket.get());
            for (int i = 0; i < slots.size(); i++)
            {
                int slotNumber = slots.get(i).getSlotNumber();

                if (parkTicket.get().getSlotNumber() == slotNumber)
                {
                    ParkingSlot slot = slots.get(i);
                    slot.setAvailability(true);
                }
            }
            System.out.println("Parking slot released");
        } else {
            System.out.println("Car Number not present in parking lot");
        }

    }

    /**
     * This methods remove the parking ticket from the list.
     * @param parkingTicket
     */
    private void deleteTicketFromList(ParkingTicket parkingTicket) {
        ParkingTicket ticketToBeRemoved = null;
        for(ParkingTicket ticket : ticketList) {
            if(ticket.getCar().getCarNumber().equalsIgnoreCase(parkingTicket.getCar().getCarNumber())){
                ticketToBeRemoved = ticket;
                break;
            }
        }
        ticketList.remove(ticketToBeRemoved);
    }

    /**
     * This method calculates the parking fare.
     * @param parkTicket
     */
    private void calculateFare(ParkingTicket parkTicket) {
        int hours = calculateTotalDuration(parkTicket);
        if(hours < 1 ) {
            System.out.println(" Your parking charge : £"+parkingRate);
        } else {
            System.out.println(" Your parking charge : £"+(parkingRate*hours));
        }
    }

    /**
     * This method calculates the total duration
     * @param ticket
     * @return
     */
    public int calculateTotalDuration(ParkingTicket ticket)
    {
        long durationMilliSeconds = System.currentTimeMillis() - ticket.getStartTime(); // total time the card was parked in the slot
        String durationParked = convertTimeFormat(durationMilliSeconds);
        String [] time = durationParked.split(":");
        int hours = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);
        int seconds = Integer.parseInt(time[2]);
        int timeInMinutes =  minutes + (seconds / 60);
        int timeInHours = hours + (timeInMinutes / 60);
        return timeInHours;
    }

    public String convertTimeFormat(long milliSeconds)
    {
        // Obtain the total seconds since midnight, Jan 1, 1970
        long totalSeconds = milliSeconds / 1000;
        // Compute the current second in the minute in the hour
        long currentSecond = totalSeconds % 60;
        // Obtain the total minutes
        long totalMinutes = totalSeconds / 60;
        // Compute the current minute in the hour
        long currentMinute = totalMinutes % 60;
        // Obtain the total hours
        long totalHours = totalMinutes / 60;
        // Compute the current hour
        long currentHour = totalHours % 24;

        return currentHour + ":" + currentMinute + ":" + currentSecond;

    }
}
