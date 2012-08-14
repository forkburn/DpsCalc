package com.warfactory.dpscalc.model;

/**
 * Model to calculate dual wield DPS
 */
public class DualDps extends Dps {

	/**
	 * DPS of secondary
	 */
	private int weapon2Dps;

	public DualDps() {

	}

	/**
	 * Copy ctor
	 * 
	 * @param that
	 */
	public DualDps(DualDps that) {
		this.setWeaponDps(that.getWeaponDps());
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
	public double getTotalWeaponDps() {
		// dual wield has +15% attack speed bonus
		return Math.round((weaponDps + weapon2Dps) * 0.5 * 1.15);
	}

	public int getWeapon2Dps() {
		return weapon2Dps;
	}

	public void setWeapon2Dps(int weapon2Dps) {
		this.weapon2Dps = weapon2Dps;
	}

}
