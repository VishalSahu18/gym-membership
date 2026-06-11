package com.firstClubMembership.strategy;

import java.math.BigDecimal;

public interface TierEvaluationStrategy {
    boolean qualifies(String userId, int orderCount, BigDecimal totalMonthlySpend, String cohort);
    String getTargetTierId();
}