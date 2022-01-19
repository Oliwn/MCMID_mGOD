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
    public static PatientLocal currentPatient = new PatientLocal("Test", "Testing", "male", "2000-01-01", "1234567890");
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

    public static void addBloodPressure(String value, String additionalInfos) {
        communicator.addObservation(value, "BP", additionalInfos);
    }

    public static void addBloodPressure(int sys, int dia, String additionalInfos) {
        communicator.addObservation(sys +"/" + dia, "BP", additionalInfos);
    }

    public static void addWeight(String weight, String additionalInfos) {
        communicator.addObservation(weight, "weight", additionalInfos);
    }

    public static void addWeight(int weight, String additionalInfos) {
        communicator.addObservation(weight + "", "weight", additionalInfos);
    }

    //wenn diese direkt verwendet wird: type ist entweder "BP" oder "weight"!
    public static void addObservation(String value, String type, String additionalInfos) {
        communicator.addObservation(value, type, additionalInfos);
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

            retList.add(obs.getValue().toString() + " on " + temp + " Additional information: " + obs.getInterpretation().get(0).getText());
                    //obs.getValueStringType().toString() +
        }
        return retList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean login(String username, String password) {
        return communicator.login(username, password);
    }
}
