package com.example.test;

import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

import com.example.main.BatteryReport;
import com.example.main.CellBalanceMonitor;
import com.example.main.ReportObservable;

public class BatteryReportTest extends TestCase {
    private BatteryReport report;

    @Before
    public void setUp() {
        this.report = new BatteryReport();
    }

    public void testAddObserver() {
        Observer observer = new Observer(this.report);
        this.report.addObserver(observer);
        CellBalanceMonitor monitor = new CellBalanceMonitor(report);
        monitor.checkCellBalance(new int[]{1, 10, 1, 10, 1});
        assertTrue(observer.updated);

    }

    class Observer implements ReportObservable {

        public boolean updated = false;
        private BatteryReport report;

        public Observer(BatteryReport report) {
            this.report = report;
        }

        @Override
        public void update() {
            updated = true;
            System.out.println("Attention: " + report.getAlert().toString());
        }
    }
}
