package com.example.mcmid_mgod;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.hl7.fhir.instance.model.api.IIdType;
import org.hl7.fhir.r4.model.Observation;

import java.util.ArrayList;
import java.util.Date;

public class Controller {
    private static FhirCommunicator communicator = new FhirCommunicator();
    public static ArrayList<PatientLocal> patientList = new ArrayList<PatientLocal>();
    public static PatientLocal currentPatient = new PatientLocal("Test", "Testing", "male", "2000-01-01", "1111010100");
    public static IIdType lastObservationId = null;
    public static ArrayList<Observation> observations = null;

    public static ArrayList<PatientLocal> getAllPatients() {
        patientList = communicator.getAllPatients();
        return patientList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<String> getAllPatientsAsString() {
        patientList = communicator.getAllPatients();
        if (patientList == null) {
            return null;
        }

        ArrayList<String> retList = new ArrayList<String>();
        for (PatientLocal p : patientList) {
            retList.add(p.toString());
        }
        return retList;
    }

    public static void addBloodPressure(String value) {
        communicator.addObservation(value, "BP");
    }

    public static void addBloodPressure(int sys, int dia) {
        communicator.addObservation(sys +"/" + dia, "BP");
    }

    public static void addWeight(String weight) {
        communicator.addObservation(weight, "weight");
    }

    /*public static void addWeight(int weight) {
        communicator.addObservation(weight + "", "weight");
    }*/

    public static void addAdditionalInformation(String additional) {
        communicator.addObservation(additional, "additional");
    }

    //wenn diese direkt verwendet wird: type ist entweder "BP" oder "weight"!
    public static void addObservation(String value, String type) {
        communicator.addObservation(value, type);
    }

    //Wert der Observation bekommt man so:  obs.getValueStringType().toString()
    //Additional info so:  obs.getInterpretation().get(0).getText()
    public static Observation getLastObservation() {
        return communicator.getLastObservation();
    }

    public static ArrayList<Observation> getAllObservations() {
        observations = communicator.getAllObservations();
        return observations;
    }

    public static ArrayList<String> getAllObservationsAsString() {
        observations = communicator.getAllObservations();
        if (observations == null) {
            return null;
        }
        ArrayList<String> retList = new ArrayList<String>();
        for (Observation obs : observations) {
            String temp = obs.getEffectiveDateTimeType().getYear() + "-" + (obs.getEffectiveDateTimeType().getMonth()+1) + "-" + obs.getEffectiveDateTimeType().getDay();

            String entry = null;
            switch (obs.getCategory().get(0).getCoding().get(0).getCode()) {
                case ("BP"):
                    entry = "Blood Pressure: " + obs.getValue().toString() + "mmHg on " + temp;
                    break;
                case("weight"):
                    entry = "Weight: " + obs.getValue().toString() + "kg measured on " + temp;
                    break;
                case("additional"):
                    entry = "Additional information on " + temp + ": " + obs.getValue().toString();
                    break;
            }
            if (entry != null) {
                retList.add(entry);  //obs.getCategory().get(0).getCoding().get(0).getCode() + ": " + obs.getValue().toString() + " on " + temp);  // + " Additional information: " + obs.getInterpretation().get(0).getText());
            }
        }
        return retList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean login(String username, String password) {
        return communicator.login(username, password);
    }
}
