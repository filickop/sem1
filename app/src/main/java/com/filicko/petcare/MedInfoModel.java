package com.filicko.petcare;

public class MedInfoModel {
    String datum;
    String info;

    public MedInfoModel(String datum, String info) {
        this.datum = datum;
        this.info = info;
    }

    public String getDatum() {
        return datum;
    }

    public String getInfo() {
        return info;
    }
}
