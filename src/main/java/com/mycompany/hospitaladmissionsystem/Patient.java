/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author Kabelo Mogale
 */
public class Patient {

    private String patientID;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category;

    public Patient(String patientID, String firstName, String lastName, int age,
                   String gender, String medicalCondition, PatientCategory category) {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    // ----- Getters -----
    public String getPatientID() {
        return patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public PatientCategory getCategory() {
        return category;
    }

    // ----- Setters used by the Update feature -----
    // (Category is intentionally NOT editable here: an Inpatient carries
    // extra ward/bed data that a plain Patient object has no room for,
    // so changing category after registration is not supported. Staff
    // can delete and re-register the patient under the correct category.)
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    /**
     * Prints this patient's details to the console.
     * The Inpatient subclass overrides this to also show ward/bed info.
     */
    public void displayDetails() {
        System.out.println("ID: " + patientID
                + " | Name: " + firstName + " " + lastName
                + " | Age: " + age
                + " | Gender: " + gender
                + " | Condition: " + medicalCondition
                + " | Category: " + category);
    }
}
