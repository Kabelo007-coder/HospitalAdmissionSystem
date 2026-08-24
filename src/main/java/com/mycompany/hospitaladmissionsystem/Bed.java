/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author Kabelo Mogale
 */
public class Bed {

    private String bedNumber; // e.g. "B01"
    private boolean occupied;
    private String patientID;  // ID of the patient occupying this bed, or null if empty

    public Bed(String bedNumber) {
        this.bedNumber = bedNumber;
        this.occupied = false;
        this.patientID = null;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public String getPatientID() {
        return patientID;
    }

    /** Marks this bed as occupied by the given patient. */
    public void occupy(String patientID) {
        this.occupied = true;
        this.patientID = patientID;
    }

    /** Frees this bed so it can be allocated to someone else. */
    public void release() {
        this.occupied = false;
        this.patientID = null;
    }
}
