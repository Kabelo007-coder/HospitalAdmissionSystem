/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.hospitaladmissionsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalAdmissionSystemTest {

    private Method setUpWard;
    private Method findPatientIndex;
    private Method findBedByNumber;
    private Method countAvailableBeds;
    private Method countOccupiedBeds;

    @BeforeEach
    void setUp() throws Exception {
        setStaticField("patients", new Patient[50]);
        setStaticField("patientCount", 0);
        setStaticField("beds", new Bed[20]);

        setUpWard = getPrivateMethod("setUpWard");
        findPatientIndex = getPrivateMethod("findPatientIndex", String.class);
        findBedByNumber = getPrivateMethod("findBedByNumber", String.class);
        countAvailableBeds = getPrivateMethod("countAvailableBeds");
        countOccupiedBeds = getPrivateMethod("countOccupiedBeds");

        setUpWard.invoke(null);
    }

    @Test
    void testSetUpWardCreates20BedsCorrectly() throws Exception {
        Bed[] beds = getBeds();
        assertEquals(20, beds.length);
        assertEquals("B01", beds[0].getBedNumber());
        assertEquals("B20", beds[19].getBedNumber());
        assertFalse(beds[0].isOccupied());
    }

    //... rest of your tests stay the same...

    // --- HELPER METHODS ---
    private void addTestPatient(Patient p) throws Exception {
        Field patientsField = HospitalAdmissionSystem.class.getDeclaredField("patients");
        Field countField = HospitalAdmissionSystem.class.getDeclaredField("patientCount");
        patientsField.setAccessible(true);
        countField.setAccessible(true);
        Patient[] patients = (Patient[]) patientsField.get(null);
        int count = (int) countField.get(null);
        patients[count] = p;
        countField.set(null, count + 1);
    }
    private Bed[] getBeds() throws Exception {
        Field f = HospitalAdmissionSystem.class.getDeclaredField("beds");
        f.setAccessible(true);
        return (Bed[]) f.get(null);
    }
    private int getPatientCount() throws Exception {
        Field f = HospitalAdmissionSystem.class.getDeclaredField("patientCount");
        f.setAccessible(true);
        return (int) f.get(null);
    }
    private Patient[] getPatients() throws Exception {
        Field f = HospitalAdmissionSystem.class.getDeclaredField("patients");
        f.setAccessible(true);
        return (Patient[]) f.get(null);
    }
    private void setStaticField(String name, Object value) throws Exception {
        Field f = HospitalAdmissionSystem.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(null, value);
    }
    private Method getPrivateMethod(String name, Class<?>... params) throws Exception {
        Method m = HospitalAdmissionSystem.class.getDeclaredMethod(name, params);
        m.setAccessible(true);
        return m;
    }
}