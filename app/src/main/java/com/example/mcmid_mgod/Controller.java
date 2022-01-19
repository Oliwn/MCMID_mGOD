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
    public static PatientLocal currentPatient = new PatientLocal("Test", "Testing", "male", new Date(2000,1,1), "1234567890");
    public static IIdType lastObservationId = null;

    public static ArrayList<PatientLocal> getAllPatients() {
        patientList = communicator.getAllPatients();
        return patientList;
    }

    public static void addBloodPressure(int sys, int dia, String additionalInfos) {
        communicator.addObservation(sys +"/" + dia, "BP", additionalInfos);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean login(String username, String password) {
        return communicator.login(username, password);
    }
}
