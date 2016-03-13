package com.example.main;


import com.example.main.ValueOutOfBoundException;


public class BatteryMonitor extends Thread{

    private static boolean on = true;
    private static SOCLogic soc;
    private static CellBalanceMonitor cellBalanceMonitor;
    private static ChargeMonitor chargeMonitor;
    public static BMSState BMS_STATE;


    public BatteryMonitor(BatteryReport batteryReport) {
        BatteryMonitor.soc = new SOCLogic();
        BatteryMonitor.cellBalanceMonitor = new CellBalanceMonitor(batteryReport);
        BatteryMonitor.chargeMonitor = new ChargeMonitor(batteryReport);

        float[] cellPower = getCellData();


        soc.setCellCapacity((Float)XMLParser.centralStorage.get(XMLParser.TOTAL_CAPACITY_EACH_CELLS));
        soc.setBatteryCapacity((Float) XMLParser.centralStorage.get(XMLParser.TOTAL_BATTERY_CAPACITY));
        soc.setCellPower(cellPower);
        soc.setCellSoc();
        soc.setBatteryPower();
        soc.setBatterySoc();

    }
    
    private static float[] getCellData() {
        int numberOfBadCells = 0;
        float[] cellData;
        cellData = new float[]{(float) XMLParser.centralStorage.get(XMLParser.CHARGE_AMOUNT_CELL1),
                (Float) XMLParser.centralStorage.get(XMLParser.CHARGE_AMOUNT_CELL2),
                (Float) XMLParser.centralStorage.get(XMLParser.CHARGE_AMOUNT_CELL3),
                (Float) XMLParser.centralStorage.get(XMLParser.CHARGE_AMOUNT_CELL4),
                (Float) XMLParser.centralStorage.get(XMLParser.CHARGE_AMOUNT_CELL5)};
        for (int i = 0; i < cellData.length; i++) {
            if (cellData[i] < 0) {
                cellData[i] = 0;
                numberOfBadCells++;

            }

        }

        on = !(numberOfBadCells == cellData.length);
        return cellData;
        
    }

    public void run() {
        while (on) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	
            if (XMLParser.getBMSStatus().equals(BMSState.CHARGING.toString())) {
                on = true;

                try {
                	soc.setCellPower(getCellData());
                    soc.setCellSoc();
                    soc.setBatteryPower();
                    soc.setBatterySoc();
                    cellBalanceMonitor.checkCellBalance(soc.getCellSoc());
                    chargeMonitor.checkChargeLevel(soc.getBatterySoc());
                    XMLParser.writer();
        	        System.exit(0);
                } catch (ValueOutOfBoundException e) {
                    e.printStackTrace();
                }
            } else if (XMLParser.getBMSStatus().equals(BMSState.ONMOVE.toString())) {

                on = true;
                try {
                    soc.setCellPower(getCellData());
                    soc.setCellSoc();
                    soc.setBatteryPower();
                    soc.setBatterySoc();
                    cellBalanceMonitor.checkCellBalance(soc.getCellSoc());
                    chargeMonitor.checkChargeLevel(soc.getBatterySoc());
                    XMLParser.writer();
        	        System.exit(0);
                } catch (ValueOutOfBoundException e) {
                    e.printStackTrace();
                }

            } else {
                on = false;
            }
        }
    }
    
}
