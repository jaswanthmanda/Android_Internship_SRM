package com.example.readyouting;

public class Country_assign {

    String confirmed_country, active_country, recovered_country, deaths_country;

    public Country_assign(String confirmed_country,String active_country,String recovered_country, String deaths_country){
        this.active_country = active_country;
        this.confirmed_country = confirmed_country;
        this.recovered_country = recovered_country;
        this.deaths_country = deaths_country;
    }

    public String getConfirmed_country() {
        return confirmed_country;
    }

    public void setConfirmed_country(String confirmed_country) {
        this.confirmed_country = confirmed_country;
    }

    public String getActive_country() {
        return active_country;
    }

    public void setActive_country(String active_country) {
        this.active_country = active_country;
    }

    public String getRecovered_country() {
        return recovered_country;
    }

    public void setRecovered_country(String recovered_country) {
        this.recovered_country = recovered_country;
    }

    public String getDeaths_country() {
        return deaths_country;
    }

    public void setDeaths_country(String deaths_country) {
        this.deaths_country = deaths_country;
    }
}