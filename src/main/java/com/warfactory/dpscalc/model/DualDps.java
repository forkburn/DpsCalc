package com.warfactory.dpscalc.model;

/**
 * Model to calculate dual wield DPS
 */
public class DualDps extends Dps {

	/**
	 * DPS of weapon in both hands
	 */
	private int weapon1Dps;
	private int weapon2Dps;

	public DualDps() {

	}

	/**
	 * Copy ctor
	 * 
	 * @param that
	 */
	public DualDps(DualDps that) {
		this.setWeapon1Dps(that.getWeapon1Dps());
		this.setWeapon2Dps(that.getWeapon2Dps());
		this.setMainAttribute(that.getMainAttribute());
		this.setIasPercent(that.getIasPercent());
		this.setCritChance(that.getCritChance());
		this.setCritDamage(that.getCritDamage());
	}

	/**
	 * Calculate the combined dps of both hands
	 */
	@Override
	public int getWeaponDps() {
		// dual wield has +15% attack speed bonus
		return (int) Math.round((weapon1Dps + weapon2Dps) * 0.5 * 1.15);
	}

	public int getWeapon1Dps() {
		return weapon1Dps;
	}

	public void setWeapon1Dps(int weapon1Dps) {
		this.weapon1Dps = weapon1Dps;
	}

	public int getWeapon2Dps() {
		return weapon2Dps;
	}

	public void setWeapon2Dps(int weapon2Dps) {
		this.weapon2Dps = weapon2Dps;
	}

}
