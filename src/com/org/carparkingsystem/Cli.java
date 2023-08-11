package com.org.carparkingsystem;

import com.org.carparkingsystem.model.ParkingTicket;
import com.org.carparkingsystem.model.ParkingSlot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

public final class Cli implements AutoCloseable {

    public static Cli create(String prompt, BufferedReader reader, BufferedWriter writer, LocalDate date) {
        requireNonNull(prompt);
        requireNonNull(reader);
        requireNonNull(writer);
        return new Cli(prompt, reader, writer, date);
    }

    public static Cli create(BufferedReader reader, BufferedWriter writer) {
        return new Cli(">", reader, writer, LocalDate.now());
    }

    private static final Predicate<String> WHITESPACE = Pattern.compile("^\\s{0,}$").asPredicate();

    private final String prompt;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final LocalDate date;
    private Cli(String prompt, BufferedReader reader, BufferedWriter writer, LocalDate date) {
        this.prompt = prompt;
        this.reader = reader;
        this.writer = writer;
        this.date = date;
    }

    private void prompt() throws IOException {
        writeLine(prompt);
    }

    private Optional<String> readLine() throws IOException {
        String line = reader.readLine();
        return line == null || WHITESPACE.test(line) ? Optional.empty() : Optional.of(line);
    }

    private void writeLine(String line) throws IOException {
        writer.write(line);
        writer.newLine();
        writer.flush();
    }

    public void run() throws IOException {
        CarParkingSystemApp carParkingApp = new CarParkingSystemApp();
        writeLine("Welcome to Car Parking System !! Select Below Option");
        writeLine("Enter 1 for a Parking Slot ");
        writeLine("Enter 2 for releasing  a existing Parking Slot ");
        writeLine("Enter space to exit");
        prompt();
        Optional<String> line = readLine();
        String inputOption = null;
        while (line.isPresent()) {
            inputOption = line.get();
            if(inputOption.equalsIgnoreCase("1")){
                handleNewParking(carParkingApp);
            } else if (inputOption.equalsIgnoreCase("2")) {
                writeLine("Please enter the number of the car which is parked");
                prompt();
                Optional<String> enteredCarNumber = readLine();
                if(enteredCarNumber.isPresent()) {
                    String carNumber = enteredCarNumber.get();
                    carParkingApp.makeSlotAvailable(carNumber);
                }
            }

            writeLine("");
            writeLine("");
            writeLine("Welcome to Car Parking System !! Select Below Option");
            writeLine("Enter 1 for a Parking Slot ");
            writeLine("Enter 2 for releasing  a existing Parking Slot ");
            writeLine("Enter space to exit");
            line = readLine();
        }
        writeLine("Thank you for visiting this Car Parking");
    }

    private void handleNewParking(CarParkingSystemApp carParkingApp) throws IOException {
        ParkingSlot slot = carParkingApp.checkAvailability();
        if (null == slot) {
            writeLine("Parking slots are not available. Please come back later ");
        } else {
            writeLine("Parking slots are available. Please enter Car number to get a parking slot ");
            prompt();
            Optional<String> enteredCarNumber = readLine();
            if(enteredCarNumber.isPresent()) {
                String carNumber = enteredCarNumber.get();
                ParkingTicket parkingTicket = carParkingApp.park(carNumber);
                if(null != parkingTicket) {
                    writeLine("Thanks. Please find the parking ticket");
                    writeLine(parkingTicket.toString());
                }
            }
        }
    }

    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }

}

