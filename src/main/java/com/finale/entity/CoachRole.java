package com.finale.entity;

public enum CoachRole {
    MASTER("master"), SUB("sub");

    private final String role;

    CoachRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
