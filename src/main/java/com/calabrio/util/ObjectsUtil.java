package com.calabrio.util;

public class ObjectsUtil {
    public static boolean noneNull(Object... objects) {
        for(Object obj : objects) {
            if(obj == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean allNull(Object... objects) {
        for(Object obj : objects) {
            if(obj != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean anyNull(Object... objects) {
        for(Object obj : objects) {
            if(obj == null) {
                return true;
            }
        }
        return false;
    }
}
