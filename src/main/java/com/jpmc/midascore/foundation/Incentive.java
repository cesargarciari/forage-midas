package com.jpmc.midascore.foundation;

public class Incentive {
    private float amount;

    public Incentive() {} // Necessary for Jackson (JSON)

    public float getAmount() { return amount; }
    public void setAmount(float amount) { this.amount = amount; }
}