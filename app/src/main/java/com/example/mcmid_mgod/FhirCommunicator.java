package com.example.mcmid_mgod;

import android.app.Activity;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.checkerframework.checker.units.qual.C;
import org.hl7.fhir.r4.model.Base;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Person;
import org.hl7.fhir.r4.model.StringType;
import org.hl7.fhir.r4.model.Type;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;

public class FhirCommunicator {
    FhirContext ctx = FhirContext.forR4();;
    private String url= "https://fhir.ls.technikum-wien.at/fhir";
    IGenericClient client = ctx.newRestfulGenericClient(url);
    private String TAG = "communicator";
    public FhirCommunicator() {
        //ctx
        //client ;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public ArrayList<PatientLocal> getAllPatients() {
        Bundle results = client.search()
                .forResource(Patient.class)
                .where(Patient.IDENTIFIER.hasSystemWithAnyCode("dicado"))
                .returnBundle(Bundle.class)
                .execute();

        ArrayList<PatientLocal> list = new ArrayList<PatientLocal>();

        //while (results != null) {
            for (int i = 0; i < results.getEntry().size(); i++) {
                Patient patFhir = (Patient)results.getEntry().get(i).getResource();
                List<Identifier> id = patFhir.getIdentifier();
                if (id.size() > 0) {
                    //Log.d(TAG, id.get(0).getSystem() + "" );
                }
                if (id.size() == 0 || !id.get(0).getSystem().equals("dicado")) {
                    //Log.d(TAG, "skipped");
                    continue;
                }
                HumanName name = patFhir.getName().get(0);

                String sex = null;
                if (patFhir.getGender() == Enumerations.AdministrativeGender.FEMALE) {
                    sex = "female";
                }
                else {
                    sex = "male";
                }

                PatientLocal pat = new PatientLocal(name.getGivenAsSingleString(), name.getFamily(), sex, patFhir.getBirthDate(), patFhir.getIdentifier().get(0).getValue());
                list.add(pat);
                //Log.d(TAG, list.size() + "");
            }
            /*if (results.getLink(Bundle.LINK_NEXT) != null) {
                results = client.loadPage().next(results).execute();
            }*/
        //}
        return list;
    }

    //wenn diese direkt verwendet wird: type ist entweder "BP" oder "weight"!
    public void addObservation(String value, String type, String additionalInfos) {
        Observation obs = new Observation();
        CodeableConcept cat = new CodeableConcept();
        Coding cod = new Coding();

        //add Category
        cod.setSystem("dicado");
        cod.setCode(type);
        cat.addCoding(cod);
        obs.addCategory(cat);

        //add identifier
        Identifier id = new Identifier();
        id.setSystem("dicado");
        id.setValue(Controller.currentPatient.insuranceNr);
        obs.getSubject().setIdentifier(id);

        //add value and timestamp
        obs.setValue(new StringType(value));
        obs.setEffective(new DateTimeType(Calendar.getInstance().getTime()));

        //add additionalInfos
        CodeableConcept interpretation = new CodeableConcept();
        interpretation.setText(additionalInfos);
        obs.addInterpretation(interpretation);

        MethodOutcome outcome = client.create().resource(obs).execute();

        Log.d(TAG, outcome.getResource().getClass() + "");
        Controller.lastObservationId = outcome.getId();
    }

    public void addBloodPressure(int sys, int dia, String additionalInfos) {
        addObservation(sys + "/" + dia, "BP", additionalInfos);
    }

    public void addWeight(int weight, String additionalInfos) {
        addObservation(weight + "", "weight", additionalInfos);
    }

    public Observation getLastObservation() {
        Observation obs = null;
        if (Controller.lastObservationId != null) {
            obs = client.read()
                    .resource(Observation.class)
                    .withId(Controller.lastObservationId)
                    .execute();
            Log.d(TAG, "observation: " + obs.getIdentifier().get(0).getValue());
            return obs;
        }

        Bundle results = client.search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .execute();

        if (Controller.currentPatient == null) {
            return null;
        }

        for (int i = results.getEntry().size()-1; i >= 0; i--) {
            obs = (Observation) results.getEntry().get(results.getEntry().size() - 1).getResource();
            if (obs.getIdentifier().get(0).getValue().equals(Controller.currentPatient.insuranceNr)) {
                Log.d(TAG, "observation: " + obs.getIdentifier().get(0).getValue());
                return obs;
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean login(String username, String password) {
        Bundle results = client.search()
                .forResource(Person.class)
                .returnBundle(Bundle.class)
                .execute();

        for (int i = 0; i < results.getEntry().size(); i++) {
            Person user = (Person) results.getEntry().get(i).getResource();
            if (user.getIdentifier().get(0).getValue().equals(username) && new String(Base64.getDecoder().decode(user.getName().get(0).getText())).equals(password)) {
                return true;
            }
        }
        return false;
    }
}
