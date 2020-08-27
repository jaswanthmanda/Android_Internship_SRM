package com.example.readyouting;

public class District_assign {
    public String district_confirmed, district_active, district_recovered, district_deceased,ditrct_main;

    public District_assign( String ditrct_main,String district_confirmed, String district_active, String district_recovered, String district_deceased){

        this.district_active = district_active;
        this.ditrct_main = ditrct_main;
        this.district_confirmed = district_confirmed;
        this.district_deceased = district_deceased;
        this.district_recovered = district_recovered;
    }

    public String getDitrct_main() {
        return ditrct_main;
    }

    public void setDitrct_main(String ditrct_main) {
        this.ditrct_main = ditrct_main;
    }

    public String getDistrict_confirmed() {
        return district_confirmed;
    }

    public void setDistrict_confirmed(String district_confirmed) {
        this.district_confirmed = district_confirmed;
    }

    public String getDistrict_active() {
        return district_active;
    }

    public void setDistrict_active(String district_active) {
        this.district_active = district_active;
    }

    public String getDistrict_recovered() {
        return district_recovered;
    }

    public void setDistrict_recovered(String district_recovered) {
        this.district_recovered = district_recovered;
    }

    public String getDistrict_deceased() {
        return district_deceased;
    }

    public void setDistrict_deceased(String district_deceased) {
        this.district_deceased = district_deceased;
    }
}