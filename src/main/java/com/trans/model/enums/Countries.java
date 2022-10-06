package com.trans.model.enums;

public enum Countries {
    BLR,RUS,ITL,GER,UKR,LAT,LIT,GRC,POL,CHN,ENG,FRA,AUT,CZE,HR,SRB,LUX;


    public static Countries fromString(String s) {
        if (s != null) {
            for (Countries pt : Countries.values()) {
                if (s.equalsIgnoreCase(pt.toString())) {
                    return pt;
                }
            }
        }
        throw new IllegalArgumentException("No such value");
    }
}
