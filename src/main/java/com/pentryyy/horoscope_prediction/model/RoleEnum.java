package com.pentryyy.horoscope_prediction.model;

import lombok.Getter;

@Getter
public enum RoleEnum {  
    ROLE_USER((short) 1),
    ROLE_ADMIN((short) 2);

    private short value;

    RoleEnum(Short value) {
        this.value = value;
    }
 
    public static String getNameByValue(Short value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.getValue() == value) {
                return role.name();
            }
        }
        throw new IllegalArgumentException("No role found for value: " + value);
    }
}