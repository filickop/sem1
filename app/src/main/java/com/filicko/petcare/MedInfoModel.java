package com.filicko.petcare;

public class MedInfoModel {
    String datum;
    String info;

    /**
     * @param datum datum z medInfo
     * @param info zaznam z medInfo
     */
    public MedInfoModel(String datum, String info) {
        this.datum = datum;
        this.info = info;
    }

    /**
     *
     * @return vrati datum
     */
    public String getDatum() {
        return datum;
    }

    /**
     *
     * @return vrati zaznam
     */
    public String getInfo() {
        return info;
    }
}
