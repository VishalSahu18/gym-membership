package com.firstClubMembership.model;

import java.time.LocalDateTime;

public class UserSubscription {
    private String userId;
    private String planId;
    private String currentTierId;
    private LocalDateTime startDate;
    private LocalDateTime expiryDate;
    private boolean active;

    public UserSubscription() {}

    public UserSubscription(String userId, String planId, String currentTierId, LocalDateTime startDate, LocalDateTime expiryDate) {
        this.userId = userId;
        this.planId = planId;
        this.currentTierId = currentTierId;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.active = true;
    }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }
    public String getCurrentTierId() { return currentTierId; }
    public void setCurrentTierId(String currentTierId) { this.currentTierId = currentTierId; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
