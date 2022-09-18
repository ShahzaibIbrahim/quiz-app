package com.tt.quizbuilder.util;

public class StringUtil {

    public static boolean isValidString(String str) {
        if(str !=null && str.length() > 0) {
            return true;
        }
        return false;
    }
}
