package com.warfactory.dpscalc.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DualfDpsTest {

	private DualDps dps;

	@Before
	public void setUp() throws Exception {
		dps = new DualDps();
	}

	@Test
	public void testDps() {
		int wp1Dps = 400;
		int wp2Dps = 500;
		int mainAttrib = 500;
		double iasPercent = 20;
		double critChance = 0.4;
		double critDam = 0.5;

		dps.setWeaponDps(wp1Dps);
		dps.setWeapon2Dps(wp2Dps);
		dps.setMainAttribute(mainAttrib);
		dps.setIasPercent(iasPercent);
		dps.setCritChance(critChance);
		dps.setCritDamage(critDam);

		double expectedWeaponDps = (int) Math.round((wp1Dps + wp2Dps) * 0.5 * 1.15);
		int expectedDps = (int) Math.round(expectedWeaponDps * (1 + mainAttrib / 100.0) * (1 + iasPercent * 0.5 / 100)
				* (1 + (critChance * critDam)));

		assertEquals(expectedDps, dps.getDps());
	}
}
