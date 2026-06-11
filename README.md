# FirstClub Membership

A production-grade, extensible backend implementation for a tiered, subscription-based membership management system.Built with **Java 17** and **Spring Boot 3.x**.

The platform enables seamless user registration across multiple billing structures (Monthly, Quarterly, Yearly)  while offering highly configurable, perk-driven reward tiers (Silver, Gold, Platinum).

---

## 🚀 Key Features

* **Decoupled Billing & Rewards:** Separates subscription durations (`PlanDuration`) from benefit levels (`MembershipTier`).
* **Dynamic Tier Evaluation Engine:** Uses the **Strategy Pattern** to automatically transition users across tiers based on real-time activity metrics like spend caps, transaction frequency, or demographic cohorts.
* **High-Performance Thread Safety:** Designed with safe, thread-contained atomic updates using `ConcurrentHashMap` structures to handle simultaneous requests securely.
* **Self-Contained Architecture:** Fully executable local demonstration with zero external dependencies (no local database installation required).

---

## 🏗️ Architecture & Extensibility

The solution relies on the **Strategy Design Pattern** to evaluate customer activity against tier advancement criteria. Adding a brand-new upgrade rule requires writing a single isolated strategy class—keeping the core checkout loops closed to modifications (**SOLID Principles**.

```text
com.firstClubMembership
├── controller        // REST Controllers (Swagger annotated)
├── dto               // Data Transfer Objects
├── model             // JPA-ready Domain Entities 
├── repository        // Thread-safe In-Memory Storage Simulator
├── service           // Core Core Business Logic
└── strategy          // Extensible Tier Promotion Rules (Strategy Pattern)
