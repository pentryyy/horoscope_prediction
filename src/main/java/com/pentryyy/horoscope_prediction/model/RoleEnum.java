package com.pentryyy.horoscope_prediction.model;

public enum RoleEnum {
    ROLE_USER((short) 1),
    ROLE_ADMIN((short) 2);

    private final short value;

    RoleEnum(short value) {
        this.value = value;
    }

    public short getValue() {
        return value;
    }

    public static String getNameByValue(short value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.getValue() == value) {
                return role.name();
            }
        }
        throw new IllegalArgumentException("No role found for value: " + value);
    }
}