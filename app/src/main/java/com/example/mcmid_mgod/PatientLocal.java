package com.example.mcmid_mgod;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class PatientLocal {

    public String firstname;
    public String lastname;
    public String sex;
    public String dob;
    public String insuranceNr;

    public PatientLocal(String firstname, String lastname, String sex, String dob, String insuranceNr) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.dob = dob;
        this.insuranceNr = insuranceNr;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String toString() {
        return firstname + " " + lastname + " " + dob + " " + sex + " " + insuranceNr;
    }

}
