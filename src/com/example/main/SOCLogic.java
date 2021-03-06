package com.example.main;


public class SOCLogic {

    private int[] cellSoc = new int[5]; //charge level of cells

    private int batterySoc; // charge level of batteries

    private float[] cellPower; // power in cells
    private float batteryPower; // power in battery

    private float cellCapacity; //cell capacity
    private float batteryCapacity; // battery capacity



    public SOCLogic() {

    }

    public void setUp(int batterySoc, float[] cellPower, float batteryPower, float cellCapacity, float batteryCapacity) {
        this.batterySoc = batterySoc;
        this.cellPower = cellPower;
        this.batteryPower = batteryPower;
        this.cellCapacity = cellCapacity;
        this.batteryCapacity = batteryCapacity;
    }






    public float[] calcCellPower(float[] loadPerCell) {

        for (int i = 0; i < cellPower.length; i++) {

            cellPower[i] = cellPower[i] - loadPerCell[i];
        }
        return cellPower;
    }

    public float calcBatteryPower() {

        float batteryPower = 0;
        for (int i = 0; i < cellPower.length; i++) {

            batteryPower += cellPower[i];
        }
        //XMLParser.centralStorage.put(XMLParser.BATTERY_CHARGE_AMOUNT, batteryPower);
        return batteryPower;
    }


    public int[] cellSOC() throws ValueOutOfBoundException {


        for (int i = 0; i < cellPower.length; i++) {
            this.cellSoc[i] = (int) ((cellPower[i] / cellCapacity) * 100);
            if (cellSoc[i] > 100)
                cellSoc[i] = 100;//throw new ValueOutOfBoundException("Value out of bounds");
        }
        return cellSoc;
    }



    public int batterySOC() throws ValueOutOfBoundException {

        int batterySOC = (int) ((getBatteryPower() / batteryCapacity) * 100);
        if (batterySOC > 100)
             batterySOC = 100;//throw new ValueOutOfBoundException("Value out of bounds");
        //XMLParser.centralStorage.put(XMLParser.BATTERY_LEVEL, batterySOC);
        
        return batterySOC;
    }


    public void setCellCapacity(float cellCapacity) {
        this.cellCapacity = cellCapacity;
    }

    public void setBatteryCapacity(float batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }



    public void setCellPower(float[] cellPower) {
        this.cellPower = cellPower;
    }

    public void setBatterySoc( ) {
        try {
            this.batterySoc = batterySOC();
            XMLParser.centralStorage.put(XMLParser.BATTERY_LEVEL, batterySoc);
        } catch (ValueOutOfBoundException e) {
        }
    }

    public void setCellSoc( ) {
        try {
            this.cellSoc = cellSOC();
        } catch (ValueOutOfBoundException e) {
        }
    }



    public int getBatterySoc() {
        return batterySoc;
    }

    public int[] getCellSoc() {
        return cellSoc;
    }


    public float getBatteryPower() {
        return batteryPower;
    }

    public void setBatteryPower() {
        this.batteryPower = calcBatteryPower();
        XMLParser.centralStorage.put(XMLParser.BATTERY_CHARGE_AMOUNT, batteryPower);
    }

}