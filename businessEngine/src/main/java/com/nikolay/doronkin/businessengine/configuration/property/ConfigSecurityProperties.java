package com.nikolay.doronkin.businessengine.configuration.property;

public class ConfigSecurityProperties {
    public static final String ADMIN = "hasRole('ADMIN')";
    public static final String USER = "hasRole('USER')";
    public static final String ADMIN_AND_USER = "hasAnyRole('ADMIN', 'USER')";
    public static final int SUBSTRING_VALUE = 7;
}
