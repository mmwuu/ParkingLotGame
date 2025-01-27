import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class ParkingLot {
    public static final int numNonParkingSpacesPerColumn = 6;
    public static final int minYear = 1970;
    public static final int maxYear = 2099;
    public static final int regnIdLength = 6;


    private List<Vehicle> checkedInVehicles = new ArrayList<>();
    private List<Vehicle> checkedOutVehicles = new ArrayList<>();
    private int emptyLots;
    private int occupiedLots;
    private boolean isInitialised;
    private int length; // Horizontally, Col
    private int width; // Vertically, Row
    private char[][] parkingArray = new char[width][length];
    private int[] position = new int[2];
    private Vehicle vehicle;
    private int numTrucks;
    private int numBikesMotorbikes;

    public void initValues() {
        emptyLots = 0;
        occupiedLots = 0;
        isInitialised = false;
        numTrucks = 0;
        numBikesMotorbikes = 0;
    }

    // Sets isInitialised to true if initialising with command line arguments.
    public void setInitialised(boolean isInitialised) {
        this.isInitialised = isInitialised;
    }

    // Sets number of empty and occupied lots if initialising with command line arguments.
    public void setEmptyLots(int emptyLots) {
        this.emptyLots = emptyLots;
    }

    // Sets up the parking lot array if initialising with command line arguments.
    public void setParkingArray(char[][] parkingArray) {
        this.parkingArray = parkingArray;
    }

    // Displays the main menu.
    public void displayMenuText() {
        if (!isInitialised) {
            String emptyLotsText = (emptyLots > 0) ? String.valueOf(emptyLots) : "[None]";
            String occupiedLotsText = (occupiedLots > 0) ? String.valueOf(occupiedLots) : "[None]";
            System.out.println("Empty Lots: " + emptyLotsText + " | Occupied: " + occupiedLotsText);
        } else {
            String emptyLotsText = (emptyLots > 0) ? String.valueOf(emptyLots) : "[None]";
            System.out.println("Empty Lots: " + emptyLotsText + " | Occupied: " + occupiedLots);
        }
        System.out.println("Please enter a command to continue.");
        System.out.println("Type 'help' to learn how to get started.");
    }

    // Handles the help menu.
    public void help(Scanner scanner) {
        displayHelpMenu();
        String command = " ";

        // Help menu command loop.
        while (!command.equals("menu")){
            System.out.print("> ");
            command = scanner.nextLine().toLowerCase();

            if (command.equals("commands")){
                commands();
                displayHelpMenu();
            } else if (command.equals("menu")) {
                System.out.println();
                displayMenuText();
                break;
            } else {
                System.out.println("Command not found!");
                displayHelpMenu();
            }
        }
    }

    // Prints the help menu.
    public void displayHelpMenu() {
        System.out.println();
        System.out.println("Type 'commands' to list all the available commands");
        System.out.println("Type 'menu' to return to the main menu");
    }

    // Prints list of possible commands.
    public void commands() {
        System.out.println();
        System.out.println("help: shows you list of commands that you can use.");
        System.out.println("parkinglot: initialise the space for parking lot or view the layout of parking lot.");
        System.out.println("checkin: add your car details while entering the parking lot.");
        System.out.println("park: park your car to one of the empty spot.");
        System.out.println("checkout: view the parking fee while exiting the parking lot.");
        System.out.println("parkingfeelog: view the transaction log for parking lot.");
        System.out.println("exit: To exit the program.");

    }

    // Handles the 'parkinglot' menu.
    public void parkingLot(Scanner scanner) {
        displayParkingLotMenu();
        String command = " ";

        // Parking lot menu command loop.
        while (!command.equals("menu")) {
            System.out.print("> ");
            command = scanner.nextLine().toLowerCase();

            if (command.equals("init")) {
                if (checkedInVehicles == null || checkedInVehicles.isEmpty()) {
                    initParkingLot(scanner);
                } else {
                    System.out.println("There are vehicles in the Parking Lot, you cannot change the space of the parking lot at the moment.");
                    displayParkingLotMenu();
                }
            } else if (command.equals("view")) {
                if (isInitialised) {
                    displayParkingArray(scanner);
                    System.out.print("Press any key to return to parkinglot menu\n");
                    scanner.nextLine();
                    displayParkingLotMenu();
                } else {
                    System.out.println("The parking lot is not initialised. Please run init!");
                    System.out.print("Press any key to return to parkinglot menu\n");
                    scanner.nextLine();
                    displayParkingLotMenu();
                }
            } else if (command.equals("menu")) {
                // Returns to main menu.
                displayMenuText();
            } else {
                System.out.println("Command not found!");
                displayParkingLotMenu();
            }
        }
    }

    // Prints the menu of the 'parkinglot' command
    private void displayParkingLotMenu () {
        System.out.println("Type 'init' to initialise the parking space");
        System.out.println("Type 'view' to view the layout of the parking space");
        System.out.println("Type 'menu' to return to the main menu");
    }

    // Handles 'init' command to initialise the parking lot.
    private void initParkingLot(Scanner scanner) {
        System.out.println("Please enter the length of the parking lot.");
        System.out.print("> ");

        // Checks if the length input is integer, returns to main menu otherwise.
        try {
            length  = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return;
        }

        while (length < 7) {
            System.out.println("The length of the parking lot cannot be less than 7. Please re-enter.");
            System.out.print("> ");
            length = Integer.parseInt(scanner.nextLine());
        }
        System.out.println("Please enter the width of the parking lot.");
        System.out.print("> ");

        // Checks if the width input is integer, returns to main menu otherwise.
        try {
            width  = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return;
        }

        while (width < 7) {
            System.out.println("The width of the parking lot cannot be less than 7. Please re-enter.");
            System.out.print("> ");
            width = Integer.parseInt(scanner.nextLine());
        }

        // Calculates number of empty lots with given formula.
        emptyLots = (length - 2 - (length-2) /2) * (width - 4) - (length - 2 - (length-2) /2)*2;

        // Initialises the 2D array of the parking lot with given width and length.
        parkingArray = initParkingArray(width, length);
        isInitialised = true;

        // View the initialised parking lot.
        System.out.println("Parking Lot Space is setup. Here is the layout -");
        displayParkingArray(scanner);
        System.out.print("Press any key to return to parkinglot menu\n");
        scanner.nextLine();
        displayParkingLotMenu();
    }

    // Initialises the 2D array that represents the parking lot.
    public char[][] initParkingArray(int width, int length) {

        char[][] parkingArray = new char[width][length];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (j == 0 || j == length - 1) {
                    parkingArray[i][j] = '|';
                } else if (i == 0 || i == width - 1) {
                    parkingArray[i][j] = '-';
                } else if (i == 1 || i == width - 2 ) {
                    parkingArray[i][j] = '~';
                } else if (i == 2 || i == width - 3) {
                    parkingArray[i][j] = j % 2 == 0 ? '~' : 'P';
                } else {
                    parkingArray[i][j] = j % 2 == 0 ? '~' : '.';
                }
            }
        }
        // Places the doors
        parkingArray[1][0] = 'D';
        parkingArray[width - 2][length - 1] = 'D';

        return parkingArray;
    }

    // Allows the user to view the parking lot
    public void displayParkingArray (Scanner scanner) {
        for (int i = 0; i < parkingArray.length; i++) {
            for (int j = 0; j < parkingArray[i].length; j++) {
                System.out.print(parkingArray[i][j]);
            }
            System.out.println();
        }
    }

    // Helper method to show error if there is no more reserved truck spots.
    private boolean noMoreTruckSpots (){
        int numTruckSpots = width - numNonParkingSpacesPerColumn;

        if (numTrucks >= numTruckSpots) {
            System.out.println("Parking full for truck. Please come back later. Taking you back to main menu.");
            return true;
        }
       return false;
    }

    // Same for bikes and motorbikes.
    private boolean noMoreBikeMotorbikeSpots (String type){
        int numBikeMotorbikeSpots = width - numNonParkingSpacesPerColumn;

        if (numBikesMotorbikes >= numBikeMotorbikeSpots) {
            if (type.equals("bike")) {
                System.out.println("Parking full for bike. Please come back later. Taking you back to main menu.");
            } else if (type.equals("motorbike")) {
                System.out.println("Parking full for motorbike. Please come back later. Taking you back to main menu.");
            }

            return true;
        }
        return false;
    }

    // Handles the 'checkin' command and checks in a vehicle.
    public void checkIn(Scanner scanner) {

        if (!isInitialised) {
            System.out.println("The parking lot hasn't been initialised. Please set up a space for the parking lot. Taking you back to main menu.");
            displayMenuText();
            return;
        }

        if (emptyLots == 0) {
            System.out.println("The parking is full. Please come back later. Taking you back to main menu.");
            displayMenuText();
            return;
        }

        // Asks for details and stores them.
        System.out.println("Please enter the vehicle details");
        System.out.print("> Vehicle Type: ");
        String vehicleType = scanner.nextLine();
        vehicleType = vehicleType.toLowerCase();

        // Checks if user entered a valid vehicle type.
        while (!vehicleType.equals("car") && !vehicleType.equals("bike") && !vehicleType.equals("motorbike") && !vehicleType.equals("truck")) {
            System.out.println("Invalid detail, please enter detail again!");
            System.out.print("> Vehicle Type: ");
            vehicleType = scanner.nextLine();
            vehicleType = vehicleType.toLowerCase();
        }

        // Checks if parking is full for that vehicle type.
        if (((vehicleType.equals("bike") || vehicleType.equals("motorbike")) && noMoreBikeMotorbikeSpots(vehicleType)) || (vehicleType.equals("truck") && noMoreTruckSpots())) {
            displayMenuText();
            return;
        }

        // Asks for Regn Id
        System.out.print("> Regn Id: ");
        String regnId = scanner.nextLine();
        regnId = regnId.toLowerCase();

        // Checks if Regn Id is in the correct format.
        while (regnId.length() != regnIdLength) {
            System.out.println("Invalid detail, please enter detail again!");
            System.out.print("> Regn Id: ");
            regnId = scanner.nextLine();
            regnId = regnId.toLowerCase();
        }

        System.out.print("> Vehicle Model: ");
        String model = scanner.nextLine();

        System.out.print("> Vehicle Colour: ");
        String color = scanner.nextLine();

        System.out.print("> Date of entry: ");
        String entryDate = scanner.nextLine();

        while (!isValidDate(entryDate)) {
            System.out.print("> Date of entry: ");
            entryDate = scanner.nextLine();
        }

        System.out.print("> Time of entry: ");
        String entryTime = scanner.nextLine();

        while (!isValidTime(entryTime)) {
            System.out.print("> Time of entry: ");
            entryTime = scanner.nextLine();
        }

        // Initialise vehicle fields.
        String exitDate = "";
        String exitTime = "";
        double parkingFee = 0.0;
        int hits = 0;
        int[] lastPosition = {0, 0};
        char tileUnderVehicle = '~';

        // Creates a Vehicle instance and adds it to the list of checked in vehicles.
        vehicle = new Vehicle(vehicleType, regnId, model, color, entryDate, entryTime, exitDate, exitTime, parkingFee, hits, lastPosition, tileUnderVehicle);
        checkedInVehicles.add(vehicle);

        occupiedLots++;
        emptyLots--;

        // Tracks the number of truck, bikes and motorbikes for reserved spots.
        if (vehicleType.equals("truck")) {
            numTrucks++;
        }
        if (vehicleType.equals("bike") || vehicleType.equals("motorbike")) {
            numBikesMotorbikes++;
        }
        displayMenuText();
    }

    // Handles 'checkout' command to check a vehicle out of the parking lot
    // and add it to the parkingfeelog.
    public void checkOut(Scanner scanner) {

        // No vehicle checked in.
        if (checkedInVehicles == null || checkedInVehicles.isEmpty()) {
            System.out.println("Invalid command! The parking is empty. Taking you back to main menu.");
            displayMenuText();
            return;
        }

        System.out.println("Please enter your vehicle details");
        System.out.print("> Regn Id: ");
        String regnId = scanner.nextLine();
        regnId = regnId.toLowerCase();

        // Invalid Regn Id length.
        while (regnId.length() != regnIdLength) {
            System.out.println("Invalid detail, please enter detail again!");
            System.out.print("> Regn Id: ");
            regnId = scanner.nextLine();
            regnId = regnId.toLowerCase();
        }

        // Finds the vehicle in list of checked in vehicles by regnId.
        boolean vehicleFound = false;
        Vehicle checkoutVehicle = null;
        for (Vehicle vehicle : checkedInVehicles) {
            if (vehicle.regnId.equalsIgnoreCase(regnId)) {
                vehicleFound = true;
                checkoutVehicle = vehicle;
            }
        }

        // The regnId entered does not match a vehicle in the parking lot.
        if (!vehicleFound) {
            System.out.println("The selected vehicle type is not present in the parking lot. Taking you back to main menu");
            displayMenuText();
            return;
        }

        // Vehicle is not at checkout door.
        if (!isAtExit(checkoutVehicle.type)) {
            System.out.println("The selected vehicle type is not at the checkout door. Please proceed to checkout door. Taking you back to main menu.");
            displayMenuText();
            return;
        }

        System.out.print("> Date of exit: ");
        String exitDate = scanner.nextLine();

        // Date is in correct format and not before the checkin date.
        while (!isValidDate(exitDate) || beforeCheckinDate(checkoutVehicle.entryDate, exitDate)) {
            System.out.print("> Date of exit: ");
            exitDate = scanner.nextLine();
        }

        System.out.print("> Time of exit: ");
        String exitTime = scanner.nextLine();

        boolean sameDayCheckout = numDaysParked(checkoutVehicle.entryDate, exitDate) == 0;

        // Time is in correct format and, if applicable, not before the checkin time.
        while (!isValidTime(exitTime) || beforeCheckinTime(checkoutVehicle.entryTime, exitTime, sameDayCheckout)) {
            System.out.print("> Time of exit: ");
            exitTime = scanner.nextLine();
        }

        // Creates instance of the TimeBetween class.
        TimeBetween result = calculateParkingDuration(checkoutVehicle.entryDate, checkoutVehicle.entryTime, exitDate, exitTime);

        // Calculate the parking fee, parking fine and adds them to get total fee.
        double parkingFee = vehicle.calculateParkingFee(checkoutVehicle.type, result.getDays(), result.getHours());
        int numHits = checkoutVehicle.hits;
        double fine = calculateFine(checkoutVehicle.type, numHits);
        double totalFee = parkingFee + fine;

        System.out.println("Please verify your details.");
        System.out.println("Total number of hours: " + result.getHours());
        if (result.getDays() != 0) {
            System.out.println("Total number of overnight parking: " + result.getDays());
        }
        System.out.println("Total number of hits:" + numHits);
        System.out.println("Vehicle Type: " + checkoutVehicle.type.substring(0, 1).toUpperCase() + checkoutVehicle.type.substring(1));
        System.out.println("Regn Id: " + (checkoutVehicle.regnId));
        System.out.println("Total Parking Fee: $" + totalFee);

        System.out.println("Type Y to accept the fee or menu to return to main menu");
        System.out.print("> ");
        String answer = scanner.nextLine();

        while (!answer.equalsIgnoreCase("Y") && !answer.equalsIgnoreCase("menu")) {
            System.out.println("You cannot checkout your vehicle. Please accept the fee by pressing Y or type menu to return to main menu and park your vehicle.");
            System.out.print("> ");
            answer = scanner.nextLine();
        }

        if (answer.equalsIgnoreCase("Y")) {
            // Removes the vehicle from the list of checked in vehicles
            // and adds it to the list of checked out vehicles (aka. parkingfeelog).
            int vehicleIndex = -1;

            // Tries to find the vehicle that is being checked out.
            for (int i = 0; i < checkedInVehicles.size(); i++) {
                Vehicle vehicle = checkedInVehicles.get(i);

                // If vehicle matches.
                if (vehicle.regnId.equalsIgnoreCase(regnId)) {
                    vehicle.exitDate = exitDate;
                    vehicle.exitTime = exitTime;
                    vehicle.parkingFee = totalFee;
                    checkedOutVehicles.add(vehicle);
                    vehicleIndex = i;
                    break;
                }
            }

            // If the vehicle is found, remove it from list of checked in vehicles.
            if (vehicleIndex != -1) {
                checkedInVehicles.remove(vehicleIndex);
            }
            if (checkoutVehicle.type.equals("truck")) {
                numTrucks--;
            }
            if (checkoutVehicle.type.equals("bike") || checkoutVehicle.type.equals("motorbike")) {
                numBikesMotorbikes--;
            }
            emptyLots++;
            occupiedLots--;
            parkingArray[width - 2][length - 2] = '~';

            System.out.println("Thank you for visiting Java Parking Lot. See you next time!");
            displayMenuText();
        } else {
            displayMenuText();
        }
    }

    // Checks if date is valid.
    private boolean isValidDate(String date) {
        // Checks if format is in yyyy-MM-dd.
        if (date.length() != 10) {
            System.out.println("Incorrect date format, please enter date in yyyy-MM-dd format again!");
            return false;
        }
        if (date.charAt(4) != '-' || date.charAt(7) != '-') {
            System.out.println("Incorrect date format, please enter date in yyyy-MM-dd format again!");
            return false;
        }

        // Parses date and checks if date is within range.
        String firstFour = date.substring(0, 4);
        String middleTwo = date.substring(5, 7);
        String lastTwo = date.substring(8, 10);

        // Checks if date input is numeric.
        if (!isNumeric(firstFour) || !isNumeric(middleTwo) || !isNumeric(lastTwo)) {
            System.out.println("Incorrect date format, please enter date in yyyy-MM-dd format again!");
            return false;
        }

        int year = Integer.parseInt(firstFour);
        int month = Integer.parseInt(middleTwo);
        int day = Integer.parseInt(lastTwo);

        int minMonth = 1, maxMonth = 12;
        int minDay = 1, maxDay = 31;

        if(year < minYear || year > maxYear || month < minMonth || month > maxMonth || day < minDay || day > maxDay){
            System.out.println("Incorrect date format, please enter date in yyyy-MM-dd format again between 1970-01-01 and 2099-12-31!");
            return false;
        }

        return true;
    }

    private boolean beforeCheckinDate(String entryDate, String exitDate) {
        if (numDaysParked(entryDate, exitDate) < 0) {
            System.out.println("Checkout datetime cannot be less than checkin datetime for the vehicle. Please re-enter.");
            return true;
        }
        return false;
    }

    // Checks that time input is valid.
    private boolean isValidTime(String time) {

        // Checks if format is HH:mm
        if (time.length() != 5) {
            System.out.println("Incorrect time format, please enter time in HH:mm format again!");
            return false;
        }
        if (time.charAt(2) != ':') {
            System.out.println("Incorrect time format, please enter time in HH:mm format again!");
            return false;
        }

        // Parses time and checks if it is within range.
        String firstTwo = time.substring(0, 2);
        String lastTwo = time.substring(3, 5);

        // Checks if time input is numeric.
        if (!isNumeric(firstTwo) || !isNumeric(lastTwo)) {
            System.out.println("Incorrect time format, please enter time in HH:mm format again!");
            return false;
        }

        int hours  = Integer.parseInt(firstTwo);
        int minutes = Integer.parseInt(lastTwo);

        if (!(hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59)) {
            System.out.println("Incorrect time format, please enter time in HH:mm format again!");
            return false;
        }

        return true;
    }

    private boolean beforeCheckinTime (String entryTime, String exitTime, boolean sameDayCheckout) {
        if (sameDayCheckout) {
            String entryFirstTwo = entryTime.substring(0, 2);
            String entryLastTwo = entryTime.substring(3, 5);

            int entryHours  = Integer.parseInt(entryFirstTwo);
            int entryMinutes = Integer.parseInt(entryLastTwo);

            String exitFirstTwo = exitTime.substring(0, 2);
            String exitLastTwo = exitTime.substring(3, 5);

            int exitHours = Integer.parseInt(exitFirstTwo);
            int exitMinutes = Integer.parseInt(exitLastTwo);

            int numMinutesParked = (exitHours - entryHours) * 60 + (exitMinutes - entryMinutes);

            if (numMinutesParked < 0) {
                System.out.println("Checkout datetime cannot be less than checkin datetime for the vehicle. Please re-enter.");
                return true;
            }
        }
        return false;
    }

    // Calculates the number of days parked.
    private int numDaysParked(String entryDate, String exitDate) {
        // Parse entryDate
        String entryFirstFour = entryDate.substring(0, 4);
        String entryMiddleTwo = entryDate.substring(5, 7);
        String entryLastTwo = entryDate.substring(8, 10);

        int entryYear = Integer.parseInt(entryFirstFour);
        int entryMonth = Integer.parseInt(entryMiddleTwo);
        int entryDay = Integer.parseInt(entryLastTwo);

        // Parse exitDate
        String exitFirstFour = exitDate.substring(0, 4);
        String exitMiddleTwo = exitDate.substring(5, 7);
        String exitLastTwo = exitDate.substring(8, 10);

        int exitYear = Integer.parseInt(exitFirstFour);
        int exitMonth = Integer.parseInt(exitMiddleTwo);
        int exitDay = Integer.parseInt(exitLastTwo);

        return ((exitYear - entryYear) * 365) + ((exitMonth - entryMonth) * 30) + (exitDay - entryDay);
    }

    // Checks if a string is numeric.
    public static boolean isNumeric(String string) {
        int intValue;

        if(string == null || string.isEmpty()) {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Calculates the number of days and hours parked.
    private TimeBetween calculateParkingDuration (String entryDate, String entryTime, String exitDate, String exitTime) {

        // Parse entryDate
        String entryFirstFour = entryDate.substring(0, 4);
        String entryMiddleTwo = entryDate.substring(5, 7);
        String entryLastTwo = entryDate.substring(8, 10);

        int entryYear = Integer.parseInt(entryFirstFour);
        int entryMonth = Integer.parseInt(entryMiddleTwo);
        int entryDay = Integer.parseInt(entryLastTwo);

        // Parse exitDate
        String exitFirstFour = exitDate.substring(0, 4);
        String exitMiddleTwo = exitDate.substring(5, 7);
        String exitLastTwo = exitDate.substring(8, 10);

        int exitYear = Integer.parseInt(exitFirstFour);
        int exitMonth = Integer.parseInt(exitMiddleTwo);
        int exitDay = Integer.parseInt(exitLastTwo);

        // Calculate number of overnights.
        int numDaysParked = ((exitYear - entryYear) * 365)
                + ((exitMonth - entryMonth) * 30)
                + (exitDay - entryDay);

        // Parse entryTime and get it in minutes.
        String entryFirstTwoHours = entryTime.substring(0, 2);
        String entryLastTwoMins = entryTime.substring(3, 5);

        int entryHour = Integer.parseInt(entryFirstTwoHours);
        int entryMinutes = Integer.parseInt(entryLastTwoMins);
        int entryTimeInMins = entryHour * 60 + entryMinutes;

        // Parse exitTime and get it in minutes.
        String exitFirstTwo = exitTime.substring(0, 2);
        String exitLastTwoMins = exitTime.substring(3, 5);

        int exitHour = Integer.parseInt(exitFirstTwo);
        int exitMinutes = Integer.parseInt(exitLastTwoMins);
        int exitTimeInMins = exitHour * 60 + exitMinutes;

        double days;
        double hours;

        // If checkout is after check in (normal).
        if (exitTimeInMins >= entryTimeInMins) {
            double totalMinutes = (numDaysParked * 24 * 60) + (exitTimeInMins - entryTimeInMins);
            days = totalMinutes / (24 * 60);
            hours = Math.ceil(totalMinutes / 60 % 24);

        } else {
            double totalMinutes = (numDaysParked * 24 * 60) - (entryTimeInMins - exitTimeInMins);
            days = totalMinutes / (24 * 60);
            hours = Math.ceil(totalMinutes / 60 % 24);
        }

        if (hours % 24 == 0 && hours != 0) {
            days = days + 1;
            hours = hours - 24;
        }

        return new TimeBetween((int)days, (int)hours);
    }

    // Calculates the parking fee according to vehicle type.


    // Calculates the parking fine according to vehicle type.
    private double calculateFine(String vehicleType, int numHits) {
        double fine;

        if (vehicleType.equals("bike")) {
            fine = 0.0;
        } else if (vehicleType.equals("motorbike")) {
            fine = 10.0;
        } else if (vehicleType.equals("car")) {
            fine = 20.0;
        } else if (vehicleType.equals("truck")) {
            fine = 50.0;
        } else {
            fine = 0;
        }
        return fine * numHits;
    }

    // Allows the user to park the car.
    public void park(Scanner scanner) {
        System.out.println("To park a vehicle provide the details.");

        String regnId;
        Vehicle vehicleToPark = null;
        boolean vehicleFound = false;

        System.out.print("> Regn Id: ");
        regnId = scanner.nextLine();
        regnId = regnId.toLowerCase();

        while (regnId.length() != regnIdLength) {
            System.out.println("Invalid detail, please enter detail again!");
            System.out.print("> Regn Id: ");
            regnId = scanner.nextLine();
            regnId = regnId.toLowerCase();
        }

        // Find vehicle in list of checked in vehicles.
        for (Vehicle vehicle : checkedInVehicles) {
            if (vehicle.regnId.equalsIgnoreCase(regnId)) {
                vehicleFound = true;
                vehicleToPark = vehicle;
            }
        }

        // The regnId entered does not match a vehicle in the parking lot.
        while (!vehicleFound) {
            System.out.println("The vehicle mentioned is not parked in the parking lot.");
            System.out.println("Please enter your vehicle details");
            System.out.print("> Regn Id: ");
            regnId = scanner.nextLine();
            regnId = regnId.toLowerCase();
            for (Vehicle vehicle : checkedInVehicles) {
                if (vehicle.regnId.equalsIgnoreCase(regnId)) {
                    vehicleFound = true;
                    vehicleToPark = vehicle;
                }
            }
        }

        // Initialise vehicle position to the entry door.
        if (vehicleToPark.lastPosition[0] == 0 && vehicleToPark.lastPosition[1] == 0) {
            position[0] = 1;
            position[1] = 0;
        } else {
            position[0] = vehicleToPark.lastPosition[0];
            position[1] = vehicleToPark.lastPosition[1];
        }

        // Find the vehicle character according to vehicle type (C/B/M/T).
        char vehicleChar = vehicleTypeChar(vehicleToPark.type);
        int[] lastPosition = new int[2];

        // Command loop for movements of a vehicle in the parking lot.
        displayParkingArray(scanner);
        while (true) {

            System.out.println("Type w/s/a/d to move the vehicle to up/down/left/right or else press q to exit.");
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("w")) {
                // Move up
                move(-1, 0, vehicleChar, regnId, vehicleToPark.tileUnderVehicle);
                displayParkingArray(scanner);
            } else if (input.equals("s")) {
                // Move down
                move(1, 0, vehicleChar, regnId, vehicleToPark.tileUnderVehicle);
                displayParkingArray(scanner);
            } else if (input.equals("a")) {
                // Move left
                move(0, -1, vehicleChar, regnId, vehicleToPark.tileUnderVehicle);
                displayParkingArray(scanner);
            } else if (input.equals("d")) {
                // Move right
                move(0, 1, vehicleChar, regnId, vehicleToPark.tileUnderVehicle);
                displayParkingArray(scanner);
            } else if (input.equals("q")) {
                // Quit/Exit park mode
                lastPosition[0] = position[0];
                lastPosition[1] = position[1];
                displayMenuText();
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
        vehicleToPark.lastPosition = lastPosition;
    }

    // Moves the Vehicle in the array.
    private void move(int rowMove, int colMove, char vehicleChar, String regnId, char tileUnderVehicle) {

        int newRow = position[0] + rowMove;
        int newCol = position[1] + colMove;

        if (isValidMove(newRow, newCol, regnId, vehicleChar)) {
            // Replace previous tile.
            if (parkingArray[position[0]][position[1]] != 'D' ){
                parkingArray[position[0]][position[1]] = tileUnderVehicle;
            }
            // store new position and tile under the vehicle.
            position[0] = newRow;
            position[1] = newCol;
            tileUnderVehicle = parkingArray[position[0]][position[1]];
            storeTileUnderVehicle(regnId, tileUnderVehicle);
            // Move the vehicle.
            parkingArray[newRow][newCol] = vehicleChar;
        }
    }

    // Returns the char that represents the vehicle type.
    private char vehicleTypeChar(String type) {
        if (type.equals("car")) {
            return 'C';
        }
        if (type.equals("bike")) {
            return 'B';
        }
        if (type.equals("motorbike")) {
            return 'M';
        }
        if (type.equals("truck")) {
            return 'T';
        }
        return 'N';
    }

    // Increments the number of hits a vehicle has, based on regnId.
    private void incrementVehicleHits (String regnId) {
        for (Vehicle vehicle : checkedInVehicles) {
            if (vehicle.regnId.equalsIgnoreCase(regnId)) {
                vehicle.hits++;
            }
        }
    }

    // Stores the tile that the vehicle is occupying.
    private void storeTileUnderVehicle (String regnId, char tileUnderVehicle) {
        for (Vehicle vehicle : checkedInVehicles) {
            if (vehicle.regnId.equalsIgnoreCase(regnId)) {
                vehicle.tileUnderVehicle = tileUnderVehicle;
            }
        }
    }

    // Checks if the move is valid and prints error messages accordingly.
    private boolean isValidMove(int row, int col, String regnId, char vehicleChar) {
        // Fixes moving out of bounds on first move.
        if (row < 0 || col < 0) {
            System.out.println("You cannot exit the parking lot without checkout.");
            return false;
        }

        // Trying to exit out door without checking out.
        if (parkingArray[row][col] == 'D') {
            System.out.println("You cannot exit the parking lot without checkout.");
            return false;
        }

        // Hit wall
        if (row < 1 || row > parkingArray.length - 2 || col < 1 || col > parkingArray[row].length - 2) {
            if (parkingArray[row][col] == 'D'){
                System.out.println("You cannot exit the parking lot without checkout.");
            } else if (vehicleChar == 'B') {
                System.out.println("You have hit the wall!");
            } else {
                System.out.println("You have hit the wall, there will be a damage fee!");
                incrementVehicleHits(regnId);
            }

            return false;
        }

        // Hit another vehicle
        if (parkingArray[row][col] == 'C' || parkingArray[row][col] == 'B' || parkingArray[row][col] == 'M' || parkingArray[row][col] == 'T') {
            if (vehicleChar == 'B') {
                System.out.println("You have hit a vehicle!");
            } else {
                System.out.println("You have hit a vehicle, there will be a damage fee!");
                incrementVehicleHits(regnId);
            }
            return false;
        }

        // Hit the pillar.
        if (parkingArray[row][col] == 'P') {
            if (vehicleChar == 'B') {
                System.out.println("You have hit the pillar!");
            } else {
                System.out.println("You have hit the pillar, there will be a damage fee!");
                incrementVehicleHits(regnId);
            }
            return false;
        }

        // Truck not parking in reserved truck spots.
        if (vehicleChar == 'T') {
            if (col > 2 && parkingArray[row][col] == '.') {
                System.out.println("You cannot park a truck in the parking lot anywhere except the parking spots near the entry.");
                return false;
            }
        }
        // Bike or motorbike not parking in their reserved spots.
        if (vehicleChar == 'B' || vehicleChar == 'M') {
            if (col < length - 3 && parkingArray[row][col] == '.') {
                System.out.println("You cannot park a bike or motorbike in the parking lot anywhere except the parking spots near the exit.");
                return false;
            }
        }

        return true;
    }

    // Checks if the vehicle is at the exit.
    private boolean isAtExit (String type) {
        char vehicleChar = ' ';
        if (type.equals("car")) {
            vehicleChar = 'C';
        }
        if (type.equals("bike")) {
            vehicleChar = 'B';
        }
        if (type.equals("motorbike")) {
            vehicleChar = 'M';
        }
        if (type.equals("truck")) {
            vehicleChar = 'T';
        }
        return parkingArray[width - 2][length - 2] == vehicleChar;
    }

    // Gets the list of checkout out vehicles.
    public List<Vehicle> getCheckedOutVehicles() {
        return checkedOutVehicles;
    }
}