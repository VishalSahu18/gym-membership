package com.firstClubMembership.model;

import java.math.BigDecimal;

public class MembershipPlan {

    private String id;
    private PlanDuration duration; // monthly, quaterly, yearly
    private String tierId;
    private BigDecimal price;



    public MembershipPlan(String id, PlanDuration duration, String tierId, BigDecimal price) {
        this.id = id;
        this.duration = duration;
        this.tierId = tierId;
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public PlanDuration getDuration() {
        return duration;
    }

    public String getTierId() {
        return tierId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
