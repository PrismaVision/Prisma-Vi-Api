package com.api.prisma_vi.user;


public enum UserRole {

    ADMIN("admin"),
    USER("user");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

}
