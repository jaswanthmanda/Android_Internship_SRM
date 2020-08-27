package com.example.readyouting;

public class State_assign {

    String state_come, confirmed,active, deaths, recovred;

    public State_assign(String state_come,String confirmed, String active,String recoverd, String deaths){
        this.state_come = state_come;
        this.active = active;
        this.confirmed =confirmed;
        this.deaths = deaths;
        this.recovred = recoverd;

    }


    public String getState_come() {
        return state_come;
    }

    public void setState_come(String state_come) {
        this.state_come = state_come;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovred() {
        return recovred;
    }

    public void setRecovred(String recovred) {
        this.recovred = recovred;
    }
}