/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author Harvard Keyz
 */
public class Inpatient extends Patient {

    private int wardNumber;
    private String bedNumber; // "Not Assigned" until a bed is allocated

    public Inpatient(String patientID, String firstName, String lastName, int age,
                      String gender, String medicalCondition,
                      int wardNumber, String bedNumber) {
        // super() initialises everything the Inpatient inherits from Patient.
        // The category is always INPATIENT for this subclass.
        super(patientID, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    public int getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    /**
     * Overrides the base version so an Inpatient's printout also shows
     * which ward and bed they are occupying.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("      Ward Number: " + wardNumber + " | Bed Number: " + bedNumber);
    }
}

