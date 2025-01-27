public class Vehicle {
    String type;
    String regnId;
    String model;
    String color;
    String entryDate;
    String entryTime;
    String exitDate;
    String exitTime;
    double parkingFee;
    int hits;
    int[] lastPosition = new int[2];
    char tileUnderVehicle;

    // constructor
    public Vehicle(String type, String regnId, String model, String color, String entryDate, String entryTime, String exitDate, String exitTime, double parkingFee, int hits, int[] lastPosition, char tileUnderVehicle) {
        this.type = type;
        this.regnId = regnId;
        this.model = model;
        this.color = color;
        this.entryDate = entryDate;
        this.entryTime = entryTime;
        this.exitDate = exitDate;
        this.exitTime = exitTime;
        this.parkingFee = parkingFee;
        this.hits = hits;
        this.lastPosition = lastPosition;
        this.tileUnderVehicle = tileUnderVehicle;
    }

    String vehicleType;
    int numDays;
    int numHours;

    public void setTypeDaysHours (String vehicleType, int numDays, int numHours) {
        this.vehicleType = vehicleType;
        this.numDays = numDays;
        this.numHours = numHours;

    }
    public double calculateParkingFee(String vehicleType, int numDays, int numHours) {
        double rate;
        double overnightFee;

        if (vehicleType.equals("bike")) {
            rate = 2.0;
            overnightFee = 5.0;
        } else if (vehicleType.equals("motorbike")) {
            rate = 3.0;
            overnightFee = 5.0;
        } else if (vehicleType.equals("car")) {
            rate = 4.0;
            overnightFee = 10.0;
        } else if (vehicleType.equals("truck")) {
            rate = 10.0;
            overnightFee = 20.0;
        } else {
            rate = 0;
            overnightFee = 0;
        }

        double overnightFeeTotal = overnightFee * numDays;
        double hourFeeTotal = rate * numHours;
        return overnightFeeTotal + hourFeeTotal;
    }

}
