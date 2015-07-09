package com.warfactory.dpscalc.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CharacterProfileTest {

	private CharacterProfile characterProfile;

	@Before
	public void setUp() throws Exception {
		characterProfile = new CharacterProfile();
	}

	@Test
	public void testDps() {
		double wpDps = 300;
		int primaryAttrib = 500;
		double iasPercent = 20;
		double critChance = 0.4;
		double critDam = 0.5;

		characterProfile.setWeapon1Dps(wpDps);
		characterProfile.setPrimaryAttribute(primaryAttrib);
		characterProfile.setIasPercent(iasPercent);
		characterProfile.setCritChance(critChance);
		characterProfile.setCritDamage(critDam);

		double expectedDps = wpDps * (1 + primaryAttrib / 100.0) * (1 + iasPercent / 100.0)
				* (1 + (critChance * critDam));

		assertEquals(expectedDps, characterProfile.getDps(), 1);
	}

    @Test
    public void testDualDps() {
        double wp1Dps = 400;
        double wp2Dps = 500;
        int primaryAttrib = 500;
        double iasPercent = 20;
        double critChance = 0.4;
        double critDam = 0.5;


        characterProfile.setWeapon1Dps(wp1Dps);
        characterProfile.setWeapon2Dps(wp2Dps);
        characterProfile.setPrimaryAttribute(primaryAttrib);
        characterProfile.setIasPercent(iasPercent);
        characterProfile.setCritChance(critChance);
        characterProfile.setCritDamage(critDam);

        double expectedWeaponDps = (wp1Dps + wp2Dps) * 0.5;
        double expectedDps = expectedWeaponDps * (1 + primaryAttrib / 100.0) * (1 + iasPercent / 100.0 + 0.15)
                * (1 + (critChance * critDam));

        assertEquals(expectedDps, characterProfile.getDps(), 1);
    }
}
