package com.org.carparkingsystem.model;

import java.util.Date;

public class ParkingTicket
{
    private Car car;
    private int slotNumber;
    private long startTime;
    private Date date;

    public ParkingTicket(Car car,int slotNumber, long startTime, Date date)
    {
        this.car = car;
        this.slotNumber = slotNumber;
        this.startTime = startTime;
        this.date = date;
    }

    public Car getCar() {
        return car;
    }

    public int  getSlotNumber() {
        return slotNumber;
    }

    public long getStartTime() {
        return startTime;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ParkingTicket{" +
                "car=" + car +
                ", slotNumber=" + slotNumber +
                ", startTime=" + startTime +
                ", date=" + date +
                '}';
    }
}