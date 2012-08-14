package com.warfactory.dpscalc.model;

/**
 * Model for calculating total DPS
 */
public class Dps {

	/**
	 * Weapon dps, as displayed in weapons's property
	 */
	private int weaponDps;

	/**
	 * Character main attribute
	 */
	private int mainAttribute;

	/**
	 * Increased attack speed percentage, from items other than weapon
	 */
	private double iasPercent;

	/**
	 * Critical hit chance. between 0 and 1
	 */
	private double critChance;

	/**
	 * Critical hit damage, in addition to normal damage. 1 means +100% critical
	 * damage.
	 */
	private double critDamage;

	public Dps() {

	}

	/**
	 * Copy ctor
	 * 
	 * @param that
	 */
	public Dps(Dps that) {
		this.weaponDps = that.weaponDps;
		this.mainAttribute = that.mainAttribute;
		this.iasPercent = that.iasPercent;
		this.critChance = that.critChance;
		this.critDamage = that.critDamage;
	}

	public int getDps() {
		double mainAttributeFactor = 1 + mainAttribute / 100.0;
		// IAS is nerfed by 50% in v1.0.3
		double iasFactor = 1 + iasPercent * 0.5;
		double critHitFactor = 1 + critChance * critDamage;
		return (int) Math.round(getWeaponDps() * mainAttributeFactor * iasFactor * critHitFactor);
	}

	public int getWeaponDps() {
		return weaponDps;
	}

	public void setWeaponDps(int weaponDps) {
		this.weaponDps = weaponDps;
	}

	public double getIasPercent() {
		return iasPercent;
	}

	public void setIasPercent(double iasPercent) {
		this.iasPercent = iasPercent;
	}

	public double getCritChance() {
		return critChance;
	}

	public void setCritChance(double critChance) {
		if (critChance > 1) {
			critChance = 1;
		}
		this.critChance = critChance;
	}

	public double getCritDamage() {
		return critDamage;
	}

	public void setCritDamage(double critDamage) {
		this.critDamage = critDamage;
	}

	public int getMainAttribute() {
		return mainAttribute;
	}

	public void setMainAttribute(int mainAttribute) {
		this.mainAttribute = mainAttribute;
	}

}
