package com.firstClubMembership.service;

import com.firstClubMembership.model.*;
import com.firstClubMembership.repository.InMemoryMembershipRepository;
import com.firstClubMembership.strategy.OrderCountStrategy;
import com.firstClubMembership.strategy.SpendStrategy;
import com.firstClubMembership.strategy.TierEvaluationStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MembershipService {

    private final InMemoryMembershipRepository repository;
    private final List<TierEvaluationStrategy> evaluationStrategies = new ArrayList<>();

    public MembershipService(InMemoryMembershipRepository repository) {
        this.repository = repository;

        // Seed rules dynamically for upgrading tiers [cite: 12]
        evaluationStrategies.add(new SpendStrategy(new BigDecimal("500.00"), "PLATINUM"));
        evaluationStrategies.add(new OrderCountStrategy(15, "GOLD"));
    }

    public List<MembershipPlan> getAvailablePlans() {
        return repository.getAllPlans();
    }

    public List<MembershipTier> getAvailableTiers() {
        return repository.getAllTiers();
    }

    public UserSubscription subscribe(String userId, String planId) {
        MembershipPlan plan = repository.findPlanById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Plan ID"));

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime expiryDate = calculateExpiry(startDate, plan.getDuration());

        UserSubscription subscription = new UserSubscription(userId, planId, plan.getTierId(), startDate, expiryDate);
        return repository.saveSubscription(subscription);
    }

    public UserSubscription updateTierManually(String userId, String targetTierId) {
        UserSubscription subscription = repository.findSubscriptionByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No active subscription found for user"));

        MembershipTier targetTier = repository.findTierById(targetTierId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Tier ID"));

        subscription.setCurrentTierId(targetTier.getId());
        return repository.saveSubscription(subscription);
    }

    public UserSubscription cancelSubscription(String userId) {
        UserSubscription subscription = repository.findSubscriptionByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No subscription found"));

        subscription.setActive(false);
        return repository.saveSubscription(subscription);
    }

    public Optional<UserSubscription> getSubscription(String userId) {
        return repository.findSubscriptionByUserId(userId);
    }

    // Dynamic Engine evaluation based on usage
    public UserSubscription evaluateDynamicTierUpdate(String userId, int orderCount, BigDecimal monthlySpend, String cohort) {
        UserSubscription subscription = repository.findSubscriptionByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        String bestTierId = subscription.getCurrentTierId();
        int currentPriority = repository.findTierById(bestTierId).map(MembershipTier::getPriority).orElse(0);

        for (TierEvaluationStrategy strategy : evaluationStrategies) {
            if (strategy.qualifies(userId, orderCount, monthlySpend, cohort)) {
                MembershipTier potentialTier = repository.findTierById(strategy.getTargetTierId()).orElse(null);
                if (potentialTier != null && potentialTier.getPriority() > currentPriority) {
                    bestTierId = potentialTier.getId();
                    currentPriority = potentialTier.getPriority();
                }
            }
        }

        if (!bestTierId.equals(subscription.getCurrentTierId())) {
            subscription.setCurrentTierId(bestTierId);
            repository.saveSubscription(subscription);
        }

        return subscription;
    }

    private LocalDateTime calculateExpiry(LocalDateTime start, PlanDuration duration) {
        return switch (duration) {
            case MONTHLY -> start.plusMonths(1);
            case QUARTERLY -> start.plusMonths(3);
            case YEARLY -> start.plusYears(1);
        };
    }
}