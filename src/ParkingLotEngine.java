import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class ParkingLotEngine {

    ParkingLot parkingLot = new ParkingLot();

    public static void main(String[] args) {

        ParkingLotEngine engine = new ParkingLotEngine();
        // Runs the main game loop.
        engine.startProgram(args);
    }


    /*
     *  Start with the main menu here.
     */
    private void startProgram(String[] args) {

        //TODO: Implementation here
        Scanner scanner = new Scanner(System.in);

        int length = 0;
        int width = 0;
        int emptyLots = 0;

        // Initialising parking lot with command line arguments
        if (args.length > 0) {
            try {

                length = Integer.parseInt(args[0]);
                width = Integer.parseInt(args[1]);

                if (length < 7 || width < 7) {
                    // Prints error if dimensions are less than 7.
                    System.out.println("ParkingLot size cannot be less than 7. Goodbye!");
                    System.exit(1);
                } else {
                    // Initialises the parking lot array
                    parkingLot.setParkingArray(parkingLot.initParkingArray(width, length));
                    parkingLot.setInitialised(true);
                    emptyLots = (length - 2 - (length-2) /2) * (width - 4) - (length - 2 - (length-2) /2)*2;
                    parkingLot.setEmptyLots(emptyLots);
                }
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }

        // Print out the title text and menu.
        displayWelcomeText();
        parkingLot.displayMenuText();

        // Main command loop
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().toLowerCase();

            if (command.equals("help")) {
                parkingLot.help(scanner);
            } else if (command.equals("parkinglot")) {
                parkingLot.parkingLot(scanner);
            } else if (command.equals("checkin")) {
                parkingLot.checkIn(scanner);
            } else if (command.equals("checkout")) {
                parkingLot.checkOut(scanner);
            } else if (command.equals("park")) {
                parkingLot.park(scanner);
            } else if (command.equals("parkingfeelog")) {
                parkingFeeLog(parkingLot);
                parkingLot.displayMenuText();
            } else if (command.equals("exit")) {
                System.out.println("Good bye from the Java Parking Lot! See you next time!");
                break;
            } else {
                System.out.println("Command not found!");
                parkingLot.displayMenuText();
            }
        }
        scanner.close();
    }

    /*
     *  Displays the welcome text.
     */
    private void displayWelcomeText() {

        String titleText =
                " _     _  _______  ___      _______  _______  __   __  _______ \n"+
                        "| | _ | ||       ||   |    |       ||       ||  |_|  ||       |\n"+
                        "| || || ||    ___||   |    |      _||   _   ||       ||    ___|\n"+
                        "|       ||   |___ |   |    |     |  |  | |  ||       ||   |___ \n"+
                        "|       ||    ___||   |___ |     |  |  |_|  || ||_|| ||    ___|\n"+
                        "|   _   ||   |___ |       ||     |_ |       || |\\/|| ||   |___ \n"+
                        "|__| |__||_______||_______||_______||_______||_|   |_||_______|\n" +
                        "_________________________ TO JAVA PARKING _____________________";

        System.out.println(titleText);
        System.out.println();
    }


    private void parkingFeeLog(ParkingLot parkingLot) {

        List<Vehicle> checkedOutVehicles = parkingLot.getCheckedOutVehicles();
        boolean noVehiclesCheckedOut = noVehiclesCheckedOut(parkingLot);

        System.out.println("============ Here are the Transaction logs for the Java Parking Lot =============");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("| Vehicle Type | Registration Id | Entry DateTime | Exit DateTime | Parking Fee |");
        System.out.println("---------------------------------------------------------------------------------");

        //TODO: Implementation here
        if (noVehiclesCheckedOut) {
            System.out.println("No records found!");
        } else {
            for (Vehicle vehicle : checkedOutVehicles) {
                String entryDateTime = vehicle.entryDate + " " + vehicle.entryTime;
                String exitDateTime = vehicle.exitDate + " " + vehicle.exitTime;

                System.out.printf("|%14s|%17s|%-16s|%-15s|%13.1f|\n", capitalizeFirstLetter(vehicle.type), vehicle.regnId, entryDateTime, exitDateTime, vehicle.parkingFee);
            }
        }

    }
    public static String capitalizeFirstLetter(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
    private boolean noVehiclesCheckedOut(ParkingLot parkingLot) {
        List<Vehicle> checkedOutVehicles = parkingLot.getCheckedOutVehicles();
        return (checkedOutVehicles == null || checkedOutVehicles.isEmpty());
    }
}
