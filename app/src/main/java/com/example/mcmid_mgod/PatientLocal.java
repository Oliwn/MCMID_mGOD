package com.example.mcmid_mgod;

import java.util.Date;

public class PatientLocal {

    public String firstname;
    public String lastname;
    public String sex;
    public Date dob;
    public String insuranceNr;

    public PatientLocal(String firstname, String lastname, String sex, Date dob, String insuranceNr) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.dob = dob;
        this.insuranceNr = insuranceNr;
    }

}
