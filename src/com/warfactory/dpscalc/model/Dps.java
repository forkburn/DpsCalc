package com.warfactory.dpscalc.model;

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
	 * Critical hit damage, in addition to normal damage, in percentage
	 */
	private double critDamage;

	public Dps() {

	}

	public Dps (Dps that) {
		this.weaponDps = that.weaponDps;
		this.mainAttribute = that.mainAttribute;
		this.iasPercent = that.iasPercent;
		this.critChance = that.critChance;
		this.critDamage = that.critDamage;
	}

	public int getDps() {
		double mainAttributeFactor = 1 + mainAttribute / 100.0;
		double iasFactor = 1 + iasPercent;
		double critHitFactor = 1 + critChance * critDamage;
		return (int) (weaponDps * mainAttributeFactor * iasFactor * critHitFactor);
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
