package com.firstClubMembership.controller;

import com.firstClubMembership.model.MembershipPlan;
import com.firstClubMembership.model.MembershipTier;
import com.firstClubMembership.model.UserSubscription;
import com.firstClubMembership.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/firstClub/membership")
@Tag(name = "Membership Management System", description = "Operations managing plans, dynamic tier promotions, and subscriptions for FirstClub.")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping("/plans")
    @Operation(summary = "Get all available membership plans", description = "Fetches the pre-configured billing intervals (Monthly, Quarterly, Yearly) with prices.")
    public ResponseEntity<List<MembershipPlan>> getPlans() {
        return ResponseEntity.ok(membershipService.getAvailablePlans());
    }


    @GetMapping("/tiers")
    @Operation(summary = "Get all membership tiers", description = "Retrieves tiers (Silver, Gold, Platinum) and details their configurable business benefits.")
    public ResponseEntity<List<MembershipTier>> getTiers() {
        return ResponseEntity.ok(membershipService.getAvailableTiers());
    }

    @PostMapping("/subscribe")
    @Operation(summary = "Subscribe a user to a baseline plan", description = "Initializes an active user subscription framework window based on the chosen plan duration.")
    public ResponseEntity<UserSubscription> subscribe(@RequestParam String userId, @RequestParam String planId) {
        return ResponseEntity.ok(membershipService.subscribe(userId, planId));
    }

    @PutMapping("/change-tier")
    @Operation(summary = "Manually change a user's tier status", description = "Overrides or manually forces a user's classification tier up or down.")
    public ResponseEntity<UserSubscription> changeTier(@RequestParam String userId, @RequestParam String targetTierId) {
        return ResponseEntity.ok(membershipService.updateTierManually(userId, targetTierId));
    }

    @PostMapping("/cancel")
    @Operation(summary = "Cancel an active membership tier subscription", description = "Flips the targeted account subscription boolean lifecycle flag to false.")
    public ResponseEntity<UserSubscription> cancel(@RequestParam String userId) {
        return ResponseEntity.ok(membershipService.cancelSubscription(userId));
    }

    @GetMapping("/track/{userId}")
    @Operation(summary = "Track user membership status", description = "Fetches active tier groupings, cycle lifespans, and current membership expiration timestamps.")
    public ResponseEntity<UserSubscription> track(@PathVariable String userId) {
        return membershipService.getSubscription(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/evaluate-activity")
    @Operation(summary = "Evaluate business rule milestones (Dynamic Upgrade Engine)", description = "Accepts checkout parameters to process user eligibility metrics (Total spend, order amounts, and cohorts) for dynamic upgrades.")
    public ResponseEntity<UserSubscription> evaluateActivity(
            @RequestParam String userId,
            @RequestParam int orderCount,
            @RequestParam BigDecimal monthlySpend,
            @RequestParam(required = false, defaultValue = "DEFAULT") String cohort) {
        return ResponseEntity.ok(membershipService.evaluateDynamicTierUpdate(userId, orderCount, monthlySpend, cohort));
    }
}
