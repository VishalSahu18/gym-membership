package com.firstClubMembership.strategy;

import java.math.BigDecimal;

public class OrderCountStrategy implements TierEvaluationStrategy {
    private final int requiredOrders;
    private final String tierId;

    public OrderCountStrategy(int requiredOrders, String tierId) {
        this.requiredOrders = requiredOrders;
        this.tierId = tierId;
    }

    @Override
    public boolean qualifies(String userId, int orderCount, BigDecimal totalMonthlySpend, String cohort) {
        return orderCount >= requiredOrders; // [cite: 20]
    }

    @Override
    public String getTargetTierId() { return tierId; }
}
