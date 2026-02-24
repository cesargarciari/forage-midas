package com.jpmc.midascore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {


    @Id
    @GeneratedValue()
    private Long id;

    @ManyToOne
    UserRecord sender;

    @ManyToOne
    private UserRecord receiver;

    @Column(nullable = false)
    private float amount;

    private float incentive;

    protected TransactionRecord() {
    }

    public TransactionRecord(UserRecord sender, UserRecord receiver, float amount, float incentive) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.incentive = incentive;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserRecord getSender() { return sender; }
    public void setSender(UserRecord sender) { this.sender = sender; }

    public UserRecord getRecipient() { return receiver; }
    public void setRecipient(UserRecord receiver) { this.receiver = receiver; }

    public float getAmount() { return amount; }
    public void setAmount(float amount) { this.amount = amount;}

    public float getIncentive() { return incentive; }
    public void setIncentive(float incentive) { this.incentive = incentive; }
}