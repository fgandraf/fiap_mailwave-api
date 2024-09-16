package com.mailwave.api.modules.users.enums;

public enum UserRole {
    ADMIN( "admin"),
    REGISTERED ("registered");

    private final String _role;
    public String getRole() { return _role; }

    UserRole (String role) {
        this._role = role;
    }

}