package com.warfactory.dpscalc.model;

/**
 * Model for calculating total DPS
 */
public class Dps {


    /**
     * Weapon dps, as displayed in weapons's property
     */
    protected double weapon1Dps;

    private double weapon2Dps;

    /**
     * Character main attribute
     */
    private int primaryAttribute;

    /**
     * Increased attack speed percentage, from items other than weapon. 1 means
     * +1% attack speed.
     */
    private double iasPercent;

    /**
     * Critical hit chance. between 0 and 1. 1 means 100% critical hit rate
     */
    private double critChance;

    /**
     * Critical hit damage increase percentage. 1 means +100% critical damage.
     */
    private double critDamage;

    public Dps() {
        // default ctor
    }

    /**
     * Copy ctor
     *
     * @param that
     */
    public Dps(Dps that) {
        this.setWeapon1Dps(that.getWeapon1Dps());
        this.setPrimaryAttribute(that.getPrimaryAttribute());
        this.setIasPercent(that.getIasPercent());
        this.setCritChance(that.getCritChance());
        this.setCritDamage(that.getCritDamage());
    }


    public double getWeapon2Dps() {
        return weapon2Dps;
    }

    public void setWeapon2Dps(double weapon2Dps) {
        this.weapon2Dps = weapon2Dps;
    }

    private double getAverageWeaponDps() {
        return (weapon1Dps + weapon2Dps) * 0.5;
    }

    private double getDualWeaponDps() {
        double primaryAttributeFactor = 1 + getPrimaryAttribute() / 100.0;
        // IAS is nerfed by 50% in v1.0.3
        // dual wield has +15% attack speed bonus
        double iasFactor = 1 + getIasPercent() / 100.0 + 0.15;
        double critHitFactor = 1 + getCritChance() * getCritDamage();
        return getAverageWeaponDps() * primaryAttributeFactor * iasFactor * critHitFactor;
    }

    private double getSingleWeaponDps() {
        double primaryAttributeFactor = 1 + primaryAttribute / 100.0;
        double iasFactor = 1 + iasPercent / 100.0;
        double critHitFactor = 1 + critChance * critDamage;
        return weapon1Dps * primaryAttributeFactor * iasFactor * critHitFactor;
    }


    public double getDps() {
        if (isDualWeapon()) {
            return getDualWeaponDps();
        } else {
            return getSingleWeaponDps();
        }
    }

    public boolean isDualWeapon() {
        return weapon2Dps!=0;
    }


    public double getWeapon1Dps() {
        return weapon1Dps;
    }

    public void setWeapon1Dps(double weapon1Dps) {
        this.weapon1Dps = weapon1Dps;
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
            this.critChance = 1;
        } else {
            this.critChance = critChance;
        }
    }

    public double getCritDamage() {
        return critDamage;
    }

    public void setCritDamage(double critDamage) {
        this.critDamage = critDamage;
    }

    public int getPrimaryAttribute() {
        return primaryAttribute;
    }

    public void setPrimaryAttribute(int mainAttribute) {
        this.primaryAttribute = mainAttribute;
    }

}
