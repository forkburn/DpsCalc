package com.warfactory.dpscalc.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DpsTest {

	private Dps dps;

	@Before
	public void setUp() throws Exception {
		dps = new Dps();
	}

	@Test
	public void testDps() {
		int wpDps = 300;
		int mainAttrib = 500;
		double iasPercent = 20;
		double critChance = 0.4;
		double critDam = 0.5;

		dps.setWeaponDps(wpDps);
		dps.setMainAttribute(mainAttrib);
		dps.setIasPercent(iasPercent);
		dps.setCritChance(critChance);
		dps.setCritDamage(critDam);

		int expectedDps = (int) Math.round(wpDps * (1 + mainAttrib / 100.0) * (1 + iasPercent * 0.5 / 100)
				* (1 + (critChance * critDam)));

		assertEquals(expectedDps, dps.getDps());
	}
}
