package com.example.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.main.BMSState;
public class BMSStateTest {

	@Test
    public void should() {
		
        assertTrue(BMSState.CHARGING.getStateValue()==1);
        assertTrue(BMSState.IDLE.getStateValue()==0);
        assertTrue(BMSState.ONMOVE.getStateValue()==2);
        assertTrue(BMSState.DAMAGED.getStateValue()==4);
        
	}

}
