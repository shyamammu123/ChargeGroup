package com.example.test;

import org.junit.Before;
import org.junit.Test;

import com.example.main.SOCLogic;
import com.example.main.ValueOutOfBoundException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SOCLogicTest {

    private SOCLogic socLogic;

    @Before
    public void setUp() throws Exception {
        this.socLogic = new SOCLogic();
    }

    @Test
    public void testCellSOCValidValues() throws Exception {
        socLogic.setUp(50,
                new float[]{45, 46, 47, 43, 50}, 250, 100, 500);
        socLogic.cellSOC();
        assertArrayEquals("SOCLogic calculating right values", new int[]{45, 46, 47, 43, 50}, socLogic.getCellSoc());
    }

    @Test
    public void testCellSOCInvalidNormalValues() throws Exception {
        socLogic.setUp(101, new float[]{100, 99, 100, 101, 90}, 280, 50, 250);
        socLogic.cellSOC();
    }


    @Test
    public void testBatterySOCValid() throws ValueOutOfBoundException {
        socLogic.setUp(50,
                new float[]{45, 46, 47, 43, 45}, 250, 50, 500);
        socLogic.batterySOC();
        assertEquals("SOCLogic calculating wrong values", 50, socLogic.getBatterySoc());
    }

    @Test
    public void testBatterySOCInvalid() throws ValueOutOfBoundException {
        socLogic.setUp(50,
                new float[]{75, 70, 80, 70, 60}, 280, 50, 250);
        socLogic.batterySOC();
    }

    @Test
    public void testCalcCellPowerValid() throws Exception {
        socLogic.setUp(100, new float[]{50, 50, 50, 50, 45}, 250, 50, 250);
        assertArrayEquals(new float[]{30, 30, 30, 30, 30}, socLogic.calcCellPower(new float[]{20, 20, 20, 20, 15}), (float) 0.1);
    }

    @Test
    public void testCalcBaterryPowerValid() throws Exception {
        socLogic.setUp(100, new float[]{50, 50, 50, 50, 45}, 250, 50, 250);
        assertEquals(245, socLogic.calcBatteryPower(), 0.1);
    }


}