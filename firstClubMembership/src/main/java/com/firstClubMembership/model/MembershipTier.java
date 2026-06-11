package com.firstClubMembership.model;

public class MembershipTier {
    private String id; //  "SILVER", "GOLD", "PLATINUM"
    private String name;
    private int priority; // Silver=1, Gold=2, Platinum=3

    private boolean freeDelivery;
    private double extraDiscountPercentage;
    private boolean earlyAccess;
    private boolean prioritySupport;


    public MembershipTier(String id, String name, int priority, boolean freeDelivery, double extraDiscountPercentage, boolean earlyAccess, boolean prioritySupport) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.freeDelivery = freeDelivery;
        this.extraDiscountPercentage = extraDiscountPercentage;
        this.earlyAccess = earlyAccess;
        this.prioritySupport = prioritySupport;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getPriority() { return priority; }
    public boolean isFreeDelivery() { return freeDelivery; }
    public double getExtraDiscountPercentage() { return extraDiscountPercentage; }
    public boolean isEarlyAccess() { return earlyAccess; }
    public boolean isPrioritySupport() { return prioritySupport; }
}
