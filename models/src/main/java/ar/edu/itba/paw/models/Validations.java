package ar.edu.itba.paw.models;

import java.util.List;

public class Validations {

    public static boolean isValid(String value){
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValid(Float value){
        return value != null && !value.isInfinite() && !value.isNaN();
    }
}
