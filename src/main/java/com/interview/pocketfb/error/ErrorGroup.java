package com.interview.pocketfb.error;

public enum ErrorGroup {
    InputError("InputError"), DataError("DataError"), ApplicationError("ApplicationError"), DependentServiceError("DependentServiceError");
    private String value;
    ErrorGroup(String val) {
        value = val;
    }
    public String getValue() {
        return value;
    }
    public static ErrorGroup fromString(String val) {
        if (val == null || val.length() < 1) {
            return null;
        }
        for (ErrorGroup v : ErrorGroup.values()) {
            if (v.toString().equalsIgnoreCase(val)) {
                return v;
            }
        }
        return null;
    }
    public String toString() {
        return value;
    }
}





