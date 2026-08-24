/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author Harvard Keyz
 */
public enum PatientCategory {

    INPATIENT("Inpatient"),
    OUTPATIENT("Outpatient"),
    EMERGENCY("Emergency");

    private final String label;

    PatientCategory(String label) {
        this.label = label;
    }

    // Gives us a nicely capitalised label whenever the enum is printed,
    // e.g. "Inpatient" instead of "INPATIENT".
    @Override
    public String toString() {
        return label;
    }
}