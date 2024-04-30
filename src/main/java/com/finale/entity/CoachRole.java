package com.finale.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoachRole {
    MASTER("master"), SUB("sub");

    private final String role;

    public static CoachRole getEnum(String role) {
        for (CoachRole coachRole : values()) {
            if (role.equals(coachRole.role)) {
                return coachRole;
            }
        }
        return null;
    }
}
