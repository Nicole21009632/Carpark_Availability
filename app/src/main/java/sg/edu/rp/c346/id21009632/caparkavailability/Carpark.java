package sg.edu.rp.c346.id21009632.caparkavailability;

public class Carpark {

    private String carpark_number;
    private String total_lots;
    private String lot_type;
    private String lots_available;

    public Carpark(String carpark_number, String total_lots, String lot_type, String lots_available) {
        this.carpark_number = carpark_number;
        this.total_lots = total_lots;
        this.lot_type = lot_type;
        this.lots_available = lots_available;
    }

    public String getCarpark_number() {
        return carpark_number;
    }

    public String getTotal_lots() {
        return total_lots;
    }

    public String getLot_type() {
        return lot_type;
    }

    public String getLots_available() {
        return lots_available;
    }

    public void setCarpark_number(String carpark_number) {
        this.carpark_number = carpark_number;
    }

    public void setTotal_lots(String total_lots) {
        this.total_lots = total_lots;
    }

    public void setLot_type(String lot_type) {
        this.lot_type = lot_type;
    }

    public void setLots_available(String lots_available) {
        this.lots_available = lots_available;
    }


    @Override
    public String toString() {
        String msg = "Carpark Number: " + carpark_number;
        msg += "\nTotal Lots: " + total_lots;
        msg += "\nLot Type: " + lot_type;
        msg += "\nLots Available: " + lots_available;

        return msg;
    }
}
