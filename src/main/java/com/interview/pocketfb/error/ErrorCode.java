package com.interview.pocketfb.error;

import com.google.gson.Gson;

public enum ErrorCode {
	// EC_1XXX for Init errors wrt data and otehr things
    EC_1000("Data validation failed", ErrorGroup.DataError),
    EC_1001("Friend not found to approve please user user/{userid} to see who user firends are", ErrorGroup.DataError),
    EC_1002("Friend not found to approve please user user/{userid} if user exists", ErrorGroup.DataError),
    
    
    
    
	 EC_2000("Input validation failed", ErrorGroup.InputError),
	EC_2001("User not found", ErrorGroup.InputError),
	EC_2002("Wrong credentials", ErrorGroup.InputError),
    
	
	EC_UNKNOWN("Dont know No time to debug. Leaving for future gens", ErrorGroup.DataError);
	
	
    private String value;
    private ErrorGroup errorGroup;
    ErrorCode(String val, ErrorGroup errorGroup) {
        value = val;
        this.errorGroup = errorGroup;
    }
    public String getValue() {
        return value;
    }
    public ErrorGroup getErrorGroup() {
        return errorGroup;
    }
    public static ErrorCode fromString(String val) {
        if (val == null || val.length() < 1) {
            return null;
        }
        for (ErrorCode v : ErrorCode.values()) {
            if (v.toString().equalsIgnoreCase(val)) {
                return v;
            }
        }
        return null;
    }
    @Override
    public String toString() {
    	 return new Gson().toJson(this);
    }
}
