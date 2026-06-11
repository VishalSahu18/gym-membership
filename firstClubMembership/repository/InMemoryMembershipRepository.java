package com.firstClubMembership.repository;

import com.firstClubMembership.model.MembershipPlan;
import com.firstClubMembership.model.MembershipTier;
import com.firstClubMembership.model.PlanDuration;
import com.firstClubMembership.model.UserSubscription;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryMembershipRepository {

    private final Map<String, MembershipTier> tiers = new ConcurrentHashMap<>();
    private final Map<String, MembershipPlan> plans = new ConcurrentHashMap<>();
    private final Map<String, UserSubscription> subscriptions = new ConcurrentHashMap<>();

    public InMemoryMembershipRepository() {
        // Seed initial Data for Demo
        tiers.put("SILVER", new MembershipTier("SILVER", "Silver", 1, true, 5.0, false, false));
        tiers.put("GOLD", new MembershipTier("GOLD", "Gold", 2, true, 10.0, true, false));
        tiers.put("PLATINUM", new MembershipTier("PLATINUM", "Platinum", 3, true, 15.0, true, true));

        plans.put("MONTHLY_SILVER", new MembershipPlan("MONTHLY_SILVER", PlanDuration.MONTHLY, "SILVER", new BigDecimal("5000")));
        plans.put("MONTHLY_GOLD", new MembershipPlan("MONTHLY_GOLD", PlanDuration.MONTHLY, "GOLD", new BigDecimal("7000")));
        plans.put("YEARLY_PLATINUM", new MembershipPlan("YEARLY_PLATINUM", PlanDuration.YEARLY, "PLATINUM", new BigDecimal("11000")));
    }

    public List<MembershipTier> getAllTiers() {
        return new ArrayList<>(tiers.values());
    }

    public Optional<MembershipTier> findTierById(String id) {
        return Optional.ofNullable(tiers.get(id));
    }

    public List<MembershipPlan> getAllPlans() {
        return new ArrayList<>(plans.values());
    }

    public Optional<MembershipPlan> findPlanById(String id) {
        return Optional.ofNullable(plans.get(id));
    }

    // Concurrency Safe Save/Update
    public UserSubscription saveSubscription(UserSubscription subscription) {
        subscriptions.put(subscription.getUserId(), subscription);
        return subscription;
    }

    public Optional<UserSubscription> findSubscriptionByUserId(String userId) {
        return Optional.ofNullable(subscriptions.get(userId));
    }
}
