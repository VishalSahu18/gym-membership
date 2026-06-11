package com.firstClubMembership.strategy;

import java.math.BigDecimal;

public class SpendStrategy implements TierEvaluationStrategy {
    private final BigDecimal requiredSpend;
    private final String tierId;

    public SpendStrategy(BigDecimal requiredSpend, String tierId) {
        this.requiredSpend = requiredSpend;
        this.tierId = tierId;
    }

    @Override
    public boolean qualifies(String userId, int orderCount, BigDecimal totalMonthlySpend, String cohort) {
        return totalMonthlySpend != null && totalMonthlySpend.compareTo(requiredSpend) >= 0; // [cite: 21]
    }

    @Override
    public String getTargetTierId() { return tierId; }
}